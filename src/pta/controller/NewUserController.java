package pta.controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

import javax.swing.JOptionPane;

import pta.lib.SHA1;
import pta.model.Person;
import pta.model.Profile;
import pta.view.Main;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.TextField;

/**
 * The controller for the New User form. The form layout and fx ids for the
 * controls are defined in NewUser.fxml.
 * 
 * @author Steven Frost
 */
public class NewUserController implements Initializable {
	/* FXML Members */
	@FXML private TextField fxForenameBox;
	@FXML private TextField fxSurnameBox;
	@FXML private TextField fxDOBDayBox;
	@FXML private TextField fxDOBMonthBox;
	@FXML private TextField fxDOBYearBox;
	@FXML private TextField fxUsernameBox;
	@FXML private TextField fxPasswordBox;

	/* System Messages */
	private static final String ERROR_FXML_NOT_FOUND = "Unable to find FXML file.";
	private static final String ERROR_MISSING_FIELDS = "One or more fields were not filled in.";
	private static final String ERROR_INVALID_DATE = "The date of birth you entered is invalid.";
	private static final String ERROR_INVALID_USERNAME = "Please enter a username consisting of lower case letters only.";
	private static final String ERROR_USERNAME_ALREADY_IN_USE = "The selected username is already in use.";

	/* New User form members */
	private Main view;
	private Profile profile;

	/**
	 * Sets the placeholder text for the form
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		/* Set standard prompt text */
		fxForenameBox.setPromptText("John");
		fxSurnameBox.setPromptText("Smith");
		fxDOBDayBox.setPromptText("DD");
		fxDOBMonthBox.setPromptText("MM");
		fxDOBYearBox.setPromptText("YYYY");
		fxUsernameBox.setPromptText("johnsmith");
	}

	@FXML
	protected void CreateAccountClick(ActionEvent event) {
		String forename = fxForenameBox.getText();
		String surname = fxSurnameBox.getText();
		String dob = fxDOBDayBox.getText() + "-" + fxDOBMonthBox.getText() + "-" + fxDOBYearBox.getText();
		String username = fxUsernameBox.getText();
		String password = fxPasswordBox.getText();

		try {
			/* Check for empty fields */
			if (forename.isEmpty() || surname.isEmpty() || dob.equals("--") || username.isEmpty() || password.isEmpty()) {
				throw new Exception(ERROR_MISSING_FIELDS);
			}

			/* Validate the date entered */
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
			sdf.setLenient(false);
			sdf.parse(dob);

			/* Validate the username */
			if (Pattern.compile("[^a-z]").matcher(username).find()) {
				throw new Exception(ERROR_INVALID_USERNAME);
			} else if (new File("profiles/" + username + ".xml").exists()) {
				throw new Exception(ERROR_USERNAME_ALREADY_IN_USE);
			}

			/* Build a new profile */
			view.setProfile(new Profile("profiles/" + username + ".xml"));
			this.profile = view.getProfile();
			profile.setUsername(username);
			profile.setPassword(SHA1.hash(password));
			profile.setUser(new Person(forename, surname, dob));
			profile.saveProfile();

			/* Load the main form */
			view.setProfile(profile);
			view.displayMain();
			((Node) (event.getSource())).getScene().getWindow().hide();
		} catch (ParseException e) {
			JOptionPane.showMessageDialog(null, ERROR_INVALID_DATE);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, ERROR_FXML_NOT_FOUND);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
	}

	public void setView(Main view) {
		this.view = view;
	}
}