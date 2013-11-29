package pta.controller;

import pta.lib.DateAxis;
import pta.model.BMIRecord;
import pta.model.DietRecord;
import pta.model.Exercise;
import pta.model.Food;
import pta.model.LinkRecord;
import pta.model.PictureRecord;
import pta.model.Profile;
import pta.model.TrainingRecord;
import pta.view.Main;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JOptionPane;
import javax.xml.transform.TransformerException;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.TilePane;
import javafx.stage.FileChooser;
import javafx.util.Callback;
import javafx.util.converter.NumberStringConverter;

/**
 * The controller for the Main form. The form layout and fx ids are defined in
 * Main.fxml. This class is the bridge between the view and the model.
 * 
 * @author Steven Frost
 */
public class MainController {
	/* FXML Members */
	@FXML private RadioMenuItem fxMenuViewTableAsDefault;
	@FXML private Label fxOverviewNameLabel;
	@FXML private Label fxOverviewUsernameLabel;
	@FXML private Label fxOverviewAgeLabel;
	@FXML private Label fxOverviewDOBLabel;
	@FXML private Label fxOverviewLastBMILabel;
	@FXML private Label fxOverviewLastMealLabel;
	@FXML private Label fxOverviewLastExerciseLabel;
	@FXML private LineChart<Date, Double> fxOverviewBMIChart;
	@FXML private DateAxis fxOverviewBMIChartX;
	@FXML private NumberAxis fxOverviewBMIChartY;
	@FXML private LineChart<Date, Double> fxOverviewTrainingChart;
	@FXML private DateAxis fxOverviewTrainingChartX;
	@FXML private NumberAxis fxOverviewTrainingChartY;
	@FXML private TextField fxBMIHeightBox;
	@FXML private TextField fxBMIWeightBox;
	@FXML private Label fxBMIHeightUnitLabel;
	@FXML private Label fxBMIWeightUnitLabel;
	@FXML private TextField fxBMIGoalBMIBox;
	@FXML private TextField fxBMIGoalWeightBox;
	@FXML private LineChart<Date, Double> fxBMIChart;
	@FXML private DateAxis fxBMIChartX;
	@FXML private NumberAxis fxBMIChartY;
	@FXML private ComboBox<String> fxBMIGraphTypeCombo;
	@FXML private ComboBox<String> fxBMIGraphResolutionCombo;
	@FXML private TableView<BMIRecord> fxBMITable;
	@FXML private TableColumn<BMIRecord, String> fxBMITableDate;
	@FXML private TableColumn<BMIRecord, String> fxBMITableTime;
	@FXML private TableColumn<BMIRecord, Number> fxBMITableHeight;
	@FXML private TableColumn<BMIRecord, Number> fxBMITableWeight;
	@FXML private TableColumn<BMIRecord, Number> fxBMITableBMI;
	@FXML private ComboBox<Exercise> fxTrainingActivitiesCombo;
	@FXML private TextField fxTrainingTimeBox;
	@FXML private TextField fxTrainingExerciseNameBox;
	@FXML private TextField fxTrainingExerciseBurnRateBox;
	@FXML private PieChart fxTrainingPieChart;
	@FXML private LineChart<Date, Double> fxTrainingXYChart;
	@FXML private DateAxis fxTrainingXYChartX;
	@FXML private NumberAxis fxTrainingXYChartY;
	@FXML private ComboBox<String> fxTrainingGraphTypeCombo;
	@FXML private ComboBox<String> fxTrainingGraphResolutionCombo;
	@FXML private TableView<TrainingRecord> fxTrainingTable;
	@FXML private TableColumn<TrainingRecord, String> fxTrainingTableDate;
	@FXML private TableColumn<TrainingRecord, String> fxTrainingTableTime;
	@FXML private TableColumn<TrainingRecord, String> fxTrainingTableExercise;
	@FXML private TableColumn<TrainingRecord, Number> fxTrainingTableDuration;
	@FXML private TableColumn<TrainingRecord, Number> fxTrainingTableCalBurnt;
	@FXML private ComboBox<Food> fxDietMealCombo;
	@FXML private TextField fxDietMealNameBox;
	@FXML private TextField fxDietCaloriesBox;
	@FXML private TextField fxDietFatBox;
	@FXML private TextField fxDietSaltBox;
	@FXML private LineChart<Date, Double> fxDietChart;
	@FXML private DateAxis fxDietChartX;
	@FXML private NumberAxis fxDietChartY;
	@FXML private ComboBox<String> fxDietGraphTypeCombo;
	@FXML private ComboBox<String> fxDietGraphResolutionCombo;
	@FXML private TableView<DietRecord> fxDietTable;
	@FXML private TableColumn<DietRecord, String> fxDietTableDate;
	@FXML private TableColumn<DietRecord, String> fxDietTableTime;
	@FXML private TableColumn<DietRecord, String> fxDietTableFoodName;
	@FXML private TableColumn<DietRecord, Number> fxDietTableCalories;
	@FXML private TableColumn<DietRecord, Number> fxDietTableFat;
	@FXML private TableColumn<DietRecord, Number> fxDietTableSalt;
	@FXML private ScrollPane fxPicturesPane;
	@FXML private TilePane fxPicturesTilePane;
	@FXML private TextField fxPicturesFileBox;
	@FXML private TextField fxPicturesURLBox;
	@FXML private TextField fxLinksNameBox;
	@FXML private TextField fxLinksURLBox;
	@FXML private TableView<LinkRecord> fxLinksTable;
	@FXML private TableColumn<LinkRecord, String> fxLinksTableName;
	@FXML private TableColumn<LinkRecord, String> fxLinksTableURL;

