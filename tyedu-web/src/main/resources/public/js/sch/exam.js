const prefix = "/sch/exam";
const app = Vue.createApp({
  extends: baseApp,
  data() {
    return {
      menuName: 'Exam',
      vnode: null,
      backUrl: '/',

      // 查询条件
      param: {
        subject: null,
        examType: null,
        grade: null
      },

      // 数据
      stu: null,
      dataList: [],

      // 表单数据
      formData: {
        examId: null,
        title: null,
        examType: null,
        examTime: null,
        stage: null,
        subject: null,
        grade: null,
        classRank: null
      },
      // 模态窗口
      winDialog: false,
      dialogTitle: '',

      // 数据字典
      dictConfig: {
        "stage": "stageList",
        "exam_type": "examTypeList"
      },
      stage: null,
      subjectList: [],
      examTypeList: [],
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
    examTypeMap() {
      return toMap(this.examTypeList);
    },
    gradeMap() {
      return toMap(this.gradeList);
    }
  },
  watch: {
    stage() {
      this.param.subject = null;
      this.param.grade = null;
      this.doQuerySubject();
      this.doQueryGrade();
      this.doQuery();
    }
  },
  mounted() {
    this.doQueryStu();
    this.vnode = this.$refs.container;
  },
  methods: {
    /*
     * 执行条件查询
     */
    doQuery() {
      this.loading = true;
      this.param.stage = this.stage;
      this.param.page = this.pagination.page;
      this.param.pageSize = this.pagination.pageSize;
      saveQueryParam(this.menuName, this.param);
      doAjaxPost(this.url(`${prefix}/${this.stage}/list`), this.param, result => {
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
     * 查询学生信息
     */
    doQueryStu() {
      doAjaxGetSimple(this.url(`/bbd/stu/info`), null , result => {
        this.stu = result.data || {};
        this.stage = this.stu.stage || 'XX';
      })
    },

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
     * 打开表单编辑画面
     */
    openFormDialog(title, id) {
      this.formData.examId = id || null;
      this.dialogTitle = title;
      this.winDialog = true;

      // 查询记录详情
      if (id) {
        this.posting = true;
        doAjaxGet(this.url(`${prefix}/single/${id}`), null, (result) => {
          if (result.state) {
            this.mergeValue(this.formData, result.data);
          } else {
            this.toast(result.message, 'warning');
          }
        });
      }
    },

    /*
     * 关闭表单编辑画面
     */
    closeFormDialog() {
      this.winDialog = false;
      this.resetForm();
      this.doQuery();
    },

    /*
     * 提交表单数据
     */
    doSubmit() {
      this.posting = true;
      this.formData.stage = this.stage;
      this.method = this.formData.examId? "update" : "save";
      doAjaxPost(this.url(`${prefix}/${this.method}`), this.formData, (result) => {
        if (result.state) {
          this.toast("操作成功");
          this.closeFormDialog();
        } else {
          this.toast(result.message, 'warning');
        }
      });
    },

    /*
     * 删除数据
     */
    doDelete(examId) {
      this.method = "del";
      doAjaxGet(this.url(`${prefix}/del/${examId}`), null, (result) => {
        if (result.state) {
          this.toast("操作成功");
          this.doQuery();
        } else {
          this.toast(result.message, 'warning');
        }
      });
    },

    /*
     * 发布考试
     */
    doPublished(examId) {
      doAjaxGet(this.url(`${prefix}/${examId}/ustatus/publish/1`), null, (result) => {
        if (result.state) {
          this.toast("操作成功");
          this.doQuery();
        } else {
          this.toast(result.message, 'warning');
        }
      });
    },

    /*
     * 进入考试题目列表页
     */
    goExamQueView(examId) {
      window.location.href = this.url(`/sch/exam/que/${examId}/view`);
    }
  }
});
const appInstance = baseApp.uses(app).mount('#app');