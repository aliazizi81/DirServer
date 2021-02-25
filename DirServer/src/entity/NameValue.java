
package entity;

import java.sql.Blob;


public class NameValue {
  
    
    private boolean isFile ;
    private String name;
    private String value;
    private Blob fValue;
    
    public NameValue(){}

    public NameValue(boolean isFile, String name, String value) {
        this.isFile = isFile;
        this.name = name;
        this.value = value;
    }

    public NameValue(boolean isFile, String name, String value, Blob fValue) {
        this.isFile = isFile;
        this.name = name;
        this.value = value;
        this.fValue = fValue;
    }

    public void setfValue(Blob fValue) {
        this.fValue = fValue;
    }

    public Blob getfValue() {
        return fValue;
    }

    public boolean isIsFile() {
        return isFile;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    public void setIsFile(boolean isFile) {
        this.isFile = isFile;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setValue(String value) {
        this.value = value;
    }
    
}
