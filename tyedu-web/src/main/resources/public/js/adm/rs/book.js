// 初始化Vue
const app = Vue.createApp({
  extends: baseApp,
  data() {
    return {
    }
  },
  mounted() {
    doAjaxGetSimple(this.url('/dict/stage'), null, result => {
      if (result.state) {
        this.stageList = result.data || [];
      }
    })
  },
  methods: {
  }
});
const appInstance = baseApp.uses(app).mount('#app');