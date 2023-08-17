package be.challenge.dto;

import be.challenge.interfaces.ChangeType;

public class ListUpdate implements ChangeType {


    public ListUpdate add(AuditListResult result){
        resultList.add(
                "{" +
                        "  \"id\": \"" + result.getId() + "\", " +
                        "  \"status\": \"" +  result.getStatus() + "\" " +
                        "}"
        );

        return this;
    }
}
