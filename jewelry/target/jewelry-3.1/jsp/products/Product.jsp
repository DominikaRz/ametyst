<%-- 
    Document   : ListProduct
    Created on : 27 sie 2021, 12:32:51
    Author     : DRzepka
--%>

<%@ page import="jwl.model.ProductMeta"%>
<%@ page import="jwl.model.link.CatTag"%>
<%@ page import="jwl.DAO.dict.CatTagDAO"%>
<%@ page trimDirectiveWhitespaces="true" %> <%--prevent nextLine in document--%>
<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

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
                <li><a href="categories?id=${ids.idCat}&t=1">${names.nameCat}</a></li>
                <li><a href="tags?id=${ids.idTag}&t=2">${names.nameTag}</a></li>
                <li class="uk-disabled"><a>${prodm.name}</a></li>
            </ul>
            <h2 class="uk-h1"><c:out value="${prodm.name}"/></h2>
            <div class="uk-margin-large-top uk-child-width-1-2@m" uk-grid>
                <div style="padding: 0;">
                    <div class="" uk-slideshow="animation: fade">
                        <div class="" uk-lightbox="animation: fade">
                            <ul class="uk-slideshow-items uk-margin">
                                <c:forEach var="i" begin="1" end="${prodm.images}" step="1" varStatus ="status">
                                <li>
                                    <a class="uk-inline" href="http://localhost:8080/jewelry/img/CT/<c:out value="${prodm.id}"/>/<c:out value="${i}"/>.jpg" 
                                       data-caption="1">
                                        <img src="http://localhost:8080/jewelry/img/CT/<c:out value="${prodm.id}"/>/<c:out value="${i}"/>.jpg" alt="">
                                    </a>
                                </li>
                                </c:forEach>
                            </ul>
                        </div>
                        <div class="uk-align-center">
                            <ul class="uk-thumbnav"> 
                                <c:forEach var="i" begin="1" end="${prodm.images}" step="1" varStatus ="status">
                                <li uk-slideshow-item="<c:out value="${i-1}"/>">
                                    <a href="#">
                                        <img src="http://localhost:8080/jewelry/img/CT/<c:out value="${prodm.id}"/>/<c:out value="${i}"/>.jpg" class="sdimg" alt="">
                                    </a>
                                </li>
                                </c:forEach>
                            </ul>
                        </div>
                    </div>
                </div>
                <div class="uk-text-center">
                    <div class="uk-child-width-1-2" uk-grid>
                        <p>Cena:</p>
                        <h4 class="uk-text-bold">
                            <c:choose>
                                <c:when test = "${prodm.discount!=0}">
                                    <s style="color: #860e0e;"><c:out value="${prodm.price}"/></s>
                                    <c:out value="${Math.round((prodm.price-(prodm.discount/100)*prodm.price)*100.0)/100.0}"/>&nbsp;zł
                                </c:when>
                                <c:otherwise>
                                    <c:out value="${prodm.price}"/>&nbsp;zł
                                </c:otherwise>
                             </c:choose>
                        </h4>
                    </div>
                    <c:set var="q" value="${prodm.quantityState-quant}"/>
                    <c:if test = "${(prodm.quantityState != 0.00)&&(q!=0)}"> 
                    <hr>
                    <div class="uk-child-width-1-2" uk-grid>
                        <p>Dostępność:</p>
                        <h5><c:out value="${q}"/></h5>
                    </div>
                    <form id="ThisFormProd" method="POST" action="addBask" accept-charset="ISO-8859-1"
                          class="uk-form-stacked uk-margin-medium-bottom  uk-align-center uk-width-4-5@xs" >
                        <div class="uk-grid-small uk-margin-medium-top  uk-child-width-1-1" uk-grid>
                            <div class="uk-child-width-1-4@l uk-child-width-1-4@s">
                                <button type="button" class="uk-button uk-button-secondary minus"
                                        onclick="if($(this).next().val()>=2)$(this).next().val(+$(this).next().val() - 1);">
                                    <span uk-icon="minus"></span></button>
                                <input id="nbr" name="quantity" class="uk-input uk-text-center" type="number" value="1" step="1" min="1" max="${q}"/>
                                <button type="button" class="uk-button uk-button-secondary plus" 
                                        onclick="if($(this).prev().val()<=${q-1})$(this).prev().val(+$(this).prev().val() + 1);">
                                    <span uk-icon="plus"></span></button>
                            </div>
                            <input name="id_pr" class="uk-input" value="<c:out value="${prodm.id}"/>" type="hidden"/>
                            <button class="uk-button uk-button-secondary" type="submit" >Do&nbsp;koszyka</button>
                        </div>
                    </form>
                    </c:if> 
                    <c:if test = "${prodm.quantityState == 0.00}"> 
                    <div class="uk-flex uk-flex-center" uk-grid>
                        <h3>Produkt archiwalny</h3>
                    </div>
                    </c:if> 
                    <c:if test = "${(q == 0)&&(prodm.quantityState != 0.00)}"> 
                    <div class="uk-flex uk-flex-center" uk-grid>
                        <button class="uk-button uk-button-secondary"  uk-toggle="target: #modal"
                                type="button">Zapytaj o&nbsp;produkt</button>
                    </div>
                    </c:if> 
                </div>
            </div>
            <div id="describe">
                <h3>OPIS PRODUKTU</h3>
                <hr>
                <h5><c:out value="${prodm.description}"/></h5>
                <p> Materiał: <c:out value="${fabr.name}"/></p>
                <c:if test = "${(prodm.height != 0.00)}">
                    <p> Wysokość: <c:out value="${prodm.height}"/> mm</p></c:if>
                <c:if test = "${(prodm.width != 0.00)}">
                    <p> Szerokość: <c:out value="${prodm.width}"/> mm</p></c:if>
                <c:if test = "${(prodm.lenght != 0.00)}">
                    <p> Długość: <c:out value="${prodm.lenght}"/> mm</p></c:if>
                <c:if test = "${(prodm.hole != 0.00)}">
                    <p> Wielkość otworu: <c:out value="${prodm.hole}"/> mm</p></c:if>
                <c:if test = "${(prodm.weight != 0.00)}">
                    <p> Waga: <c:out value="${prodm.weight}"/> g</p></c:if>
                <p> Kształt: <c:out value="${shap.name}"/></p>
                <c:if test = "${(prodm.diameter != 0.00)}">
                    <p> Średnica: <c:out value="${prodm.diameter}"/> mm</p></c:if>
                <p> Kolor: <c:out value="${colo.name}"/></p>
                <p> Ilość w opakowaniu: <c:out value="${prodm.quantity}"/></p>
                <p class="uk-margin-top-small"> Źródło: <a href="<c:out value="${prodm.source}"/>"><c:out value="${prodm.source}"/></a></p>
            </div>
            <c:if test="${not empty listRew}">
            <div id="comments" class="uk-margin-medium">
               <p><span class="uk-h3">Komentarze</span> <small> odpowiedź po najechaniu na tekst komentarza</small></p>
                <hr>
                <c:forEach var="rev" items="${listRew}" varStatus="stat"> 
                <article class="uk-comment">
                    <div class="uk-grid-medium uk-flex-middle" uk-grid>
                        <div class="uk-width-medium">
                            <h5 class="uk-comment-title uk-margin-remove">
                                <a class="uk-link-reset" href="#">
                                    <c:out value="${rev.name}"/>
                                </a></h5>
                            <p class="uk-comment-meta uk-subnav uk-subnav-divider uk-margin-small-left uk-margin-remove-top">
                                <c:out value="${rev.publication}"/></p>
                        </div>
                        <div>
                            <p>
                                <c:forEach var="i" begin="1" end="${rev.stars}" step="1" varStatus ="status">
                                <i uk-icon="star" class="prodstar" style="color: #ffff00;"></i>
                                </c:forEach>
                            </p>
                            <p <c:if test="${not empty rev.reply}">uk-tooltip="title: Odpowiedź:<br>${rev.reply}"</c:if>
                               >${rev.content}</p>
                        </div>
                    </div>
                </article>
                <hr style="border-top: 1px solid #b59bc77a; width: 50vw;">
                </c:forEach>
            </div>
            </c:if>
        </section>
        <div id="modal" uk-modal>
            <div class="uk-modal-dialog uk-modal-body">
                <button class="uk-modal-close-outside" type="button" uk-close></button>
                <div class="uk-modal-header"><h2 class="uk-modal-title">Zapytaj o produkt</h2></div>
                <form class="uk-form-stacked" id="ThisFormModal1_3" method="POST" action="insert?tab=2" accept-charset="ISO-8859-1">
                    <div class="uk-grid-medium" uk-grid>
                        <div class="uk-width-1-1">
                            <label class="uk-form-label">Tytuł: </label>
                            <div class="uk-form-controls">
                               <input id="title" name="title" class="uk-input" value="Zapytanie o produkt: ${prodm.name}"
                                       type="text" style="color: black;" disabled/>
                            </div>
                        </div>
                    </div>
                    <div class="uk-grid-medium" uk-grid>
                        <div class="uk-width-3-5">
                            <label class="uk-form-label">Email zwrotny: </label>
                            <div class="uk-form-controls">
                               <input id="mail" name="mail" class="uk-input" value=""
                                       type="text" maxlength="100" placeholder="email@email.com"/>
                            </div>
                        </div>
                        <div class="uk-width-1-1">
                            <div class="uk-form-controls">
                                 <label class="uk-form-label">Treść zapytania:</label>
                                 <textarea id="content" name="content" class="uk-textarea" rows="5" cols="5"
                                           placeholder="Treść... "></textarea>
                            </div>
                        </div>
                        <div class="uk-flex uk-flex-center uk-margin-medium-top">
                            <button class="uk-button uk-button-secondary" type="submit">Wyślij</button> 
                        </div>
                    </div>
                </form>
            </div>
        </div> 
        <c:if test = "${(sessionScope.acc != false)}">                               
        <div id="modal1" uk-modal>
            <div class="uk-modal-dialog uk-modal-body">
                <button class="uk-modal-close-outside" type="button" uk-close></button>
                <div class="uk-modal-header"><h2 class="uk-modal-title">Dodano do koszyka</h2></div>
                    <div class="uk-grid-medium uk-margin-small-top" uk-grid>
                        <div class="uk-width-1-1">
                            <h4>Produkt:</h4>
                            <div class="uk-column-1-2@m">
                                <img src="http://localhost:8080/jewelry/img/CT/<c:out value="${prodm.id}"/>/1.jpg" alt="img" width="200"/>
                                <h3>${prodm.name}</h3>
                            </div>
                            <h4>został pomyślnie dodany do koszyka</h4>
                        </div>
                </div>
                <div class="uk-flex uk-flex-center uk-margin-medium-top uk-child-width-1-2@m uk-child-width-1-2@l uk-child-width-1-1@s" uk-grid>
                    <button class="uk-button uk-button-default uk-modal-close uk-margin-small-bottom@xs" type="button">Kontynuuj&nbsp;zakupy</button>  
                    <button class="uk-button uk-button-secondary" type="button" onclick="window.location.href='basket'"
                            style="margin: 0;">Przejdź do&nbsp;koszyka</button> 
                </div>
            </div>
        </div>
        <script>
            var acc = ${sessionScope.acc};
            if(acc==true){ UIkit.modal("#modal1").show(); }
        </script>
        </c:if>
        <% session.removeAttribute("acc"); %>
    </main> 
   <!--FOOTER-->  
    <jsp:include page="../footer.jsp"/>
  </body>
</html>
