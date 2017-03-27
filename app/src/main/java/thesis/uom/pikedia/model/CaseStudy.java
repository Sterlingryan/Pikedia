package thesis.uom.pikedia.model;

import java.util.HashMap;

/**
 * Created by ryana on 3/27/2017.
 */

public class CaseStudy {

    private HashMap<String,String> names;
    private HashMap<String,String> typesOfArchitecture;
    private HashMap<String,String> artisticStyle;
    private HashMap<String,String> builtBy;
    private HashMap<String,String> builtIn;
    private HashMap<String,String> architect;
    private HashMap<String,Float> longitude;
    private HashMap<String,Float> latitude;
    private HashMap<String,String> historicalEvents;
    private HashMap<String,String> imageGUID;
    private HashMap<String,String> religion;
    private HashMap<String,String> materials;
    private HashMap<String,String> purpose;
    private HashMap<String,String> languageOf;
    private HashMap<String,String> openingTimes;
    private HashMap<String,Float> entranceFees;
    private HashMap<String,String> services;
    private HashMap<String,String> colors;
    private HashMap<String,Object> timestampCreated;


    /* Empty Constructor for Firebase */
    public CaseStudy() {
    }

    public CaseStudy(HashMap<String, String> names, HashMap<String, String> typesOfArchitecture, HashMap<String, String> artisticStyle, HashMap<String, String> builtBy, HashMap<String, String> builtIn, HashMap<String, String> architect, HashMap<String, Float> longitude, HashMap<String, Float> latitude, HashMap<String, String> historicalEvents, HashMap<String, String> imageGUID, HashMap<String, String> religion, HashMap<String, String> materials, HashMap<String, String> purpose, HashMap<String, String> languageOf, HashMap<String, String> openingTimes, HashMap<String, Float> entranceFees, HashMap<String, String> services, HashMap<String, String> colors, HashMap<String, Object> timestampCreated) {
        this.names = names;
        this.typesOfArchitecture = typesOfArchitecture;
        this.artisticStyle = artisticStyle;
        this.builtBy = builtBy;
        this.builtIn = builtIn;
        this.architect = architect;
        this.longitude = longitude;
        this.latitude = latitude;
        this.historicalEvents = historicalEvents;
        this.imageGUID = imageGUID;
        this.religion = religion;
        this.materials = materials;
        this.purpose = purpose;
        this.languageOf = languageOf;
        this.openingTimes = openingTimes;
        this.entranceFees = entranceFees;
        this.services = services;
        this.colors = colors;
        this.timestampCreated = timestampCreated;
    }

    public HashMap<String, String> getNames() {
        return names;
    }

    public HashMap<String, String> getTypesOfArchitecture() {
        return typesOfArchitecture;
    }

    public HashMap<String, String> getArtisticStyle() {
        return artisticStyle;
    }

    public HashMap<String, String> getBuiltBy() {
        return builtBy;
    }

    public HashMap<String, String> getBuiltIn() {
        return builtIn;
    }

    public HashMap<String, String> getArchitect() {
        return architect;
    }

    public HashMap<String, Float> getLongitude() {
        return longitude;
    }

    public HashMap<String, Float> getLatitude() {
        return latitude;
    }

    public HashMap<String, String> getHistoricalEvents() {
        return historicalEvents;
    }

    public HashMap<String, String> getImageGUID() {
        return imageGUID;
    }

    public HashMap<String, String> getReligion() {
        return religion;
    }

    public HashMap<String, String> getMaterials() {
        return materials;
    }

    public HashMap<String, String> getPurpose() {
        return purpose;
    }

    public HashMap<String, String> getLanguageOf() {
        return languageOf;
    }

    public HashMap<String, String> getOpeningTimes() {
        return openingTimes;
    }

    public HashMap<String, Float> getEntranceFees() {
        return entranceFees;
    }

    public HashMap<String, String> getServices() {
        return services;
    }

    public HashMap<String, String> getColors() {
        return colors;
    }

    public HashMap<String, Object> getTimestampCreated() {
        return timestampCreated;
    }
}