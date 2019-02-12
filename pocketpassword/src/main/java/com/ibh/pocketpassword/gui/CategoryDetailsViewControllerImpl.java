package com.ibh.pocketpassword.gui;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ibh.pocketpassword.service.CategoryService;
import com.ibh.pocketpassword.validation.ValidationException;
import com.ibh.pocketpassword.viewmodel.CategoryVM;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;

@Component
public class CategoryDetailsViewControllerImpl implements CategoryDetailsViewController {

	@Autowired
	private CategoryService categService;
	@Autowired(required=false)
	private CategoryListViewControllerImpl clvController;
	
	private CategoryVM viewModel;
	private CRUDEnum mode;
	
	@FXML
	private Button cmdSave;
	@FXML
	private Button cmdCancel;
	@FXML
	private TextField txtName;
	@FXML
	private ColorPicker cpColor;

	@FXML
	public void handleSave() {
		try {
		if (mode == CRUDEnum.Delete) {
			categService.delete(viewModel);
		} else {
			categService.save(viewModel);
		}
		clvController.reloadData();
		} catch (ValidationException ve) {
			
//			ve.getValidationError().forEach(a->{
//				a.getBoundedControl().getStyleClass().add("txtError");
//				Tooltip msg = new Tooltip(String.join(System.lineSeparator(), a.getErrorMessages()));
//				msg.getStyleClass().add("tooltip-error");							
//				a.getBoundedControl().setTooltip(msg);
//			});
//			
//			
//			ve.getValidationError().entrySet().forEach(m -> {
//				if(m.getKey().equals("name")) {
//					txtName.getStyleClass().add("txtError");
//					Tooltip msg = new Tooltip(String.join(System.lineSeparator(), m.getValue()));
//					msg.getStyleClass().add("tooltip-error");							
//					txtName.setTooltip(msg);
//				}
//			});
		}
	}
	
	@FXML
	public void handleCancel() {
		clvController.reloadData();
	}
	
	@FXML
	public void initialize() {

	}
	
	@Override
	public void refresh(CRUDEnum mode, Long id) {
		if (viewModel != null) {
			unbind();
		}
		
		this.mode = mode;
		if (this.mode == CRUDEnum.New) {
			viewModel = new CategoryVM();
		} else {
			viewModel = categService.getVMById(id);
		}
		bind();
		txtName.getStyleClass().removeIf(style -> style.equals("txtError"));
		txtName.setEditable(false);
		cpColor.setEditable(false);
		cmdSave.setVisible(false);
		cmdCancel.setVisible(false);
		
		if (mode != CRUDEnum.View) {
			cmdSave.setVisible(true);
			cmdCancel.setVisible(true);
		}
		if (mode == CRUDEnum.New || mode == CRUDEnum.Update) {
			txtName.setEditable(true);
			cpColor.setEditable(true);
		}
		if (mode == CRUDEnum.Delete) {
			cmdSave.setText("Delete");
		} else {
			cmdSave.setText("Save");
		}
	}

	private void bind() {
		txtName.textProperty().bindBidirectional(viewModel.nameProperty());
		cpColor.valueProperty().bindBidirectional(viewModel.colorProperty());
	}

	private void unbind() {
		txtName.textProperty().unbindBidirectional(viewModel.nameProperty());
		cpColor.valueProperty().unbindBidirectional(viewModel.colorProperty());
	}
	
	
}
