/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

$(document).ready(reloadContent);

$("skill_filter").ready(function(){
    skills = $("#skill_filter").children(".filter-item").length + 1;
});
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
    
    $("#project_select").change(function(){
        var project = $("#project_select").val();
        console.log(project);
        $("#task_select").children().remove();
        $.ajax({
            type: 'POST',
            url: 'getTasks',
            data:{
                proj_id:    project
            },
            success: function (response) {
                $(".filter_task").removeClass("display_none");
                var withoutbrackets = response.substring(1,response.length-1);
                var split = withoutbrackets.split(", ");
                var biarray = new Array();
                var result = new Array();
                for(var i=0;i<split.length;i++){
                    biarray[i]=split[i].split("=");
                }
                var opt=document.createElement("option");
                opt.setAttribute("value",-1);
                opt.setAttribute("disabled","disabled");
                opt.setAttribute("selected","selected");
                opt.setAttribute("hidden","hidden");
                opt.innerHTML="Seleziona un task";
                $("#task_select").append(opt);
                for(var i=0;i<biarray.length;i++){
                    opt = document.createElement("option");
                    opt.setAttribute("value",biarray[i][0]);
                    opt.innerHTML=biarray[i][1];
                    $("#task_select").append(opt);
                }
            }
        });
    });
    
    $("#type_select").change(function(){
        $("#btn_delete_type").removeClass("display_none");
        $("#btn_edit_type").removeClass("display_none");
        $("#selected_type").val($("#type_select").val());
    });
    
    $("#btn_edit_type").on("click",function(){
        $("#btn_edit_type").addClass("display_none");
        $("#input_edit_type").removeClass("display_none");
    });
    
    $("#btn_delete_type").on("click",function(){
         $.ajax({
            type: 'POST',
            url: 'doadmintypedelete',
            data:{
                type_id: $("#type_select").val()
            },
            success: function () {
                window.location.replace("type");
            }
        });
    });
    
    $("#btn_new_type").on("click",function(){
        $.ajax({
            type: 'POST',
            url: 'doadmintypeadd',
            data:{
                newtypename: $("#newtype").val()
            },
            success: function () {
                window.location.replace("type");
            }
        });
    });
    
    $("#btn_new_skill").on("click",function(){
        var parent = $("#skill_parent").val() !== null && $("#select_father").checked? $("#skill_parent").val() : 0;
        var nome = $("#newskill").val();
        if(nome === "") return
        $.ajax({
            type: 'POST',
            url: 'doadminskilladd',
            data:{
                parent: parent,
                newskill: nome
            },
            success: function () {
                window.location.replace("skill");
            }
        });
    });
    
    $("#skill_select").change(function(){
        $("input[name='task_parent']").removeAttr("disabled");
    });
    
    $("#btn_set_types").on("click",function(){
        var types = [];
        var skill = $("#skill_select").val();
        $("input[type=checkbox]:checked").each(function(i){
            types[i] = $(this).val();
        });
       
        if(types.length===0 || skill=== null) return;
        else{
            $.ajax({
            type: 'POST',
            dataType:'json',
            url: 'doadminsetskilltypes',
            data:{
                skill: skill,
                types: types
            },
            success: function () {
                window.location.replace("skill");
            }
        });
        }
    });
    
    $("#btn_delete_skill").on("click",function(){
        if($("#skill_edit").val() === null)
            return;
        $.ajax({
            type: 'POST',
            url: 'doadminskilldelete',
            data:{
                skill_id: $("#skill_edit").val()
            },
            success: function () {
                window.location.replace("skill");
            }
        });
    });
    
    $("#btn_edit_skill").on("click",function(){
        $("#edit_box").toggleClass("display_none");
    });
    
    
    $("#skill_edit").change(function(){
        var selected=$("#skill_edit").val();
        console.log(selected);
        if(actual_skill === 0){
            actual_skill=$("#skill_edit_parent option[value='"+selected+"']");
            $("#skill_edit_parent option[value='"+selected+"']").remove();
        }else{
            $("#skill_edit_parent").append(actual_skill);
            actual_skill=$("#skill_edit_parent option[value='"+selected+"']");
            $("#skill_edit_parent option[value='"+selected+"']").remove();
        }
    });
    
    $("#edit_skill_conf").on("click",function(){
        var newname = $("#new_name_skill").val();
        var newparent = $("#skill_edit_parent").val() !== null? $("#skill_edit_parent").val() : 0;
        if(newname==="" && newparent===null) return;
        $.ajax({
            type: 'POST',
            url: 'doadminskilledit',
            data:{
                skill_id: $("#skill_edit").val(),
                newname: newname,
                newparent: newparent,
            },
            success: function () {
                window.location.replace("skill");
            }
        });
        
    });
    
    
    
}
var skills = 2;
var actual_skill = 0;
function add_filter_skill(){
    var skill_container = $("#skill_filter");
    let new_skill = document.createElement("div");
    new_skill.setAttribute("class","filter-item skill_filter");
    new_skill.innerHTML = "<div class=\"row\"> <div class=\"col-xs-9 col-xxs-9\"> <label class=\"mt--30\"> <span class=\"text-darker ff--primary fw--500\">Skill</span> <select name=\"skills_"+skills+"\" class=\"form-control form-sm\" data-trigger=\"selectmenu\"> </select> </label> </div> <div class=\"col-xs-3 col-xxs-3\"> <label class=\"mt--30\"> <span class=\"text-darker ff--primary fw--500\">Livello</span> <input type=\"number\" class=\"form-control\" min=\"1\" max=\"10\" name=\"skill_level_"+skills+"\"/> </label> </div> </div> <button class=\"btn btn-red btn-delete\" onclick=\"remove_filter_skill(this)\"><i class=\"fa fa-trash \"></i></button>";
    var options = $('.filter-item select').first().children("option").clone();
    skill_container.append(new_skill);
    new_skill = $('.skill_filter select').last();
    new_skill.append(options);

    new_skill.customSelectMenu();
    skills++;
}

function remove_filter_skill(element){
    $(element).parent().remove();
    skills--;
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
