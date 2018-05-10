/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */



$(document).ready(reloadContent);

function reloadContent(){
    $('.script_enabled').removeClass('display_none'); 
   
    $("#currentTaskPage").keypress(function(e) {
        if(e.which === 13) {
            var urlParams = new URLSearchParams(window.location.search);
            goToTaskPage(urlParams.get('task_id'),this.value,10);
        }
    });    
    
    $("#currentSearchPage").keypress(function(e) {
        if(e.which === 13) {
            var urlParams = new URLSearchParams(window.location.search);
            goToSearchPage(urlParams.get('filter'),this.value,8);
        }
    });  
}
function setVote(form){
    console.log($(form).children("input[name='vote']").val());
    $.ajax({
        type: 'POST',
        url: 'setVote',
        data:{
            vote:       $(form).children('.slider_box').children("input[name='vote']").val(),
            task_id:    $(form).children("input[name='task_id']").val(),
            user_id:    $(form).children("input[name='user_id']").val(),
            async:      "1"  
        },
        success: function (response) {
            if(response >= 0){
                $(form).children("div").children("div:contains("+response+")").addClass('selected_val');
                $(form).children('.slider_box').children("input[name='vote']").remove();
                $(form).children('div').children('button').text("Modifica");
                $(form).children('div').children('button').attr("onclick","editVote($(this).closest(\'form\'))");
            }else{
                
            }
        }
    });

   
}

function editVote(form){
   
    $.ajax({
        type: 'POST',
        url: 'setVote',
        data:{
            vote:       "0",
            task_id:    $(form).children("input[name='task_id']").val(),
            user_id:    $(form).children("input[name='user_id']").val(),
            async:      "1"  
        },
        success: function (response) {
            let slider = document.createElement("input");
            slider.setAttribute("type","range");
            slider.setAttribute("name","vote");
            slider.setAttribute("min","1");
            slider.setAttribute("max","5");
            slider.setAttribute("step","1");
            slider.setAttribute("class","vote_slider");
            $(form).children("div").children().removeClass("selected_val");
            $(form).children("div[class='slider_box']").append(slider);
            $(form).children('div').children('button').text("Conferma");
            $(form).children('div').children('button').attr("onclick","setVote($(this).closest(\'form\'))");
            
        }
    });

}

function goToTaskPage(taskid,page,perpage){
    $.ajax({
        type: 'POST',
        url: 'TaskForum',
        data:{
            page:       page,
            task_id:    taskid,
            perpage:    perpage,
            async:      "1"  
        },
        success: function (response) {
            if(response){
                $('#updatable_content').html(response);
                $("html, body").animate({scrollTop: $('.main--content').offset().top - 140}, "slow");
                reloadContent();
            }else{
                
            }
        }
    });
}

function goToSearchPage(filter,page,perpage){
    $.ajax({
        type: 'POST',
        url: 'search',
        data:{
            page:       page,
            filter:     filter,
            perpage:    perpage,
            async:      "1"  
        },
        success: function (response) {
            if(response){
                $('#updatable_content').html(response);
                $("html, body").animate({scrollTop: $('.main--content').offset().top - 140}, "slow");
                reloadContent();
            }else{
                
            }
        }
    });
}
