<%-- 
    Document   : refresh
    Created on : 1 paź 2021, 11:51:13
    Author     : DRzepka
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <jsp:include page="jsp/head.jsp"/><%----%>
        <title>Odświerzanie</title>
        <meta http-equiv="Cache-control" content="no-cache">
        <meta http-equiv="Expires" content="-1">
        <style>
            main{ background-color: #181818 !important; height: 90vh; width: 100vw; margin: 0; padding: 0;}
            p{ color: silver;}

            @media (max-width: 500px) {
                img{ left: 30vw; top: 30vh; width: 50vw;}
                div { left: 30vw; top: 60vh; }
            }
            @media (min-width: 500px) and (max-width: 950px) {
                img{ left: 30vw; top: 30vh; width: 50vw;}
                div { left: 30vw; top: 70vh; }
                p{ font-size: 4vw;}
            }
        </style>
    </head>
    <body>
        <main>
            <img src="img/logo.svg" alt="logo" width="300"  style="left: 30vw; top: 30vh;"
                 class="uk-animation-fade uk-position-absolute uk-hidden@xs"/>
            <div class=" uk-position-absolute uk-position-center uk-text-lead uk-margin-medium-bottom uk-hidden@xs">
                <p>Odetchnij spokojnie. <br> Pliki są już na swoim miejscu.</p></div>
            <img src="img/logo.svg" alt="logo" 
                 class="uk-animation-fade uk-position-absolute uk-hidden@m" />
            <div class=" uk-position-absolute uk-hidden@m">
                <p>Odetchnij spokojnie. <br> Pliki są już na swoim miejscu.</p></div>
        </main><!-- -->
        <script>
            /**/
            $(document).ready(function(){ 
                setTimeout(function () {
                window.location.href = "redirect?answ=acc";
             }, 5000); //will call the function after 5 secs.
            });
        </script>
    </body>
</html>
