package com.keremyurekli.minecraftservergui;

import com.github.alexdlaird.ngrok.NgrokClient;
import com.github.alexdlaird.ngrok.conf.JavaNgrokConfig;
import com.github.alexdlaird.ngrok.installer.NgrokVersion;
import com.github.alexdlaird.ngrok.protocol.CreateTunnel;
import com.github.alexdlaird.ngrok.protocol.Proto;
import com.github.alexdlaird.ngrok.protocol.Tunnel;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import javafx.application.Platform;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;
import java.util.concurrent.Executors;

import static java.nio.charset.StandardCharsets.UTF_8;

public class Util {
    static final JavaNgrokConfig javaNgrokConfig = new JavaNgrokConfig.Builder().withNgrokVersion(NgrokVersion.V3).build();
    private static NgrokClient ngrokClient;

    public static void inputHandler(InputStream inputStream) throws IOException, InterruptedException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String line = bufferedReader.readLine();
        while (line != null) {
            if (Main.logger != null) {
                final String str = line + "\n";
                Platform.runLater(() -> {
                    Main.logger.appendText(str);
                });
                if (str.contains("[Server thread/INFO]: Stopping server") || str.contains("[Server thread/INFO]: Stopping the server")) {
                    GUI.buttonUpdate();
                }
            }
            line = bufferedReader.readLine();
        }
    }

    public static void outputHandler(OutputStream outputStream, String str) throws IOException {
        if(outputStream != null){
                outputStream.write(str.getBytes(UTF_8));
                outputStream.flush();
        }
    }

    public static Process initProcess(int RAM, String JAVAPATH) throws IOException {
        if (JAVAPATH == null) {
            JAVAPATH = "java";
        }
        ProcessBuilder pb = new ProcessBuilder(JAVAPATH, "-Xmx" + RAM + "m", "-jar", Main.SERVER_JAR.getName(), "nogui");
        pb.directory(Main.SERVER_PARENT);

        Process p = pb.start();
        Main.inputStream = p.getInputStream();
        Main.outputStream = p.getOutputStream();
        Executors.newSingleThreadExecutor().execute(() -> {
            try {
                inputHandler(Main.inputStream);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        return p;
    }

    public static void httpHandle() throws IOException {
        String url = "https://api.mcstatus.io/v2/status/java/" + Main.NGROK_IP;
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        // optional default is GET
        con.setRequestMethod("GET");
        //add request header
        con.setRequestProperty("User-Agent", "Mozilla/5.0");
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        JsonObject j1 = (JsonObject) JsonParser.parseString(response.toString());

        JsonObject j2 = null;
        JsonObject j3 = null;
        JsonObject j4 = null;
        if (JsonParser.parseString(String.valueOf(j1.get("players"))) != JsonNull.INSTANCE) {
            j2 = (JsonObject) JsonParser.parseString(String.valueOf(j1.get("players")));
        }
        if (JsonParser.parseString(String.valueOf(j1.get("version"))) != JsonNull.INSTANCE) {
            j3 = (JsonObject) JsonParser.parseString(String.valueOf(j1.get("version")));
        }
        if (JsonParser.parseString(String.valueOf(j1.get("motd"))) != JsonNull.INSTANCE) {
            j4 = (JsonObject) JsonParser.parseString(String.valueOf(j1.get("motd")));
        }
        Main.MC_ONLINE_PLAYERS = j2.asMap().get("online").getAsInt();
        Main.MC_PLAYERS_MAX = j2.asMap().get("max").getAsInt();

        Main.MC_MOTD = j4.asMap().get("clean").getAsString().replace("\n", "");
        Main.MC_VERSION = j3.asMap().get("name_clean").getAsString();

    }

    public static String ngrokSwitch(String port, String authtoken) {
        String rtrn = null;
        if (!Main.NGROK_IS_RUNNING) {
            Main.NGROK_IS_RUNNING = true;
            if (ngrokClient == null) {
                ngrokClient = new NgrokClient.Builder().withJavaNgrokConfig(javaNgrokConfig).build();
                if (readNgrok() != authtoken) {
                    ngrokClient.setAuthToken(authtoken);
                }
            }
            CreateTunnel sshCreateTunnel = new CreateTunnel.Builder().withProto(Proto.TCP).withAddr(port).build();
            Tunnel sshTunnel = ngrokClient.connect(sshCreateTunnel);
            ngrokClient.getNgrokProcess();
            Main.NGROK_IP = sshTunnel.getPublicUrl().replace("tcp://", "");
            rtrn = Main.NGROK_IP;

        } else {
            if (ngrokClient != null) {
                Main.NGROK_IS_RUNNING = false;
                ngrokClient.kill();
                rtrn = null;
            }


        }

        return rtrn;
    }

    public static String readNgrok() {
        String end = null;
        try {
            File myObj = new File("C:\\Users\\" + System.getProperty("user.name") + "\\.ngrok2\\ngrok.yml");
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                JsonObject j1 = (JsonObject) JsonParser.parseString(data);
                end = j1.asMap().get("authtoken").toString();
                end = end.substring(1, end.length() - 1);
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred while reading default ngrok file.");
            e.printStackTrace();
        }
        return end;
    }

}
