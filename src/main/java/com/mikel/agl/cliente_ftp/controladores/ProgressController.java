/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mikel.agl.cliente_ftp.controladores;

import static com.mikel.agl.cliente_ftp.controladores.MainMenuController.rootItem;
import com.mikel.agl.cliente_ftp.hilos.TreeBuilderThread;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.scene.control.TreeItem;

/**
 * FXML Controller class
 *
 * @author Villoh
 */
public class ProgressController implements Initializable {

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        File file = new File("F:/");
        rootItem = new TreeItem<>("F:/");
        Thread treeThread = new TreeBuilderThread(file, rootItem, true);
        treeThread.start();
    }    
    
}
