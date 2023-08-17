package be.challenge.dto;

import be.challenge.interfaces.ChangeType;

public class PropertyUpdate implements ChangeType {

    public PropertyUpdate add(AuditResult result) {
        resultList.add(
                "{" +
                        "  \"property\": \"" + result.getField() + "["+ result.getId() +"]\", " +
                        "  \"previous\": \"" + result.getPreviousValue().toString() + "\", " +
                        "  \"current\": \"" + result.getCurrentValue().toString() + "\"" +
                        "}"
        );

        return this;
    }
}
