//아이디 맵
const inputIdMap = new Map();

//아이디(key) 가격과 개수(value) 매핑
const idPriceMap = new Map();


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
    //먼저 품목별 카테고리 radio버튼을 만든다
    createRadioBtnCategory();
    const btnAddProduct = document.getElementById("registerPostBtn");

    btnAddProduct.addEventListener('click',function (){
        let id = "id";
        let productArray = [];
        let productJson = {
            "jumjuID" : id,
            "productsOrderForm" : productArray
        };
        for(const [key,value]of idPriceMap)
        {
            let arrayList={
                "productID": key.toString(),
                "count": value[1]
            }
            productArray.push(arrayList);
        }
        console.log(productJson);

        const url = `https://dummyjson.com/posts/add`;
        fetch(url,{
            method:"POST",
            headers:{
                "Content-Type":"application/json"
            },
            body:JSON.stringify(productJson)
        })
            .then(res=>res.json())
            .then(console.log);
        idPriceMap.clear();
        inputIdMap.clear();
        const querySearchAfterScript = document.getElementById('querySearchAfterScript');
        querySearchAfterScript.innerHTML = ''
    });

    //검색 버튼
    const btn_add2 = document.getElementById('button_add2');
    btn_add2.addEventListener("click", function () {
       //상품별 검색 옵션 넣을 예정
    });
});



// 내용을 채워 넣는 함수
function addTableRow(index, id, title, price) {
    const querySearchScript = document.getElementById('querySearchScript');
    // 홀수와 짝수 색 구분
    const newRow = document.createElement('tr');
    newRow.className = index % 2 === 1 ? 'table-light' : 'table-success';
    newRow.classList.add('text-center');
    const thFirst = document.createElement('th');
    const tdSecond = document.createElement('td');
    const tdThird = document.createElement('td');
    const tdForth = document.createElement('td');
    thFirst.setAttribute("scope","row");
    thFirst.className = 'col-3';
    thFirst.textContent = id.toString();
    tdSecond.className = 'col-3';
    tdSecond.textContent = title.toString();
    tdThird.className = 'col-3';
    tdThird.textContent = price.toString();

    newRow.appendChild(thFirst);
    newRow.appendChild(tdSecond);
    newRow.appendChild(tdThird);
    newRow.appendChild(tdForth);

    const querySearchAfterScript = document.getElementById('querySearchAfterScript');

    // 클릭 이벤트 리스너를 등록...
    newRow.addEventListener('dblclick', function () {
        try {
            alert('상품추가');
            const value = document.createElement('tr');
            value.className = 'text-center';
            const thFirst = document.createElement('th');
            const tdSecond = document.createElement('td');
            const tdThird = document.createElement('td');
            const tdForth = document.createElement('td');
            const label = document.createElement('label');
            const input = document.createElement('input');
            const i = document.createElement('i');
            const img = document.createElement('img');

            thFirst.setAttribute("scope","row");
            thFirst.classList.add("fw-normal","col-4");
            thFirst.textContent = title.toString();

            tdSecond.classList.add("m-0", 'col-3', "text-center");

            label.setAttribute("for",id.toString());
            label.setAttribute("style","display:none");

            input.setAttribute("type","number");
            input.setAttribute("value","1");
            input.setAttribute("id",id.toString());
            input.setAttribute("min","1");
            input.classList.add("form-control", "text-center", "p-0");

            tdSecond.appendChild(label);
            tdSecond.appendChild(input);

            tdThird.className = 'col-3';
            tdThird.textContent = price.toString();

            tdForth.className = "col-2";

            i.classList.add("p-0","btn","btn-primary","w-100");
            img.setAttribute("alt","");
            img.setAttribute("src","../../image/svg/x.svg");

            i.appendChild(img);

            tdForth.appendChild(i);

            value.appendChild(thFirst);
            value.appendChild(tdSecond);
            value.appendChild(tdThird);
            value.appendChild(tdForth);

            checkDuplicatedId(input.id.toString());
            // 값 등록
            addMap(id.toString(), price, 1);
            calSum();
            input.addEventListener('change', function () {
                const count = idPriceMap.get(input.id.toString());
                count[1] = Number(this.value);
                calSum();
            });

            i.addEventListener('click',function (){
                value.remove();
                idPriceMap.delete(input.id.toString());
                inputIdMap.delete(input.id.toString());
                calSum();
            })

            querySearchAfterScript.appendChild(value);
        } catch (e) {
            alert(e.name + '\n' + e.message);

        }
    });

    querySearchScript.appendChild(newRow);
}


// 추가하는 함수
function addMap(id, price, count) {
    idPriceMap.set(id, [price, count]);
}

// 총합을 구하는 함수
function calSum() {

    let sum = 0;
    const priceSum = document.getElementById('priceSum');
    idPriceMap.forEach((value, key) => {
        sum += Number(value[0]) * Number(value[1]);
        // 출력해주는 함수(콘솔창 띄우기)
        console.log(sum);
    });
    priceSum.textContent = sum;
}
function createRadioBtnCategory(){
    const url ="https://dummyjson.com/products/categories";
    fetch(url)
        .then(response => response.json())
        .then(data=>{
            console.log(data);
            //페치된 데이터 보내기
            creatRadioBtn(data);
        })
}

//라디오 버튼을 넣는 함수
function creatRadioBtn(categoryOption){
    //카테고리 품목 라디오 버튼 넣기
    const category = document.getElementById('category');

    categoryOption.forEach(function (item,index) {
        const input = document.createElement('input');
        const label = document.createElement('label');
        //품목 라디오 input, label넣기
        input.setAttribute('type','radio');
        input.setAttribute('name','btnradio');
        input.setAttribute('id',`btnRadio${item.toString()}`); //아이디 지정 방식 btnRadio + 상품명
        input.setAttribute('autocomplete','off');
        input.setAttribute('value',`${item.toString()}`);
        input.className = 'btn-check';
        //input에 이벤트 리스너 등록하기
        //품목 클릭후 품목별 검색에서 테이블 가져오기
        //품목 클릭시 테이블 그리기
        input.addEventListener('change',funcCategoryFetch(input.value.toString()));

        label.classList.add('btn', 'btn-outline-primary', 'rounded-0','m-0');
        label.setAttribute('for',`btnRadio${item.toString()}`);
        label.textContent = `${item.toString()}`;
        category.appendChild(input);
        category.appendChild(label);
    })
}


function funcCategoryFetch(value){
    const url = `https://dummyjson.com/products/category/${value}`;
    const querySearchScript = document.getElementById('querySearchScript');
    while (querySearchScript.firstChild) {
        querySearchScript.removeChild(querySearchScript.firstChild);
    }
    // 생성된 url
    fetch(url)
        .then(response => response.json())
        .then(data => {
            if (Array.isArray(data.products)) {
                // product가 배열인 경우
                data.products.forEach(function (item, index) {
                    addTableRow(index + 1, item.id, item.title, item.price);
                });
            } else {

            }
        })
        .catch(error => console.error('Error', error));
}