package com.ibh.pocketpassword.gui;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ibh.pocketpassword.model.Category;
import com.ibh.pocketpassword.service.AuthenticationService;
import com.ibh.pocketpassword.service.CategoryService;
import com.ibh.pocketpassword.validation.ValidationError;
import com.ibh.pocketpassword.validation.ValidationException;
import com.ibh.pocketpassword.viewmodel.AuthenticationVM;

import javafx.beans.property.ObjectProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.util.Callback;
import javafx.util.StringConverter;

@Component
public class AuthCrudControllerImpl extends BaseController implements Initializable, AuthCrudController {

	private static final Logger LOG = LoggerFactory.getLogger(AuthCrudControllerImpl.class);
	
	@Autowired
	private AuthenticationService service;
	@Autowired
	private CategoryService categService;
	@Autowired(required=false)
	private AuthListViewControllerImpl listViewController;
		
	private AuthenticationVM viewModel;
	private CRUDEnum mode;
	
	private List<Category> categList;
	
	@FXML
	private TextField txtTitle;
	@FXML
	private ComboBox<Category> cmbCategory;
	@FXML
	private TextField txtWebAddress;
	@FXML
	private DatePicker dpValidFrom;
	@FXML
	private TextArea txaDescription;
	@FXML
	private TextField txtUserName;
	@FXML
	private TextField txtPassword;

	@FXML
	private Button cmdSave;
	@FXML
	private Button cmdCancel;
	
	@Override
	public void refresh(CRUDEnum mode, Long id) {
		
		this.mode = mode;
		if (this.mode == CRUDEnum.New) {
			viewModel = new AuthenticationVM();
		} else {
			viewModel = service.getVMById(id);
		}

		bindBidirectional(viewModel.getTitle(), txtTitle.textProperty());
		bindBidirectional(viewModel.getCategory(), cmbCategory.valueProperty());
		bindBidirectional(viewModel.getUserName(), txtUserName.textProperty());
		bindBidirectional(viewModel.getPassword(), txtPassword.textProperty());
		bindBidirectional(viewModel.getWebUrl(), txtWebAddress.textProperty());
		bindBidirectional(viewModel.getValidFrom(), dpValidFrom.valueProperty());
		bindBidirectional(viewModel.getDescription(), txaDescription.textProperty());

		if(this.mode == CRUDEnum.Delete) {
			cmdSave.setText("Delete");
			
			txtTitle.setEditable(false);
			cmbCategory.setEditable(false);
			txtUserName.setEditable(false);
			txtPassword.setEditable(false);
			txtWebAddress.setEditable(false);
			dpValidFrom.setEditable(false);
			txaDescription.setEditable(false);
		}

	}



	@Override
	public void initialize(URL location, ResourceBundle resources) {
		setControlStateNormal();
		
		categList = categService.getData();
		cmbCategory.setItems(FXCollections.observableList(categList));
		cmbCategory.setCellFactory(new Callback<ListView<Category>,ListCell<Category>>(){
            @Override
            public ListCell<Category> call(ListView<Category> l){
                return new ListCell<Category>(){
                    @Override
                    protected void updateItem(Category item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item == null || empty) {
                            setGraphic(null);
                        } else {
                            setText(item.getName());
                        }
                    }
                } ;
            }
        });
		cmbCategory.setConverter(new StringConverter<Category>() {
            @Override
            public String toString(Category item) {
              if (item == null){
                return null;
              } else {
                return item.getName();
              }
            }

          @Override
          public Category fromString(String name) {
              return categList.stream().filter(p->p.getName().equals(name)).findFirst().get();
          }
      });
	}
	
	@FXML
	public void handleSave() {
		setControlStateNormal();
		try {
			viewModel.validateModel();
			
			if(this.mode == CRUDEnum.Delete) {
				service.delete(viewModel);
			} else {		
				service.save(viewModel);
			}
			listViewController.postCrud();
		} catch(ValidationException e) {
			setControlStateError(e);
		}
	}

	@FXML
	public void handleCancel() {
		listViewController.postCrud();
	}

}
