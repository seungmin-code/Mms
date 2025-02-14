/**
 * Common.js 공통 함수 모음
 */

$(function () {
    sidebarEvent();
    preventionSubmit();
});

/**
 * 사이드바 선택 시 발생 이벤트
 * 대메뉴에 음영표시
 * 하위 메뉴에 글씨 볼드와 밑줄 표시
 */
function sidebarEvent() {
    // 현재 페이지 경로를 가져온 후 두 번째 '/' 전까지 추출
    const currentPath = window.location.pathname.split('/').slice(0, 2).join('/');

    $(".nav-item").each(function () {
        const $navItem = $(this);
        const $parentLink = $navItem.children("a.nav-link");
        const $subMenu = $navItem.find(".collapse");

        // 서브 메뉴 내에서 현재 경로의 시작 부분과 일치하는 항목을 찾음
        const $activeSubMenuItem = $subMenu.find("a.nav-link").filter(function() {
            const hrefPath = $(this).attr("href").split('/').slice(0, 2).join('/');  // href에서 두 번째 '/' 전까지 추출
            return hrefPath && currentPath === hrefPath;  // 두 경로를 비교
        });

        if ($activeSubMenuItem.length > 0) {
            $parentLink.addClass("active");
            $subMenu.addClass("show");
            $activeSubMenuItem.addClass("active");
        }
    });

    // 아이콘 회전 이벤트 설정
    $(".nav-link[data-bs-toggle='collapse']").on("click", function () {
        const $icon = $(this).find(".bi-chevron-down");

        if ($(this).attr("aria-expanded") === "true") {
            $icon.removeClass("rotate");
        } else {
            $icon.addClass("rotate");
        }
    });
}

/**
 * 엔터클릭하여 폼 제출되는 현상 방지
 */
function preventionSubmit() {
    $("#searchForm").on("keydown", function(event) {
        if (event.key === "Enter") {
            event.preventDefault();
        }
    });
}

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
 * @param async 비동기 동기 여부
 */
