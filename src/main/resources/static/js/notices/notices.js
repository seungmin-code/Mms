
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
    }

    tableBody.append(tableHtml);
}

function createPage() {
    window.location.href = "/notices/create";
}

function detailPage(id) {
    window.location.href = "/notices/detail/" + id;
}

function updatePage() {
    window.location.href = "/notices/update";
}

function createData() {
    const params = {title: "title", content: "content", create_by: "seungmin"};
    const success = function(response) {
        console.log(response);
    }

    ajaxCall("/notices", "POST", JSON.stringify(params), success, "", "");
}

function searchDetailData() {
    const success = function(response) {
        console.log(response);
    }

    ajaxCall("/notices/1", "GET", "", success, "", "");
}

function patchData() {
    const params = {title: "updated title", content: "updated content"};
    const success = function(response) {
        console.log(response);
    }

    ajaxCall("/notices/1", "PATCH", JSON.stringify(params), success, "", "");
}

function deleteData() {
    const success = function(response) {
        console.log(response);
    }

    ajaxCall("/notices/1", "DELETE", "", success, "", "");
}
