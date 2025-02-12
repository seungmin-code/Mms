function createData() {
    if (confirm("등록하시겠습니까?")) {
        let formData = new FormData();

        formData.append("title", $("#title").val());
        formData.append("content", $("#content").val());

        const file = $("#file")[0].files[0];
        if (file) {
            formData.append("file", file);
        }


        const success = function(response) {
            alert("등록이 완료되었습니다")
            window.location.href = "/notices/select";
        }

        ajaxFileCall("/notices", "POST", formData, success, "", "");
    }
}