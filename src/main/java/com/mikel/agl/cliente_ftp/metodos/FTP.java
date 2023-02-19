/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mikel.agl.cliente_ftp.metodos;

import java.io.IOException;
import org.apache.commons.net.ftp.FTPClient;

/**
 *
 * @author Villoh
 */
public class FTP {
    
    private String usuario;
    private String ip;
    private int port;
    
    
    private FTPClient fTPClient;
    private boolean isLogued;
    
    public FTP(String usuario, String ip, int port){
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
        try {
            fTPClient = new FTPClient();
            fTPClient.connect(ip, port);
            isLogued = fTPClient.login(usuario, contrasenia);
        } catch (IOException ex) {
            System.err.println(ex);
        }
        return isLogued;
    }
    
    public FTPClient getfTPClient() {
        return fTPClient;
    }  
}
