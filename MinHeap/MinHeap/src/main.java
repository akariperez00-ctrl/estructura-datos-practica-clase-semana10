public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);
    MinHeap heap = new MinHeap();
    int[] lista = new int[0];
    int op;

    do {
        System.out.println("\nLista actual: " + Arrays.toString(lista));
        heap.mostrar();
        System.out.println("1. Crear lista");
        System.out.println("2. Cargar lista");
        System.out.println("3. Insertar");
        System.out.println("4. Extraer mínimo");
        System.out.println("5. Mostrar lista");
        System.out.println("0. Salir");
        System.out.print("> ");
        op = sc.nextInt();

        switch (op) {
            case 1:
                System.out.print("Cantidad: ");
                int n = sc.nextInt();
                lista = new int[Math.max(0, n)];
                for (int i = 0; i < lista.length; i++) {
                    System.out.print("[" + i + "]: ");
                    lista[i] = sc.nextInt();
                }
                System.out.println("Nueva lista: " + Arrays.toString(lista));
                heap.heapify(Arrays.copyOf(lista, lista.length));
                heap.mostrar();
                break;
            case 2:
                heap.heapify(Arrays.copyOf(lista, lista.length));
                break;
            case 3:
                System.out.print("Valor: ");
                heap.insertar(sc.nextInt());
                break;
            case 4:
                System.out.println("Sacado: " + heap.pop());
                break;
            case 5:
                System.out.println("Lista actual: " + Arrays.toString(lista));
                break;
        }
    } while (op != 0);

    sc.close();
}