package pta.controller;

import java.security.NoSuchAlgorithmException;

import javax.swing.JOptionPane;
import javax.xml.transform.TransformerException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import pta.lib.SHA1;
import pta.model.Profile;

/**
 * The controller for the Reset Password form. The form structure and fx ids for
 * individual components are defined in the ResetPassword.fxml file.
 * 
 * @author Steven Frost
 */
public class ResetPasswordController {
	/* FXML Members */
	@FXML private TextField fxCurrentPassword;
	@FXML private TextField fxNewPassword;

	/* System messages */
	private static final String ERROR_INCORRECT_PASSWORD = "An incorrect current password was specified.";
	private static final String ERROR_INTERNAL = "An internal error occurred.";
	private static final String ERROR_SAVING_PROFILE = "Error while saving profile.";

	/* Reset form members */
	private Profile profile;
	private Stage stage;

	@FXML
	protected void ResetPasswordClick(ActionEvent event) {
		try {
			if (!SHA1.hash(fxCurrentPassword.getText()).equals(profile.getPassword())) {
				throw new Exception(ERROR_INCORRECT_PASSWORD);
			}
			profile.setPassword(SHA1.hash(fxNewPassword.getText()));
			profile.saveProfile();
			stage.close();
		} catch (NoSuchAlgorithmException e) {
			JOptionPane.showMessageDialog(null, ERROR_INTERNAL);
			return;
		} catch (TransformerException e) {
			JOptionPane.showMessageDialog(null, ERROR_SAVING_PROFILE);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
	}

	public void setProfile(Profile profile) {
		this.profile = profile;
	}

	public void setStage(Stage stage) {
		this.stage = stage;
	}
}