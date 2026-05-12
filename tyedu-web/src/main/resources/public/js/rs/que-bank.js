// 初始化Vue
const app = Vue.createApp({
  extends: baseApp,
  data() {
    return {
      // 查询条件
      param: {
      },

      // 菜单
      menuList: [
        {value: 'chpt', title: '章节选题'},
        {value: 'k', title: '知识点选题'}
      ],
      selectedMenu: 'chpt',

      // 数据
      bookList: [],
      selectedBook: [],
      chptList: [],
      selectedChpt: [],
      knowledgeList: [],
      selectedKnowledge: [],
      qtypeList: [],

      // 数据字典
      dictConfig: {
        "stage": "stageList"
      },
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
    // 加载学科列表
    this.doQuerySubject();
  },
  methods: {
    /*
     * 执行条件查询
     */
    doQuery() {
      console.log("doQuery ....", "默认条件：stage、subject");
      this.loading = true;
    },

    /*
     * 查询学科列表
     */
    doQuerySubject() {
      if (this.stage) {
        this.loadDict("subject/" + this.stage, result => {
          this.subjectList = result.data || [];
          if (this.subjectList?.length > 0) {
            this.selectedSubject = [this.subjectList[0].value];
          }
        })
      }
    },

    /*
     * 查询学科对应的题目类型
     */
    doQueryQTypes() {
      if (this.subject) {
        this.loadDict("qtype/" + this.stage + "/" + this.subject, result => {
          this.qtypeList = result.data || [];
        })
      }
    },

    /*
     * 查询教材版本
     */
    doQueryEdition() {
      if (this.subject) {
        this.loadDict("edition/" + this.stage + "/" + this.subject, result => {
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
    }
  }
});
const appInstance = baseApp.uses(app).mount('#app');