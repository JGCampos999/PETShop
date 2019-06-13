package control;

import java.util.ArrayList;
import java.util.List;

import entity.Animal;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class AnimalControl {
	private List<Animal> animais = new ArrayList<Animal>();
	private ObservableList<Animal> dataList = FXCollections.observableArrayList();

	public void adicionar(Animal a) {
		animais.add(a);
		dataList.clear();
		dataList.addAll(animais);
	}

	public Animal pesquisarPorNome(String nome) {
		dataList.clear();
		for (Animal a : animais) {
			if (a.getNome().contains(nome)) {
				dataList.add(a);
				return a;
			}
		}
		dataList.addAll(animais);
		return null;
	}

	public ObservableList<Animal> getDataList() {
		return dataList;
	}

	public void setDataList(ObservableList<Animal> dataList) {
		this.dataList = dataList;
	}
}
