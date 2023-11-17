import {chooseRadioButtonWhatSelect} from './MainPageCanvasAdapter.js'

const dropBoxsClass = '.dropdown-item';

document.addEventListener('DOMContentLoaded',function () {
    const dropBoxs = document.querySelectorAll(dropBoxsClass);
    dropBoxs.forEach(function (selected) {
        selected.addEventListener('click', function () {
            const selectedValue = this.id;
            // console.log(selectedValue.toString());
            chooseRadioButtonWhatSelect(selectedValue.toString());
        })
    })
});

