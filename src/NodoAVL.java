public class NodoAVL {

    private int llave;
    private NodoAVL hijoIzquierdo;
    private NodoAVL hijoDerecho;
    private int altura;

    public NodoAVL(int llave) {
        this.llave = llave;
        this.hijoIzquierdo = null;
        this.hijoDerecho = null;
        this.altura = 1; // Una hoja se considera de altura 1.
    }

    public int getLlave() {
        return llave;
    }

    public NodoAVL getHijoIzquierdo() {
        return hijoIzquierdo;
    }

    public NodoAVL getHijoDerecho() {
        return hijoDerecho;
    }

    public int getAltura() {
        return altura;
    }

    public void setLlave(int llave) {
        this.llave = llave;
    }

    public void setHijoIzquierdo(NodoAVL hijoIzquierdo) {
        this.hijoIzquierdo = hijoIzquierdo;
    }

    public void setHijoDerecho(NodoAVL hijoDerecho) {
        this.hijoDerecho = hijoDerecho;
    }

    public void setAltura(int altura) {
        this.altura = altura;
    }

    // Actualiza la altura basándose en las alturas de los hijos.
    public void actualizarAltura() {
        int izq = (hijoIzquierdo == null) ? 0 : hijoIzquierdo.getAltura();
        int der = (hijoDerecho == null) ? 0 : hijoDerecho.getAltura();
        this.altura = Math.max(izq, der) + 1;
    }

    // Factor de balance: diferencia entre altura izquierrda y derecha.
    public int obtenerBalance() {
        int izq = (hijoIzquierdo == null) ? 0 : hijoIzquierdo.getAltura();
        int der = (hijoDerecho == null) ? 0 : hijoDerecho.getAltura();
        return izq - der;
    }

    // Rotación simple a la derecha (caso Izquierda-Izquierda).
    public static NodoAVL rotarDerecha(NodoAVL y) {
        NodoAVL x = y.getHijoIzquierdo();
        NodoAVL t2 = x.getHijoDerecho();

        x.setHijoDerecho(y);
        y.setHijoIzquierdo(t2);

        y.actualizarAltura();
        x.actualizarAltura();

        return x; // nueva raíz del subárbol rotado
    }

    // Rotación simple a la izquierda (caso Derecha-Derecha).
    public static NodoAVL rotarIzquierda(NodoAVL x) {
        NodoAVL y = x.getHijoDerecho();
        NodoAVL t2 = y.getHijoIzquierdo();

        y.setHijoIzquierdo(x);
        x.setHijoDerecho(t2);

        x.actualizarAltura();
        y.actualizarAltura();

        return y;
    }

    // Rotación doble Izquierda-Derecha.
    public static NodoAVL rotarIzquierdaDerecha(NodoAVL nodo) {
        nodo.setHijoIzquierdo(rotarIzquierda(nodo.getHijoIzquierdo()));
        return rotarDerecha(nodo);
    }

    // Rotación doble Derecha-Izquierda.
    public static NodoAVL rotarDerechaIzquierda(NodoAVL nodo) {
        nodo.setHijoDerecho(rotarDerecha(nodo.getHijoDerecho()));
        return rotarIzquierda(nodo);
    }
}