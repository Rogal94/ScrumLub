package pl.coderslab.web.application.plan;

import pl.coderslab.dao.PlanDao;
import pl.coderslab.model.Admin;
import pl.coderslab.model.Plan;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "DeletePlanServlet", value = "/app/plan/delete")
public class DeletePlanServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        PlanDao planDao = new PlanDao();
        int planId = Integer.parseInt(request.getParameter("planId"));
        Plan planToDelete = planDao.read(planId);
        if (planToDelete.getAdminId() == ((Admin)session.getAttribute("adminLogged")).getId()) {
            planDao.delete(planToDelete.getId());
        }
        response.sendRedirect("/app/plan/list");
    }
}
