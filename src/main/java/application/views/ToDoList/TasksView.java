package application.views.ToDoList;

import application.views.BasicLayout;
import application.views.components.Task;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route("Tasks")
@PageTitle("Taskview")
public class TasksView extends BasicLayout {

    public TasksView() {
        String title = "Tasks";
        setTitle(title);

        VerticalLayout layout = new VerticalLayout();
        layout.setSizeFull();
        layout.setSpacing(true);
        layout.setMargin(true);
        layout.setPadding(true);

        HorizontalLayout controlbar = new HorizontalLayout();

        controlbar.setSpacing(true);
        controlbar.setMargin(true);
        controlbar.setPadding(true);
        Button addTask = new Button("Add");
        addTask.addClickListener(e -> {});
        Button removeTask = new Button("Remove");
        removeTask.addClickListener(e -> {});
        Button editTask = new Button("Edit");
        editTask.addClickListener(e -> {});
        controlbar.add(addTask, removeTask, editTask);

        Grid<Task> grid = new Grid();
        grid.setAllRowsVisible(true);
        grid.setWidth("1510px");

        grid.addColumn(Task::getTitle)
                .setHeader("Title")
                .setWidth("500px");
        grid.addColumn(Task::getDescription)
                .setHeader("Description")
                .setWidth("500px");
        grid.addColumn(Task::getPriority)
                .setHeader("Priority")
                .setWidth("500px");

        layout.add(controlbar, grid);
        setContent(layout);
    }

}
