$(document).ready(function() {
    // .check-item에 대한 change 이벤트를 바인딩
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

            $.ajax({
                url: 'item/seleteOneItem', // 요청할 URL
                type: 'GET', // 요청 방식 (GET, POST 등)
                dataType: 'json', // 서버에서 반환할 데이터 형식
                success: function(
                {
                   item_name : item_name,
                }
                ) {
                    // 요청 성공 시 호출되는 함수
                    console.log(data); // 받은 데이터 처리
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
            $('#orderModal').modal('show'); // 모달 열기
            updateTable(selectItem);
        } else {
            console.log('체크박스 미클릭 상태');
            alert("리스트를 선택해주세요.");
        }


    });

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
