/**
 * Common.js 공통 함수 모음
 */

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
 * jQuery Block UI 표출 함수
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
            color : '#fff',
            marginLeft: '150px'
        }
    });
}

/**
 * jQuery Block UI 중단 함수
 */
function offBlockUI() {
    $.unblockUI();
}

/**
 * 셀렉트박스에 옵션을 추가하는 함수
 * @param data 옵션에 추가될 데이터리스트
 * @param selectBoxId 추가할 셀렉트 박스 아이디
 * @param defaultOption 기본 옵션 리스트 (기본값 : -전체-)
 */
function addOptions(data, selectBoxId, defaultOption) {
    const selectBox = $(selectBoxId);
    selectBox.empty();

    if(defaultOption) {
        selectBox.append(`<option value="">${defaultOption}</option>`);
    }

    // 서버에서 받아온 데이터로 옵션 추가
    data.forEach(option => {
        selectBox.append(`<option value="${option}">${option}</option>`);
    });
}