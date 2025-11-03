/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Clases;

import EstructurasDeDatos.Lista;
import Gson.GsonManager;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Random;
import javax.swing.JOptionPane;

/**
 *
 * @author marco
 */
public class SistemaArchivo {

    private Directorio raiz;
    private MemoryManager memoryManager;

    public SistemaArchivo() {
        this.raiz = new Directorio("Raíz");
        this.memoryManager = new MemoryManager(100);
        inicializarDatosPorDefecto();
    }

    private void inicializarDatosPorDefecto() {
        // Crear estructura mínima para evitar JSON vacío
        Directorio ejemploDir = new Directorio("Ejemplo");
        Archivo ejemploArchivo = new Archivo("init.txt");
        ejemploArchivo.setTamañoBloques(1);

        // Asignar bloques reales
        Lista<Bloque> bloques = memoryManager.asignarBloquesEncadenados(1);
        ejemploArchivo.setBloquesAsignados(bloques);

        ejemploDir.agregarArchivo(ejemploArchivo);
        raiz.agregarSubdirectorio(ejemploDir);
    }

    public void crearDirectorio(String nombrePadre, String nombreDirectorio) {
        Directorio padre = buscarDirectorio(raiz, nombrePadre);
        if (padre != null && !padre.buscarSubdirectorio(nombreDirectorio)) {
            Directorio nuevo = new Directorio(nombreDirectorio);
            padre.agregarSubdirectorio(nuevo);
        }
    }

    public void eliminarDirectorio(String nombrePadre, String nombreDirectorio) throws Exception {
        Directorio padre = buscarDirectorio(raiz, nombrePadre);
        if (padre != null) {
            padre.eliminarSubdirectorio(nombreDirectorio, memoryManager);
        }
    }

    public void crearArchivo(String nombreDirectorio, String nombreArchivo, int tamano) {
        Directorio dir = buscarDirectorio(raiz, nombreDirectorio);
        if (dir != null && memoryManager.bloquesDisponibles() >= tamano) {
            Lista<Bloque> bloques = memoryManager.asignarBloquesEncadenados(tamano);
            if (bloques != null) {
                Archivo archivo = new Archivo(nombreArchivo);
                archivo.setTamañoBloques(bloques.getLength());
                archivo.setBloquesAsignados(bloques);
                dir.agregarArchivo(archivo);
            }
        }
    }

    public void eliminarArchivo(String nombreDirectorio, String nombreArchivo) {
        Directorio dir = buscarDirectorio(raiz, nombreDirectorio);
        if (dir != null) {
            Archivo archivo = dir.buscarArchivo(nombreArchivo);
            if (archivo != null) {
                memoryManager.liberarBloques(archivo.getBloquesAsignados());
                dir.eliminarArchivo(nombreArchivo);
            }
        }
    }

    public Directorio buscarDirectorio(Directorio actual, String nombre) {
        if (actual.getNombre().equals(nombre)) {
            return actual;
        }
        for (int i = 0; i < actual.getSubdirectorios().getLength(); i++) {
            Directorio encontrado = buscarDirectorio(actual.getSubdirectorios().get(i), nombre);
            if (encontrado != null) {
                return encontrado;
            }
        }
        return null;
    }

    public void crearDirectoriosYArchivosAleatorios() {
        Random rand = new Random();
        for (int i = 1; i <= 5; i++) {
            String dirName = "Directorio_" + i;
            crearDirectorio("Raíz", dirName);
            for (int j = 1; j <= 2; j++) {
                String fileName = "Archivo_" + rand.nextInt(1000);
                int tamaño = rand.nextInt(15) + 1;
                crearArchivo(dirName, fileName, tamaño);
            }
        }
    }

    public void guardarEstado(String rutaArchivo) {
        try {
            GsonManager.guardarEstado(this, rutaArchivo);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error al guardar: " + e.getMessage());
        }
    }

    public static SistemaArchivo cargarEstado(String rutaArchivo) {
        File file = new File(rutaArchivo);

        try {
            // Crear nuevo sistema si el archivo no existe
            if (!file.exists() || file.length() == 0) {
                SistemaArchivo nuevoSistema = new SistemaArchivo();
                nuevoSistema.guardarEstado(rutaArchivo);
                return nuevoSistema;
            }

            // Cargar desde archivo
            SistemaArchivo sistema = GsonManager.cargarEstado(rutaArchivo);

            // Reconstruir enlaces entre bloques
            sistema.getMemoryManager().reconstruirSiguienteBloques();
            sistema.getMemoryManager().reconstruirColaBloquesLibres(); // Cola de libres

            reemplazarBloquesEnArchivos(sistema.getRaiz(), sistema.getMemoryManager());
            return sistema;

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al cargar: " + e.getMessage());
            return new SistemaArchivo(); // Sistema limpio como fallback
        }
    }

    private static void reemplazarBloquesEnArchivos(Directorio dir, MemoryManager mm) {
        // Reemplazar bloques en archivos del directorio
        for (int i = 0; i < dir.getArchivos().getLength(); i++) {
            Archivo archivo = dir.getArchivos().get(i);
            Lista<Bloque> nuevosBloques = new Lista<>();
            for (int j = 0; j < archivo.getBloquesAsignados().getLength(); j++) {
                Bloque bloqueOriginal = archivo.getBloquesAsignados().get(j);
                Bloque bloqueReal = mm.findBloqueById(bloqueOriginal.getId());
                if (bloqueReal != null) {
                    nuevosBloques.insertLast(bloqueReal);
                }
            }
            archivo.setBloquesAsignados(nuevosBloques);
        }
        // Procesar subdirectorios recursivamente
        for (int i = 0; i < dir.getSubdirectorios().getLength(); i++) {
            reemplazarBloquesEnArchivos(dir.getSubdirectorios().get(i), mm);
        }
    }

    // Getters
    public Directorio getRaiz() {
        return raiz;
    }

    public MemoryManager getMemoryManager() {
        return memoryManager;
    }
}
