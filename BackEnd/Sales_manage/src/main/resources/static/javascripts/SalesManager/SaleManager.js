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
    //라디오 선택 될 때 마다 값 바꿔주기
    showChart();
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
    const product =[];
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
    const url = `/allOrderproducts?search_merchandise=${product}&startDateTime=`+startDate +"&endDateTime="+endDate+"&brandoffice_id="+"";
    const url2 = `/chartOrderproducts?search_merchandise=${product}&startDateTime=`+startDate +"&endDateTime="+endDate+"&brandoffice_id="+"";
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
//바 차트를 위한 맵
let myMap = new Map();

//라인 차트 그리기
let labels = [];
let salesData = [];
let profitsData = [];
function addTableRow(data){
    let index = 0;
    const tbody = document.getElementById('tbody');
    myMap.clear();
    labels.length = 0;
    salesData.length = 0;
    profitsData.length = 0;
    console.log(data);
    //자식요소 삭제하기
    while (tbody.firstChild) {
        console.log("삭제합니다..");
        tbody.removeChild(tbody.firstChild);
    }
    for(let index = 0; index < data.length - 1; index++){
            console.log(index);
            const ordersheet = data[index];
            const tr = document.createElement('tr');
            index % 2 === 0 ? tr.setAttribute('class', 'table-right') : tr.setAttribute('class', 'table-success');
            const th = document.createElement('th');
            const td = document.createElement('td');
            const td2 = document.createElement('td');
            const td3 = document.createElement('td');
            const td4 = document.createElement('td');
            const td5 = document.createElement('td');

            th.setAttribute('scope','row');
            if(index === 0){
                td.setAttribute('class','col-2');
                td2.setAttribute('class','col-2');
                td3.setAttribute('class','col-2');
                td4.setAttribute('class','col-2');
                td5.setAttribute('class','col-2');
            }

            th.textContent = ordersheet[0];
            td.textContent = ordersheet[1];
            td2.textContent = ordersheet[2];
            td3.textContent = ordersheet[4];
            td4.textContent = ordersheet[5];
            td5.textContent = ordersheet[3];
            tr.appendChild(th);
            tr.appendChild(td);
            tr.appendChild(td2);
            tr.appendChild(td3);
            tr.appendChild(td4);
            tr.appendChild(td5);
            console.log(tr);
            tbody.appendChild(tr);
            //맵을 생성
            const getRadio = document.getElementById('optionsRadios2');
            const getRadio2 = document.getElementById('optionsRadios1');
            if(getRadio2.checked) {
                if (myMap.has(ordersheet[0])) {
                    let netProfit = myMap.get(ordersheet[0])[0];
                    let totalsales = myMap.get(ordersheet[0])[1];
                    let list = [];
                    list.push(netProfit + ordersheet[4]);
                    list.push(totalsales + ordersheet[5]);
                    myMap.set(ordersheet[0], list);
                } else {
                    let list = [];
                    list.push(ordersheet[4]);
                    list.push(ordersheet[5]);
                    myMap.set(ordersheet[0], list);
                }
            }
            else{
                if (myMap.has(ordersheet[2])) {
                    let netProfit = myMap.get(ordersheet[1])[0];
                    let totalsales = myMap.get(ordersheet[1])[1];
                    let list = [];
                    list.push(netProfit + ordersheet[4]);
                    list.push(totalsales + ordersheet[5]);
                    myMap.set(ordersheet[2], list);
                } else {
                    let list = [];
                    list.push(ordersheet[4]);
                    list.push(ordersheet[5]);
                    myMap.set(ordersheet[2], list);
                }
            }
    }
    let total = data[data.length-1];

    myMap.set(total[0],[total[1],total[2]]);
    drawBarChart();
    drawDoughnut();
    drawDoughnut2();
    const showChart = document.getElementById('showChart');
    showChart.style.display = 'none';
    index = 0;
}


//차트 변수들
let myBarChart;
let myDoughnut;
let myDoughnut2;
function drawBarChart() {
    const canvas = document.getElementById('div2');
    if (myBarChart !== undefined) {
        myBarChart.destroy();
    }
    canvas.style.width = '100%';
    myMap.forEach((value,key)=>{
        labels.push(key);
        salesData.push(value[0]);
        profitsData.push(value[1]);
    })

     myBarChart = new Chart(canvas, {
        type: 'bar',
        data: {
            labels: labels,
            datasets: [
                {
                    label: '영업이익',
                    data: salesData,
                    borderColor: 'rgba(75, 192, 192, 1)',
                    borderWidth: 2,
                    fill: true
                },
                {
                    label: '매출액',
                    data: profitsData,
                    borderColor: 'rgba(255, 99, 132, 1)',
                    borderWidth: 2,
                    fill: true
                }
            ]
        },
        options: {
            responsive: true,
            plugins: {
                legend: {
                    position: 'top',
                },
                title: {
                    display: true,
                    text: ''
                }
            }
        }
    });

}


