let name;
let category;
//name = null category = all일때
const productUrl = `/products?products=${name}&category=${category}`;

let testUrl = '/products';


window.onload = function () {
    fetchDataOrder();
    fetchDataSearch();
    //모든 데이터 가져오기
    fetchAllProduct();
}

function fetchDataOrder() {
    name = null;
    category = 'all';
    console.log('함수가 실행 되었습니다.');
    fetch(testUrl)
        .then(response => response.json())
        .then(data => {
            console.log(data);
            checkList(data);
        })
}

function checkList(data) {
    let div_first = document.getElementById('checkbox-div-first');
    let div_second = document.getElementById('checkbox-div-second');
    let tableList = document.getElementById('tableList');
    let div = div_first;
    data.forEach(function (value, index) {
        if (index === Math.floor(data.length / 2)) {
            div = div_second;
        }
        //tableList 자식추가
        let table = document.createElement('tbody');
        table.setAttribute('id',`${value.toString()}`);
        tableList.appendChild(table);

        const label = document.createElement('label');
        const input = document.createElement('input');

        input.setAttribute('class', 'form-check-input');
        input.setAttribute('type', 'checkbox');
        input.setAttribute('id', `${value.toString()}`);
        input.setAttribute('value', `${value.toString()}`);

        label.appendChild(input);
        label.setAttribute('class', 'text-truncate');
        label.appendChild(document.createTextNode(`${value.toString()}`));
        div.appendChild(label);
        //input값이 바뀔 때마 eventListener등록
        input.addEventListener('change', function () {
            whatChoose(input);
        });
    })
}

//상품별 검색 옵션
function fetchDataSearch() {
    //요소 가져오는 방법
    const button_addon2 = document.getElementById('button-addon2');
    button_addon2.addEventListener("click", eventFetchBtn2);
}

function eventFetchBtn2() {
    const input_addon2 = document.getElementById('input-addon2');
    let products = input_addon2.value.toString();
    let searchProductUrl = `https://dummyjson.com/products/search?q=${products.toString()}`;

    console.log(searchProductUrl);
    fetch(searchProductUrl)
        .then(response => response.json())
        .then(data => {
            const table = document.getElementById('productName');
            destroyTable(table);
            addTableRow(data,'productName');
            unlockCheckList();
            console.log(data);
        })
}

//선택 된 값을 가져오는 방법
function whatChoose(input) {
    console.log(input.checked);
    if (input.checked)
        getProdcutByTag(input);
    else
        destroyTable(input);

}

//선택된 체크박스 테이블 fetch 불러오기
function getProdcutByTag(categoryId) {
    const url = `https://dummyjson.com/products/category/${categoryId.id.toString()}`;
    fetch(url)
        .then(response => response.json())
        .then(data => {
            console.log(data);
            addTableRow(data, data.products[0].category.toString());
        })
        .catch(error => {
            //예외 처리 함수
            console.log(error);
        });
}

function addTableRow(data, category) {
    console.log(category.toString());
    console.log(data);
    //테이블 리스트에서
    let tableList = document.getElementById('tableList');
    //table 아이디 찾기
    let table = tableList.querySelector(`#${category.toString()}`);
    data.products.forEach(function (value, index) {
        let tr = document.createElement('tr');
        let th = document.createElement('th');
        th.setAttribute('scope', 'row');
        const tdList = [
            document.createElement('td'),
            document.createElement('td'),
            document.createElement('td'),
            document.createElement('td')
        ];

        console.log(index);
        //첫번째 세팅
        if (index === 0) {
            console.log(index);
            th.setAttribute('class', 'col-20');
            for (let i = 0; i < tdList.length; i++)
                tdList[i].setAttribute('class', 'col-20');
        }
        index % 2 === 0 ? tr.setAttribute('class', 'table-right') : tr.setAttribute('class', 'table-success');
        th.textContent = `${value.category.toString()}`;
        tdList[0].textContent = `${value.id.toString()}`;
        tdList[1].textContent = `${value.title.toString()}`;
        tdList[2].textContent = `${value.price.toString()}`;

        tr.appendChild(th);
        for (let i = 0; i < tdList.length; i++)
            tr.appendChild(tdList[i]);
        table.appendChild(tr);
        console.log(tr);
    })

}

//테이블을 삭제하는 메서드
function destroyTable(categoryId){
    console.log(categoryId.id.toString());
    const tableList = document.getElementById('tableList');
    const table = tableList.querySelector(`#${categoryId.id}`);
    console.log(table);
    if(table){
        while (table.firstChild) {
            table.removeChild(table.firstChild);
        }
    }
}

//전체 상품 혹은 부분 상품 보여주기
function fetchAllProduct(){
    const url = 'https://dummyjson.com/products';
    fetch(url)
        .then(response=>response.json())
        .then(data=>{
            console.log(data);
            addTableRow(data,'productAll');
        })
        .catch(error=>{
            console.log(error);
        })
}

function unlockCheckList(){
    const showAll = document.querySelector('#showAll');
    const showCheckbox = document.querySelector('#showCheckbox');
    showAll.checked = false;
    showCheckbox.checked = false;
    toggleCheckboxVisibility();
}

