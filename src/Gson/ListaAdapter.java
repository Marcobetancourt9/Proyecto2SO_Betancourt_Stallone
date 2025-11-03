/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Gson;

import EstructurasDeDatos.Lista;
import com.google.gson.*;
import java.lang.reflect.Type;

/**
 *
 * @author marco
 */
public class ListaAdapter<T> implements JsonSerializer<Lista<T>>, JsonDeserializer<Lista<T>> {

    @Override
    public JsonElement serialize(Lista<T> lista, Type type, JsonSerializationContext context) {
        JsonArray jsonArray = new JsonArray();
        for (int i = 0; i < lista.getLength(); i++) {
            T item = lista.get(i);
            JsonObject elementObj = new JsonObject();
            elementObj.addProperty("__type", item.getClass().getName());
            elementObj.add("data", context.serialize(item));
            jsonArray.add(elementObj);
        }
        return jsonArray;
    }

    @Override
    public Lista<T> deserialize(JsonElement json, Type type, JsonDeserializationContext context) throws JsonParseException {
        Lista<T> lista = new Lista<>();
        JsonArray jsonArray = json.getAsJsonArray();
        for (JsonElement element : jsonArray) {
            JsonObject elementObj = element.getAsJsonObject();
            String className = elementObj.get("__type").getAsString();
            try {
                Class<?> clazz = Class.forName(className);
                T item = context.deserialize(elementObj.get("data"), clazz);
                lista.insertLast(item);
            } catch (ClassNotFoundException e) {
                throw new JsonParseException("Clase no encontrada: " + className, e);
            }
        }
        return lista;
    }
}
