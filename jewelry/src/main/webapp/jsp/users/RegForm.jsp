<%-- 
    Document   : RegForm
    Created on : 9 wrz 2021, 12:21:23
    Author     : DRzepka
--%>

<%@ page trimDirectiveWhitespaces="true" %> <%--prevent nextLine in document--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
  <head>
      <jsp:include page="../head.jsp"/>
      <jsp:include page="../form.jsp"/>
<!--TITLE-->    
    <title>Rejestracja</title>
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
        <section class="uk-width-4-5@m uk-margin-medium">
            <form class="uk-form-stacked" id="ThisFormReg" method="POST" action="addRegister" accept-charset="ISO-8859-1">
                <h2>Rejestracja</h2>
                <div class="uk-child-width-1-1" uk-grid>
                    <div class="uk-form-controls uk-width-1-1"> 
                        <label class="uk-form-label" for="form-stacked-text" for="mail">Email *</label>   
                        <input id="mail" name="mail" class="uk-input" type="email" placeholder="np. nazwa@domena.com" maxlength="100">
                    </div>
                    <div class="uk-child-width-1-2@m uk-child-width-1-1 uk-margin-small-top" uk-grid>
                        <div class="uk-form-controls">    
                            <label class="uk-form-label" for="form-stacked-text" for="password">Hasło *</label>
                            <div class="uk-inline">
                                <a class="uk-form-icon" uk-icon="icon: question" onclick="see1()" uk-tooltip="Pokaż hasło"></a>
                                <input id="password" name="password" class="uk-input" type="password" placeholder="***" maxlength="20">
                            </div>
                        </div>
                        <div class="uk-form-controls">    
                            <label class="uk-form-label" for="form-stacked-text" for="password_again">Powtórz hasło *</label>
                            <div class="uk-inline">
                                <a class="uk-form-icon" uk-icon="icon: question" onclick="see2()" uk-tooltip="Pokaż hasło"></a>
                                <input id="password_again" name="password_again" class="uk-input" type="password" placeholder="***" maxlength="20">
                            </div>
                        </div>
                    </div>
                </div>
                <h3 class="uk-text-center">Dane osobowe</h3>
                <div class="uk-grid-small" uk-grid>
                    <div class="uk-width-1-3@m uk-width-1-2">
                        <label class="uk-form-label" for="form-stacked-text">Imię *</label>
                        <div class="uk-form-controls">
                            <input id="name"  name="name" class="uk-input" type="text" placeholder="np. Jan">
                        </div>
                    </div>
                    <div class="uk-width-1-3@m uk-width-1-2">
                        <label class="uk-form-label" for="form-stacked-text">Nazwisko *</label>
                        <div class="uk-form-controls">
                            <input id="surname" name="surname" class="uk-input" type="text" placeholder="np. Kowalski">
                        </div>
                    </div>
                </div>
                <div class="uk-grid-small" uk-grid>
                    <div class="uk-width-1-2">
                        <label class="uk-form-label" for="form-stacked-text">Telefon *</label>
                        <div class="uk-form-controls">    
                            <input id="tel" name="tel" class="uk-input uk-form-width-medium" type="text" maxlength="11" placeholder="np. 123-456-789">
                        </div> 
                    </div>
                </div>
                <h3 class="uk-text-center">Adres 
                    <label class="uk-form-label uk-margin-small-top uk-text-bold" for="firmCh">
                        <input id="firmCh" name="firmCh" class="uk-checkbox" type="checkbox"/> Firma <!-- checked-->
                </label></h3>
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
                    <div class="uk-width-2-5@m uk-width-2-5">
                        <input id="code" name="code" class="uk-input" type="text" placeholder="np. 12-345" maxlength="6">
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
                            <select id="country" name="country" class="uk-select" type="text" placeholder="Kraj" value="Polska" maxlength="40">
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
                                <input id="tel_firm" name="tel_firm" class="uk-input uk-form-width-medium" type="text" maxlength="11" placeholder="np. 123-456-789">
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
                <div class="uk-flex uk-flex-right uk-margin-medium-top">
                    <div>
                        <label for="terms">Akceptuje warunki zapisane w regulaminie* </label>
                        <input id="regul" name="regul" class="uk-checkbox" type="checkbox" checked> 
                    </div>
                </div>
                <div class="uk-flex uk-flex-center uk-margin-medium-top" >
                    <button class="uk-button uk-button-default" type="reset">Anuluj</button>
                    <button class="uk-button uk-button-secondary" type="submit">Rejestruj</button> 
                </div>
            </form>
        </section>
      </div>
    </main> 
   <!--FOOTER-->  
    <jsp:include page="../footer.jsp"/>
  </body>
</html>