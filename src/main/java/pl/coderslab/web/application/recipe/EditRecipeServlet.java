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

@WebServlet(name = "EditRecipeServlet", value = "/app/recipe/edit")
public class EditRecipeServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("recipeId"));
        RecipeDao recipeDao = new RecipeDao();
        Recipe recipe = recipeDao.read(id);
        request.setAttribute("recipe",recipe);
        getServletContext().getRequestDispatcher("/WEB-INF/views/app/editRecipe.jsp").forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String dataTimeUpdated = LocalDateTime.now().format(formatter);

        RecipeDao recipeDao = new RecipeDao();
        Recipe recipe = recipeDao.read(Integer.parseInt(request.getParameter("id")));
        if(recipe.getAdminId() == ((Admin)session.getAttribute("adminLogged")).getId()) {
            recipe.setName(request.getParameter("name"));
            recipe.setDescription(request.getParameter("description"));
            recipe.setIngredients(request.getParameter("ingredients"));
            recipe.setPreparation(request.getParameter("preparation"));
            recipe.setPreparationTime(request.getParameter("preparationTime"));
            recipe.setUpdated(dataTimeUpdated);
            recipeDao.update(recipe);
        }
        response.sendRedirect("/app/recipe/list");
    }
}
