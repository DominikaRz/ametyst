<%-- 
    Document   : indexRnk
    Created on : 14 wrz 2021, 10:50:39
    Author     : DRzepka
--%>

<%@ page trimDirectiveWhitespaces="true" %> <%--prevent nextLine in document--%>
<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
  <head>
    <jsp:include page="../head.jsp"/>
    <jsp:include page="../form.jsp"/>
    <link rel="stylesheet" type="text/css" href="css/styleRnk.css"/>
    <link rel="stylesheet" href="css/vanillaSelectBox.css">
    <script src="js/vanillaSelectBox.js"></script>
<!--TITLE-->    
    <title>Ametyst - user</title>
  </head>
  <body>
    <main>
      <div id="top" class="uk-margin" uk-grid>
        <jsp:include page="../asideRnk.jsp"/>
        <section class="uk-width-5-6@m uk-margin-medium uk-align-center"><!---->
          <c:if test = "${rank==4}">
              <c:if test = "${t==1}">
                <div uk-grid>
                    <div class="uk-width-5-6">
                        <div class="uk-inline uk-width-5-6 uk-align-right">
                            <a class="uk-form-icon" href="#" uk-icon="icon: search"></a>
                            <input class="uk-input search" type="text" placeholder="Szukaj.."/>
                        </div>
                    </div>
                    <div class="uk-width-1-6@m uk-width-1-2 uk-text-right@m">
                        <div class="uk-text-center@xs"><a href="#" uk-icon="icon: plus; ratio: 1.5" 
                               uk-tooltip="Dodaj" uk-toggle="target: #modal4_10"></a></div>
                    </div>
                    <div class="uk-width-1-6 uk-hidden@m uk-margin-small-right uk-text-right@m">
                        <div class="uk-text-right@xs"><a href="#menuRnk" uk-toggle uk-navbar-toggle-icon></a></div>
                    </div>
                </div>
                <div>
                    <label class="uk-form-label uk-text-bold uk-margin-medium-top">
                        <h4><input class="filterCh uk-checkbox" type="checkbox"/> Filtrowanie</h4> <!-- checked-->
                    </label>
                    <div uk-filter="target: .js-filter; animation: fade" style="margin-left: 0.2rem;">
                        <div class="fltHid uk-hidden uk-grid-small uk-flex-middle uk-margin-small-top">
                            <div>
                                <h4 class="uk-width-1-3@m" style="margin: 0 0 0.35rem 0;">
                                    <input id="filterGr" class="uk-checkbox" type="checkbox"/> Grupy</h4> <!-- checked-->
                                <h4 class="uk-width-1-3@m" style="margin: 0 0 0.35rem 0;">
                                    <input id="filterCt" class="uk-checkbox" type="checkbox"/> Kategorie</h4> <!-- checked-->
                                <h4 class="uk-width-1-3@m" style="margin: 0 0 0.35rem 0;">
                                    <input id="filterTg" class="uk-checkbox" type="checkbox"/> Tagi</h4> <!-- checked-->
                            </div>
                            <div class="uk-width-expand">
                                <div class="uk-grid-small uk-grid-divider">
                                    <div>
                                        <ul class="uk-subnav uk-subnav-pill uk-child-width-1-3 uk-text-center" uk-margin>
                                            <li class="uk-width-1-1" uk-filter-control><a href="#">Wszystkie produkty</a></li>
                                            <li class="uk-hidden@m"><span>Id.</span></li>
                                            <li class="uk-hidden@m" uk-filter-control="sort: data-id; order: desc"><a href="#" uk-icon="icon: arrow-down"></a></li>
                                            <li class="uk-hidden@m" uk-filter-control="sort: data-id"><a href="#" uk-icon="icon: arrow-up"></a></li>
                                            <li class="uk-hidden@m"><span>Nazwa</span></li>
                                            <li class="uk-hidden@m" uk-filter-control="sort: data-name; order: desc"><a href="#" uk-icon="icon: arrow-down"></a></li>
                                            <li class="uk-hidden@m" uk-filter-control="sort: data-name"><a href="#" uk-icon="icon: arrow-up"></a></li>
                                            <li class="uk-hidden@m"><span>Data</span></li>
                                            <li class="uk-hidden@m" uk-filter-control="sort: data-create; order: desc"><a href="#" uk-icon="icon: arrow-down"></a></li>
                                            <li class="uk-hidden@m" uk-filter-control="sort: data-create"><a href="#" uk-icon="icon: arrow-up"></a></li>
                                        </ul>
                                    </div>
                                    <div id="menuGrp" class="uk-hidden">
                                        <ul class="uk-subnav uk-subnav-pill uk-child-width-1-1@xs uk-child-width-1-5@m uk-text-center" uk-margin>
                                          <c:forEach var="gr" items="${listGroup}" varStatus ="stat">
                                            <li><a uk-filter-control="[data-idgr='${gr.id}']" href="#">${gr.name}</a></li>
                                          </c:forEach>
                                        </ul>
                                    </div>
                                    <div id="menuCtg" class="uk-hidden">
                                        <ul class="uk-subnav uk-subnav-pill uk-child-width-1-1@xs uk-child-width-1-5@m uk-text-center" uk-margin>
                                          <c:forEach var="ct" items="${listCT}" varStatus ="stat">
                                              <c:if test = "${ct.idCat != listCT[stat.index-1].idCat}">
                                               <li><a uk-filter-control="[data-idcat='${ct.idCat}']" href="#">${ct.nameCat}</a></li> 
                                              </c:if>
                                          </c:forEach>
                                        </ul>
                                    </div>
                                    <div id="menuTag" class="uk-hidden">
                                        <ul class="uk-subnav  uk-subnav-pill uk-child-width-1-1@xs uk-child-width-1-5@m uk-text-center" uk-margin>
                                          <c:forEach var="ct" items="${listCT}" varStatus ="stat">
                                               <li><a uk-filter-control="[data-idct='${ct.id}']" href="#">${ct.nameTag}</a></li> 
                                          </c:forEach>
                                        </ul>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <table class="uk-table uk-table-hover uk-table-divider uk-table-responsive" 
                                style="position: relative;">
                            <thead>
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
                               </th>
                               <th class="uk-width-small">
                                   <div class="uk-width-auto uk-text-nowrap">
                                       Data dodania
                                       <span uk-filter-control="sort: data-create">
                                           <a class="uk-icon-link" href="" uk-icon="icon: arrow-down"></a>
                                       </span>
                                       <span uk-filter-control="sort: data-create; order: desc">
                                           <a class="uk-icon-link" href="" uk-icon="icon: arrow-up"></a>
                                       </span>
                                   </div>
                               </th>
                               <th class="uk-width-small">Akcje</th>
                           </thead>
                           <tbody class="js-filter">
                           <c:forEach var="prod" items="${listProdM}" varStatus ="stat">
                               <c:set var="cattag" value="${Integer.parseInt((prod.idCattag).toString())}"/>
                               <tr 
                                   data-id="<c:out value="${prod.id}"/>" 
                                   data-name="<c:out value="${listProd[stat.index].name}"/>" 
                                   data-create="<c:out value="${prod.create}"/>" 
                                   data-idgr="<c:out value="${listCT[cattag-1].idGr}"/>" 
                                   data-idcat="<c:out value="${listCT[cattag-1].idCat}"/>" 
                                   data-idct="<c:out value="${cattag}"/>" 
                               >
                                   <td class="fl">
                                        <a href="viewOneProduct?id=<c:out value="${prod.id}"/>&t=2" target="_blank" uk-tooltip="Wyświetl"> 
                                         <c:out value="${prod.id}"/></a>
                                   </td>
                                   <td><c:out value="${prod.name}"/></td>
                                   <td><span class="uk-hidden@m">Data utworzenia: </span><c:out value="${prod.create}"/></td>
                                   <td>
                                        <span class="uk-hidden@m">Akcje: </span>
                                        <a href="deleteProduct?id=${prod.id}" 
                                          uk-icon="close" uk-tooltip="Usuń" class="uk-margin-small-left"></a>

                                        <c:if test = "${prod.quantityState == 0}"> 
                                            <a uk-icon="refresh" uk-tooltip="Odnów" uk-toggle="target: #modal4_5" 
                                                onclick="getIdp(<c:out value="${prod.id}"/>)"></a>
                                        </c:if>
                                   </td>
                               </tr>
                           </c:forEach>
                           </tbody>
                        </table>
                    </div>
                    <div id="modal4_5" uk-modal>
                        <div class="uk-modal-dialog uk-modal-body">
                            <button class="uk-modal-close-outside" type="button" uk-close></button>
                            <div class="uk-modal-header"><h2 class="uk-modal-title">Materiał nr. <span id="ids_p">x</span></h2></div>
                            <form class="uk-form-stacked" id="ThisFormModal4_5" method="POST" action="" accept-charset="ISO-8859-1">
                                <div class="uk-margin-medium-top" uk-grid>
                                    <div class="uk-width-2-3@m"> 
                                        <h4 class="uk-text-bold">Nowa ilość na stanie:</h4> 
                                        <input id="state" name="state" class="uk-input" value=""
                                           type="number" step="1"/>
                                    </div>                              
                                    <div class="uk-width-1-6@m uk-margin-medium-top">
                                        <button class="uk-button uk-button-secondary" type="submit">Zmień</button> 
                                    </div>
                                </div>
                            </form>
                        </div>
                    </div>
                    <script>
                        function getIdp(id){
                            var ids_p = [<c:forEach var="prod" items="${listProdM}"><c:out value="${prod.id}"/>, </c:forEach>0];
                            for(var i=0; i<ids_p.length; i++){
                                if(ids_p[i]===id){
                                    $("#ids_p").html(ids_p[i].toString());
                                    $('#ThisFormModal4_5').attr('action', ('restockProduct?id='+ids_p[i]));
                                } 
                            } 
                        }
                    </script>
                </div>
                <div id="modal4_10" uk-modal>
                    <div class="uk-modal-dialog uk-modal-body">
                        <button class="uk-modal-close-outside" type="button" uk-close></button>
                        <div class="uk-modal-header"><h2 class="uk-modal-title">Dodaj produkt</h2></div>
                        <form class="uk-form-stacked" id="ThisFormModalPrd" method="POST" 
                              action="addProduct" accept-charset="ISO-8859-1">
                            <div class="uk-margin-medium-top" uk-grid>
                                 <div class="uk-grid-small" uk-grid>
                                    <div class="uk-width-4-5@m">
                                         <div class="uk-form-controls">
                                             <label class="uk-form-label">Nazwa produktu:</label>
                                            <input id="prod_name" name="prod_name" class="uk-input" type="text"/>
                                         </div>
                                    </div>
                                    <div class="uk-width-1-5@m uk-width-1-2 uk-hidden@xs">
                                         <div class="uk-form-controls">
                                             <label class="uk-form-label">Ilość obrazów:</label>
                                            <input id="prod_img" name="prod_img" class="uk-input" value="0" type="text" disabled/>
                                         </div>
                                    </div>
                                    <div class="uk-width-3-5@m uk-width-1-1">
                                         <div class="uk-form-controls">
                                            <label class="uk-form-label">Kategoria | Tag:</label>
                                            <select id="select1" class="uk-select uk-width-2-5@m" name="CT">
                                                <c:forEach var="CT" items="${listCT}" varStatus="st">
                                                    <c:if test = "${CT.idCat != listCT[st.index-1].idCat}">   
                                                    <option value="<c:out value="${CT.idCat}"/>">
                                                        <c:out value="${CT.nameCat}"/></option>
                                                    </c:if>
                                                </c:forEach>
                                            </select>
                                            <c:forEach var="CT" items="${listCT}" varStatus="st">
                                                <c:if test = "${CT.idCat != listCT[st.index-1].idCat}">
                                                <select id="s<c:out value="${CT.idCat}"/>" name="s<c:out value="${CT.idCat}"/>" class="uk-select uk-width-2-5@m" 
                                                        <c:if test = "${CT.idCat != 1}">style="display: none;"</c:if>>
                                                </c:if>
                                                    <option value="<c:out value="${CT.id}"/>"><c:out value="${CT.nameTag}"/></option>
                                                <c:if test = "${listCT[st.index+1].idCat != CT.idCat}">
                                                </select>
                                                </c:if>
                                            </c:forEach>
                                            <script>
                                                const tab = [<c:forEach var="CT" items="${listCT}" varStatus="sta"><c:if test = "${CT.idCat != listCT[sta.index-1].idCat}"><c:out value="${CT.idCat}"/>, </c:if></c:forEach>0 ];
                                                var str = "";
                                                $("#select1").change(function(){
                                                    for(var i=0; i<tab.length; i++){
                                                        str = "#s"+tab[i];
                                                        if($(this).val() == tab[i]){ $(str).show(); }
                                                        else{ $(str).hide(); }
                                                    }
                                                });
                                                //source: https://jsfiddle.net/f4n5yv2d/
                                            </script>
                                         </div>
                                    </div>
                                    <div class="uk-width-1-5@m uk-width-1-2">
                                         <div class="uk-form-controls">
                                             <label class="uk-form-label">Cena (zł):</label>
                                             <input id="prod_prc" name="prod_prc" class="uk-input" type="number" step="0.01"/>
                                         </div>
                                    </div>
                                    <div class="uk-width-1-5@m uk-width-1-2">
                                         <div class="uk-form-controls">
                                             <label class="uk-form-label">Promocja (%):</label>
                                            <input id="prodm_discount" name="prodm_discount" class="uk-input" placeholder="Przecena w %..."
                                                     type="number" step="1"/>
                                         </div>
                                     </div>
                                 </div>       
                                 <div class="uk-grid-small" uk-grid>
                                     <div class="uk-width-1-5@m uk-width-1-2">
                                         <div class="uk-form-controls">
                                             <label class="uk-form-label">Wysokość (mm):</label>
                                            <input id="prodm_height" name="prodm_height" class="uk-input" placeholder="Wysokość produktu..."
                                                     type="number" step="0.01"/>
                                         </div>
                                     </div>
                                     <div class="uk-width-1-5@m uk-width-1-2">
                                         <div class="uk-form-controls">
                                             <label class="uk-form-label">Szerokość (mm):</label>
                                            <input id="prodm_width" name="prodm_width" class="uk-input" placeholder="Szerokość produktu..."
                                                     type="number" step="0.01"/>
                                         </div>
                                     </div>
                                     <div class="uk-width-1-5@m uk-width-1-2">
                                         <div class="uk-form-controls">
                                             <label class="uk-form-label">Długość (mm):</label>
                                            <input id="prodm_lenght" name="prodm_lenght" class="uk-input" placeholder="Długość produktu..."
                                                    type="number" step="0.01"/>
                                         </div>
                                     </div>
                                     <div class="uk-width-1-5@m uk-width-1-2">
                                         <div class="uk-form-controls">
                                             <label class="uk-form-label">Otwór (mm):</label>
                                            <input id="prodm_hole" name="prodm_hole" class="uk-input" placeholder="Wielkość dziury produktu..."
                                                     type="number" step="0.01"/>
                                         </div>
                                     </div>
                                     <div class="uk-width-1-5@m uk-width-1-2">
                                         <div class="uk-form-controls">
                                             <label class="uk-form-label">Waga (g):</label>
                                            <input id="prodm_weight" name="prodm_weight" class="uk-input" placeholder="Waga produktu..."
                                                     type="number" step="0.01"/>
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
                                                   type="number" step="1"/>
                                         </div>
                                     </div>
                                     <div class="uk-width-2-5@m uk-width-1-2">
                                         <div class="uk-form-controls">
                                             <label class="uk-form-label">Ilość w opakowaniu:</label>
                                            <input id="prod_quant" name="prod_quant" class="uk-input" placeholder="Ilość w opakowaniu..."
                                                    type="number" step="1"/>
                                         </div>
                                     </div>
                                     <div class="uk-width-1-1@m">
                                         <div class="uk-form-controls">
                                             <label class="uk-form-label">Źródło produktu:</label>
                                             <textarea id="prodm_src" name="prodm_src" class="uk-textarea" rows="2" 
                                                       placeholder="Źródło produktu... "></textarea>
                                         </div>
                                     </div>
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
                <ul class="uk-switcher uk-margin switcher-supplier2">
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
                                       uk-tooltip="Dodaj" uk-toggle="target: #modal4_1_add" onclick="getId0(0, 1, 1)"></a></div>
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
                                  <c:forEach var="col" items="${listCol}">
                                    <tr 
                                        data-id="${col.id}" data-name="${col.name}" 
                                    >
                                        <td class="fl"><c:out value="${col.id}"/>.</td>
                                        <td><c:out value="${col.name}"/></td>
                                        <td>
                                            <span class="uk-hidden@m">Akcje: </span>
                                            <a uk-icon="pencil" uk-tooltip="Edytuj" uk-toggle="target: #modal4_1" onclick="getId0(${col.id}, 1, 0)"></a>
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
                                       uk-tooltip="Dodaj" uk-toggle="target: #modal4_1_add" onclick="getId0(0, 2, 1)"></a></div>
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
                                    <c:forEach var="shp" items="${listShp}">
                                        <tr 
                                            data-id="${shp.id}" data-name="${shp.name}"
                                        >  
                                            <td class="fl"><c:out value="${shp.id}" />.</td>
                                            <td><c:out value="${shp.name}" /></td>
                                            <td>
                                                <span class="uk-hidden@m">Akcje: </span>
                                                <a uk-icon="pencil" uk-tooltip="Edytuj"  uk-toggle="target: #modal4_1" onclick="getId0(${shp.id}, 2, 0)"></a>
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
                                       uk-tooltip="Dodaj" uk-toggle="target: #modal4_1_add" onclick="getId0(0, 3, 1)"></a></div>
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
                                    <c:forEach var="fbr" items="${listFbr}">
                                        <tr 
                                            data-id="${fbr.id}" data-name="${fbr.name}" 
                                        >        
                                            <td class="fl"><c:out value="${fbr.id}" />.</td>
                                            <td><c:out value="${fbr.name}" /></td>
                                            <td>
                                                <span class="uk-hidden@m">Akcje: </span>
                                                <a uk-icon="pencil" uk-tooltip="Edytuj" uk-toggle="target: #modal4_1" onclick="getId0(${fbr.id}, 3, 0)"></a>
                                                    <a href="deleteDictionary?idMod=<c:out value='${fbr.id}'/>&t=3" 
                                                    uk-icon="close" uk-tooltip="Usuń" class="uk-margin-small-left"></a>
                                            </td> 
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </li>
                    <div id="modal4_1" uk-modal>
                        <div class="uk-modal-dialog uk-modal-body">
                            <button class="uk-modal-close-outside" type="button" uk-close></button>
                            <div class="uk-modal-header"><h2 class="uk-modal-title"><span id="title_modal1"></span></h2></div>
                            <form class="uk-form-stacked" id="ThisFormModal4_1" method="POST" action="" accept-charset="ISO-8859-1">
                                <div class="uk-margin-medium-top" uk-grid>
                                    <div class="uk-width-2-3@m"> 
                                        <h4 class="uk-text-bold">Nazwa:</h4> 
                                        <input id="name" name="name" class="uk-input" value="" type="text"/>
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
                                var ids_col = [<c:forEach var="col" items="${listCol}"><c:out value="${col.id}"/>, </c:forEach>0];
                                var ids_fab = [<c:forEach var="fab" items="${listFbr}"><c:out value="${fab.id}"/>, </c:forEach>0];
                                var ids_shp = [<c:forEach var="shp" items="${listShp}"><c:out value="${shp.id}"/>, </c:forEach>0];
                                var names_col = [<c:forEach var="col" items="${listCol}">"<c:out value="${col.name}"/>", </c:forEach>""];
                                var names_fab = [<c:forEach var="fab" items="${listFbr}">"<c:out value="${fab.name}"/>", </c:forEach>""];
                                var names_shp = [<c:forEach var="shp" items="${listShp}">"<c:out value="${shp.name}"/>", </c:forEach>""];
                                $("#name_modal").val("".toString());

                                switch(t){
                                    case 1: //kolor
                                        if(a===1){ //add
                                            $("#title_modal2").html("Dodawanie nowego koloru".toString());
                                            $('#ThisFormModal4_1a').attr('action', ('insertDictionary?t=1')); //? what t 
                                        } 
                                        else{
                                            for(var i=0; i<ids_col.length; i++){
                                                if(ids_col[i]===id1){
                                                    $("#title_modal1").html("Edycja koloru".toString());
                                                    $("#name").val(names_col[i].toString());
                                                    $("#idMod").val(id1.toString());
                                                    $('#ThisFormModal4_1').attr('action', ("updateDictionary?&t=1"));
                                                }
                                            }
                                        } 
                                     break;
                                    case 2: //kształt 
                                        if(a===1){ //add
                                            $("#title_modal2").html("Dodawanie nowego kształtu".toString());
                                            $('#ThisFormModal4_1a').attr('action', ('insertDictionary?t=2')); 
                                        }
                                        else{ //edit
                                            for(var i=0; i<ids_shp.length; i++){
                                                if(ids_shp[i]===id1){
                                                    $("#title_modal1").html("Edycja kształtu".toString());
                                                    $("#name").val(names_shp[i].toString());
                                                    $("#idMod").val(id1.toString());
                                                    $('#ThisFormModal4_1').attr('action', ("updateDictionary?t=2"));
                                                }
                                            } 
                                        }  
                                     break;
                                    case 3: //materiał 
                                        if(a===1){ //add
                                            $("#title_modal2").html("Dodawanie nowego materiału".toString());
                                            $('#ThisFormModal4_1a').attr('action', ('insertDictionary?t=3')); 
                                        }
                                        else{ //edit
                                            for(var i=0; i<ids_fab.length; i++){
                                                if(ids_fab[i]===id1){
                                                    $("#title_modal1").html("Edycja materiału".toString());
                                                    $("#name").val(names_fab[i].toString());
                                                    $("#idMod").val(id1.toString());
                                                    $('#ThisFormModal4_1').attr('action', ("updateDictionary?t=3"));
                                                }
                                            } 
                                        } 
                                     break;
                                }
                            }
                    </script>
                    <div id="modal4_1_add" uk-modal>
                        <div class="uk-modal-dialog uk-modal-body">
                            <button class="uk-modal-close-outside" type="button" uk-close></button>
                            <div class="uk-modal-header"><h2 class="uk-modal-title"><span id="title_modal2"></span></h2></div>
                            <form class="uk-form-stacked" id="ThisFormModal4_1a" method="POST" action="" accept-charset="ISO-8859-1">
                                <div class="uk-margin-medium-top" uk-grid>
                                    <div class="uk-width-2-3@m"> 
                                        <h4 class="uk-text-bold">Nazwa:</h4> 
                                        <input id="name" name="name" class="uk-input" value="" type="text"/>
                                    </div>                              
                                    <div class="uk-width-1-6@m uk-margin-medium-top">
                                        <button class="uk-button uk-button-secondary" type="submit">Dodaj</button> 
                                    </div>
                                </div>
                            </form>
                        </div>
                    </div>
                </ul>
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

