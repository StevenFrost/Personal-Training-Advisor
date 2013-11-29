package pta.view;

import pta.controller.MainController;
import pta.controller.NewUserController;
import pta.controller.PreferencesController;
import pta.controller.ResetPasswordController;
import pta.controller.SwitchUserController;
import pta.model.Profile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JOptionPane;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * The main view class. This class defines the entry point for the program and
 * manages the creation and state of different windows in the program.
 * 
 * @author Steven Frost
 */
public class Main extends Application {
	/* Main class members */
	private Stage stage;
	private Profile profile;

	/**
	 * Checks the integrity of the system and the existence of profiles other
	 * than the default. The function then displays the most appropriate window
	 * for the current state of the system.
	 */
	@Override
	public void start(Stage stage) throws Exception {
		this.stage = stage;

		File profileDir = new File("profiles/");
		ArrayList<File> files = new ArrayList<File>(Arrays.asList(profileDir.listFiles()));
		ArrayList<File> profiles = new ArrayList<File>();
		boolean defaultFileExists = false;

		for (File f : files) {
			if (f.getName().startsWith("default.xml")) {
				defaultFileExists = true;
				continue;
			} else if (f.getName().endsWith(".xml")) {
				profiles.add(f);
			}
		}

		if (profiles.size() == 0) {
			displayNewUser();
		} else if (files.size() > 1) {
			displaySwitchUser();
		} else if (!defaultFileExists) {
			JOptionPane.showMessageDialog(null, "Some required files are missing from the system. Please reinstall.");
			System.exit(0);
		}
	}

	/**
	 * Displays the main window as a standard form. The window is not modal.
	 * 
	 * @param stage the root stage
	 * @throws IOException if the fxml file cannot be located
	 */
	public void displayMain() throws IOException {
		/* Load the main fxml file */
		FXMLLoader main = new FXMLLoader();
		main.setLocation(getClass().getResource("Main.fxml"));
		main.load();

		/* Initialise the controller */
		Parent root = main.getRoot();
		MainController mc = (MainController) main.getController();
		mc.setView(this);
		mc.initialise();

		/* Configure the scene */
		Scene scene = new Scene(root);
		stage.setTitle("Personal Training Advisor");
		stage.setMinHeight(553);
		stage.setMinWidth(802);
		stage.setScene(scene);
		stage.show();
	}

	/**
	 * Displays the reset password window as a modal dialog box.
	 * 
	 * @param stage the parent stage
	 * @throws IOException if the fxml file cannot be located
	 */
	public void displayResetPassword(Profile profile) throws IOException {
		/* Load the reset password fxml file */
		FXMLLoader resetPassword = new FXMLLoader();
		resetPassword.setLocation(getClass().getResource("ResetPassword.fxml"));
		resetPassword.load();

		Parent root = resetPassword.getRoot();
		Stage dialog = new Stage(StageStyle.DECORATED);
		dialog.initModality(Modality.WINDOW_MODAL);
		dialog.initOwner(stage);

		ResetPasswordController rpc = (ResetPasswordController) resetPassword.getController();
		rpc.setProfile(profile);
		rpc.setStage(dialog);

		/* Configure the scene */
		Scene scene = new Scene(root);
		dialog.setTitle("Reset Password");
		dialog.setMinHeight(164);
		dialog.setMaxHeight(164);
		dialog.setMinWidth(297);
		dialog.setMaxWidth(297);
		dialog.setScene(scene);
		dialog.show();
	}

	/**
	 * Displays the new user window as a modal dialog box.
	 * 
	 * @param stage the parent stage
	 * @throws IOException if the fxml file cannot be located
	 */
	public void displayNewUser() throws IOException {
		/* Load the new user fxml file */
		FXMLLoader newUser = new FXMLLoader();
		newUser.setLocation(getClass().getResource("NewUser.fxml"));
		newUser.load();

		Parent root = newUser.getRoot();
		Stage dialog = new Stage(StageStyle.DECORATED);
		dialog.initModality(Modality.WINDOW_MODAL);
		dialog.initOwner(stage);

		NewUserController nuc = (NewUserController) newUser.getController();
		nuc.setView(this);

		/* Configure the scene */
		Scene scene = new Scene(root);
		dialog.setTitle("New User Account");
		dialog.setMinHeight(256);
		dialog.setMaxHeight(256);
		dialog.setMinWidth(302);
		dialog.setMaxWidth(302);
		dialog.setScene(scene);
		dialog.show();
	}