	/* System messages */
	private static final String ERROR_SAVING_PROFILE = "Error while saving profile.";
	private static final String ERROR_INVALID_HEIGHT_WEIGHT_VALUES = "Invalid height or weight value.";
	private static final String ERROR_INVALID_BMI_WEIGHT_VALUES = "Invalid BMI or weight value.";
	private static final String ERROR_TIME_LESS_THAN_ZERO = "Please enter a time greater than zero.";
	private static final String ERROR_INVALID_TRAINING_TIME_VALUE = "Invalid training time value.";
	private static final String ERROR_INVALID_NAME = "Please enter a name containing only alphanumeric characters and spaces.";
	private static final String ERROR_BURN_RATE_LESS_THAN_ZERO = "Please enter a burn rate greater than zero.";
	private static final String ERROR_BURN_RATE_INVALID = "Invalid burn rate value.";
	private static final String ERROR_CALORIES_LESS_THAN_ZERO = "Please enter a value for calories greater than zero.";
	private static final String ERROR_FAT_LESS_THAN_ZERO = "Please enter a fat content value greater than zero.";
	private static final String ERROR_SALT_LESS_THAN_ZERO = "Please enter a salt content value greater than zero.";
	private static final String ERROR_INVALID_URL = "Invalid URL entered";
	private static final String ERROR_FXML_NOT_FOUND = "Unable to find FXML file.";
	private static final String ERROR_NO_FILE = "No file was specified";

	/* Main form members */
	private Main view;
	private Profile profile;

	/**
	 * Initialises the main form by loading profile data into the form controls.
	 * This method is called automatically when the Main form controller
	 * instance is created.
	 */
	public void initialise() {
		loadProfileData();
		configureCallbacks();
		setChartVisibility(null);
	}

	/**
	 * Sets the visibility of certain elements based on the user's 'table as default' preference.
	 * When the user changes their preference, all charts should have their visibility set based on
	 * the new selection.
	 * 
	 * @param event the action event object from the view
	 */
	@FXML
	protected void setChartVisibility(ActionEvent event) {
		if (event != null) {
			try {
				profile.setTableDefault(fxMenuViewTableAsDefault.isSelected());
				profile.saveProfile();
			} catch (TransformerException e) {
				JOptionPane.showMessageDialog(null, ERROR_SAVING_PROFILE);
			}
		}

		/* Configure the tabs according to the user's preference */
		if (profile.getTableDefault()) {
			fxBMITable.setVisible(true);
			fxTrainingTable.setVisible(true);
			fxDietTable.setVisible(true);
			fxBMIChart.setVisible(false);
			fxTrainingXYChart.setVisible(false);
			fxTrainingPieChart.setVisible(false);
			fxDietChart.setVisible(false);
		} else {
			fxBMITable.setVisible(false);
			fxTrainingTable.setVisible(false);
			fxDietTable.setVisible(false);
			fxBMIChart.setVisible(true);
			fxTrainingXYChart.setVisible(true);
			fxTrainingPieChart.setVisible(false);
			fxDietChart.setVisible(true);
		}
	}

	/**
	 * Loads data from the specified profile into the Main form controls. All
	 * tabs are initialised during the execution of this function.
	 */
	private void loadProfileData() {
		/* Load individual tab content */
		loadOverviewTab(false);
		loadBMITab(false);
		loadTrainingTab();
		loadDietTab();
		loadPicturesTab(false);
		loadLinksTab();

		/* Menu configuration */
		fxMenuViewTableAsDefault.setSelected(profile.getTableDefault());
	}

	/**
	 * Loads content from the user's profile into the appropriate fields in the Overview tab. Some
	 * fields are less frequently updated so a minimal flag is available to speed up operations.
	 * 
	 * @param minimal true to perform a minimal tab update
	 */
	private void loadOverviewTab(boolean minimal) {
		BMIRecord bmiRecord = profile.getBMIRecord();
		DietRecord dietRecord = profile.getDietRecord();
		TrainingRecord exerciseRecord = profile.getTrainingRecord();

		/* Check if the user has any records */
		String bmiLabel = bmiRecord == null ? "N/A" : new DecimalFormat("#.##").format(bmiRecord.getBMI());
		String mealLabel = dietRecord == null ? "N/A" : dietRecord.getMeal().getName();
		String exerciseLabel = exerciseRecord == null ? "N/A" : exerciseRecord.getRoutine().getName();

		/* Latest activity statistics */
		fxOverviewLastBMILabel.setText(bmiLabel);
		fxOverviewLastMealLabel.setText(mealLabel);
		fxOverviewLastExerciseLabel.setText(exerciseLabel);
		fxOverviewBMIChartX.setLabel("Date");
		fxOverviewBMIChartY.setLabel("BMI value");
		BMILoadData(fxOverviewBMIChart, false, "BMI", "Month");

		fxOverviewTrainingChartX.setLabel("Date");
		fxOverviewTrainingChartY.setLabel("Calories Burned (Cal)");
		TrainingLoadXYData(fxOverviewTrainingChart, "Month");

		/* Exit early */
		if (minimal)
			return;

		/* Static label initialisation */
		fxOverviewNameLabel.setText(profile.getUser().getForename() + " " + profile.getUser().getSurname());
		fxOverviewUsernameLabel.setText(profile.getUsername());
		fxOverviewAgeLabel.setText(String.valueOf(profile.getUser().getAge()));
		fxOverviewDOBLabel.setText(new SimpleDateFormat("dd/MM/yyyy").format(profile.getUser().getDOB()));
		fxOverviewBMIChartY.setForceZeroInRange(false);
	}

	/**
	 * Loads content from the user's profile into the appropriate fields in the BMI tab. Some
	 * fields are less frequently updated so a minimal flag is available to speed up operations.
	 * 
	 * @param minimal true to perform a minimal tab update
	 */
	private void loadBMITab(boolean minimal) {
		/* Unit configuration */
		fxBMIHeightUnitLabel.setText(profile.getHeightUnit() == Profile.HeightMeasure.METRES ? "m" : "ft");
		fxBMIWeightUnitLabel.setText(profile.getWeightUnit() == Profile.WeightMeasure.KILOGRAMS ? "kg" : "lbs");

		/* Exit early */
		if (minimal)
			return;

		String height = profile.getBMIRecord() == null ? "" : String.valueOf(profile.getBMIRecord().getHeight());

		/* General tab configuration */
		fxBMIGoalBMIBox.setText(String.valueOf(profile.getBMIGoal()));
		fxBMIGoalWeightBox.setText(String.valueOf(profile.getWeightGoal()));
		fxBMIHeightBox.setText(height);
		fxBMIChartY.setForceZeroInRange(false);
		fxBMITable.setEditable(true);

		/* Table column width binding */
		fxBMITableDate.prefWidthProperty().bind(fxBMITable.widthProperty().multiply(0.19));
		fxBMITableTime.prefWidthProperty().bind(fxBMITable.widthProperty().multiply(0.12));
		fxBMITableHeight.prefWidthProperty().bind(fxBMITable.widthProperty().multiply(0.2));
		fxBMITableWeight.prefWidthProperty().bind(fxBMITable.widthProperty().multiply(0.2));
		fxBMITableBMI.prefWidthProperty().bind(fxBMITable.widthProperty().multiply(0.26));

		/* Load Data */
		fxBMITable.setItems(profile.getBMIRecords());
		BMILoadData(fxBMIChart, true, fxBMIGraphTypeCombo.getValue(), fxBMIGraphResolutionCombo.getValue());
	}

