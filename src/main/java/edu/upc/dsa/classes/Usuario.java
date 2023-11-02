package edu.upc.dsa.classes;

import edu.upc.dsa.util.idGenerator;

import java.util.HashMap;

public class Usuario {

    private String id;
    String mail;
    String nombre;
    String contra;
    private HashMap<String, Comanda> comandas;

    public Usuario(){}
    public Usuario(String mail, String nombre, String contra) {
        this.setId(idGenerator.getId());
        this.setMail(mail);
        this.setContra(contra);
        this.setNombre(nombre);
        this.setComandas();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public HashMap<String, Comanda> getComandas() {
        return comandas;
    }

    public void setComandas() {
        this.comandas = new HashMap<>();
    }

    public void addComanda(String id, Comanda comanda){
        this.comandas.put(id, comanda);
    }
}
