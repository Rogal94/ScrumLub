package pl.coderslab.web.admin;

import pl.coderslab.dao.AdminDao;
import pl.coderslab.model.Admin;
import pl.coderslab.verification.AdminVerification;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "EditAdminServlet", value = "/app/user/edit")
public class EditAdminServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        getServletContext().getRequestDispatcher("/WEB-INF/views/app/editUser.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Admin admin = (Admin) session.getAttribute("adminLogged");
        AdminDao adminDao = new AdminDao();
        admin.setFirstName(request.getParameter("firstName"));
        admin.setLastName(request.getParameter("lastName"));
        admin.setEmail(request.getParameter("email"));
        boolean verEmail = AdminVerification.verifyEmail(request.getParameter("email"));
        boolean notEmpty = AdminVerification.verifyParameters(request.getParameterMap());
        boolean verifySameEmail = AdminVerification.sameEmail(request.getParameter("email"));
        if(verEmail && notEmpty && verifySameEmail) {
            adminDao.update(admin);
        }
        response.sendRedirect("/dashboard");
    }
}
