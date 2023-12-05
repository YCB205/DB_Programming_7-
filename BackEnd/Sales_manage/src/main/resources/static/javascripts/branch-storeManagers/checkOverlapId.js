function checkOverlapId() {
    // 서버로 데이터 요청
    const idStoremanager = document.querySelector('#idStoremanager').value;
    fetch(`/checkedStoreManagerId?idStoremanager=${idStoremanager}`, {
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
            // 텍스트 형식으로 변환
            return response.text();
        })
        .then(data => {
            // 성공적으로 처리된 경우의 동작
            console.log('Data successfully received:', data);

            // 서버에서 반환한 내용에 따라 경고 메시지를 조절
            if (data === 'success') {
                document.getElementById('check').value = 'Y';
            }
        })
        .catch(error => {
            // 오류 발생 시 처리
            console.error('Error getting store managers:', error);
            alert('중복된 아이디입니다.');
            document.getElementById('idStoremanager').value = '';
            document.getElementById('check').value = 'N';

        });
}
