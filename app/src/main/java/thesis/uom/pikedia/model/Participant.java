package thesis.uom.pikedia.model;

/**
 * Created by ryana on 3/27/2017.
 */

public class Participant {
    private int participantId;
    private String gender;
    private int age;
    private String locality;
    private String smartPhoneOwnershipOS;
    private String smartPhoneYearsOfOwnership;
    private String employment;
    private String course;

    /* Empty Constructor for Firebase */
    public Participant() {
    }

    public Participant(int participantId, String gender, int age, String locality, String smartPhoneOwnershipOS, String smartPhoneYearsOfOwnership, String employment, String course) {
        this.participantId = participantId;
        this.gender = gender;
        this.age = age;
        this.locality = locality;
        this.smartPhoneOwnershipOS = smartPhoneOwnershipOS;
        this.smartPhoneYearsOfOwnership = smartPhoneYearsOfOwnership;
        this.employment = employment;
        this.course = course;
    }

    public int getParticipantId() {
        return participantId;
    }

    public String getGender() {
        return gender;
    }

    public int getAge() {
        return age;
    }

    public String getLocality() {
        return locality;
    }

    public String getSmartPhoneOwnershipOS() {
        return smartPhoneOwnershipOS;
    }

    public String getSmartPhoneYearsOfOwnership() {
        return smartPhoneYearsOfOwnership;
    }

    public String getEmployment() {
        return employment;
    }

    public String getCourse() {
        return course;
    }
}
