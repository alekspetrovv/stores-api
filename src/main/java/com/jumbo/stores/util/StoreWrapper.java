package com.jumbo.stores.util;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.jumbo.stores.entity.Store;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class StoreWrapper {
    @JsonProperty("stores")
    private List<Store> stores;
}
