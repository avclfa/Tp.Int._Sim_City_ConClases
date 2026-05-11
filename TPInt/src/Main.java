import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    static Scanner leer = new Scanner(System.in);
    static Boolean valido = false;
    static int opcion;
    static Ciudad miCiudad;

    static List<Estructura> Estructuras = cargarDatos("C:\\Users\\HP\\IdeaProjects\\Juegos\\TPInt\\src\\edificios.txt");
    static List<Estructura> EdificiosComprados = new ArrayList<>();
    public static void main(String[] args) {

        System.out.println("¡Bienvenido a Sim City! Has sido seleccionado para ser alcalde de una ciudad");
        System.out.println("Complete el formulario: ");
        AsignarAlcalde();
        System.out.println("Listo! " + miCiudad.alcalde.nombre + " la ciudad es tuya.Se te otorgara un $1.000.000 para poder edificar " + miCiudad.nombre);
        CompraEdificios();
        while (!valido) {
            System.out.println("\n MENU DE " + miCiudad.nombre.toUpperCase());
            System.out.println("1-Listado del alcalde y edificios comprados");
            System.out.println("2-Promedio Seguridad");
            System.out.println("3-Promedio de prevencion de incendios");
            System.out.println("4-Promedio de felicidad");
            System.out.println("5-Edificio publico de mayor a menor valor");
            System.out.println("6-Dinero invertido");
            System.out.println("7-???");
            System.out.println("8-Salir de la ciudad");
            opcion = leer.nextInt();
            valido = true;
            switch (opcion) {
                case 1:
                    System.out.println("Datos de " + miCiudad.nombre);
                    System.out.println("Alcalde: " + miCiudad.alcalde.nombre + " " + miCiudad.alcalde.apellido);
                    cargarDatos("C:\\Users\\HP\\IdeaProjects\\Juegos\\TPInt\\src\\edificios.txt");
                    System.out.println("Edificios comprados: ");
                    for(Estructura e : EdificiosComprados){
                        System.out.println(e.tipo + " " + e.nombre + " | Precio: $" + e.precio);
                    }
                    valido = false;
                    break;
                case 2:
                    Ciudad.CalcularPromedio("seguridad");
                    valido=false;
                    break;
                case 3:
                    Ciudad.CalcularPromedio("incendios");
                    valido=false;
                    break;
                case 4:
                    Ciudad.CalcularPromedio("felicidad");
                    valido=false;
                    break;
                case 5:
                    Ciudad.EdificioValor();
                    valido=false;
                    break;
                case 6:
                    Ciudad.DineroInvertido();
                    valido=false;
                    break;
                case 7:
                    Ciudad.AporteEdificio();
                    valido=false;
                    break;
                case 8:
                    System.out.println("Saliendo de la ciudad...");
                    valido=true;
                    break;
                default:
                    System.out.println("Ingrese una opcion valida");
                    valido = false;
                    break;
            }
        }
    }

    public static List<Estructura> cargarDatos(String ruta) {
        List<Estructura> lista = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(ruta))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                linea = linea.replace("{", "").replace("}", "").replace("\"", "");
                if (linea.endsWith(",")) linea = linea.substring(0, linea.length() - 1);

                String[] p = linea.split(",");
                if (p.length == 6) {
                    lista.add(new Estructura(
                            p[0].trim(), p[1].trim(),
                            Integer.parseInt(p[2].trim()), Integer.parseInt(p[3].trim()),
                            Integer.parseInt(p[4].trim()), Integer.parseInt(p[5].trim())
                    ));
                }
            }
        } catch (IOException e) {
            System.out.println("Error al leer el archivo: " + e.getMessage());
        }
        return lista;
    }

    public static void AsignarAlcalde() {
        System.out.println("Dale un nombre a tu ciudad: ");
        String ciudad = leer.nextLine();
        System.out.println("Nombre: ");
        String nom = leer.next();
        System.out.println("Apellido: ");
        String ape = leer.next();
        Alcalde alc = new Alcalde(nom, ape);
        miCiudad = new Ciudad(ciudad, alc, 1000000, 0, 0 , 0);
    }

    public static void CompraEdificios() {
        boolean comprando = false;
        System.out.println(" *********** TIENDA ************");
        System.out.println("Solo puede comprar un edificio de cada tipo.");
        do {
            int cont = 1;
            String[] Tipos = { "Planta energética", "Planta de agua", "Seguridad", "Incendios", "Caminos", "Ecología"};
            for (String t : Tipos) {
                System.out.println("--- " + t.toUpperCase() + " ---");
                for (Estructura e : Estructuras) {
                    if (e.tipo.equals(t)) {
                        System.out.println(cont + " " + e.nombre + " | Precio: $" + e.precio);
                        cont += 1;
                    }
                }
            }
            System.out.println("Ingrese nro de edificio que desee comprar");
            String eleccion = leer.next().toUpperCase();
            if (eleccion.equals("TORG")) {
                miCiudad.presupuesto += 1000000;
                System.out.println("Truco activado, Saldo actual: $" + miCiudad.presupuesto);
            } else {
                int eleccion2 = Integer.parseInt(eleccion);
                if (eleccion2 > 0 && eleccion2 <= 26) {
                    Estructura Eleccion = Estructuras.get(eleccion2 + 4);
                    boolean yaComprado = false;
                    for (Estructura comprado : EdificiosComprados) {
                        if (comprado.tipo.equals(Eleccion.tipo)) {
                            yaComprado = true;
                            break;
                        }
                    }
                    if (yaComprado) {
                        System.out.println(" Ya has construido un edificio de tipo: " + Eleccion.tipo);
                    }
                    else if (Eleccion.precio <= miCiudad.presupuesto) {
                        miCiudad.presupuesto = miCiudad.presupuesto - Eleccion.precio;
                        System.out.println("Seleccionaste " + Eleccion.tipo + " " + Eleccion.nombre + ".Construyendo...");
                        EdificiosComprados.add(Eleccion);
                    }
                    else {
                        System.out.println("Saldo insuficiente. Saldo actual: " + miCiudad.presupuesto);
                    }
                    if (EdificiosComprados.size() == 6) {
                        boolean comprando2 = false;
                        System.out.println(" *********** TIENDA ************");
                        System.out.println("Compraste todos los edificios publicos necesarios para edificar " + miCiudad.nombre);
                        System.out.println("Antes de salir de la tienda, puede comprar edificios de tipo Maravilla");
                        do {
                            System.out.println("1-Mostrar menú Edificios Maravilla");
                            System.out.println("2-Salir de la tienda");
                            int opcion = leer.nextInt();
                            switch (opcion) {
                                case 1:
                                    int cont2 = 1;
                                    System.out.println("--- MARAVILLA ---");
                                    for (Estructura e : Estructuras) {
                                        if (e.tipo.equals("Maravilla")) {
                                            System.out.println(cont2 + " - " + e.nombre + " | Precio: $" + e.precio);
                                            cont2 += 1;
                                        }
                                    }
                                    System.out.println("Ingrese nro de edificio que desee comprar: ");
                                    eleccion = leer.next().toUpperCase();

                                    if (eleccion.equals("TORG")) {
                                        miCiudad.presupuesto += 1000000;
                                        System.out.println("Truco activado, Saldo actual: $" + miCiudad.presupuesto);
                                    } else if (eleccion.equals("2")) {
                                        comprando2 = true;
                                        comprando = true;
                                    } else {
                                        eleccion2 = Integer.parseInt(eleccion);
                                        if (eleccion2 > 0 && eleccion2 <= 5) {
                                            Eleccion = Estructuras.get(eleccion2 - 1);

                                            if (Eleccion.precio <= miCiudad.presupuesto) {
                                                miCiudad.presupuesto = miCiudad.presupuesto - Eleccion.precio;
                                                System.out.println("Seleccionaste " + Eleccion.tipo + " " + Eleccion.nombre + ".Construyendo...");
                                                EdificiosComprados.add(Eleccion);
                                            } else {
                                                System.out.println("Saldo insuficiente. Saldo actual: " + miCiudad.presupuesto);
                                            }
                                        } else {
                                            System.out.println("Opcion invalida");
                                        }
                                    }
                                    break;
                                case 2:
                                    comprando2 = true;
                                    comprando = true;
                                    break;
                                default:
                                    System.out.println("Opción inválida.");
                                    break;
                            }
                        } while (!comprando2);
                    }
                } else {
                    System.out.println("Número de edificio fuera de rango.");
                }
            }
        } while (!comprando);
    }

}