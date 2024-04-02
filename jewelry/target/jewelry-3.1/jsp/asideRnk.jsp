<%-- 
    Document   : index
    Created on : 25 sierpień 2021, 15:20:49
    Author     : DRzepka
--%>

<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %> <%--prevent nextLine in document--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<%@page import="java.time.LocalDateTime"%>
<%@page import="java.time.format.DateTimeFormatter"%>
<%@page import="java.util.List"%>
<%@page import="jwl.model.History"%>
<%@page import="jwl.DAO.HistoryDAO"%>
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
        <!--NAVIGATION-->  
        <style>
            @media (max-width: 600px) {
                
            }
        </style>
        <aside class=" uk-width-1-6@m uk-first-column"><!---->
            <div id="menuRnk" class="uk-width-1-6" uk-offcanvas>
                <div class="menu-bar uk-offcanvas-bar"> <!-- uk-offcanvas-->
                <ul class="uk-nav-default uk-nav" uk-nav="">
                    <li class="uk-text-center">
                        <a href="home">
                            <img id="logo" src="img/logo.svg" alt="logo" width="50"><br>
                            <span>Ametyst</span>
                        </a>
                        <p class="uk-nav-divider"></p>
                    </li>
                <c:if test="${rank==1}">
                    <li class="<c:choose><c:when test = "${t==1}">uk-parent uk-open</c:when><c:otherwise>uk-nav-header</c:otherwise></c:choose>">
                        <a href="<c:choose><c:when test = "${t==1}">#</c:when><c:otherwise>administrator?t=1</c:otherwise></c:choose>" 
                           <c:if test = "${t==1}"> class="uk-nav-header"</c:if>>
                            <span class="uk-margin-small-right" uk-icon="icon: settings"></span>Tabele słownikowe
                        </a>
                       <c:if test="${t==1}">
                        <ul class="uk-nav-sub uk-margin-small-left" uk-switcher="connect: .switcher-admin1">
                            <li><a href="#">Kolor</a></li>
                            <li><a href="#">Kształt</a></li>
                            <li><a href="#">Materiał</a></li>
                            <li><a href="#">Dostawa</a></li>
                            <li><a href="#">Płatność</a></li>
                            <li><a href="#">Promocje</a></li>
                        </ul>
                       </c:if>
                    </li>
                    <li class="uk-nav-header <c:if test="${t==2}">uk-active</c:if>">
                        <a href="<c:choose><c:when test = "${t==2}">#</c:when><c:otherwise>administrator?t=2</c:otherwise></c:choose>">
                            <span class="uk-margin-small-right" uk-icon="icon: users"></span>Użytkownicy
                        </a>
                    </li>
                    <li class="uk-nav-header <c:if test="${t==3}">uk-active</c:if>">
                        <a href="<c:choose><c:when test = "${t==3}">#</c:when><c:otherwise>administrator?t=3</c:otherwise></c:choose>">
                            <span class="uk-margin-small-right" uk-icon="icon: menu"></span>Menu
                        </a>
                    </li>
                </c:if>
                <c:if test="${rank==2}"> 
                    <li class="<c:choose><c:when test = "${t==1}">uk-parent uk-open</c:when><c:otherwise>uk-nav-header</c:otherwise></c:choose>">
                        <a href="<c:choose><c:when test = "${t==1}">#</c:when><c:otherwise>worker</c:otherwise></c:choose>" 
                           <c:if test = "${t==1}"> class="uk-nav-header"</c:if>>
                            <span class="uk-margin-small-right" uk-icon="icon: settings"></span>Zamówienia
                        </a>
                       <c:if test="${t==1}">
                        <ul class="uk-nav-sub uk-margin-small-left" uk-switcher="connect: .switcher-worker1">
                            <li><a href="#">Poczekalnia</a></li> <%--id 1--%>
                            <li><a href="#">Akceptacja</a></li> <%--id 2--%>
                            <li><a href="#">Płatność</a></li> <%--id 3--%>
                            <li><a href="#">Przygotowanie</a></li> <%--id 4--%>
                            <li><a href="#">W realizacji</a></li> <%--id 5--%>
                            <li><a href="#">Wysłano</a></li> <%--id 6--%>
                            <li><a href="#">W drodze</a></li> <%--id 7--%>
                            <li><a href="#">Zakończono</a></li> <%--id 8--%>
                            <li><a href="#">Anulowano</a></li> <%--id 9--%>
                        </ul>
                       </c:if>
                    </li>
                </c:if>    
                <c:if test="${rank==3}"> 
                    <li class="uk-nav-header <c:if test="${t==1}">uk-active</c:if>">
                        <a href="<c:choose><c:when test = "${t==1}">#</c:when><c:otherwise>corrector?t=1</c:otherwise></c:choose>">
                            <span class="uk-margin-small-right" uk-icon="icon: list"></span> Recenzje
                        </a></li> 
                    <li class="uk-nav-header <c:if test="${t==2}">uk-active</c:if>">
                        <a href="<c:choose><c:when test = "${t==2}">#</c:when><c:otherwise>corrector?t=2</c:otherwise></c:choose>">
                            <span class="uk-margin-small-right" uk-icon="icon: thumbnails"></span> Produkt
                        </a></li>
                    <li class="uk-nav-header <c:if test="${t==3}">uk-active</c:if>">
                        <a href="<c:choose><c:when test = "${t==3}">#</c:when><c:otherwise>corrector?t=3</c:otherwise></c:choose>">
                            <span class="uk-margin-small-right" uk-icon="icon: menu"></span> Kategoria
                        </a></li>
                    <li class="uk-nav-header <c:if test="${t==4}">uk-active</c:if>">
                        <a href="<c:choose><c:when test = "${t==4}">#</c:when><c:otherwise>corrector?t=4</c:otherwise></c:choose>">
                            <span class="uk-margin-small-right" uk-icon="icon: tag"></span> Tag
                        </a>
                    </li>
                </c:if>    
                <c:if test="${rank==4}"> 
                    <li class="uk-nav-header <c:if test="${t==1}">uk-active</c:if>">
                        <a href="<c:choose><c:when test = "${t==1}">#</c:when><c:otherwise>supplier?t=1</c:otherwise></c:choose>" 
                           <c:if test = "${t==1}"> class="uk-nav-header"</c:if>>
                            <span class="uk-margin-small-right" uk-icon="icon: thumbnails"></span>Produkty
                        </a>
                    </li>   
                    <li class="<c:choose><c:when test = "${t==2}">uk-parent uk-open</c:when><c:otherwise>uk-nav-header</c:otherwise></c:choose>">
                        <a href="<c:choose><c:when test = "${t==2}">#</c:when><c:otherwise>supplier?t=2</c:otherwise></c:choose>"
                           <c:if test = "${t==2}">class=" uk-nav-header"</c:if>>
                            <span class="uk-margin-small-right" uk-icon="icon: settings"></span>Tabele słownikowe
                        </a>
                        <c:if test="${t==2}">
                        <ul class="uk-nav-sub uk-margin-small-left" <c:if test="${t==2}">uk-switcher="connect: .switcher-supplier2"</c:if>>
                            <li><a href="#>">Kolor</a></li> 
                            <li><a href="#>">Kształt</a></li>
                            <li><a href="#">Materiał</a></li> 
                        </ul>
                        </c:if>
                    </li>
                </c:if>
                    <li class="uk-nav-header">
                        <a href="#history" uk-toggle>
                            <span class="uk-margin-small-right" uk-icon="icon: clock"></span>Historia
                        </a>
                    </li>
                    <hr class="uk-hidden@m" class="uk-margin-small">
                    <li class="uk-hidden@m">
                        <a href="orders"><span uk-icon="icon:  list; ratio: 0.8" class="uk-icon"></span> Zamówienia</a>
                    </li>
                    <li class="uk-hidden@m"><a href="home"><span uk-icon="icon: reply; ratio: 0.8"></span> Sklep</a></li>
                    <li class="uk-hidden@m">
                        <a href="settings"><span uk-icon="icon: cog; position: left; ratio: 0.8" class="uk-icon"></span> Ustawienia</a>
                    </li>
                    <li class="uk-hidden@m"><a href="logout"><span uk-icon="icon: sign-out; ratio: 0.8" class="uk-icon"></span> Wyloguj</a></li>
                </ul> 
                <div class="hidsm uk-position-large uk-position-bottom-left">
                    <div class="uk-inline">
                        <a class="uk-text-right">
                            <p class="uk-text-center uk-margin-remove"><span uk-icon="icon: user; ratio: 2" class="uk-icon"></span></p>
                            <h4 class="uk-text-center uk-margin-remove">${name}&#32;${surname}</h4>
                        </a>
                        <div uk-dropdown="pos: right-center">
                            <ul class="uk-nav uk-dropdown-nav">
                                <li>
                                    <a href="orders"><span uk-icon="icon:  list; ratio: 0.8" class="uk-icon"></span> Zamówienia</a>
                                </li>
                                <li><a href="home"><span uk-icon="icon: reply; ratio: 0.8"></span> Sklep</a></li>
                                <li>
                                    <a href="settings"><span uk-icon="icon: cog; position: left; ratio: 0.8" class="uk-icon"></span> Ustawienia</a>
                                </li>
                                <hr class="uk-margin-small">
                                <li><a href="logout"><span uk-icon="icon: sign-out; ratio: 0.8" class="uk-icon"></span> Wyloguj</a></li>
                            </ul>
                        </div>
                    </div>
                </div>
            </div>
            </div>
        </aside>           
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
       <c:if test = "${err_disc!=null}"> 
        <script>
            UIkit.notification({message: "<p class='uk-text-center uk-margin-remove-bottom'> \n\
                <span uk-icon=\'icon: warning\'></span> <c:out value="${err_disc}"/></p>", 
                status: "danger"});
            <% session.removeAttribute("err_disc"); %>
        </script>
       </c:if>
       <c:if test = "${succ_disc!=null}"> 
        <script>
            UIkit.notification({message: "<p class='uk-text-center uk-margin-remove-bottom'> \n\
                <span uk-icon=\'icon: info\'></span> <c:out value="${succ_disc}"/></p>", 
                status: "success"});
            <% session.removeAttribute("succ_disc"); %>
        </script>
       </c:if>
