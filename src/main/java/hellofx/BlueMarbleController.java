package hellofx;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.DialogPane;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class BlueMarbleController extends BlueMarble {	
	private boolean isEnhanced;
	private boolean isBlackAndWhite;
	
	@FXML
	private ImageView image;

	@FXML
	private DatePicker date;
	
	@FXML
	private DialogPane errorPane;
	
	@FXML
	private Button enhancedBtn;
	
	@FXML
	private Button blackWhiteBtn;
	
	@FXML
	void updateDate(ActionEvent event) {
		isBlackAndWhite = false;
		isEnhanced = false;
		populateImage();
	}
	
	@FXML
	void enhanceImage(ActionEvent event) {
		isEnhanced = true;
		populateImage();
	}
	
	@FXML
	void showBlackAndWhite(ActionEvent event) {
		isBlackAndWhite = true;
		populateImage();
	}
	
	void populateImage() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		
		BlueMarble blueMarble = new BlueMarble();
		
		String selectedDate = date.getValue().toString();
		LocalDate selectedLocalDate = LocalDate.parse(selectedDate, formatter);

		String latestNaturalImg = BlueMarble.getMostRecentImageDate("natural");
		LocalDate latestNaturalImgLocalDate = LocalDate.parse(latestNaturalImg, formatter);
		
		String latestEnhancedImg = BlueMarble.getMostRecentImageDate("enhanced");
		LocalDate latestEnhancedImgLocalDate = LocalDate.parse(latestEnhancedImg, formatter);
		
		if (selectedLocalDate.isAfter(latestNaturalImgLocalDate)) {
			errorPane.setContentText("Please select a date \n"
					+ "on or before " + latestNaturalImgLocalDate);
	    } else if (selectedLocalDate.isAfter(latestEnhancedImgLocalDate)) {
	    	enhancedBtn.setDisable(true);
	    } else {
	    	errorPane.setContentText("");
			blueMarble.setDate(selectedDate);
			blueMarble.setEnhanced(isEnhanced);
			image.setImage(new Image(blueMarble.getImage()));
			ColorAdjust colorAdjust = new ColorAdjust();
			if (isBlackAndWhite) {
		        colorAdjust.setSaturation(-1);
			} else {
		        colorAdjust.setSaturation(0);
			}
			image.setEffect(colorAdjust);
		}
	}


}