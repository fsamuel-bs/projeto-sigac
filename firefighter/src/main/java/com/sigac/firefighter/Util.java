package com.sigac.firefighter;

import java.util.List;


public class Util {

    public static void main(String[] args) {
        try {
            DbUtil.delete_victim("0");
            DbUtil.delete_victim("1");

            DbUtil.persist_victim(new Victim(0, Victim.State.BLACK, Victim.Sex.MALE, Victim.Age.YOUNG, "", 0));
            DbUtil.persist_victim(new Victim(1, Victim.State.GREEN, Victim.Sex.MALE, Victim.Age.ADULT, "", 0));

            List<Victim> victims = DbUtil.get_victims();
            System.out.println(victims.toString());
        }
        catch(Exception e) {
            System.out.println(e.toString());
        }
    }


}
