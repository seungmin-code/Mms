
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
    const params = {page: page, size: 10};

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
        console.log(response);
        // 셀렉트박스 옵션 추가
        addOptions(response.data, "#stationCategory", "전체");
    }
    // AJAX 호출
    ajaxCall("/station/api/category", "GET", "", success, "");
}

/**
 * 테이블 생성 함수
 * @param data 데이터 리스트
 */
function createTable(data) {
    const tableBody = $("#stationTableBody");
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

/**
 * 페이징 처리 함수
 * @param pagination
 */
function createPagination(pagination) {
    const paginationContainer = $("#paginationContainer");
    paginationContainer.empty();

    let paginationHtml = '';

    const totalPages = pagination.pages;
    const pageSize = pagination.pageSize;
    const currentPage = pagination.pageNum;

    const startPage = Math.floor((currentPage - 1) / pageSize) * pageSize + 1;
    const endPage = Math.min(startPage + pageSize - 1, totalPages);


    if (currentPage > 1) {
        paginationHtml += `<button class="page-btn btn btn-secondary btn-sm" onclick="search(1)" style="margin-right: 5px;"><<</button>`;
        paginationHtml += `<button class="page-btn btn btn-secondary btn-sm" onclick="search(${currentPage - 1})" style="margin-right: 5px;"><</button>`;
    }
    for (let i = startPage; i <= endPage; i++) {
        if (i === currentPage) {
            paginationHtml += `<button class="page-btn btn btn-primary btn-sm" onclick="search(${i})" style="margin-right: 5px;">${i}</button>`;
        } else {
            paginationHtml += `<button class="page-btn btn btn-secondary btn-sm" onclick="search(${i})" style="margin-right: 5px;">${i}</button>`;
        }
    }
    if (currentPage < totalPages) {
        paginationHtml += `<button class="page-btn btn btn-secondary btn-sm" onclick="search(${currentPage + 1})" style="margin-right: 5px;">></button>`;
        paginationHtml += `<button class="page-btn btn btn-secondary btn-sm" onclick="search(${totalPages})">>></button>`;
    }

    paginationContainer.html(paginationHtml);
}