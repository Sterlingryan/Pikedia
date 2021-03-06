package thesis.uom.pikedia.model;

import java.io.Serializable;
import java.util.HashMap;

/**
 * Created by ryana on 3/27/2017.
 */

public class CaseStudy implements Serializable{

    private String participantID;
    private String name;
    private String description;
    private String typesOfArchitecture;
    private String artisticStyle;
    private String builtBy;
    private String builtIn;
    private String architect;
    private String longitude;
    private String latitude;
    private String imageGUID;
    private String religion;
    private HashMap<String,String> materials;
    private String purpose;
    private String languageOf;
    private String openingTimes;
    private String entranceFees;
    private HashMap<String,String> services;
    private String depiction;
    private String time;
    private HashMap<String,String> historicalEvents;
    private HashMap<String,String> features;


    /* Empty Constructor for Firebase */
    public CaseStudy() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getParticipantID() {
        return participantID;
    }

    public void setParticipantID(String participantID) {
        this.participantID = participantID;
    }

    public String getTypesOfArchitecture() {
        return typesOfArchitecture;
    }

    public void setTypesOfArchitecture(String typesOfArchitecture) {
        this.typesOfArchitecture = typesOfArchitecture;
    }

    public String getArtisticStyle() {
        return artisticStyle;
    }

    public void setArtisticStyle(String artisticStyle) {
        this.artisticStyle = artisticStyle;
    }

    public String getBuiltBy() {
        return builtBy;
    }

    public void setBuiltBy(String builtBy) {
        this.builtBy = builtBy;
    }

    public String getBuiltIn() {
        return builtIn;
    }

    public void setBuiltIn(String builtIn) {
        this.builtIn = builtIn;
    }

    public String getArchitect() {
        return architect;
    }

    public void setArchitect(String architect) {
        this.architect = architect;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getImageGUID() {
        return imageGUID;
    }

    public void setImageGUID(String imageGUID) {
        this.imageGUID = imageGUID;
    }

    public String getReligion() {
        return religion;
    }

    public void setReligion(String religion) {
        this.religion = religion;
    }

    public HashMap<String, String> getMaterials() {
        return materials;
    }

    public void setMaterials(HashMap<String, String> materials) {
        this.materials = materials;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public String getLanguageOf() {
        return languageOf;
    }

    public void setLanguageOf(String languageOf) {
        this.languageOf = languageOf;
    }

    public String getOpeningTimes() {
        return openingTimes;
    }

    public void setOpeningTimes(String openingTimes) {
        this.openingTimes = openingTimes;
    }

    public String getEntranceFees() {
        return entranceFees;
    }

    public void setEntranceFees(String entranceFees) {
        this.entranceFees = entranceFees;
    }

    public HashMap<String, String> getServices() {
        return services;
    }

    public void setServices(HashMap<String, String> services) {
        this.services = services;
    }

    public HashMap<String, String> getHistoricalEvents() {
        return historicalEvents;
    }

    public void setHistoricalEvents(HashMap<String, String> historicalEvents) {
        this.historicalEvents = historicalEvents;
    }

    public String getDepiction() {
        return depiction;
    }

    public void setDepiction(String depiction) {
        this.depiction = depiction;
    }

    public HashMap<String, String> getFeatures() {
        return features;
    }

    public void setFeatures(HashMap<String, String> features) {
        this.features = features;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}