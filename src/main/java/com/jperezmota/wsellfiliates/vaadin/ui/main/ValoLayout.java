package com.jperezmota.wsellfiliates.vaadin.ui.main;


import com.vaadin.ui.Component;
import com.vaadin.ui.ComponentContainer;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;

public class ValoLayout extends HorizontalLayout {

	private CssLayout menuArea;
    private CssLayout contentArea;

    public ValoLayout() {
    		createInterface(); 
    }
    
    private void createInterface() {
    		configureRootLayout();
    		createComponents();
    		addComponentsToUI();
    }

    private void configureRootLayout() {
    		setSizeFull();
    }
    
    private void createComponents() {
    		menuArea = new CssLayout();
    	  	menuArea.setPrimaryStyleName("valo-menu");

    	  	contentArea = new CssLayout();
    	  	contentArea.setPrimaryStyleName("valo-content");
    	  	contentArea.addStyleName("v-scrollable");
    	  	contentArea.setSizeFull();
    }
    
    private void addComponentsToUI() {
    		addComponents(menuArea, contentArea);
        setExpandRatio(contentArea, 1);
    }
    
    public ComponentContainer getContentContainer() {
        return contentArea;
    }

    public void addMenu(Component menu) {
        menuArea.addComponent(menu);
    }

}