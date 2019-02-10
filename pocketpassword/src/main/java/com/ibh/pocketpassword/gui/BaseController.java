package com.ibh.pocketpassword.gui;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ibh.pocketpassword.validation.ValidationException;

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
	private Map<String, Control> validatedControls;
	
	
	
	protected void setControlStateNormal() {
		BorderStroke bs = new BorderStroke(Paint.valueOf("GREY"), BorderStrokeStyle.SOLID, CornerRadii.EMPTY,
				BorderWidths.DEFAULT);
		Border b = new Border(bs);

		validatedControls.forEach((k, v) -> {
			v.borderProperty().set(b);
			v.tooltipProperty().set(null);
		});
	}

	protected void setControlStateError(ValidationException valexc) {
		BorderWidths width = new BorderWidths(2);
		BorderStroke bs = new BorderStroke(Paint.valueOf("RED"), BorderStrokeStyle.SOLID, CornerRadii.EMPTY, width);
		Border b = new Border(bs);

		valexc.getValidationError().forEach((field, errs) -> {
			Control ctrl = validatedControls.get(field);
			if (ctrl == null) {
				LOG.warn("there is no control for field: {}", field);
			} else {
				ctrl.borderProperty().set(b);

				Tooltip tt = new Tooltip(String.join("\n", errs));
				ctrl.tooltipProperty().set(tt);
			}
		});

	}

}
