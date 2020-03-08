package com.bettorware.karera.drf;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.bettorware.karera.drf.idl.DrfHorse;
import com.bettorware.karera.drf.idl.DrfJockey;
import com.bettorware.karera.drf.idl.DrfRace;
import com.bettorware.karera.drf.idl.DrfStatCategory;
import com.bettorware.karera.drf.idl.DrfTrainer;
import com.bettorware.karera.drf.idl.DrfWorkout;
import com.bettorware.karera.drf.idl.SingleFileDrf;

public class SingleFileDrfParserTest {
	
	private static final String DRF_TEST_FILE = "AQU0101.DRF";

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testParse() {
		ClassLoader classLoader = DrfDecoder.class.getClassLoader();
		File file = new File(classLoader.getResource(DRF_TEST_FILE).getFile());
		SingleFileDrf drf = null;
		
		try {
			drf = SingleFileDrfParser.parse(file);
		} catch (IOException e) {
			e.printStackTrace();
			fail();
		}
		
		assertTrue(drf.getRaces().size() > 0);
		System.out.println("numRaces = " + drf.getRaces().size());
		for (DrfRace race : drf.getRaces()) {
			System.out.println("Race " + race.getRaceNum());
			System.out.println(race.getCompleteRaceConditions());
			System.out.println(race.getHorses().size() + " horses");
			for (DrfHorse h : race.getHorses()) {
				System.out.println(h.getName());
				DrfTrainer trainer = h.getTrainer();
				System.out.println("Trainer: " + trainer.getName());
				System.out.println("Post Position = " + h.getPostPosition());
				for (DrfStatCategory stat : trainer.getKeyStatCategories()) {
					System.out.println(stat.getLabel());
				}
				
				DrfJockey jockey = h.getJockey();
				System.out.println(jockey.getName());
				System.out.println(h.getWorkouts().size() + " workouts");
				for (DrfWorkout wo : h.getWorkouts()) {
					System.out.println(wo.getWorkoutDate() + ":" + wo.getTrackCode() + ":" + wo.getDistance() + ":" + wo.getWorkoutTime());
				}
				
				System.out.println(h.getPpraces().size() + " PP races");
				System.out.println();
				if (h.getPpraces().size() > 1) {
					System.out.println("bris spd rtg = " + h.getPpraces().get(0).getBrisSpeedRating());
					System.out.println("spd rtg = " + h.getPpraces().get(0).getSpeedRating());
					System.out.println("race classification = " + h.getPpraces().get(0).getRaceClassification());
					System.out.println("race type = " + h.getPpraces().get(0).getRaceType());
				}
			}
			System.out.println();
		}
	}

}
