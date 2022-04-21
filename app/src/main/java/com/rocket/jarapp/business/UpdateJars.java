package com.rocket.jarapp.business;

import com.rocket.jarapp.application.Services;
import com.rocket.jarapp.objects.Jar;
import com.rocket.jarapp.persistence.JarPersistence;

public class UpdateJars {
    private JarPersistence jarPersistence;

    public UpdateJars(JarPersistence jarPersistence) {
        this.jarPersistence = jarPersistence;
    }

    public UpdateJars() {
        this.jarPersistence = Services.getJarPersistence();
    }

    public boolean update(Jar jar) {
        return jarPersistence.updateJar(jar);
    }

    public boolean insert(Jar jar) {
        return jarPersistence.insertJar(jar);
    }

    public boolean delete(Jar jar) {
        return jarPersistence.deleteJar(jar);
    }

    public int getNextId() {
        return jarPersistence.getNextId();
    }
}
