<%-- 
    Document   : index
    Created on : 25 sierpień 2021, 15:20:49
    Author     : DRzepka
--%>

<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %> <%--prevent nextLine in document--%>
<%@ page import="jwl.DAO.dict.CatTagDAO" %>
<%@page import="jwl.DAO.dict.GroupDAO"%>
<%@ page import="jwl.model.link.CatTag" %>
<%@ page import="jwl.model.dict.Group" %>
<%@ page import="java.util.*" %>
<%
    CatTagDAO ctDAO = new CatTagDAO(application.getInitParameter("jdbcURL"),
                                    application.getInitParameter("jdbcMenu"),   
                                    application.getInitParameter("jdbcMenuPassw"));
    List<CatTag> listCT = ctDAO.read();
    request.setAttribute("listCT",listCT);
    
    GroupDAO grDAO = new GroupDAO(application.getInitParameter("jdbcURL"),
                                    application.getInitParameter("jdbcMenu"),   
                                    application.getInitParameter("jdbcMenuPassw"));
    List<Group> listGr = grDAO.read();
    request.setAttribute("listGr",listGr);
%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!--NAVIGATION-->   
        <aside class="uk-width-1-5@m">
            <div id="menu" uk-offcanvas>
                <div class="menu-bar uk-offcanvas-bar">
                    <ul class="uk-nav-default uk-nav-parent-icon" uk-nav>
                    <c:forEach var="CT" items="${listCT}" varStatus="stat">
                        <c:if test = "${CT.idGr != listCT[stat.index-1].idGr}">
                            <c:if test = "${CT.idGr != 1}">
                            <li class="uk-nav-divider uk-margin"></li>
                            </c:if> 
                            <a href="#" class="uk-nav-header">
                                <h4 class="uk-margin-small-top uk-text-uppercase"><c:out value="${CT.name}"/></h4>
                            </a>
                        </c:if>
                        <c:if test = "${CT.idCat != listCT[stat.index-1].idCat}">
                        <li class="uk-parent">
                            <a href="#" class="uk-nav-header">
                                <c:out value="${CT.nameCat}"/></a>
                            <ul class="uk-nav-sub">
                                <li>
                                    <a href="categories?id=<c:out value="${CT.idCat}"/>" 
                                       class="uk-margin-small-left">
                                        Wszystkie
                                    </a>
                                </li> 
                        </c:if>
                                <li>
                                    <a href="tags?id=<c:out value="${CT.id}"/>" 
                                       class="uk-margin-medium-left">
                                        <c:out value="${CT.nameTag}"/>
                                    </a>
                                </li>
                        <c:if test = "${listCT[stat.index+1].idCat != CT.idCat}">
                            </ul>
                        </li>
                        </c:if> 
                    </c:forEach>
                    </ul>
                </div>
            </div>
        </aside>
