package com.erp.mes.controller;

import com.erp.mes.dto.ItemDTO;
import com.erp.mes.service.ItemService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/item")
public class ItemController {

    private final ItemService itemService;

    // 생성자 주입
    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping("/detail/{id}")
    public String getItemById(@PathVariable("id") int id, Model model) {
        ItemDTO item = itemService.getItemById(id); // 품목 상세 조회
        model.addAttribute("item", item);
        return "item/itemDetail"; // 상세 페이지 반환
    }

    @PostMapping("/delete/{id}")
    public String deleteItem(@PathVariable("id") int id, Model model) {
        int result = itemService.deleteItem(id); // 삭제 처리
        if (result > 0) {
            return "redirect:/item/itemList"; // 성공 시 목록 리다이렉트
        } else {
            model.addAttribute("errorMessage", "삭제에 실패했습니다.");
            return "item/itemDetail"; // 실패 시 상세 페이지로 돌아감
        }
    }

    @GetMapping("/insert")
    public String showInsertForm(Model model) {
        model.addAttribute("item", new ItemDTO());
        return "item/itemInsert"; // 추가 폼 이동
    }

    @PostMapping("/save")
    public String saveItem(@ModelAttribute ItemDTO item, Model model) {
        int result = itemService.saveItem(item); // 업데이트 또는 삽입 처리
        if (result > 0) {
            return "redirect:/item/itemList"; // 성공 시 목록 리다이렉트
        } else {
            model.addAttribute("errorMessage", "저장에 실패했습니다.");
            return "item/itemInsert"; // 실패 시 추가 폼으로 돌아감
        }
    }

    @GetMapping("/itemList")
    public String itemList(Model model) {
        List<ItemDTO> items = itemService.getAllItems(); // 목록 조회
        model.addAttribute("items", items);
        return "item/itemList";
    }

    @PostMapping("/update/{id}")
    public String updateItem(@PathVariable("id") int id, @ModelAttribute ItemDTO item, Model model) {
        item.setItem_id(id);
        int result = itemService.updateItem(item);
        if (result > 0) {
            return "redirect:/item/itemList"; // 성공 시 목록 리다이렉트
        } else {
            model.addAttribute("errorMessage", "수정에 실패했습니다.");
            return "item/itemEdit"; // 실패 시 수정 폼으로 돌아감
        }
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") int id, Model model) {
        ItemDTO item = itemService.getItemById(id);
        model.addAttribute("item", item);
        return "item/itemEdit";
    }

    @GetMapping("/search")
    public String searchItems(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "type", required = false) String type,
            @RequestParam(value = "minPrice", required = false) Double minPrice,
            @RequestParam(value = "maxPrice", required = false) Double maxPrice,
            Model model) {

        List<ItemDTO> items = itemService.searchItems(name, type, minPrice, maxPrice);
        model.addAttribute("items", items);
        return "item/itemList";  // 검색 결과를 표시할 페이지
    }
}
