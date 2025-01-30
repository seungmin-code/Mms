
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
    

