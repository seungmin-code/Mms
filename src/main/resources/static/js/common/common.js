$(function() {
    const currentPath = window.location.pathname;

    // 모든 아코디언 메뉴 접기
    $('.collapse').removeClass('show');
    $('.nav-link').removeClass('active'); // 기존 활성화 제거

    // 경로에 따라 해당 메뉴 활성화
    if (currentPath.includes("/station/stations")) {
        $('#stationInfoMenu').addClass('show');
        $('a[href="/station/stations"]').addClass('active');
    }
    if (currentPath.includes("/cs/notices")) {
        $('#supportMenu').addClass('show');
        $('a[href="/cs/notices"]').addClass('active');
    }
    if (currentPath.includes("/user/gis-map") || currentPath.includes("/user/gis-analysis")) {
        $('#gisMenu').addClass('show');
        $('a[href="' + currentPath + '"]').addClass('active');
    }
});