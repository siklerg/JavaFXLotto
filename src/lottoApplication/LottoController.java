package lottoApplication;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class LottoController implements Initializable {

	@FXML
	Pane basicPane;
	@FXML
	Pane popupPane;

	@FXML
	TextField guessNumberText1;
	@FXML
	TextField guessNumberText2;
	@FXML
	TextField guessNumberText3;
	@FXML
	TextField guessNumberText4;
	@FXML
	TextField guessNumberText5;

	@FXML
	Label alertMessage;
	@FXML
	Label calcMessage;
	@FXML
	Label genNumberLabel1;
	@FXML
	Label genNumberLabel2;
	@FXML
	Label genNumberLabel3;
	@FXML
	Label genNumberLabel4;
	@FXML
	Label genNumberLabel5;

	@FXML
	Button generateButton;

	@FXML
	Button buttonOK;

	private final int MIN = 1;
	private final int MAX = 90;
	private final String WIN0 = " Sajnos nem nyertél!";
	private final String WIN1 = " Ez azért gyakran előfordul...";
	private final String WIN2 = " Egy kis zsebpénz.";
	private final String WIN3 = " Na, ez már ritka!";
	private final String WIN4 = " Ez már valami! 13. havi fizu...";
	private final String WIN5 = " Főnyeremény! Gratulálunk!";

	private int genNumber1;
	private int genNumber2;
	private int genNumber3;
	private int genNumber4;
	private int genNumber5;

	private int guessNumber1;
	private int guessNumber2;
	private int guessNumber3;
	private int guessNumber4;
	private int guessNumber5;

	private ArrayList<Integer> guessNumbers = new ArrayList<>();

	/**
	 * Sorsolás indítása: - ellenőrzés - véletlen számok generálása
	 */
	@FXML
	private void calculate(ActionEvent event) {

		// a megadott számok ellenőrzése: szám-e?
		try {
			guessNumbers.clear();
			guessNumber1 = Integer.parseInt(guessNumberText1.getText());
			guessNumbers.add(guessNumber1);
			guessNumber2 = Integer.parseInt(guessNumberText2.getText());
			guessNumbers.add(guessNumber2);
			guessNumber3 = Integer.parseInt(guessNumberText3.getText());
			guessNumbers.add(guessNumber3);
			guessNumber4 = Integer.parseInt(guessNumberText4.getText());
			guessNumbers.add(guessNumber4);
			guessNumber5 = Integer.parseInt(guessNumberText5.getText());
			guessNumbers.add(guessNumber5);
		} catch (NumberFormatException e) {
			alert("Hibás beviteli adatok! 5 számot kell megadni!");
			return;
		}

		// a megadott számok ellenőrzése: különbözőek-e?
		HashSet<Integer> checkNumbers = new HashSet<>(guessNumbers);
		if (checkNumbers.size() != 5) {
			alert("A számoknak különbözőknek kell lenniük!");
			return;
		}

		// a megadott számok ellenőrzése: a megfelelő intervallumban vannak-e?
		for (Integer integer : guessNumbers) {
			if (integer < MIN || integer > MAX) {
				alert("Tipp nem lehet kisebb mint " + MIN + ", \n és nem lehet nagyobb mint " + MAX + "!");
				return;
			}
		}
		// számhúzás
		generate();

		int[] generatedNumbers = { genNumber1, genNumber2, genNumber3, genNumber4, genNumber5 };
		Label[] genNumberLabels = { genNumberLabel1, genNumberLabel2, genNumberLabel3, genNumberLabel4,
				genNumberLabel5 };

		int numberOfHits = 0;
		sortNumbers(generatedNumbers);

		// számok megjelenítése, nyerő számok kiemelése
		for (int i = 0; i < genNumberLabels.length; i++) {
			genNumberLabels[i].setText(String.valueOf(generatedNumbers[i]));
			if (guessNumbers.contains(generatedNumbers[i])) {
				genNumberLabels[i].setTextFill(Color.RED);
				numberOfHits++;
			}
		}

		switch (numberOfHits) {
		case 0:
			calcMessage.setText("Találatok száma: " + numberOfHits + WIN0);
			break;
		case 1:
			calcMessage.setText("Találatok száma: " + numberOfHits + WIN1);
			break;
		case 2:
			calcMessage.setText("Találatok száma: " + numberOfHits + WIN2);
			break;
		case 3:
			calcMessage.setText("Találatok száma: " + numberOfHits + WIN3);
			break;
		case 4:
			calcMessage.setText("Találatok száma: " + numberOfHits + WIN4);
			break;
		case 5:
			calcMessage.setText("Találatok száma: " + numberOfHits + WIN5);
			break;
		}

	}

	/** Számhúzás */
	private void generate() {
		genNumberLabel1.setTextFill(Color.BLACK);
		genNumberLabel2.setTextFill(Color.BLACK);
		genNumberLabel3.setTextFill(Color.BLACK);
		genNumberLabel4.setTextFill(Color.BLACK);
		genNumberLabel5.setTextFill(Color.BLACK);

		genNumber1 = 0;
		genNumber2 = 0;
		genNumber3 = 0;
		genNumber4 = 0;
		genNumber5 = 0;

		genNumber1 = generateRandomNumber();
		genNumber2 = generateRandomNumber();
		genNumber3 = generateRandomNumber();
		genNumber4 = generateRandomNumber();
		genNumber5 = generateRandomNumber();
	}

	/** Véletlen szám generálás */
	private int generateRandomNumber() {

		int randomNumber = (int) (Math.random() * MAX) + MIN;
		if (randomNumber == genNumber1 || randomNumber == genNumber2 || randomNumber == genNumber3
				|| randomNumber == genNumber4 || randomNumber == genNumber5) {
			return generateRandomNumber();
		}
		return randomNumber;
	}

	/** Kihúzott számok sorrendbe tétele */
	private void sortNumbers(int[] generatedNumbers) {
		for (int i = 0; i < generatedNumbers.length; i++) {
			for (int j = i + 1; j < generatedNumbers.length; j++) {
				if (generatedNumbers[i] > generatedNumbers[j]) {
					int x = generatedNumbers[j];
					generatedNumbers[j] = generatedNumbers[i];
					generatedNumbers[i] = x;
				}
			}
		}
	}

	/** Felugró ablak megkjelenítése megadott szöveggel */
	private void alert(String text) {
		popupPane.setVisible(true);
		basicPane.setOpacity(0.3);
		basicPane.setDisable(true);
		alertMessage.setText(text);
	}

	@FXML
	private void clickOK(ActionEvent event) {
		popupPane.setVisible(false);
		basicPane.setOpacity(1);
		basicPane.setDisable(false);
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub

	}

}
