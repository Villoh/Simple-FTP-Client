/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mikel.agl.cliente_ftp.hilos;

import com.mikel.agl.cliente_ftp.controladores.LoginController;
import static com.mikel.agl.cliente_ftp.controladores.MainMenuController.rootItem;
import com.mikel.agl.cliente_ftp.metodos.ConexionFTP;
import com.mikel.agl.cliente_ftp.metodos.View;
import javafx.application.Platform;
import javafx.scene.control.TreeItem;
import javafx.stage.Stage;

/**
 *
 * @author Villoh
 */
public class ConexionFTPThread extends Thread{
    
    private String usuario;
    private String ip;
    private int port;
    private Stage stage;
    
    private ConexionFTP ftp;
    
    public ConexionFTPThread(String usuario, String ip, int port, Stage stage){
        this.usuario = usuario;
        this.ip = ip;
        this.port = port;
        this.stage = stage;
    }
    
    @Override
    public void run() {
        //Inicializa el objeto ConexionFTP con los datos para realizar la conexión posteriormente
        ftp = new ConexionFTP(usuario, ip, port);
        Platform.runLater(() -> {
            //Comprueba que la conexión al servidor y el usuario son validos.
            if (ftp.conectaFTP(LoginController.pfContraseniaStatic.getText())) {
                //Carga el fxml progress por si tiene que cargar un arbol de directorios muy grande
                View.loadProgress(stage);
                //Inicializa el rootItem del controlador MainMenu que será el padre del vista en arbol.
                rootItem = new TreeItem<>("Ruta raíz del servidor FTP");
                //Inicializa un hilo para construir la vista en arbol.
                Thread treeThread = new TreeBuilderThread(ftp, rootItem, stage);
                treeThread.start();
            }
        });
    }
}
