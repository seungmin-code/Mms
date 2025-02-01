
    // 온로드 함수
    $(function() {
        search(1);
    })

    // 이벤트 함수
    $(function() {
        // 검색버튼 엔터클릭 이벤트방지
        $('#searchForm').submit(function(event) {
            event.preventDefault();
            search(1);
        });
    })

    // 공지사항 목록 조회
    function search(page) {
        $.ajax({
            url: "/data/notices",
            method: "GET",
            data: $("#searchForm").serialize() + "&page=" + page + "&pageSize=10",
            success: function(response) {
                const columns = ['id', 'title', 'create_by', 'created_at'];
                const columnsName = ['번호', '제목', '작성자', '작성일'];
                createTable(columns, columnsName, response.data);
                createPagination(response.pagination);
            },
            error: function (error) {
                alert("데이터 조회 중 오류발생 : " + error);
            }

        })
    }

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
                tableHtml += `<tr onclick="location.href='/user/notices/${row.id}'">`;  // 클릭 시 상세보기로 이동
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
    

