package com.bgpark.item.domain.product.item;

import com.bgpark.item.domain.product.item.dto.ItemCreateDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/item-service")
@RequiredArgsConstructor
public class ItemController {

    private final ItemRepository itemRepository;

    @PostMapping("/items")
    public ResponseEntity<ItemCreateDto> create(@RequestBody ItemCreateDto request, @RequestHeader(value = "item-request", required = false) String itemHeader) {
        log.info("item header=" + itemHeader);
        Item savedItem = itemRepository.save(convertItem(request));
        return ResponseEntity.ok(ItemCreateDto.create(savedItem));
    }

    @GetMapping("/items")
    public ResponseEntity<List<ItemCreateDto>> findAll() {
        List<Item> items = itemRepository.findAll();
        return ResponseEntity.ok(items.stream()
                .map(item -> ItemCreateDto.create(item))
                .collect(Collectors.toList()));
    }

    @GetMapping("/items/{itemId}")
    public ResponseEntity<ItemSearchDto> findByItemId(@PathVariable Long itemId) {
        ItemSearchDto response = itemRepository.findById(itemId)
                .map(ItemSearchDto::of)
                .orElse(null);
        return ResponseEntity.ok(response);
    }

    private Item convertItem(ItemCreateDto request) {
        List<OptionGroup> groups = request.getGroups().stream()
                .map(group -> {
                    List<Option> options = group.getOptions().stream()
                            .map(option -> Option.create(option.getName(), option.getPrice(), option.getAmount()))
                            .collect(Collectors.toList());

                    OptionGroup optionGroup = OptionGroup.create(group.getName());

                    optionGroup.addOptions(options);
                    return optionGroup;
                }).collect(Collectors.toList());
        Item item = Item.create(request.getName(), request.getPrice(), request.getAmount());
        item.addGroup(groups);
        return item;
    }
}
