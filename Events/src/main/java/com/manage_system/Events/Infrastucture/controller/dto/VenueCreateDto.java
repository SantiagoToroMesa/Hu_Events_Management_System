package com.manage_system.Events.Infrastucture.controller.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VenueCreateDto {
    @NotBlank
    private String name;
    @NotBlank
    private String location;
    private int capacity;

}
