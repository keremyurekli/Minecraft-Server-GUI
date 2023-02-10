package com.keremyurekli.minecraftservergui;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.util.*;

public class Saves {

//serverpath=
//javapath=
//ram=
//port=

    private static String serverpath;
    private static String javapath;
    private static String ram;
    private static String port;


    public static File savefile = new File("saves.json");



    private static Gson gson = new GsonBuilder().setPrettyPrinting().create();


    public static void overwrite() throws IOException {
        HashMap<String,String> map = new HashMap<>();
        if(GUI.javapathSlot != null && GUI.ramSlot != null && GUI.portSlot != null){
            map.put("serverpath",Main.SERVER_JAR_PATH);
            map.put("javapath",GUI.javapathSlot.getText());
            map.put("ram",GUI.ramSlot.getText());
            map.put("port",GUI.portSlot.getText());



            BufferedWriter writer = new BufferedWriter(new FileWriter(savefile));
            writer.write(gson.toJson(map));
            writer.close();
        }

    }

//port server açıldığında propertiese kaydedilecek, uygulama kapatıldığında hepsiyle birlikte savese konulacak
    public static void putDefaults() throws IOException {

        HashMap<String,String> defaults = new HashMap<>();
        defaults.put("serverpath","");
        defaults.put("javapath","");
        defaults.put("ram","");
        defaults.put("port","");



        BufferedWriter writer = new BufferedWriter(new FileWriter(savefile));
        writer.write(gson.toJson(defaults));
        writer.close();

    }


    public static void changePort(int PORT) throws IOException {
        File temp = new File(Main.SERVER_PARENT_PATH + "\\server.properties");

        StringBuilder bl = new StringBuilder();
        Scanner myReader = new Scanner(temp);
        while (myReader.hasNextLine()) {
            String toappend = null;
            String data = myReader.nextLine();
            if(data.contains("server-port=")){
                toappend = "server-port="+PORT;
            }else{
                toappend = data;
            }
            bl.append(toappend+"\n");
        }
        myReader.close();
        String string = bl.toString();

        BufferedWriter writer = new BufferedWriter(new FileWriter(temp));
        writer.write(string);
        writer.close();
    }


    private static HashMap<String,String> read() throws FileNotFoundException {
        StringBuilder bl = new StringBuilder();
        Scanner myReader = new Scanner(savefile);
        while (myReader.hasNextLine()) {
            String data = myReader.nextLine();
            bl.append(data);
        }
        myReader.close();
        String string = bl.toString();


        HashMap<String,String> latest = gson.fromJson(string,HashMap.class);


        return latest;
    }



    public static String getValueFor(String s) throws FileNotFoundException {
        return read().get(s);
    }
}
