
jQuery(document).ready(function() {

        console.log(window.localStorage.userName);
        $("#username").val(window.localStorage.userName);

    /*
        Fullscreen background
    */    
    $.backstretch("assets/img/backgrounds/1.jpg");
    
    $('#top-navbar-1').on('shown.bs.collapse', function(){
    	$.backstretch("resize");
    });
    $('#top-navbar-1').on('hidden.bs.collapse', function(){
    	$.backstretch("resize");
    });
    
    
    
    
    /*
        Form validation
    */
    $('.registration-form input[type="text"], .registration-form textarea').on('focus', function() {
    	$(this).removeClass('input-error');
    });
    
    
    $('.registration-form').on('submit', function(e) {
        
        window.localStorage.userName=document.getElementById("username").value;
        
        
    	var username;
        var password;
        
        var num = 0;
        
    	$(this).find('input#username').each(function(){
    		if( $(this).val() == "" ) {
    			e.preventDefault();
    			$(this).addClass('input-error');
    		}
    		else {
    			$(this).removeClass('input-error');
                        username  = $(this).val();
                        num+=1;
    		}
    	});
        
        $(this).find('input#password').each(function(){
    		if( $(this).val() == "" ) {
    			e.preventDefault();
    			$(this).addClass('input-error');
    		}
    		else {
    			$(this).removeClass('input-error');
                        username  = $(this).val();
                        num+=1;
    		}
    	});
        
        
        
        if (num >= 2){
            
            $.post("/login",{"username":username,"password":password},function (data) {
                if (data=='1') {
                    alert("µÇÂ½³É¹¦£¡");
                }
            });
            
        }
        
    	
    });
    
    
});
