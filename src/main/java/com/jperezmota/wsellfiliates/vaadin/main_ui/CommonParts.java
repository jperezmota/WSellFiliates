package com.jperezmota.wsellfiliates.vaadin.main_ui;

import com.vaadin.navigator.View;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

public class CommonParts  extends VerticalLayout implements View {
	
	public CommonParts() {
		addComponent(new Label("You did it."));
	}

}
