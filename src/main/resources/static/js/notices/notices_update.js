$(function() {
    searchData();
})

function searchData() {
    const success = function(response) {
        const data = response.data;

        $("#title").val(data.title);
        $("#content").val(data.content);
        $("#create_by").text(data.create_by);
        $("#create_at").text(data.created_at);
        $("#update_at").text(data.updated_at);
    }

    // 공지사항 데이터를 GET으로 요청
    ajaxCall("/notices/" + $("#id").val(), "GET", "", success, "", "");
}

function updateData() {
    if (confirm("정말로 수정하시겠습니까?")) {
        const params = {
            title: $("#title").val(),
            content: $("#content").val(),
            file_path: $("#file_path").val()
        }

        const success = function(response) {
            alert("수정이 완료되었습니다")
            window.location.href = "/notices/select";
        }

        ajaxCall("/notices/" + $("#id").val(), "PATCH", JSON.stringify(params), success, "", "");
    }
}