package com.ibh.pocketpassword.gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ibh.pocketpassword.service.AuthenticationService;
import com.ibh.pocketpassword.viewmodel.AuthLimitedVM;
import com.ibh.pocketpassword.viewmodel.AuthUserPwdVM;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.util.Duration;

@Component
public class AuthDetailsViewControllerImpl implements Initializable, AuthDetailsViewController {

	private static final Logger LOG = LoggerFactory.getLogger(AuthDetailsViewControllerImpl.class);
	
	@Autowired
	private AuthenticationService service;
	
	private AuthLimitedVM viewModel;
	private CRUDEnum mode;
	
	private final String ShowAuthPaneTitle = "Show Authentication";
	private final Integer TIMERVALUE = 10;
	private Integer timerCountDown = TIMERVALUE;
	private Timeline timeline;
	
	@FXML
	private Hyperlink hlTitle;
	@FXML
	private Label lblCategName;
	
	@FXML
	private TextField txtTitle;
	@FXML
	private TextField txtCategory;
	@FXML
	private Hyperlink txtWebAddress;
	@FXML
	private DatePicker dpValidFrom;
	@FXML
	private TextArea txaDescription;
	@FXML
	private TitledPane tPaneShowAuth;
	@FXML
	private Button cmdEdit;
	@FXML
	private Button cmdDelete;

	@FXML
	private TextField txtShowUserName;

	@FXML
	private TextField txtShowPassword;

	@FXML
	public void handleEdit() {
//		MessageService.send(UIContentMessage.class, new UIContentMessage(ViewEnum.AuthCRUDView, vm.getId().get(), CRUDEnum.Update));
	}

	@FXML
	public void handleCopyUserName() {

	}

	@FXML
	public void handleCopyPassword() {

	}

	@FXML
	public void handleDelete() {

//		getBl().getAuthRepos().delete(vm.getId().get());
//
//		MessageService.send(RefreshDataMessage.class, new RefreshDataMessage(ViewEnum.AuthListView));

	}

	@FXML
	public void handleWebAddressLink() {
		String command = String.format("start %s %s", "firefox", txtWebAddress.getText());
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
	public void handleTitleLink() {
		String command = String.format("start %s %s", "firefox", viewModel.getWebUrl().getValue());
		try {
			Runtime.getRuntime().exec(new String[] { "cmd", "/c", command });
			// this is linux
			// Runtime.getRuntime().exec(new String[] { "chromium-browser",
			// "http://example.com/" });
		} catch (IOException ex) {
			LOG.warn("cannot open browser", ex);
		}
	}
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		// TODO

//		CrudMessage msg = (CrudMessage) getMessage();
//		switch (msg.getCrud()) {
//		case View:
//			vm = fromEntityToVM(getBl().getAuthRepos().getById(msg.getId()));
//			break;
//		default:
//			break;
//		}
		
		tPaneShowAuth.setExpanded(false);
		tPaneShowAuth.expandedProperty().addListener((obs, wasExpanded, isExpanded)->{
			if(isExpanded) {
				AuthUserPwdVM upvm = service.getUserPwd(viewModel.getId().longValue());
				txtShowUserName.setText(upvm.getUserName());
				txtShowPassword.setText(upvm.getPassword());
				
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
//		setUpValidators();
	}

	private void closeShowAuthPane() {
        timeline.stop();
        tPaneShowAuth.setExpanded(false);
        tPaneShowAuth.setText(ShowAuthPaneTitle);

		txtShowUserName.setText(null);
		txtShowPassword.setText(null);

	}
	
	@Override
	public void refresh(CRUDEnum mode, Long id) {
		closeShowAuthPane();
		if (viewModel != null) {
			unbind();
		}
		
		this.mode = mode;
		viewModel = service.getVMById(id); 
		bind();
//		txtName.getStyleClass().removeIf(style -> style.equals("txtError"));
//		txtName.setEditable(false);
//		cpColor.setEditable(false);
//		cmdSave.setVisible(false);
//		cmdCancel.setVisible(false);
//		
//		if (mode != CRUDEnum.View) {
//			cmdSave.setVisible(true);
//			cmdCancel.setVisible(true);
//		}
//		if (mode == CRUDEnum.New || mode == CRUDEnum.Update) {
//			txtName.setEditable(true);
//			cpColor.setEditable(true);
//		}
//		if (mode == CRUDEnum.Delete) {
//			cmdSave.setText("Delete");
//		} else {
//			cmdSave.setText("Save");
//		}
	}

	private void bind() {
//		hlTitle.textProperty().bindBidirectional(viewModel.getTitle());
//		lblCategName.textProperty().bindBidirectional(viewModel.getCategory());
		
		txtTitle.textProperty().bindBidirectional(viewModel.getTitle());
		txtCategory.textProperty().bindBidirectional(viewModel.getCategory());
		txtWebAddress.textProperty().bindBidirectional(viewModel.getWebUrl());
		dpValidFrom.valueProperty().bindBidirectional(viewModel.getValidFrom());
		txaDescription.textProperty().bindBidirectional(viewModel.getDescription());
	}

	private void unbind() {
		txtTitle.textProperty().unbindBidirectional(viewModel.getTitle());
//		cpColor.valueProperty().unbindBidirectional(viewModel.colorProperty());
	}
	
//	@Override
//	public void setUpValidators() {
//		try {
//			setUpValidator(txtTitle, vm.getTitle());
//			setUpValidator(txtCategory, vm.getCategory());
//			setUpValidator(txtWebAddress, vm.getWebUrl());
//			setUpValidator(txtValidFrom, vm.getValidFrom());
//			setUpValidator(txaDescription, vm.getDescription());
//
//			setControlStateNormal();
//		} catch (Exception ex) {
//			LOG.error(ex.getMessage(), ex);
//		}
//	}

//	private AuthLimitedVM fromEntityToVM(Authentication c) {
//		return new AuthLimitedVM(c.getId(), c.getTitle(), c.getCategory().getName(), c.getWeburl(), c.getDescription(), c.getValidfrom(), c.getCategory().getColor());
//	}

}