	/**
	 * Displays the switch user window as a modal dialog box.
	 * 
	 * @param stage the parent stage
	 * @throws IOException if the fxml file cannot be located
	 */
	public void displaySwitchUser() throws IOException {
		/* Load the switch user fxml file */
		FXMLLoader switchUser = new FXMLLoader();
		switchUser.setLocation(getClass().getResource("SwitchUser.fxml"));
		switchUser.load();

		Parent root = switchUser.getRoot();
		Stage dialog = new Stage(StageStyle.DECORATED);
		dialog.initModality(Modality.WINDOW_MODAL);
		dialog.initOwner(stage);

		SwitchUserController suc = (SwitchUserController) switchUser.getController();
		suc.setView(this);

		/* Configure the scene */
		Scene scene = new Scene(root);
		dialog.setTitle("Switch User Account");
		dialog.setMinHeight(164);
		dialog.setMaxHeight(164);
		dialog.setMinWidth(287);
		dialog.setMaxWidth(287);
		dialog.setScene(scene);
		dialog.show();
	}

	/**
	 * Displays the preferences window as a modal dialog box.
	 * 
	 * @param stage the parent stage
	 * @throws IOException if the fxml file cannot be located
	 */
	public void displayPreferences(Profile profile) throws IOException {
		/* Load the preferences fxml file */
		FXMLLoader preferences = new FXMLLoader();
		preferences.setLocation(getClass().getResource("Preferences.fxml"));
		preferences.load();

		Parent root = preferences.getRoot();
		Stage dialog = new Stage(StageStyle.DECORATED);
		dialog.initModality(Modality.WINDOW_MODAL);
		dialog.initOwner(stage);

		PreferencesController pc = (PreferencesController) preferences.getController();
		pc.setProfile(profile);
		pc.setStage(dialog);
		pc.initialise();

		/* Configure the scene */
		Scene scene = new Scene(root);
		dialog.setTitle("Preferences");
		dialog.setMinHeight(257);
		dialog.setMaxHeight(257);
		dialog.setMinWidth(201);
		dialog.setMaxWidth(201);
		dialog.setScene(scene);
		dialog.show();
	}

	/**
	 * Displays the date time picker window as a modal dialog box.
	 * 
	 * @param stage the parent stage
	 * @throws IOException if the fxml file cannot be located
	 */
	public void DisplayDateTimePicker() throws IOException {
		/* Load the preferences fxml file */
		FXMLLoader dateTime = new FXMLLoader();
		dateTime.setLocation(getClass().getResource("DateTimePicker.fxml"));
		dateTime.load();

		Parent root = dateTime.getRoot();
		Stage dialog = new Stage(StageStyle.DECORATED);
		dialog.initModality(Modality.WINDOW_MODAL);
		dialog.initOwner(stage);

		/* Configure the scene */
		Scene scene = new Scene(root);
		dialog.setTitle("Time and Date");
		dialog.setMinHeight(165);
		dialog.setMaxHeight(165);
		dialog.setMinWidth(253);
		dialog.setMaxWidth(253);
		dialog.setScene(scene);
		dialog.show();
	}

	public void close() {
		stage.close();
	}

	public Stage getStage() {
		return stage;
	}

	public void setProfile(Profile profile) {
		this.profile = profile;
	}

	public Profile getProfile() {
		return profile;
	}

	/**
	 * Main program entry point
	 * 
	 * @param args command-line arguments
	 */
	public static void main(String[] args) {
		launch(args);
	}
}