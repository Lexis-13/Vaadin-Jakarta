package application;

import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.login.LoginI18n;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route("login")
public class LogInView extends VerticalLayout {

    private final LoginForm loginForm = new LoginForm();

    public LogInView(){
        loginForm.setAction("login");
        loginForm.setI18n(createGermanI18n());
        add(loginForm);
        setSizeFull();
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);
    }

    private LoginI18n createGermanI18n() {
        LoginI18n i18n = LoginI18n.createDefault();
        i18n.getForm().setTitle("Login");
        i18n.getForm().setUsername("Benutzername");
        i18n.getForm().setPassword("Passwort");
        i18n.getForm().setSubmit("Einloggen");
        i18n.getForm().setForgotPassword("Passwort vergessen?");
        return i18n;
    }

}
