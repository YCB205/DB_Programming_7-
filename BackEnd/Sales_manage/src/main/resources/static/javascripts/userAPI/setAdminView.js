 function setAdmin() {
     // id가 position인 span 태그 가져오기
     var positionSpan = document.getElementById("position");

     // 관리자 여부 확인 후 처리
     if (positionSpan && positionSpan.innerText === '관리자') {
         console.log(positionSpan.innerText)
         // "관리자"일 경우: adminView 클래스를 가진 모든 태그를 보이게 설정
         var adminElements = document.querySelectorAll(".adminView");
         adminElements.forEach(function (element) {
             element.style.display = "block";
         });
     } else {
         console.log(positionSpan.innerText)
         // "관리자"가 아닐 경우: adminView 클래스를 가진 모든 태그를 숨기게 설정
         var adminElements = document.querySelectorAll(".adminView");
         adminElements.forEach(function (element) {
             element.style.display = "none";
         });
     }
 }
