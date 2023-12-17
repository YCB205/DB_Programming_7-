function putManager_User_information_check() {
    const apiUrl = '/user';

    const email = document.getElementById('email').value;
    const id = document.getElementById('idManager').value;
    const passwd = document.getElementById('passwd').value;
    const name = document.getElementById('name').value;
    const phoneNumber = document.getElementById('phoneNumber').value;

    // 필드가 비어 있는지 확인
    if (!email || !idManager || !passwd || !name || !phoneNumber) {
        alert('모든 정보를 입력하세요.');
        return; // 필드가 하나라도 비어 있으면 함수 종료
    }

    const userData = {
        email,
        id,
        passwd,
        name,
        phoneNumber,
    };

    fetch(apiUrl, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(userData),
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
