
const drawLineFunc=()=> {
    let myChart = document.getElementById('myChart');
    //이전 차트 파괴 메서드 있으면 오류나서 파괴하고 다시 그리기
    let existingChart = Chart.getChart(myChart);
    if(existingChart){
        existingChart.destroy();
    }
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
}
export {drawLineFunc};