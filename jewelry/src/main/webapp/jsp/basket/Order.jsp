<%-- 
    Document   : Order
    Created on : 24 paź 2021, 13:24:42
    Author     : DRzepka
--%>

<%@ page trimDirectiveWhitespaces="true" %> <%--prevent nextLine in document--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
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
    <!--SIDENAV -->
      <jsp:include page="../nav.jsp"/>
  <!--MAIN-->
    <main>
      <div class="uk-margin" uk-grid>
      <!--ASIDE-->
      <c:if test = "${(acc!=2)||(acc==null)}">
        <jsp:include page="../aside.jsp"/>
        <section class="uk-width-4-5@m uk-margin-medium">
            <h2>Wypełnij formularz aby wyświetlić zamówienie: </h2>
            <hr>
            <div align="center">
                <form class="uk-form-stacked" id="ThisFormOrderNL" method="POST" action="order?acc=1" accept-charset="ISO-8859-1">
                   <div class="uk-grid-small" uk-grid>
                       <div class="uk-width-2-5">
                           <div class="uk-form-controls">
                              <input id="name" name="name" class="uk-input" value="<c:out value="${order.name}"/>"
                                      type="text" maxlength="100" placeholder="Imię..."/>
                           </div>
                       </div>
                       <div class="uk-width-2-5">
                           <div class="uk-form-controls">
                              <input id="surname" name="surname" class="uk-input" value="<c:out value="${order.surname}"/>"
                                      type="text" maxlength="40" placeholder="Nazwisko..."/>
                           </div>
                       </div>
                   </div>
                   <div class="uk-grid-small" uk-grid>
                       <div class="uk-width-3-5">
                           <div class="uk-form-controls">
                              <input id="mail" name="mail" class="uk-input" value="<c:out value="${order.email}"/>"
                                      type="text" maxlength="100" placeholder="Adres email..."/>
                           </div>
                       </div>
                       <div class="uk-width-2-5">
                           <div class="uk-form-controls">
                              <input id="order" name="order" class="uk-input" value="<c:out value="${order.id}"/>"
                                      type="text" maxlength="10" placeholder="Nr zamówienia..."/>
                           </div>
                       </div>
                   </div>
                   <div class="uk-flex uk-flex-center uk-margin-medium-top" >
                       <button class="uk-button uk-button-secondary" type="submit">Potwierdź</button> 
                   </div>
               </form>
            </div>            
           </c:if> 
           <c:if test = "${(acc==1)||(acc==2)}"> 
           <c:if test = "${(acc==2)}">
            <div style="width: 80%; margin-left: 10%;">
            </c:if>
            <h3 class="uk-text-center">Twoje zamówienie:</h3>
            <c:set var="i" value="${1}"/>
            <c:set var="sum" value="${0}"/>
            <h3>Zamówienie nr. <c:out value="${order.id}"/></h3>
            <div class="uk-accordion-content">
                <div class="uk-accordion-content">
                    <div class="uk-child-width-1-3" uk-grid>
                        <p> <span class="uk-text-bold uk-h4">Status:</span>
                             <br/><span class="uk-text-bold"><c:out value="${stat.name}"/></span>
                             <br/><c:out value="${stat.description}"/></p>
                        <p> <span class="uk-text-bold uk-h4">Płatność:</span>
                            <br/><c:out value="${pay.category}"/>:
                            <c:out value="${pay.name}"/></p>
                        <p> <span class="uk-text-bold uk-h4">Wysyłka:</span>
                            <br/><c:out value="${del.name}"/>
                            <br/><span class="uk-text-bold"><c:out value="${order.deliv_nr}"/></span></p>
                    </div>
                    <table class="uk-table uk-table-hover uk-table-divider">
                        <thead>
                            <tr>
                                <th class="uk-width-small">Lp.</th>
                                <th>Produkt</th>
                                <th class="uk-width-small">Ilość</th>
                                <th class="uk-width-small">Cena</th>
                                <th class="uk-width-small">Wartość</th>
                            </tr>
                        </thead>
                        <tbody>
                           <c:forEach var="prod" items="${listProdm}" varStatus="st">
                            <tr>
                                <td><c:out value="${i}"/></td>
                                <td>
                                    <img src="http://localhost:8080/jewelry/img/CT/<c:out value="${prod.id}"/>/1.jpg" alt="img" width="50"/>
                                     <a href="product?id=${prod.id}" target="_blanc"><c:out value="${prod.name}"/></a>
                                <td><c:out value="${listOrderp[st.index].quantity}"/></td>
                                <td>
                                    <p>
                                    <c:choose>
                                        <c:when test = "${listOrderp[st.index].discount!=0}">
                                            <s style="color: #860e0e;"><c:out value="${prod.price}"/>&nbsp;zł</s>
                                            <c:set var="priceOfProduct" 
                                                   value="${Math.round((prod.price-(listOrderp[st.index].discount/100)*prod.price)*100.0)/100.0}"/>
                                            <c:out value="${priceOfProduct}"/>&nbsp;zł
                                        </c:when>
                                        <c:otherwise>
                                            <c:set var="priceOfProduct" value="${prod.price}"/>
                                            <c:out value="${priceOfProduct}"/>&nbsp;zł
                                        </c:otherwise>
                                    </c:choose>
                                    </p>
                                </td>
                                <td class="uk-text-bold">
                                    <c:set var="price" value="${Math.round((priceOfProduct*listOrderp[st.index].quantity)*100.00)/100.00}"/>
                                    <c:set var="sum" value="${Math.round((sum + price)*100.00)/100.00}"/>
                                    <c:out value="${price}"/>&nbsp;zł
                                </td>
                            </tr>
                            <c:set var="i" value="${i+1}"/>
                           </c:forEach>
                        </tbody>
                    </table>
                    <div class="uk-child-width-1-2" uk-grid>
                       <c:if test="${acc!=2}"> 
                        <div class="uk-child-width-1-2" uk-grid>
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
                        </c:if>
                        <c:if test="${acc==2}">
                        <div class="uk-child-width-1-2" uk-grid>
                            <div>
                                <h4 class="uk-text-bold">Adres:</h4>
                                <p class="uk-text-bold uk-margin-remove"><c:out value="${order.name}"/>&nbsp;<c:out value="${order.surname}"/></p>
                                <p class="uk-margin-remove">ul.&nbsp;<c:out value="${userm.adr_str}"/>&nbsp;<c:out value="${userm.adr_nr}"/></p>
                                <p class="uk-margin-remove"><c:out value="${userm.adr_code}"/>&nbsp;<c:out value="${userm.adr_town}"/></p>
                                <p class="uk-margin-remove"><c:out value="${userm.adr_state}"/>,&nbsp;<c:out value="${userm.adr_count}"/></p>
                            </div>
                            <c:if test = "${userm.firm==true}"> 
                            <div>
                                <h4 class="uk-text-bold">Firma:</h4>
                                <p class="uk-text-bold uk-margin-remove"><c:out value="${userm.firm_name}"/></p>
                                <p class="uk-margin-remove">NIP. <c:out value="${userm.firm_nip}"/></p>
                                <p class="uk-margin-remove"> <c:out value="${userm.firm_email}"/></p>
                                <p class="uk-margin-remove">tel: <c:out value="${userm.firm_tel}"/></p>
                            </div>
                            </c:if>
                        </div>
                        </c:if>
                        <div>
                             <c:if test = "${(order.idDisc!=null)&&(order.idDisc!=0)}"> 
                            <div class="uk-flex uk-flex-right uk-margin-large-right" >
                                <p>Rabat:</p>
                                <h4 class="uk-margin-remove-top uk-margin-medium-left"><c:out value="${disc.value}"/>&nbsp;%</h4> <!---->
                            </div>
                            </c:if>
                            <div class="uk-flex uk-flex-right uk-margin-remove-bottom uk-margin-large-right" >
                                <p>Suma:</p>
                                <h4 class="uk-margin-remove-top uk-margin-medium-left">
                                    <c:choose>
                                        <c:when test = "${order.idDisc!=0}">
                                            <s style="color: #860e0e;"><c:out value="${sum}"/>&nbsp;zł</s>
                                            <c:set var="priceDisc" 
                                                   value="${order.sum}"/>
                                            <c:out value="${priceDisc}"/>&nbsp;zł
                                        </c:when>
                                        <c:otherwise>
                                            <c:set var="priceDisc" value="${order.sum}"/>
                                            <c:out value="${priceDisc}"/>&nbsp;zł
                                        </c:otherwise>
                                    </c:choose>
                                </h4> 
                            </div>
                            <div class="uk-flex uk-flex-right uk-margin-large-right" >
                                <p>Dostawa:</p>
                                <h4 class="uk-margin-remove-top uk-margin-medium-left"><c:out value="${del.price}"/>&nbsp;zł</h4> 
                            </div>
                            <div class="uk-flex uk-flex-right uk-margin-large-right" >
                                <p>Łączna wartość zakupów:</p>
                                <h4 class="uk-margin-remove-top uk-margin-medium-left uk-text-bold">
                                    <c:set var="summary" value="${priceDisc + del.price}"/> <!-- delivery.price-->
                                    <c:out value="${Math.round(summary*100.00)/100.00}"/>&nbsp;zł</h4> 
                            </div>
                        </div>
                    </div>
                    <c:if test = "${(reviewed!=false)}">
                    <div class="uk-child-width-1-2" uk-grid>
                        <div class="uk-margin-medium-top" >
                             <button class="uk-button uk-button-secondary" type="submit" 
                                     onclick="window.location.href='review?id=${order.id}'">Zrecenzuj produkty</button> 
                         </div>
                    </c:if>
                    <c:if test = "${order.comments!=null}">    
                        <div class="uk-flex uk-flex-right uk-margin-medium-top 
                             <c:if test = "${reviewed==false}">uk-margin-medium-right</c:if>">
                            <p>Komentarz:</p>
                            <h4 class="uk-margin-remove-top uk-margin-medium-left"><c:out value="${order.comments}"/></h4> 
                        </div>
                    <c:if test = "${reviewed!=false}"></div></c:if>
                    </c:if>
                </div>
            </div>
            <c:if test = "${(acc==2)}"></div></c:if>
           </c:if> 
       <c:if test = "${(acc!=2)||(acc==null)}">
        </section></c:if>
      </div>
    </main> 
   <!--FOOTER-->  
    <jsp:include page="../footer.jsp"/>
  </body>
</html>


