package com.jperezmota.wsellfiliates.vaadin.main_ui;


import com.vaadin.ui.Component;
import com.vaadin.ui.ComponentContainer;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;

public class ValoLayout extends HorizontalLayout {

	CssLayout menuArea = new CssLayout();
    CssLayout contentArea = new CssLayout();

    public ValoLayout() {
        setSizeFull();

        menuArea.setPrimaryStyleName("valo-menu");

        contentArea.setPrimaryStyleName("valo-content");
        contentArea.addStyleName("v-scrollable");
        contentArea.setSizeFull();

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