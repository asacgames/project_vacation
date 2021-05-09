<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>

<%
    String sessionId = (String) session.getAttribute("loginId");
    if(sessionId==null){sessionId="";}else{sessionId.replace("<", "&lt;");sessionId.replace(">", "&gt;");}
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>

    <link rel="icon" href="data:;base64,iVBORw0KGgo=">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap-theme.min.css">
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/js/bootstrap.min.js"></script>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>

</head>

<script>

    $(document).ready(function(){

        // 주석풀고 url 휴가신청 페이지 및 휴가정보 확인 페이지로 변경해야 함
        if('<%=sessionId%>' != null && '<%=sessionId%>' != ''){
            location.href ="/loginMain.do";
        }

    });

    function fn_login(){

        var inputId = $('#loginId').val();
        var inputPass = $('#loginPass').val();

        if(inputId == null || inputId === ''){
            alert("아이디를 입력해주세요.");
            return;
        }

        if(inputPass == null || inputPass === ''){
            alert("비밀번호를 입력해주세요.");
            return;
        }

        $.ajax({
            url: "/loginApi/login",
            type: "POST",
            data: {
                inputId : inputId,
                inputPass : inputPass
            },
            success: function( result ) {
                if(result === '1000') {
                    location.href ="/loginMain.do";
                }else if(result === '1002'){
                    alert("회원정보가 없습니다. 다시 확인 해 주세요.");
                }else if(result === '1003'){
                    alert("입력된 아이디가 없습니다.");
                }else if(result === '1004'){
                    alert("입력된 비밀번호가 없습니다.");
                }else if(result === '1005'){
                    alert("비밀번호가 다릅니다. 다시 확인 해 주세요.");
                }
            },
            error:function(error){
                alert("오류가 발생했습니다. 관리자에게 문의해주세요.");
            }
        });
    }

</script>

<body>

    <h3 class="col-sm-offset-4 col-sm-10" style="font-weight: bold;margin-top: 50px;margin-bottom: 50px;">휴가관리 시스템 로그인 <small>휴가신청을 위한 로그인이 필요합니다.</small></h3>

    <form class="form-horizontal">
        <div class="form-group">
            <label for="loginId" class="col-sm-4 control-label">Email</label>
            <div class="col-md-4">
                <input type="id" class="form-control" id="loginId" placeholder="id">
            </div>
        </div>
        <div class="form-group">
            <label for="loginPass" class="col-sm-4 control-label">Password</label>
            <div class="col-md-4">
                <input type="password" class="form-control" id="loginPass" placeholder="Password">
            </div>
        </div>
        <div class="form-group">
            <div class="col-sm-offset-4 col-sm-10">
                <span class="btn btn-default" onClick="fn_login();">Sign in</span>
            </div>
        </div>
    </form>

</body>
</html>