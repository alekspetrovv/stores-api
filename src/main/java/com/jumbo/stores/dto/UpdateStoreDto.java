package com.jumbo.stores.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateStoreDto {
    private String city;
    private String postalCode;
    private String street;
    private String street2;
    private String street3;
    private String addressName;
    private Double longitude;
    private Double latitude;
    private String complexNumber;
    private Boolean showWarningMessage;
    private String todayOpen;
    private String locationType;
    private Boolean collectionPoint;
    private String sapStoreID;
    private String todayClose;
}
