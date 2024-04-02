<%-- 
    Document   : about
    Created on : 14 sty 2022, 13:55:40
    Author     : DRzepka
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
  <head>
      <jsp:include page="../head.jsp"/>
<!--TITLE-->    
    <title>Ametyst</title>
  </head>
  <body>
  <!--HEADER-->
    <jsp:include page="../header.jsp"/>
    <!--NAVIGATION-->
      <jsp:include page="../nav.jsp"/>
  <!--MAIN-->
    <main>    
      <div class="uk-margin" uk-grid>
      <!--ASIDE-->
        <jsp:include page="../aside.jsp"/>
      <!--ABOUT-->  
        <section class="uk-width-4-5@m uk-margin-medium uk-text-center">
            <div class="uk-child-width-1-1@s" uk-grid>
                <h1>Aplikacja webowa stworzona na potrzeby pracy inżynierskiej</h1>
                <h3>Tytuł pracy: "Oprogramowanie dla sklepu internetowego z biżuterią"</h3>
                <h4>Autor strony: Dominika Rzepka</h4>
                <h5>Czas tworzenia: 2021 - 2022 rok</h5>
            </div>
        </section>
      </div>
    </main>
   <!--FOOTER-->  
    <jsp:include page="../footer.jsp"/>
  </body>
</html>