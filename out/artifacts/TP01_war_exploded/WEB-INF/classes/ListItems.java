
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ListItems extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        // Lire l'article saisi dans le formulaire.
        String newItem = request.getParameter("newItem");

        // Retrouver la session du navigateur.
        HttpSession session = request.getSession();

        // Chercher la liste déjà enregistrée dans cette session.
        ArrayList<String> items =
                (ArrayList<String>) session.getAttribute("items");

        // Si c'est le premier article, créer une nouvelle liste.
        if (items == null) {
            items = new ArrayList<String>();
        }

        // Ajouter l'article s'il n'est pas vide.
        if (newItem != null && !newItem.trim().isEmpty()) {
            items.add(newItem.trim());
        }

        // Enregistrer la liste dans la session.
        session.setAttribute("items", items);

        // Afficher tous les articles enregistrés.
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        out.println("<!DOCTYPE html>");
        out.println("<html lang=\"fr\">");
        out.println("<head><meta charset=\"UTF-8\"><title>Mes articles</title></head>");
        out.println("<body>");
        out.println("<h1>Articles enregistrés dans votre session</h1>");
        out.println("<ul>");

        for (String item : items) {
            out.println("<li>" + item + "</li>");
        }

        out.println("</ul>");
        out.println("<p><a href=\"items.html\">Ajouter un autre article</a></p>");
        out.println("</body>");
        out.println("</html>");
    }
}