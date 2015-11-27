package com.sigac.firefighter.model;

import com.sigac.firefighter.Victim;

import java.util.*;

public class MemoryMockModelManager extends BaseModelManager {

    private static class InstanceHolder {
        private static final MemoryMockModelManager INSTANCE = new MemoryMockModelManager();
    }

    public static MemoryMockModelManager getInstance() {
        return InstanceHolder.INSTANCE;
    }

    private static final Random RANDOM = new Random();

    private Map<String, Victim> mDatabase = new HashMap<>();

    private MemoryMockModelManager() {
        /* Prevents outside instantiation */
    }

    @Override
    public void deleteVictim(String id) {
        mDatabase.remove(id);
        notifyObservers();
    }

    @Override
    public List<Victim> getVictims() throws Exception {
        return new ArrayList<>(mDatabase.values());
    }

    @Override
    public void persistVictim(Victim victim) {
        mDatabase.put(String.valueOf(victim.getToken()), victim);
        notifyObservers();
    }

    @Override
    public String getTag() {
        String id;
        do {
            id = Integer.toString(Math.abs(RANDOM.nextInt() % 10000), 16);
        } while (mDatabase.containsKey(id));
        return id;
    }
}
