var main = { // index.js 만의 스코프를 만들어 사용한다. 이렇게 하면 index 객체 안에서만 함수들이 유효하기 때문에 다른 js와 겹칠 위험이 사라진다.
    init : function () {
        var _this = this;
        $('#btn-save').on('click', function () {
            _this.save()
        });
    },
    save : function () {
        var data = {
            title: $('#title').val(),
            author: $('#author').val(),
            content: $('#content').val()
        };

        $.ajax({
            type: 'POST',
            url: '/api/v1/posts',
            dataType: 'json',
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function () {
            alert('글이 등록되었습니다.');
            window.location.href = '/'; // 글 등록이 성공하면 메인페이지(/)로 이동한다.
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    }
};

main.init();