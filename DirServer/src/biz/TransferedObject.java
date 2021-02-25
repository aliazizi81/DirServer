
package biz;


public class TransferedObject {
  
    
    private int command;
    private boolean isFile ;
    private String name;
    private String value;
    private String fValue;
    //private Byte[] a=new Byte[10];
    
    
    
    public TransferedObject(){}


    public TransferedObject(int command,boolean isFile, String name, String value, String fValue) {
        this.command=command;
        this.isFile = isFile;
        this.name = name;
        this.value = value;
        this.fValue = fValue;
        
    }
     public TransferedObject(int command) {
        
         this.command=command;
        
    }

    public void setfValue(String fValue) {
        this.fValue = fValue;
    }

    public String getfValue() {
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
    
    public void setCommand(int command) {
        this.command = command;
    }

    public int getCommand() {
        return command;
    }
}
