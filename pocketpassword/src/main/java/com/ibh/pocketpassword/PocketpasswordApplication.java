package com.ibh.pocketpassword;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

import com.ibh.pocketpassword.gui.LoginControllerImpl;
import com.ibh.pocketpassword.gui.LoginDialog;
import com.ibh.pocketpassword.gui.MainViewControllerImpl;
import com.ibh.pocketpassword.viewmodel.LoginVM;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;

@SpringBootApplication
public class PocketpasswordApplication extends Application {

    private ConfigurableApplicationContext context;
    private Parent rootNode;
  
    AnnotationConfigApplicationContext annotCtx;
    LoginControllerImpl loginController;
    MainViewControllerImpl mainController;
    
    //https://wimdeblauwe.wordpress.com/2017/09/18/using-spring-boot-with-javafx/	
	@Override
	public void init() throws Exception {

		ResourceBundle bundle = ResourceBundle.getBundle("bundles.UIBundle", new Locale("hu"));
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Main.fxml"), bundle);
        //loader.setControllerFactory(context::getBean);
        rootNode = loader.load();
        mainController = loader.<MainViewControllerImpl>getController();
//        mainController.setPostLoginHandler((c)->postLogin(c));

//        System.setProperty("user.db.url", "jdbc:h2:tcp://localhost/c:/temp/db/test3");
//        System.setProperty("user.db.username", "ivan");
//        System.setProperty("user.db.password", "a");
//        
//        if (canConnect()) {
//	        SpringApplicationBuilder builder = new SpringApplicationBuilder(PocketpasswordApplication.class);
//	        context = builder.run(getParameters().getRaw().toArray(new String[0]));
//        }
//        
//
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
		        
        Stage loginDialog = new LoginDialog(primaryStage, null, (c)->postLogin(c));
        loginDialog.initModality(Modality.APPLICATION_MODAL);
        loginDialog.sizeToScene();
        loginDialog.setOnHidden(e -> mainController.loginHidden());
        loginDialog.showAndWait();
		
	}
	
	private void postLogin(Map<String, String> creds) {
		System.out.println(creds.get("dbname"));
		System.out.println(creds.get("username"));
		System.out.println(creds.get("password"));
		
//        SpringApplicationBuilder builder = new SpringApplicationBuilder(PocketpasswordApplication.class);
//        context = builder.run(getParameters().getRaw().toArray(new String[0]));

		mainController.postLogin();
	}
		
}
