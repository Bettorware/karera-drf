package com.bettorware.karera.drf;

import static org.junit.Assert.*;

import org.junit.Test;

import com.bettorware.karera.drf.idl.DrfEquipmentChangeType;
import com.bettorware.karera.drf.idl.DrfMedicationType;
import com.bettorware.karera.drf.idl.DrfNasalStripChangeType;
import com.bettorware.karera.drf.idl.DrfRaceSurfaceType;
import com.bettorware.karera.drf.idl.DrfRaceType;
import com.bettorware.karera.drf.idl.DrfWorkoutTrackType;

public class DrfDecoderTest {

	@Test
	public void testToRaceSurfaceType() {
		assertEquals(DrfRaceSurfaceType.DIRT, DrfDecoder.toRaceSurfaceType("D"));
		assertEquals(DrfRaceSurfaceType.TURF, DrfDecoder.toRaceSurfaceType("T"));
		assertEquals(DrfRaceSurfaceType.INNER_DIRT, DrfDecoder.toRaceSurfaceType("d"));
		assertEquals(DrfRaceSurfaceType.INNER_TURF, DrfDecoder.toRaceSurfaceType("t"));
		assertEquals(DrfRaceSurfaceType.STEEPLECHASE, DrfDecoder.toRaceSurfaceType("s"));
		assertEquals(DrfRaceSurfaceType.HUNT, DrfDecoder.toRaceSurfaceType("h"));
	}

	@Test
	public void testToRaceType() {
		assertEquals(DrfRaceType.GRADE_1, DrfDecoder.toRaceType("G1"));
		assertEquals(DrfRaceType.GRADE_2, DrfDecoder.toRaceType("G2"));
		assertEquals(DrfRaceType.GRADE_3, DrfDecoder.toRaceType("G3"));
		assertEquals(DrfRaceType.NON_GRADED, DrfDecoder.toRaceType("N"));
		assertEquals(DrfRaceType.ALLOWANCE, DrfDecoder.toRaceType("A"));
		assertEquals(DrfRaceType.STARTER_ALLOWANCE, DrfDecoder.toRaceType("R"));
		assertEquals(DrfRaceType.STARTER_HANDICAP, DrfDecoder.toRaceType("T"));
		assertEquals(DrfRaceType.CLAIMING, DrfDecoder.toRaceType("C"));
		assertEquals(DrfRaceType.OPTIONAL_CLAIMING, DrfDecoder.toRaceType("CO"));
		assertEquals(DrfRaceType.MAIDEN_SPECIAL_WEIGHT, DrfDecoder.toRaceType("S"));
		assertEquals(DrfRaceType.MAIDEN_CLAIMING, DrfDecoder.toRaceType("M"));
		assertEquals(DrfRaceType.ALLOWANCE_OPTIONAL_CLAIMING, DrfDecoder.toRaceType("AO"));
		assertEquals(DrfRaceType.MAIDEN_OPTIONAL_CLAIMING, DrfDecoder.toRaceType("MO"));
		assertEquals(DrfRaceType.OPTIONAL_CLAIMING_STAKES, DrfDecoder.toRaceType("NO"));
	}

	@Test
	public void testToWorkoutTrackType() {
		assertEquals(DrfWorkoutTrackType.MAIN_DIRT, DrfDecoder.toWorkoutTrackType("MT"));
		assertEquals(DrfWorkoutTrackType.INNER_DIRT, DrfDecoder.toWorkoutTrackType("IM"));
		assertEquals(DrfWorkoutTrackType.TRAINING_TRACK, DrfDecoder.toWorkoutTrackType("TT"));
		assertEquals(DrfWorkoutTrackType.MAIN_TURF, DrfDecoder.toWorkoutTrackType("T"));
		assertEquals(DrfWorkoutTrackType.INNER_TURF, DrfDecoder.toWorkoutTrackType("IT"));
		assertEquals(DrfWorkoutTrackType.WOOD_CHIP, DrfDecoder.toWorkoutTrackType("WC"));
		assertEquals(DrfWorkoutTrackType.HILLSIDE_COURSE, DrfDecoder.toWorkoutTrackType("HC"));
		assertEquals(DrfWorkoutTrackType.TURF_TRAINING_TRACK, DrfDecoder.toWorkoutTrackType("TN"));
		assertEquals(DrfWorkoutTrackType.INNER_TURF_TRAINING_TRACK, DrfDecoder.toWorkoutTrackType("IN"));
		assertEquals(DrfWorkoutTrackType.TRAINING_RACE, DrfDecoder.toWorkoutTrackType("TR"));
	}

	@Test
	public void testToMedicationType() {
		assertEquals(DrfMedicationType.NONE, DrfDecoder.toMedicationType("0"));
		assertEquals(DrfMedicationType.LASIX, DrfDecoder.toMedicationType("1"));
		assertEquals(DrfMedicationType.BUTE, DrfDecoder.toMedicationType("2"));
		assertEquals(DrfMedicationType.BUTE_LASIX, DrfDecoder.toMedicationType("3"));
		assertEquals(DrfMedicationType.LASIX_FIRST, DrfDecoder.toMedicationType("4"));
		assertEquals(DrfMedicationType.BUTE_LASIX_FIRST, DrfDecoder.toMedicationType("5"));
		assertEquals(DrfMedicationType.UNAVAILABLE, DrfDecoder.toMedicationType("9"));
	}

	@Test
	public void testToEquipmentChangeType() {
		assertEquals(DrfEquipmentChangeType.NO_CHANGE, DrfDecoder.toEquipmentChangeType("0"));
		assertEquals(DrfEquipmentChangeType.BLINKERS_ON, DrfDecoder.toEquipmentChangeType("1"));
		assertEquals(DrfEquipmentChangeType.BLINKERS_OFF, DrfDecoder.toEquipmentChangeType("2"));
		assertEquals(DrfEquipmentChangeType.UNAVAILABLE, DrfDecoder.toEquipmentChangeType("9"));
	}
	
	@Test
	public void testToNasalStripChangeType() {
		assertEquals(DrfNasalStripChangeType.NO_CHANGE, DrfDecoder.toNasalStripChangeType("0"));
		assertEquals(DrfNasalStripChangeType.NASAL_STRIP_ON, DrfDecoder.toNasalStripChangeType("1"));
		assertEquals(DrfNasalStripChangeType.NASAL_STRIP_OFF, DrfDecoder.toNasalStripChangeType("2"));
		assertEquals(DrfNasalStripChangeType.UNAVAILABLE, DrfDecoder.toNasalStripChangeType("9"));
	}
	
	@Test
	public void testToTrackName() {
		assertEquals("Aqueduct", DrfDecoder.toTrackName("AQU"));
		assertEquals("Churchill Downs", DrfDecoder.toTrackName("CD"));
	}


}
