let trlList;

window.onload = function () {
    getOrderManager();
}

//매출 정보 조히 차트 띄우기
function getOrderManager(){
    getFetch();
}

//fetch함수 부르기
function getFetch(){
    const search_merchandise = document.getElementById('showPopup');
    const product = "";
    const startDate = '2011-10-11 00:00:00';
    const endDate = '2024-12-30 00:00:00';
    const url = `/orderSheet?search_merchandise=${product}&startDateTime=${startDate}&endDateTime=${endDate}`;
    fetch(url)
        .then(response => response.json())
        .then(data=>{
            addTableRow(data);
            drawLineChart();
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
            console.log(product);
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
//테이블을 그리기
const labels = [];
const salesData = [];
const profitsData = [];
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

export function getTrList(getTr){
    trlList = getTr;
    const chart = document.getElementById('chart');
    for()
}