// 사용자의 ID
const userId = "ckd5310"

// 쿼리 매개변수를 포함한 URL 생성
const apiUrl = '/user';
const queryParams = { userId: userId };
const queryString = new URLSearchParams(queryParams).toString();
const urlWithQueryParams = `${apiUrl}?${queryString}`;

// fetch() 함수를 사용하여 GET 요청 보내기
fetch(urlWithQueryParams)
    .then(response => response.json())
    .then(data => console.log(data))
    .catch(error => console.error('Error:', error));
