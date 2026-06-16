function ApexBuilder() {}
ApexBuilder.config = {}
ApexBuilder.config.base = () => {
  return {
    noData: {
      text: '暂无数据'
    }
  }
}

/**
 * 条形图（Bar Chart）构建器
 */
ApexBuilder.Bar = {
  options(id, opts) {
    return _.merge({
      chart: {
        id
      },
      plotOptions: {
        bar: {
          horizontal: true,
          dataLabels: {
            position: 'top'
          },
          borderRadius: 5,
          borderRadiusApplication: 'end',
        }
      },
      dataLabels: {
        offsetX: -20
      }
    }, ApexBuilder.config.base(), opts)
  }
};

/**
 * 柱状图（Column Chart）构建器
 */
ApexBuilder.Column = {
  options(id, opts) {
    return _.merge({
      chart: {
        id
      }
    }, ApexBuilder.config.base(), opts)
  }
};

/**
 * 雷达图（Radar Chart）构建器
 */
ApexBuilder.Radar = {
  options(id, opts) {
    return _.merge({
      chart: {
        id
      },
      yaxis: {
        show: false
      },
      tooltip: {
        x: {
          formatter: (val) => val
        }
      }
    }, ApexBuilder.config.base, opts)
  }
};