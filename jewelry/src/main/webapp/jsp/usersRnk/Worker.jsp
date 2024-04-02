<%-- 
    Document   : indexRnk
    Created on : 14 wrz 2021, 10:50:39
    Author     : DRzepka
--%>

<%@ page trimDirectiveWhitespaces="true" %> <%--prevent nextLine in document--%>
<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<%
    request.setAttribute("t", 1);
%>
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
            <ul class="uk-switcher uk-margin switcher-worker1">
              <c:if test = "${rank==2}">
               <c:if test = "${t==1}">
                <c:forEach var="i" begin="1" end="${9}" step="1" varStatus ="status">
                <li>
                    <div uk-grid>
                        <div class="uk-width-4-5">
                            <div class="uk-inline uk-width-5-6 uk-align-right">
                                <a class="uk-form-icon" href="#" uk-icon="icon: search"></a>
                                <input class="uk-input search" type="text" placeholder="Szukaj.."/>
                            </div>
                            <div class="uk-width-1-3 uk-hidden@m uk-margin-small-right uk-text-right@m">
                                <div class="uk-text-right@xs"><a href="#menuRnk" uk-toggle uk-navbar-toggle-icon></a></div>
                            </div>
                        </div>
                    </div>
                    <label class="uk-form-label uk-text-bold uk-hidden@m" for="firmCh">
                        <h3><input class="filterCh uk-checkbox" type="checkbox"/> Filtrowanie</h3> <!-- checked-->
                    </label>
                    <div uk-filter="target: .js-filter${status.index}; animation: fade" style="margin-left: 0.2rem;">
                        <div class="fltHid uk-grid-small uk-flex-middle uk-hidden@m uk-hidden uk-margin-small-top" uk-grid>
                            <div class="uk-width-expand">
                                <div class="uk-grid-small uk-grid-divider" uk-grid>
                                    <div>
                                        <ul class="uk-subnav uk-subnav-pill uk-child-width-1-3 uk-text-center" uk-margin>
                                            <li><span>Id.</span></li>
                                            <li uk-filter-control="sort: data-id; order: desc"><a href="#" uk-icon="icon: arrow-down"></a></li>
                                            <li uk-filter-control="sort: data-id"><a href="#" uk-icon="icon: arrow-up"></a></li>
                                            <li><span>Nazwa</span></li>
                                            <li uk-filter-control="sort: data-sname; order: desc"><a href="#" uk-icon="icon: arrow-down"></a></li>
                                            <li uk-filter-control="sort: data-sname"><a href="#" uk-icon="icon: arrow-up"></a></li>
                                            <li><span>Ilość</span></li>
                                            <li uk-filter-control="sort: data-qnt; order: desc"><a href="#" uk-icon="icon: arrow-down"></a></li>
                                            <li uk-filter-control="sort: data-qnt"><a href="#" uk-icon="icon: arrow-up"></a></li>
                                            <li><span>Utworzenie</span></li>
                                            <li uk-filter-control="sort: data-date; order: desc"><a href="#" uk-icon="icon: arrow-down"></a></li>
                                            <li uk-filter-control="sort: data-date"><a href="#" uk-icon="icon: arrow-up"></a></li>
                                            <li><span>Aktualizacja</span></li>
                                            <li uk-filter-control="sort: data-update; order: desc"><a href="#" uk-icon="icon: arrow-down"></a></li>
                                            <li uk-filter-control="sort: data-update"><a href="#" uk-icon="icon: arrow-up"></a></li>
                                        </ul>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <table class="uk-table uk-table-hover uk-table-divider uk-table-responsive" 
                                style="position: relative;">
                            <thead> <!--  uk-sticky="sel-target: .uk-navbar-table; cls-active: uk-navbar-sticky"-->
                                <tr><!-- class="uk-navbar-table uk-margin-large-top"-->
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
                                            Nazwisko i imię
                                            <span uk-filter-control="sort: data-sname">
                                                <a class="uk-icon-link" href="" uk-icon="icon: arrow-down"></a>
                                            </span>
                                            <span uk-filter-control="sort: data-sname; order: desc">
                                                <a class="uk-icon-link" href="" uk-icon="icon: arrow-up"></a>
                                            </span>
                                        </div>
                                    </th>
                                    <th class="uk-width-small">
                                        <div class="uk-width-auto uk-text-nowrap">
                                            Ilość produktów
                                            <span uk-filter-control="sort: data-qnt">
                                                <a class="uk-icon-link" href="" uk-icon="icon: arrow-down"></a>
                                            </span>
                                            <span uk-filter-control="sort: data-qnt; order: desc">
                                                <a class="uk-icon-link" href="" uk-icon="icon: arrow-up"></a>
                                            </span>
                                        </div>
                                    </th>
                                    <th class="uk-width-small">
                                        <div class="uk-width-auto uk-text-nowrap">
                                            Data utworzenia
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
                                            Data aktualizacji
                                            <span uk-filter-control="sort: data-update">
                                                <a class="uk-icon-link" href="" uk-icon="icon: arrow-down"></a>
                                            </span>
                                            <span uk-filter-control="sort: data-update; order: desc">
                                                <a class="uk-icon-link" href="" uk-icon="icon: arrow-up"></a>
                                            </span>
                                        </div>
                                    </th>
                                </tr>
                            </thead>
                            <tbody class="js-filter${status.index}">
                                <c:forEach var="ord" items="${listOrder}" varStatus="stat">
                                <c:if test = "${ord.idStat==i}"> 
                                    <c:choose>
                                        <c:when test = "${ord.name==null}">
                                          <c:forEach var="usr" items="${listUser}" varStatus="sta">
                                            <c:if test = "${ord.idUserMeta.equals(usr.idMeta)}"> 
                                             <c:set var="name" value="${usr.name}"/>
                                             <c:set var="sname" value="${usr.surname}"/>
                                            </c:if>
                                          </c:forEach>
                                        </c:when>
                                        <c:otherwise>
                                             <c:set var="name" value="${ord.name}"/>
                                             <c:set var="sname" value="${ord.surname}"/>
                                        </c:otherwise>
                                    </c:choose> 
                                <tr 
                                    data-id="<c:out value="${ord.id}"/>" 
                                    data-sname="<c:out value="${sname}"/>" 
                                    data-qnt="<c:out value="${listQuantity[stat.index]}"/>" 
                                    data-date="<c:out value="${ord.create}"/>" 
                                    data-update="<c:out value="${ord.update}"/>"
                                >
                                    <td class="fl">
                                        <a href="viewOrder?id=<c:out value="${ord.id}"/>" target="_blank" uk-tooltip="Wyświetl">
                                            <c:out value="${ord.id}"/></a>
                                    </td>
                                    <td>
                                       <c:out value="${sname}"/>&nbsp;<c:out value="${name}"/>                             
                                    </td>
                                    <td><span class="uk-hidden@m">Ilość: </span><c:out value="${listQuantity[stat.index]}"/></td>
                                    <td><span class="uk-hidden@m">Utworzenie: </span><c:out value="${ord.create}"/></td>
                                    <td><span class="uk-hidden@m">Aktualizacja: </span><c:out value="${ord.update}"/></td>
                                </tr>
                                </c:if>
                                </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </li>
                </c:forEach>
               </c:if> 
              </c:if> 
            </ul>
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

