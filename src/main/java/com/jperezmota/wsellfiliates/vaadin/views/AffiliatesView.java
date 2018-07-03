package com.jperezmota.wsellfiliates.vaadin.views;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

@SpringView(name = AffiliatesView.VIEW_NAME)
public class AffiliatesView extends VerticalLayout implements View{
	
	public static final String VIEW_NAME = "affiliates";
	
	@Override
	public void enter(ViewChangeEvent event) {
		addComponent(new Label("Affiliates"));
    }

}
