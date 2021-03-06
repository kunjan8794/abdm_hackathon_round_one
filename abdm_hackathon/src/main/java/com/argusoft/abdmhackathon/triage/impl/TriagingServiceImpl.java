package com.argusoft.abdmhackathon.triage.impl;

import com.argusoft.abdmhackathon.common.util.ConstantUtil;
import com.argusoft.abdmhackathon.question.dao.QuestionMasterDao;
import com.argusoft.abdmhackathon.triage.TriagingService;
import com.argusoft.abdmhackathon.triage.dto.TriagingResultsDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class TriagingServiceImpl implements TriagingService {

    @Autowired
    QuestionMasterDao questionMasterDao;

    @Override
    public List<TriagingResultsDto> doAllTriage(Map<Integer, String> mapOfAnswers, Map<String, String> previousClassifications, String preferredLanguage) {
        List<TriagingResultsDto> results = new LinkedList<>();
        checkForCoughSymptoms(mapOfAnswers, results, preferredLanguage);
        checkForDiarrhoea(mapOfAnswers, results, preferredLanguage);
        checkForFeverSymptoms(mapOfAnswers, results, preferredLanguage);
        checkForMeaslesSymptoms(mapOfAnswers, results, preferredLanguage);
        removeMultipleClassificationsForAllTraige(results, preferredLanguage);
        return results;
    }

    @Override
    public Map<String, String> doTriage(Map<Integer, String> mapOfAnswers, Map<String, String> previousClassifications) {
        Map<String, String> results = new LinkedHashMap<>();
        checkForSeverePneumonia(mapOfAnswers, results);
        checkForPneumonia(mapOfAnswers, results);
        removeMultipleClassifications(results);
        removePreviousClassifications(results, previousClassifications);
        checkForCoughOrCold(mapOfAnswers, results);
        checkForDiarrhoeaWithSevereDehydration(mapOfAnswers, results);
        checkForDiarrhoeaWithSomeDehydration(mapOfAnswers, results);
        return results;
    }

    public void checkForCoughSymptoms(Map<Integer, String> mapOfAnswers, List<TriagingResultsDto> results, String preferredLanguage) {
        String symptoms = mapOfAnswers.get(23);
        TriagingResultsDto pneumoniaResult = new TriagingResultsDto();
        TriagingResultsDto severePneumoniaResult = new TriagingResultsDto();
        List<String> pneumoniaSuggestions = new ArrayList<>();
        List<String> pneumoniaSymptoms = new ArrayList<>();
        List<String> severePneumoniaSuggestions = new ArrayList<>();
        List<String> severePneumoniaSymptoms = new ArrayList<>();

        if (symptoms != null) {

            if (symptoms.contains("STRIDOR_IN_CHILD")) {
                severePneumoniaSymptoms.add(questionMasterDao.getQuestionOptionByPreferredLanguage(23, "STRIDOR_IN_CHILD", preferredLanguage));
            }

            if (symptoms.contains("OXYGEN_SATURATION_LT90")) {
                severePneumoniaSymptoms.add(questionMasterDao.getQuestionOptionByPreferredLanguage(23, "OXYGEN_SATURATION_LT90", preferredLanguage));
            }

            if (symptoms.contains("STRIDOR_IN_CHILD") || symptoms.contains("OXYGEN_SATURATION_LT90")) {
            }

            if (severePneumoniaSymptoms.size() > 0) {
                severePneumoniaResult.setDisease(ConstantUtil.getKeyByLanguage("SEVERE_PNEUMONIA", preferredLanguage));
                severePneumoniaResult.setSymptoms(severePneumoniaSymptoms);
                severePneumoniaSuggestions.add(ConstantUtil.getKeyByLanguage("SEVERE_PNEUMONIA_SUGGESTION1", preferredLanguage));
                severePneumoniaSuggestions.add(ConstantUtil.getKeyByLanguage("SEVERE_PNEUMONIA_SUGGESTION2", preferredLanguage));
                severePneumoniaSuggestions.add(ConstantUtil.getKeyByLanguage("SEVERE_PNEUMONIA_SUGGESTION3", preferredLanguage));
                severePneumoniaResult.setSuggestions(severePneumoniaSuggestions);
                severePneumoniaResult.setCode("PNEUMONIA");
                results.add(severePneumoniaResult);
            } else {
                if (symptoms.contains("COUGH_GT14")) {
                    pneumoniaSymptoms.add(questionMasterDao.getQuestionOptionByPreferredLanguage(23, "COUGH_GT14", preferredLanguage));
                }

                if (symptoms.contains("CHEST_INDRAWING")) {
                    pneumoniaSymptoms.add(questionMasterDao.getQuestionOptionByPreferredLanguage(23, "CHEST_INDRAWING", preferredLanguage));
                }

                if (symptoms.contains("RECURRENT_WHEEZING")) {
                    pneumoniaSymptoms.add(questionMasterDao.getQuestionOptionByPreferredLanguage(23, "RECURRENT_WHEEZING", preferredLanguage));
                }

                if (symptoms.contains("DIFFICULTY_BREATHING_GT14")) {
                    pneumoniaSymptoms.add(questionMasterDao.getQuestionOptionByPreferredLanguage(23, "DIFFICULTY_BREATHING_GT14", preferredLanguage));
                }

                if (pneumoniaSymptoms.size() > 0) {
                    pneumoniaResult.setDisease(ConstantUtil.getKeyByLanguage("PNEUMONIA", preferredLanguage));
                    pneumoniaResult.setCode("PNEUMONIA");
                    pneumoniaResult.setSymptoms(pneumoniaSymptoms);
                    pneumoniaSuggestions.add(ConstantUtil.getKeyByLanguage("SEVERE_PNEUMONIA_SUGGESTION1", preferredLanguage));
                    pneumoniaSuggestions.add(ConstantUtil.getKeyByLanguage("SEVERE_PNEUMONIA_SUGGESTION2", preferredLanguage));
                    pneumoniaSuggestions.add(ConstantUtil.getKeyByLanguage("SEVERE_PNEUMONIA_SUGGESTION3", preferredLanguage));
                    pneumoniaResult.setSuggestions(pneumoniaSuggestions);
                    results.add(pneumoniaResult);
                }
            }
        }
    }

    public void checkForDiarrhoea(Map<Integer, String> mapOfAnswers, List<TriagingResultsDto> results, String preferredLanguage) {
        String symptoms = mapOfAnswers.get(25);
        TriagingResultsDto someDehydrationResult = new TriagingResultsDto();
        TriagingResultsDto severeDehydrationResult = new TriagingResultsDto();
        List<String> someDehydrationSuggestions = new ArrayList<>();
        List<String> someDehydrationSymptoms = new ArrayList<>();
        List<String> severeDehydrationSuggestions = new ArrayList<>();
        List<String> severeDehydrationSymptoms = new ArrayList<>();

        if (symptoms != null) {
            Boolean isUnableToDrinkWater = symptoms.contains("COMPLETELY_UNABLE_TO_DRINK_WATER");
            Boolean isVomitsImmediately = symptoms.contains("VOMITS_IMMEDIATELY_OR_EVERYTHING");
            Boolean isDrinksPoorly = symptoms.contains("DRINKS_POORLY");
            Boolean isDrinksEagerly = symptoms.contains("DRINKS_EAGERLY_OR_THIRSTILY");

            if (symptoms.contains("SUNKEN_EYES") && symptoms.contains("SKIN_PINCH_VERY_SLOWLY") && (isUnableToDrinkWater || isVomitsImmediately || isDrinksPoorly)) {
                if (isUnableToDrinkWater)
                    severeDehydrationSymptoms.add(questionMasterDao.getQuestionOptionByPreferredLanguage(25, "COMPLETELY_UNABLE_TO_DRINK_WATER", preferredLanguage));
                if (isVomitsImmediately)
                    severeDehydrationSymptoms.add(questionMasterDao.getQuestionOptionByPreferredLanguage(25, "VOMITS_IMMEDIATELY_OR_EVERYTHING", preferredLanguage));
                if (isDrinksPoorly)
                    severeDehydrationSymptoms.add(questionMasterDao.getQuestionOptionByPreferredLanguage(25, "DRINKS_POORLY", preferredLanguage));

                severeDehydrationSymptoms.add(questionMasterDao.getQuestionOptionByPreferredLanguage(25, "SUNKEN_EYES", preferredLanguage));
                severeDehydrationSymptoms.add(questionMasterDao.getQuestionOptionByPreferredLanguage(25, "SKIN_PINCH_VERY_SLOWLY", preferredLanguage));
            }

            if (severeDehydrationSymptoms.size() > 0) {
                severeDehydrationResult.setDisease(ConstantUtil.getKeyByLanguage("DIARRHOEA_SEVERE_DEHYDRATION", preferredLanguage));
                severeDehydrationResult.setSymptoms(severeDehydrationSymptoms);
                severeDehydrationSuggestions.add(ConstantUtil.getKeyByLanguage("DIARRHOEA_SUGGESSION1", preferredLanguage));
                severeDehydrationSuggestions.add(ConstantUtil.getKeyByLanguage("DIARRHOEA_SUGGESSION2", preferredLanguage));
                severeDehydrationSuggestions.add(ConstantUtil.getKeyByLanguage("DIARRHOEA_SUGGESSION3", preferredLanguage));
                severeDehydrationSuggestions.add(ConstantUtil.getKeyByLanguage("DIARRHOEA_SUGGESSION4", preferredLanguage));
                severeDehydrationResult.setSuggestions(severeDehydrationSuggestions);
                severeDehydrationResult.setCode("DIARRHOEA");
                results.add(severeDehydrationResult);
            } else {
                List<String> symptomsList = new ArrayList<>();
                symptomsList.add("SUNKEN_EYES");
                symptomsList.add("SKIN_PINCH_VERY_SLOWLY");
                symptomsList.add("RESTLESS_IRRITABLE");
                symptomsList.add("SKIN_PINCH_SLOWLY");
                Integer symptomCount = 0;
                for (String s : symptomsList) {
                    if (symptoms.contains(s)) {
                        symptomCount++;
                        someDehydrationSymptoms.add(questionMasterDao.getQuestionOptionByPreferredLanguage(25, s, preferredLanguage));
                    }
                }
                if (symptomCount >= 2 && (isUnableToDrinkWater || isDrinksEagerly || isDrinksPoorly)) {
                    if (isUnableToDrinkWater)
                        severeDehydrationSymptoms.add(questionMasterDao.getQuestionOptionByPreferredLanguage(25, "COMPLETELY_UNABLE_TO_DRINK_WATER", preferredLanguage));
                    if (isDrinksEagerly)
                        severeDehydrationSymptoms.add(questionMasterDao.getQuestionOptionByPreferredLanguage(25, "DRINKS_EAGERLY_OR_THIRSTILY", preferredLanguage));
                    if (isDrinksPoorly)
                        severeDehydrationSymptoms.add(questionMasterDao.getQuestionOptionByPreferredLanguage(25, "DRINKS_POORLY", preferredLanguage));

//                    someDehydrationSuggestions.add(ConstantUtil.getKeyByLanguage("ORAL_FLUID_TEST_RECOMMENDATION_FOR_SOME_DEHYDRATION_DESC", preferredLanguage));
                    someDehydrationResult.setDisease(ConstantUtil.getKeyByLanguage("DIARRHOEA_SOME_DEHYDRATION", preferredLanguage));
                    someDehydrationResult.setSymptoms(someDehydrationSymptoms);
                    someDehydrationSuggestions.add(ConstantUtil.getKeyByLanguage("DIARRHOEA_SUGGESSION1", preferredLanguage));
                    someDehydrationSuggestions.add(ConstantUtil.getKeyByLanguage("DIARRHOEA_SUGGESSION2", preferredLanguage));
                    someDehydrationSuggestions.add(ConstantUtil.getKeyByLanguage("DIARRHOEA_SUGGESSION3", preferredLanguage));
                    someDehydrationSuggestions.add(ConstantUtil.getKeyByLanguage("DIARRHOEA_SUGGESSION4", preferredLanguage));
                    someDehydrationResult.setSuggestions(someDehydrationSuggestions);
                    someDehydrationResult.setCode("DIARRHOEA");
                    results.add(someDehydrationResult);
                }
            }

        }
    }

    private static void checkForSeverePneumonia(Map<Integer, String> mapOfAnswers, Map<String, String> results) {
        //IF STRIDER IN CALM CHILD = YES
        //OR
        //IF OXYGEN SATURATION < 90%
        String foundResult = null;
        String ifForStriderInCalmChild = mapOfAnswers.get(9);
        String oxygenSaturation = mapOfAnswers.get(12);

//        if (Objects.equals(ifForStriderInCalmChild, "YES")) {
//            results.put(ConstantUtil.SEVERE_PNEUMONIA, ConstantUtil.SEVERE_PNEUMONIA_STRIDOR_DESC);
//        }
//        if (Objects.equals(oxygenSaturation, "LT90")) {
//            results.put(ConstantUtil.SEVERE_PNEUMONIA, ConstantUtil.SEVERE_PNEUMONIA_OXY_SAT_DESC);
//        }
//
//        if (Objects.equals(oxygenSaturation, "LT90") && Objects.equals(ifForStriderInCalmChild, "YES")) {
//            results.put(ConstantUtil.SEVERE_PNEUMONIA, ConstantUtil.SEVERE_PNEUMONIA_DESC);
//        }
    }

    private static void checkForPneumonia(Map<Integer, String> mapOfAnswers, Map<String, String> results) {
        //IF CHEST INDRAWING  = YES
        //AND
        //IF CONFIRMED HIV INFECTION OR HIV EXPOSED
        // AND
        // IF CHEST INDRAWING (POST INHALED BRONCHODILATOR TRIAL) OR INHALE BRONCHODILATOR TRIAL NOT FEASIBLE/AVAILABLE

        //WHEEZING AND RECURRENT WHEEZE
        //OR
        //COUGH FOR HOW LONG?  >=14
        //OR
        //DIFFICULTY BREATHING FOR HOW LONG? >=14
        if (!(mapOfAnswers.containsKey(7) && mapOfAnswers.containsKey(10) && mapOfAnswers.containsKey(4)) && mapOfAnswers.containsKey(11) && mapOfAnswers.containsKey(14)) {
            return;
        }
        String foundResult = null;

        String chestIndrawing = mapOfAnswers.get(7);
        String wheezing = mapOfAnswers.get(10);
        String recurrentWheezing = mapOfAnswers.get(11);
//        String coughHowLong = mapOfAnswers.get(4);
        String difficultyInBreathing = mapOfAnswers.get(14);

//        if (chestIndrawing != null && chestIndrawing.equals("YES")) {
//            results.put(ConstantUtil.PNEUMONIA, ConstantUtil.PNEUMONIA_CHEST_INDRAWING_DESC);
//        }
//
//        if (wheezing != null && recurrentWheezing != null && wheezing.equals("YES") && recurrentWheezing.equals("YES"))
//            results.put(ConstantUtil.PNEUMONIA, ConstantUtil.PNEUMONIA_DESC_WHEEZING);
//        if (coughHowLong != null && coughHowLong.equals("GTE14"))
//            results.put(PNEUMONIA, PNEUMONIA_COUGH_GTE14_DESC);
//        if (difficultyInBreathing != null && difficultyInBreathing.equals("GTE14"))
//            results.put(ConstantUtil.PNEUMONIA, ConstantUtil.PNEUMONIA_DIFFICULTY_BREATHING_DESC);


        //WHEEZING = YES
        //AND
        //CHEST INDRAWING AND NOT HIV EXPOSED
        //OR
        //FAST BREATHING

        //WHEEZING
        //AND
        //FAST BREATHING OR  CHEST INDRAWING
        //AND
        //INHALE BRONCHODILATOR TRIAL NOT FEASIBLE/AVAILABLE

        //CHEST INDRAWING
        //AND
        //NOT HIV EXPOSED/INFECTED

        // FAST BREATHING
    }

    private static void checkForCoughOrCold(Map<Integer, String> mapOfAnswers, Map<String, String> results) {
        //WHEEZING

        //(COUGH =YES OR  DIFFICULTY BREATHING = YES)
        //AND
        //(NO FAST BREATHING AND NO CHEST INDRAWING)
        String foundResult = null;
        String wheezing = mapOfAnswers.get(10);
        String cough = mapOfAnswers.get(3);
        String difficultyInBreathing = mapOfAnswers.get(13);
        String noFastBreathing = mapOfAnswers.get(6);
        String noChestIndrawing = mapOfAnswers.get(7);
        if (wheezing != null && wheezing.equals("YES")) {
//            results.put(ConstantUtil.COUGHORCOLD, ConstantUtil.COUGHORCOLD_DESC);
        }
        if (((cough != null && cough.equals("YES")) || (difficultyInBreathing != null && difficultyInBreathing.equals("YES"))) && ((noFastBreathing != null && !noFastBreathing.equals("GTE16")) && (noChestIndrawing != null && !noChestIndrawing.equals("YES")))) {
//            results.put(ConstantUtil.COUGHORCOLD, ConstantUtil.COUGHORCOLD_DESC);
        }
    }

    private static void checkForDiarrhoeaWithSevereDehydration(Map<Integer, String> mapOfAnswers, Map<String, String> results) {
        //DIARRHOEA
        //AND
        //TWO SIGNS OR MORE OF ANY OF THE FOLLOWING:
        // LETHARGIC OR UNCONSCIOUS / SUNKEN EYES /SKIN PINCH GOES BACK VERY SLOWLY
        //AND
        //     ORAL FLUID TEST =
        // COMPLETELY UNABLE TO DRINK
        // OR
        // VOMITS IMMEDIATELY/EVERYTHING
        // OR
        // DRINKS POORLY
        String diarrhoea = mapOfAnswers.get(15);
        String sunkenEyes = mapOfAnswers.get(18);
        String skinPinchAbdomen = mapOfAnswers.get(19);
        if ((diarrhoea != null && diarrhoea.equals("YES")) && (sunkenEyes != null && !sunkenEyes.equals("YES")) && (skinPinchAbdomen != null && !skinPinchAbdomen.equals("VERY_SLOWLY"))) {
//            results.put(ConstantUtil.DIARRHOEA_SEVERE_DEHYDRATION, ConstantUtil.ORAL_FLUID_TEST_RECOMMENDATION_FOR_SEVERE_DEHYDRATION_DESC);
        }
    }

    private static void checkForDiarrhoeaWithSomeDehydration(Map<Integer, String> mapOfAnswers, Map<String, String> results) {
        //DIARRHOEA
        //AND
        //TWO SIGNS OR MORE OF ANY OF THE FOLLOWING:
        // LETHARGIC OR UNCONSCIOUS OR RESTLESS and IRRITABLE  / SUNKEN EYES /SKIN PINCH GOES BACK VERY SLOWLY/SKIN PINCH GOES BACK SLOWLY
        //OR
        //     ORAL FLUID TEST =
        // DRINK EAGERLY,THIRSTILY
        // OR
        // DRINKS POORLY
        // OR
        //  COMPLETELY UNABLE TO DRINK
        Integer signCounts = 0;
        String diarrhoea = mapOfAnswers.get(15);
        boolean isDiarrhoea = diarrhoea != null && diarrhoea.equals("YES");
        String sunkenEyes = mapOfAnswers.get(18);
        boolean isSunkenEyes = sunkenEyes != null && !sunkenEyes.equals("YES");
        String skinPinchVeryslowly = mapOfAnswers.get(19);
        boolean isSkinPinchVeryslowly = skinPinchVeryslowly != null && skinPinchVeryslowly.equals("VERY_SLOWLY");
        String skinPinchSlowly = mapOfAnswers.get(19);
        boolean isSkinPinchSlowly = skinPinchSlowly != null && skinPinchSlowly.equals("SLOWLY");
        String restlessAndIrritable = mapOfAnswers.get(20);
        boolean isRestlessAndIrritable = restlessAndIrritable != null && restlessAndIrritable.equals("YES");
        List<Boolean> checkList = new ArrayList<>();
        checkList.add(isRestlessAndIrritable);
        checkList.add(isSkinPinchSlowly);
        checkList.add(isSkinPinchVeryslowly);
        checkList.add(isSunkenEyes);
        for (Boolean value : checkList) {
            if (Boolean.TRUE.equals(value)) {
                signCounts++;
            }
            if (signCounts >= 2) {
//                results.put(ConstantUtil.DIARRHOEA_SOME_DEHYDRATION, ConstantUtil.ORAL_FLUID_TEST_RECOMMENDATION_FOR_SOME_DEHYDRATION_DESC);
                break;
            }
        }

    }

    private void checkForFeverSymptoms(Map<Integer, String> mapOfAnswers, List<TriagingResultsDto> results, String preferredLanguage) {
        String feverResults = mapOfAnswers.get(24);
        if (feverResults == null) {
            return;
        }
//        boolean oralSoresMouthUlcers=Arrays.stream(feverResultsArray).anyMatch("ORAL_SORES_MOUTH_ULCERS"::equals);

        if (feverResults.contains("TEMP_GTE_37_5")) {
            /*results.put(FEVER, FEVER_DESC);*/
            TriagingResultsDto feverResult = new TriagingResultsDto();
            List<String> feverSuggestions = new ArrayList<>();
            List<String> feverSymptoms = new ArrayList<>();
            feverResult.setDisease(ConstantUtil.getKeyByLanguage("FEVER", preferredLanguage));
            feverSymptoms.add(questionMasterDao.getQuestionOptionByPreferredLanguage(24, "TEMP_GTE_37_5", preferredLanguage));
            feverSuggestions.add(ConstantUtil.getKeyByLanguage("FEVER_SUGGESTION1", preferredLanguage));
            feverSuggestions.add(ConstantUtil.getKeyByLanguage("FEVER_SUGGESTION2", preferredLanguage));
            feverResult.setSymptoms(feverSymptoms);
            feverResult.setSuggestions(feverSuggestions);
            feverResult.setCode("FEVER");
            results.add(feverResult);
        }
        if ( feverResults.contains("REFUSAL_USE_LIMB") && feverResults.contains("WARM_TENDER_SWOLLEN_JOINT_BONE")) {
            TriagingResultsDto boneInfectionResult = new TriagingResultsDto();
            List<String> boneInfectionSuggestions = new ArrayList<>();
            List<String> boneInfectionSymptoms = new ArrayList<>();
            boneInfectionResult.setDisease(ConstantUtil.getKeyByLanguage("POSSIBLE_BONE_INFECTION", preferredLanguage));
            /*boneInfectionSymptoms.add(questionMasterDao.getQuestionOptionByPreferredLanguage(24, "TEMP_GTE_37_5", preferredLanguage));*/
            boneInfectionSymptoms.add(questionMasterDao.getQuestionOptionByPreferredLanguage(24, "REFUSAL_USE_LIMB", preferredLanguage));
            boneInfectionSymptoms.add(questionMasterDao.getQuestionOptionByPreferredLanguage(24, "WARM_TENDER_SWOLLEN_JOINT_BONE", preferredLanguage));
            boneInfectionSuggestions.add(ConstantUtil.getKeyByLanguage("POSSIBLE_BONE_INFECTION_SUGGESTION1", preferredLanguage));
            boneInfectionSuggestions.add(ConstantUtil.getKeyByLanguage("POSSIBLE_BONE_INFECTION_SUGGESTION2", preferredLanguage));
            boneInfectionResult.setSymptoms(boneInfectionSymptoms);
            boneInfectionResult.setSuggestions(boneInfectionSuggestions);
            boneInfectionResult.setCode("BONE_INFECTION");
            results.add(boneInfectionResult);
        }
        if (feverResults.contains("DIFFICULTY_PASSING_URINE")) {
            TriagingResultsDto urineInfectionResult = new TriagingResultsDto();
            List<String> urineInfectionSuggestions = new ArrayList<>();
            List<String> urineInfectionSymptoms = new ArrayList<>();
            urineInfectionResult.setDisease(ConstantUtil.getKeyByLanguage("POSSIBLE_URINE_INFECTION", preferredLanguage));
            /*urineInfectionSymptoms.add(questionMasterDao.getQuestionOptionByPreferredLanguage(24, "TEMP_GTE_37_5", preferredLanguage));*/
            urineInfectionSymptoms.add(questionMasterDao.getQuestionOptionByPreferredLanguage(24, "DIFFICULTY_PASSING_URINE", preferredLanguage));
            urineInfectionSuggestions.add(ConstantUtil.getKeyByLanguage("POSSIBLE_URINE_INFECTION_SUGGESTION1", preferredLanguage));
            urineInfectionSuggestions.add(ConstantUtil.getKeyByLanguage("POSSIBLE_URINE_INFECTION_SUGGESTION2", preferredLanguage));
            urineInfectionSuggestions.add(ConstantUtil.getKeyByLanguage("POSSIBLE_URINE_INFECTION_SUGGESTION3", preferredLanguage));
            urineInfectionSuggestions.add(ConstantUtil.getKeyByLanguage("POSSIBLE_URINE_INFECTION_SUGGESTION4", preferredLanguage));
            urineInfectionSuggestions.add(ConstantUtil.getKeyByLanguage("POSSIBLE_URINE_INFECTION_SUGGESTION5", preferredLanguage));
            urineInfectionResult.setSymptoms(urineInfectionSymptoms);
            urineInfectionResult.setSuggestions(urineInfectionSuggestions);
            urineInfectionResult.setCode("URINE_INFECTION");
            results.add(urineInfectionResult);
        }

    }

    private void checkForMeaslesSymptoms(Map<Integer, String> mapOfAnswers, List<TriagingResultsDto> results, String preferredLanguage) {
        String feverResults = mapOfAnswers.get(27) != null ? mapOfAnswers.get(27).replace(" ", "") : null;
        String[] feverResultsArray = feverResults != null ? feverResults.trim().split(",") : new String[0];
        if (feverResultsArray.length == 0) {
            return;
        }
        /*boolean hasFever = Arrays.stream(feverResultsArray).anyMatch("TEMP_GTE_37_5"::equals);*/
        boolean measlesInLast3Months = Arrays.stream(feverResultsArray).anyMatch("MEASLES_IN_LAST_3MONTHS"::equals);
        boolean pusDrainingFromEye = Arrays.stream(feverResultsArray).anyMatch("PUS_DRAINING_FROM_EYE"::equals);
        boolean mouthUlcersNotDeep = Arrays.stream(feverResultsArray).anyMatch("MOUTH_SORES_ULCERS_NOT_DEEP"::equals);
        boolean mouthUlcersDeep = Arrays.stream(feverResultsArray).anyMatch("MOUTH_SORES_ULCERS_DEEP"::equals);
        boolean cough = Arrays.stream(feverResultsArray).anyMatch("COUGH"::equals);
        boolean runnyNose = Arrays.stream(feverResultsArray).anyMatch("RUNNY_NOSE"::equals);
        boolean mrunnyNose = Arrays.stream(feverResultsArray).anyMatch("RUNNY_NOSE"::equals);
        boolean redEyes = Arrays.stream(feverResultsArray).anyMatch("RED_EYES"::equals);
        boolean cloudingOfCornea = Arrays.stream(feverResultsArray).anyMatch("CLOUDING_OF_CORNEA"::equals);

        if ((cough || runnyNose || redEyes) && (measlesInLast3Months || cloudingOfCornea || mouthUlcersDeep)) {
            /*results.put(SEVERE_COMPLICATED_MEASLES, SEVERE_COMPLICATED_MEASLES_DESC);*/
            TriagingResultsDto severeMeaslesResult = new TriagingResultsDto();
            List<String> severeMeaslesSuggestions = new ArrayList<>();
            List<String> severeMeaslesSymptoms = new ArrayList<>();
            severeMeaslesResult.setDisease(ConstantUtil.getKeyByLanguage("SEVERE_COMPLICATED_MEASLES", preferredLanguage));
            if (cough) {
                severeMeaslesSymptoms.add(questionMasterDao.getQuestionOptionByPreferredLanguage(27, "COUGH", preferredLanguage));
            }
            if (runnyNose) {
                severeMeaslesSymptoms.add(questionMasterDao.getQuestionOptionByPreferredLanguage(27, "RUNNY_NOSE", preferredLanguage));
            }
            if (redEyes) {
                severeMeaslesSymptoms.add(questionMasterDao.getQuestionOptionByPreferredLanguage(27, "RED_EYES", preferredLanguage));
            }
            if (measlesInLast3Months) {
                severeMeaslesSymptoms.add(questionMasterDao.getQuestionOptionByPreferredLanguage(27, "MEASLES_IN_LAST_3MONTHS", preferredLanguage));
            }
            if (cloudingOfCornea) {
                severeMeaslesSymptoms.add(questionMasterDao.getQuestionOptionByPreferredLanguage(27, "CLOUDING_OF_CORNEA", preferredLanguage));
            }
            if (mouthUlcersDeep) {
                severeMeaslesSymptoms.add(questionMasterDao.getQuestionOptionByPreferredLanguage(27, "MOUTH_SORES_ULCERS_DEEP", preferredLanguage));
            }
            severeMeaslesResult.setSymptoms(severeMeaslesSymptoms);
            severeMeaslesSuggestions.add(ConstantUtil.getKeyByLanguage("MEASLES_SUGGESTION1", preferredLanguage));
            severeMeaslesSuggestions.add(ConstantUtil.getKeyByLanguage("MEASLES_SUGGESTION2", preferredLanguage));
            severeMeaslesSuggestions.add(ConstantUtil.getKeyByLanguage("MEASLES_SUGGESTION3", preferredLanguage));
            severeMeaslesSuggestions.add(ConstantUtil.getKeyByLanguage("MEASLES_SUGGESTION4", preferredLanguage));
            severeMeaslesSuggestions.add(ConstantUtil.getKeyByLanguage("MEASLES_SUGGESTION5", preferredLanguage));
            severeMeaslesResult.setSuggestions(severeMeaslesSuggestions);
            severeMeaslesResult.setCode("MEASLES");
            results.add(severeMeaslesResult);
        }
        if (measlesInLast3Months && pusDrainingFromEye && mouthUlcersNotDeep && (cough || runnyNose || redEyes)) {
            TriagingResultsDto measlesWithEyeorMouthInfectionResult = new TriagingResultsDto();
            List<String> severeMeaslesSuggestions = new ArrayList<>();
            List<String> severeMeaslesSymptoms = new ArrayList<>();
            measlesWithEyeorMouthInfectionResult.setDisease(ConstantUtil.getKeyByLanguage("MEASLES_WITH_EYE_OR_MOUTH_COMPLICATION", preferredLanguage));
            severeMeaslesSymptoms.add(questionMasterDao.getQuestionOptionByPreferredLanguage(27, "MEASLES_IN_LAST_3MONTHS", preferredLanguage));
            severeMeaslesSymptoms.add(questionMasterDao.getQuestionOptionByPreferredLanguage(27, "MOUTH_SORES_ULCERS_NOT_DEEP", preferredLanguage));
            severeMeaslesSymptoms.add(questionMasterDao.getQuestionOptionByPreferredLanguage(27, "PUS_DRAINING_FROM_EYE", preferredLanguage));

            if (cough) {
                severeMeaslesSymptoms.add(questionMasterDao.getQuestionOptionByPreferredLanguage(27, "COUGH", preferredLanguage));
            }
            if (runnyNose) {
                severeMeaslesSymptoms.add(questionMasterDao.getQuestionOptionByPreferredLanguage(27, "RUNNY_NOSE", preferredLanguage));
            }
            if (redEyes) {
                severeMeaslesSymptoms.add(questionMasterDao.getQuestionOptionByPreferredLanguage(27, "RED_EYES", preferredLanguage));
            }
            measlesWithEyeorMouthInfectionResult.setSymptoms(severeMeaslesSymptoms);
            severeMeaslesSuggestions.add(ConstantUtil.getKeyByLanguage("MEASLES_SUGGESTION1", preferredLanguage));
            severeMeaslesSuggestions.add(ConstantUtil.getKeyByLanguage("MEASLES_SUGGESTION2", preferredLanguage));
            severeMeaslesSuggestions.add(ConstantUtil.getKeyByLanguage("MEASLES_SUGGESTION3", preferredLanguage));
            severeMeaslesSuggestions.add(ConstantUtil.getKeyByLanguage("MEASLES_SUGGESTION4", preferredLanguage));
            severeMeaslesSuggestions.add(ConstantUtil.getKeyByLanguage("MEASLES_SUGGESTION5", preferredLanguage));
            measlesWithEyeorMouthInfectionResult.setSuggestions(severeMeaslesSuggestions);
            measlesWithEyeorMouthInfectionResult.setCode("MEASLES");
            results.add(measlesWithEyeorMouthInfectionResult);
        }
    }

    private static void removeMultipleClassificationsForAllTraige(List<TriagingResultsDto> results, String preferredLanguage) {
        List<TriagingResultsDto> severeMeaslesResult = results.stream().filter(data -> data.getDisease().equals(ConstantUtil.getKeyByLanguage("SEVERE_COMPLICATED_MEASLES", preferredLanguage))).collect(Collectors.toList());
        if (severeMeaslesResult.size() > 0) {
            List<TriagingResultsDto> measlesWithComplicationResult = results.stream().filter(data -> data.getDisease().equals(ConstantUtil.getKeyByLanguage("MEASLES_WITH_EYE_OR_MOUTH_COMPLICATION", preferredLanguage))).collect(Collectors.toList());
            if (measlesWithComplicationResult.size() > 0) {
                results.stream().filter(data -> data.getDisease().equals(ConstantUtil.getKeyByLanguage("SEVERE_COMPLICATED_MEASLES", preferredLanguage))).forEach(d -> {
                    d.getSymptoms().addAll(measlesWithComplicationResult.get(0).getSymptoms());
                });
                results.removeAll(measlesWithComplicationResult);
            }
        }
    }

    private static void removeMultipleClassifications(Map<String, String> results) {
//        if (results.containsKey(ConstantUtil.SEVERE_PNEUMONIA))
//            results.remove(ConstantUtil.PNEUMONIA);
//        if (results.containsKey(ConstantUtil.DIARRHOEA_SEVERE_DEHYDRATION))
//            results.remove(ConstantUtil.DIARRHOEA_SOME_DEHYDRATION);
    }

    private static void removePreviousClassifications(Map<String, String> mapOfAnswers, Map<String, String> previousClassifications) {
        for (String previousClassification : previousClassifications.keySet()) {
            mapOfAnswers.remove(previousClassification);
        }
    }
}
