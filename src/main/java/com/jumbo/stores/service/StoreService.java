package com.jumbo.stores.service;

import com.jumbo.stores.dto.CreateStoreDto;
import com.jumbo.stores.dto.ReadStoreDto;
import com.jumbo.stores.dto.UpdateStoreDto;
import com.jumbo.stores.entity.Store;
import com.jumbo.stores.exception.RecordNotFoundException;
import com.jumbo.stores.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StoreService {
    private static final Logger logger = LoggerFactory.getLogger(StoreService.class);
    private static final String CACHE_NAME = "StoreCache";
    private final StoreRepository storeRepository;
    private final ModelMapper modelMapper;

    @CachePut(value = CACHE_NAME, key = "#result.uuid")
    public ReadStoreDto createStore(CreateStoreDto createStoreDto) {
        Store store = modelMapper.map(createStoreDto, Store.class);
        store.setUuid(UUID.randomUUID().toString());
        logger.info("Creating store: {}", store);
        storeRepository.save(store);
        return modelMapper.map(store, ReadStoreDto.class);
    }


    @Cacheable(value = CACHE_NAME, key = "#uuid")
    public ReadStoreDto getStoreById(String uuid) {
        Store store = this.findStoreEntityById(uuid);
        return modelMapper.map(store, ReadStoreDto.class);
    }

    public List<ReadStoreDto> getClosestStores(double latitude, double longitude, int limit) {
        logger.info("Fetching closes stores from DB.");
        List<Store> stores = storeRepository.findClosestStoresByDistance(latitude, longitude, limit);
        if (stores.isEmpty()) {
            return new ArrayList<>();
        }

        return stores.stream()
                .map(store -> modelMapper.map(store, ReadStoreDto.class))
                .collect(Collectors.toList());
    }


    @CachePut(value = CACHE_NAME, key = "#result.uuid")
    public ReadStoreDto updateStore(String uuid, UpdateStoreDto updateStoreDto) {
        Store store = this.findStoreEntityById(uuid);
        modelMapper.map(updateStoreDto, store);

        storeRepository.save(store);
        return modelMapper.map(store, ReadStoreDto.class);
    }

    @CacheEvict(value = CACHE_NAME, key = "#uuid")
    public void deleteStore(String uuid) {
        findStoreEntityById(uuid);
        logger.info("Deleting service with id: {}. Cache entry will be evicted.", uuid);
        storeRepository.deleteById(uuid);
    }

    public Store findStoreEntityById(String uuid) {
        return storeRepository.findById(uuid)
                .orElseThrow(() -> new RecordNotFoundException("Store not found with uuid: " + uuid));
    }
}