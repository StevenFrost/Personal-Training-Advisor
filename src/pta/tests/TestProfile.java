package pta.tests;

import static org.junit.Assert.*;

import java.io.File;
import java.util.Date;

import org.junit.Test;

import pta.lib.SHA1;
import pta.model.BMIRecord;
import pta.model.DietRecord;
import pta.model.Exercise;
import pta.model.Food;
import pta.model.LinkRecord;
import pta.model.Person;
import pta.model.PictureRecord;
import pta.model.Profile;
import pta.model.TrainingRecord;

public class TestProfile {
	Profile p;

	public void setup() throws Exception {
		/* Delete an existing test profile */
		File f = new File("profiles/unitTest.utxml");
		if (f.exists()) {
			f.delete();
		}

		/* Build a new default profile for testing */
		p = new Profile("profiles/unitTest.utxml");
	}

	@Test
	public void testNumBMIRecords() throws Exception {
		setup();
		assertEquals(p.numBMIRecords(), 0);
		p.addBMIRecord(new BMIRecord(1.8, 80.3, Profile.HeightMeasure.METRES, Profile.WeightMeasure.KILOGRAMS, new Date()));
		assertEquals(p.numBMIRecords(), 1);
	}

	@Test
	public void testNumDietRecords() throws Exception {
		setup();
		assertEquals(p.numDietRecords(), 0);
		p.addDietRecord(new DietRecord(new Food("Chicken", 270, 0.1, 0.1), new Date()));
		assertEquals(p.numDietRecords(), 1);
	}

	@Test
	public void testNumTrainingRecords() throws Exception {
		setup();
		assertEquals(p.numTrainingRecords(), 0);
		p.addTrainingRecord(new TrainingRecord(new Exercise("Running", 430.5), 20, new Date()));
		assertEquals(p.numTrainingRecords(), 1);
	}

	@Test
	public void testNumPictureRecords() throws Exception {
		setup();
		assertEquals(p.numPictureRecords(), 0);
		p.addPictureRecord(new PictureRecord("/path/to/picture", new Date()));
		assertEquals(p.numPictureRecords(), 1);
	}

	@Test
	public void testNumLinkRecords() throws Exception {
		setup();
		assertEquals(p.numLinkRecords(), 0);
		p.addLinkRecord(new LinkRecord("Google", "http://www.google.co.uk", new Date()));
		assertEquals(p.numLinkRecords(), 1);
	}

	@Test
	public void testNumExercises() throws Exception {
		setup();
		assertEquals(p.numExercises(), 7);
		p.addExerciseRoutine(new Exercise("Running", 430.5));
		assertEquals(p.numExercises(), 8);
	}

	@Test
	public void testNumMeals() throws Exception {
		setup();
		assertEquals(p.numMeals(), 2);
		p.addMeal(new Food("Chicken", 270, 0.1, 0.1));
		assertEquals(p.numMeals(), 3);
	}

	@Test
	public void testGetWeightUnit() throws Exception {
		setup();
		assertEquals(p.getWeightUnit(), Profile.WeightMeasure.KILOGRAMS);
	}

	@Test
	public void testSetWeightUnit() throws Exception {
		setup();
		assertEquals(p.getWeightUnit(), Profile.WeightMeasure.KILOGRAMS);
		p.setWeightUnit(Profile.WeightMeasure.POUNDS);
		assertEquals(p.getWeightUnit(), Profile.WeightMeasure.POUNDS);
	}

	@Test
	public void testGetHeightUnit() throws Exception {
		setup();
		assertEquals(p.getHeightUnit(), Profile.HeightMeasure.METRES);
	}

	@Test
	public void testSetHeightUnit() throws Exception {
		setup();
		assertEquals(p.getHeightUnit(), Profile.HeightMeasure.METRES);
		p.setHeightUnit(Profile.HeightMeasure.FEET);
		assertEquals(p.getHeightUnit(), Profile.HeightMeasure.FEET);
	}

	@Test
	public void testGetTableDefault() throws Exception {
		setup();
		assertEquals(p.getTableDefault(), false);
	}

	@Test
	public void testSetTableDefault() throws Exception {
		setup();
		assertEquals(p.getTableDefault(), false);
		p.setTableDefault(true);
		assertEquals(p.getTableDefault(), true);
	}

