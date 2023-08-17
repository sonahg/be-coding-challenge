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
public class ObjectWithId implements Auditable {
    private final String id;
    private final String firstName;
    private final String lastName;
    private final Integer age;
    private Address address;

    public ObjectWithId(String id){
        this.id = id;
        firstName = "";
        lastName = "";
        age = 0;
    }
}
