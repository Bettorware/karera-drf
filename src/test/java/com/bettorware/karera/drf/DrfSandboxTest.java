package com.bettorware.karera.drf;

import java.io.File;

import org.apache.commons.lang.StringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvParser;

public class DrfSandboxTest {
	
	private static final String drfTestFile = "AQU0101.DRF";
	MappingIterator<String[]> mappingIter = null;

	@Before
	public void setUp() throws Exception {
		try {
			ClassLoader classLoader = DrfDecoder.class.getClassLoader();
			File file = new File(classLoader.getResource(drfTestFile).getFile());
			CsvMapper mapper = new CsvMapper();
			mapper.enable(CsvParser.Feature.WRAP_AS_ARRAY);
			mappingIter = mapper.reader(String[].class)
					.readValues(file);
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testPrintValsForIndex() {
		
		// Race Number
		int raceIdx = 2;
		
		int index = 1095;
		
		String[] line = null;
		while (mappingIter.hasNext()) {
			line = mappingIter.next();
			String val = null;
			if (line[index].equals("")) {
				val = "empty string";
			}
			else if (line[index] == null) {
				val = "null";
			}
			else {
				val = line[index];
			}
			System.out.println(line[raceIdx] + ", " + val);
		}		
	}
	
	@Test
	public void testPrintTrainerStatCats() {
		
		String[] line = null;
		while (mappingIter.hasNext()) {
			line = mappingIter.next();
			System.out.println("Race " + line[2] + ": ");
			System.out.println(line[44]);
			System.out.println(line[1341]);
			System.out.println(line[1346]);
			System.out.println(line[1351]);
			System.out.println(line[1356]);
			System.out.println(line[1361]);
			System.out.println();
		}		
	}
	
	@Test
	public void testPrintWagerTypes() {
		
		String[] line = null;
		while (mappingIter.hasNext()) {
			line = mappingIter.next();
			System.out.println("Race " + line[2] + ": ");
			for (int i=239; i<248; i++) {
				if (StringUtils.isNotEmpty(line[i])) {
					System.out.println(line[i]);
				}
			}
			System.out.println();;
		}
	}

}
