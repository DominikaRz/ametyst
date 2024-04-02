<%-- 
    Document   : 404
    Created on : 14 sty 2022, 13:27:44
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
                <h1>Strona w budowie</h1>
                <p><img src="img/roboty.jpg" alt="roboty" style="width: 40vw;"/></p>
            </div>
        </section>
      </div>
    </main>
   <!--FOOTER-->  
    <jsp:include page="jsp/footer.jsp"/>
  </body>
</html>
