package pl.coderslab.dao;

import pl.coderslab.exception.NotFoundException;
import pl.coderslab.model.DayName;
import pl.coderslab.utils.DbUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class DayNameDao {
    private static final String CREATE_DAYNAME_QUERY = "INSERT INTO day_name(name,display_order) VALUES (?,?);";
    private static final String DELETE_DAYNAME_QUERY = "DELETE FROM day_name where id = ?;";
    private static final String FIND_ALL_DAYNAME_QUERY = "SELECT * FROM day_name;";
    private static final String READ_DAYNAME_QUERY = "SELECT * from day_name where id = ?;";
    private static final String UPDATE_DAYNAME_QUERY = "UPDATE	day_name SET name = ? , display_order = ? WHERE	id = ?;";




    public DayName read(Integer dayNameId) {
        DayName dayName = new DayName();
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(READ_DAYNAME_QUERY)
        ) {
            statement.setInt(1, dayNameId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    dayName.setId(resultSet.getInt("id"));
                    dayName.setName(resultSet.getString("name"));
                    dayName.setDisplayOrder(resultSet.getInt("display_order"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dayName;

    }


    public List<DayName> findAll() {
        List<DayName> dayNameList = new ArrayList<>();
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_ALL_DAYNAME_QUERY);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                DayName dayNameToAdd = new DayName();
                dayNameToAdd.setId(resultSet.getInt("id"));
                dayNameToAdd.setName(resultSet.getString("name"));
                dayNameToAdd.setDisplayOrder(resultSet.getInt("display_order"));
                dayNameList.add(dayNameToAdd);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dayNameList;
    }

    public DayName create(DayName dayName) {
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement insertStm = connection.prepareStatement(CREATE_DAYNAME_QUERY,
                     PreparedStatement.RETURN_GENERATED_KEYS)) {
            insertStm.setString(1, dayName.getName());
            insertStm.setInt(2, dayName.getDisplayOrder());
            int result = insertStm.executeUpdate();

            if (result != 1) {
                throw new RuntimeException("Execute update returned " + result);
            }

            try (ResultSet generatedKeys = insertStm.getGeneratedKeys()) {
                if (generatedKeys.first()) {
                    dayName.setId(generatedKeys.getInt(1));
                    return dayName;
                } else {
                    throw new RuntimeException("Generated key was not found");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public void delete(Integer dayNameId) {
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_DAYNAME_QUERY)) {
            statement.setInt(1, dayNameId);
            int deleted = statement.executeUpdate();

            if (deleted != 1) {
                throw new NotFoundException("Day not found");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void update(DayName dayName) {
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_DAYNAME_QUERY)) {
            statement.setInt(3, dayName.getId());
            statement.setString(1, dayName.getName());
            statement.setInt(2, dayName.getDisplayOrder());

            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
