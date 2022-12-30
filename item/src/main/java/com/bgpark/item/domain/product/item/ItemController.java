package com.bgpark.item.domain.product.item;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ItemController {

    private final ItemRepository itemRepository;

    @PostMapping("/items")
    public ResponseEntity create(@RequestBody Item request) {
        Item savedItem = itemRepository.save(request);
        return ResponseEntity.ok(savedItem);
    }

    @GetMapping("/items")
    public ResponseEntity findAll() {
        List<Item> items = itemRepository.findAll();
        return ResponseEntity.ok(items);
    }
}
