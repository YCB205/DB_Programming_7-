getBranchStoreManagers()
function getBranchStoreManagers() {
    const officeName = document.querySelector('#searchOfficeName').value;
    const name = document.querySelector('#searchStoreManagerName').value;

    // 서버로 데이터 요청
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
            generateBranchStoreManagerTable(data);

        })
        .catch(error => {
            // 오류 발생 시 처리
            console.error('Error getting products:', error);
        });
}

function generateBranchStoreManagerTable(data) {
    // Clear existing table rows
    const tableBody = document.getElementById('table-body');
    tableBody.innerHTML = '';

    // Iterate over each brand office and related store manager in the data
    data.forEach((brandAndStoreManagers, index) => {
        // Check if there is at least one store manager for the brand office
        if (brandAndStoreManagers.length > 1) {
            // Create and append the main brand office row
            const mainRow = createTableRow(brandAndStoreManagers[0], brandAndStoreManagers[1], index, index % 2 === 0 ? 'table-light-ycb' : 'table-success');
            tableBody.appendChild(mainRow);

            // Create and append the store manager row
            const storeManagerRow = createStoreManagerRow(brandAndStoreManagers[1], index);
            tableBody.appendChild(storeManagerRow);
        }
    });
}

// Modify the createTableRow function to accept the brand office data
function createTableRow(brandOffice, storeManager, index, rowClass) {
    const row = document.createElement('tr');
    row.classList.add(rowClass, `branchTable${index + 1}`);
    row.setAttribute('data-bs-toggle', 'collapse');
    row.setAttribute('data-bs-target', `#collapseExample${index + 1}`);
    row.setAttribute('aria-expanded', 'false');
    row.style.borderBottom = '#78c2ad';
    row.innerHTML = `
        <td class="col-2 text-center m-0">${brandOffice.idBrandOffice}</td>
        <td class="col-3 text-center m-0">${brandOffice.officeName}</td>
        <td class="col-2 text-center m-0">${storeManager.name}</td>
        <td class="col-4 text-center m-0">${brandOffice.address}</td>
        <td class="col-1 text-center m-0" style="padding-left: 0; padding-right: 0; border-bottom: 0px; background-color: #fff;">
            <a class="btn btn-primary" target="_self" onclick="showPopupUpdateOrderSheet('updateBranch.html',600,617,\`tr.branchTable${index + 1}\` )">
                <i class="bi bi-pencil-square"></i>
            </a>
            <a class="btn btn-primary" onclick="CheckDelete(\`tr.branchTable${index + 1}\`)">
                <i class="bi bi-trash"></i>
            </a>
        </td>`;

    return row;
}

// Modify the createStoreManagerRow function to accept the store manager data
function createStoreManagerRow(storeManager, index) {
    const row = document.createElement('tr');
    row.className = 'collapse';
    row.id = `collapseExample${index + 1}`;
    row.innerHTML = `
        <td colspan="4" style="background-color: #e0e0e0;">
            <div class="product_table_div">
                <table class="product_table">
                    <thead class="product_table_thead">
                        <tr>
                            <th style="position:sticky; top:0px;">아이디</th>
                            <th style="position:sticky; top:0px;">이름</th>
                            <th style="position:sticky; top:0px;">이메일</th>
                            <th style="position:sticky; top:0px;">전화번호</th>
                        </tr>
                    </thead>
                    <tbody class="product_table_tbody">
                        ${generateStoreManagerRow(storeManager)}
                    </tbody>
                </table>
            </div>
        </td>`;

    return row;
}

// Modify the generateStoreManagerRows function to generate a single store manager row
function generateStoreManagerRow(storeManager) {
    return `
        <tr>
            <td>${storeManager.idStoremanager}</td>
            <td>${storeManager.name}</td>
            <td>${storeManager.email}</td>
            <td>${storeManager.phoneNumber}</td>
            <td class="col-1 text-center m-0" style="padding-left: 0; padding-right: 0; border-bottom: 0px;  background-color: #e0e0e0;">
                <a class="btn btn-info" target="_self" onclick="showPopupStoreManager('storeManager_information_check.html',600,617, ${storeManager.idStoremanager})">
                <i class="bi bi-pencil-square"></i></a>
                <a class="btn btn-info">
                <i class="bi bi-trash"></i></a>
            </td>
        </tr>`;
}

