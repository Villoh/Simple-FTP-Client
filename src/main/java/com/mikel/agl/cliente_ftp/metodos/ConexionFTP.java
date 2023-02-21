/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mikel.agl.cliente_ftp.metodos;

import com.mikel.agl.cliente_ftp.App;
import com.mikel.agl.cliente_ftp.controladores.MainMenuController;
import com.mikel.agl.cliente_ftp.hilos.UpdateTreeThread;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.time.Duration;
import javafx.scene.control.TreeItem;
import javafx.stage.Stage;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

/**
 *
 * @author Villoh
 */
public class ConexionFTP {
    
    private String usuario;
    private String ip;
    private int port;
    
    
    private FTPClient ftpClient;
    private boolean logued;
    private Dialogo dialogo;
    private TreeItem<String> rootItem;
    
    public ConexionFTP(String usuario, String ip, int port){
        this.usuario = usuario;
        this.ip = ip;
        this.port = port;
    }
    
    /**
     * Realiza la conexión al servidor FTP y el login mediante el usuario pasados previamentes en el constructor.
     * Devuelve true si la conexión se realiza exitosamente y false si la conexión no se ha podido realizar.
     * @param contrasenia Contrasenia del usuario.
     * @return boolean
     */
    public boolean conectaFTP(String contrasenia){
        int replyCode;
        try {
            ftpClient = new FTPClient();
            // Establecer el tiempo de espera en milisegundos
            ftpClient.setConnectTimeout(50);
            ftpClient.setDataTimeout(Duration.ofMillis(50));
            // Establece en true la autodectección de la codificación UTF-8, de esta forma no habrá problemas con las tildes
            ftpClient.setAutodetectUTF8(true);
            //Conexión con el servidor FTP
            ftpClient.connect(ip, port);
            //Obtener el código de respuesta del intento de conexión
            replyCode = ftpClient.getReplyCode();
            if (FTPReply.isPositiveCompletion(replyCode)) {
                // Establecer el modo pasivo
                ftpClient.enterLocalPassiveMode();
                //Loguearse en el servidor
                logued = ftpClient.login(usuario, contrasenia);
                if (!logued) {
                    //Mensaje de error al loguearse
                    dialogo = new Dialogo(App.st, "Error", "No se ha podido realizar el login con el usuario indicado en el servidor FTP, pero si se ha logrado la conexión, revisa que el usuario y la contraseña sean correctos", "Error al loguearse en el servidor FTP");
                    dialogo.openError();
                    //Desconecta del servidor
                    ftpClient.disconnect();
                } else {
                    // Establecer el tipo de transferencia a binario
                    ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
                }
            }
        } catch (IOException ex) {
            //Dialogo de error
            dialogo = new Dialogo(App.st, "Error", "No se ha podido establecer una conexión con el servidor FTP indicado, revisa que la IP y el puerto sean correctos", "Error en la conexión FTP");
            dialogo.openError();
            System.err.println(ex);
        }
        return logued;
    }
    
    public FTPClient getfTPClient() {
        return ftpClient;
    }

    public boolean isLogued() {
        return logued;
    }
    
    /**
     * Sube un archivo al servidor FTP
     * @param rutaArchivoRemoto ruta del servidor FTP donde se subirá el archivo
     * @param archivoLocal archivo que se va a subir
     * @param stage stage donde se mostrará el mensaje.
     */
    public void subirArchivoFTP(String rutaArchivoRemoto, File archivoLocal, Stage stage, MainMenuController mmc) {
        if (!isLogued()) {
            dialogo = new Dialogo(stage, "Error", "No esta logueado en el servidor FTP, cierra y reintenta de nuevo.", "Error con la conexión");
            dialogo.openError();
            return;
        }
        try (InputStream inputStream = new FileInputStream(archivoLocal)) {
            if (ftpClient.storeFile(rutaArchivoRemoto + archivoLocal.getName(), inputStream)) {
                Thread treeThread = new UpdateTreeThread(this, rootItem, mmc);
                treeThread.start();
                dialogo = new Dialogo(stage, "Subido", "Se ha subido correctamente el archivo: " + archivoLocal.getName() + " al servidor FTP.", "Archivo subido correctamente");
                dialogo.openInfo();
            } else {
                dialogo = new Dialogo(stage, "Error", "No se ha podido subir el archivo: " + archivoLocal.getName() + " al servidor FTP.", "Error con la subida del archivo");
                dialogo.openError();
            }
        } catch (FileNotFoundException ex) {
            System.err.println(ex);
        } catch (IOException ex) {
            System.err.println(ex);
        }
    }
    
