package com.ibh.pocketpassword.viewmodel;

import java.time.LocalDate;

import com.ibh.pocketpassword.model.Category;
import com.ibh.pocketpassword.validation.ValidationException;

import javafx.beans.property.LongProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class AuthenticationVM extends BaseViewModel<AuthenticationVM> {

	private LongProperty id;
	private StringProperty title;
	private ObjectProperty<Category> category;
	private StringProperty userName;
	private StringProperty password;
	private StringProperty webUrl;
	private StringProperty description;
	private ObjectProperty<LocalDate> validFrom;

	public AuthenticationVM() {
		this.id = new SimpleLongProperty(null, "id", 0);
		this.title = new SimpleStringProperty(null, "title", "");
		this.category = new SimpleObjectProperty<Category>(null, "category");
		this.webUrl = new SimpleStringProperty(null, "webUrl", "");
		this.description = new SimpleStringProperty(null, "description", "");
		this.validFrom = new SimpleObjectProperty<LocalDate>(null, "validFrom", LocalDate.now());
		this.userName = new SimpleStringProperty(null, "userName", "");
		this.password = new SimpleStringProperty(null, "password", "");
	}

	public AuthenticationVM(long id, String title, Category category, String userName, String password, String webUrl, String description, LocalDate validFrom) {
		super();

		this.id = new SimpleLongProperty(null, "id", id);
		this.title = new SimpleStringProperty(null, "title", title);
		this.category = new SimpleObjectProperty<Category>(null, "category", category);
		this.webUrl = new SimpleStringProperty(null, "webUrl", webUrl);
		this.description = new SimpleStringProperty(null, "description", description);
		this.validFrom = new SimpleObjectProperty<LocalDate>(null, "validFrom", validFrom);
		this.userName = new SimpleStringProperty(null, "userName", userName);
		this.password = new SimpleStringProperty(null, "password", password);
	}

	@Override
	public void validateModel() throws ValidationException {
		super.validate();
	}

	public LongProperty getId() {
		return id;
	}

	public void setId(LongProperty id) {
		this.id = id;
	}

	public StringProperty getTitle() {
		return title;
	}

	public void setTitle(StringProperty title) {
		this.title = title;
	}

	public ObjectProperty<Category> getCategory() {
		return category;
	}

	public void setCategory(ObjectProperty<Category> category) {
		this.category = category;
	}

	public StringProperty getWebUrl() {
		return webUrl;
	}

	public void setWebUrl(StringProperty webUrl) {
		this.webUrl = webUrl;
	}

	public StringProperty getDescription() {
		return description;
	}

	public void setDescription(StringProperty description) {
		this.description = description;
	}

	public StringProperty getUserName() {
		return userName;
	}

	public void setUserName(StringProperty userName) {
		this.userName = userName;
	}

	public StringProperty getPassword() {
		return password;
	}

	public void setPassword(StringProperty password) {
		this.password = password;
	}

	public ObjectProperty<LocalDate> getValidFrom() {
		return validFrom;
	}

	public void setValidFrom(ObjectProperty<LocalDate> validFrom) {
		this.validFrom = validFrom;
	}

}
