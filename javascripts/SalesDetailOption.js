const whatOptionChoose = document.getElementById("selectDivOptionDetails");
const dropDownMenueForSale =()=>{

    let form = document.createElement('form');
    let label = document.createElement('label');
    let select = document.createElement('select');

    /*월별 주별 일별 선택 옵션*/
    let optionValueMonth = document.createElement("option");
    let optionValueWeek = document.createElement('option');
    let optionValueDate = document.createElement("option");

    /*태그 안의 ID value값 지정*/
    label.setAttribute('for','options');
    select.setAttribute('name','optionName');
    select.setAttribute('id','options');

    //옵션밸류값 지정
    optionValueMonth.setAttribute('id','month');
    optionValueWeek.setAttribute('id','week');
    optionValueDate.setAttribute('id','date');

    whatOptionChoose.appendChild(form);
    form.appendChild(label);
    form.appendChild(select);
    select.appendChild(optionValueMonth);
    select.appendChild(optionValueWeek);
    select.appendChild(optionValueDate);
}

export {dropDownMenueForSale}