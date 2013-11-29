package pta.model;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathException;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * The main representation of the model. This class brings together all other
 * model elements to represent the user's profile in the system. One of these
 * profiles should be loaded at any one time, once the has has been logged in.
 * 
 * @author Steven Frost
 */
public class Profile {
	public enum HeightMeasure {
		METRES, FEET
	};

	public enum WeightMeasure {
		KILOGRAMS, POUNDS
	};

	/* Internal subclass instance */
	private ProfileXML xml;

	/* Preferences */
	private HeightMeasure heightUnit;
	private WeightMeasure weightUnit;
	private boolean tableDefault;

	/* Account details */
	private String username;
	private String password;
	private Person user;

	/* Goals */
	private double bmiGoal;
	private double weightGoal;

	/* Records */
	protected ObservableList<BMIRecord> bmiRecords = FXCollections.observableArrayList();
	protected ObservableList<DietRecord> dietRecords = FXCollections.observableArrayList();
	protected ObservableList<TrainingRecord> trainingRecords = FXCollections.observableArrayList();
	protected ObservableList<PictureRecord> pictureRecords = FXCollections.observableArrayList();
	protected ObservableList<LinkRecord> linkRecords = FXCollections.observableArrayList();
	protected ObservableList<Food> mealRecords = FXCollections.observableArrayList();
	protected ObservableList<Exercise> exerciseRecords = FXCollections.observableArrayList();

	/* Record counters */
	protected int numBMIRecordsOnSave;
	protected int numDietRecordsOnSave;
	protected int numTrainingRecordsOnSave;
	protected int numPictureRecordsOnSave;
	protected int numLinkRecordsOnSave;
	protected int numExercisesOnSave;
	protected int numMealsOnSave;

	/**
	 * Constructor
	 * 
	 * @param fileName the path to the user's profile
	 * @throws Exception if the profile cannot be found
	 */
	public Profile(String fileName) throws Exception {
		xml = new ProfileXML(fileName);
		loadProfile();
	}

	/**
	 * Loads the profile from the XML file into the Profile class
	 * @throws XPathException
	 */
	private void loadProfile() throws XPathException {
		heightUnit = xml.getHeightUnit();
		weightUnit = xml.getWeightUnit();
		tableDefault = xml.getTableDefault();
		username = xml.getUsername();
		password = xml.getPassword();
		user = xml.getUser();
		bmiGoal = xml.getBMIGoal();
		weightGoal = xml.getWeightGoal();

		for (int i = 1; i <= xml.getNumMeals(); i++)
			mealRecords.add(xml.getFoodRecord(i));
		for (int i = 1; i <= xml.getNumExercises(); i++)
			exerciseRecords.add(xml.getExerciseRecord(i));
		for (int i = 1; i <= xml.getNumBMIRecords(); i++)
			bmiRecords.add(xml.getBMIRecord(i));
		for (int i = 1; i <= xml.getNumDietRecords(); i++)
			dietRecords.add(xml.getDietRecord(i));
		for (int i = 1; i <= xml.getNumTrainingRecords(); i++)
			trainingRecords.add(xml.getTrainingRecord(i));
		for (int i = 1; i <= xml.getNumPictureRecords(); i++)
			pictureRecords.add(xml.getPictureRecord(i));
		for (int i = 1; i <= xml.getNumLinkRecords(); i++)
			linkRecords.add(xml.getLinkRecord(i));

		updateRecordCount();
	}

	/**
	 * Saves the current state of the class to the XML file
	 * 
	 * @throws TransformerException if some element couldn't be saved
	 */
	public void saveProfile() throws TransformerException {
		try {
			xml.setHeightUnit(heightUnit);
			xml.setWeightUnit(weightUnit);
			xml.setTableDefault(tableDefault);
			xml.setUsername(username);
			xml.setPassword(password);
			xml.setUser(user);
			xml.setBMIGoal(bmiGoal);
			xml.setWeightGoal(weightGoal);
			xml.setRecords();
		} catch (XPathExpressionException e) {
			return;
		}

		xml.writeProfile();
		updateRecordCount();
	}

