package com.erp.mes.controller;

import com.erp.mes.dto.ItemDTO;
import com.erp.mes.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/item")
public class ItemController {

    private final ItemService itemService;

    // 품목 목록 조회
    @GetMapping("/list")
    public String selectItemList(@RequestParam(value = "keyword", required = false) String keyword, Model model) {
        List<ItemDTO> itemList = itemService.selectItemList(keyword);
        model.addAttribute("itemList", itemList);
        return "item/itemList";  // itemList.html로 반환
    }

    // 품목 등록 폼
    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("itemDTO", new ItemDTO());
        return "item/itemForm";
    }

    // 품목 등록
    @PostMapping("/add")
    public String insertItem(@ModelAttribute ItemDTO itemDTO, Model model) {
        try {
            int result = itemService.insertItem(itemDTO);
            return "redirect:/item/list";
        } catch (IllegalArgumentException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "item/itemForm";
        }
    }

    // 품목 수정 폼
    @GetMapping("/edit/{itemId}")
    public String showEditForm(@PathVariable("itemId") int itemId, Model model) {
        ItemDTO itemDTO = itemService.selectItemById(itemId);
        model.addAttribute("itemDTO", itemDTO);
        return "item/itemForm";
    }

    // 품목 수정
    @PostMapping("/edit")
    public String updateItem(@ModelAttribute ItemDTO itemDTO) {
        itemService.updateItem(itemDTO);
        return "redirect:/item/list";
    }

    // 품목 삭제
    @GetMapping("/delete/{itemId}")
    public String deleteItem(@PathVariable("itemId") int itemId) {
        itemService.deleteItem(itemId);
        return "redirect:/item/list";
    }

    // 품목 상세 조회
    @GetMapping("/view/{itemId}")
    public String viewItemDetails(@PathVariable("itemId") int itemId, Model model) {
        ItemDTO itemDTO = itemService.selectItemById(itemId);
        model.addAttribute("itemDTO", itemDTO);
        return "item/itemView"; // itemView.html로 반환
    }
}
