let map;
let marker;

$(function() {
    initMap();
    tableClickListening();

    searchCategory();
    searchSido();
    searchData(1);
});

/**
 * 지도 초기화 함수
 */
function initMap() {
    const container = document.getElementById("map");
    const options = {
        center: new kakao.maps.LatLng(33.450701, 126.570667),
        level: 3,
    };
    map = new window.kakao.maps.Map(container, options);
}

/**
 * 테이블 클릭 이벤트 리스너 함수
 */
function tableClickListening() {
    $(document).on("click", "#tableBody tr", function () {
        // X좌표 Y좌표 추출
        const dmX = $(this).find("td:nth-child(6)").text().trim();
        const dmY = $(this).find("td:nth-child(7)").text().trim();

        // 기존 선택된 행 음영 표시 삭제
        $("#tableBody tr").removeClass("table-active");
        // 클릭한행에 선택 음영 표시 추가
        $(this).addClass("table-active");

        if (dmX && dmY) {
            // 마커생성 함수로 X좌표 Y좌표 전달
            placeMarker(dmX, dmY);
        } else {
            alert("좌표 정보가 없습니다.");
        }
    });
}

/**
 * 지도에 마커를 배치하는 함수
 * @param {string} dmX X 좌표
 * @param {string} dmY Y 좌표
 */
function placeMarker(dmX, dmY) {
    const markerPosition = new kakao.maps.LatLng(dmX, dmY);

    if (marker) {
        marker.setMap(null);
    }

    marker = new kakao.maps.Marker({
        position: markerPosition
    });

    marker.setMap(map);
    map.setCenter(markerPosition);
}

/**
 * 데이터 검색 함수
 * @param {number} page 호출할 페이지 번호
 */
function searchData(page) {
    const params = {
        page: page,
        size: 10,
        category: $("#category").val(),
        sido: $("#sido").val()
    };

    const success = function(response) {
        updateTableAndMap(response.data);
        createPagination(response.pagination);
    }

    ajaxCall("/station/api/data", "GET", params, success, "");
}

/**
 * 카테고리 셀렉트박스 검색 함수
 */
function searchCategory() {
    const success = function(response) {
        addOptions(response.data, "#category", "전체");
    }

    ajaxCall("/station/api/category", "GET", "", success, "");
}

/**
 * 엑셀 다운로드 함수
 */
function excelDownload() {
    const category = $("#category").val();
    const sido = $("#sido").val();
    window.location.href = `/station/api/excelDownload?size=10&category=${category}&sido=${sido}`;
}

/**
 * 시도 셀렉트박스 검색 함수
 */
function searchSido() {
    const success = function(response) {
        addOptions(response.data, "#sido", "전체");
    }
    ajaxCall("/station/api/sidoCategory", "GET", "", success, "");
}

/**
 * 테이블 생성 및 지도 업데이트 함수
 * @param {Array} data 데이터 리스트
 */
function updateTableAndMap(data) {
    const mapSection = $("#mapStationContainer");
    const tableBody = $("#tableBody");
    tableBody.empty();

    if (data.length > 0) {
        mapSection.show();

        data.forEach((station, index) => {
            const row = `
            <tr>
                <td class="align-center">${station.station_name}</td>
                <td class="align-left">${station.addr}</td>
                <td class="align-center">${station.item}</td>
                <td class="align-center">${station.mang_name}</td>
                <td class="align-center">${station.year}</td>
                <td class="align-center" style="display: none;">${station.dm_x}</td>
                <td class="align-center" style="display: none;">${station.dm_y}</td>
            </tr>
            `;
            tableBody.append(row);

            if (index === 0) {
                // 첫번째행에 선택 음영 표시 추가
                $("#tableBody tr:first").addClass("table-active");

                // 첫번째행의 X좌표 Y좌표 기준으로 지도 및 마커 설정
                const dmX = parseFloat(station.dm_x);
                const dmY = parseFloat(station.dm_y);
                if (!isNaN(dmX) && !isNaN(dmY)) {
                    placeMarker(dmX, dmY);
                }
            }
        });
    } else {
        const row = `<tr><td colspan="5" class="align-center">데이터가 없습니다.</td></tr>`;
        tableBody.append(row);
        mapSection.hide();
    }
}