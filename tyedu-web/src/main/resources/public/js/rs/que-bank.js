// 初始化Vue
const app = Vue.createApp({
  extends: baseApp,
  data() {
    return {
      menuName: 'QueBank',
      vnode: null,

      // 查询条件
      param: {
        type: null
      },

      // 菜单
      menuList: [
        {value: 'chpt', title: '章节选题'},
        {value: 'k', title: '知识点选题'}
      ],
      selectedMenu: null,

      // 数据
      bookList: [],
      selectedBook: [],
      chptList: [],
      selectedChpt: [],
      knowledgeList: [],
      selectedKnowledge: [],
      qtypeList: [],
      dataList: [],

      // 数据字典
      dictConfig: {
        "stage": "stageList"
      },
      stage: null,
      subjectList: [],
      selectedSubject: [],
      editionList: [],
      selectedEdition: []
    }
  },
  computed: {
    stageMap() {
      return toMap(this.stageList);
    },
    subjectMap() {
      return toMap(this.subjectList);
    },
    editionMap() {
      return toMap(this.editionList);
    },
    bookMap() {
      return toMap(this.bookList, 'bid', 'bname');
    },
    qtypeMap() {
      return toMap(this.qtypeList)
    },
    chptTreeData() {
      return this.wrapTreeData(this.chptList, 'chptId');
    },
    knowledgeTreeData() {
      return this.wrapTreeData(this.knowledgeList, 'kid');
    },
    subject() {
      return this.selectedSubject?.length > 0? this.selectedSubject[0] : null;
    },
    book() {
      return this.selectedBook?.length > 0? this.selectedBook[0] : null;
    },
    edition() {
      return this.editionList?.length > 0? this.selectedEdition[0] : null;
    },
    stageSujectText() {
      return this.stageMap[this.stage] + '' + (this.subjectMap[this.subject] || '');
    },
    editionBookText() {
      return (this.editionMap[this.edition] || '') + ' ' + (this.bookMap[this.book] || '');
    }
  },
  watch: {
    selectedMenu() {
      this.doQueryChpts();
      this.doQueryKnowledges();
    },
    stage() {
      this.selectedSubject = [];
      this.doQuerySubject();
    },
    selectedSubject(val) {
      if (val?.length) {
        this.doQuery();
        this.doQueryQTypes();
        this.doQueryEdition();
        this.doQueryKnowledges();
      }
    },
    selectedEdition() {
      this.doQueryBook();
    },
    selectedBook() {
      this.doQueryChpts();
    },
    bookList(val) {
      if (!(val?.length > 0)) {
        this.selectedBook = [];
      }
    }
  },
  mounted() {
    clearQueryParam();
    this.selectedMenu = this.param.selectedMenu || 'chpt';
    this.stage = this.param.stage || 'XX';
    this.vnode = this.$refs.qcontainer;
  },
  methods: {
    /*
     * 执行条件查询
     */
    doQuery() {
      saveQueryParam(this.menuName, {stage: this.stage, subject: this.subject, selectedMenu: this.selectedMenu});
      this.loading = true;
      this.param.page = this.pagination.page;
      this.param.pageSize = this.pagination.pageSize;

      doAjaxPost(this.url(`/rs/que/${this.stage}/${this.subject}/list`), this.param, result => {
        if (result.state) {
          let pageData = result.data;
          this.pagination.pageCount = pageData.pages; // 总页数
          this.dataList = addIndexPropForArray(pageData.data, this.pagination); // 数据集合
          this.scrollTop();
        } else {
          this.toast(result.message, 'warning');
        }
      })
    },

    /*
     * 查询学科列表
     */
    doQuerySubject() {
      if (this.stage) {
        this.loadDict(`subject/${this.stage}`, result => {
          this.subjectList = result.data || [];
          if (this.subjectList?.length > 0) {
            let selected = [this.subjectList[0].value];
            if (this.param.subject) {
              selected = [this.param.subject];
            }
            this.selectedSubject = selected;
          }
          this.clearParam();
        })
      }
    },

    /*
     * 查询学科对应的题目类型
     */
    doQueryQTypes() {
      if (this.subject) {
        this.loadDict(`qtype/${this.stage}/${this.subject}`, result => {
          this.qtypeList = result.data || [];
          this.param.type = null;
        })
      }
    },

    /*
     * 查询教材版本
     */
    doQueryEdition() {
      if (this.subject) {
        this.loadDict(`edition/${this.stage}/${this.subject}`, result => {
          this.editionList = result.data || [];
          if (this.editionList?.length > 0) {
            this.selectedEdition = [this.editionList[0].value];
          }
        })
      } else {
        this.editionList = [];
        this.selectedEdition = [];
      }
    },

    /*
     * 查询教材
     */
    doQueryBook() {
      if (this.edition) {
        doAjaxGetSimple(this.url(`/rs/book/${this.stage}/${this.subject}/${this.edition}/list`), null, result => {
          this.bookList = result.data || [];
          if (this.bookList?.length > 0) {
            this.selectedBook = [this.bookList[0].bid];
          }
        })
      } else {
        this.bookList = [];
        this.selectedBook = [];
      }
    },

    /*
     * 查询章节目录
     */
    doQueryChpts() {
      if ("chpt" === this.selectedMenu && this.selectedBook?.length > 0) {
        doAjaxGetSimple(this.url(`/rs/book-chpt/${this.book}/list`), null, result => {
          this.chptList = result.data || [];
          this.selectedChpt = [];
        })
      } else {
        this.chptList = [];
        this.selectedChpt = [];
      }
    },

    /*
     * 查询知识点
     */
    doQueryKnowledges() {
      if ("k" === this.selectedMenu) {
        doAjaxGetSimple(this.url(`/rs/knowledge/list/${this.stage}/${this.subject}`), null, result => {
          this.knowledgeList = result.data || [];
          this.selectedKnowledge = [];
        })
      } else {
        this.knowledgeList = [];
        this.selectedKnowledge = [];
      }
    },

    /*
     * 清空查询条件
     */
    clearParam() {
      for (let p of Object.keys(this.param)) {
        this.param[p] = null;
      }
    },

    /*
     * 前往编辑页面（新增/修改）
     */
    goEdit(qid) {
      let url = `/rs/que/${this.stage}/${this.subject}/edit/`;
      if (!(qid instanceof Event) && qid) {
        url += (qid + '/');
      }
      url += 'view';
      window.location.href = this.url(url)
    }
  }
});
const appInstance = baseApp.uses(app).mount('#app');