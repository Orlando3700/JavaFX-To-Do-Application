package todo;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.util.Callback;

// It extends Application to make a JavaFX app.
public class ToDoListApp2 extends Application {

    @Override
    // The main entry point for all JavaFX applications.
    // Stage is the main window.
    public void start(Stage primaryStage) {
        // Create UI components
    	// A list to show all tasks. Each task is a string.
        ListView<String> taskListView = new ListView<>();
        // Input box to type in new tasks.
        // setPromptText shows placeholder text when empty.
        TextField taskInputField = new TextField();
        taskInputField.setPromptText("Enter a new task");
        // A button to add tasks to the list.
        Button addButton = new Button("Add Task");
        // Adds custom CSS classes for styling.
        taskInputField.getStyleClass().add("text-field");
        addButton.getStyleClass().add("blue-button");

        // Add button action
        // When clicking Add, it:
        // Gets the trimmed text.
        // If it's not empty, adds it to the list.
        // Clears the input box.
        addButton.setOnAction(e -> {
            String task = taskInputField.getText().trim();
            if (!task.isEmpty()) {
                taskListView.getItems().add(task);
                taskInputField.clear();
            }
        });

        // Custom cell factory for ListView items
        // Customizes how each item in the ListView looks.
        taskListView.setCellFactory(new Callback<ListView<String>, ListCell<String>>() {
            @Override
            public ListCell<String> call(ListView<String> listView) {
            	// Defines a new custom cell type.
                return new ListCell<String>() {
                	// Horizontal layout
                    HBox hBox = new HBox();
                    Label label = new Label();
                    CheckBox checkBox = new CheckBox();
                    Button deleteButton = new Button("Delete");
                    Button editButton = new Button("Edit");

                    {
                    	// Apply CSS styles                    	
                    	label.getStyleClass().add("task-label");
                    	checkBox.getStyleClass().add("task-checkbox");
                    	editButton.getStyleClass().add("yellow-button");
                    	deleteButton.getStyleClass().add("red-button");
                         
                    	// Sets space between elements.
                    	// Adds all UI elements to the horizontal box.
                        hBox.setSpacing(10);
                        hBox.getChildren().addAll(checkBox, label, editButton, deleteButton);
                        
                        // Delete action
                        // Removes the current task when Delete is clicked
                        deleteButton.setOnAction(e -> {
                            getListView().getItems().remove(getItem());
                        });
                        
                        // Creates a new window for editing the task.
                        editButton.setOnAction(e -> {
                            Stage editStage = new Stage();
                            editStage.setTitle("Edit Task");

                            // Make the window modal
                            // Makes the new window modal, so it blocks the main window.
                            editStage.initOwner(getScene().getWindow());
                            editStage.initModality(javafx.stage.Modality.WINDOW_MODAL);

                            // Create UI components
                            // Input and buttons for the edit dialog.
                            TextField editField = new TextField(getItem());
                            Button saveButton = new Button("Save");
                            Button cancelButton = new Button("Cancel");
                            
                            // Add CSS styling
                            saveButton.getStyleClass().add("green-button");
                            cancelButton.getStyleClass().add("red-button");

                            // Layout for buttons
                            // Organizes UI inside the edit popup with spacing and padding.
                            HBox buttonBox = new HBox(10, saveButton, cancelButton);
                            VBox layout = new VBox(10, new Label("Edit Task:"), editField, buttonBox);
                            layout.setPadding(new Insets(15));

                            // Creates the edit window and adds styles.
                            Scene scene = new Scene(layout, 300, 150);
                            scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
                            
                            // Pressing Enter will "click" the save button.
                            scene.setOnKeyPressed(keyEvent -> {
                            	if (keyEvent.getCode() == javafx.scene.input.KeyCode.ENTER) {
                            		saveButton.fire();
                            	}
                            });
                            	//switch (keyEvent.getCode()) {
                            	//case ENTER:
                            		//saveButton.fire();
                            		//break;
                            	//}
                            //});

                            // Save action
                            // Updates the task in the list and closes the window.
                            saveButton.setOnAction(ev -> {
                                String updatedTask = editField.getText().trim();
                                // If updated task isn't empty
                                if (!updatedTask.isEmpty()) {
                                    getListView().getItems().set(getIndex(), updatedTask);
                                    // Close window
                                    editStage.close();
                                }
                            });

                            // Cancel action
                            cancelButton.setOnAction(ev -> editStage.close());
                            editStage.setScene(scene);
                            // Shows the dialog and waits until it’s closed.
                            editStage.showAndWait();
                            
                        });
                        
                        // CheckBox Action
                        // If the checkbox is checked, it strikes through the task text.
                        checkBox.setOnAction(e -> {
                        	if (checkBox.isSelected()) {
                        		label.setStyle("-fx-strikethrough: true;");
                        	} else {
                        		label.setStyle("-fx-strikethrough: false;");
                        	}
                        });
                    }

                    @Override
                    // Updates the contents of each cell when items change.
                    // Resets the UI and reuses the HBox with the correct text.
                    protected void updateItem(String task, boolean empty) {
                        super.updateItem(task, empty);
                        if (empty || task == null) {
                            setGraphic(null);
                        } else {
                            label.setText(task);
                            //checkBox.setSelected(false);
                            //label.setStyle("-fx-strikethrough: false;");
                            setGraphic(hBox);
                        }
                    }
                };
            }
        });

        // Layout
        // Vertical layout for the entire window: input -> button -> list.
        VBox layout = new VBox(15);
        layout.setPadding(new Insets(20));
        layout.getChildren().addAll(taskInputField, addButton, taskListView);

        // Set the scene
        // Sets the main scene size and loads CSS styles.
        Scene scene = new Scene(layout, 400, 450);
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        
        // Pressing Enter anywhere will trigger the add button.
        scene.setOnKeyPressed(keyEvent -> {
        	if (keyEvent.getCode() == javafx.scene.input.KeyCode.ENTER) {
        		addButton.fire();
        	}
        });
        	 //switch (keyEvent.getCode()) {
        	 //case ENTER:
        		 //addButton.fire();
        		 //break;
        	 //}
        //});
        
        // Shows the main application window.
        primaryStage.setTitle("To-Do List");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Starts the JavaFX application.
    public static void main(String[] args) {
        launch(args);
    }
}



