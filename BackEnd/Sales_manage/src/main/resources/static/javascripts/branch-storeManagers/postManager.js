function postManager(){


    // 각 input 태그에서 값을 읽어옴
    const managerName = document.querySelector('#managerName').value;
    const idManager = document.querySelector('#idManager').value;
    const passwd = document.querySelector('#passwd').value;
    const phoneNumber = document.querySelector('#phoneNumber').value;
    const email = document.querySelector('#email').value;

    // 서버에게 보낼 데이터 객체 생성
    const data = {
        idManager: idManager,
        name: managerName,
        passwd: passwd,
        phoneNumber: phoneNumber,
        email: email
    };

    // 서버에게 POST 요청 보내기
    fetch('/managers', {
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
            alert(`${managerName} 매니저 회원을 생성했습니다.`)
            location.reload();
        })
        .catch(error => {
            // 오류 발생 시 처리
            console.error('Error sending branch data to server:', error);
        });
}
