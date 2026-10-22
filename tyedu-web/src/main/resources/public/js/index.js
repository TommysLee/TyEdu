const weekday = ["星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"];

// 初始化Vue
const app = Vue.createApp({
  extends: baseApp,
  data() {
    return {
      time: null
    }
  },
  mounted() {
    setInterval(this.updateTime, 1000);
    this.$nextTick(() => {
      this.updateTime();
    })
  },
  methods: {
    updateTime() {
      const d = new Date();
      this.time = this.$t(weekday[d.getDay()]) + " " + d.format("MM月dd日 hh:mm:ss");
    }
  }
});
const appInstance = baseApp.uses(app).mount('#app');