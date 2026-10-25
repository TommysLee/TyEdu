// 初始化Vue
const app = Vue.createApp({
  extends: baseApp,
  data() {
    return {
      menuDisplayName: '学习知识库',
      vnode: null,
      ctgList: [],
      selectedCtgId: [],
      entryList: [],

      dialogTitle: '',
      ctgWinDialog: false,
      entryWinDialog: false,

      // 查询条件
      param: {
        ctgId: null
      },

      // 富文本编辑器对象
      editor: null,

      // 分类表单数据
      ctgFormData: {
        ctgId: null,
        ctgName: null,
        enabled: 1,
        remark: null
      },

      // 条目表单数据
      entryFormData: {
        eid: null,
        ctgId: null,
        title: null,
        content: null,
        remark: null
      },

      // 抽屉窗口
      winDrawer: false,
      winDrawerWidth: 800,
      winDrawerTitle: '',
      winDrawerContent: ''
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
    this.vnode = this.$refs.econtainer;
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
      this.loading = true;
      this.param.page = this.pagination.page;
      this.param.pageSize = this.pagination.pageSize;
      this.param.ctgId = this.ctgId;
      doAjaxPost(this.url('/content/entry/list'), this.param, result => {
        if (result.state) {
          let pageData = result.data;
          this.pagination.pageCount = pageData.pages; // 总页数
          this.entryList = addIndexPropForArray(pageData.data, this.pagination); // 数据集合
          this.scrollTop();
        } else {
          this.toast(result.message, 'warning');
        }
      })
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
     * 打开【条目】表单编辑画面
     */
    openEntryFormDialog(title, id) {
      this.entryFormData.eid = id || null;
      this.dialogTitle = title;
      this.entryWinDialog = true;
      this.initEditor();

      // 查询记录详情
      if (id) {
        this.posting = true;
        doAjaxGet(this.url("/content/entry/single/" + id), null, (result) => {
          if (result.state) {
            this.mergeValue(this.entryFormData, result.data);
            TinyEditor.setContent(this.editor, this.entryFormData.content);
          } else {
            this.toast(result.message, 'warning');
          }
        });
      } else {
        this.entryFormData.ctgId = this.ctgId;
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
     * 关闭【条目】表单编辑画面
     */
    closeEntryFormDialog() {
      this.entryWinDialog = false;
      this.resetForm('entryDataForm');
      this.editor = null;
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
     * 提交【条目】表单数据
     */
    doSubmitEntry() {
      this.posting = true;
      this.method = this.entryFormData.eid? "update" : "save";
      this.entryFormData.content = this.editor.getSemanticHTML();
      doAjaxPost(this.url("/content/entry/" + this.method), this.entryFormData, (result) => {
        if (result.state) {
          this.toast("操作成功");
          this.closeEntryFormDialog();
          this.doQueryEntry();
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
     * 删除【条目】
     */
    doDeleteEntry(eid) {
      this.method = "del";
      doAjaxGet(this.url("/content/entry/del/" + eid), null, (result) => {
        if (result.state) {
          this.toast("操作成功");
          this.doQueryEntry();
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
    },

    /*
     * 初始化编辑器
     */
    initEditor() {
      this.$nextTick(() => {
        this.editor = Vue.markRaw(TinyEditor.init('#editor'));
      })
    },

    /*
     * 打开抽屉窗口
     */
    openWinDrawer(item) {
      this.winDrawerWidth = window.innerWidth || this.winDrawerWidth;
      this.winDrawerTitle = item.title || '';
      this.winDrawerContent = item.content || '';
      this.winDrawer = true;
    }
  }
});
const appInstance = baseApp.uses(app).mount('#app');