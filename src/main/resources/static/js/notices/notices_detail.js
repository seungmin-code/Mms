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
        $("#file_name").text(data.file_name ? data.file_name : '첨부파일 없음');
        $("#content").text(data.content);

        // 파일이 존재할 경우 다운로드 버튼 추가
        if (data.file_name) {
            const fileName = data.file_name;
            const fileLink = $("<span>")
                .text(fileName)
                .css({
                    color: "blue",
                    textDecoration: "underline",
                    cursor: "pointer"
                })
                .on("click", function() {
                    const encodedFileName = encodeURIComponent(fileName);
                    location.href = "/notices/download/" + encodedFileName;
                });
            $("#file_name").html(fileLink);
        }
    }

    ajaxCall("/notices/" + $("#id").val(), "GET", "", success, "", "");
}

// 파일 다운로드 함수
function downloadFile(fileName) {
    location.href = "/notices/download/" + encodeURIComponent(fileName);
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