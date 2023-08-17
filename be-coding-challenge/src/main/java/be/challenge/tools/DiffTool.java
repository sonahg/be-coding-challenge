package be.challenge.tools;

import be.challenge.dto.AuditListResult;
import be.challenge.dto.AuditResult;
import be.challenge.dto.ListUpdate;
import be.challenge.dto.PropertyUpdate;
import be.challenge.interfaces.Auditable;
import be.challenge.interfaces.ChangeType;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class DiffTool {

    private List<ChangeType> result = new ArrayList<>();
    private boolean fromList = false;

    public List<ChangeType> diff(Object previous, Object current) throws Exception{
        if (current instanceof List) {
            fromList = true;
            checkIdField((List) current);
            checkIdField((List) previous);
            checkAdded((List) current, (List) previous);
            checkRemoved((List) current, (List) previous);
            checkDiff((List) current, (List) previous);

        } else if (previous instanceof Auditable && current instanceof Auditable){
            diffObject(previous, current);
        } else {
          throw new IllegalArgumentException("class has to implement Auditable interface to can be processed");
        }

        return result;
    }

    private void checkAdded(List current, List previous) throws Exception {
        for(Object currentItem : current){
            boolean isAdded = true;
            Field idField = currentItem.getClass().getDeclaredField("id");
            idField.setAccessible(true);
            Object cValue = idField.get(currentItem);
            for(Object previousItem : previous){
                Object pValue = idField.get(previousItem);
                if(pValue == cValue){
                    isAdded = false;
                }
            }

            if(isAdded){
                result.add(new ListUpdate().add(new AuditListResult(cValue.toString(), "ADDED")));
            }
        }
    }

    private void checkRemoved(List current, List previous) throws Exception {
        for(Object currentItem : previous){
            boolean isRemoved = true;
            Field idField = currentItem.getClass().getDeclaredField("id");
            idField.setAccessible(true);
            Object pValue = idField.get(currentItem);
            for(Object previousItem : current){
                Object cValue = idField.get(previousItem);
                if(pValue == cValue){
                    isRemoved = false;
                }
            }

            if(isRemoved){
                result.add(new ListUpdate().add(new AuditListResult(pValue.toString(), "REMOVED")));
            }
        }
    }

    private void checkDiff(List current, List previous) throws Exception {
        for(Object currentItem : current){
            Field idField = currentItem.getClass().getDeclaredField("id");
            idField.setAccessible(true);
            Object pValue = idField.get(currentItem);
            for(Object previousItem : previous){
                Object cValue = idField.get(previousItem);
                if(pValue == cValue){
                    diffObject(previousItem, currentItem);
                }
            }
        }
    }

    private void checkIdField(List list) throws IllegalArgumentException{
        try {
            for(Object item: list){
                item.getClass().getDeclaredField("id");
            }
        } catch (NoSuchFieldException noField) {
            throw new IllegalArgumentException("Object need the id attribute to can be processed");
        }
    }

    private void diffObject(Object previous, Object current){
        diffObject(previous, current, "");
    }

    private void diffObject(Object previous, Object current, String path) {
        try {
            for (Field cField : current.getClass().getDeclaredFields()) {
                cField.setAccessible(true);

                if (! compare(cField, previous, current)) {
                    if(fromList){
                        result.add(new PropertyUpdate().add(new AuditResult(path + cField.getName(), getValue(cField, previous), getValue(cField, current), getIdValue(current))));
                    }else {
                        result.add(new PropertyUpdate().add(new AuditResult(path + cField.getName(), getValue(cField, previous), getValue(cField, current))));
                    }
                }
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private String getIdValue(Object current) throws Exception {
        Field idField = current.getClass().getDeclaredField("id");
        idField.setAccessible(true);
        return idField.get(current).toString();
    }

    private boolean compare(Field cField, Object previous, Object current) throws IllegalAccessException {
        Object cValue = getValue(cField, current);
        Object pValue = getValue(cField, previous);
        if( cValue instanceof Auditable) {
            diffObject(pValue, cValue, cField.getName() + ".");
            return true;
        } else {
            return cValue == pValue;
        }
    }

    private Object getValue(Field field, Object object) throws IllegalAccessException {
        return field.get(object);
    }
}
