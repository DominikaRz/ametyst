<%-- 
    Document   : Review
    Created on : 21 paź 2021, 09:23:52
    Author     : Dominika
--%>

<%@ page trimDirectiveWhitespaces="true" %> <%--prevent nextLine in document--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
  <head>
      <jsp:include page="../head.jsp"/>
    <link rel="stylesheet" type="text/css" href="css/styleUsr.css"/>
<!--TITLE-->    
    <title>Ametyst</title>
    <style>
        .selected svg polygon{ fill: #6113b9af; stroke-width: 1.01;}
        .uk-icon svg polygon{ stroke-width: 1.01;}
        i{  cursor: pointer;}
        h5 a, h5 a:hover, h5 a:active, h5 a:link{ color: #742f9e;}
        h5 a:hover{ color: #361251;}
        .uk-accordion-title::before{ display: contents;}
    </style>
  </head>
  <body>
  <!--HEADER-->
    <jsp:include page="../header.jsp"/>
    <!--SIDENAV -->
    <c:if test="${id_user_logged!=null}"><jsp:include page="../navL.jsp"/></c:if>
    <c:if test="${id_user_logged==null}"><jsp:include page="../nav.jsp"/></c:if>
  <!--MAIN-->
    <main>
      <div class="uk-margin" uk-grid>
      <!--ASIDE-->
        <jsp:include page="../aside.jsp"/>
        <section class="uk-width-4-5@m uk-margin-medium">
            <h2>Recenzje w wybranym zamówieniu: </h2>
            <hr>
            <form class="uk-form-stacked" id="ThisFormRev" method="POST" action="addReview?&ord=${order}" accept-charset="ISO-8859-1">
                <ul uk-accordion> 
                    <c:set var="i" value="${1}"/>
                  <c:forEach var="prod" items="${listProdm}" varStatus="st">
                    <li <c:if test="${i==1}"> class="uk-open"</c:if>>
                        <a class="uk-accordion-title" href="#">
                            <span class="uk-margin-small-right"><c:out value="${i}"/></span>
                            <img src="http://localhost:8080/jewelry/img/CT/<c:out value="${prod.id}"/>/1.jpg" alt="img" width="50"/>
                            <c:out value="${prod.name}"/> 
                        </a>
                        <div class="uk-accordion-content">
                            <div class="uk-accordion-content">
                                <h5><a href="product?id=${prod.id}" target="_blanc">Link do produktu</a></h5>
                                <input id="idProd${i}" name="product${i}" type="text" value="${prod.id}" hidden>
                                <div class="uk-width-1-1 uk-margin-medium-bottom">
                                    <i class="star${i}" value="1" uk-icon="icon: star; ratio: 2"></i>
                                    <i class="star${i}" value="2" uk-icon="icon: star; ratio: 2"></i>
                                    <i class="star${i}" value="3" uk-icon="icon: star; ratio: 2"></i>
                                    <i class="star${i}" value="4" uk-icon="icon: star; ratio: 2"></i>
                                    <i class="star${i}" value="5" uk-icon="icon: star; ratio: 2"></i>
                                </div>
                                <div class="uk-width-3-5 uk-margin-small-bottom">
                                    <div class="uk-form-controls">
                                        <input id="name${i}" name="name${i}" class="uk-input" placeholder="Tytuł recenzji..."
                                               type="text" maxlength="40"/>
                                    </div>
                                </div>
                                <input id="stars${i}" name="stars${i}" type="text" hidden>
                                <div class="uk-width-1-1">
                                    <div class="uk-form-controls">
                                        <textarea id="review" name="review${i}" class="uk-textarea" rows="5" cols="3"
                                                  placeholder="Wpisz swoje spostrzeżenia tutaj... "></textarea>
                                    </div>
                                </div>
                                <script>
                                    $('.star${i}').on('click', function(){
                                        $('.star${i}').removeClass('selected');
                                        var count = $(this).attr('value'); 
                                        for (var i=0; i<count; i++){        
                                            $('.star${i}').eq(i).addClass('selected');
                                        }
                                        $("#stars${i}").val(count.toString());
                                    });
                                </script>
                            </div>
                        </div>
                    </li>
                    <c:set var="i" value="${i+1}"/>
                  </c:forEach>
                </ul>
                <div class="uk-flex uk-flex-center uk-margin-medium-top">
                    <button class="uk-button uk-button-secondary" type="submit" name="quant" value="${i}">Potwierdź</button> 
                </div>
            </form>       <!--Niesamowity kolor kamieni i do tego dobra cena. Polecam-->  
        </section>
      </div>
    </main> 
   <!--FOOTER-->  
    <jsp:include page="../footer.jsp"/>
  </body>
</html>