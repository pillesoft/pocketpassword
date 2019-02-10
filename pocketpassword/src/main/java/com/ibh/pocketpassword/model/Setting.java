package com.ibh.pocketpassword.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import com.ibh.pocketpassword.model.Authentication.Builder;
import com.ibh.pocketpassword.validation.ValidationException;

@Entity
@Table(name = "SETTING")
public final class Setting extends BaseValidatableModel<Setting> {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private final Long id;
	@Column(name = "NAME", unique = true, length = 50)
	private final String name;
	@Column(name = "VALUE", length = 150)
	private final String value;

	protected Setting() {
		this(null, null, null);
	}

	private Setting(Long id, String name, String value) {
		this.id = id;
		this.name = name;
		this.value = value;
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getValue() {
		return value;
	}

	@Override
	public void validateModel() throws ValidationException {
		super.validate();
	}

	public static class Builder {
		private Long id;
		private String name;
		private String value;
		
		public Setting create() {
			return new Setting(id, name, value);
		}
		
		public Builder id(Long id) {
			this.id = id;
			return this;
		}

		public Builder name(String name) {
			this.name = name;
			return this;
		}

		public Builder value(String value) {
			this.value = value;
			return this;
		}

	}
}
