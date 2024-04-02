/* global UIkit */

jQuery(document).ready(function($) {
    var alterClass = function() {
        var ww = document.body.clientWidth;
        var element = jQuery('#menu');
        var element1 = jQuery('#cart');
        if (ww > 800) { //większe
            $('.menu-bar').removeClass('uk-offcanvas-bar');     //menu dla offcanvas
            $('#menu').removeClass('uk-offcanvas');             //menu userNL
            $('#menuRnk').removeClass('uk-offcanvas');          //menu dla rangi (asideRnk)
            $('.parent-s').removeClass('uk-parent');            //footer usunięcie listy rozwijalnej
            $('.sub-s').removeClass('uk-nav-sub');              //footer zmiana listy
            $('.hidsm').removeClass('uk-hidden');               //pokazanie elementu
            $('#search').removeClass('uk-hidden');              //wyszukiwanie dla dużych ekranów
            $('#bask').removeClass('uk-hidden');                //koszyk dla dużych
            $('#searchSm').addClass('uk-hidden');               //wyszukiwanie dla małych ekranów
            $('#baskSm').addClass('uk-hidden');                 //koszyk dla małych
            $('#bask').addClass('uk-parent');                   //dodawanie odwołania do okienka modalnego koszyka
            element.removeAttr('uk-offcanvas');                 //usunięce offcanvas z menu userNL
            $('#menuRnk').removeAttr('uk-offcanvas');           //usunięce offcanvas z menu rang (asideRnk)
            element1.add();                                     //dodanie okienka koszyka
        }
        else if (ww < 801) { //małe
            $('.parent-s').addClass('uk-parent');               //footer lista rozwijalan
            $('.sub-s').addClass('uk-nav-sub');                 //footer zmiana listy
            $('.h4li').css('pointer-events', "");               //footer pokazanie
            $('#menu').attr("uk-offcanvas");                    //menu userNL
            $('#menuRnk').attr("uk-offcanvas");                 //menu dla rangi (asideRnk)
            $('.hidsm').addClass('uk-hidden');                  //chowanie elementu
            element1.remove();                                  //usuwanie okienka koszyka
            $('#search').addClass('uk-hidden');                 //wyszukiwanie dla dużych ekranów
            $('#bask').addClass('uk-hidden');                   //koszyk dla dużych
            $('#searchSm').removeClass('uk-hidden');            //wyszukiwanie dla małych ekranów
            $('#baskSm').removeClass('uk-hidden');              //koszyk dla małych
        };
        
        var pathArray = window.location.pathname;
        //sprawdzanie ścieżki dla usunięcia ikony koszyka
        if((pathArray=='/jewelry/basket')||(pathArray=='/jewelry/basket1')||
           (pathArray == '/jewelry/NLbasket')||(pathArray=='/jewelry/summaryBask')){ 
            $('#bask').remove(); 
            $('#baskSm').remove(); 
        } 
    };
    
   //pokazywanie z checkboxem 
    $('#firmCh').click(function(){
        if($(this).is(':checked')){ 
            $('#fHid').removeClass('uk-hidden');
        }
        else{ 
            $('#fHid').addClass('uk-hidden');
        }
    });
    $('#filterCh').click(function(){
        if($(this).is(':checked')){ 
            $('#fltHid').removeClass('uk-hidden');
        }
        else{ 
            $('#fltHid').addClass('uk-hidden');
        }
    });
    $( ".filterCh" ).each(function(index) {
        $(this).on("click", function(){
            if($(this).is(':checked')){ 
                $('.fltHid').removeClass('uk-hidden');
            }
            else{ 
                $('.fltHid').addClass('uk-hidden');
            }
        });
    });
    $('#filterGr').click(function(){
        if($(this).is(':checked')){ 
            $('#menuGrp').removeClass('uk-hidden');
        }
        else{ 
            $('#menuGrp').addClass('uk-hidden');
        }
    });
    $('#filterCt').click(function(){
        if($(this).is(':checked')){ 
            $('#menuCtg').removeClass('uk-hidden');
        }
        else{ 
            $('#menuCtg').addClass('uk-hidden');
        }
    });
    $('#filterTg').click(function(){
        if($(this).is(':checked')){ 
            $('#menuTag').removeClass('uk-hidden');
        }
        else{ 
            $('#menuTag').addClass('uk-hidden');
        }
    });

    $(window).resize(function(){
        alterClass();
    });
    //Fire it when the page first loads:
    alterClass();
});


//https://codepen.io/richerimage/pen/jEXWWG


