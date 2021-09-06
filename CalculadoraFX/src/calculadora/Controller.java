package calculadora;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

public class Controller {

    @FXML
    private Text output;

    private String operator = "";
    private double number1 = 0.0;
    private double result;
    private Model model = new Model();
    private boolean start = true;
    private boolean number1Value = false;//Controla  um valor atribuído
    private boolean dot = false;//Controlados números
    private boolean number1Negative = false;//Controla  número negativo

    @FXML
    private void processNumber(ActionEvent event) {

        if (start) {
            output.setText("");
            start = false;
        }

        String value = ((Button) event.getSource()).getText();
        output.setText(output.getText() + value);
        number1Value = true;
    }

    @FXML
    private void processOperator(ActionEvent event) {

        String value = ((Button) event.getSource()).getText();

        if (!"=".equals(value)) {

            if (!operator.isEmpty()) {
                if (operator.equals("-") && number1Value == true) {
                    operator = value;
                    number1 = Double.parseDouble(output.getText());
                    output.setText("");
                    dot = false;
                }
                return;
            }
            
            //número negativo
            if (value.equals("-")) {
                if (number1Value == true) {
                    operator = value;
                    number1 = Double.parseDouble(output.getText());
                    output.setText("");
                    dot = false;
                    return;
                } else {
                    output.setText(output.getText() + value);
                    start = false;
                    operator = value;
                }
                return;
            }

            // operadores de +, * e /
            if (!number1Value) {
                return;
            }

            operator = value;
            number1 = Double.parseDouble(output.getText());
            output.setText("");
            dot = false;

        } else {
            //
            if (operator.isEmpty() || number1Value == false) {
                return;
            }

            if (operator.equals("√")) {
                result = model.calculate(number1, 0, operator);
            } else {
                result = model.calculate(number1, Double.parseDouble(output.getText()), operator);
            }

            output.setText(String.valueOf(result));
            start = true;
            operator = "";
            number1Value = false;
        }
    }

    @FXML
    private void processClean(ActionEvent event) {

        number1 = 0.0;
        operator = "";
        output.setText("");
        number1Value = false;
        dot = false;
    }

    //Este método controla no números
    @FXML
    private void processDot(ActionEvent event) {


        if (start) {
            output.setText("");
            start = false;
        }


        if (dot) {
            return;
        }

        //Impede que inicie un número
        if (!number1Value) {
            return;
        }

        String value = ((Button) event.getSource()).getText();
        output.setText(output.getText() + value);
        dot = true;
    }
}
