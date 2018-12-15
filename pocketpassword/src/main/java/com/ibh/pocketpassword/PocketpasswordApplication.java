package com.ibh.pocketpassword;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

@SpringBootApplication
public class PocketpasswordApplication extends Application {

    private ConfigurableApplicationContext context;
    private Parent rootNode;
    
    //https://wimdeblauwe.wordpress.com/2017/09/18/using-spring-boot-with-javafx/	
	@Override
	public void init() throws Exception {
        SpringApplicationBuilder builder = new SpringApplicationBuilder(PocketpasswordApplication.class);
        context = builder.run(getParameters().getRaw().toArray(new String[0]));
 
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Main.fxml"));
        loader.setControllerFactory(context::getBean);
        rootNode = loader.load();
        }


	@Override
	public void stop() throws Exception {
		context.close();
	}


	@Override
	public void start(Stage stage) throws Exception {
//        Rectangle2D visualBounds = Screen.getPrimary().getVisualBounds();
//        double width = visualBounds.getWidth()-100;
//        double height = visualBounds.getHeight()-100;
 
        Scene scene = new Scene(rootNode, 1000, 800);
        scene.getStylesheets().add(this.getClass().getResource("/style/Application.css").toString());
        
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
		
	}
	
	
}
