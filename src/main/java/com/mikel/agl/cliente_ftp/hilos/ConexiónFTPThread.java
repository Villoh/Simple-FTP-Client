/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mikel.agl.cliente_ftp.hilos;

import com.mikel.agl.cliente_ftp.App;
import com.mikel.agl.cliente_ftp.controladores.LoginController;
import static com.mikel.agl.cliente_ftp.controladores.LoginController.newStage;
import static com.mikel.agl.cliente_ftp.controladores.MainMenuController.rootItem;
import com.mikel.agl.cliente_ftp.metodos.Dialogo;
import com.mikel.agl.cliente_ftp.metodos.FTP;
import com.mikel.agl.cliente_ftp.metodos.View;
import javafx.application.Platform;
import javafx.scene.control.TreeItem;

/**
 *
 * @author Villoh
 */
public class ConexiónFTPThread extends Thread{
    
    private String usuario;
    private String ip;
    private int port;
    
    private FTP ftp;
    private Dialogo dialogo;
    
    public ConexiónFTPThread(String usuario, String ip, int port){
        this.usuario = usuario;
        this.ip = ip;
        this.port = port;
    }
    
    @Override
    public void run() {
        //Inicializa el objeto FTP con los datos para realizar la conexión posteriormente
        ftp = new FTP(usuario, ip, port);
        Platform.runLater(() -> {
            //Comprueba que la conexión al servidor y el usuario son validos.
            if (ftp.conectaFTP(LoginController.pfContraseniaStatic.getText()) == true) {
                //Carga el fxml progress por si tiene que cargar un arbol de directorios muy grande
                View.load("progress", newStage);
                //Inicializa el rootItem del controlador MainMenu que será el padre del vista en arbol.
                rootItem = new TreeItem<>("Ruta raíz del servidor FTP");
                //Inicializa un hilo para construir la vista en arbol.
                Thread treeThread = new TreeBuilderThread(ftp.getfTPClient(), rootItem);
                treeThread.start();
            }else{
                dialogo = new Dialogo(App.st, "Error", "No se ha podido establecer una conexión con el servidor FTP indicado, revisa los datos", "Error en la conexión FTP");
                dialogo.openError();
            }
        });
    }
}