    /**
     * Borra un archivo del servidor FTP.
     * @param rutaArchivoRemoto ruta del servidor FTP donde se encuentra el archivo a borrar.
     * @param nombreArchivo nombre del archivo a borrar.
     * @param stage stage donde se mostrará el mensaje.
     */
    public void borrarArchivoFTP(String rutaArchivoRemoto, String nombreArchivo, Stage stage, MainMenuController mmc) {
        if (!isLogued()) {
            dialogo = new Dialogo(stage, "Error", "No esta logueado en el servidor FTP, cierra y reintenta de nuevo.", "Error con la conexión");
            dialogo.openError();
            return;
        }
        try {
            if (ftpClient.deleteFile(rutaArchivoRemoto)) {
                Thread treeThread = new UpdateTreeThread(this, rootItem, mmc);
                treeThread.start();
                dialogo = new Dialogo(stage, "Borrado", "Se ha borrado correctamente el archivo: "  + nombreArchivo + " del servidor FTP.", "Archivo borrado correctamente");
                dialogo.openInfo();
            } else {
                dialogo = new Dialogo(stage, "Error", "No se ha podido borrar el archivo: " + nombreArchivo + " del servidor FTP.", "Error con el borrado del archivo");
                dialogo.openError();
            }
        } catch (IOException ex) {
            System.err.println(ex);
        }
    }
    
    /**
     * Descarga un archivo del servidor FTP.
     * @param rutaArchivoRemoto ruta del servidor FTP donde se encuentra el archivo a descargar.
     * @param archivoLocal archivo local (donde se guardará el remoto).
     * @param stage stage donde se mostrará el mensaje.
     */
    public void descargarArchivoFTP(String rutaArchivoRemoto, File archivoLocal, Stage stage) {
        if (!isLogued()) {
            dialogo = new Dialogo(stage, "Error", "No esta logueado en el servidor FTP, cierra y reintenta de nuevo.", "Error con la conexión");
            dialogo.openError();
            return;
        }
        try (OutputStream outputStream = new BufferedOutputStream(new FileOutputStream(archivoLocal))) {
            if (ftpClient.retrieveFile(rutaArchivoRemoto, outputStream)) {
                dialogo = new Dialogo(stage, "Descargado", "Se ha descargado correctamente el archivo: "  + archivoLocal.getName() + " del servidor FTP.", "Archivo borrado correctamente");
                dialogo.openInfo();
            } else {
                dialogo = new Dialogo(stage, "Error", "No se ha podido descargar el archivo: " + archivoLocal.getName() + " del servidor FTP.", "Error con la descarga del archivo");
                dialogo.openError();
            }
        } catch (FileNotFoundException ex) {
            System.err.println(ex);
        } catch (IOException ex) {
            System.err.println(ex);
        }
    }
    
    /**
     * Crea un directorio en el servidor FTP.
     * @param rutaDirectorioRemoto ruta del servidor FTP donde se creará la carpeta.
     * @param nombreDirectorio nombre del directorio que se va a crear.
     * @param stage stage donde se mostrará el mensaje.
     * @param mmc
     */
    public void crearDirectorioFTP(String rutaDirectorioRemoto, String nombreDirectorio, Stage stage, MainMenuController mmc){
        if (!isLogued()) {
            dialogo = new Dialogo(stage, "Error", "No esta logueado en el servidor FTP, cierra y reintenta de nuevo.", "Error con la conexión");
            dialogo.openError();
            return;
        }
        try{
            if(ftpClient.makeDirectory(rutaDirectorioRemoto + nombreDirectorio)){
                Thread treeThread = new UpdateTreeThread(this, rootItem, mmc);
                treeThread.start();
                dialogo = new Dialogo(stage, "Creado", "Se ha creado correctamente el directorio: "  + nombreDirectorio + " en el servidor FTP.", "Directorio creado correctamente");
                dialogo.openInfo();
            }else{
                dialogo = new Dialogo(stage, "Error", "No se ha podido crear el directorio: " + nombreDirectorio + " del servidor FTP.", "Error con la descarga del fichero");
                dialogo.openError();
            }
        }catch (IOException ex) {
            System.err.println(ex);
        }
    }
    
    /**
     * Borra un directorio del servidor FTP.
     * @param rutaArchivoRemoto ruta del servidor FTP donde se encuentra el directorio a borrar.
     * @param nombreArchivo nombre del directorio a borrar.
     * @param stage stage donde se mostrará el mensaje.
     * @param mmc
     */
    public void borrarDirectorioFTP(String rutaArchivoRemoto, String nombreArchivo, Stage stage, MainMenuController mmc){
        if (!isLogued()) {
            dialogo = new Dialogo(stage, "Error", "No esta logueado en el servidor FTP, cierra y reintenta de nuevo.", "Error con la conexión");
            dialogo.openError();
            return;
        }
        try {
            if (ftpClient.removeDirectory(rutaArchivoRemoto)) {
                Thread treeThread = new UpdateTreeThread(this, rootItem, mmc);
                treeThread.start();
                dialogo = new Dialogo(stage, "Borrado", "Se ha borrado correctamente el directorio: "  + nombreArchivo + " del servidor FTP.", "Directorio borrado correctamente");
                dialogo.openInfo();
            } else {
                dialogo = new Dialogo(stage, "Error", "No se ha podido borrar el directorio " + nombreArchivo + " del servidor FTP.", "Error con el borrado del directorio");
                dialogo.openError();
            }
        } catch (IOException ex) {
            System.err.println(ex);
        }
    }

    public void setRootItem(TreeItem<String> rootItem) {
        this.rootItem = rootItem;
    }
}
