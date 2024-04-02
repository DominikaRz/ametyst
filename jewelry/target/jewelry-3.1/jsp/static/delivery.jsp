<%-- 
    Document   : delivery
    Created on : 14 sty 2022, 13:32:45
    Author     : DRzepka
--%>

<%@page import="jwl.model.dict.Delivery"%>
<%@page import="java.util.List"%>
<%@page import="jwl.DAO.dict.DeliveryDAO"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
    DeliveryDAO deliveryDAO = new DeliveryDAO(application.getInitParameter("jdbcURL"),
                                    application.getInitParameter("jdbcUserNL"),   
                                    application.getInitParameter("jdbcUserNLPassw"));
    List<Delivery> listDeliv = deliveryDAO.read();
    request.setAttribute("listDel", listDeliv);
%>
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
        <section class="uk-width-4-5@m uk-margin-medium uk-text-center">
            <div class="uk-child-width-1-1@s" uk-grid>
                <h1>Dostawa</h1>
                <h3>W naszym sklepie możliwe są następujące sposoby wysyłki:</h3>
                <ul class="uk-list uk-list-bullet uk-margin-large-left uk-text-left">
                  <c:forEach var="del" items="${listDel}" varStatus="stat">
                    <li class="uk-h4"><h4>${del.name} - ${del.price} zł</li>
                  </c:forEach>
                </ul>
                <h4>Życzymy miłych oraz udanych zakupów!</h4>
            </div>
        </section>
      </div>
    </main> 
   <!--FOOTER-->  
    <jsp:include page="../footer.jsp"/> 
  </body>
</html>
