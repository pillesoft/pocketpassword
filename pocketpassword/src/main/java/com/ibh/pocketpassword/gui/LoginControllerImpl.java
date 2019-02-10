package com.ibh.pocketpassword.gui;

import com.ibh.pocketpassword.helper.DbHelper;
import com.ibh.pocketpassword.model.IBHDatabaseException;
import com.ibh.pocketpassword.validation.ValidationException;
import com.ibh.pocketpassword.viewmodel.LoginVM;
import java.net.URL;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.function.Consumer;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import org.apache.commons.dbcp2.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class LoginControllerImpl extends BaseController implements Initializable {

	private static final Logger LOG = LoggerFactory.getLogger(LoginControllerImpl.class);
	private LoginVM viewModel;

	private Consumer<Map<String, String>> postLoginHandler;

	@FXML
	private TextField txtDatabaseName;
	@FXML
	private TextField txtUserName;
	@FXML
	private PasswordField txtPassword;
	@FXML
	private Label lblErrorText;

	@FXML
	private void handleLogin() {

		String dbpath = "c:/temp/db";

		setControlStateNormal();
		lblErrorText.setText("");

		try {
			viewModel.validateModel();

			DbHelper.tryConnect(viewModel.getDatabaseName().get(), viewModel.getUserName().get(), viewModel.getPassword().get());
			
			Map<String, String> creds = new HashMap<>();
			creds.put("dbname", viewModel.getDatabaseName().get());
			creds.put("username", viewModel.getUserName().get());
			creds.put("password", viewModel.getPassword().get());
			this.postLoginHandler.accept(creds);

		} catch (IBHDatabaseException dexc) {
			lblErrorText.setText(dexc.getType().getDescription());
		} catch (ValidationException exc) {
			setControlStateError(exc);
		} catch (Exception exc) {
			lblErrorText.setText("Wrong User name / Password");
		}
	}

	@FXML
	private void handleCreateDB() {
//    setControlStateNormal();
		lblErrorText.setText("");

		LOG.debug("handleCreateDB");

//    try {
//      vm.validateModel();
//
//      getBl().createDB(txtUserName.getText(), txtPassword.getText().toCharArray());
//      getBl().login(txtUserName.getText(), txtPassword.getText().toCharArray());
//      MessageService.send(ActionMessage.class, new ActionMessage(ViewEnum.AuthListView));
//    } catch (ValidationException exc) {
//      setControlStateError(exc);
//    } catch (IBHDatabaseException dexc) {
//      lblErrorText.setText(dexc.getType().getDescription());
//    } catch (Exception exc) {
//      lblErrorText.setText("Wrong User name / Password");
//    }
	}

	/**
	 * Initializes the controller class.
	 */
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		viewModel = new LoginVM();

		txtDatabaseName.textProperty().bindBidirectional(viewModel.getDatabaseName());
		txtUserName.textProperty().bindBidirectional(viewModel.getUserName());
		txtPassword.textProperty().bindBidirectional(viewModel.getPassword());

//    setInstance(vm);

//    setUpValidators();
	}


	public LoginVM getViewModel() {
		return viewModel;
	}

	public void setPostLoginHandler(Consumer<Map<String, String>> consumer) {
		this.postLoginHandler = consumer;
	}

	@Override
	public void refresh(CRUDEnum mode, Long id) {
		// TODO Auto-generated method stub
		
	}

//  @Override
//  public void setUpValidators() {
//    try {
//      setUpValidator(txtUserName, vm.getUserName());
//      setUpValidator(txtPassword, vm.getPassword());
//
//      setControlStateNormal();
//    } catch (Exception ex) {
//      LOG.error(ex.getMessage(), ex);
//    }
//
//  }

}
