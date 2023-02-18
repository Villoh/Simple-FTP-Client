package com.mikel.agl.cliente_ftp.controladores;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

public class MainMenuController implements Initializable{
    
    @FXML
    private TreeView<String> treeViewServer;
    
    public static TreeItem<String> rootItem;  

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.treeViewServer.setRoot(rootItem);
    }
    
    
}
