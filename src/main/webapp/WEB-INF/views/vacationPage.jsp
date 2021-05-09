<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>

<%
    String sessionId = (String) session.getAttribute("loginId");
    String sessionName = (String) session.getAttribute("loginName");
    if(sessionId==null){sessionId="";}else{sessionId.replace("<", "&lt;");sessionId.replace(">", "&gt;");}
    if(sessionName==null){sessionName="";}else{sessionName.replace("<", "&lt;");sessionName.replace(">", "&gt;");}
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>

    <style>
        .tb1 {text-align: center;}
        .tb1 th {text-align: center;}

        .left {float: left;}
        .right {float: right;}
    </style>

</head>

<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap-theme.min.css">
<link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">

<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/js/bootstrap.min.js"></script>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script src="/static/main/js/datepicker.js/datepicker.js"></script>
<script>

    $( function() {

        $('#startSelectTime').hide();
        $('#endSelectTime').hide();

        // 휴가 시작일에 대한 설정
        $("#vacationStart").datepicker({
              dateFormat: 'yy-mm-dd'
            , minDate: new Date('${today}')
            , regional: "ko"
            , beforeShowDay: function(date){
                var day = date.getDay();
                return [(day != 0 && day != 6)];
              }
            , onSelect:function (dateText){
                changeCal('start');
            }
        });

        // 휴가 종료일에 대한 설정
        $("#vacationEnd").datepicker({
              dateFormat: 'yy-mm-dd'
            , minDate: new Date('${today}')
            , regional: "ko"
            , beforeShowDay: function(date){
                var day = date.getDay();
                return [(day != 0 && day != 6)];
            }
            , onSelect:function (dateText) {
                changeCal('end');
            }
        });

        // 휴가시작 반차/반반차 설정
        $("#startHalfYn").change(function(){
            if($("#startHalfYn").is(":checked")){
                $('#startSelectTime').show();
                changeCal('start');
            }else{
                $('#startSelectTime').hide();
            }
        });

        // 휴가종료 반차/반반차 설정
        $("#endHalfYn").change(function(){
            if($("#endHalfYn").is(":checked")){
                $('#endSelectTime').show();
                changeCal('end');
            }else{
                $('#endSelectTime').hide();
            }
        });

        // 툴팁 설정(부트스트랩)
        $('[data-toggle="tooltipLogout"]').tooltip()

        $('#startSelectTime').change(function(){
            changeCal('start');
        });

        $('#endSelectTime').change(function(){
            changeCal('end');
        });


        $('#comment').on('keyup', function() {
            $('#comment_cnt').html("("+$(this).val().length+" / 100)");

            if($(this).val().length > 100) {
                $(this).val($(this).val().substring(0, 100));
                $('#comment_cnt').html("(100 / 100)");
            }
        });

    });

    // 날짜 차이 계산
    function dateCal(start, end){
        var startDate = start;
        var endDate = end;
        var arr1 = startDate.split('-');
        var arr2 = endDate.split('-');
        var da1 = new Date(arr1[0], arr1[1], arr1[2]);
        var da2 = new Date(arr2[0], arr2[1], arr2[2]);

        var count = 0;

        // 주말을 제외하고 계산
        while(true) {
            var temp_date = da1;
            if(temp_date.getTime() > da2.getTime()) {
                break;
            } else {
                var tmp = temp_date.getDay();
                if((tmp != 2 && tmp != 3)) {
                    count++;
                }
                temp_date.setDate(da1.getDate() + 1);
            }
        }

        var result = 0.0;
        result = count;

        if($("#startHalfYn").is(":checked") || $("#endHalfYn").is(":checked")){
            result = result - 1;
            if($("#startHalfYn").is(":checked") && !$("#endHalfYn").is(":checked")){
                var startTime = parseInt($('#startSelectTime').val().substring(0,2));
                var endTime = 18;
                result = result + timeCal(startTime, endTime);
            }else if($("#endHalfYn").is(":checked") && !$("#startHalfYn").is(":checked")){
                var startTime = 9;
                var endTime = parseInt($('#endSelectTime').val().substring(0,2));
                result = result + timeCal(startTime, endTime);
            }else{
                var startTime = parseInt($('#startSelectTime').val().substring(0,2));
                var endTime = parseInt($('#endSelectTime').val().substring(0,2));
                result = result + timeCal(startTime, endTime);
            }
        }

        return result;
    }

    // 시간 차이 값을 반차/반반차로 변경
    function timeCal(startTime, endTime){
        var compareTime = endTime - startTime;
        var resultTime  = 0.0;

        if(compareTime === 0){
            resultTime = 0;
        }else if(compareTime === 2 || compareTime === 3){
            resultTime = 0.25;
        }else if(compareTime === 4 || compareTime === 5 || compareTime === 6){
            resultTime = 0.5;
        }else if(compareTime === 7){
            resultTime = 0.75;
        }else if(compareTime === 9){
            resultTime = 1;
        }else if(compareTime === -2 || compareTime === -3){
            resultTime = -0.25;
        }else if(compareTime === -4 || compareTime === -5 || compareTime === -6){
            resultTime = -0.5;
        }else if(compareTime === -7){
            resultTime = -0.75;
        }else if(compareTime === -9){
            resultTime = -1;
        }

        return resultTime;
    }


    // 휴가 신청
    function vacationRequest(){


        var startDate = $('#vacationStart').val();
        var endDate = $('#vacationEnd').val();
        var startTime = "";
        var endTime = "";
        var userId = $('#userId').val();
        var useVacationDate = parseFloat($('#useVacationDate').val());
        var restVacationDay = parseFloat($('#useDay').val());
        var comment = $('#comment').val();

        if(restVacationDay < useVacationDate){
            alert("연차일 수가 부족합니다.");
            return;
        }

        if(useVacationDate <= 0){
            alert("휴가신청 날짜가 올바르지 않습니다.");
            return;
        }

        if($("#startHalfYn").is(":checked")){
            startTime = $('#startSelectTime').val();
        }

        if($("#endHalfYn").is(":checked")){
            endTime = $('#endSelectTime').val();
        }

        startDate = startDate.replace(/\-/g,'');
        endDate = endDate.replace(/\-/g,'');
        startTime = startTime.replace(/\:/g,'');
        endTime = endTime.replace(/\:/g,'');

        var reqData = {
             "startDate" : startDate
            ,"endDate" : endDate
            ,"userId" : userId
            ,"startTime" : startTime
            ,"endTime" : endTime
            ,"comment" : comment
        };

        $.ajax({
            url: "/vacation/register",
            type: "POST",
            dataType: "json",
            data: JSON.stringify(reqData) ,
            contentType: "application/json; charset=UTF-8",
            success: function( result ) {
                alert(result.resultMsg);
                if(result.resultCode === '1000') {
                    location.href = "/vacationListPage.do";
                }
            },
            error:function(error){
                alert(error);
            }
        });

    }

    function changeCal(startEnd){
        var startDate = $('#vacationStart').val();
        var endDate = $('#vacationEnd').val();
        var dateText = $('#vacationEnd').val();

        if($("#startHalfYn").is(":checked") || $("#endHalfYn").is(":checked")) {
            if($("#startHalfYn").is(":checked") && !$("#endHalfYn").is(":checked")){
                var startTime = startDate+$('#startSelectTime').val();
                var endTime = dateText+"09:00";
            }else if($("#endHalfYn").is(":checked") && !$("#startHalfYn").is(":checked")){
                var startTime = startDate+"09:00";
                var endTime = dateText+$('#endSelectTime').val();
            }else{
                var startTime = startDate+$('#startSelectTime').val();
                var endTime = dateText+$('#endSelectTime').val();
            }

            if(startEnd == 'start'){
                if (startTime > endTime) {
                    alert("휴가 시작일이 종료일 보다 클 수 없습니다.");
                    $("#vacationStart").val(endDate);
                    $('#startSelectTime').val('09:00');
                    $('#endSelectTime').val('11:00');
                    $('#useVacationDate').val('0.25');
                    return;
                }
            }else {
                if (endTime < startTime) {
                    alert("휴가 시작일이 종료일 보다 클 수 없습니다.");
                    $("#vacationEnd").val(startDate);
                    $('#startSelectTime').val('09:00');
                    $('#endSelectTime').val('11:00');
                    $('#useVacationDate').val('0.25');
                    return;
                }
            }

        }else{
            if(startEnd == 'start'){
                if (startDate > endDate) {
                    alert("휴가 시작일이 종료일 보다 클 수 없습니다.");
                    $("#vacationStart").val(endDate);
                    $('#startSelectTime').val('09:00');
                    $('#endSelectTime').val('11:00');
                    $('#useVacationDate').val('1');
                    return;
                }
            }else {
                if (endDate < startDate) {
                    alert("휴가 종료일이 시작일 보다 작을 수 없습니다.");
                    $("#vacationEnd").val(startDate);
                    $('#startSelectTime').val('09:00');
                    $('#endSelectTime').val('11:00');
                    $('#useVacationDate').val('1');
                    return;
                }
            }
        }

        $('#useVacationDate').val(dateCal(startDate, dateText));
    }

