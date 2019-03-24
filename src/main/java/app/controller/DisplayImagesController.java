/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.controller;

import app.Application;
import app.conversion.InverseRadonTransform;
import app.conversion.RadonTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javax.imageio.ImageIO;

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
    private ImageView sinograph;

    @FXML
    private ImageView outputImage;

    @FXML
    private ProgressIndicator progressCircle1;

    @FXML
    private ProgressIndicator progressCircle2;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        progressCircle1.setVisible(false);
        progressCircle2.setVisible(false);
    }

    public void setMain(Application app) {
        try {
            this.app = app;
            inputImage.setImage(new Image(app.getInputImage().toURI().toURL().toExternalForm()));
            showSinograph();
        } catch (MalformedURLException ex) {
            Logger.getLogger(DisplayImagesController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void showSinograph() {

        final File file = this.app.getInputImage();

        progressCircle1.setVisible(true);
        new Thread(() -> {
            try {
                BufferedImage image = ImageIO.read(this.app.getInputImage());

                BufferedImage sin = new RadonTransform(this.app.getN(), this.app.getL(), this.app.getAlfa()).transform(image);
                File f = new File("sin.jpg");
                ImageIO.write(sin, "jpg", f);

                progressCircle1.setVisible(false);
                sinograph.setImage(new Image(f.toURI().toURL().toExternalForm()));

                progressCircle2.setVisible(true);
                List<BufferedImage> outList = new InverseRadonTransform(this.app.getN(), this.app.getL(), this.app.getAlfa()).transform(sin, 1);
                BufferedImage out = outList.get(outList.size()-1);

                f = new File("out.jpg");
                ImageIO.write(out, "jpg", f);

                outputImage.setImage(new Image(f.toURI().toURL().toExternalForm()));

                progressCircle2.setVisible(false);

            } catch (IOException ex) {
                Logger.getLogger(MainViewConroller.class.getName()).log(Level.SEVERE, null, ex);
            }
        }).start();

    }

}