	@Test
	public void testGetUsername() throws Exception {
		setup();
		assertEquals(p.getUsername(), "");
	}

	@Test
	public void testGetPassword() throws Exception {
		setup();
		assertEquals(p.getPassword(), "");
	}

	@Test
	public void testSetUsername() throws Exception {
		setup();
		assertEquals(p.getUsername(), "");
		p.setUsername("test");
		assertEquals(p.getUsername(), "test");
	}

	@Test
	public void testSetPassword() throws Exception {
		setup();
		assertEquals(p.getPassword(), "");
		p.setPassword(SHA1.hash("test"));
		assertEquals(p.getPassword(), SHA1.hash("test"));
	}

	@Test
	public void testGetUser() throws Exception {
		setup();
		assertEquals(p.getUser(), null);
	}

	@Test
	public void testSetUser() throws Exception {
		setup();
		Person person = new Person("Steven", "Frost", "26-11-1993");
		assertEquals(p.getUser(), null);
		p.setUser(person);
		assertEquals(p.getUser(), person);
	}

	@Test
	public void testGetBMIGoal() throws Exception {
		setup();
		assertEquals(p.getBMIGoal(), 23.0, 0.01);
	}

	@Test
	public void testSetBMIGoal() throws Exception {
		setup();
		assertEquals(p.getBMIGoal(), 23.0, 0.01);
		p.setBMIGoal(22.3);
		assertEquals(p.getBMIGoal(), 22.3, 0.01);
	}

	@Test
	public void testGetWeightGoal() throws Exception {
		setup();
		assertEquals(p.getWeightGoal(), 73.5, 0.01);
	}

	@Test
	public void testSetWeightGoal() throws Exception {
		setup();
		assertEquals(p.getWeightGoal(), 73.5, 0.01);
		p.setWeightGoal(73.1);
		assertEquals(p.getWeightGoal(), 73.1, 0.01);
	}

	@Test
	public void testGetBMIRecordInt() throws Exception {
		setup();

		BMIRecord a = new BMIRecord(1.8, 80.3, Profile.HeightMeasure.METRES, Profile.WeightMeasure.KILOGRAMS, new Date());
		BMIRecord b = new BMIRecord(1.8, 80.6, Profile.HeightMeasure.METRES, Profile.WeightMeasure.KILOGRAMS, new Date());

		assertEquals(p.getBMIRecord(0), null);
		assertEquals(p.getBMIRecord(1), null);
		p.addBMIRecord(a);
		assertEquals(p.getBMIRecord(0), a);
		assertEquals(p.getBMIRecord(1), a);
		p.addBMIRecord(b);
		assertEquals(p.getBMIRecord(0), a);
		assertEquals(p.getBMIRecord(1), b);
	}

	@Test
	public void testGetBMIRecord() throws Exception {
		setup();

		BMIRecord a = new BMIRecord(1.8, 80.3, Profile.HeightMeasure.METRES, Profile.WeightMeasure.KILOGRAMS, new Date());
		BMIRecord b = new BMIRecord(1.8, 80.6, Profile.HeightMeasure.METRES, Profile.WeightMeasure.KILOGRAMS, new Date());

		assertEquals(p.getBMIRecord(), null);
		p.addBMIRecord(a);
		assertEquals(p.getBMIRecord(), a);
		p.addBMIRecord(b);
		assertEquals(p.getBMIRecord(), b);
	}

	@Test
	public void testGetDietRecordInt() throws Exception {
		setup();

		DietRecord a = new DietRecord(new Food("Chicken", 270, 0.2, 0.1), new Date());
		DietRecord b = new DietRecord(new Food("Pork", 250, 0.1, 0.1), new Date());

		assertEquals(p.getDietRecord(0), null);
		assertEquals(p.getDietRecord(1), null);
		p.addDietRecord(a);
		assertEquals(p.getDietRecord(0), a);
		assertEquals(p.getDietRecord(1), a);
		p.addDietRecord(b);
		assertEquals(p.getDietRecord(0), a);
		assertEquals(p.getDietRecord(1), b);
	}

