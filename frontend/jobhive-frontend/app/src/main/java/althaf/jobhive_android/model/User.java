package althaf.jobhive_android.model;

public class User {
    public String email;
    public String password;
    public String fullName;
    public String address;

    public String toString() {
        return "User{" +
                "fullName=" + fullName +
                ", email=" + email +
                ", password=" + password +
                ", address=" + address +
                '}';
    }
}
