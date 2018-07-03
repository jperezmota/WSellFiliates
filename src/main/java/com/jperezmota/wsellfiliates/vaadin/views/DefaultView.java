package com.jperezmota.wsellfiliates.vaadin.views;

import com.jperezmota.wsellfiliates.services.UserSession;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

@SpringView(name = DefaultView.VIEW_NAME)
public class DefaultView extends VerticalLayout implements View {

	private static final long serialVersionUID = 1L;
	public static final String VIEW_NAME = "";
	
	@Autowired
	UserSession userSession;
	
	@PostConstruct
	private void init() {
		addComponent(new Label("This is the default view"));
	}
	
	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub
	}

}