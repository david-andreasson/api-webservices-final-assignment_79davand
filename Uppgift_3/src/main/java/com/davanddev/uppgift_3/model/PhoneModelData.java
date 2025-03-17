package com.davanddev.uppgift_3.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PhoneModelData {
    private Long id;
    private String modelName;
    private String manufacturer;
    private int releaseYear;
}
