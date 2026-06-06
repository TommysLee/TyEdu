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

      // 数据字典
      qtypeList: []
    }
  },
  computed: {
    qtypeMap() {
      return toMap(this.qtypeList)
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