$(document).ready(function() {

    // .check-item에 대한 change 이벤트를 바인딩
    let supplierList = [];
    let selectItem = []; // 선택 리스트 값
    let checkData = false;
    let itemInfo = [];
    const checkedCount = $('.item-checkbox:checked').length;

    $('.check-item').on('change', function() {
        if (this.checked) {
            selectItem = []; // 값 초기화
            // 다른 체크박스는 체크 해제
            $('.check-item').not(this).prop('checked', false);

            // 체크된 체크박스의 데이터 가져오기
            const row = $(this).closest('tr');
            const plan_id = row.find('[data-plan_id]').text();
            const item_name = row.find('[data-item_name]').text();
            const qty = row.find('[data-qty]').text();
            const date = row.find('[data-date]').text();
            const leadtime = row.find('[data-leadtime]').text();
            const status = row.find('[data-status]').text();
            selectItem.push({plan_id,item_name,qty,date,leadtime,status});
            console.log(selectItem);
            // 선택한 리스트 아이템 가져오기
//            $.ajax({
//                url: 'item/selectItemById', // 요청할 URL
//                type: 'POST', // 요청 방식 (GET, POST 등)
//                data: JSON.stringify(dataObject), // 요청할 데이터 (예: 객체를 JSON 문자열로 변환)
//                contentType: 'application/json', // 요청 데이터 타입
//                dataType: 'json', // 서버에서 반환할 데이터 형식
//                success: function(response) {
//                    // 요청 성공 시 호출되는 함수
//                    console.log('응답 데이터:', response); // 받은 데이터 처리
//                    itemInfo = response.item;
//                },
//                error: function(xhr, status, error) {
//                    // 요청 실패 시 호출되는 함수
//                    console.error('오류 발생:', error);
//                }
//            });

            $.ajax({
                url: '/purchase/getAllSupplier', // 요청할 URL
                type: 'POST', // 요청 방식 (GET, POST 등)
                dataType: 'json', // 서버에서 반환할 데이터 형식
                success: function(response) {
                    // 요청 성공 시 호출되는 함수
                    supplierList = response.supList;
                    console.log(response); // 받은 데이터 처리
                },
                error: function(xhr, status, error) {
                    // 요청 실패 시 호출되는 함수
                    console.error('Error:', error);
                }
            });

            checkData = true;
        } else {
            selectItem = []; // 값 초기화
            checkData = false;
        }
    });

    $('#orderCreate').on('click', function() {
        // 체크박스 유효성검사
        if(checkData == true){
//            console.log('체크박스 클릭 상태');
            // Modal Open
            $('#orderModal').modal({
                backdrop: false // 백드롭 비활성화
            });
            $('#orderModal').modal('show'); // 모달 열기

            updateTable(selectItem);
        } else {
            console.log('체크박스 미클릭 상태');
            alert("리스트를 선택해주세요.");
        }
    });

    $('.closeBtn').on('click', function(){
        $('.modal-backdrop').remove();
        $("#orderModal").hide(); // 모달 닫기
    });
      // 이메일 발송 성공
//    $('#createOrder').on('click', function(){
//        $.ajax({
//            url: '/purchase/orderCreate', // 요청할 URL
//            type: 'POST', // 요청 방식 (GET, POST 등)
//            data: JSON.stringify({"targetMail": "soowkw@naver.com"}), // JSON 형식으로 데이터 전송
//            contentType: 'application/json', // JSON 형식으로 전송
//            dataType: 'json', // 서버에서 반환할 데이터 형식
//            success: function(data) {
//                console.log(data); // 받은 데이터 처리
//                alert("메일발송 성공");
//            },
//            error: function(xhr, status, error) {
//                console.error('Error:', error);
//                alert("메일발송 실패");
//            }
//        });
//    });
    // 협력업체 값 지정 및 선택 모달 스크립트
     $("#myBtn_in").click(function () {
       $('#searchModal').modal({
           backdrop: false // 백드롭 비활성화
       });
       displayPartners(supplierList);
       $('#searchModal').modal('show');

     });


    // 협력업체 데이터 (예시)
//    const partners = [
//        { code: "001", name: "회사 A" },
//        { code: "002", name: "회사 B" },
//        { code: "003", name: "회사 C" },
//    ];






    // 협력업체 리스트 표시
    function displayPartners(supList) {
        const tbody = $("#supplierList tbody");
        tbody.empty(); // 기존 데이터 제거

        supList.forEach(item=> {
           tbody.append(`
               <tr class="supplier-row" data-code="${item.sup_code}" data-name="${item.name}">
                   <td>${item.sup_code}</td>
                   <td>${item.name}</td>
               </tr>
           `)
        });
    }
    // 초기 데이터 표시
//    displayPartners(partners);

    // 검색 기능
    $("#searchInput").on("keyup", function () {
        const query = $(this).val().toLowerCase();
        const filteredPartners = supplierList.filter((partner) =>
            partner.name.toLowerCase().includes(query)
        );
        displayPartners(filteredPartners);
    });



    // 협력업체 선택
    $(document).on("click", ".partner-row", function () {
        const code = $(this).data("code");
        const name = $(this).data("name");
        $("#order_sup_name").val(`${name}`);
        $('.modal-backdrop').remove();
        $("#searchModal").hide(); // 모달 닫기
    });

    // 협력업체 모달 닫기 시



});

function updateTable(items){
    // 기존의 테이블 내용을 초기화
    const tableBody = $('#planModalTableBody'); // 모달 안의 테이블 본체 선택
    tableBody.empty(); // 테이블 내용 초기화

    // items 배열을 순회하여 테이블에 행 추가
    items.forEach(item => {
        const res = `<tr>
            <td>${item.plan_id}</td>
            <td>${item.item_name}</td>
            <td>${item.qty}</td>
            <td>${item.date}</td>
            <td>${item.leadtime}</td>
            <td>${item.status}</td>
        </tr>`;

        tableBody.append(res); // 테이블에 행 추가
    });

}
