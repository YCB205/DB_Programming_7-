function putProduct() {

    const apiUrl = '/products';

    var idMerchandise = parseInt(document.getElementById('idMerchandise').value);
    var categori = document.getElementById('categori').value;
    var merchandiseName = document.getElementById('merchandiseName').value;
    var cost = document.getElementById('cost').value;
    var price = document.getElementById('price').value;
    var salesStatus = document.getElementById('salesStatus').value;

    if (salesStatus === "Y"){
        salesStatus = true;
    }else {
        salesStatus=false;
    }
    const productData = {
        idMerchandise,
        categori,
        merchandiseName,
        cost,
        price,
        salesStatus
    };
    console.log(productData);

    fetch(apiUrl, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(productData),
    })
        .then(response => {
            if (!response.ok) {
                throw new Error(`Error: ${response.status} ${response.statusText}`);
            }
            // 성공적인 응답이라면 JSON 파싱을 시도
            return response.text();
        })
        .then(data => {
            // 데이터가 JSON 형식이 아니라면 파싱을 시도하지 않음
            console.log(data);
            window.opener.postMessage('refreshParent', '*');
            window.close();
        })
        .catch(error => {
            console.error(error);
        });
}
