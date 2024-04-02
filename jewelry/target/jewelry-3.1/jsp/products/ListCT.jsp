<%-- 
    Document   : ListCT
    Created on : 30 sie 2021, 11:35:42
    Author     : DRzepka
--%>

<%@ page trimDirectiveWhitespaces="true" %> <%--prevent nextLine in document--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>  
<!DOCTYPE html>
<html>
  <head>
      <jsp:include page="../head.jsp"/>
<!--TITLE-->    
    <title>Ametyst</title>
  </head>
  <body>
  <!--HEADER-->
    <jsp:include page="../header.jsp"/>
    <!--NAVIGATION-->
      <jsp:include page="../nav.jsp"/>
  <!--MAIN-->
    <main>    
      <div class="uk-margin" uk-grid>
      <!--ASIDE-->
        <jsp:include page="../aside.jsp"/>
      <!--ABOUT-->  
        <section class="uk-width-4-5@m uk-margin-medium">
            <ul class="uk-breadcrumb">
                <li><a href="home">Strona główna</a></li>
                
                <c:if test="${t==1}"><li class="uk-disabled"><a>${cat.name}</a></li></c:if>
                <c:if test="${t==2}">
                <li><a href="categories?id=${catname.id}">${catname.name}</a></li>
                <li class="uk-disabled"><a>${tag.name}</a></li>
                </c:if>
                <c:if test="${t==3}"><li class="uk-disabled"><a>Nowości</a></li></c:if>
                <c:if test="${t==4}"><li class="uk-disabled"><a>Promocje</a></li></c:if>
                <c:if test="${t==5}"><li class="uk-disabled"><a>Ponownie w sprzedaży</a></li></c:if>
            </ul>
            <h2>
                <c:if test="${t==1}"><c:out value="${cat.name}"/></c:if>
                <c:if test="${t==2}"><c:out value="${tag.name}"/></c:if>
                <c:if test="${t==3}">Nowości</c:if>
                <c:if test="${t==4}">Promocje</c:if>
                <c:if test="${t==5}">Ponownie w sprzedaży</c:if>
            </h2>
            <hr> 
            <div class="uk-margin-medium-bottom">
                <h5>
                <c:if test="${t==1}"><c:out value="${cat.desc}"/></c:if>
                <c:if test="${t==2}"><c:out value="${tag.desc}"/></c:if>
                </h5>
                
            <label class="uk-form-label uk-text-bold" for="firmCh">
                <h3><input id="filterCh" name="filterCh" class="uk-checkbox" type="checkbox"/> Filtrowanie</h3> <!-- checked-->
            </label>
            </div>
            <div uk-filter="target: .js-filter">
                <div id="fltHid" class="uk-grid-small uk-grid-divider uk-child-width-auto uk-hidden" uk-grid>
                    <div>
                        <ul class="uk-subnav uk-subnav-pill" uk-margin>
                            <li class="uk-active" uk-filter-control><a href="#">Wszystkie</a></li>
                        </ul>
                    </div>
                    <div>
                        <h3>Kolor</h3>
                        <ul class="uk-subnav uk-subnav-pill" uk-margin>
                         <c:forEach var="col" items="${listCol}">
                            <li uk-filter-control="filter: [data-color*='${col.id}']; group: data-color"><a href="#">${col.name}</a></li>
                         </c:forEach>
                        </ul>
                    </div>
                    <div>
                        <h3>Kształt</h3>
                        <ul class="uk-subnav uk-subnav-pill" uk-margin>
                         <c:forEach var="shp" items="${listShp}">
                            <li uk-filter-control="filter: [data-shape*='${shp.id}']; group: data-shape"><a href="#">${shp.name}</a></li>
                         </c:forEach>
                        </ul>
                    </div>
                </div>
                <div uk-grid uk-scrollspy="cls: uk-animation-fade; target: .uk-card; delay: 10; repeat: false">
                   <div class="uk-child-width-1-3@m js-filter" uk-grid="masonry: true">
                     <c:if test="${t==1}">
                       <c:forEach var="prod" items="${listProdC}">
                       <c:if test="${prod.quantityState!=0}">    
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
                                            <s><fmt:formatNumber type="number" maxFractionDigits="2" minFractionDigits="2" 
                                            value="${prod.price}"/></s>
                                            <fmt:formatNumber type="number" maxFractionDigits="2" minFractionDigits="2" 
                                            value="${Math.round((prod.price-(prod.discount/100)*prod.price)*100.0)/100.0}"/>&nbsp;zł
                                        </c:when>
                                        <c:otherwise>
                                            <fmt:formatNumber type="number" maxFractionDigits="2" minFractionDigits="2" 
                                            value="${prod.price}"/>&nbsp;zł
                                        </c:otherwise>
                                    </c:choose>
                                    </p>
                                </div>
                            </div>
                          </a>
                      </div>
                     </c:if> 
                      </c:forEach>
                     </c:if> 
                     <c:if test="${t==2}">
                      <c:forEach var="prod" items="${listProd}">
                       <c:if test="${prod.quantityState!=0}">  
                       <div>
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
                                            <s><fmt:formatNumber type="number" maxFractionDigits="2" minFractionDigits="2" 
                                            value="${prod.price}"/></s>
                                            <fmt:formatNumber type="number" maxFractionDigits="2" minFractionDigits="2" 
                                            value="${Math.round((prod.price-(prod.discount/100)*prod.price)*100.0)/100.0}"/>&nbsp;zł
                                        </c:when>
                                        <c:otherwise>
                                            <fmt:formatNumber type="number" maxFractionDigits="2" minFractionDigits="2" 
                                            value="${prod.price}"/>&nbsp;zł
                                        </c:otherwise>
                                    </c:choose>
                                    </p>
                                </div>
                            </div>
                          </a>
                      </div>
                     </c:if> 
                      </c:forEach>
                     </c:if> 
                     <c:if test="${t==3}">
                      <c:forEach var="prod" items="${listProdN}">
                       <c:if test="${prod.quantityState!=0}">  
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
                                            <s><fmt:formatNumber type="number" maxFractionDigits="2" minFractionDigits="2" 
                                            value="${prod.price}"/></s>
                                            <fmt:formatNumber type="number" maxFractionDigits="2" minFractionDigits="2" 
                                            value="${Math.round((prod.price-(prod.discount/100)*prod.price)*100.0)/100.0}"/>&nbsp;zł
                                        </c:when>
                                        <c:otherwise>
                                            <fmt:formatNumber type="number" maxFractionDigits="2" minFractionDigits="2" 
                                            value="${prod.price}"/>&nbsp;zł
                                        </c:otherwise>
                                    </c:choose>
                                    </p>
                                </div>
                            </div>
                          </a>
                      </div>
                     </c:if> 
                      </c:forEach>
                     </c:if> 
                     <c:if test="${t==4}">
                      <c:forEach var="prod" items="${listProdDc}">
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
                                            <s><fmt:formatNumber type="number" maxFractionDigits="2" minFractionDigits="2" 
                                            value="${prod.price}"/></s>
                                            <fmt:formatNumber type="number" maxFractionDigits="2" minFractionDigits="2" 
                                            value="${Math.round((prod.price-(prod.discount/100)*prod.price)*100.0)/100.0}"/>&nbsp;zł
                                        </c:when>
                                        <c:otherwise>
                                            <fmt:formatNumber type="number" maxFractionDigits="2" minFractionDigits="2" 
                                            value="${prod.price}"/>&nbsp;zł
                                        </c:otherwise>
                                    </c:choose>
                                    </p>
                                </div>
                            </div>
                          </a>
                      </div>
                     </c:if> 
                      </c:forEach>
                     </c:if> 
                     <c:if test="${t==5}">
                      <c:forEach var="prod" items="${listProdRs}">
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
                                            <s><fmt:formatNumber type="number" maxFractionDigits="2" minFractionDigits="2" 
                                            value="${prod.price}"/></s>
                                            <fmt:formatNumber type="number" maxFractionDigits="2" minFractionDigits="2" 
                                            value="${Math.round((prod.price-(prod.discount/100)*prod.price)*100.0)/100.0}"/>&nbsp;zł
                                        </c:when>
                                        <c:otherwise>
                                            <fmt:formatNumber type="number" maxFractionDigits="2" minFractionDigits="2" 
                                            value="${prod.price}"/>&nbsp;zł
                                        </c:otherwise>
                                    </c:choose>
                                    </p>
                                </div>
                            </div>
                          </a>
                      </div>
                     </c:if> 
                      </c:forEach>
                     </c:if>
                       <!--for only one result (proper display)-->
                       <div data-color="1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17 18 19 20 21 22 23 24 25 26 27 28 29 30
                            31 32 33 34 35 36 37 38 39 40 41 42 43 44 45 46 47 48 49 50 51 52 53 54 55 56 57 58 59 60" 
                            data-shape="1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17 18 19 20 21 22 23 24 25 26 27 28 29 30 
                            31 32 33 34 35 36 37 38 39 40 41 42 43 44 45 46 47 48 49 50 51 52 53 54 55 56 57 58 59 60">
                           <div class="uk-card"><div><img src=" " alt="" width="450"/></div><div></div></div></div>  
                   </div>
                </div>    
            </div>    
        </section>
      </div>
    </main> 
   <!--FOOTER-->  
    <jsp:include page="../footer.jsp"/>
  </body>
</html>

