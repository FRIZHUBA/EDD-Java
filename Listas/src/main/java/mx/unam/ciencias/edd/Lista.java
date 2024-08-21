package mx.unam.ciencias.edd;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * <p>Clase genérica para listas doblemente ligadas.</p>
 *
 * <p>Las listas nos permiten agregar elementos al inicio o final de la lista,
 * eliminar elementos de la lista, comprobar si un elemento está o no en la
 * lista, y otras operaciones básicas.</p>
 *
 * <p>Las listas no aceptan a <code>null</code> como elemento.</p>
 *
 * @param <T> El tipo de los elementos de la lista.
 */
public class Lista<T> implements Coleccion<T> {

    /* Clase interna privada para nodos. */
    private class Nodo {
        /* El elemento del nodo. */
        private T elemento;
        /* El nodo anterior. */
        private Nodo anterior;
        /* El nodo siguiente. */
        private Nodo siguiente;

        /* Construye un nodo con un elemento. */
        private Nodo(T elemento) {
            this.elemento = elemento;
        }
    }

    /* Clase interna privada para iteradores. */
    private class Iterador implements IteradorLista<T> {
        /* El nodo anterior. */
        private Nodo anterior;
        /* El nodo siguiente. */
        private Nodo siguiente;

        /* Construye un nuevo iterador. */
        private Iterador() {
            this.anterior = null;
            this.siguiente = cabeza;
        }

        /* Nos dice si hay un elemento siguiente. */
        @Override public boolean hasNext() {
            
            return siguiente != null;
        }

        /* Nos da el elemento siguiente. */
        @Override public T next() {

            if(hasNext()) {
    
                anterior = siguiente;
                siguiente = siguiente.siguiente;
    
                return anterior.elemento;

            } else {

                throw new NoSuchElementException();
            }
        }

        /* Nos dice si hay un elemento anterior. */
        @Override public boolean hasPrevious() {
            
            return anterior != null;
        }

        /* Nos da el elemento anterior. */
        @Override public T previous() {
            
            if(hasPrevious()) {
    
                siguiente = anterior;
                anterior = anterior.anterior;
    
                return siguiente.elemento;

            } else {

                throw new NoSuchElementException();
            }
        }

        /* Mueve el iterador al inicio de la lista. */
        @Override public void start() {
            this.anterior = null;
            this.siguiente = cabeza;
        }

        /* Mueve el iterador al final de la lista. */
        @Override public void end() {
            this.anterior = rabo;
            this.siguiente = null;
        }
    }

    /* Primer elemento de la lista. */
    private Nodo cabeza;
    /* Último elemento de la lista. */
    private Nodo rabo;
    /* Número de elementos en la lista. */
    private int longitud;

    /**
     * Regresa la longitud de la lista. El método es idéntico a {@link
     * #getElementos}.
     * @return la longitud de la lista, el número de elementos que contiene.
     */
    public int getLongitud() {
        
        return longitud;
    }

    /**
     * Regresa el número elementos en la lista. El método es idéntico a {@link
     * #getLongitud}.
     * @return el número elementos en la lista.
     */
    @Override public int getElementos() {
        
        return longitud;
    }

    /**
     * Nos dice si la lista es vacía.
     * @return <code>true</code> si la lista es vacía, <code>false</code> en
     *         otro caso.
     */
    @Override public boolean esVacia() {
        
        return (cabeza == null && rabo == null && longitud == 0);
    }

    /**
     * Agrega un elemento a la lista. Si la lista no tiene elementos, el
     * elemento a agregar será el primero y último. El método es idéntico a
     * {@link #agregaFinal}.
     * @param elemento el elemento a agregar.
     * @throws IllegalArgumentException si <code>elemento</code> es
     *         <code>null</code>.
     */
    @Override public void agrega(T elemento) {
        
        agregaFinal(elemento);
    }

    /**
     * Agrega un elemento al final de la lista. Si la lista no tiene elementos,
     * el elemento a agregar será el primero y último.
     * @param elemento el elemento a agregar.
     * @throws IllegalArgumentException si <code>elemento</code> es
     *         <code>null</code>.
     */
    public void agregaFinal(T elemento) {

        if(elemento == null) throw new IllegalArgumentException();
        
        else if(esVacia()) agregaInicio(elemento);

        else {

            Nodo nuevo = new Nodo(elemento);

            rabo.siguiente = nuevo;
            nuevo.anterior = rabo;
            rabo = nuevo;
            
            longitud ++;
        }
    }

