
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ReadCookie extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        // Valeurs affichées si un cookie n'est pas trouvé.
        String firstName = "Cookie absent";
        String lastName = "Cookie absent";
        String birthDay = "Cookie absent";
        String sex = "Cookie absent";

        // Le navigateur joint ses cookies à cette demande.
        Cookie[] cookies = request.getCookies();

        // Vérifier chaque cookie reçu.
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                String name = cookie.getName();

                if ("firstName".equals(name)) {
                    firstName = cookie.getValue();
                } else if ("lastName".equals(name)) {
                    lastName = cookie.getValue();
                } else if ("birthDay".equals(name)) {
                    birthDay = cookie.getValue();
                } else if ("sex".equals(name)) {
                    sex = cookie.getValue();
                }
            }
        }

        // Préparer la réponse HTML.
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        out.println("<!DOCTYPE html>");
        out.println("<html lang=\"fr\">");
        out.println("<head><meta charset=\"UTF-8\"><title>Mes cookies</title></head>");
        out.println("<body>");
        out.println("<h1>Valeurs des cookies</h1>");
        out.println("<table border=\"1\">");
        out.println("<tr><th>Cookie</th><th>Valeur</th></tr>");
        out.println("<tr><td>Prénom</td><td>" + firstName + "</td></tr>");
        out.println("<tr><td>Nom</td><td>" + lastName + "</td></tr>");
        out.println("<tr><td>Date de naissance</td><td>"
                    + birthDay + "</td></tr>");
        out.println("<tr><td>Sexe</td><td>" + sex + "</td></tr>");
        out.println("</table>");
        out.println("</body>");
        out.println("</html>");
    }
}