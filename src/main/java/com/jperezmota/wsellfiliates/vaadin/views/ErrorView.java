package com.jperezmota.wsellfiliates.vaadin.views;

import com.jperezmota.wsellfiliates.services.UserSession;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

@SpringView(name = ErrorView.VIEW_NAME)
public class ErrorView extends VerticalLayout implements View {

	public static final String VIEW_NAME = "404";

	@Override
	public void enter(ViewChangeEvent event) {
		addComponent(new Label("Page not Found. Try enter a valid URL :)"));
	}

}