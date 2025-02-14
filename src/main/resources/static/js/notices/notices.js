
$(function() {
    searchData(1);
})

function searchData(page) {
    const params = {
        category: $("#category").val(),
        keyword: $("#keyword").val(),
        page: page,
        pageSize: 10
    };
    const success = function(response) {
        createTable(response.data);
        createPagination(response.pagination)
    }

    ajaxCall("/notices", "GET", params, success, "", "");
}

function createTable(data) {
    const tableBody = $("#tableBody");
    tableBody.empty();

    let tableHtml = "";
    if (data.length > 0) {
        data.forEach(item => {
            tableHtml += `
                <tr onclick="detailPage(${item.id})">
                  <td class='text-center'>${item.id}</td>
                  <td>${item.title}</td>
                  <td class='text-center'>${item.create_by}</td>
                  <td class='text-center'>${item.created_at}</td>
                </tr>
            `;
        });
    } else {
        tableHtml += `
                <tr>
                  <td class='text-center' colspan="4">데이터가 없습니다</td>
                </tr>
            `;
    }

    tableBody.append(tableHtml);
}

function createPage() {
    window.location.href = "/notices/create";
}

function detailPage(id) {
    window.location.href = "/notices/detail/" + id;
}