	/**
	 * Loads content from the user's profile into the appropriate fields in the Training tab. Two
	 * charts are initialised in this function to enable more streamlined switching.
	 */
	private void loadTrainingTab() {
		/* ComboBox configuration */
		fxTrainingActivitiesCombo.setItems(profile.getExerciseRecords());
		fxTrainingActivitiesCombo.setValue(profile.getExerciseRoutine());
		fxTrainingActivitiesCombo.setButtonCell(new ExerciseComboBoxItem());

		/* Table column width binding */
		fxTrainingTableDate.prefWidthProperty().bind(fxTrainingTable.widthProperty().multiply(0.19));
		fxTrainingTableTime.prefWidthProperty().bind(fxTrainingTable.widthProperty().multiply(0.12));
		fxTrainingTableExercise.prefWidthProperty().bind(fxTrainingTable.widthProperty().multiply(0.2));
		fxTrainingTableDuration.prefWidthProperty().bind(fxTrainingTable.widthProperty().multiply(0.2));
		fxTrainingTableCalBurnt.prefWidthProperty().bind(fxTrainingTable.widthProperty().multiply(0.26));

		/* Load Data */
		fxTrainingTable.setItems(profile.getTrainingRecords());
		TrainingLoadXYData(fxTrainingXYChart, fxTrainingGraphResolutionCombo.getValue());
		TrainingLoadPieData(fxTrainingPieChart, fxTrainingGraphResolutionCombo.getValue());
	}

	/**
	 * Loads content from the user's profile into the appropriate fields in the Diet tab. Callback
	 * configuration takes place earlier in the initialisation process.
	 */
	private void loadDietTab() {
		/* ComboBox configuration */
		fxDietMealCombo.setItems(profile.getMealRecords());
		fxDietMealCombo.setValue(profile.getMeal());
		fxDietMealCombo.setButtonCell(new MealComboBoxItem());

		/* Table column width binding */
		fxDietTableDate.prefWidthProperty().bind(fxDietTable.widthProperty().multiply(0.19));
		fxDietTableTime.prefWidthProperty().bind(fxDietTable.widthProperty().multiply(0.13));
		fxDietTableFoodName.prefWidthProperty().bind(fxDietTable.widthProperty().multiply(0.16));
		fxDietTableCalories.prefWidthProperty().bind(fxDietTable.widthProperty().multiply(0.16));
		fxDietTableFat.prefWidthProperty().bind(fxDietTable.widthProperty().multiply(0.16));
		fxDietTableSalt.prefWidthProperty().bind(fxDietTable.widthProperty().multiply(0.16));

		/* Load Data */
		fxDietTable.setItems(profile.getDietRecords());
		DietLoadData(fxDietChart, fxDietGraphTypeCombo.getValue(), fxDietGraphResolutionCombo.getValue());
	}

	/**
	 * Loads content from the user's profile into the appropriate fields in the Links tab. This tab
	 * only offers a table and empty fields so configuration is minimal.
	 */
	private void loadLinksTab() {
		fxLinksTable.setItems(profile.getLinkRecords());
	}

	/**
	 * Loads content from the user's profile into the appropriate fields in the Pictures tab. This
	 * function can also be used to reload pictures after the user has inserted a new picture
	 * record.
	 * 
	 * @param minimal true to perform a minimal tab update
	 */
	private void loadPicturesTab(boolean minimal) {
		/* Clear any previous content */
		fxPicturesTilePane.getChildren().clear();

		if (!minimal) {
			/* Initial configuration */
			fxPicturesTilePane.setPadding(new Insets(5, 5, 5, 5));
			fxPicturesPane.setContent(fxPicturesTilePane);
			fxPicturesPane.setFitToHeight(true);
			fxPicturesPane.setFitToWidth(true);
		}

		/* Load images */
		for (PictureRecord picture : profile.getPictureRecords()) {
			Image image = new Image(picture.getPath());
			ImageView iView = new ImageView(image);
			iView.setFitWidth(250);
			iView.setSmooth(true);
			iView.setPreserveRatio(true);
			fxPicturesTilePane.getChildren().add(iView);
		}
	}

