public class Estructura {
    String nombre, tipo;
    int seguridad, prevencion, felicidad, precio;

    public Estructura(String nombre, String tipo, int seguridad, int prevencion, int felicidad, int precio) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.seguridad = seguridad;
        this.prevencion = prevencion;
        this.felicidad = felicidad;
        this.precio = precio;
    }

    @Override
    public String toString() {
        return nombre + " (" + tipo + ") - Precio: $" + precio;
    }
}
