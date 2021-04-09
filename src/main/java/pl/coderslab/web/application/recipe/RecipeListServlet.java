package pl.coderslab.web.application.recipe;

import pl.coderslab.dao.RecipeDao;
import pl.coderslab.model.Admin;
import pl.coderslab.model.Recipe;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "RecipeListServlet", value = "/app/recipe/list")
public class RecipeListServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Admin admin =(Admin)session.getAttribute("adminLogged");
        RecipeDao recipeDao = new RecipeDao();
        List<Recipe> recipeList = recipeDao.findByAdmin(admin.getId());
        request.setAttribute("errorDelete",session.getAttribute("errorDelete"));
        session.removeAttribute("errorDelete");
        request.setAttribute("recipeListSort",recipeList);
        getServletContext().getRequestDispatcher("/WEB-INF/views/app/recipeList.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
