package be.challenge.interfaces;

import java.util.HashSet;
import java.util.Set;

public interface ChangeType {
    Set<String> resultList = new HashSet<>();

    default void print() {
        for(String line : resultList){
            System.out.println(line);
        }
    }
}
