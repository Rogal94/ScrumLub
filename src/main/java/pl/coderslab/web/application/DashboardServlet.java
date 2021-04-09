package pl.coderslab.web.application;

import pl.coderslab.dao.DayNameDao;
import pl.coderslab.dao.PlanDao;
import pl.coderslab.dao.RecipeDao;
import pl.coderslab.model.Admin;
import pl.coderslab.model.DayName;
import pl.coderslab.model.PlanDetailsRecord;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@WebServlet(name = "DashboardServlet", value = "/dashboard")
public class DashboardServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Admin admin = (Admin) session.getAttribute("adminLogged");
        DayNameDao dayNameDao = new DayNameDao();
        List<DayName> dayNameList = dayNameDao.findAll();
        Map<String, List<PlanDetailsRecord>> planMap = new LinkedHashMap<>();
        for(DayName dayName : dayNameList) {
            List<PlanDetailsRecord> plan = new ArrayList<>();
            for(PlanDetailsRecord elem : PlanDao.latestPlanInfo(admin.getId()))
                if(dayName.getName().equals(elem.getDayName())) {
                    plan.add(elem);
                }
            planMap.put(dayName.getName(),plan);
        }
        request.setAttribute("adminLogged", admin);
        request.setAttribute("countRecipe", RecipeDao.numberOfRecipes(admin));
        request.setAttribute("countPlan", PlanDao.numberOfPlans(admin));
        request.setAttribute("latestPlanName", PlanDao.latestPlanName(admin));
        request.setAttribute("latestPlanInfoList", planMap);
        getServletContext().getRequestDispatcher("/WEB-INF/views/app/dashboard.jsp").forward(request, response);
    }
}