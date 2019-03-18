/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.controller;

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
    
    
    public void ok(){
        System.out.println("OK");
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
