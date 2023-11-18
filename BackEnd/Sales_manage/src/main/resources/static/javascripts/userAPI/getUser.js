const apiUrl = '/user';

fetch(apiUrl)
    .then(response => response.json())
    .then(data => {
        // Assuming you have elements with corresponding IDs in your HTML
        document.getElementById('brandName').textContent = data.brandName;
        document.getElementById('officeName').textContent = data.officeName;
        document.getElementById('position').textContent = data.position;
        document.getElementById('name').textContent = data.name;
        document.getElementById('email').textContent = data.email;
        document.getElementById('idStoreManager').textContent = data.idStoreManager;
        document.getElementById('number').textContent = data.number;


    })
    .catch(error => console.error('Error:', error));
