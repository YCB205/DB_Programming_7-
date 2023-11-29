getStoreManagers();
function getStoreManagers() {
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

                // 행을 tbody에 추가
                tbody.appendChild(row);

                // 클릭 이벤트 리스너 추가
                row.addEventListener('click', function () {
                    // 모든 행의 choiceValue 클래스 제거
                    const allRows = document.querySelectorAll('.product_table_tbody tr');
                    allRows.forEach(otherRow => {
                        if (otherRow !== this) {
                            otherRow.classList.remove('choiceValue');
                            // 기존 배경색 스타일 제거 (기본값으로 초기화)
                            otherRow.querySelectorAll('td').forEach(cell => {
                                cell.style.backgroundColor = '';
                            });
                        }
                    });

                    // 현재 클릭된 행에 choiceValue 클래스 추가
                    this.classList.toggle('choiceValue');

                    // 클릭된 행에 choiceValue 클래스가 있는지 확인
                    const hasChoiceValueClass = this.classList.contains('choiceValue');

                    // choiceValue 클래스의 존재 여부에 따라 style 속성 추가 또는 제거
                    this.querySelectorAll('td').forEach(cell => {
                        if (hasChoiceValueClass) {
                            // choiceValue 클래스가 있으면 지정된 배경색 스타일 추가
                            cell.style.backgroundColor = '#ec98a7';
                        } else {
                            // choiceValue 클래스가 없으면 배경색 스타일 제거 (기본값으로 초기화)
                            cell.style.backgroundColor = '';
                        }
                    });
                });
            });
        })
        .catch(error => {
            // 오류 발생 시 처리
            console.error('Error getting store managers:', error);
        });
}
