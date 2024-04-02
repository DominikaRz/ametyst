<%-- 
    Document   : Basket2
    Created on : 9 wrz 2021, 13:03:30
    Author     : DRzepka
--%>

<%@page import="jwl.model.UserMeta"%>
<%@page import="jwl.DAO.UserMDAO"%>
<%@ page trimDirectiveWhitespaces="true" %> <%--prevent nextLine in document--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<% 
    UserMDAO usermDAO = new UserMDAO(application.getInitParameter("jdbcURL"),
                                    application.getInitParameter("jdbcUsernNL"),   
                                    application.getInitParameter("jdbcUsernNLPassw"));
    
    try {
        int id = (int) session.getAttribute("userMeta_id");
        UserMeta usrmF= usermDAO.checkFirm(id);
        UserMeta usrmL= usermDAO.checkLogged(id);
        UserMeta listUser = null;
        if(usrmL.isLogged()){
            if(usrmF.isFirm()){ listUser = usermDAO.readFirmL(id);}
            else{ listUser = usermDAO.readAdrL(id); }
        }
        else{
            if(usrmF.isFirm()){ listUser = usermDAO.readFirmN(id); }
            else{ listUser = usermDAO.readAdrN(id); }
        }  
          request.setAttribute("userm", listUser);
    } catch (NullPointerException e) {}
%>

<!DOCTYPE HTML>
<html>
  <head>
    <jsp:include page="../head.jsp"/>
    <link rel="stylesheet" type="text/css" href="css/styleBask.css"/>       <!--style dla koszyka-->
    <jsp:include page="../form.jsp"/>
