const prefix = "/rs/knowledge";
const ROOT_NODE_ID = 0;
const app = Vue.createApp({
  extends: baseApp,
  data() {
    return {
      knowledgeList: [],
      selectedKnowledge: [],
      // 表单数据
      formData: {
        kid: null,
        parentId: null,
        kname: null,
        importance: 3
      },
      // 模态窗口
      winDialog: false,
      dialogTitle: '',
      // 数据字典
      dictConfig: {
        "stage": "stageList"
      },
      subjectList: [],
      subject: null
    }
  },
  computed: {
    knowledgeTreeData() {
      return this.wrapTreeData(this.knowledgeList, 'kid');
    },
    knowledgeMap() {
      let map = {};
      map[ROOT_NODE_ID] = "根节点";
      return toMap(this.knowledgeList, "kid", "kname", map);
    },
    parentId() {
      return this.selectedKnowledge?.length > 0? this.selectedKnowledge[0] : ROOT_NODE_ID;
    }
  },
  watch: {
    stage() {
      this.knowledgeList = [];
      this.selectedKnowledge = [];
      this.subject = null;
      this.doQuerySubject();
    },
    subject(val) {
      if (val) {
        this.doQuery();
      }
    }
  },
  mounted() {
    // 页面渲染完成后，计算辅助元素的总高度
    this.$nextTick(() => {
      this.assistHeight = calcAssistHeight();
    });

    // 加载学科列表
    this.doQuerySubject();
  },
  methods: {
    /*
     * 执行条件查询
     */
    doQuery() {
      this.loading = true;
      this.selectedKnowledge = [];
      doAjaxGet(this.url(`${prefix}/list/${this.stage}/${this.subject}`), null, result => {
        if (result.state) {
          this.knowledgeList = result.data || [];
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
        this.loadDict("subject/" + this.stage, result => {
          this.subjectList = result.data || [];
          if (this.subjectList?.length > 0) {
            this.subject = this.subjectList[0].value;
          }
        })
      }
    },

    /*
     * 打开表单编辑画面
     */
    openFormDialog(title, id) {
      this.formData.kid = id || null;
      this.formData.parentId = this.parentId;
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
      this.formData.importance = 3;
      this.resetForm();
    },

    /*
     * 提交表单数据
     */
    doSubmit() {
      this.posting = true;
      this.method = this.formData.kid? "update" : "save";
      doAjaxPost(this.url(`${prefix}/${this.method}/${this.stage}/${this.subject}`), this.formData, (result) => {
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
    doDelete(kid) {
      this.method = "del";
      doAjaxGet(this.url(`${prefix}/del/${kid}`), null, (result) => {
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