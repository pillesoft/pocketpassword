package com.ibh.pocketpassword.viewmodel;

import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;

import com.ibh.pocketpassword.validation.ValidationException;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.LongProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class AuthLimitedVM extends BaseViewModel<AuthLimitedVM> {

	private LongProperty id;
	private StringProperty title;
	private StringProperty category;
	private StringProperty webUrl;
	private StringProperty description;
	private LocalDate validFrom;
	private StringProperty color;

	public AuthLimitedVM() {
		this.id = new SimpleLongProperty(null, "id", 0);
		title = new SimpleStringProperty(null, "title", "");
		category = new SimpleStringProperty(null, "category");
		webUrl = new SimpleStringProperty(null, "webUrl", "");
		description = new SimpleStringProperty(null, "description", "");
		validFrom = null;
	}

	public AuthLimitedVM(long id, String title, String category, String webUrl, String description, LocalDate validFrom,
			String color) {
		super();

		this.id = new SimpleLongProperty(null, "id", id);
		this.title = new SimpleStringProperty(null, "title", title);
		this.category = new SimpleStringProperty(null, "category", category);
		this.webUrl = new SimpleStringProperty(null, "webUrl", webUrl);
		this.description = new SimpleStringProperty(null, "description", description);
		this.validFrom = validFrom;
		this.color = new SimpleStringProperty(null, "color", color);
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

	public StringProperty getCategory() {
		return category;
	}

	public void setCategory(StringProperty category) {
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

	public LocalDate getValidFrom() {
		return validFrom;
	}

	public void setValidFrom(LocalDate validFrom) {
		this.validFrom = validFrom;
	}

	public StringProperty getCategColor() {
		return color;
	}

	public IntegerProperty getNumberOfDays() {
		if (validFrom == null) {
			return new SimpleIntegerProperty(0);
		} else {			
			long days = ChronoUnit.DAYS.between(validFrom, LocalDate.now());
			return new SimpleIntegerProperty((int) days);
		}
	}

	public String getCSSColor() {
		return "#" + color.get().substring(2);
	}
}
