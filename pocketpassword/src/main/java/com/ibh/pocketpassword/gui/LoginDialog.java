package com.ibh.pocketpassword.gui;

import java.io.IOException;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.function.Consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class LoginDialog extends Stage {
	
	private static final Logger LOG = LoggerFactory.getLogger(MainViewControllerImpl.class);

	private LoginControllerImpl loginController;
	
	public LoginDialog(Stage owner, ResourceBundle bundle, Consumer<Map<String, String>> postLoginHandler) {
        super();
                
        initOwner(owner);
        setTitle("Login");
        Group root = new Group();
        Scene scene = new Scene(root, 450, 350, Color.WHITE);
        setScene(scene);

        Node node;
        try {
        	
          FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/LoginView.fxml"), bundle);
          node = loader.load();
          loginController = loader.<LoginControllerImpl>getController();
          loginController.setPostLoginHandler(postLoginHandler);
          
          root.getChildren().add(node);

        } catch (IOException | SecurityException | IllegalArgumentException ex) {
        	LOG.warn("Cannot load Login component", ex);
        }

    }
}
