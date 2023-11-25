function putOrderSheet() {
    const url = '/orderSheet';

    // 데이터를 JSON 형식으로 가져오기
    const orderData = getDataAsJSON();

    // 서버로 데이터 전송
    fetch(url, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json',
        },
        body: orderData,
    })
        .then(response => {
            // 응답 데이터가 JSON 형식이 아닌 경우에 대비하여 확인
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            // 여기에서 별도의 JSON 변환 없이 반환
            return response;
        })
        .then(data => {
            // 성공적으로 처리된 경우의 동작
            console.log('Order sheet successfully sent:', data);
            window.opener.postMessage('orderSheetUpdated', '*');
            window.close();
        })
        .catch(error => {
            // 오류 발생 시 처리
            console.error('Error sending order sheet:', error);
        });
}


function getDataAsJSON() {
    var orderData = {
    "idOrdersheet": parseInt(document.getElementById('orderTableBody').getElementsByTagName('tr')[0].getElementsByTagName('td')[0].textContent),
    "orderTime": formatOrderTime(document.getElementById('orderTableBody').getElementsByTagName('tr')[0].getElementsByTagName('td')[1].textContent),
    "sales": parseInt(convertStringToNumber(document.getElementById('orderTableBody').getElementsByTagName('tr')[0].getElementsByTagName('td')[2].textContent)),
    "profit": parseInt(convertStringToNumber(document.getElementById('orderTableBody').getElementsByTagName('tr')[0].getElementsByTagName('td')[3].textContent)),
    "merchandise": []
};

    var productRows = document.getElementById('productTableBody').getElementsByTagName('tr');

    for (var i = 0; i < productRows.length; i++) {
    var columns = productRows[i].getElementsByTagName('td');

    var merchandise = {
    "categori": columns[0].textContent.trim(),
    "id_merchandise": parseInt(columns[1].textContent.trim()),
    "merchandiseName": columns[2].textContent.trim(),
    "cost": parseInt(convertStringToNumber(columns[3].textContent.trim())),
    "price": parseInt(convertStringToNumber(columns[4].textContent.trim())),
    "orderCount": parseInt(columns[5].getElementsByTagName('input')[0].value)
};

    orderData.merchandise.push(merchandise);
}

    return JSON.stringify(orderData, null, 2);
}

function formatOrderTime(orderTime) {
    return orderTime.replace(/(\d{4})년 (\d{1,2})월 (\d{1,2})일 - (\d{2}:\d{2}:\d{2})/, '$1-$2-$3 $4');
}