	/**
	 * Loads BMI data into a specified LineChart. Goals can be shown as well as options to customise
	 * graph type and resolution.
	 * 
	 * @param chart the LineChart object to push BMI data to
	 * @param displayGoal true if the user's current BMI/Weight goal should be shown on the graph
	 * @param type the type of the graph to display
	 * @param res the resolution of the graph to display
	 */
	private void BMILoadData(LineChart<Date, Double> chart, boolean displayGoal, String type, String res) {
		long timeThreshold;
		Date end = new Date();
		Date start = new Date();
		XYChart.Series<Date, Double> graphData = new XYChart.Series<Date, Double>();
		XYChart.Series<Date, Double> bmiGoal = new XYChart.Series<Date, Double>();
		XYChart.Series<Date, Double> weightGoal = new XYChart.Series<Date, Double>();

		/* Date region calculation */
		chart.getData().clear();
		timeThreshold = getChartResolutionTimeThreshold(res);
		start.setTime(start.getTime() - timeThreshold);

		/* Load BMI records into the graph */
		for (BMIRecord a : profile.getBMIRecords()) {
			if (end.getTime() - a.getDateTime().getTime() < timeThreshold) {
				switch (type) {
				case "BMI":
					graphData.getData().add(new XYChart.Data<Date, Double>(a.getDateTime(), a.getBMI()));
					break;
				case "Weight":
					graphData.getData().add(new XYChart.Data<Date, Double>(a.getDateTime(), a.getWeight()));
					break;
				case "Height":
					graphData.getData().add(new XYChart.Data<Date, Double>(a.getDateTime(), a.getHeight()));
					break;
				}
			}
		}

		/* Add the data to the chart */
		fxBMIChartX.setLabel("Date");
		chart.getData().add(graphData);

		/* Span the BMI goal across the valid region */
		if (displayGoal) {
			switch (type) {
			case "BMI":
				fxBMIChartY.setLabel("BMI value");
				bmiGoal.getData().add(new XYChart.Data<Date, Double>(start, profile.getBMIGoal()));
				bmiGoal.getData().add(new XYChart.Data<Date, Double>(end, profile.getBMIGoal()));
				chart.getData().add(bmiGoal);
				break;
			case "Weight":
				fxBMIChartY.setLabel("Weight (" + (profile.getWeightUnit() == Profile.WeightMeasure.KILOGRAMS ? "kg" : "lbs") + ")");
				weightGoal.getData().add(new XYChart.Data<Date, Double>(start, profile.getWeightGoal()));
				weightGoal.getData().add(new XYChart.Data<Date, Double>(end, profile.getWeightGoal()));
				chart.getData().add(weightGoal);
				break;
			case "Height":
				fxBMIChartY.setLabel("Height (" + (profile.getHeightUnit() == Profile.HeightMeasure.METRES ? "m" : "ft") + ")");
				break;
			}
		}
	}

	/**
	 * Loads Training XY data into a specified LineChart. options are available to customise
	 * graph resolution.
	 * 
	 * @param chart chart the LineChart object to push Training XY data to
	 * @param res the resolution of the graph to display
	 */
	private void TrainingLoadXYData(LineChart<Date, Double> chart, String res) {
		long timeThreshold;
		Date end = new Date();
		Date start = new Date();
		XYChart.Series<Date, Double> graphData = new XYChart.Series<Date, Double>();
		LinkedHashMap<String, Double> calBurn = new LinkedHashMap<String, Double>();

		/* Date region calculation */
		chart.getData().clear();
		timeThreshold = getChartResolutionTimeThreshold(res);
		start.setTime(start.getTime() - timeThreshold);

		/* Group training activity burn by day */
		for (TrainingRecord record : profile.getTrainingRecords()) {
			if (end.getTime() - record.getDateTime().getTime() < timeThreshold) {
				String date = record.getDateString();
				double prevCalsBurnt = calBurn.containsKey(date) ? calBurn.get(date) : 0.0;
				double calsBurnt = record.getRoutine().getBurnRate() * ((double) record.getTime() / 60.0);
				calBurn.put(date, prevCalsBurnt + calsBurnt);
			}
		}

		/* Add the calories burned to the graph */
		for (Entry<String, Double> i : calBurn.entrySet()) {
			try {
				String dateStr = i.getKey();
				double calsBurnt = i.getValue();
				Date date = new SimpleDateFormat("yyyy-MM-dd").parse(dateStr);
				graphData.getData().add(new XYChart.Data<Date, Double>(date, calsBurnt));
			} catch (ParseException e) {
				continue;
			}
		}

		/* Add the data to the chart */
		fxTrainingXYChartY.setLabel("Calories Burnt (Cal)");
		fxTrainingXYChartX.setLabel("Date");
		chart.getData().add(graphData);
	}

	/**
	 * Loads Training Pie Chart data into a specified PieChart. options are available to customise
	 * graph resolution.
	 * 
	 * @param chart chart the PieChart object to push Training PieChart data to
	 * @param res the resolution of the graph to display
	 */
	private void TrainingLoadPieData(PieChart chart, String res) {
		long timeThreshold;
		Date end = new Date();
		Date start = new Date();
		ObservableList<PieChart.Data> chartData = FXCollections.observableArrayList();
		LinkedHashMap<String, Integer> exercises = new LinkedHashMap<String, Integer>();

		/* Date region calculation */
		chart.getData().clear();
		timeThreshold = getChartResolutionTimeThreshold(res);
		start.setTime(start.getTime() - timeThreshold);

		/* Group by training activity, total time within the resolution */
		for (TrainingRecord a : profile.getTrainingRecords()) {
			if (end.getTime() - a.getDateTime().getTime() < timeThreshold) {
				String key = a.getRoutine().getName();

				if (exercises.containsKey(key)) {
					exercises.put(key, exercises.get(key).intValue() + a.getTime());
				} else {
					exercises.put(key, a.getTime());
				}
			}
		}

		/* Add the training items */
		for (Entry<String, Integer> e : exercises.entrySet()) {
			chartData.add(new PieChart.Data(e.getKey(), e.getValue()));
		}

		/* Add the data to the chart */
		chart.setData(chartData);
	}

