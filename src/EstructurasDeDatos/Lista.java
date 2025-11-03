/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package EstructurasDeDatos;

/**
 *
 * @author chela
 */
public class Lista<T> {

    private Nodo head;
    private int length;

    // Constructor
    public Lista() {
        this.head = null;
        this.length = 0;
    }

    // Getters y Setters
    public Nodo getHead() {
        return head;
    }

    public void setHead(Nodo head) {
        this.head = head;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    // Metodo para saber si esta vacio
    public boolean esVacio() {
        return getHead() == null;
    }

    // Metodos de insercion 
    public void insertFirst(T data) {
        Nodo nodo = new Nodo(data);
        if (esVacio()) {
            setHead(nodo);
        } else {
            nodo.setNext(getHead());
            setHead(nodo);
        }
        length++;
    }

    public void insertLast(T data) {
        Nodo nodo = new Nodo(data);
        if (esVacio()) {
            setHead(nodo);
        } else {
            Nodo apuntador = getHead();
            while (apuntador.getNext() != null) {
                apuntador = apuntador.getNext();
            }
            apuntador.setNext(nodo);
        }
        length++;
    }

    public void insertIndex(T data, int position) {
        Nodo nodo = new Nodo(data);
        if (esVacio()) {
            setHead(nodo);
        } else {
            Nodo apuntador = getHead();
            int counter = 0;
            while (counter < (position - 1) && apuntador.getNext() != null) {
                apuntador = apuntador.getNext();
                counter++;
            }
            nodo.setNext(apuntador.getNext());
            apuntador.setNext(nodo);
        }
        length++;
    }

    // Metodos para la eliminacion
    public void deleteFirst() {
        if (!esVacio()) {
            Nodo pointer = getHead();
            setHead(pointer.getNext());
            pointer.setNext(null);
            length--;
        } else {
            System.out.println("La lista está vacía.");
        }
    }

    public void deleteLast() {
        if (!esVacio()) {
            if (head.getNext() == null) { // Caso de un solo elemento
                setHead(null);
            } else {
                Nodo pointer = getHead();
                while (pointer.getNext().getNext() != null) {
                    pointer = pointer.getNext();
                }
                pointer.setNext(null);
            }
            length--;
        } else {
            System.out.println("La lista está vacía.");
        }
    }

    public void deleteIndex(int position) {
        if (position < 0 || position >= length) {
            throw new IndexOutOfBoundsException("Posición inválida");
        }
        if (esVacio()) {
            return;
        }

        // Caso 1: Eliminar el primer nodo (posición 0)
        if (position == 0) {
            Nodo temp = head;
            head = head.getNext(); // Actualizar la cabeza
            temp.setNext(null);    // Desconectar el nodo
        } // Caso 2: Eliminar nodos en posición > 0
        else {
            Nodo pointer = head;
            int count = 0;
            // Avanzar hasta el nodo anterior al que se eliminará (position - 1)
            while (count < position - 1) {
                pointer = pointer.getNext();
                count++;
            }
            Nodo nodoAEliminar = pointer.getNext();
            pointer.setNext(nodoAEliminar.getNext()); // Saltar el nodo a eliminar
            nodoAEliminar.setNext(null);              // Desconectar el nodo
        }

        length--; // Actualizar la longitud
    }

    public void deleteContent(T data) {
        if (head == null) {
            return;
        }

        if (head.getData().equals(data)) {
            head = head.getNext();
            length--;
            return;
        }

        Nodo<T> actual = head;
        while (actual.getNext() != null) {
            if (actual.getNext().getData().equals(data)) {
                actual.setNext(actual.getNext().getNext());
                length--;
                return;
            }
            actual = actual.getNext();
        }
    }

    // Metodo para obtener el elemento de un nodo determinado
    public T get(int indice) {
        if (indice < 0 || indice >= length) {
            throw new IndexOutOfBoundsException();
        }
        Nodo<T> actual = head;
        for (int i = 0; i < indice; i++) {
            actual = actual.getNext();
        }

        return actual.getData();
    }

    // Metodo para imprimir
    public void imprimir() {
        if (!esVacio()) {
            Nodo aux = head;
            for (int i = 0; i < length; i++) {
                System.out.println(aux.getData() + "");
                aux = aux.getNext();
            }
        } else {
            System.out.println("Esta lista esta vacía");
        }
    }

    // Metodo para saber si un elemento esta en la lista
    public boolean contains(T elemento) {
        Nodo<T> actual = head;
        while (actual != null) {
            if (actual.getData().equals(elemento)) {
                return true;
            }
            actual = actual.getNext();
        }
        return false;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        Nodo<T> actual = head;
        while (actual != null) {
            sb.append(actual.getData()).append(" -> ");
            actual = actual.getNext();
        }
        sb.append("NULL");
        return sb.toString();
    }
}
