<%
    // Initialisation des variables
    String sessionUser = (String)session.getAttribute("sessionUser");
    String invalidate = request.getParameter("invalidate");
    String user = request.getParameter("user");

    // Calcule le temps de persistence de la session http
    long created  = System.currentTimeMillis();
    long current  = session.getCreationTime();
    int totalSeconds = (int)(created - current) / 1000;
%>

<html>
<head>
    <title>Hello PetStore!</title>
</head>
<body>

<% // Si la session est nouvelle
   if (sessionUser == null || sessionUser.length() < 0) {

       // Si le nom de l'utilisateur est saisi
       if (user != null && user.length() > 0) {
           out.println("La valeur " + user + " est stockée en session");
           session.setAttribute("sessionUser", user);
       }

   // Si on clique sur le bouton invalider session
   } else if (invalidate != null && invalidate.length() > 0) {
       session.invalidate();
   // La session existe, on affiche sa durée
   } else {
       out.println("La valeur " +sessionUser + " est stockée en session depuis " + totalSeconds + " secondes!<br>");
   }
%>

<form action="hello.jsp" method=POST>
   Utilisateur: <input type=text name=user><br><br>
   <input type=submit value="Stocker Session">
   <input type=submit name=invalidate value="Invalider session">
   <a href="hello.jsp">Rafraichir</a>
</form>

</body>
</html>
