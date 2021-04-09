package pl.coderslab.web.login;

import pl.coderslab.dao.AdminDao;
import pl.coderslab.model.Admin;
import pl.coderslab.verification.AdminVerification;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "LoginServlet", value = "/login")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Object admin = session.getAttribute("adminLogged");
        if(admin != null) {
            response.sendRedirect("/dashboard");
            return;
        }
        getServletContext().getRequestDispatcher("/WEB-INF/views/home/login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        AdminDao adminDao = new AdminDao();
        Admin admin = adminDao.readEmail(request.getParameter("email"));
        if(admin.getEmail() != null) {
            boolean verification = AdminVerification.adminVerification(admin,request.getParameter("password"));
            if(verification) {
                HttpSession session = request.getSession();
                session.invalidate();
                session = request.getSession(true);
                admin.setPassword("");
                session.setAttribute("adminLogged", admin);
                response.sendRedirect("/dashboard");
                return;
           }
        }
        response.sendRedirect("/login");
    }
}
