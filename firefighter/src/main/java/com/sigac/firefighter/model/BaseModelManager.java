package com.sigac.firefighter.model;

import java.util.List;

public abstract class BaseModelManager implements ObservableModelManager {

    private List<Observer> mObservers;

    public void addObserver(Observer observer) {
        mObservers.add(observer);
    }

    protected void notifyObservers() {
        for (Observer observer : mObservers) {
            observer.onChange(this);
        }
    }
}
