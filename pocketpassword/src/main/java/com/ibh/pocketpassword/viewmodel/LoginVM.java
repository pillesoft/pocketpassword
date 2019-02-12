package com.ibh.pocketpassword.viewmodel;

import com.ibh.pocketpassword.validation.ValidationException;

import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;


public class LoginVM extends BaseViewModel<LoginVM> {
  
  @NotEmpty(message = "Database Name is obligatory")
  @Size(min=5, message = "Too short. Minimum length is 5 characters")
  private StringProperty databaseName;
  @NotEmpty(message = "User Name is obligatory")
  @Size(min=5, message = "Too short. Minimum length is 5 characters")
  private StringProperty userName;
  @NotEmpty(message = "Password is obligatory")
  @Size(min=6, message = "Too short. Minimum length is 6 characters")
  private StringProperty password;

  public LoginVM() {
    databaseName = new SimpleStringProperty(null, "databaseName", "");
    userName = new SimpleStringProperty(null, "userName", "");
    password = new SimpleStringProperty(null, "password", "");
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
  
  public StringProperty getDatabaseName() {
	return databaseName;
}

public void setDatabaseName(StringProperty databaseName) {
	this.databaseName = databaseName;
}

@Override
  public void validateModel() throws ValidationException {
    super.validate();
  }

  

  
  
}
