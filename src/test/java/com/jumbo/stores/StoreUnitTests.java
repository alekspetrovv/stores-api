package com.jumbo.stores;

import com.jumbo.stores.dto.CreateStoreDto;
import com.jumbo.stores.dto.ReadStoreDto;
import com.jumbo.stores.dto.UpdateStoreDto;
import com.jumbo.stores.entity.Store;
import com.jumbo.stores.exception.RecordNotFoundException;
import com.jumbo.stores.repository.StoreRepository;
import com.jumbo.stores.service.StoreService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.eq;

@ExtendWith(MockitoExtension.class)
class StoreUnitTests {

    @Mock
    private StoreRepository storeRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private StoreService storeService;

    private Store store;
    private ReadStoreDto readStoreDto;
    private CreateStoreDto createStoreDto;

    @BeforeEach
    void setUp() {
        store = new Store();
        store.setUuid("test-uuid");
        store.setAddressName("Test Jumbo Store");
        store.setCity("Test City");

        readStoreDto = new ReadStoreDto();
        readStoreDto.setUuid("test-uuid");
        readStoreDto.setAddressName("Test Jumbo Store");

        createStoreDto = new CreateStoreDto();
        createStoreDto.setAddressName("New Jumbo Store");
    }

    @Test
    @DisplayName("createStore should generate a UUID and save the store")
    void createStore_shouldGenerateUuidAndSaveStore() {
        // Arrange
        when(modelMapper.map(any(CreateStoreDto.class), eq(Store.class))).thenReturn(store);
        when(storeRepository.save(any(Store.class))).thenReturn(store);
        when(modelMapper.map(any(Store.class), eq(ReadStoreDto.class))).thenReturn(readStoreDto);

        // Act
        ReadStoreDto result = storeService.createStore(createStoreDto);

        // Assert
        assertNotNull(result);
        assertEquals("test-uuid", result.getUuid());

        ArgumentCaptor<Store> storeArgumentCaptor = ArgumentCaptor.forClass(Store.class);
        verify(storeRepository).save(storeArgumentCaptor.capture());

        Store savedStore = storeArgumentCaptor.getValue();
        assertNotNull(savedStore.getUuid());
    }

    @Test
    @DisplayName("getStoreById should return store when found")
    void getStoreById_whenStoreExists_shouldReturnStoreDto() {
        // Arrange
        when(storeRepository.findById("test-uuid")).thenReturn(Optional.of(store));
        when(modelMapper.map(store, ReadStoreDto.class)).thenReturn(readStoreDto);

        // Act
        ReadStoreDto result = storeService.getStoreById("test-uuid");

        // Assert
        assertNotNull(result);
        assertEquals("test-uuid", result.getUuid());
        verify(storeRepository).findById("test-uuid");
    }

    @Test
    @DisplayName("getStoreById should throw RecordNotFoundException when not found")
    void getStoreById_whenStoreDoesNotExist_shouldThrowException() {
        // Arrange
        when(storeRepository.findById("non-existent-uuid")).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RecordNotFoundException.class, () -> {
            storeService.getStoreById("non-existent-uuid");
        });

        verify(storeRepository).findById("non-existent-uuid");
    }

    @Test
    @DisplayName("getClosestStores should return a list of stores")
    void getClosestStores_shouldReturnListOfStores() {
        // Arrange
        when(storeRepository.findClosestStoresByDistance(anyDouble(), anyDouble(), anyInt())).thenReturn(List.of(store));
        when(modelMapper.map(store, ReadStoreDto.class)).thenReturn(readStoreDto);

        // Act
        List<ReadStoreDto> results = storeService.getClosestStores(52.0, 5.0, 1);

        // Assert
        assertFalse(results.isEmpty());
        assertEquals(1, results.size());
        assertEquals("test-uuid", results.get(0).getUuid());
        verify(storeRepository).findClosestStoresByDistance(52.0, 5.0, 1);
    }

    @Test
    @DisplayName("getClosestStores should return an empty list when no stores are found")
    void getClosestStores_whenNoStoresFound_shouldReturnEmptyList() {
        // Arrange
        when(storeRepository.findClosestStoresByDistance(anyDouble(), anyDouble(), anyInt())).thenReturn(Collections.emptyList());

        // Act
        List<ReadStoreDto> results = storeService.getClosestStores(52.0, 5.0, 5);

        // Assert
        assertTrue(results.isEmpty());
        verify(storeRepository).findClosestStoresByDistance(52.0, 5.0, 5);
    }

    @Test
    @DisplayName("updateStore should save changes and return updated DTO")
    void updateStore_shouldSaveChangesAndReturnDto() {
        // Arrange
        UpdateStoreDto updateDto = new UpdateStoreDto();
        updateDto.setCity("Updated City");

        when(storeRepository.findById("test-uuid")).thenReturn(Optional.of(store));
        when(storeRepository.save(any(Store.class))).thenReturn(store);

        doNothing().when(modelMapper).map(any(UpdateStoreDto.class), any(Store.class));

        when(modelMapper.map(store, ReadStoreDto.class)).thenReturn(readStoreDto);

        // Act
        ReadStoreDto result = storeService.updateStore("test-uuid", updateDto);

        // Assert
        assertNotNull(result);
        verify(storeRepository).findById("test-uuid");
        verify(modelMapper).map(updateDto, store);
        verify(storeRepository).save(store);
    }

    @Test
    @DisplayName("deleteStore should call deleteById when store exists")
    void deleteStore_whenStoreExists_shouldCallDelete() {
        // Arrange
        when(storeRepository.findById("test-uuid")).thenReturn(Optional.of(store));
        doNothing().when(storeRepository).deleteById("test-uuid");

        // Act
        storeService.deleteStore("test-uuid");

        // Assert
        verify(storeRepository, times(1)).deleteById("test-uuid");
    }
}