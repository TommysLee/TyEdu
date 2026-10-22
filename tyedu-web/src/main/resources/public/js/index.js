const weekday = ["星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"];

// 初始化Vue
const app = Vue.createApp({
  extends: baseApp,
  data() {
    return {
      time: null
    }
  },
  created() {
    this.updateTime();
  },
  mounted() {
    setInterval(this.updateTime, 1000)
  },
  methods: {
    updateTime() {
      const d = new Date();
      this.time = weekday[d.getDay()] + " " + d.format("MM月dd日 hh:mm:ss");
    }
  }
});
const appInstance = baseApp.uses(app).mount('#app');