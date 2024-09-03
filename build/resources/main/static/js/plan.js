$(document).ready(function() {
    $('#orderCreate').on('click', function() {
        console.log('클릭');

        $('.check-item').each(function() {
            $(this).on('change', function() {
                if (this.checked) {
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

                    console.log({
                        plan_id,
                        item_name,
                        qty,
                        date,
                        leadtime,
                        status
                    });
                }
            });
        });
    });
});
