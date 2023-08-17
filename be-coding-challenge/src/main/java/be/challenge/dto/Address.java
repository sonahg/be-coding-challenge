package be.challenge.dto;

import be.challenge.interfaces.Auditable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class Address implements Auditable {
    private String city;
    private String address;
}