	@Test
	public void testGetDietRecord() throws Exception {
		setup();

		DietRecord a = new DietRecord(new Food("Chicken", 270, 0.2, 0.1), new Date());
		DietRecord b = new DietRecord(new Food("Pork", 250, 0.1, 0.1), new Date());

		assertEquals(p.getDietRecord(), null);
		p.addDietRecord(a);
		assertEquals(p.getDietRecord(), a);
		p.addDietRecord(b);
		assertEquals(p.getDietRecord(), b);
	}

	@Test
	public void testGetTrainingRecordInt() throws Exception {
		setup();

		TrainingRecord a = new TrainingRecord(new Exercise("Running", 430.0), 20, new Date());
		TrainingRecord b = new TrainingRecord(new Exercise("Walking", 250.0), 60, new Date());

		assertEquals(p.getTrainingRecord(0), null);
		assertEquals(p.getTrainingRecord(1), null);
		p.addTrainingRecord(a);
		assertEquals(p.getTrainingRecord(0), a);
		assertEquals(p.getTrainingRecord(1), a);
		p.addTrainingRecord(b);
		assertEquals(p.getTrainingRecord(0), a);
		assertEquals(p.getTrainingRecord(1), b);
	}

	@Test
	public void testGetTrainingRecord() throws Exception {
		setup();

		TrainingRecord a = new TrainingRecord(new Exercise("Running", 430.0), 20, new Date());
		TrainingRecord b = new TrainingRecord(new Exercise("Walking", 250.0), 60, new Date());

		assertEquals(p.getTrainingRecord(), null);
		p.addTrainingRecord(a);
		assertEquals(p.getTrainingRecord(), a);
		p.addTrainingRecord(b);
		assertEquals(p.getTrainingRecord(), b);
	}

	@Test
	public void testGetPictureRecordInt() throws Exception {
		setup();

		PictureRecord a = new PictureRecord("/path/to/picture", new Date());
		PictureRecord b = new PictureRecord("/path/to/picture", new Date());

		assertEquals(p.getPictureRecord(0), null);
		assertEquals(p.getPictureRecord(1), null);
		p.addPictureRecord(a);
		assertEquals(p.getPictureRecord(0), a);
		assertEquals(p.getPictureRecord(1), a);
		p.addPictureRecord(b);
		assertEquals(p.getPictureRecord(0), a);
		assertEquals(p.getPictureRecord(1), b);
	}

	@Test
	public void testGetPictureRecord() throws Exception {
		setup();

		PictureRecord a = new PictureRecord("/path/to/picture", new Date());
		PictureRecord b = new PictureRecord("/path/to/picture", new Date());

		assertEquals(p.getPictureRecord(), null);
		p.addPictureRecord(a);
		assertEquals(p.getPictureRecord(), a);
		p.addPictureRecord(b);
		assertEquals(p.getPictureRecord(), b);
	}

	@Test
	public void testGetLinkRecordInt() throws Exception {
		setup();

		LinkRecord a = new LinkRecord("Google", "http://www.google.co.uk", new Date());
		LinkRecord b = new LinkRecord("Bing", "http://www.bing.com", new Date());

		assertEquals(p.getLinkRecord(0), null);
		assertEquals(p.getLinkRecord(1), null);
		p.addLinkRecord(a);
		assertEquals(p.getLinkRecord(0), a);
		assertEquals(p.getLinkRecord(1), a);
		p.addLinkRecord(b);
		assertEquals(p.getLinkRecord(0), a);
		assertEquals(p.getLinkRecord(1), b);
	}

	@Test
	public void testGetLinkRecord() throws Exception {
		setup();

		LinkRecord a = new LinkRecord("Google", "http://www.google.co.uk", new Date());
		LinkRecord b = new LinkRecord("Bing", "http://www.bing.com", new Date());

		assertEquals(p.getLinkRecord(), null);
		p.addLinkRecord(a);
		assertEquals(p.getLinkRecord(), a);
		p.addLinkRecord(b);
		assertEquals(p.getLinkRecord(), b);
	}

