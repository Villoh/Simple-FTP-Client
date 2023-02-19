/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mikel.agl.cliente_ftp.controladores;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author Villoh
 */
public class ProgressController implements Initializable {
    
    private double xOffset = 0;
    private double yOffset = 0;
    
    @FXML 
    private AnchorPane rootPane;
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        makeStageDragable(rootPane);
    }    
    
    /**
     * Hace la interfaz arrastable
     * @param tittleBar
     */
    public void makeStageDragable(AnchorPane tittleBar) {

        tittleBar.setOnMousePressed((event) -> {

            xOffset = event.getSceneX();
            yOffset = event.getSceneY();

        });

        tittleBar.setOnMouseDragged((event) -> {

            LoginController.newStage.setX(event.getScreenX() - xOffset);
            LoginController.newStage.setY(event.getScreenY() - yOffset);
            LoginController.newStage.setOpacity(0.8f);

        });

        tittleBar.setOnDragDone((event) -> {

            LoginController.newStage.setOpacity(1.0f);

        });

        tittleBar.setOnMouseReleased((event) -> {

            LoginController.newStage.setOpacity(1.0f);

        });
    }
    
}
