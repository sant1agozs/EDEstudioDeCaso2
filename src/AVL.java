import java.util.ArrayList;
import java.util.List;

public class AVL {

    private NodoAVL raiz;

    public AVL() {
        raiz = null;
    }

    // Búsqueda pública
    public boolean buscar(int llave) {
        return buscarRec(raiz, llave) != null;
    }

    // Búsqueda recursiva típica de BST
    private NodoAVL buscarRec(NodoAVL nodo, int llave) {
        if (nodo == null) return null;
        if (llave == nodo.getLlave()) return nodo;
        if (llave < nodo.getLlave()) return buscarRec(nodo.getHijoIzquierdo(), llave);
        return buscarRec(nodo.getHijoDerecho(), llave);
    }

    // Inserción pública
    public void insertar(int llave) {
        raiz = insertarRec(raiz, llave);
    }

    // Inserción con balanceo incluido
    private NodoAVL insertarRec(NodoAVL nodo, int llave) {

        if (nodo == null) return new NodoAVL(llave);

        if (llave < nodo.getLlave()) {
            nodo.setHijoIzquierdo(insertarRec(nodo.getHijoIzquierdo(), llave));
        } else if (llave > nodo.getLlave()) {
            nodo.setHijoDerecho(insertarRec(nodo.getHijoDerecho(), llave));
        } else {
            return nodo; // No duplicados
        }

        nodo.actualizarAltura();
        int balance = nodo.obtenerBalance();

        // Caso LL
        if (balance > 1 && nodo.getHijoIzquierdo().obtenerBalance() >= 0)
            return NodoAVL.rotarDerecha(nodo);

        // Caso LR
        if (balance > 1 && nodo.getHijoIzquierdo().obtenerBalance() < 0)
            return NodoAVL.rotarIzquierdaDerecha(nodo);

        // Caso RR
        if (balance < -1 && nodo.getHijoDerecho().obtenerBalance() <= 0)
            return NodoAVL.rotarIzquierda(nodo);

        // Caso RL
        if (balance < -1 && nodo.getHijoDerecho().obtenerBalance() > 0)
            return NodoAVL.rotarDerechaIzquierda(nodo);

        return nodo;
    }

    // Eliminación pública
    public void eliminar(int llave) {
        raiz = eliminarRec(raiz, llave);
    }

    // Eliminación con rebalanceo
    private NodoAVL eliminarRec(NodoAVL nodo, int llave) {

        if (nodo == null) return null;

        if (llave < nodo.getLlave()) {
            nodo.setHijoIzquierdo(eliminarRec(nodo.getHijoIzquierdo(), llave));
        } else if (llave > nodo.getLlave()) {
            nodo.setHijoDerecho(eliminarRec(nodo.getHijoDerecho(), llave));
        } else {

            // Caso 1: 0 o 1 hijo
            if (nodo.getHijoIzquierdo() == null || nodo.getHijoDerecho() == null) {
                NodoAVL temp = (nodo.getHijoIzquierdo() != null) ? nodo.getHijoIzquierdo() : nodo.getHijoDerecho();
                nodo = temp;
            }
            // Caso 2: dos hijos (usar sucesor)
            else {
                NodoAVL sucesor = nodo.getHijoDerecho();
                while (sucesor.getHijoIzquierdo() != null)
                    sucesor = sucesor.getHijoIzquierdo();

                nodo.setLlave(sucesor.getLlave());
                nodo.setHijoDerecho(eliminarRec(nodo.getHijoDerecho(), sucesor.getLlave()));
            }
        }

        if (nodo == null) return null;

        nodo.actualizarAltura();
        int balance = nodo.obtenerBalance();

        int balanceIzq = (nodo.getHijoIzquierdo() == null) ? 0 : nodo.getHijoIzquierdo().obtenerBalance();
        int balanceDer = (nodo.getHijoDerecho() == null) ? 0 : nodo.getHijoDerecho().obtenerBalance();

        // Rebalanceo
        if (balance > 1 && balanceIzq >= 0)
            return NodoAVL.rotarDerecha(nodo);

        if (balance > 1 && balanceIzq < 0)
            return NodoAVL.rotarIzquierdaDerecha(nodo);

        if (balance < -1 && balanceDer <= 0)
            return NodoAVL.rotarIzquierda(nodo);

        if (balance < -1 && balanceDer > 0)
            return NodoAVL.rotarDerechaIzquierda(nodo);

        return nodo;
    }

    // Recorrido inorden
    public void imprimirEnOrden() {
        inordenRec(raiz);
        System.out.println();
    }

    private void inordenRec(NodoAVL nodo) {
        if (nodo != null) {
            inordenRec(nodo.getHijoIzquierdo());
            System.out.print(nodo.getLlave() + " ");
            inordenRec(nodo.getHijoDerecho());
        }
    }

    // ===============================
    //   IMPRESIÓN VISUAL DEL ÁRBOL
    // ===============================

    public void imprimirArbolVisual() {
        if (raiz == null) {
            System.out.println("(árbol vacío)");
            return;
        }

        int altura = raiz.getAltura();
        int ancho = (int) Math.pow(2, altura) * 4;

        List<StringBuilder> lineas = new ArrayList<>();
        for (int i = 0; i < altura * 2; i++) {
            lineas.add(new StringBuilder(" ".repeat(ancho)));
        }

        imprimirNodo(raiz, 0, ancho / 2, lineas, ancho / 4);

        for (StringBuilder linea : lineas) {
            System.out.println(linea);
        }
    }

    private void imprimirNodo(NodoAVL nodo, int nivel, int pos, List<StringBuilder> lineas, int separacion) {
        if (nodo == null) return;

        String valor = String.valueOf(nodo.getLlave());
        insertarTexto(lineas.get(nivel), pos, valor);

        if (nivel + 1 < lineas.size()) {
            if (nodo.getHijoIzquierdo() != null)
                insertarTexto(lineas.get(nivel + 1), pos - separacion, "/");

            if (nodo.getHijoDerecho() != null)
                insertarTexto(lineas.get(nivel + 1), pos + separacion, "\\");
        }

        imprimirNodo(nodo.getHijoIzquierdo(), nivel + 2, pos - separacion, lineas, separacion / 2);
        imprimirNodo(nodo.getHijoDerecho(), nivel + 2, pos + separacion, lineas, separacion / 2);
    }

    private void insertarTexto(StringBuilder sb, int pos, String texto) {
        for (int i = 0; i < texto.length() && pos + i < sb.length(); i++) {
            sb.setCharAt(pos + i, texto.charAt(i));
        }
    }
}