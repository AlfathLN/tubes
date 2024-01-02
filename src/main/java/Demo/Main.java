package Demo;

import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Main extends Application {

    private Stage primaryStage;
    private TextField userTextField;
    private PasswordField passwordBox;
    private TextField NamaField;
    private TextField NikField;
    private TextField NomorField;
    private TableView<DataPemilihan> tableView;
    private ObservableList<DataPemilihan> data = FXCollections.observableArrayList();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        showLoginPage();
    }

    private void showLoginPage() {
        primaryStage.setTitle("Login");

        GridPane grid = new GridPane();
        grid.setAlignment(javafx.geometry.Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Label userName = new Label("Username:");
        userTextField = new TextField();
        Label pw = new Label("Password:");
        passwordBox = new PasswordField();

        Button loginButton = new Button("Login");
        loginButton.setOnAction(e -> showInputPage());

        grid.add(userName, 0, 1);
        grid.add(userTextField, 1, 1);
        grid.add(pw, 0, 2);
        grid.add(passwordBox, 1, 2);
        grid.add(loginButton, 1, 3);

        Scene scene = new Scene(grid, 300, 200);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void showInputPage() {

        primaryStage.setTitle("Data Pemilihan");

        // Form components




        Label NamaLabel = new Label("Nama:");
        NamaField = new TextField();

        Label NikLabel = new Label("NIK:");
        NikField = new TextField();

        Label NomorLabel = new Label("Nomor:");
        NomorField = new TextField();

        Button createButton = new Button("Create");
        createButton.setOnAction(e -> createButtonClicked());

        Button exportButton = new Button("Export to TXT");
        exportButton.setOnAction(e -> exportToTxtButtonClicked());

        ImageView imageView = new ImageView(new Image(getClass().getResourceAsStream("/933123404p.jpg")));
        imageView.setFitWidth(300); // Sesuaikan dengan lebar yang diinginkan
        imageView.setFitHeight(150); // Sesuaikan dengan tinggi yang diinginkan



        // Table view
        // Table view
        tableView = new TableView<>();
        tableView.setItems(data);

        TableColumn<DataPemilihan, String> NamaCol = new TableColumn<>("Nama");
        NamaCol.setCellValueFactory(new PropertyValueFactory<>("nama"));

// Mengganti cara menampilkan NIK pada tabel
        TableColumn<DataPemilihan, String> NikCol = new TableColumn<>("NIK");
        NikCol.setCellValueFactory(cellData -> {
            String nik = cellData.getValue().getNik();
            if (nik.length() > 4) {
                return new SimpleStringProperty("********" + nik.substring(nik.length() - 4));
            } else {
                return new SimpleStringProperty("********");
            }
        });

// Mengganti cara menampilkan Nomor pada tabel
        TableColumn<DataPemilihan, String> NomorCol = new TableColumn<>("Nomor");
        NomorCol.setCellValueFactory(cellData -> {
            String nomor = cellData.getValue().getNomor();
            return new SimpleStringProperty("*" + nomor.substring(nomor.length() - 0));
        });

        tableView.getColumns().addAll(NamaCol, NikCol, NomorCol);


        GridPane inputGrid = new GridPane();
        inputGrid.setAlignment(javafx.geometry.Pos.CENTER);
        inputGrid.setHgap(10);
        inputGrid.setVgap(10);
        inputGrid.setPadding(new Insets(25, 25, 25, 25));

        inputGrid.add(NamaLabel, 0, 1);
        inputGrid.add(NamaField, 1, 1);
        inputGrid.add(NikLabel, 0, 2);
        inputGrid.add(NikField, 1, 2);
        inputGrid.add(NomorLabel, 0, 3);
        inputGrid.add(NomorField, 1, 3);
        inputGrid.add(createButton, 1, 4);
        inputGrid.add(exportButton, 1, 5);
        inputGrid.add(imageView, 0, 0, 2, 1);


        VBox vbox = new VBox(20);
        vbox.getChildren().addAll(inputGrid, tableView);

        Scene scene = new Scene(vbox, 560, 500);
        primaryStage.setScene(scene);
    }

    private void createButtonClicked() {
        try {
            String nama = NamaField.getText();
            String nik = NikField.getText();
            String nomor = NomorField.getText();

            if (nama.isEmpty() || nik.isEmpty() || nomor.isEmpty()) {
                showAlert("Input Error", "Semua field harus diisi.");
                return;
            }
            if (!nik.matches("\\d+")) {
                showAlert("Input Error", "NIK harus berupa angka.");
                return;
            }
            if (!nik.matches("\\d{17}")) {
                showAlert("Input Error", "NIK harus berupa angka dengan panjang 17 digit.");
                return;
            }
            if (!nomor.matches("\\d+")) {
                showAlert("Input Error", "Nomor harus berupa angka.");
                return;
            }
            if (nomor.length() != 1 || !nomor.matches("\\d")) {
                showAlert("Input Error", "Nomor harus berupa satu angka.");
                return;
            }
            if (!nomor.matches("[1-3]")) {
                showAlert("Input Error", "Nomor harus berupa angka antara 1-3.");
                return;
            }



            // Add data to table
            DataPemilihan dataPemilihan = new DataPemilihan(nama, nik, nomor);
            data.add(dataPemilihan);

            NamaField.clear();
            NikField.clear();
            NomorField.clear();

        } catch (Exception e) {
            showAlert("Error", "Terjadi kesalahan.");
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void exportToTxtButtonClicked() {
        // Gunakan FileChooser untuk memilih lokasi penyimpanan file
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Simpan ke file TXT");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("TXT Files", "*.txt"));

        File file = fileChooser.showSaveDialog(primaryStage);

        if (file != null) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                // Tulis data tabel ke file
                for (DataPemilihan dataPemilihan : data) {
                    writer.write(dataPemilihan.getNama() + "\t" + dataPemilihan.getNik() + "\t" + dataPemilihan.getNomor());
                    writer.newLine();
                }
                showAlert("Export Success", "Data berhasil diekspor ke file TXT.");
            } catch (IOException e) {
                showAlert("Export Error", "Terjadi kesalahan saat menulis ke file TXT.");
            }
        }
    }



    public static class DataPemilihan {
        private final String nama;
        private final String nik;
        private final String nomor;

        public DataPemilihan(String nama, String nik, String nomor) {
            this.nama = nama;
            this.nik = nik;
            this.nomor = nomor;
        }

        public String getNama() {
            return nama;
        }

        public String getNik() {
            return nik;
        }

        public String getNomor() {
            return nomor;
        }
    }
}