const prefix = "/sch/exam/que";
const app = Vue.createApp({
  extends: baseApp,
  data() {
    return {
      menuName: 'Exam',
      vnode: null,
      backUrl: '/sch/exam/view',
      title: _title,
      published: _published,
      reviewed: _reviewed,

      // 抽屉窗口
      winDrawer: false,
      winDrawerWidth: 800,
      drawerTitle: '',

      // 编辑器
      editor: null,

      // 表单数据
      formData: {
        qid: null,
        response: null,
        score: null,
        maxScore: 1
      },
      queItem: null,

      // 数据
      dataList: [],
      stypes: [],
      sktags: [],

      // 分析助手
      eaa: null,

      // 图表
      stypesChartOptions: ApexBuilder.Bar.options('stypes', {
        dataLabels: {
          formatter(value) {
            return value + '道';
          }
        },
        tooltip: {
          y: { formatter: value => value + '道' }
        }
      }),
      stypesRateChartOptions: ApexBuilder.Bar.options('stypesRate', {
        dataLabels: {
          formatter(value) {
            return value + '%';
          }
        },
        colors: [function({ value }) {
          if (value < 60) {
            return '#FF4560';
          }
          return '#00BFA5';
        }],
        tooltip: {
          y: { formatter: value => value + '%' }
        }
      }),
      sktagsChartOptions: ApexBuilder.Radar.options('sktags', {
        chart: {
          toolbar: {
            offsetX: -30
          }
        },
        colors: ['#F9C80E'],
        xaxis: {
          labels: { show: false }
        },
        yaxis: {
          stepSize: 20,
          min: 0,
          max: 100
        },
        plotOptions: {
          radar: {
            polygons: {
              fill: { colors: ['#f8f8f8', '#fff'] }
            }
          }
        },
        tooltip: {
          y: { formatter: value => value + '%' }
        }
      }),

      // 数据字典
      qtypeList: []
    }
  },
  setup() {
    const maxScore = _maxScore, score = _score;
    const scoreRateVal = Vue.ref(0);
    const animatedScoreRate = VueUse.useTransition(scoreRateVal, { duration: 1000 });
    scoreRateVal.value = Math.round(score / maxScore * 100);
    const scoreRate = Vue.computed(() => Math.round(animatedScoreRate.value));
    return {maxScore, score, scoreRate};
  },
  computed: {
    qtypeMap() {
      return toMap(this.qtypeList)
    },
    stypesSeries() {
      if (!this.stypes || this.stypes?.length === 0) {
        return [];
      }
      return [{
        name: '题数',
        data: this.stypes,
        parsing: {
          x: 'title',
          y: 'count'
        }
      }]
    },
    stypesRateSeries() {
      if (!this.stypes || this.stypes?.length === 0) {
        return [];
      }
      return [{
        name: '得分率',
        data: this.stypes,
        parsing: {
          x: 'title',
          y: 'rate'
        }
      }]
    },
    sktagsSeries() {
      if (!this.sktags || this.sktags?.length === 0) return [];
      return [{
        name: "掌握情况",
        data: this.sktags,
        parsing: {
          x: 'kname',
          y: 'rate'
        }
      }]
    }
  },
  mounted() {
    this.doQuery();
    this.doQueryQTypes();
    this.vnode = this.$refs.container;
    this.$nextTick(() => {
      this.initEditor();
    });
  },
  methods: {
    /*
     * 执行条件查询
     */
    doQuery() {
      this.loading = true;
      doAjaxPost(this.url(`${prefix}/${_examId}/list`), null, result => {
        if (result.state) {
          this.dataList = addIndexPropForArray(result.data);
          this.scrollTop();
          this.initEAA();
        } else {
          this.toast(result.message, 'warning');
        }
      })
    },

    /*
     * 查询学科对应的题目类型
     */
    doQueryQTypes() {
      this.loadDict(`qtype/${_stage}/${_subject}`, result => {
        this.qtypeList = result.data || [];
      })
    },

    /*
     * 删除数据
     */
    doDelete(qid) {
      this.method = "del";
      doAjaxGet(this.url(`${prefix}/${_examId}/del/${qid}`), null, (result) => {
        if (result.state) {
          this.toast("操作成功");
          this.doQuery();
        } else {
          this.toast(result.message, 'warning');
        }
      });
    },

    /*
     * 更新批阅状态
     */
    doReviewed() {
      doAjaxGet(this.url(`/sch/exam/${_examId}/ustatus/review/1`), null, (result) => {
        if (result.state) {
          this.toast("操作成功");
          lazy(() => {
            window.location.reload();
          })
        } else {
          this.toast(result.message, 'warning');
        }
      });
    },

    /*
     * 提交学生作答
     */
    doSubmitResponse() {
      const response = this.editor.getSemanticHTML();
      doAjaxPost(this.url(`${prefix}/${_examId}/upsert/resp/${this.formData.qid}`), {response}, result => {
          if (result.state) {
            this.toast("操作成功");
            this.queItem.response = response;
          } else {
            this.toast(result.message, 'warning');
          }
      })
    },

    /*
     * 提交单题成绩
     */
    doSubmitScore() {
      doAjaxGet(this.url(`${prefix}/${_examId}/upsert/socre/${this.formData.qid}/${this.formData.score}`), null, result => {
        if (result.state) {
          this.toast("操作成功");
          this.queItem.score = this.formData.score;
        } else {
          this.toast(result.message, 'warning');
        }
      })
    },

    // 打开抽屉窗口
    openWinDrawer(que) {
      this.winDrawer = true;
      this.drawerTitle = this.$t('作答') + this.$t('与') + this.$t('批阅') + ' (' + this.$t('题号') + ': ' + que.index + ')';
      this.mergeValue(this.formData, que || {});
      this.queItem = que;
      TinyEditor.setContent(this.editor, this.formData.response);
    },

    // 关闭抽屉窗口
    closeWinDrawer() {
      this.winDrawer = false;
      this.resetValue(this.formData);
    },

    /*
     * 初始化编辑器
     */
    initEditor() {
      this.editor = Vue.markRaw(TinyEditor.init('#editor'));
    },

    /*
     * 初始化分析助手
     */
    initEAA() {
      if (this.reviewed) {
        this.$nextTick(() => {
          this.eaa = new ExamAnalysisAssistant(this.dataList, this.qtypeMap);
          this.stypes = this.eaa.getTypes();
          this.sktags = this.eaa.getKtags();
        })
      }
    },

    /*
     * 切换雷达图标签显隐
     */
    toggleRadarLabel(isMax) {
      this.sktagsChartOptions.xaxis.labels.show = isMax;
      this.sktagsChartOptions = {...this.sktagsChartOptions};
    },

    /*
     * 将题目拷贝到错题集
     */
    copyToWrong(item, confirm) {
      doAjaxGet(this.url(`${prefix}/${_examId}/copy/${item.qid}/${item.index}`), null, result => {
        if (result.state) {
          this.toast("操作成功");
        } else {
          this.toast(result.message, 'warning');
        }
        confirm.loading = false;
      })
    },

    /*
     * 前往编辑页面（新增/修改）
     */
    goEdit(qid) {
      let url = `${prefix}/${_examId}/edit/`;
      if (!(qid instanceof Event) && qid) {
        url += (qid + '/');
      }
      url += 'view';
      window.location.href = this.url(url)
    },

    /*
     * 前往打标页面
     */
    goMarked(qid) {
      window.location.href = this.url(`${prefix}/${_examId}/marked/${qid}/view`);
    }
  }
});
const appInstance = baseApp.uses(app).mount('#app');