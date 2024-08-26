package com.erp.mes.controller;

import com.erp.mes.dto.ItemDTO;
import com.erp.mes.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/item")
public class ItemController {

    @Autowired
    private ItemService itemService;

    @GetMapping("/detail/{id}")
    public String getItemById(@PathVariable("id") int id, Model model) {
        ItemDTO item = itemService.getItemById(id); //품목 상세 조회
        model.addAttribute("item", item);
        return "item/itemDetail"; // 상세 페이지 반환
    }

    @PostMapping("/delete/{id}")
    public String deleteItem(@PathVariable("id") int id) {
        itemService.deleteItem(id); // 삭제처리
        return "redirect:/item/itemList"; // 목록 리다이렉트 (스크립트로 삭제 예/아니오 팝업)
    }

    @GetMapping("/insert")
    public String showInsertForm(Model model) {
        model.addAttribute("item", new ItemDTO());
        return "item/itemInsert"; // 추가 폼 이동
    }

    @PostMapping("/save")
    public String saveItem(@ModelAttribute ItemDTO item) {
        itemService.saveItem(item); // 업데이트
        return "redirect:/item/itemList";
    }

    @GetMapping("/itemList")
    public String itemList(Model model) {
        List<ItemDTO> items = itemService.getAllItems(); // 목록조회
        model.addAttribute("items", items);
        return "item/itemList";
    }

    @PostMapping("/update/{id}")
    public String updateItem(@PathVariable("id") int id, @ModelAttribute ItemDTO item) {
        item.setItem_id(id);
        itemService.updateItem(item);
        return "redirect:/item/itemList";
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

