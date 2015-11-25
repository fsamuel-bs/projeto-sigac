package com.sigac.firefighter;

import com.google.gson.*;
import com.google.gson.annotations.SerializedName;

import java.lang.reflect.Type;

// TODO: Implement parcelable
public class Victim {
    public enum Sex {
        @SerializedName("0")
        MALE(0),

        @SerializedName("1")
        FEMALE(1);

        private final int value;

        Sex (int value) {
            this.value = value;
        }
    }

    public enum Age {
        @SerializedName("0")
        CHILD(0),

        @SerializedName("1")
        YOUNG(1),

        @SerializedName("2")
        ADULT(2),

        @SerializedName("3")
        OLD(3);

        private final int value;

        Age (int value) {
            this.value = value;
        }
    }

    public enum Status {
        @SerializedName("0")
        GREEN(0),

        @SerializedName("1")
        YELLOW(1),

        @SerializedName("2")
        RED(2),

        @SerializedName("3")
        BLACK(3);

        private final int value;

        Status(int value) {
            this.value = value;
        }
    }

    private String token;
    private String name;

    private Sex sex;
    private Age age;
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

    public static class Serializer implements JsonSerializer<Victim> {
        @Override
        public JsonElement serialize(Victim victim, Type typeOfSrc, JsonSerializationContext context) {
            JsonObject result = new JsonObject();

            result.add("token", new JsonPrimitive(victim.getToken()));
            result.add("name", new JsonPrimitive(victim.getName()));
            result.add("sex", new JsonPrimitive(victim.getSex().toString().toLowerCase()));
            result.add("age", new JsonPrimitive(victim.getAge().toString().toLowerCase()));
            result.add("status", new JsonPrimitive(victim.getStatus().toString().toLowerCase()));

            return result;

        }
    }
}
