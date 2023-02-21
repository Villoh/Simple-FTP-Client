/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mikel.agl.cliente_ftp.metodos;

import com.mikel.agl.cliente_ftp.App;
import static com.mikel.agl.cliente_ftp.controladores.MainMenuController.rootItem;
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
            // Establecer el modo pasivo
            ftpClient.enterLocalPassiveMode();
            // Establecer el tiempo de espera en milisegundos
            ftpClient.setConnectTimeout(100);
            ftpClient.setDataTimeout(Duration.ofMillis(100));
            //Conexión con el servidor FTP
            ftpClient.connect(ip, port);
            //Obtener el código de respuesta del intento de conexión
            replyCode = ftpClient.getReplyCode();
            if(FTPReply.isPositiveCompletion(replyCode)){
                //Loguearse en el servidor
                logued = ftpClient.login(usuario, contrasenia);
                if(!logued){
                    //Mensaje de error al loguearse
                    dialogo = new Dialogo(App.st, "Error", "No se ha podido realizar el login con el usuario indicado en el servidor FTP, pero si se ha logrado la conexión, revisa que el usuario y la contraseña sean correctos", "Error al loguearse en el servidor FTP");
                    dialogo.openError();
                    //Desconecta del servidor
                    ftpClient.disconnect();
                }else{
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
     */
    public void subirArchivoFTP(String rutaArchivoRemoto, File archivoLocal) {
        if (!isLogued()) {
            dialogo = new Dialogo(null, "Error", "No esta logueado en el servidor FTP, cierra y reintenta de nuevo", "Error con la conexión");
            dialogo.openError();
            return;
        }
        try (InputStream inputStream = new FileInputStream(archivoLocal)) {
            if (ftpClient.storeFile(rutaArchivoRemoto, inputStream)) {
                Thread treeThread = new UpdateTreeThread(this, rootItem);
                treeThread.start();
                dialogo = new Dialogo(null, "Subido", "Se ha subido correctamente el archivo: " + archivoLocal.getName() + " al servidor FTP", "Archivo subido correctamente");
                dialogo.openInfo();
            } else {
                dialogo = new Dialogo(null, "Error", "No se ha podido subir el fichero " + archivoLocal.getName() + " al servidor", "Error con la subida del fichero");
                dialogo.openError();
            }
        } catch (FileNotFoundException ex) {
            System.err.println(ex);
        } catch (IOException ex) {
            System.err.println(ex);
        }
    }
    
    /**
     * Borra un archivo del servidor FTP
     * @param rutaArchivoRemoto ruta del servidor FTP donde se encuentra el archivo a borrar.
     * @param nombreArchivo 
     */
    public void borrarArchivoFTP(String rutaArchivoRemoto, String nombreArchivo) {
        if (!isLogued()) {
            dialogo = new Dialogo(null, "Error", "No esta logueado en el servidor FTP, cierra y reintenta de nuevo", "Error con la conexión");
            dialogo.openError();
            return;
        }
        try {
            if (ftpClient.deleteFile(rutaArchivoRemoto)) {
                Thread treeThread = new UpdateTreeThread(this, rootItem);
                treeThread.start();
                dialogo = new Dialogo(null, "Borrado", "Se ha borrado correctamente el archivo: "  + nombreArchivo + " del servidor FTP", "Archivo borrado correctamente");
                dialogo.openInfo();
            } else {
                dialogo = new Dialogo(null, "Error", "No se ha podido borrar el fichero " + nombreArchivo + " del servidor", "Error con el borrado del fichero");
                dialogo.openError();
            }
        } catch (IOException ex) {
            System.err.println(ex);
        }
    }
    
    public void descargarArchivoFTP(String rutaArchivoRemoto, File archivoLocal) {
        if (!isLogued()) {
            dialogo = new Dialogo(null, "Error", "No esta logueado en el servidor FTP, cierra y reintenta de nuevo", "Error con la conexión");
            dialogo.openError();
            return;
        }
        try (OutputStream outputStream = new BufferedOutputStream(new FileOutputStream(archivoLocal))) {
            if (ftpClient.retrieveFile(rutaArchivoRemoto, outputStream)) {
                dialogo = new Dialogo(null, "Descargado", "Se ha descargado correctamente el archivo: "  + archivoLocal.getName() + " del servidor FTP", "Archivo borrado correctamente");
                dialogo.openInfo();
            } else {
                dialogo = new Dialogo(null, "Error", "No se ha podido descargar el fichero " + archivoLocal.getName() + " del servidor", "Error con la descarga del fichero");
                dialogo.openError();
            }
        } catch (FileNotFoundException ex) {
            System.err.println(ex);
        } catch (IOException ex) {
            System.err.println(ex);
        }
    }
}
