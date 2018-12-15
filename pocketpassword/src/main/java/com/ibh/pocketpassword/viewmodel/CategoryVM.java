package com.ibh.pocketpassword.viewmodel;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.ibh.pocketpassword.model.Category;

import javafx.beans.property.LongProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.paint.Color;

public class CategoryVM extends BaseViewModel<CategoryVM> {

	private LongProperty id;
	@NotEmpty(message = "Category Name is obligatory")
	@Size(min = 3, message = "Too short. Minimum length is 3 characters")
	private StringProperty name;
	private ObjectProperty<Color> color;

	public CategoryVM() {
		this.id = new SimpleLongProperty(null, "id", 0);
		this.name = new SimpleStringProperty(null, "name", "");
		this.color = new SimpleObjectProperty<Color>(null, "color", Color.WHITE);
	}

	public CategoryVM(Long id, String name, String color) {
		this.id = new SimpleLongProperty(null, "id", id);
		this.name = new SimpleStringProperty(null, "name", name);
		this.color = new SimpleObjectProperty<Color>(null, "color", Color.valueOf(color));
	}

	public final Long getId() {
		return id.get();
	}

	public void setId(Long id) {
		this.id.set(id);
	}

	public final String getName() {
		return name.get();
	}

	public void setName(String name) {
		this.name.set(name);
	}

	public final Color getColor() {
		return color.get();
	}

	public void setColor(Color color) {
		this.color.set(color);
	}

	public String getCSSColor() {
		return "#" + color.get().toString().substring(2);
	}

	@Override
	public void validateModel() throws ValidationException {
		super.validate();
	}

	public static CategoryVM fromModel(Category model) {
		return new CategoryVM(model.getId(), model.getName(), model.getColor());
	}

	public LongProperty idProperty() {
		return id;
	}

	public StringProperty nameProperty() {
		return name;
	}

	public ObjectProperty<Color> colorProperty() {
		return color;
	}

}
