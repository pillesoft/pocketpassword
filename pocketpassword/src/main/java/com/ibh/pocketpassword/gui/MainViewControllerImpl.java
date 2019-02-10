package com.ibh.pocketpassword.gui;

import java.io.IOException;
import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.function.Consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.BorderPane;

@Component
public class MainViewControllerImpl implements Initializable {

	private static final Logger LOG = LoggerFactory.getLogger(MainViewControllerImpl.class);

	@Autowired
	private ApplicationContext appContext;
	
  @FXML
  private BorderPane borderPane;
  @FXML
  private ToolBar toolbar;

  private BooleanProperty isToolbarDisabled;
  private ResourceBundle bundle;

  @Override
  public void initialize(URL url, ResourceBundle bundle) {
	  

	  this.bundle = bundle;
	  
      isToolbarDisabled = new SimpleBooleanProperty(true);
      toolbar.disableProperty().bind(isToolbarDisabled);
    
//      setContentCenter(ViewEnum.Login);

  }

  private void setContentCenter(ViewEnum view) {
    borderPane.centerProperty().setValue(null);

    Node node;
    try {
    	
      FXMLLoader loader = new FXMLLoader(getClass().getResource(String.format("/fxml/%s.fxml", view.getViewFile())), this.bundle);
      loader.setControllerFactory(appContext::getBean);
      node = loader.load();
      
      borderPane.centerProperty().setValue(node);

    } catch (IOException | SecurityException | IllegalArgumentException ex) {
    	LOG.warn("Cannot load view component", ex);
    }
  }

  public void postLogin() {
    System.out.println("postLogin");
    isToolbarDisabled.setValue(false);
  }

  @FXML
  public void handleTlbExit() {
    Platform.exit();
  }

  @FXML
  public void handleTlbAuth() {
    setContentCenter(ViewEnum.AuthListView);
  }

  @FXML
  public void handleTlbCateg() {
    setContentCenter(ViewEnum.CategoryListView);
  }

  @FXML
  public void handleTlbAbout() {

  }

}
