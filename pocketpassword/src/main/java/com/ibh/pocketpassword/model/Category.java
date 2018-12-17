package com.ibh.pocketpassword.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.ibh.pocketpassword.validation.ValidationException;

@Entity
@Table(name = "CATEGORIES_DICT")
public final class Category extends BaseValidatableModel<Category> {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private final Long id;

	@Column(unique = true, length = 100)
	@NotEmpty(message = "Category Name is obligatory")
	@Size(min = 3, message = "Too short. Minimum length is 3 characters")
	private final String name;
	@Column(length = 15)
	private final String color;

	private Category() {
		this(null, null, null);
	}
	
	private Category(Long id, String name, String hexcolor) {
		this.id = id;
		this.name = name;
		this.color = hexcolor;
	}

	public String getName() {
		return name;
	}

	public String getColor() {
		return color;
	}

	@Override
	public Long getId() {
		return id;
	}
	
	@Override
	public void validateModel() throws ValidationException {
		validate();		
	}
	
	public static class Builder {
		private Long id;

		private String name;
		private String color;

		public Category create() {
			return new Category(id, name, color);
		}

		public Builder id(Long id) {
			this.id = id;
			return this;
		}

		public Builder name(String name) {
			this.name = name;
			return this;
		}

		public Builder color(String color) {
			this.color = color;
			return this;
		}

	}


}
