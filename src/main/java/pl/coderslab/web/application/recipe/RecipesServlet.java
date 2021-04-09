package pl.coderslab.web.application.recipe;

import pl.coderslab.dao.RecipeDao;
import pl.coderslab.model.Recipe;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/recipes")
public class RecipesServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RecipeDao recipeDao = new RecipeDao();
        List<Recipe> recipeList = recipeDao.findAll();
        request.setAttribute("recipeListSort",recipeList);
        getServletContext().getRequestDispatcher("/WEB-INF/views/app/recipes.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RecipeDao recipeDao = new RecipeDao();
        List<Recipe> recipeList = recipeDao.findByName(request.getParameter("recipeName"));
        request.setAttribute("recipeListSort",recipeList);
        getServletContext().getRequestDispatcher("/recipes.jsp").forward(request, response);
    }
}
