package edu.upc.dsa.classes;

public class productoComanda {
    Producto producto;
    int cant;

    public productoComanda(Producto producto, int cant) {
        this.producto = producto;
        this.cant = cant;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public int getCant() {
        return cant;
    }

    public void setCant(int cant) {
        this.cant = cant;
    }
}
