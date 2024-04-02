<%-- 
    Document   : ListCT
    Created on : 30 sie 2021, 11:35:42
    Author     : DRzepka
--%>

<%@page import="java.util.List"%>
<%@page import="jwl.DAO.ProductMDAO"%>
<%@page import="jwl.model.ProductMeta"%>
<%@page trimDirectiveWhitespaces="true" %> <%--prevent nextLine in document--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
    ProductMDAO prodmDAO = new ProductMDAO(application.getInitParameter("jdbcURL"),
                                    application.getInitParameter("jdbcUserNL"),   
                                    application.getInitParameter("jdbcUserNLPassw"));
    List<ProductMeta> listProdIndex = prodmDAO.readIndexRand();
    request.setAttribute("listProdI", listProdIndex);
%>
<!DOCTYPE html>
<html>
  <head>
      <jsp:include page="jsp/head.jsp"/>
<!--TITLE-->    
    <title>Ametyst</title>
  </head>
  <body>
  <!--HEADER-->
  <jsp:include page="jsp/header.jsp"/>
    <!--NAVIGATION-->
      <jsp:include page="jsp/nav.jsp"/>
  <!--MAIN-->
    <main>      
      <div class="uk-margin" uk-grid>
      <!--ASIDE-->
        <jsp:include page="jsp/aside.jsp"/>
      <!--ABOUT-->  
        <section class="uk-width-4-5@m uk-margin-medium">
            <div class="uk-justify-center" uk-grid>
            <div class="uk-width-1-1 uk-text-center" style="width: 90vw;">
                <h2 class="uk-text-lead">Witaj wędrowcze!</h2>
                <p>Dziękujemy, że&nbsp;zawitałeś na naszą najnowszą stronę. Znajdziesz tu&nbsp;czego dusza pragnie do&nbsp;tworzenia różnorakiej biżuterii. Nie&nbsp;musisz się spieszyć z&nbsp;wyborem produktów (no&nbsp;chyba&nbsp;że jest go&nbsp;mało na&nbsp;stanie). 
                Usiądź więc wygodnie (najlepiej przy dobrej kawie) i&nbsp;zapoznaj się&nbsp;z&nbsp;naszym asortymentem. Jestem pewna, że&nbsp;wpadnie ci&nbsp;coś w&nbsp;oko.</p>
                <hr class="uk-divider-icon">
            </div>
            </div>
            <h2 class="uk-text-center">Proponowane produkty</h2>
                <div uk-grid uk-scrollspy="cls: uk-animation-fade; target: .uk-card; delay: 10; repeat: false">
                   <div class="uk-child-width-1-3@m js-filter" uk-grid="masonry: true">
                      <c:forEach var="prod" items="${listProdI}">
                       <c:if test="${(prod.quantityState!=0)}">  
                       <div data-color="${prod.idCol}" data-shape="${prod.idShap}">
                          <a href="product?id=<c:out value="${prod.id}"/>">
                            <div class="uk-card uk-card-hover">
                                <div class="uk-card-media-top">
                                    <img src="http://localhost:8080/jewelry/img/CT/<c:out value="${prod.id}"/>/1.jpg" alt="image" width="450">
                                </div>
                                <div class="uk-card-body">
                                    <h5 class="uk-card-title"><c:out value="${prod.name}"/></h5>
                                    <p>
                                    <c:choose>
                                        <c:when test = "${prod.discount!=0}">
                                            <s><c:out value="${prod.price}"/></s>
                                            <c:out value="${Math.round((prod.price-(prod.discount/100)*prod.price)*100.0)/100.0}"/>&nbsp;zł
                                        </c:when>
                                        <c:otherwise>
                                            <c:out value="${prod.price}"/>&nbsp;zł
                                        </c:otherwise>
                                    </c:choose>
                                    </p>
                                </div>
                            </div>
                          </a>
                      </div>
                     </c:if> 
                      </c:forEach>
                   </div>
                </div>    
        </section>
      </div>
    </main> 
   <!--FOOTER-->  
    <jsp:include page="jsp/footer.jsp"/>
  </body>
</html>

