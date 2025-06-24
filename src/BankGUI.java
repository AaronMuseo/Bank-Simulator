package BankSimulator;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class BankGUI extends Application {

    DBOperations dbo = new DBOperations();

    @Override
    public void start(Stage primaryStage) {
        // Ensure database and table exist
        DBSetup.initializeDatabase();

        // Input Fields
        TextField idField = new TextField();
        TextField nameField = new TextField();
        TextField balanceField = new TextField();
        TextField loanField = new TextField();
        TextField amountField = new TextField();

        TextArea output = new TextArea();
        output.setEditable(false);
        output.setWrapText(true);

        // Labels and Buttons
        Button createBtn = new Button("Create Account");
        Button checkBtn = new Button("Check Funds");
        Button depositBtn = new Button("Deposit");
        Button withdrawBtn = new Button("Withdraw");

        // Layouts
        GridPane form = new GridPane();
        form.setPadding(new Insets(10));
        form.setVgap(10);
        form.setHgap(10);

        form.add(new Label("Customer ID:"), 0, 0);
        form.add(idField, 1, 0);

        form.add(new Label("Name:"), 0, 1);
        form.add(nameField, 1, 1);

        form.add(new Label("Balance:"), 0, 2);
        form.add(balanceField, 1, 2);

       

        form.add(new Label("Amount (Deposit/Withdraw):"), 0, 4);
        form.add(amountField, 1, 4);

        HBox buttons = new HBox(10, createBtn, checkBtn, depositBtn, withdrawBtn);
        buttons.setPadding(new Insets(10, 0, 10, 0));

        VBox layout = new VBox(10, form, buttons, output);
        layout.setPadding(new Insets(20));

        // Scene Setup
        Scene scene = new Scene(layout, 500, 450);
        primaryStage.setTitle("Banking System GUI");
        primaryStage.setScene(scene);
        primaryStage.show();

        // ==== Button Logic ====

        createBtn.setOnAction(e -> {
            try {
                int id = Integer.parseInt(idField.getText());
                String name = nameField.getText();
                int balance = Integer.parseInt(balanceField.getText());
                

                String result = dbo.Create(name, id, balance);
                output.setText(result);
            } catch (Exception ex) {
                output.setText("Invalid input: " + ex.getMessage());
            }
        });

        checkBtn.setOnAction(e -> {
            try {
                int id = Integer.parseInt(idField.getText());
                String[] result = dbo.CheckFunds(id);
                if (result[0] != null) {
                    output.setText("ID: " + result[0] + "\nName: " + result[1] + "\nBalance: " + result[2]);
                } else {
                    output.setText("Customer not found.");
                }
            } catch (Exception ex) {
                output.setText("Invalid input: " + ex.getMessage());
            }
        });

        depositBtn.setOnAction(e -> {
            try {
                int id = Integer.parseInt(idField.getText());
                int amount = Integer.parseInt(amountField.getText());
                String[] result = dbo.Deposit(id, amount);
                output.setText(result[0] + "\nNew Balance: " + result[1]);
            } catch (Exception ex) {
                output.setText("Invalid input: " + ex.getMessage());
            }
        });

        withdrawBtn.setOnAction(e -> {
            try {
                int id = Integer.parseInt(idField.getText());
                int amount = Integer.parseInt(amountField.getText());
                String[] result = dbo.Withdraw(id, amount);
                if (result[0].equals("Success")) {
                    output.setText("Withdrawal successful.\nNew Balance: " + result[1]);
                } else {
                    output.setText(result[0]);
                }
            } catch (Exception ex) {
                output.setText("Invalid input: " + ex.getMessage());
            }
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}
