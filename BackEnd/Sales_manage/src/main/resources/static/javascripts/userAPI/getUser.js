const apiUrl = '/user';

fetch(apiUrl)
    .then(response => response.json())
    .then(data => {
        if (data.position==="점주"){
            document.getElementById('brandName').textContent = data.brandName;
            document.getElementById('officeName').textContent = data.officeName;
            document.getElementById('position').textContent = data.position;
            document.getElementById('name').textContent = data.name;
        } else if(data.position==="매니저" || data.position==="관리자"){
            document.getElementById('brandName').textContent = data.brandName;
            document.getElementById('position').textContent = data.position;
            document.getElementById('name').textContent = data.name;
        }
        else {return null;}

    })
    .catch(error => console.error('Error:', error));
