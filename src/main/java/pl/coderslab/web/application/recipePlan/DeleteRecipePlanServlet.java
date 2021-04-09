package pl.coderslab.web.application.recipePlan;

import pl.coderslab.dao.PlanDao;
import pl.coderslab.dao.RecipePlanDao;
import pl.coderslab.model.Admin;
import pl.coderslab.model.Plan;
import pl.coderslab.model.RecipePlan;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "DeleteRecipePlanServlet", value = "/app/recipe/plan/delete")
public class DeleteRecipePlanServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        RecipePlanDao recipePlanDao = new RecipePlanDao();
        PlanDao planDao = new PlanDao();
        int recipePlanId = Integer.parseInt(request.getParameter("recipePlanId"));
        RecipePlan recipePlanToDelete = recipePlanDao.read(recipePlanId);
        Plan plan = planDao.read(recipePlanToDelete.getPlanId());
        if(plan.getAdminId() == ((Admin)session.getAttribute("adminLogged")).getId()){
            recipePlanDao.delete(recipePlanToDelete.getId());
        }
        response.sendRedirect("/app/plan/details?planId=" + request.getParameter("showPlanId"));
    }
}