	/**
	 * Loads Diet Pie Chart data into a specified LineChart. options are available to customise
	 * graph resolution and type.
	 * 
	 * @param chart chart the LineChart object to push Diet LineChart data to
	 * @param type the type of the graph to display
	 * @param res the resolution of the graph to display
	 */
	private void DietLoadData(LineChart<Date, Double> chart, String type, String res) {
		long timeThreshold;
		Date end = new Date();
		Date start = new Date();
		XYChart.Series<Date, Double> graphData = new XYChart.Series<Date, Double>();
		LinkedHashMap<String, Double> mappedData = new LinkedHashMap<String, Double>();

		/* Date region calculation */
		chart.getData().clear();
		timeThreshold = getChartResolutionTimeThreshold(res);
		start.setTime(start.getTime() - timeThreshold);

		/* Group diet records by day */
		for (DietRecord a : profile.getDietRecords()) {
			if (end.getTime() - a.getDateTime().getTime() < timeThreshold) {
				String date = a.getDateString();
				double prevData = mappedData.containsKey(date) ? mappedData.get(date) : 0.0;
				double data = 0.0;

				switch (type) {
				case "Calories Consumed":
					fxDietChartY.setLabel("Calories Consumed (Cal)");
					data = (double) a.getMeal().getCalories();
					break;
				case "Fat Consumed":
					fxDietChartY.setLabel("Fat Consumed (g)");
					data = (double) a.getMeal().getFatContent();
					break;
				case "Salt Consumed":
					fxDietChartY.setLabel("Salt Consumed (g)");
					data = (double) a.getMeal().getSaltContent();
					break;
				}

				mappedData.put(date, prevData + data);
			}
		}

		/* Add the diet items to the graph */
		for (Entry<String, Double> i : mappedData.entrySet()) {
			try {
				String dateStr = i.getKey();
				double data = i.getValue();
				Date date = new SimpleDateFormat("yyyy-MM-dd").parse(dateStr);
				graphData.getData().add(new XYChart.Data<Date, Double>(date, data));
			} catch (ParseException e) {
				continue;
			}
		}

		/* Add the data to the chart */
		fxDietChartX.setLabel("Date");
		chart.getData().add(graphData);
	}

