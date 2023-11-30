window.onload = function () {
    getOrderManager();
}

//매출 정보 조히 차트 띄우기
function getOrderManager(){
    getFetch();
}

//fetch함수 부르기
function getFetch(){
    const url = `/sales?product=all&start_date=date&end_date=date`;
    fetch(url)
        .then(response => response.json())
        .then(data=>{
            console.log(data);
        })
}