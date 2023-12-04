let trlList;

window.onload = function () {
    //버튼에 이벤트리스너 등록하기
    const btn = document.getElementById('fetchData');
    btn.addEventListener('click',function () {
        getTime();
    })
    const startDate = document.getElementById('fromDate');
    const endDate = document.getElementById('toDate');
    //디폴트 시간 설정하기
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

//시간을 입력받아 현재 시간이 유효한지 확인하기
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


//input요소로 시간 받아오기
function getTime(){
    const startTime = document.getElementById('fromDate');
    const endTime = document.getElementById('toDate');
    getFetch(startTime.value, endTime.value);
}



//매출 정보 조히 차트 띄우기

//fetch함수 부르기
function getFetch(startdate, enddate){
    const product = "";
    const startDate = convertTimeType(startdate);
    const endDate = convertTimeType(enddate);
    const url = `/orderSheet?search_merchandise=${product}&startDateTime=${startDate}&endDateTime=${endDate}`;
    fetch(url)
        .then(response => response.json())
        .then(data=>{
            addTableRow(data);
            // drawLineChart();
        })
}

function addTableRow(data){
    console.log(data);
    let index = 0;
    const tbody = document.getElementById('tbody');
    data.sort((a,b) => new Date(a.orderTime) - new Date(b.orderTime));
    for(const ordersheet of data){

        labels.push(ordersheet.orderTime);
        salesData.push(ordersheet.sales);
        profitsData.push(ordersheet.profit);
        for(const product of ordersheet.merchandise){
            const tr = document.createElement('tr');
            index % 2 === 0 ? tr.setAttribute('class', 'table-right') : tr.setAttribute('class', 'table-success');
            const th = document.createElement('th');
            const td = document.createElement('td');
            const td2 = document.createElement('td');
            const td3 = document.createElement('td');
            const td4 = document.createElement('td');

            th.setAttribute('scope','row');
            if(index === 0){
                td.setAttribute('class','col-20');
                td2.setAttribute('class','col-20');
                td3.setAttribute('class','col-20');
                td4.setAttribute('class','col-20');
            }

            th.textContent = product.categori;
            td.textContent = product.id_merchandise;
            td2.textContent = product.merchandiseName;
            td3.textContent = ordersheet.profit;
            td4.textContent = ordersheet.sales;
            tr.appendChild(th);
            tr.appendChild(td);
            tr.appendChild(td2);
            tr.appendChild(td3);
            tr.appendChild(td4);
            tbody.appendChild(tr);
            index++;
        }

    }
    index = 0;
}
//라인 차트 그리기
const labels = [];
const salesData = [];
const profitsData = [];

//원형 차트 그리기
const doughnutLabel = [];
const doughnutData = [];

function drawLineChart() {
    const ctx = document.getElementById('div2').getContext('2d');
    const myLineChart = new Chart(ctx, {
        type: 'line',
        data: {
            labels: labels,
            datasets: [
                {
                    label: 'Sales',
                    data: salesData,
                    borderColor: 'rgba(75, 192, 192, 1)',
                    borderWidth: 2,
                    fill: false
                },
                {
                    label: 'Profit',
                    data: profitsData,
                    borderColor: 'rgba(255, 99, 132, 1)',
                    borderWidth: 2,
                    fill: false,
                    type: 'line' // 이 데이터셋에 대한 차트 유형을 설정 (라인 차트)
                }
            ]
        },
        options: {
            responsive: true,
            maintainAspectRatio: false,
            scales: {
                x: {
                    type: 'category',
                    position: 'bottom'
                },
                y: {
                    type: 'linear',
                    position: 'left'
                }
            }
        }
    });

}


// function drawDoughnut(){
//     const ctx = document.getElementById('div3').getContext('2d');
//     const myDoughnut = new Chart(ctx,{
//         type: 'doughnut',
//         data:{
//
//         }
//     })
// }

// function append(tr){
//
//     const chart = document.getElementById('chart');
//     chart.appendChild(tr);
//     console.log(chart);
// }

window.addEventListener('message',function (event){
    if(event.data === 'HTMLClosed'){
        const data = localStorage.getItem('table_info');
        const trList = JSON.parse(data);
        const chart = document.getElementById('chart');
        trList.productsList.forEach((value, index)=>{
            const tr = document.createElement('tr');
            const td1 = document.createElement('td');
            const td2 = document.createElement('td');
            td1.setAttribute('scope','row');
            td1.textContent = value.id.toString();
            td2.textContent = value.name.toString();
            tr.appendChild(td1);
            tr.appendChild(td2);
            chart.append(tr);
        })
    }
})