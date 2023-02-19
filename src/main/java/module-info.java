module com.mikel.agl.cliente_ftp {
    requires javafx.controls;
    requires javafx.fxml;
    requires MaterialFX;
    requires java.base;
    requires org.apache.commons.net;
    
    opens com.mikel.agl.cliente_ftp.controladores to javafx.fxml;
    exports com.mikel.agl.cliente_ftp;
}
