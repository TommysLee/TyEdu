const prefix = "/sch/exam/que";
const app = Vue.createApp({
  extends: baseApp,
  data() {
    return {
      menuName: 'Exam',
      backUrl: `/sch/exam/que/${_examId}/view`,
      qid: _qid,

      // 数据
      content: null,
      knowledgeList: [],
      selectedKnowledge: _selectedKnowledge,
      chptList: [],
      chptTreeData: [],
      selectedChpt: _selectedChpt
    }
  },
  computed: {
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
      let items = [_title];
      items.push(this.$t("打标"), this.$t('题号') + ":" + this.qid.toString());
      return items;
    }
  },
  mounted() {
    this.doQueryDetail();
    this.doQueryKnowledges();
    this.doQueryBook();
  },
  methods: {
    /*
     * 查询记录详情
     */
    doQueryDetail() {
      this.loading = true;
      doAjaxGet(this.url(`${prefix}/${_examId}/single/${this.qid}`), null, result => {
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
      doAjaxGet(this.url(`/rs/knowledge/list/${_stage}/${_subject}`), null, result => {
        this.knowledgeList = result.data || [];
      })
    },

    /*
     * 查询教材
     */
    doQueryBook() {
      doAjaxGet(this.url(`/rs/book/${_stage}/${_subject}/list`), null, result => {
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
      this.selectedChpt = this.selectedChpt.filter(v => v !== id)
    },

    /*
     * 移除知识点标数据
     */
    removeKTags(id) {
      this.selectedKnowledge = this.selectedKnowledge.filter(v => v !== id)
    },

    /*
     * 提交章节标数据
     */
    doSubmitChptMarked() {
      this.posting = true;
      const data = [];
      for (let cid of this.selectedChpt) {
        data.push({chptId: cid, chptName: this.chptMap[cid]});
      }
      doAjaxBody(this.url(`/sch/que-chapter/upsert/${this.qid}`), data, result => {
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
      const data = [];
      for (let kid of this.selectedKnowledge) {
        data.push({kid, kname: this.knowledgeMap[kid]});
      }
      doAjaxBody(this.url(`/sch/que-knowledge/upsert/${this.qid}`), data, result => {
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