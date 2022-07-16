package com.argusoft.abdmhackathon.common.util;

import java.util.HashMap;
import java.util.Map;

public class ConstantUtil {

    private static Map<String, String> lan = new HashMap<>();

    static {
        lan.put("SEVERE_PNEUMONIA", "Severe Pneumonia");
        lan.put("SEVERE_PNEUMONIA_HN", "Severe Pneumonia");
        lan.put("SEVERE_PNEUMONIA_GU", "Severe Pneumonia");
        lan.put("SEVERE_PNEUMONIA_STRIDOR_DESC", "The child seems to have STRIDORS.");
        lan.put("SEVERE_PNEUMONIA_STRIDOR_DESC_HN", "The child seems to have STRIDORS.");
        lan.put("SEVERE_PNEUMONIA_STRIDOR_DESC_GU", "The child seems to have STRIDORS.");
        lan.put("SEVERE_PNEUMONIA_OXY_SAT_DESC", "The child has Oxygen saturation level below 90%.");
        lan.put("SEVERE_PNEUMONIA_OXY_SAT_DESC_HN", "The child has Oxygen saturation level below 90%.");
        lan.put("SEVERE_PNEUMONIA_OXY_SAT_DESC_GU", "The child has Oxygen saturation level below 90%.");
        lan.put("SEVERE_PNEUMONIA_DESC", "There is a very high possibility of the child having severe Pneumonitis.");
        lan.put("SEVERE_PNEUMONIA_DESC_HN", "There is a very high possibility of the child having severe Pneumonitis.");
        lan.put("SEVERE_PNEUMONIA_DESC_GU", "There is a very high possibility of the child having severe Pneumonitis.");
        lan.put("PNEUMONIA", "Probable Pneumonia");
        lan.put("PNEUMONIA_HN", "Probable Pneumonia");
        lan.put("PNEUMONIA_GU", "Probable Pneumonia");
        lan.put("PNEUMONIA_CHEST_INDRAWING_DESC", "The child has chest indrawing.");
        lan.put("PNEUMONIA_CHEST_INDRAWING_DESC_HN", "The child has chest indrawing.");
        lan.put("PNEUMONIA_CHEST_INDRAWING_DESC_GU", "The child has chest indrawing.");
        lan.put("PNEUMONIA_DESC", "We suggest checking exposure of HIV \nor\n conduct Inhaled broncholar trial.");
        lan.put("PNEUMONIA_DESC_HN", "We suggest checking exposure of HIV \nor\n conduct Inhaled broncholar trial.");
        lan.put("PNEUMONIA_DESC_GU", "We suggest checking exposure of HIV \nor\n conduct Inhaled broncholar trial.");
        lan.put("PNEUMONIA_DESC_WHEEZING", "The child has recurrent wheezing.");
        lan.put("PNEUMONIA_DESC_WHEEZING_HN", "The child has recurrent wheezing.");
        lan.put("PNEUMONIA_DESC_WHEEZING_GU", "The child has recurrent wheezing.");
        lan.put("PNEUMONIA_COUGH_GTE14_DESC", "The child has prolonged coughing. It can be a probable case of Pneumonia.");
        lan.put("PNEUMONIA_COUGH_GTE14_DESC_HN", "The child has prolonged coughing. It can be a probable case of Pneumonia.");
        lan.put("PNEUMONIA_COUGH_GTE14_DESC_GU", "The child has prolonged coughing. It can be a probable case of Pneumonia.");
        lan.put("PNEUMONIA_DIFFICULTY_BREATHING_DESC", "The child faces difficulty while breathing.");
        lan.put("PNEUMONIA_DIFFICULTY_BREATHING_DESC_HN", "The child faces difficulty while breathing.");
        lan.put("PNEUMONIA_DIFFICULTY_BREATHING_DESC_GU", "The child faces difficulty while breathing.");
        lan.put("COUGHORCOLD", "Cough Or Cold");
        lan.put("COUGHORCOLD_HN", "Cough Or Cold");
        lan.put("COUGHORCOLD_GU", "Cough Or Cold");
        lan.put("COUGHORCOLD_DESC", "");
        lan.put("COUGHORCOLD_DESC_HN", "");
        lan.put("COUGHORCOLD_DESC_GU", "");
        lan.put("DIARRHOEA_SEVERE_DEHYDRATION", "Diarrhoea with Severe Dehydration");
        lan.put("DIARRHOEA_SEVERE_DEHYDRATION_HN", "Diarrhoea with Severe Dehydration");
        lan.put("DIARRHOEA_SEVERE_DEHYDRATION_GU", "Diarrhoea with Severe Dehydration");
        lan.put("ORAL_FLUID_TEST_RECOMMENDATION_FOR_SEVERE_DEHYDRATION_DESC", "We recommend you to do an Oral Fluid Test as this can be Diarrhoea with Severe Dehydration.");
        lan.put("ORAL_FLUID_TEST_RECOMMENDATION_FOR_SEVERE_DEHYDRATION_DESC_HN", "We recommend you to do an Oral Fluid Test as this can be Diarrhoea with Severe Dehydration.");
        lan.put("ORAL_FLUID_TEST_RECOMMENDATION_FOR_SEVERE_DEHYDRATION_DESC_GU", "We recommend you to do an Oral Fluid Test as this can be Diarrhoea with Severe Dehydration.");
        lan.put("DIARRHOEA_SOME_DEHYDRATION", "Diarrhoea with Some Dehydration");
        lan.put("DIARRHOEA_SOME_DEHYDRATION_HN", "Diarrhoea with Some Dehydration");
        lan.put("DIARRHOEA_SOME_DEHYDRATION_GU", "Diarrhoea with Some Dehydration");
        lan.put("ORAL_FLUID_TEST_RECOMMENDATION_FOR_SOME_DEHYDRATION_DESC", "We recommend you to do an Oral Fluid Test as this can be Diarrhoea with Some Dehydration.");
        lan.put("ORAL_FLUID_TEST_RECOMMENDATION_FOR_SOME_DEHYDRATION_DESC_HN", "We recommend you to do an Oral Fluid Test as this can be Diarrhoea with Some Dehydration.");
        lan.put("ORAL_FLUID_TEST_RECOMMENDATION_FOR_SOME_DEHYDRATION_DESC_GU", "We recommend you to do an Oral Fluid Test as this can be Diarrhoea with Some Dehydration.");
        lan.put("POSSIBLE_BONE_INFECTION", "Possible Bone/Joint Infection");
        lan.put("POSSIBLE_BONE_INFECTION_HN", "Possible Bone/Joint Infection");
        lan.put("POSSIBLE_BONE_INFECTION_GU", "Possible Bone/Joint Infection");
        lan.put("POSSIBLE_BONE_INFECTION_DESC", "");
        lan.put("POSSIBLE_BONE_INFECTION_DESC_HN", "");
        lan.put("POSSIBLE_BONE_INFECTION_DESC_GU", "");
        lan.put("FEVER", "Fever ");
        lan.put("FEVER_HN", "Fever ");
        lan.put("FEVER_GU", "Fever ");
        lan.put("FEVER_DESC", "Fever");
        lan.put("FEVER_DESC_HN", "Fever");
        lan.put("FEVER_DESC_GU", "Fever");
        lan.put("POSSIBLE_URINE_INFECTION", "Possible Urine Infection ");
        lan.put("POSSIBLE_URINE_INFECTION_HN", "Possible Urine Infection ");
        lan.put("POSSIBLE_URINE_INFECTION_GU", "Possible Urine Infection ");
        lan.put("POSSIBLE_URINE_INFECTION_DESC", "");
        lan.put("POSSIBLE_URINE_INFECTION_DESC_HN", "");
        lan.put("POSSIBLE_URINE_INFECTION_DESC_GU", "");
        lan.put("SEVERE_COMPLICATED_MEASLES", "Severe Complicated Measles");
        lan.put("SEVERE_COMPLICATED_MEASLES_HN", "Severe Complicated Measles");
        lan.put("SEVERE_COMPLICATED_MEASLES_GU", "Severe Complicated Measles");
        lan.put("SEVERE_COMPLICATED_MEASLES_DESC", "Severe Complicated Measles");
        lan.put("SEVERE_COMPLICATED_MEASLES_DESC_HN", "Severe Complicated Measles");
        lan.put("SEVERE_COMPLICATED_MEASLES_DESC_GU", "Severe Complicated Measles");
        lan.put("MEASLES_WITH_EYE_OR_MOUTH_COMPLICATION", "Measles with Eye or Mouth Complication");
        lan.put("MEASLES_WITH_EYE_OR_MOUTH_COMPLICATION_DESC", "Measles with Eye or Mouth Complication");
    }

