/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Gson;

import EstructurasDeDatos.Queue;
import EstructurasDeDatos.Nodo;
import com.google.gson.*;
import java.lang.reflect.Type;

/**
 *
 * @author marco
 */
public class QueueAdapter<T> implements JsonSerializer<Queue<T>>, JsonDeserializer<Queue<T>> {

    @Override
    public JsonElement serialize(Queue<T> queue, Type type, JsonSerializationContext context) {
        JsonArray jsonArray = new JsonArray();
        Nodo<T> current = queue.getFirst();
        while (current != null) {
            T item = current.getData();
            JsonObject elementObj = new JsonObject();
            elementObj.addProperty("__type", item.getClass().getName());
            elementObj.add("data", context.serialize(item));
            jsonArray.add(elementObj);
            current = current.getNext();
        }
        return jsonArray;
    }

    @Override
    public Queue<T> deserialize(JsonElement json, Type type, JsonDeserializationContext context) throws JsonParseException {
        Queue<T> queue = new Queue<>();
        JsonArray jsonArray = json.getAsJsonArray();
        for (JsonElement element : jsonArray) {
            JsonObject elementObj = element.getAsJsonObject();
            String className = elementObj.get("__type").getAsString();
            try {
                Class<?> clazz = Class.forName(className);
                T item = context.deserialize(elementObj.get("data"), clazz);
                queue.enqueue(item);
            } catch (ClassNotFoundException e) {
                throw new JsonParseException("Clase no encontrada: " + className, e);
            }
        }
        return queue;
    }
}
