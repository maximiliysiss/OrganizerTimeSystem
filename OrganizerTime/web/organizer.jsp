<%-- 
    Document   : organizer
    Created on : Apr 20, 2018, 8:29:13 AM
    Author     : zimma
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<html>

    <head>
        <script src="https://code.jquery.com/jquery-1.10.2.js"></script>
        <link rel="stylesheet" type="text/css" href="styles/organizerpage-user-style.css">
        <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.0.10/css/all.css" integrity="sha384-+d0P83n9kaQMCwj8F4RJB66tzIwOKmrdb46+porD/OvrJ+37WqIM7UoBtwHO6Nlg" crossorigin="anonymous">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
        <title>Organizer</title>
        <script>
            <%
                String ID, EMAIL, IMGURL, FULLNAME;
                request.setCharacterEncoding("UTF-8");
                ID = request.getParameter("userid");
                EMAIL = request.getParameter("useremail");
                IMGURL = request.getParameter("userimageurl");
                FULLNAME = request.getParameter("username");
                if (ID == null || EMAIL == null || IMGURL == null) {
                    out.println("alert('Error');");
                    response.sendRedirect("index.jsp");
                    return;
                }
                DBUser dBUser = new DBUser();
                dBUser.tryAddUser(EMAIL, ID, IMGURL);
            %>
        </script>
    </head>

    <body class="body-user" ondblclick="CloseModalWindowsDBL(event)" onclick="CloseModalWindows(event)">
        <div class="header">
            <div style="cursor: pointer" onclick="document.getElementById('defaultOpen').click()" class="header-icon">
                <img src="img/AddingStyleElement/icon.png" style="height: 100%; margin-left: -5%;"></img>
            </div>
            <div class="header-text">
                <div class="h-header">DayPlan</div>
                <p class="p-header" id="header-text-str">
                </p>
            </div>
            <div class="header-evelope">
                <i style="cursor: pointer" onclick="var link = 'mailto:zim.ma2010@yandex.ru?subject=Error Working';
                        window.open(link);" class="fa-2x fas fa-envelope"></i>
            </div>
            <div class="header-username">
                <div style="background-color: #dbdbdb; margin-right: 5%;"><% out.println(FULLNAME); %><br>
                    <% out.println(EMAIL);%></div>
            </div>
            <div class="header-user">
                <img style="cursor: pointer" onclick="SignOut()" id="user-icon" class="user-img"></img>
            </div>
        </div>
        <div class="tab-user">
            <button class="tablinks-user" onclick="openTabs(event, 'Plans')" id="defaultOpen">
                <i class="img-tabs fas fa-check-square fa-3x" id="Plans_Icon" style="width: 100%; text-align: center;"></i>
            </button>
            <button class="tablinks-user" onclick="openTabs(event, 'Journal')">
                <i class="img-tabs fas fa-3x fa-list-alt" id="Journal_Icon" style="margin-left:-10% ;width: 100%; text-align: center;"></i>
            </button>
            <button class="tablinks-user" onclick="openTabs(event, 'Calendar')">
                <i class="img-tabs fas fa-3x fa-calendar-alt" id="Calendar_Icon" style="width: 100%; text-align: center;"></i>
            </button>
            <button class="tablinks-user" onclick="openTabs(event, 'Question')">
                <i class="img-tabs fas fa-3x fa-question" id="Question_Icon" style="width: 100%; text-align: center;"></i>
            </button>
        </div>

        <div id="Plans" class="tabcontent-user">
            <%@include file="plans.jsp" %>
        </div>

        <div id="Journal" class="tabcontent-user">
            <%@include file="journal.jsp" %>
        </div>
        <div id="Calendar" class="tabcontent-user" onload="GetCalendareURL()">
            <iframe id="gcalendframe" width="100%" height="100%" frameborder="0"></iframe>
        </div>
        <div id="Question" class="tabcontent-user">
            <%@include file="questions.html" %>
        </div>
        <div class="language-box">
            <img onclick="ChangeLanguage(event)" id="en" class="language" src="img/Languages/en.png">
            <img onclick="ChangeLanguage(event)" id="ru" class="language" src="img/Languages/ru.png">
        </div>
    </body>

    <form method="POST" id="form-reg" action="organizer.jsp" accept-charset="UTF-8">
        <input type="hidden" id="useremail" name="useremail" value="<%=EMAIL%>">
        <input type="hidden" id="userid" name="userid" value="<%=ID%>">
        <input type="hidden" id="userimageurl" name="userimageurl" value="<%=IMGURL%>">
        <input type="hidden" id="username" name="username" value="<%=FULLNAME%>">
        <input type="hidden" id="lang" name="lang" value="<%=request.getParameter("lang") == null ? "ru" : request.getParameter("lang")%>">
    </form>

</html>