	/**
	 * Updates the number of records at any one time, to enable easy detection
	 * of changes
	 */
	private void updateRecordCount() {
		numBMIRecordsOnSave = bmiRecords.size();
		numDietRecordsOnSave = dietRecords.size();
		numTrainingRecordsOnSave = trainingRecords.size();
		numPictureRecordsOnSave = pictureRecords.size();
		numLinkRecordsOnSave = linkRecords.size();
		numExercisesOnSave = exerciseRecords.size();
		numMealsOnSave = mealRecords.size();
	}

	public int numBMIRecords() {
		return bmiRecords.size();
	}

	public int numDietRecords() {
		return dietRecords.size();
	}

	public int numTrainingRecords() {
		return trainingRecords.size();
	}

	public int numPictureRecords() {
		return pictureRecords.size();
	}

	public int numLinkRecords() {
		return linkRecords.size();
	}

	public int numExercises() {
		return exerciseRecords.size();
	}

	public int numMeals() {
		return mealRecords.size();
	}

	public WeightMeasure getWeightUnit() {
		return weightUnit;
	}

	public void setWeightUnit(WeightMeasure weightUnit) {
		this.weightUnit = weightUnit;
	}

	public HeightMeasure getHeightUnit() {
		return heightUnit;
	}

	public void setHeightUnit(HeightMeasure heightUnit) {
		this.heightUnit = heightUnit;
	}

	public boolean getTableDefault() {
		return tableDefault;
	}

