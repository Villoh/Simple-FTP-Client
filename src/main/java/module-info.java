module com.mikel.agl.cliente_ftp {
    requires javafx.controls;
    requires javafx.fxml;
    requires MaterialFX;
    requires java.base;
    
    opens com.mikel.agl.cliente_ftp.controladores to javafx.fxml;
    exports com.mikel.agl.cliente_ftp;
}
