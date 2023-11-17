new Chart(document.getElementById('acquisitions'), {
    type:'line',
    data:{
        labels: ['January','Feburary', 'March', 'April', 'May', 'June', 'July'],
        datasets: [{
            label:'My First Dataset',
            data:[65, 59, 80, 81, 56, 55, 40],
            borderColor: 'rgba(75, 192, 192, 1)',
            borderWidth:1,
            fill:false
        }]
    },
    options:{
        scales: {
            x:{
                beginAtZero:true
            },
            y:{
                beginAtZero: true
            }
        }
    }
});