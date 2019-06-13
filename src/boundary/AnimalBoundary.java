package boundary;

import java.util.Date;

import javax.swing.JOptionPane;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import control.AnimalControl;
import entity.Animal;
import javafx.application.Application;
import javafx.beans.property.ReadOnlyIntegerWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class AnimalBoundary extends Application implements EventHandler<ActionEvent> {
	private TextField txtId = new TextField();
	private TextField txtNome = new TextField();
	private TextField txtNasc = new TextField();
	private TextField txtPeso = new TextField();
	private Button btnAdd = new Button("Adicionar");
	private Button btnSch = new Button("Pesquisar");

	private AnimalControl control = new AnimalControl();
	private TableView<Animal> tableView = new TableView<>();
	private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

	public static void main(String[] args) {
		Application.launch(args);
	}

	public void start(Stage stage) throws Exception {
		VBox box = new VBox();
		GridPane grid = new GridPane();
		Scene scene = new Scene(box, 500, 300);
		box.getChildren().addAll(grid, tableView);

		createTableColumns();
		txtId.setText("");
		txtNome.setText("");
		txtNasc.setText("");
		txtPeso.setText("");
		grid.add(new Label("Id"), 0, 0);
		grid.add(txtId, 1, 0);
		grid.add(new Label("Nome do Animal"), 0, 1);
		grid.add(txtNome, 1, 1);
		grid.add(new Label("Data de Nascimento"), 0, 2);
		grid.add(txtNasc, 1, 2);
		grid.add(new Label("Peso"), 0, 3);
		grid.add(txtPeso, 1, 3);
		grid.add(btnAdd, 0, 6);
		grid.add(btnSch, 1, 6);
		btnAdd.setPrefWidth(150);
		btnAdd.setStyle("-fx-border-color:#5173ee");
		btnSch.setPrefWidth(150);
		btnSch.setStyle("-fx-border-color:#5173ee");
		btnAdd.addEventFilter(ActionEvent.ACTION, this);
		btnSch.addEventFilter(ActionEvent.ACTION, this);

		stage.setScene(scene);
		stage.setTitle("Registro de Animais do Petshop");
		stage.show();
	}

	public void handle(ActionEvent event) {
		if (event.getTarget() == btnAdd) {
			if (!txtId.getText().equals("") && !txtNome.getText().equals("") && !txtNasc.getText().equals("")
					&& !txtPeso.getText().equals("")) {
				Animal a = boundaryToAnimal();
				control.adicionar(a);
				animalToBoundary(a);
			} else {
				JOptionPane.showMessageDialog(null,
						"Não será possível fazer o registro, pois faltam informações a serem preenchidas");
			}
		} else if (event.getTarget() == btnSch) {
			if (!txtNome.getText().equals("")) {
				control.pesquisarPorNome(txtNome.getText());
			} else {
				JOptionPane.showMessageDialog(null,
						"Não será possível realizar a pesquisa, pois faltam informações a serem preenchidas");
			}
		}
	}

	private void animalToBoundary(Animal a) {
		txtId.setText(String.valueOf(a.getId()));
		txtNome.setText(a.getNome());
		String data = sdf.format(a.getNascimento());
		txtNasc.setText(data);
		System.out.println(data);
		txtPeso.setText(String.format("%6.2f", a.getPeso()));
	}

	private Animal boundaryToAnimal() {
		Animal a = new Animal();
		a.setNome(txtNome.getText());
		try {
			a.setId(Integer.parseInt(txtId.getText()));
			a.setPeso(Float.parseFloat(txtPeso.getText()));
			Date d = sdf.parse(txtNasc.getText());
			System.out.println(d);
			a.setNascimento(d);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return a;
	}

	private void createTableColumns() {
		tableView.setItems(control.getDataList());
		tableView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Animal>() {
			@Override
			public void changed(ObservableValue<? extends Animal> a, Animal a1, Animal a2) {
				animalToBoundary(a2);
			}
		});
		TableColumn<Animal, Number> idColumn = new TableColumn<>("Id");
		idColumn.setCellValueFactory(item -> new ReadOnlyIntegerWrapper(item.getValue().getId()));

		TableColumn<Animal, String> nomeColumn = new TableColumn<>("Nome");
		nomeColumn.setCellValueFactory(item -> new ReadOnlyStringWrapper(item.getValue().getNome()));

		TableColumn<Animal, String> dataColumn = new TableColumn<>("Fabricação");
		dataColumn.setCellValueFactory(item -> new ReadOnlyStringWrapper(sdf.format(item.getValue().getNascimento())));

		TableColumn<Animal, Double> pesoColumn = new TableColumn<>("Peso");
		pesoColumn.setCellValueFactory(new PropertyValueFactory<Animal, Double>("peso"));

		tableView.getColumns().addAll(idColumn, nomeColumn, dataColumn, pesoColumn);
	}
}
