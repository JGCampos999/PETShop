package boundary;

import java.text.SimpleDateFormat;

import com.sun.javafx.css.Stylesheet;

import control.AnimalControl;
import entity.Animal;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class AnimalBoundary implements EventHandler<ActionEvent> {
	private TextField txtId = new TextField();
	private TextField txtNome = new TextField();
	private TextField txtData = new TextField();
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
		Scene scene = new Scene(box, 300, 300);
		box.getChildren().addAll(grid, tableView);
		
		createTableColumns();
		
		grid.add(new Label("Id"), 0, 0);
		grid.add(txtId, 1, 0);
		grid.add(new Label("Nome do Animal"), 0, 1);
		grid.add(txtNome, 1, 1);
		grid.add(new Label("Data de Nascimento"), 0, 2);
		grid.add(txtData, 1, 2);
		grid.add(new Label("Peso"), 0, 3);
		grid.add(txtPeso, 1, 3);
		grid.add(btnAdd, 0, 6);
		grid.add(btnSch, 1, 6);
		
		btnAdd.addEventFilter(ActionEvent.ACTION, this);
		btnSch.addEventFilter(ActionEvent.ACTION, this);
		
		
		stage.setScene(scene);
		stage.setTitle("Gestão de Pizzas");
		stage.show();
}
	public void handle(ActionEvent event) {
		if (event.getTarget() == btnAdd) {
			Animal a = boundaryToAnimal();
			control.adicionar(a);
			animalToBoundary(new Animal());
		} else if (event.getTarget() == btnSch) {
			control.pesquisarPorNome(txtNome.getText());
		}
	}

	private void animalToBoundary(Animal a) {
		txtId.setText(String.valueOf(a.getId()));
		txtNome.setText(a.getNome());
		txtData.setText(sdf.format(a.getNascimento()));
		txtPeso.setText(String.format("%6.2f", a.getPeso()));
	}

	private Animal boundaryToAnimal() {
		Animal a = new Animal();
		a.setNome(txtNome.getText());
		p.set(txtIngredientes.getText());
		p.setTamanho(cmbTamanho.getValue());
		try {
			p.setId(Long.parseLong(txtId.getText()));
			p.setPreco(Float.parseFloat(txtPreco.getText()));
			Date d = sdf.parse(txtFabricacao.getText());
			p.setFabricacao(d);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return a;
	}

	private void createTableColumns() {
		tableView.setItems(control.getDataList());
		tableView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Pizza>() {
			@Override
			public void changed(ObservableValue<? extends Pizza> p, Animal a1, Animal a2) {
				animalToBoundary(p2);
			}
		});
		TableColumn<Pizza, Number> idColumn = new TableColumn<>("Id");
		idColumn.setCellValueFactory(item -> new ReadOnlyLongWrapper(item.getValue().getId()));

		TableColumn<Pizza, String> saborColumn = new TableColumn<>("Sabor");
		saborColumn.setCellValueFactory(item -> new ReadOnlyStringWrapper(item.getValue().getSabor()));

		TableColumn<Pizza, Double> precoColumn = new TableColumn<>("Preço");
		precoColumn.setCellValueFactory(new PropertyValueFactory<Pizza, Double>("preco"));

		TableColumn<Pizza, String> fabricColumn = new TableColumn<>("Fabricação");
		fabricColumn
				.setCellValueFactory(item -> new ReadOnlyStringWrapper(sdf.format(item.getValue().getFabricacao())));

		tableView.getColumns().addAll(idColumn, saborColumn, precoColumn, fabricColumn);
	}
}
