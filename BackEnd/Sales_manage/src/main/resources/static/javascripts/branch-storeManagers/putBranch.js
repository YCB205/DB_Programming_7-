function putBranch() {

    const apiUrl = '/branch-storeManagers';

    var idBrandOffice = parseInt(document.getElementById('idBrandOffice').value);
    var idStoreManager = document.getElementById('idStoreManager').value;
    var officeName = document.getElementById('officeName').value;
    var address = document.getElementById('address').value;

    const branchData = {
        idStoreManager,
        idBrandOffice,
        officeName,
        address
    };
    console.log(branchData);

    fetch(apiUrl, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(branchData),
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
            window.opener.postMessage('refreshParent', '*');
            window.close();
        })
        .catch(error => {
            console.error(error);
        });
}
