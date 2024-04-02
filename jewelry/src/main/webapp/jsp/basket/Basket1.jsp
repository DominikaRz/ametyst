<%-- 
    Document   : Basket1
    Created on : 9 wrz 2021, 12:01:51
    Author     : DRzepka
--%>

<%@ page trimDirectiveWhitespaces="true" %> <%--prevent nextLine in document--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE HTML>
<html>
  <head>
      <jsp:include page="../head.jsp"/>
      <link rel="stylesheet" type="text/css" href="css/styleBask.css"/>
<!--TITLE-->    
    <title>Koszyk - krok 2</title>
  </head>
  <body>
  <!--HEADER-->
    <jsp:include page="../header.jsp"/>
    <!--SIDENAV -->
        <jsp:include page="../nav.jsp"/>
  <!--MAIN-->
    <main>  
      <div class="uk-margin" uk-grid>
        <section class="uk-width-1-1@m uk-margin-medium">
            <ul class="uk-nav uk-column-1-4@m uk-column-divider uk-visible@m uk-text-center uk-margin-large-bottom" 
                style="width: 80%; margin-left: 10%;">
                <li class="unsee">
                    <h3 class="uk-margin-remove-bottom">Krok 1/4</h3>
                    <p>Akceptacja koszyka</p>
                </li>
                <li class="uk-active">
                    <h3 class="uk-margin-remove-bottom">Krok 2/4</h3>
                    <p>Możliwość zalogowania</p>
                </li>
                <li class="unsee">
                    <h3 class="uk-margin-remove-bottom">Krok 3/4</h3>
                    <p>Dane osobowe</p>
                </li>
                <li class="unsee">
                    <h3 class="uk-margin-remove-bottom">Krok 4/4</h3>
                    <p>Podsumowanie</p>
                </li>
            </ul>
            <script>
                $(".unsee > h3").addClass("uk-text-muted");
                $(".unsee > p").addClass("uk-text-muted");
            </script>
            <div class="uk-grid-medium@m uk-child-width-1-2@m" uk-grid>
                <form id="ThisFormLog" method="POST" action="loging" accept-charset="ISO-8859-1" 
                      class="uk-form-stacked uk-position-relative uk-margin-medium-bottom">
                    <h2>Zaloguj się</h2>
                    <div class="uk-grid-medium@m uk-child-width-1-1" uk-grid>
                        <div>
                            <label class="uk-form-label" for="form-stacked-text">Email *</label>
                            <div class="uk-form-controls">    
                                <input id="login" class="uk-input" name="login" type="text" 
                                       placeholder="np. nazwa@domena.com" value="example@example.com" maxlength="100">
                            </div>
                        </div>
                        <div class=" uk-margin-small-top">
                            <label class="uk-form-label" for="form-stacked-text">Hasło *</label>
                            <div class="uk-form-controls"> 
                                <input class="uk-input" type="password" id="pass" name="passw" placeholder="****" 
                                       value="HasłoToNieMasło" required> 
                            </div>
                        </div>
                        <div class="uk-flex uk-flex-center k-margin-small-top" >
                            <button class="uk-button uk-button-default" type="reset" onclick="window.location.href='#'">Anuluj</button>
                            <button class="uk-button uk-button-secondary" type="submit">Zaloguj</button> 
                        </div>
                    </div>
                </form> 
                <div class="uk-text-center">
                    <h3>Masz też inne opcje:</h3>
                    <p class="uk-margin-small-bottom">Nie restrowałeś się?<br/>
                        Nie robiłeś u nas zakupów?</p>
                    <button class="uk-button uk-button-secondary" type="button" 
                            onclick="window.location.href='register'">Zarejestruj się</button>
                    
                    <h4>LUB</h4>
                    <p class="uk-margin-small-bottom uk-text-secondary">Zrób zakupy jednorazowe:</p>
                    <button class="uk-button uk-button-secondary" type="button" 
                            onclick="window.location.href='NLbasket'">Bez konta</button>
                </div>
            </div>
        </section>
      </div>
    </main> 
   <!--FOOTER-->  
    <jsp:include page="../footer.jsp"/>
  </body>
</html>


