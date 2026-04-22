// 初始化Vue
const app = Vue.createApp({
  extends: baseApp,
  data() {
    return {
      menuName: 'HeloK'
    }
  },
  mounted() {
  },
  methods: {
  }
});
const appInstance = baseApp.uses(app).mount('#app');