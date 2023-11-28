function getBranchStoreManagers() {
    const officeName = document.querySelector('.input_officeName').value;
    const name = document.querySelector('.input_store_name').value;

    // 서버로 데이터 요청
    fetch(`/branch-storeManagers?officeName=${officeName}&name=${name}`, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
            // You may include other headers if needed
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
            // 여기에서 받은 데이터를 처리하거나 화면에 표시할 수 있습니다.

        })
        .catch(error => {
            // 오류 발생 시 처리
            console.error('Error getting products:', error);
        });
}
