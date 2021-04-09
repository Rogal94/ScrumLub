package pl.coderslab.dao;

import pl.coderslab.exception.NotFoundException;
import pl.coderslab.model.Admin;
import pl.coderslab.model.Plan;
import pl.coderslab.model.PlanDetailsRecord;
import pl.coderslab.utils.DbUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PlanDao {
    // ZAPYTANIA SQL
    private static final String CREATE_PLAN_QUERY = "INSERT INTO plan(name,description,created,admin_id) VALUES (?,?,?,?);";
    private static final String DELETE_PLAN_QUERY = "DELETE FROM plan where id = ?;";
    private static final String FIND_ALL_PLAN_QUERY = "SELECT * FROM plan;";
    private static final String READ_PLAN_QUERY = "SELECT * from plan where id = ?;";
    private static final String UPDATE_PLAN_QUERY = "UPDATE	plan SET name = ? , description = ?, created = ?, admin_id = ? WHERE	id = ?;";
    private static final String LATEST_PLAN_QUERY = "SELECT MAX(id) from plan WHERE admin_id = ?;";
    private static final String INFO_PLAN_QUERY = "SELECT day_name.name as day_name, meal_name, recipe.name as recipe_name, recipe_id, recipe_plan.id" +
                                                    " FROM `recipe_plan`" +
                                                    " JOIN day_name on day_name.id=day_name_id" +
                                                    " JOIN recipe on recipe.id=recipe_id WHERE plan_id = ?" +
                                                    " ORDER by day_name.display_order, recipe_plan.display_order;";
    private static final String INFO_LATEST_PLAN_QUERY = "SELECT day_name.name as day_name, meal_name,  recipe.name as recipe_name, recipe_id" +
                                                    " FROM `recipe_plan`" +
                                                    " JOIN day_name on day_name.id=day_name_id" +
                                                    " JOIN recipe on recipe.id=recipe_id WHERE" +
                                                    " recipe_plan.plan_id =  (SELECT MAX(id) from plan WHERE admin_id = ?)" +
                                                    " ORDER by day_name.display_order, recipe_plan.display_order;";
    private static final String FIND_BY_ADMIN_PLAN_QUERY = "SELECT * FROM plan WHERE admin_id = ?;";

    /**
     * Get plan by id
     *
     * @param planId
     * @return
     */
    public Plan read(Integer planId) {
        Plan plan = new Plan();
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(READ_PLAN_QUERY)
        ) {
            statement.setInt(1, planId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    plan.setId(resultSet.getInt("id"));
                    plan.setName(resultSet.getString("name"));
                    plan.setDescription(resultSet.getString("description"));
                    plan.setCreated(resultSet.getString("created"));
                    plan.setAdminId(resultSet.getInt("admin_id"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return plan;

    }

    /**
     * Return all plans
     *
     * @return
     */
    public List<Plan> findAll() {
        List<Plan> planList = new ArrayList<>();
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_ALL_PLAN_QUERY);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Plan planToAdd = new Plan();
                planToAdd.setId(resultSet.getInt("id"));
                planToAdd.setName(resultSet.getString("name"));
                planToAdd.setDescription(resultSet.getString("description"));
                planToAdd.setCreated(resultSet.getString("created"));
                planToAdd.setAdminId(resultSet.getInt("admin_id"));
                planList.add(planToAdd);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return planList;

    }

    /**
     * Create plan
     *
     * @param plan
     * @return
     */
    public Plan create(Plan plan) {
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(CREATE_PLAN_QUERY,
                     PreparedStatement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, plan.getName());
            statement.setString(2, plan.getDescription());
            statement.setString(3, plan.getCreated());
            statement.setInt(4, plan.getAdminId());
            int result = statement.executeUpdate();

            if (result != 1) {
                throw new RuntimeException("Execute update returned " + result);
            }

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.first()) {
                    plan.setId(generatedKeys.getInt(1));
                    return plan;
                } else {
                    throw new RuntimeException("Generated key was not found");
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * Remove plan by id
     *
     * @param planId
     */
    public void delete(Integer planId) {
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_PLAN_QUERY)) {
            statement.setInt(1, planId);
            int deleted = statement.executeUpdate();

            if (deleted != 1) {
                throw new NotFoundException("Plan not found");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * Update plan
     *
     * @param plan
     */
    public void update(Plan plan) {
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_PLAN_QUERY)) {
            statement.setInt(5, plan.getId());
            statement.setString(1, plan.getName());
            statement.setString(2, plan.getDescription());
            statement.setString(3, plan.getCreated());
            statement.setInt(4, plan.getAdminId());

            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    public static int numberOfPlans(Admin admin) {
        PlanDao planDao = new PlanDao();
        List <Plan> plans = planDao.findAll();
        int count = 0;
        for(int i = 0; i<plans.size(); i++) {
            if (plans.get(i).getAdminId() == admin.getId()) {
                count ++;
            }
        }
        return count;
    }

    public static String latestPlanName (Admin admin) {
        int planId = 0;
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(LATEST_PLAN_QUERY)
        ) {
            statement.setInt(1, admin.getId());
            try (ResultSet resultSet = statement.executeQuery()) {
                resultSet.next();
                planId = resultSet.getInt("MAX(id)");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        PlanDao planDao = new PlanDao();
        Plan plan = planDao.read(planId);
        return plan.getName();
    }

    public static List<PlanDetailsRecord> planInfo (int planId) {
        List<PlanDetailsRecord> planInfo = new ArrayList<>();
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(INFO_PLAN_QUERY)
        ) {
            statement.setInt(1, planId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    PlanDetailsRecord plan = new PlanDetailsRecord();
                    plan.setDayName(resultSet.getString("day_name"));
                    plan.setMealName(resultSet.getString("meal_name"));
                    plan.setRecipe(resultSet.getString("recipe_name"));
                    plan.setRecipeId(resultSet.getInt("recipe_id"));
                    plan.setRecipePlanId(resultSet.getInt("recipe_plan.id"));
                    planInfo.add(plan);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return planInfo;
    }

    public static List<PlanDetailsRecord> latestPlanInfo (int adminId) {
        List<PlanDetailsRecord> planInfo = new ArrayList<>();
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(INFO_LATEST_PLAN_QUERY)
        ) {
            statement.setInt(1, adminId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    PlanDetailsRecord plan = new PlanDetailsRecord();
                    plan.setDayName(resultSet.getString("day_name"));
                    plan.setMealName(resultSet.getString("meal_name"));
                    plan.setRecipe(resultSet.getString("recipe_name"));
                    plan.setRecipeId(resultSet.getInt("recipe_id"));
                    planInfo.add(plan);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return planInfo;
    }

    public List<Plan> findByAdmin(int adminId) {
        List<Plan> planList = new ArrayList<>();
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_BY_ADMIN_PLAN_QUERY)
             ) {
            statement.setInt(1, adminId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Plan planToAdd = new Plan();
                planToAdd.setId(resultSet.getInt("id"));
                planToAdd.setName(resultSet.getString("name"));
                planToAdd.setDescription(resultSet.getString("description"));
                planToAdd.setCreated(resultSet.getString("created"));
                planToAdd.setAdminId(resultSet.getInt("admin_id"));
                planList.add(planToAdd);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return planList;

    }
}


