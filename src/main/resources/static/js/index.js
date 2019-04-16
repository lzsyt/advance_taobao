
function upload(f){
    var file = $("#antzone").val();
    var fileName = file.substr(file.lastIndexOf("\\") + 1);
    var re = /[\u0391-\uFFE5]+/g;
    if(fileName.match(re) != null){
            file  = null;
            alert("文件名不能包含汉字！");
            $("#antzone").val("")
            return false;
        }else {
            var str = "";
            for (var i = 0; i < f.length; i++) {
                var reader = new FileReader();
                reader.readAsDataURL(f[i]);
                reader.onload = function (e) {
                    str += '<img src="' + e.target.result + '"/>';
                    document.getElementById("ImgBox").innerHTML = str;
                }
            }
        }
}



    function uploads(f){
    var file = $("#antzones").val();
    var fileName = file.substr(file.lastIndexOf("\\") + 1);
    var re = /[\u0391-\uFFE5]+/g;
    if(fileName.match(re) != null){
        file  = null;
        alert("文件名不能包含汉字！");
        $("#antzones").val("")
        return false;
    }else {
        var str = "";
        for (var i = 0; i < f.length; i++) {
            var reader = new FileReader();
            reader.readAsDataURL(f[i]);
            reader.onload = function (e) {
                str += '<img src="' + e.target.result + '" />';
                document.getElementById("ImgBoxs").innerHTML = str;

            }
        }
    }
}

function uploadss(f){
    var file = $("#antzoness").val();
    var fileName = file.substr(file.lastIndexOf("\\") + 1);
    var re = /[\u0391-\uFFE5]+/g;
    if(fileName.match(re) != null){
        file  = null;
        alert("文件名不能包含汉字！");
        $("#antzoness").val("")
        return false;
    }else {
        var str = "";
        for (var i = 0; i < f.length; i++) {
            var reader = new FileReader();
            reader.readAsDataURL(f[i]);
            reader.onload = function (e) {
                str += '<img src="' + e.target.result + '" />';
                document.getElementById("ImgBoxss").innerHTML = str;
            }
        }
    }
}



function CheckTitle(obj){
    var title=obj.value;
    var res = /[\u0391-\uFFE5]+/g;
    if (title.match(res) != null) {
        obj.value = null;
        alert("不允许输入中文")
        obj.select();
        return false;
    }
}

/**
 * 前台登录
 */

function openDialogs(){
    document.getElementById('useInfo').style.display='block';
}

function closeDialog(){
    document.getElementById('light').style.display='none';
    document.getElementById('registDiv').style.display='none';

}
function openDivlog(){
    $("#MyForm")[0].reset();
    $("#sp").html("");
    $(" #forget-sp").html("");
    document.getElementById('light').style.display='block';
    document.getElementById('MyForm').style.display='block';
    document.getElementById('forgetForm').style.display='none';
}

function openForget(){
    $("#forgetForm")[0].reset();
    document.getElementById('MyForm').style.display='none';
    document.getElementById('forgetForm').style.display='block';
    $("#forgetForm input:text").css("border-color","");
}

function openDivreg(){
    $("#registForm")[0].reset();
    $("#spTitle").html("");
    document.getElementById('registDiv').style.display='block';

}

$(document).ready(function(){
    $("#useInfo").mouseleave(function(){
        $("#useInfo").css("display","none");
    });

});

