import java.util.ArrayList;
import java.util.List;

public class Ciudad {
    static String nombre;
    int seguridad;
    int prevencion;
    int felicidad;
    int presupuesto;
    Alcalde alcalde;

    public Ciudad(String nombre,Alcalde alcalde, int presupuesto,int seguridad, int prevencion, int felicidad) {
        this.nombre = nombre;
        this.seguridad = seguridad;
        this.prevencion = prevencion;
        this.felicidad = felicidad;
        this.presupuesto = 1000000;
        this.alcalde = alcalde;
    }
    public static void CalcularPromedio(String promedio){
        int suma =0;
        for(Estructura e : Main.EdificiosComprados){
            if (promedio.equals("seguridad")){
                suma+=e.seguridad;
            }
            else if(promedio.equals("incendios")){
                suma+=e.prevencion;
            }
            else if(promedio.equals("felicidad")){
                suma+=e.felicidad;
            }
        }
        double prom = (double) suma / Main.EdificiosComprados.size();
        System.out.println("El promedio de " + promedio + " de "+ nombre+ " es " + prom);
    }

    public static void EdificioValor(){
        List<Estructura> listaOrdenada = new ArrayList<>(Main.EdificiosComprados);

        for (int i = 0; i < listaOrdenada.size() - 1; i++) {
            for (int j = 0; j < listaOrdenada.size() - 1 - i; j++) {
                if (listaOrdenada.get(j).precio < listaOrdenada.get(j + 1).precio) {
                    Estructura aux = listaOrdenada.get(j);
                    listaOrdenada.set(j, listaOrdenada.get(j + 1));
                    listaOrdenada.set(j + 1, aux);
                }
            }
        }
        Estructura mayor = listaOrdenada.get(0);
        Estructura menor = listaOrdenada.get(listaOrdenada.size() - 1);
        System.out.println("El edificio que mas vale es: " + mayor.nombre + " | $" + mayor.precio );
        System.out.println("El edificio que menos vale es: " + menor.nombre + " |  $" + menor.precio);
    }

    public static void DineroInvertido(){
        int suma=0;
        for(Estructura e: Main.EdificiosComprados){
            suma+=e.precio;
        }
        System.out.println("Se invirtio un total de: " + suma);
    }
    public static void AporteEdificio(){
        List<Estructura> Felicidad = new ArrayList<>(Main.EdificiosComprados);
        for (int i = 0; i < Felicidad.size() - 1; i++) {
            for (int j = 0; j < Felicidad.size() - 1 - i; j++) {
                if (Felicidad.get(j).felicidad < Felicidad.get(j + 1).felicidad) {
                    Estructura aux = Felicidad.get(j);
                    Felicidad.set(j, Felicidad.get(j + 1));
                    Felicidad.set(j + 1, aux);
                }
            }
        }
        Estructura aportemax = Felicidad.get(0);
        Estructura aportemin = Felicidad.get(Felicidad.size() -1);
        System.out.println("El edificio con mayor aporte de felicidad es: " + aportemax + " | felicidad: " + aportemax.felicidad);
        System.out.println("El edificio con menor aporte de felicidad es: " + aportemin + " | felicidad: " + aportemin.felicidad);
    }

}