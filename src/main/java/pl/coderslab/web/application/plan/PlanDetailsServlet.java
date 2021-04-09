package pl.coderslab.web.application.plan;

import pl.coderslab.dao.DayNameDao;
import pl.coderslab.dao.PlanDao;
import pl.coderslab.model.DayName;
import pl.coderslab.model.Plan;
import pl.coderslab.model.PlanDetailsRecord;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@WebServlet(name = "PlanDetailsServlet", value = "/app/plan/details")
public class PlanDetailsServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("planId"));
        PlanDao planDao = new PlanDao();
        Plan plan = planDao.read(id);
        DayNameDao dayNameDao = new DayNameDao();
        List<DayName> dayNameList = dayNameDao.findAll();
        Map<String, List<PlanDetailsRecord>> planMap = new LinkedHashMap<>();
        for(DayName dayName : dayNameList) {
            List<PlanDetailsRecord> plann = new ArrayList<>();
            for(PlanDetailsRecord elem : PlanDao.planInfo(id))
                if(dayName.getName().equals(elem.getDayName())) {
                    plann.add(elem);
                }
            planMap.put(dayName.getName(),plann);
        }
        request.setAttribute("plan", plan);
        request.setAttribute("planInfoList", planMap);
        getServletContext().getRequestDispatcher("/WEB-INF/views/app/planDetails.jsp").forward(request, response);
    }
}