<script>

    /**
     * On load body
     * @type {type}
     */
    $('#body').ready(function () {
        LoadPageFirst();
        LoadLangSettings();
    });

    /**
     * Change GUI Language
     * @param {event} event
     * @return {void}
     */
    function ChangeLanguage(event) {
        if (event.target.id === '<%=request.getParameter("lang") == null ? "ru" : request.getParameter("lang")%>')
            return;
        switch (event.target.id) {
            case "ru":
                document.getElementById('lang').value = 'ru';
                break;
            case "en":
                document.getElementById('lang').value = 'en';
                break;
        }
        document.getElementById('form-reg').submit();
    }

    /**
     * Load GUI Language
     * @return {void}
     */
    function LoadLangSettings() {
        var language = '<%=request.getParameter("lang") == null ? "ru" : request.getParameter("lang")%>';
        document.getElementById(language).src = 'img/Languages/' + language + '-active.png';
        switch (language) {
            case "ru":
                $.getJSON("sitelang/ru.json", function (data) {
                    SetTextLang(data);
                });
                break;
            case "en":
                $.getJSON("sitelang/en.json", function (data) {
                    SetTextLang(data);
                });
                break;
        }
    }

    /**
     * SetTextCommon
     * @type {Arguments}
     */
    function SetTextLang(data) {
        data = data["organizer"];
        document.getElementById('header-text-str').innerHTML = data["header-text-str"];
        document.getElementById('plan-text-header').innerHTML = data["plan"];
        document.getElementById('header-long-plan').innerHTML = data["longplan"]["title"];
        document.getElementById('header-short-plan').innerHTML = data["shortplan"]["title"];
        document.getElementById('header-today-plan').innerHTML = data["todayplan"]["title"];
        document.getElementById('generate-btn').value = data["todayplan"]["button"];
        document.getElementById('header-journal').innerHTML = data["journal"]["title"];
        SetTextModalLongPlan(data["longplan"]["modal-windows"]);
        SetTextModalShortPlan(data["shortplan"]["modal-windows"]);
        SetTextModalTodayPlan(data["todayplan"]["modal-windows"]);
        SetTextModalJournal(data["journal"]["modal-windows"]);
    }

    /**
     * SetText for LongPlan
     * @param {JSON} data
     * @return {undefined}
     */
    function SetTextModalLongPlan(data) {
        document.getElementById('longplan-menu-delete').innerHTML = data["longplan-menu"]["delete"];
        document.getElementById('longplan-menu-finish').innerHTML = data["longplan-menu"]["finish"];
        document.getElementById('longplan-menu-postpone').innerHTML = data["longplan-menu"]["postpone"];
        document.getElementById('longplan-menu-modify').innerHTML = data["longplan-menu"]["modify"];
        document.getElementById('longplan-menu-end-actual').innerHTML = data["longplan-menu-end"]["actual"];
        document.getElementById('longtask-menu-end-actual').innerHTML = data["longtask-menu-end"]["actual"];
        document.getElementById('longtask-menu-delete').innerHTML = data["longtask-menu"]["delete"];
        document.getElementById('longtask-menu-finish').innerHTML = data["longtask-menu"]["finish"];
        document.getElementById('longtask-menu-postpone').innerHTML = data["longtask-menu"]["postpone"];
        document.getElementById('longtask-menu-modify').innerHTML = data["longtask-menu"]["modify"];
        document.getElementById('main-menu-delete-all').innerHTML = data["main-menu"]["delete-all"];
        document.getElementById('longplan-position-move').innerHTML = data["longplan-move-menu"]["move"];
        document.getElementById('longplan-position-insert-up').innerHTML = data["longplan-move-menu"]["insert-up"];
    }

    /**
     * SetText for ShortPlan
     * @param {JSON} data
     * @return {undefined}
     */
    function SetTextModalShortPlan(data) {
        document.getElementById('shortplan-main-menu-delete-finish').innerHTML = data["main-menu"]["delete-finished"];
        document.getElementById('shortplan-main-menu-delete-all').innerHTML = data["main-menu"]["delete-all"];
        document.getElementById('shortplan-menu-delete').innerHTML = data["shortplan-menu"]["delete"];
        document.getElementById('shortplan-menu-finish').innerHTML = data["shortplan-menu"]["finish"];
        document.getElementById('shortplan-menu-postpone').innerHTML = data["shortplan-menu"]["postpone"];
        document.getElementById('shortplan-menu-modify').innerHTML = data["shortplan-menu"]["modify"];
        document.getElementById('shortplan-menu-end-actual').innerHTML = data["shortplan-menu-end"]["actual"];
    }

    /**
     * SetTex for TodayPlan
     * @param {JSON} data
     * @return {undefined}
     */
    function SetTextModalTodayPlan(data) {
        document.getElementById('todayplan-menu-delete').innerHTML = data["todayplan-menu"]["delete"];
        document.getElementById('todayplan-menu-delete-all').innerHTML = data["todayplan-menu"]["delete-all"];
        document.getElementById('todayplan-menu-finish').innerHTML = data["todayplan-menu"]["postpone"];
        document.getElementById('todayplan-menu-end-actual').innerHTML = data["todayplan-menu-end"]["actual"];
    }

    /**
     * SetText for Journal
     * @param {JSON} data
     * @return {undefined}
     */
    function SetTextModalJournal(data) {
        document.getElementById('jtask-menu-add').innerHTML = data["jtask-menu"]["add"];
        document.getElementById('jtask-menu-modify').innerHTML = data["jtask-menu"]["modify"];
        document.getElementById('jtask-menu-delete').innerHTML = data["jtask-menu"]["delete"];
        document.getElementById('jstep-menu-modify').innerHTML = data["jstep-menu"]["modify"];
        document.getElementById('jstep-menu-delete').innerHTML = data["jstep-menu"]["delete"];
    }


    /**
     * Sign Out
     * @return {void}
     */
    function SignOut() {
        window.location.href = 'http://localhost:52603/OrganizerTime?lang=<%=request.getParameter("lang") == null ? "ru" : request.getParameter("lang")%>';
    }

    /**
     * On load page
     * @return {void}
     */
    function LoadPageFirst()
    {
    <%
        out.println("document.getElementById('user-icon').src='" + IMGURL + "'");
        out.println("console.log('" + ID + " '+'" + IMGURL + "');");
    %>
        document.getElementById('gcalendframe').src = 'https://calendar.google.com/calendar/embed?src=<%=EMAIL.split("@")[0]%>%40gmail.com&ctz=Europe%2FMoscow';
    }

    /**
     * Open Tabs
     * @param {event} evt
     * @param {pagetab} pagepart
     * @return {void}
     */
    function openTabs(evt, pagepart) {
        var i, tabcontent, tablinks, imgtabs;
        tabcontent = document.getElementsByClassName("tabcontent-user");
        for (i = 0; i < tabcontent.length; i++) {
            tabcontent[i].style.display = "none";
        }
        tablinks = document.getElementsByClassName("tablinks-user");
        for (i = 0; i < tablinks.length; i++) {
            tablinks[i].className = tablinks[i].className.replace(" active", "");
        }
        imgtabs = document.getElementsByClassName('img-tabs');
        for (i = 0; i < imgtabs.length; i++) {
            imgtabs[i].style.color = 'white';
        }
        document.getElementById(pagepart).style.display = "block";
        document.getElementById(pagepart + '_Icon').style.color = 'black';
        evt.currentTarget.className += " active";
    }

    /**
     * On Load Choice
     * @type {type}
     */
    document.getElementById("defaultOpen").click();

    /**
     * is Modal Window
     * @param {element} element
     * @return {Boolean}
     */
    function isModalWindow(element) {
        if (!element)
            return false;
        if (element.className === 'modal-string' || element.className === 'modal-string-minpadding')
            return true;
        return isModalWindow(element.parentElement);
    }

    /**
     * Close Windows by double click
     * @param {event} event
     * @return {void}
     */
    function CloseModalWindowsDBL(event) {
        if (!isModalWindow(event.target)) {
            document.getElementById('eventsys').style.display = 'none';
            document.getElementById('timerun').style.display = 'none';
        }
    }

    /**
     * Close Windows by click
     * @param {event} event
     * @return {void}
     */
    function CloseModalWindows(event) {
        if (!event || !isModalWindow(event.target)) {
            document.getElementById('create-jstep').style.display = 'none';
            document.getElementById('change-jtask').style.display = 'none';
            document.getElementById('change-jstep').style.display = 'none';
            document.getElementById('modal-jtask-work').style.display = 'none';
            document.getElementById('modal-step-work').style.display = 'none';
            document.getElementById('modal-longplan-work').style.display = 'none';
            document.getElementById('modal-longplan-work-end').style.display = 'none';
            document.getElementById('modal-longtask-work-end').style.display = 'none';
            document.getElementById('modal-longtask-work').style.display = 'none';
            document.getElementById('modal-today-work').style.display = 'none';
            document.getElementById('modal-today-end-work').style.display = 'none';
            document.getElementById('modal-short-task').style.display = 'none';
            document.getElementById('modal-long-plan-del').style.display = 'none';
            document.getElementById('modal-short-task-work').style.display = 'none';
            document.getElementById('modal-short-task-work-end').style.display = 'none';
            document.getElementById('modal-long-position').style.display = 'none';
            document.getElementById('change-plan-lp').style.display = 'none';
            document.getElementById('change-today').style.display = 'none';
        }
    }
</script>

<script async defer src="https://apis.google.com/js/api.js"
        onload="this.onload = function () {};
        handleClientLoad()"
        onreadystatechange="if (this.readyState === 'complete') this.onload()">
</script>