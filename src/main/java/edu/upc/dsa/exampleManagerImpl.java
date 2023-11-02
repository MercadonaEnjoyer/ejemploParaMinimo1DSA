package edu.upc.dsa;

import edu.upc.dsa.Exceptions.exampleNoExisteException;
import edu.upc.dsa.Exceptions.usuarioNoExisteException;
import edu.upc.dsa.classes.Comanda;
import edu.upc.dsa.classes.Producto;
import edu.upc.dsa.classes.Usuario;
import edu.upc.dsa.classes.productoComanda;
import org.apache.log4j.Logger;

import java.util.*;
import java.util.stream.Collectors;

public class exampleManagerImpl implements exampleManager{

    private static exampleManager instance;
    protected Map<String, Usuario> lUsuarios;
    protected Queue<Comanda> cComandas;
    protected List<Producto> lProductos;

    final static Logger logger = Logger.getLogger(exampleManagerImpl.class);

    public exampleManagerImpl() {
        this.lUsuarios = new HashMap<>();
        this.cComandas = new LinkedList<>();
        this.lProductos = new LinkedList<Producto>();
    }

    public static exampleManager getInstance() {
        if (instance==null) instance = new exampleManagerImpl();
        return instance;
    }

    //--------------------------------------Productos------------------------------------------//

    public Producto getProducto(String prod) throws exampleNoExisteException{
        for(Producto producto : lProductos){
            if(producto.getNombre().equals(prod)){
                return producto;
            }
        }
        throw new exampleNoExisteException();
    }

    public List<Producto> getAllProducts(){
        return  sortList();
    }

    public void addProducto(String nombre, int precio){
        try{
            Producto name = getProducto(nombre);
            logger.info("producto ya existe" + name);
        }
        catch (exampleNoExisteException e){
            Producto p = new Producto(precio, nombre);
            lProductos.add(p);
        }
    }

    public int sizeProductos(){
        return this.lProductos.size();
    }

    public List<Producto> sortList(){
        lProductos.sort((Producto o1, Producto o2) -> Integer.compare(o1.getPrecio(),(o2.getPrecio())));
        for(Producto producto : lProductos){
            logger.info("Producto: " + producto.getNombre() + " - Precio: " + producto.getPrecio());
        }
        return lProductos;
    }

    public List<Producto> sortBestSellers(){
        lProductos.sort((Producto o1, Producto o2) -> Integer.compare(o2.getN(),(o1.getN())));
        for(Producto producto : lProductos){
            logger.info("Producto: " + producto.getNombre() + " - Veces comprado: " + producto.getN());
        }
        return lProductos;
    }

    //--------------------------------------Usuarios------------------------------------------//

    public Usuario getUser(String mail) throws usuarioNoExisteException{
        if(lUsuarios.get(mail) == null){
            throw new usuarioNoExisteException();
        }
        return lUsuarios.get(mail);
    }

    public List<Usuario> getAllUsers(){
        return this.lUsuarios.values().stream().collect(Collectors.toList());
    }

    public Usuario addUser(String mail, String name, String contra) {
        logger.info("Creating a new user: " + name);
        try{
            getUser(mail);
            logger.info("Correo ya en uso!");
        }catch (usuarioNoExisteException e){
            Usuario u = new Usuario(mail, name, contra);
            lUsuarios.put(mail, u);
            logger.info("Usuario creado con exito!");
            return u;
        }
        return null;
    }

    public Usuario addUser(Usuario u) {
        logger.info("Creating a new user: " + u.getNombre());
        try{
            getUser(u.getMail());
            logger.info("Correo ya en uso!");
            return null;
        }catch (usuarioNoExisteException e){
            lUsuarios.put(u.getMail(), u);
            logger.info("Usuario creado con exito!");
            return u;
        }
    }

    public LinkedList<Comanda> comandaCompletadaUser(Usuario u) throws usuarioNoExisteException{
        LinkedList<Comanda> comandasC = new LinkedList<>();
        logger.info("Comandas completadas de " + u.getNombre());
        for (Map.Entry<String, Comanda> set : u.getComandas().entrySet()) {
            if (set.getValue().isCompletado()) {
                comandasC.add(set.getValue());
                logger.info("id: " + set.getValue().getId());
            }
        }
        if (comandasC.isEmpty()){
            return null;
        }
        return comandasC;
    }

    public int sizeUsers(){
        logger.info("Numero de usuarios en la lista: " + this.lUsuarios.size());
        return this.lUsuarios.size();
    }

    //--------------------------------------Comandas------------------------------------------//

    public Comanda addComanda(LinkedList<productoComanda> productos, Usuario u) throws usuarioNoExisteException {
        int precio = 0;
        if(u == null){
            logger.info("Usuario no existe");
            return null;
        }
        Comanda comanda = new Comanda();
        logger.info("Generando comanda:");
        for(productoComanda producto : productos){
            logger.info("Producto: " + producto.getProducto().getNombre() + " - Precio: " + producto.getProducto().getPrecio());
            precio = precio + producto.getProducto().getPrecio()*producto.getCant();
            producto.getProducto().addN(producto.getCant());
            comanda.addProducto(producto.getProducto().getNombre(), producto);
        }
        comanda.setPrecioT(precio);
        cComandas.add(comanda);
        logger.info("Generada comanda con id: " + comanda.getId() + " - Precio comanda: " + precio);
        u.addComanda(comanda.getId(), comanda);
        return  comanda;
    }

    public void servirComanda(){
        Comanda c = cComandas.remove();
        logger.info("Comanda completada con id: " + c.getId());
        c.setCompletado();
    }
    public int sizeComanda(){
        logger.info("Numero de comandas en la lista: " + this.cComandas.size());
        return this.cComandas.size();
    }

}
