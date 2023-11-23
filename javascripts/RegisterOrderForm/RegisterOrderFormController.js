
//윈도우의 모든 리소스가 등록 된 후 실행하는 함수
window.onload = async function (){
    //category 아이디 값 가져오기
    const category =document.getElementById('category');
    await initCategory(category);
    //라디오 이벤트 리스너 및 상품별 테이블 띄우기
    await radioEventListner(category);
}
async function initCategory(category){
    //fetch함수 부르기
    //비동기 실행을 동기적으로 바꾸기
    const data = await categoryRadioFetch();
    data.forEach(function (item, index){
        //input과 label을 초기화 하기
        const input = document.createElement('input');
        const label = document.createElement('label');
        input.setAttribute('type','radio');
        input.setAttribute('class','btn-check');
        input.setAttribute('name','btnradio');
        input.setAttribute('id', `btnRadio${item.toString()}`);
        input.setAttribute('autocompleted','off');
        input.setAttribute('value',`${item.toString()}`);
        label.setAttribute('class','btn btn-outline-primary rounded-0 m-0');
        label.setAttribute('for',`btnRadio${item.toString()}`);
        label.textContent = `${item.toString()}`;
        //category에 넣기 id = category
        category.appendChild(input);
        category.appendChild(label);
    })
}

function categoryRadioFetch(){
    //url get을 요청함
    //카테고리별 값 가져오기
    const url = 'https://dummyjson.com/products/categories';
    return  fetch(url)
        .then(response => response.json())
        .then(data=>{
                return data;
        })
}

//이벤트 리스너 등록 동기 함수
async function radioEventListner(category){
    const categoryChildInput = category.getElementsByTagName('input');
    console.log(categoryChildInput.length);
    for (let i = 0; i < categoryChildInput.length; i++) {
        const inputElement = categoryChildInput[i];
        // 클릭 이벤트 리스너 등록
        inputElement.addEventListener('click', async function () {
            console.log(inputElement);
            const data = await categoryProductFetch(inputElement.value.toString());
            await addTableRow(data);
        });
    }
}

//라디오 버튼에서 클릭한 함수로 값을 fetch해서 가져오는 함수
    async function addTableRow(data){
    const tbodyId = document.getElementById('querySearchScript');
    while (tbodyId.firstChild) {
        tbodyId.removeChild(tbodyId.firstChild);
    }
    console.log(data);
    data.products.forEach(function (item,index){
        const newRow  = document.createElement('tr');
        (index%2 === 0) ? newRow.classList.add('table-light') : newRow.classList.add('table-success');
        newRow.classList.add('text-center');
        newRow.setAttribute('id',`${item.id.toString()}`);

        // 각 열에 해당하는 요소 생성
        const thFirst = document.createElement('th');
        const tdSecond = document.createElement('td');
        const tdThird = document.createElement('td');
        const tdFourth = document.createElement('td');

        // 각 열의 속성 및 텍스트 설정
        thFirst.setAttribute('scope', 'row');
        thFirst.className = 'col-3';
        thFirst.textContent = item.id.toString();

        tdSecond.className = 'col-3';
        tdSecond.textContent = item.title.toString();

        tdThird.className = 'col-3';
        tdThird.textContent = item.price;

        tdFourth.className = 'col-3';
        tdFourth.textContent = "null";

        // 행에 열 요소들 추가
        newRow.appendChild(thFirst);
        newRow.appendChild(tdSecond);
        newRow.appendChild(tdThird);
        newRow.appendChild(tdFourth);

        //newRow행클릭시 이벤트 리스너 등록;
        newRow.addEventListener('dblclick',function (){
            console.log(newRow.id.toString());
            dbClickEventListener(newRow);
        })

        // tbody에 행 추가
        tbodyId.appendChild(newRow);

    })
}
//상품별 데이터 패치하기
function categoryProductFetch(inputElement) {
    const url = `https://dummyjson.com/products/category/${inputElement.toString()}`;
    return fetch(url).then(response => response.json());
}

//테이블 더블 클릭시 이벤트 리스너 등록하기
function dbClickEventListener(newRow){
    //더블 클릭시 옆의 행 추가하는 메서드
    //table그리기
    const querySearchAfterScript = document.getElementById('querySearchAfterScript');
    const querySearchAfterScriptchild = querySearchAfterScript.getElementsByTagName('tr');
    //세번째 행값을 가져옴
    let tdThird1 = newRow.getElementsByTagName('td')[1];

    for(let i=0; i < querySearchAfterScriptchild.length; i++) {
        //이미 추가된 자식들 중에 값이 있다면
        const trId = querySearchAfterScriptchild[i].id.toString();
        console.log(trId);
        console.log(newRow.id.toString());
        if( trId === newRow.id.toString()){
            //자식 태그 중 input 요소 들고 오기 trId의 input값
            const trInput = querySearchAfterScriptchild[i].querySelector('input');
            //자식 태그에 1추가하기 그 다음 종료
            trInput.value++;
            return;
        }
    }
    //아니면 행을 더함
    const value = document.createElement('tr');
    value.className = 'text-center';
    value.setAttribute('id',`${newRow.id.toString()}`);
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
    thFirst.textContent = newRow.id.toString();

    tdSecond.classList.add("p-0", 'col-3', "text-center");

    label.setAttribute("for",`${newRow.id.toString()}`);
    label.setAttribute("style","display:none");

    input.setAttribute("type","number");
    input.setAttribute("value","1");
    input.setAttribute("id",`${newRow.id.toString()}`);
    input.setAttribute("min","1");
    input.classList.add("form-control", "text-center", "p-0");

    tdSecond.appendChild(label);
    tdSecond.appendChild(input);

    //상품 수량 입력
    tdThird.className = 'col-3';
    tdThird.textContent = tdThird1.textContent;

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
    querySearchAfterScript.appendChild(value);
    console.log(newRow.id.toString());

}

