getClosedBranch();
function getClosedBranch() {
    const officeName = document.querySelector('.input_store_name').value;

    // 서버로 데이터 요청
    fetch(`/closedBranch?officeName=${officeName}`, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json'
        },
    })
        .then(response => response.json())
        .then(data => {
            // 데이터가 성공적으로 받아진 경우의 동작
            console.log('데이터 성공적으로 수신:', data);
            generateBranchStoreManagerTable(data);
        })
        .catch(error => {
            // 오류 발생 시 처리
            console.error('폐점 정보를 가져오는 중 오류 발생:', error);
        });
}

function generateBranchStoreManagerTable(data) {
    // 기존의 테이블 행을 지웁니다.
    const tableBody = document.querySelector('.product_table_tbody');
    tableBody.innerHTML = '';

    // 각 브랜드 오피스에 대해 반복합니다.
    data.forEach((brandOffice, index) => {
        // 브랜드 오피스 행을 생성하고 추가합니다.
        const row = createTableRow(brandOffice, index);
        tableBody.appendChild(row);
    });
}

function createTableRow(brandOffice, index) {
    const row = document.createElement('tr');
    row.classList.add(`branchTable${index}`);
    row.innerHTML = `
   
        <td>${brandOffice.idBrandOffice}</td>
        <td>${brandOffice.officeName}</td>
        <td>${brandOffice.address}</td>
        <td>
            <a class="btn btn-info" target="_self" 
            onclick="showPopupUpdateClosedBranch('updateClosedBranch.html',600,508,\`tr.branchTable${index}\` )">
                <i class="bi bi-pencil-square"></i>
            </a>
        </td>`;
    return row;
}
