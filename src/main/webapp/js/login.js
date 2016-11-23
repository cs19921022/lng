/* 
* @Author: anchen
* @Date:   2016-11-22 10:02:04
* @Last Modified by:   anchen
* @Last Modified time: 2016-11-22 20:30:58
*/

$(function(){
    $("#login").click(function(){
        var username=$("#username").val();
        var password=$("#password").val();
        $.ajax({
            url: 'localhost:80/lng/restapi/account/login',
            type: 'post',
            dataType: 'json',
            data: {username: 'username',password:'password'},
            success:function(data, textStatus){
                //if(data=="token")
                //{window.location.href="/index.html";}
               // else
                //{alert('用户名密码错误');}
                //
                alert(data);
            }   
    )}
});