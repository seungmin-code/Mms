




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
