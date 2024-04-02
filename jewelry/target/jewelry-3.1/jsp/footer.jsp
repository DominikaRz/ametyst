<%-- 
    Document   : footer
    Created on : 21 maj 2021, 08:19:11
    Author     : DRzepak
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<footer>
      <div id="about" class="uk-child-width-1-3@m uk-margin uk-padding" uk-grid>
        <ul class="uk-nav-default uk-nav-parent-icon" uk-nav>
            <li class="parent-s"> <!--uk-parent-->
            <a class="h4li" href="#" style="pointer-events: none;">O FIRMIE</a>
                <ul class="sub-s"> <!--uk-nav-sub-->
                    <li><a href="about">O nas</a></li>
                    <li><a href="customer">Informacje dla Konsumenta</a></li>
                    <li><a href="contact">Kontakt</a></li>
                </ul>
            </li>
        </ul>
        <ul class="uk-nav-default uk-nav-parent-icon" uk-nav>
            <li class="parent-s">
            <a class="h4li" href="#" style="pointer-events: none;">GWARANCJA I ZWROTY</a>
                <ul class="sub-s">
                    <li><a href="privacy">Polityka prywatności</a></li>
                    <li><a href="delivery">Koszty dostawy</a></li>
                    <li><a href="payment">Sposoby płatności</a></li>
                </ul>
            </li>
        </ul>
        <ul class="uk-nav-default uk-nav-parent-icon" uk-nav>
            <li class="parent-s">
            <a class="h4li" href="#" style="pointer-events: none;">MOJE KONTO</a>
                <ul class="sub-s">
                    <li><a href="orders" uk-tooltip="Tylko dla zalogowanych">Twoje zamówienia</a></li>
                    <li><a href="settings" uk-tooltip="Tylko dla zalogowanych">Ustawienia konta</a></li>
                    <li><a href="order" uk-tooltip="Tylko dla niezalogowanych">Czytaj zamówienie</a></li>
                </ul>
            </li>
        </ul>
    </div>
    </footer>
   <!--RIGHTS-->  
    <div id="rights">
      <p>
        <img src="img/logo.svg" alt="logo" class="img-responsive reveal-inline-block pb-1" width="20"/>
        Ametyst&copy;. 2022. All right reserved.
      </p>
    </div>
    <c:if test = "${err_disc!=null}"> 
      <script>
          UIkit.notification({message: "<p class='uk-text-center uk-margin-remove-bottom'> \n\
              <span uk-icon=\'icon: warning\'></span> <c:out value="${err_disc}"/></p>", 
              status: "danger"});
          <% session.removeAttribute("err_disc"); %>
      </script>
      </c:if>
    <c:if test = "${succ_disc!=null}"> 
      <script>
          UIkit.notification({message: "<p class='uk-text-center uk-margin-remove-bottom'> \n\
              <span uk-icon=\'icon: info\'></span> <c:out value="${succ_disc}"/></p>", 
              status: "success"});
          <% session.removeAttribute("succ_disc"); %>
      </script>
    </c:if>