package com.ibh.pocketpassword;

import java.io.UnsupportedEncodingException;
import java.nio.file.Path;
import java.security.NoSuchAlgorithmException;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import com.ibh.pocketpassword.gui.LoginDialog;
import com.ibh.pocketpassword.gui.MainViewControllerImpl;
import com.ibh.pocketpassword.helper.CryptHelper;
import com.ibh.pocketpassword.helper.DbHelper;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

@SpringBootApplication
public class PocketpasswordApplication extends Application {

	private static final Logger LOG = LoggerFactory.getLogger(PocketpasswordApplication.class);

	private ConfigurableApplicationContext context;
	private Parent rootNode;
  private ResourceBundle bundle;
	MainViewControllerImpl mainController;

	// https://wimdeblauwe.wordpress.com/2017/09/18/using-spring-boot-with-javafx/
	@Override
	public void init() throws Exception {

		bundle = ResourceBundle.getBundle("bundles.UIBundle", new Locale("hu"));

		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Main.fxml"), bundle);
		rootNode = loader.load();
		mainController = loader.<MainViewControllerImpl>getController();
	}

	@Override
	public void stop() throws Exception {
		if (context != null) {
			context.close();
		}
	}

	@Override
	public void start(Stage primaryStage) throws Exception {

		Scene scene = new Scene(rootNode, 1000, 800);
		scene.getStylesheets().add(this.getClass().getResource("/style/Application.css").toString());

		primaryStage.setScene(scene);
		primaryStage.centerOnScreen();
		primaryStage.show();

		Stage loginDialog = new LoginDialog(primaryStage, bundle, (c) -> {
			try {
				postLogin(c);
			} catch (NoSuchAlgorithmException | UnsupportedEncodingException e1) {
				LOG.warn("exception", e1);
			}
		});
		loginDialog.initModality(Modality.APPLICATION_MODAL);
		loginDialog.sizeToScene();
		loginDialog.setOnHidden(e -> mainController.loginHidden());
		loginDialog.showAndWait();

	}

	private void postLogin(Map<String, String> creds) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		String filepwd = CryptHelper.hash(creds.get("password"));
		System.setProperty("user.db.url", DbHelper.getConnectionUrl(creds.get("dbname"), creds.get("newdb") == "true" ? false : true));
		System.setProperty("user.db.username", creds.get("username"));
		System.setProperty("user.db.password", String.format("%s %s", filepwd, creds.get("password")));

		LOG.debug(System.getProperty("user.db.url"));
		LOG.debug(System.getProperty("user.db.username"));
		LOG.debug(System.getProperty("user.db.password"));
		
		SpringApplicationBuilder builder = new SpringApplicationBuilder(PocketpasswordApplication.class);
		context = builder.run(getParameters().getRaw().toArray(new String[0]));		
		
		ApplicationContext appctx = (ApplicationContext)context;
		System.clearProperty("user.db.password");
		
		mainController.postLogin(appctx);
	}

	  public static void main(String[] args) {
		    launch(args);
		  }
}
