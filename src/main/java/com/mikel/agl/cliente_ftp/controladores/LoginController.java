/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mikel.agl.cliente_ftp.controladores;

import com.mikel.agl.cliente_ftp.App;
import static com.mikel.agl.cliente_ftp.controladores.MainMenuController.rootItem;
import com.mikel.agl.cliente_ftp.hilos.TreeBuilderThread;
import com.mikel.agl.cliente_ftp.metodos.Dialogo;
import com.mikel.agl.cliente_ftp.metodos.View;
import io.github.palexdev.materialfx.controls.MFXPasswordField;
import io.github.palexdev.materialfx.controls.MFXTextField;
import io.github.palexdev.materialfx.font.MFXFontIcon;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TreeItem;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author Villoh
 */
public class LoginController implements Initializable {
    
    
    @FXML
    private MFXFontIcon minimizeIcon;

    @FXML
    private MFXFontIcon closeIcon;

    @FXML
    private MFXPasswordField pfContrasenia;

    @FXML
    private MFXTextField tfPuerto;

    @FXML
    private MFXTextField tfUsuario;
    
    @FXML
    private AnchorPane rootPane;

    @FXML
    private HBox tittleBar;
    
    public static Stage newStage;
    
    private double xOffset = 0;
    private double yOffset = 0;
    
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        makeStageDragable(tittleBar);
        newStage = new Stage(StageStyle.UNDECORATED);
    }
    
    /**
     * Cierra la aplicacion.
     * @param event 
     */
    @FXML
    private void closeApp(MouseEvent event) {
        App.st.close();
    }
    
    /**
     * Minimiza
     * @param event 
     */
    @FXML
    private void minStage(MouseEvent event) {
        App.st.setIconified(true);
    }

    @FXML
    private void connect(){
        crearVentanaPrincipal();
    }
    
    /**
     * Comprueba que el stage no se esté mostrando, en caso afirmativo te muestra un error y en caso negativo te carga una nueva ventana principal.
     */
    private void crearVentanaPrincipal(){
        if (!newStage.isShowing()) {
            View.load("progress", newStage);
            File file = new File("F:/");
            rootItem = new TreeItem<>("F:/");
            Thread treeThread = new TreeBuilderThread(file, rootItem, true);
            treeThread.start();
        }else{
            Dialogo dialogo = new Dialogo(App.st, "Error", "Ya tienes una ventana abierta y el máximo número permitido de ventanas principales abiertas es de 1", "Error, ventana principal ya abierta");
            dialogo.openError();
        }
    }
    
    /**
     * Hace la interfaz arrastable
     * @param tittleBar
     */
    public void makeStageDragable(HBox tittleBar) {

        tittleBar.setOnMousePressed((event) -> {

            xOffset = event.getSceneX();
            yOffset = event.getSceneY();

        });

        tittleBar.setOnMouseDragged((event) -> {

            App.st.setX(event.getScreenX() - xOffset);
            App.st.setY(event.getScreenY() - yOffset);
            App.st.setOpacity(0.8f);

        });

        tittleBar.setOnDragDone((event) -> {

            App.st.setOpacity(1.0f);

        });

        tittleBar.setOnMouseReleased((event) -> {

            App.st.setOpacity(1.0f);

        });
    }
}
