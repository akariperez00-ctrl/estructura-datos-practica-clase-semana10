import java.util.ArrayList;

public class MinHeap {

    // Atributos
    private ArrayList<Integer>monticulo;

    // Métodos
    // Constructor
    public MinHeap() {
        monticulo = new ArrayList<>();
    }

    // Operacion
    private  boolean estaVacio(){
        return monticulo.isEmpty();
    }
    private  int getPadre(int i){
        return  (i -1)/2;
    }

    private int getIzp(int i){
        return (2*i)+1;
    }

    private int getDer(int i){
        return (2*i)+2;
    }

    public void mostrar() {
        System.out.println("Montículo actual: " + monticulo);
    }

    public void insertar(int nodo){
        if (nodo < 0) {
            System.out.println("El montículo no puede almacenar datos negativos. \n");
            return;
        }
        monticulo.add(nodo);
        upHeapify(monticulo.size() - 1);
    }



    private void upHeapify(int i){
        while (i > 0) {
            int padre = (i - 1) / 2;

            if (monticulo.get(i) < monticulo.get(padre)) {
                int temp = monticulo.get(i);
                monticulo.set(i, monticulo.get(padre));
                monticulo.set(padre, temp);

                i = padre;
            } else {
                break;
            }
        }

    }

    public int pop() {
        if (estaVacio()) {
            System.out.println("El montículo está vacío.");
            return -1;
        }

        int minimo = monticulo.get(0);
        int ultimo = monticulo.remove(monticulo.size() - 1);

        if (!monticulo.isEmpty()) {
            monticulo.set(0, ultimo);
            downHeapify(0);
        }

        return minimo;
    }
    private void downHeapify(int indice){
        int menor = indice;
        int izq = 2 * indice + 1;
        int der = 2 * indice + 2;

        if (izq < monticulo.size() && monticulo.get(izq) < monticulo.get(menor)) {
            menor = izq;
        }

        if (der < monticulo.size() && monticulo.get(der) < monticulo.get(menor)) {
            menor = der;
        }

        if (menor != indice) {
            int temp = monticulo.get(indice);
            monticulo.set(indice, monticulo.get(menor));
            monticulo.set(menor, temp);

            downHeapify(menor);
        }

    }

    public int peek(){
        if (estaVacio()) {
            System.out.println("El montículo está vacío. \n");
            return -1;
        }
        return monticulo.getFirst();
    }

    public void heapify(int[] arreglo) {
        // Vaciar lo que actualmente haya en el atributo monticulo
        // Ordenar el arreglo recibido para que cumpla con el principio de ordenamiento
        // Copiar los contenidos del arreglo ya ordenando en el atributo monticulo
        // Copiar los contenidos del arreglo ya ordenando en el atributo monticulo
        monticulo.clear();

        if (arreglo == null || arreglo.length == 0) {
            return;
        }

        for (int valor : arreglo) {
            if (valor < 0) {
                System.out.println("El montículo no puede almacenar datos negativos. \n");
                return;
            }
        }

        int n = arreglo.length;
        // Empezar desde el último nodo interno (padre del último elemento) hasta la raíz
        for (int i = (n / 2) - 1; i >= 0; i--) {
            downHeapifyArreglo(arreglo, n, i);
        }

        for (int valor : arreglo) {
            monticulo.add(valor);
        }
    }
    private void downHeapifyArreglo(int[] arr, int n, int indice) {
        int menor = indice;
        int izq = 2 * indice + 1;
        int der = 2 * indice + 2;

        if (izq < n && arr[izq] < arr[menor]) {
            menor = izq;
        }

        if (der < n && arr[der] < arr[menor]) {
            menor = der;
        }

        if (menor != indice) {
            int temp = arr[indice];
            arr[indice] = arr[menor];
            arr[menor] = temp;

            downHeapifyArreglo(arr, n, menor);
        }
    }


}
