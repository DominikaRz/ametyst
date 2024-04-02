<%-- 
    Document   : navigation
    Created on : 20 maj 2021, 18:06:57
    Author     : DRzepak
--%>

<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="jwl.DAO.BasketDAO"%>
<%@ page import="jwl.model.link.Basket"%>
<%@ page import="jwl.DAO.ProductMDAO"%>
<%@ page import="jwl.model.ProductMeta"%>
<%@ page trimDirectiveWhitespaces="true" %> <%--prevent nextLine in document--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
    BasketDAO baskDAO = new BasketDAO(application.getInitParameter("jdbcURL"),
                                    application.getInitParameter("jdbcUserNL"),   
                                    application.getInitParameter("jdbcUserNLPassw"));
    ProductMDAO prodmDAO = new ProductMDAO(application.getInitParameter("jdbcURL"),
                                    application.getInitParameter("jdbcUserNL"),   
                                    application.getInitParameter("jdbcUserNLPassw"));
    
    String sessi = "";
    Cookie[] cookies = request.getCookies();
    for (Cookie cookie : cookies) {
        if (cookie.getName().equals("session")) { sessi = cookie.getValue(); }
    }

    int id_usr = 0; 
    try { id_usr = (int) session.getAttribute("id_user_logged"); } 
    catch (NullPointerException e) { }

    List<Basket> listBask = baskDAO.readSess(sessi);
    if(id_usr!=0){ 
        if(!session.equals("")){
            for (Basket item : listBask) {
                boolean q = baskDAO.updateSessToUsr(item, id_usr);                                              //przypisz przedmiot do użytkownika zalogowanego
                if(q){ session.setAttribute("succ_disc","Dodano koszyk do aktualnego użytkownika."); }          //jeśli się udało
                else{ session.setAttribute("err_disc","Błąd dodania do uzytkownika aktualnego koszyka.");}      //w przeciwnym wypadku wyświetlanie komunikatu o błędzie
            }
            listBask = baskDAO.readUsr(id_usr);
        }
        else listBask = baskDAO.readUsr(id_usr);
    }
    request.setAttribute("listBask", listBask);
         
    double sum = 0;
    List<ProductMeta> listPrd = new ArrayList<>();
    for (Basket item : listBask) {
        ProductMeta prodm = prodmDAO.readProdBask(item.getIdProd());
        sum += (prodm.getPrice() -(prodm.getDiscount()/100.0)*prodm.getPrice())*item.getQuantity();
        listPrd.add(prodm);
    }
    request.setAttribute("listPrd", listPrd);
    request.setAttribute("summary", sum);
%>

