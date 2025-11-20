package com.manage_system.Events.DTO;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventCreateDto {
    @NotBlank
    private String title;
    private String description;
    @NotNull  @Future
    private LocalDateTime startAt;
    @NotNull @Future
    private LocalDateTime endAt;
    @NotNull
    private int venueId;
}
