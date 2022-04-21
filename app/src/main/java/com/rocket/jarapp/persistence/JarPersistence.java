package com.rocket.jarapp.persistence;

import com.rocket.jarapp.objects.Jar;

import java.util.List;

public interface JarPersistence {
    /**
     * getJars
     *
     * Return all Jars
     */
    List<Jar> getAllJars();

    /**
     * insertJar
     *
     * Save a new jar
     */
    boolean insertJar(Jar jar);

    /**
     * updateJar
     *
     * Return true if jar is updated. Return null otherwise
     */
    boolean updateJar(Jar jar);

    /**
     * deleteJar
     *
     * Return true if a jar is completely from the system
     */
    boolean deleteJar(Jar jar);

    /**
     * getNextId
     *
     * Returns the next unique id that can be used
     */
    int getNextId();
}
