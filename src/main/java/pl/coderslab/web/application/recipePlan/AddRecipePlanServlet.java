package pl.coderslab.web.application.recipePlan;

import pl.coderslab.dao.*;
import pl.coderslab.model.*;
import pl.coderslab.verification.AdminVerification;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "AddRecipePlanServlet", value ="/app/recipe/plan/add")
public class AddRecipePlanServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Admin admin =(Admin)session.getAttribute("adminLogged");
        PlanDao planDao = new PlanDao();
        List<Plan> planList = planDao.findByAdmin(admin.getId());
        RecipeDao recipeDao = new RecipeDao();
        List<Recipe> recipeList = recipeDao.findByAdmin(admin.getId());

        DayNameDao dayNameDao = new DayNameDao();
        List<DayName> dayNameList = dayNameDao.findAll();

        request.setAttribute("planList",planList);
        request.setAttribute("recipeList",recipeList);
        request.setAttribute("dayNameList",dayNameList);
        getServletContext().getRequestDispatcher("/WEB-INF/views/app/recipePlanAdd.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        boolean verify = AdminVerification.verifyParameters(request.getParameterMap());
        PlanDao planDao = new PlanDao();
        Plan plan = planDao.read(Integer.parseInt(request.getParameter("choosePlan")));
        if(verify && plan.getAdminId() == ((Admin)session.getAttribute("adminLogged")).getId()) {
            RecipePlanDao recipePlanDao = new RecipePlanDao();
            RecipePlan newRecipePlan = new RecipePlan();
            newRecipePlan.setMealName(request.getParameter("nameMeal"));
            newRecipePlan.setPlanId(Integer.parseInt(request.getParameter("choosePlan")));
            newRecipePlan.setDisplayOrder(Integer.parseInt(request.getParameter("mealNumber")));
            newRecipePlan.setRecipeId(Integer.parseInt(request.getParameter("recipe")));
            newRecipePlan.setDayNameId(Integer.parseInt(request.getParameter("day")));
            recipePlanDao.create(newRecipePlan);
        }
        response.sendRedirect("/app/recipe/plan/add");
    }
}
