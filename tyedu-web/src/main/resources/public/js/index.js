// 初始化Vue
const app = Vue.createApp({
  extends: baseApp,
  data() {
    return {
    }
  },
  mounted() {
  },
  methods: {
    zoom(flag) {
      console.log(flag? "放大":"还原");
    },
    refresh() {
      console.log("刷新面板...");
    }
  }
});
const appInstance = baseApp.uses(app).mount('#app');