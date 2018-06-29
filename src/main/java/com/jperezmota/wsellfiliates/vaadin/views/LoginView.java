package com.jperezmota.wsellfiliates.vaadin.views;

import com.vaadin.navigator.View;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

@SpringView(name = "login")
public class LoginView extends VerticalLayout implements View{
	
	public static final String VIEW_NAME = "login";
	
	public LoginView() {
		addComponent(new Label("klk"));
	}

}
