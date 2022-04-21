package com.rocket.jarapp.business;

import com.rocket.jarapp.application.Services;
import com.rocket.jarapp.objects.Jar;
import com.rocket.jarapp.persistence.JarPersistence;
import java.util.List;

public class AccessJars {
    private JarPersistence jarPersistence;

    public AccessJars() {
        jarPersistence = Services.getJarPersistence();
    }

    public AccessJars(JarPersistence jarPersistence) {
        this.jarPersistence = jarPersistence;
    }

    public List<Jar> getAllJars() {
        return jarPersistence.getAllJars();
    }

    public Jar getJarById(int id) {
        for (Jar jar : jarPersistence.getAllJars()) {
            if (jar.getId() == id) {
                return jar;
            }
        }
        return null;
    }
}
