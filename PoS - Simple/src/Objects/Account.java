package Objects;

import java.io.Serializable;

public class Account implements Serializable {
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String phoneNumber;

    public Account() {
        this.username = "default";
        this.password = "default";
        this.firstName = "-NoFirstName-";
        this.lastName = "-NoLastName-";
        this.phoneNumber = "-NoPhoneNumber-";
    }
    public Account(String username, String password, String firstName, String lastName, String phoneNumber) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
