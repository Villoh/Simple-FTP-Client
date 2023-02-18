/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mikel.agl.cliente_ftp.metodos;

import com.mikel.agl.cliente_ftp.App;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

/**
 *
 * @author Villoh
 */
public class LoadView {
    
    public static void load(String fxmlName) {
        Scene scene;
        try {
            scene = new Scene(loadFXML(fxmlName));
            App.st.setScene(scene);
            App.st.sizeToScene();
            App.st.centerOnScreen();
            App.st.show();
        } catch (IOException ex) {
            System.err.println(ex);
        }
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }
}
