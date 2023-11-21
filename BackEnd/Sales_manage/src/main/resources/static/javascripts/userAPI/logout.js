function logout(){
    const apiUrl = '/logout';

    fetch(apiUrl)
        .then(response => response.json())
        .then(data => {
            console.log(data)
        })
        .catch(error => console.error('Error:', error));

    location.replace('http://localhost:8080/');
}