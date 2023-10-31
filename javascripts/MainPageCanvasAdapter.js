import {drawLineFunc} from './LineChartRender.js'
const LINECHART = "salesInformationRegistrationID"
const PRODUCTTABLE ="registerOrderForm"

const chooseRadioButtonWhatSelect=(radioBtnValue)=> {
    let radioBtnValueWhatSelected = radioBtnValue.toString();

        if (radioBtnValueWhatSelected === LINECHART) {
            drawLineFunc();
        } else if (radioBtnValueWhatSelected === PRODUCTTABLE) {
            console.log("아직 구현 안함");
        }

}
export {chooseRadioButtonWhatSelect};