package com.jperezmota.wsellfiliates.vaadin.main_ui;
//package com.jperezmota.wsellfiliates;
//
//import org.springframework.beans.factory.annotation.Autowired;
//
//import com.jperezmota.wsellfiliates.services.AuthService;
//import com.vaadin.annotations.Theme;
//import com.vaadin.navigator.Navigator;
//import com.vaadin.navigator.View;
//import com.vaadin.navigator.ViewDisplay;
//import com.vaadin.server.VaadinRequest;
//import com.vaadin.spring.annotation.SpringUI;
//import com.vaadin.spring.annotation.SpringViewDisplay;
//import com.vaadin.spring.navigator.SpringNavigator;
//import com.vaadin.spring.navigator.SpringViewProvider;
//import com.vaadin.ui.Button;
//import com.vaadin.ui.Button.ClickEvent;
//import com.vaadin.ui.Button.ClickListener;
//import com.vaadin.ui.Component;
//import com.vaadin.ui.Notification;
//import com.vaadin.ui.Panel;
//import com.vaadin.ui.UI;
//import com.vaadin.ui.VerticalLayout;
//
//@Theme("valo")
//@SpringUI(path="")
//@SpringViewDisplay
//public class VaadinUI extends UI implements ViewDisplay{
//	
//	Panel springViewDisplay = new Panel();
//	
//	@Autowired
//	AuthService authService;
//	@Autowired
//	private SpringViewProvider springViewProvider;
//
//	@Autowired
//	 SpringNavigator navigator;
//	  protected static final String MAINVIEW = "main";
//
//	@Override
//	protected void init(VaadinRequest request) {
//	
//		
//	    getPage().setTitle("Navigation Example");
//        navigator.addProvider(springViewProvider);
//        setNavigator(navigator);    
//        
//        Button btn = new Button("Display Login");
//        btn.addClickListener(event -> getUI().getNavigator().navigateTo("login"));
//        VerticalLayout content = new VerticalLayout();
//        setContent(content);
//        
//        TabSheet tabSheet = new TabSheet(personsView, groupView);
//        tabSheet.addStyleName(ValoTheme.TABSHEET_CENTERED_TABS);
//        tabSheet.setSizeFull();
//        
//        
//        content.addComponent(tabSheet);
//        content.addComponent(btn);
//        content.addComponent(springViewDisplay);
//        
//       
//		
//	}
//
//	@Override
//	public void showView(View view) {
//		springViewDisplay.setContent((Component) view);
//		
//	}
//
//
//}
