package be.challenge.dto;

import be.challenge.interfaces.Auditable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
public class SingleObject implements Auditable {
    private final String firstName;
    private final String lastName;
    private final Integer age;
    private Address address;
}
