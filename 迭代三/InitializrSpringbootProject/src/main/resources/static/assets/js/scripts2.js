
jQuery(document).ready(function() {
	
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
    
    
    
    var hostURL = "/";
    
    /*
        Form validation
    */
    $('.registration-form input[type="text"], .registration-form textarea').on('focus', function() {
    	$(this).removeClass('input-error');
    });
    
    $('.registration-form').on('submit', function(e) {
    	var username;
        var password;
        var email;
        
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
        
        $(this).find('input#email').each(function(){
    		if( $(this).val() == "" ) {
    			e.preventDefault();
    			$(this).addClass('input-error');
    		}
    		else {
    			$(this).removeClass('input-error');
                        email  = $(this).val();
                        num+=1;
    		}
    	});
        
        if (num >= 3){
            
            $.post(hostURL,{"username":username,"password":password},function (data) {
                if (data === "1") {
                    alert("µÇÂ½³É¹¦£¡");
                }
            });
            
        }
        
    	
    });
    
    
});
