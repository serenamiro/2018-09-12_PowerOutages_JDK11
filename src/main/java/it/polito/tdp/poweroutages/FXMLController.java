package it.polito.tdp.poweroutages;

import java.net.URL;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.poweroutages.model.Adiacenza;
import it.polito.tdp.poweroutages.model.Model;
import it.polito.tdp.poweroutages.model.Nerc;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {
	
	private Model model;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextArea txtResult;

    @FXML
    private Button btnCreaGrafo;

    @FXML
    private ComboBox<Nerc> cmbBoxNerc;

    @FXML
    private Button btnVisualizzaVicini;

    @FXML
    private TextField txtK;

    @FXML
    private Button btnSimula;

    @FXML
    void doCreaGrafo(ActionEvent event) {
    	txtResult.clear();
    	txtResult.appendText("Creazione grafo...\n\n");
    	try {
    		model.creaGrafo();
    		txtResult.appendText("Grafo creato\n");
    		cmbBoxNerc.getItems().addAll(this.model.grafo.vertexSet());
    		txtResult.appendText("vertici: "+model.nVertici()+"\n");
    		txtResult.appendText("archi: "+model.nArchi()+"\n");
    	} catch (NumberFormatException e) {
    		txtResult.appendText("Inserire un numero valido.\n");
    	}
    }

    @FXML
    void doSimula(ActionEvent event) {
    	txtResult.clear();
    	Nerc n = cmbBoxNerc.getValue();
    	if(n==null) {
    		txtResult.appendText("Selezionare un Nerc per continuare.\n");
    		return;
    	}
    	try {
    		Integer k = Integer.parseInt(txtK.getText());
    		model.doSimulazione(k);
    		txtResult.appendText("SIMULAZIONE EFFETTUATA:\n");
    		txtResult.appendText("Numero catastrofi: "+model.getCatastrofi());
    		txtResult.appendText("NERC ID - BONUS:\n");
    		for(Nerc nerc : this.model.getBonus().keySet()) {
    			txtResult.appendText(nerc.getId()+" - "+this.model.getBonus().get(nerc)+"\n");
    		}
    	} catch(NumberFormatException e) {
    		e.printStackTrace();
    		txtResult.appendText("Inserire un numero valido.\n");
    	}
    }

    @FXML
    void doVisualizzaVicini(ActionEvent event) {
    	txtResult.clear();
    	Nerc n = cmbBoxNerc.getValue();
    	if(n==null) {
    		txtResult.appendText("Selezionare un Nerc per continuare.\n");
    		return;
    	}
    	List<Adiacenza> vicini = model.getVicini(n);
    	if(vicini!=null) {
    		txtResult.appendText("VICINI DEL NERC SELEZIONATO:\n");
    		Collections.sort(vicini);
    		for(Adiacenza a : vicini) {
    			txtResult.appendText(a.toString()+"\n");
    		}
    	} else {
    		txtResult.appendText("ERRORE.\n");
    	}
    }

    @FXML
    void initialize() {
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'PowerOutages.fxml'.";
        assert btnCreaGrafo != null : "fx:id=\"btnCreaGrafo\" was not injected: check your FXML file 'PowerOutages.fxml'.";
        assert cmbBoxNerc != null : "fx:id=\"cmbBoxNerc\" was not injected: check your FXML file 'PowerOutages.fxml'.";
        assert btnVisualizzaVicini != null : "fx:id=\"btnVisualizzaVicini\" was not injected: check your FXML file 'PowerOutages.fxml'.";
        assert txtK != null : "fx:id=\"txtK\" was not injected: check your FXML file 'PowerOutages.fxml'.";
        assert btnSimula != null : "fx:id=\"btnSimula\" was not injected: check your FXML file 'PowerOutages.fxml'.";

    }

	public void setModel(Model model) {
		this.model = model;
	}
}
