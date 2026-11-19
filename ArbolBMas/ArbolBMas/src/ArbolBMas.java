import java.util.ArrayList;
import java.util.List;

public class ArbolBMas {

    // Atributos
    private final int orden;
    private NodoArbolBMas raiz;

    // Métodos
    // Constructor
    public ArbolBMas(int orden) {
        if (orden < 3) {
            throw new IllegalArgumentException("El orden del árbol B+ debe ser al menos 3.");
        }
        this.orden = orden;
        raiz = new NodoArbolBMas(true);
    }

    // Inserción sobrecargada solo con clave
    public void insertar(int clave) {
        insertar(clave, "");
    }

    // Inserción de una llave y su dato en el árbol B+
    public void insertar(int clave, String dato) {
        insertarRecursivo(this.raiz, clave, dato);

        // Se verifica si la raíz se desbordó
        if (this.raiz.getClaves().size() == orden) {
            NodoArbolBMas nuevaRaiz = new NodoArbolBMas(false);
            nuevaRaiz.getHijos().add(this.raiz);
            dividirHijo(nuevaRaiz, 0);
            this.raiz = nuevaRaiz;
        }
    }

    // Inserción recursiva de una llave y su dato
    private void insertarRecursivo(NodoArbolBMas nodo, int llave, String dato) {
        int i = nodo.getClaves().size() - 1;

        if (nodo.esHoja()) {
            while (i >= 0 && llave < nodo.getClaves().get(i)) {
                i--;
            }
            // Si la clave ya existe en la hoja, actualiza su dato
            if (i >= 0 && nodo.getClaves().get(i) == llave) {
                nodo.getDatos().set(i, dato);
                return;
            }
            nodo.getClaves().add(i + 1, llave);
            nodo.getDatos().add(i + 1, dato);
        } else {
            while (i >= 0 && llave < nodo.getClaves().get(i)) {
                i--;
            }
            i++;
            NodoArbolBMas hijo = nodo.getHijos().get(i);

            insertarRecursivo(hijo, llave, dato);

            // Evaluación de desbordamiento al retornar de la recursividad
            if (hijo.getClaves().size() == orden) {
                dividirHijo(nodo, i);
            }
        }
    }

    // Dividir un nodo que está lleno
    private void dividirHijo(NodoArbolBMas padre, int indice) {
        NodoArbolBMas nodoLleno = padre.getHijos().get(indice);
        NodoArbolBMas nuevoNodo = new NodoArbolBMas(nodoLleno.esHoja());
        int mitad = orden / 2;

        if (nodoLleno.esHoja()) {
            int total = nodoLleno.getClaves().size();

            // Dividir claves manteniendo la llave media en el nuevo nodo (hoja derecha)
            nuevoNodo.setClaves(new ArrayList<>(nodoLleno.getClaves().subList(mitad, total)));
            nodoLleno.setClaves(new ArrayList<>(nodoLleno.getClaves().subList(0, mitad)));

            // Dividir los datos en correspondencia exacta con las claves
            nuevoNodo.setDatos(new ArrayList<>(nodoLleno.getDatos().subList(mitad, total)));
            nodoLleno.setDatos(new ArrayList<>(nodoLleno.getDatos().subList(0, mitad)));

            // Enlazar hojas adyacentes
            nuevoNodo.setSiguiente(nodoLleno.getSiguiente());
            nodoLleno.setSiguiente(nuevoNodo);

            // El padre recibe como separador la primera llave del nuevo nodo
            int llaveMedia = nuevoNodo.getClaves().get(0);
            padre.getClaves().add(indice, llaveMedia);
            padre.getHijos().add(indice + 1, nuevoNodo);
        } else {
            int totalClaves = nodoLleno.getClaves().size();
            int totalHijos = nodoLleno.getHijos().size();

            // En nodos internos, la llave media sube y se elimina del hijo
            int claveMedia = nodoLleno.getClaves().get(mitad);
            nuevoNodo.setClaves(new ArrayList<>(nodoLleno.getClaves().subList(mitad + 1, totalClaves)));
            nodoLleno.setClaves(new ArrayList<>(nodoLleno.getClaves().subList(0, mitad)));

            // Mover los hijos del nodo dividido
            nuevoNodo.setHijos(new ArrayList<>(nodoLleno.getHijos().subList(mitad + 1, totalHijos)));
            nodoLleno.setHijos(new ArrayList<>(nodoLleno.getHijos().subList(0, mitad + 1)));

            padre.getClaves().add(indice, claveMedia);
            padre.getHijos().add(indice + 1, nuevoNodo);
        }
    }

