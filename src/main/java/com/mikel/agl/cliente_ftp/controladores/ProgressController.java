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
import javafx.stage.Stage;

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
    
    private Stage stage;
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

            stage.setX(event.getScreenX() - xOffset);
            stage.setY(event.getScreenY() - yOffset);
            stage.setOpacity(0.8f);

        });

        tittleBar.setOnDragDone((event) -> {

            stage.setOpacity(1.0f);

        });

        tittleBar.setOnMouseReleased((event) -> {

            stage.setOpacity(1.0f);

        });
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
