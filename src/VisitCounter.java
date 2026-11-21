
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class VisitCounter extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        int visits = 0;

        // Lire le compteur envoyé par le navigateur, s'il existe.
        Cookie[] cookies = request.getCookies();

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("visitCountTP01".equals(cookie.getName())) {
                    try {
                        visits = Integer.parseInt(cookie.getValue());
                    } catch (NumberFormatException e) {
                        visits = 0;
                    }
                    break;
                }
            }
        }

        // Cette demande compte comme une visite.
        visits++;

        // Envoyer le nouveau total au navigateur dans un cookie.
        Cookie counterCookie =
                new Cookie("visitCountTP01", String.valueOf(visits));
        counterCookie.setPath(request.getContextPath());
        counterCookie.setMaxAge(24 * 60 * 60);
        response.addCookie(counterCookie);

        // Afficher le total.
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        out.println("<!DOCTYPE html>");
        out.println("<html lang=\"fr\">");
        out.println("<head><meta charset=\"UTF-8\"><title>Compteur</title></head>");
        out.println("<body>");
        out.println("<h1>Compteur de visites</h1>");
        out.println("<p>Vous avez consulté cette page "
                    + visits + " fois.</p>");
        out.println("<p><a href=\"VisitCounter\">Compter une autre visite</a></p>");
        out.println("</body>");
        out.println("</html>");
    }
}