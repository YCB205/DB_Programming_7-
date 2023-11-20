
function putStore_manager_User_information_check() {

    const apiUrl = '/user';

    const email = document.getElementById('email').value;
    const id = document.getElementById('idStoreManager').value;
    const passwd = document.getElementById('passwd').value;
    const name = document.getElementById('name').value;
    const phoneNumber = document.getElementById('phoneNumber').value;

    const userData = {
        email,
        id,
        name,
        passwd,
        phoneNumber,
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
            const mainPage= window.open('../../html/store_manager/MainJumju.html')
            opener.location.replace('../../html/store_manager/MainJumju.html');
            window.close();
        })
        .catch(error => {
            console.error(error);
        });
}
