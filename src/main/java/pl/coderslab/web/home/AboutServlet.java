package pl.coderslab.web.home;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet("/about")
public class AboutServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String var1 = "Lorem ipsum dolor sit amet";
        String var2 = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Aliquam at porttitor sem. Aliquam erat\n" +
                "                    volutpat. Donec placerat nisl magna, et faucibus arcu condimentum sed. Lorem ipsum dolor sit\n" +
                "                    amet, consectetur adipiscing elit. Aliquam at porttitor sem. Aliquam erat volutpat. Donec\n" +
                "                    placerat nisl magna, et faucibus arcu condimentum sed.";
        request.setAttribute("var1", var1);
        request.setAttribute("var2", var2);
        getServletContext().getRequestDispatcher("/WEB-INF/views/home/about.jsp").forward(request, response);
    }
}
