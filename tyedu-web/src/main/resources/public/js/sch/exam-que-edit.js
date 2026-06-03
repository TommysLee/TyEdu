const prefix = "/sch/exam/que";
const app = Vue.createApp({
  extends: baseApp,
  data() {
    return {
      menuName: 'Exam',
      backUrl: `/sch/exam/que/${_examId}/view`,
      qid: _qid,

      // 富文本编辑器对象
      stemEditor: null,
      answerEditor: null,
      analysisEditor: null,

      // 表单数据
      formData: {
        qid: _qid,
        examId: _examId,
        stage: _stage,
        subject: _subject,
        type: null,
        difficulty: 3,
        stem: null,
        answer: null,
        analysis: null,
        maxScore: null,
        seq: null
      },

      // 数据字典
      qtypeList: []
    }
  },
  computed: {
    breadcrumbs() {
      let items = [_title];
      if (this.qid) {
        items.push(this.$t("修改题目"), this.$t('题号') + ":" + this.qid.toString());
      } else {
        items.push(this.$t("新增题目"));
      }
      return items;
    }
  },
  mounted() {
    this.doQueryQTypes();
    this.$nextTick(() => {
      this.initEditor();
      this.doQueryDetail();
    })
  },
  methods: {
    /*
     * 查询学科对应的题目类型
     */
    doQueryQTypes() {
      this.loadDict(`qtype/${_stage}/${_subject}`, result => {
        this.qtypeList = result.data || [];
      })
    },

    /*
     * 初始化编辑器
     */
    initEditor() {
      this.stemEditor = Vue.markRaw(TinyEditor.init('#stemEditor'));
      this.answerEditor = Vue.markRaw(TinyEditor.init('#answerEditor'));
      this.analysisEditor = Vue.markRaw(TinyEditor.init('#analysisEditor'));
    },

    /*
     * 查询记录详情
     */
    doQueryDetail() {
      if (this.formData.qid) {
        this.posting = true;
        doAjaxGet(this.url(`${prefix}/${_examId}/single/${this.qid}`), null, result => {
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
      doAjaxPost(this.url(`${prefix}/${_examId}/${this.method}`), this.formData, (result) => {
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
        TinyEditor.setContent(this.stemEditor, this.formData.stem);
      }
      if (this.formData.answer) {
        TinyEditor.setContent(this.answerEditor, this.formData.answer);
      }
      if (this.formData.analysis) {
        TinyEditor.setContent(this.analysisEditor, this.formData.analysis);
      }
    }
  }
});
const appInstance = baseApp.uses(app).mount('#app');