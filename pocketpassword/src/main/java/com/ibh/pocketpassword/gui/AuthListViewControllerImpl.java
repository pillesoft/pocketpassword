package com.ibh.pocketpassword.gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.ibh.pocketpassword.service.AuthLimitedService;
import com.ibh.pocketpassword.viewmodel.AuthLimitedVM;
import javafx.event.ActionEvent;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

@Component
public class AuthListViewControllerImpl implements Initializable {

	private static final Logger LOG = LoggerFactory.getLogger(AuthListViewControllerImpl.class);

	@Autowired
	private AuthLimitedService service;
	@Autowired
	private AuthDetailsViewController detailsController;
	@Autowired
	private AuthCrudController crudController;
	@Autowired
	private ApplicationContext appContext;

	@FXML
	private TableView<AuthLimitedVM> authTable;
	@FXML
	private TableColumn<AuthLimitedVM, String> categoryColumn;
	@FXML
	private TableColumn<AuthLimitedVM, String> titleColumn;
	@FXML
	private TableColumn<AuthLimitedVM, Integer> howOldColumn;
	@FXML
	private TableColumn<AuthLimitedVM, Void> actionColumn;

	@FXML
	private TextField categoryFilter;
	@FXML
	private TextField titleFilter;

	@FXML
	private VBox crudContainer;

	private ResourceBundle bundle;
	private ObservableList<AuthLimitedVM> data;
	private FilteredList<AuthLimitedVM> filteredData;
	private AuthLimitedVM currentData = null;

	@Override
	public void initialize(URL url, ResourceBundle bundle) {

		this.bundle = bundle;

		categoryColumn.setCellValueFactory(cellData -> cellData.getValue().getCategory());
		titleColumn.setCellValueFactory(cellData -> cellData.getValue().getTitle());
		howOldColumn.setCellValueFactory(cellData -> cellData.getValue().getNumberOfDays().asObject());
		actionColumn.setCellFactory(new Callback<TableColumn<AuthLimitedVM, Void>, TableCell<AuthLimitedVM, Void>>() {

			@Override
			public TableCell<AuthLimitedVM, Void> call(TableColumn<AuthLimitedVM, Void> param) {
				final TableCell<AuthLimitedVM, Void> cell = new TableCell<AuthLimitedVM, Void>() {

					private final Button btnEdit = new Button("Edit");
					private final Button btnDelete = new Button("Delete");
					{
						btnEdit.setOnAction((ActionEvent event) -> {
							AuthLimitedVM data = getTableView().getItems().get(getIndex());
							System.out.println("Edit - selectedData: " + data.getId());
							setContentCenter(ViewEnum.AuthCrudView);
							updateContainer(CRUDEnum.Update, data.getId());
						});

						btnDelete.setOnAction((ActionEvent event) -> {
							AuthLimitedVM data = getTableView().getItems().get(getIndex());
							System.out.println("Delete - selectedData: " + data.getId());
							setContentCenter(ViewEnum.AuthCrudView);
							updateContainer(CRUDEnum.Delete, data.getId());
						});
					}

					private final HBox actionContainer = new HBox(2D, btnEdit, btnDelete);

					@Override
					public void updateItem(Void item, boolean empty) {
						super.updateItem(item, empty);
						if (empty) {
							setGraphic(null);
						} else {
							setGraphic(actionContainer);
						}
					}
				};
				return cell;
			}
		});

		authTable.getSelectionModel().selectedItemProperty().addListener(((observable, oldvalue, newvalue) -> {
			currentData = newvalue;
			updateContainer();
		}));

		categoryFilter.textProperty().addListener((observable, oldValue, newValue) -> {
			doFilter();
		});

		titleFilter.textProperty().addListener((obs, oldv, newv) -> {
			doFilter();
		});

		authTable.setRowFactory(tv -> new TableRow<AuthLimitedVM>() {
			@Override
			public void updateItem(AuthLimitedVM item, boolean empty) {
				super.updateItem(item, empty);
				if (item == null) {
					setStyle("");
				} else {
					setStyle(String.format("-fx-background-color: %s;", item.getCSSColor()));
				}
			}
		});

		data = FXCollections.observableList(service.getVMData());
		filteredData = new FilteredList<>(data, a->true);
	
		if (!data.isEmpty()) {
			setContentCenter(ViewEnum.AuthDetailsView);
			reloadData();
		}
	}

	public void postCrud() {
		data = FXCollections.observableList(service.getVMData());
		filteredData = new FilteredList<>(data, a->true);
		if (data.isEmpty()) {
			currentData = null;
			crudContainer.getChildren().clear();
		} else {
			setContentCenter(ViewEnum.AuthDetailsView);
		}
		reloadData();
	}
	
	private void setContentCenter(ViewEnum view) {
		crudContainer.getChildren().clear();

		Node node;
		try {

			FXMLLoader loader = new FXMLLoader(
					getClass().getResource(String.format("/fxml/%s.fxml", view.getViewFile())), this.bundle);
			loader.setControllerFactory(appContext::getBean);
			node = loader.load();

			crudContainer.getChildren().add(node);

		} catch (IOException | SecurityException | IllegalArgumentException ex) {
			LOG.warn("exception", ex);
		}
	}

	private void reloadData() {

		doFilter();

		SortedList<AuthLimitedVM> sortedData = new SortedList<>(filteredData);
		sortedData.comparatorProperty().bind(authTable.comparatorProperty());

		authTable.setItems(sortedData);
		if (!sortedData.isEmpty()) {
			currentData = sortedData.get(0);
		}

		updateContainer();

	}

	private void updateContainer() {

		if (currentData != null) {
			detailsController.refresh(CRUDEnum.View, currentData.getId());
		}
	}

	private void updateContainer(CRUDEnum crud, Long id) {
		crudController.refresh(crud, id);
	}

	@FXML
	public void handleNew() {
		setContentCenter(ViewEnum.AuthCrudView);
		updateContainer(CRUDEnum.New, 0L);
	}

	@FXML
	public void handleClearFilter() {
		categoryFilter.clear();
		titleFilter.clear();
		filteredData.setPredicate(a -> true);
	}

	private void doFilter() {
		String cfilter = categoryFilter.getText();
		String tfilter = titleFilter.getText();

		filteredData.setPredicate(a -> {

			String lowercFilter = cfilter.toLowerCase();
			String lowertFilter = tfilter.toLowerCase();
			// both are empty
			if (StringUtils.isEmpty(cfilter) && StringUtils.isEmpty(tfilter)) {
				return true;
			// none of empty
			} else if (((cfilter != null && !cfilter.isEmpty())
					&& a.getCategory().getValue().toLowerCase().contains(lowercFilter))
					&& ((tfilter != null && !tfilter.isEmpty())
							&& a.getTitle().getValue().toLowerCase().contains(lowertFilter))) {
				return true;
			// title is empty
			} else if (((cfilter != null && !cfilter.isEmpty())
					&& a.getCategory().getValue().toLowerCase().contains(lowercFilter))
					&& (tfilter == null || tfilter.isEmpty())) {
				return true;
			// category is empty
			} else if ((cfilter == null || cfilter.isEmpty()) && ((tfilter != null && !tfilter.isEmpty())
					&& a.getTitle().getValue().toLowerCase().contains(lowertFilter))) {
				return true;
			}
			return false;
		});

	}

}
