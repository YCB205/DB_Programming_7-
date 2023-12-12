window.onload =function () {
    console.log('세컨드 창이 열렸습니다.');
    //테이블 초기화하기
    initTable();
    const btn = document.getElementById('registerBtn');
    console.log(btn);
    btn.addEventListener('click',function (){
        const trList = registerBtn();
        localStorage.removeItem('table_info');
        let productsList = [];
        const productInfo = {
            productsList
        }
        for(let i = 0;i < trList.length; i++){
            let product = {
                "id_merchandise" : trList[i].children[0].textContent,
                "merchandiseName": trList[i].children[1].textContent
            }
            productsList.push(product);
        }
        localStorage.setItem("table_info", JSON.stringify(productInfo));
        console.log(productInfo);
        notifyOtherHtml();
        window.close();
    });
    const input  = document.getElementById('inputProduct');
    input.addEventListener('mouseover',function (){
        input.addEventListener('keydown',function (event){
            if(event.key === 'Enter'){
                fetchProduct(input.value);
            }
        })
    });
}

function fetchProduct(input){
    let category = [];
    console.log(input.toString());
    const url = `/orderproducts?product_name=${input}&category=${category}`;
    fetch(url)
        .then(response => response.json())
        .then(data=>{
            console.log(data);
            addTableRow(data);
        })
}

function addTableRow(data){
    const second_tbody = document.getElementById('second_tbody');
    destroyTable(second_tbody);
    console.log(data);
    for(const category in data){
        console.log(category);
        data[category].forEach(function (item, index){

            const tr = document.createElement('tr');
            const td = document.createElement('td');
            const td1 = document.createElement('td');
            td.textContent = item.id_merchandise;
            td1.textContent = item.merchandiseName;

            tr.appendChild(td);
            tr.appendChild(td1);
            tr.setAttribute('id',item.id_merchandise);

            tr.addEventListener('dblclick', function () {
                const tbodyfirst = document.getElementById('tbodyfirst');
                const trList = tbodyfirst.querySelectorAll('tr');
                for(let i = 0; i < trList.length; i++){
                    console.log(trList[i].id.toString());
                    console.log(tr.id.toString());
                    if(tr.id.toString() === trList[i].id.toString()){
                        return;
                    }
                }
                chartTable(tr);

            })

            second_tbody.appendChild(tr);
        })
    }
}

function destroyTable(tbody) {
    if (tbody) {
        while (tbody.firstChild) {
            tbody.removeChild(tbody.firstChild);
        }
    }
}

function chartTable(tr) {
    const tbodyfirst = document.getElementById('tbodyfirst');
    const clonetr = tr.cloneNode(true);
    clonetr.setAttribute('id',tr.id.toString());
    tbodyfirst.appendChild(clonetr);

    clonetr.addEventListener('dblclick', function () {
        clonetr.remove();
    })
    console.log('테이블 행을 추가!');
}

function registerBtn(){
    const tbodyfirst = document.getElementById('tbodyfirst');
    const trList = tbodyfirst.querySelectorAll('tr');
    return trList;
}

//이벤트 리스너 등록
function notifyOtherHtml(){
    window.opener.postMessage('HTMLClosed',window.location.origin + '/html/store_manager/Sales%20information%20inquiry.html');
    window.opener.postMessage('HTMLClosed',window.location.origin + '/html/store_manager/Sales%20information%20inquiry2.html');
}   console.log(window.location.origin);

//테이블 초기화히기
function initTable(){
    const data = localStorage.getItem('table_info');
    const trList = JSON.parse(data);
    if(trList===null)
        return;
    addTableRow(trList);
    const second_tbody = document.getElementById('second_tbody');
    let doubleClickEvent = new MouseEvent("dblclick");
    const trNodelist = second_tbody.querySelectorAll('tr');
    for(let i =0; i < trNodelist.length;i++){
        trNodelist[i].dispatchEvent(doubleClickEvent);
    }
    destroyTable(second_tbody);
}