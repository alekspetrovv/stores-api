package com.jumbo.stores.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReadStoreDto {
    private String uuid;
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
    private LocalDate createdAt;
    private LocalDate updatedAt;
}
