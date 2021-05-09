<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

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
</head>

<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap-theme.min.css">
<link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<link rel="stylesheet" href="/main/css/common.css">

<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/js/bootstrap.min.js"></script>
<script src="/main/js/datepicker.js"></script>
<script>

    // 캘린더 설정(한글화) - 주말 포함
    $.datepicker.setDefaults({
        dateFormat: 'yymmdd',
        prevText: '이전 달',
        nextText: '다음 달',
        monthNames: ['1월', '2월', '3월', '4월', '5월', '6월', '7월', '8월', '9월', '10월', '11월', '12월'],
        monthNamesShort: ['1월', '2월', '3월', '4월', '5월', '6월', '7월', '8월', '9월', '10월', '11월', '12월'],
        dayNames: ['일', '월', '화', '수', '목', '금', '토'],
        dayNamesShort: ['일', '월', '화', '수', '목', '금', '토'],
        dayNamesMin: ['일', '월', '화', '수', '목', '금', '토'],
        showMonthAfterYear: true,
        yearSuffix: '년'
    });

    // 휴가 취소
    function fn_vacationCancel(id, index, cancel_yn){

        var userId = id;
        var vacationNum = index;
        var cancelYn = cancel_yn;

        if(cancel_yn == 'Y'){
            alert("이미 취소된건 입니다.");
            return;
        }

        var reqData = {
             "userId" : userId
            ,"vacationNum" : vacationNum
        };

        if(confirm("휴가를 취소 하시겠습니까?")) {
            $.ajax({
                url: "/vacation/cancel",
                type: "POST",
                dataType: "json",
                data: JSON.stringify(reqData),
                contentType: "application/json; charset=UTF-8",
                success: function (result) {
                    alert(result.resultMsg);
                    if (result.resultCode === '1000') {
                        location.reload();
                    }
                },
                error: function (error) {
                    alert("오류가 발생했습니다. 관리자에게 문의해주세요.");
                }
            });
        }

    }

    // 코멘트 확인 창
    function fn_comment(comment){
        $('#commentContent').text(comment);
        $('#commentView').modal('show');
    }


</script>

<body>

    <input type="hidden" id="userId" value="<%=sessionId%>"></input>

    <div style="margin-left: 150px;margin-right: 150px;">
        <div class="right" style="margin-left: 20px;">
            <span class="glyphicon glyphicon-log-out" data-toggle="tooltipLogout" data-placement="left" title="로그아웃" style="font-size: 15px; cursor:pointer;" onclick="location.href='/logout.do'"></span>
        </div>
        <div class="left" style="font-weight: bold;">
            <span class="glyphicon glyphicon-home" data-toggle="tooltipLogout" data-placement="left" title="메인화면" style="font-size: 28px; cursor:pointer;" onclick="location.href='/main'"></span>
        </div>
        <div class="right"><%=sessionName%>님 접속 중</div>
    </div>

    <h2 style="text-align: center; font-weight: bold; margin-top: 50px;margin-bottom: 50px;">휴가신청 내역</h2>

    <span class="col-sm-offset-1 " style="font-size: 20px;font-weight: bold;"><%=sessionName%>님 휴가신청 내역</span>

    <table class="table table-striped col-sm-offset-1 tb1" style="width:80%">
        <colgroup>
            <col style="width: 9%";>
            <col style="width: 15%";>
            <col style="width: 15%";>
            <col style="width: 9%";>
            <col style="width: 9%";>
            <col style="width: 9%";>
            <col style="width: 15%";>
            <col style="width: 8%";>
            <col style="width: 10%";>
        </colgroup>

        <tbody>
            <tr>
                <th>no</th>
                <th>휴가시작일시</th>
                <th>휴가종료일시</th>
                <th>휴가유형</th>
                <th>휴가 사용일</th>
                <th>취소여부</th>
                <th>취소일시</th>
                <th>내용</th>
                <th></th>
            </tr>
            <c:if test="${empty vacationList || resultCount <= 0}">
                <tr>
                    <td colspan="9"><p>휴가 신청내역이 없습니다.</p></td>
                </tr>
            </c:if>

            <c:if test="${not empty vacationList && resultCount > 0}">
                <c:forEach var="result" items="${vacationList}" varStatus="status">
                    <tr>
                        <td>${status.count}</td>
                        <td>${result.VACATION_ST_DATE}</td>
                        <td>${result.VACATION_END_DATE}</td>
                        <td>${result.VACATION_TYPE}</td>
                        <td>${result.USE_DAY}</td>
                        <td>${result.CANCEL_YN}</td>
                        <td>${result.CANCEL_DT}</td>
                        <td>
                            <span onclick="fn_comment('${result.COMMENT}');" style="cursor: pointer;text-decoration: underline;">확인</span>
                        </td>
                        <c:if test="${result.CANCEL_YN eq 'N'}">
                            <td>
                                <div class="form-group">
                                    <div class="col-sm-offset-4 col-sm-10">
                                        <span class="btn btn-default" onClick="fn_vacationCancel('${result.ID}', '${result.INDEX}', '${result.CANCEL_YN}');">취소하기</span>
                                    </div>
                                </div>
                            </td>
                        </c:if>
                        <c:if test="${result.CANCEL_YN eq 'Y'}">
                            <td> </td>
                        </c:if>
                    </tr>
                </c:forEach>
            </c:if>
        </tbody>
    </table>

    <%--  코멘트 창--%>
    <div class="modal fade" id="commentView">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title">휴가신청 내용</h4>
                </div>
                <div class="modal-body">
                    <p id="commentContent"></p>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">닫기</button>
                </div>
            </div>
        </div>
    </div>

</body>
</html>
