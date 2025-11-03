package EstructurasDeDatos;

/**
 *
 * @author chela
 */
public class Queue<T> {

    private Nodo<T> first;
    private Nodo<T> last;
    private int length;

    public Queue() {
        this.first = null;
        this.last = null;
        this.length = 0;
    }

    // Getters
    public int getLength() {
        return length;
    }

    public Nodo<T> getFirst() {
        return first;
    }

    public void setFirst(Nodo<T> first) {
        this.first = first;
    }

    public void setLast(Nodo<T> last) {
        this.last = last;
    }

    // Metodo para saber si es vacio
    public boolean isEmpty() {
        return length == 0;
    }

    // Metodo para inserta un elemento al final de la cola
    public void enqueue(T data) {
        Nodo<T> nuevoNodo = new Nodo<>(data);
        if (isEmpty()) {
            first = nuevoNodo;
            last = nuevoNodo;
        } else {
            last.setNext(nuevoNodo);
            last = nuevoNodo;
        }
        length++;
    }

    /**
     * Elimina y retorna el elemento del frente de la cola
     *
     * @return El dato eliminado o null si la cola está vacía.
     */
    public T dequeue() {
        if (isEmpty()) {
            System.out.println("La cola está vacía.");
            return null;
        }
        T data = first.getData();
        first = first.getNext();
        length--;
        if (length == 0) { // Si la cola quedó vacía, reiniciar last a null
            last = null;
        }
        return data;
    }

    /**
     * Retorna el elemento del frente sin eliminarlo (peek).
     *
     * @return El dato en el frente de la cola o null si la cola está vacía.
     */
    public T peek() {
        if (isEmpty()) {
            System.out.println("La cola está vacía.");
            return null;
        }
        return first.getData();
    }

    // Metodo para imprimir la cola
    public String imprimir() {
        if (isEmpty()) {
            return "La cola está vacía.";
        }
        StringBuilder sb = new StringBuilder();
        Nodo<T> actual = first;
        while (actual != null) {
            sb.append(actual.getData()).append(" -> ");
            actual = actual.getNext();
        }
        sb.append("NULL");
        return sb.toString();
    }

    // Metodo para dejar la cola vacia
    public void clear() {
        first = null;
        last = null;
        length = 0;
    }
}
