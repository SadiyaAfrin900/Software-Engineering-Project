package com.rocket.jarapp.persistence.hsqldb;

import android.util.Log;

import com.rocket.jarapp.objects.Date;
import com.rocket.jarapp.objects.Expense;
import com.rocket.jarapp.objects.Tag;
import com.rocket.jarapp.objects.Time;
import com.rocket.jarapp.objects.exceptions.RocketDatabaseAccessException;
import com.rocket.jarapp.objects.exceptions.RocketDatabaseWriteException;
import com.rocket.jarapp.persistence.ExpensePersistence;
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

public class ExpensePersistenceHSQLDB implements ExpensePersistence {
    private List<Expense> expenses;
    private String dbPath;
    private int nextId;

    public ExpensePersistenceHSQLDB(String dbPath, TagPersistence tagPersistence) {
        this.dbPath = dbPath;
        expenses = new ArrayList<>();
        loadSavedExpenses(tagPersistence);

    }

    private Connection connect() throws SQLException {
        return DriverManager.getConnection("jdbc:hsqldb:file:" + dbPath + ";shutdown=true", "SA", "");
    }

    /**
     * loadSavedExpenses
     *
     * Loads all expenses and stores them in our system
     */
    private void loadSavedExpenses(TagPersistence tagPersistence) {
        try (Connection connection = connect()) {
            final Statement statement = connection.createStatement();
            final ResultSet resultSet = statement.executeQuery("SELECT * FROM EXPENSES");
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String note = resultSet.getString("note");
                double amount = resultSet.getDouble("amount");
                int day = resultSet.getInt("day");
                int monthNum = resultSet.getInt("month");
                int year = resultSet.getInt("year");
                int hour = resultSet.getInt("hour");
                int minute = resultSet.getInt("minute");
                Date.Month month = Date.Month.values()[monthNum - 1];

                Date expenseDate = new Date(day, month, year);
                Time expenseTime = new Time(hour, minute);

                Expense expense = new Expense(id, name, note, amount, expenseDate, expenseTime);
                assignTags(expense, tagPersistence);
                expenses.add(expense);

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
     * assignTags
     *
     * Adds the linked tags to the expense object
     */
    private void assignTags(Expense expense, TagPersistence tagPersistence) {
        int tagId = expense.getId();

        try (Connection connection = connect()) {
            String statementStr = "SELECT tagId FROM EXPENSETAGS WHERE EXPENSETAGS.expenseId=?";

            final PreparedStatement statement = connection.prepareStatement(statementStr);
            statement.setInt(1, tagId);
            final ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("tagId");
                for (Tag tag : tagPersistence.getAllTags()) {
                    if (tag.getId() == id) {
                        expense.addTag(tag);
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
    public List<Expense> getAllExpenses() {
        return Collections.unmodifiableList(expenses);
    }

    @Override
    public boolean insertExpense(Expense expense) {
        try (Connection connection = connect()) {
            if (!expenses.contains(expense)) {
                // Insert Expense
                final PreparedStatement eStatement = connection.prepareStatement("INSERT INTO EXPENSES VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)");
                eStatement.setInt(1, expense.getId());
                eStatement.setString(2, expense.getName());
                eStatement.setString(3, expense.getNote());
                eStatement.setDouble(4, expense.getAmount());
                eStatement.setInt(5, expense.getDate().getDay());
                eStatement.setInt(6, expense.getDate().getMonth().getMonthNum());
                eStatement.setInt(7, expense.getDate().getYear());
                eStatement.setInt(8, expense.getTime().getHour());
                eStatement.setInt(9, expense.getTime().getMinute());
                eStatement.executeUpdate();
                eStatement.close();

                // Add relationships for all of its tags in the expense
                addTagExpenseRelations(connection, expense.getTags(), expense.getId());

                expenses.add(expense);
                nextId++;
                return true;
            }
        } catch (final SQLException e) {
            throw new RocketDatabaseWriteException(e);
        }
        return false;
    }

    /**
     * deleteTagExpenseRelations
     *
     * Removes all links to any tags to the specified expense
     */
    private void deleteTagExpenseRelations(Connection connection, int expenseId) throws SQLException{
        final PreparedStatement etStatement = connection.prepareStatement("DELETE FROM EXPENSETAGS WHERE expenseId=?");
        etStatement.setInt(1, expenseId);
        etStatement.executeUpdate();
        etStatement.close();
    }

    /**
     * addTagExpenseRelations
     *
     * Adds links from the provided expense to the list of tags
     */
    private void addTagExpenseRelations(Connection connection, List<Tag> tags, int expenseId) throws SQLException {
        for (Tag tag : tags) {
            final PreparedStatement etStatement = connection.prepareStatement("INSERT INTO EXPENSETAGS VALUES(?, ?)");
            etStatement.setInt(1, expenseId);
            etStatement.setInt(2, tag.getId());
            etStatement.executeUpdate();
            etStatement.close();
        }
    }

    @Override
    public boolean deleteExpense(Expense expense) {
        try (Connection connection = connect()) {
            if (expenses.contains(expense)) {
                final PreparedStatement eStatement = connection.prepareStatement("DELETE FROM EXPENSES WHERE id=?");
                eStatement.setInt(1, expense.getId());
                eStatement.executeUpdate();
                eStatement.close();

                deleteTagExpenseRelations(connection, expense.getId());
                expenses.remove(expense);
                return true;
            }
        } catch (final SQLException e) {
            throw new RocketDatabaseWriteException(e);
        }
        return false;
    }

    @Override
    public boolean updateExpense(Expense expense) {
        try (Connection connection = connect()) {
            if (expenses.contains(expense)) {
                // Update the expense
                String expenseStatementStr = "UPDATE EXPENSES " +
                        "SET name=?, note=?, amount=?, day=?, month=?, year=?, hour=?, minute=? " +
                        "WHERE id=?";
                final PreparedStatement eStatement = connection.prepareStatement(expenseStatementStr);
                eStatement.setString(1, expense.getName());
                eStatement.setString(2, expense.getNote());
                eStatement.setDouble(3, expense.getAmount());
                eStatement.setInt(4, expense.getDate().getDay());
                eStatement.setInt(5, expense.getDate().getMonth().getMonthNum());
                eStatement.setInt(6, expense.getDate().getYear());
                eStatement.setInt(7, expense.getTime().getHour());
                eStatement.setInt(8, expense.getTime().getMinute());
                eStatement.setInt(9, expense.getId());
                eStatement.executeUpdate();
                eStatement.close();

                // Update expense <-> tag table
                deleteTagExpenseRelations(connection, expense.getId());
                addTagExpenseRelations(connection, expense.getTags(), expense.getId());
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
