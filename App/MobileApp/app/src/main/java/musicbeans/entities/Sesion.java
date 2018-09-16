package musicbeans.entities;

import musicbeans.dataaccess.Status;

public class Sesion{

    private static Sesion instance;
    private String username;
    private Status accounType;


    public static Sesion getInstance() {
        if(instance == null){
            instance = new Sesion();
        }
        return instance;
    }

    public static Sesion createInstance(String username){
        Sesion instance = getInstance();
        instance.setUsername(username);
        return instance;
    }

    public String getUsername() {
        return username;
    }

    public Status getAccounType() {
        return accounType;
    }

    private void setUsername(String username){
        this.username = username;
    }

    public void setAccounType(Status status){
        this.accounType = status;
    }

}
