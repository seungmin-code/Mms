/**
 * 온로드 실행 함수
 */
$(function() {

});

/**
 * Ajax 호출 함수(기본)
 * @param url 호출 할 URL
 * @param type 데이터타입(GET, POST 등..)
 * @param params 데이터 파라미터
 * @param success 호출 성공 시 실행 할 함수
 * @param failure 호출 실패 시 실행 할 함수
 */
function ajaxCallNoBlock(url, type, params, success, failure) {
    $.ajax({
        url: url,
        type: type,
        data: params,
        async: true,
        dataType: "json",
        success: function(response) {
            if (typeof success === "function") {
                success(response);
            }
        },
        error: function(xhr, status, error) {
            alert("시스템 에러가 발생했습니다");
            if (typeof failure === "function") {
                failure(xhr, status, error);
            }
        }
    })
}

/**
 * Ajax 호출 함수(로딩블록 포함)
 * @param url 호출 할 URL
 * @param type 데이터타입(GET, POST 등..)
 * @param params 데이터 파라미터
 * @param success 호출 성공 시 실행 할 함수
 * @param failure 호출 실패 시 실행 할 함수
 */
function ajaxCall(url, type, params, success, failure) {
    $.ajax({
        url: url,
        type: type,
        data: params,
        async: true,
        dataType: "json",
        success: function(response) {
            if (typeof success === "function") {
                success(response);
            }
        },
        error: function(xhr, status, error) {
            alert("시스템 에러가 발생했습니다");
            if (typeof failure === "function") {
                failure(xhr, status, error);
            }
        },
        beforeSend: function() {
            onBlockUI("시스템 처리중입니다...");
        },
        complete: function() {
            offBlockUI();
        }
    })
}

/**
 * jQuery Block UI 활용한 로딩블록 시작
 * @param message 로딩블록에 표시 될 메시지
 */
function onBlockUI(message) {
    // 메시지 없으면 기본 메시지 적용
    if (message === undefined) {
        message = "시스템 처리중입니다..."
    }

    // 블록 UI 디자인
    $.blockUI({
        message: message,
        css : {
            border : 'none',
            padding : '15px',
            backgroundColor : '#000',
            '-webkit-border-radius' : '10px',
            '-moz-border-radius' : '10px',
            position : 'absolute',
            'z-index' : '9999999',
            opacity : .5,
            color : '#fff'
        }
    });
}

/**
 * jQuery Block UI 활용한 로딩블록 종료
 */
function offBlockUI() {
    $.unblockUI();
}