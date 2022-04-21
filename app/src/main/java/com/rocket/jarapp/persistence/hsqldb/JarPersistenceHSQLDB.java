package com.rocket.jarapp.persistence.hsqldb;

import android.util.Log;

import com.rocket.jarapp.objects.Expense;
import com.rocket.jarapp.objects.Jar;
import com.rocket.jarapp.objects.exceptions.RocketDatabaseAccessException;
import com.rocket.jarapp.objects.exceptions.RocketDatabaseWriteException;
import com.rocket.jarapp.persistence.ExpensePersistence;
import com.rocket.jarapp.persistence.JarPersistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class JarPersistenceHSQLDB implements JarPersistence {
    private ExpensePersistence expensePersistence;
    private List<Jar> jars;
    private String dbPath;
    private int nextId;

    public JarPersistenceHSQLDB(String dbPath, ExpensePersistence expensePersistence) {
        this.dbPath = dbPath;
        this.expensePersistence = expensePersistence;
        this.jars = new ArrayList<>();
        loadSavedJars();
    }

    private Connection connect() throws SQLException {
        return DriverManager.getConnection("jdbc:hsqldb:file:" + dbPath + ";shutdown=true", "SA", "");
    }

    private void loadSavedJars() {
        try (Connection connection = connect()) {
            final Statement statement = connection.createStatement();
            final ResultSet resultSet = statement.executeQuery("SELECT * FROM JARS");
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                int themeNum = resultSet.getInt("theme");
                double budget = resultSet.getDouble("budget");
                Jar.Colour theme = Jar.Colour.values()[themeNum];

                Jar jar = new Jar(id, budget, name, theme);
                assignExpenses(jar);
                jars.add(jar);

                // I Would prefer to use IDENTITY() but that doesn't seem to be
                // working on the android device.... But this will work too,
                // as id's will always be increasing in size.
                nextId = id + 1;
            }
        } catch (final SQLException e) {
            throw new RocketDatabaseAccessException(e);
        }
    }

    /**
     * assignExpenses
     *
     * Adds only the expenses that are associated with a jar to the jar.
     */
    private void assignExpenses(Jar jar) {
        int jarId = jar.getId();

        try (Connection connection = connect()) {
            String statementStr = "SELECT expenseId FROM JAREXPENSES WHERE JAREXPENSES.jarId=?";

            final PreparedStatement statement = connection.prepareStatement(statementStr);
            statement.setInt(1, jarId);
            final ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("expenseId");
                for (Expense e : expensePersistence.getAllExpenses()) {
                    if (e.getId() == id) {
                        jar.addExpense(e);
                        break;
                    }
                }
            }
            statement.close();
            resultSet.close();
        } catch (final SQLException e) {
            throw new RocketDatabaseAccessException(e);
        }
    }

    @Override
    public List<Jar> getAllJars() {
        return Collections.unmodifiableList(jars);
    }

    /**
     * removeJarExpenseRelations
     *
     * Remove the rows linking expenses to the specified jar
     */
    private void removeJarExpenseRelations(Connection connection, int jarId) throws SQLException{
        final PreparedStatement etStatement = connection.prepareStatement("DELETE FROM JAREXPENSES WHERE jarId=?");
        etStatement.setInt(1, jarId);
        etStatement.executeUpdate();
        etStatement.close();
    }

    /**
     * addJarExpenseRelations
     *
     * Link provided expenses with the specified jar
     */
    private void addJarExpenseRelations(Connection connection, List<Expense> expenses, int jarId) throws SQLException {
        for (Expense expense : expenses) {
            final PreparedStatement etStatement = connection.prepareStatement("INSERT INTO JAREXPENSES VALUES(?, ?)");
            etStatement.setInt(1, jarId);
            etStatement.setInt(2, expense.getId());
            etStatement.executeUpdate();
            etStatement.close();
        }
    }

    /**
     * removeAssociatedExpenses
     *
     * Remove all expenses associated with the specified jar, and all links involving
     * those expenses.
     */
    private void removeAssociatedExpenses(Connection connection, int jarId) throws SQLException{
        String statementStr = "SELECT expenseId FROM JAREXPENSES WHERE jarId=?";

        final PreparedStatement statement = connection.prepareStatement(statementStr);
        statement.setInt(1, jarId);
        final ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {
            int expenseId = resultSet.getInt("expenseId");
            removeExpenseTagRelations(connection, expenseId);
            removeExpense(connection, expenseId);
        }
    }

    /**
     * removeExpense
     *
     * Remove an expense from the DB
     */
    private void removeExpense(Connection connection, int expenseId) throws SQLException {
        final PreparedStatement etStatement = connection.prepareStatement("DELETE FROM EXPENSES WHERE id=?");
        etStatement.setInt(1, expenseId);
        etStatement.executeUpdate();
        etStatement.close();
    }

    /**
     * RemoveExpenseTagRelations
     *
     * Remove the links between the given expense and any tags associated with it
     *
     * Note: Doesn't remove any tags, just the links associated a tag to an expense.
     */
    private void removeExpenseTagRelations(Connection connection, int expenseId) throws SQLException {
        final PreparedStatement etStatement = connection.prepareStatement("DELETE FROM EXPENSETAGS WHERE expenseId=?");
        etStatement.setInt(1, expenseId);
        etStatement.executeUpdate();
        etStatement.close();
    }

    @Override
    public boolean insertJar(Jar jar) {
        try (Connection connection = connect()) {
            if (!jars.contains(jar)) {
                // Insert Expense
                final PreparedStatement eStatement = connection.prepareStatement("INSERT INTO JARS VALUES(?, ?, ?, ?)");
                eStatement.setInt(1, jar.getId());
                eStatement.setString(2, jar.getName());
                eStatement.setInt(3, jar.getTheme().ordinal());
                eStatement.setDouble(4, jar.getBudget());
                eStatement.executeUpdate();
                eStatement.close();

                // Add relationships for all of its tags in the expense
                addJarExpenseRelations(connection, jar.getExpenses(), jar.getId());

                jars.add(jar);
                nextId++;
                return true;
            }
        } catch (final SQLException e) {
            throw new RocketDatabaseWriteException(e);
        }
        return false;
    }

    @Override
    public boolean updateJar(Jar jar) {
        try (Connection connection = connect()) {
            if (jars.contains(jar)) {
                // Update the expense
                String expenseStatementStr = "UPDATE JARS " +
                        "SET name=?, theme=?, budget=? " +
                        "WHERE id=?";
                final PreparedStatement eStatement = connection.prepareStatement(expenseStatementStr);
                eStatement.setString(1, jar.getName());
                eStatement.setInt(2, jar.getTheme().ordinal());
                eStatement.setDouble(3, jar.getBudget());
                eStatement.setInt(4, jar.getId());
                eStatement.executeUpdate();
                eStatement.close();

                // Update expense <-> tag table
                removeJarExpenseRelations(connection, jar.getId());
                addJarExpenseRelations(connection, jar.getExpenses(), jar.getId());
                return true;
            }
        } catch (final SQLException e) {
            throw new RocketDatabaseWriteException(e);
        }
        return false;
    }

    @Override
    public boolean deleteJar(Jar jar) {
        try (Connection connection = connect()) {
            if (jars.contains(jar)) {
                final PreparedStatement eStatement = connection.prepareStatement("DELETE FROM JARS WHERE id=?");
                eStatement.setInt(1, jar.getId());
                eStatement.executeUpdate();
                eStatement.close();

                removeAssociatedExpenses(connection, jar.getId());
                removeJarExpenseRelations(connection, jar.getId());
                jars.remove(jar);
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
