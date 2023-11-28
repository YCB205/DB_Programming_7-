document.addEventListener('DOMContentLoaded', function () {
    // URL 쿼리 매개변수에서 storeManagerId를 추출
    const params = new URLSearchParams(window.location.search);
    const storeManagerId = params.get('idStoreManager');

    if (storeManagerId) {
        // storeManagerId를 필요에 따라 사용
        fetch(`/storeManagers?idStoreManager=${storeManagerId}`, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
                // 다른 필요한 헤더를 포함할 수 있습니다.
            },
        })
            .then(response => response.json())
            .then(data => {
                if (data.position === "점주") {
                    document.getElementById('email').value = data.email;
                    document.getElementById('idStoreManager').value = data.idStoreManager;
                    document.getElementById('name').value = data.name;
                    document.getElementById('phoneNumber').value = data.phoneNumber;
                    document.getElementById('officeName').value = data.officeName;
                } else {
                    console.error('잘못된 직급:', data.position);
                }
            })
            .catch(error => console.error('에러:', error));
    } else {
        console.error('storeManagerId가 제공되지 않았습니다.');
    }
});
