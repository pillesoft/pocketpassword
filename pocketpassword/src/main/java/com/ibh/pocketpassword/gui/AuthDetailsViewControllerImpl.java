package com.ibh.pocketpassword.gui;

import java.io.IOException;
import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ibh.pocketpassword.helper.CryptHelper;
import com.ibh.pocketpassword.service.AuthLimitedService;
import com.ibh.pocketpassword.service.AuthUserPwdService;
import com.ibh.pocketpassword.viewmodel.AuthLimitedVM;
import com.ibh.pocketpassword.viewmodel.AuthUserPwdVM;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.util.Duration;

@Component
public class AuthDetailsViewControllerImpl implements Initializable, AuthDetailsViewController {

	private static final Logger LOG = LoggerFactory.getLogger(AuthDetailsViewControllerImpl.class);
	
	@Autowired
	private AuthLimitedService service;
	@Autowired
	private AuthUserPwdService userPwdService;

	private Clipboard clpbrd; 
	
	private AuthLimitedVM viewModel;
	
	private final String ShowAuthPaneTitle = "Show Authentication";
	private final Integer TIMERVALUE = 10;
	private Integer timerCountDown = TIMERVALUE;
	private Timeline timeline;
	
	@FXML
	private Hyperlink hlTitle;
	@FXML
	private Label lblCategName;
	@FXML
	private Label lblDaysOld;
	@FXML
	private Label lblCreatedOn;
	
	@FXML
	private TitledPane tPaneShowAuth;

	@FXML
	private ToggleButton tbtnShowHide;
	@FXML
	private PasswordField txtMaskedUserName;
	@FXML
	private PasswordField txtMaskedPassword;
	@FXML
	private TextField txtShowUserName;
	@FXML
	private TextField txtShowPassword;

	@FXML
	public void handleCopyUserName() {
		ClipboardContent content = new ClipboardContent();
		content.putString(txtShowUserName.getText());
		clpbrd.setContent(content);
		
	}

	@FXML
	public void handleCopyPassword() {
		ClipboardContent content = new ClipboardContent();
		content.putString(txtShowPassword.getText());
		clpbrd.setContent(content);
	}

	@FXML
	public void handleTitleLink() {
		String command = String.format("start %s %s", "firefox", viewModel.getWebUrl());
		try {
			Runtime.getRuntime().exec(new String[] { "cmd", "/c", command });
			// this is linux
			// Runtime.getRuntime().exec(new String[] { "chromium-browser",
			// "http://example.com/" });
		} catch (IOException ex) {
			LOG.warn("cannot open browser", ex);
		}
	}
	
	@FXML
	public void handleShowHide() {
		String title = tbtnShowHide.selectedProperty().get() ? "Hide UserName/Password" : "Show UserName/Password";  
		tbtnShowHide.setText(title);
	}
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		
		clpbrd = Clipboard.getSystemClipboard();
		
		txtShowUserName.managedProperty().bind(tbtnShowHide.selectedProperty());
		txtShowUserName.visibleProperty().bind(tbtnShowHide.selectedProperty());
		txtMaskedUserName.managedProperty().bind(tbtnShowHide.selectedProperty().not());
		txtMaskedUserName.visibleProperty().bind(tbtnShowHide.selectedProperty().not());

		txtShowPassword.managedProperty().bind(tbtnShowHide.selectedProperty());
		txtShowPassword.visibleProperty().bind(tbtnShowHide.selectedProperty());
		txtMaskedPassword.managedProperty().bind(tbtnShowHide.selectedProperty().not());
		txtMaskedPassword.visibleProperty().bind(tbtnShowHide.selectedProperty().not());

		txtShowUserName.textProperty().bindBidirectional(txtMaskedUserName.textProperty());
		txtShowPassword.textProperty().bindBidirectional(txtMaskedPassword.textProperty());
		
		tPaneShowAuth.setExpanded(false);
		tPaneShowAuth.expandedProperty().addListener((obs, wasExpanded, isExpanded)->{
			if(isExpanded) {
				AuthUserPwdVM upvm = userPwdService.getVMById(viewModel.getId().longValue());
				txtShowUserName.setText(CryptHelper.decrypt(upvm.getUserName()));
				txtShowPassword.setText(CryptHelper.decrypt(upvm.getPassword()));
				
				// add the timer
				timerCountDown = TIMERVALUE;
				timeline = new Timeline();
		        timeline.setCycleCount(Timeline.INDEFINITE);
		        timeline.getKeyFrames().add(
		                new KeyFrame(Duration.seconds(1),
		                  new EventHandler<ActionEvent>() {
		                    // KeyFrame event handler
		                    public void handle(ActionEvent event) {
		                        timerCountDown--;
		                        // update header
		                        tPaneShowAuth.setText(String.format("%s - %s", ShowAuthPaneTitle, timerCountDown));
		                        if (timerCountDown <= 0) {
		                        	closeShowAuthPane();
		                        }
		                      }
		                }));
		        timeline.playFromStart();
			} else {
				// this is when the pane is closed by the user
				if(timeline != null) {
					closeShowAuthPane();
				}
			}
		});
	}

	private void closeShowAuthPane() {
		timeline.stop();
		tPaneShowAuth.setExpanded(false);
        tPaneShowAuth.setText(ShowAuthPaneTitle);

        clpbrd.clear();
        tbtnShowHide.setSelected(false);
		txtShowUserName.setText(null);
		txtShowPassword.setText(null);

	}
	
	@Override
	public void refresh(CRUDEnum mode, Long id) {
		if(timeline != null) {
			closeShowAuthPane();
		}
		viewModel = service.getVMById(id); 
		bind();	
		
	}

	private void bind() {
		hlTitle.setText(viewModel.getTitle().get());
		lblCategName.setText(viewModel.getCategory().get());
		lblDaysOld.setText(String.valueOf(viewModel.getNumberOfDays().intValue()));
		lblCreatedOn.setText(viewModel.getValidFrom().format(DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG)));
	}


}
