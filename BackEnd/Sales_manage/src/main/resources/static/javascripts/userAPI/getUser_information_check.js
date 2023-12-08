const apiUrl = '/user';

fetch(apiUrl)
    .then(response => response.json())
    .then(data => {
        if (data.position==="점주"){
            document.getElementById('email').value = data.email;
            document.getElementById('idStoreManager').value = data.idStoreManager;
            document.getElementById('name').value = data.name;
            document.getElementById('phoneNumber').value = data.phoneNumber;
            document.getElementById('officeName').value = data.officeName;
        } else if(data.position==="매니저" || data.position==="관리자"){
            document.getElementById('brandName').value = data.brandName;
            document.getElementById('name').value = data.name;
            document.getElementById('email').value = data.email;
            document.getElementById('idManager').value = data.idManager;
            document.getElementById('phoneNumber').value = data.phoneNumber;
        } else {return null;}

    })
    .catch(error => console.error('Error:', error));
