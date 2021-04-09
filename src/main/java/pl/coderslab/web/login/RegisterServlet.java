package pl.coderslab.web.login;

import pl.coderslab.dao.AdminDao;
import pl.coderslab.model.Admin;
import pl.coderslab.verification.AdminVerification;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "RegisterServlet", value = "/register")
public class RegisterServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Object admin = session.getAttribute("adminLogged");
        if (admin != null) {
            session.invalidate();
        }
        getServletContext().getRequestDispatcher("/WEB-INF/views/home/register.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        AdminDao adminDao = new AdminDao();
        Admin newAdmin = new Admin();
        boolean verify = AdminVerification.verifyParameters(request.getParameterMap());
        boolean verEmail = AdminVerification.verifyEmail(request.getParameter("email"));
        boolean verifyPassword = request.getParameter("password").equals(request.getParameter("repassword"));
        boolean verifySameEmail = AdminVerification.sameEmail(request.getParameter("email"));

        if (verifyPassword && verify && verEmail && verifySameEmail) {
            newAdmin.setFirstName(request.getParameter("name"));
            newAdmin.setLastName(request.getParameter("surname"));
            newAdmin.setEmail(request.getParameter("email"));
            newAdmin.setPassword(request.getParameter("password"));
            newAdmin.setEnable(1);
            adminDao.create(newAdmin);

            response.sendRedirect("/login");
        } else {
            response.sendRedirect("/register");
        }
    }
}
