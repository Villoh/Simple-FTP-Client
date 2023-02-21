/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mikel.agl.cliente_ftp.controladores;

import com.mikel.agl.cliente_ftp.App;
import com.mikel.agl.cliente_ftp.hilos.ConexionFTPThread;
import com.mikel.agl.cliente_ftp.metodos.Dialogo;
import io.github.palexdev.materialfx.controls.MFXPasswordField;
import io.github.palexdev.materialfx.controls.MFXTextField;
import io.github.palexdev.materialfx.font.MFXFontIcon;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Pattern;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
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
    
    public static MFXPasswordField pfContraseniaStatic;

    @FXML
    private MFXTextField tfPuerto;

    @FXML
    private MFXTextField tfUsuario;
    
    @FXML
    private MFXTextField tfIP;

    @FXML
    private HBox tittleBar;
    
    private Dialogo dialogo;
    public static Stage newStage;
    private Thread hiloConexionFTP;
    
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
        pfContraseniaStatic = pfContrasenia; //Duplica variable, para tener una estática y poder acceder a ella.
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
    
    /**
     * Acción realizada en el botón conectar.
     */
    @FXML
    private void connect(){
        crearVentanaPrincipal();
    }
    
    /**
     * Comprueba que el stage no se esté mostrando, en caso afirmativo te muestra un error y en caso negativo te inicializa y ejecuta un hilo.
     */
    private void crearVentanaPrincipal() {
        Stage stage = new Stage(StageStyle.UNDECORATED); //Inicio la futura ventana principal. Lo tengo que hacer aqui para que solo se inicialice una vez y asi realizar comprobacuiones.
        if (isNumeric(this.tfPuerto.getText())) {
            hiloConexionFTP = new ConexionFTPThread(this.tfUsuario.getText(), this.tfIP.getText(), Integer.parseInt(this.tfPuerto.getText()), stage);
            hiloConexionFTP.start();
        }else{
            dialogo = new Dialogo(App.st, "Error", "El campo puerto introducido no es númerico o lo has dejado vacío, asegurate de que este campo sea explicitamente númerico.", "Error en el campo puerto");
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
    
    /**
     * Comprueba que un String sea númerico
     * @param string string que va a comprobar
     * @return boolean
     */
    public boolean isNumeric(String string) {
        String regex = "[0-9]+[\\.]?[0-9]*";
        return Pattern.matches(regex, string);
    }
}
