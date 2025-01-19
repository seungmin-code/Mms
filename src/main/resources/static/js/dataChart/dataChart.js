
$(function() {
    createChart();
})

function createChart() {
    const chartDom = document.getElementById("dataChart");
    const myChart = echarts.init(chartDom);

    const option = {
        title: {
            text: "차트 제목",
            subtext: '차트 보조 제목',
            left: 'center',
        },
        toolbox: {
            show: true,
            orient: 'horizontal',
            feature: {
                dataZoom: {
                    yAxisIndex: "none"
                },
                dataView: {
                    readOnly: false
                },
                magicType: {
                    type: ["line", "bar"]
                },
                restore: {},
                saveAsImage: {}
            },
            right: '10%',
            top: '5%'
        },
        tooltip: {
            trigger: 'axis',
            axisPointer: {
                type: 'cross',
                label: {
                    backgroundColor: '#6a7985'
                }
            }
        },
        dataZoom: [
            {
                type:'slider',
                xAxisIndex: [0],
            },
            {
                type:'slider',
                yAxisIndex: [0]
            }
        ],
        legend: {
            show: true,
            left: '10%',
            top: '5%',
            data: ["범례1", "범례2", "범례3"]
        },
        xAxis: {
            type: 'category',
            data: ['월', '화', '수']
        },
        yAxis: {
            type: 'value'
        },
        series: [
            {
                name: '범례1',
                data: [45, 25, 15],
                type: 'line',
                smooth: true
            },
            {
                name: '범례2',
                data: [15, 25, 35],
                type: 'line',
                smooth: true
            },
            {
                name: '범례3',
                data: [5, 10, 20],
                type: 'line',
                smooth: true
            }
        ]
    };

    myChart.setOption(option);
}