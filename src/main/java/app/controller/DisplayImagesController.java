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
import javafx.scene.image.ImageView;

/**
 * FXML Controller class
 *
 * @author Weronika
 */
public class DisplayImagesController implements Initializable {

    Application app;

    @FXML
    private ImageView inputImage;

    @FXML
    private ImageView sinogram;

    @FXML
    private ImageView outputImage;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    public void setMain(Application app) {
        this.app = app;
        inputImage.setImage(app.getInputImage());

    }

}
