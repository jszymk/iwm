/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.controller;

import app.dicom.DicomExport;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.time.ZoneId;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Weronika
 */
public class DicomController implements Initializable {

    private Stage dicomStage;
    
    private BufferedImage out;
    
    @FXML
    private TextField firstName;
    
    @FXML
    private TextField lastName;
    
    @FXML
    private DatePicker date;
    
    @FXML
    private TextArea comment;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    

    public Stage getDicomStage() {
        return dicomStage;
    }

    public void setDicomStage(Stage dicomStage) {
        this.dicomStage = dicomStage;
    }

    public BufferedImage getOut() {
        return out;
    }

    public void setOut(BufferedImage out) {
        this.out = out;
    }
    
    
    public void close(){
        this.dicomStage.close();
    }
    
    public void save() throws IOException{
        
        new DicomExport().writeFile("dicom", this.out, firstName.getText() + " " +lastName.getText(), Date.from(date.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()), comment.getText());
        
        close();
    }
    
    
}
