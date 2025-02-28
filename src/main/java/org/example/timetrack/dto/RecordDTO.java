package org.example.timetrack.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class RecordDTO {

    private Long projectId;
    private String username;
    private LocalDateTime startTime;
    private LocalDateTime finishTime;
    private Long duration;
}
