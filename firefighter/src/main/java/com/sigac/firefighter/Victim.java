package com.sigac.firefighter;

import com.google.gson.annotations.SerializedName;


// TODO: Implement parcelable
public class Victim {
    public enum Sex {
        @SerializedName("male")
        MALE,

        @SerializedName("female")
        FEMALE
    }

    public enum Age {
        @SerializedName("child")
        CHILD,

        @SerializedName("young")
        YOUNG,

        @SerializedName("adult")
        ADULT,

        @SerializedName("old")
        OLD
    }

    public enum Status {
        @SerializedName("green")
        GREEN,

        @SerializedName("yellow")
        YELLOW,

        @SerializedName("red")
        RED,

        @SerializedName("black")
        BLACK
    }

    @SerializedName("token")
    private String token;

    @SerializedName("name")
    private String name;

    @SerializedName("sex")
    private Sex sex;

    @SerializedName("age")
    private Age age;

    @SerializedName("status")
    private Status status;

    public Victim() {
        age = Age.CHILD;
        name = "";
        status = Status.GREEN;
        sex = Sex.MALE;
        token = "0";
    }

    public Victim(String token, String name, Sex sex, Age age, Status status) {
        this.token = token;
        this.name = name;
        this.sex = sex;
        this.age = age;
        this.status = status;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("ID: " + token + "\n");
        sb.append("Name:" + name + "\n");
        sb.append("Sex:" + sex + "\n");
        sb.append("Age:" + age + "\n");
        sb.append("Status:" + status + "\n");

        return sb.toString();
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Sex getSex() {
        return sex;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }

    public Age getAge() {
        return age;
    }

    public void setAge(Age age) {
        this.age = age;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
