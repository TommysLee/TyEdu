const prefix = "/rs/book";
const app = Vue.createApp({
  extends: baseApp,
  data() {
    return {
      vtheme: 'dark',
      // 查询条件
      param: {
        bname: null,
        subject: null,
        stage: null
      },
      subjectList: [],
      // 数据表格
      datatable: {
        headers: [
          { title: '#', value:'index', align:"center", width: 60},
          { title: '教材名称', value:'bname'},
          { title: '学科', value:'subject'},
          { title: '版本', value:'editionTitle'},
          { title: '更新时间', value:'updateTime', align:"center", width:180},
          { title: '操作', value:'operation', align:"center"}
        ],
        items: [],
        total: 0
      },
      // 表单数据
      formData: {
        bid: null,
        bname: null,
        stage: null,
        subject: null,
        edition: null,
        editionTitle: null,
        remark: null
      },
      // 模态窗口
      winDialog: false,
      dialogTitle: '',
      // 数据字典
      dictConfig: {
        "stage": "stageList"
      },
      editionList: []
    }
  },
  watch: {
    stage() {
      this.param.subject = null;
      this.doQuerySubject();
      this.doQuery();
    },
    "formData.subject": function(val) {
      this.doQueryEdition(val);
    }
  },
  computed: {
    subjectMap() {
      t(this.subjectList);
      return toMap(this.subjectList);
    },
    editionMap() {
      return toMap(this.editionList);
    }
  },
  mounted() {
    // 页面渲染完成后，计算辅助元素的总高度
    this.$nextTick(() => {
      this.assistHeight = calcAssistHeight();
    })

    // 加载学科列表
    this.doQuerySubject();

    // 加载数据
    this.doQuery();
  },
  methods: {
    /*
     * 执行条件查询
     */
    doQuery() {
      if (!this.loading) {
        this.param.stage = this.stage;
        this.doQueryTable();
      }
    },

    /*
     * 查询数据表
     */
    doQueryTable() {
      this.loading = true;
      this.scrollDTableTop();
      doAjaxPost(this.url(`${prefix}/list`), this.param, (result) => {
        if (result.state) {
          this.datatable.items = addIndexPropForArray(result.data);
        } else {
          this.toast(result.message, 'warning');
        }
      });
    },

    /*
     * 查询学科列表
     */
    doQuerySubject() {
      this.loadDict("subject/" + this.stage, result => {
        this.subjectList = result.data || [];
      })
    },

    /*
     * 查询学科的教材版本
     */
    doQueryEdition(subject) {
      if (subject) {
        this.loadDict("edition/" + this.stage + "/" + subject, result => {
          this.editionList = result.data || [];
        })
      } else {
        this.editionList = [];
      }
    },

    /*
     * 重置查询表单
     */
    resetQueryForm() {
      if (this.method !== 'update') {
        this.resetForm('queryForm');
      }
      this.doQuery();
    },

    /*
     * 打开表单编辑画面
     */
    openFormDialog(title, id) {
      this.formData.bid = id || null;
      this.dialogTitle = title;
      this.winDialog = true;

      // 查询记录详情
      if (id) {
        this.posting = true;
        doAjaxGet(this.url(prefix + "/single/" + id), null, (result) => {
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
    },

    /*
     * 提交表单数据
     */
    doSubmit() {
      this.formData.stage = this.stage;
      let editionTitle = this.editionMap[this.formData.edition];
      if (!editionTitle) {
        this.formData.edition = null;
        return;
      }
      this.formData.editionTitle = editionTitle;

      this.posting = true;
      this.method = this.formData.bid? "update" : "save";
      doAjaxPost(this.url(prefix + "/" + this.method), this.formData, (result) => {
        if (result.state) {
          this.toast("操作成功");
          this.closeFormDialog();
          this.resetQueryForm();
        } else {
          this.toast(result.message, 'warning');
        }
      });
    },

    /*
     * 删除数据
     */
    doDelete(bid) {
      this.method = "del";
      doAjaxGet(this.url(prefix + "/del/" + bid), null, (result) => {
        if (result.state) {
          this.toast("操作成功");
          this.doQuery();
        } else {
          this.toast(result.message, 'warning');
        }
      });
    },

    /*
     * 跳转到章节管理模块
     */
    viewChpt(bid) {
      window.location.href = this.url(`/rs/book-chpt/${bid}/view`);
    }
  }
});
const appInstance = baseApp.uses(app).mount('#app');