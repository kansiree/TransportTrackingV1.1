package th.co.gosoft.storemobile.transporttracking.Models;

/**
 * Created by Jubjang on 9/25/2017.
 */

public class model_person {
    String personName;
    String personID;
    String personPhone;
    String personCar;
    String workID;

    public String getWorkID() {
        return workID;
    }

    public void setWorkID(String workID) {
        this.workID = workID;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }

    public String getPersonPhone() {
        return personPhone;
    }

    public void setPersonPhone(String personPhone) {
        this.personPhone = personPhone;
    }

    public String getPersonCar() {
        return personCar;
    }

    public void setPersonCar(String personCar) {
        this.personCar = personCar;
    }
}
