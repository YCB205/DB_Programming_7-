function showPopupUpdateOrderSheet(destination_html, width, height, tagClass) {
    var html = destination_html;
    var leftPosition = (screen.width - 660) / 2;
    var topPosition = (screen.height - 661) / 2;
    var options = `width=${width},height=${height},left=` + leftPosition + ',top=' + topPosition + ',location=no,resizable=no,toolbar=no,menubar=no';

    var popupWindow = window.open(html, 'Popup' + Date.now(), options);
    var tdContentList = getParentValues(tagClass);
    // 기존 창이 정상적으로 열렸을 때
    if (popupWindow) {
        // zoom 속성을 사용하여 110% 배율로 조정
        popupWindow.document.body.style.zoom = "110%";

        // 자식 페이지에 데이터 전달
        var myData = { orderData: tdContentList.tdContentList, productData:tdContentList.productListContentList };
        localStorage.setItem('myData', JSON.stringify(myData));

    }
}

function getParentValues(tagClass) {
    var selectedRow = document.querySelector(tagClass);
    var tdElements = selectedRow.querySelectorAll('td'); //tdElements[0]~[3]  까지만 유의미한 데이터임
    var nextSiblingRow = selectedRow.nextElementSibling;
    var productList = nextSiblingRow.querySelectorAll('td')

    var tdContentList = [];
    tdElements.forEach(function (td) {
        tdContentList.push(td.textContent.trim());
    });
    var productListContentList = [];
    productList.forEach(function (td) {
        productListContentList.push(td.textContent.trim());
    });
    // 부모 페이지에 데이터 전달
    return {
        tdContentList: tdContentList,
        productListContentList: productListContentList
    };

}
