package pl.coderslab.web.home;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet("/contact")
public class ContactServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Aliquam at porttitor sem.  Aliquam erat volutpat. Donec placerat nisl magna.";
        String information = "Lorem ipsum dolor";
        String contact = "Lorem ipsum dolor";
        String [] contactDetails = {"consectetur adipiscing elit", "sed do eiusmod tempor", "incididunt ut labore", "et dolore magna aliqua"};
        request.setAttribute("description", description);
        request.setAttribute("information", information);
        request.setAttribute("contact", contact);
        request.setAttribute("contactDetails", contactDetails);
        getServletContext().getRequestDispatcher("/WEB-INF/views/home/contact.jsp").forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
