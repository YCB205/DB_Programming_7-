document.addEventListener('DOMContentLoaded', function () {
    // URL 쿼리 매개변수에서 storeManagerId를 추출
    const params = new URLSearchParams(window.location.search);
    const ManagerId = params.get('idManager');

    if (ManagerId) {
        // storeManagerId를 필요에 따라 사용
        fetch(`/managers?idManager=${ManagerId}&name`, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
                // 다른 필요한 헤더를 포함할 수 있습니다.
            },
        })
            .then(response => response.json())
            .then(data => {
                if (data.position === "매니저") {
                    document.getElementById('email').value = data.email;
                    document.getElementById('idManager').value = data.idManager;
                    document.getElementById('name').value = data.name;
                    document.getElementById('phoneNumber').value = data.phoneNumber;
                } else {
                    console.error('잘못된 직급:', data.position);
                }
            })
            .catch(error => console.error('에러:', error));
    } else {
        console.error('storeManagerId가 제공되지 않았습니다.');
    }
});
