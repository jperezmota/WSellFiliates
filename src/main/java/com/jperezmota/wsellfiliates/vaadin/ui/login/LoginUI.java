package com.jperezmota.wsellfiliates.vaadin.ui.login;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

import com.jperezmota.wsellfiliates.entity.AsignedCoupon;
import com.jperezmota.wsellfiliates.entity.Authority;
import com.jperezmota.wsellfiliates.entity.User;
import com.jperezmota.wsellfiliates.services.AsignedCouponServiceImpl;
import com.jperezmota.wsellfiliates.services.SecurityServiceImpl;
import com.jperezmota.wsellfiliates.utilities.ApplicationProperties;
import com.jperezmota.wsellfiliates.utilities.SystemNotificationUtil;
import com.jperezmota.wsellfiliates.utilities.UserSession;
import com.jperezmota.wsellfiliates.vaadin.ui.main.MainUI;
import com.vaadin.annotations.PreserveOnRefresh;
import com.vaadin.annotations.Theme;
import com.vaadin.event.ShortcutAction;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.Page;
import com.vaadin.server.Responsive;
import com.vaadin.server.VaadinRequest;
import com.vaadin.shared.Position;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

@SpringUI(path = "/login")
@Theme("wsellfiliates")
public class LoginUI extends UI{
	
	@Autowired
	private SecurityServiceImpl authService;
	@Autowired
	private AsignedCouponServiceImpl asignedCouponImplService;
	@Autowired 
	private UserSession UserSession;
	@Autowired
	private Environment environment;
	
	private VerticalLayout rootLayout;
	private Component loginLayout;
	private Label welcome;
	private Label title;

    private TextField txtUsername;
    private PasswordField txtPassword;

    private Button btnLogin;
    
    @Autowired
    private UserSession userSession;

    @Override
    protected void init(VaadinRequest request) {
    	if(userSession.isAuthenticated()) {
			redirectToMainUI();
		}else {
			createInterface();		
		}
    }
    
    private void redirectToMainUI() {
		   UI.getCurrent().getPage().setLocation("/");
	}
    
    private void createInterface() {
    		configureRootLayout();
    		createComponents();
    		addComponentsToUI();
    }
    
    private void configureRootLayout() {
        Responsive.makeResponsive(this);
    		super.setSizeFull();
    }
    
    private void createComponents() { 		
        rootLayout = new VerticalLayout();
        rootLayout.setSizeFull();
        rootLayout.setMargin(false);
        rootLayout.setSpacing(false);
        
        loginLayout = createLoginLayout();
    }
    
    private void addComponentsToUI() {
    		rootLayout.addComponent(loginLayout);
    		rootLayout.setComponentAlignment(loginLayout, Alignment.MIDDLE_CENTER);
    		setContent(rootLayout);
    }
    
    private void login() {
        try {
        		proccessLogin();
        } catch (Exception ex) {
        		resetForm();
            SystemNotificationUtil.showExceptionNotification(ex.getMessage());
        }
    }
    
    private void proccessLogin() {
    		String username = txtUsername.getValue();
		String password = txtPassword.getValue();
		
		User authenticatedUser =  authService.authenticateUser(username, password);
		List<String> authorities = authService.getUserAuthorities(authenticatedUser);
		AsignedCoupon asignedCoupon = asignedCouponImplService.getAsignedCouponByUsername(username);
		
		createUserSession(authenticatedUser.getUsername(), authorities, asignedCoupon);
		redirectToMainUI();
    }
    
    private void resetForm() {
    	  	txtUsername.focus();
    	  	txtUsername.selectAll();
    	  	txtPassword.setValue("");
    }

	private void createUserSession(String username, List<String> authorities, AsignedCoupon asignedCoupon) {
		userSession.setAuthenticated(true);
		userSession.setUsername(username);
		userSession.setAuthorities(authorities);
		userSession.setCoupon(asignedCoupon.getCoupon());
		userSession.setAdmin();
	}

    private Component createLoginLayout() {
        final VerticalLayout loginLayout = new VerticalLayout();
        loginLayout.setSizeUndefined();
        loginLayout.setMargin(true);
        loginLayout.addStyleName("login-panel");
        Responsive.makeResponsive(loginLayout);

        loginLayout.addComponent(buildLabels());
        loginLayout.addComponent(createFields());
        return loginLayout;
    }

    private Component buildLabels() {
        CssLayout labelsLayout = new CssLayout();
        labelsLayout.addStyleName("labels");

        welcome = new Label("Welcome");
        welcome.setSizeUndefined();
        welcome.addStyleName(ValoTheme.LABEL_H4);
        welcome.addStyleName(ValoTheme.LABEL_COLORED);

        title = new Label(environment.getProperty(ApplicationProperties.APP_NAME));
        title.setSizeUndefined();
        title.addStyleName(ValoTheme.LABEL_H3);
        title.addStyleName(ValoTheme.LABEL_LIGHT);
        
        labelsLayout.addComponent(welcome);
        labelsLayout.addComponent(title);
        return labelsLayout;
    }

    private Component createFields() {
        VerticalLayout fields = new VerticalLayout();
        fields.setMargin(false);

        txtUsername = new TextField("Username");
        txtUsername.setIcon(VaadinIcons.USER);
        txtUsername.addStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);
        txtUsername.setWidth("100%");

        txtPassword = new PasswordField("Password");
        txtPassword.setIcon(VaadinIcons.LOCK);
        txtPassword.addStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);
        txtPassword.setWidth("100%");

        btnLogin = new Button("Login");
        btnLogin.addStyleName(ValoTheme.BUTTON_PRIMARY);
        btnLogin.setClickShortcut(ShortcutAction.KeyCode.ENTER);
        btnLogin.setWidth("100%");
        btnLogin.focus();

        fields.addComponents(txtUsername, txtPassword, btnLogin);
        fields.setComponentAlignment(btnLogin, Alignment.BOTTOM_RIGHT);
        btnLogin.addClickListener(e -> login());
        return fields;
    }
    
}
