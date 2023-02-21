/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mikel.agl.cliente_ftp.metodos;

import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import io.github.palexdev.materialfx.dialogs.MFXGenericDialog;
import io.github.palexdev.materialfx.dialogs.MFXGenericDialogBuilder;
import io.github.palexdev.materialfx.dialogs.MFXStageDialog;
import io.github.palexdev.materialfx.enums.FloatMode;
import io.github.palexdev.materialfx.enums.ScrimPriority;
import io.github.palexdev.materialfx.font.MFXFontIcon;
import java.util.Map;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author Villoh
 */
public class Dialogo {
    private MFXGenericDialog dialogContent;
    private MFXStageDialog dialog;
    private MFXTextField textoFieldInput;
    
    public Dialogo(Stage stage, String titulo, String textoContenido, String textoHeader) {
        this.dialogContent = MFXGenericDialogBuilder.build()
                .setContentText(textoContenido)
                .setShowAlwaysOnTop(false)
                .setHeaderText(textoHeader)
                .makeScrollable(true)
                .get();
        this.dialog = MFXGenericDialogBuilder.build(dialogContent)
                .toStageDialogBuilder()
                .initOwner(stage)
                .initModality(Modality.APPLICATION_MODAL)
                .setDraggable(true)
                .setTitle(titulo)
                //.setOwnerNode(parent)
               
                .setScrimPriority(ScrimPriority.WINDOW)
                .setScrimOwner(true)
                .get();

        dialogContent.addActions(
                Map.entry(new MFXButton("Confirmar"), event -> dialog.close())
        );
        dialogContent.setMaxSize(400, 200);
    }
    
    
    /**
     * Mensaje de información.
     */
    public void openInfo() {
        MFXFontIcon infoIcon = new MFXFontIcon("mfx-info-circle", 18);
        dialogContent.setHeaderIcon(infoIcon);
        convertDialogTo("mfx-info-dialog");
        dialog.showDialog();
    }
    
    /**
     * Mensaje de información con input.
     * @param promptText
     */
    public void openInput(String promptText) {
        MFXFontIcon infoIcon = new MFXFontIcon("mfx-info-circle", 18);
        dialogContent.setHeaderIcon(infoIcon);
        dialogContent.clearActions();
        textoFieldInput = new MFXTextField();
        textoFieldInput.setFloatMode(FloatMode.INLINE);
        textoFieldInput.setPrefSize(250, 50);
        textoFieldInput.setFloatingText(promptText);
        dialogContent.addActions(
            Map.entry(textoFieldInput, event -> {}),
            Map.entry(new MFXButton("Confirmar"), event -> dialog.close()));
        convertDialogTo("mfx-info-dialog");
        dialog.showAndWait();
    }
    
    /**
     * Mensaje de advertencia.
     */
    public void openWarning() {
        MFXFontIcon warnIcon = new MFXFontIcon("mfx-do-not-enter-circle", 18);
        dialogContent.setHeaderIcon(warnIcon);
        convertDialogTo("mfx-warn-dialog");
        dialog.showDialog();
    }
    
    /**
     * Mensaje de error.
     */
    public void openError() {
        MFXFontIcon errorIcon = new MFXFontIcon("mfx-exclamation-circle-filled", 18);
        dialogContent.setHeaderIcon(errorIcon);
        convertDialogTo("mfx-error-dialog");
        dialog.showDialog();
    }
    
    /**
     * Mensaje genérico.
     */
    public void openGeneric() {
        dialogContent.setHeaderIcon(null);
        convertDialogTo(null);
        dialog.showDialog();
    }
    
    /**
     * Cambia el estilo de la clase dialogContent en base al tipo de dialogo
     * @param styleClass estilo de clase.
     */
    private void convertDialogTo(String styleClass) {
        dialogContent.getStyleClass().removeIf(
                s -> s.equals("mfx-info-dialog") || s.equals("mfx-warn-dialog") || s.equals("mfx-error-dialog")
        );

        if (styleClass != null) {
            dialogContent.getStyleClass().add(styleClass);
        }
    }

    public String getTextoFieldInput() {
        return textoFieldInput.getText();
    }
}
