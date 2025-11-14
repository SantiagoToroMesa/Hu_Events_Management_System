package com.manage_system.Events.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VenueDto {
    private int id;
    @NotBlank
    private String name;
    @NotBlank
    private String location;
    private int capacity;
}
