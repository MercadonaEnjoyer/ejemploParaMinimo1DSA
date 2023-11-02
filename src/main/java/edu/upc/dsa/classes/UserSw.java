package edu.upc.dsa.classes;

public class UserSw {
    String mail;
    String nombre;
    String contra;

    public UserSw(){}

    public UserSw(String mail, String nombre, String contra) {
        this.mail = mail;
        this.nombre = nombre;
        this.contra = contra;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getContra() {
        return contra;
    }

    public void setContra(String contra) {
        this.contra = contra;
    }
}
