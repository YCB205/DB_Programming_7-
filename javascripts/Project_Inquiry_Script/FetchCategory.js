let name;
let category;
//name = null category = all일때
const productUrl = `/products?products=${name}&category=${category}`;

let testUrl = 'https://dummyjson.com/products/categories';


window.onload =function (){
    fetchDataOrder();
    fetchDataSearch();
}
function fetchDataOrder(){
    name = null;
    category = 'all';
    console.log('함수가 실행 되었습니다.');
    fetch(testUrl)
        .then(response => response.json())
        .then(data=>{
            checkList(data);
        })
}

function checkList(data){
    let div_first = document.getElementById('checkbox-div-first');
    let div_second = document.getElementById('checkbox-div-second');
    let div = div_first;
    data.forEach(function (value,index){
        if(index === Math.floor(data.length / 2)){
            div = div_second;
        }
        const label = document.createElement('label');
        const input = document.createElement('input');

        input.setAttribute('class','form-check-input');
        input.setAttribute('type','checkbox');
        input.setAttribute('id',`${value.toString()}`);
        input.setAttribute('value',`${value.toString()}`);

        label.appendChild(input);
        label.setAttribute('class','text-truncate');
        label.appendChild(document.createTextNode(`${value.toString()}`));
        div.appendChild(label);
        //input값이 바뀔 때마 eventListener등록
        input.addEventListener('change',function (){
            whatChoose(input);
        });
    })
}

//상품별 검색 옵션
function fetchDataSearch(){
    //요소 가져오는 방법
    const button_addon2 = document.getElementById('button-addon2');
    button_addon2.addEventListener("click",eventFetchBtn2);
}

function eventFetchBtn2(){
    const input_addon2 = document.getElementById('input-addon2');
    let products = input_addon2.value.toString();
    let searchProductUrl = `https://dummyjson.com/products/search?q=${products.toString()}`;

    console.log(searchProductUrl);
    fetch(searchProductUrl)
        .then(response => response.json())
        .then(data=>{
            console.log(data);
        })
}

//선택 된 값을 가져오는 방법
function whatChoose(input){
    let category = input.id.toString();
    let categoryId = document.getElementById(category);
    console.log(categoryId.checked);
    if(categoryId.checked)
        getProdcutByTag(categoryId);
    else
        destroyTable(categoryId);

}

//선택된 체크박스 테이블 fetch 불러오기
function getProdcutByTag(categoryId){
    const url = `https://dummyjson.com/products/category/${categoryId.id.toString()}`;
    fetch(url)
        .then(response => response.json())
        .then(data=>{
            addTableRow(data);
        })
        .catch(error=>{
            //예외 처리 함수
        });
}
function addTableRow(data){

    data.forEach(function (value,index){

    })
}