function drawDoughnut() {
    const canvas = document.getElementById('div3');
    if (myDoughnut !== undefined) {
        myDoughnut.destroy();
    }

    const totalSalesIndex = labels.indexOf('총매출');
    if (totalSalesIndex !== -1) {
        const totalSales = salesData[totalSalesIndex];

        // Extract relevant data for individual products
        const productData = labels.slice(0, totalSalesIndex).map((label, index) => {
            const sales = salesData[index];
            return {
                label: label,
                percentage: (sales / totalSales) * 100,
                color: `rgba(${Math.floor(Math.random() * 256)}, ${Math.floor(Math.random() * 256)}, ${Math.floor(Math.random() * 256)}, 1)`,
            };
        });

        // Calculate the percentage for "항목을 제외한 나머지 비율"
        const restPercentage = 100 - productData.reduce((sum, item) => sum + item.percentage, 0);

        // Add "항목을 제외한 나머지 비율" to the product data only if it's 1% or more
        if (restPercentage >= 1) {
            productData.push({
                label: "항목을 제외한 나머지 비율",
                percentage: restPercentage,
                color: `rgba(${Math.floor(Math.random() * 256)}, ${Math.floor(Math.random() * 256)}, ${Math.floor(Math.random() * 256)}, 1)`,
            });
        }

        myDoughnut = new Chart(canvas, {
            type: 'doughnut',
            data: {
                labels: productData.map(item => item.label),
                datasets: [{
                    data: productData.map(item => item.percentage.toFixed(1)),
                    backgroundColor: productData.map(item => item.color),
                }]
            },
            options: {
                responsive: true,
                plugins: {
                    legend: {
                        position: 'top',
                    },
                    title: {
                        display: true,
                        text: '영업이익 (%)'
                    }
                }
            }
        });
    } else {
        console.error("총매출 데이터가 없습니다.");
    }
}

function drawDoughnut2() {
    const canvas = document.getElementById('div4');
    if (myDoughnut2 !== undefined) {
        myDoughnut2.destroy();
    }

    const totalProfitsIndex = labels.indexOf('총매출');
    if (totalProfitsIndex !== -1) {
        const totalProfits = profitsData[totalProfitsIndex];

        // Extract relevant data for individual products
        const productData = labels.slice(0, totalProfitsIndex).map((label, index) => {
            const profits = profitsData[index];
            return {
                label: label,
                percentage: (profits / totalProfits) * 100,
                color: `rgba(${Math.floor(Math.random() * 256)}, ${Math.floor(Math.random() * 256)}, ${Math.floor(Math.random() * 256)}, 1)`,
            };
        });

        // Calculate the percentage for "항목을 제외한 나머지 비율"
        const restPercentage = 100 - productData.reduce((sum, item) => sum + item.percentage, 0);

        // Add "항목을 제외한 나머지 비율" to the product data only if it's 1% or more
        if (restPercentage >= 1) {
            productData.push({
                label: "항목을 제외한 나머지 비율",
                percentage: restPercentage,
                color: `rgba(${Math.floor(Math.random() * 256)}, ${Math.floor(Math.random() * 256)}, ${Math.floor(Math.random() * 256)}, 1)`,
            });
        }

        myDoughnut2 = new Chart(canvas, {
            type: 'doughnut',
            data: {
                labels: productData.map(item => item.label),
                datasets: [{
                    data: productData.map(item => item.percentage.toFixed(1)),
                    backgroundColor: productData.map(item => item.color),
                }]
            },
            options: {
                responsive: true,
                plugins: {
                    legend: {
                        position: 'top',
                    },
                    title: {
                        display: true,
                        text: '매출액 (%)'
                    }
                }
            }
        });
    } else {
        console.error("총매출 데이터가 없습니다.");
    }
}

window.addEventListener('message',function (event){
    if(event.data === 'HTMLClosed'){
        const data = localStorage.getItem('table_info');
        const trList = JSON.parse(data);
        const chart = document.getElementById('chart');
        let rowCount = chart.rows.length;

        for (let i = rowCount - 1; i >= 0; i--) {
            chart.deleteRow(i);
        }

        trList.productsList.forEach((value, index)=>{
            const tr = document.createElement('tr');
            const td1 = document.createElement('td');
            const td2 = document.createElement('td');
            td1.setAttribute('scope','row');
            td1.textContent = value.id_merchandise.toString();
            td2.textContent = value.merchandiseName.toString();
            td1.setAttribute('class','col-2');
            td2.setAttribute('class','col-4');
            tr.appendChild(td1);
            tr.appendChild(td2);
            chart.append(tr);
        })
    }
})

//showChart
function showChart(){
    const radioButtons = document.querySelectorAll('input[name="searchRadios"]');
    const showTable = document.getElementById('showTable');
    const showChart = document.getElementById('showChart');
    radioButtons.forEach((radio) => {
        radio.addEventListener('change', function() {
            // 라디오 버튼의 값이 변경되었을 때 실행될 코드를 작성합니다.
            //차트 보기를 선택했을 때
            console.log(this.value);
            if(this.value === "option1"){
                showChart.style.display="";
                showTable.style.display='none';
            }
            else{
                showChart.style.display="none";
                showTable.style.display="";
            }

        });
    });
}

//창이 닫히기전에 가지고 있던 값 삭제
window.addEventListener('beforeunload', function (event) {
    localStorage.removeItem('table_info');
});