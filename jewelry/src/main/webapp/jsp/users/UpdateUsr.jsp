<%-- 
    Document   : UpdateUsr
    Created on : 14 wrz 2021, 15:15:35
    Author     : DRzepka
--%>

<%@ page trimDirectiveWhitespaces="true" %> <%--prevent nextLine in document--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page errorPage="error" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
  <head>
      <jsp:include page="../head.jsp"/>
      <jsp:include page="../form.jsp"/>
      <link rel="stylesheet" type="text/css" href="css/styleUsr.css"/>
<!--TITLE-->    
    <title>Ametyst - user</title>
  </head>
  <body>
  <!--HEADER-->
    <jsp:include page="../header.jsp"/>
    <!--NAV-->
    <c:choose>
        <c:when test = "${(rank==0)||(rank==null)}">
            <jsp:include page="../navL.jsp"/>
        </c:when>
        <c:otherwise>
            <jsp:include page="../navRnk.jsp"/>
        </c:otherwise>
    </c:choose>
  <!--MAIN-->
    <main>
      <div class="uk-margin" uk-grid>
      <!--ASIDE-->
        <jsp:include page="../aside.jsp"/>
        <section class="uk-width-4-5@m uk-margin-medium">
            <h2>Zmień co chcesz:</h2>
            <hr>
            <ul uk-accordion>
                <li id="AccPass"> <!-- class="uk-open"-->
                    <a class="uk-accordion-title" href="#">Hasło:</a>
                    <div class="uk-accordion-content">
                        <form class="uk-form-stacked" id="ThisFormPass" method="POST" action="updatePassw" accept-charset="ISO-8859-1">
                            <div class="uk-child-width-1-1" uk-grid>
                                <div class="uk-child-width-1-2 uk-margin-small-top" uk-grid>
                                    <div class="uk-form-controls">    
                                        <label class="uk-form-label" for="form-stacked-text" for="password">Poprzednie hasło *</label>
                                        <div class="uk-inline">
                                            <input id="password_previous" name="password_previous" class="uk-input" type="password" placeholder="***" maxlength="20">
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="uk-child-width-1-1" uk-grid>
                                <div class="uk-child-width-1-2@m uk-margin-small-top" uk-grid>
                                    <div class="uk-form-controls">    
                                        <label class="uk-form-label" for="form-stacked-text" for="password">Nowe hasło *</label>
                                        <div class="uk-inline">
                                            <a class="uk-form-icon" uk-icon="icon: question" onclick="see1()" uk-tooltip="Pokaż hasło"></a>
                                            <input id="password" name="password" class="uk-input" type="password" placeholder="***" maxlength="20">
                                        </div>
                                    </div>
                                    <div class="uk-form-controls">    
                                        <label class="uk-form-label" for="form-stacked-text" for="password_again">Potwierdź hasło *</label>
                                        <div class="uk-inline">
                                            <a class="uk-form-icon" uk-icon="icon: question" onclick="see2()" uk-tooltip="Pokaż hasło"></a>
                                            <input id="password_again" name="password_again" class="uk-input" type="password" placeholder="***" maxlength="20">
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="uk-flex uk-flex-center uk-margin-medium-top" >
                                <button class="uk-button uk-button-default" type="reset">Anuluj</button>
                                <button class="uk-button uk-button-secondary" type="submit">Zmień</button> 
                            </div>
                        </form>
                    </div>
                </li>
                <li>
                    <a class="uk-accordion-title" href="#">Dane osobowe:</a>
                    <div class="uk-accordion-content">
                        <form class="uk-form-stacked" id="ThisFormData" method="POST" action="updateInform" accept-charset="ISO-8859-1">
                            <div class="uk-grid-small" uk-grid>
                                <div class="uk-width-1-3@m uk-width-1-2">
                                    <label class="uk-form-label" for="form-stacked-text">Imię *</label>
                                    <div class="uk-form-controls">
                                        <input id="name"  name="name" class="uk-input" type="text" placeholder="np. Jan" 
                                               value="<c:out value="${information.name}"/>">
                                    </div>
                                </div>
                                <div class="uk-width-1-3@m uk-width-1-2">
                                    <label class="uk-form-label" for="form-stacked-text">Nazwisko *</label>
                                    <div class="uk-form-controls">
                                        <input id="surname" name="surname" class="uk-input" type="text" placeholder="np. Kowalski"
                                               value="<c:out value="${information.surname}"/>">
                                    </div>
                                </div>
                            </div>
                            <div class="uk-grid-small" uk-grid>
                                <div class="uk-width-1-5@m">
                                    <label class="uk-form-label" for="form-stacked-text">Telefon *</label>
                                    <div class="uk-form-controls">    
                                        <input id="tel" name="tel" class="uk-input uk-form-width-medium" type="text" placeholder="np. 123-456-789"
                                               value="<c:out value="${information.tel}"/>">
                                    </div> 
                                </div>
                                <div class="uk-form-controls uk-width-2-5@m uk-width-1-1"> 
                                    <label class="uk-form-label" for="form-stacked-text" for="mail">Email </label>   
                                    <input id="mail" name="mail" class="uk-input" type="email" placeholder="np. nazwa@domena.com" maxlength="100"
                                           value="<c:out value="${information.email}"/>" disabled>
                                </div>
                            </div>
                            <div class="uk-margin-medium-top">
                                <div>
                                    <label for="terms">Newsletter </label>
                                    <input id="newsletter" name="newsletter" class="uk-checkbox" type="checkbox" 
                                           <c:if test="${information.newsletter.equals(true)}">checked</c:if>/>
                                </div>
                            </div>
                            <div class="uk-flex uk-flex-center uk-margin-medium-top" >
                                <button class="uk-button uk-button-default" type="reset">Anuluj</button>
                                <button class="uk-button uk-button-secondary" type="submit">Zmień</button> 
                            </div>
                        </form> 
                    </div>
                </li>
                <c:forEach var="adr" items="${addresses}" varStatus="stat">
                <li>
                    <a class="uk-accordion-title" href="#">Adres <c:out value="${stat.index+1}"/>:</a>
                    <div class="uk-accordion-content">
                        <form class="uk-form-stacked ThisFormExist" method="POST" action="updateAddrr" accept-charset="ISO-8859-1"> <%--?id=<c:out value="${adr.idMeta}"/>--%>
                            <h3>Adres *</h3>
                            <div class="uk-grid-small" uk-grid>
                                <div class="uk-width-4-5">
                                    <label class="uk-form-label" for="form-stacked-text">Ulica *</label>
                                    <input id="street" name="street" class="uk-input" type="text" placeholder="ul. Stefana Wyszyńskiego" 
                                           value="<c:out value="${adr.adr_str}"/>">
                                </div>
                                <div class="uk-width-1-5">
                                    <label class="uk-form-label" for="form-stacked-text">
                                        <span class="uk-hidden@m">Nr </span><span class="uk-hidden@xs">Numer budynku </span>*</label>
                                    <input id="nbr"  name="nbr" class="uk-input" type="text" placeholder="5/3"
                                           value="<c:out value="${adr.adr_nr}"/>">
                                </div>
                            </div>
                            <div class="uk-grid-small" uk-grid>
                                <div class="uk-width-1-5@m uk-width-2-5">
                                    <label class="uk-form-label" for="form-stacked-text">Kod pocztowy *</label>
                                    <input id="code" name="code" class="uk-input cd" type="text" placeholder="np. 12-345"
                                           value="<c:out value="${adr.adr_code}"/>">
                                </div>
                                <div class="uk-width-2-5@m uk-width-3-5">
                                    <label class="uk-form-label" for="form-stacked-text">Poczta *</label>
                                    <input id="post" name="post" class="uk-input" type="text" placeholder="poczta: np. Oleśnica"
                                           value="<c:out value="${adr.adr_post}"/>">
                                </div>
                                <div class="uk-width-2-5@m">
                                    <label class="uk-form-label" for="form-stacked-text">Miasto *</label>
                                    <input id="town" name="town" class="uk-input" type="text" placeholder="miasto: np. Jelenia Góra" 
                                           value="<c:out value="${adr.adr_town}"/>">
                                </div>
                            </div>
                            <div class="uk-grid-small" uk-grid>
                                <div class="uk-width-3-5">
                                    <label class="uk-form-label" for="form-stacked-text">Województwo *</label>
                                    <input id="state" name="state" class="uk-input" type="text" placeholder="np. Dolnośląskie"
                                           value="<c:out value="${adr.adr_state}"/>">
                                </div>
                                <div class="uk-width-2-5">
                                    <div class="uk-form-controls">
                                        <label class="uk-form-label" for="form-stacked-text">Kraj *</label>
                                        <select id="country" name="country" class="uk-select" type="text" placeholder="Kraj" 
                                                value="<c:out value="${adr.adr_count}"/>">
                                            <jsp:include page="../country.jsp"/>                          
                                        </select>
                                    </div>
                                </div>
                            </div>
                            <c:if test = "${adr.firm!=false}">
                            <h3>Firma</h3>
                            <div class="uk-margin">
                                <div class="uk-grid-small" uk-grid>
                                    <div class="uk-width-1-2@m">
                                        <label class="uk-form-label" for="form-stacked-text">Nazwa firmy *</label>
                                        <input id="name_firm" name="name_firm" class="uk-input" type="text" placeholder="np. Dobre ziarno"
                                           value="<c:out value="${adr.firm_name}"/>">
                                    </div>
                                    <div class="uk-width-1-2">
                                        <label class="uk-form-label" for="form-stacked-text">Nip *</label>
                                        <input id="nip" name="nip" class="uk-input" type="text" placeholder="np. 1236547890"
                                           value="<c:out value="${adr.firm_nip}"/>">
                                    </div>
                                    <div class="uk-width-1-5@m uk-width-1-2">
                                        <label class="uk-form-label" for="form-stacked-text">Telefon firmy *</label>
                                        <div class="uk-form-controls">    
                                            <input id="tel_firm" name="tel_firm" class="uk-input uk-form-width-medium tf" type="text" 
                                                   maxlength="11" placeholder="np. 123-456-789" value="<c:out value="${adr.firm_tel}"/>">
                                        </div> 
                                    </div>
                                    <div class="uk-width-2-5@m">
                                        <label class="uk-form-label" for="form-stacked-text" for="mail">Email firmy*</label>   
                                        <div class="uk-form-controls"> 
                                            <input id="mail_firm" name="mail_firm" class="uk-input" type="email" placeholder="np. nazwa@domena.com" 
                                                   maxlength="100" value="<c:out value="${adr.firm_email}"/>">
                                        </div>
                                    </div>
                                </div>
                            </div>
                            </c:if>
                            <div class="uk-flex uk-flex-center uk-margin-medium-top" >
                                <button class="uk-button uk-button-secondary" name="idMeta" value="${adr.idMeta}" type="submit">Aktualizuj</button> 
                            </div>
                        </form> 
                    </div>
                </li>
                </c:forEach>
                <li class="uk-open">
                    <a class="uk-accordion-title" href="#">Dodaj nowy adres:</a>
                    <div class="uk-accordion-content">
                        <form class="uk-form-stacked" id="ThisFormNew" method="POST" action="addAddrr" accept-charset="ISO-8859-1">
                            <h2>Dane osobowe 
                                <label class="uk-form-label uk-margin-small-top uk-text-bold" for="firmCh">
                                    <input id="firmCh" name="firmCh" class="uk-checkbox" type="checkbox"/> Firma <!-- checked-->
                            </label></h2>
                            <div class="uk-margin"></div>
                            <label class="uk-form-label" for="form-stacked-text">Adres *</label>
                            <div class="uk-grid-small" uk-grid>
                                <div class="uk-width-4-5">
                                    <input id="street" name="street" class="uk-input" type="text" placeholder="ul. Stefana Wyszyńskiego" minlength="5" maxlength="60">
                                </div>
                                <div class="uk-width-1-5">
                                    <input id="nbr"  name="nbr" class="uk-input" type="text" placeholder="5/3" maxlength="10">
                                </div>
                            </div>
                            <div class="uk-grid-small" uk-grid>
                                <div class="uk-width-1-5@m uk-width-2-5">
                                    <input id="code" name="code" class="uk-input cd" type="text" placeholder="np. 12-345" maxlength="6">
                                </div>
                                <div class="uk-width-2-5@m uk-width-3-5">
                                    <input id="post" name="post" class="uk-input" type="text" placeholder="poczta: np. Oleśnica" maxlength="40">
                                </div>
                                <div class="uk-width-2-5@m">
                                    <input id="town" name="town" class="uk-input" type="text" placeholder="miasto: np. Jelenia Góra" maxlength="40">
                                </div>
                            </div>
                            <div class="uk-grid-small" uk-grid>
                                <div class="uk-width-3-5">
                                    <input id="state" name="state" class="uk-input" type="text" maxlength="40" placeholder="np. Dolnośląskie">
                                </div>
                                <div class="uk-width-2-5">
                                    <div class="uk-form-controls">
                                        <select name="country" class="uk-select" type="text" placeholder="Kraj" value="Polska">
                                            <jsp:include page="../country.jsp"/>                          
                                        </select>
                                    </div>
                                </div>
                            </div>
                            <div id="fHid" class="uk-margin uk-hidden">
                                <div class="uk-grid-small" uk-grid>
                                    <div class="uk-width-1-2@m">
                                        <label class="uk-form-label" for="form-stacked-text">Nazwa firmy *</label>
                                        <input id="name_firm" name="name_firm" class="uk-input" type="text" placeholder="np. Dobre ziarno">
                                    </div>
                                    <div class="uk-width-1-2">
                                        <label class="uk-form-label" for="form-stacked-text">Nip *</label>
                                        <input id="nip" name="nip" class="uk-input" type="text" placeholder="np. 1236547890" maxlength="10">
                                    </div>
                                    <div class="uk-width-1-5@m uk-width-1-2">
                                        <label class="uk-form-label" for="form-stacked-text">Telefon firmy *</label>
                                        <div class="uk-form-controls">    
                                            <input id="tel_firm" name="tel_firm" class="uk-input uk-form-width-medium tf" type="text" maxlength="11" placeholder="np. 123-456-789">
                                        </div> 
                                    </div>
                                    <div class="uk-width-2-5@m">
                                        <label class="uk-form-label" for="form-stacked-text" for="mail">Email firmy*</label>   
                                        <div class="uk-form-controls"> 
                                            <input id="mail_firm" name="mail_firm" class="uk-input" type="email" placeholder="np. nazwa@domena.com" maxlength="100">
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="uk-flex uk-flex-center uk-margin-medium-top" >
                                <button class="uk-button uk-button-default" type="reset">Anuluj</button>
                                <button class="uk-button uk-button-secondary" type="submit">Dodaj</button> 
                            </div>
                        </form> 
                    </div>
                </li>
            </ul>          
        </section>


      </div>
    </main> 
   <!--FOOTER-->  
    <jsp:include page="../footer.jsp"/>
  </body>
</html>

