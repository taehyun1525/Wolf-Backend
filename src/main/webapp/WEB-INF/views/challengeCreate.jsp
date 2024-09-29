<!-- <%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%> -->
<!DOCTYPE html>
<html lang="ko">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>WOLF 관리자 페이지</title>
	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/normalize/8.0.1/normalize.min.css">
    <link rel="stylesheet" href="/resources/css/globalstyle.css">
    <link rel="stylesheet" href="/resources/css/mainContents.css">
</head>

<body>
    <%@ include file="components/header.jsp" %>
        <div class="mainContents">
            <%@ include file="components/sidebar.jsp" %>
                <div class="infoCard">
                    <h2 class="title">챌린지 작성</h2>
                    <form method="get" action="/challenge" onsubmit="alert('작성완료');" class="inputSection scrollArea">
						<%-- 인증 주체 입력 필드 --%>
						<jsp:include page="components/inputRadio/verificationAgent.jsp" />
						
						<%-- 챌린지 기간 입력 필드 --%>
						<jsp:include page="components/inputDate/startEndDate.jsp" >
							<jsp:param name="startDate" value="" />
							<jsp:param name="endDate" value="" />
						</jsp:include>
						
						<%-- 챌린지 이름 입력 필드 --%>
						<jsp:include page="components/inputText/challengeName.jsp" >
							<jsp:param name="challengeName" value="" />
						</jsp:include>
						
						<%-- 챌린지 내용 필드 --%>
						<jsp:include page="components/textarea/content.jsp" >
							<jsp:param name="content" value="" />
						</jsp:include>
						
						<%-- 유의사항 입력 필드 --%>
						<jsp:include page="components/textarea/challengeWarning.jsp" >
							<jsp:param name="challengeWarning" value="" />
						</jsp:include>
						
						<%-- 보상 입력 필드 --%>
						<jsp:include page="components/textarea/challengeAwardContent.jsp" >
							<jsp:param name="challengeAwardContent" value="" />
						</jsp:include>
						
						<%-- 첨부파일 입력 필드 --%>
						<jsp:include page="components/inputFile/inputFile.jsp" >
							<jsp:param name="inputFile" value="" />
						</jsp:include>
						
						<%-- 취소 & 완료(submit) 버튼 --%>
						<jsp:include page="components/button/cancelCompleteButton.jsp" />
                    </form>
                </div>
        </div>
        <%@ include file="components/footer.jsp" %>
</body>

</html>