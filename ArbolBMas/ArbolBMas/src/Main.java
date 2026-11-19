import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        ArbolBMas arbol = new ArbolBMas(4);

        arbol.insertar(10, "Diez");
        arbol.insertar(20, "Veinte");
        arbol.insertar(5, "Cinco");
        arbol.insertar(6, "Seis");
        arbol.insertar(15, "Quince");
        arbol.insertar(30, "Treinta");
        arbol.insertar(25, "Veinticinco");
        arbol.insertar(40, "Cuarenta");
        arbol.insertar(45, "Cuarenta y cinco");
        arbol.insertar(50, "Cincuenta");

        int opcion;

        do {
            System.out.println("\n--- MENU ARBOL B+ ---");
            System.out.println("1. Insertar clave y dato");
            System.out.println("2. Buscar clave");
            System.out.println("3. Eliminar clave");
            System.out.println("4. Recorrer por rango");
            System.out.println("5. Imprimir arbol");
            System.out.println("0. Salir");
            System.out.print("Elige una opcion: ");

            opcion = scanner.nextInt();

            switch (opcion) {
                case 1:
                    System.out.print("Clave (numero): ");
                    int clave = scanner.nextInt();
                    System.out.print("Dato (texto): ");
                    String dato = scanner.nextLine();

                    arbol.insertar(clave, dato);
                    System.out.println("Insertado correctamente.");
                    break;

                case 2:
                    System.out.print("Clave a buscar: ");
                    int claveBuscar = scanner.nextInt();

                    if (arbol.buscar(claveBuscar)) {
                        System.out.println("Encontrado -> Dato: " + arbol.buscarDato(claveBuscar));
                    } else {
                        System.out.println("No se encontro la clave.");
                    }
                    break;

                case 3:
                    System.out.print("Clave a eliminar: ");
                    int claveEliminar = scanner.nextInt();

                    if (arbol.eliminar(claveEliminar)) {
                        System.out.println("Clave eliminada.");
                    } else {
                        System.out.println("No se encontro la clave para eliminar.");
                    }
                    break;

                case 4:
                    System.out.print("Inicio del rango: ");
                    int inicio = scanner.nextInt();
                    System.out.print("Fin del rango: ");
                    int fin = scanner.nextInt();

                    arbol.recorrerRango(inicio, fin);
                    break;

                case 5:
                    arbol.imprimirArbol();
                    break;

                case 0:
                    System.out.println("Saliendo...");
                    break;

                default:
                    System.out.println("Opcion no valida.");
                    break;
            }

        } while (opcion != 0);

    }
}