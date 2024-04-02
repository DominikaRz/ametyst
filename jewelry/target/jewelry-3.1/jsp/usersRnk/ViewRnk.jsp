<%-- 
    Document   : ViewRnk
    Created on : 16 wrz 2021, 11:53:12
    Author     : DRzepka
--%>

<%@ page trimDirectiveWhitespaces="true" %> <%--prevent nextLine in document--%>
<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<!DOCTYPE html>
<html>
  <head>
    <jsp:include page="../head.jsp"/>
    <jsp:include page="../form.jsp"/>
    <link rel="stylesheet" type="text/css" href="css/styleRnk.css"/>
    <style>
        .block {
            width: 250px;
            position: absolute;
            bottom: 0;
            top: 0;
            box-shadow: inset 0px 0px 5px 5px #f1eced;
        }
        
    </style>
<!--TITLE-->    
    <title>Ametyst - user</title>
  </head>
  <body>
  <!--MAIN-->
    <main>
      <div class="uk-margin" uk-grid>
        <jsp:include page="../asideRnk.jsp"/>  
        <section class="uk-width-5-6@m uk-margin-medium">
        <c:if test = "${rank==1}">
            <div class="uk-child-width-auto uk-flex uk-flex-middle" uk-grid>
                <h2>${rankUsr.name}</h2>
                <h3><span uk-icon="icon: user; ratio: 1.2"> </span> ${user.name}&nbsp;${user.surname}</h3>
                <h3><span uk-icon="icon: mail; ratio: 1.5"> </span> ${user.email}</h3>
                <h3><span uk-icon="icon: receiver; ratio: 1.5"> </span> ${user.tel}</h3>
            </div>
          <c:if test = "${not empty listHist}">
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
                                        <li><span>Data</span></li>
                                        <li uk-filter-control="sort: data-time; order: desc"><a href="#" uk-icon="icon: arrow-down"></a></li>
                                        <li uk-filter-control="sort: data-time"><a href="#" uk-icon="icon: arrow-up"></a></li>
                                        <li><span>Akcja</span></li>
                                        <li class="uk-active" uk-filter-control><a href="#">Wszystkie</a></li>
                                        <li uk-filter-control="[data-act='1']"><a href="#">Dodawanie</a></li>
                                        <li uk-filter-control="[data-act='3']"><a href="#">Usuwanie</a></li>
                                        <li uk-filter-control="[data-act='2']"><a href="#">Modyfikacja</a></li>
                                        <li uk-filter-control="[data-act='4']"><a href="#">Cofnięcie</a></li>
                                        <li uk-filter-control="[data-act='6']"><a href="#">Ponowienie</a></li>
                                        <li uk-filter-control="[data-act='5']"><a href="#">Logowanie</a></li>
                                        <li uk-filter-control="[data-act='7']"><a href="#">Zdjęcia</a></li>
                                        <li uk-filter-control="[data-act='8']"><a href="#">Zamówienie</a></li>
                                        <li uk-filter-control="[data-act='9']"><a href="#">Użytkownik</a></li>
                                        <li uk-filter-control="[data-act='10']"><a href="#">Towar</a></li>
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
                                        Akcja
                                        <span uk-filter-control="sort: data-act">
                                            <a class="uk-icon-link" href="" uk-icon="icon: arrow-down"></a>
                                        </span>
                                        <span uk-filter-control="sort: data-act; order: desc">
                                            <a class="uk-icon-link" href="" uk-icon="icon: arrow-up"></a>
                                        </span>
                                    </div>
                                </th>
                                <th class="uk-width-medium">Opis</th>
                                <th class="uk-width-auto">
                                    <div class="uk-width-auto uk-text-nowrap">
                                        Data
                                        <span uk-filter-control="sort: data-time">
                                            <a class="uk-icon-link" href="" uk-icon="icon: arrow-down"></a>
                                        </span>
                                        <span uk-filter-control="sort: data-time; order: desc">
                                            <a class="uk-icon-link" href="" uk-icon="icon: arrow-up"></a>
                                        </span>
                                    </div>
                                </th>
                                <th class="uk-width-auto">Modyfikacja</th>
                            </tr>
                        </thead>
                        <tbody class="js-filter1">
                           <c:forEach var="his" items="${listHist}">
                            <tr 
                                data-id="${his.id}" data-usr="${his.idUser}" data-act="${his.idAct}" 
                                data-time="${his.date}"
                            >
                                <td class="fl"><c:out value="${his.id}" />.</td>
                                <td><c:out value="${act[his.idAct-1].name}" /></td>
                                <td><span class="uk-hidden@m">Opis: </span><c:out value="${his.description}" /></td>
                                <td><span class="uk-hidden@m">Data: </span><c:out value="${his.date}" /></td>
                                <td><span class="uk-hidden@m">Modyfikacja: </span>${his.modify}</td>
                            </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
          </c:if>
          <c:if test = "${empty listHist}"> 
              <div class="uk-align-center uk-text-center uk-margin-large-top uk-margin-large-left" style="height: 30vh" uk-grid>
                <h2>Świeżutki użytkownik!</h2> 
                <p>Ta osoba nie posiada jeszcze historii użytkowania aplikacji.</p>
            </div>
          </c:if>
        </c:if>
        <c:if test = "${rank==2}"> 
            <div uk-grid>
                <div class="uk-width-4-5">
                    <div class="uk-width-1-1 uk-hidden@m uk-margin-small-right uk-text-right@m">
                        <div class="uk-text-right@xs"><a href="#menuRnk" uk-toggle uk-navbar-toggle-icon></a></div>
                    </div>
                </div>
            </div>
            <h2>Zamówienie nr <c:out value="${order.id}"/></h2>
            <hr>
            <div class="uk-child-width-1-3@m uk-margin-large-bottom" uk-grid="masonry: true">
                <div> 
                    <h4 class="uk-text-bold">Status:</h4> 
                    <p class="uk-text-bold"><c:out value="${stat.name}"/></p>
                    <p><c:out value="${stat.description}"/></p>
                </div>
                <div> 
                    <h4 class="uk-text-bold">Płatność:</h4>
                    <p><c:out value="${pay.category}"/>: <c:out value="${pay.name}"/></p>
                    <p>Suma: <c:out value="${Math.round((order.sum+deliv.price)*100.00)/100.00}"/>&nbsp;zł</p>
                </div>
                <div> 
                    <h4 class="uk-text-bold">Dostawa:</h4>
                    <p><c:out value="${deliv.name}"/></p>
                    <c:if test = "${((order.deliv_nr!=0)&&(order.deliv_nr!=null))}">
                    <p>Nr przesyłki: <c:out value="${order.deliv_nr}"/></p>
                    </c:if>
                </div>
            </div>
            <div class="uk-child-width-1-4@m uk-margin-medium-bottom" uk-grid="masonry: true">
                <div>
                    <c:choose>
                        <c:when test = "${user.name!=null}">
                            <h4 class="uk-text-bold">Dane osobowe: </h4>
                            <p class="uk-text-bold"><c:out value="${user.name}"/>&nbsp;<c:out value="${user.surname}"/></p>
                            <p>tel: <c:out value="${user.tel}"/></p>
                            <p>mail: <c:out value="${user.email}"/></p>
                        </c:when>
                        <c:otherwise>
                            <h4 class="uk-text-bold">Dane osobowe: </h4>
                            <p class="uk-text-bold"><c:out value="${order.name}"/>&nbsp;<c:out value="${order.surname}"/></p>
                            <p>tel: <c:out value="${order.telephone}"/></p>
                            <p>mail: <c:out value="${order.email}"/></p> 
                        </c:otherwise>
                    </c:choose>
                </div>
                <div>
                    <h4 class="uk-text-bold">Adres:</h4>
                    <p>ul. <c:out value="${userm.adr_str}"/>&nbsp;<c:out value="${userm.adr_nr}"/></p>
                    <p><c:out value="${userm.adr_code}"/>&nbsp;<c:out value="${userm.adr_town}"/></p>
                    <p><c:out value="${userm.adr_state}"/>, <c:out value="${userm.adr_count}"/></p>
                </div>
                <div>
                <c:if test = "${userm.firm==true}"> 
                    <h4 class="uk-text-bold">Firma:</h4>
                    <p class="uk-text-bold"><c:out value="${userm.firm_name}"/></p>
                    <p>NIP. <c:out value="${userm.firm_nip}"/></p>
                    <p>mail: <c:out value="${userm.firm_email}"/></p>
                    <p>tel: <c:out value="${userm.firm_tel}"/></p>
                </c:if>
                </div>
                <div>
                    <div class="uk-flex uk-flex-right uk-flex-column uk-margin-large-right" >
                        <button class="uk-button uk-button-secondary" uk-toggle="target: #modal2">Edytuj</button>
                    </div>
                </div>
            </div>
            <div> 
                <div id="products">
                    <table class="uk-table uk-table-responsive uk-table-hover uk-table-divider uk-margin-large-bottom">
                        <thead>
                            <tr>
                                <th class="uk-width-small">Lp.</th>
                                <th>Produkt</th>
                                <th class="uk-width-small">Ilość</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="prod" items="${listProdm}" varStatus="st">
                            <tr>
                                <td class="fl"><c:out value="${st.index+1}"/>.</td>
                                <td>
                                    <span class="uk-margin-small-right">
                                        <img src="http://localhost:8080/jewelry/img/CT/<c:out value="${prod.id}"/>/1.jpg" 
                                             alt="img" width="50"/></span> <c:out value="${prod.name}"/>
                                <td><span class="uk-hidden@m">Ilość: </span><c:out value="${listOrderp[st.index].quantity}"/></td>
                            </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
            <div class="uk-flex uk-flex-right uk-margin-large-right" >
                <h4 class="uk-text-bold uk-margin-medium-right">Komentarz: </h4>
                <c:choose>
                    <c:when test = "${(order.comments!=null)&&(order.comments!='')}">
                        <p><c:out value="${order.comments}"/></p>
                    </c:when>
                    <c:otherwise>
                        <p>brak komentarza</p>
                    </c:otherwise>
                </c:choose>
            </div>
        <div id="modal2" uk-modal>
            <div class="uk-modal-dialog uk-modal-body">
                <button class="uk-modal-close-outside" type="button" uk-close></button>
                <div class="uk-modal-header"><h2 class="uk-modal-title">Zamówienie nr. <c:out value="${order.id}"/></h2></div>
                <form class="uk-form-stacked" id="ThisFormModalOrd" method="POST" action="modifyOrder" accept-charset="ISO-8859-1">
                    <div class="uk-margin-medium-top" uk-grid>
                        <div class="uk-width-1-3@m"> 
                            <h4 class="uk-text-bold">Status:</h4> 
                            <select id="status"  name="status" class="uk-select uk-form-width-medium">
                                <c:forEach var="status" items="${listStat}" varStatus="st">
                                <c:if test = "${(status.id>=stat.id)}">
                                <option value="<c:out value="${status.id}"/>" 
                                        <c:if test = "${(status.id==stat.id)}">selected</c:if>>
                                    <c:out value="${status.name}"/>
                                </option>
                                </c:if>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="uk-width-1-2@m"> 
                            <h4 class="uk-text-bold">Wysyłka:</h4>
                            <input id="delivery_nr" name="delivery_nr" type="text" class="uk-input" placeholder="Numer przesyłki"
                                   value="<c:out value="${order.deliv_nr}"/>">
                            <p class="uk-margin-small-top uk-margin-small-left"></p>
                        </div>                                
                        <div class="uk-width-1-6@m uk-margin-medium-top">
                            <button class="uk-button uk-button-secondary" name="id" value="${order.id}" type="submit">Zmień</button> 
                        </div>
                    </div>
                </form>
            </div>
        </div>
        </c:if> 
        <c:if test = "${rank==3}">
          <c:choose>
            <c:when test = "${t==1}">
                <h2>Recenzja nr <c:out value="${rev.id}"/></h2>
                <hr>
                <div class="uk-child-width-1-3@m" uk-grid="masonry: true">
                    <div> 
                        <h4 class="uk-text-bold">Osoba wystawiająca: </h4>
                        <p><c:out value="${names.name}"/>&nbsp;<c:out value="${names.surname}"/></p>
                    </div>
                    <div> 
                        <h4 class="uk-text-bold">Data wystawienia recenzji:</h4> 
                        <p><c:out value="${rev.publication}"/></p>
                    </div>
                    <div> 
                        <h4 class="uk-text-bold">Gwazdki:</h4>
                        <p>
                            <c:forEach var="i" begin="1" end="${rev.stars}" step="1" varStatus ="status">
                            <i uk-icon="star" class="prodstar" style="color: #ffff00;"></i>
                            </c:forEach>
                        </p>
                    </div>
                </div>
                <div class="uk-child-width-1-2@m" uk-grid="masonry: true">
                    <div> 
                        <h4 class="uk-text-bold">Recenzja: </h4>
                        <p><c:out value="${rev.content}"/></p>
                    </div>
                    <div> 
                        <h4 class="uk-text-bold">Dotyczy produktu: </h4>
                        <p><a href="product?id=<c:out value="${productM.id}"/>">
                            <img src="http://localhost:8080/jewelry/img/CT/<c:out value="${productM.id}"/>/1.jpg" 
                                 alt="img" width="70" class="uk-margin-remove-adjacent uk-margin-medium-right@xs fl"/>
                            <c:out value="${productM.name}"/>
                        </a></p>
                    </div>
                    <div>
                        <c:choose>
                            <c:when test="${rev.reply!=null}">
                               <h4 class="uk-text-bold uk-hidden@xs">Odpowiedź z dnia <c:out value="${rev.response}"/> </h4>
                               <h4 class="uk-text-bold uk-hidden@m">Odpowiedź (<c:out value="${rev.response}"/> )</h4>
                               <p><c:out value="${rev.reply}"/></p> 
                            </c:when>
                            <c:otherwise>
                               <h4 class="uk-text-bold">Brak odpowiedzi</h4> 
                            </c:otherwise>
                        </c:choose>
                    </div>
                    <div>
                        <div class="uk-flex uk-flex-right uk-flex-column uk-margin-large-right" >
                            <button class="uk-button uk-button-secondary" uk-toggle="target: #modal5_1">
                                Edytuj odpowiedź</button>
                        </div>
                    </div>
                </div>
                <div id="modal5_1" uk-modal>
                   <div class="uk-modal-dialog uk-modal-body">
                       <button class="uk-modal-close-outside" type="button" uk-close></button>
                       <div class="uk-modal-header"><h2 class="uk-modal-title">Recenzja nr. <c:out value="${rev.id}"/></h2></div>
                       <form class="uk-form-stacked" id="ThisFormModalRew" method="POST" 
                             action="modifyReview?id=${rev.id}" accept-charset="ISO-8859-1">
                           <div class="uk-margin-medium-top" uk-grid>
                               <div class="uk-width-1-1@m"> 
                                   <h4 class="uk-text-bold">Odpowiedź:</h4> 
                                   <div class="uk-margin">
                                       <textarea class="uk-textarea" rows="5" name="answer" id="answer" 
                                         placeholder="Odpowiedź na wystawioną recenzję"><c:out value="${rev.reply}"/></textarea>
                                   </div>
                               </div>                        
                               <div class="uk-width-1-6@m uk-margin-medium-top">
                                   <button class="uk-button uk-button-secondary" type="submit">Opublikuj</button> 
                               </div>
                           </div>
                       </form>
                   </div>
                </div>
            </c:when>
            <c:when test = "${t==2}">
                <h2>Produkt nr <c:out value="${prod.id}"/></h2>
                <hr>
                <div class="uk-child-width-1-2@m" uk-grid="masonry: true">
                    <div>
                        <img src="http://localhost:8080/jewelry/img/CT/<c:out value="${prod.id}"/>/1.jpg" 
                             alt="img" width="250"/>
                    </div>
                    <div> 
                        <h3>Nazwa przedmiotu:</h3> 
                        <hr>
                        <p><c:out value="${prod.name}"/></p>
                    </div>
                    <div> 
                        <h3>OPIS PRODUKTU</h3>
                        <hr>
                        <h5 class="uk-text-bold"><c:out value="${prod.description}"/></h5>
                    </div>
                    <div>
                        <h3>Dane produktu</h3>
                        <hr>
                        <p> Materiał: <c:out value="${fabr.name}"/></p>
                        <c:if test = "${(prod.height != 0.00)}">
                            <p> Wysokość: <c:out value="${prod.height}"/> mm</p></c:if>
                        <c:if test = "${(prod.width != 0.00)}">
                            <p> Szerokość: <c:out value="${prod.width}"/> mm</p></c:if>
                        <c:if test = "${(prod.lenght != 0.00)}">
                            <p> Długość: <c:out value="${prod.lenght}"/> mm</p></c:if>
                        <c:if test = "${(prod.hole != 0.00)}">
                            <p> Wielkość otworu: <c:out value="${prod.hole}"/> mm</p></c:if>
                        <c:if test = "${(prod.weight != 0.00)}">
                            <p> Waga: <c:out value="${prod.weight}"/> g</p></c:if>
                        <p> Kształt: <c:out value="${shap.name}"/></p>
                        <c:if test = "${(prod.diameter != 0.00)}">
                            <p> Średnica: <c:out value="${prod.diameter}"/> mm</p></c:if>
                        <p> Kolor: <c:out value="${colo.name}"/></p>
                        <p> Ilość: <c:out value="${prod.quantity}"/></p>
                        <p class="uk-margin-top-small"> Źródło: <a href="<c:out value="${prod.source}"/>"><c:out value="${prod.source}"/></a></p>
                    </div>
                    <div>
                        <div class="uk-flex uk-flex-right uk-flex-column uk-margin-large-right" >
                            <button class="uk-button uk-button-secondary" uk-toggle="target: #modal5_2">
                                Edytuj opis produktu</button>
                        </div>
                    </div>
                </div>
                <div id="modal5_2" uk-modal>
                   <div class="uk-modal-dialog uk-modal-body">
                       <button class="uk-modal-close-outside" type="button" uk-close></button>
                       <div class="uk-modal-header"><h2 class="uk-modal-title">Produkt nr. <c:out value="${prod.id}"/></h2></div>
                       <form class="uk-form-stacked" id="ThisFormModalPrdC" method="POST" 
                             action="modifyProduct?id=${prod.id}" accept-charset="ISO-8859-1">
                           <div class="uk-margin-medium-top" uk-grid>
                               <div class="uk-width-1-1@m"> 
                                   <h4 class="uk-text-bold">Opis produktu: </h4> 
                                   <div class="uk-margin">
                                       <textarea class="uk-textarea" rows="5" name="description" id="description" 
                                         placeholder="Opis produktu..."><c:out value="${prod.description}"/></textarea>
                                   </div>
                               </div>                        
                               <div class="uk-width-1-6@m uk-margin-medium-top">
                                   <button class="uk-button uk-button-secondary" type="submit">Opublikuj</button> 
                               </div>
                           </div>
                       </form>
                   </div>
                </div>
            </c:when>
            <c:when test = "${t==3}">
                <h2>Kategoria nr <c:out value="${cat.id}"/></h2>
                <hr>
                <div class="uk-child-width-1-2@m" uk-grid="masonry: true">
                    <div> 
                        <h3>Nazwa kategorii</h3> 
                        <p><c:out value="${cat.name}"/></p>
                    </div>
                    <div> 
                        <h3>Opis kategorii ${s}</h3>
                        <hr>
                        <h5 class="uk-text-bold"><c:out value="${cat.desc}"/></h5>
                    </div>
                    <div>
                        <div class="uk-flex uk-flex-right uk-flex-column uk-margin-large-right" >
                            <button class="uk-button uk-button-secondary" uk-toggle="target: #modal5_3">
                                Edytuj opis kategori</button>
                        </div>
                    </div>
                </div>
                <div id="modal5_3" uk-modal>
                   <div class="uk-modal-dialog uk-modal-body">
                       <button class="uk-modal-close-outside" type="button" uk-close></button>
                       <div class="uk-modal-header"><h2 class="uk-modal-title">Kategoria nr. <c:out value="${cat.id}"/></h2></div>
                       <form class="uk-form-stacked" id="ThisFormModalCatC" method="POST" 
                             action="modifyCategory?id=${cat.id}" accept-charset="ISO-8859-1">
                           <div class="uk-margin-medium-top" uk-grid>
                               <div class="uk-width-1-1@m"> 
                                   <h4 class="uk-text-bold">Opis Kategorii </h4> 
                                   <div class="uk-margin">
                                       <textarea class="uk-textarea" rows="5" name="description" id="description" 
                                         placeholder="Opis kategorii..."><c:out value="${cat.desc}"/></textarea>
                                   </div>
                               </div>                        
                               <div class="uk-width-1-6@m uk-margin-medium-top">
                                   <button class="uk-button uk-button-secondary" type="submit">Opublikuj</button> 
                               </div>
                           </div>
                       </form>
                   </div>
                </div>
            </c:when>
            <c:when test = "${t==4}">
                <h2>Kategoria nr <c:out value="${tag.id}"/></h2>
                <hr>
                <div class="uk-child-width-1-2@m" uk-grid="masonry: true">
                    <div> 
                        <h3>Nazwa kategorii</h3> 
                        <p><c:out value="${tag.name}"/></p>
                    </div>
                    <div> 
                        <h3>Opis kategori</h3>
                        <hr>
                        <h5 class="uk-text-bold"><c:out value="${tag.desc}"/></h5>
                    </div>
                    <div>
                        <div class="uk-flex uk-flex-right uk-flex-column uk-margin-large-right" >
                            <button class="uk-button uk-button-secondary" uk-toggle="target: #modal5_3">
                                Edytuj opis tagu</button>
                        </div>
                    </div>
                </div>
                <div id="modal5_3" uk-modal>
                   <div class="uk-modal-dialog uk-modal-body">
                       <button class="uk-modal-close-outside" type="button" uk-close></button>
                       <div class="uk-modal-header"><h2 class="uk-modal-title">Tag nr. <c:out value="${tag.id}"/></h2></div>
                       <form class="uk-form-stacked" id="ThisFormModalTagC" method="POST" 
                             action="modifyTag?id=${tag.id}" accept-charset="ISO-8859-1">
                           <div class="uk-margin-medium-top" uk-grid>
                               <div class="uk-width-1-1@m"> 
                                   <h4 class="uk-text-bold">Opis Kategorii </h4> 
                                   <div class="uk-margin">
                                       <textarea class="uk-textarea" rows="5" name="description" id="description" 
                                         placeholder="Opis tagu..."><c:out value="${tag.desc}"/></textarea>
                                   </div>
                               </div>                        
                               <div class="uk-width-1-6@m uk-margin-medium-top">
                                   <button class="uk-button uk-button-secondary" type="submit">Opublikuj</button> 
                               </div>
                           </div>
                       </form>
                   </div>
                </div>
            </c:when>
            <c:otherwise>
                <h4 class="uk-text-bold">Brak dostępu! </h4>
            </c:otherwise>
          </c:choose>
        </c:if>
        <c:if test = "${rank==4}"> 
            <div class="uk-width-4-5">
                <div class="uk-width-1-1 uk-hidden@m uk-margin-small-right uk-text-right@m">
                    <div class="uk-text-right@xs"><a href="#menuRnk" uk-toggle uk-navbar-toggle-icon></a></div>
                </div>
            </div>
            <h2>Produkt nr <c:out value="${prodm.id}"/></h2>
            <hr>
            <h2 class="uk-h2"><c:out value="${prodm.name}"/></h2>
            <div class="uk-margin-large-top uk-child-width-1-2@m" uk-grid>
                <div>
                    <div class="" uk-slideshow="animation: fade">
                        <div class="uk-position-relative" uk-lightbox="animation: fade">
                            <ul class="uk-slideshow-items uk-margin">
                                <c:forEach var="i" begin="1" end="${prodm.images}" step="1" varStatus ="status">
                                <li id="imgs">
                                    <a class="uk-inline" href="http://localhost:8080/jewelry/img/CT/<c:out value="${prodm.id}"/>/<c:out value="${i}"/>.jpg" 
                                       data-caption="1">
                                        <img src="http://localhost:8080/jewelry/img/CT/<c:out value="${prodm.id}"/>/<c:out value="${i}"/>.jpg" 
                                             onerror="this.style.visibility = 'hidden'" alt="<c:out value="${i-1}"/>">
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
                                        <img src="http://localhost:8080/jewelry/img/CT/<c:out value="${prodm.id}"/>/<c:out value="${i}"/>.jpg" 
                                             class="sdimg" onerror="this.style.visibility = 'hidden'"  alt="<c:out value="${i-1}"/>">
                                    </a>
                                </li>
                                </c:forEach>
                            </ul>
                        </div>
                    </div>
                    <div class="uk-flex uk-flex-right uk-flex-column uk-margin-large-right uk-margin-medium-bottom" >
                            <button class="uk-button uk-button-secondary" uk-toggle="target: #modal4_1">
                                Edytuj obrazy</button>
                        </div>
                </div>
                <div class="uk-child-width-1-2@m uk-margin-medium-bottom" uk-grid="masonry: true">
                    <div> 
                        <h4 class="uk-text-bold">Cena produktu: </h4>
                        <p><c:out value="${prodm.price}"/>&nbsp;zł</p>
                    </div>
                    <div> 
                        <h4 class="uk-text-bold">Ilość na stanie:</h4> 
                        <p><c:out value="${prodm.quantityState}"/></p>
                    </div>
                    <div> 
                        <h4 class="uk-text-bold">Data utworzenia: </h4>
                        <p><c:out value="${prodm.create}"/> </p>
                    </div>
                    <div> 
                        <h4 class="uk-text-bold">Kategoria i tag: </h4>
                        <p><c:out value="${ct.nameCat}"/>, <c:out value="${ct.nameTag}"/></p>
                    </div>
                    <div> 
                        <h4 class="uk-text-bold">Data ponowienia towaru: </h4>
                        <p><c:out value="${prodm.restock}"/> </p>
                    </div>
                    <div>
                        <div class="uk-flex uk-flex-right uk-flex-column uk-margin-large-right@m" >
                            <button class="uk-button uk-button-secondary" uk-toggle="target: #modal4">
                                Edytuj</button>
                        </div>
                    </div>
                </div>
            </div>
            <div>
                <h4>Opis</h4>
                <hr>
                <h5><c:out value="${prodm.description}"/></h5>
                <h4>DANE PRODUKTU</h4>
                <hr>
                <p> <span class="uk-text-bold">Materiał:</span> <c:out value="${fabr.name}"/></p>
                <p><span class="uk-text-bold">Wysokość:</span>  <c:out value="${prodm.height}"/> mm</p>
                <p><span class="uk-text-bold">Szerokość:</span>   <c:out value="${prodm.width}"/> mm</p>
                <p><span class="uk-text-bold">Długość:</span>   <c:out value="${prodm.lenght}"/> mm</p>
                <p><span class="uk-text-bold">Wielkość otworu:</span>  <c:out value="${prodm.hole}"/> mm</p>
                <p><span class="uk-text-bold">Waga:</span>   <c:out value="${prodm.weight}"/> g</p>
                <p><span class="uk-text-bold">Kształt:</span>   <c:out value="${shap.name}"/></p>
                <p><span class="uk-text-bold">Średnica:</span>   <c:out value="${prodm.diameter}"/> mm</p>
                <p><span class="uk-text-bold">Kolor:</span>   <c:out value="${colo.name}"/></p>
                <p><span class="uk-text-bold">Ilość w opakowaniu:</span>   <c:out value="${prodm.quantity}"/></p>
                <p class="uk-margin-top-small"><span class="uk-text-bold">Źródło:</span>   <a href="<c:out value="${prodm.source}"/>"><c:out value="${prodm.source}"/></a></p>
            </div>
            <div id="modal4" uk-modal>
               <div class="uk-modal-dialog uk-modal-body">
                   <button class="uk-modal-close-outside" type="button" uk-close></button>
                   <div class="uk-modal-header"><h2 class="uk-modal-title">Produkt nr. <c:out value="${prodm.id}"/></h2></div>
                   <form class="uk-form-stacked" id="ThisFormModalPrd" method="POST" 
                         action="updateProduct?id=<c:out value="${prodm.id}"/>" accept-charset="ISO-8859-1">
                       <div class="uk-margin-medium-top" uk-grid>
                            <div class="uk-grid-small" uk-grid>
                                <div class="uk-width-4-5@m">
                                    <div class="uk-form-controls">
                                        <label class="uk-form-label">Nazwa produktu:</label>
                                       <input id="prod_name" name="prod_name" class="uk-input" value="<c:out value="${prodm.name}"/>"
                                               type="text"/>
                                    </div>
                                </div>
                                <div class="uk-width-1-5@ uk-width-1-2 uk-hidden@xs">
                                    <div class="uk-form-controls">
                                        <label class="uk-form-label">Ilość obrazów:</label>
                                       <input id="prod_img" name="prod_img" class="uk-input"  value="<c:out value="${prodm.images}"/>"
                                               type="text" disabled/>
                                    </div>
                                </div>
                                <div class="uk-width-3-5@m uk-width-1-1">
                                    <div class="uk-form-controls">
                                        <label class="uk-form-label">Kategoria | Tag:</label>
                                        <select id="prod_ct" name="prod_ct" class="uk-select" type="text" maxlength="40">
                                            <c:forEach var="CT" items="${listCT}">
                                                   <option value="<c:out value="${CT.id}"/>"
                                                     <c:if test="${CT.id==prodm.idCattag}">selected</c:if>>
                                                       <c:out value="${CT.nameCat}"/> | <c:out value="${CT.nameTag}"/>
                                                   </option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                </div>
                                <div class="uk-width-1-5@m uk-width-1-2">
                                    <div class="uk-form-controls">
                                        <label class="uk-form-label">Cena (zł):</label>
                                        <input id="prod_prc" name="prod_prc" class="uk-input"  value="<c:out value="${prodm.price}"/>"
                                               type="number" step="0.01"/>
                                    </div>
                                </div>
                               <div class="uk-width-1-5@m uk-width-1-2">
                                    <div class="uk-form-controls">
                                        <label class="uk-form-label">Promocja (%):</label>
                                       <input id="prodm_discount" name="prodm_discount" class="uk-input" placeholder="Przecena w %..."
                                               value="<c:out value="${prodm.discount}"/>" type="number" step="1"/>
                                    </div>
                                </div>
                            </div>       
                            <div class="uk-grid-small" uk-grid>
                                <div class="uk-width-1-5@m uk-width-1-2">
                                    <div class="uk-form-controls">
                                        <label class="uk-form-label">Wysokość (mm):</label>
                                       <input id="prodm_height" name="prodm_height" class="uk-input" placeholder="Wysokość produktu..."
                                               value="<c:out value="${prodm.height}"/>" type="number" step="0.01"/>
                                    </div>
                                </div>
                                <div class="uk-width-1-5@m uk-width-1-2">
                                    <div class="uk-form-controls">
                                        <label class="uk-form-label">Szerokość (mm):</label>
                                       <input id="prodm_width" name="prodm_width" class="uk-input" placeholder="Szerokość produktu..."
                                               value="<c:out value="${prodm.width}"/>" type="number" step="0.01"/>
                                    </div>
                                </div>
                                <div class="uk-width-1-5@m uk-width-1-2">
                                    <div class="uk-form-controls">
                                        <label class="uk-form-label">Długość (mm):</label>
                                       <input id="prodm_lenght" name="prodm_lenght" class="uk-input" placeholder="Długość produktu..."
                                               value="<c:out value="${prodm.lenght}"/>" type="number" step="0.01"/>
                                    </div>
                                </div>
                                <div class="uk-width-1-5@m uk-width-1-2">
                                    <div class="uk-form-controls">
                                        <label class="uk-form-label">Otwór(mm):</label>
                                       <input id="prodm_hole" name="prodm_hole" class="uk-input" placeholder="Wielkość dziury produktu..."
                                               value="<c:out value="${prodm.hole}"/>" type="number" step="0.01"/>
                                    </div>
                                </div>
                                <div class="uk-width-1-5@m uk-width-1-2">
                                    <div class="uk-form-controls">
                                        <label class="uk-form-label">Waga (g):</label>
                                       <input id="prodm_weight" name="prodm_weight" class="uk-input" placeholder="Waga produktu..."
                                               value="<c:out value="${prodm.weight}"/>" type="number" step="0.01"/>
                                    </div>
                                </div> 
                                <div class="uk-width-1-5@m uk-width-1-2">
                                    <div class="uk-form-controls">
                                        <label class="uk-form-label">Średnica (mm): </label>
                                       <input id="prodm_diameter" name="prodm_diameter" class="uk-input" placeholder="Średnica produktu..."
                                               value="<c:out value="${prodm.diameter}"/>" type="number" step="0.01"/>
                                    </div>
                                </div>
                                <div class="uk-width-2-5@m">
                                    <div class="uk-form-controls">
                                        <label class="uk-form-label">Materiał:</label>
                                        <select id="prodm_fabr" name="prodm_fabr" class="uk-select"
                                            <c:forEach var="Fabr" items="${listFbr}">
                                                   <option value="<c:out value="${Fabr.id}"/>"
                                                     <c:if test="${Fabr.id==prodm.idFabr}"> selected</c:if>>
                                                       <c:out value="${Fabr.name}"/> 
                                                   </option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                </div>
                                <div class="uk-width-2-5@m">
                                    <div class="uk-form-controls">
                                        <label class="uk-form-label">Kształt: </label>
                                        <select id="prodm_shap" name="prodm_shap" class="uk-select"
                                            <c:forEach var="shp" items="${listShp}">
                                                    <option value="<c:out value="${shp.id}"/>"
                                                     <c:if test="${shp.id==prodm.idShap}"> selected</c:if>>
                                                       <c:out value="${shp.name}"/>
                                                   </option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                </div>
                                <div class="uk-width-1-5@m">
                                    <div class="uk-form-controls">
                                        <label class="uk-form-label">Kolor:</label>
                                        <select id="prodm_color" name="prodm_color" class="uk-select"
                                            <c:forEach var="col" items="${listCol}">
                                                   <option value="<c:out value="${col.id}"/>"
                                                <c:if test="${col.id==prodm.idCol}"> selected</c:if>>
                                                       <c:out value="${col.name}"/>
                                                   </option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                </div>
                                <div class="uk-width-1-1">
                                    <div class="uk-form-controls">
                                        <label class="uk-form-label">Opis produktu:</label>
                                       <textarea id="prodm_desc" name="prodm_desc" class="uk-textarea" rows="5" cols="5"
                                                 placeholder="Opis produktu... "><c:out value="${prodm.description}"/></textarea>
                                    </div>
                                </div>
                            </div>
                            <div class="uk-grid-small" uk-grid> 
                                <div class="uk-width-2-5@m uk-width-1-2">
                                    <div class="uk-form-controls">
                                        <label class="uk-form-label">Ilość na stanie magazynu:</label>
                                        <input id="prod_state" name="prod_state" class="uk-input" placeholder="Ilość na stanie..."
                                              value="<c:out value="${prodm.quantityState}"/>"  type="number" step="1"/>
                                    </div>
                                </div>
                                <div class="uk-width-2-5@m uk-width-1-2">
                                    <div class="uk-form-controls">
                                        <label class="uk-form-label">Ilość w opakowaniu:</label>
                                       <input id="prod_quant" name="prod_quant" class="uk-input" placeholder="Ilość w opakowaniu..."
                                               value="<c:out value="${prodm.quantity}"/>" type="number" step="1"/>
                                    </div>
                                </div>
                                <div class="uk-width-1-1@m">
                                    <div class="uk-form-controls">
                                        <label class="uk-form-label">Źródło produktu:</label>
                                        <textarea id="prodm_src" name="prodm_src" class="uk-textarea" rows="2" 
                                                  placeholder="Źródło produktu... "><c:out value="${prodm.source}"/></textarea>
                                    </div>
                                </div>
                            </div>
                            <div class="uk-width-1-6@m uk-margin-medium-top">
                                <button class="uk-button uk-button-secondary" type="submit">Opublikuj</button> 
                            </div>
                       </div>
                   </form>
               </div>
            </div> 
            <style>
                .uk-checkbox, .uk-checkbox:focus{
                    height: 10vh !important;
                    width: 11vw !important;
                    margin-left: 0.75vw !important;
                    background-size: 5vw !important;
                }
                .uk-checkbox:checked:focus{
                    background-color: #fff !important;
                    border: 1px solid #eeeded;
                }
                #filesimg { border: 1px dashed #000; }
                .uk-subnav-pill>.uk-active>a{ background-color: #361251; color: #fff; }
            </style>
            <div id="modal4_1" uk-modal>
               <div class="uk-modal-dialog uk-modal-body">
                    <button class="uk-modal-close-outside" type="button" uk-close></button>
                    <div class="uk-modal-header"><h2 class="uk-modal-title">Wybierz obraz</h2></div>
                    <ul class="uk-subnav uk-subnav-pill" uk-switcher="animation: uk-animation-fade">
                        <li><a href="#">Dodawanie</a></li>
                        <li><a href="#">Usuwanie</a></li>
                    </ul>
                    <ul class="uk-switcher uk-margin">                
                        <li>
                            <form class="uk-form-stacked" id="ThisFormModal" method="POST" 
                                action="upload?id=<c:out value="${prodm.id}"/>" accept-charset="ISO-8859-1" enctype="multipart/form-data"> 
                              <div class="uk-margin-medium-top" uk-grid>
                                  <div class="uk-width-1-1@m">  
                                      <div class="uk-margin" uk-margin>
                                        <div class="uk-upload-box">
                                            <div id="error-alert-file-upload" class="uk-alert-danger uk-margin-top uk-hidden" uk-alert>
                                                    <p id="error-messages-fileupload"></p>
                                            </div>
                                            <div id="filesimg" class="js-upload uk-placeholder uk-text-center">
                                                <span uk-icon="icon: cloud-upload"></span>
                                                <span class="uk-text-middle uk-margin-small-left">Upuść pliki tutaj lub skorzystaj z </span>
                                                <div uk-form-custom>
                                                    <input type="file" name="fileToUpload" accept="image/png, .jpg, .gif, .jfif, .jpeg" multiple>
                                                    <span class="uk-link">tego linka</span>
                                                </div>
                                                <ul id="preview" class="uk-list uk-grid-match uk-child-width-1-2 uk-child-width-1-4@l uk-child-width-1-5@xl uk-text-center" uk-grid uk-scrollspy="cls: uk-animation-scale-up; target: .list-item; delay: 80"></ul>
                                            </div>
                                        </div>
                                        <div class="uk-width-3-4@m uk-margin-medium-top">
                                            <button class="uk-button uk-button-secondary" type="submit">Zatwierdź pliki</button> 
                                        </div>
                                      </div>
                                  </div>
                              </div>
                            </form>
                            <script>
                            function imgPreviewLi(readerResult, filename) {
                                const li = document.createElement("li");
                                const div = document.createElement("div");
                                const img = document.createElement("img");
                                const span = document.createElement("span");

                                li.className = "list-item uk-margin-medium-top";
                                div.className = "uk-cover-container";
                                img.className = "delete-img-preview";
                                img.setAttribute("id", "img-preview-responsive");
                                img.setAttribute("src", readerResult);
                                img.setAttribute("data-name", filename);
                                img.setAttribute("alt", "file-image-preview");
                                span.className = "uk-text-meta uk-text-break file-upload-name";
                                span.textContent = filename;

                                div.append(img);
                                li.append(div, span);
                                return li;
                            }

                            function previewMultipleFiles(files, fileInput, preview, alert, alertMessage) {
                                    const acceptedDocMimes = ["application/pdf", "image/png", "image/jpeg"];
                              const docFiles = [...files]

                                    docFiles.forEach(file => {
                                            const size = file["size"];
                                            const fileType = file["type"];

                                            if (docFiles.length !== 0) {
                                                while(preview.firstChild) {
                                                    preview.removeChild(preview.firstChild);
                                                }	
                                            }

                                            if (size > 2000000) {
                                                alertMessage.textContent =
                                                        "Sorry, one or more of your files has exceeded the file size limit of 2MB.";
                                                alertMessage.classList.add("uk-text-danger");
                                                alert.classList.remove("uk-hidden");
                                                preview.innerHTML = "";
                                                fileInput.files = [];
                                                return false;
                                            }

                                            if (acceptedDocMimes.includes(fileType)) {
                                                alertMessage.textContent = "";
                                                alert.classList.add("uk-hidden");
                                                fileInput.files = files;

                                                // console.log(fileInput.files)

                                                const reader = new FileReader();
                                                reader.onload = () => { 
                                                        let filename = file["name"];
                                                        let imgPreview = imgPreviewLi(reader.result, filename);
                                                        preview.append(imgPreview);
                                                };
                                                reader.readAsDataURL(file);
                                            } else {
                                                alertMessage.textContent = "Sorry, your file type is not allowed.";
                                                alertMessage.classList.add("uk-text-danger");
                                                alert.classList.remove("uk-hidden");
                                                preview.innerHTML = "";
                                                fileInput.files = [];
                                            }
                                    });
                            }

                            function previewSingleFile(files, fileInput, preview, alert, alertMessage) {	
                              const acceptedDocMimes = ["application/pdf", "image/png", "image/jpeg"];
                              const size = files[0]["size"];
                              const fileType = files[0]["type"];
                              let filename = files[0]["name"];

                              if (files[0].length !== 0) {
                                while(preview.firstChild) {
                                    preview.removeChild(preview.firstChild);
                                }
                              }

                            if (size > 2000000) {
                              alertMessage.textContent =
                                "Niestety możesz przesyłać pliki do maksymalnej wielkości wymoszącej 2MB.";
                              alertMessage.classList.add("uk-text-danger");
                              alert.classList.remove("uk-hidden");
                              preview.innerHTML = "";
                                          fileInput.files = [];
                              console.log(`${size} is more than 2 mb`);
                              return false;
                            }

                            if (acceptedDocMimes.includes(fileType)) {
                              alertMessage.textContent = "";
                              alert.classList.add("uk-hidden");

                              const reader = new FileReader();
                              reader.onload = () => {
                                let imgPreview = imgPreviewLi(reader.result, filename);
                                preview.append(imgPreview);
                              };
                              reader.readAsDataURL(files[0]);

                            } else {
                              alertMessage.textContent = "Złe rozszerzenie pliku. Spróbuj PNG, JPG lub GIF.";
                              alertMessage.classList.add("uk-text-danger");
                              alert.classList.remove("uk-hidden");
                              preview.innerHTML = "";
                                          fileInput.files = [];
                              console.log(`${fileType} is not allowed`);
                            }
                          }

                            let bar = document.getElementById("js-progressbar");
                            UIkit.upload(".js-upload", {
                              url: "",
                              multiple: true,
                              beforeSend: function() {},
                              beforeAll: function() {
                                const files = arguments[1];
                                const jsUploadEl = arguments[0].$el;
                                const fileInput = jsUploadEl.querySelector(".uk-form-custom>input");
                                const preview = jsUploadEl.querySelector("#preview");
                                const alert = jsUploadEl.parentElement.querySelector(
                                  ".uk-upload-box>#error-alert-file-upload"
                                );
                                const alertMessage = jsUploadEl.parentElement.querySelector(
                                  ".uk-upload-box>#error-alert-file-upload>p"
                                );
                                let multiple = false;
                                if (fileInput.hasAttribute("multiple")) {
                                  multiple = true;
                                    previewMultipleFiles(files, fileInput, preview, alert, alertMessage);
                                } else {
                                  previewSingleFile(files, fileInput, preview, alert, alertMessage);
                                }
                              },
                              load: function() {},
                              error: function() {},
                              complete: function() {},
                              loadStart: function(e) {
                                bar.removeAttribute("hidden");
                                bar.max = e.total;
                                bar.value = e.loaded;
                              },
                              progress: function(e) {
                                bar.max = e.total;
                                bar.value = e.loaded;
                              },
                              loadEnd: function(e) {
                                bar.max = e.total;
                                bar.value = e.loaded;
                              },
                              completeAll: function() {
                                setTimeout(function() {
                                  bar.setAttribute("hidden", "hidden");
                                }, 1000);
                              }
                            });
                            //source: https://codepen.io/kent_iyo/pen/VwLbvmg
                        </script>
                        </li>  
                        <li>
                            <form class="uk-form-stacked" id="ThisFormModal" method="POST" 
                                action="delfile?id=${prodm.id}&lgh=${prodm.images}" accept-charset="ISO-8859-1" enctype="multipart/form-data">
                              <div class="uk-margin-medium-top uk-width-4-5@m" uk-grid>
                                  <c:forEach var="i" begin="1" end="${prodm.images}" step="1" varStatus ="status">
                                   <div class="uk-height-small uk-width-1-5 uk-background-cover uk-light uk-flex uk-flex-middle uk-margin-medium-left"
                                       data-src="http://localhost:8080/jewelry/img/CT/<c:out value="${prodm.id}"/>/${i}.jpg" uk-img>
                                      <input type="checkbox" class="uk-checkbox" name="img" value="${i}"/>
                                   </div>
                                  </c:forEach>               
                                  <div class="uk-width-3-4@m uk-margin-medium-top">
                                      <button class="uk-button uk-button-secondary" type="submit">Usuń zaznaczone</button> 
                                  </div>
                              </div>
                          </form>
                        </li> 
                    </ul>
               </div>
            </div>
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

