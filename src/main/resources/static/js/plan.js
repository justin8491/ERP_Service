$(document).ready(function() {
    // .check-item에 대한 change 이벤트를 바인딩
    let selectItem = []; // 선택 리스트 값
    let checkData = false;
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
            
        } else {
            console.log('체크박스 미클릭 상태');
            alert("리스트를 선택해주세요.")
        }


    });

});
