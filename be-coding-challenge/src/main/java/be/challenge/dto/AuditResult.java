package be.challenge.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
public class AuditResult {
    private final String field;
    private final Object previousValue;
    private final Object currentValue;
    private String id;
}
