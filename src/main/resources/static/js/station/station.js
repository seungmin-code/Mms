
$(function() {
    searchCategory();
    searchData(1);
})

/**
 * 데이터 검색 함수
 * AJAX
 * @param page 호출 할 페이지
 */
function searchData(page) {
    // 페이지 사이즈는 기본 10으로 설정
    const params = {
        page: page,
        size: 10,
        category: $("#category").val()
    };

    // 성공 함수
    const success = function(response) {
        // 테이블 생성
        createTable(response.data);
        // 페이징 생성
        createPagination(response.pagination);
    }
    // AJAX 호출
    ajaxCall("/station/api/data", "GET", params, success, "");
}

/**
 * 카테고리 검색 함수
 * AJAX
 */
function searchCategory() {
    // 성공 함수
    const success = function(response) {
        // 셀렉트박스 옵션 추가
        addOptions(response.data, "#category", "전체");
    }
    // AJAX 호출
    ajaxCall("/station/api/category", "GET", "", success, "");
}

/**
 * 테이블 생성 함수
 * @param data 데이터 리스트
 */
function createTable(data) {
    const tableBody = $("#tableBody");
    tableBody.empty();

    if (data.length > 0) { // 데이터가 존재하면
        data.forEach((station) => {
            const row = `
            <tr>
                <td class="align-center">${station.station_name}</td>
                <td class="align-left">${station.addr}</td>
                <td class="align-center">${station.item}</td>
                <td class="align-center">${station.mang_name}</td>
                <td class="align-center">${station.year}</td>
            </tr>
            `;
            tableBody.append(row);
        })
    } else { // 데이터가 존재하지 않으면
        const row = `
            <tr>
                <td colspan="5" class="align-center">데이터가 없습니다.</td>
            </tr>
            `;
        tableBody.append(row);
    }
}