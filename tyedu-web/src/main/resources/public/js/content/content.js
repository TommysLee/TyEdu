// 初始化Vue
const app = Vue.createApp({
  extends: baseApp,
  data() {
    return {
      menuDisplayName: '学习知识库',
      ctgList: [],
      selectedCtgId: [],

      dialogTitle: '',
      ctgWinDialog: false,

      // 分类表单数据
      ctgFormData: {
        ctgId: null,
        ctgName: null,
        enabled: 1,
        remark: null
      },

      // 条目表单数据
    }
  },
  computed: {
    ctgId() {
      return this.selectedCtgId && this.selectedCtgId.length > 0? this.selectedCtgId[0] : null;
    }
  },
  watch: {
    ctgList() {
      this.selectedCtgId = this.ctgList.length > 0? [this.ctgList[0].ctgId] : [];
    },
    selectedCtgId() {
      this.doQueryEntry();
    }
  },
  mounted() {
    this.doQuery();
  },
  methods: {
    /*
     * 执行查询
     */
    doQuery() {
      this.doQueryCategory();
    },

    /*
     * 查询分类
     */
    doQueryCategory() {
      doAjaxGet(this.url('/content/ctg/list'), null, result => {
        if (result.state) {
          let data = result.data || [];
          for (let i = 0; i < data.length; i++) {
            let prev = data[i - 1];
            let next = data[i + 1];
            let item = data[i];
            item.prev = prev?.ctgId;
            item.next = next?.ctgId;
          }
          this.ctgList = data;
        } else {
          this.toast(result.message, 'warning');
        }
      })
    },

    /*
     * 查询条目
     */
    doQueryEntry() {
      console.log("查询条目", this.ctgId);

      /*doAjaxGet(this.url('/content/entry/list'), null, result => {
        if (result.state) {
          this.ctgList = result.data || [];
        } else {
          this.toast(result.message, 'warning');
        }
      })*/
    },

    /*
     * 打开【分类】表单编辑画面
     */
    openCtgFormDialog(title, id) {
      this.ctgFormData.ctgId = id || null;
      this.dialogTitle = title;
      this.ctgWinDialog = true;

      // 查询记录详情
      if (id) {
        this.posting = true;
        doAjaxGet(this.url("/content/ctg/single/" + id), null, (result) => {
          if (result.state) {
            this.mergeValue(this.ctgFormData, result.data);
          } else {
            this.toast(result.message, 'warning');
          }
        });
      }
    },

    /*
     * 关闭【分类】表单编辑画面
     */
    closeCtgFormDialog() {
      this.ctgWinDialog = false;
      this.resetForm('ctgDataForm');
    },

    /*
     * 提交【分类】表单数据
     */
    doSubmitCtg() {
      this.posting = true;
      this.method = this.ctgFormData.ctgId? "update" : "save";
      doAjaxPost(this.url("/content/ctg/" + this.method), this.ctgFormData, (result) => {
        if (result.state) {
          this.toast("操作成功");
          this.closeCtgFormDialog();
          this.doQueryCategory();
        } else {
          this.toast(result.message, 'warning');
        }
      });
    },

    /*
     * 删除【分类】
     */
    doDeleteCtg(ctgId) {
      this.method = "del";
      doAjaxGet(this.url(`/content/ctg/del/${ctgId}`), null, (result) => {
        if (result.state) {
          this.toast("操作成功");
          this.doQueryCategory();
        } else {
          this.toast(result.message, 'warning');
        }
      });
    },

    /*
     * 上下移动分类
     */
    moveCategory(ctgId1, ctgId2) {
      doAjaxGet(this.url(`/content/ctg/swap/seq/${ctgId1}/${ctgId2}`), null, (result) => {
        if (result.state) {
          this.toast("操作成功");
          this.doQueryCategory();
        } else {
          this.toast(result.message, 'warning');
        }
      });
    }
  }
});
const appInstance = baseApp.uses(app).mount('#app');