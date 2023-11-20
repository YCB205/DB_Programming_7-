class DuplicatedIdError extends Error {
    constructor(message) {
        super(message);
        this.name = "상품중복 추가 error!!";
    }
}

//중복 확인 호출 함수
function checkDuplicatedId(parentID, id) {
    let parentIdList = parentID;
    let childId = id;
    if (!parentIdList.length) {
        console.error('부모 요소를 찾을 수 없습니다.');
        return false;
    }
    //반복해서 id값 찾기
    //부모요소의 모든 자식 요소에서 반복
    parentIdList.find('[id]').each(function () {
        console.log(this.id);
        console.log(childId);
        //이미 Set에 있을 때(즉 중복이 될때)
        if (this.id.toString() === childId.toString()) {
            throw new DuplicatedIdError(`중복된 상품의 아이디: ${childId}`);
        } else {

        }

    })
}

$(window).on('load', function () {
    //모든 리소스가 로드 된 후에 실행되는 함수
    $('input[name="btnradio"]').change(function () {
        //라디오 버튼이 변경됐을 때 실행되는 함수
        //동적으로 url생성
        //카테고리들고 와서 post 신청
        const category = $(this).val();
        $('#querySearchScript').empty();
        const url = `https://dummyjson.com/products/category/${category}`
        //생성된 url로 ajax요청 보내기
        $.ajax({
            url: url,
            method: 'GET',
            contentType: 'application/json',
            success: function (data) {
                if (Array.isArray(data.products)) {
                    // product가 배열인 경우
                    data.products.forEach(function (item, index) {
                        addTableRow(index + 1, item.id, item.title, item.price);
                        console.log(item.id);
                    });
                } else {
                    // data가 배열이 아니라면 전체 data를 로그로 출력
                    console.log(data.products);
                }
            },
            error: function (error) {
                console.error('Error', error);
            }
        });
    });
});


//내용을 채워 넣는 함수
function addTableRow(index, id, title, price) {
    const tableBody = $('#querySearchScript');
    //홀수와 짝수 색 구분
    let newRow;
    if (index % 2 === 1)
        newRow = $("<tr>").addClass('table-light');
    else
        newRow = $("<tr>").addClass('table-success');
    newRow.append($("<th>").attr("scope", "row").text(id));
    newRow.append($("<td>").text(title));
    newRow.append($("<td>").text(price));
    console.log(name);
    const querySearchAfterScript = $('#querySearchAfterScript');
    //클릭 이벤트 리스너를 등록...
    newRow.on('dblclick', function () {
        try {
            alert('상품추가');
            checkDuplicatedId(querySearchAfterScript, id);
            let value = $(`
        <tr class="text-center">
            <th scope="row" class="fw-normal">${title}</th>
            <td class="m-0">
                <label for="${id}" style="display: none"></label>
                <input type="number" class="form-control text-center p-0" value="1" id="${id}" min="1">
            </td>
            <td>${price}</td>
            <td>@mdo</td>
        </tr>`);
            //값 등록
            addMap(id,price,1);
            calSum();
            value.find(`#${id}`).on('change',function () {
                const count = idPriceMap.get(id);
                count[1] = $(this).val();
                calSum();
            })
            querySearchAfterScript.append(value);
        } catch (e) {
            alert(e.name + '\n' + e.message);
        }
    });
    tableBody.append(newRow);
}

const idPriceMap = new Map();

//추가하는 함수
function addMap(id,price,count){
    idPriceMap.set(`${id}`,[price,count]);
}



//총합을 구하는 함수
function calSum() {
    let sum = 0;
    idPriceMap.forEach((value,key)=>{
        sum += key[0] * key[1];
    })

}