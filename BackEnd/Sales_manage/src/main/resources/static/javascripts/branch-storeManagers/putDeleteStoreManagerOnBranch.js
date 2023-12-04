function putDeleteStoreManagerOnBranch(id_randOffice) {

    var idBrandOffice = parseInt(id_randOffice);
    console.log(idBrandOffice);

    const apiUrl = `/deleteStoreManagerOnBranch?idBrandOffice=${idBrandOffice}`;

    fetch(apiUrl, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json',
        },
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
            getBranchStoreManagers();
        })
        .catch(error => {
            console.error(error);
        });
}
