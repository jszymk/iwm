/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.controller;

import app.Application;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Weronika
 */
public class SettingsController implements Initializable {
    
    private Stage settingStage;
    private Application app;
    

    @FXML
    private TextField alfa;
    
    @FXML
    private TextField n;
    
    @FXML
    private TextField l;
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    public void setMain(Application app){
        this.app = app;
    }
    
    
    public void ok(){
        this.app.setAlfa(Double.valueOf(alfa.getText()));
        this.app.setL(Double.valueOf(l.getText()));
        this.app.setN(Integer.parseInt(n.getText()));
        closeWindow();
    }
    
    public void closeWindow(){
        this.settingStage.close();
        
    }

    public Stage getSettingStage() {
        return settingStage;
    }

    public void setSettingStage(Stage settingStage) {
        this.settingStage = settingStage;
    }
    
}
