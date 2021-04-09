package pl.coderslab.web.application.plan;

import pl.coderslab.dao.PlanDao;
import pl.coderslab.model.Admin;
import pl.coderslab.model.Plan;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "PlanListServlet", value = "/app/plan/list")
public class PlanListServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Admin admin =(Admin)session.getAttribute("adminLogged");
        PlanDao planDao = new PlanDao();
        List<Plan> planList = planDao.findByAdmin(admin.getId());
        request.setAttribute("planListSort",planList);
        getServletContext().getRequestDispatcher("/WEB-INF/views/app/planList.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
