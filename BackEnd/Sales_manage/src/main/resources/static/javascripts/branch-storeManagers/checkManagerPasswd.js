function checkManagerPasswd() {
    // 서버로 데이터 요청
    const passwd = document.querySelector('#passwd').value;
    fetch(`/userPasswd?passwd=${passwd}`, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json'
        },
    })
        .then(response => {
            // 응답 데이터가 JSON 형식이 아닌 경우에 대비하여 확인
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            // JSON 형식으로 변환
            return response.json();
        })
        .then(data => {
            // 성공적으로 처리된 경우의 동작
            console.log('Data successfully received:', data);
            window.opener.postMessage('deleteStoreManager', '*');
            window.close();
        })
        .catch(error => {
            // 오류 발생 시 처리
            console.error('Error getting store managers:', error);

            // 서버에서 반환한 내용에 따라 경고 메시지를 조절할 수 있습니다.
            if (error.message === 'The password does not match') {
                alert('비밀번호가 틀렸습니다!');
            } else {
                alert('서버 오류가 발생했습니다.');
            }
        });
}
