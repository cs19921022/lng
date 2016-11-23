/* 
* @Author: anchen
* @Date:   2016-11-09 12:45:17
* @Last Modified by:   anchen
* @Last Modified time: 2016-11-18 09:44:39
*/
$(function(){
        $(".haschild").click(function(){
         if($(this).children('a').is(":hidden"))
        {
          $(this).addClass('highlight')
          .children('a').show().end()
          .siblings().removeClass('highlight')
          .children('a').hide();
          $(this).find("#tubiao").attr('class', 'glyphicon glyphicon-chevron-down');
          $(this).siblings().find("#tubiao").attr('class', 'glyphicon glyphicon-chevron-left');
        }
        else
        {
            $(this).removeClass('highlight')
            .children('a').hide();
            $(this).find("#tubiao").attr('class', 'glyphicon glyphicon-chevron-left');
        }
        });
       });