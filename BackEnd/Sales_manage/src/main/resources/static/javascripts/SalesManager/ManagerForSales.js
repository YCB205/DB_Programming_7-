// 서버로 데이터 요청
window.onload = function () {
    getData();
    console.log('창이 열렸습니다.');
    const btn = document.getElementById('fetchData');
    btn.addEventListener('click',function () {
        //검색실행
        getTime();
    })
    const startDate = document.getElementById('fromDate');
    const endDate = document.getElementById('toDate');
    const currentDate = new Date();
    currentDate.setHours(0,0,0,0);
    const setDefault = convertTimeType(currentDate);
    startDate.value = setDefault;
    endDate.value = setDefault;


    startDate.addEventListener('change',function (){
        checkTimeValid();
    })
    endDate.addEventListener('change',function () {
        checkTimeValid();
    })

}


function getData() {
        let officeName = [];
        let name = '';
        fetch(`/branch-storeManagers?officeName=${officeName}&name=${name}`, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
                // You may include other headers if needed
            },
        })
            .then(response => response.json())
            .then(data => {
                // 성공적으로 처리된 경우의 동작
                console.log('Data successfully received:', data);
                addBtnRadio(data);
            })
            .catch(error => {
                // 오류 발생 시 처리
                console.error('Error getting products:', error);
            });
    }




function addBtnRadio(data){
    let btngroup = document.getElementById('btngroup');
    // <input type="checkbox" className="btn-check" name="btncheck" id="btncheck1" autoComplete="off">-->
    //     <!--                    <label class="btn btn-outline-primary rounded-0 m-0" for="btncheck1">공주대 천안점</label>-->
    for(let i = 0; i< data.length;i++) {
            let input = document.createElement('input');
            input.setAttribute('type', 'checkbox');
            input.setAttribute('class', 'btn-check');
            input.setAttribute('name', 'btncheck');
            input.setAttribute('id', `${data[i][0].idStoreManager}`);
            input.setAttribute('autoComplete', 'off');
            input.setAttribute('value', `${data[i][0].idStoreManager}`);
            let label = document.createElement('label');
            label.classList.add('btn', 'btn-outline-primary', 'rounded-0', 'm-0');
            label.setAttribute('for', `${data[i][0].idStoreManager}`);
            label.textContent = `${data[i][0].officeName}`;
            btngroup.appendChild(input);
            btngroup.appendChild(label);
        }

}

function checkTimeValid(){
    const fromDate = document.getElementById('fromDate');
    const toDate = document.getElementById('toDate');
    let curruentTime = new Date();
    let startValue = new Date(fromDate.value);
    let endValue = new Date(toDate.value);
    if(startValue > endValue){
        fromDate.value = toDate.value;
    }
    else if(endValue > curruentTime){
        toDate.value =  convertTimeType(curruentTime);
    }
}

function getTime(){
    const startTime = document.getElementById('fromDate');
    const endTime = document.getElementById('toDate');
    getFetch(startTime.value, endTime.value);
}


//fetch부르기
function getFetch(startdate, enddate){
    const product =[];
    const company =['1001'];
    const startDate = convertTimeType(startdate);
    const endDate = convertTimeType(enddate);
    const getRadio = document.getElementById('optionsRadios2');
    const getRadio2 = document.getElementById('optionsRadios1');
    if(getRadio.checked){
        const getLocal = localStorage.getItem('table_info');
        const data = JSON.parse(getLocal);
        data.productsList.forEach((value, index)=>{
            product.push(value.merchandiseName.toString());
        })
    }
    const url = `/allOrderproducts?search_merchandise=${product}&startDateTime=`+startDate +"&endDateTime="+endDate+`&brandoffice_id=${company}`;
    const url2 = `/chartOrderproducts?search_merchandise=${product}&startDateTime=`+startDate +"&endDateTime="+endDate+`&brandoffice_id=${company}`;
    fetch(url)
        .then(response => response.json())
        .then(data=>{
            addTableRow(data);
        })
    fetch(url2)
        .then(response=>response.json())
        .then(data=>{
            console.log(data);
        })
}

function convertTimeType(inputDateTime) {
    const date = new Date(inputDateTime);

    const year = date.getFullYear();
    const month = String(date.getMonth() + 1).padStart(2, '0');
    const day = String(date.getDate()).padStart(2, '0');
    const hours = String(date.getHours()).padStart(2, '0');
    const minutes = String(date.getMinutes()).padStart(2, '0');
    const seconds = String(date.getSeconds()).padStart(2, '0');

    const formattedDate = `${year}-${month}-${day}`;
    const formattedTime = `${hours}:${minutes}:${seconds}`;
    console.log((`${formattedDate} ${formattedTime}`));
    return ((`${formattedDate} ${formattedTime}`));
}