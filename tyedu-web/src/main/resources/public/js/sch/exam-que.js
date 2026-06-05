const prefix = "/sch/exam/que";
const app = Vue.createApp({
  extends: baseApp,
  data() {
    return {
      menuName: 'Exam',
      vnode: null,
      backUrl: '/sch/exam/view',
      title: _title,
      published: _published,
      reviewed: _reviewed,

      // 数据
      dataList: [],

      // 数据字典
      qtypeList: []
    }
  },
  computed: {
    qtypeMap() {
      return toMap(this.qtypeList)
    }
  },
  mounted() {
    this.doQuery();
    this.doQueryQTypes();
    this.vnode = this.$refs.container;
  },
  methods: {
    /*
     * 执行条件查询
     */
    doQuery() {
      this.loading = true;
      doAjaxPost(this.url(`${prefix}/${_examId}/list`), null, result => {
        if (result.state) {
          this.dataList = addIndexPropForArray(result.data);
          this.scrollTop();
        } else {
          this.toast(result.message, 'warning');
        }
      })
    },

    /*
     * 查询学科对应的题目类型
     */
    doQueryQTypes() {
      this.loadDict(`qtype/${_stage}/${_subject}`, result => {
        this.qtypeList = result.data || [];
      })
    },

    /*
     * 删除数据
     */
    doDelete(qid) {
      this.method = "del";
      doAjaxGet(this.url(`${prefix}/${_examId}/del/${qid}`), null, (result) => {
        if (result.state) {
          this.toast("操作成功");
          this.doQuery();
        } else {
          this.toast(result.message, 'warning');
        }
      });
    },

    /*
     * 前往编辑页面（新增/修改）
     */
    goEdit(qid) {
      let url = `${prefix}/${_examId}/edit/`;
      if (!(qid instanceof Event) && qid) {
        url += (qid + '/');
      }
      url += 'view';
      window.location.href = this.url(url)
    },

    /*
     * 前往打标页面
     */
    goMarked(qid) {
      window.location.href = this.url(`${prefix}/${_examId}/marked/${qid}/view`);
    }
  }
});
const appInstance = baseApp.uses(app).mount('#app');