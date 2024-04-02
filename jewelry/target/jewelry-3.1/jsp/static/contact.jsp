<%-- 
    Document   : contact
    Created on : 14 sty 2022, 14:08:54
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
                <h1>Kontakt:</h1>
                <h3>Kontakt do autora:</h3>
                <p>s6150@horyzont.eu</p>
                <h3>Kontakt prywatny do autora:</h3>
                <p>dominikarzepka@outlook.com</p>
            </div>
        </section>
      </div>
    </main>
   <!--FOOTER-->  
    <jsp:include page="../footer.jsp"/>
  </body>
</html>