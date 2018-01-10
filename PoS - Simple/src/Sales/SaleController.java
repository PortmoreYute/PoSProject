package Sales;

import Objects.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;

import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.sql.Time;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Scanner;

public class SaleController implements Initializable {
    @FXML private ComboBox<String> itemCB = new ComboBox<>();
    @FXML private ComboBox<String> servingCB = new ComboBox<>();
    @FXML private TextField quantityTF,subCostTF,totalTF,nameOnOrderTF;
    @FXML private TextArea instructionTA;
    @FXML private TableView<OrderItem> itemTV;
    @FXML private TableView<Order> orderTV;
    @FXML private TableColumn<OrderItem,String> itemTC;
    @FXML private TableColumn<OrderItem,String>servingTC;
    @FXML private TableColumn<OrderItem,Integer>quantityTC;
    @FXML private TableColumn<OrderItem,Float>subCostTC;
    @FXML private TableColumn<OrderItem,String>instructionTC;
    @FXML private TableColumn<Order,String>nameOnOrderTC;
    @FXML private TableColumn<Order,Float>totalTC;
    @FXML private TableColumn<Order,String>orderNumTC;
    @FXML private TableColumn<Order,Time>timeTC;
    @FXML private AnchorPane saleAP;
    @FXML private Button orderBtn,removeItemBtn,completeBtn,cancelBtn;
    private ObservableList<OrderItem> data = FXCollections.observableArrayList();
    private ObservableList<Order> orderData = FXCollections.observableArrayList();
    private Log log;


    public Log getLog() {
        return log;
    }
    public void setLog(Log log) {
        this.log = log;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeItemComboBox();
        initializeServingComboBox();
        addListeners();
        setProperties();
        addPendingOrders();
    }
    //Supplementary Functions
    private void addListeners(){
        /*quantityTF.textProperty().addListener((obs, oldText, newText) ->{
            if(!quantityTF.getText().matches("^\\d+$")){

                //set the textField empty
                quantityTF.setText("0");
            }
            updateInputCost(newText);
        });*/

        quantityTF.setOnAction(event -> {
            if(!quantityTF.getText().matches("^\\d+$")){
                //set the textField empty
                quantityTF.setText("0");
            }
            updateInputCost(quantityTF.getText());
        });
        quantityTF.focusedProperty().addListener((arg0, oldValue, newValue) -> {
            if (!newValue) { //when focus lost
                if(!quantityTF.getText().matches("^\\d+$")){

                    //set the textField empty
                    quantityTF.setText("0");
                }
            }
            updateInputCost(quantityTF.getText());
        });

        itemCB.setOnAction(event ->updateInputCost(quantityTF.getText()) );
        servingCB.setOnAction(event ->updateInputCost(quantityTF.getText()) );
    }

    private void setProperties(){
        //Properties for itemTV
        itemTC.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        quantityTC.setCellValueFactory(new PropertyValueFactory<>("itemQuantity"));
        subCostTC.setCellValueFactory(new PropertyValueFactory<>("subCost"));
        servingTC.setCellValueFactory(new PropertyValueFactory<>("serving"));
        instructionTC.setCellValueFactory(new PropertyValueFactory<>("instructions"));

        //Properties for orderTV
        nameOnOrderTC.setCellValueFactory(new PropertyValueFactory<>("name"));
        totalTC.setCellValueFactory(new PropertyValueFactory<>("total"));
        orderNumTC.setCellValueFactory(new PropertyValueFactory<>("orderNum"));
        timeTC.setCellValueFactory(new PropertyValueFactory<>("time"));

        orderTV.setRowFactory(tv -> {
            TableRow<Order> row = new TableRow<>();
            row.hoverProperty().addListener((obs, wasHovered, isNowHovered) -> {
                Tooltip tooltip = new Tooltip();
                if (isNowHovered && ! row.isEmpty()) {
                    tooltip.setText(getItemsForOrder(row.getItem().getOrderNum()));
                    row.setTooltip(tooltip);
                }
            });
            return row ;
        });


    }

