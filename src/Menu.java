import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;

public class Menu {

    private BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    private PrintStream out = System.out;
    private AVL arbol = new AVL();

    public void menu() throws Exception {

        int opcion = 0;

        do {
            out.println("\n========= MENÚ ÁRBOL AVL =========");
            out.println("1. Insertar nodo");
            out.println("2. Buscar nodo");
            out.println("3. Eliminar nodo");
            out.println("4. Mostrar árbol en orden (Inorden)");
            out.println("5. Mostrar árbol visual (ASCII)");
            out.println("6. Salir");
            out.print("Seleccione una opción: ");

            opcion = Integer.parseInt(in.readLine());

            switch (opcion) {

                case 1:
                    out.print("Ingrese la llave a insertar: ");
                    arbol.insertar(Integer.parseInt(in.readLine()));
                    break;

                case 2:
                    out.print("Ingrese la llave a buscar: ");
                    boolean encontrado = arbol.buscar(Integer.parseInt(in.readLine()));
                    out.println(encontrado ? "La llave está en el árbol." : "La llave NO está en el árbol.");
                    break;

                case 3:
                    out.print("Ingrese la llave a eliminar: ");
                    arbol.eliminar(Integer.parseInt(in.readLine()));
                    break;

                case 4:
                    out.println("\nÁrbol en inorden:");
                    arbol.imprimirEnOrden();
                    break;

                case 5:
                    out.println("\nRepresentación visual del árbol:");
                    arbol.imprimirArbolVisual();
                    break;

                case 6:
                    out.println("\nFin del programa.");
                    break;

                default:
                    out.println("Opción inválida.");
            }

        } while (opcion != 6);
    }
}