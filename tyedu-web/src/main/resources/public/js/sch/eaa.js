/**
 * 考试分析助手
 */
function ExamAnalysisAssistant(data, typesMap) {

  // 数据
  this.data = data || [];

  // 类型词典数据
  this.typesMap = typesMap || {};

  // 题型分析数据
  this.types = null;

  // 知识点分析数据
  this.ktags = null;

  /**
   * 获取题型分析数据
   */
  this.getTypes = () => {
    if (!this.types) {
      this.types = handleStatisticsTypes(this.data, this.typesMap);
    }
    return this.types;
  };

  /**
   * 获取知识点分析数据
   */
  this.getKtags = () => {
    if (!this.ktags) {
      this.ktags = handleStatisticsKtags(this.data);
    }
    return this.ktags;
  }

  /**
   * 基于数据，进行题型分析
   */
  function handleStatisticsTypes(data, typesMap) {
    const grouped = data.reduce((acc, item) => {
      const key = item.type;
      if (key) {
        let targetItem = acc[key] || {type: key, title: typesMap[key], maxScore: 0, score: 0, count: 0};
        targetItem.maxScore += (item.maxScore ?? 0);
        targetItem.score += (item.score ?? 0);
        targetItem.count++;
        acc[key] = targetItem;
      }
      return acc;
    }, {})
    return Object.values(grouped).map(item => (item.rate = Math.round(item.score / item.maxScore * 100), item));
  }

  /**
   * 基于数据，进行知识点分析
   */
  function handleStatisticsKtags(data) {
    const grouped = data.reduce((acc, item) => {
      const ktags = item.ktags;
      if (ktags && ktags?.length > 0) {
        for (let tag of ktags) {
          let key = tag.kid;
          let targetItem = acc[key] || {kid: key, kname: tag.kname, maxScore: 0, score: 0, maxRate: -100, rate: 0, count: 0, qidList: [], qindexList: []};
          targetItem.maxScore += (item.maxScore ?? 0);
          targetItem.score += (item.score ?? 0);
          targetItem.qidList.push(item.qid);
          targetItem.qindexList.push(item.index);
          targetItem.count++;
          acc[key] = targetItem;
        }
      }
      return acc;
    }, {});
    return Object.values(grouped)
      .map(item => (item.rate = Math.round(item.score / item.maxScore * 100), item))
      .sort((a, b) => a.score - b.score);
  }
}