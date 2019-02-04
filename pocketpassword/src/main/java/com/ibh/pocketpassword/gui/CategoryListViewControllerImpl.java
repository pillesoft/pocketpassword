/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibh.pocketpassword.gui;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ibh.pocketpassword.service.CategoryService;
import com.ibh.pocketpassword.viewmodel.CategoryVM;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

@Component
public class CategoryListViewControllerImpl  implements Initializable {

	@Autowired
	private CategoryService categService;
	@Autowired
	private CategoryDetailsViewController detailsController;
	
	@FXML
	private Label lblHeader;
	@FXML
	private TableView<CategoryVM> categoryTable;
	@FXML
	private TableColumn<CategoryVM, String> nameColumn;

	@FXML
	private VBox crudContainer;

	private ObservableList<CategoryVM> data;
	private FilteredList<CategoryVM> filteredData;
	private CategoryVM currentData = null;

	@Override
	public void initialize(URL url, ResourceBundle bundle) {

		nameColumn.setCellValueFactory(new PropertyValueFactory<CategoryVM, String>("name"));

		categoryTable.getSelectionModel().selectedItemProperty().addListener(((observable, oldvalue, newvalue) -> {
			currentData = newvalue;
			showDetails();
		}));

		categoryTable.setRowFactory(tv -> new TableRow<CategoryVM>() {
			@Override
			public void updateItem(CategoryVM item, boolean empty) {
				super.updateItem(item, empty);
				if (item == null) {
					setStyle("");
				} else {
					setStyle(String.format("-fx-background-color: %s;", item.getCSSColor()));
				}
			}
			
			
		});

		categoryTable.setOnMousePressed(new EventHandler<MouseEvent>() {
		    @Override 
		    public void handle(MouseEvent event) {
		        if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {
		        	CategoryVM selected = categoryTable.getSelectionModel().getSelectedItem();
		        	detailsController.refresh(CRUDEnum.Update, selected.getId());
		        }
		    }
		});
		
		reloadData();

	}

	public void reloadData() {
		List<CategoryVM> cvmlist = categService.getVMData();

		data = FXCollections.observableArrayList(cvmlist);

		filteredData = new FilteredList<>(data, p -> true);

		SortedList<CategoryVM> sortedData = new SortedList<>(filteredData);
		sortedData.comparatorProperty().bind(categoryTable.comparatorProperty());

		categoryTable.setItems(sortedData);
		if (!sortedData.isEmpty()) {
			currentData = sortedData.get(0);
		}

		showDetails();

	}

	private void showDetails() {
		if (currentData != null) {
			detailsController.refresh(CRUDEnum.View, currentData.getId());
		}
	}

	@FXML
	public void handleNew() {
		detailsController.refresh(CRUDEnum.New, 0L);
	}

	@FXML
	public void handleDelete() {
		detailsController.refresh(CRUDEnum.Delete, currentData.getId());
	}


}
