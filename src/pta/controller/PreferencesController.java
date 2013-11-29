package pta.controller;

import javax.swing.JOptionPane;
import javax.xml.transform.TransformerException;

import pta.model.Profile;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

/**
 * The controller for the preferences form. The form layout and fx ids are
 * defined in the PReferences.fxml file.
 * 
 * @author Steven Frost
 */
public class PreferencesController {
	/* FXML Members */
	@FXML private RadioButton fxHeightSelectionMetres;
	@FXML private RadioButton fxHeightSelectionFeet;
	@FXML private RadioButton fxWeightSelectionKilograms;
	@FXML private RadioButton fxWeightSelectionPounds;
	@FXML private ToggleGroup heightGroup;
	@FXML private ToggleGroup weightGroup;

	/* System Messages */
	private static final String ERROR_SAVING_PROFILE = "Error while saving profile.";

	/* Preferences form members */
	private Profile profile;
	private Stage stage;

	/**
	 * Sets the user's current preferences to be selected in the view
	 */
	public void initialise() {
		if (profile.getHeightUnit() == Profile.HeightMeasure.METRES) {
			fxHeightSelectionMetres.setSelected(true);
			fxHeightSelectionFeet.setSelected(false);
		} else {
			fxHeightSelectionMetres.setSelected(false);
			fxHeightSelectionFeet.setSelected(true);
		}

		if (profile.getWeightUnit() == Profile.WeightMeasure.KILOGRAMS) {
			fxWeightSelectionKilograms.setSelected(true);
			fxWeightSelectionPounds.setSelected(false);
		} else {
			fxWeightSelectionKilograms.setSelected(false);
			fxWeightSelectionPounds.setSelected(true);
		}
	}

	@FXML
	protected void SavePreferencesClick(ActionEvent event) {
		try {
			Profile.HeightMeasure hPref = fxHeightSelectionMetres.isSelected() ? Profile.HeightMeasure.METRES : Profile.HeightMeasure.FEET;
			Profile.WeightMeasure wPref = fxWeightSelectionKilograms.isSelected() ? Profile.WeightMeasure.KILOGRAMS : Profile.WeightMeasure.POUNDS;

			profile.setHeightUnit(hPref);
			profile.setWeightUnit(wPref);
			profile.saveProfile();
			stage.close();
		} catch (TransformerException e) {
			JOptionPane.showMessageDialog(null, ERROR_SAVING_PROFILE);
		}
	}

	public void setProfile(Profile profile) {
		this.profile = profile;
	}

	public void setStage(Stage stage) {
		this.stage = stage;
	}
}
