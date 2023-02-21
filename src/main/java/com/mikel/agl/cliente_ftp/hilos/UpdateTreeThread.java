/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mikel.agl.cliente_ftp.hilos;

import com.mikel.agl.cliente_ftp.controladores.MainMenuController;
import com.mikel.agl.cliente_ftp.metodos.ConexionFTP;
import com.mikel.agl.cliente_ftp.metodos.TreeBuilder;
import javafx.scene.control.TreeItem;

/**
 *
 * @author Villoh
 */
public class UpdateTreeThread extends Thread{
    private TreeItem<String> rootItem;
    private ConexionFTP ftp;
    private MainMenuController mmc;
    
    public UpdateTreeThread(ConexionFTP ftp, TreeItem<String> rootItem, MainMenuController mmc){
        this.ftp = ftp;
        this.rootItem = rootItem;
        this.mmc = mmc;
    }
    
    @Override
    public void run(){
        rootItem.getChildren().clear();
        //Construye la vista en arbol.
        TreeBuilder.construyeArbol(ftp.getfTPClient(), rootItem, "/");
        //Setea el nodo raíz en el controlador
        mmc.setRootItem(rootItem);
    }
}
