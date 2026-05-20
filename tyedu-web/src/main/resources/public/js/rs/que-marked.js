const prefix = "/rs/que";
const app = Vue.createApp({
  extends: baseApp,
  data() {
    return {
      menuName: 'QueBank',
      backUrl: '/rs/que-bank',

      // 参数
      stage: _stage,
      subject: _subject,
      qid: _qid,

      // 数据
      content: null,
      knowledgeList: [],
      selectedKnowledge: _selectedKnowledge,
      chptList: [],
      chptTreeData: [],
      selectedChpt: _selectedChpt,

      // 数据字典
      dictConfig: {
        "stage": "stageList"
      },
      qtypeList: [],
      subjectList: []
    }
  },
  computed: {
    stageMap() {
      return toMap(this.stageList);
    },
    subjectMap() {
      return toMap(this.subjectList);
    },
    chptMap() {
      return toMap(this.chptList, 'chptId', 'chptName')
    },
    knowledgeMap() {
      return toMap(this.knowledgeList, 'kid', 'kname')
    },
    knowledgeTreeData() {
      return this.wrapTreeData(this.knowledgeList, 'kid');
    },
    breadcrumbs() {
      let items = [];
      items.push(this.stageMap[this.stage] + this.subjectMap[this.subject]);
      items.push(this.$t("打标"), this.$t('题号') + ":" + this.qid.toString());
      return items;
    }
  },
  mounted() {
    saveQueryParam(this.menuName, {stage: this.stage, subject: this.subject});
    this.doQuerySubject();
    this.doQueryDetail();
    this.doQueryKnowledges();
    this.doQueryBook();
  },
  methods: {
    /*
     * 查询学科列表
     */
    doQuerySubject() {
      if (this.stage) {
        this.loadDict(`subject/${this.stage}`, result => {
          this.subjectList = result.data || [];
        })
      }
    },

    /*
     * 查询记录详情
     */
    doQueryDetail() {
      this.loading = true;
      doAjaxGet(this.url(`${prefix}/single/${this.qid}`), null, result => {
        if (result.state) {
          this.content = result.data.stem || null;
        } else {
          this.toast(result.message, 'warning');
        }
        this.$nextTick(() => {
          this.assistHeight = calcAssistHeight();
        })
      })
    },

    /*
     * 查询知识点
     */
    doQueryKnowledges() {
      this.loading = true;
      doAjaxGet(this.url(`/rs/knowledge/list/${this.stage}/${this.subject}`), null, result => {
        this.knowledgeList = result.data || [];
      })
    },

    /*
     * 查询教材
     */
    doQueryBook() {
      doAjaxGet(this.url(`/rs/book/${this.stage}/${this.subject}/list`), null, result => {
        let data = result.data || [];
        let arr = [];
        let id = new Date().getTime();
        for (let item of data) {
          arr.push({chptId: (id + item.bid), bid: item.bid, chptName: (item.bname + '（' + item.editionTitle + '）'), prependIcon: 'mdi-bookshelf', baseColor: 'teal', disabled: true, children: []})
        }
        this.chptTreeData = arr;
      })
    },

    /*
     * 查询章节目录
     */
    doQueryChpts(item) {
      return doAjaxGetSimple(this.url(`/rs/book-chpt/${item.bid}/list`), null, result => {
        let children = null;
        if (result.data) {
          children = this.wrapTreeData(result.data, 'chptId')
          this.chptList.push(...result.data);
        }
        item.children = children;
      })
    },

    /*
     * 移除章节标数据
     */
    removeCTags(id) {
      console.log("ctag=", id);
      this.selectedChpt = this.selectedChpt.filter(v => v !== id)
      console.log(this.selectedChpt);
    },

    /*
     * 移除知识点标数据
     */
    removeKTags(id) {
      this.$nextTick(() => {
        console.log("Ktag=", id, typeof(id));
        console.log(this.selectedKnowledge);
        this.selectedKnowledge = this.selectedKnowledge.filter(v => v !== id)
        console.log(this.selectedKnowledge);
      })
    },

    /*
     * 提交章节标数据
     */
    doSubmitChptMarked() {
      this.posting = true;
      doAjaxPost(this.url(`/rs/que-chapter/save/${this.qid}`), {ids: this.selectedChpt}, result => {
        if (result.state) {
          this.toast("操作成功");
        } else {
          this.toast(result.message, 'warning');
        }
      })
    },

    /*
     * 提交知识点标数据
     */
    doSubmitKnowledgeMarked() {
      this.posting = true;
      doAjaxPost(this.url(`/rs/que-knowledge/save/${this.qid}`), {ids: this.selectedKnowledge}, result => {
        if (result.state) {
          this.toast("操作成功");
        } else {
          this.toast(result.message, 'warning');
        }
      })
    }
  }
});
const appInstance = baseApp.uses(app).mount('#app');