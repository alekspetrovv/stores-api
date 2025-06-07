package com.jumbo.stores.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jumbo.stores.entity.Store;
import com.jumbo.stores.repository.StoreRepository;
import com.jumbo.stores.util.StoreWrapper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {
    private static final Logger log = LoggerFactory.getLogger(DataLoader.class);
    private final StoreRepository storeRepository;

    @Override
    public void run(String... args) throws Exception {
        if (storeRepository.count() != 0) {
            log.info("Database already contains {} stores. Skipping data load.", storeRepository.count());
            return;
        }

        log.info("Database is empty. Loading stores from JSON...");
        Resource resource = new ClassPathResource("stores.json");
        ObjectMapper mapper = new ObjectMapper();
        try (InputStream inputStream = resource.getInputStream()) {
            StoreWrapper wrapper = mapper.readValue(inputStream, StoreWrapper.class);
            List<Store> savedStores = storeRepository.saveAll(wrapper.getStores());
            log.info("Successfully loaded {} stores into the database.", savedStores.size());
        } catch (Exception e) {
            log.error("Failed to read or load store data.", e);
            throw e;
        }
    }
}