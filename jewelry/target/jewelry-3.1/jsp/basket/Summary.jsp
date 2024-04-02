<%-- 
    Document   : Summary
    Created on : 9 wrz 2021, 15:07:23
    Author     : DRzepka
--%>

<%@ page trimDirectiveWhitespaces="true" %> <%--prevent nextLine in document--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>  
<!DOCTYPE HTML>
<html>
  <head>
    <jsp:include page="../head.jsp"/>
    <link rel="stylesheet" type="text/css" href="css/styleBask.css"/>       <%--style dla koszyka--%>
    <jsp:include page="../form.jsp"/>
<!--TITLE-->    
    <title>Koszyk - krok 4</title>
  </head>
  <body>
  <!--HEADER-->
    <jsp:include page="../header.jsp"/>
    <!--SIDENAV -->
        <jsp:include page="../nav.jsp"/>
  <!--MAIN-->
    <main>     
      <div class="uk-margin" uk-grid>
        <section class="uk-width-1-1@m uk-margin-medium@m" style="width: 80%; margin-left: 10%;">
            <ul class="uk-nav uk-column-1-4@m uk-column-divider uk-visible@m uk-text-center uk-margin-large-bottom">
                <li class="unsee">
                    <h3 class="uk-margin-remove-bottom">Krok 1/4</h3>
                    <p>Akceptacja koszyka</p>
                </li>
                <li class="unsee">
                    <h3 class="uk-margin-remove-bottom">Krok 2/4</h3>
                    <p>Możliwość zalogowania</p>
                </li>
                <li class="unsee">
                    <h3 class="uk-margin-remove-bottom">Krok 3/4</h3>
                    <p>Dane osobowe</p>
                </li>
                <li class="uk-active">
                    <h3 class="uk-margin-remove-bottom">Krok 4/4</h3>
                    <p>Podsumowanie</p>
                </li>
            </ul>
            <script>
                $(".unsee > h3").addClass("uk-text-muted");
                $(".unsee > p").addClass("uk-text-muted");
            </script>
            <div class="uk-child-width-1-3@m uk-margin-small-bottom" uk-grid>
                <p> <span class="uk-text-bold uk-h4">Płatność:</span><br/><c:out value="${payment.name}"/></p>
                <p> <span class="uk-text-bold uk-h4">Wysyłka:</span><br/><c:out value="${delivery.name}"/></p>
                <p class="uk-flex-first@xs" style="padding: 0;"> 
                    <button class="uk-button uk-button-secondary" onclick="window.open('basket', '_blank')" >
                    Powrót do koszyka</button></p>
            </div>
            <table class="uk-table uk-table-responsive uk-table-hover uk-table-divider">
                <thead>
                    <tr>
                        <th class="uk-width-small uk-hidden@xs">Lp.</th>
                        <th class="uk-width-large">Produkt</th>
                        <th class="uk-width-small">Ilość</th>
                        <th class="uk-width-small">Cena</th>
                        <th class="uk-width-small">Wartość</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="prod" items="${listProd}" varStatus="stat">
                    <tr>
                        <td class="uk-hidden@xs"><c:out value="${stat.index+1}"/></td>
                        <td>
                            <img src="http://localhost:8080/jewelry/img/CT/<c:out value="${prod.id}"/>/1.jpg" alt="img" width="50"
                                 class="uk-align-left uk-margin-remove-adjacent"/>
                             <c:out value="${prod.name}"/>
                        <td><c:out value="${listBask[stat.index].quantity}"/><span class="uk-hidden@m">&nbsp;szt.</span></td>
                        <td>
                            <p>
                            <c:choose>
                                <c:when test = "${prod.discount!=0}">
                                    <s style="color: #860e0e;"><fmt:formatNumber type="number" maxFractionDigits="2" minFractionDigits="2" 
                                          value="${prod.price}"/>&nbsp;zł</s>
                                    <c:set var="priceOfProduct" 
                                           value="${Math.round((prod.price-(prod.discount/100)*prod.price)*100.0)/100.0}"/>
                                    <fmt:formatNumber type="number" maxFractionDigits="2" minFractionDigits="2" 
                                          value="${priceOfProduct}"/>&nbsp;zł
                                    <span class="uk-hidden@m">/szt.</span>
                                </c:when>
                                <c:otherwise>
                                    <c:set var="priceOfProduct" value="${prod.price}"/>
                                    <fmt:formatNumber type="number" maxFractionDigits="2" minFractionDigits="2" 
                                          value="${priceOfProduct}"/>&nbsp;zł
                                    <span class="uk-hidden@m">/szt.</span>
                                </c:otherwise>
                            </c:choose>
                            </p>
                        </td>
                        <td class="uk-text-bold">
                            <c:set var="price" value="${Math.round((priceOfProduct*listBask[stat.index].quantity)*100.00)/100.00}"/>
                            <c:set var="sum" value="${Math.round((sum + price)*100.00)/100.00}"/>
                            <span class="uk-hidden@m">Wartość: </span><fmt:formatNumber type="number" 
                                maxFractionDigits="2" minFractionDigits="2" value="${price}"/>&nbsp;zł
                        </td>
                    </tr>
                    </c:forEach>
                </tbody>
            </table>
            <div class="uk-child-width-1-2@m" uk-grid>
                <div class="uk-child-width-1-2@m" uk-grid>
                    <div style="padding: 0;">
                        <h4 class="uk-text-bold">Adres:</h4>
                        <p class="uk-text-bold"><c:out value="${name}"/>&nbsp;<c:out value="${surname}"/></p>
                        <p>ul. <c:out value="${userm.adr_str}"/>&nbsp;<c:out value="${userm.adr_nr}"/></p>
                        <p><c:out value="${userm.adr_code}"/>&nbsp;<c:out value="${userm.adr_town}"/></p>
                        <p><c:out value="${userm.adr_state}"/>, <c:out value="${userm.adr_count}"/></p>
                    </div>
                    <c:if test = "${userm.firm==true}"> 
                    <div style="padding: 0;">
                        <h4 class="uk-text-bold">Firma:</h4>
                        <p class="uk-text-bold"><c:out value="${userm.firm_name}"/></p>
                        <p>NIP. <c:out value="${userm.firm_nip}"/></p>
                        <p> <c:out value="${userm.firm_email}"/></p>
                        <p>tel: <c:out value="${userm.firm_tel}"/></p>
                    </div>
                    </c:if>
                    <c:choose>
                        <c:when test = "${id_user_logged!=null}">
                            <a href="#modal-choose1" class="uk-button uk-button-secondary uk-width-1-1" uk-toggle>Zmień adres</a>
                        </c:when>
                        <c:otherwise>
                            <a href="NLbasket" class="uk-button uk-button-secondary uk-width-1-1">Zmień adres</a>
                        </c:otherwise>
                    </c:choose>
                </div>
                <div>
                    <c:if test = "${(discount_id!=null)&&(discount_id!=0)}"> 
                    <div class="uk-flex uk-flex-right uk-margin-large-right@m  uk-flex-left@s" >
                        <p>Rabat:</p>
                        <h4 class="uk-margin-remove-top uk-margin-medium-left"><c:out value="${disc_value}"/>&nbsp;%</h4> 
                    </div>
                    </c:if>
                    <div class="uk-flex uk-flex-right uk-margin-remove-bottom uk-margin-large-right@m  uk-flex-left@s">
                        <p>Suma:</p>
                        <h4 class="uk-margin-remove-top uk-margin-medium-left">
                            <c:choose>
                                <c:when test = "${discount_id!=0}">
                                    <s style="color: #860e0e;"><c:out value="${sum}"/>&nbsp;zł</s>
                                    <c:set var="priceDisc" 
                                           value="${Math.round((sum -(disc_value/100.0)*sum )*100.0)/100.0}"/>
                                    <c:out value="${priceDisc}"/>&nbsp;zł
                                </c:when>
                                <c:otherwise>
                                    <c:set var="priceDisc" value="${sum}"/>
                                    <c:out value="${priceDisc}"/>&nbsp;zł
                                </c:otherwise>
                            </c:choose>
                        </h4> 
                    </div>
                    <div class="uk-flex uk-flex-right uk-margin-large-right@m  uk-flex-left@s" >
                        <p>Dostawa:</p>
                        <h4 class="uk-margin-remove-top uk-margin-medium-left"><c:out value="${delivery.price}"/>&nbsp;zł</h4> 
                    </div>
                    <div class="uk-flex uk-flex-right uk-margin-large-right@m  uk-flex-left@s" >
                        <p>Łączna wartość zakupów:</p>
                        <h4 class="uk-margin-remove-top uk-margin-medium-left uk-text-bold">
                            <c:set var="summary" value="${priceDisc + delivery.price}"/> <!-- delivery.price-->
                            <c:out value="${Math.round(summary*100.00)/100.00}"/>&nbsp;zł</h4> 
                    </div>
                </div>
            </div>  
            <form class="uk-form-stacked uk-margin" id="ThisFormSumm" method="POST" action="addOrder" accept-charset="ISO-8859-1">    
                <div class="uk-grid-small" uk-grid> 
                    <div class=" uk-margin-small-top">
                        <div class="uk-form-controls uk-margin-large@m">
                            <label for="form-stacked-text" for="comm">
                            <span class="uk-h4 uk-text-bold">Komentarz: </span>
                            <span class="uk-text-danger uk-text-small"> (zapisywany tylko po zatwierdzeniu zamówienia)</span>
                            </label>  
                            <textarea id="comm" name="comm" class="uk-textarea " rows="3" 
                                      placeholder="Dodaj komentarz do zamówienia... "></textarea>
                        </div>
                    </div>
                </div>
                <div class="uk-flex uk-flex-right uk-margin-small-top">
                    <div>
                        <label for="terms"><span class="uk-text-secondary">Akceptuje warunki zapisane w regulaminie* </span></label>
                        <input id="regul" name="regul" class="uk-checkbox" type="checkbox">  <!-- checked-->
                    </div>
                </div>
                <div class="uk-button uk-button-secondary uk-align-right uk-margin-large-right@m uk-margin-medium-top" >
                    <button class="uk-button uk-button-secondary" type="submit">Potwierdzam i składam zamówienie</button> 
                </div>
            </form>
        </section>
      </div>
    </main>
    <c:if test = "${id_user_logged!=null}">  
    <div id="modal-choose1" uk-modal>
        <div class="uk-modal-dialog uk-dark uk-background-muted">
            <button class="uk-modal-close-default" type="button" uk-close></button>
            <div class="uk-modal-header">
                <h2 class="uk-modal-body uk-text-center">Wybierz:</h2>
            </div>
            <form id="ThisForm" class="uk-form-stacked uk-position-relative" action="updBaskAdr" method="POST" accept-charset="ISO-8859-1">
            <div class="uk-modal-body">
                <div class="uk-grid-medium uk-child-width-1-1" uk-grid>
                    <div class="uk-form-controls">    
                       <select id="choice" name="choice" class="uk-select">
                            <c:forEach var="adr" items="${addresses}">
                            <option value="<c:out value="${adr.idMeta}"/>"
                              <c:if test="${adr.idMeta==userMeta_id}">selected</c:if>>
                                ul. <c:out value="${adr.adr_str}"/>&nbsp;<c:out value="${adr.adr_nr}"/> 
                                | <c:out value="${adr.adr_code}"/>&nbsp;<c:out value="${adr.adr_post}"/>, <c:out value="${adr.adr_state}"/>
                            </option>
                            </c:forEach>
                        </select>
                    </div>
                </div>
                <div class="uk-modal-footer uk-text-right uk-margin-medium-top">
                    <button class="uk-button uk-button-primary" type="submit">Zmień</button> 
                </div>
            </div>
        </form> 
        </div>
    </div>
    </c:if>
                    
   <!--FOOTER-->  
    <jsp:include page="../footer.jsp"/>
  </body>
</html>
