package com.ibh.pocketpassword.gui;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.BorderPane;

@Component
public class MainViewControllerImpl {

	@Autowired
	private ApplicationContext appContext;
	
  @FXML
  private BorderPane borderPane;
  @FXML
  private ToolBar toolbar;

  private BooleanProperty isToolbarDisabled;

  @FXML
  public void initialize() {

//    setBundle(rb);

    isToolbarDisabled = new SimpleBooleanProperty(false);
    toolbar.disableProperty().bind(isToolbarDisabled);

  }

  private void setContentCenter(ViewEnum view) {
    borderPane.centerProperty().setValue(null);

    Node node;
    try {
    	
      FXMLLoader loader = new FXMLLoader(getClass().getResource(String.format("/fxml/%s.fxml", view.getViewFile())));
      loader.setControllerFactory(appContext::getBean);
      node = loader.load();
      
      borderPane.centerProperty().setValue(node);

    } catch (IOException ex) {
      Logger.getLogger(MainViewControllerImpl.class.getName()).log(Level.SEVERE, null, ex);
    } catch (SecurityException ex) {
      Logger.getLogger(MainViewControllerImpl.class.getName()).log(Level.SEVERE, null, ex);
    } catch (IllegalArgumentException ex) {
      Logger.getLogger(MainViewControllerImpl.class.getName()).log(Level.SEVERE, null, ex);
    }
  }

//  private void postLogin() {
//    System.out.println("postLogin");
//    isToolbarDisabled.setValue(false);
//  }

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
