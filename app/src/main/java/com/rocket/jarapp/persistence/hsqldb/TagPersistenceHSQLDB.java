package com.rocket.jarapp.persistence.hsqldb;

import android.util.Log;

import com.rocket.jarapp.objects.Tag;
import com.rocket.jarapp.objects.exceptions.RocketDatabaseAccessException;
import com.rocket.jarapp.objects.exceptions.RocketDatabaseWriteException;
import com.rocket.jarapp.persistence.TagPersistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TagPersistenceHSQLDB implements TagPersistence {
    private final String dbPath;
    private List<Tag> tags;
    private int nextId;

    public TagPersistenceHSQLDB(String dbPath) {
        this.dbPath = dbPath;
        this.tags = new ArrayList<>();
        loadSavedTags();
    }

    private Connection connect() throws SQLException {
        return DriverManager.getConnection("jdbc:hsqldb:file:" + dbPath + ";shutdown=true", "SA", "");
    }

    /**
     * loadSavedTags
     *
     * Loads all tags saved in memory into the system
     */
    private void loadSavedTags() {
        try (Connection connection = connect()) {
            final Statement statement = connection.createStatement();
            final ResultSet resultSet = statement.executeQuery("SELECT * FROM TAGS");
            while (resultSet.next()) {
                String name = resultSet.getString("name");
                int id = resultSet.getInt("id");
                Tag tag = new Tag(id, name);
                tags.add(tag);

                // I Would prefer to use IDENTITY() but that doesn't seem to be
                // working on the android device.... But this will work too,
                // as id's will always be increasing in size.
                nextId = id + 1;
            }
            resultSet.close();
            statement.close();
        } catch (final SQLException e) {
            throw new RocketDatabaseAccessException(e);
        }
    }

    @Override
    public List<Tag> getAllTags() {
        return Collections.unmodifiableList(tags);
    }

    @Override
    public boolean insertTag(Tag tag) {
        try (Connection connection = connect()) {
            if (!tags.contains(tag)) {
                final PreparedStatement statement = connection.prepareStatement("INSERT INTO TAGS VALUES(DEFAULT, ?)");
                statement.setString(1, tag.getName());
                statement.executeUpdate();
                tags.add(tag);
                nextId++;
                statement.close();
                return true;
            }
        } catch (final SQLException e) {
            throw new RocketDatabaseWriteException(e);
        }
        return false;
    }

    @Override
    public int getNextId() {
        return nextId;
    }
}
