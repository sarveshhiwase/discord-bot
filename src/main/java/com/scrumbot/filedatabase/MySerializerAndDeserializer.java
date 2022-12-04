package com.scrumbot.filedatabase;

import java.io.*;

public class MySerializerAndDeserializer {
    private ObjectOutputStream oos;
    private FileOutputStream fos;
    private ObjectInputStream ois;
    private FileInputStream fis;

    public MySerializerAndDeserializer(String fileName) {
        try {
            fos = new FileOutputStream(fileName);
            fis = new FileInputStream(fileName);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void serialize(Object obj) {
        try {
            oos = new ObjectOutputStream(fos);
            oos.writeObject(obj);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Serialization Done succesfully...");
    }

    public Object deserialize(Class c) {
        Object obj = null;
        try {
            ois = new ObjectInputStream(fis);
            obj = ois.readObject();
            if (obj.getClass() != c.getClass()) throw new ClassNotFoundException();
            System.out.println("Deserialization Done succesfully...");
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }
        return obj;
    }
}
