window.onload = function () {
    getOrderManager();
}

//매출 정보 조히 차트 띄우기
function getOrderManager(){
    getFetch();
}

//fetch함수 부르기
function getFetch(){
    const search_merchandise = document.getElementById('showPopup');
    const product = "";
    const startDate = '';
    const endDate = '';
    const url = `/orderSheet?search_merchandise=${product}&startDateTime=${startDate}&endDateTime=${endDate}`;
    fetch(url)
        .then(response => response.json())
        .then(data=>{
            console.log(data);
        })
}