</script>

<body>

    <input type="hidden" id="userId" value="<%=sessionId%>"></input>
    <input type="hidden" id="useDay" value="${vacationRest}"></input>

    <div style="margin-left: 150px;margin-right: 150px;">
        <div class="right" style="margin-left: 20px;">
            <span class="glyphicon glyphicon-log-out" data-toggle="tooltipLogout" data-placement="left" title="로그아웃" style="font-size: 15px; cursor:pointer;" onclick="location.href='/logout.do'"></span>
        </div>
        <div class="left" style="font-weight: bold;">
            <span class="glyphicon glyphicon-home" data-toggle="tooltipLogout" data-placement="left" title="메인화면" style="font-size: 28px; cursor:pointer;" onclick="location.href='/main'"></span>
        </div>
        <div class="right"><%=sessionName%>님 접속 중</div>
    </div>

    <h2 style="text-align: center; font-weight: bold; margin-top: 50px;margin-bottom: 50px;">휴가신청 화면</h2>

    <table class="table table-striped col-sm-offset-1 tb1" style="width:80%">
        <colgroup>
            <col style="width: 50%";>
            <col style="width: 50%";>
        </colgroup>
        <tbody>
            <tr>
                <th style="border-right: 1px solid #dad9d9;">신청자</th>
                <th>소속</th>
            </tr>
            <tr>
                <td style="border-right: 1px solid #dad9d9;"><%=sessionName%></td>
                <td>${positionNm}/${groupNm}</td>
            </tr>
        </tbody>
    </table>

    <table class="table table-striped col-sm-offset-1 tb1" style="width:80%">
        <colgroup>
            <col style="width: 33.3%";>
            <col style="width: 33.3%";>
            <col style="width: 33.3%";>
        </colgroup>
        <tbody>
        <tr>
            <th style="border-right: 1px solid #dad9d9;">연차</th>
            <th style="border-right: 1px solid #dad9d9;">사용한 연차</th>
            <th>남은 연차 (사용가능)</th>
        </tr>
        <tr>
            <td style="border-right: 1px solid #dad9d9;">${vacation}일</td>
            <td style="border-right: 1px solid #dad9d9;">${vacationUse}일</td>
            <td>${vacationRest}일</td>
        </tr>
        <tr><td colspan="3" style="background-color: white; text-align: end; font-weight: bold;">* 0.5일은 반차, 0.25일은 반반차를 의미합니다.</td></tr>
        </tbody>
    </table>

    <table class="table table-striped col-sm-offset-1 tb1" style="width:80%">
        <colgroup>
            <col style="width: 33.3%";>
            <col style="width: 33.3%";>
            <col style="width: 33.3%";>
        </colgroup>
        <tbody>
        <tr>
            <th style="border-right: 1px solid #dad9d9;">휴가 시작일</th>
            <th style="border-right: 1px solid #dad9d9;">휴가 종료일</th>
            <th>사용 요청 연차일</th>
        </tr>
        <tr>
            <td style="border-right: 1px solid #dad9d9;">
                <p>
                    <input type="text" id="vacationStart" style="text-align: center; width: 150px;" value="${today}"> <span class="glyphicon glyphicon-calendar" id="vacationStartIcon" aria-hidden="true"></span>
                    &ensp;
                    <select id="startSelectTime">
                        <option>09:00</option>
                        <option>11:00</option>
                        <option>14:00</option>
                        <option>16:00</option>
                    </select>
                </p>
                <input type="checkbox" id="startHalfYn" aria-label="..."> 반차/반반차 사용유무
            </td>
            <td style="border-right: 1px solid #dad9d9;">
                <p>
                    <input type="text" id="vacationEnd" style="text-align: center; width: 150px;" value="${today}"> <span class="glyphicon glyphicon-calendar" id="vacationEndIcon" aria-hidden="true"></span>
                    &ensp;
                    <select id="endSelectTime">
                        <option>11:00</option>
                        <option>14:00</option>
                        <option>16:00</option>
                        <option>18:00</option>
                    </select>
                </p>
                <input type="checkbox" id="endHalfYn" aria-label="..."> 반차/반반차 사용유무
            </td>
            <td>
                <input type="text" id="useVacationDate" style="text-align: center; width: 65px;" readonly value="1"> 일
            </td>
        </tr>
        </tbody>
    </table>

    <table class="col-sm-offset-1 tb1" style="width:80%;margin-top: 30px;margin-bottom: 30px;">
        <colgroup>
            <col style="width: 100%";>
        </colgroup>
        <tbody>
            <tr>
                <td>
                    <div class="input-group" style="width: 100%;height: 200px;">
                        <span class="input-group-addon" id="sizing-addon2">내용</span>
                        <textarea type="text" id="comment" class="form-control" placeholder="" aria-describedby="sizing-addon2" style="z-index: auto;height: 200px;"></textarea>
                    </div>
                    <div id="comment_cnt">(0 / 100)</div>
                </td>
            </tr>
        </tbody>
    </table>

    <table class="col-sm-offset-1 tb1" style="width:80%">
        <colgroup>
            <col style="width: 100%";>
        </colgroup>
        <tbody>
        <tr>
            <td>
                <div class="form-group">
                    <div>
                        <span class="btn btn-default" onClick="vacationRequest();">휴가신청</span>
                    </div>
                </div>
            </td>
        </tr>
        </tbody>
    </table>

</body>
</html>
