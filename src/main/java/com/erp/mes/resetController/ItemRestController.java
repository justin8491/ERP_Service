package com.erp.mes.resetController;

import com.erp.mes.dto.ItemDTO;
import com.erp.mes.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/item")
public class ItemRestController {

    private final ItemService itemService;

    // 품목 목록 조회
    @GetMapping("/list")
    public ResponseEntity<List<ItemDTO>> selectItemList(
            @RequestParam(value = "keyword", required = false) String keyword,
            @RequestParam(value = "supplier_name", required = false) String supplierName,
            @RequestParam(value = "startDate", required = false) String startDate,
            @RequestParam(value = "endDate", required = false) String endDate) {

        // 서비스 호출 및 필터링 결과 전달
        List<ItemDTO> itemList = itemService.selectItemList(keyword, supplierName, startDate, endDate);
        return ResponseEntity.ok(itemList);
    }

    // 품목 등록
    @PostMapping("/add")
    public ResponseEntity<String> insertItem(@RequestBody ItemDTO itemDTO) {
        try {
            itemService.insertItem(itemDTO);
            return ResponseEntity.ok("Item added successfully");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // 품목 수정
    @PutMapping("/edit")
    public ResponseEntity<String> updateItem(@RequestBody ItemDTO itemDTO) {
        itemService.updateItem(itemDTO);
        return ResponseEntity.ok("Item updated successfully");
    }

    // 품목 삭제
    @DeleteMapping("/delete/{itemId}")
    public ResponseEntity<String> deleteItem(@PathVariable("itemId") int itemId) {
        itemService.deleteItem(itemId);
        return ResponseEntity.ok("Item deleted successfully");
    }

    // 품목 상세 조회
    @GetMapping("/view/{itemId}")
    public ResponseEntity<ItemDTO> viewItemDetails(@PathVariable("itemId") int itemId) {
        ItemDTO itemDTO = itemService.selectItemById(itemId);
        if (itemDTO != null) {
            return ResponseEntity.ok(itemDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
