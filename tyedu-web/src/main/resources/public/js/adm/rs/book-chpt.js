const prefix = "/rs/book-chpt";
const app = Vue.createApp({
  extends: baseApp,
  data() {
    return {
      enableMenuInfer: true,
      bid: _bid,
      bname: _bname,
      subject: _subject,
      edition: _edition,
      // 数据
      chptList: [],
      selectedChpt: [],
      // 表单数据
      formData: {
        chptId: null,
        parentId: null,
        chptName: null,
        importance: 3
      },
      // 模态窗口
      winDialog: false,
      dialogTitle: '',
      // 数据字典
      subjectList: []
    }
  },
  computed: {
    title() {
      return [this.bname, this.subjectMap[this.subject], this.edition].join(" · ");
    },
    subjectMap() {
      t(this.subjectList);
      return toMap(this.subjectList);
    },
    chptTreeData() {
      return this.wrapTreeData(this.chptList, 'chptId');
    },
    parentItem() {
      return this.selectedChpt?.length > 0? this.selectedChpt[0] : {chptId: 0, chptName: "根节点"};
    }
  },
  mounted() {
    // 页面渲染完成后，计算辅助元素的总高度
    this.$nextTick(() => {
      this.assistHeight = calcAssistHeight();
    });

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
      this.loading = true;
      this.selectedChpt = [];
      doAjaxGet(this.url(`${prefix}/${this.bid}/list`), null, result => {
        if (result.state) {
          this.chptList = result.data || [];
        } else {
          this.toast(result.message, 'warning');
        }
      })
    },

    /*
     * 查询学科列表
     */
    doQuerySubject() {
      if (_stage) {
        this.loadDict("subject/" + _stage, result => {
          this.subjectList = result.data || [];
        })
      }
    },

    /*
     * 打开表单编辑画面
     */
    openFormDialog(title, id) {
      this.formData.chptId = id || null;
      this.formData.parentId = this.parentItem.chptId;
      this.dialogTitle = title;
      this.winDialog = true;

      // 查询记录详情
      if (id) {
        this.posting = true;
        doAjaxGet(this.url(`${prefix}/${this.bid}/single/${id}`), null, (result) => {
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
      this.formData.importance = 3;
      this.resetForm();
    },

    /*
     * 提交表单数据
     */
    doSubmit() {
      this.posting = true;
      this.method = this.formData.chptId? "update" : "save";
      doAjaxPost(this.url(`${prefix}/${this.bid}/${this.method}`), this.formData, (result) => {
        if (result.state) {
          this.toast("操作成功");
          this.closeFormDialog();
          this.doQuery();
        } else {
          this.toast(result.message, 'warning');
        }
      });
    },
    /*
     * 删除数据
     */
    doDelete(chptId) {
      this.method = "del";
      doAjaxGet(this.url(`${prefix}/${this.bid}/del/${chptId}`), null, (result) => {
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