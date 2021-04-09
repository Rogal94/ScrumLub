package pl.coderslab.web.application.plan;

import pl.coderslab.dao.PlanDao;
import pl.coderslab.model.Admin;
import pl.coderslab.model.Plan;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@WebServlet(name = "AddPlanServlet", value = "/app/plan/add")
public class AddPlanServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        getServletContext().getRequestDispatcher("/WEB-INF/views/app/addPlan.jsp").forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PlanDao planDao = new PlanDao();
        Plan newPlan = new Plan();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String dataTimeCreated = LocalDateTime.now().format(formatter);

        newPlan.setName(request.getParameter("name"));
        newPlan.setDescription(request.getParameter("description"));
        newPlan.setCreated(dataTimeCreated);
        HttpSession session=request.getSession();
        Admin admin = (Admin)session.getAttribute("adminLogged");
        newPlan.setAdminId(admin.getId());
        planDao.create(newPlan);
        response.sendRedirect("/app/plan/list");
    }
}
