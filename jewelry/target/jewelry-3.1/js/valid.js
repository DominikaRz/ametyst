 $(document).ready(function() {
    $.validator.addMethod("regName", function (value, element) {
        return this.optional(element) || /^[A-ZĄĆŁÓŚŻŹ][a-ząćęłóśżź]*$/.test(value);
    });
    $.validator.addMethod("regAdress", function (value, element) {
        return this.optional(element) || /^([-a-ząćęłóśżźA-ZĄĆŁÓŚŻŹ]+|[a-ząćęłóśżźA-ZĄĆŁÓŚŻŹ]+s[a-ząćęłóśżźA-ZĄĆŁÓŚŻŹ]+)$/.test(value); //([-a-ząćęłóśżźA-ZĄĆŁÓŚŻŹ]+|[a-ząćęłóśżźA-ZĄĆŁÓŚŻŹ]+s[a-ząćęłóśżźA-ZĄĆŁÓŚŻŹ]+)
    });
    //basketNL
    $("#ThisFormNL").validate({
        errorClass: "error fail-alert",
        validClass: "valid success-alert",
        rules:{
            mail: {
                required: true,
                email: true,
            },
            name: {
                required: true,
                regName: true
            },
            surname: {
                required: true,
                regName: true
            },
            tel: {
                required: true,
                maxlength: 22,
                minlength: 11
            },
            street: {
                required: true,
                maxlength: 60
            },
            nbr: {
                required: true,
                maxlength: 10
            },
            code: {
                required: true
            },
            post: {
                required: true,
                regAdress: true,
                maxlength: 40
            },
            town: {
                required: true,
                regAdress: true,
                maxlength: 40
            },
            country: {
                required: true,
                regAdress: true
            },
            state: {
                required: true,
                regAdress: true
            },
            regul: {
                required: true
            },
            name_firm: {
                required: function (element) {
                    if($("#firmCh").is(':checked')){return true;}
                    else{ return false;} 
            }},
            mail_firm: {
                required: function (element) {
                    if($("#firmCh").is(':checked')){return true;}
                    else{ return false;} 
            },
            },
            tel_firm: {
                required: function (element) {
                    if($("#firmCh").is(':checked')){return true;}
                    else{ return false;} 
            },},
            nip: {
                required: function (element) {
                    if($("#firmCh").is(':checked')){return true;}
                    else{ return false;}  
                 } 
            }
        },
        messages: {
            mail: {
                required: "To pole jest wymagane!",
                email: "Musi spełniać warunki pola!"
            },
            name:{
                required: "To pole jest wymagane!",
                regName: "To nie jest imię!"
            },
            surname:{
                required: "To pole jest wymagane!",
                regName: "To nie jest nazwisko!"
            },
            tel:{
                required: "To pole jest wymagane!",
                maxlength: "Numer telefonu nie może być dłuższy niż 22 znaki",
                minlength: "Numer telefonu musi posiadać co najmniej 9 znaków"
            },
            street:{
                required: "To pole jest wymagane!",
                maxlength: "Nie może być więcej niż 60 znaków!"
            },
            nbr:{
                required: "To pole jest wymagane!",
                maxlength: "Nie może być więcej niż 10 znaków!"
            },
            code:{
                required: "To pole jest wymagane!",
            },
            post:{
                required: "To pole jest wymagane!",
                regAdress: "Niepoprawna miejscowość!",
                maxlength: "Nie może być więcej niż 40 znaków!"
            },
            town:{
                required: "To pole jest wymagane!",
                regAdress: "Niepoprawna miejscowość!",
                maxlength: "Nie może być więcej niż 40 znaków!"
            },
            country:{
                required: "To pole jest wymagane!",
                regAdress: "Niepoprawna nazwa kraju!"
            },
            state:{
                required: "To pole jest wymagane!",
                regAdress: "Niepoprawna nazwa województwa!"
            },
            firm:{
                required: "To pole jest wymagane!"
            },
            nip:{
                required: "To pole jest wymagane!"
            }
        }
    });
    $("#ThisFormOrderNL").validate({
        errorClass: "error fail-alert",
        validClass: "valid success-alert",
        rules:{
            mail: {
                required: true,
                email: true,
            },
            name: {
                required: true,
                regName: true
            },
            surname: {
                required: true,
                regName: true
            },
            order: {
                required: true,
                number: true
            }
        },
        messages: {
            mail: {
                required: "To pole jest wymagane!",
                email: "Musi spełniać warunki pola!"
            },
            name:{
                required: "To pole jest wymagane!",
                regName: "To nie jest imię!"
            },
            surname:{
                required: "To pole jest wymagane!",
                regName: "To nie jest nazwisko!"
            },
            tel:{
                required: "To pole jest wymagane!",
                number: "Nieprawidłowy identyfikator zamówienia"
            }
        }
    });
    //summaryBask
    $("#ThisFormSumm").validate({
        errorClass: "error fail-alert",
        validClass: "valid success-alert",
        rules:{
            comm: {},
            regul: { required: true }
        },
        messages: {
            comm: {},
            regul: { required: "Musisz zaakceptować regulamin!" }
        }
    });
    //settings
    $("#ThisFormPass").validate({
        errorClass: "error fail-alert",
        validClass: "valid success-alert",
        rules:{
            password_revious: {
                minlength: 8,
                maxlength: 20,
                required: true
            },
            password: {
                minlength: 8,
                maxlength: 20,
                required: true
            },
            password_again: {
                required: true,
                equalTo: "#password"
            }
        },
        messages: {
            password_revious: {
                required: "To pole jest wymagane!",
                minlength: "Musi mieć przynajmniej 8 znaków!",
                maxlength: "Musi mieć co najwyżej 20 znaków!"
            },
            password: {
                required: "To pole jest wymagane!",
                minlength: "Musi mieć przynajmniej 8 znaków!",
                maxlength: "Musi mieć co najwyżej 20 znaków!"
            },
            password_again: {
                required: "To pole jest również wymagane!",
                equalTo: "W obu polach musi być to samo hasło!"
            }
        }
    });
    //settings
    $("#ThisFormData").validate({
        errorClass: "error fail-alert",
        validClass: "valid success-alert",
        rules:{
            name: {
                required: true,
                regName: true
            },
            surname: {
                required: true,
                regName: true
            },
            tel: {
                required: true,
                maxlength: 22,
                minlength: 11
            }
        },
        messages: {
            name:{
                required: "To pole jest wymagane!",
                regName: "To nie jest imię!"
            },
            surname:{
                required: "To pole jest wymagane!",
                regName: "To nie jest nazwisko!"
            },
            tel:{
                required: "To pole jest wymagane!",
                maxlength: "Numer telefonu nie może być dłuższy niż 22 znaki",
                minlength: "Numer telefonu musi posiadać co najmniej 9 znaków"
            }
        }
    });
    $('.ThisFormExist').each( function(){
      var form = $(this);
      form.validate({
        errorClass: "error fail-alert",
        validClass: "valid success-alert",
        rules:{
            street: {
                required: true,
                maxlength: 60,
                minlength: 5
            },
            nbr: {
                required: true,
                maxlength: 10
            },
            code: {
                required: true
            },
            post: {
                required: true,
                regAdress: true,
                maxlength: 40
            },
            town: {
                required: true,
                regAdress: true,
                maxlength: 40
            },
            country: {
                required: true,
                regAdress: true
            },
            state: {
                required: true,
                regAdress: true
            },
            regul: {
                required: true
            },
            name_firm: {
                required: function (element) {
                    if($("#firmCh").is(':checked')){return true;}
                    else{ return false;} 
            },},
            mail_firm: {
                required: function (element) {
                    if($("#firmCh").is(':checked')){return true;}
                    else{ return false;} 
            },
            },
            tel_firm: {
                required: function (element) {
                    if($("#firmCh").is(':checked')){return true;}
                    else{ return false;} 
            },},
            nip: {
                required: function (element) {
                    if($("#firmCh").is(':checked')){return true;}
                    else{ return false;}  
                 } 
            }
        },
        messages: {
            street:{
                required: "To pole jest wymagane!",
                maxlength: "Nie może być więcej niż 60 znaków!",
                minlength: "Nie może być mniej niż 5 znaków!"
            },
            nbr:{
                required: "To pole jest wymagane!",
                maxlength: "Nie może być więcej niż 10 znaków!"
            },
            code:{
                required: "To pole jest wymagane!",
            },
            post:{
                required: "To pole jest wymagane!",
                regAdress: "Niepoprawna miejscowość!",
                maxlength: "Nie może być więcej niż 40 znaków!"
            },
            town:{
                required: "To pole jest wymagane!",
                regAdress: "Niepoprawna miejscowość!",
                maxlength: "Nie może być więcej niż 40 znaków!"
            },
            country:{
                required: "To pole jest wymagane!",
                regAdress: "Niepoprawna nazwa kraju!"
            },
            state:{
                required: "To pole jest wymagane!",
                regAdress: "Niepoprawna nazwa województwa!"
            },
            firm:{
                required: "To pole jest wymagane!"
            },
            nip:{
                required: "To pole jest wymagane!"
            }
        }
    });
   });
    $("#ThisFormNew").validate({
        errorClass: "error fail-alert",
        validClass: "valid success-alert",
        rules:{
            street: {
                required: true,
                maxlength: 60
            },
            nbr: {
                required: true,
                maxlength: 10
            },
            code: {
                required: true
            },
            post: {
                required: true,
                regAdress: true,
                maxlength: 40
            },
            town: {
                required: true,
                regAdress: true,
                maxlength: 40
            },
            country: {
                required: true,
                regAdress: true
            },
            state: {
                required: true,
                regAdress: true
            },
            regul: {
                required: true
            },
            name_firm: {
                required: function (element) {
                    if($("#firmCh").is(':checked')){return true;}
                    else{ return false;} 
            },},
            mail_firm: {
                required: function (element) {
                    if($("#firmCh").is(':checked')){return true;}
                    else{ return false;} 
            },
            },
            tel_firm: {
                required: function (element) {
                    if($("#firmCh").is(':checked')){return true;}
                    else{ return false;} 
            },},
            nip: {
                required: function (element) {
                    if($("#firmCh").is(':checked')){return true;}
                    else{ return false;}  
                 } 
            }
        },
        messages: {
            street:{
                required: "To pole jest wymagane!",
                maxlength: "Nie może być więcej niż 60 znaków!"
            },
            nbr:{
                required: "To pole jest wymagane!",
                maxlength: "Nie może być więcej niż 10 znaków!"
            },
            code:{
                required: "To pole jest wymagane!",
            },
            post:{
                required: "To pole jest wymagane!",
                regAdress: "Niepoprawna miejscowość!",
                maxlength: "Nie może być więcej niż 40 znaków!"
            },
            town:{
                required: "To pole jest wymagane!",
                regAdress: "Niepoprawna miejscowość!",
                maxlength: "Nie może być więcej niż 40 znaków!"
            },
            country:{
                required: "To pole jest wymagane!",
                regAdress: "Niepoprawna nazwa kraju!"
            },
            state:{
                required: "To pole jest wymagane!",
                regAdress: "Niepoprawna nazwa województwa!"
            },
            firm:{
                required: "To pole jest wymagane!"
            },
            nip:{
                required: "To pole jest wymagane!"
            }
        }
    });
    //register
    $("#ThisFormReg").validate({
        errorClass: "error fail-alert",
        validClass: "valid success-alert",
        rules:{
            mail: {
                required: true,
                email: true
            },
            password: {
                minlength: 8,
                maxlength: 20,
                required: true
            },
            password_again: {
                required: true,
                equalTo: "#password"
            },
            name: {
                required: true,
                regName: true
            },
            surname: {
                required: true,
                regName: true
            },
            tel: {
                required: true
            },
            street: {
                required: true,
                maxlength: 60
            },
            nbr: {
                required: true,
                maxlength: 10
            },
            code: {
                required: true
            },
            post: {
                required: true,
                regAdress: true,
                maxlength: 40
            },
            town: {
                required: true,
                regAdress: true,
                maxlength: 40
            },
            country: {
                required: true,
                regAdress: true
            },
            state: {
                required: true,
                regAdress: true
            },
            regul: {
                required: true
            },
            name_firm: {
                required: function (element) {
                    if($("#firmCh").is(':checked')){return true;}
                    else{ return false;}  
                 } 
            },
            tel_firm: {
                required: function (element) {
                    if($("#firmCh").is(':checked')){return true;}
                    else{ return false;}  
                 } 
            },
            mail_firm: {
                required: function (element) {
                    if($("#firmCh").is(':checked')){return true;}
                    else{ return false;}  
                 }, 
                email: function (element) {
                    if($("#firmCh").is(':checked')){return true;}
                    else{ return false;}
                },
            },
            nip: {
                required: function (element) {
                    if($("#firmCh").is(':checked')){return true;}
                    else{ return false;}  
                 } 
            }
        },
        messages: {
            mail: {
                required: "To pole jest wymagane!",
                email: "Musi spełniać warunki pola!"
            },
            password: {
                required: "To pole jest również wymagane!",
                minlength: "Musi mieć przynajmniej 8 znaków!",
                maxlength: "Musi mieć co najwyżej 20 znaków!"
            },
            password_again: {
                required: "To pole jest również wymagane!",
                equalTo: "W obu polach musi być to samo hasło!"
            },
            name:{
                required: "To pole jest wymagane!",
                regName: "To nie jest imię!"
            },
            surname:{
                required: "To pole jest wymagane!",
                regName: "To nie jest nazwisko!"
            },
            tel:{
                required: "To pole jest wymagane!"
            },
            street:{
                required: "To pole jest wymagane!",
                maxlength: "Nie może być więcej niż 60 znaków!"
            },
            nbr:{
                required: "To pole jest wymagane!",
                maxlength: "Nie może być więcej niż 10 znaków!"
            },
            code:{
                required: "To pole jest wymagane!",
            },
            post:{
                required: "To pole jest wymagane!",
                regAdress: "Niepoprawna miejscowość!",
                maxlength: "Nie może być więcej niż 40 znaków!"
            },
            town:{
                required: "To pole jest wymagane!",
                regAdress: "Niepoprawna miejscowość!",
                maxlength: "Nie może być więcej niż 40 znaków!"
            },
            country:{
                required: "To pole jest wymagane!",
                regAdress: "Niepoprawna nazwa kraju!"
            },
            state:{
                required: "To pole jest wymagane!",
                regAdress: "Niepoprawna nazwa województwa!"
            },
            name_firm:{
                required: "To pole jest wymagane!"
            },
            tel_firm:{
                required: "To pole jest wymagane!"
            },
            mail_firm:{
                required: "To pole jest wymagane!"
            },
            nip:{
                required: "To pole jest wymagane!"
            }
        }
    }); 
    //login + basket1
    $("#ThisFormLog").validate({
        errorClass: "error fail-alert",
        validClass: "valid success-alert",
        rules:{
            password: {
                required: true
            },
            login: {
                required: true,
                email: true
            }
            
        },
        messages: {
            pass: {
                required: "To pole jest wymagane do logowania!"
            },
            login: {
                required: "To pole jest wymagane do logowania!",
                email: "Musi spełniać warunki adresu e-mail!"
            }
        }
    });
    //supplier product (add + edit)
    $("#ThisFormModalPrd").validate({
        errorClass: "error fail-alert",
        validClass: "valid success-alert",
        rules:{
            prod_name: { 
                required: true
            },
            prod_prc: { 
                required: true, 
                min: 0
            },
            prodm_discount: {
                min: 0,
                max: 100
            },
            prodm_height: {
                min: 0
            },
            prodm_width: {
                min: 0
            },
            prodm_lenght: {
                min: 0
            },
            prodm_hole: {
                min: 0
            },
            prodm_weight: {
                min: 0
            },
            prodm_diameter: {
                min: 0
            },
            prodm_desc: {
                required: true
            },
            prod_state: {
                required: true,
                min: 0
            },
            prod_quant: {
                required: true,
                min: 0
            },
            prodm_src: {
               required: true 
            }
        },
        messages: {
            prod_name: { 
                required: "To pole jest wymagane!"
            },
            prod_prc: { 
                required: "To pole jest wymagane!", 
                min: "Nie może być mniej niż 0!"
            },
            prodm_discount: { 
                min: "Nie może być mniej niż 0!",
                max: "Nie może być więcej niż 100!"
            },
            prodm_height: { 
                min: "Nie może być mniej niż 0!"
            },
            prodm_width: { 
                min: "Nie może być mniej niż 0!"
            },
            prodm_lenght: {
                min: "Nie może być mniej niż 0!"
            },
            prodm_hole: {
                min: "Nie może być mniej niż 0!"
            },
            prodm_weight: {
                min: "Nie może być mniej niż 0!"
            },
            prodm_diameter: {
                min: "Nie może być mniej niż 0!"
            },
            prodm_desc: {
                required: "To pole jest wymagane!"
            },
            prod_state: {
                required: "To pole jest wymagane!", 
                min: "Nie może być mniej niż 0!"
            },
            prod_quant: {
                required: "To pole jest wymagane!", 
                min: "Nie może być mniej niż 0!"
            },
            prodm_src: {
               required: "To pole jest wymagane!"
            }
        }
    });
    //supplier restock
    $("#ThisFormModal4_5").validate({
        errorClass: "error fail-alert",
        validClass: "valid success-alert",
        rules:{
            state: {
                required: true,
                min: 0
            }
        },
        messages: {
            state: {
                required: "To pole jest wymagane!",
                min: "Nie może być mniej niż 0!"
            }
        }
    });
    //supplier tables
    $("#ThisFormModal4_1").validate({
        errorClass: "error fail-alert",
        validClass: "valid success-alert",
        rules:{
            name: {
                required: true
            }
        },
        messages: {
            name: {
                required: "To pole jest wymagane!"
            }
        }
    });
    $("#ThisFormModal4_1a").validate({
        errorClass: "error fail-alert",
        validClass: "valid success-alert",
        rules:{
            name: {
                required: true
            }
        },
        messages: {
            name: {
                required: "To pole jest wymagane!"
            }
        }
    });
    //corrector
    $("#ThisFormModalRew").validate({
        errorClass: "error fail-alert",
        validClass: "valid success-alert",
        rules:{
            answer: {
                required: true
            }
        },
        messages: {
            answer: {
                required: "To pole jest wymagane!"
            }
        }
    });
    $("#ThisFormModalPrdC").validate({
        errorClass: "error fail-alert",
        validClass: "valid success-alert",
        rules:{
            description: {
                required: true
            }
        },
        messages: {
            description: {
                required: "To pole jest wymagane!"
            }
        }
    });
    $("#ThisFormModalCatC").validate({
        errorClass: "error fail-alert",
        validClass: "valid success-alert",
        rules:{
            description: {
                required: true
            }
        },
        messages: {
            description: {
                required: "To pole jest wymagane!"
            }
        }
    });
    $("#ThisFormModalTagC").validate({
        errorClass: "error fail-alert",
        validClass: "valid success-alert",
        rules:{
            description: {
                required: true
            }
        },
        messages: {
            description: {
                required: "To pole jest wymagane!"
            }
        }
    });
    //worker
    $("#ThisFormModalOrd").validate({
        errorClass: "error fail-alert",
        validClass: "valid success-alert",
        rules:{
            delivery_nr: {
                required: function (element) {
                    if($("#status").val()>=6){return true;}
                    else{ return false;} 
            }}
        },
        messages: {
            delivery_nr: {
                required: "To pole jest wymagane po wysłaniu zamówienia!"
            }
        }
    });
    //administrator
    $("#ThisFormModal1").validate({
        errorClass: "error fail-alert",
        validClass: "valid success-alert",
        rules:{
            name: {
                required: true 
            },
            price: {
                required: function (element) {
                    if($("#priceS1").hasClass('uk-hidden')){return false;}
                    else{ return true;} 
            }},
            category: {
                required: function (element) {
                    if($("#categoryS1").hasClass('uk-hidden')){return false;}
                    else{ return true;}
            }},
            active: {
                required: function (element) {
                    if($("#activeS1").hasClass('uk-hidden')){return false;}
                    else{ return true;}
            }}
        },
        messages: {
            name: {
                required: "To pole jest wymagane!"
            },
            price: {
                required: "To pole jest wymagane!"
            },
            category: {
                required: "To pole jest wymagane!"
            },
            active: {
                required: "To pole jest wymagane!" 
            }
        }
    });
    $("#ThisFormModal1a").validate({
        errorClass: "error fail-alert",
        validClass: "valid success-alert",
        rules:{
            name: {
                required: true
            },
            price: {
                required: function (element) {
                    if($("#priceS").hasClass('uk-hidden')){return false;}
                    else{ return true;} 
            }},
            category: {
                required: function (element) {
                    if($("#categoryS").hasClass('uk-hidden')){return false;}
                    else{ return true;} 
            }},
            active: {
                required: function (element) {
                    if($("#activeS").hasClass('uk-hidden')){return false;}
                    else{ return true;} 
            }},
            values: {
                required: function (element) {
                    if($("#valuesS").hasClass('uk-hidden')){return false;}
                    else{ return true;} 
            }}          
        },
        messages: {
            name: {
                required: "To pole jest wymagane!"
            },
            price: {
                required: "To pole jest wymagane!"
            },
            category: {
                required: "To pole jest wymagane!"
            },
            active: {
                required: "To pole jest wymagane!"
            },
            values: {
                required: "To pole jest wymagane!"
            } 
        }
    });
    $("#ThisFormRegAdmin").validate({
        errorClass: "error fail-alert",
        validClass: "valid success-alert",
        rules:{
            usr_name: {
                required: true,
                regName: true
            },
            usr_sname: {
                required: true,
                regName: true
            },
            usr_emali: {
                required: true,
                email: true
            },
            usr_phone: {
               required: true,
                maxlength: 22,
                minlength: 11
            },
            usr_pass: {
                minlength: 8,
                maxlength: 20,
                required: function (element) {
                    if($("#generete").is(':checked')){return false;}
                    else{ return true;}  
                 } 
            }         
        },
        messages: {
            usr_name: {
                required: "To pole jest wymagane!",
                regName: "To nie jest imię!"
            },
            usr_sname: {
                required: "To pole jest wymagane!",
                regName: "To nie jest nazwisko!"
            },
            usr_emali: {
                required: "To pole jest wymagane!",
                email: "Musi spełniać warunki pola!"
            },
            usr_phone: {
                required: "To pole jest wymagane!",
                maxlength: "Numer telefonu nie może być dłuższy niż 22 znaki",
                minlength: "Numer telefonu musi posiadać co najmniej 9 znaków"
            },
            usr_pass: {
                required: "To pole jest wymagane!",
                minlength: "Musi mieć przynajmniej 8 znaków!",
                maxlength: "Musi mieć co najwyżej 20 znaków!"
            }
        }
    });
    $("#ThisFormModal1_3").validate({
        errorClass: "error fail-alert",
        validClass: "valid success-alert",
        rules:{
            name: {
                required: function (element) {
                    if((!$("#select_ct").hasClass('uk-hidden'))&&(!$('#cattag').val())){return true;}
                    else{ return false;} 
            }},
            name2: {
                required: function (element) {
                    if($("#name_modal2").hasClass('uk-hidden')){return false;}
                    else{ return true;} 
            }}       
        },
        messages: {
            name: {
                required: "To pole jest wymagane!"
            },
            name2: {
                required: "To pole jest wymagane!"
            }
        }
    });
 });

$(document).ready(function(){
    $('form').each( function(){
        $('#tel').mask('000-000-000');
        $('#tel_firm').mask('000-000-000');
        $('#usr_phone').mask('000-000-000');
        $('.tf').mask('000-000-000');
        $('#code').mask('00-000');
        $('.cd').mask('00-000');
    });
});

function see1() {
    var x = document.getElementById("password");
    if (x.type === "password") {
        x.type = "text";
    } else {
        x.type = "password";
    }
}
function see2() {
    var x = document.getElementById("password_again");
    if (x.type === "password") {
        x.type = "text";
    } else {
        x.type = "password";
    }
}
function see3() {
    var x = document.getElementById("password_revious");
    if (x.type === "password") {
        x.type = "text";
    } else {
        x.type = "password";
    }
}
