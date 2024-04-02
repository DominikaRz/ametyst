<%-- 
    Document   : Basket
    Created on : 6 wrz 2021, 12:23:30
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
      <link rel="stylesheet" type="text/css" href="css/styleBask.css"/>
<!--TITLE-->    
    <title>Koszyk - krok 1</title>
  </head>
  <body>
  <!--HEADER-->
    <jsp:include page="../header.jsp"/>
    <!--SIDENAV -->
        <jsp:include page="../nav.jsp"/>
  <!--MAIN-->
    <main> 
      <div class="uk-margin" uk-grid>
      <!--ABOUT-->  
       <section class="uk-width-1-1 uk-margin-medium uk-align-center"><!---->
        <c:choose>
          <c:when test = "${not empty listProd}">
            <ul class="uk-nav uk-column-1-4@m uk-column-divider uk-visible@m uk-text-center uk-margin-large-bottom"
                style="width: 80%; margin-left: 10%;">
                <li class="uk-active">
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
                <li class="unsee">
                    <h3 class="uk-margin-remove-bottom">Krok 4/4</h3>
                    <p>Podsumowanie</p>
                </li>
            </ul>
            <script>
                $(".unsee > h3").addClass("uk-text-muted");
                $(".unsee > p").addClass("uk-text-muted");
            </script>
            <table class="uk-table uk-table-responsive uk-table-hover uk-table-divider">
                <thead>
                    <tr>
                        <th class="uk-width-auto">Produkt</th>
                        <th class="uk-width-auto" style="width: 240px">Ilość</th>
                        <th class="uk-width-small">Cena</th>
                        <th class="uk-width-small">Wartość</th>
                        <th class="uk-width-small">Usuń</th>
                    </tr>
                </thead>
                <tbody>
                  <c:forEach var="prod" items="${listProd}" varStatus="stat">
                    <tr class="uk-text-center@xs">
                        <td>
                            <img src="http://localhost:8080/jewelry/img/CT/<c:out value="${prod.id}"/>/1.jpg" alt="img" width="50"
                                 class="uk-align-left uk-margin-remove-adjacent"/>
                            <a href="product?id=${prod.id}"><c:out value="${prod.name}"/></a>
                        </td>
                        <td style="display: inline-block;">
                            <form id="ThisFormBask1" action="updBask?id=${prod.id}" method="POST" style="display: inline;">
                                <div>
                                    <button type="button" class="uk-button uk-button-secondary minus" style="padding: 0px 15px;"
                                            onclick="if($(this).next().val()>=2)$(this).next().val(+$(this).next().val() - 1);">
                                        <span uk-icon="minus"></span></button>
                                    <input id="nbr" name="nbr" class="uk-input uk-text-center" type="number" 
                                           value="${listBask[stat.index].quantity}" step="1" min="1" max="20"/>
                                    <button type="button" class="uk-button uk-button-secondary plus"  style="padding: 0px 15px;"
                                            onclick="if($(this).prev().val()<=20)$(this).prev().val(+$(this).prev().val() + 1);">
                                        <span uk-icon="plus"></span></button>
                                    <button class="uk-button uk-button-link" type="submit" uk-tooltip="title: uaktualnij; pos: right">
                                        <span uk-icon="icon: check; ratio: 1.4"></span></button>
                                </div>
                            </form>
                        </td>
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
                                </c:when>
                                <c:otherwise>
                                    <c:set var="priceOfProduct" value="${prod.price}"/>
                                    <fmt:formatNumber type="number" maxFractionDigits="2" minFractionDigits="2" 
                                          value="${priceOfProduct}"/>&nbsp;zł
                                </c:otherwise>
                            </c:choose>
                                    <span class="uk-hidden@m">/szt.</span>
                            </p>
                        </td>
                        <td class="uk-text-bold uk-text@xs">
                            <c:set var="price" value="${Math.round((priceOfProduct*listBask[stat.index].quantity)*100.00)/100.00}"/>
                            <c:set var="sum" value="${Math.round((sum + price)*100.00)/100.00}"/>
                            <span class="uk-hidden@m">Wartość: </span><fmt:formatNumber type="number" 
                                    maxFractionDigits="2" minFractionDigits="2" value="${price}"/>&nbsp;zł
                        </td>
                        <td>
                            <a href="delBask?id=<c:out value="${prod.id}"/>" class="item-rm" uk-icon="icon: close; ratio: 1.25">
                                <span class="uk-hidden@m">Usuń: </span></a>
                        </td>
                    </tr>
                    </c:forEach>
                </tbody>
            </table>
            <div class="uk-margin-large-right" >
                <a href="#" uk-toggle="target: #modal1;">
                    <h3 class="uk-margin-remove-top uk-margin-medium-left uk-margin-medium-bottom">
                        <span uk-icon="icon: trash; ratio: 1.3"></span> Opróżnij koszyk</h3>
                </a>
            </div>
            <div class="uk-flex uk-flex-right uk-flex-left@s uk-margin-large-right" >
                <p>Suma:</p>
                <h3 class="uk-margin-remove-top uk-margin-medium-left"><span id="sum"></span>&nbsp;zł</h3> 
            </div>
            <div class="uk-flex uk-flex-right uk-flex-left@s uk-margin-large-right" >
                <p>Dostawa:</p>
                <h4 class="uk-margin-remove-top uk-margin-medium-left"><span id="deli"></span>&nbsp;zł</h4> 
            </div> <!---->
            <c:if test = "${(discount_id!=0)&&(discount_id!=null)}"> 
            <div class="uk-flex uk-flex-right uk-flex-left@s uk-margin-large-right" >
                <p>Rabat:</p>
                <h4 class="uk-margin-remove-top uk-margin-medium-left"><c:out value="${disc_value}"/>&nbsp;%</h4> 
            </div>
            </c:if>
            <div class="uk-flex uk-flex-right uk-flex-left@s uk-margin-large-right" >
                <p>Łączna&nbsp;wartość zakupów:</p>
                <h3 class="uk-margin-remove-top uk-margin-medium-left uk-text-bold">
                    <span id="summary"></span>&nbsp;zł 
                </h3> 
            </div>
            <div class="uk-flex uk-flex-right uk-margin-large-right@m uk-margin-medium-top" >
                <form id="ThisFormBask1" action="checkDisc" method="POST" accept-charset="ISO-8859-1" style="display: inline;">
                        <input class="uk-input uk-width-medium@m" type="text" id="name" name="name" placeholder="Kod rabatowy..."
                               <c:if test = "${(discount_id!=0)&&(discount_id!=null)}"> value="<c:out value="${disc_name}"/>"</c:if>>
                    <button class="uk-button uk-button-link" type="submit" uk-tooltip="title: sprawdź;">
                        <span uk-icon="icon: check; ratio: 1.25"/>
                    </button>
                </form> 
            </div>
            <form class="uk-form-stacked uk-margin-medium-top" id="ThisFormBask" method="POST" action="nextBasket" accept-charset="ISO-8859-1">
                <div uk-grid>
                    <div class="uk-width-2-5@m" style="padding: 0;">
                        <h4 class="uk-text-uppercase">Płatność:</h4>
                        <ul uk-accordion>
                        <c:forEach var="pay" items="${listPay}" varStatus="stat">
                            <c:if test = "${listPay[stat.index-1].category != pay.category}">
                            <li<c:if test = "${pay.category.equals('Inne')}"> class="uk-open"</c:if>>
                                <a class="uk-accordion-title" href="#"><p class="uk-margin-small-top"><c:out value="${pay.category}"/>:</p></a>
                                <div class="uk-accordion-content">
                                </c:if>
                                    <label>
                                        <input class="uk-radio uk-margin-medium-left@s" type="radio" name="radio1" value="<c:out value="${pay.id}"/>"
                                        <c:choose>
                                            <c:when test = "${(payment_id!=0)&&(payment_id!=null)}">
                                                <c:if test = "${pay.id==payment_id}">checked</c:if>>
                                            </c:when>
                                            <c:otherwise>
                                               <c:if test = "${pay.id==12}">checked</c:if>>
                                            </c:otherwise>
                                        </c:choose> 
                                        <c:out value="${pay.name}"/>;
                                    </label><br>
                                <c:if test = "${listPay[stat.index+1].category != pay.category}">
                                </div>
                            </li>
                            </c:if>
                        </c:forEach>
                        </ul>
                    </div>
                    <div class="uk-width-3-5@m uk-margin-medium-bottom" style="padding: 0;">
                        <h4 class="uk-text-uppercase">Dostawa:</h4>
                        <c:forEach var="deliv" items="${listDel}" varStatus="stat">
                        <p style="padding-top: 0.2rem;"><label class="radioch">
                            <input class="uk-radio" type="radio" name="radio2" value="<c:out value="${deliv.id}"/>" 
                            <c:choose>
                                <c:when test = "${(discount_id!=0)&&(discount_id!=null)}">
                                    <c:if test = "${deliv.id==discount_id}">checked</c:if>>
                                </c:when>
                                <c:otherwise>
                                   <c:if test = "${deliv.id==11}">checked</c:if>>
                                </c:otherwise>
                            </c:choose> 
                            <c:out value="${deliv.name}"/>
                            (<fmt:formatNumber type="number" maxFractionDigits="2" minFractionDigits="2" 
                                          value="${deliv.price}"/>&nbsp;zł)
                        </label></p>
                        </c:forEach>
                    </div>
                </div>
                <button class="uk-button uk-button-secondary uk-align-right uk-margin-large-right" type="submit" >Dalej...</button>
            </form>
            <script>
                function showSum() {
                    var delivery = $('input[name=radio2]:checked', '#ThisFormBask' ).val(); 
                     var tab = [<c:forEach var="deliv" items="${listDel}" varStatus="stat"><c:out value="${deliv.price}"/>0, </c:forEach>0.0];
                     $("#deli").html(tab[delivery-1].toFixed(2).toString()); //
                     var sum = <c:out value="${sum}"/>;
                     var disc = <c:out value="${disc_value}"/>; 
                     var string= '';
                     var str= 0;
                     if(disc!==0){
                         string = '<s style="color: #860e0e;">'+sum+'&nbsp;zł</s> '+ (sum-(disc/100.0)*sum).toFixed(2);
                         sum = sum-(disc/100.0)*sum;
                     }
                     else{
                         string = sum.toFixed(2);
                     }
                     $("#sum").html(string.toString()); 
                     $("#summary").html((sum+tab[delivery-1]).toFixed(2).toString()); //
                  }
                
               //to get value at start of the page 
                $(document).ready(function(){ showSum(); });
                
               //to get correct value of changing radios  
                $('#ThisFormBask input').on('change', function() { showSum(); }); 
                
            </script> 
          </c:when>
          <c:otherwise>
            <div class="uk-align-center uk-text-center uk-margin-large-top uk-margin-large-left" style="height: 30vh" uk-grid>
                <h2>Koszyk jest pusty!</h2> 
                <p>Twój koszyk jest obecnie pusty. Musisz dodać produkty aby móc złożyć zamówienie.</p>
                <p class="uk-margin-remove-top">Przejdź do <a href="home" class="uk-text-bold">Strony głównej</a> aby dodać produkty</p>
            </div>
          </c:otherwise>
        </c:choose>
       </section>
      </div>                      
        <div id="modal1" uk-modal>
            <div class="uk-modal-dialog uk-modal-body uk-text-center"">
                <button class="uk-modal-close-outside" type="button" uk-close></button>
                <div class="uk-modal-header"><h2 class="uk-modal-title uk-text-danger">Uwaga!</h2></div>
                    <div class="uk-grid-medium uk-margin-small-top" uk-grid>
                        <div class="uk-width-1-1">
                            <h4 class=" uk-text-danger">Tej operacji nie można cofnąć!</h4>
                            <p>Czy mimo to chcesz kontynuować?</p>
                        </div>
                </div>
                <div class="uk-flex uk-flex-center uk-margin-medium-top uk-child-width-1-2@m uk-child-width-1-2@l uk-child-width-1-1@s" uk-grid>
                    <button class="uk-button uk-button-default uk-modal-close" type="button">Nie,&nbsp;rezygnuje</button>  
                    <button class="uk-button uk-button-secondary" type="button" onclick="window.location.href='delAllBask'"
                            style="margin:0; padding:0; ">Tak,&nbsp;usuń</button> 
                </div>
            </div>
        </div>
    </main> 
   <!--FOOTER-->  
    <jsp:include page="../footer.jsp"/>
  </body>
</html>
