$(function() {
    searchData();
})

function searchData() {
    const success = function(response) {
        const data = response.data;

        $("#title").text(data.title);
        $("#create_by").text(data.create_by);
        $("#create_at").text(data.created_at);
        $("#update_at").text(data.updated_at);
        $("#file_name").text(data.file_name);
        $("#content").text(data.content);
    }
    ajaxCall("/notices/" + $("#id").val(), "GET", "", success, "", "");
}

function updatePage() {
    window.location.href = "/notices/update/" + $("#id").val();
}

function deleteData() {
    if (confirm("정말로 삭제하시겠습니까?")) {
        const url = "/notices/" + $("#id").val();
        const success = function(response) {
            alert("삭제가 완료되었습니다")
            window.location.href = "/notices/select";
        }
        ajaxCall(url, "DELETE", "", success, "", "");
    }
}

function saveData() {

}