let name;
let category;
let worldIndex = 0;
window.onload = function () {
    //모든 데이터 가져오기
    fetchAllProduct();
    fetchDataSearch();
}


function checkList(data) {
    let div = document.getElementById('checkbox-div-first');

    const label = document.createElement('label');
    const input = document.createElement('input');

    input.setAttribute('class', 'form-check-input');
    input.setAttribute('type', 'checkbox');
    input.setAttribute('value', `${data.toString()}`);

    label.appendChild(input);
    label.setAttribute('class', 'text-truncate');
    label.appendChild(document.createTextNode(`${data.toString()}`));
    div.appendChild(label);
}


//상품별 검색 옵션
function fetchDataSearch() {
    //요소 가져오는 방법
    console.log('페치 실행');
    const button_addon2 = document.getElementById('button-addon2');
    button_addon2.addEventListener("click", eventFetchBtn2);
}

function eventFetchBtn2() {
    const table = document.getElementById('category');
    destroyTable(table);
    const input_addon2 = document.getElementById('input-addon2').value;
    let categoryCheck = document.getElementById('checkbox-div-first').querySelectorAll('input');
    let categoryList =[];
    categoryCheck.forEach((checkBox)=>{
        if(checkBox.checked){
            let value = checkBox.value;
            categoryList.push(value);

        }
    });
    console.log(input_addon2);
    console.log(categoryList);
    let searchProductUrl = `/orderproducts?product_name=${input_addon2}&category=${categoryList}`;

    console.log(searchProductUrl);
    fetch(searchProductUrl)
        .then(response => response.json())
        .then(data => {
            for(const key in data) {
                addTableRow(data[key],key, 'category');
                console.log(data);
            }
        })
}


//선택된 체크박스 테이블 fetch 불러오기
function getProdcutByTag(categoryId) {
    let productName = '';
    const url = `/orderproducts?product_name=${productName}&category=${categoryId.id.toString()}`;
    fetch(url)
        .then(response => response.json())
        .then(data => {
            for (const key in data) {
                console.log(data[key]);
                console.log(data);
                addTableRow(data[key], key, 'category');
            }
            worldIndex = 0;
        })
        .catch(error => {
            //예외 처리 함수
            console.log(error);
        });
}


function addTableRow(data, key/*카테고리가 들어감*/, category) {
    console.log(category.toString());
    console.log(data);
    //테이블 리스트에서
    let tableList = document.getElementById('tableList');
    //table 아이디 찾기
    let table = tableList.querySelector(`#${category.toString()}`);
    console.log(table);
    data.forEach(function (value, index) {
        let tr = document.createElement('tr');
        let th = document.createElement('th');
        th.setAttribute('scope', 'row');
        const tdList = [
            document.createElement('td'),
            document.createElement('td'),
            document.createElement('td'),
            document.createElement('td')
        ];

        console.log(worldIndex);
        //첫번째 세팅
        if (worldIndex === 0) {
            console.log(index);
            th.setAttribute('class', 'col-20');
            for (let i = 0; i < tdList.length; i++)
                tdList[i].setAttribute('class', 'col-20');
        }
        worldIndex % 2 === 0 ? tr.setAttribute('class', 'table-right') : tr.setAttribute('class', 'table-success');
        th.textContent = `${key.toString()}`;
        tdList[0].textContent = `${value.id_merchandise.toString()}`;
        tdList[1].textContent = `${value.merchandiseName.toString()}`;
        tdList[2].textContent = `${value.price.toString()}`;
        tdList[3].textContent = `${value.cost}`;
        tr.appendChild(th);
        for (let i = 0; i < tdList.length; i++)
            tr.appendChild(tdList[i]);
        table.appendChild(tr);
        console.log(tr);
        worldIndex++;
    })


}

//테이블을 삭제하는 메서드
function destroyTable(categoryId) {
    console.log(categoryId.id.toString());
    const tableList = document.getElementById('tableList');
    const table = tableList.querySelector(`#${categoryId.id.toString()}`);
    console.log(table);
    if (table) {
        while (table.firstChild) {
            table.removeChild(table.firstChild);
        }
    }
}

//전체 상품 혹은 부분 상품 보여주기
function fetchAllProduct() {
    const productname = "";
    const category = [];
    const url = `/orderproducts?product_name=${productname}&category=${category}`;
    fetch(url)
        .then(response => response.json())
        .then(data => {
            for (const key in data) {
                console.log(data[key]);
                console.log(data);
                addTableRow(data[key], key, 'productAll');
                checkList(key);
            }
        })
        .catch(error => {
            console.log(error);
        })
}


