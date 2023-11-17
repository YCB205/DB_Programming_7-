import {returnPieDataSet} from './CircleChatDataSetup.js'

//원형 차트를 그리는 메서드 '/CircleChatDataSetup'의 returnPieDataSet의 값을 가져온다

const data = returnPieDataSet();
const drawPieFunc = () => {
    let myChartDiv = document.getElementById('scrollable-div-id');

    //원형차트 캔버스 넣기
    let myChart = document.createElement('canvas');
    myChart.setAttribute('id','myChartPie');
    new Chart(myChart,{
        type : 'doughnut',
        data : data,
    })
    myChartDiv.appendChild(myChart);
}
export {drawPieFunc};