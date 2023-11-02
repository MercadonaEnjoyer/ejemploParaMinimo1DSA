package edu.upc.dsa;

import edu.upc.dsa.Exceptions.exampleNoExisteException;
import edu.upc.dsa.Exceptions.usuarioNoExisteException;
import edu.upc.dsa.classes.Comanda;
import edu.upc.dsa.classes.Producto;
import edu.upc.dsa.classes.Usuario;
import edu.upc.dsa.classes.productoComanda;

import java.util.LinkedList;
import java.util.List;

public interface exampleManager {

    public void addProducto(String nombre, int precio);
    public List<Producto> getAllProducts();
    public Producto getProducto(String prod) throws exampleNoExisteException;
    public List<Producto> sortBestSellers();
    public int sizeProductos();
    public List<Producto> sortList();
    public Usuario addUser(String mail, String name, String contra) throws usuarioNoExisteException;
    public Usuario addUser(Usuario u);
    public List<Usuario> getAllUsers();
    public int sizeUsers();
    public Usuario getUser(String mail) throws usuarioNoExisteException;
    public Comanda addComanda(LinkedList<productoComanda> productos, Usuario u) throws usuarioNoExisteException;
    public int sizeComanda();
    public void servirComanda();
    public LinkedList<Comanda> comandaCompletadaUser(Usuario u)throws usuarioNoExisteException;


}
