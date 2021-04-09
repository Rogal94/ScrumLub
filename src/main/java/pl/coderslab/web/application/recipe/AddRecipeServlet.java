package pl.coderslab.web.application.recipe;

import pl.coderslab.dao.RecipeDao;
import pl.coderslab.model.Admin;
import pl.coderslab.model.Recipe;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@WebServlet(name = "AddRecipeServlet", value = "/app/recipe/add")
public class AddRecipeServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        getServletContext().getRequestDispatcher("/WEB-INF/views/app/addRecipe.jsp").forward(request, response);
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String dataTimeCreated = LocalDateTime.now().format(formatter);
        RecipeDao recipeDao = new RecipeDao();
        Recipe newRecipe = new Recipe();
        newRecipe.setName(request.getParameter("name"));
        newRecipe.setIngredients(request.getParameter("ingredients"));
        newRecipe.setDescription(request.getParameter("description"));
        newRecipe.setCreated(dataTimeCreated);
        newRecipe.setUpdated(dataTimeCreated);
        newRecipe.setPreparationTime(request.getParameter("preparationTime"));
        newRecipe.setPreparation(request.getParameter("preparation"));
        HttpSession session=request.getSession();
        Admin admin = (Admin)session.getAttribute("adminLogged");
        newRecipe.setAdminId(admin.getId());

        recipeDao.create(newRecipe);
        response.sendRedirect("/app/recipe/list");

    }
}
