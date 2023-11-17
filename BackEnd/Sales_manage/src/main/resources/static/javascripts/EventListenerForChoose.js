import {drawChart} from './MainPageCanvasAdapter.js'

/*차트나 테이블은 클릭 될 때마다 특정 함수를 호출한다.*/
//매출 정보 등록을 클릭할 때 폼 요소를 입력받거나 이벤트 발생시 처리해주는 메서드
document.addEventListener('DOMContentLoaded',function (){
    const radioOptionNavChart = document.querySelector("#chart");
    const radioOptionNavTable = document.querySelector("#table");
    //검색 버튼을 클릭시 호출되는 함수
    const buttonSearchAtNavOptionButton = document.querySelector("#submitFormButton");
    buttonSearchAtNavOptionButton.addEventListener('click', function () {
        getInfoFromNavOption();
    })

    radioOptionNavChart.addEventListener('click',function () {
        drawChart();
    });
    radioOptionNavTable.addEventListener('click',function () {
        console.log("아직 구현 안함");
    })
})

//메인 네브 옵션에서 입력받은 값들을 처리
const getInfoFromNavOption = () => {
    const navOptionIfo = document.querySelector(".timeFormCssParent");
    const weekOrMonth =  navOptionIfo.getElementById("weekOrMonth");
    console.log(weekOrMonth.value.toString());
}




