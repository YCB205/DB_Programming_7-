getAllProducts();
let isCheckboxGroupExecuted = false;
function getAllProducts() {
    const productName = document.querySelector('#searchProductName').value;
    const categories = [];

    // 서버로 데이터 요청
    fetch(`/allProducts?product_name=${productName}&category=${categories}`, {
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

            // 테이블의 tbody 요소
            const tbody = document.querySelector('.product_table_tbody');

            // 기존 행 제거
            tbody.innerHTML = '';

            if (!isCheckboxGroupExecuted) {
                // 체크박스 그룹
                var checkbox_group_left = document.querySelector('.checkbox-group-left');
                var checkbox_group_right = document.querySelector('.checkbox-group-right');
                checkbox_group_left.innerHTML = '';
                checkbox_group_right.innerHTML = '';
            }

            // 받은 데이터로 새로운 행과 체크박스 추가
            for (const category in data) {
                const products = data[category];

                if (!isCheckboxGroupExecuted) {
                    // 체크박스 추가
                    const label = document.createElement('label');
                    label.innerHTML = `<input class="form-check-input" type="checkbox" value="${category}">${category}`;

                    // 카테고리 값의 순서에 따라 체크박스 그룹에 추가
                    if (Object.keys(data).indexOf(category) % 2 === 0) {
                        checkbox_group_left.appendChild(label);
                    } else {
                        checkbox_group_right.appendChild(label);
                    }
                }

                for (let i = 0; i < products.length; i++) {
                    const product = products[i];
                    const row = document.createElement('tr');

                    // 카테고리명이 추가되는 경우
                    if (i % 2 === 0) {
                        row.classList.add('table-light');
                    } else {
                        row.classList.add('table-success');
                    }

                    // 첫 번째 td에는 카테고리명 추가
                    const categoryCell = document.createElement('td');
                    categoryCell.textContent = category;
                    row.appendChild(categoryCell);

                    // 카테고리, 상품명, 판매 상태, 가격, 비용 순으로 데이터를 추가
                    for (const key of ['id_merchandise', 'merchandiseName', 'sales_status', 'cost', 'price']) {
                        const cell = document.createElement('td');
                        if (key === 'cost' || key === 'price') {
                            cell.textContent = `${product[key]}원`;
                        } else if (key === 'sales_status') {
                            cell.textContent = product[key] ? 'Y' : 'N';
                        } else {
                            cell.textContent = product[key];
                        }
                        row.appendChild(cell);
                    }

                    // 행을 tbody에 추가
                    tbody.appendChild(row);
                }
            }
            isCheckboxGroupExecuted = true;
        })
        .catch(error => {
            // 오류 발생 시 처리
            console.error('Error getting products:', error);
        });
}
