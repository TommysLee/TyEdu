const prefix = "/sch/wrong/que";
const app = Vue.createApp({
  extends: baseApp,
  data() {
    return {
      menuName: 'WrongQueBank',
      menuDisplayName: '我的错题',
      vnode: null,
      backUrl: '/',

      // 查询条件
      param: {
        sourceType: null,
        grade: null,
        type: null
      },

      // 数据
      knowledgeList: [],
      selectedKnowledge: [],
      qtypeList: [],
      dataList: [],

      // 数据字典
      dictConfig: {
        "stage": "stageList",
        "exam_type": "sourceList"
      },
      stage: null,
      subjectList: [],
      selectedSubject: [],
      sourceList: [],
      gradeList: []
    }
  },
  computed: {
    stageMap() {
      return toMap(this.stageList);
    },
    subjectMap() {
      return toMap(this.subjectList);
    },
    gradeMap() {
      return toMap(this.gradeList);
    },
    sourceMap() {
      return toMap(this.sourceList);
    },
    qtypeMap() {
      return toMap(this.qtypeList)
    },
    knowledgeTreeData() {
      return this.wrapTreeData(this.knowledgeList, 'kid');
    },
    subject() {
      return this.selectedSubject?.length > 0? this.selectedSubject[0] : null;
    },
    stageSujectText() {
      return this.stageMap[this.stage] + '' + (this.subjectMap[this.subject] || '');
    }
  },
  watch: {
    stage() {
      this.selectedSubject = [];
      this.param.source = null;
      this.param.grade = null;
      this.doQuerySubject();
      this.doQueryGrade();
    },
    selectedSubject(val) {
      if (val?.length) {
        this.doQuery();
        this.doQueryQTypes();
        this.doQueryKnowledges();
      }
    }
  },
  mounted() {
    this.vnode = this.$refs.container;
    this.stage = this.param.stage?? this.stage;
    if (!this.stage) {
      this.doQueryStu();
    }
  },
  methods: {
    /*
     * 查询学生信息
     */
    doQueryStu() {
      doAjaxGetSimple(this.url(`/bbd/stu/info`), null , result => {
        const stu = result.data || {};
        this.stage = stu.stage || 'XX';
      })
    },

    /*
     * 执行条件查询
     */
    doQuery() {
      saveQueryParam(this.menuName, {stage: this.stage, subject: this.subject, sourceType: this.param.sourceType});
      this.loading = true;
      this.param.page = this.pagination.page;
      this.param.pageSize = this.pagination.pageSize;
      this.param.ktagsId = this.selectedKnowledge;

      doAjaxPost(this.url(`${prefix}/${this.stage}/${this.subject}/list`), this.param, result => {
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
        })
      }
    },

    /*
     * 查询年级列表
     */
    doQueryGrade() {
      if (this.stage) {
        this.loadDict(`grade/${this.stage}`, result => {
          this.gradeList = result.data || [];
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
     * 查询知识点
     */
    doQueryKnowledges() {
      doAjaxGetSimple(this.url(`/rs/knowledge/list/${this.stage}/${this.subject}`), null, result => {
        this.knowledgeList = result.data || [];
        this.selectedKnowledge = [];
      })
    },

    /*
     * 删除错题
     */
    doDelete(qid) {
      doAjaxGet(`${prefix}/del/${qid}`, null, result => {
        if (result.state) {
          this.toast("操作成功");
          this.doQuery();
        } else {
          this.toast(result.message, 'warning');
        }
      })
    }
  }
});
const appInstance = baseApp.uses(app).mount('#app');