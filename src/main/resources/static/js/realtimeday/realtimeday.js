$(function() {
    selectBoxHandling();
    flatPickerSetting("day");

    searchSidoSelectBox();
    searchStationSelectBox();
    searchData();

    handleTabResize();
})

function handleTabResize() {
    $('a[data-bs-toggle="tab"]').on('shown.bs.tab', function (event) {
        // 현재 활성화된 탭의 ID 가져오기
        const targetTab = $(event.target).attr("href"); // 예: "#tab1"

        // 해당 탭 안에 있는 차트 컨테이너 찾기
        const chartContainer = $(targetTab).find(".chartContainer");

        // 각 차트에 대해 resize() 호출
        chartContainer.each(function () {
            const chart = echarts.getInstanceByDom(this);
            if (chart) {
                chart.resize();
            }
        });
    });
}

function selectBoxHandling() {
    $("#sido").on("change", function() {
        searchStationSelectBox();
    });
}

function searchSidoSelectBox() {
    const success = function(response) {
        addOptions(response.data, "#sido", false);
    }

    ajaxCall("/common/fetchSidoData", "GET", "", success, "");
}

function searchStationSelectBox() {
    const params = {sido: $("#sido").val()}

    const success = function(response) {
        addOptions(response.data, "#station", false);
    }

    ajaxCall("/common/fetchStationData", "GET", params, success, "");
}

function searchData() {
    const params = {
        inqBginMm: $("#startDate").val().replace(/-/g, ""),
        inqEndMm: $("#endDate").val().replace(/-/g, ""),
        msrstnName: $("#station").val()
    };

    const success = function(response) {
        createChart(response.data);
    }

    ajaxCall("/realTimeDay/fetchApiCallData", "GET", params, success, "");
}

function createChart(data) {
    console.log(data);
    const stationName = data[0].msrstnName;
    const categories = data.map(item => item.msurDt);
    const chartDivIds = ['pm10', 'pm25', 'so2', 'co', 'no2', 'o3'];
    const tableDivIds = ['pm10-table-container', 'pm25-table-container', 'so2-table-container', 'co-table-container', 'no2-table-container', 'o3-table-container'];

    const chartData = {
        pm10: data.map(item => item.pm10Value),
        pm25: data.map(item => item.pm25Value),
        so2 : data.map(item => item.so2Value),
        co  : data.map(item => item.coValue),
        no2 : data.map(item => item.no2Value),
        o3  : data.map(item => item.o3Value)
    };

    const chartTitle = {
        pm10: '미세먼지 (µg/m²)',
        pm25: '초미세먼지 (µg/m²)',
        so2 : '아황산가스 (ppm)',
        co  : '일산화탄소 (ppm)',
        no2 : '이산화질소 (ppm)',
        o3  : '오존 (ppm)'
    };

    chartDivIds.forEach((chartId, index) => {
        const chartElement = echarts.init(document.getElementById(chartId));
        chartElement.setOption(generateChartOption(stationName, categories, chartData[chartId], chartTitle[chartId]));

        // 테이블 생성
        createTable(data);
    });
}

function generateChartOption(stationName, categories, data, title) {
    return {
        title: {
            text: stationName + " 측정소 " + title + " 실시간 일 평균",
            left: 'center'
        },
        tooltip: {
            trigger: 'axis',
            axisPointer: {
                type: 'cross',
                crossStyle: {
                    color: '#999'
                }
            },
        },
        legend: {
            data: [title],
            bottom: '1%'
        },
        xAxis: {
            type: 'category',
            boundaryGap: false,
            data: categories
        },
        yAxis: {
            type: 'value'
        },
        series: [{
            name: title,
            type: 'line',
            data: data,
            smooth: true
        }]
    };
}

function createTable(data) {
    const tableContainer = $("#tableBody");
    tableContainer.empty();

    let tableHTML = "";
    data.forEach(item => {
        tableHTML += `<tr>
                        <td class="align-center">${item.msrstnName}</td>
                        <td class="align-center">${item.msurDt}</td>
                        <td class="align-center">${item.pm10Value}</td>
                        <td class="align-center">${item.pm25Value}</td>
                        <td class="align-center">${item.so2Value}</td>
                        <td class="align-center">${item.coValue}</td>
                        <td class="align-center">${item.no2Value}</td>
                        <td class="align-center">${item.o3Value}</td>
                    </tr>`;
    });

    tableHTML += '</tbody></table>';

    // 테이블 컨테이너에 테이블 HTML 삽입
    tableContainer.html(tableHTML);
}

