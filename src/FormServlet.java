
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.servlet.http.Cookie;
public class FormServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        // Lire correctement les caractères accentués du formulaire
        request.setCharacterEncoding("UTF-8");

        // Lire les valeurs des champs grâce à leur attribut name
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String birthDay = request.getParameter("birthDay");
	String sex = request.getParameter("sex");

	// Si aucun bouton de sexe n'a été choisi, on utilise une valeur vide.
	if (sex == null) {
	    sex = "";
	}

	// Préparer les quatre cookies avec les valeurs du formulaire.
	Cookie cookieFirstName = new Cookie("firstName", firstName);
	Cookie cookieLastName = new Cookie("lastName", lastName);
	Cookie cookieBirthDay = new Cookie("birthDay", birthDay);
	Cookie cookieSex = new Cookie("sex", sex);

	// Les cookies seront disponibles pour les pages de l'application TP01.
	cookieFirstName.setPath(request.getContextPath());
	cookieLastName.setPath(request.getContextPath());
	cookieBirthDay.setPath(request.getContextPath());
	cookieSex.setPath(request.getContextPath());

	// Les garder dans le navigateur pendant 24 heures.
	cookieFirstName.setMaxAge(24 * 60 * 60);
	cookieLastName.setMaxAge(24 * 60 * 60);
	cookieBirthDay.setMaxAge(24 * 60 * 60);
	cookieSex.setMaxAge(24 * 60 * 60);

	// Ajouter les cookies à la réponse envoyée par le serveur.
	response.addCookie(cookieFirstName);
	response.addCookie(cookieLastName);
	response.addCookie(cookieBirthDay);
	response.addCookie(cookieSex);

        // Indiquer que la réponse sera une page HTML en UTF-8
        response.setContentType("text/html;charset=UTF-8");

        // Permet d'écrire la page envoyée au navigateur
        PrintWriter out = response.getWriter();

        out.println("<!DOCTYPE html>");
        out.println("<html lang=\"fr\">");
        out.println("<head><meta charset=\"UTF-8\"><title>Résultat</title></head>");
        out.println("<body>");
        out.println("<h1>Informations reçues</h1>");
        out.println("<table border=\"1\">");
        out.println("<tr><th>Champ</th><th>Valeur</th></tr>");
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