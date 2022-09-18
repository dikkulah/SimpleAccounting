package com.dikkulah.accountservice.dto;

import com.dikkulah.accountservice.model.enums.ActivityType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ActivityDto implements Serializable {
    private UUID id;
    private LocalDateTime time;
    private BigDecimal amount;
    private ActivityType activityType;
    private String description;
}
