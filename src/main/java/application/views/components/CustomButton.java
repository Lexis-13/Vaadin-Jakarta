package application.views.components;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class CustomButton extends VerticalLayout {

    public CustomButton(String label){
        Button button = new Button();
        button.setSizeFull();
        button.setWidth("100%");
        button.setHeight("100%");
        button.setText("");
        button.setText(label);
        button.addClickListener(event -> {

        });
        add(button);
    }
}
