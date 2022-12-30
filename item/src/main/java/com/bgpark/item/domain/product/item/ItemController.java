package com.bgpark.item.domain.product.item;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class ItemController {

    private final ItemRepository itemRepository;

    @PostMapping("/items")
    public ResponseEntity<ItemCreateDto> create(@RequestBody ItemCreateDto request) {
        Item savedItem = itemRepository.save(request.toItem());
        return ResponseEntity.ok(ItemCreateDto.create(savedItem));
    }

    @GetMapping("/items")
    public ResponseEntity<List<ItemCreateDto>> findAll() {
        List<Item> items = itemRepository.findAll();
        return ResponseEntity.ok(items.stream()
                .map(item -> ItemCreateDto.create(item))
                .collect(Collectors.toList()));
    }
}
