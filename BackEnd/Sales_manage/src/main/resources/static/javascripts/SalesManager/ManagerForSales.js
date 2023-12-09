// 서버로 데이터 요청
window.onload = function () {
    getData();
    console.log('창이 열렸습니다.');

    function getData() {
        let officeName = [];
        let name = '스타벅스';
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


}

function addBtnRadio(data){
    let btngroup = document.getElementById('btngroup');
    // <input type="checkbox" className="btn-check" name="btncheck" id="btncheck1" autoComplete="off">-->
    //     <!--                    <label class="btn btn-outline-primary rounded-0 m-0" for="btncheck1">공주대 천안점</label>-->
    for(let i = 0; i< data.length;i++) {
        for (let j = 0; j < data[i].length; j++) {
            let input = document.createElement('input');
            input.setAttribute('type', 'checkbox');
            input.setAttribute('class', 'btn-check');
            input.setAttribute('name', 'btncheck');
            input.setAttribute('id', `${data[i][j].idStoreManager}`);
            input.setAttribute('autoComplete', 'off');
            input.setAttribute('value', `${data[i][j].idStoreManager}`);
            let label = document.createElement('label');
            label.classList.add('btn', 'btn-outline-primary', 'rounded-0', 'm-0');
            label.setAttribute('for', `${data[i][j].idStoreManager}`);
            label.textContent = `${data[i][j].officeName}`;
            btngroup.appendChild(input);
            btngroup.appendChild(label);
        }
    }
}

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