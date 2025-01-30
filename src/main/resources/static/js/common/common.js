
    // 테이블 초기화 및 생성
    function createTable(columns, columnsName, data) {
        const tableContainer = $("#tableContainer");
        tableContainer.empty();

        let tableHtml = `<table class="table table-hover"><thead><tr>`;

        // columnsName을 사용하여 헤더 출력
        columnsName.forEach(function (colName) {
            tableHtml += `<th class='text-center' style='background-color: #F6F4F4FF'>${colName}</th>`;
        });

        tableHtml += `</tr></thead><tbody>`;

        if (data.length > 0) {
            data.forEach(function (row) {
                tableHtml += `<tr>`;
                columns.forEach(function (col, index) {
                    let className = (index === 1) ? "" : "class='text-center'";
                    tableHtml += `<td ${className}>${row[col]}</td>`;
                });
                tableHtml += `</tr>`;
            });
        } else {
            tableHtml += `<tr><td colspan="${columns.length}" class="text-center">데이터가 없습니다</td></tr>`;
        }

        tableHtml += `</tbody></table>`;

        tableContainer.html(tableHtml);
    }


    // 페이징 처리
    function createPagination(pagination) {
        const paginationContainer  = $("#pagination");
        paginationContainer.empty();

        let paginationHtml = '';

        const currentPage = pagination.pageNum;
        const totalPages = pagination.pages;
        const pageSize = pagination.pageSize;

        const startPage = Math.floor((currentPage - 1) / pageSize) * pageSize + 1;
        const endPage = Math.min(startPage + pageSize - 1, totalPages);

        if (currentPage > 1) {
            paginationHtml += `<button class="page-btn btn btn-secondary btn-sm" onclick="search(1)" style="margin-right: 5px;"><<</button>`;
            paginationHtml += `<button class="page-btn btn btn-secondary btn-sm" onclick="search(${currentPage - 1})" style="margin-right: 5px;"><</button>`;
        }

        for (let i = startPage; i <= endPage; i++) {
            let activeClass = (i === currentPage) ? 'active' : '';

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
