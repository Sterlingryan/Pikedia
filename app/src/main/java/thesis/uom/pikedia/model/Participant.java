package thesis.uom.pikedia.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by ryana on 3/27/2017.
 */

public class Participant implements Serializable {
    private String gender;
    private String age;
    private String locality;
    private String smartPhoneOwnershipOS;
    private String smartPhoneYearsOfOwnership;
    private String employment;
    private String course;

    /* Empty Constructor for Firebase */
    public Participant() {
    }

    public Participant( String gender, String age, String locality, String smartPhoneOwnershipOS, String smartPhoneYearsOfOwnership, String employment, String course) {
        this.gender = gender;
        this.age = age;
        this.locality = locality;
        this.smartPhoneOwnershipOS = smartPhoneOwnershipOS;
        this.smartPhoneYearsOfOwnership = smartPhoneYearsOfOwnership;
        this.employment = employment;
        this.course = course;
    }

    public String getGender() {
        return gender;
    }

    public String getAge() {
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
