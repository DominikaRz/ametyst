<%-- 
    Document   : navigation
    Created on : 20 maj 2021, 18:06:57
    Author     : DRzepak
--%>
<%@page import="java.time.LocalDateTime"%>
<%@page import="java.time.format.DateTimeFormatter"%>
<%@page import="java.util.List"%>
<%@page import="jwl.model.History"%>
<%@page import="jwl.DAO.HistoryDAO"%>
<%@ page trimDirectiveWhitespaces="true" %> <%--prevent nextLine in document--%>
<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<%
    DateTimeFormatter formatLocalDate = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    String now = formatLocalDate.format(LocalDateTime.now());
    request.setAttribute("now",now);
        
    int usr = (int) session.getAttribute("id_user_logged");
    HistoryDAO hisDAO = new HistoryDAO(application.getInitParameter("jdbcURL"),
                                    application.getInitParameter("jdbcHistory"),   
                                    application.getInitParameter("jdbcHistoryPassw"));
    List<History> listHis = hisDAO.readUsr(usr);
    
    
    for (History item : listHis) {
        String[] date = item.getDate().split(" ");
        item.setDate(date[0]);
    }
    request.setAttribute("listHis",listHis);
%>
<nav class="uk-navbar-container" uk-sticky="sel-target: .uk-navbar-container; cls-active: uk-navbar-sticky" uk-navbar>
      <div class="uk-navbar-center"><div> <!--Additional div to display in IE 11-->
          <ul class="uk-navbar-nav"> 
            <li class="hidsm"><a href="home">Powrót do Sklepu</a></li>
            <li><a class="uk-navbar-item uk-logo" href="index">
                <img id="logo" src="img/logo.svg" alt="logo" width="45"/></a>
            </li>
            <li class="uk-parent">
              <a href="index" uk-icon="icon: user"></a><!--ZALOGUJ-->
              <c:set var="uri" value="<%= request.getRequestURI() %>"/>
                <div class="uk-navbar-dropdown">
                    <ul class="uk-nav uk-navbar-dropdown-nav">
                        <li <c:if test="${(uri=='/jewelry/IndexUsr.jsp')||(uri=='/jewelry/IndexRnk.jsp')}">class="uk-active"</c:if>>
                            <a href="index"><span uk-icon="icon:  table; ratio: 0.8"></span> Twój panel</a>
                        </li>
                        <li <c:if test="${uri=='/jewelry/IndexUser.jsp'}">class="uk-active"</c:if>>
                            <a href="orders"><span uk-icon="icon:  list; ratio: 0.8"></span> Twoje zamówienia</a>
                        </li>
                        <li <c:if test="${uri=='/jewelry/UpdateUsr.jsp'}">class="uk-active"</c:if>>
                            <a href="settings"><span uk-icon="icon: cog; position: left; ratio: 0.8"></span> Ustawienia konta</a>
                        </li>
                        <hr class="uk-margin-small">
                        <li><a href="logout"><span uk-icon="icon: sign-out; ratio: 0.8"></span> Wyloguj</a></li>
                    </ul>
                </div>
            </li>
            <li><a href="#history" uk-toggle>Historia</a></li>
          </ul>
        </div>
      </div>
    </nav>
<div id="history" uk-offcanvas="flip: true; overlay: true">
    <div class="uk-offcanvas-bar uk-flex uk-flex-column">
        <ul class="uk-nav uk-nav-primary uk-nav-center uk-margin-auto-vertical uk-nav-parent-icon" uk-nav="multiple: true">
        <c:forEach var="his" items="${listHis}" varStatus="stat">
          <c:if test = "${his.date != listHis[stat.index-1].date}">
           <c:if test = "${his.date.contains(now)}"> 
            <li class="uk-parent uk-open">
                <a href="#">Dzisiaj</a>
           </c:if>
           <c:if test = "${!his.date.contains(now)}"> 
            <li class="uk-parent">  
                <a href="#">${his.date}</a>
              </c:if>
                <ul class="uk-nav-sub">
           </c:if>
                    <li><p class="uk-text-left"><span class="uk-text-small" uk-tooltip="title: ${his.modify}">
                        ${his.description}<c:if test = "${(his.idAct != 4)&&(his.idAct != 5)}"> (id. ${his.id})</c:if>
                    </span> 
                 <c:if test = "${his.idAct != 5}">       
                        <a href="history?id=${his.id}&a=1" uk-icon="icon: history" uk-tooltip="Cofnij" class="uk-margin-small-left" >
                        </a><a href="history?id=${his.id}&a=2" uk-icon="icon: future" uk-tooltip="Ponów" class="uk-margin-small-left"></a>
                </c:if>        
                        </p>
                    </li>
                
            <c:if test = "${listHis[stat.index+1].date != his.date}">
                </ul>
            </li>
            </c:if> 
        </c:forEach>
        </ul>
    </div>
</div>