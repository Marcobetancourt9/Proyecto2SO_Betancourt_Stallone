/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Clases;

/**
 *
 * @author marco
 */
public class Bloque {

    private int id;
    private boolean ocupado;
    private Bloque siguienteBloque;
    private int siguienteId;

    public Bloque(int id) {
        this.id = id;
        this.ocupado = false;
        this.siguienteBloque = null;
    }

    public Bloque() {
    } // Constructor vacío

    // Setters
    public void setOcupado(boolean ocupado) {
        this.ocupado = ocupado;
    }

    public void setSiguienteId(int siguienteId) {
        this.siguienteId = siguienteId;
    }

    public void setSiguienteBloque(Bloque siguiente) {
        this.siguienteBloque = siguiente;
        this.siguienteId = (siguiente != null) ? siguiente.getId() : -1;
    }

    public void setId(int id) {
        this.id = id;
    }

    // Getters
    public int getId() {
        return id;
    }

    public int getSiguienteId() {
        return siguienteId;
    }

    public boolean isOcupado() {
        return ocupado;
    }

    public Bloque getSiguienteBloque() {
        return siguienteBloque;
    }
}
