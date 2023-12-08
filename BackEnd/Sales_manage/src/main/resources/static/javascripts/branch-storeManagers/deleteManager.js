function deleteManager() {
    const idManager = document.getElementById('idManager').value;
    const url = `/managers/${idManager}`;

    // 서버로 데이터 전송
    fetch(url, {
        method: 'DELETE',
        headers: {
            'Content-Type': 'application/json',
        },
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
            console.log('StoreManager successfully sent:', data);
            window.opener.postMessage('updateManagerInformation', '*');
            window.close();
        })
        .catch(error => {
            // 오류 발생 시 처리
            console.error('Error sending order sheet:', error);
        });
}

