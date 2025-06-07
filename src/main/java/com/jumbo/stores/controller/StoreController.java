package com.jumbo.stores.controller;

import com.jumbo.stores.dto.CreateStoreDto;
import com.jumbo.stores.dto.ReadStoreDto;
import com.jumbo.stores.dto.UpdateStoreDto;
import com.jumbo.stores.service.StoreService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/stores")
@RequiredArgsConstructor
public class StoreController {
    private final StoreService storeService;

    @PostMapping
    public ResponseEntity<ReadStoreDto> createStore(@Valid @RequestBody CreateStoreDto createStoreDto) {
        return new ResponseEntity<>(storeService.createStore(createStoreDto), HttpStatus.CREATED);
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<ReadStoreDto> getStoreById(@PathVariable String uuid) {
        return new ResponseEntity<>(storeService.getStoreById(uuid), HttpStatus.OK);
    }

    @GetMapping("/closest")
    public ResponseEntity<List<ReadStoreDto>> getAllStores(
            @RequestParam double latitude,
            @RequestParam double longitude,
            @RequestParam(defaultValue = "5") int limit
    ) {
        return new ResponseEntity<>(storeService.getClosestStores(latitude, longitude, limit), HttpStatus.OK);
    }

    @PutMapping("/{uuid}")
    public ResponseEntity<ReadStoreDto> updateStore(@PathVariable String uuid, @Valid @RequestBody UpdateStoreDto updateStoreDto) {
        return new ResponseEntity<>(storeService.updateStore(uuid, updateStoreDto), HttpStatus.OK);
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<Void> deleteStore(@PathVariable String uuid) {
        storeService.deleteStore(uuid);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