	public void setTableDefault(boolean tableDefault) {
		this.tableDefault = tableDefault;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Person getUser() {
		return user;
	}

	public void setUser(Person user) {
		this.user = user;
	}

	public double getBMIGoal() {
		return bmiGoal;
	}

	public void setBMIGoal(double bmiGoal) {
		this.bmiGoal = bmiGoal;
	}

	public double getWeightGoal() {
		return weightGoal;
	}

	public void setWeightGoal(double weightGoal) {
		this.weightGoal = weightGoal;
	}

	public void addBMIRecord(BMIRecord record) {
		if (record == null)
			return;
		bmiRecords.add(record);
	}

	public void addDietRecord(DietRecord record) {
		if (record == null)
			return;
		dietRecords.add(record);
	}

	public void addTrainingRecord(TrainingRecord record) {
		if (record == null)
			return;
		trainingRecords.add(record);
	}

	public void addPictureRecord(PictureRecord record) {
		if (record == null)
			return;
		pictureRecords.add(record);
	}

	public void addLinkRecord(LinkRecord record) {
		if (record == null)
			return;
		linkRecords.add(record);
	}

	public void addExerciseRoutine(Exercise routine) {
		if (routine == null)
			return;
		exerciseRecords.add(routine);
	}

	public void addMeal(Food meal) {
		if (meal == null)
			return;
		mealRecords.add(meal);
	}

	public BMIRecord getBMIRecord(int id) {
		if (id < 0) {
			return null;
		} else if (id >= bmiRecords.size()) {
			return getBMIRecord();
		}
		return bmiRecords.get(id);
	}

	public BMIRecord getBMIRecord() {
		return getBMIRecord(bmiRecords.size() - 1);
	}

	public DietRecord getDietRecord(int id) {
		if (id < 0) {
			return null;
		} else if (id >= dietRecords.size()) {
			return getDietRecord();
		}
		return dietRecords.get(id);
	}

	public DietRecord getDietRecord() {
		return getDietRecord(dietRecords.size() - 1);
	}

	public TrainingRecord getTrainingRecord(int id) {
		if (id < 0) {
			return null;
		} else if (id >= trainingRecords.size()) {
			return getTrainingRecord();
		}
		return trainingRecords.get(id);
	}

	public TrainingRecord getTrainingRecord() {
		return getTrainingRecord(trainingRecords.size() - 1);
	}

	public PictureRecord getPictureRecord(int id) {
		if (id < 0) {
			return null;
		} else if (id >= pictureRecords.size()) {
			return getPictureRecord();
		}
		return pictureRecords.get(id);
	}

	public PictureRecord getPictureRecord() {
		return getPictureRecord(pictureRecords.size() - 1);
	}

	public LinkRecord getLinkRecord(int id) {
		if (id < 0) {
			return null;
		} else if (id >= linkRecords.size()) {
			return getLinkRecord();
		}
		return linkRecords.get(id);
	}

	public LinkRecord getLinkRecord() {
		return getLinkRecord(linkRecords.size() - 1);
	}

	public Exercise getExerciseRoutine(int id) {
		if (id < 0) {
			return null;
		} else if (id >= exerciseRecords.size()) {
			return getExerciseRoutine();
		}
		return exerciseRecords.get(id);
	}

	public Exercise getExerciseRoutine() {
		return getExerciseRoutine(exerciseRecords.size() - 1);
	}

	public Food getMeal(int id) {
		if (id < 0) {
			return null;
		} else if (id >= mealRecords.size()) {
			return getMeal();
		}
		return mealRecords.get(id);
	}

	public Food getMeal() {
		return getMeal(mealRecords.size() - 1);
	}

	public void removeBMIRecord(int id) {
		if (id < 0 || id >= bmiRecords.size()) {
			return;
		}
		bmiRecords.remove(id);
	}

	public void removeDietRecord(int id) {
		if (id < 0 || id >= dietRecords.size()) {
			return;
		}
		dietRecords.remove(id);
	}

	public void removeTrainingRecord(int id) {
		if (id < 0 || id >= trainingRecords.size()) {
			return;
		}
		trainingRecords.remove(id);
	}

	public void removePictureRecord(int id) {
		if (id < 0 || id >= pictureRecords.size()) {
			return;
		}
		pictureRecords.remove(id);
	}

	public void removeLinkRecord(int id) {
		if (id < 0 || id >= linkRecords.size()) {
			return;
		}
		linkRecords.remove(id);
	}

	public ObservableList<BMIRecord> getBMIRecords() {
		return bmiRecords;
	}

	public ObservableList<TrainingRecord> getTrainingRecords() {
		return trainingRecords;
	}

	public ObservableList<DietRecord> getDietRecords() {
		return dietRecords;
	}

	public ObservableList<PictureRecord> getPictureRecords() {
		return pictureRecords;
	}

	public ObservableList<LinkRecord> getLinkRecords() {
		return linkRecords;
	}

	public ObservableList<Food> getMealRecords() {
		return mealRecords;
	}

	public ObservableList<Exercise> getExerciseRecords() {
		return exerciseRecords;
	}
	
	/**
	 * Internal profile class for reading and writing the user's XML profile.
	 * This class is not exposed to the rest of the system and has direct access
	 * to some inner members of the Profile class.
	 * 
	 * @author Steven Frost
	 */
	private class ProfileXML {
		private XPath xPath;
		private Document document;
		private XPathExpression expr;
		private String fileName = "";
		private XPathFactory xPathFactory;
		private DocumentBuilder docBuilder;
		private DocumentBuilderFactory factory;

		/**
		 * Constructor
		 * 
		 * @param fileName the filename of the profile
		 * @throws ParserConfigurationException if the profile cannot be parsed
		 * @throws SAXException if a document couldn't be read
		 * @throws IOException if a profile document couldn't be loaded
		 */
		ProfileXML(String fileName) throws ParserConfigurationException, SAXException, IOException {
			this.fileName = fileName;

			/* If the profile doesn't exist, create a new one */
			if (!new File(fileName).exists()) {
				createProfile();
			}

			/* Load the user's data from the profile */
			loadProfile();
		}

		/**
		 * Create a new copy of the default profile layout, with the name of
		 * the current fileName.
		 * 
		 * @throws IOException if the profile could not be created
		 */
		private void createProfile() throws IOException {
			Path FROM = Paths.get("profiles/default.xml");
			Path TO = Paths.get(fileName);
			Files.copy(FROM, TO, StandardCopyOption.COPY_ATTRIBUTES);
		}

		/**
		 * Loads the profile XML into memory, ready to be processed and split
		 * into native java objects
		 * 
		 * @throws ParserConfigurationException
		 * @throws SAXException
		 * @throws IOException
		 */
		public void loadProfile() throws ParserConfigurationException, SAXException, IOException {
			factory = DocumentBuilderFactory.newInstance();
			docBuilder = factory.newDocumentBuilder();
			document = docBuilder.parse(fileName);

			xPathFactory = XPathFactory.newInstance();
			xPath = xPathFactory.newXPath();
		}

		public HeightMeasure getHeightUnit() {
			return getElementText("//preferences//heightUnit").equals("METRES") ? HeightMeasure.METRES : HeightMeasure.FEET;
		}

		public WeightMeasure getWeightUnit() {
			return getElementText("//preferences//weightUnit").equals("KILOGRAMS") ? WeightMeasure.KILOGRAMS : WeightMeasure.POUNDS;
		}

		public boolean getTableDefault() {
			return getElementText("//preferences//tableDefault").equals("TRUE");
		}

		public String getUsername() {
			return getElementText("//account//username");
		}

		public String getPassword() {
			return getElementText("//account//password");
		}

		public Person getUser() {
			String forename = null, surname = null, dob = null;
			try {
				forename = getElementText("//account//forename");
				surname = getElementText("//account//surname");
				dob = getElementText("//account//dob");

				return new Person(forename, surname, dob);
			} catch (ParseException e) {
				return null;
			}
		}

		public double getBMIGoal() {
			try {
				return Double.parseDouble(getElementText("//goals//bmi"));
			} catch (NumberFormatException e) {
				return 0.0;
			}
		}

		public double getWeightGoal() {
			try {
				return Double.parseDouble(getElementText("//goals//weight"));
			} catch (NumberFormatException e) {
				return 0.0;
			}
		}

		public int getNumBMIRecords() {
			return getNumElements("//records//bmi//record");
		}

		public int getNumDietRecords() {
			return getNumElements("//records//diet//record");
		}

		public int getNumTrainingRecords() {
			return getNumElements("//records//training//record");
		}

		public int getNumPictureRecords() {
			return getNumElements("//records//pictures//record");
		}

		public int getNumLinkRecords() {
			return getNumElements("//records//links//record");
		}

		public int getNumMeals() {
			return getNumElements("//records//meals//record");
		}

		public int getNumExercises() {
			return getNumElements("//records//exercises//record");
		}

		public Food getFoodRecord(int i) {
			String base = "//records//meals//record[" + i + "]";

			try {
				String name = getElementText(base + "//name");
				int calories = Integer.parseInt(getElementText(base + "//calories"));
				double fat = Double.parseDouble(getElementText(base + "//fat"));
				double salt = Double.parseDouble(getElementText(base + "//salt"));

				return new Food(name, calories, fat, salt);
			} catch (NumberFormatException e) {
				return null;
			}
		}

		public BMIRecord getBMIRecord(int i) {
			String base = "//records//bmi//record[" + i + "]";

			try {
				double height = Double.parseDouble(getElementText(base + "//height"));
				double weight = Double.parseDouble(getElementText(base + "//weight"));
				HeightMeasure hUnit = getElementText(base + "//height//@unit").equals("METRES") ? HeightMeasure.METRES : HeightMeasure.FEET;
				WeightMeasure wUnit = getElementText(base + "//weight//@unit").equals("KILOGRAMS") ? WeightMeasure.KILOGRAMS : WeightMeasure.POUNDS;
				String date = getElementText(base + "//@date");

				return new BMIRecord(height, weight, hUnit, wUnit, date);
			} catch (ParseException e) {
				return null;
			}
		}

		public DietRecord getDietRecord(int i) {
			String base = "//records//diet//record[" + i + "]";

			try {
				String foodItem = getElementText(base + "//food");
				String date = getElementText(base + "//@date");

				for (Food f : mealRecords) {
					if (f.getName().equals(foodItem)) {
						return new DietRecord(f, date);
					}
				}
				return null;
			} catch (ParseException e) {
				return null;
			}
		}

		public TrainingRecord getTrainingRecord(int i) {
			String base = "//records//training//record[" + i + "]";

			try {
				String exerciseRoutine = getElementText(base + "//routine");
				int time = Integer.parseInt(getElementText(base + "//time"));
				String date = getElementText(base + "//@date");

				for (Exercise e : exerciseRecords) {
					if (e.getName().equals(exerciseRoutine)) {
						return new TrainingRecord(e, time, date);
					}
				}
				return null;
			} catch (ParseException e) {
				return null;
			}
		}

		public PictureRecord getPictureRecord(int i) {
			String base = "//records//pictures//record[" + i + "]";

			try {
				String path = getElementText(base + "//path");
				String date = getElementText(base + "//@date");

				return new PictureRecord(path, date);
			} catch (ParseException e) {
				return null;
			}
		}

		public LinkRecord getLinkRecord(int i) {
			String base = "//records//links//record[" + i + "]";

			try {
				String name = getElementText(base + "//name");
				String url = getElementText(base + "//url");
				String date = getElementText(base + "//@date");

				return new LinkRecord(name, url, date);
			} catch (ParseException e) {
				return null;
			}
		}

		public Exercise getExerciseRecord(int i) {
			String base = "//records//exercises//record[" + i + "]";

			try {
				String name = getElementText(base + "//name");
				double burnRate = Double.parseDouble(getElementText(base + "//burnRate"));

				return new Exercise(name, burnRate);
			} catch (NumberFormatException e) {
				return null;
			}
		}

		private int getNumElements(String selection) {
			try {
				expr = xPath.compile(selection);
				return ((NodeList) expr.evaluate(document, XPathConstants.NODESET)).getLength();
			} catch (XPathExpressionException e) {
				return 0;
			}
		}

		public String getElementText(String selection) {
			try {
				expr = xPath.compile(selection);
				return ((String) expr.evaluate(document, XPathConstants.STRING));
			} catch (XPathExpressionException e) {
				return null;
			}
		}

		public void setHeightUnit(HeightMeasure hUnit) throws XPathExpressionException {
			setElementText("//preferences//heightUnit", hUnit.toString());
		}

		public void setWeightUnit(WeightMeasure wUnit) throws XPathExpressionException {
			setElementText("//preferences//weightUnit", wUnit.toString());
		}

		public void setTableDefault(boolean tableDefault) throws XPathExpressionException {
			setElementText("//preferences//tableDefault", tableDefault ? "TRUE" : "FALSE");
		}

		public void setUsername(String username) throws XPathExpressionException {
			setElementText("//account//username", username);
		}

		public void setPassword(String password) throws XPathExpressionException {
			setElementText("//account//password", password);
		}

		public void setUser(Person user) throws XPathExpressionException {
			setElementText("//account//forename", user.getForename());
			setElementText("//account//surname", user.getSurname());
			setElementText("//account//dob", user.getDOBString());
		}

		public void setBMIGoal(double goal) throws XPathExpressionException {
			setElementText("//goals//bmi", String.valueOf(goal));
		}

		public void setWeightGoal(double goal) throws XPathExpressionException {
			setElementText("//goals//weight", String.valueOf(goal));
		}

		public void setRecords() {
			Element root = document.getDocumentElement();
			Node records = root.getElementsByTagName("records").item(0);

			for (int i = 0; i < records.getChildNodes().getLength(); i++) {
				Node record = records.getChildNodes().item(i);

				if (record.getNodeName().equals("bmi")) {
					setBMIRecords(record);
				} else if (record.getNodeName().equals("diet")) {
					setDietRecords(record);
				} else if (record.getNodeName().equals("training")) {
					setTrainingRecords(record);
				} else if (record.getNodeName().equals("pictures")) {
					setPictureRecords(record);
				} else if (record.getNodeName().equals("links")) {
					setLinkRecords(record);
				} else if (record.getNodeName().equals("meals")) {
					setMeals(record);
				} else if (record.getNodeName().equals("exercises")) {
					setExercises(record);
				}
			}
		}

		private void setBMIRecords(Node bmiRecord) {
			for (int i = numBMIRecordsOnSave; i < bmiRecords.size(); i++) {
				BMIRecord a = bmiRecords.get(i);

				Element bmiRec = document.createElement("record");
				bmiRec.setAttribute("date", new SimpleDateFormat("dd-MM-yyyy HH:mm").format(a.getDateTime()));

				Element bmiHeight = document.createElement("height");
				bmiHeight.setAttribute("unit", a.getHeightUnit().toString());
				bmiHeight.setTextContent(String.valueOf(a.getHeight()));
				bmiRec.appendChild(bmiHeight);

				Element bmiWeight = document.createElement("weight");
				bmiWeight.setAttribute("unit", a.getWeightUnit().toString());
				bmiWeight.setTextContent(String.valueOf(a.getWeight()));
				bmiRec.appendChild(bmiWeight);
				bmiRecord.appendChild(bmiRec);
			}
		}

		private void setDietRecords(Node dietRecord) {
			for (int i = numDietRecordsOnSave; i < dietRecords.size(); i++) {
				DietRecord a = dietRecords.get(i);

				Element dietRec = document.createElement("record");
				dietRec.setAttribute("date", new SimpleDateFormat("dd-MM-yyyy HH:mm").format(a.getDateTime()));

				Element dietFoodItem = document.createElement("food");
				dietFoodItem.setTextContent(a.getMeal().getName());
				dietRec.appendChild(dietFoodItem);
				dietRecord.appendChild(dietRec);
			}
		}

		private void setTrainingRecords(Node trainingRecord) {
			for (int i = numTrainingRecordsOnSave; i < trainingRecords.size(); i++) {
				TrainingRecord a = trainingRecords.get(i);

				Element trainingRec = document.createElement("record");
				trainingRec.setAttribute("date", new SimpleDateFormat("dd-MM-yyyy HH:mm").format(a.getDateTime()));

				Element trainingActivity = document.createElement("routine");
				trainingActivity.setTextContent(a.getRoutine().getName());
				trainingRec.appendChild(trainingActivity);

				Element trainingTime = document.createElement("time");
				trainingTime.setTextContent(String.valueOf(a.getTime()));
				trainingRec.appendChild(trainingTime);
				trainingRecord.appendChild(trainingRec);
			}
		}

		private void setPictureRecords(Node pictureRecord) {
			for (int i = numPictureRecordsOnSave; i < pictureRecords.size(); i++) {
				PictureRecord a = pictureRecords.get(i);

				Element pictureRec = document.createElement("record");
				pictureRec.setAttribute("date", new SimpleDateFormat("dd-MM-yyyy HH:mm").format(a.getDateTime()));

				Element picturePath = document.createElement("path");
				picturePath.setTextContent(a.getPath());
				pictureRec.appendChild(picturePath);
				pictureRecord.appendChild(pictureRec);
			}
		}

		private void setLinkRecords(Node linkRecord) {
			for (int i = numLinkRecordsOnSave; i < linkRecords.size(); i++) {
				LinkRecord a = linkRecords.get(i);

				Element linkRec = document.createElement("record");
				linkRec.setAttribute("date", new SimpleDateFormat("dd-MM-yyyy HH:mm").format(a.getDateTime()));

				Element linkName = document.createElement("name");
				linkName.setTextContent(a.getName());
				linkRec.appendChild(linkName);

				Element linkUrl = document.createElement("url");
				linkUrl.setTextContent(a.getURL());
				linkRec.appendChild(linkUrl);
				linkRecord.appendChild(linkRec);
			}
		}

		private void setMeals(Node meal) {
			for (int i = numMealsOnSave; i < mealRecords.size(); i++) {
				Food a = mealRecords.get(i);

				Element mealRec = document.createElement("record");

				Element mealName = document.createElement("name");
				mealName.setTextContent(a.getName());
				mealRec.appendChild(mealName);

				Element mealCalories = document.createElement("calories");
				mealCalories.setTextContent(String.valueOf(a.getCalories()));
				mealRec.appendChild(mealCalories);

				Element mealFat = document.createElement("fat");
				mealFat.setTextContent(String.valueOf(a.getFatContent()));
				mealRec.appendChild(mealFat);

				Element mealSalt = document.createElement("salt");
				mealSalt.setTextContent(String.valueOf(a.getSaltContent()));
				mealRec.appendChild(mealSalt);
				meal.appendChild(mealRec);
			}
		}

		private void setExercises(Node exercise) {
			for (int i = numExercisesOnSave; i < exerciseRecords.size(); i++) {
				Exercise a = exerciseRecords.get(i);

				Element exerciseRec = document.createElement("record");

				Element exerciseName = document.createElement("name");
				exerciseName.setTextContent(a.getName());
				exerciseRec.appendChild(exerciseName);

				Element exerciseBurnRate = document.createElement("burnRate");
				exerciseBurnRate.setTextContent(String.valueOf(a.getBurnRate()));
				exerciseRec.appendChild(exerciseBurnRate);
				exercise.appendChild(exerciseRec);
			}
		}

		private void setElementText(String selection, String value) throws XPathExpressionException {
			expr = xPath.compile(selection);
			Node node = (Node) expr.evaluate(document, XPathConstants.NODE);
			node.setTextContent(value);
		}

		public void writeProfile() throws TransformerException {
			TransformerFactory tFactory = TransformerFactory.newInstance();
			Transformer transformer = tFactory.newTransformer();
			DOMSource source = new DOMSource(document);
			StreamResult result = new StreamResult(new File(fileName));

			transformer.transform(source, result);
		}
	};
}