package com.ibh.pocketpassword.viewmodel;

import java.time.LocalDate;
import java.time.Period;

import com.ibh.pocketpassword.validation.ValidationException;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.LongProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public interface AuthUserPwdVM {

	String getUserName();
	String getPassword();

}
