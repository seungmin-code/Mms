$(function() {
    searchData();
})

function deleteFile() {
    $("#existingFile").text("");
    $("#deleteFile").hide();
    $("#fileInput").show();
}

function searchData() {
    const success = function(response) {
        const data = response.data;

        $("#title").val(data.title);
        $("#content").val(data.content);
        $("#create_by").text(data.create_by);
        $("#create_at").text(data.created_at);
        $("#update_at").text(data.updated_at);

        // 기존 파일이 있는 경우 파일명과 삭제 버튼 표시
        if (data.file_name) {
            $("#existingFile").text(data.file_name);
            $("#deleteFile").show();
            $("#fileInput").hide();
        }
    }

    // 공지사항 데이터를 GET으로 요청
    ajaxCall("/notices/" + $("#id").val(), "GET", "", success, "", "");
}

function updateData() {
    console.log($("#deleteFile").is(":visible"));
    if (confirm("정말로 수정하시겠습니까?")) {
        let formData = new FormData();

        formData.append("title", $("#title").val());
        formData.append("content", $("#content").val());

        if(!$("#deleteFile").is(":visible")) {
            let file = $("#fileInput")[0];
            if (file.files.length > 0) {
                formData.append("file", file.files[0]);
            }
            formData.append("deleteFile", "true");
        } else {
            formData.append("deleteFile", "false");
        }

        const success = function(response) {
            alert("수정이 완료되었습니다")
            window.location.href = "/notices/select";
        }
        ajaxFileCall("/notices/" + $("#id").val(), "PATCH", formData, success, "");
    }
}