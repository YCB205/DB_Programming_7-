function postStoreManager(){


    // 각 input 태그에서 값을 읽어옴
    const storeManagerName = document.querySelector('#storeManagerName').value;
    const idStoremanager = document.querySelector('#idStoremanager').value;
    const passwd = document.querySelector('#passwd').value;
    const phoneNumber = document.querySelector('#phoneNumber').value;
    const email = document.querySelector('#email').value;

    // 필드가 비어 있는지 확인
    if (!storeManagerName || !idStoremanager || !passwd || !phoneNumber || !email) {
        alert('모든 정보를 입력하세요.');
        return; // 필드가 하나라도 비어 있으면 함수 종료
    }

    // 서버에게 보낼 데이터 객체 생성
    const data = {
        idStoremanager: idStoremanager,
        name: storeManagerName,
        passwd: passwd,
        phoneNumber: phoneNumber,
        email: email
    };

    // 서버에게 POST 요청 보내기
    fetch('/storeManagers', {
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
            alert(`${storeManagerName} 점주 회원을 생성했습니다.`)
            location.reload();
        })
        .catch(error => {
            // 오류 발생 시 처리
            console.error('Error sending branch data to server:', error);
        });
}
