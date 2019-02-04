package com.ibh.pocketpassword.viewmodel;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import com.ibh.pocketpassword.validation.ValidationException;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class AuthLimitedVM extends BaseViewModel<AuthLimitedVM> {

	private final Long id;
	private final StringProperty title;
	private final StringProperty category;
	private final StringProperty webUrl;
	private final StringProperty description;
	private final LocalDate validFrom;
	private final String color;

	public AuthLimitedVM(long id, String title, String webUrl, String description, LocalDate validFrom, String category, String color) {
		super();

		this.id = id;
		this.title = new SimpleStringProperty(this, "title", title);
		this.category = new SimpleStringProperty(this, "category", category);
		this.webUrl = new SimpleStringProperty(this, "webUrl", webUrl);
		this.description = new SimpleStringProperty(this, "description", description);
		this.validFrom = validFrom;
		this.color = color;
	}

	@Override
	public void validateModel() throws ValidationException {
		super.validate();
	}

	public Long getId() {
		return id;
	}

	public StringProperty getTitle() {
		return title;
	}

	public StringProperty getCategory() {
		return category;
	}

	public StringProperty getWebUrl() {
		return webUrl;
	}

	public StringProperty getDescription() {
		return description;
	}

	public LocalDate getValidFrom() {
		return validFrom;
	}

	public String getCategColor() {
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
		return "#" + color.substring(2);
	}
}
