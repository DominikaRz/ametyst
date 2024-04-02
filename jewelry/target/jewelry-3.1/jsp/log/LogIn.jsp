<%-- 
    Document   : newjspLogIn
    Created on : 27 maj 2021, 15:04:29
    Author     : DRzepak
--%>

<%@ page trimDirectiveWhitespaces="true" %> <%--prevent nextLine in document--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE HTML>
<html>
  <head>
      <jsp:include page="../head.jsp"/>
      <jsp:include page="../form.jsp"/>
<!--TITLE-->    
    <title>Ametyst</title>
  </head>
  <body>
  <!--HEADER-->
    <jsp:include page="../header.jsp"/>
    <!--SIDENAV -->
      <jsp:include page="../nav.jsp"/>
  <!--MAIN-->
    <main> 
      <div class="uk-margin" uk-grid>
       <jsp:include page="../aside.jsp"/>
      <!--ABOUT-->  
        <section class="uk-width-4-5@m uk-child-width-1-1@xs uk-margin-medium">
            <div class="uk-grid-medium uk-child-width-1-2@m" uk-grid>
                <form action="loging" class="uk-form-stacked uk-position-relative" id="ThisFormLog" method="POST" accept-charset="ISO-8859-1">
                    <h2>Zaloguj się</h2>
                    <div class="uk-grid-@m uk-child-width-1-1 uk-margin-medium-bottom" uk-grid>
                        <div>
                            <label class="uk-form-label" for="form-stacked-text">Email *</label>
                            <div class="uk-form-controls">    
                                <input id="login" class="uk-input" name="login" type="text" 
                                       placeholder="np. nazwa@domena.com" value="none@none.com">
                                <%--example@example.com--%> <!--user-->
                                <%--none@none.com--%> <!--admin-->
                                <%--worek@gmail.com--%> <!--pracownik-->
                                <%--cosTam@jakas.com--%> <!--korektor-->
                                <%--bleble@mail.com--%> <!--zaopatrzeniowiec-->
                            </div>
                        </div>
                        <div class=" uk-margin-small-top">
                            <label class="uk-form-label" for="form-stacked-text">Hasło *</label>
                            <div class="uk-form-controls"> 
                                <input class="uk-input" type="password" id="pass" name="passw" placeholder="****" 
                                       value="HasłoToJednakMasło" required> 
                                <%--HasłoToNieMasło--%> <!--user-->
                                <%--HasłoToJednakMasło--%> <!--admin-->
                                <%--HasłoToMasło--%> <!--pracownik-->
                                <%--nhy6%TGB--%> <!--korektor-->
                                <%--3edcvfr4--%> <!--zaopatrzeniowiec-->
                            </div>
                        </div>
                        <div class="uk-flex uk-flex-center uk-margin-small-top" >
                            <button class="uk-button uk-button-secondary" type="submit">Zaloguj</button> 
                        </div>
                </form> 
            </div>
            <div>
                <h3>Masz też inną opcję:</h3>
                <p class="uk-margin-small-bottom">Nie restrowałeś się?<br/>
                    Nie robiłeś u nas zakupów?<br/>
                    Nie widniejesz jeszcze w naszej bazie danych?<br/>
                    Jeśli nie masz jeszcze hasła do konta: </p>
                <button class="uk-button uk-button-secondary" type="button" 
                        onclick="window.location.href='register'">Zarejestruj się</button>
            </div>
        </section>
      </div>
    </main> 
   <!--FOOTER-->  
    <jsp:include page="../footer.jsp"/>
  </body>
</html>
