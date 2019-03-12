package com.ibh.pocketpassword.gui;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.function.Consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import com.ibh.pocketpassword.helper.CryptHelper;
import com.ibh.pocketpassword.service.SettingService;
import com.ibh.pocketpassword.viewmodel.SettingVM;

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
public class MainViewControllerImpl implements Initializable, ApplicationListener<ContextRefreshedEvent> {

	private static final Logger LOG = LoggerFactory.getLogger(MainViewControllerImpl.class);

	private ApplicationContext appContext;
	
  @FXML
  private BorderPane borderPane;
  @FXML
  private ToolBar toolbar;

  private BooleanProperty isAuthenticated;
  private ResourceBundle bundle;

  @Override
  public void initialize(URL url, ResourceBundle bundle) {
	  

	  this.bundle = bundle;
	  
      isAuthenticated = new SimpleBooleanProperty(false);
      toolbar.disableProperty().bind(isAuthenticated.not());
    
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

  public void postLogin(ApplicationContext appctx) throws NoSuchAlgorithmException, UnsupportedEncodingException {
//    System.out.println("postLogin");
    
    this.appContext = appctx;
    isAuthenticated.setValue(true);

	SettingService ss = this.appContext.getBean(SettingService.class);
	ss.initDB();
	CryptHelper.init(ss.getDbCreateTimestamp());
//	ss.getDbCreateTimestamp();

    setContentCenter(ViewEnum.AuthListView);
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

	public void loginHidden() {
		if(isAuthenticated.not().get()) {
			Platform.exit();
		}
	}

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {

//		SettingService ss = event.getApplicationContext().getBean(SettingService.class);
//		ss.getDbCreateTimestamp();
		
		
	}

}