    /**
     * Agrega un elemento al inicio de la lista. Si la lista no tiene elementos,
     * el elemento a agregar será el primero y último.
     * @param elemento el elemento a agregar.
     * @throws IllegalArgumentException si <code>elemento</code> es
     *         <code>null</code>.
     */
    public void agregaInicio(T elemento) {
        
        if(elemento == null) throw new IllegalArgumentException();

        else {

            Nodo nuevo = new Nodo(elemento);
    
            if(esVacia()) {
        
                cabeza = nuevo;
                rabo = nuevo;
    
            } else {
    
                nuevo.siguiente = cabeza;
                cabeza.anterior = nuevo;
                cabeza = nuevo;
            }
    
            longitud ++;
        }
    }

    /**
     * Busca un nodo por su elemento
     * @param elemento el elemento a agregar.
     */
    private Nodo buscarNodo(T elemento){

        if(elemento == null) return null;

        else {

            Nodo aux = cabeza;
            
              
            while(aux != null){
    
                if(aux.elemento.equals(elemento)) return aux;
    
                aux = aux.siguiente;
            
            }
        }

        return null;
    }

    /**
     * Busca un nodo por su elemento
     * @param elemento el elemento a agregar.
     */
    private Nodo buscarNodo(int i){

        Nodo aux = cabeza;
        int j = 0;
            
        while(j < i){

            aux = aux.siguiente;
            j ++;
        }

        return aux;
    }

    /**
     * Inserta un elemento en un índice explícito.
     *
     * Si el índice es menor o igual que cero, el elemento se agrega al inicio
     * de la lista. Si el índice es mayor o igual que el número de elementos en
     * la lista, el elemento se agrega al fina de la misma. En otro caso,
     * después de mandar llamar el método, el elemento tendrá el índice que se
     * especifica en la lista.
     * @param i el índice dónde insertar el elemento. Si es menor que 0 el
     *          elemento se agrega al inicio de la lista, y si es mayor o igual
     *          que el número de elementos en la lista se agrega al final.
     * @param elemento el elemento a insertar.
     * @throws IllegalArgumentException si <code>elemento</code> es
     *         <code>null</code>.
     */
    public void inserta(int i, T elemento) {

        if (elemento == null) throw new IllegalArgumentException();
        
        if (i <= 0) agregaInicio(elemento);
        
        else if (i >= longitud) agregaFinal(elemento);
        
        else {
    
            Nodo aux = buscarNodo(i-1);
            Nodo nuevo = new Nodo(elemento);
    
            nuevo.siguiente = aux.siguiente;
    
            if (aux.siguiente != null) aux.siguiente.anterior = nuevo;
    
            nuevo.anterior = aux;
            aux.siguiente = nuevo;
    
            longitud++;
        }
    }


    /**
     * Elimina un elemento de la lista. Si el elemento no está contenido en la
     * lista, el método no la modifica.
     * @param elemento el elemento a eliminar.
     */
    @Override public void elimina(T elemento) {

        if(esVacia()) return;
        
        Nodo aux = buscarNodo(elemento);

        if(aux == null) return;

        if(aux == cabeza && aux == rabo) limpia();

        else if(aux == cabeza) eliminaPrimero();
        
        else if(aux == rabo) eliminaUltimo();
        
        else {

            aux.anterior.siguiente = aux.siguiente;
            aux.siguiente.anterior = aux.anterior;

            longitud --;
        }
    }


    /**
     * Elimina el primer elemento de la lista y lo regresa.
     * @return el primer elemento de la lista antes de eliminarlo.
     * @throws NoSuchElementException si la lista es vacía.
     */


    public T eliminaPrimero() {

        if (esVacia()) throw new NoSuchElementException();
    
        Nodo aux = cabeza;
    
        if (cabeza == rabo) limpia();

        else {

            cabeza = aux.siguiente;
            cabeza.anterior = null;

            longitud--;
        }
    
        return aux.elemento;
    }
    

    /**
     * Elimina el último elemento de la lista y lo regresa.
     * @return el último elemento de la lista antes de eliminarlo.
     * @throws NoSuchElementException si la lista es vacía.
     */
    public T eliminaUltimo() {
        
        if(esVacia()) throw new NoSuchElementException();
        
        Nodo aux = rabo;

        if(cabeza == rabo) limpia();

        else {

            rabo = aux.anterior;
            rabo.siguiente = null;

            longitud --;
        }
        
        return aux.elemento;
    }

    /**
     * Nos dice si un elemento está en la lista.
     * @param elemento el elemento que queremos saber si está en la lista.
     * @return <code>true</code> si <code>elemento</code> está en la lista,
     *         <code>false</code> en otro caso.
     */
    @Override public boolean contiene(T elemento) {
        
        return buscarNodo(elemento) != null;
    }