	@Test
	public void testGetExerciseRoutineInt() throws Exception {
		setup();

		Exercise a = new Exercise("Running", 430.0);
		Exercise b = new Exercise("Walking", 250.0);

		assertNotNull(p.getExerciseRoutine(p.numExercises()));
		assertNotNull(p.getExerciseRoutine(p.numExercises() + 1));
		p.addExerciseRoutine(a);
		assertEquals(p.getExerciseRoutine(p.numExercises() - 1), a);
		assertEquals(p.getExerciseRoutine(p.numExercises()), a);
		p.addExerciseRoutine(b);
		assertEquals(p.getExerciseRoutine(p.numExercises() - 2), a);
		assertEquals(p.getExerciseRoutine(p.numExercises() - 1), b);
	}

	@Test
	public void testGetExerciseRoutine() throws Exception {
		setup();

		Exercise a = new Exercise("Running", 430.0);
		Exercise b = new Exercise("Walking", 250.0);

		assertNotNull(p.getExerciseRoutine());
		p.addExerciseRoutine(a);
		assertEquals(p.getExerciseRoutine(), a);
		p.addExerciseRoutine(b);
		assertEquals(p.getExerciseRoutine(), b);
	}

	@Test
	public void testGetMealInt() throws Exception {
		setup();

		Food a = new Food("Chicken", 270, 0.2, 0.1);
		Food b = new Food("Pork", 250, 0.1, 0.1);

		assertNotNull(p.getMeal(p.numMeals()));
		assertNotNull(p.getMeal(p.numMeals() + 1));
		p.addMeal(a);
		assertEquals(p.getMeal(p.numMeals() - 1), a);
		assertEquals(p.getMeal(p.numMeals()), a);
		p.addMeal(b);
		assertEquals(p.getMeal(p.numMeals() - 2), a);
		assertEquals(p.getMeal(p.numMeals() - 1), b);
	}

	@Test
	public void testGetMeal() throws Exception {
		setup();

		Food a = new Food("Chicken", 270, 0.2, 0.1);
		Food b = new Food("Pork", 250, 0.1, 0.1);

		assertNotNull(p.getMeal());
		p.addMeal(a);
		assertEquals(p.getMeal(), a);
		p.addMeal(b);
		assertEquals(p.getMeal(), b);
	}

	@Test
	public void testGetBMIRecords() throws Exception {
		setup();
		assertEquals(p.getBMIRecords().size(), 0);
		p.addBMIRecord(new BMIRecord(1.8, 80.3, Profile.HeightMeasure.METRES, Profile.WeightMeasure.KILOGRAMS, new Date()));
		assertEquals(p.getBMIRecords().size(), 1);
	}

	@Test
	public void testGetTrainingRecords() throws Exception {
		setup();
		assertEquals(p.getTrainingRecords().size(), 0);
		p.addTrainingRecord(new TrainingRecord(new Exercise("Running", 430.5), 20, new Date()));
		assertEquals(p.getTrainingRecords().size(), 1);
	}

	@Test
	public void testGetDietRecords() throws Exception {
		setup();
		assertEquals(p.getDietRecords().size(), 0);
		p.addDietRecord(new DietRecord(new Food("Chicken", 270, 0.1, 0.1), new Date()));
		assertEquals(p.getDietRecords().size(), 1);
	}

	@Test
	public void testGetPictureRecords() throws Exception {
		setup();
		assertEquals(p.getPictureRecords().size(), 0);
		p.addPictureRecord(new PictureRecord("/path/to/picture", new Date()));
		assertEquals(p.getPictureRecords().size(), 1);
	}

	@Test
	public void testGetLinkRecords() throws Exception {
		setup();
		assertEquals(p.getLinkRecords().size(), 0);
		p.addLinkRecord(new LinkRecord("Google", "http://www.google.co.uk", new Date()));
		assertEquals(p.getLinkRecords().size(), 1);
	}

	@Test
	public void testGetMealRecords() throws Exception {
		setup();
		assertEquals(p.getMealRecords().size(), 2);
		p.addMeal(new Food("Chicken", 270, 0.1, 0.1));
		assertEquals(p.getMealRecords().size(), 3);
	}

	@Test
	public void testGetExerciseRecords() throws Exception {
		setup();
		assertEquals(p.getExerciseRecords().size(), 7);
		p.addExerciseRoutine(new Exercise("Running", 430.5));
		assertEquals(p.getExerciseRecords().size(), 8);
	}
}