package be.challenge.tools;

import be.challenge.dto.Address;
import be.challenge.dto.ObjectWithId;
import be.challenge.dto.SingleObject;
import be.challenge.interfaces.ChangeType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DiffToolTest {

    @Test
    void diffSingleObject() throws Exception {
        SingleObject current = new SingleObject("Harold", "Gallardo", 37, new Address("Bogota", "123 Street"));
        SingleObject previous = new SingleObject("Harold", "Gallardo", 38, new Address("Bogota DC", "123 Street"));
        List<ChangeType> result = new DiffTool().diff(current, previous);

        for(ChangeType ct : result){
            ct.print();
        }

        Assertions.assertEquals(result.size(),2);
    }

    @Test
    void errorWithoutIdField() {
        List current = new ArrayList();
        List previous = new ArrayList();

        current.add(new SingleObject("name", "lastname", 77));

        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
           new DiffTool().diff(current, previous);
        });

        assertTrue(thrown.getMessage().contains("Object need the id attribute to can be processed"));

    }

    @Test
    void testAddedAndRemoved() throws Exception {
        List current = new ArrayList();
        List previous = new ArrayList();

        current.add(new ObjectWithId("1"));
        current.add(new ObjectWithId("2"));

        previous.add(new ObjectWithId("1"));
        previous.add(new ObjectWithId("3"));

        List<ChangeType> result = new DiffTool().diff(current, previous);

        for(ChangeType ct : result){
            ct.print();
        }

        Assertions.assertEquals(result.size(),2);

    }

    @Test
    void testDiffFromList() throws Exception {
        List current = new ArrayList();
        List previous = new ArrayList();

        current.add(new ObjectWithId("1", "name1", "lastName", 18));

        previous.add(new ObjectWithId("1", "name", "lastName", 18));


        List<ChangeType> result = new DiffTool().diff(previous,current);

        for(ChangeType ct : result){
            ct.print();
        }

        Assertions.assertEquals(result.size(),1);

    }
}