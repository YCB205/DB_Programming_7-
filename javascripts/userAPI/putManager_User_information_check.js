const apiUrl = '/user';

const email = document.getElementById('emailInput').value;
const id = document.getElementById('idStoreManagerInput').value;
const passwd = document.getElementById('passwd').value;
const name = document.getElementById('nameInput').value;
const number = document.getElementById('numberInput').value;

const userData = {
    email,
    id,
    passwd,
    name,
    number,
};

fetch(apiUrl, {
    method: 'PUT',
    headers: {
        'Content-Type': 'application/json',
    },
    body: JSON.stringify(userData),
})
    .then(response => {
        if (!response.ok) {
            throw new Error(`Error: ${response.status} ${response.statusText}`);
        }
        // 성공적인 응답이라면 JSON 파싱을 시도
        return response.text();
    })
    .then(data => {
        // 데이터가 JSON 형식이 아니라면 파싱을 시도하지 않음
        console.log(data);
        const mainPage= window.open('매니저 메인페이지 주소')
        mainPage.location.reload();
        window.close();
    })
    .catch(error => {
        console.error(error);
    });
