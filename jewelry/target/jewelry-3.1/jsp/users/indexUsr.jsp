<%-- 
    Document   : indexUsr
    Created on : 14 wrz 2021, 10:50:27
    Author     : DRzepka
--%>

<%@ page trimDirectiveWhitespaces="true" %> <%--prevent nextLine in document--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page errorPage="error" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>  
<!DOCTYPE html>
<html>
  <head>
      <jsp:include page="../head.jsp"/>
    <link rel="stylesheet" type="text/css" href="css/styleUsr.css"/>
<!--TITLE-->    
    <title>Ametyst - user</title>
  </head>
  <body>
  <!--HEADER-->
    <jsp:include page="../header.jsp"/>
    <!--SIDENAV -->
      <jsp:include page="../navL.jsp"/>
  <!--MAIN-->
    <main>
      <div class="uk-margin" uk-grid>
      <!--ASIDE-->
        <jsp:include page="../aside.jsp"/>
        <section class="uk-width-4-5@m uk-margin-medium">
            <!--kianit.pl-->
            <h2>Witaj ponownie <c:out value="${name}"/>&nbsp;<c:out value="${surname}"/>!</h2>
            <hr>
            <c:if test="${not empty listOrder}">
            <h3 class="uk-text-center">Twoje zamówienia:</h3>
               <c:forEach var="userm" items="${listUserm}" varStatus="stat">
            <ul uk-accordion>    
                <c:forEach var="ord" items="${listOrder}" varStatus="sta">
                 <c:if test = "${(ord.idUserMeta==userm.idMeta)}">
                  <c:set var="i" value="${1}"/>
                  <c:set var="sum" value="${0}"/>
                <li>
                    <a class="uk-accordion-title" href="#">Zamówienie nr. <c:out value="${ord.id}"/></a>
                    <div class="uk-accordion-content">
                        <div class="uk-accordion-content">
                            <div class="uk-child-width-1-3@m uk-margin-medium-bottom" uk-grid>
                                <p> <span class="uk-text-bold uk-h4">Status:</span>
                                     <br/><span class="uk-text-bold"><c:out value="${listStat[ord.idStat-1].name}"/></span>
                                     <br/><c:out value="${listStat[ord.idStat-1].description}"/></p>
                                <p> <span class="uk-text-bold uk-h4">Płatność:</span>
                                    <br/><c:out value="${listPay[ord.idPay].category}"/>:
                                    <c:out value="${listPay[ord.idPay-1].name}"/></p>
                                <p> <span class="uk-text-bold uk-h4">Wysyłka:</span>
                                    <br/><c:out value="${listDel[ord.idDeliv-1].name}"/>
                                    <br/><span class="uk-text-bold"><c:out value="${ord.deliv_nr}"/></span></p>
                            </div>
                            <table class="uk-table uk-table-hover uk-table-responsive uk-table-divider uk-text-left@xs">
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
                                   <c:forEach var="prod" items="${listProdMt}" varStatus="st">
                                    <c:if test = "${(prod.id==listOrderp[st.index].idProd)&&(ord.id==listOrderp[st.index].idOrder)}"> <!--&&(ord.idUserMeta==userm.idMeta)-->
                                    <tr>
                                        <td class="uk-hidden@xs"><c:out value="${i}"/></td>
                                        <td>
                                            <img src="http://localhost:8080/jewelry/img/CT/<c:out value="${prod.id}"/>/1.jpg" alt="img" width="50"
                                                class="uk-align-left uk-margin-remove-adjacent"/>
                                             <a href="product?id=${prod.id}" target="_blanc"><c:out value="${prod.name}"/></a>
                                        <td class="fl"><c:out value="${listOrderp[st.index].quantity}"/><span class="uk-hidden@m">&nbsp;szt.</span></td>
                                        <td>
                                            <c:choose>
                                                <c:when test = "${listOrderp[st.index].discount!=0}">
                                                    <s style="color: #860e0e;"><fmt:formatNumber type="number" maxFractionDigits="2" minFractionDigits="2" 
                                                        value="${prod.price}"/>&nbsp;zł</s>
                                                    <c:set var="priceOfProduct" 
                                                           value="${Math.round((prod.price-(listOrderp[st.index].discount/100)*prod.price)*100.0)/100.0}"/>
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
                                        </td>
                                        <td class="uk-text-bold uk-text-right@xs">
                                            <c:set var="price" value="${Math.round((priceOfProduct*listOrderp[st.index].quantity)*100.00)/100.00}"/>
                                            <c:set var="sum" value="${Math.round((sum + price)*100.00)/100.00}"/>
                                            <span class="uk-hidden@m">Wartość: </span><fmt:formatNumber type="number" 
                                                maxFractionDigits="2" minFractionDigits="2" value="${price}"/>&nbsp;zł
                                        </td>
                                    </tr>
                                    <c:set var="i" value="${i+1}"/>
                                    </c:if>
                                   </c:forEach>
                                </tbody>
                            </table>
                            <div class="uk-child-width-1-2@m" uk-grid>
                                <div class="uk-child-width-1-2@m" uk-grid>
                                    <div>
                                        <h4 class="uk-text-bold">Adres:</h4>
                                        <p class="uk-text-bold"><c:out value="${name}"/>&nbsp;<c:out value="${surname}"/></p>
                                        <p>ul.&nbsp;<c:out value="${userm.adr_str}"/>&nbsp;<c:out value="${userm.adr_nr}"/></p>
                                        <p><c:out value="${userm.adr_code}"/>&nbsp;<c:out value="${userm.adr_town}"/></p>
                                        <p><c:out value="${userm.adr_state}"/>,&nbsp;<c:out value="${userm.adr_count}"/></p>
                                    </div>
                                    <c:if test = "${userm.firm==true}"> 
                                    <div>
                                        <h4 class="uk-text-bold">Firma:</h4>
                                        <p class="uk-text-bold"><c:out value="${userm.firm_name}"/></p>
                                        <p>NIP. <c:out value="${userm.firm_nip}"/></p>
                                        <p> <c:out value="${userm.firm_email}"/></p>
                                        <p>tel: <c:out value="${userm.firm_tel}"/></p>
                                    </div>
                                    </c:if>
                                </div>
                                <div>
                                     <c:if test = "${(ord.idDisc!=null)&&(ord.idDisc!=0)}"> 
                                    <div class="uk-flex uk-flex-right uk-margin-large-right" >
                                        <p>Rabat:</p>
                                        <h4 class="uk-margin-remove-top uk-margin-medium-left"><c:out value="${listDisc[ord.idDisc-1].value}"/>&nbsp;%</h4> <!---->
                                    </div>
                                    </c:if>
                                    <div class="uk-flex uk-flex-right uk-margin-remove-bottom uk-margin-large-right" >
                                        <p>Suma:</p>
                                        <h4 class="uk-margin-remove-top uk-margin-medium-left">
                                            <c:choose>
                                                <c:when test = "${ord.idDisc!=0}">
                                                    <s style="color: #860e0e;"><c:out value="${sum}"/>&nbsp;zł</s>
                                                    <c:set var="priceDisc" 
                                                           value="${ord.sum}"/>
                                                    <c:out value="${priceDisc}"/>&nbsp;zł
                                                </c:when>
                                                <c:otherwise>
                                                    <c:set var="priceDisc" value="${ord.sum}"/>
                                                    <c:out value="${priceDisc}"/>&nbsp;zł
                                                </c:otherwise>
                                            </c:choose>
                                        </h4> 
                                    </div>
                                    <div class="uk-flex uk-flex-right uk-margin-large-right" >
                                        <p>Dostawa:</p>
                                        <h4 class="uk-margin-remove-top uk-margin-medium-left"><c:out value="${listDel[ord.idDeliv-1].price}"/>&nbsp;zł</h4> 
                                    </div>
                                    <div class="uk-flex uk-flex-right uk-margin-large-right" >
                                        <p>Łączna wartość zakupów:</p>
                                        <h4 class="uk-margin-remove-top uk-margin-medium-left uk-text-bold">
                                            <c:set var="summary" value="${priceDisc + listDel[ord.idDeliv-1].price}"/> <!-- delivery.price-->
                                            <c:out value="${Math.round(summary*100.00)/100.00}"/>&nbsp;zł</h4> 
                                    </div>
                                </div>
                            </div>
                            <c:if test = "${reviewed.get(sta.index)!=false}">
                            <div class="uk-child-width-1-2" uk-grid>
                                <div class="uk-margin-medium-top" >
                                     <button class="uk-button uk-button-secondary" type="submit" 
                                             onclick="window.location.href='review?id=${ord.id}'">Zrecenzuj produkty</button> 
                                 </div>
                            </c:if>
                            <c:if test = "${ord.comments!=null}">  
                                <div class="uk-flex uk-flex-right uk-margin-medium-top 
                                     <c:if test = "${reviewed.get(sta.index)==false}">uk-margin-medium-right</c:if>">
                                    <p>Komentarz:</p>
                                    <h4 class="uk-margin-remove-top uk-margin-medium-left"><c:out value="${ord.comments}"/></h4> 
                                </div>
                            <c:if test = "${reviewed.get(sta.index)!=false}"></div></c:if>
                            </c:if>
                        </div>
                    </div>
                </li>
               </c:if>
               </c:forEach>
              </c:forEach>
            </ul>
            </c:if>
            <style>
                p a, p a:link, p a:active, p a:visited{ color: #361251;}
                p a:hover{ color: #6b26a0;}
            </style>
            <c:if test="${empty listOrder}">
            <div class="uk-align-center uk-text-center uk-margin-large-top uk-margin-large-left" style="height: 30vh" uk-grid>
                <h3>Nie masz jeszcze zamówień!</h3> 
                <p>Na twoim koncie nie ma jeszcze zamówień. Musisz złożyć zamówienie aby było ono widoczne w tym oknie.</p>
                <p class="uk-margin-remove-top">Przejdź do <a href="home" class="uk-text-bold">Strony głównej</a> aby dodać produkty</p>
            </div>  
            </c:if>
        </section>

      </div>
    </main> 
   <!--FOOTER-->  
    <jsp:include page="../footer.jsp"/>
  </body>
</html>

