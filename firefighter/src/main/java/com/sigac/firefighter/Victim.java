package com.sigac.firefighter;

/*
create table victims(mId int not null, mState int not null, mSex int, mAge int, mName varchar(30), mOccurrenceId int );
insert into victims values(mId, mState, mSex, mAge, mName, mOccurrenceId);

 VICTIM
    mId int
    mSex int   { MALE, FEMALE }
    mAge int   { CHILD, YOUNG, ADULT, OLD }
    mState int { GREEN, YELLOW, RED, BLACK }

    mName string
    mOccurrenceId
*/

import com.google.gson.annotations.SerializedName;

public class Victim {

    public enum Sex {
        @SerializedName("0")
        MALE (0),

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

    public enum State {
        @SerializedName("0")
        GREEN(0),

        @SerializedName("1")
        YELLOW(1),

        @SerializedName("2")
        RED(2),

        @SerializedName("3")
        BLACK(3);

        private final int value;

        State (int value) {
            this.value = value;
        }
    }

    private int mId;
    private Sex mSex;
    private Age mAge;
    private State mState;

    private String mName;
    private int mOccurrenceId;

    public Victim(int id, State state, Sex sex, Age age, String name, int occurrence_id) {
        mAge = age;
        mOccurrenceId = occurrence_id;
        mName = name;
        mState = state;
        mSex = sex;
        mId = id;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("ID: " + mId + "\n");
        sb.append("State:" + mState + "\n");
        sb.append("Sex:" + mSex + "\n");
        sb.append("Name:" + mName + "\n");
        sb.append("OccurrenceId:" + mOccurrenceId + "\n");

        return sb.toString();
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        this.mId = id;
    }

    public Sex getSex() {
        return mSex;
    }

    public void setSex(Sex sex) {
        this.mSex = sex;
    }

    public Age getAge() {
        return mAge;
    }

    public void setAge(Age age) {
        this.mAge = age;
    }

    public State getState() {
        return mState;
    }

    public void setState(State state) {
        this.mState = state;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        this.mName = name;
    }

    public int getOccurrenceId() {
        return mOccurrenceId;
    }

    public void setOccurrence_id(int occurrence_id) {
        this.mOccurrenceId = occurrence_id;
    }
}
