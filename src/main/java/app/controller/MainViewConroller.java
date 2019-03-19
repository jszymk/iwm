/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.controller;

import app.Application;
import app.conversion.RadonTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javax.imageio.ImageIO;

/**
 * FXML Controller class
 *
 * @author Weronika
 */
public class MainViewConroller implements Initializable {

    private Application app;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    public void setMain(Application app) {
        this.app = app;
    }

    public void loadImage() {
        FileChooser fileChooser = new FileChooser();

        //Set extension filter
        FileChooser.ExtensionFilter extFilterJPG = new FileChooser.ExtensionFilter("JPG files (*.jpg)", "*.JPG");
        FileChooser.ExtensionFilter extFilterPNG = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.PNG");
        fileChooser.getExtensionFilters().addAll(extFilterJPG, extFilterPNG);

        //Show open file dialog
        File file = fileChooser.showOpenDialog(null);

        if (file != null) {
            this.app.setInputImage(file);
            showImages();
        }

    }

    public void showImages() {
        try {

            FXMLLoader loader = new FXMLLoader();

            URL url = Paths.get("src/main/java/app/view/DisplayImages.fxml").toUri().toURL();
            loader.setLocation(url);
            AnchorPane mainPage = loader.load();

            this.app.getRootLayout().setCenter(mainPage);

            DisplayImagesController displayImagesController = loader.getController();
            displayImagesController.setMain(this.app);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void displaySettings() {
        try {
            FXMLLoader loader = new FXMLLoader();
            URL url = Paths.get("src/main/java/app/view/Settings.fxml").toUri().toURL();
            loader.setLocation(url);

            AnchorPane settings = loader.load();

            Stage settingsStage = new Stage();
            settingsStage.setTitle("Settings");
            settingsStage.initModality(Modality.WINDOW_MODAL);
            settingsStage.initOwner(app.getPrimaryStage());

            Scene scene = new Scene(settings);
            settingsStage.setScene(scene);

            SettingsController controller = loader.getController();

            controller.setSettingStage(settingsStage);

            settingsStage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
