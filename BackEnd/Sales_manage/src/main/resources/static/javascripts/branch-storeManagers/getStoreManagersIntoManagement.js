getStoreManagersIntoManagement();
function getStoreManagersIntoManagement() {
    // 서버로 데이터 요청
    const name = document.querySelector('.input_store_name').value;
    fetch(`/storeManagers?idStoreManager&name=${name}`, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
            // 다른 필요한 헤더를 포함할 수 있습니다.
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

            // 테이블의 tbody 요소
            const tbody = document.querySelector('.product_table_tbody');

            // 기존 행 제거
            tbody.innerHTML = '';

            // 받은 데이터로 새로운 행 추가
            data.storeManagers.forEach(storeManager => {
                const row = document.createElement('tr');

                // 나머지 데이터를 추가
                for (const key of ['idStoremanager', 'name', 'phoneNumber', 'email']) {
                    const cell = document.createElement('td');
                    cell.textContent = storeManager[key];
                    row.appendChild(cell);
                }

                // 수정 버튼 추가
                const editCell = document.createElement('td');
                const editButton = document.createElement('a');
                editButton.className = 'btn btn-info';
                editButton.target = '_self';
                // 수정 버튼 클릭 시 showPopupStoreManager 함수 호출
                editButton.onclick = function () {
                    showPopupStoreManager('storeManager_information_check.html', 600, 617, storeManager['idStoremanager']);
                };
                const editIcon = document.createElement('i');
                editIcon.className = 'bi bi-pencil-square';
                editButton.appendChild(editIcon);
                editCell.appendChild(editButton);
                row.appendChild(editCell);

                // 행을 tbody에 추가
                tbody.appendChild(row);
            });
        })
        .catch(error => {
            // 오류 발생 시 처리
            console.error('Error getting store managers:', error);
        });
}
