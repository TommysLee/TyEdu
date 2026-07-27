const prefix = "/bbd/stu";
const app = Vue.createApp({
  extends: baseApp,
  data() {
    return {
      vtheme: 'dark',
      stage: null,
      // 表单数据
      formData: {
        stage: null,
        school: null,
        name: null,
        grade: null,
        gradeTitle: null
      },
      // 数据字典
      dictConfig: {
        "stage": "stageList"
      },
      gradeList: []
    }
  },
  watch: {
    stage() {
      this.doQueryGrade();
    }
  },
  computed: {
    gradeMap() {
      return toMap(this.gradeList);
    }
  },
  mounted() {
    // 加载数据
    this.doQuery();
  },
  methods: {
    /*
     * 执行条件查询
     */
    doQuery() {
      this.loading = true;
      doAjaxGet(this.url(`${prefix}/info`), null , result => {
        if (result.state) {
          this.mergeValue(this.formData, result.data || {});
        } else {
          this.toast(result.message, 'warning');
        }
        this.stage = this.formData.stage || 'XX';
      })
    },

    /*
     * 查询年级列表
     */
    doQueryGrade() {
      this.loadDict(`grade/${this.stage}`, result => {
        this.gradeList = result.data || [];
      })
    },

    /*
     * 提交表单数据
     */
    doSubmit() {
      this.posting = true;
      this.formData.stage = this.stage;
      this.formData.gradeTitle = this.gradeMap[this.formData.grade];

      doAjaxPost(this.url(`${prefix}/save`), this.formData, (result) => {
        if (result.state) {
          this.toast("操作成功");
        } else {
          this.toast(result.message, 'warning');
        }
      });
    }
  }
});
const appInstance = baseApp.uses(app).mount('#app');