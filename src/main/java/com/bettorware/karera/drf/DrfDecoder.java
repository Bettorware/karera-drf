package com.bettorware.karera.drf;

import java.io.File;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.bettorware.karera.drf.idl.DrfEquipmentChangeType;
import com.bettorware.karera.drf.idl.DrfMedicationType;
import com.bettorware.karera.drf.idl.DrfNasalStripChangeType;
import com.bettorware.karera.drf.idl.DrfRaceSurfaceType;
import com.bettorware.karera.drf.idl.DrfRaceType;
import com.bettorware.karera.drf.idl.DrfWorkoutTrackType;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvParser;

public class DrfDecoder {
	
	private final static Map<String, String> drfRaceSurfaceTypeMap = buildLookupMap("drfRaceSurfaceTypes.csv");
	private final static Map<String, String> drfRaceTypeMap = buildLookupMap("drfRaceTypes.csv");	
	private final static Map<String, String> drfWorkoutTrackTypeMap = buildLookupMap("drfWorkoutTrackTypes.csv");
	private final static Map<String, String> drfMedicationTypeMap = buildLookupMap("drfMedicationTypes.csv");
	private final static Map<String, String> drfEquipmentChangeTypeMap = buildLookupMap("drfEquipmentChangeTypes.csv");
	private final static Map<String, String> drfNasalStripChangeTypeMap = buildLookupMap("drfNasalStripChangeTypes.csv");
	private final static Map<String, String> drfTrackMap = buildLookupMap("drfTracks.csv");

	
	public static DrfRaceSurfaceType toRaceSurfaceType(String code) {
		String val = drfRaceSurfaceTypeMap.get(code);
		return StringUtils.isNotEmpty(val)?DrfRaceSurfaceType.valueOf(val):null;
	}
	
	public static DrfRaceType toRaceType(String code) {
		String val = drfRaceTypeMap.get(code);
		return StringUtils.isNotEmpty(val)?DrfRaceType.valueOf(val):null;
	}
	
	public static DrfWorkoutTrackType toWorkoutTrackType(String code) {
		String val = drfWorkoutTrackTypeMap.get(code);
		return StringUtils.isNotEmpty(val)?DrfWorkoutTrackType.valueOf(val):null;
	}
	
	public static DrfMedicationType toMedicationType(String code) {
		String val = drfMedicationTypeMap.get(code);
		return StringUtils.isNotEmpty(val)?DrfMedicationType.valueOf(val):null;
	}
	
	public static DrfEquipmentChangeType toEquipmentChangeType(String code) {
		String val = drfEquipmentChangeTypeMap.get(code);
		return StringUtils.isNotEmpty(val)?DrfEquipmentChangeType.valueOf(val):null;
	}
	
	public static DrfNasalStripChangeType toNasalStripChangeType(String code) {
		String val = drfNasalStripChangeTypeMap.get(code);
		return StringUtils.isNotEmpty(val)?DrfNasalStripChangeType.valueOf(val):null;
	}
	
	public static String toTrackName(String code) {
		return drfTrackMap.get(code);
	}
	
	private static Map<String, String> buildLookupMap(String fileName) {
		
		MappingIterator<String[]> mappingIter = null;
		
		try {
			ClassLoader classLoader = DrfDecoder.class.getClassLoader();
			File file = new File(classLoader.getResource(fileName).getFile());
			CsvMapper mapper = new CsvMapper();
			mapper.enable(CsvParser.Feature.WRAP_AS_ARRAY);
			mappingIter = mapper.reader(String[].class)
					.readValues(file);
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
		
		Map<String, String> lookupMap = new HashMap<String, String>();
		String[] line;
		while (mappingIter.hasNext()) {
			line = mappingIter.next();
			lookupMap.put(line[0], line[1]);	
		}
		
		return lookupMap;
	}

}
