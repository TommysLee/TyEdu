const prefix = "/rs/que";
const app = Vue.createApp({
  extends: baseApp,
  data() {
    return {
      backUrl: '/rs/que-bank',

      // 参数
      stage: _stage,
      subject: _subject,
      qid: _qid,

      // 富文本编辑器对象
      stemEditor: null,
      answerEditor: null,
      analysisEditor: null,

      // 表单数据
      formData: {
        qid: _qid,
        subject: _subject,
        type: null,
        difficulty: 3,
        stem: null,
        answer: null,
        analysis: null
      },

      // 数据字典
      dictConfig: {
        "stage": "stageList"
      },
      qtypeList: [],
      subjectList: []
    }
  },
  computed: {
    stageMap() {
      return toMap(this.stageList);
    },
    subjectMap() {
      return toMap(this.subjectList);
    },
    breadcrumbs() {
      let items = [];
      items.push(this.stageMap[this.stage] + this.subjectMap[this.subject]);
      if (this.qid) {
        items.push(this.$t("修改题目"), this.$t('题号') + ":" + this.qid.toString());
      } else {
        items.push(this.$t("新增题目"));
      }
      return items;
    }
  },
  mounted() {
    this.doQuerySubject();
    this.doQueryQTypes();
    this.$nextTick(() => {
      this.initEditor();
      this.doQueryDetail();
    })
  },
  methods: {
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
     * 查询学科对应的题目类型
     */
    doQueryQTypes() {
      if (this.subject) {
        this.loadDict(`qtype/${this.stage}/${this.subject}`, result => {
          this.qtypeList = result.data || [];
        })
      }
    },

    /*
     * 初始化编辑器
     */
    initEditor() {
      this.stemEditor = TinyEditor.init('#stemEditor');
      this.answerEditor = TinyEditor.init('#answerEditor');
      this.analysisEditor = TinyEditor.init('#analysisEditor');
    },

    /*
     * 查询记录详情
     */
    doQueryDetail() {
      if (this.formData.qid) {
        this.posting = true;
        doAjaxGet(this.url(`${prefix}/single/${this.qid}`), null, result => {
          if (result.state) {
            this.mergeValue(this.formData, result.data);
            this.setEditorsContent();
          } else {
            this.toast(result.message, 'warning');
          }
        })
      }
    },

    /*
     * 关闭表单编辑画面
     */
    closeFormDialog() {
      setTimeout(() => {
        this.back();
      }, 800)
    },

    /*
     * 提交表单数据
     */
    doSubmit() {
      this.formData.stem = this.stemEditor.getSemanticHTML();
      this.formData.answer = this.answerEditor.getSemanticHTML();
      this.formData.analysis = this.analysisEditor.getSemanticHTML()

      this.posting = true;
      this.method = this.formData.qid? "update" : "save";
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
     * 设置编辑器内容
     */
    setEditorsContent() {
      if (this.formData.stem) {
        this.stemEditor.root.innerHTML = TinyEditor.resolveHtml(this.formData.stem);
      }
      if (this.formData.answer) {
        this.answerEditor.root.innerHTML = TinyEditor.resolveHtml(this.formData.answer);
      }
      if (this.formData.analysis) {
        this.analysisEditor.root.innerHTML = TinyEditor.resolveHtml(this.formData.analysis);
      }
    }
  }
});
const appInstance = baseApp.uses(app).mount('#app');