    // Impresión del árbol como parte de la interfaz pública del árbol
    public void imprimirArbol() {
        imprimirNodo(raiz, "", true);
    }

    // Impresión recursiva privada de los nodos a partir de uno inicial
    private void imprimirNodo(NodoArbolBMas nodo, String indentacion, boolean esUltimo) {
        if (nodo.esHoja()) {
            List<String> pares = new ArrayList<>();
            for (int j = 0; j < nodo.getClaves().size(); j++) {
                pares.add(nodo.getClaves().get(j) + ":" + nodo.getDatos().get(j));
            }
            System.out.println(indentacion + "+- Hoja > " + pares);
        } else {
            System.out.println(indentacion + "+- Interno > " + nodo.getClaves());
        }

        indentacion += esUltimo ? "   " : "|  ";
        for (int i = 0; i < nodo.getHijos().size(); i++) {
            imprimirNodo(nodo.getHijos().get(i), indentacion, i == (nodo.getHijos().size() - 1));
        }
    }

    // Búsqueda de una clave en el árbol B+
    public boolean buscar(int llave) {
        return buscarHoja(raiz, llave) != null;
    }

    // Búsqueda del dato asociado a una clave
    public String buscarDato(int llave) {
        NodoArbolBMas hoja = buscarHoja(raiz, llave);
        if (hoja == null) {
            return null;
        }
        int pos = indiceClaveEnHoja(hoja, llave);
        return (pos != -1) ? hoja.getDatos().get(pos) : null;
    }

    // Baja hasta la hoja correspondiente a la llave
    private NodoArbolBMas buscarHoja(NodoArbolBMas nodo, int llave) {
        if (nodo.esHoja()) {
            return indiceClaveEnHoja(nodo, llave) != -1 ? nodo : null;
        }
        int i = 0;
        while (i < nodo.getClaves().size() && llave >= nodo.getClaves().get(i)) {
            i++;
        }
        return buscarHoja(nodo.getHijos().get(i), llave);
    }

    // Encuentra la hoja donde comenzaría un rango dado
    private NodoArbolBMas encontrarHojaInicio(NodoArbolBMas nodo, int llave) {
        if (nodo.esHoja()) {
            return nodo;
        }
        int i = 0;
        while (i < nodo.getClaves().size() && llave >= nodo.getClaves().get(i)) {
            i++;
        }
        return encontrarHojaInicio(nodo.getHijos().get(i), llave);
    }

    private int indiceClaveEnHoja(NodoArbolBMas hoja, int llave) {
        for (int i = 0; i < hoja.getClaves().size(); i++) {
            if (hoja.getClaves().get(i) == llave) {
                return i;
            }
        }
        return -1;
    }

    // Eliminación de una clave y su dato en la hoja correspondiente
    public boolean eliminar(int clave) {
        NodoArbolBMas actual = raiz;
        while (!actual.esHoja()) {
            int i = 0;
            while (i < actual.getClaves().size() && clave >= actual.getClaves().get(i)) {
                i++;
            }
            actual = actual.getHijos().get(i);
        }

        int pos = indiceClaveEnHoja(actual, clave);
        if (pos == -1) {
            return false;
        }
        actual.getClaves().remove(pos);
        actual.getDatos().remove(pos);
        return true;
    }

    // Recorrido por rango [inicio, fin] mostrando clave y dato
    public void recorrerRango(int inicio, int fin) {
        if (inicio > fin) {
            System.out.println("Rango inválido: el inicio es mayor que el fin.");
            return;
        }

        NodoArbolBMas hojaActual = encontrarHojaInicio(raiz, inicio);
        boolean encontroAlgo = false;

        while (hojaActual != null) {
            for (int i = 0; i < hojaActual.getClaves().size(); i++) {
                int claveActual = hojaActual.getClaves().get(i);
                if (claveActual > fin) {
                    if (!encontroAlgo) {
                        System.out.println("No se encontraron registros en el rango [" + inicio + ", " + fin + "].");
                    }
                    return;
                }
                if (claveActual >= inicio) {
                    System.out.println("Clave: " + claveActual + " -> Dato: " + hojaActual.getDatos().get(i));
                    encontroAlgo = true;
                }
            }
            hojaActual = hojaActual.getSiguiente();
        }

        if (!encontroAlgo) {
            System.out.println("No se encontraron registros en el rango [" + inicio + ", " + fin + "].");
        }
    }
}