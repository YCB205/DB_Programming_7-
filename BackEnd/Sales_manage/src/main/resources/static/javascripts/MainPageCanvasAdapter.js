import {drawLineFunc} from './LineChartRender.js'
import {dropDownMenueForSale} from './SalesDetailOption.js'
import {drawPieFunc} from "./CircleChartRender.js";
import {destoryAll} from "./DestoryAll.js";
//.dropdown-content의 자식 id
//매출정보 조회 선택시
const LINECHARTORTABLEDROP = "salesInformationRegistrationID"
//차트인가 테이블인가 체크하기 위한 변수
let chartOrTable;

//chartID값을 가져온다
const CHARTID = document.getElementById('chart');
const TABLEID = document.getElementById('table');
//처음에는 chart로 지정
chartOrTable = CHARTID;
const PRODUCTTABLE ="registerOrderForm"

const chooseRadioButtonWhatSelect=(radioBtnValue)=> {
    let radioBtnValueWhatSelected = radioBtnValue.toString();

        if (radioBtnValueWhatSelected === LINECHARTORTABLEDROP) {
            if(chartOrTable === CHARTID) {
                drawChart();
                console.log('차트를 선택');
            }
            else if (chartOrTable === TABLEID){
                console.log("아직 구현 못함");
            }
        } else if (radioBtnValueWhatSelected === PRODUCTTABLE) {
            console.log("아직 구현 안함");
        }

}
export {chooseRadioButtonWhatSelect};

// chart를 그리는 메서드의 집합을 나타냄
const drawChart = () => {
    destoryAll('scrollable-div-id');
    drawLineFunc();
    drawPieFunc();
    dropDownMenueForSale();
    console.log('차트를 선택');
}
export {drawChart};

