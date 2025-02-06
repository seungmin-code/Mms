
$(function() {
    selectBoxControl();
    searchData();
})


function selectBoxControl() {
    $("#dataGubun").on("change", function() {
        const dataGubunValue = $("#dataGubun").val();

        if (dataGubunValue === "HOUR") {
            $("#searchCondition").prop("disabled", true);
            $("#searchCondition").hide();
        } else {
            $("#searchCondition").prop("disabled", false);
            $("#searchCondition").show();
        }
    })
}

function searchData() {
    const params = $("#searchForm").serialize();

    const success = function(response) {
        console.log(response);
        createTable(response.items);
        createChart(response.items);
    };

    console.log(params);
    ajaxCall("/realTimeSido/callRealTimeSidoApi?", "GET", params, success, "");
}

function createTable(data) {
    const tableBody = $("#tableBody");
    tableBody.empty();

    if (data.length > 0) {
        data.forEach((item, index) => {
            const itemCodeText = item.itemCode === "PM2.5" ? "초미세먼지" :
                item.itemCode === "PM10" ? "미세먼지" : item.itemCode;

            const row = `
                <tr>
                    <td class="align-center">${item.dataTime}</td>
                    <td class="align-center">${itemCodeText}</td>
                    <td class="align-center">${item.seoul}</td>
                    <td class="align-center">${item.daejeon}</td>
                    <td class="align-center">${item.daegu}</td>
                    <td class="align-center">${item.busan}</td>
                    <td class="align-center">${item.chungbuk}</td>
                    <td class="align-center">${item.chungnam}</td>
                    <td class="align-center">${item.gangwon}</td>
                    <td class="align-center">${item.gwangju}</td>
                    <td class="align-center">${item.gyeongbuk}</td>
                    <td class="align-center">${item.gyeonggi}</td>
                    <td class="align-center">${item.gyeongnam}</td>
                    <td class="align-center">${item.incheon}</td>
                    <td class="align-center">${item.jeju}</td>
                    <td class="align-center">${item.jeonbuk}</td>
                    <td class="align-center">${item.jeonnam}</td>
                    <td class="align-center">${item.sejong}</td>
                    <td class="align-center">${item.ulsan}</td>
                </tr>
            `;
            tableBody.append(row);
        });
    } else {
        const row = `<tr><td colspan="17" class="align-center">데이터가 없습니다.</td></tr>`;
        tableBody.append(row);
    }
}

function createChart(data) {
    const chartDom = document.getElementById("chartContainer");
    const myChart = echarts.init(chartDom);

    const cityMapping = {
        seoul: "서울", daejeon: "대전", daegu: "대구", busan: "부산",
        chungbuk: "충북", chungnam: "충남", gangwon: "강원", gwangju: "광주",
        gyeongbuk: "경북", gyeonggi: "경기", gyeongnam: "경남", incheon: "인천",
        jeju: "제주", jeonbuk: "전북", jeonnam: "전남", sejong: "세종", ulsan: "울산"
    };

    const cityKeys = Object.keys(cityMapping); // ["seoul", "daejeon", ...]

    const pm10Data = data.filter(item => item.itemCode === "PM10");
    const pm25Data = data.filter(item => item.itemCode === "PM2.5");

    const selectedData = pm10Data.length > 0 ? pm10Data : pm25Data; // PM10이 없으면 PM2.5 사용

    const seriesData = cityKeys.map(city => ({
        name: cityMapping[city],
        type: "line",
        data: selectedData.map(item => item[city] !== null ? parseInt(item[city]) : 0)
    }));

    const option = {
        title: {
            text: "시도별 대기오염 데이터",
            left: "center"
        },
        tooltip: {
            trigger: "axis"
        },
        legend: {
            data: Object.values(cityMapping),
            bottom: 0
        },
        xAxis: {
            type: "category",
            data: selectedData.map(item => item.dataTime)
        },
        yAxis: {
            type: "value",
            name: "농도 (µg/m³)"
        },
        series: seriesData
    };

    myChart.setOption(option);
}

