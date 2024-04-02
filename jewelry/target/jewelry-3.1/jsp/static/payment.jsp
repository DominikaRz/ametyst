<%-- 
    Document   : payment
    Created on : 14 sty 2022, 13:42:57
    Author     : DRzepka
--%>

<%@page import="jwl.model.dict.Payment"%>
<%@page import="java.util.List"%>
<%@page import="jwl.DAO.dict.PaymentDAO"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
    PaymentDAO paymentDAO = new PaymentDAO(application.getInitParameter("jdbcURL"),
                                    application.getInitParameter("jdbcUserNL"),   
                                    application.getInitParameter("jdbcUserNLPassw"));
    List<Payment> listPay = paymentDAO.read();
    request.setAttribute("listPay", listPay);
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
                <h1>Płatność</h1>
                <h3>Płatności w naszym sklepie można dokonać za pomocą:</h3>
                <ul class="uk-list uk-list-decimal uk-margin-large-left">
                <c:forEach var="pay" items="${listPay}" varStatus="stat">
                  <c:if test = "${pay.category != listPay[stat.index-1].category}">
                    <h4>${pay.category}</h4>
                      <ul class="uk-list uk-list-bullet uk-margin-large-left uk-text-left">
                  </c:if>
                        <li>${pay.name}</li>
                  <c:if test = "${listPay[stat.index+1].category != pay.category}">
                      </ul>
                  </c:if> 
                </c:forEach>
                </ul>
                <h5>W przypadku wybrania płatności z góry trzeba pamiętać, że za zamówienie należy zapłacić w terminie 
                    7 Dni roboczych od złożenia zamówienia. Płatności zawsze dokonujemy po złożeniu zamówienia.</h5>
                <h4>Życzymy miłych oraz udanych zakupów!</h4>
            </div> 
        </section>
      </div>
    </main>
   <!--FOOTER-->  
    <jsp:include page="../footer.jsp"/>
  </body>
</html>
