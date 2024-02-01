package com.example.pgz;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

public class Controller {

    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;
    @FXML
    private Button buttonSearch;
    @FXML
    private Text clockArea;
    @FXML
    private Text felt;
    @FXML
    private Text max;
    @FXML
    private Text min;
    @FXML
    private Text pressure;
    @FXML
    private TextField search;
    @FXML
    private Text temp;

    private static String getUrlContent(String urlAdress) {
        StringBuffer content = new StringBuffer();
        try {
            URL url = new URL(urlAdress);
            URLConnection urlConn = url.openConnection();

            BufferedReader br = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));
            String line;
            while ((line = br.readLine()) != null) {
                content.append(line + "\n");
            }
            br.close();
        } catch (Exception e) {
            System.out.println("Такого города не существует!");
        }
        return content.toString();
    }

    private void setClockArea(String text) {
        clockArea.setText(text);
    }

    @FXML
    void initialize() {


        buttonSearch.setOnAction(event -> {
            String getUserCity = search.getText().trim();
        if (!getUserCity.equals("")) {
            String output = getUrlContent("https://api.openweathermap.org/data/2.5/weather?q=" + getUserCity + "&appid=dcb2421b785c030fec1fec2ef99e3a52&units=metric");
            if (!output.isEmpty()) {
                JSONObject obj = new JSONObject(output);
                temp.setText("Температура: " + obj.getJSONObject("main").getDouble("temp"));
                felt.setText("Ощущается: " + obj.getJSONObject("main").getDouble("feels_like"));
                max.setText("Максимум: " + obj.getJSONObject("main").getDouble("temp_max"));
                min.setText("Минимум: " + obj.getJSONObject("main").getDouble("temp_min"));
                pressure.setText("Давление: " + obj.getJSONObject("main").getDouble("pressure"));
                System.out.println(obj);
            }
        }
        });


        Thread threadClock = new Thread(() -> {
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");

            while (true) {
                String time = sdf.format(new Date());
                setClockArea(time);
                try {
                    Thread.sleep(1000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        threadClock.setDaemon(true);
        threadClock.start();
    }


}
