
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
public class ShowSession extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();

        // Retrouver et augmenter le compteur de visites de cette session.
        Integer visits = (Integer) session.getAttribute("visits");

        if (visits == null) {
            visits = 1;
        } else {
            visits++;
        }

        session.setAttribute("visits", visits);

        // Chercher les informations déjà enregistrées dans la session.
        String fullName = (String) session.getAttribute("fullName");
        String birthDay = (String) session.getAttribute("birthDay");

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        out.println("<!DOCTYPE html>");
        out.println("<html lang=\"fr\">");
        out.println("<head><meta charset=\"UTF-8\"><title>Ma session</title></head>");
        out.println("<body>");

        if (fullName == null || birthDay == null) {
            // Première visite : demander les informations.
            out.println("<h1>Bienvenue sur mon site !</h1>");
            out.println("<p>Indiquez votre nom et votre date de naissance.</p>");

            out.println("<form action=\"ShowSession\" method=\"post\">");
            out.println("<label>Nom complet : "
                    + "<input type=\"text\" name=\"fullName\" required></label><br><br>");
            out.println("<label>Date de naissance : "
                    + "<input type=\"date\" name=\"birthDay\" required></label><br><br>");
            out.println("<button type=\"submit\">Envoyer</button>");
            out.println("</form>");
        } else {
            // Visite suivante : calculer le délai jusqu'au prochain anniversaire.
            long days = daysUntilBirthday(birthDay);

            out.println("<h1>Bienvenue à nouveau, "
                    + fullName + " !</h1>");
            out.println("<p>Il reste " + days
                    + " jour(s) avant votre prochain anniversaire.</p>");
        }

        out.println("<p>Visites dans cette session : " + visits + "</p>");
        out.println("</body>");
        out.println("</html>");
    }

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String fullName = request.getParameter("fullName");
        String birthDay = request.getParameter("birthDay");

        if (fullName == null || fullName.trim().isEmpty()
                || birthDay == null || birthDay.isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST,
                    "Le nom et la date de naissance sont obligatoires.");
            return;
        }

        // Vérifier que la date reçue est valide.
        try {
            LocalDate.parse(birthDay);
        } catch (DateTimeParseException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST,
                    "La date de naissance n'est pas valide.");
            return;
        }

        // Enregistrer les informations sur le serveur, dans la session.
        HttpSession session = request.getSession();
        session.setAttribute("fullName", fullName.trim());
        session.setAttribute("birthDay", birthDay);

        // Revenir à doGet() pour afficher le message et le calcul.
        response.sendRedirect(request.getContextPath() + "/ShowSession");
    }

    private long daysUntilBirthday(String birthDay) {
        LocalDate today = LocalDate.now();
        LocalDate birthday = LocalDate.parse(birthDay);

        // Prendre le prochain anniversaire, dans l'année actuelle ou la suivante.
        LocalDate nextBirthday = birthday.withYear(today.getYear());

        if (nextBirthday.isBefore(today)) {
            nextBirthday = nextBirthday.plusYears(1);
        }

        return ChronoUnit.DAYS.between(today, nextBirthday);
    }
}