    /**
     * Regresa la reversa de la lista.
     * @return una nueva lista que es la reversa la que manda llamar el método.
     */
    public Lista<T> reversa() {
        
        Lista<T> revLista = new Lista<>();
        Nodo aux = cabeza;
        int i = 0;

        while(i < longitud){

            revLista.agregaInicio(aux.elemento);
            aux = aux.siguiente;
            i ++;
        }

        return revLista;
    }

    /**
     * Regresa una copia de la lista. La copia tiene los mismos elementos que la
     * lista que manda llamar el método, en el mismo orden.
     * @return una copiad de la lista.
     */
    public Lista<T> copia() {
        
        Lista<T> copLista = new Lista<>();
        Nodo aux = cabeza;
        int i = 0;

        while(i < longitud){

            copLista.agregaFinal(aux.elemento);
            aux = aux.siguiente;
            i ++;
        }

        return copLista;
    }

    /**
     * Limpia la lista de elementos, dejándola vacía.
     */
    @Override public void limpia() {
        
        cabeza = null;
        rabo = null;
        longitud = 0;
    }

    /**
     * Regresa el primer elemento de la lista.
     * @return el primer elemento de la lista.
     * @throws NoSuchElementException si la lista es vacía.
     */
    public T getPrimero() {
        
        if(esVacia()) throw new NoSuchElementException();

        else return cabeza.elemento;
    }

    /**
     * Regresa el último elemento de la lista.
     * @return el último elemento de la lista.
     * @throws NoSuchElementException si la lista es vacía.
     */
    public T getUltimo() {
        
        if(esVacia()) throw new NoSuchElementException();

        else return rabo.elemento;
    }

    /**
     * Regresa el <em>i</em>-ésimo elemento de la lista.
     * @param i el índice del elemento que queremos.
     * @return el <em>i</em>-ésimo elemento de la lista.
     * @throws ExcepcionIndiceInvalido si <em>i</em> es menor que cero o mayor o
     *         igual que el número de elementos en la lista.
     */
    public T get(int i) {
        
        if(i < 0 || i >= longitud) throw new ExcepcionIndiceInvalido();

        else if(i == 0) return getPrimero();

        else if(i == longitud-1) return getUltimo();

        else{

            return buscarNodo(i).elemento;
        }
    }

    /**
     * Regresa el índice del elemento recibido en la lista.
     * @param elemento el elemento del que se busca el índice.
     * @return el índice del elemento recibido en la lista, o -1 si el elemento
     *         no está contenido en la lista.
     */
    public int indiceDe(T elemento) {
        
        int i = 0;

        Nodo aux = cabeza;
        
        while(aux != null){

            if(aux.elemento.equals(elemento)) return i;
        
            aux = aux.siguiente;
            i ++;
        }

        return -1;
    }

    /**
     * Regresa una representación en cadena de la lista.
     * @return una representación en cadena de la lista.
     */
    @Override public String toString() {

        if (esVacia()) return "[]";
    
        StringBuilder sb = new StringBuilder("[");
        Nodo aux = cabeza;
    
        while (aux != null) {
            
            sb.append(aux.elemento);
            
            if (aux.siguiente != null) sb.append(", ");
            
            aux = aux.siguiente;
        }
    
        sb.append("]");

        return sb.toString();
    }

    /**
     * Nos dice si la lista es igual al objeto recibido.
     * @param objeto el objeto con el que hay que comparar.
     * @return <code>true</code> si la lista es igual al objeto recibido;
     *         <code>false</code> en otro caso.
     */
    @Override public boolean equals(Object objeto) {
        if (objeto == null || getClass() != objeto.getClass())
            return false;
        @SuppressWarnings("unchecked") Lista<T> lista = (Lista<T>)objeto;
        
        if(longitud != lista.getLongitud()) return false;

        else {

            Nodo aux = cabeza;
            Nodo aux2 = lista.cabeza;
            int i = 0;

            while(i < longitud) {
                
                if(! aux.elemento.equals(aux2.elemento)) return false;
                
                aux = aux.siguiente;
                aux2 = aux2.siguiente;

                i ++;
            }

            return true;
        }
    }

    /**
     * Regresa un iterador para recorrer la lista en una dirección.
     * @return un iterador para recorrer la lista en una dirección.
     */
    @Override public Iterator<T> iterator() {
        return new Iterador();
    }

    /**
     * Regresa un iterador para recorrer la lista en ambas direcciones.
     * @return un iterador para recorrer la lista en ambas direcciones.
     */
    public IteradorLista<T> iteradorLista() {
        return new Iterador();
    }
}