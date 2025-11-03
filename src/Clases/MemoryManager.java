/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Clases;

import EstructurasDeDatos.Lista;
import EstructurasDeDatos.Queue;

/**
 *
 * @author marco
 */
public class MemoryManager {

    private Queue<Bloque> bloquesLibres;
    private Lista<Bloque> todosLosBloques;  // Nueva lista para rastrear todos los bloques

    public MemoryManager(int totalBloques) {
        this.todosLosBloques = new Lista<>();
        this.bloquesLibres = new Queue<>();
        inicializarBloques(totalBloques);
    }

    private void inicializarBloques(int total) {
        for (int i = 0; i < total; i++) {
            Bloque bloque = new Bloque(i);
            todosLosBloques.insertLast(bloque);  // Guardar en lista general
            bloquesLibres.enqueue(bloque);       // Agregar a la cola de libres
        }
    }

    public void liberarBloques(Lista<Bloque> bloques) {
        for (int i = 0; i < bloques.getLength(); i++) {
            Bloque bloque = bloques.get(i);
            bloque.setOcupado(false);
            bloque.setSiguienteBloque(null);
        }
        // Reconstruir la cola de libres después de liberar
        reconstruirColaBloquesLibres(); // <-- Añadir esta línea
    }

    public boolean estaOcupado(int idBloque) {
        for (int i = 0; i < todosLosBloques.getLength(); i++) {
            Bloque bloque = todosLosBloques.get(i);
            if (bloque.getId() == idBloque) {
                return bloque.isOcupado();
            }
        }
        return false;
    }

    public Lista<Bloque> asignarBloquesEncadenados(int cantidad) {
        if (bloquesLibres.getLength() < cantidad) {
            return null;
        }

        Lista<Bloque> bloquesAsignados = new Lista<>();
        Bloque anterior = null;

        for (int i = 0; i < cantidad; i++) {
            Bloque bloque = bloquesLibres.dequeue();
            bloque.setOcupado(true);

            if (anterior != null) {
                anterior.setSiguienteBloque(bloque); // Enlazar bloques
            }

            bloquesAsignados.insertLast(bloque);
            anterior = bloque;
        }

        return bloquesAsignados;
    }

    public void reconstruirSiguienteBloques() {
        for (int i = 0; i < todosLosBloques.getLength(); i++) {
            Bloque bloque = todosLosBloques.get(i);
            if (bloque.getSiguienteId() != -1) {
                Bloque siguiente = findBloqueById(bloque.getSiguienteId());
                bloque.setSiguienteBloque(siguiente); // Enlazar
            } else {
                bloque.setSiguienteBloque(null);
            }
        }
    }

    Bloque findBloqueById(int id) {
        for (int i = 0; i < todosLosBloques.getLength(); i++) {
            Bloque b = todosLosBloques.get(i);
            if (b.getId() == id) {
                return b;
            }
        }
        return null;
    }

    public void reconstruirColaBloquesLibres() {
        bloquesLibres.clear(); // Limpiar la cola actual

        // Reconstruir la cola solo con bloques NO ocupados
        for (int i = 0; i < todosLosBloques.getLength(); i++) {
            Bloque bloque = todosLosBloques.get(i);
            if (!bloque.isOcupado()) {
                bloquesLibres.enqueue(bloque);
            }
        }
    }

    public int bloquesDisponibles() {
        return bloquesLibres.getLength();
    }

    public Bloque getBloque(int id) {
        // Implementar si se necesita acceso directo (opcional)re
        return null;
    }

    public boolean haySuficienteEspacio(int cantidad) {
        return bloquesLibres.getLength() >= cantidad;
    }

}
