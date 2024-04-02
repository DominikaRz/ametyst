<%-- 
    Document   : indexRnk
    Created on : 14 wrz 2021, 10:50:39
    Author     : DRzepka
--%>

<%@ page trimDirectiveWhitespaces="true" %> <%--prevent nextLine in document--%>
<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
  <head>
    <jsp:include page="../head.jsp"/>
    <link rel="stylesheet" type="text/css" href="css/styleRnk.css"/>
    <link rel="stylesheet" href="css/vanillaSelectBox.css">
    <script src="js/vanillaSelectBox.js"></script>
    
<!--TITLE-->    
    <title>Ametyst - user</title>
  </head>
  <body>
  <!--MAIN-->
    <main>
      <div id="top" class="uk-margin" uk-grid>
        <jsp:include page="../asideRnk.jsp"/>
        <section class="uk-width-5-6 uk-margin-medium uk-align-center"><!---->
            <c:if test = "${rank==3}">
              <c:if test = "${t==1}">
                <li> 
                    <div uk-grid>
                        <div class="uk-width-4-5">
                            <div class="uk-inline uk-width-5-6 uk-align-right">
                                <a class="uk-form-icon" href="#" uk-icon="icon: search"></a>
                                <input class="uk-input search" type="text" placeholder="Szukaj.."/>
                            </div>
                            <div class="uk-width-1-1 uk-hidden@m uk-margin-small-right uk-text-right@m">
                                <div class="uk-text-right@xs"><a href="#menuRnk" uk-toggle uk-navbar-toggle-icon></a></div>
                            </div>
                        </div>
                    </div>
                    <label class="uk-form-label uk-text-bold uk-hidden@m" for="firmCh">
                        <h3><input class="filterCh uk-checkbox" type="checkbox"/> Filtrowanie</h3> <!-- checked-->
                    </label>
                    <div uk-filter="target: .js-filter1; animation: fade" style="margin-left: 0.2rem;">
                        <div class="fltHid uk-grid-small uk-flex-middle uk-hidden@m uk-hidden uk-margin-small-top" uk-grid>
                            <div class="uk-width-expand">
                                <div class="uk-grid-small uk-grid-divider" uk-grid>
                                    <div>
                                        <ul class="uk-subnav uk-subnav-pill uk-child-width-1-3 uk-text-center" uk-margin>
                                            <li><span>Id.</span></li>
                                            <li uk-filter-control="sort: data-id; order: desc"><a href="#" uk-icon="icon: arrow-down"></a></li>
                                            <li uk-filter-control="sort: data-id"><a href="#" uk-icon="icon: arrow-up"></a></li>
                                            <li><span>Gwiazdki</span></li>
                                            <li uk-filter-control="sort: data-stars; order: desc"><a href="#" uk-icon="icon: arrow-down"></a></li>
                                            <li uk-filter-control="sort: data-stars"><a href="#" uk-icon="icon: arrow-up"></a></li>
                                            <li><span>Produkt</span></li>
                                            <li uk-filter-control="sort: data-prod; order: desc"><a href="#" uk-icon="icon: arrow-down"></a></li>
                                            <li uk-filter-control="sort: data-prod"><a href="#" uk-icon="icon: arrow-up"></a></li>
                                            <li><span>Wystawienie</span></li>
                                            <li uk-filter-control="sort: data-date; order: desc"><a href="#" uk-icon="icon: arrow-down"></a></li>
                                            <li uk-filter-control="sort: data-date"><a href="#" uk-icon="icon: arrow-up"></a></li>
                                            <li><span>Odpowiedź</span></li>
                                            <li uk-filter-control="sort: data-answer; order: desc"><a href="#" uk-icon="icon: arrow-down"></a></li>
                                            <li uk-filter-control="sort: data-answer"><a href="#" uk-icon="icon: arrow-up"></a></li>
                                        </ul>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <table class="uk-table uk-table-hover uk-table-divider uk-table-responsive" 
                                style="position: relative;"> <!--uk-filter="target: .js-filter1; animation: fade"-->
                            <thead>
                                <tr>
                                    <th class="uk-width-small">
                                        <div class="uk-width-auto uk-text-nowrap">
                                            Id.
                                            <span uk-filter-control="sort: data-id">
                                                <a class="uk-icon-link" href="" uk-icon="icon: arrow-down"></a>
                                            </span>
                                            <span uk-filter-control="sort: data-id; order: desc">
                                                <a class="uk-icon-link" href="" uk-icon="icon: arrow-up"></a>
                                            </span>
                                        </div>
                                    </th>
                                    <th class="uk-width-small">
                                        <div class="uk-width-auto uk-text-nowrap">
                                            Stars
                                            <span uk-filter-control="sort: data-stars">
                                                <a class="uk-icon-link" href="" uk-icon="icon: arrow-down"></a>
                                            </span>
                                            <span uk-filter-control="sort: data-stars; order: desc">
                                                <a class="uk-icon-link" href="" uk-icon="icon: arrow-up"></a>
                                            </span>
                                        </div>
                                    </th>
                                    <th class="uk-width-small">
                                        <div class="uk-width-auto uk-text-nowrap">
                                            Produkt
                                            <span uk-filter-control="sort: data-prod">
                                                <a class="uk-icon-link" href="" uk-icon="icon: arrow-down"></a>
                                            </span>
                                            <span uk-filter-control="sort: data-prod; order: desc">
                                                <a class="uk-icon-link" href="" uk-icon="icon: arrow-up"></a>
                                            </span>
                                        </div>
                                    </th>
                                    <th class="uk-width-small">
                                        <div class="uk-width-auto uk-text-nowrap">
                                            Data wystawnienia
                                            <span uk-filter-control="sort: data-date">
                                                <a class="uk-icon-link" href="" uk-icon="icon: arrow-down"></a>
                                            </span>
                                            <span uk-filter-control="sort: data-date; order: desc">
                                                <a class="uk-icon-link" href="" uk-icon="icon: arrow-up"></a>
                                            </span>
                                        </div>
                                    </th>
                                    <th class="uk-width-small">
                                        <div class="uk-width-auto uk-text-nowrap">
                                            Data odpowiedzi
                                            <span uk-filter-control="sort: data-answer">
                                                <a class="uk-icon-link" href="" uk-icon="icon: arrow-down"></a>
                                            </span>
                                            <span uk-filter-control="sort: data-answer; order: desc">
                                                <a class="uk-icon-link" href="" uk-icon="icon: arrow-up"></a>
                                            </span>
                                        </div>
                                    </th>
                                </tr>
                            </thead>
                            <tbody class="js-filter1">
                               <c:forEach var="rev" items="${listRew}" varStatus="stat" > 
                                <tr
                                    data-id="<c:out value="${rev.id}"/>" 
                                    data-stars="<c:out value="${rev.stars}"/>" 
                                    data-prod="<c:out value="${rev.idProd}"/>" 
                                    data-date="<c:out value="${rev.publication}"/>" 
                                    data-answer="<c:out value="${rev.response}"/>"
                                >
                                    <td class="id_rev fl"><%----%>
                                        <a href="viewReview?id=<c:out value="${rev.id}"/>" target="_blank" uk-tooltip="Wyświetl"> 
                                            <c:out value="${rev.id}"/></a>.
                                    </td>
                                    <td>
                                        <c:forEach var="i" begin="1" end="${rev.stars}" step="1" varStatus ="status">
                                        <i uk-icon="star" class="prodstar" style="color: #ffff00;"></i>
                                        </c:forEach>                          
                                    </td>
                                    <td><span class="uk-hidden@m">Produkt: </span><a href="product?id=<c:out value="${rev.idProd}"/>"><c:out value="${rev.idProd}"/></a></td>
                                    <td><span class="uk-hidden@m">Publikacja: </span><c:out value="${rev.publication}"/></td>
                                    <td><span class="uk-hidden@m">Odpowiedź: </span><c:out value="${rev.response}"/></td>
                                </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </li>
              </c:if>
              <c:if test = "${t==2}">
                <li>
                    <div uk-grid>
                        <div class="uk-width-4-5">
                            <div class="uk-inline uk-width-5-6 uk-align-right">
                                <a class="uk-form-icon" href="#" uk-icon="icon: search"></a>
                                <input class="uk-input search" type="text" placeholder="Szukaj.."/>
                            </div>
                            <div class="uk-width-1-1 uk-hidden@m uk-margin-small-right uk-text-right@m">
                                <div class="uk-text-right@xs"><a href="#menuRnk" uk-toggle uk-navbar-toggle-icon></a></div>
                            </div>
                        </div>
                    </div>
                    <label class="uk-form-label uk-text-bold uk-hidden@m" for="firmCh">
                        <h3><input class="filterCh uk-checkbox" type="checkbox"/> Filtrowanie</h3> <!-- checked-->
                    </label>
                    <div uk-filter="target: .js-filter2; animation: fade" style="margin-left: 0.2rem;">
                        <div class="fltHid uk-grid-small uk-flex-middle uk-hidden@m uk-hidden uk-margin-small-top" uk-grid>
                            <div class="uk-width-expand">
                                <div class="uk-grid-small uk-grid-divider" uk-grid>
                                    <div>
                                        <ul class="uk-subnav uk-subnav-pill uk-child-width-1-3 uk-text-center" uk-margin>
                                            <li><span>Id.</span></li>
                                            <li uk-filter-control="sort: data-id; order: desc"><a href="#" uk-icon="icon: arrow-down"></a></li>
                                            <li uk-filter-control="sort: data-id"><a href="#" uk-icon="icon: arrow-up"></a></li>
                                            <li><span>Nazwa</span></li>
                                            <li uk-filter-control="sort: data-name; order: desc"><a href="#" uk-icon="icon: arrow-down"></a></li>
                                            <li uk-filter-control="sort: data-name"><a href="#" uk-icon="icon: arrow-up"></a></li>
                                        </ul>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <table class="uk-table uk-table-hover uk-table-divider uk-table-responsive" 
                                style="position: relative;">
                            <thead>
                                <tr>
                                    <th class="uk-width-small">
                                        <div class="uk-width-auto uk-text-nowrap">
                                            Id.
                                            <span uk-filter-control="sort: data-id">
                                                <a class="uk-icon-link" href="" uk-icon="icon: arrow-down"></a>
                                            </span>
                                            <span uk-filter-control="sort: data-id; order: desc">
                                                <a class="uk-icon-link" href="" uk-icon="icon: arrow-up"></a>
                                            </span>
                                        </div>
                                    </th>
                                    <th class="uk-width-medium">
                                        <div class="uk-width-auto uk-text-nowrap">
                                            Nazwa
                                            <span uk-filter-control="sort: data-name">
                                                <a class="uk-icon-link" href="" uk-icon="icon: arrow-down"></a>
                                            </span>
                                            <span uk-filter-control="sort: data-name; order: desc">
                                                <a class="uk-icon-link" href="" uk-icon="icon: arrow-up"></a>
                                            </span>
                                        </div>
                                    </th>
                                    <th class="uk-width-large">
                                        <div class="uk-width-auto uk-text-nowrap">
                                            Opis
                                        </div>
                                    </th>
                                </tr>
                            </thead>
                            <tbody class="js-filter2">
                               <c:forEach var="prod" items="${listProd}" varStatus="stat" > 
                                <tr 
                                    data-id="<c:out value="${prod.id}"/>" 
                                    data-name="<c:out value="${prod.name}"/>" 
                                >
                                    <td class="fl">
                                        <a href="viewProduct?id=<c:out value="${prod.id}"/>" target="_blank" uk-tooltip="Wyświetl">
                                            <c:out value="${prod.id}"/>.</a>
                                    </td>
                                    <td>
                                       <img src="http://localhost:8080/jewelry/img/CT/<c:out value="${prod.id}"/>/1.jpg" alt="img" width="50"/>
                                       <a href="product?id=${prod.id}"><c:out value="${prod.name}"/></a>                          
                                    </td>
                                    <td class="uk-text-truncate"><c:out value="${prod.description}"/></td>
                                </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </li>
              </c:if>
              <c:if test = "${t==3}">
                <li>
                    <div uk-grid>
                        <div class="uk-width-4-5">
                            <div class="uk-inline uk-width-5-6 uk-align-right">
                                <a class="uk-form-icon" href="#" uk-icon="icon: search"></a>
                                <input class="uk-input search" type="text" placeholder="Szukaj.."/>
                            </div>
                            <div class="uk-width-1-1 uk-hidden@m uk-margin-small-right uk-text-right@m">
                                <div class="uk-text-right@xs"><a href="#menuRnk" uk-toggle uk-navbar-toggle-icon></a></div>
                            </div>
                        </div>
                    </div>
                    <label class="uk-form-label uk-text-bold uk-hidden@m" for="firmCh">
                        <h3><input class="filterCh uk-checkbox" type="checkbox"/> Filtrowanie</h3> <!-- checked-->
                    </label>
                    <div uk-filter="target: .js-filter3; animation: fade" style="margin-left: 0.2rem;">
                        <div class="fltHid uk-grid-small uk-flex-middle uk-hidden@m uk-hidden uk-margin-small-top" uk-grid>
                            <div class="uk-width-expand">
                                <div class="uk-grid-small uk-grid-divider" uk-grid>
                                    <div>
                                        <ul class="uk-subnav uk-subnav-pill uk-child-width-1-3 uk-text-center" uk-margin>
                                            <li><span>Id.</span></li>
                                            <li uk-filter-control="sort: data-id; order: desc"><a href="#" uk-icon="icon: arrow-down"></a></li>
                                            <li uk-filter-control="sort: data-id"><a href="#" uk-icon="icon: arrow-up"></a></li>
                                            <li><span>Nazwa</span></li>
                                            <li uk-filter-control="sort: data-name; order: desc"><a href="#" uk-icon="icon: arrow-down"></a></li>
                                            <li uk-filter-control="sort: data-name"><a href="#" uk-icon="icon: arrow-up"></a></li>
                                        </ul>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <table class="uk-table uk-table-hover uk-table-divider uk-table-responsive" 
                                style="position: relative;">
                        <thead>
                            <tr>
                                <th class="uk-width-small">
                                    <div class="uk-width-auto uk-text-nowrap">
                                        Id.
                                        <span uk-filter-control="sort: data-id">
                                            <a class="uk-icon-link" href="" uk-icon="icon: arrow-down"></a>
                                        </span>
                                        <span uk-filter-control="sort: data-id; order: desc">
                                            <a class="uk-icon-link" href="" uk-icon="icon: arrow-up"></a>
                                        </span>
                                    </div>
                                </th>
                                <th class="uk-width-small">
                                    <div class="uk-width-auto uk-text-nowrap">
                                        Nazwa
                                        <span uk-filter-control="sort: data-name">
                                            <a class="uk-icon-link" href="" uk-icon="icon: arrow-down"></a>
                                        </span>
                                        <span uk-filter-control="sort: data-name; order: desc">
                                            <a class="uk-icon-link" href="" uk-icon="icon: arrow-up"></a>
                                        </span>
                                    </div>
                                </th>
                                <th class="uk-width-large">
                                    <div class="uk-width-auto uk-text-nowrap">Opis </div>
                                </th>
                            </tr>
                        </thead>
                        <tbody class="js-filter3">
                           <c:forEach var="cat" items="${listCat}" varStatus="stat" > 
                            <tr 
                                data-id="<c:out value="${cat.id}"/>" 
                                data-name="<c:out value="${cat.name}"/>" 
                                data-desc="<c:out value="${cat.desc}"/>" 
                            >
                                <td class="fl">
                                    <a href="viewCategory?id=<c:out value="${cat.id}"/>" target="_blank" uk-tooltip="Wyświetl">
                                        <c:out value="${cat.id}"/></a>
                                </td>
                                <td><c:out value="${cat.name}"/> </td>
                                <td class="uk-text-truncate" style="white-space: nowrap !important; overflow: hidden !important; 
                                    text-overflow: ellipsis;"><c:out value="${cat.desc}"/></td>
                            </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </li>
              </c:if>
              <c:if test = "${t==4}">
                <li>
                    <div uk-grid>
                        <div class="uk-width-4-5">
                            <div class="uk-inline uk-width-5-6 uk-align-right">
                                <a class="uk-form-icon" href="#" uk-icon="icon: search"></a>
                                <input class="uk-input search" type="text" placeholder="Szukaj.."/>
                            </div>
                            <div class="uk-width-1-1 uk-hidden@m uk-margin-small-right uk-text-right@m">
                                <div class="uk-text-right@xs"><a href="#menuRnk" uk-toggle uk-navbar-toggle-icon></a></div>
                            </div>
                        </div>
                    </div>
                    <label class="uk-form-label uk-text-bold uk-hidden@m" for="firmCh">
                        <h3><input class="filterCh uk-checkbox" type="checkbox"/> Filtrowanie</h3> <!-- checked-->
                    </label>
                    <div uk-filter="target: .js-filter4; animation: fade" style="margin-left: 0.2rem;">
                        <div class="fltHid uk-grid-small uk-flex-middle uk-hidden@m uk-hidden uk-margin-small-top" uk-grid>
                            <div class="uk-width-expand">
                                <div class="uk-grid-small uk-grid-divider" uk-grid>
                                    <div>
                                        <ul class="uk-subnav uk-subnav-pill uk-child-width-1-3 uk-text-center" uk-margin>
                                            <li><span>Id.</span></li>
                                            <li uk-filter-control="sort: data-id; order: desc"><a href="#" uk-icon="icon: arrow-down"></a></li>
                                            <li uk-filter-control="sort: data-id"><a href="#" uk-icon="icon: arrow-up"></a></li>
                                            <li><span>Nazwa</span></li>
                                            <li uk-filter-control="sort: data-name; order: desc"><a href="#" uk-icon="icon: arrow-down"></a></li>
                                            <li uk-filter-control="sort: data-name"><a href="#" uk-icon="icon: arrow-up"></a></li>
                                        </ul>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <table class="uk-table uk-table-hover uk-table-divider uk-table-responsive" 
                                style="position: relative;">
                        <thead>
                            <tr>
                                <th class="uk-width-small">
                                    <div class="uk-width-auto uk-text-nowrap">
                                        Id.
                                        <span uk-filter-control="sort: data-id">
                                            <a class="uk-icon-link" href="" uk-icon="icon: arrow-down"></a>
                                        </span>
                                        <span uk-filter-control="sort: data-id; order: desc">
                                            <a class="uk-icon-link" href="" uk-icon="icon: arrow-up"></a>
                                        </span>
                                    </div>
                                </th>
                                <th class="uk-width-small">
                                    <div class="uk-width-auto uk-text-nowrap">
                                        Nazwa
                                        <span uk-filter-control="sort: data-name">
                                            <a class="uk-icon-link" href="" uk-icon="icon: arrow-down"></a>
                                        </span>
                                        <span uk-filter-control="sort: data-name; order: desc">
                                            <a class="uk-icon-link" href="" uk-icon="icon: arrow-up"></a>
                                        </span>
                                    </div>
                                </th>
                                <th class="uk-width-large">
                                    <div class="uk-width-auto uk-text-nowrap">Opis</div>
                                </th>
                            </tr>
                        </thead>
                        <tbody class="js-filter4">
                           <c:forEach var="tag" items="${listTag}" varStatus="stat" > 
                            <tr 
                                data-id="<c:out value="${tag.id}"/>" 
                                data-name="<c:out value="${tag.name}"/>" 
                                data-desc="<c:out value="${tag.desc}"/>" 
                            >
                                <td class="fl">
                                    <a href="viewTag?id=<c:out value="${tag.id}"/>" target="_blank" uk-tooltip="Wyświetl">
                                        <c:out value="${tag.id}"/></a>
                                </td>
                                <td><c:out value="${tag.name}"/> </td>
                                <td class="uk-text-truncate" style="white-space: nowrap !important; overflow: hidden !important; 
                                    text-overflow: ellipsis;"><c:out value="${tag.desc}"/></td>
                            </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </li>
              </c:if>
            </c:if>
        <script>
            $(document).ready(function(){
              $(".search").on("keyup", function() {
                var value = $(this).val().toLowerCase();
                $(".uk-table tr").filter(function() {
                  $(this).toggle($(this).text().toLowerCase().indexOf(value) > -1);
                });
              });
            });
            //source: https://www.w3schools.com/bootstrap/bootstrap_filters.asp
        </script>
        </section>
      </div>
    </main> 
  </body>
</html>

