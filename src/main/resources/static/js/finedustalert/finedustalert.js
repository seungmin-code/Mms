$(function() {
    addYearSelectBox();
    searchData();
})

function searchData() {
    const params = $("#searchForm").serialize();
    const success = function(response) {
        createTable(response.data);
        createChart(response.data);
    }
    ajaxCall("/finedustalert/fetchApiCallData", "GET", params, success, "", "");
}

function getPreviousYears() {
    const currentYear = new Date().getFullYear();
    return Array.from({length: 6}, (_, i) => currentYear - i);
}

function addYearSelectBox() {
    addOptions(getPreviousYears(), "#year", "");
}

function createTable(data) {
    const tableContainer = $("#tableBody");
    tableContainer.empty();

    let tableHTML = "";
    if (data.length > 0) {
        data.forEach(item => {
            tableHTML += `<tr>
                    <td class="align-center">${item.sn}</td>
                    <td class="align-center">${item.districtName}</td>
                    <td class="align-center">${item.moveName}</td>
                    <td class="align-center">${item.itemCode}</td>
                    <td class="align-center">${item.issueGbn}</td>
                    <td class="align-center">${item.dataDate} ${item.issueTime}</td>
                    <td class="align-center">${item.clearDate} ${item.clearTime}</td>
                  </tr>`;
        });

        tableHTML += '</tbody></table>';
    } else {
        const tableContainer = $("#tableBody");
        tableContainer.empty();
        tableHTML += `<tr><td class="align-center" colspan="7">데이터가 없습니다.</td></tr>`;
    }
    tableContainer.html(tableHTML);
}

function createChart(data) {
    const chartDom = document.getElementById("chartContainer");
    const myChart = echarts.init(chartDom);

    if (data.length > 0) {
        // 월별 발령 횟수를 집계합니다.
        const monthlyCounts = new Array(12).fill(0);
        data.forEach(item => {
            const month = new Date(item.issueDate).getMonth();
            monthlyCounts[month]++;
        });

        // 차트 옵션을 설정합니다.
        const option = {
            title: {
                text: '월별 미세먼지 경보 발생 횟수',
                left: 'center'
            },
            tooltip: {
                trigger: 'axis',
                axisPointer: {
                    type: 'cross',
                    crossStyle: {
                        color: '#999'
                    }
                }
            },
            legend: {
                data: ["발생 횟수"],
                bottom: '1%'
            },
            xAxis: {
                type: 'category',
                boundaryGap: false,
                data: ['1월', '2월', '3월', '4월', '5월', '6월', '7월', '8월', '9월', '10월', '11월', '12월']
            },
            yAxis: {
                type: 'value'
            },
            series: [
                {
                    name: '발생 횟수',
                    type: 'bar',
                    data: monthlyCounts,
                    smooth: true
                }
            ]
        };

        // 차트를 그립니다.
        myChart.setOption(option);

        // 차트 컨테이너를 표시합니다.
        chartDom.style.display = 'block';
    } else {
        // 차트 컨테이너를 숨깁니다.
        chartDom.style.display = 'none';
    }
}


