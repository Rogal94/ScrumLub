package pl.coderslab.web.admin;

import pl.coderslab.dao.AdminDao;
import pl.coderslab.model.Admin;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "SuperadminUsersServlet", value = "/app/admin/users")
public class SuperadminUsersServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Admin admin =(Admin)session.getAttribute("adminLogged");
        if(admin.getSuperadmin()!=1) {
            response.sendRedirect("/dashboard");
            return;
        }
        AdminDao adminDao = new AdminDao();
        List<Admin> adminListAll = adminDao.findAll();
        List<Admin> adminList = new ArrayList<>();
        for(Admin elem : adminListAll) {
            if(elem.getEnable()==1)
                adminList.add(elem);
        }
        request.setAttribute("adminList" , adminList);
        getServletContext().getRequestDispatcher("/WEB-INF/views/app/superadminUsers.jsp").forward(request, response);
    }
}
