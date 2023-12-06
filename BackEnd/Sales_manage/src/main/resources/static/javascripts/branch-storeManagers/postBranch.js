function postBranch() {
    // 각 input 태그에서 값을 읽어옴
    const idStoreManager = document.querySelector('#idStoreManager').value;
    const officeName = document.querySelector('#officeName').value;
    const address = document.querySelector('#address').value;

    // 서버에게 보낼 데이터 객체 생성
    const data = {
        idStoreManager: idStoreManager,
        officeName: officeName,
        address: address
    };

    // 서버에게 POST 요청 보내기
    fetch('/branch', {
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
            alert(`${officeName}지점을 생성했습니다.`)
            location.reload();
        })
        .catch(error => {
            // 오류 발생 시 처리
            console.error('Error sending branch data to server:', error);
        });
}
