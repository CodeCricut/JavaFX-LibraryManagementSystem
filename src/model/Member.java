package model;

public class Member {

    private int id;
    private String name;
    private String phoneNum;
    private String email;

    public Member(int id, String name, String phoneNum, String email){
        this.id = id;
        this.name = name;
        this.phoneNum = phoneNum;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        if (name == null)
            return "No Name Set";
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNum() {
        if (phoneNum == null)
            return "No Phone Number Set";
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getEmail() {
        if (email == null)
            return "No Email Set";
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
