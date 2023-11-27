// Set today's date and time for the input fields
setTodayTime();
getOrderSheet();
// Get order sheet data based on user input
function getOrderSheet() {
    // Get values from input fields
    const search_merchandise = document.getElementById('search_merchandise').value;
    const sDateTime = formatDate(new Date(document.getElementById('startDateTime').value));
    const eDateTime = formatDate(new Date(document.getElementById('endDateTime').value));

    // Construct the URL with query parameters
    const url = `/orderSheet?search_merchandise=${search_merchandise}&startDateTime=${sDateTime}&endDateTime=${eDateTime}`;

    // Fetch data from the server
    fetch(url)
        .then(response => response.json())
        .then(data => {
            // Handle the JSON response
            generateOrderTable(data);
        })
        .catch(error => {
            // Handle errors
            console.error('Error fetching data:', error);
        });
}

// Generate the order table dynamically
function generateOrderTable(data) {
    // Clear existing table rows
    const tableBody = document.getElementById('table-body');
    tableBody.innerHTML = '';

    // Iterate over each order in the data
    data.forEach((order, index) => {
        // Create and append the main order row
        const mainRow = createTableRow(order, index, index % 2 === 0 ? 'table-light-ycb' : 'table-success');
        tableBody.appendChild(mainRow);

        // Create and append the product row
        const productRow = createProductRow(order.merchandise, index);
        tableBody.appendChild(productRow);
    });
}

// Create a table row for the main order information
function createTableRow(order, index, rowClass) {
    const row = document.createElement('tr');
    row.classList.add(rowClass, `orderTable${index+1}`);
    row.setAttribute('data-bs-toggle', 'collapse');
    row.setAttribute('data-bs-target', `#collapseExample${index + 1}`);
    row.setAttribute('aria-expanded', 'false');
    row.style.borderBottom = '#78c2ad';
    row.innerHTML = `
        <td class="col-2 text-center m-0">${order.idOrdersheet}</td>
        <td class="col-3 text-center m-0">${convertTimeType(order.orderTime)}</td>
        <td class="col-3 text-center m-0">${order.sales}원</td>
        <td class="col-3 text-center m-0">${order.profit}원</td>
        <td class="col-1 text-center m-0" style="padding-left: 0; padding-right: 0; border-bottom: 0px; background-color: #fff;">
            <a class="btn btn-primary" target="_self" onclick="showPopupUpdateOrderSheet('UpdateOrderForm.html',660,661,\`tr.orderTable${index + 1}\` )">
                <i class="bi bi-pencil-square"></i>
            </a>
            <a class="btn btn-primary" onclick="CheckDelete(\`tr.orderTable${index + 1}\`)">
                <i class="bi bi-trash"></i>
            </a>
        </td>`;

    return row;
}

// Create a table row for the product information
function createProductRow(merchandise, index) {
    const row = document.createElement('tr');
    row.className = 'collapse';
    row.id = `collapseExample${index + 1}`;
    row.innerHTML = `
        <td colspan="4" style="background-color: #e0e0e0;">
            <div class="product_table_div">
                <table class="product_table">
                    <thead class="product_table_thead">
                        <tr>
                            <th style="position:sticky; top:0px;">품목명</th>
                            <th style="position:sticky; top:0px;">상품번호</th>
                            <th style="position:sticky; top:0px;">상품명</th>
                            <th style="position:sticky; top:0px;">원가</th>
                            <th style="position:sticky; top:0px;">판매가</th>
                            <th style="position:sticky; top:0px;">판매수량</th>
                        </tr>
                    </thead>
                    <tbody class="product_table_tbody">
                        ${generateProductRows(merchandise)}
                    </tbody>
                </table>
            </div>
        </td>`;

    return row;
}

// Generate rows for each product in the order
function generateProductRows(merchandise) {
    return merchandise.map(item => `
        <tr>
            <td>${item.categori}</td>
            <td>${item.id_merchandise}</td>
            <td>${item.merchandiseName}</td>
            <td>${item.cost}원</td>
            <td>${item.price}원</td>
            <td>${item.orderCount}개</td>
        </tr>
    `).join('');
}

// Date formatting function
function formatDate(date) {
    const year = date.getFullYear();
    const month = String(date.getMonth() + 1).padStart(2, '0');
    const day = String(date.getDate()).padStart(2, '0');
    const hours = String(date.getHours()).padStart(2, '0');
    const minutes = String(date.getMinutes()).padStart(2, '0');
    const seconds = '00'; // Since the input does not provide seconds, set to '00'

    return `${year}-${month}-${day} ${hours}:${minutes}:${seconds}`;
}

function convertTimeType(inputDateTime) {
    const date = new Date(inputDateTime);

    const year = date.getFullYear();
    const month = String(date.getMonth() + 1).padStart(2, '0');
    const day = String(date.getDate()).padStart(2, '0');
    const hours = String(date.getHours()).padStart(2, '0');
    const minutes = String(date.getMinutes()).padStart(2, '0');
    const seconds = String(date.getSeconds()).padStart(2, '0');

    const formattedDate = `${year}년 ${month}월 ${day}일`;
    const formattedTime = `${hours}:${minutes}:${seconds}`;

    return `${formattedDate} - ${formattedTime}`;
}

// Set today's date and time for the input fields
function setTodayTime() {
    const currentDate = new Date();
    const currentYear = currentDate.getFullYear();
    const currentMonth = String(currentDate.getMonth() + 1).padStart(2, '0');
    const currentDay = String(currentDate.getDate()).padStart(2, '0');
    const currentHours = String(currentDate.getHours()).padStart(2, '0');
    const currentMinutes = String(currentDate.getMinutes()).padStart(2, '0');

    // Set initial values for startDateTime and endDateTime
    document.getElementById('startDateTime').value = `${currentYear}-${currentMonth}-${currentDay} 00:00:00`;
    document.getElementById('endDateTime').value = `${currentYear}-${currentMonth}-${currentDay} ${currentHours}:${currentMinutes}:00`;
}
