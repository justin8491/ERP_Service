$(document).ready(function() {

    // .check-item에 대한 change 이벤트를 바인딩
    let supplierList = [];
    let selectItem = []; // 선택 리스트 값
    let checkData = false;

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
            selectItem.push({ plan_id, item_name, qty, date, leadtime, status });
            console.log(selectItem);

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
        if (checkData) {
            // Modal Open
            $('#orderModal').modal({
                backdrop: false // 백드롭 비활성화
            });
            $('#orderModal').modal('show'); // 모달 열기

            updateTable(selectItem);
        } else {
            alert("리스트를 선택해주세요.");
        }
    });

    $('.closeBtn').on('click', function(){
        $("#orderModal").hide(); // 모달 닫기
    });

    // 발주서 발행
    $('#createOrder').on('click', function(){
        const orderData = {
            sup_id: $("#order_sup_id").val(),
            sup_name: $("#order_sup_name").val(), // 협력업체 이름
            item_name: $("#order_item_name").val(), // 품목 이름
            quantity: $("#order_qty").val(), // 수량
            order_date: $("#order_date").val(), // 주문 날짜
            lead_time: $("#order_leadtime").val() // 리드타임
        };

        // AJAX 요청 (주석 해제 후 사용)
        /*
        $.ajax({
            url: '/insertOrder', // 주문 삽입을 처리하는 서버의 URL
            type: 'POST',
            contentType: 'application/json',
            data: JSON.stringify(orderData),
            success: function (response) {
                alert("주문이 성공적으로 추가되었습니다.");
                $("#orderModalTableBody").find("input").val(''); // 입력 필드 초기화
            },
            error: function (xhr, status, error) {
                alert("주문 추가에 실패했습니다: " + error);
            }
        });
        */
    });

    // 협력업체 값 지정 및 선택 모달 스크립트
    $("#myBtn_in").click(function () {
       $('#searchModal').modal({
           backdrop: false // 백드롭 비활성화
       });
       displayPartners(supplierList);
       $('#searchModal').modal('show');
    });

    // 협력업체 리스트 표시
    function displayPartners(supList) {
        const tbody = $("#supplierList tbody");
        tbody.empty(); // 기존 데이터 제거

        supList.forEach(item => {
           tbody.append(`
               <tr class="supplier-row" data-id="${item.sup_id}" data-code="${item.sup_code}" data-name="${item.name}">
                   <td>${item.sup_id}</td>
                   <td>${item.sup_code}</td>
                   <td>${item.name}</td>
               </tr>
           `);
        });
    }

    // 검색 기능
    $("#searchInput").on("keyup", function () {
        const query = $(this).val().toLowerCase();
        const filteredPartners = supplierList.filter(supplier =>
            supplier.name.toLowerCase().includes(query)
        );
        displayPartners(filteredPartners);
    });

    // 협력업체 선택
    $(document).on("click", ".supplier-row", function () {
        const id = $(this).data("id");
        const code = $(this).data("code");
        const name = $(this).data("name");
        $("#order_sup_id").val(id);
        $("#order_sup_name").val(name);
        $("#searchModal").hide(); // 모달 닫기
    });

});

// 테이블 업데이트 함수
function updateTable(items){
    const tableBody = $('#planModalTableBody'); // 모달 안의 테이블 본체 선택
    tableBody.empty(); // 테이블 내용 초기화

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
