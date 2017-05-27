package com.bettorware.karera.drf;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import org.apache.commons.lang.StringUtils;

import com.bettorware.karera.drf.idl.DrfHorse;
import com.bettorware.karera.drf.idl.DrfJockey;
import com.bettorware.karera.drf.idl.DrfPpRace;
import com.bettorware.karera.drf.idl.DrfRace;
import com.bettorware.karera.drf.idl.DrfStatCategory;
import com.bettorware.karera.drf.idl.DrfTrainer;
import com.bettorware.karera.drf.idl.DrfWorkout;
import com.bettorware.karera.drf.idl.SingleFileDrf;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvParser;

public class SingleFileDrfParser {
	
	public static SingleFileDrf parse(final Reader reader) {
		
		Objects.requireNonNull(reader);
		
		try {
			CsvMapper mapper = new CsvMapper();
			mapper.enable(CsvParser.Feature.WRAP_AS_ARRAY);
			MappingIterator<String[]> mappingIter = mapper.reader(String[].class)
					.readValues(reader);
			return parse(mappingIter);
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public static SingleFileDrf parse(final File file) throws IOException {
		
		Objects.requireNonNull(file);
		
		return parse(new InputStreamReader(new FileInputStream(file)));
	}

	public static SingleFileDrf parse(final InputStream inputStream) {
		
		Objects.requireNonNull(inputStream);
		
		return parse(new InputStreamReader(inputStream));
	}
	
	public static SingleFileDrf parse(final String string) {
		
		Objects.requireNonNull(string);
		
		return parse(new StringReader(string));
	}
	
	private static SingleFileDrf parse(MappingIterator<String[]> mappingIter) throws IOException {
		
		Objects.requireNonNull(mappingIter);
		
		SingleFileDrf.Builder drfBuilder = SingleFileDrf.newBuilder();
		DrfRace.Builder raceBuilder = null;
		
		String[] line = null;
		List<DrfRace> races = new ArrayList<DrfRace>();
		int raceNum = 0;
		boolean isFirstLine = true;
		
		while (true) {
			
			if (mappingIter.hasNext()) {
			
				line = mappingIter.nextValue();
				
				if (isFirstLine) {
					drfBuilder.setTrack(line[0]);
					drfBuilder.setMeetDate(line[1]);
					isFirstLine = false;
				}
				
				int tmpRaceNum = getInt(line[2]);
				
				if (tmpRaceNum != raceNum) {
					
					if (raceBuilder != null) {
						races.add(raceBuilder.build());
					}
					
					// Build new race
					raceNum = tmpRaceNum;
					raceBuilder = DrfRace.newBuilder();
					
					// Initialize horse list
					raceBuilder.setHorses(new ArrayList<DrfHorse>());
					
					raceBuilder
						.setTrack(getString(line[0]))
						.setRaceDate(getString(line[1]))
						.setRaceNum(raceNum)
						.setDistance(getDouble(line[5]))
						.setSurface(DrfDecoder.toRaceSurfaceType(line[6]))
						.setRaceType(DrfDecoder.toRaceType(line[8]))
						.setAgeSexRestrictions(getString(line[9]))
						.setRaceClassification(getString(line[10]))
						.setPurse(getInt(line[11]))
						.setClaimingPrice(getInt(line[12]))
						.setClaimingPriceOfHorse(getInt(line[13]))
						.setTrackRecord(getDouble(line[14]))
						.setRaceConditions(getString(line[15]))
						.setTodaysLasixList(getStringList(line[16]))
						.setTodaysButeList(getStringList(line[17]))
						.setTodaysCoupledList(getStringList(line[18]))
						.setTodaysMutuelList(getStringList(line[19]))
						.setSimulcastHostTrackCode(getString(line[20]))
						.setSimulcastHostTrackRaceNum(getInt(line[21]))
						.setAllWeatherSurface(getString(line[24])==null?false:true)
						.setBrisPaceParForLevel2f(getInt(line[213]))
						.setBrisPaceParForLevel4f(getInt(line[214]))
						.setBrisPaceParForLevel6f(getInt(line[215]))
						.setBrisSpeedParForLevel(getInt(line[216]))
						.setBrisLatePaceParForLevel(getInt(line[217]))
						.setLowClaimingPrice(getInt(line[237]))
						.setStatebred(getString(line[238])==null?false:true)
						.setPostTime(getString(line[1417]));
					
					// Complete race conditions
					StringBuilder condLines = new StringBuilder("");
					for (int i=224; i<230; i++) {
						condLines.append(line[i]);
					}
					raceBuilder.setCompleteRaceConditions(condLines.toString().trim().replace(';', ','));
				}
				
				DrfHorse.Builder horseBuilder = DrfHorse.newBuilder();
				horseBuilder
					.setOwner(getString(line[38]))
					.setOwnerSilks(getString(line[39]))
					.setMainTrackOnlyOrAE(getString(line[40]))
					.setProgramNumber(getString(line[42]))
					.setMorningLineOdds(getDouble(line[43]))
					.setName(getString(line[44]))
					.setYearOfBirth(getInt(line[45]))
					.setFoalingMonth(getInt(line[46]))
					.setSex(getString(line[48]))
					.setColor(getString(line[49]))
					.setWeight(getInt(line[50]))
					.setSire(getString(line[51]))
					.setSireOfSire(getString(line[52]))
					.setDam(getString(line[53]))
					.setSireOfDam(getString(line[54]))
					.setBreeder(getString(line[55]))
					.setWhereBred(getString(line[56]))
					.setPostPosition(getInt(line[57]))
					.setMedication(DrfDecoder.toMedicationType(line[61]))
					.setEquipmentChange(DrfDecoder.toEquipmentChangeType(line[63]))
					.setLifetimeStartsAtDistance(getInt(line[64]))
					.setLifetimeWinsAtDistance(getInt(line[65]))
					.setLifetimePlacesAtDistance(getInt(line[66]))
					.setLifetimeShowsAtDistance(getInt(line[67]))
					.setLifetimeEarningsAtDistance(getInt(line[68]))
					.setLifetimeStartsAtTrack(getInt(line[69]))
					.setLifetimeWinsAtTrack(getInt(line[70]))
					.setLifetimePlacesAtTrack(getInt(line[71]))
					.setLifetimeShowsAtTrack(getInt(line[72]))
					.setLifetimeEarningsAtTrack(getInt(line[73]))
					.setLifetimeStartsOnTurf(getInt(line[74]))
					.setLifetimeWinsOnTurf(getInt(line[75]))
					.setLifetimePlacesOnTurf(getInt(line[76]))
					.setLifetimeShowsOnTurf(getInt(line[77]))
					.setLifetimeEarningsOnTurf(getInt(line[78]))
					.setLifetimeStartsOnWet(getInt(line[79]))
					.setLifetimeWinsOnWet(getInt(line[80]))
					.setLifetimePlacesOnWet(getInt(line[81]))
					.setLifetimeShowsOnWet(getInt(line[82]))
					.setLifetimeEarningsOnWet(getInt(line[83]))
					.setCurrentYear(getInt(line[84]))
					.setCurrentYearStarts(getInt(line[85]))
					.setCurrentYearWins(getInt(line[86]))
					.setCurrentYearPlaces(getInt(line[87]))
					.setCurrentYearShows(getInt(line[88]))
					.setCurrentYearEarnings(getInt(line[89]))
					.setPreviousYear(getInt(line[90]))
					.setPreviousYearStarts(getInt(line[91]))
					.setPreviousYearWins(getInt(line[92]))
					.setPreviousYearPlaces(getInt(line[93]))
					.setPreviousYearShows(getInt(line[94]))
					.setPreviousYearEarnings(getInt(line[95]))
					.setLifetimeStarts(getInt(line[96]))
					.setLifetimeWins(getInt(line[97]))
					.setLifetimePlaces(getInt(line[98]))
					.setLifetimeShows(getInt(line[99]))
					.setLifetimeEarningsAtDistance(getInt(line[100]))
					.setBrisRunStyle(getString(line[209]))
					.setQuirinStyleSpeedPts(getInt(line[210]))
					.setTrainerJockeyStarts365D(getInt(line[218]))
					.setTrainerJockeyWins365D(getInt(line[219]))
					.setTrainerJockeyPlaces365D(getInt(line[220]))
					.setTrainerJockeyShows365D(getInt(line[221]))
					.setTrainerJockeyROI365D(getDouble(line[222]))
					.setDaysSinceLastRace(getInt(line[223]))
					.setLifetimeStartsAllWeatherSurf(getInt(line[230]))
					.setLifetimeWinsAllWeatherSurf(getInt(line[231]))
					.setLifetimePlacesAllWeatherSurf(getInt(line[232]))
					.setLifetimeShowsAllWeatherSurf(getInt(line[233]))
					.setLifetimeEarningsAllWeatherSurf(getInt(line[234]))
					.setBestBrisSpeedAllWeatherSurf(getInt(line[235]))
					.setBrisPrimePowerRtg(getDouble(line[250]))
					.setSireStudFee(getInt(line[1176]))
					.setBestBrisSpeedFast(getInt(line[1177]))
					.setBestBrisSpeedTurf(getInt(line[1178]))
					.setBestBrisSpeedOffTrack(getInt(line[1179]))
					.setBestBrisSpeedDistance(getInt(line[1180]))
					.setAuctionPrice(getInt(line[1221]))
					.setAuctionDateLocSold(getString(line[1222]))
					.setBrisDirtPedigreeRtg(getString(line[1263]))
					.setBrisMudPedigreeRtg(getString(line[1264]))
					.setBrisTurfPedigreeRtg(getString(line[1265]))
					.setBrisDistPedigreeRtg(getString(line[1266]))
					.setBestBrisSpeedLife(getInt(line[1327]))
					.setBestBrisSpeedMostRecentYr(getInt(line[1328]))
					.setBestBrisSpeed2ndMostRecentYr(getInt(line[1329]))
					.setBestBrisSpeedTodaysTrack(getInt(line[1330]))
					.setNumStartsFastDirt(getInt(line[1331]))
					.setNumWinsFastDirt(getInt(line[1332]))
					.setNumPlacesFastDirt(getInt(line[1333]))
					.setNumShowsFastDirt(getInt(line[1334]))
					.setEarningsFastDirt(getInt(line[1335]))
					.setTrainerJockeyStartsMeet(getInt(line[1412]))
					.setTrainerJockeyWinsMeet(getInt(line[1413]))
					.setTrainerJockeyPlacesMeet(getInt(line[1414]))
					.setTrainerJockeyShowsMeet(getInt(line[1415]))
					.setTrainerJockeyROIMeet(getDouble(line[1416]));
				
				DrfTrainer.Builder trainerBuilder = DrfTrainer.newBuilder();
				trainerBuilder.setKeyStatCategories(new ArrayList<DrfStatCategory>());
				trainerBuilder
					.setName(getString(line[27]))
					.setStartsCurrentMeet(getInt(line[28]))
					.setWinsCurrentMeet(getInt(line[29]))
					.setPlacesCurrentMeet(getInt(line[30]))
					.setShowsCurrentMeet(getInt(line[31]))
					.setStartsCurrentYear(getInt(line[1146]))
					.setWinsCurrentYear(getInt(line[1147]))
					.setPlacesCurrentYear(getInt(line[1148]))
					.setShowsCurrentYear(getInt(line[1149]))
					.setRoiCurrentYear(getDouble(line[1150]))
					.setStartsPreviousYear(getInt(line[1151]))
					.setWinsPreviousYear(getInt(line[1152]))
					.setPlacesPreviousYear(getInt(line[1153]))
					.setShowsPreviousYear(getInt(line[1154]))
					.setRoiPreviousYear(getDouble(line[1155]));
				
				// Add Key Trnr Stat Categories
				int ktscIdx = 1336;
				while(ktscIdx < 1366) {
					DrfStatCategory.Builder statCatBuilder = DrfStatCategory.newBuilder();
					String label = getString(line[ktscIdx++]);
					if (label != null) {
						statCatBuilder.setLabel(label);
						statCatBuilder.setStarts(getInt(line[ktscIdx++]));
						statCatBuilder.setWinPct(getDouble(line[ktscIdx++]));
						statCatBuilder.setInTheMoneyPct(getDouble(line[ktscIdx++]));
						statCatBuilder.setReturnOnInvestment(getDouble(line[ktscIdx++]));
						trainerBuilder.getKeyStatCategories().add(statCatBuilder.build());
					}
					else {
						break;
					}
				}
				horseBuilder.setTrainer(trainerBuilder.build());
				
				DrfJockey.Builder jockeyBuilder = DrfJockey.newBuilder();
				jockeyBuilder
					.setName(getString(line[32]))
					.setApprenticeWtAllow(getInt(line[33]))
					.setStartsCurrentMeet(getInt(line[34]))
					.setWinsCurrentMeet(getInt(line[35]))
					.setPlacesCurrentMeet(getInt(line[36]))
					.setShowsCurrentMeet(getInt(line[37]))
					.setStartsCurrentYear(getInt(line[1156]))
					.setWinsCurrentYear(getInt(line[1157]))
					.setPlacesCurrentYear(getInt(line[1158]))
					.setShowsCurrentYear(getInt(line[1159]))
					.setRoiCurrentYear(getDouble(line[1160]))
					.setStartsPreviousYear(getInt(line[1161]))
					.setWinsPreviousYear(getInt(line[1162]))
					.setPlacesPreviousYear(getInt(line[1163]))
					.setShowsPreviousYear(getInt(line[1164]))
					.setRoiPreviousYear(getDouble(line[1165]))
					.setDistTurfLabel(getString(line[1366]))
					.setDistTurfStarts(getInt(line[1367]))
					.setDistTurfWins(getInt(line[1368]))
					.setDistTurfPlaces(getInt(line[1369]))
					.setDistTurfShows(getInt(line[1370]))
					.setDistTurfROI(getDouble(line[1371]))
					.setDistTurfEarnings(getInt(line[1372]));
				horseBuilder.setJockey(jockeyBuilder.build());
				
				// Workouts
				List<DrfWorkout> workouts = new ArrayList<DrfWorkout>();
				for (int i=0; i<12; i++) {
					String woDate = getString(line[101+i]);
					if (StringUtils.isNotEmpty(woDate)) {
						DrfWorkout.Builder workoutBuilder = DrfWorkout.newBuilder();
						workoutBuilder
							.setWorkoutDate(woDate)
							.setWorkoutTime(getDouble(line[113+i]))
							.setTrackCode(getString(line[125+i]))
							.setDistance(getDouble(line[137+i]))
							.setTrackCondition(getString(line[149+i]))
							.setWorkoutDescription(getString(line[161+i]))
							.setTrackType(DrfDecoder.toWorkoutTrackType(getString(line[173+i])))
							.setNumWorkouts(getInt(line[185+i]))
							.setWorkoutRank(getInt(line[197+i]));
						workouts.add(workoutBuilder.build());
					}
					else {
						break;
					}
				}
				horseBuilder.setWorkouts(workouts);
				
				// PP's
				List<DrfPpRace> ppRaces = new ArrayList<DrfPpRace>();
				for (int i=0; i<10; i++) {
					String raceDate = getString(line[255+i]);
					if (StringUtils.isNotEmpty(raceDate)) {
						DrfPpRace.Builder ppBuilder = DrfPpRace.newBuilder();
						ppBuilder
							.setRaceDate(raceDate)
							.setDaysSincePrevious(getInt(line[265+i]))
							.setTrackCode(getString(line[275+i]))
							.setBrisTrackCode(getString(line[285+i]))
							.setRaceNum(getInt(line[295+i]))
							.setTrackCondition(getString(line[305+i]))
							.setDistance(getInt(line[315+i]))
							.setSurface(DrfDecoder.toRaceSurfaceType(getString(line[325+i])))
							.setSpecialChute(getString(line[335+i])==null?false:true)
							.setNumEntrants(getInt(line[345+i]))
							.setPostPosition(getInt(line[355+i]))
							.setEquipment(getString(line[365+i]))
							.setRaceName(getString(line[375+i]))
							.setMedication(DrfDecoder.toMedicationType(getString(line[385+i])))
							.setTripComment(getString(line[395+i]))
							.setWinnerName(getString(line[405+i]))
							.setSecondPlaceName(getString(line[415+i]))
							.setThirdPlaceName(getString(line[425+i]))
							.setWinnerWeightCarried(getInt(line[435+i]))
							.setSecondPlaceWeightCarried(getInt(line[445+i]))
							.setThirdPlaceWeightCarried(getInt(line[455+i]))
							.setWinnerMargin(getDouble(line[465+i]))
							.setSecondPlaceMargin(getDouble(line[475+i]))
							.setThirdPlaceMargin(getDouble(line[485+i]))
							.setAltExtraComment(getString(line[495+i]))
							.setWeight(getInt(line[505]))
							.setOdds(getDouble(line[515]))
							.setIsEntry(getString(line[525+i])==null?false:true)
							.setRaceClassification(getString(line[535+i]))
							.setClaimingPrice(getInt(line[545+i]))
							.setPurse(getInt(line[555+i]))
							.setStartCallPosition(getString(line[565+i]))
							.setFirstCallPosition(getString(line[575+i]))
							.setSecondCallPosition(getString(line[585+i]))
							.setGateCallPosition(getString(line[595+i]))
							.setStretchPosition(getString(line[605+i]))
							.setFinishPosition(getString(line[615+i]))
							.setMoneyPosition(getString(line[625+i]))
							.setStartCallBtnLdrLengths(getDouble(line[635+i]))
							.setStartCallBtnLengthsOnly(getDouble(line[645+i]))
							.setFirstCallBtnLdrLengths(getDouble(line[655+i]))
							.setFirstCallBtnLengthsOnly(getDouble(line[665+i]))
							.setSecondCallBtnLdrLengths(getDouble(line[675+i]))
							.setSecondCallBtnLengthsOnly(getDouble(line[685+i]))
							.setBrisRaceShapeFirstCall(getInt(line[695+i]))
							.setStretchBtnLdrLengths(getDouble(line[715+i]))
							.setStretchBtnLengthsOnly(getDouble(line[725+i]))
							.setFinishBtnLdrLengths(getDouble(line[735+i]))
							.setFinishBtnLengthsOnly(getDouble(line[745+i]))
							.setBrisRaceShapeSecondCall(getInt(line[755+i]))
							.setBrisPaceFig2F(getInt(line[765+i]))
							.setBrisPaceFig4F(getInt(line[775+i]))
							.setBrisPaceFig6F(getInt(line[785+i]))
							.setBrisPaceFig8F(getInt(line[795+i]))
							.setBrisPaceFig10F(getInt(line[805+i]))
							.setBrisLatePaceFig(getInt(line[815+i]))
							.setBrisSpeedRating(getInt(line[845+i]))
							.setSpeedRating(getInt(line[855+i]))
							.setTrackVariant(getInt(line[865+i]))
							.setFraction2F(getDouble(line[875+i]))
							.setFraction3F(getDouble(line[885+i]))
							.setFraction4F(getDouble(line[895+i]))
							.setFraction5F(getDouble(line[905+i]))
							.setFraction6F(getDouble(line[915+i]))
							.setFraction7F(getDouble(line[925+i]))
							.setFraction8F(getDouble(line[935+i]))
							.setFraction10F(getDouble(line[945+i]))
							.setFraction12F(getDouble(line[955+i]))
							.setFraction14F(getDouble(line[965+i]))
							.setFraction16F(getDouble(line[975+i]))
							.setFraction1(getDouble(line[985+i]))
							.setFraction2(getDouble(line[995+i]))
							.setFraction3(getDouble(line[1005+i]))
							.setFinalTime(getDouble(line[1035+i]))
							.setClaimed(getString(line[1045+i])==null?false:true)
							.setTrainer(getString(line[1055+i]))
							.setJockey(getString(line[1065+i]))
							.setApprenticeWtAllow(getInt(line[1075+i]))
							.setRaceType(DrfDecoder.toRaceType(getString(line[1085+i])))
							.setAgeSexRestrictions(getString(line[1095+i]))
							.setStatebred((getString(line[1105+i])==null?false:true))
							.setRestrictedQualifierFlag(getString(line[1115+i]))
							.setFavorite((getInt(line[1125+i])==1?true:false))
							.setFrontBandages((getInt(line[1135+i])==1?true:false))
							.setBrisSpeedParForLevel(getInt(line[1166+i]))
							.setBarShoe(getString(line[1181+i])==null?false:true)
							.setLowClaimingPrice(getInt(line[1201+i]))
							.setHighClaimingPrice(getInt(line[1211+i]))
							.setNasalStripOrOffTurf(getString(line[1253+i]))
							.setClaimedFromAndTrainerSwitch1(getString(line[1267+i]))
							.setClaimedFromAndTrainerSwitch2(getInt(line[1277+i]))
							.setClaimedFromAndTrainerSwitch3(getInt(line[1287+i]))
							.setClaimedFromAndTrainerSwitch4(getInt(line[1297+i]))
							.setClaimedFromAndTrainerSwitch5(getInt(line[1307+i]))
							.setClaimedFromAndTrainerSwitch6(getDouble(line[1317+i]))
							.setExtendedStartComment(getString(line[1382+i]))
							.setSealedTrack((getString(line[1392+i])==null?false:true))
							.setAllWeatherSurface((getString(line[1402+i])==null?false:true));
						
						ppRaces.add(ppBuilder.build());
					}
					else {
						break;
					}
				}
				horseBuilder.setPpraces(ppRaces);
				
				raceBuilder.getHorses().add(horseBuilder.build());
			}
			else {
				// add last race
				races.add(raceBuilder.build());
				break;
			}
			
			
		} // end while
		
		drfBuilder.setRaces(races);
		
		return drfBuilder.build();
	}
	
	private static final String getString(String str) {
		return StringUtils.isNotEmpty(str)?str.trim():null;
	}
	
	private static final double getDouble(String str) {
		return StringUtils.isNotEmpty(str)?Double.parseDouble(str.trim()):0.0;
	}
	
	private static final int getInt(String str) {
		return StringUtils.isNotEmpty(str)?Integer.parseInt(str.trim()):0;
	}
	
	private static final List<String> getStringList(String str) {
		List<String> strList = null;
		if (StringUtils.isNotBlank(str)) {
			strList = new ArrayList<String>(Arrays.asList(str.trim().split(",")));
		}
		return strList;
	}

}
