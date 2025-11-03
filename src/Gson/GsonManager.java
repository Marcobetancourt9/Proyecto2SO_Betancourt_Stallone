/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Gson;

import Clases.SistemaArchivo;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.*;
import java.time.LocalDateTime;
import javax.swing.JOptionPane;

/**
 *
 * @author marco
 */
public class GsonManager {

    private static final Gson gson = new GsonBuilder()
            .registerTypeAdapter(EstructurasDeDatos.Lista.class, new ListaAdapter<>())
            .registerTypeAdapter(EstructurasDeDatos.Queue.class, new QueueAdapter<>())
            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
            .setPrettyPrinting()
            .create();

    public static void guardarEstado(SistemaArchivo sistema, String ruta) throws IOException {
        File file = new File(ruta);
        if (!file.exists()) {
            file.createNewFile(); // <-- Asegura la creación física del archivo
        }
        try (Writer writer = new FileWriter(file)) {
            gson.toJson(sistema, writer);
        }
    }

    public static SistemaArchivo cargarEstado(String ruta) throws FileNotFoundException {
        File file = new File(ruta);
        if (!file.exists()) {
            return null; // O lanzar una excepción según tu lógica
        }
        try (Reader reader = new FileReader(file)) {
            return gson.fromJson(reader, SistemaArchivo.class);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al cargar: " + e.getMessage());
            e.printStackTrace();
            return new SistemaArchivo();
        }
    }
}