	/*
	 * Overview Tab
	 */
	@FXML
	protected void overviewButtonResetClick(ActionEvent event) {
		try {
			view.displayResetPassword(profile);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, ERROR_FXML_NOT_FOUND);
		}
	}

	/*
	 * BMI Tab
	 */
	@FXML
	protected void BMIAddRecordClick(ActionEvent event) {
		try {
			/* Parse the user's input & save the record */
			double height = Double.parseDouble(fxBMIHeightBox.getText());
			double weight = Double.parseDouble(fxBMIWeightBox.getText());
			BMIRecord b = new BMIRecord(height, weight, profile.getHeightUnit(), profile.getWeightUnit(), new Date());
			profile.addBMIRecord(b);
			profile.saveProfile();

			/* Update the view */
			loadOverviewTab(true);
			BMILoadData(fxBMIChart, true, fxBMIGraphTypeCombo.getValue(), fxBMIGraphResolutionCombo.getValue());
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(null, ERROR_INVALID_HEIGHT_WEIGHT_VALUES);
		} catch (TransformerException e) {
			JOptionPane.showMessageDialog(null, ERROR_SAVING_PROFILE);
		}
	}

	@FXML
	protected void BMIUpdateGoalsClick(ActionEvent event) {
		/* Get the user's goals */
		String bmiStr = fxBMIGoalBMIBox.getText();
		String weightStr = fxBMIGoalWeightBox.getText();

		/* Assume an empty box as no goal */
		if (bmiStr.equals("")) {
			bmiStr = "0";
		}
		if (weightStr.equals("")) {
			weightStr = "0";
		}

		try {
			/* Update the user's profile */
			profile.setBMIGoal(Double.parseDouble(bmiStr));
			profile.setWeightGoal(Double.parseDouble(weightStr));
			profile.saveProfile();
			BMILoadData(fxBMIChart, true, fxBMIGraphTypeCombo.getValue(), fxBMIGraphResolutionCombo.getValue());
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(null, ERROR_INVALID_BMI_WEIGHT_VALUES);
		} catch (TransformerException e) {
			JOptionPane.showMessageDialog(null, ERROR_SAVING_PROFILE);
		}
	}

	@FXML
	protected void BMIGraphUpdateAction(ActionEvent event) {
		BMILoadData(fxBMIChart, true, fxBMIGraphTypeCombo.getValue(), fxBMIGraphResolutionCombo.getValue());
	}

	@FXML
	protected void BMITableButtonToggle(ActionEvent event) {
		if (fxBMITable.visibleProperty().getValue()) {
			fxBMIChart.setVisible(true);
			fxBMITable.setVisible(false);
			fxBMIGraphTypeCombo.setDisable(false);
			fxBMIGraphResolutionCombo.setDisable(false);
		} else {
			fxBMIChart.setVisible(false);
			fxBMITable.setVisible(true);
			fxBMIGraphTypeCombo.setDisable(true);
			fxBMIGraphResolutionCombo.setDisable(true);
		}
	}

	/*
	 * Training Tab
	 */
	@FXML
	protected void TrainingAddRecordClick(ActionEvent event) {
		try {
			/* Get the user's input */
			int time = Integer.parseInt(fxTrainingTimeBox.getText());

			/* Validate the input */
			if (time <= 0) {
				throw new Exception(ERROR_TIME_LESS_THAN_ZERO);
			}

			/* Update the user's profile */
			profile.addTrainingRecord(new TrainingRecord(fxTrainingActivitiesCombo.getValue(), time, new Date()));
			profile.saveProfile();
			loadOverviewTab(true);
			TrainingLoadXYData(fxTrainingXYChart, fxTrainingGraphResolutionCombo.getValue());
			TrainingLoadPieData(fxTrainingPieChart, fxTrainingGraphResolutionCombo.getValue());
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(null, ERROR_INVALID_TRAINING_TIME_VALUE);
		} catch (TransformerException e) {
			JOptionPane.showMessageDialog(null, ERROR_SAVING_PROFILE);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		} finally {
			fxTrainingTimeBox.setText("");
		}
	}

	@FXML
	protected void TrainingAddExerciseClick(ActionEvent event) {
		try {
			/* Get the user's input */
			String name = fxTrainingExerciseNameBox.getText();
			double burnRate = Double.parseDouble(fxTrainingExerciseBurnRateBox.getText());
			Pattern regex = Pattern.compile("[^a-zA-Z0-9 ]");

			/* Validate the exercise name */
			if (regex.matcher(name).find()) {
				throw new Exception(ERROR_INVALID_NAME);
			} else if (burnRate <= 0) {
				throw new Exception(ERROR_BURN_RATE_LESS_THAN_ZERO);
			}

			/* Update the user's profile */
			profile.addExerciseRoutine(new Exercise(name, burnRate));
			profile.saveProfile();
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(null, ERROR_BURN_RATE_INVALID);
		} catch (TransformerException e) {
			JOptionPane.showMessageDialog(null, ERROR_SAVING_PROFILE);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		} finally {
			fxTrainingExerciseNameBox.setText("");
			fxTrainingExerciseBurnRateBox.setText("");
		}
	}

	@FXML
	protected void TrainingGraphUpdateTypeAction(ActionEvent event) {
		if (fxTrainingGraphTypeCombo.getValue().equals("Calories Burned")) {
			fxTrainingXYChart.setVisible(true);
			fxTrainingPieChart.setVisible(false);
		} else {
			fxTrainingXYChart.setVisible(false);
			fxTrainingPieChart.setVisible(true);
		}
	}

	@FXML
	protected void TrainingGraphUpdateResolutionAction(ActionEvent event) {
		TrainingLoadXYData(fxTrainingXYChart, fxTrainingGraphResolutionCombo.getValue());
		TrainingLoadPieData(fxTrainingPieChart, fxTrainingGraphResolutionCombo.getValue());
	}

	@FXML
	protected void TrainingTableButtonToggle(ActionEvent event) {
		if (fxTrainingTable.visibleProperty().getValue()) {
			fxTrainingTable.setVisible(false);
			fxTrainingGraphTypeCombo.setDisable(false);
			fxTrainingGraphResolutionCombo.setDisable(false);

			if (fxTrainingGraphTypeCombo.getValue().equals("Calories Burned")) {
				fxTrainingXYChart.setVisible(true);
				fxTrainingPieChart.setVisible(false);
			} else {
				fxTrainingXYChart.setVisible(false);
				fxTrainingPieChart.setVisible(true);
			}
		} else {
			fxTrainingTable.setVisible(true);
			fxTrainingGraphTypeCombo.setDisable(true);
			fxTrainingGraphResolutionCombo.setDisable(true);
			fxTrainingXYChart.setVisible(false);
			fxTrainingPieChart.setVisible(false);
		}
	}

	/*
	 * Diet Tab
	 */
	@FXML
	protected void DietAddRecordClick(ActionEvent event) {
		try {
			/* Update the user's profile */
			profile.addDietRecord(new DietRecord(fxDietMealCombo.getValue(), new Date()));
			profile.saveProfile();
			loadOverviewTab(true);
			DietLoadData(fxDietChart, fxDietGraphTypeCombo.getValue(), fxDietGraphResolutionCombo.getValue());
		} catch (TransformerException e) {
			JOptionPane.showMessageDialog(null, ERROR_SAVING_PROFILE);
		}
	}

	@FXML
	protected void DietAddMealClick(ActionEvent event) {
		try {
			/* Get the user's input */
			String name = fxDietMealNameBox.getText();
			int calories = Integer.parseInt(fxDietCaloriesBox.getText());
			double fat = Double.parseDouble(fxDietFatBox.getText());
			double salt = Double.parseDouble(fxDietSaltBox.getText());
			Pattern regex = Pattern.compile("[^a-zA-Z0-9 ]");

			/* Validate the exercise name */
			if (regex.matcher(name).find()) {
				throw new Exception(ERROR_INVALID_NAME);
			} else if (calories <= 0) {
				throw new Exception(ERROR_CALORIES_LESS_THAN_ZERO);
			} else if (fat <= 0) {
				throw new Exception(ERROR_FAT_LESS_THAN_ZERO);
			} else if (salt <= 0) {
				throw new Exception(ERROR_SALT_LESS_THAN_ZERO);
			}

			/* Update the user's profile */
			profile.addMeal(new Food(name, calories, fat, salt));
			profile.saveProfile();
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(null, ERROR_INVALID_TRAINING_TIME_VALUE);
		} catch (TransformerException e) {
			JOptionPane.showMessageDialog(null, ERROR_SAVING_PROFILE);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		} finally {
			fxDietMealNameBox.setText("");
			fxDietCaloriesBox.setText("");
			fxDietFatBox.setText("");
			fxDietSaltBox.setText("");
		}
	}

	@FXML
	protected void DietGraphUpdateAction(ActionEvent event) {
		DietLoadData(fxDietChart, fxDietGraphTypeCombo.getValue(), fxDietGraphResolutionCombo.getValue());
	}

	@FXML
	protected void DietTableButtonToggle(ActionEvent event) {
		if (fxDietTable.visibleProperty().getValue()) {
			fxDietChart.setVisible(true);
			fxDietTable.setVisible(false);
			fxDietGraphTypeCombo.setDisable(false);
			fxDietGraphResolutionCombo.setDisable(false);
		} else {
			fxDietChart.setVisible(false);
			fxDietTable.setVisible(true);
			fxDietGraphTypeCombo.setDisable(true);
			fxDietGraphResolutionCombo.setDisable(true);
		}
	}

	/*
	 * Pictures Tab
	 */
	@FXML
	protected void PicturesFileBrowseClick(ActionEvent event) {
		/* FileChooser configuration */
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Select a picture");
		fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("All Images", "*.jpg", "*.png"),
				new FileChooser.ExtensionFilter("JPG", "*.jpg"), new FileChooser.ExtensionFilter("PNG", "*.png"));

		/* Get the file selection from the user */
		File f = fileChooser.showOpenDialog(view.getStage());
		if (f == null) {
			fxPicturesFileBox.setText("");
		} else {
			fxPicturesFileBox.setText(f.getAbsolutePath());
		}
	}

	@FXML
	protected void PicturesAddPictureFileClick(ActionEvent event) {
		if (fxPicturesFileBox.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, ERROR_NO_FILE);
			return;
		}

		try {
			/* Update the user's profile */
			profile.addPictureRecord(new PictureRecord("file:" + fxPicturesFileBox.getText(), new Date()));
			profile.saveProfile();
			loadPicturesTab(true);
		} catch (TransformerException e) {
			JOptionPane.showMessageDialog(null, ERROR_SAVING_PROFILE);
		}
	}

	@FXML
	protected void PicturesAddPictureURLClick(ActionEvent event) {
		try {
			Pattern p = Pattern
					.compile("^((([A-Za-z]{3,9}:(?:\\/\\/)?)(?:[-;:&=\\+\\$,\\w]+@)?[A-Za-z0-9.-]+|(?:www.|[-;:&=\\+\\$,\\w]+@)[A-Za-z0-9.-]+)((?:\\/[\\+~%\\/.\\w-_]*)?\\??(?:[-\\+=&;%@.\\w_]*)#?(?:[\\w]*))?)$");
			Matcher m = p.matcher(fxLinksURLBox.getText());

			if (fxPicturesURLBox.getText().isEmpty() || !m.find()) {
				throw new TransformerException(ERROR_INVALID_URL);
			}

			/* Update the user's profile */
			profile.addPictureRecord(new PictureRecord(fxPicturesURLBox.getText(), new Date()));
			profile.saveProfile();
			loadPicturesTab(true);
		} catch (TransformerException e) {
			JOptionPane.showMessageDialog(null, ERROR_SAVING_PROFILE);
		}
	}

	@FXML
	protected void PicturesRemovePictureClick(ActionEvent event) {
		// TODO: Remove selected Picture
	}

	/*
	 * Links Tab
	 */
	@FXML
	protected void LinksAddURLClick(ActionEvent event) {
		try {
			Pattern p = Pattern
					.compile("^((([A-Za-z]{3,9}:(?:\\/\\/)?)(?:[-;:&=\\+\\$,\\w]+@)?[A-Za-z0-9.-]+|(?:www.|[-;:&=\\+\\$,\\w]+@)[A-Za-z0-9.-]+)((?:\\/[\\+~%\\/.\\w-_]*)?\\??(?:[-\\+=&;%@.\\w_]*)#?(?:[\\w]*))?)$");
			Matcher m = p.matcher(fxLinksURLBox.getText());

			if (!m.find()) {
				throw new Exception(ERROR_INVALID_URL);
			}

			profile.addLinkRecord(new LinkRecord(fxLinksNameBox.getText(), fxLinksURLBox.getText(), new Date()));
			profile.saveProfile();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
	}

	@FXML
	protected void LinksRemoveURLClick(ActionEvent event) {
		// TODO: Remove selected URL
	}

	/*
	 * Menu Bar
	 */
	@FXML
	protected void menuFileNewUserClick(ActionEvent event) {
		try {
			view.displayNewUser();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, ERROR_FXML_NOT_FOUND);
		}
	}

	@FXML
	protected void menuFileSwitchUserClick(ActionEvent event) {
		try {
			view.displaySwitchUser();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, ERROR_FXML_NOT_FOUND);
		}
	}

	@FXML
	protected void menuFileCloseClick(ActionEvent event) {
		try {
			profile.saveProfile();
			view.close();
		} catch (TransformerException e) {
			JOptionPane.showMessageDialog(null, ERROR_SAVING_PROFILE);
		}
	}

	@FXML
	protected void menuEditPreferencesClick(ActionEvent event) {
		try {
			view.displayPreferences(profile);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, ERROR_FXML_NOT_FOUND);
		}
		
		loadProfileData();
	}

	@FXML
	protected void menuHelpDocumentationClick(ActionEvent event) {
		JOptionPane.showMessageDialog(null, "No documentation is available for this program.");
	}

	@FXML
	protected void menuHelpAboutClick(ActionEvent event) {
		JOptionPane.showMessageDialog(null, "Personal Training Advisor v1.0 - Steven Frost 2013");
	}

	/**
	 * Sets the view associated with this controller
	 * 
	 * @param view the instance of the main view class
	 */
	public void setView(Main view) {
		this.view = view;
		this.profile = view.getProfile();
	}

	/**
	 * Gets the number of milliseconds in a Day, Week, Month and Year. The parameter switches
	 * between these values.
	 * 
	 * @param time Day, Week, Month or Year
	 * @return the number of milliseconds in the specified time scale. Zero if an incorrect argument
	 *         was provided.
	 */
	private long getChartResolutionTimeThreshold(String time) {
		switch (time) {
		case "Day":
			return 86400000;
		case "Week":
			return 604800000;
		case "Month":
			return 2629740000L;
		case "Year":
			return 31556900000L;
		}
		return 0L;
	}

	/**
	 * Internal implementation of a specialised ComboBox Item for the Training tab
	 */
	private class ExerciseComboBoxItem extends ListCell<Exercise> {
		@Override
		protected void updateItem(Exercise e, boolean b) {
			super.updateItem(e, b);
			if (e != null) {
				setText(e.getName());
			}
		}
	}

	/**
	 * Internal implementation of a specialised ComboBox Item for the Meal tab
	 */
	private class MealComboBoxItem extends ListCell<Food> {
		@Override
		protected void updateItem(Food e, boolean b) {
			super.updateItem(e, b);
			if (e != null) {
				setText(e.getName());
			}
		}
	}

	/**
	 * Configures callbacks for various tables and ComboBoxes in the application
	 */
	private void configureCallbacks() {
		/* BMI tab callback configuration */
		fxBMITableDate.setCellValueFactory(new Callback<CellDataFeatures<BMIRecord, String>, ObservableValue<String>>() {
			public ObservableValue<String> call(CellDataFeatures<BMIRecord, String> p) {
				return new SimpleStringProperty(p.getValue().getDateString());
			}
		});
		fxBMITableTime.setCellValueFactory(new Callback<CellDataFeatures<BMIRecord, String>, ObservableValue<String>>() {
			public ObservableValue<String> call(CellDataFeatures<BMIRecord, String> p) {
				return new SimpleStringProperty(p.getValue().getTimeString());
			}
		});
		fxBMITableHeight.setCellValueFactory(new Callback<CellDataFeatures<BMIRecord, Number>, ObservableValue<Number>>() {
			public ObservableValue<Number> call(CellDataFeatures<BMIRecord, Number> p) {
				return new SimpleDoubleProperty(p.getValue().getHeight());
			}
		});
		fxBMITableWeight.setCellValueFactory(new Callback<CellDataFeatures<BMIRecord, Number>, ObservableValue<Number>>() {
			public ObservableValue<Number> call(CellDataFeatures<BMIRecord, Number> p) {
				return new SimpleDoubleProperty(p.getValue().getWeight());
			}
		});
		fxBMITableBMI.setCellValueFactory(new Callback<CellDataFeatures<BMIRecord, Number>, ObservableValue<Number>>() {
			public ObservableValue<Number> call(CellDataFeatures<BMIRecord, Number> p) {
				return new SimpleDoubleProperty(p.getValue().getBMI());
			}
		});

		fxBMITableHeight.setCellFactory(TextFieldTableCell.<BMIRecord, Number> forTableColumn(new NumberStringConverter()));
		fxBMITableHeight.setOnEditCommit(new EventHandler<CellEditEvent<BMIRecord, Number>>() {
			@Override
			public void handle(CellEditEvent<BMIRecord, Number> t) {
				((BMIRecord) t.getTableView().getItems().get(t.getTablePosition().getRow())).setHeight(t.getNewValue().doubleValue());
			}
		});
		fxBMITableWeight.setCellFactory(TextFieldTableCell.<BMIRecord, Number> forTableColumn(new NumberStringConverter()));
		fxBMITableWeight.setOnEditCommit(new EventHandler<CellEditEvent<BMIRecord, Number>>() {
			@Override
			public void handle(CellEditEvent<BMIRecord, Number> t) {
				((BMIRecord) t.getTableView().getItems().get(t.getTablePosition().getRow())).setWeight(t.getNewValue().doubleValue());
			}
		});

		/* Training tab callback configuration */
		fxTrainingActivitiesCombo.setCellFactory(new Callback<ListView<Exercise>, ListCell<Exercise>>() {
			@Override
			public ListCell<Exercise> call(ListView<Exercise> param) {
				return new ExerciseComboBoxItem();
			}
		});
		fxTrainingTableDate.setCellValueFactory(new Callback<CellDataFeatures<TrainingRecord, String>, ObservableValue<String>>() {
			public ObservableValue<String> call(CellDataFeatures<TrainingRecord, String> p) {
				return new SimpleStringProperty(p.getValue().getDateString());
			}
		});
		fxTrainingTableTime.setCellValueFactory(new Callback<CellDataFeatures<TrainingRecord, String>, ObservableValue<String>>() {
			public ObservableValue<String> call(CellDataFeatures<TrainingRecord, String> p) {
				return new SimpleStringProperty(p.getValue().getTimeString());
			}
		});
		fxTrainingTableExercise.setCellValueFactory(new Callback<CellDataFeatures<TrainingRecord, String>, ObservableValue<String>>() {
			public ObservableValue<String> call(CellDataFeatures<TrainingRecord, String> p) {
				return new SimpleStringProperty(p.getValue().getRoutine().getName());
			}
		});
		fxTrainingTableDuration.setCellValueFactory(new Callback<CellDataFeatures<TrainingRecord, Number>, ObservableValue<Number>>() {
			public ObservableValue<Number> call(CellDataFeatures<TrainingRecord, Number> p) {
				return new SimpleDoubleProperty(p.getValue().getTime());
			}
		});
		fxTrainingTableCalBurnt.setCellValueFactory(new Callback<CellDataFeatures<TrainingRecord, Number>, ObservableValue<Number>>() {
			public ObservableValue<Number> call(CellDataFeatures<TrainingRecord, Number> p) {
				return new SimpleDoubleProperty(p.getValue().getRoutine().getBurnRate() * ((double) p.getValue().getTime() / 60.0));
			}
		});

		/* Diet tab callback configuration */
		fxDietMealCombo.setCellFactory(new Callback<ListView<Food>, ListCell<Food>>() {
			@Override
			public ListCell<Food> call(ListView<Food> param) {
				return new MealComboBoxItem();
			}
		});
		fxDietTableDate.setCellValueFactory(new Callback<CellDataFeatures<DietRecord, String>, ObservableValue<String>>() {
			public ObservableValue<String> call(CellDataFeatures<DietRecord, String> p) {
				return new SimpleStringProperty(p.getValue().getDateString());
			}
		});
		fxDietTableTime.setCellValueFactory(new Callback<CellDataFeatures<DietRecord, String>, ObservableValue<String>>() {
			public ObservableValue<String> call(CellDataFeatures<DietRecord, String> p) {
				return new SimpleStringProperty(p.getValue().getTimeString());
			}
		});
		fxDietTableFoodName.setCellValueFactory(new Callback<CellDataFeatures<DietRecord, String>, ObservableValue<String>>() {
			public ObservableValue<String> call(CellDataFeatures<DietRecord, String> p) {
				return new SimpleStringProperty(p.getValue().getMeal().getName());
			}
		});
		fxDietTableCalories.setCellValueFactory(new Callback<CellDataFeatures<DietRecord, Number>, ObservableValue<Number>>() {
			public ObservableValue<Number> call(CellDataFeatures<DietRecord, Number> p) {
				return new SimpleDoubleProperty(p.getValue().getMeal().getCalories());
			}
		});
		fxDietTableFat.setCellValueFactory(new Callback<CellDataFeatures<DietRecord, Number>, ObservableValue<Number>>() {
			public ObservableValue<Number> call(CellDataFeatures<DietRecord, Number> p) {
				return new SimpleDoubleProperty(p.getValue().getMeal().getFatContent());
			}
		});
		fxDietTableSalt.setCellValueFactory(new Callback<CellDataFeatures<DietRecord, Number>, ObservableValue<Number>>() {
			public ObservableValue<Number> call(CellDataFeatures<DietRecord, Number> p) {
				return new SimpleDoubleProperty(p.getValue().getMeal().getSaltContent());
			}
		});

		/* Links tab callback configuration */
		fxLinksTableName.setCellValueFactory(new Callback<CellDataFeatures<LinkRecord, String>, ObservableValue<String>>() {
			public ObservableValue<String> call(CellDataFeatures<LinkRecord, String> p) {
				return new SimpleStringProperty(p.getValue().getName());
			}
		});
		fxLinksTableURL.setCellValueFactory(new Callback<CellDataFeatures<LinkRecord, String>, ObservableValue<String>>() {
			public ObservableValue<String> call(CellDataFeatures<LinkRecord, String> p) {
				return new SimpleStringProperty(p.getValue().getURL());
			}
		});
	}
}