    public static String getKeyByLanguage(String key, String language) {
        if (language.equals("HN")) {
            key = key + "_HN";
        } else if (language.equals("GU")) {
            key = key + "_GU";
        }
        return lan.get(key);
    }

//    public static String SEVERE_PNEUMONIA = "Severe Pneumonia";
//    public static String SEVERE_PNEUMONIA_ALL = "Very Severe Pneumonia";

//    public static String SEVERE_PNEUMONIA_STRIDOR_DESC = "The child seems to have STRIDORS.";
//    public static String SEVERE_PNEUMONIA_OXY_SAT_DESC = "The child has Oxygen saturation level below 90%.";

//    public static String SEVERE_PNEUMONIA_DESC = "There is a very high possibility of the child having severe Pneumonitis.";
//    public static String PNEUMONIA = "Probable Pneumonia";
//    public static String PNEUMONIA_CHEST_INDRAWING_DESC = "The child has chest indrawing.";

//    public static String PNEUMONIA_DESC = "We suggest checking exposure of HIV \nor\n conduct Inhaled broncholar trial.";
//    public static String PNEUMONIA_DESC_WHEEZING = "The child has recurrent wheezing.";
//    public static String PNEUMONIA_COUGH_GTE14_DESC = "The child has prolonged coughing. It can be a probable case of Pneumonia.";
//    public static String PNEUMONIA_DIFFICULTY_BREATHING_DESC = "The child faces difficulty while breathing.";

//    public static String COUGHORCOLD = "Cough Or Cold";
//    public static String COUGHORCOLD_DESC = "";
//    public static String DIARRHOEA_SEVERE_DEHYDRATION = "Diarrhoea with Severe Dehydration";
//    public static String ORAL_FLUID_TEST_RECOMMENDATION_FOR_SEVERE_DEHYDRATION_DESC = "We recommend you to do an Oral Fluid Test as this can be Diarrhoea with Severe Dehydration.";
//    public static String DIARRHOEA_SOME_DEHYDRATION = "Diarrhoea with Some Dehydration";
//    public static String ORAL_FLUID_TEST_RECOMMENDATION_FOR_SOME_DEHYDRATION_DESC = "We recommend you to do an Oral Fluid Test as this can be Diarrhoea with Some Dehydration.";
//    public static String POSSIBLE_BONE_INFECTION = "Possible Bone/Joint Infection";
//    public static String POSSIBLE_BONE_INFECTION_DESC = "";
//    public static String FEVER = "Fever ";
//    public static String FEVER_DESC = "Fever";
//    public static String POSSIBLE_URINE_INFECTION = "Possible Urine Infection ";
//    public static String POSSIBLE_URINE_INFECTION_DESC = "Fever ";
//    public static String SEVERE_COMPLICATED_MEASLES = "Severe Complicated Measles";
//    public static String SEVERE_COMPLICATED_MEASLES_DESC = "Severe Complicated Measles";
//    public static String MEASLES_WITH_EYE_OR_MOUTH_COMPLICATION = "Measles with Eye or Mouth Complication";
//    public static String MEASLES_WITH_EYE_OR_MOUTH_COMPLICATION_DESC = "Measles with Eye or Mouth Complication";

}
