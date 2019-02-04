package com.ibh.pocketpassword.model;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ibh.pocketpassword.validation.ValidationException;

@Entity
public final class Authentication extends BaseValidatableModel<Authentication> {

	private static final long serialVersionUID = 1L;

	private static final Logger LOG = LoggerFactory.getLogger(Authentication.class);

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private final Long id;

	@Column(nullable = false, unique = true)
    @NotNull
    @Size(min = 5, max = 100)
	private final String title;

	@Column(nullable = false)
    @NotNull
    @Size(min = 4, max = 100)
	private final String userName;

	private final String password;

	private final String webUrl;

	private final String description;

	private final LocalDate validFrom;

	@ManyToOne()
	@JoinColumn(name = "category_id", nullable = false)
//  @NotNull
	private final Category category;

	@OneToMany(targetEntity = AuthProperty.class, cascade = CascadeType.ALL, mappedBy = "authentication")
	private final List<AuthProperty> properties;

	@OneToMany(targetEntity = AuthPwdHistory.class, cascade = CascadeType.ALL, mappedBy = "authentication")
	private final List<AuthPwdHistory> histories;


	public String getTitle() {
		return title;
	}

	public String getUsername() {
		return userName;
	}

	public String getPassword() {
		return password;
	}

	public String getWeburl() {
		return webUrl;
	}

	public String getDescription() {
		return description;
	}

	public LocalDate getValidfrom() {
		return validFrom;
	}

	public Category getCategory() {
		return category;
	}

	public Long getId() {
		return id;
	}

	public List<AuthProperty> getProperties() {
		return properties;
	}

	public List<AuthPwdHistory> getHistories() {
		return histories;
	}
	
	protected Authentication() {
		this(null, null, null, null, null, null, null, null, null, null);
	}
	
	private Authentication(Long id, String title, String userName, String password, String webUrl, String description,
			LocalDate validFrom, Category category, List<AuthProperty> properties, List<AuthPwdHistory> histories) {
		super();
		this.id = id;
		this.title = title;
		this.userName = userName;
		this.password = password;
		this.webUrl = webUrl;
		this.description = description;
		this.validFrom = validFrom;
		this.category = category;
		this.properties = properties;
		this.histories = histories;
	}

	@Override
	public void validateModel() throws ValidationException {
		super.validate();
	}
	
	public static class Builder {
		private Long id;

		private String title;
		private String userName;
		private String password;
		private String webUrl;
		private String description;
		private LocalDate validFrom;
		private Category category;
		private List<AuthProperty> properties;
		private List<AuthPwdHistory> histories;

		public Authentication create() {
			return new Authentication(id, title, userName, password, webUrl, description, validFrom, category, properties, histories);
		}

		public Builder id(Long id) {
			this.id = id;
			return this;
		}

		public Builder title(String title) {
			this.title = title;
			return this;
		}

		public Builder userName(String userName) {
			this.userName = userName;
			return this;
		}

		public Builder password(String password) {
			this.password = password;
			return this;
		}
		public Builder webUrl(String webUrl) {
			this.webUrl = webUrl;
			return this;
		}
		public Builder description(String description) {
			this.description = description;
			return this;
		}
		public Builder validFrom(LocalDate validFrom) {
			this.validFrom = validFrom;
			return this;
		}
		public Builder category(Category category) {
			this.category = category;
			return this;
		}
		public Builder properties(List<AuthProperty> properties) {
			this.properties = properties;
			return this;
		}
		public Builder histories(List<AuthPwdHistory> histories) {
			this.histories = histories;
			return this;
		}
	}


}
