package com.jumbo.stores.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateStoreDto {
    @NotBlank(message = "City is mandatory")
    private String city;
    @NotBlank(message = "Postal Code is mandatory")
    private String postalCode;
    @NotNull(message = "Latitude is mandatory")
    private Double latitude;
    @NotNull(message = "Longitude is mandatory")
    private Double longitude;
    @NotBlank(message = "Address Name is mandatory")
    private String addressName;
    @NotBlank(message = "Street is mandatory")
    private String street;
    private String street2;
    private String street3;
    private String complexNumber;
    private Boolean showWarningMessage;
    private String todayOpen;
    private String locationType;
    private Boolean collectionPoint;
    private String sapStoreID;
    private String todayClose;
}
