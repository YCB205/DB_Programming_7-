const apiUrl = '/userPosition';

fetch(apiUrl)
    .then(response => {
        // 응답 데이터가 텍스트 형식이 아닌 경우에 대비하여 확인
        if (!response.ok) {
            throw new Error('Network response was not ok');
        }
        // 텍스트 형식으로 변환
        return response.text();
    })
    .then(data => {
        document.querySelector("#position").innerText = data;
        console.log(data);
        console.log(typeof (data));
    })
    .catch(error => console.error('Error:', error));
