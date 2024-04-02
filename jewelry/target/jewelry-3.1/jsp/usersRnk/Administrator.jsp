<%-- 
    Document   : indexRnk
    Created on : 14 wrz 2021, 10:50:39
    Author     : DRzepka
--%>

<%@ page trimDirectiveWhitespaces="true" %> <%--prevent nextLine in document--%>
<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page errorPage="error" %>
<!DOCTYPE html>
<html>
  <head>
    <jsp:include page="../head.jsp"/>
    <jsp:include page="../form.jsp"/>
    <link rel="stylesheet" type="text/css" href="css/styleRnk.css"/>                    <%--ścieżka relaywna! Nie potzreba powracania do katalogu głównego--%>
    <link rel="stylesheet" type="text/css" href="css/vanillaSelectBox.css">
    <script src="js/vanillaSelectBox.js"></script>
<!--TITLE-->    
    <title>Administrator</title>
  </head>
  <body>
  <!--MAIN-->
    <main>
      <div id="top" class="uk-margin" uk-grid>
        <jsp:include page="../asideRnk.jsp"/>
        <section class="uk-width-5-6 uk-margin-medium uk-align-center">
         <c:if test = "${rank==1}">
           <c:if test = "${t==1}">
            <ul class="uk-switcher uk-margin switcher-admin1">
                <li>
                    <div uk-grid>
                        <div class="uk-width-4-5@m uk-width-5-6">
                            <div class="uk-inline uk-width-5-6 uk-align-right">
                                <a class="uk-form-icon" href="#" uk-icon="icon: search"></a>
                                <input class="uk-input search" type="text" placeholder="Szukaj.."/>
                            </div>
                        </div>
                        <div class="uk-width-1-5@m uk-width-1-2 uk-text-right@m">
                            <div class="uk-text-center@xs"><a href="#" uk-icon="icon: plus; ratio: 1.5" 
                                   uk-tooltip="Dodaj" uk-toggle="target: #modal1_1_add" onclick="getId0(0, 1, 1)"></a></div>
                        </div>
                        <div class="uk-width-1-3 uk-hidden@m uk-margin-small-right uk-text-right@m">
                            <div class="uk-text-right@xs"><a href="#menuRnk" uk-toggle uk-navbar-toggle-icon></a></div>
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
                                            <li><span>Nazwa</span></li>
                                            <li uk-filter-control="sort: data-name; order: desc"><a href="#" uk-icon="icon: arrow-down"></a></li>
                                            <li uk-filter-control="sort: data-name"><a href="#" uk-icon="icon: arrow-up"></a></li>
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
                                            <span uk-filter-control="sort: data-id; order: desc">
                                                <a class="uk-icon-link" href="" uk-icon="icon: arrow-down"></a>
                                            </span>
                                            <span uk-filter-control="sort: data-id">
                                                <a class="uk-icon-link" href="" uk-icon="icon: arrow-up"></a>
                                            </span>
                                        </div>
                                    </th>
                                    <th class="uk-width-large">
                                        <div class="uk-width-auto uk-text-nowrap">
                                            Nazwa
                                            <span uk-filter-control="sort: data-name; order: desc">
                                                <a class="uk-icon-link" href="" uk-icon="icon: arrow-down"></a>
                                            </span>
                                            <span uk-filter-control="sort: data-name">
                                                <a class="uk-icon-link" href="" uk-icon="icon: arrow-up"></a>
                                            </span>
                                        </div>
                                    </th>
                                    <th class="uk-width-small">Akcje</th>
                                </tr>
                            </thead>
                            <tbody class="js-filter1">
                              <c:forEach var="col" items="${listColor}">
                                <tr 
                                    data-id="${col.id}" data-name="${col.name}" 
                                >
                                    <td class="fl"><c:out value="${col.id}"/>.</td>
                                    <td><c:out value="${col.name}"/></td>
                                    <td>
                                        <span class="uk-hidden@m">Akcje: </span>
                                        <a uk-icon="pencil" uk-tooltip="Edytuj" uk-toggle="target: #modal1_1" onclick="getId0(${col.id}, 1, 0)"></a>
                                        <a href="deleteDictionary?idMod=<c:out value='${col.id}'/>&t=1" 
                                           uk-icon="close" uk-tooltip="Usuń" class="uk-margin-small-left"></a>
                                    </td>
                                </tr>
                              </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </li>
                <li>
                    <div uk-grid>
                        <div class="uk-width-4-5">
                            <div class="uk-inline uk-width-5-6 uk-align-right">
                                <a class="uk-form-icon" href="#" uk-icon="icon: search"></a>
                                <input class="uk-input search" type="text" placeholder="Szukaj.."/>
                            </div>
                        </div>
                        <div class="uk-width-1-5@m uk-width-1-2 uk-text-right@m">
                            <div class="uk-text-center@xs"><a href="#" uk-icon="icon: plus; ratio: 1.5" 
                                   uk-tooltip="Dodaj" uk-toggle="target: #modal1_1_add" onclick="getId0(0, 2, 1)"></a></div>
                        </div>
                        <div class="uk-width-1-3 uk-hidden@m uk-margin-small-right uk-text-right@m">
                            <div class="uk-text-right@xs"><a href="#menuRnk" uk-toggle uk-navbar-toggle-icon></a></div>
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
                                            <span uk-filter-control="sort: data-id; order: desc">
                                                <a class="uk-icon-link" href="" uk-icon="icon: arrow-down"></a>
                                            </span>
                                            <span uk-filter-control="sort: data-id">
                                                <a class="uk-icon-link" href="" uk-icon="icon: arrow-up"></a>
                                            </span>
                                        </div>
                                    </th>
                                    <th class="uk-width-large">
                                        <div class="uk-width-auto uk-text-nowrap">
                                            Nazwa
                                            <span uk-filter-control="sort: data-name; order: desc">
                                                <a class="uk-icon-link" href="" uk-icon="icon: arrow-down"></a>
                                            </span>
                                            <span uk-filter-control="sort: data-name">
                                                <a class="uk-icon-link" href="" uk-icon="icon: arrow-up"></a>
                                            </span>
                                        </div>
                                    </th>
                                    <th class="uk-width-small">Akcje</th>
                                </tr>
                            </thead>
                            <tbody class="js-filter2">
                                <c:forEach var="shp" items="${listShpe}">
                                    <tr 
                                        data-id="${shp.id}" data-name="${shp.name}"
                                    >  
                                        <td class="fl"><c:out value="${shp.id}" />.</td>
                                        <td><c:out value="${shp.name}" /></td>
                                        <td>
                                            <span class="uk-hidden@m">Akcje: </span>
                                            <a uk-icon="pencil" uk-tooltip="Edytuj"  uk-toggle="target: #modal1_1" onclick="getId0(${shp.id}, 2, 0)"></a>
                                                <a href="deleteDictionary?idMod=<c:out value='${shp.id}'/>&t=2" 
                                                uk-icon="close" uk-tooltip="Usuń" class="uk-margin-small-left"></a>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </li> 
                <li>
                    <div uk-grid>
                        <div class="uk-width-4-5">
                            <div class="uk-inline uk-width-5-6 uk-align-right">
                                <a class="uk-form-icon" href="#" uk-icon="icon: search"></a>
                                <input class="uk-input search" type="text" placeholder="Szukaj.."/>
                            </div>
                        </div>
                        <div class="uk-width-1-5@m uk-width-1-2 uk-text-right@m">
                            <div class="uk-text-center@xs"><a href="#" uk-icon="icon: plus; ratio: 1.5" 
                                   uk-tooltip="Dodaj" uk-toggle="target: #modal1_1_add" onclick="getId0(0, 3, 1)"></a></div>
                        </div>
                        <div class="uk-width-1-3 uk-hidden@m uk-margin-small-right uk-text-right@m">
                            <div class="uk-text-right@xs"><a href="#menuRnk" uk-toggle uk-navbar-toggle-icon></a></div>
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
                                            <span uk-filter-control="sort: data-id; order: desc">
                                                <a class="uk-icon-link" href="" uk-icon="icon: arrow-down"></a>
                                            </span>
                                            <span uk-filter-control="sort: data-id">
                                                <a class="uk-icon-link" href="" uk-icon="icon: arrow-up"></a>
                                            </span>
                                        </div>
                                    </th>
                                    <th class="uk-width-large">
                                        <div class="uk-width-auto uk-text-nowrap">
                                            Nazwa
                                            <span uk-filter-control="sort: data-name; order: desc">
                                                <a class="uk-icon-link" href="" uk-icon="icon: arrow-down"></a>
                                            </span>
                                            <span uk-filter-control="sort: data-name">
                                                <a class="uk-icon-link" href="" uk-icon="icon: arrow-up"></a>
                                            </span>
                                        </div>
                                    </th>
                                    <th class="uk-width-small">Akcje</th>
                                </tr>
                            </thead>
                            <tbody class="js-filter3">
                                <c:forEach var="fbr" items="${listFbric}">
                                    <tr 
                                        data-id="${fbr.id}" data-name="${fbr.name}" 
                                    >        
                                        <td class="fl"><c:out value="${fbr.id}" />.</td>
                                        <td><c:out value="${fbr.name}" /></td>
                                        <td>
                                            <span class="uk-hidden@m">Akcje: </span>
                                            <a uk-icon="pencil" uk-tooltip="Edytuj" uk-toggle="target: #modal1_1" onclick="getId0(${fbr.id}, 3, 0)"></a>
                                                <a href="deleteDictionary?idMod=<c:out value='${fbr.id}'/>&t=3" 
                                                uk-icon="close" uk-tooltip="Usuń" class="uk-margin-small-left"></a>
                                        </td> 
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </li>
                <li>
                    <div uk-grid>
                        <div class="uk-width-4-5">
                            <div class="uk-inline uk-width-5-6 uk-align-right">
                                <a class="uk-form-icon" href="#" uk-icon="icon: search"></a>
                                <input class="uk-input search" type="text" placeholder="Szukaj.."/>
                            </div>
                        </div>
                        <div class="uk-width-1-5@m uk-width-1-2 uk-text-right@m">
                            <div class="uk-text-center@xs"><a href="#" uk-icon="icon: plus; ratio: 1.5" 
                                   uk-tooltip="Dodaj" uk-toggle="target: #modal1_1_add" onclick="getId0(0, 4, 1)"></a></div>
                        </div>
                        <div class="uk-width-1-3 uk-hidden@m uk-margin-small-right uk-text-right@m">
                            <div class="uk-text-right@xs"><a href="#menuRnk" uk-toggle uk-navbar-toggle-icon></a></div>
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
                                            <li><span>Cena</span></li>
                                            <li uk-filter-control="sort: data-price; order: desc"><a href="#" uk-icon="icon: arrow-down"></a></li>
                                            <li uk-filter-control="sort: data-price"><a href="#" uk-icon="icon: arrow-up"></a></li>
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
                                            <span uk-filter-control="sort: data-id; order: desc">
                                                <a class="uk-icon-link" href="" uk-icon="icon: arrow-down"></a>
                                            </span>
                                            <span uk-filter-control="sort: data-id">
                                                <a class="uk-icon-link" href="" uk-icon="icon: arrow-up"></a>
                                            </span>
                                        </div>
                                    </th>
                                    <th class="uk-width-large">
                                        <div class="uk-width-auto uk-text-nowrap">
                                            Nazwa
                                            <span uk-filter-control="sort: data-name; order: desc">
                                                <a class="uk-icon-link" href="" uk-icon="icon: arrow-down"></a>
                                            </span>
                                            <span uk-filter-control="sort: data-name">
                                                <a class="uk-icon-link" href="" uk-icon="icon: arrow-up"></a>
                                            </span>
                                        </div>
                                    </th>
                                    <th class="uk-width-medium">
                                        <div class="uk-width-auto uk-text-nowrap">
                                            Cena
                                            <span uk-filter-control="sort: data-price; order: desc">
                                                <a class="uk-icon-link" href="" uk-icon="icon: arrow-down"></a>
                                            </span>
                                            <span uk-filter-control="sort: data-price">
                                                <a class="uk-icon-link" href="" uk-icon="icon: arrow-up"></a>
                                            </span>
                                        </div>
                                    </th>
                                    <th class="uk-width-small">Akcje</th>
                                </tr>
                            </thead>
                            <tbody class="js-filter4">
                                <c:forEach var="del" items="${listDelivery}">
                                    <tr 
                                        data-id="${del.id}" data-name="${del.name}"  data-price="${del.price}"
                                    >
                                        <td class="fl"><c:out value="${del.id}" />.</td>
                                        <td><c:out value="${del.name}" /></td>
                                        <td><c:out value="${del.price}" /> zł</td>
                                        <td>
                                            <span class="uk-hidden@m">Akcje: </span>
                                            <a uk-icon="pencil" uk-tooltip="Edytuj" uk-toggle="target: #modal1_1" onclick="getId0(${del.id}, 4, 0)"></a>
                                                <a href="deleteDictionary?idMod=<c:out value='${del.id}'/>&t=4" 
                                                uk-icon="close" uk-tooltip="Usuń" class="uk-margin-small-left"></a>
                                        </td> 
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </li>
                <li>
                    <div uk-grid>
                        <div class="uk-width-4-5">
                            <div class="uk-inline uk-width-5-6 uk-align-right">
                                <a class="uk-form-icon" href="#" uk-icon="icon: search"></a>
                                <input class="uk-input search" type="text" placeholder="Szukaj.."/>
                            </div>
                        </div>
                        <div class="uk-width-1-5@m uk-width-1-2 uk-text-right@m">
                            <div class="uk-text-center@xs"><a href="#" uk-icon="icon: plus; ratio: 1.5" 
                                   uk-tooltip="Dodaj" uk-toggle="target: #modal1_1_add" onclick="getId0(0, 5, 1)"></a></div>
                        </div>
                        <div class="uk-width-1-3 uk-hidden@m uk-margin-small-right uk-text-right@m">
                            <div class="uk-text-right@xs"><a href="#menuRnk" uk-toggle uk-navbar-toggle-icon></a></div>
                        </div>
                    </div>
                    <label class="uk-form-label uk-text-bold uk-hidden@m" for="firmCh">
                        <h3><input class="filterCh uk-checkbox" type="checkbox"/> Filtrowanie</h3> <!-- checked-->
                    </label>
                    <div uk-filter="target: .js-filter5; animation: fade" style="margin-left: 0.2rem;">
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
                                            <li><span>Cena</span></li>
                                            <li uk-filter-control="sort: data-price; order: desc"><a href="#" uk-icon="icon: arrow-down"></a></li>
                                            <li uk-filter-control="sort: data-price"><a href="#" uk-icon="icon: arrow-up"></a></li>
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
                                    <th class="uk-width-medium">
                                        <div class="uk-width-auto uk-text-nowrap">
                                            Kategoria
                                            <span uk-filter-control="sort: data-cat">
                                                <a class="uk-icon-link" href="" uk-icon="icon: arrow-down"></a>
                                            </span>
                                            <span uk-filter-control="sort: data-cat; order: desc">
                                                <a class="uk-icon-link" href="" uk-icon="icon: arrow-up"></a>
                                            </span>
                                        </div>
                                    </th>
                                    <th class="uk-width-small">Akcje</th>
                                </tr>
                            </thead>
                            <tbody class="js-filter5">
                                <c:forEach var="pay" items="${listPayment}">
                                    <tr 
                                        data-id="${pay.id}" data-name="${pay.name}" data-cat="${pay.category}"
                                    >  
                                        <td class="fl"><c:out value="${pay.id}" />.</td>
                                        <td><c:out value="${pay.name}" /></td>
                                        <td><c:out value="${pay.category}" /></td>
                                        <td>
                                            <span class="uk-hidden@m">Akcje: </span>
                                            <a uk-icon="pencil" uk-tooltip="Edytuj" uk-toggle="target: #modal1_1" onclick="getId0(${pay.id}, 5, 0)"></a>
                                                <a href="deleteDictionary?idMod=<c:out value='${pay.id}'/>&t=5" 
                                                uk-icon="close" uk-tooltip="Usuń" class="uk-margin-small-left"></a>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </li>
                <li>
                    <div uk-grid>
                        <div class="uk-width-4-5">
                            <div class="uk-inline uk-width-5-6 uk-align-right">
                                <a class="uk-form-icon" href="#" uk-icon="icon: search"></a>
                                <input class="uk-input search" type="text" placeholder="Szukaj.."/>
                            </div>
                        </div>
                        <div class="uk-width-1-5@m uk-width-1-2 uk-text-right@m">
                            <div class="uk-text-center@xs"><a href="#" uk-icon="icon: plus; ratio: 1.5" 
                                   uk-tooltip="Dodaj" uk-toggle="target: #modal1_1_add" onclick="getId0(0, 6, 1)"></a></div>
                        </div>
                        <div class="uk-width-1-3 uk-hidden@m uk-margin-small-right uk-text-right@m">
                            <div class="uk-text-right@xs"><a href="#menuRnk" uk-toggle uk-navbar-toggle-icon></a></div>
                        </div>
                    </div>
                    <label class="uk-form-label uk-text-bold uk-hidden@m" for="firmCh">
                        <h3><input class="filterCh uk-checkbox" type="checkbox"/> Filtrowanie</h3> <!-- checked-->
                    </label>
                    <div uk-filter="target: .js-filter6; animation: fade" style="margin-left: 0.2rem;">
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
                                            <li><span>Aktywność</span></li>
                                            <li uk-filter-control="sort: data-act; order: desc"><a href="#" uk-icon="icon: arrow-down"></a></li>
                                            <li uk-filter-control="sort: data-act"><a href="#" uk-icon="icon: arrow-up"></a></li>
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
                                <th class="uk-width-large">
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
                                <th class="uk-width-small">
                                    <div class="uk-width-auto uk-text-nowrap">
                                        Aktywny?
                                        <span uk-filter-control="sort: data-act">
                                            <a class="uk-icon-link" href="" uk-icon="icon: arrow-down"></a>
                                        </span>
                                        <span uk-filter-control="sort: data-act; order: desc">
                                            <a class="uk-icon-link" href="" uk-icon="icon: arrow-up"></a>
                                        </span>
                                    </div>
                                </th>
                                <th class="uk-width-small">Wysokość</th>
                                <th class="uk-width-small">Akcje</th>
                            </tr>
                        </thead>
                        <tbody class="js-filter6">
                            <c:forEach var="disc" items="${listDiscount}">
                                <tr 
                                    data-id="${disc.id}" data-name="${disc.name}" data-act="${disc.active}"
                                >  
                                    <td class="fl"><c:out value="${disc.id}" />.</td>
                                    <td><c:out value="${disc.name}" /></td>
                                    <td><c:out value="${disc.active}" /></td>
                                    <td><c:out value="${disc.value}" />%</td>
                                    <td>
                                        <span class="uk-hidden@m">Akcje: </span>
                                        <a uk-icon="pencil" uk-tooltip="Edytuj" uk-toggle="target: #modal1_1" onclick="getId0(${disc.id}, 6, 0)"></a>
                                            <a href="deleteDictionary?idMod=<c:out value='${disc.id}'/>&t=6" 
                                            uk-icon="close" uk-tooltip="Usuń" class="uk-margin-small-left"></a>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </li>
            </ul>
            <div id="modal1_1" uk-modal>
                <div class="uk-modal-dialog uk-modal-body">
                    <button class="uk-modal-close-outside" type="button" uk-close></button>
                    <div class="uk-modal-header"><h2 class="uk-modal-title"><span id="title_modal1"></span></h2></div>
                    <form class="uk-form-stacked" id="ThisFormModal1" method="POST" action="" accept-charset="ISO-8859-1">
                        <div class="uk-margin-medium-top" uk-grid>
                            <div id="nameS1" class="uk-width-2-3@m"> 
                                <h4 class="uk-text-bold">Nazwa:</h4> 
                                <input id="name1" name="name" class="uk-input" value="" type="text"/>
                            </div>                              
                            <div id="priceS1" class="uk-width-1-3@m"> 
                                <h4 class="uk-text-bold">Cena:</h4> 
                                <input id="price1" name="price" class="uk-input" value="" type="number" step="0.01"/>
                            </div>                              
                            <div id="categoryS1" class="uk-width-2-3@m"> 
                                <h4 class="uk-text-bold">Kategoria:</h4> 
                                <input id="category1" name="category" class="uk-input" value="" type="text"/>
                            </div>                              
                            <div id="activeS1" class="uk-width-1-3@m"> 
                                <h4 class="uk-text-bold">Aktywny?:</h4> 
                                <select id="active1" name="active" class="uk-select">
                                    <option value="true">tak</option>
                                    <option value="false">nie</option>
                                </select>
                            </div>
                            <div class="uk-width-1-6@m uk-margin-medium-top">
                                <button id="idMod" name="idMod" value="" 
                                        class="uk-button uk-button-secondary" type="submit">Zmień</button> 
                            </div>
                        </div>
                    </form>
                </div>
            </div>
            <script>
                function getId0(id1, t, a){
                        var ids_col = [<c:forEach var="col" items="${listColor}"><c:out value="${col.id}"/>, </c:forEach>0];
                        var ids_del = [<c:forEach var="del" items="${listDelivery}"><c:out value="${del.id}"/>, </c:forEach>0];
                        var ids_fab = [<c:forEach var="fab" items="${listFbric}"><c:out value="${fab.id}"/>, </c:forEach>0];
                        var ids_pay = [<c:forEach var="pay" items="${listPayment}"><c:out value="${pay.id}"/>, </c:forEach>0];
                        var ids_shp = [<c:forEach var="shp" items="${listShpe}"><c:out value="${shp.id}"/>, </c:forEach>0];
                        var ids_dis = [<c:forEach var="dis" items="${listDiscount}"><c:out value="${dis.id}"/>, </c:forEach>];
                        var names_col = [<c:forEach var="col" items="${listColor}">"<c:out value="${col.name}"/>", </c:forEach>""];
                        var names_del = [<c:forEach var="del" items="${listDelivery}">"<c:out value="${del.name}"/>", </c:forEach>""];
                        var price_del = [<c:forEach var="del" items="${listDelivery}"><c:out value="${del.price}"/>, </c:forEach>0];
                        var names_fab = [<c:forEach var="fab" items="${listFbric}">"<c:out value="${fab.name}"/>", </c:forEach>""];
                        var names_pay = [<c:forEach var="pay" items="${listPayment}">"<c:out value="${pay.name}"/>", </c:forEach>""];
                        var categories_pay = [<c:forEach var="pay" items="${listPayment}">"<c:out value="${pay.category}"/>", </c:forEach>""];
                        var names_shp = [<c:forEach var="shp" items="${listShpe}">"<c:out value="${shp.name}"/>", </c:forEach>""];
                        var names_dis = [<c:forEach var="dis" items="${listDiscount}">"<c:out value="${dis.name}"/>", </c:forEach>""];
                        var valuess_dis = [<c:forEach var="dis" items="${listDiscount}">"<c:out value="${dis.value}"/>", </c:forEach>""];
                        var actives_dis = [<c:forEach var="dis" items="${listDiscount}"><c:out value="${dis.active}"/>, </c:forEach>false];
                        $("#name_modal").val("".toString());

                        switch(t){
                            case 1: //kolor
                                if(a===1){ //add
                                    $("#priceS").addClass('uk-hidden');
                                    $("#categoryS").addClass('uk-hidden');
                                    $("#activeS").addClass('uk-hidden');
                                    $("#valuesS").addClass('uk-hidden');
                                    $("#title_modal2").html("Dodawanie nowego koloru".toString());
                                    $('#ThisFormModal1a').attr('action', ('insertDictionary?t=1')); //? what t 
                                } 
                                else{
                                    for(var i=0; i<ids_col.length; i++){
                                        if(ids_col[i]===id1){
                                            $("#priceS1").addClass('uk-hidden');
                                            $("#categoryS1").addClass('uk-hidden');
                                            $("#activeS1").addClass('uk-hidden');
                                            $("#title_modal1").html("Edycja koloru".toString());
                                            $("#name1").val(names_col[i].toString());
                                            $("#idMod").val(id1.toString());
                                            $('#ThisFormModal1').attr('action', ("updateDictionary?&t=1"));
                                        }
                                    }
                                } 
                             break;
                            case 2: //kształt 
                                if(a===1){ //add
                                    $("#priceS").addClass('uk-hidden');
                                    $("#categoryS").addClass('uk-hidden');
                                    $("#activeS").addClass('uk-hidden');
                                    $("#valuesS").addClass('uk-hidden');
                                    $("#title_modal2").html("Dodawanie nowego kształtu".toString());
                                    $('#ThisFormModal1a').attr('action', ('insertDictionary?t=2')); 
                                }
                                else{ //edit
                                    for(var i=0; i<ids_shp.length; i++){
                                        if(ids_shp[i]===id1){
                                            $("#priceS1").addClass('uk-hidden');
                                            $("#categoryS1").addClass('uk-hidden');
                                            $("#activeS1").addClass('uk-hidden');
                                            $("#title_modal1").html("Edycja kształtu".toString());
                                            $("#name1").val(names_shp[i].toString());
                                            $("#idMod").val(id1.toString());
                                            $('#ThisFormModal1').attr('action', ("updateDictionary?t=2"));
                                        }
                                    } 
                                }  
                             break;
                            case 3: //materiał 
                                if(a===1){ //add
                                    $("#priceS").addClass('uk-hidden');
                                    $("#categoryS").addClass('uk-hidden');
                                    $("#activeS").addClass('uk-hidden');
                                    $("#valuesS").addClass('uk-hidden');
                                    $("#title_modal2").html("Dodawanie nowego materiału".toString());
                                    $('#ThisFormModal1a').attr('action', ('insertDictionary?t=3')); 
                                }
                                else{ //edit
                                    for(var i=0; i<ids_fab.length; i++){
                                        if(ids_fab[i]===id1){
                                            $("#priceS1").addClass('uk-hidden');
                                            $("#categoryS1").addClass('uk-hidden');
                                            $("#activeS1").addClass('uk-hidden');
                                            $("#title_modal1").html("Edycja materiału".toString());
                                            $("#name1").val(names_fab[i].toString());
                                            $("#idMod").val(id1.toString());
                                            $('#ThisFormModal1').attr('action', ("updateDictionary?t=3"));
                                        }
                                    } 
                                } 
                             break;
                            case 4: //dostawa 
                                if(a===1){ //add
                                    $("#priceS").removeClass('uk-hidden');
                                    $("#categoryS").addClass('uk-hidden');
                                    $("#activeS").addClass('uk-hidden');
                                    $("#valuesS").addClass('uk-hidden');
                                    $("#title_modal2").html("Dodawanie nowej metody dostawy".toString());
                                    $('#ThisFormModal1a').attr('action', ('insertDictionary?t=4')); 
                                }
                                else{ //edit
                                    for(var i=0; i<ids_del.length; i++){
                                        if(ids_del[i]===id1){
                                            $("#priceS1").removeClass('uk-hidden');
                                            $("#categoryS1").addClass('uk-hidden');
                                            $("#activeS1").addClass('uk-hidden');
                                            $("#title_modal1").html("Edycja metody dostawy".toString());
                                            $("#name1").val(names_del[i].toString());
                                            $("#price1").val(price_del[i].toString());
                                            $("#idMod").val(id1.toString());
                                            $('#ThisFormModal1').attr('action', ("updateDictionary?t=4"));
                                        }
                                    } 
                                } 
                             break;
                            case 5: //płatność 
                                if(a===1){ //add
                                    $("#priceS").addClass('uk-hidden');
                                    $("#categoryS").removeClass('uk-hidden');
                                    $("#activeS").addClass('uk-hidden');
                                    $("#valuesS").addClass('uk-hidden');
                                    $("#title_modal2").html("Dodawanie nowej metody płatności".toString());
                                    $('#ThisFormModal1a').attr('action', ('insertDictionary?t=5')); 
                                }
                                else{ //edit
                                    for(var i=0; i<ids_pay.length; i++){
                                        if(ids_pay[i]===id1){
                                            $("#priceS1").addClass('uk-hidden');
                                            $("#categoryS1").removeClass('uk-hidden');
                                            $("#activeS1").addClass('uk-hidden');
                                            $("#title_modal1").html("Edycja metody płatności".toString());
                                            $("#name1").val(names_pay[i].toString());
                                            $("#category1").val(categories_pay[i].toString());
                                            $("#idMod").val(id1.toString());
                                            $('#ThisFormModal1').attr('action', ("updateDictionary?t=5"));
                                        }
                                    } 
                                }  
                             break;
                            case 6: //kody promocyjne  
                                if(a===1){ //add
                                    $("#priceS").addClass('uk-hidden');
                                    $("#categoryS").addClass('uk-hidden');
                                    $("#activeS").removeClass('uk-hidden');
                                    $("#valuesS").removeClass('uk-hidden');
                                    $("#title_modal2").html("Dodawanie nowego kodu promocyjnego".toString());
                                    $('#ThisFormModal1a').attr('action', ('insertDictionary?t=6')); 
                                }
                                else{ //edit
                                    for(var i=0; i<ids_dis.length; i++){
                                        if(ids_dis[i]===id1){
                                            $("#priceS1").addClass('uk-hidden');
                                            $("#categoryS1").addClass('uk-hidden');
                                            $("#activeS1").removeClass('uk-hidden');
                                            $("#title_modal1").html("Edycja kodu promocyjnego".toString());
                                            $("#name1").val(names_dis[i].toString());
                                            $("#active1").val(actives_dis[i].toString());
                                            $("#nameS1").addClass('uk-hidden');
                                            $("#idMod").val(id1.toString());
                                            $('#ThisFormModal1').attr('action', ("updateDictionary?t=6"));
                                        }
                                    } 
                                } 
                             break;
                        }
                            
                            
                    }
            </script>
            <div id="modal1_1_add" uk-modal>
                <div class="uk-modal-dialog uk-modal-body">
                    <button class="uk-modal-close-outside" type="button" uk-close></button>
                    <div class="uk-modal-header"><h2 class="uk-modal-title"><span id="title_modal2"></span></h2></div>
                    <form class="uk-form-stacked" id="ThisFormModal1a" method="POST" action="" accept-charset="ISO-8859-1">
                        <div class="uk-margin-medium-top" uk-grid>
                            <div class="uk-width-2-3@m"> 
                                <h4 class="uk-text-bold">Nazwa:</h4> 
                                <input id="name" name="name" class="uk-input" value="" type="text"/>
                            </div>                              
                            <div id="priceS" class="uk-width-1-3@m"> 
                                <h4 class="uk-text-bold">Cena:</h4> 
                                <input id="price" name="price" class="uk-input" value="" type="number" step="0.01"/>
                            </div>                               
                            <div id="valuesS" class="uk-width-1-3@m"> 
                                <h4 class="uk-text-bold">Wielkość zniżki (%):</h4> 
                                <input id="values" name="values" class="uk-input" value="" type="number" step="1"/>
                            </div>                              
                            <div id="categoryS" class="uk-width-2-3@m"> 
                                <h4 class="uk-text-bold">Kategoria:</h4> 
                                <input id="category" name="category" class="uk-input" value="" type="text"/>
                            </div>                              
                            <div id="activeS" class="uk-width-1-3@m"> 
                                <h4 class="uk-text-bold">Aktywny?:</h4> 
                                <select id="active" name="active" value="true" class="uk-select">
                                    <option value="true">tak</option>
                                    <option value="false">nie</option>
                                </select>
                            </div>                            
                            <div class="uk-width-1-6@m uk-margin-medium-top">
                                <button class="uk-button uk-button-secondary" type="submit">Dodaj</button> 
                            </div>
                        </div>
                    </form>
                </div>
            </div>
            </c:if>
            <c:if test = "${t==2}">
                <div uk-grid>
                    <div class="uk-width-4-5">
                        <div class="uk-inline uk-width-5-6 uk-align-right">
                            <a class="uk-form-icon" href="#" uk-icon="icon: search"></a>
                            <input class="uk-input search" type="text" placeholder="Szukaj.."/>
                        </div>
                    </div>
                    <div class="uk-width-1-5@m uk-width-1-2 uk-text-right@m">
                        <div class="uk-text-center@xs"><a href="#" uk-icon="icon: plus; ratio: 1.5" 
                               uk-tooltip="Dodaj" uk-toggle="target: #modal2_add"></a></div>
                    </div>
                    <div class="uk-width-1-3 uk-hidden@m uk-margin-small-right uk-text-right@m">
                        <div class="uk-text-right@xs"><a href="#menuRnk" uk-toggle uk-navbar-toggle-icon></a></div>
                    </div>
                </div>
                <label class="uk-form-label uk-text-bold uk-hidden@m" for="firmCh">
                    <h3><input class="filterCh uk-checkbox" type="checkbox"/> Filtrowanie</h3> <!-- checked-->
                </label>
                <div uk-filter="target: .js-filter7; animation: fade" style="margin-left: 0.2rem;">
                    <div class="fltHid uk-grid-small uk-flex-middle uk-hidden@m uk-hidden uk-margin-small-top" uk-grid>
                        <div class="uk-width-expand">
                            <div class="uk-grid-small uk-grid-divider" uk-grid>
                                <div>
                                    <ul class="uk-subnav uk-subnav-pill uk-child-width-1-3 uk-text-center" uk-margin>
                                        <li><span>Id.</span></li>
                                        <li uk-filter-control="sort: data-id; order: desc"><a href="#" uk-icon="icon: arrow-down"></a></li>
                                        <li uk-filter-control="sort: data-id"><a href="#" uk-icon="icon: arrow-up"></a></li>
                                        <li><span>Ranga</span></li>
                                        <li uk-filter-control="sort: data-rnk; order: desc"><a href="#" uk-icon="icon: arrow-down"></a></li>
                                        <li uk-filter-control="sort: data-rnk"><a href="#" uk-icon="icon: arrow-up"></a></li>
                                        <li><span>Nazwa</span></li>
                                        <li uk-filter-control="sort: data-name; order: desc"><a href="#" uk-icon="icon: arrow-down"></a></li>
                                        <li uk-filter-control="sort: data-name"><a href="#" uk-icon="icon: arrow-up"></a></li>
                                        <li><span>Mail</span></li>
                                        <li uk-filter-control="sort: data-mail; order: desc"><a href="#" uk-icon="icon: arrow-down"></a></li>
                                        <li uk-filter-control="sort: data-mail"><a href="#" uk-icon="icon: arrow-up"></a></li>
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
                                        Ranga
                                        <span uk-filter-control="sort: data-rnk">
                                            <a class="uk-icon-link" href="" uk-icon="icon: arrow-down"></a>
                                        </span>
                                        <span uk-filter-control="sort: data-rnk; order: desc">
                                            <a class="uk-icon-link" href="" uk-icon="icon: arrow-up"></a>
                                        </span>
                                    </div>
                                </th>
                                <th class="uk-width-small">
                                    <div class="uk-width-auto uk-text-nowrap">
                                        Nazwisko i imię
                                        <span uk-filter-control="sort: data-name">
                                            <a class="uk-icon-link" href="" uk-icon="icon: arrow-down"></a>
                                        </span>
                                        <span uk-filter-control="sort: data-name; order: desc">
                                            <a class="uk-icon-link" href="" uk-icon="icon: arrow-up"></a>
                                        </span>
                                    </div>
                                </th>
                                <th class="uk-width-medium">
                                    <div class="uk-width-auto uk-text-nowrap">
                                        Mail
                                        <span uk-filter-control="sort: data-mail">
                                            <a class="uk-icon-link" href="" uk-icon="icon: arrow-down"></a>
                                        </span>
                                        <span uk-filter-control="sort: data-mail; order: desc">
                                            <a class="uk-icon-link" href="" uk-icon="icon: arrow-up"></a>
                                        </span>
                                    </div>
                                </th>
                                <th class="uk-width-small">Telefon</th>
                                <th class="uk-width-small">Akcje</th>
                            </tr>
                        </thead>
                        <tbody class="js-filter7">
                            <c:set var="i" value="1"/>
                            <c:forEach var="usr" items="${listUsers}" varStatus="stat">
                                <tr 
                                    data-id="${usr.id}" data-usr="${usr.user}" data-rnk="${listRank[stat.index].id}"
                                    data-name="${usr.surname} ${usr.name}"data-mail="${usr.email}" 
                                    data-reg="${usr.regist_date}"
                                >  
                                    <td class="fl"><a href="usersHistory?id=<c:out value="${usr.id}"/>" target="_blank" uk-tooltip="Wyświetl"> 
                                        ${i}</a>
                                    </td>
                                    <td class="uk-text-bold@xs"><c:if test="${usr.rank==true}">${listRank[stat.index].name}</c:if> <%--${}--%>
                                        <c:if test="${usr.rank==false}">Użytkownik</c:if></td>
                                    <td><span uk-icon="user" class="uk-hidden@m"> </span> <c:out value="${usr.surname}" />&nbsp;<c:out value="${usr.name}" /></td>
                                    <td><span class="uk-hidden@m">@: </span><c:out value="${usr.email}" /></td>
                                    <td><c:out value="${usr.tel}" /></td>
                                    <td>
                                      <c:if test = "${history.get(stat.index)!=true}"><%----%>
                                        <span class="uk-hidden@m">Usuń: </span>
                                        <a href="deleteUser?id=${usr.id}" 
                                            uk-icon="close" uk-tooltip="Usuń" class="uk-margin-small-left"></a>
                                      </c:if>  <%----%>
                                    </td>
                                </tr>
                                <c:set var="i" value="${i+1}"/>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
                <div id="modal2_add" uk-modal>
                  <div class="uk-modal-dialog uk-modal-body">
                    <button class="uk-modal-close-outside" type="button" uk-close></button>
                    <div class="uk-modal-header"><h2 class="uk-modal-title">Dodawanie nowego użytkownika</span></h2></div>
                    <div align="center" class="uk-margin-medium-top">
                        <form class="uk-form-stacked" id="ThisFormRegAdmin" method="POST" action="addUsers" accept-charset="ISO-8859-1">
                            <div class="uk-grid-small" uk-grid>
                                <div class="uk-width-1-5@m">
                                    <div class="uk-form-controls">
                                        <input id="usr_isusr" name="usr_isusr" class="uk-checkbox" type="checkbox"/> Użytkownik?
                                    </div>
                                </div>
                                <div class="uk-width-2-5@m">
                                    <div class="uk-form-controls">
                                        <select id="usr_rnk" name="usr_rnk" class="uk-select">
                                            <!--<option value="0">Brak</option>-->
                                            <c:forEach var="rank" items="${listRnk}">
                                                   <option value="<c:out value="${rank.id}"/>">
                                                       <c:out value="${rank.name}"/>
                                                   </option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                </div>
                                <div class="uk-width-1-5@m">
                                    <div class="uk-form-controls">
                                        <input id="usr_news" name="usr_news" class="uk-checkbox" type="checkbox"/> Newsletter?
                                    </div>
                                </div>
                            </div>
                            <div class="uk-grid-small" uk-grid>
                                <div class="uk-width-1-5@m uk-width-1-2">
                                    <div class="uk-form-controls">
                                       <input id="usr_name" name="usr_name" class="uk-input" placeholder="Imię..."
                                               type="text" />
                                    </div>
                                </div>
                                <div class="uk-width-1-5@m uk-width-1-2">
                                    <div class="uk-form-controls">
                                       <input id="usr_sname" name="usr_sname" class="uk-input" placeholder="Nazwisko..."
                                               type="text" />
                                    </div>
                                </div>
                                <div id="mail" class="uk-width-2-5@m">
                                    <div class="uk-form-controls">
                                       <input id="usr_emali" name="usr_emali" class="uk-input" placeholder="Email..."
                                               type="text" />
                                    </div>
                                </div>
                                <div class="uk-width-1-5@m">
                                    <div class="uk-form-controls">
                                       <input id="usr_phone" name="usr_phone" class="uk-input" placeholder="Telefon..."
                                               type="text" />
                                    </div>
                                </div>
                            </div>
                            <div class="uk-grid-small uk-align-center" uk-grid>
                                <div class="uk-width-1-3@m">
                                    <label class="uk-form-label" for="form-stacked-text">Hasło:</label>
                                    <div class="uk-form-controls">
                                       <input id="usr_pass" name="usr_pass" class="uk-input" type="text" 
                                              placeholder="Hasło..."/>
                                    </div>
                                </div>
                                <div class="uk-width-1-3@m">
                                    <div class="uk-form-controls">
                                        <input id="generete" name="generete" class="uk-checkbox" type="checkbox"/> Wygeneruj hasło
                                    </div>
                                </div>
                            </div>
                            <div class="uk-flex uk-flex-center uk-margin-medium-top" >
                               <button id="idUsr" name="idUsr" class="uk-button uk-button-secondary"
                                         type="submit" value="">Potwierdź</button> 
                           </div>
                        </form>
                    </div>   
                  </div>
                </div>
            </c:if>
            <c:if test = "${t==3}">
                <style> 
                    .uk-nav-header:not(:first-child){ margin-top: 0; }
                    ul.uk-nav-sub{ padding: 5px 15px 25px 0;}
                    .uk-h5, h5{ margin: 0.4rem;}
                    .uk-h3{ font-size: 1.4rem;}
                    h4 a:hover, a:hover{ color: silver;}
                    .uk-nav-default .uk-nav-sub a{ color: black !important;}
                    .icL {float: left; cursor: pointer; margin-top: 0.4rem;}
                </style>
                <div class="uk-width-1-1 uk-hidden@m  uk-text-right@m">
                    <div class="uk-text-right@xs uk-margin-medium-right"><a href="#menuRnk" uk-toggle uk-navbar-toggle-icon></a></div>
                </div>
                <p class="uk-text-danger uk-text-center"><span uk-icon="info"></span>
                    Nowo dodana część menu będzie widoczna dla użytkownika po dodaniu pierwszego tagu do danej kategorii w grupie</p>
                <ul class="uk-nav-default uk-nav-parent-icon" uk-nav>
                <c:set var="ex" value="${1}"/>
                <c:set var="siz" value="${1}"/>
                <c:forEach var="CT" items="${listCatTag}" varStatus="stat">
                  <c:if test = "${CT.idGr != listCatTag[stat.index-1].idGr}">
                    <c:if test = "${CT.idGr != 1}">
                    <li class="uk-nav-divider uk-margin"></li>
                    <c:set var="ex" value="${CT.idGr}"/>
                    <c:set var="siz" value="${siz+1}"/>
                    </c:if> 
                    <div class="uk-nav-header">
                        <h2 class="uk-margin-small-top uk-text-uppercase">
                            <c:out value="${CT.name}"/></h2>
                    </div>
                  </c:if>
                  <c:if test = "${CT.idCat != listCatTag[stat.index-1].idCat}">
                    <li class="uk-parent">
                        <span class="icL uk-margin-small-right" uk-icon="icon: pencil; ratio: 1.4" 
                              uk-toggle="target: #modal1_3" onclick="getId1(${CT.idCat}, 0, 2, 0)"
                              uk-tooltip="title: Edytuj; pos: top-left"></span>
                        <a href="#" class="uk-h3 uk-nav-header">
                            <c:out value="${CT.nameCat}"/></a>
                        <ul class="uk-nav-sub uk-column-1-4@l uk-column-1-2@m">
                            <li>
                                <h4><a href="" uk-toggle="target: #modal1_3" onclick="getId1(0, ${CT.idCat}, 3, 1)">
                                    <span class="uk-margin-small-right uk-icon-button" 
                                          uk-icon="icon: plus"></span>Dodaj tag
                                </a></h4>
                            </li> 
                  </c:if>
                            <li>
                                <h5 uk-tooltip="title: Edytuj; pos: top-left">
                                    <a href="" uk-toggle="target: #modal1_3" onclick="getId1(${CT.idTag}, 0, 3, 0)">
                                    <span class="uk-margin-small-right" 
                                          uk-icon="icon: pencil"></span><c:out value="${CT.nameTag}"/>
                                </a></h5>
                            </li> 
                  <c:if test = "${listCatTag[stat.index+1].idCat != CT.idCat}">
                        </ul>
                    </li>
                  </c:if>  
                  <c:if test = "${CT.idGr != listCatTag[stat.index+1].idGr}"> 
                   <c:forEach var="cat" items="${listCategories}" varStatus="stat">
                    <c:if test = "${CT.idGr == cat.idGr}"> 
                    <li class="uk-parent">
                        <span class="icL uk-margin-small-right" uk-icon="icon: pencil; ratio: 1.4" 
                              uk-toggle="target: #modal1_3" onclick="getId1(${cat.id}, 0, 2, 0)"
                              uk-tooltip="title: Edytuj; pos: top-left"></span>
                        <a href="#" class="uk-h3 uk-nav-header">
                            <c:out value="${cat.name}"/></a>
                        <ul class="uk-nav-sub uk-column-1-4@l uk-column-1-2@m">
                            <li>
                                <h4><a href="" uk-toggle="target: #modal1_3" onclick="getId1(0, ${cat.id}, 3, 1)">
                                    <span class="uk-margin-small-right uk-icon-button" 
                                          uk-icon="icon: plus"></span>Dodaj tag
                                </a></h4>
                            </li> 
                        </ul>
                    </li>
                    </c:if> 
                   </c:forEach>
                    <li class="uk-nav-header">
                        <h4><a href="" uk-toggle="target: #modal1_3" onclick="getId1(0, ${CT.idGr}, 2, 1)">
                            <span class="uk-margin-small-right uk-icon-button" 
                                  uk-icon="icon: plus"></span>Dodaj kategorię
                        </a></h4>
                    </li>
                  </c:if> 
                </c:forEach>
                <c:if test = "${listGroup.size() > siz}">
                  <c:forEach var="cat" items="${listCategories}" varStatus="stat">
                   <c:if test = "${cat.idGr > ex}">
                    <c:if test = "${(cat.idGr != listCategories[stat.index-1].idGr)}">
                    <c:if test = "${cat.idGr != 1}">
                    <li class="uk-nav-divider uk-margin"></li>
                    <c:set var="ex" value="${cat.idGr}"/>
                    </c:if>
                    <div class="uk-nav-header">
                        <h2 class="uk-margin-small-top uk-text-uppercase">
                            <c:forEach var="gr" items="${listGroup}" varStatus="stat">
                                <c:if test = "${cat.idGr == gr.id}">
                            <c:out value="${gr.name}"/></h2></c:if>
                            </c:forEach>
                    </div>
                  </c:if>
                    <li class="uk-parent">
                        <span class="icL uk-margin-small-right" uk-icon="icon: pencil; ratio: 1.4" 
                              uk-toggle="target: #modal1_3" onclick="getId1(${cat.id}, 0, 2, 0)"
                              uk-tooltip="title: Edytuj; pos: top-left"></span>
                        <a href="#" class="uk-h3 uk-nav-header">
                            <c:out value="${cat.name}"/></a>
                        <ul class="uk-nav-sub uk-column-1-4@l uk-column-1-2@m">
                            <li>
                                <h4><a href="" uk-toggle="target: #modal1_3" onclick="getId1(0, ${cat.id}, 3, 1)">
                                    <span class="uk-margin-small-right uk-icon-button" 
                                          uk-icon="icon: plus"></span>Dodaj tag
                                </a></h4>
                            </li> 
                        </ul>
                    </li>
                  <c:if test = "${cat.idGr != listCategories[stat.index+1].idGr}">
                    <li class="uk-nav-header">
                        <h4><a href="" uk-toggle="target: #modal1_3" onclick="getId1(0, ${cat.idGr}, 2, 1)">
                            <span class="uk-margin-small-right uk-icon-button" 
                                  uk-icon="icon: plus"></span>Dodaj kategorię
                        </a></h4>
                    </li>
                  </c:if> 
                  </c:if> 
                 </c:forEach>
                </c:if>
                    <li class="uk-nav-divider uk-margin"></li>
                    <div class="uk-nav-header">
                        <h2 class="uk-margin-small-top uk-text-uppercase">
                            <a uk-toggle="target: #modal1_3" onclick="getId1(0, 0, 1, 1)">
                                <span class="uk-margin-small-right uk-icon-button" 
                                      uk-icon="icon: plus"></span>Dodaj grupę
                            </a>
                        </h2>
                    </div>
                </ul>
                <script>
                    function getId1(id1, id2, t, a){
                        var ids_cat = [<c:forEach var="cat" items="${listCatTag}"><c:if test="${cat.idCat!=siz}">${cat.idCat}, </c:if><c:set var="siz" value="${cat.idCat}"/></c:forEach><c:forEach var="cats" items="${listCategories}">${cats.id}, </c:forEach>0];
                        var ids_tag = [<c:forEach var="tag" items="${listCatTag}"><c:out value="${tag.idTag}"/>, </c:forEach>0];
                        var ids_gr = [<c:forEach var="gr" items="${listGroup}"><c:out value="${gr.id}"/>, </c:forEach>0];
                        var names_cat = [<c:forEach var="cat" items="${listCatTag}"><c:if test="${cat.idCat!=size}">"${cat.nameCat}", </c:if><c:set var="size" value="${cat.idCat}"/></c:forEach><c:forEach var="cats" items="${listCategories}">"${cats.name}", </c:forEach>""];
                        var names_tag = [<c:forEach var="tag" items="${listCatTag}">"<c:out value="${tag.nameTag}"/>", </c:forEach>""];
                        var names_gr = [<c:forEach var="gr" items="${listGroup}">"<c:out value="${gr.name}"/>", </c:forEach>""];
                        $("#name_modal").val("".toString());

                        switch(t){
                            case 1: //gr 
                                for(var i=0; i<ids_gr.length; i++){
                                    if(ids_gr[i]===id1){
                                        if(a===1){ //add
                                            $("#select_ct").addClass('uk-hidden');
                                            $("#name_modal2").removeClass('uk-hidden');
                                            $("#title_modal").html("Dodawanie nowej grupy".toString());
                                            $('#ThisFormModal1_3').attr('action', ('addGroup')); //? what t 
                                        }
                                        /*
                                        else{ //edit
                                            $("#name_modal2").addClass('uk-hidden');
                                            $("#select_ct").addClass('uk-hidden');
                                            $("#title_modal").html("Edycja grupy".toString());
                                            $("#name_modal").val(names_gr[i].toString());
                                            $('#ThisFormModal1_3').attr('action', ('edit?gr='+ids_gr[i]+"&id=19")); //? what t 
                                        }*/
                                    } 
                                } 
                             break;
                            case 2: //cat 
                                for(var i=0; i<ids_cat.length; i++){
                                    if(ids_cat[i]===id1){
                                        if(a===1){ //add
                                            $("#select_ct").addClass('uk-hidden');
                                            $("#name_modal2").addClass('uk-hidden');
                                            $("#title_modal").html("Dodawanie nowej kategorii".toString());
                                            $('#ThisFormModal1_3').attr('action', ('addCategory?gr='+id2)); 
                                        }
                                        else{ //edit
                                            $("#select_ct").addClass('uk-hidden');
                                            $("#name_modal2").addClass('uk-hidden');
                                            $("#title_modal").html("Edycja kategorii".toString());
                                            $("#name_modal").val(names_cat[i].toString());
                                            $('#ThisFormModal1_3').attr('action', ('editCategory?cat='+ids_cat[i]));  
                                        }
                                    } 
                                } 
                             break;
                            case 3: //tag 
                                for(var i=0; i<ids_tag.length; i++){
                                    if(ids_tag[i]===id1){
                                        if(a===1){ //add
                                            $("#name_modal2").addClass('uk-hidden');
                                            $("#select_ct").removeClass('uk-hidden');
                                            $("#title_modal").html("Dodawanie nowego tagu".toString());
                                            $('#ThisFormModal1_3').attr('action', ('addTag?cat='+id2));
                                        }
                                        else{ //edit
                                            $("#name_modal2").addClass('uk-hidden');
                                            $("#select_ct").addClass('uk-hidden');
                                            $("#title_modal").html("Edycja tagu".toString());
                                            $("#name_modal").val(names_tag[i].toString());
                                            $('#ThisFormModal1_3').attr('action', ('editTag?tag='+ids_tag[i]));
                                        }
                                    } 
                                } 
                             break;
                        }
                    }
                </script>
                <div id="modal1_3" uk-modal>
                    <div class="uk-modal-dialog uk-modal-body">
                        <button class="uk-modal-close-outside" type="button" uk-close></button>
                        <div class="uk-modal-header"><h2 class="uk-modal-title"><span id="title_modal"></span></h2></div>
                        <form class="uk-form-stacked" id="ThisFormModal1_3" method="POST" action="insert?tab=2" accept-charset="ISO-8859-1">
                            <div class="uk-margin-medium-top" uk-grid>
                                <div class="uk-width-2-3@m"> 
                                    <h4 class="uk-text-bold">Nazwa:</h4> 
                                    <input id="name_modal" name="name" class="uk-input" value=""
                                       type="text"/>
                                </div>
                                <div id="name_modal2" class="uk-width-2-3@m uk-hidden"> 
                                    <h4 class="uk-text-bold">Nazwa pierwszej kategorii:</h4> 
                                    <input id="name2" name="name2" class="uk-input" value=""
                                       type="text"/>
                                </div>
                                <div id="select_ct" class="uk-width-3-5 uk-hidden">
                                    <h4 class="uk-text-bold">Wybierz istniejące tagi:</h4>
                                    <select id="cattag" name="multi_tags" size="1" class="uk-select" multiple> 
                                      <c:forEach var="CT1" items="${listCatTag}">
                                        <option value="${CT1.idTag}">${CT1.nameTag}</option>
                                      </c:forEach>
                                    </select>
                                    <script>let mySelect = new vanillaSelectBox("#cattag",{
                                        maxWidth: 900, minWidth: 150});</script>
                                </div>
                                <div class="uk-width-1-6@m uk-margin-medium-top">
                                    <button class="uk-button uk-button-secondary" type="submit">Zatwierdź</button> 
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
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