<nav class="uk-navbar-container" uk-sticky="sel-target: .uk-navbar-container; cls-active: uk-navbar-sticky" uk-navbar>
    <div class="uk-navbar-center"><div> <!--Additional div to display in IE 11-->
        <ul class="uk-navbar-nav"> 
            <li class="hidsm"><a href="news">NOWOŚCI</a></li>  
            <li class="hidsm"><a href="discount">PROMOCJE</a></li>
            <li class="hidsm"><a href="restock">PONOWIONE</a></li>
            <li><a class="uk-navbar-item uk-logo uk-animation-scale-down" href="home">
                <img id="logo" src="img/logo.svg" alt="logo" width="45"/></a>
            </li><!----> 
            <!--KOSZYK-->
            <li id="baskSm" class="uk-parent">
                <a href="basket" uk-icon="icon: cart"></a> <%-- --%>
            </li>
            <li id="bask" class="uk-parent">
                <a href="basket" uk-icon="icon: cart"></a> <%-- --%>
                <div id="cart" uk-dropdown="pos: bottom-center; boundary: .uk-navbar-container; boundary-align: true">
                  <c:choose>
                   <c:when test = "${not empty listPrd}">   
                    <div><h4 class="uk-card-footer">Suma: <c:out value="${Math.round(summary*100.00)/100.00}"/>&nbsp;zł</h4></div>
                    <c:forEach var="prod" items="${listPrd}" varStatus="stat">
                    <div id="div1" class="uk-dropdown-grid uk-margin-small-top"> 
                        <div class="uk-child-width-1-5" uk-grid>
                            <div class="uk-justify-right">
                                <span class="uk-text-bold uk-margin-small-right"><c:out value="${stat.index+1}"/></span>
                                <img src="http://localhost:8080/jewelry/img/CT/<c:out value="${prod.id}"/>/1.jpg" alt="img" width="50"/>
                            </div>
                            <div class="item-text uk-width-3-5">
                                <p><c:out value="${prod.name}"/></p>
                            </div>
                            <div>
                                <p class="item-price uk-text-nowrap uk-text-right uk-text-bold">
                                    <c:choose>
                                        <c:when test = "${prod.discount!=0}">
                                            <c:set var="priceOfProduct" 
                                                   value="${Math.round((prod.price-(prod.discount/100)*prod.price)*100.0)/100.0}"/>
                                        </c:when>
                                        <c:otherwise>
                                            <c:set var="priceOfProduct" value="${prod.price}"/>
                                        </c:otherwise>
                                    </c:choose>
                                    <c:set var="price" value="${Math.round((priceOfProduct*listBask[stat.index].quantity)*100.00)/100.00}"/>
                                    <c:out value="${listBask[stat.index].quantity}"/>&nbsp;*&nbsp;<c:out value="${price}"/>&nbsp;zł
                                </p>
                            </div>
                        </div>
                    </div> 
                    <c:set var="sum" value="${Math.round((sum + price)*100.00)/100.00}"/>      
                    </c:forEach>
                    </c:when>
                    <c:otherwise>
                      <div><h4 class="uk-card-footer">Koszyk pusty</h4></div>
                    </c:otherwise>
                  </c:choose>
                </div>
            </li> 
            <!--USER-->
            <li class="uk-parent"> 
            <c:if test="${sessionScope.id_user_logged == null}"> 
              <a href="login" uk-icon="icon: user" uk-tooltip="title: Zaloguj / Zarejestruj; pos: bottom"></a>
             </c:if>  
             <c:if test="${(sessionScope.id_user_logged != null)&&(sessionScope.rank == null)}"> 
              <a href="" uk-icon="icon: user"></a>
                <div class="uk-navbar-dropdown">
                    <ul class="uk-nav uk-navbar-dropdown-nav">
                        <li><a href="orders"><span uk-icon="icon:  table; ratio: 0.8"></span> Twoje zamówienia</a></li>
                        <li><a href="settings"><span uk-icon="icon: cog; position: left; ratio: 0.8"></span> Ustawienia konta</a></li>
                        <hr class="uk-margin-small">
                        <li><a href="logout"><span uk-icon="icon: sign-out; ratio: 0.8"></span> Wyloguj</a></li>
                    </ul>
                </div>
               </c:if>  
             <c:if test="${(sessionScope.id_user_logged != null)&&(sessionScope.rank != null)}"> 
              <a href="" uk-icon="icon: user"></a>
                <div class="uk-navbar-dropdown">
                    <ul class="uk-nav uk-navbar-dropdown-nav">
                        <li class="uk-active"><a href="redirectRnk"><span uk-icon="icon:  table; ratio: 0.8"></span> Twój panel</a></li>
                        <li><a href="orders"><span uk-icon="icon:  table; ratio: 0.8"></span> Twoje zamówienia</a></li>
                        <li><a href="settings"><span uk-icon="icon: cog; position: left; ratio: 0.8"></span> Ustawienia konta</a></li>
                        <hr class="uk-margin-small">
                        <li><a href="logout"><span uk-icon="icon: sign-out; ratio: 0.8"></span> Wyloguj</a></li>
                    </ul>
                </div>
             </c:if>
            </li>
          <!--SZUKAJ-->
            <li id="search" class="uk-parent">  
                <a class="uk-navbar-toggle" uk-search-icon href="#"></a>
                <div class="uk-drop" uk-drop="mode: click; pos: right-center; offset: 0">
                    <form method="POST" action="search" accept-charset="ISO-8859-1" class="uk-search uk-search-navbar uk-width-1-1"> <!---->
                        <input class="uk-search-input" name="search" type="search" placeholder="Szukaj" autofocus>
                    </form>
                </div>
            </li>
            <li id="searchSm" class="uk-parent uk-hidden">  
                <a class="uk-navbar-toggle" href="#" uk-search-icon></a>
                <div id="sear-button uk-width-2-3" uk-dropdown>
                    <div class="uk-dropdown-grid"> 
                        <div class="uk-child-width-1-5" uk-grid>
                            <div class="uk-width-4-5">
                                <form class="uk-search uk-search-navbar uk-width-1-1" method="POST" action="search" accept-charset="ISO-8859-1">
                                    <input class="uk-search-input" name="search" type="search" placeholder="Szukaj" autofocus>
                                </form>
                            </div>
                        </div>
                  </div>
                </div>
            </li>
            <li><a href="#menu" class="uk-hidden@m" uk-toggle uk-navbar-toggle-icon></a></li><!-- -->
        </ul>
    </div></div>
</nav>