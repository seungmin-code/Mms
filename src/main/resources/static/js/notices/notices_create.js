function createData() {
    if (confirm("공지사항을 등록하시겠습니까?")) {
        const success = function () {
            alert("공지사항이 등록되었습니다");
            window.location.href = "/notices/select";
        }
        let formData = new FormData();

        formData.append("title", $("#title").val());
        formData.append("content", $("#content").val());

        let file = $("#fileInput")[0];
        if (file.files.length > 0) {
            formData.append("file", file.files[0]);
        }

        ajaxFileCall("/notices", "POST", formData, success, "");
    }

}