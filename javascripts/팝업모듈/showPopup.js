function showPopup(destination_html,width,height) {
    var html = destination_html
    var leftPosition = (screen.width - 600) / 2;
    var topPosition = (screen.height - 560) / 2;
    var options = `width=${width},height=${height},left=` + leftPosition + ',top=' + topPosition + ',location=no, resizable=no, toolbar=no menubar=no';
    window.open(html, 'Popup', options);
}