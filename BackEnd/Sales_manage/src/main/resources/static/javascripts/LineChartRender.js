
const drawLineFunc=()=> {
    let myChartDiv = document.getElementById('scrollable-div-id');

    //canvas를 생성 넣기
    let myChart = document.createElement('canvas');
    myChart.setAttribute('id','myChartHist');
    myChart.innerHTML='';
    new Chart(myChart, {
        type: 'line',
        data: {
            labels: ['January', 'Feburary', 'March', 'April', 'May', 'June', 'July'],
            datasets: [{
                label: 'My First Dataset',
                data: [65, 59, 80, 81, 56, 55, 40],
                borderColor: 'rgba(75, 192, 192, 1)',
                borderWidth: 1,
                fill: false
            }]
        },
        options: {
            scales: {
                x: {
                    beginAtZero: true
                },
                y: {
                    beginAtZero: true
                }
            }
        }
    });
    myChartDiv.appendChild(myChart);
}
export {drawLineFunc};