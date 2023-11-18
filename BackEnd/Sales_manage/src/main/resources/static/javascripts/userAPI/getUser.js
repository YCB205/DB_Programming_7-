const apiUrl = '/user';

fetch(apiUrl)
    .then(response => response.json())
    .then(data => {
        if (data.position==="점주"){
            document.getElementById('brandName').textContent = data.brandName;
            document.getElementById('officeName').textContent = data.officeName;
            document.getElementById('position').textContent = data.position;
            document.getElementById('name').textContent = data.name;
            document.getElementById('email').textContent = data.email;
            document.getElementById('idStoreManager').textContent = data.idStoreManager;
            document.getElementById('number').textContent = data.number;
        } else if(data.position==="매니저"){
            document.getElementById('brandName').textContent = data.brandName;
            document.getElementById('position').textContent = data.position;
            document.getElementById('name').textContent = data.name;
            document.getElementById('email').textContent = data.email;
            document.getElementById('idStoreManager').textContent = data.idManager;
            document.getElementById('number').textContent = data.phoneNumber;
        } else {return null;}

    })
    .catch(error => console.error('Error:', error));
