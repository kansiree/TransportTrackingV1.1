package th.co.gosoft.storemobile.transporttracking.Models.HQ;

/**
 * Created by Jubjang on 9/25/2017.
 */

public class RequestPersonLogin {
    String returnCode;
    String returnDescription;

    public String getReturnCode() {
        return returnCode;
    }

    public void setReturnCode(String returnCode) {
        this.returnCode = returnCode;
    }

    public String getReturnDescription() {
        return returnDescription;
    }

    public void setReturnDescription(String returnDescription) {
        this.returnDescription = returnDescription;
    }

    String personID;
    String personName;
    String personPhone;
    String personCar;

    public String getPersonID() {
        return personID;
    }
    public void setPersonID(String personID) {
        this.personID = personID;
    }
    public String getPersonName() {
        return personName;
    }
    public void setPersonName(String personName) {
        this.personName = personName;
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
