package pta.controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import pta.lib.SHA1;
import pta.model.Profile;
import pta.view.Main;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

/**
 * The controller for the Switch User form. The form components and fx ids are
 * defined in the SwitchUser.fxml file.
 * 
 * @author Steven Frost
 */
public class SwitchUserController implements Initializable {
	/* FXML Members */
	@FXML private ComboBox<String> fxUsersCombo;
	@FXML private TextField fxPassword;

	/* System Messages */
	private static final String ERROR_FXML_NOT_FOUND = "Unable to find FXML file.";
	private static final String ERROR_MISSING_PROFILE = "Unable to locate the selected user's profile.";
	private static final String ERROR_INCORRECT_PASSWORD = "Incorrect password provided.";

	/* Switch User form members */
	Main view;

	/**
	 * Populates the list of user-names with the account user-names gathered from
	 * profiles in the profiles directory.
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		File profileDir = new File("profiles/");
		ArrayList<File> files = new ArrayList<File>(Arrays.asList(profileDir.listFiles()));

		/* Clear any items from the combobox */
		fxUsersCombo.getItems().clear();

		/* For each profile, add the username to the combobox */
		for (File f : files) {
			if (f.getName().endsWith(".xml") && !f.getName().startsWith("default.xml")) {
				fxUsersCombo.getItems().add(f.getName().replace(".xml", ""));
			}
		}

		/* Select the first user by default */
		fxUsersCombo.setValue(fxUsersCombo.getItems().get(0));
	}

	@FXML
	protected void SwitchUserClick(ActionEvent event) {
		try {
			/* Attempt to load the user's profile */
			Profile p = new Profile("profiles/" + fxUsersCombo.getValue() + ".xml");

			/* Check the password validity */
			if (!SHA1.hash(fxPassword.getText()).equals(p.getPassword())) {
				throw new UnknownError(ERROR_INCORRECT_PASSWORD);
			}

			/* Load the main form */
			view.setProfile(p);
			view.displayMain();
			((Node) (event.getSource())).getScene().getWindow().hide();
		} catch (UnknownError e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, ERROR_FXML_NOT_FOUND);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, ERROR_MISSING_PROFILE);
		}
	}

	public void setView(Main view) {
		this.view = view;
	}
}