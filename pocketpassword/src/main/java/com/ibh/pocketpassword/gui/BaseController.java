package com.ibh.pocketpassword.gui;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ibh.pocketpassword.model.Category;
import com.ibh.pocketpassword.validation.ValidationException;

import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Control;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Paint;

public abstract class BaseController implements CrudController {
	private static final Logger LOG = LoggerFactory.getLogger(BaseController.class);
	private final Map<String, Control> boundedControls = new HashMap<>();

	protected void bindBidirectional(StringProperty vmStringProperty, StringProperty controlStringProperty) {
		controlStringProperty.bindBidirectional(vmStringProperty);
//		Bindings.bindBidirectional(vmStringProperty, controlStringProperty);
		boundedControls.put(vmStringProperty.getName(), (Control) controlStringProperty.getBean());
	}

	protected <T> void bindBidirectional(ObjectProperty<T> vmTypeProperty, ObjectProperty<T> controlValueProperty) {
		controlValueProperty.bindBidirectional(vmTypeProperty);
//		Bindings.bindBidirectional(vmTypeProperty, controlValueProperty);
		boundedControls.put(vmTypeProperty.getName(), (Control) controlValueProperty.getBean());
	}
	
	protected void setControlStateNormal() {
		BorderStroke bs = new BorderStroke(Paint.valueOf("GREY"), BorderStrokeStyle.SOLID, CornerRadii.EMPTY,
				BorderWidths.DEFAULT);
		Border b = new Border(bs);

		boundedControls.forEach((k, v) -> {
			v.borderProperty().set(b);
			v.tooltipProperty().set(null);
		});
	}

	protected void setControlStateError(ValidationException valexc) {
		BorderWidths width = new BorderWidths(2);
		BorderStroke bs = new BorderStroke(Paint.valueOf("RED"), BorderStrokeStyle.SOLID, CornerRadii.EMPTY, width);
		Border b = new Border(bs);

		valexc.getValidationError().forEach((err) -> {
			Control ctrl = boundedControls.get(err.getFieldName());
			if (ctrl == null) {
				LOG.warn("there is no control for field: {}", err.getFieldName());
			} else {
				ctrl.borderProperty().set(b);

				Tooltip tt = new Tooltip(String.join("\n", err.getErrorMessages()));
				ctrl.tooltipProperty().set(tt);
			}
		});

	}

}
