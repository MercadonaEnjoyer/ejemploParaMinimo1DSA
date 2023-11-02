package edu.upc.dsa.Service;

import edu.upc.dsa.Exceptions.exampleNoExisteException;
import edu.upc.dsa.Exceptions.usuarioNoExisteException;
import edu.upc.dsa.classes.*;
import edu.upc.dsa.exampleManager;
import edu.upc.dsa.exampleManagerImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import javax.ws.rs.*;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.LinkedList;
import java.util.List;

@Api(value = "/example", description = "Endpoint to example service")
@Path("/example")
public class exampleService {

   private exampleManager em;

   public exampleService() throws usuarioNoExisteException {
       this.em = exampleManagerImpl.getInstance();
       if(em.sizeUsers() == 0){
           this.em.addUser("SuperPG@correo.com", "SuperPG", "TuMadre");
           this.em.addUser("Jlarrinzal@correo.com", "Zipi", "1234");
           this.em.addUser("christianL@correo.com", "Zape", "1234");
       }
       if(em.sizeProductos() == 0){
           this.em.addProducto("Bocadillo", 3);
           this.em.addProducto("Cocacola", 1);
           this.em.addProducto("Croisant", 2);
       }

   }

   @GET
   @ApiOperation(value = "get productos ordenados por precio", notes = "Listado productos ordenados de forma ascendente por precio")
   @ApiResponses(value = {
           @ApiResponse(code = 201, message = "Successful", response = Producto.class, responseContainer = "List")
   })
   @Path("/productos")
   @Produces(MediaType.APPLICATION_JSON)
   public Response  getAllProducts(){
       List<Producto> productos = this.em.getAllProducts();
       GenericEntity<List<Producto>> entity = new GenericEntity<List<Producto>>(productos) {};
       return Response.status(201).entity(entity).build()  ;
   }

   @POST
   @ApiOperation(value = "registar un usuario", notes = "so no head?")
   @ApiResponses(value = {
           @ApiResponse(code = 201, message = "Successful", response = UserSw.class),
           @ApiResponse(code = 500, message = "Usuario ya registrado o faltan datos")
   })
   @Path("/users/{Correo}/{Nombre}/{Contra}")
   @Consumes(MediaType.APPLICATION_JSON)
   public Response addUser(@PathParam("Correo") String correo,@PathParam("Nombre") String nombre,@PathParam("Contra") String contra) throws usuarioNoExisteException {
       if (correo==null || nombre==null || contra==null)  return Response.status(500).build();
       Usuario u = this.em.addUser(correo,nombre,contra);
       return Response.status(201).entity(u).build();
   }

    @POST
    @ApiOperation(value = "Hacer comanda", notes = "Añadir comanda con el siguiente formato: producto cantidad producto cantidad")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response = Comanda.class),
            @ApiResponse(code = 500, message = "Error, usuario no registrado o incorrecto")
    })
    @Path("/comanda/{Correo}/{Contra}/{Comanda}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addComanda(@PathParam("Correo") String correo, @PathParam("Contra") String contra, @PathParam("Comanda") String comanda) throws usuarioNoExisteException, exampleNoExisteException {
        Usuario u = em.getUser(correo);
        if (correo == null || u == null || !u.getContra().equals(contra))  return Response.status(500).build();
        String[] lComanda = comanda.split(" ");
        LinkedList<productoComanda> productos = new LinkedList<>();
        int i = 0;
        while(i < lComanda.length){
            productoComanda p = new productoComanda(em.getProducto(lComanda[i]), Integer.parseInt(lComanda[i+1]));
            productos.add(p);
            i = i + 2;
        }
        Comanda c = em.addComanda(productos, u);
        return Response.status(201).entity(c).build();
    }

    @PUT
    @ApiOperation(value = "Servir una comanda", notes = "asdasd")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful"),
            @ApiResponse(code = 500, message = "Lista de comandas vacia")
    })
    @Path("/servirComanda")
    public Response servirComanda() {
       if (em.sizeComanda() == 0)
           return Response.status(500).build();
       em.servirComanda();
       return Response.status(201).build();
    }

    @GET
    @ApiOperation(value = "get comandas usuario", notes = "Listado comandas usuario")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response = Producto.class, responseContainer = "List"),
            @ApiResponse(code = 500, message = "Usuario no encontrado")
    })
    @Path("/comandasCompletadas/{Mail}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response  getComandasCompletadas(@PathParam("Mail") String m) throws usuarioNoExisteException {
       if (em.getUser(m) == null)
           return Response.status(500).build();
       LinkedList<Comanda> comandas = this.em.comandaCompletadaUser(em.getUser(m));
       GenericEntity<List<Comanda>> entity = new GenericEntity<List<Comanda>>(comandas) {};
       return Response.status(201).entity(entity).build()  ;
    }

    @GET
    @ApiOperation(value = "get productos ordenados por ventas", notes = "Listado productos ordenados de forma descendente por ventas")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response = Producto.class, responseContainer = "List")
    })
    @Path("/productosPorVentas")
    @Produces(MediaType.APPLICATION_JSON)
    public Response  sortProductosPorVenta(){
        List<Producto> productos = this.em.sortBestSellers();
        GenericEntity<List<Producto>> entity = new GenericEntity<List<Producto>>(productos) {};
        return Response.status(201).entity(entity).build()  ;
    }

}
