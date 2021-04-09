package pl.coderslab.web.admin;

import pl.coderslab.dao.AdminDao;
import pl.coderslab.model.Admin;
import pl.coderslab.verification.AdminVerification;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "EditAdminPasswordServlet", value = "/app/user/password/edit")
public class EditAdminPasswordServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        getServletContext().getRequestDispatcher("/WEB-INF/views/app/editUserPassword.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Admin admin =(Admin) session.getAttribute("adminLogged");
        AdminDao adminDao = new AdminDao();
        admin.setPassword(request.getParameter("password"));
        boolean verifyPassword = request.getParameter("password").equals(request.getParameter("repassword"));
        boolean notEmpty = AdminVerification.verifyParameters(request.getParameterMap());
        if(verifyPassword && notEmpty) {
            adminDao.update(admin);
        }
        response.sendRedirect("/dashboard");
    }
}
