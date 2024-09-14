$(document).ready(function() {
    $('.modal-btn').click(function() {
        // 클릭된 버튼의 부모 행을 찾음
        var row = $(this).closest('tr');

        // <th> 태그에서 값을 가져옴
        var orderCode = row.find('th:nth-child(2)').text();
        var itemName = row.find('th:nth-child(3)').text();
        var qty = row.find('th:nth-child(4)').text();
        var leadtime = row.find('th:nth-child(5)').text();
        var supName = row.find('th:nth-child(6)').text();

        // 모달 테이블에 데이터를 삽입
        var tableRow = `
            <tr>
                <td id="orderCode">${orderCode}</td>
                <td>${itemName}</td>
                <td>${qty}</td>
                <td>${leadtime}</td>
                <td>${supName}</td>
                <td style="text-align: center;">
                    <select class="form-select" name="inspectionStatus">
                        <option value="진행중">진행중</option>
                        <option value="완료">완료</option>
                    </select>
                </td>
                <input type="hidden" name="orderCode" value="${orderCode}">
            </tr>`;

        // 모달의 테이블에 데이터 추가
        $('.modal-table').html(tableRow);
    });
});