<!--TITLE-->    
    <title>Koszyk - krok 3</title>
  </head>
  <body>
  <!--HEADER-->
    <jsp:include page="../header.jsp"/>
    <!--SIDENAV -->
        <jsp:include page="../nav.jsp"/>
  <!--MAIN-->
    <main>  
      <div class="uk-margin" uk-grid>
        <section class="uk-width-1-1@m uk-margin-medium" style="width: 80%; margin-left: 10%;">
        <ul class="uk-nav uk-column-1-4@m uk-column-divider uk-visible@m uk-text-center uk-margin-large-bottom">
            <li class="unsee">
                <h3 class="uk-margin-remove-bottom">Krok 1/4</h3>
                <p>Akceptacja koszyka</p>
            </li>
            <li class="unsee">
                <h3 class="uk-margin-remove-bottom">Krok 2/4</h3>
                <p>Możliwość zalogowania</p>
            </li>
            <li class="uk-active">
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
            <form class="uk-form-stacked" id="ThisFormNL" method="POST" action="addUMBask" accept-charset="ISO-8859-1">
                <h2>Dane osobowe 
                    <label class="uk-form-label uk-margin-small-top uk-text-bold" for="firmCh">
                        <input id="firmCh" name="firmCh" class="uk-checkbox" type="checkbox" 
                               <c:if test="${userm.firm.equals(true)}">checked</c:if>/> Firma
                </label></h2>
                <div class="uk-grid-small" uk-grid>
                    <div class="uk-width-1-3@m uk-width-1-2">
                        <label class="uk-form-label" for="form-stacked-text">Imię *</label>
                        <div class="uk-form-controls">
                            <input id="name"  name="name" class="uk-input" type="text" placeholder="np. Jan"
                                    value="<c:out value="${name}"/>"/>
                        </div>
                    </div>
                    <div class="uk-width-1-3@m uk-width-1-2">
                        <label class="uk-form-label" for="form-stacked-text">Nazwisko *</label>
                        <div class="uk-form-controls">
                            <input id="surname" name="surname" class="uk-input" type="text" placeholder="np. Kowalski"
                                    value="<c:out value="${surname}"/>"/>
                        </div>
                    </div>
                </div>
                <div class="uk-grid-small" uk-grid>
                    <div class="uk-width-1-5@m">
                        <label class="uk-form-label" for="form-stacked-text">Telefon *</label>
                        <div class="uk-form-controls">    
                            <input id="tel" name="tel" class="uk-input uk-form-width-medium" type="text" maxlength="11" 
                                   placeholder="np. 123-456-789" value="<c:out value="${tel}"/>"/>
                        </div> 
                    </div>
                    <div class="uk-width-2-5@m">
                        <label class="uk-form-label" for="form-stacked-text" for="mail">Email *</label>   
                        <div class="uk-form-controls"> 
                            <input id="mail" name="mail" class="uk-input" type="email" placeholder="np. nazwa@domena.com" 
                                   maxlength="100" value="<c:out value="${mail}"/>"/>
                        </div>
                    </div>
                </div>
                <div class="uk-margin"></div>
                <label class="uk-form-label" for="form-stacked-text">Adres *</label>
                <div class="uk-grid-small" uk-grid>
                    <div class="uk-width-4-5">
                        <input id="street" name="street" class="uk-input" type="text" placeholder="ul. Stefana Wyszyńskiego"
                               maxlength="60" value="<c:out value="${userm.adr_str}"/>"/>
                    </div>
                    <div class="uk-width-1-5">
                        <input id="nbr"  name="nbr" class="uk-input" type="text" placeholder="5/3"
                               value="<c:out value="${userm.adr_nr}"/>"/>
                    </div>
                </div>
                <div class="uk-grid-small" uk-grid>
                    <div class="uk-width-1-5@m uk-width-2-5">
                        <input id="code" name="code" class="uk-input" type="text" placeholder="np. 12-345"
                               value="<c:out value="${userm.adr_code}"/>"/>
                    </div>
                    <div class="uk-width-2-5@m uk-width-3-5">
                        <input id="post" name="post" class="uk-input" type="text" placeholder="poczta: np. Oleśnica"
                               value="<c:out value="${userm.adr_post}"/>"/>
                    </div>
                    <div class="uk-width-2-5@m">
                        <input id="town" name="town" class="uk-input" type="text" placeholder="miasto: np. Jelenia Góra"
                               value="<c:out value="${userm.adr_town}"/>"/>
                    </div>
                </div>
                <div class="uk-grid-small" uk-grid>
                    <div class="uk-width-3-5">
                        <input id="state" name="state" class="uk-input" type="text" maxlength="40" placeholder="np. Dolnośląskie"
                                value="<c:out value="${userm.adr_state}"/>"/>
                    </div>
                    <div class="uk-width-2-5">
                        <div class="uk-form-controls">
                            <select id="country" name="country" class="uk-select" type="text" placeholder="Kraj" value="<c:out value="${userm.adr_count}"/>" maxlength="40">
                                <jsp:include page="../country.jsp"/>                       
                            </select>
                        </div>
                    </div>
                </div>
                <div id="fHid" class="uk-margin uk-hidden">
                    <div class="uk-grid-small" uk-grid>
                        <div class="uk-width-1-2@m">
                            <label class="uk-form-label" for="form-stacked-text">Nazwa firmy *</label>
                            <input id="name_firm" name="name_firm" class="uk-input" type="text" placeholder="np. Dobre ziarno" 
                                   value="<c:out value="${userm.firm_name}"/>">
                        </div>
                        <div class="uk-width-1-2">
                            <label class="uk-form-label" for="form-stacked-text">Nip *</label>
                            <input id="nip" name="nip" class="uk-input" type="text" placeholder="np. 1236547890"
                                    value="<c:out value="${userm.firm_nip}"/>"/>
                        </div>
                        <div class="uk-width-1-5@m uk-width-1-2">
                            <label class="uk-form-label" for="form-stacked-text">Telefon firmy *</label>
                            <div class="uk-form-controls">    
                                <input id="tel_firm" name="tel_firm" class="uk-input uk-form-width-medium" type="text"
                                       placeholder="np. 123-456-789" value="<c:out value="${userm.firm_tel}"/>"/>
                            </div> 
                        </div>
                        <div class="uk-width-2-5@m">
                            <label class="uk-form-label" for="form-stacked-text" for="mail">Email firmy*</label>   
                            <div class="uk-form-controls"> 
                                <input id="mail_firm" name="mail_firm" class="uk-input" type="email" placeholder="np. nazwa@domena.com" 
                                       maxlength="100" value="<c:out value="${userm.firm_email}"/>"/>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="uk-flex uk-flex-center uk-margin-medium-top" >
                    <button class="uk-button uk-button-default" type="reset">Anuluj</button>
                    <button class="uk-button uk-button-secondary" type="submit">Przejdź dalej...</button> 
                </div>
            </form>
        </section>
      </div>
    </main> 
   <!--FOOTER-->  
    <jsp:include page="../footer.jsp"/>
  </body>
</html>
