// 서버로 데이터 요청
window.onload = function () {
    getData();
    console.log('창이 열렸습니다.');
    const btn = document.getElementById('fetchData');
    btn.addEventListener('click', function () {
        //검색실행
        getTime();
    })
    const startDate = document.getElementById('fromDate');
    const endDate = document.getElementById('toDate');
    const currentDate = new Date();
    currentDate.setHours(0, 0, 0, 0);
    const setDefault = convertTimeType(currentDate);
    startDate.value = setDefault;
    endDate.value = setDefault;


    startDate.addEventListener('change', function () {
        checkTimeValid();
    })
    endDate.addEventListener('change', function () {
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


function addBtnRadio(data) {
    let btngroup = document.getElementById('btngroup');
    // <input type="checkbox" className="btn-check" name="btncheck" id="btncheck1" autoComplete="off">-->
    //     <!--                    <label class="btn btn-outline-primary rounded-0 m-0" for="btncheck1">공주대 천안점</label>-->
    for (let i = 0; i < data.length; i++) {
        let input = document.createElement('input');
        input.setAttribute('type', 'radio');
        input.setAttribute('class', 'btn-check');
        input.setAttribute('name', 'btncheck');
        input.setAttribute('id', `${data[i][0].idStoreManager}`);
        input.setAttribute('autoComplete', 'off');
        input.setAttribute('value', `${data[i][0].officeName}`);
        let label = document.createElement('label');
        label.classList.add('btn', 'btn-outline-primary', 'rounded-0', 'm-0');
        label.setAttribute('for', `${data[i][0].idStoreManager}`);
        label.textContent = `${data[i][0].officeName}`;
        btngroup.appendChild(input);
        btngroup.appendChild(label);
        input.addEventListener('change', function () {
            changeChart(this.value);
            hideTable(this.value);
        })
    }
}

function checkTimeValid() {
    const fromDate = document.getElementById('fromDate');
    const toDate = document.getElementById('toDate');
    let curruentTime = new Date();
    let startValue = new Date(fromDate.value);
    let endValue = new Date(toDate.value);
    if (startValue > endValue) {
        fromDate.value = toDate.value;
    } else if (endValue > curruentTime) {
        toDate.value = convertTimeType(curruentTime);
    }
}

function getTime() {
    const startTime = document.getElementById('fromDate');
    const endTime = document.getElementById('toDate');
    getFetch(startTime.value, endTime.value);
}


//fetch부르기
function getFetch(startdate, enddate) {
    const product = [];
    const company = [];
    const startDate = convertTimeType(startdate);
    const endDate = convertTimeType(enddate);
    const getRadio = document.getElementById('optionsRadios2');
    const getRadio2 = document.getElementById('optionsRadios1');
    if (getRadio.checked) {
        const getLocal = localStorage.getItem('table_info');
        const data = JSON.parse(getLocal);
        data.productsList.forEach((value, index) => {
            product.push(value.merchandiseName.toString());
        })
    }
    const url = `/allOrderproducts?search_merchandise=${product}&startDateTime=` + startDate + "&endDateTime=" + endDate + `&brandoffice_id=${company}`;
    fetch(url)
        .then(response => response.json())
        .then(data => {
            console.log(data);
            addTableRow(data);
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

let myMap = new Map();
let jumjuMap = new Map();
let labels = [];
let salesData = [];
let profitsData = [];

function groupAndConvertToJSON(data) {
    const groupedData = {};

    // 데이터를 그룹화합니다.
    data.forEach(item => {
        const key = item[0]; // [0]번째 숫자 값을 그룹화 키로 사용합니다.

        if (!groupedData[key]) {
            groupedData[key] = [];
        }

        // 데이터를 JSON 객체로 변환하여 그룹에 추가합니다.
        groupedData[key].push({
            jumju: item[1],
            category: item[2],
            id: item[3],
            name: item[4],
            profit: item[6],
            sales: item[7],
        });
    });

    // 그룹화된 데이터를 배열로 변환합니다.
    const result = Object.values(groupedData);

    return result;
}

function addTableRow(data) {
    const tbody = document.getElementById('tbody');
    //데이터 전처리하기
    const groupedAndConvertedData = groupAndConvertToJSON(data);
    console.log(groupedAndConvertedData);
    myMap.clear();
    jumjuMap.clear();
    labels.length = 0;
    salesData.length = 0;
    profitsData.length = 0;
    //자식요소 삭제하기
    while (tbody.firstChild) {
        console.log("삭제합니다..");
        tbody.removeChild(tbody.firstChild);
    }
    for (let index = 0; index < groupedAndConvertedData.length - 1; index++) {
        const ordersheet = groupedAndConvertedData[index];
        let trClass = document.createElement('tr');
        let th = document.createElement('th');
        th.setAttribute('rowspan', ordersheet.length + 1);
        th.setAttribute('scope', 'row');
        th.setAttribute('style', 'vertical-align: middle');
        let jumju = ordersheet[0].jumju;
        th.textContent = jumju;
        th.setAttribute('id', ordersheet[0].jumju);
        trClass.appendChild(th);
        tbody.appendChild(trClass);
        myMap.set(jumju, 0);
        jumjuMap.set(jumju, 0);
        let list = [0, 0];
        let categoryMap = new Map();
        const getRadio = document.getElementById('optionsRadios2');
        const getRadio2 = document.getElementById('optionsRadios1');
        for (let jdex = 0; jdex < ordersheet.length; jdex++) {
            let data = ordersheet[jdex];
            let tr = document.createElement('tr');
            let td1 = document.createElement('td');
            let td2 = document.createElement('td');
            let td3 = document.createElement('td');
            let td4 = document.createElement('td');
            let td5 = document.createElement('td');
            td1.textContent = data.category;
            td2.textContent = data.name;
            td3.textContent = data.id;
            td4.textContent = data.profit;
            td5.textContent = data.sales;

            list[0] += data.profit;
            list[1] += data.sales;

            //만약 카테고리가 없다면?
            if (categoryMap.get(data.category) == null) {
                let list2 = [0, 0];
                list2[0] += data.profit;
                list2[1] += data.sales;
                categoryMap.set(data.category, list2);
            } else {
                let existingValue = categoryMap.get(data.category);
                existingValue[0] += data.profit;
                existingValue[1] += data.sales;
            }

            tr.appendChild(td1);
            tr.appendChild(td2);
            tr.appendChild(td3);
            tr.appendChild(td4);
            tr.appendChild(td5);
            tbody.appendChild(tr);
            myMap.set(jumju, list);
        }
        jumjuMap.set(jumju, categoryMap);
    }
    drawBarChart();
    drawDoughnut();
    drawDoughnut2();
}

//차트를 그리기 위한 메서드

let myBarChart;
let myDoughnut;
let myDoughnut2;

function drawBarChart() {
    const canvas = document.getElementById('myAllChart');
    if (myBarChart !== undefined) {
        myBarChart.destroy();
    }
    canvas.style.width = '100%';
    myMap.forEach((value, key) => {
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
                    label: '순이익',
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
                    text: '지점별 총매출과 순이익'
                }
            }
        }
    });

}

function drawDoughnut() {
    const canvas = document.getElementById('partChartTable');
    if (myDoughnut !== undefined) {
        myDoughnut.destroy();
    }
    myDoughnut = new Chart(canvas, {
        type: 'doughnut',
        data: {
            labels: labels,
            datasets: [{
                label: '순이익',
                data: salesData
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
                    text: '순이익'
                }
            }
        }
    })
}

function drawDoughnut2() {
    const canvas = document.getElementById('partChartTable2');
    if (myDoughnut2 !== undefined) {
        myDoughnut2.destroy();
    }
    myDoughnut2 = new Chart(canvas, {
        type: 'doughnut',
        data: {
            labels: labels,
            datasets: [{
                label: '매출액',
                data: profitsData
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
                    text: '매출액'
                }
            }
        }
    })
}

function changeChart(value) {
    let newlabe = [];
    let newsale = [];
    let newprofits = [];
    let jumjunInfo = jumjuMap.get(value);
    console.log(jumjunInfo);
    jumjunInfo.forEach((value, key) => {
        newlabe.push(key);
        newsale.push(value[0]);
        newprofits.push(value[1]);
    })
    myBarChart.data.datasets[0].data = newsale;
    myBarChart.data.datasets[1].data = newprofits;
    myBarChart.data.labels = newlabe;

    myDoughnut.data.datasets[0].data = newsale;
    myDoughnut2.data.datasets[0].data = newprofits;

    myDoughnut.data.labels = newlabe;
    myDoughnut2.data.labels = newlabe;

    myBarChart.update();
    myDoughnut.update();
    myDoughnut2.update();
}

//테이블 정보 가져오기
window.addEventListener('message', function (event) {
    if (event.data === 'HTMLClosed') {
        const data = localStorage.getItem('table_info');
        const trList = JSON.parse(data);
        const chart = document.getElementById('productTable');
        let rowCount = chart.rows.length;

        for (let i = rowCount - 1; i >= 0; i--) {
            chart.deleteRow(i);
        }

        trList.productsList.forEach((value, index) => {
            const tr = document.createElement('tr');
            const td1 = document.createElement('td');
            const td2 = document.createElement('td');
            td1.setAttribute('scope', 'row');
            td1.textContent = value.id_merchandise.toString();
            td2.textContent = value.merchandiseName.toString();
            td1.setAttribute('class', 'col-2');
            td2.setAttribute('class', 'col-4');
            tr.appendChild(td1);
            tr.appendChild(td2);
            chart.append(tr);
        })
    }
})

//창이 닫히기전에 가지고 있던 값 삭제
window.addEventListener('beforeunload', function (event) {
    localStorage.removeItem('table_info');
});

function hideTable(value) {
    let tbody = document.getElementById('tbody');
    let trList = tbody.querySelectorAll('tr');
    console.log(trList.length);
    for (let i = 0; i < trList.length; i++) {
        let th = trList[i].querySelector('th');
            if (th !==null && th.id.toString() === value) {
                console.log(th.rowSpan);
                let start = i;
                i += th.rowSpan - 1;
                let end = i;
                for(start; start <= end; start++){
                    trList[start].style.display ='';
                    console.log(trList[start]);
                }
            }
            else {
                trList[i].style.display = 'none';
            }
        }
}
