<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>

<%
    String sessionName = (String) session.getAttribute("loginName");
    if(sessionName==null){sessionName="";}else{sessionName.replace("<", "&lt;");sessionName.replace(">", "&gt;");}
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>

    <link rel="icon" href="data:;base64,iVBORw0KGgo=">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap-theme.min.css">
    <link rel="stylesheet" href="/main/css/common.css">

    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/js/bootstrap.min.js"></script>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>

</head>

<script>

    function vacationReg(){
        $('#loading').show();
        location.href = "/vacationPage.do";
    }

    function vacationList(){
        $('#loading').show();
        location.href = "/vacationListPage.do";
    }

</script>

<body>

    <div style="margin-left: 150px;margin-right: 150px;">
        <div class="right" style="margin-left: 20px;">
            <span class="glyphicon glyphicon-log-out" data-toggle="tooltipLogout" data-placement="left" title="로그아웃" style="font-size: 15px; cursor:pointer;" onclick="location.href='/logout.do'"></span>
        </div>
        <div class="left" style="font-weight: bold;">
            <span class="glyphicon glyphicon-home" data-toggle="tooltipLogout" data-placement="left" title="메인화면" style="font-size: 28px; cursor:pointer;" onclick="location.href='/main'"></span>
        </div>
        <div class="right"><%=sessionName%>님 접속 중</div>
    </div>

    <h2 style="text-align: center; font-weight: bold; margin-top: 50px;margin-bottom: 50px;">메인 페이지</h2>

    <div class="page-header" style="text-align: center;margin-left: -85px;">
        <div style="font-size: 25px;">
            <span class="label label-default">총 연차 : ${member.vacation}일</span>
            <span class="label label-default">사용한 연차 : ${member.vacation_use}일</span>
            <span class="label label-default">남은 연차 : ${restVacationNum}일</span>
        </div>
    </div>

    <table class="col-sm-offset-1 tb1" style="width:80%">
        <colgroup>
            <col style="width: 50%";>
            <col style="width: 50%";>
        </colgroup>
        <tbody>
        <tr>
            <td style="text-align: end; padding: 30px;">
                <img src="/main/images/register.png" onClick="vacationReg();" style="cursor:pointer;"></img>
            </td>
            <td style="text-align: start;">
                <img src="/main/images/registerList.png" onClick="vacationList();" style="cursor:pointer;"></img>
            </td>
        </tr>
        </tbody>
    </table>

</body>
</html>