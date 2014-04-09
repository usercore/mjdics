
    function checkAllInput(){
        var allInput = $(".input");
        for(var i = 0; i < allInput.length; i ++)
        {
            var item = allInput.get(i);
            var brother = $(item).parent().find(".input_tips");
            if(typeof(brother) == "undefined"){
                continue;
            }
            var c = $(item).val();
            if(c == ""){
                brother.show();
            }else{
                brother.hide();
            }
        }
    }
    function clearTips(){
        var brother = $(this).parent().find(".input_tips");
        if(typeof(brother) == "undefined"){
            return;
        }
        brother.hide();
    }
    
    function checkTips(){
        var brother = $(this).parent().find(".input_tips");
        if(typeof(brother) == "undefined"){
            return;
        }
        var c = $(this).val();
        if(c == ""){
            brother.show();
        }
    }
    
    function hiddenSelf(){
        $(this).hide();
        var brother = $(this).parent().find("input");
        if(typeof(brother) == "undefined"){
            return;
        }
        brother.focus();
    }