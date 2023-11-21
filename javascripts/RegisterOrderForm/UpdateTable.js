const inputIdMap = new Map();
class DuplicatedIdError extends Error {
    constructor(message) {
        super(message);
        this.name = "상품중복 추가 error!!";
    }
}

// 중복 확인 호출 함수
function checkDuplicatedId(id) {
    console.log(id);
    if(inputIdMap.has(id)){
        throw new DuplicatedIdError(`중복된 상품의 아이디: ${id}`);
    }
    else
        inputIdMap.set(id,true);
}

document.addEventListener('DOMContentLoaded', function () {
    // 모든 리소스가 로드 된 후에 실행되는 함수
    const radioButtons = document.querySelectorAll('input[name="btnradio"]');

    radioButtons.forEach(function (radioButton) {
        radioButton.addEventListener('change', function () {
            const category = this.value;
            const querySearchScript = document.getElementById('querySearchScript');
            querySearchScript.innerHTML = '';

            const url = `https://dummyjson.com/products/category/${category}`;

            // 생성된 url로 ajax 요청 보내기
            fetch(url)
                .then(response => response.json())
                .then(data => {
                    if (Array.isArray(data.products)) {
                        // product가 배열인 경우
                        data.products.forEach(function (item, index) {
                            addTableRow(index + 1, item.id, item.title, item.price);
                        });
                    } else {
                        // data가 배열이 아니라면 전체 data를 로그로 출력
                    }
                })
                .catch(error => console.error('Error', error));
        });
    });
});

// 내용을 채워 넣는 함수
function addTableRow(index, id, title, price) {
    const querySearchScript = document.getElementById('querySearchScript');
    // 홀수와 짝수 색 구분
    const newRow = document.createElement('tr');
    newRow.className = index % 2 === 1 ? 'table-light' : 'table-success';
    newRow.classList.add('text-center');
    newRow.innerHTML = `
        <th scope="row">${id.toString()}</th>
        <td class="text-center p-0">${title}</td>
        <td class="text-center p-0">${price}</td>
    `;

    const querySearchAfterScript = document.getElementById('querySearchAfterScript');
    // 클릭 이벤트 리스너를 등록...
    newRow.addEventListener('dblclick', function () {
        try {
            alert('상품추가');

            const value = document.createElement('tr');
            value.className = 'text-center';
            value.innerHTML = `
                <th scope="row" class="fw-normal">${title}</th>
                <td class="m-0">
                    <label for="${id.toString()}" style="display: none"></label>
                    <input type="number" class="form-control text-center p-0" value="1" id="${id.toString()}" min="1">
                </td>
                <td>${price}</td>
                <td>@mdo</td>
            `;
            const valueLabel = value.querySelector('input');
            checkDuplicatedId(valueLabel.id.toString());
            // 값 등록
            addMap(id.toString(), price, 1);
            calSum();
            valueLabel.addEventListener('change', function () {
                const count = idPriceMap.get(valueLabel.id.toString());
                count[1] = Number(this.value);
                calSum();
            });

            querySearchAfterScript.appendChild(value);
        } catch (e) {
            alert(e.name + '\n' + e.message);

        }
    });

    querySearchScript.appendChild(newRow);
}

const idPriceMap = new Map();

// 추가하는 함수
function addMap(id, price, count) {
    idPriceMap.set(id, [price, count]);
}

// 총합을 구하는 함수
function calSum() {
    let sum = 0;
    idPriceMap.forEach((value, key) => {
        sum += Number(value[0]) * Number(value[1]);
        // 출력해주는 함수(콘솔창 띄우기)
        console.log(sum);
        const priceSum = document.getElementById('priceSum');
        priceSum.innerText = sum;
    });
}
