//Khi nào trang html nội dung đã được nạp vào trình duyệt thì sẽ chạy code bên trong function
$(document).ready(function() {
    //Lắng nghe sự kiện click cho thẻ có id là btn-delete-user
    $(".btn-delete-role").click(function () {
        var id = $(this).attr("roleid")
        var This = $(this)
        $.ajax({
            method: "GET",
            url: "http://localhost:8080/demoservlet/role/delete?id=" + id,
            //data: { name: "John", location: "Boston" }
        })
            .done(function (result) {
                This.closest("tr").remove();
                console.log("Ket qua", result)
            });
    });
})