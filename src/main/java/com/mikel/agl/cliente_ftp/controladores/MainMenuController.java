package com.mikel.agl.cliente_ftp.controladores;

import com.mikel.agl.cliente_ftp.metodos.ConexionFTP;
import com.mikel.agl.cliente_ftp.metodos.Dialogo;
import com.mikel.agl.cliente_ftp.metodos.FileUtils;
import io.github.palexdev.materialfx.controls.MFXButton;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.apache.commons.net.ftp.FTPFile;

public class MainMenuController implements Initializable{
    
    @FXML
    private TreeView<String> treeViewServer;
    
    @FXML
    private AnchorPane tittleBar;
    
    @FXML
    private AnchorPane rootPane;
    
     @FXML
    private MFXButton buttonBorrar;

    @FXML
    private MFXButton buttonDescargar;

    @FXML
    private MFXButton buttonSubir;
    
    public static TreeItem<String> rootItem; 
    public Stage stage;
    private ConexionFTP ftp;
    private String itemSelectedAbsolutePath;
    private String itemSelectedName;
    private Dialogo dialogo;
    private File archivo;
    
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
        stage.close();
    }
    
    /**
     * Minimiza
     * @param event 
     */
    @FXML
    private void minStage(MouseEvent event) {
        stage.setIconified(true);
    }
    
    /**
     * Maximiza
     * @param event 
     */
    @FXML
    private void maxStage(MouseEvent event) {
        if(stage.isMaximized()){
            stage.setMaximized(false);
        }else{
            stage.setMaximized(true);
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
    
    @FXML
    private void onItemSelected(MouseEvent event) {
        TreeItem<String> itemSelected = treeViewServer.getSelectionModel().getSelectedItem();
        if (itemSelected != null) {
            itemSelectedAbsolutePath = getAbsolutePath(itemSelected);
            itemSelectedName = itemSelected.getValue();
            System.out.println(itemSelectedAbsolutePath);
        }
    }
    
    /**
     * Acción del botón subir
     */
    @FXML
    private void onSubirAction(){ 
       if(isItemSelected() && isItemDirectory() && isFileValid()){
            ftp.subirArchivoFTP(itemSelectedAbsolutePath+archivo.getName(), archivo);
        }
    }
    
    /**
     * Acción del botón borrar
     */
    @FXML
    private void onBorrarAction(){
        if(isItemSelected() && isItemFile()){
            ftp.borrarArchivoFTP(itemSelectedAbsolutePath, itemSelectedName);
        }
    }
    
    /**
     * Acción del botón descargar
     */
    @FXML
    private void onDescargarAction(){
        if(isItemSelected() && isItemFile() && isDownloadFileValid()){
            ftp.descargarArchivoFTP(itemSelectedAbsolutePath, archivo);
        }
    }
    
    /**
     * Comprueba que hay un item seleccionado en la vista de arbol
     * @return boolean
     */
    private boolean isItemSelected(){
        if(itemSelectedAbsolutePath != null){
            if(!itemSelectedAbsolutePath.equals("")){
                return true;
            }else{
                dialogo = new Dialogo(stage, "Error", "No has seleccionado nada, por favor selecciona un directorio para poder subir el archivo.", "Error con el item seleccionado");
                dialogo.openError();
                return false;
            }
        }else{
            dialogo = new Dialogo(stage, "Error", "No has seleccionado nada, por favor selecciona un directorio para poder subir el archivo.", "Error con el item seleccionado");
            dialogo.openError();
            return false;
        }
    }
    
    /**
     * Comprueba que el item seleccionado se trata de un directorio
     * @return boolean
     */
    private boolean isItemDirectory(){
        FTPFile archivoRemoto;
        try {
            archivoRemoto = ftp.getfTPClient().mlistFile(itemSelectedAbsolutePath);
            if(archivoRemoto.isDirectory()){
                return true;
            }else{
                dialogo = new Dialogo(stage, "Error", "El fichero seleccionado es un archivo, repite el proceso y selecciona un directorio", "Error con el item seleccionado");
                dialogo.openError();
                return false;
            }
        } catch (IOException ex) {
            System.err.println(ex);
        }
        return false;
    }
    
    /**
     * Comprueba que el item seleccionado se trata de un archivo
     * @return boolean
     */
    private boolean isItemFile(){
        FTPFile archivoRemoto;
        try {
            archivoRemoto = ftp.getfTPClient().mlistFile(itemSelectedAbsolutePath);
            if(archivoRemoto.isFile()){
                return true;
            }else{
                dialogo = new Dialogo(stage, "Error", "El fichero seleccionado es un directorio, repite el proceso y selecciona un archivo", "Error con el item seleccionado");
                dialogo.openError();
                return false;
            }
        } catch (IOException ex) {
            System.err.println(ex);
        }
        return false;
    }
    
    /**
     * Comprueba que el archivo seleccionado en el FileChooser no sea nulo
     * @return boolean
     */
    private boolean isFileValid(){
        archivo = FileUtils.seleccionaFicheroASubir(stage);
        if(archivo != null){
           return true; 
        }else{
            dialogo = new Dialogo(stage, "Error", "No has seleccionado ningún archivo, repite el proceso y asegurate de seleccionar un archivo", "Error con el archivo");
            dialogo.openError();
            return false;
        }
    }
    
    /**
     * Comprueba que el archivo seleccionado en el FileChooser no sea nulo
     * @return boolean
     */
    private boolean isDownloadFileValid(){
        archivo = FileUtils.seleccionaFicheroAGuardar(stage, itemSelectedName);
        
        if(archivo != null){
           return true; 
        }else{
            dialogo = new Dialogo(stage, "Error", "No has seleccionado ningún directorio de guardado, repite el proceso y asegurate de seleccionar un directorio", "Error con el directorio");
            dialogo.openError();
            return false;
        }
    }
    
    public String getAbsolutePath(TreeItem<String> selectedItem) {
        String absolutePath = "";
        TreeItem<String> item = selectedItem;
        while (item.getParent() != null) {
            // omitir la raíz
            if (item.getParent().getValue() != null) {
                absolutePath = item.getValue() + "/" + absolutePath;
            }
            item = item.getParent();
        }
        absolutePath = "/" + absolutePath;
        return absolutePath;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
    
    public void setFTP(ConexionFTP ftp) {
        this.ftp = ftp;
    }
}
