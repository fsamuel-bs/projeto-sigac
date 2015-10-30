package com.sigac.firefighter;

import java.util.List;


public class Util {

    public static void main(String[] args) {
        try {
            DbUtil.deleteVictim("0");
            DbUtil.deleteVictim("1");

            DbUtil.persistVictim(new Victim(0, Victim.State.BLACK, Victim.Sex.MALE, Victim.Age.YOUNG, "", 0));
            DbUtil.persistVictim(new Victim(1, Victim.State.GREEN, Victim.Sex.MALE, Victim.Age.ADULT, "", 0));

            List<Victim> victims = DbUtil.getVictims();
            System.out.println(victims.toString());
        }
        catch(Exception e) {
            System.out.println(e.toString());
        }
    }


}
