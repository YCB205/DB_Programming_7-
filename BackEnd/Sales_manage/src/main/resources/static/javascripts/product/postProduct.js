function postProduct() {
    // 각 input 태그에서 값을 읽어옴
    const idMerchandise = document.querySelector('#idMerchandise').value;
    const merchandiseName = document.querySelector('#merchandiseName').value;
    const cost = document.querySelector('#cost').value;
    const price = document.querySelector('#price').value;

    // 서버에게 보낼 데이터 객체 생성
    const data = {
        idMerchandise: idMerchandise,
        merchandiseName: merchandiseName,
        cost: cost,
        price: price
    };

    // 서버에게 POST 요청 보내기
    fetch('/products', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(data)
    })
        .then(response => {
            // 응답이 성공적으로 이루어지면 처리
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            // return response.json(); // JSON 형식으로 변환
        })
        .then(result => {
            // 서버의 응답 데이터에 따른 동작 수행
            console.log('Server response:', result);
            alert(`${merchandiseName}을 생성했습니다.`)
            location.reload();
        })
        .catch(error => {
            // 오류 발생 시 처리
            console.error('Error sending branch data to server:', error);
        });
}