    private void initializeItemComboBox(){
        ArrayList<String> itemList = new ArrayList<>();
        Scanner inFile;
        File accFile = new File("ItemList.txt");
        InventoryItem tempItem = new InventoryItem();
        try {
            inFile = new Scanner(accFile);
            while (inFile.hasNext()){
                tempItem.setItemName(inFile.next());
                tempItem.setQuantity(inFile.nextInt());
                tempItem.setItemPrice(inFile.nextFloat());
                itemList.add(tempItem.getItemName());
            }
            inFile.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        itemCB.getItems().addAll(itemList);
    }

    private void initializeServingComboBox(){
        ArrayList<String> servingList = new ArrayList<>();
        Scanner inFile;
        File accFile = new File("ServingList.txt");
        ServingItem tempItem = new ServingItem();
        try {
            inFile = new Scanner(accFile);
            while (inFile.hasNext()){
                tempItem.setName(inFile.next());
                tempItem.setPrice(inFile.nextFloat());
                servingList.add(tempItem.getName());
            }
            inFile.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        servingCB.getItems().addAll(servingList);
    }

    //Control Actions
    public void addButtonAction() {
        if(checkItemToAdd()) {
            addToItemTV();
            clearField();
        }
    }

    public void orderButtonAction(ActionEvent actionEvent) {
        if(actionEvent.getSource().equals(orderBtn)){
            addToOrderTV();
            nameOnOrderTF.setText("");
            totalTF.setText("0");
            clearField();
        }
        if(actionEvent.getSource().equals(removeItemBtn)){
            OrderItem temp = itemTV.getSelectionModel().getSelectedItem();

            totalTF.setText(String.valueOf(Float.parseFloat(totalTF.getText())-temp.getSubCost()));
            itemTV.getItems().remove(itemTV.getSelectionModel().getSelectedItem());
        }
        if(actionEvent.getSource().equals(completeBtn)){
            completeOrder(orderTV.getSelectionModel().getSelectedItem());
            orderTV.getItems().remove(orderTV.getSelectionModel().getSelectedItem());
            showInformationAlert("Completed");
        }
        if(actionEvent.getSource().equals(cancelBtn)){
            cancelOrder(orderTV.getSelectionModel().getSelectedItem());
            orderTV.getItems().remove(orderTV.getSelectionModel().getSelectedItem());
            showInformationAlert("Cancelled");
        }
    }

    public void closeMIAction() throws IOException {
        Node source = saleAP;
        Stage stage  = (Stage) source.getScene().getWindow();
        this.log.logUser();
        stage.close();
        loginView();
    }

    private void updateInputCost(String text) {
        float subCost;
        int quantity = 0;
        if(!text.equals("")){
            quantity = Integer.parseInt(quantityTF.getText());
        }
        subCost = (findItemCost() * quantity) + findServingCost();
        subCostTF.setText(String.valueOf(subCost));
    }

    //Logic Functions
    private void addToItemTV(){
        float total = 0;
        float itemQuantityCost;
        OrderItem temp = new OrderItem();

        if(!(itemCB.getValue()==null)){
            temp.setItemName(itemCB.getValue());
        }

        if(!(servingCB.getValue()==null)){
            temp.setServing(servingCB.getValue());
        }
        temp.setItemQuantity(Integer.parseInt(quantityTF.getText()));

        if(!instructionTA.getText().equals("") && !(instructionTA.getText() == null)){
            temp.setInstructions(instructionTA.getText());
        }

        temp.setSubCost(Float.parseFloat(subCostTF.getText()));

        itemQuantityCost = Float.parseFloat(subCostTF.getText());
        if (!totalTF.getText().equals("")) {
            total = Float.parseFloat(totalTF.getText());
        }
        total = total + itemQuantityCost;
        totalTF.setText(String.valueOf(total));

        data.add(temp);
        itemTV.setItems(data);
    }

    private void addToOrderTV(){
        int orderNum;
        orderNum = findOrderNum();
        Order temp = new Order();

        if(!nameOnOrderTF.getText().equals("") && !(nameOnOrderTF.getText() == null)){
            temp.setName(nameOnOrderTF.getText().replaceAll("\\s","_"));
        }
        temp.setTotal(Float.parseFloat(totalTF.getText()));
        temp.setOrderNum(String.valueOf(orderNum));
        temp.setStatus("Pending");
        temp.setTime(Time.valueOf(LocalTime.now()));
        orderData.add(temp);
        orderTV.setItems(orderData);
        writeToItemsOnOrder(orderNum);
        writeToOrderFile(temp);
        itemTV.getItems().clear();
    }

    private float findItemCost(){
        float cost = 0;
        Scanner inFile;
        File accFile = new File("ItemList.txt");
        InventoryItem tempItem = new InventoryItem();
        try {
            inFile = new Scanner(accFile);
            while (inFile.hasNext()){
                tempItem.setItemName(inFile.next());
                tempItem.setQuantity(inFile.nextInt());
                tempItem.setItemPrice(inFile.nextFloat());
                if(tempItem.getItemName().equals(itemCB.getValue())){
                    cost = tempItem.getItemPrice();
                }
            }
            inFile.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return cost;
    }

    private float findServingCost(){
        float cost = 0;
        Scanner inFile;
        File accFile = new File("ServingList.txt");
        ServingItem tempItem = new ServingItem();
        try {
            inFile = new Scanner(accFile);
            while (inFile.hasNext()){
                tempItem.setName(inFile.next());
                tempItem.setPrice(inFile.nextFloat());
                if(tempItem.getName().equals(servingCB.getValue())){
                    cost = tempItem.getPrice();
                }
            }
            inFile.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return cost;
    }

    private void writeToOrderFile(Order order){
        File orderFile = new File("Orders.txt");
        try {
            FileWriter orderFW = new FileWriter(orderFile, true);
            BufferedWriter orderBW = new BufferedWriter(orderFW);
            orderBW.write(order.getName()+ " ");
            orderBW.write(order.getTotal() + " ");
            orderBW.write(order.getOrderNum() + " ");
            orderBW.write(order.getStatus() + " ");
            orderBW.write(order.getTime().toString());
            orderBW.newLine();
            orderBW.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void cancelOrder(Order selectedOrder){
        Scanner inFile;
        File orderFile = new File("Orders.txt");
        File tempFile = new File("TempOrders.txt");
        Order tempOrder = new Order();

        try {
            inFile = new Scanner(orderFile);
            while (inFile.hasNext()) {
                tempOrder.setName(inFile.next());
                tempOrder.setTotal(Float.parseFloat(inFile.next()));
                tempOrder.setOrderNum(inFile.next());
                tempOrder.setStatus(inFile.next());
                tempOrder.setTime(Time.valueOf(inFile.next()));
                if (!tempOrder.getOrderNum().equals(selectedOrder.getOrderNum())) {
                    writeTempOrderFile(tempOrder);
                }else {
                    tempOrder.setStatus("Cancelled");
                    writeTempOrderFile(tempOrder);
                }
            }
            inFile.close();
            orderFile.delete();
            tempFile.renameTo(new File("Orders.txt"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void completeOrder(Order selectedOrder){
        Scanner inFile;
        File orderFile = new File("Orders.txt");
        File tempFile = new File("TempOrders.txt");
        Order tempOrder = new Order();

        try {
            inFile = new Scanner(orderFile);
            while (inFile.hasNext()) {
                tempOrder.setName(inFile.next());
                tempOrder.setTotal(Float.parseFloat(inFile.next()));
                tempOrder.setOrderNum(inFile.next());
                tempOrder.setStatus(inFile.next());
                tempOrder.setTime(Time.valueOf(inFile.next()));
                if (tempOrder.getOrderNum().equals(selectedOrder.getOrderNum())) {
                    tempOrder.setStatus("Complete");
                }
                writeTempOrderFile(tempOrder);
            }
            inFile.close();
            orderFile.delete();
            tempFile.renameTo(new File("Orders.txt"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void writeTempOrderFile(Order order){
        File orderFile = new File("TempOrders.txt");
        try {
            FileWriter orderFW = new FileWriter(orderFile, true);
            BufferedWriter orderBW = new BufferedWriter(orderFW);
            orderBW.write(order.getName()+ " ");
            orderBW.write(order.getTotal() + " ");
            orderBW.write(order.getOrderNum() + " ");
            orderBW.write(order.getStatus() + " ");
            orderBW.write(order.getTime().toString());
            orderBW.newLine();
            orderBW.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private int findOrderNum(){
        Scanner inFile;
        File orderFile = new File("Orders.txt");
        Order temp = new Order();
        if(!orderFile.exists()){
            return 1;
        }else{
            try {
                inFile = new Scanner(orderFile);
                while (inFile.hasNext()) {
                    temp.setName(inFile.next());
                    temp.setTotal(Float.parseFloat(inFile.next()));
                    temp.setOrderNum(inFile.next());
                    temp.setStatus(inFile.next());
                    temp.setTime(Time.valueOf(inFile.next()));
                }
                inFile.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            return Integer.parseInt(temp.getOrderNum()) + 1;
        }
    }

    private void writeToItemsOnOrder(int orderNum){
        File itemsOOFile = new File("ItemsOnOrders.txt");
        OrderItem temp;
        char uni = 0x2202;
        try {
            FileWriter itemOOFW = new FileWriter(itemsOOFile, true);
            BufferedWriter itemOOBW = new BufferedWriter(itemOOFW);
            for (OrderItem aData : data) {
                temp = aData;
                temp.setOrderNum(String.valueOf(orderNum));
                itemOOBW.write(temp.getOrderNum() + " ");
                itemOOBW.write(temp.getItemName() + " ");
                itemOOBW.write(temp.getServing() + " ");
                itemOOBW.write(temp.getItemQuantity() + " ");
                itemOOBW.write(temp.getSubCost() + " ");
                itemOOBW.write(temp.getInstructions()+ " "+uni);
                itemOOBW.newLine();
            }
            itemOOBW.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void addPendingOrders(){
        orderTV.getItems().clear();
        Scanner inFile;
        File orderFile = new File("Orders.txt");
        if(orderFile.exists()) {
            try {
                inFile = new Scanner(orderFile);
                while (inFile.hasNext()) {
                    Order temp = new Order();
                    temp.setName(inFile.next());
                    temp.setTotal(Float.parseFloat(inFile.next()));
                    temp.setOrderNum(inFile.next());
                    temp.setStatus(inFile.next());
                    temp.setTime(Time.valueOf(inFile.next()));
                    if(temp.getStatus().equals("Pending"))
                        orderData.add(temp);
                }
                inFile.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            orderTV.setItems(orderData);
        }
    }

    private void clearField(){
        quantityTF.setText("");
        subCostTF.setText(null);
        instructionTA.setText("");
        servingCB.getSelectionModel().clearSelection();
        itemCB.getSelectionModel().clearSelection();

    }

    private String getItemsForOrder(String orderNum){
        Scanner inFile;
        File itemsOOFile = new File("ItemsOnOrders.txt");
        OrderItem temp = new OrderItem();
        String itemsInOrder = null;
        String uni = "\u2202";
        String check;
        try {
            inFile = new Scanner(itemsOOFile);
            while (inFile.hasNext()) {
                temp.setOrderNum(inFile.next());
                temp.setItemName(inFile.next());
                temp.setServing(inFile.next());
                temp.setItemQuantity(Integer.parseInt(inFile.next()));
                temp.setSubCost(Float.parseFloat(inFile.next()));
                temp.setInstructions(inFile.next());
                while (inFile.hasNext()){
                    check = inFile.next();
                    if(!check.equals(uni)){
                        temp.setInstructions(temp.getInstructions()+" "+check);
                    }else{
                        break;
                    }
                }
                if(temp.getOrderNum().equals(orderNum)){
                    if(itemsInOrder == null){
                        itemsInOrder = temp.display();
                    }else{
                        itemsInOrder = itemsInOrder+"\n"+temp.display();
                    }
                }
            }
            inFile.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return itemsInOrder;
    }

    private boolean checkItemToAdd(){
        if(itemCB.getValue() == null){
            showErrorAlert("Item ","An item must be selected");
            return false;
        }
        if(servingCB.getValue() == null){
            showErrorAlert("Serving","A serving must be selected");
            return false;
        }
        if(quantityTF.getText().equals("")){
            showErrorAlert("Quantity","Field Cannot Be Empty");
            return false;
        }
        return true;
    }

    void showErrorAlert(String fieldError, String message){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText("Invalid Input");
        alert.setContentText(fieldError + " has invalid input\n" + message);
        alert.showAndWait();
    }

    void showInformationAlert(String action){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText("Success");
        alert.setContentText("Order was successfully "+action);
        alert.showAndWait();
    }

    //View Functions
    private void loginView() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Login/LoginView.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root);
        Stage stage = new Stage();
        //stage.initStyle(StageStyle.TRANSPARENT);
        //scene.setFill(Color.TRANSPARENT);
        scene.getStylesheets().add("Resource/modena_dark.css");
        stage.setScene(scene);
        stage.show();
    }

}
