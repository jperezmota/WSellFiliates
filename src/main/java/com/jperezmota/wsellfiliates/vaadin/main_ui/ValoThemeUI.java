package com.jperezmota.wsellfiliates.vaadin.main_ui;


import com.vaadin.annotations.PreserveOnRefresh;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.*;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.spring.navigator.SpringViewProvider;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

@SpringUI
@PreserveOnRefresh
@Theme("valo")
@Title("Demo Valo Theme")
public class ValoThemeUI extends UI{

    @Autowired
    private SpringViewProvider viewProvider;
    private Navigator navigator;

    ValoLayout rootLayout;
    CssLayout menuItemsLayout = new CssLayout();

    CssLayout menu;
    
    @Override
    protected void init(VaadinRequest request) {
    		createInterface();
    }
    
    private void createInterface() {
    		configureRootUI();
		createUIComponents();
		addComponentsToUI();
    }

    private void configureRootUI() {
		Responsive.makeResponsive(this);
	    addStyleName(ValoTheme.UI_WITH_MENU);
	}
    
    private void createUIComponents() {
    	 	createMainLayout();
    	 	createMainMenu();
    }
    
    private void createMainLayout() {
    		rootLayout = new ValoLayout();
		rootLayout.setWidth("100%");		
    }
    
    private void createMainMenu() {
    		ComponentContainer viewDisplayArea = rootLayout.getContentContainer();
		rootLayout.addMenu(new MainMenu(this, viewDisplayArea));	
    }
   
    
    private void addComponentsToUI() {
    		setContent(rootLayout);
    }
   
}