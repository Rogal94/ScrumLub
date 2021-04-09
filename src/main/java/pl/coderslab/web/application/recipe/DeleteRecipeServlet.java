package pl.coderslab.web.application.recipe;

import pl.coderslab.dao.RecipeDao;
import pl.coderslab.model.Admin;
import pl.coderslab.model.Recipe;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "DeleteRecipeServlet", value = "/app/recipe/delete")
public class DeleteRecipeServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        RecipeDao recipeDao = new RecipeDao();
        int recipeId = Integer.parseInt(request.getParameter("recipeId"));
        if(!RecipeDao.isRecipeAddToPlan(recipeId)) {
            Recipe recipeToDelete = recipeDao.read(recipeId);
            if (recipeToDelete.getAdminId() == ((Admin)session.getAttribute("adminLogged")).getId()) {
                recipeDao.delete(recipeToDelete.getId());
            }
        }else{
            session.setAttribute("errorDelete",true);
        }
        response.sendRedirect("/app/recipe/list");
    }
}
