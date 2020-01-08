package mx.shf6.produccion.utilities;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.control.ComboBox;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class AutoCompleteComboBoxListener implements EventHandler<KeyEvent> {
	
	//PROPIEDADES
	private ComboBox<String> autoCompleteComboBox;
    private ObservableList<String> listaObservable;
    private boolean moveCaretToPos = false;
    private int caretPos;

    public AutoCompleteComboBoxListener(final ComboBox<String> autoCompleteComboBox) {
        this.autoCompleteComboBox = autoCompleteComboBox;
        listaObservable = autoCompleteComboBox.getItems();
        this.autoCompleteComboBox.setEditable(true);
        
        this.autoCompleteComboBox.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent t) {
                autoCompleteComboBox.hide();
            }
        });//FIN SENTENCIA
        
        this.autoCompleteComboBox.setOnKeyReleased(AutoCompleteComboBoxListener.this);
    }//FIN CONSTRUCTOR

    @Override
    public void handle(KeyEvent event) {

        if (event.getCode() == KeyCode.UP) {
            caretPos = -1;
            moveCaret(autoCompleteComboBox.getEditor().getText().length());
            return;
        } else if (event.getCode() == KeyCode.DOWN) {
            if (!autoCompleteComboBox.isShowing())
                autoCompleteComboBox.show();
            caretPos = -1;
            moveCaret(autoCompleteComboBox.getEditor().getText().length());
            return;
        } else if (event.getCode() == KeyCode.BACK_SPACE) {
            moveCaretToPos = true;
            caretPos = autoCompleteComboBox.getEditor().getCaretPosition();
        } else if (event.getCode() == KeyCode.DELETE) {
            moveCaretToPos = true;
            caretPos = autoCompleteComboBox.getEditor().getCaretPosition();
        }//FIN IF/ELSE

        if (event.getCode() == KeyCode.RIGHT || event.getCode() == KeyCode.LEFT || event.isControlDown() || event.getCode() == KeyCode.HOME || event.getCode() == KeyCode.END || event.getCode() == KeyCode.TAB)
            return;

        ObservableList<String> list = FXCollections.observableArrayList();
        for (int i=0; i<listaObservable.size(); i++) {
            if (listaObservable.get(i).toString().toLowerCase().startsWith(AutoCompleteComboBoxListener.this.autoCompleteComboBox.getEditor().getText().toLowerCase()))
                list.add(listaObservable.get(i));
        }//FIN FOR
        
        String t = autoCompleteComboBox.getEditor().getText();
        autoCompleteComboBox.setItems(list);
        autoCompleteComboBox.getEditor().setText(t);
        
        if (!moveCaretToPos)
        	caretPos = -1;
        moveCaret(t.length());
        
        if (!list.isEmpty())
            autoCompleteComboBox.show();
    }//FIN METODO

    private void moveCaret(int textLength) {
        if (caretPos == -1)
            autoCompleteComboBox.getEditor().positionCaret(textLength);
       else
            autoCompleteComboBox.getEditor().positionCaret(caretPos);
        moveCaretToPos = false;
    }//FIN METODO
    
}//FIN CLASE
