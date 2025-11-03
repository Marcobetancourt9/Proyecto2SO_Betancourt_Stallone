/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Clases;

import EstructurasDeDatos.Lista;

/**
 *
 * @author marco
 */
public class Directorio {

    private String nombre;
    private Lista<Directorio> subdirectorios;
    private Lista<Archivo> archivos;

    public Directorio(String nombre) {
        this.nombre = nombre;
        this.subdirectorios = new Lista<>();
        this.archivos = new Lista<>();
    }

    // Constructor vacío necesario para Gson
    public Directorio() {
        this.subdirectorios = new Lista<>();
        this.archivos = new Lista<>();
    }

    // Métodos CRUD para archivos
    public void agregarArchivo(Archivo archivo) {
        if (!archivos.contains(archivo)) {
            archivos.insertLast(archivo);
        }
    }

    public Archivo buscarArchivo(String nombreArchivo) {
        for (int i = 0; i < archivos.getLength(); i++) {
            if (archivos.get(i).getNombre().equals(nombreArchivo)) {
                return archivos.get(i);
            }
        }
        return null;
    }

    public void eliminarArchivo(String nombreArchivo) {
        for (int i = 0; i < archivos.getLength(); i++) {
            if (archivos.get(i).getNombre().equals(nombreArchivo)) {
                archivos.deleteIndex(i);
                return; // Salir del bucle después de eliminar
            }
        }
    }

    // Métodos para subdirectorios
    public void agregarSubdirectorio(Directorio subdirectorio) {
        if (!subdirectorios.contains(subdirectorio)) {
            subdirectorios.insertLast(subdirectorio);
        }
    }

    public boolean buscarSubdirectorio(String nombre) {
        for (int i = 0; i < subdirectorios.getLength(); i++) {
            if (subdirectorios.get(i).getNombre().equals(nombre)) {
                return true;
            }
        }
        return false;
    }

    public boolean eliminarSubdirectorio(String nombre, MemoryManager memoryManager) {
        for (int i = 0; i < subdirectorios.getLength(); i++) {
            Directorio subdir = subdirectorios.get(i);
            if (subdir.getNombre().equals(nombre)) {
                liberarRecursos(subdir, memoryManager);
                subdirectorios.deleteIndex(i);
                return true;
            }
        }
        return false;
    }

    private void liberarRecursos(Directorio dir, MemoryManager memoryManager) {
        // Liberar archivos
        for (int i = 0; i < dir.getArchivos().getLength(); i++) {
            Archivo archivo = dir.getArchivos().get(i);
            memoryManager.liberarBloques(archivo.getBloquesAsignados());
        }
        // Liberar subdirectorios recursivamente
        for (int i = 0; i < dir.getSubdirectorios().getLength(); i++) {
            liberarRecursos(dir.getSubdirectorios().get(i), memoryManager);
        }
    }

    // Getters y Setters
    public String getNombre() {
        return nombre;
    }

    public Lista<Directorio> getSubdirectorios() {
        return subdirectorios;
    }

    public Lista<Archivo> getArchivos() {
        return archivos;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setSubdirectorios(Lista<Directorio> subdirectorios) {
        this.subdirectorios = subdirectorios;
    }

    public void setArchivos(Lista<Archivo> archivos) {
        this.archivos = archivos;
    }

    @Override
    public String toString() {
        return this.nombre;
    }
}
