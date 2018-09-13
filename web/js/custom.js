// ------------------------------------------------------------
// Script eseguito al caricamento della pagina e rende visibile i relativi elementi 

$(document).ready(function() {
    $(".no_javascript").addClass("si_javascript");
    $(".no_javascript").removeClass("no_javascript");
});



// ------------------------------------------------------------
// Creazione di nuovo html per l'inserimento di una nuova skill

var skill = 1;

function add_skill() {
    skill++;
    var skills = document.getElementById('skills');
    var new_skill = document.createElement("div");
    new_skill.setAttribute("class","row");
    new_skill.innerHTML = '<div class="col-xs-6"> <div class="form-group"> <select name="nome_skill_'+skill+'" id="nome_skill_'+skill+'" class="form-control nome_skill"></select> </div> </div> <div class="col-xs-6"> <div class="form-group"> <input name="voto_skill_'+skill+'" type="number" class="form-control voto_skill"  step="1" min="1"  max="10" required> </div> </div>';
    skills.appendChild(new_skill);
    var $options = $("[name='nome_skill_1'] > option").clone();
    $('#nome_skill_'+skill+'').append($options);
    $('#maxskill').attr('value', skill);
}



// ------------------------------------------------------------
// Controllo dei dati inseriti nella form di registrazione iniziale

function signup_check() {
    
    // Controllo il nome
    var nome = document.forms["signup_form"]["nome"].value;
    nome = nome.trim();
    if (nome === "") {
        alert("Inserisci il nome");
        return false;
    } else if (/\d/.test(nome)) {
        alert("Inserisci un nome corretto");
        return false;
    }
    
    // Controllo il cognome
    var cognome = document.forms["signup_form"]["cognome"].value;
    cognome = cognome.trim();
    if (cognome === "") {
        alert("Inserisci il cognome");
        return false;
    } else if (/\d/.test(cognome)) {
        alert("Inserisci un cognome corretto");
        return false;
    }
    
    // Controllo lo username
    var username = document.forms["signup_form"]["username"].value;
    username = username.trim();
    if (username === "") {
        alert("Inserisci l'username");
        return false;
    } else if (username.length < 8) {
        alert("Lo username deve avere almeno 8 caratteri");
        return false;
    }
    
    // Controllo la e-mail
    var email = document.forms["signup_form"]["email"].value;
    email = email.trim();
    if (email === "") {
        alert("Inserisci l'e-mail");
        return false;
    } else if (!(/^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/.test(String(email)))) {
        alert("Inserisci un'e-mail corretta");
        return false;
    }
    
    // Controllo la password
    var password = document.forms["signup_form"]["password"].value;
    if (password === "") {
        alert("Inserisci la password");
        return false;
    } else if (password.length < 8) {
        alert("La password deve avere minimo 8 caratteri");
        return false;
    } else if (password.lenght > 16) {
        alert("La password deve avere massimo 16 caratteri");
        return false;
    } else if (/\s/g.test(password)) {
        alert("La password non può contenere spazi bianchi");
        return false;
    } else if (!(/^[a-zA-Z0-9- ]*$/.test(password))) {
        alert("La password non può contenere caratteri speciali");
        return false;
    }
    
    // Controllo la data di nascita
    var data_nascita = document.forms["signup_form"]["data_nascita"].value;
    if (data_nascita === "") {
        alert("Inserisci la data di nascita");
        return false;
    }
    var today = new Date();
    var dd = today.getDate();
    var mm = today.getMonth()+1; //January is 0!
    var yyyy = today.getFullYear();
    if(dd < 10) {
        dd = '0' + dd;
    } 
    if(mm < 10) {
        mm = '0' + mm;
    } 
    today = yyyy + '-' + mm + '-' + dd;
    if (data_nascita > today) {
        alert("Vieni dal futuro?");
        return false;
    }
    
    // Controllo la biografia
    var biografia = document.forms["signup_form"]["biografia"].value;
    biografia = biografia.trim();
    if (biografia === "") {
        alert("Inserisci la biografia");
        return false;
    } else if (biografia.match(/^[0-9]+$/)) {
        alert("La biografia non può essere composta da soli numeri");
        return false;
    } else if (biografia.length < 50) {
        alert("La biografia deve avere almeno 50 caratteri");
        return false;
    }
    
    // Controllo le skill
    var valori_skill = new Set();
    for (i = 1; i <= skill; i++) {
        valori_skill.add($('#nome_skill_'+i).val());
    }
    if (valori_skill.size !== skill) {
        alert("Sono presenti delle skill ripetute");
        return false;
    }
    
    // Controllo i voti delle skill
    for (i = 1; i <= skill; i++) {
        var voto = $('#nome_skill_'+i).val();
        if (voto < 1 || voto === "" || voto > 10) {
            alert("I voti degli skill devono essere compresi tra 1 e 10");
            return false;
        }
    }
    
    document.signup_form.action = "SignUpFiles";
    
    return true;
    
}



// ------------------------------------------------------------
// Controllo le skill che l'utente vuole aggiungersi modificando il suo profilo 

function check_add_skills() {
    
    // Controllo le skill
    var valori_skill = new Set();
    for (i = 1; i <= skill; i++) {
        valori_skill.add($('#nome_skill_'+i).val());
    }
    if (valori_skill.size !== skill) {
        alert("Sono presenti delle skill ripetute");
        return false;
    }
    
    // Controllo i voti delle skill
    for (i = 1; i <= skill; i++) {
        var voto = $('#nome_skill_'+i).val();
        if (voto < 1 || voto === "" || voto > 10) {
            alert("I voti degli skill devono essere compresi tra 1 e 10");
            return false;
        }
    }
    
    return true;
    
}



// ------------------------------------------------------------
// Controllo dei dati inseriti nella form di registrazione finale

function signup_cv_pic_check() {
    // Controllo il CV
    var CV_text = document.forms["signup_form"]["CV_text"].value;
    CV_text = CV_text.trim();
    var check_CV_DPF = document.getElementById("CV_PDF").files.length;
    if (CV_text !== "" && check_CV_DPF !== 0) {
        alert("Il CV deve essere inserito con un solo metodo.");
        return false;
    } else if (CV_text === "" && check_CV_DPF === 0) {
        alert("Inserisci il CV.");
        return false;
    } else if (CV_text.match(/^[0-9]+$/) && check_CV_DPF === 0) {
        alert("Inserisci il CV in modo corretto.");
        return false;
    }
    
    //Controllo la foto profilo
    var check_foto_profilo = document.getElementById("foto_profilo").files.length;
    if(check_foto_profilo === 0) {
        alert("Carica la foto profilo.");
        return false;
    }
    
    return true;
    
}

// ------------------------------------------------------------
// Cambia la src dell'immagine del profilo durante la registrazione con
// l'anteprima di quella caricata momentanemanete (bisogna metterla dentro
// una funziona onchange sull'input o qualcosa del genere)
$("#foto_profilo").on("change",function(){
    var file = this.files[0];
    var reader = new FileReader();
    reader.onload = function(){
        document.getElementById('immagine_profilo').src = this.result;
    };
    reader.readAsDataURL(file);
});

//var inp = document.getElementById('foto_profilo');
//inp.addEventListener('change', function(e){
//    var file = this.files[0];
//    var reader = new FileReader();
//    reader.onload = function(){
//        document.getElementById('immagine_profilo').src = this.result;
//    };
//    reader.readAsDataURL(file);
//},false);

// ------------------------------------------------------------

function popupInvite(){
    alert("Richiesta inviata con successo");
    window.location.reload(true);
}

