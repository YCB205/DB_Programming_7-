function getProducts() {
    const productName = document.querySelector('.input_store_name').value;
    const categories = [];

    // 서버로 데이터 요청
    fetch(`/orderproducts?product_name=${productName}&category=${categories}`, {
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
            console.log('Products successfully received:', data);
            // 여기에서 받은 데이터를 처리하거나 화면에 표시할 수 있습니다.

            // 테이블의 tbody 요소
            const tbody = document.querySelector('.product_table_tbody');

            // 기존 행 제거
            tbody.innerHTML = '';

            // 받은 데이터로 새로운 행 추가
            for (const category in data) {
                const products = data[category];
                for (const product of products) {
                    const row = document.createElement('tr');

                    // 첫 번째 td에는 카테고리명 추가
                    const categoryCell = document.createElement('td');
                    categoryCell.textContent = category;
                    row.appendChild(categoryCell);

                    // 나머지 데이터를 추가
                    for (const key of ['id_merchandise', 'merchandiseName', 'cost', 'price']) {
                        const cell = document.createElement('td');
                        if (key === 'cost' || key === 'price') {
                            cell.textContent = `${product[key]}원`;
                        } else {
                            cell.textContent = product[key];
                        }
                        row.appendChild(cell);
                    }

                    // 행을 tbody에 추가
                    tbody.appendChild(row);
                    // 클릭 이벤트 리스너 추가
                    row.addEventListener('click', function () {
                        this.classList.toggle('choiceValue');

                        // 클릭된 행 내부의 모든 td 요소 가져오기
                        const tdElements = this.getElementsByTagName('td');

                        // 클릭된 행에 choiceValue 클래스가 있는지 확인
                        const hasChoiceValueClass = this.classList.contains('choiceValue');

                        // choiceValue 클래스의 존재 여부에 따라 style 속성 추가 또는 제거
                        for (const td of tdElements) {
                            if (hasChoiceValueClass) {
                                // choiceValue 클래스가 있으면 지정된 배경색 스타일 추가
                                td.style.backgroundColor = '#ec98a7';
                            } else {
                                // choiceValue 클래스가 없으면 배경색 스타일 제거 (기본값으로 초기화)
                                td.style.backgroundColor = '';
                            }
                        }
                    });
                }
            }
        })
        .catch(error => {
            // 오류 발생 시 처리
            console.error('Error getting products:', error);
        });
}
