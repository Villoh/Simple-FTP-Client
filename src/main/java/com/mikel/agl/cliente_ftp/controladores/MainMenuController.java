package com.mikel.agl.cliente_ftp.controladores;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class MainMenuController implements Initializable{
    
    @FXML
    private TreeView<String> treeViewServer;
    
    
    @FXML
    private AnchorPane tittleBar;
    
    public static TreeItem<String> rootItem; 

    private double xOffset = 0;
    private double yOffset = 0;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        makeStageDragable(tittleBar);
        this.treeViewServer.setRoot(rootItem);
    }
    
    /**
     * Cierra la aplicacion.
     * @param event 
     */
    @FXML
    private void closeApp(MouseEvent event) {
        LoginController.newStage.close();
    }
    
    /**
     * Minimiza
     * @param event 
     */
    @FXML
    private void minStage(MouseEvent event) {
        LoginController.newStage.setIconified(true);
    }
    
    /**
     * Maximiza
     * @param event 
     */
    @FXML
    private void maxStage(MouseEvent event) {
        if(LoginController.newStage.isMaximized()){
            LoginController.newStage.setMaximized(false);
        }else{
            LoginController.newStage.setMaximized(true);
        }
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
