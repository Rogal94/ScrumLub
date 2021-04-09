package pl.coderslab.web.application.plan;

import pl.coderslab.dao.PlanDao;
import pl.coderslab.model.Admin;
import pl.coderslab.model.Plan;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "EditPlanServlet", value = "/app/plan/edit")
public class EditPlanServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("planId"));
        PlanDao planDao = new PlanDao();
        Plan plan = planDao.read(id);
        request.setAttribute("plan",plan);
        getServletContext().getRequestDispatcher("/WEB-INF/views/app/editPlan.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        PlanDao planDao = new PlanDao();
        Plan plan = planDao.read(Integer.parseInt(request.getParameter("id")));
        if(plan.getAdminId() == ((Admin)session.getAttribute("adminLogged")).getId()){
            plan.setName(request.getParameter("name"));
            plan.setDescription(request.getParameter("description"));
            planDao.update(plan);
        }
        response.sendRedirect("/app/plan/list");
    }
}
