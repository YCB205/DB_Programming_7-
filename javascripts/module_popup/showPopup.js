function showPopup(destination_html, width, height) {
    var html = destination_html;
    var leftPosition = (screen.width - 600) / 2;
    var topPosition = (screen.height - 692) / 2;
    var options = `width=${width},height=${height},left=` + leftPosition + ',top=' + topPosition + ',location=no,resizable=no,toolbar=no,menubar=no';

    var popupWindow = window.open(html, 'Popup' + Date.now(), options);

    // 기존 창이 정상적으로 열렸을 때
    if (popupWindow) {
        // zoom 속성을 사용하여 110% 배율로 조정
        popupWindow.document.body.style.zoom = "110%";
    }
}
