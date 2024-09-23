<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>challengeInfo</title>
    <!-- Kavoon 폰트 -->
    <link href="https://fonts.googleapis.com/css2?family=Kavoon&display=swap" rel="stylesheet">
    <!-- Pretendard 폰트 -->
    <link rel="stylesheet" as="style" crossorigin
        href="https://cdn.jsdelivr.net/gh/orioncactus/pretendard@v1.3.8/dist/web/static/pretendard.css" />
    <!-- 글로벌스타일.css -->
    <link rel="stylesheet" href="globalstyle.css">
    <!-- 사이드바.css -->
    <link rel="stylesheet" href="sidebar.css">
    <!-- mainContents.css -->
    <link rel="stylesheet" href="mainContents.css">
</head>

<body>
    <header style="margin: 0 auto; width: 100%; height: 175px; background-color: var(--violet200);">
    </header>
    <div class="mainContents">
        <div class="sidebar">
            <a class="sidebar-item" href="faqInfo.html">FAQ</a>
            <a class="sidebar-item" href="noticeInfo.html">공지사항</a>
            <a class="sidebar-item" href="userInfo.html">회원</a>
            <a class="sidebar-item" href="groupInfo.html">그룹</a>
            <a class="sidebar-item" href="challengeInfo.html">챌린지</a>
            <a class="sidebar-item" href="reportInfo.html">신고</a>
            <a class="sidebar-item" href="authInfo.html">인증</a>
        </div>
        <div class="infoCard">
            <div class="inputGroup">
                <h2 class="title">그룹 정보</h2>
                <span class="buttonSideContainer">
                    <button class="formButton linePurpleButton">이전</button>
                </span>
            </div>
            <div class="inputSection">
                <div class="inputGroup">
                    <label class="subtitle" for="auth-date">기간</label>
                    <input class="textContent inputEnabled" type="date" name="auth-date" id="auth-date" required>
                    ~
                    <input class="textContent inputEnabled" type="date" name="auth-date" id="auth-date" required>
                </div>
                <div class="inputGroup">
                    <label class="subtitle" for="username">그룹명</label>
                    <input class="textContent inputEnabled" type="text" name="username" id="username" required>
                </div>
                <div class="inputGroup">
                    <label class="subtitle" for="challenge">그룹장</label>
                    <input class="textContent inputEnabled" type="text" name="challenge" id="challenge">
                </div>
                <div class="inputGroup">
                    <label class="subtitle" for="content">구분</label>
                    <input class="textContent inputEnabled" type="text" name="challenge" id="challenge">
                </div>
                <div class="inputGroup">
                    <label class="subtitle" for="content">상태</label>
                    <input class="textContent inputEnabled" type="text" name="challenge" id="challenge">
                </div>
                <div class="inputGroup">
                    <label class="subtitle" for="challenge">인원</label>
                    <input class="textContent inputEnabled" type="text" name="challenge" id="challenge">
                </div>
                <div class="inputGroup">
                    <label class="subtitle" for="challenge">태그</label>
                    <input class="textContent inputEnabled" type="text" name="challenge" id="challenge">
                </div>
                <div class="inputGroup">
                    <label class="subtitle" for="challenge">챌린지</label>
                    <input class="textContent inputEnabled" type="text" name="challenge" id="challenge">
                </div>
                <div class="inputGroup">
                    <label class="subtitle" for="challenge">참가 챌린지</label>
                    <input class="textContent inputEnabled" type="text" name="challenge" id="challenge">
                </div>
            </div>
        </div>
    </div>
    <footer style="margin: 0 auto; width: 100%; height: 175px; background-color: var(--violet200);">
    </footer>
</body>

</html>