function ajaxCall(url, type, params, success, failure, async) {
    async = (typeof async === "undefined") ? true : async;

    $.ajax({
        url: url,
        type: type,
        data: params,
        async: async,
        dataType: "json",
        contentType: "application/json",
        success: function(response) {
            if (typeof success === "function") {
                success(response);
            }
        },
        error: function(xhr, status, error) {
            alert("시스템 에러가 발생했습니다");
            console.log(xhr, status, error);
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
 * Ajax 호출 함수(로딩블록과 파일처리를 위한 코드 포함)
 * @param url 호출 할 URL
 * @param type 데이터타입(GET, POST 등..)
 * @param formData 데이터 파라미터(파일포함)
 * @param success 호출 성공 시 실행 할 함수
 * @param failure 호출 실패 시 실행 할 함수
 * @param async 비동기 동기 여부
 */
function ajaxFileCall(url, type, formData, success, failure, async) {
    async = (typeof async === "undefined") ? true : async;

    $.ajax({
        url: url,
        type: type,
        data: formData,
        async: async,
        processData: false,
        contentType: false,
        success: function(response) {
            if (typeof success === "function") {
                success(response);
            }
        },
        error: function(xhr, status, error) {
            alert("시스템 에러가 발생했습니다");
            console.log(xhr, status, error);
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
    if (message === undefined) {
        message = "시스템 처리중입니다..."
    }

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

/**
 * 셀렉트박스에 옵션을 추가하는 함수
 * @param data 옵션에 추가될 데이터리스트 (List<Map<String, Object>> 형태)
 * @param selectBoxId 추가할 셀렉트 박스 아이디
 * @param defaultOption 기본 옵션 리스트 (기본값 : -전체-)
 * @param valueKey 옵션 값으로 사용할 key (예: 'id')
 * @param textKey 옵션 텍스트로 사용할 key (예: 'name')
 */
function multiAddOptions(data, selectBoxId, defaultOption, valueKey, textKey) {
    const selectBox = $(selectBoxId);
    selectBox.empty();

    if (defaultOption) {
        selectBox.append(`<option value="">${defaultOption}</option>`);
    }

    // 서버에서 받아온 데이터로 옵션 추가
    data.forEach(item => {
        const value = item[valueKey];
        const text = item[textKey];
        selectBox.append(`<option value="${value}">${text}</option>`);
    });
}

/**
 * 페이지헬퍼를 통해 받아온 데이터를 기반으로 페이징 처리를 진행하는 함수
 * 조회함수는 searchData 통일
 * 페이지 DIV 아이디는 pageContainer 통일
 * @param pagination 페이징 처리를 위한 정보를 담은 파라미터
 */
function createPagination(pagination) {
    const pageContainer = $("#pageContainer");
    pageContainer.empty();

    if (!pagination || pagination.pages <= 1) return;

    const totalPages = pagination.pages;
    const currentPage = pagination.pageNum;
    const pageList = pagination.navigatepageNums || [];

    let paginationHtml = '';

    if (currentPage > 1) {
        paginationHtml += `<button class="page-btn btn btn-secondary btn-sm" onclick="searchData(1)" ${currentPage === 1 ? 'disabled' : ''} style="margin-right: 5px;"><<</button>`;
        paginationHtml += `<button class="page-btn btn btn-secondary btn-sm" onclick="searchData(${currentPage - 1})" ${currentPage === 1 ? 'disabled' : ''} style="margin-right: 5px;"><</button>`;
    }
    pageList.forEach(page => {
        if (page === currentPage) {
            paginationHtml += `<button class="page-btn btn btn-primary btn-sm" onclick="searchData(${page})" style="margin-right: 5px;">${page}</button>`;
        } else {
            paginationHtml += `<button class="page-btn btn btn-secondary btn-sm" onclick="searchData(${page})" style="margin-right: 5px;">${page}</button>`;
        }
    });

    if (currentPage < totalPages) {
        paginationHtml += `<button class="page-btn btn btn-secondary btn-sm" onclick="searchData(${currentPage + 1})" ${currentPage === totalPages ? 'disabled' : ''} style="margin-right: 5px;">></button>`;
        paginationHtml += `<button class="page-btn btn btn-secondary btn-sm" onclick="searchData(${totalPages})" ${currentPage === totalPages ? 'disabled' : ''} style="margin-right: 5px;">>></button>`;
    }
    pageContainer.html(paginationHtml);
}

/**
 * flatPicker 설정 함수
 * @param dateType ("month or day")
 */
function flatPickerSetting(dateType) {
    const today = new Date();
    const todayString = today.toISOString().split("T")[0].slice(0, 7);
    const todayDayString = today.toISOString().split("T")[0];

    // 한 달 전 날짜 계산
    const oneMonthAgo = new Date(today);
    oneMonthAgo.setMonth(today.getMonth() - 1);
    const oneMonthAgoString = oneMonthAgo.toISOString().split("T")[0];

    // 6개월 전 날짜 계산
    const sixMonthsAgo = new Date(today);
    sixMonthsAgo.setMonth(today.getMonth() - 6);
    const sixMonthsAgoString = sixMonthsAgo.toISOString().split("T")[0];

    const flatpickrOptions = {
        locale: "ko",
        maxDate: "today"
    };

    if (dateType === "month") {
        const monthOptions = {
            ...flatpickrOptions,
            dateFormat: "Y-m",
            mode: "single",
            plugins: [new monthSelectPlugin({ dateFormat: "Y-m" })],
            minDate: sixMonthsAgoString,
            defaultDate: sixMonthsAgoString.slice(0, 7)
        };

        flatpickr("#startDate", monthOptions);
        flatpickr("#endDate", {
            ...monthOptions,
            defaultDate: todayString
        });
    }

    if (dateType === "day") {
        const dayOptions = {
            ...flatpickrOptions,
            dateFormat: "Y-m-d",
            mode: "single",
            minDate: sixMonthsAgoString,
            defaultDate: oneMonthAgoString
        };

        flatpickr("#startDate", dayOptions);
        flatpickr("#endDate", {
            ...dayOptions,
            defaultDate: todayDayString
        });
    }
}

/**
 * 현재 날짜를 YYYYMMDD 형식의 문자열로 반환하는 함수
 * @returns {string} 현재 날짜 (YYYYMMDD 형식)
 */
function getYYYYmmdd() {
    const today = new Date();
    return formatDate(today);
}

/**
 * n일 전의 날짜를 YYYYMMDD 형식의 문자열로 반환하는 함수
 * @param {number} n - 가져올 날짜의 일 수
 * @returns {string} n일 전의 날짜 (YYYYMMDD 형식)
 */
function getYYYYmmddNDaysAgo(n) {
    const date = new Date();
    date.setDate(date.getDate() - n); // n일 전 날짜로 설정
    return formatDate(date);
}

/**
 * 날짜 객체를 YYYYMMDD 형식의 문자열로 반환하는 함수
 * @param {Date} date - 날짜 객체
 * @returns {string} 날짜 (YYYYMMDD 형식)
 */
function formatDate(date) {
    const year = String(date.getFullYear());
    const month = String(date.getMonth() + 1).padStart(2, '0');
    const day = String(date.getDate()).padStart(2, '0');
    return year + month + day;
}



