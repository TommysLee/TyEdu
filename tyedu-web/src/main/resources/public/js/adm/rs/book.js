// 初始化Vue
const app = Vue.createApp({
  extends: baseApp,
  data() {
    return {
      // 查询条件
      param: {
        bName: null,
        subject: null,
        stage: null
      },
      subjectList: [],
      // 数据表格
      datatable: {
        headers: [
          { title: '#', value:'index', align:"center", width: 60},
          { title: '教材名称', value:'bName'},
          { title: '学科', value:'subject'},
          { title: '创建时间', value:'createTime', align:"center", width:180},
          { title: '操作', value:'operation', align:"center"}
        ],
        items: [],
        total: 0
      },
      // 表单数据
      formData: {
        bId: null,
        bName: null,
        stage: null,
        subject: null,
        remark: null,
      },
      // 模态窗口
      winDialog: false,
      dialogTitle: '',
      // 数据字典
      dictConfig: {
        "stage": "stageList"
      }
    }
  },
  watch: {
    stage(val) {
      this.param.subject = null;
      this.doQuerySubject();
      this.doQuery();
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
      doAjaxPost(this.url("/rs/book/list"), this.param, (result) => {
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
      doAjaxGetSimple(this.url("/dict/subject/" + this.stage), null, result => {
        this.subjectList = result.data || [];
      })
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
      this.formData.bId = id || null;
      this.dialogTitle = title;
      this.winDialog = true;

      // 查询记录详情
      if (id) {
        this.posting = true;
        doAjaxGet(this.url("/rs/book/single/" + id), null, (result) => {
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
      this.posting = true;
      this.formData.stage = this.stage;
      this.method = this.formData.bId? "update" : "save";
      doAjaxPost(this.url("/rs/book/" + this.method), this.formData, (result) => {
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
    doDelete(bId) {
      this.method = "del";
      doAjaxGet(this.url("/rs/book/del/" + bId), null, (result) => {
        if (result.state) {
          this.toast("操作成功");
          this.doQuery();
        } else {
          this.toast(result.message, 'warning');
        }
      });
    }
  }
});
const appInstance = baseApp.uses(app).mount('#app');