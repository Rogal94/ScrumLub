package pl.coderslab.web.admin;

import pl.coderslab.dao.AdminDao;
import pl.coderslab.model.Admin;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "BlockSuperadminUsersServlet", value = "/app/admin/users/block")
public class BlockSuperadminUsersServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Admin adminLogged =(Admin)session.getAttribute("adminLogged");
        if(adminLogged.getSuperadmin()!=1) {
            response.sendRedirect("/dashboard");
            return;
        }
        int adminId = Integer.parseInt(request.getParameter("adminId"));
        AdminDao adminDao = new AdminDao();
        Admin admin = adminDao.read(adminId);
        admin.setEnable(0);
        adminDao.update(admin);
        response.sendRedirect("/app/admin/users");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
