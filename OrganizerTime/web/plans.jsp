<%-- 
    Document   : plans
    Created on : Apr 20, 2018, 9:57:07 AM
    Author     : zimma
--%>

<%@page import="PageElement.TodayPlanRecord"%>
<%@page import="DbConnection.DBUser"%>
<%@page import="DbConnection.DBLongPlan"%>
<%@page import="DbConnection.DBShortPlan"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<script src="http://code.jquery.com/jquery-1.8.3.js"></script>
<link rel="stylesheet" href="styles/modal-user-style.css" type="text/css">
<link rel="stylesheet" href="styles/plans-user-style.css" type="text/css">
<meta name="google-signin-client_id" 
      content="732349035499-bgn20kn5k8or0h0uu468v5srrtavppm5.apps.googleusercontent.com">

<div class="plans-name-user" id="plan-text-header"></div>

<div id="plans-module" class="plans-module"> <!--ondblclick="CloseOther(event)" onclick="ClickOnWnd(event)"-->

    <div class="modal-window-user" id="modal-longplan-work">
        <div class="modal-content-user">
            <div id="longplan-menu-delete" class="modal-string" onclick="DeleteLP()">
            </div>
            <div id="longplan-menu-finish" class="modal-string" onclick="ExitLongPlane('lpvalid')">
            </div>
            <div id="longplan-menu-postpone" class="modal-string" onclick="PutOutPlan('lpvalid')">
            </div>
            <div id="longplan-menu-modify" class="modal-string" onclick="ModifyLPPlan()">
            </div>
        </div>
    </div>

    <div class="modal-window-user" id="modal-longplan-work-end">
        <div class="modal-content-user">
            <div id="longplan-menu-end-actual" class="modal-string" onclick="SetActualyPlan('lpvalid'); modalTarget.style.display = 'none';">
            </div>
        </div>
    </div>

    <div class="modal-window-user" id="modal-longtask-work-end">
        <div class="modal-content-user">
            <div id="longtask-menu-end-actual" class="modal-string" onclick="SetActualyPlan('idlt'); modalTarget.style.display = 'none';">
            </div>
        </div>
    </div>

    <div class="modal-window-user" id="modal-longtask-work">
        <div class="modal-content-user">
            <div id="longtask-menu-delete" class="modal-string" onclick="DeleteLP()">
            </div>
            <div id="longtask-menu-finish" class="modal-string" onclick="ExitLongPlane('idlt')">
            </div>
            <div id="longtask-menu-postpone" class="modal-string" onclick="PutOutPlan('idlt')">
            </div>
            <div id="longtask-menu-modify" class="modal-string" onclick="ModifyLPTask()">
            </div>
        </div>
    </div>

    <div class="modal-window-user" id="modal-today-work">
        <div class="modal-content-user">
            <div id="todayplan-menu-delete" class="modal-string" onclick="deleteToday('test')">
            </div>
            <div id="todayplan-menu-delete-all" class="modal-string" onclick="deleteToday('all')">
            </div>
            <div id="todayplan-menu-finish" class="modal-string" onclick="CloseTodayPlan(JGetIDVal(todayTarget.id))">
            </div>
        </div>
    </div>


    <div class="modal-window-user" id="modal-today-end-work">
        <div class="modal-content-user">
            <div id="todayplan-menu-end-actual" class="modal-string" onclick="SetActualTodayPlan(JGetIDVal(todayTarget.id)); endTodayMenu.style.display = 'none';">
            </div>
        </div>
    </div>

    <script>
        function deleteToday(element) {
            if (element === 'all')
                DeleteToDBLPlan('all', 'TodayPlanRecordWork');
            else
                DeleteToDBLPlan(JGetIDVal(todayTarget.id), 'TodayPlanRecordWork');
            CloseModalWindows(null);
        }
    </script>

    <div class="modal-window-user" id="modal-short-task">
        <div class="modal-content-user">
            <div id="shortplan-main-menu-delete-finish" class="modal-string" onclick="DeleteFColumn()">
            </div>
            <div id="shortplan-main-menu-delete-all" class="modal-string" onclick="deleteAllShorts()">
            </div>
        </div>
    </div>

    <div class="modal-window-user" id="modal-long-plan-del">
        <div class="modal-content-user">
            <div id="main-menu-delete-all" class="modal-string" onclick="deleteAllLongPlans(currentLevel)">
            </div>
        </div>
    </div>

    <div class="modal-window-user" id="modal-short-task-work">
        <div class="modal-content-user">
            <div id="shortplan-menu-delete" class="modal-string" onclick="DeleteSColumn()">
            </div>
            <div id="shortplan-menu-finish" class="modal-string" onclick="ExitShortPlane()">
            </div>
            <div id="shortplan-menu-postpone" class="modal-string" onclick="PutOutShortPlane()">
            </div>
            <div id="shortplan-menu-modify" class="modal-string" onclick="ChangeShPlan()">
            </div>
        </div>
    </div>

    <div class="modal-window-user" id="modal-short-task-work-end">
        <div class="modal-content-user">
            <div id="shortplan-menu-end-actual" class="modal-string" onclick="SetActualyShortPlane(); endShortPlanMenu.style.display = 'none'">
            </div>
        </div>
    </div>

    <!--<div class="modal-window-user" id="modal-today-move">
        <div id="modal-today-move-content" class="modal-content-user">
        </div>
    </div>-->


    <div class="modal-window-user" id="modal-long-position">
        <div class="modal-content-user">
            <div id="longplan-position-move" class="modal-string" onclick="MoveTo()">
            </div>
            <div id="longplan-position-insert-up" class="modal-string" onclick="InsertUpper()">
            </div>
        </div>
    </div>

    <div class="modal-window-user" id="eventsys">
        <div id="modal-cnr" class="modal-content-user">
            <div class="modal-string-minpadding">
                <input onkeydown="setDateRunEnter(event)" id="eventsys-val" type="date">
            </div>
        </div>
    </div>

    <script>
        function setDateRunEnter(event) {
            if (event)
                if (event.keyCode === 13) {
                    eventSysClick();
                }
        }
    </script>

    <div class="modal-window-user" id="timerun">
        <div id="modal-cnr" class="modal-content-user">
            <div class="modal-string-minpadding">
                <input onkeydown="setTimeRunEnter(event)" type="time" step="1800" id="timerun-val">
            </div>
        </div>
    </div>

    <script>
        function setTimeRunEnter(event) {
            if (event)
                if (event.keyCode === 13) {
                    timeRun();
                }
        }
    </script>


    <div class="modal-window-user" id="change-plan-lp">
        <div id="modal-cnr" class="modal-content-user">
            <div class="modal-string-minpadding">
                <input onkeydown="changePlanLP(event)" id="changeplanval" type="text">
            </div>
        </div>
    </div>

    <div class="modal-window-user" id="change-today">
        <div id="modal-cnr" class="modal-content-user">
            <div class="modal-string-minpadding">
                <input onkeydown="changeTodayPlanReact(event)" id="changetodayval" type="text">
            </div>
        </div>
    </div>

    <script>
        function changeTodayPlanReact(event) {
            if (event.keyCode === 13)
                ModifTodayConfirm();
        }
    </script>

    <div class="plans-user">
        <div style="border-left: none" class="column-user" id="f-column" oncontextmenu="longWorkAll(event);return false">
            <div class="column-header-user" id="header-long-plan"></div>
            <div id="info-st" class="info-state"></div><br>
            <div onclick="ShowHideCreateLP(event)" ondrop="drop(event)" ondragover="allowDrop(event)" style="height: 82%" class="plans-content" id="f-column-content">
            </div>
        </div>
        <div class="column-user" oncontextmenu="OpenContextMenuShort(event); return false;" id="s-column">
            <div class="column-header-user" id="header-short-plan"></div>
            <div onclick="ShowHideCreate(event)" ondrop="drop(event)" ondragover="allowDrop(event)" class="plans-content" id="s-column-content">
            </div>
        </div>
        <div class="column-user" id="t-column" ondblclick="OpenContextTodayPlan()">
            <div id="header-today-plan" class="column-header-user" style="float: left; width: 60%"></div>
            <input type="button" onclick="showHideStartTime()" id="generate-btn" class="button-generate">
            <center><input style="display: none" onkeydown="generateStart(event)" type="time" id="setstarttime"></center>
            <div ondrop="drop(event)" ondragover="allowDrop(event)" style="clear: left" class="plans-content" id="t-column-content">
            </div>
        </div>
    </div>
</div>
<div id="actuaclindexGT" style="display: none"></div>
<div id="actuaclindexSP" style="display: none"></div>
<div id="contentgoogle" style="display: none"></div>
<div style="display: none" id="responselp"></div>
<div id='timerunContext' style='display: none'></div>

<script type="text/javascript">

    var modalTarget = null;

    //var movementModalToday = document.getElementById('modal-today-move-content');

    function showHideStartTime() {
        var btn = document.getElementById('setstarttime');
        if (btn.style.display === 'none') {
            btn.style.display = 'block';
        } else
            btn.style.display = 'none';
    }

    function generateStart(event) {
        if (event.keyCode === 13) {
            SetStartTime(event.target.value);
            GetTodayPlans('generateTodayPlans');
            listUpcomingEvents(true);
            document.getElementById('setstarttime').style.display = 'none';
        }
    }

    /*function isModal(target) {
     if (!target)
     return false;
     if (target.className === 'modal-window-user')
     return true;
     return isModal(target.parentElement);
     }*/

    /*function CloseOther(event) {
     if (!isModal(event.target)) {
     document.getElementById('modal-longtask-work-end').style.display = 'none';
     document.getElementById('modal-longplan-work-end').style.display = 'none';
     document.getElementById('modal-short-task-work-end').style.display = 'none';
     document.getElementById('modal-today-end-work').style.display = 'none';
     document.getElementById('change-plan-lp').style.display = 'none';
     document.getElementById('timerun').style.display = 'none';
     document.getElementById('modal-longplan-work').style.display = 'none';
     document.getElementById('modal-longtask-work').style.display = 'none';
     document.getElementById('eventsys').style.display = 'none';
     todayPlanChangerModule.style.display = 'none';
     document.getElementById('change-today').style.display = 'none';
     document.getElementById('modal-long-plan-del');
     endTodayMenu.style.display = 'none';
     if (modalTarget !== null)
     modalTarget.style.display = 'none';
     }
     }*/

    function listUpcomingEvents(isGet) {
        console.log('get calendar from api');
        var today = new Date();
        today.setHours(0);
        today.setMinutes(0);
        var nextday = new Date(today);
        nextday.setDate(today.getDate() + 1);
        gapi.client.calendar.events.list({
            'calendarId': 'primary',
            'timeMin': today.toISOString(),
            'timeMax': nextday.toISOString(),
            'showDeleted': false,
            'singleEvents': true,
            'maxResults': 10,
            'orderBy': 'startTime'
        }).then(function (response) {
            var events = response.result.items;
            if (events.length > 0) {
                for (i = 0; i < events.length; i++) {
                    var event = events[i];
                    var when = event.start.dateTime;
                    var end = event.end.dateTime;
                    if (!when) {
                        when = event.start.date;
                    }
                    console.log(event.summary + '(' + when + ')(' + end + ')/');
                    document.getElementById('contentgoogle').innerHTML += event.summary + '(' + when + ')(' + end + ')/';
                }
            }
            if (isGet)
                GetTodayPlans("getTodayPlans");
        });
    }

    var CLIENT_ID = '732349035499-bgn20kn5k8or0h0uu468v5srrtavppm5.apps.googleusercontent.com';
    var API_KEY = 'AIzaSyAQsBoneCnVVInuburWbwO4--Fq8HscwP8';

    // Array of API discovery doc URLs for APIs used by the quickstart
    var DISCOVERY_DOCS = ["https://www.googleapis.com/discovery/v1/apis/calendar/v3/rest"];

    // Authorization scopes required by the API; multiple scopes can be
    // included, separated by spaces.
    var SCOPES = "https://www.googleapis.com/auth/calendar.readonly";

    /**
     *  On load, called to load the auth2 library and API client library.
     */
    function handleClientLoad() {
        gapi.load('client:auth2', initClient);
    }

    /**
     *  Initializes the API client library and sets up sign-in state
     *  listeners.
     */
    function initClient() {
        console.log('init user');
        gapi.client.init({
            apiKey: API_KEY,
            clientId: CLIENT_ID,
            discoveryDocs: DISCOVERY_DOCS,
            scope: SCOPES
        }).then(function () {
            // Listen for sign-in state changes.
            gapi.auth2.getAuthInstance().isSignedIn.listen(updateSigninStatus);

            // Handle the initial sign-in state.
            updateSigninStatus(gapi.auth2.getAuthInstance().isSignedIn.get());
        });
    }

    /**
     *  Called when the signed in status changes, to update the UI
     *  appropriately. After a sign-in, the API is called.
     */
    function updateSigninStatus(isSignedIn) {
        if (isSignedIn) {
            listUpcomingEvents(true);
        } else {
            window.location.href = "http://OrganizerTime/index.jsp";
        }
    }

    /**
     *  Sign in the user upon button click.
     */
    function handleAuthClick(event) {
        gapi.auth2.getAuthInstance().signIn();
    }

    /**
     *  Sign out the user upon button click.
     */
    function handleSignoutClick(event) {
        gapi.auth2.getAuthInstance().signOut();
    }

    var indexShortPlan = 0;

    function allowDrop(ev) {
        ev.preventDefault();
    }

    var targetDrag = null;
    var dragElement = null;
    function getParenColumt(elem) {
        if (elem.id === 'f-column-content' || elem.id === 's-column-content' || elem.id === 'info-st'
                || elem.id === 't-column-content')
            return elem.id;
        return getParenColumt(elem.parentElement);
    }

    function drag(ev) {
        ev.dataTransfer.setData("text", ev.target.id);
        if (targetDrag === null) {
            dragElement = ev.target;
            targetDrag = getParenColumt(ev.target);
        }
    }

    //var isOpenLongPosition = false;
    var ttempID = null;

    function InsertUpper() {
        //isOpenLongPosition = false;
        SetNewSequency(JGetIDVal(dragElement.id), ttempID, 'LongPlanWork');
        document.getElementById('modal-long-position').style.display = 'none';
    }
    function MoveTo() {
        //isOpenLongPosition = false;
        MoveElement(ttempID);
        document.getElementById('modal-long-position').style.display = 'none';
    }


    function getTimeFromTColumn(element) {
        if (element.className === "record") {
            var childs = element.childNodes;
            return childs[0].innerHTML;
        }
        return getTimeFromTColumn(element.parentElement);
    }

    function ModifTodayConfirm(event) {
        if (event)
            if (event.keyCode === 13) {
                var id = JGetIDVal(event.target.id);
                var value = document.getElementById('changetplan' + id).value;
                if (value.trim().length === 0) {
                    listUpcomingEvents(true);
                    return;
                }
                if (id > '0') {
                    if (value.trim().length === 0)
                        value = 'None';
                    ModifToday(value, id);
                } else {
                    var time = getTimeFromTColumn(event.target);
                    ModifTodayByTime(value, time);
                }
            }
    }


    function drop(ev) {
        switch (getParenColumt(ev.target)) {
            case 'f-column-content':
                switch (targetDrag) {
                    case 'f-column-content':
                        if (ev.target.id === 'f-column-content') {
                            SetLastSequency(JGetIDVal(dragElement.id), 'LongPlanWork');
                            break;
                        }
                        ttempID = JGetIDVal(ev.target.id);
                        if (isLongPlan(ev.target)) {
                            document.getElementById('modal-long-position').style.display = 'block';
                            document.getElementById('modal-long-position').style.left = ev.pageX;
                            document.getElementById('modal-long-position').style.top = ev.pageY;
                            //isOpenLongPosition = true;
                        } else
                            InsertUpper();
                        break;
                    case 's-column-content':
                        TransferToFirst(JGetIDVal(dragElement.id));
                        break;
                }
                break;
            case 's-column-content':
                switch (targetDrag) {
                    case 'f-column-content':
                        if (dragElement.id.indexOf('idlp') !== -1)
                            break;
                        TrasnformToSec(JGetIDVal(dragElement.id));
                        break;
                    case 's-column-content':
                        if (ev.target.id === 's-column-content') {
                            var id = JGetIDVal(dragElement.id);
                            SetLastSequency(id, 'ShortPlanWork');
                            break;
                        }
                        var ident = JGetIDVal(ev.target.id);
                        SetNewSequency(JGetIDVal(dragElement.id), ident, 'ShortPlanWork');
                        break;
                }
                break;
            case 'info-st':
                switch (targetDrag) {
                    case 'f-column-content':
                        MoveElement(ev.target.getAttribute('name'));
                        break;
                }
                break;
            case 't-column-content':
                switch (targetDrag) {
                    case 'f-column-content':
                        if (dragElement.id.indexOf('idlp') !== -1)
                            break;
                        var time = getTimeFromTColumn(ev.target);
                        TrasnformToToday('LongPlanWork', JGetIDVal(dragElement.id), time);
                        break;
                    case 's-column-content':
                        var time = getTimeFromTColumn(ev.target);
                        TrasnformToToday('ShortPlanWork', JGetIDVal(dragElement.id), time);
                        break;
                    case 't-column-content':
                        var time = getTimeFromTColumn(ev.target);
                        TrasnformToToday('TodayPlanRecordWork', JGetIDVal(dragElement.id), time);
                        break;
                }
                break;
        }
        targetDrag = null;
    }

    /*function ChoiceMoveToday(idies, event, time) {
     var ids = idies.split(',');
     movementModalToday.innerHTML = '';
     for (var i = 0; i < ids.length; i++) {
     movementModalToday.innerHTML += '<div id="' + time + '" class="modal-string" name="' + ids[i] + '" onclick="chMoveToday(event)">' +
     document.getElementById('tval' + idies).innerHTML.split(',')[i] + '</div>';
     movementModalToday.name = idies;
     }
     var move = document.getElementById('modal-today-move');
     move.style.display = 'block';
     move.style.left = event.pageX;
     move.style.top = event.pageY;
     }
     
     function chMoveToday(event) {
     var id = event.target.getAttribute("name");
     var time = event.target.id;
     TrasnformToToday('TodayPlanRecordWork', id, time);
     var move = document.getElementById('modal-today-move');
     move.style.display = 'none';
     }*/


    function MoveElement(from) {
        ChangeParent(JGetIDVal(dragElement.id), from);
    }

    $('#s-column-content').ready(function () {
        GetShortPlans();
    });

    var responseHandlerLP = document.getElementById('responselp');
    var indexLongGT = 0;

    /* function ClickOnWnd() {
     if (isOpenLongPlanWorkModule) {
     isOpenLongPlanWorkModule = false;
     longPlanWorkModule.style.display = 'none';
     }
     if (isOpenLongTaskWorkModule) {
     isOpenLongTaskWorkModule = false;
     longTaskWorkModule.style.display = 'none';
     }
     if (isOpenShortPlanModule) {
     isOpenShortPlanModule = false;
     shortPlanModule.style.display = 'none';
     }
     if (isOpenShortPlanWorkModule) {
     isOpenShortPlanWorkModule = false;
     shortPlanWorkModule.style.display = 'none';
     }
     if (isOpenTodayPlanModule) {
     isOpenTodayPlanModule = false;
     todayPlanModule.style.display = 'none';
     }
     if (isOpenLongPosition) {
     isOpenLongPosition = false;
     document.getElementById('modal-long-position').style.display = 'none';
     InsertUpper();
     }
     if (isOpenLongPlanDel) {
     isOpenLongPlanDel = false;
     document.getElementById('modal-long-plan-del').style.display = 'none';
     }
     endShortPlanMenu.style.display = 'none';
     }*/

    function AddToDBPlanL(val, par, t) {
        $.ajax({
            url: 'LongPlanWork',
            type: 'POST',
            data: {type: "add", user: "<%= request.getParameter("useremail")%>", value: val, parent: par, transform: t},
            error: function (textStatus, errorThrown) {
                alert('Error adding to long');
            },
            success: function (data) {
                GetLongPlans(currentLevel);
            }
        });
    }

    function AddToDBPlanS(val, prior) {
        $.ajax({
            url: 'ShortPlanWork',
            type: 'POST',
            data: {type: "add", user: "<%= request.getParameter("useremail")%>", value: val, priority: prior},
            error: function (textStatus, errorThrown) {
                alert('Error adding to short');
            },
            success: function (data) {
                GetShortPlans();
            }
        });
    }

    function DeleteToDBLPlan(idEl, servlet) {
        $.ajax({
            url: servlet,
            type: 'POST',
            data: {type: "delete", user: "<%= request.getParameter("useremail")%>", id: idEl},
            success: function (data) {
                GetLongPlans(currentLevel);
                GetShortPlans();
                listUpcomingEvents(true);
            },
            error: function (textStatus, errorThrown) {
                alert('Error deleting');
            }
        });
    }

    function DeleteFColumn() {
        CloseModalWindows(null);
        $.ajax({
            url: 'ShortPlanWork',
            type: 'POST',
            data: {type: "deleteAllEnd", user: "<%= request.getParameter("useremail")%>"},
            success: function (data) {
                GetShortPlans();
            },
            error: function () {
                alert('Error deleteing from fCloumn');
            }
        });
    }

    function TransferToFirst(idEl) {
        $.ajax({
            url: 'ShortPlanWork',
            type: 'POST',
            data: {type: "transformToFirst", parent: currentLevel, user: "<%= request.getParameter("useremail")%>", id: idEl},
            success: function (data) {
                GetShortPlans();
                GetLongPlans(currentLevel);
            },
            error: function () {
                alert('Error transfer to first');
            }
        });
    }

    function CloseTodayPlan(idEl) {
        CloseModalWindows(null);
        $.ajax({
            url: 'TodayPlanRecordWork',
            type: 'POST',
            data: {type: "closePlan", user: "<%= request.getParameter("useremail")%>", id: idEl},
            success: function (data) {
                listUpcomingEvents(true);
            },
            error: function () {
                alert('Error transfer to first');
            }
        });
    }

    function SetActualTodayPlan(idEl) {
        $.ajax({
            url: 'TodayPlanRecordWork',
            type: 'POST',
            data: {type: "actualPlan", user: "<%= request.getParameter("useremail")%>", id: idEl},
            success: function (data) {
                listUpcomingEvents(true);
            },
            error: function () {
                alert('Error transfer to first');
            }
        });
    }

    function ModifToDBLPlanS(val, trans, idEl, prior) {
        $.ajax({
            url: 'ShortPlanWork',
            type: 'POST',
            data: {type: "modify", value: val, transform: trans, user: "<%= request.getParameter("useremail")%>", id: idEl,
                priority: prior},
            success: function (data) {
                GetShortPlans();
            },
            error: function () {
                alert('Error modifying shortplans');
            }
        });
    }

    function ModifToDBLPlanL(val, trans, idEl) {
        $.ajax({
            url: 'LongPlanWork',
            type: 'POST',
            data: {type: "modify", value: val, transform: trans, user: "<%= request.getParameter("useremail")%>", id: idEl},
            success: function (data) {
                GetLongPlans(currentLevel);
            },
            error: function () {
                alert('Error modifying long plans');
            }
        });
    }

    function TrasnformToSec(idEl) {
        $.ajax({
            url: 'LongPlanWork',
            type: 'POST',
            data: {type: "transformToSecond", user: "<%= request.getParameter("useremail")%>", id: idEl},
            success: function (data) {
                GetLongPlans(currentLevel);
                GetShortPlans();
            },
            error: function () {
                alert('Error transform to second');
            }
        });
    }

    function TrasnformToToday(column, idEl, time) {
        $.ajax({
            url: column,
            type: 'POST',
            data: {type: "transformToToday", timeStart: time, user: "<%= request.getParameter("useremail")%>", id: idEl},
            success: function (data) {
                listUpcomingEvents(true);
            },
            error: function () {
                alert('Error transform to second');
            }
        });
    }

    function GetLongPlans(idEl) {
        $.ajax({
            url: 'FileSystem',
            type: 'POST',
            data: {id: idEl, user: "<%= new DBUser().getUserID(request.getParameter("useremail"))%>"},
            success: function (data) {
                document.getElementById('f-column-content').innerHTML = data;
            },
            error: function () {
                alert('Error get longplans');
            }
        });
    }

    function GetShortPlans() {
        $.ajax({
            url: 'ShortsPlans',
            type: 'POST',
            data: {user: "<%= new DBUser().getUserID(request.getParameter("useremail"))%>"},
            success: function (data) {
                document.getElementById('s-column-content').innerHTML = data;
            },
            error: function () {
                alert('Error get shortsplans');
            }
        });
    }

    function deleteAllShorts() {
        CloseModalWindows(null);
        $.ajax({
            url: 'ShortPlanWork',
            type: 'POST',
            data: {type: "deleteAll", user: "<%= new DBUser().getUserID(request.getParameter("useremail"))%>"},
            success: function (data) {
                console.log("Success");
                GetShortPlans();
            },
            error: function () {
                alert('Error delete all shortsplans');
            }
        });
    }

    function deleteAllLongPlans(idEl) {
        CloseModalWindows(null);
        $.ajax({
            url: 'LongPlanWork',
            type: 'POST',
            data: {type: "deleteAll", id: idEl, user: "<%= new DBUser().getUserID(request.getParameter("useremail"))%>"},
            success: function (data) {
                GetLongPlans(currentLevel);
            },
            error: function () {
                alert('Error delete all shortsplans');
            }
        });
    }

    function GetTodayPlans(types) {
        var calendar = document.getElementById('contentgoogle').innerHTML;
        document.getElementById('contentgoogle').innerHTML = '';
        $.ajax({
            url: 'TodayPlanWork',
            type: 'POST',
            data: {type: types, googlecalapi: calendar.length > 0 ? calendar : "none", user: "<%= request.getParameter("useremail")%>"},
            success: function (data) {
                document.getElementById('t-column-content').innerHTML = data;
            },
            error: function (textStatus, errorThrown) {
                alert('Error generate todayplans');
            }
        });
    }

    function SetTimeRun(ident, timer) {
        $.ajax({
            url: 'ShortPlanWork',
            type: 'POST',
            data: {type: "timeRun", time: timer, id: ident, user: "<%= request.getParameter("useremail")%>"},
            success: function (data) {
                GetShortPlans();
            },
            error: function (textStatus, errorThrown) {
                alert('Error set timerun');
            }
        });
    }

    function SetDateRun(ident, dater) {
        $.ajax({
            url: 'ShortPlanWork',
            type: 'POST',
            data: {type: "dateRun", date: dater, id: ident, user: "<%= request.getParameter("useremail")%>"},
            success: function (data) {
                GetShortPlans();
            },
            error: function (textStatus, errorThrown) {
                alert('Error set daterun');
            }
        });
    }

    function ChangeParent(ident, par) {
        $.ajax({
            url: 'LongPlanWork',
            type: 'POST',
            data: {type: "changeParent", parent: par, id: ident, user: "<%= request.getParameter("useremail")%>"},
            success: function (data) {
                GetLongPlans(currentLevel);
            },
            error: function (textStatus, errorThrown) {
                alert('Error change parent plan');
            }
        });
    }

    function ModifToday(val, idEl) {
        $.ajax({
            url: 'TodayPlanRecordWork',
            type: 'POST',
            data: {type: "modify", value: val, id: idEl, user: "<%= request.getParameter("useremail")%>"},
            success: function (data) {
                listUpcomingEvents(true);
            },
            error: function (textStatus, errorThrown) {
                alert('Error modifying from plans');
            }
        });
    }

    function ModifTodayByTime(val, timeEl) {
        $.ajax({
            url: 'TodayPlanRecordWork',
            type: 'POST',
            data: {type: "modifyByTime", value: val, time: timeEl, user: "<%= request.getParameter("useremail")%>"},
            success: function (data) {
                listUpcomingEvents(true);
            },
            error: function (textStatus, errorThrown) {
                alert('Error modifying from plans');
            }
        });
    }

    function SetStartTime(val) {
        $.ajax({
            url: 'UserHandler',
            type: 'POST',
            data: {type: "setStartTime", time: val, id: "<%= request.getParameter("useremail")%>"},
            success: function (data) {
            },
            error: function (textStatus, errorThrown) {
                alert('Error modifying from plans ' + textStatus + ' ' + errorThrown);
            }
        });
    }

    function SetNewSequency(identity, afterChange, servlet) {
        $.ajax({
            url: servlet,
            type: 'POST',
            data: {type: "newSequency", id: identity, after: afterChange, user: "<%= request.getParameter("useremail")%>"},
            success: function (data) {
                if (servlet === 'LongPlanWork')
                    GetLongPlans(currentLevel);
                else
                    GetShortPlans();
            },
            error: function (textStatus, errorThrown) {
                alert('Error modifying from plans ' + textStatus + ' ' + errorThrown);
            }
        });
    }

    function SetLastSequency(identity, servlet) {
        $.ajax({
            url: servlet,
            type: 'POST',
            data: {type: "lastSequency", id: identity, user: "<%= request.getParameter("useremail")%>"},
            success: function (data) {
                if (servlet === 'LongPlanWork')
                    GetLongPlans(currentLevel);
                else
                    GetShortPlans();
            },
            error: function (textStatus, errorThrown) {
                alert('Error modifying from plans ' + textStatus + ' ' + errorThrown);
            }
        });
    }

</script>
<script type="text/javascript" src="scripts/todayplans-script.js"></script>
<script type="text/javascript" src="scripts/longplan-script.js"></script>
<script type="text/javascript" src="scripts/shortplan-script.js"></script>
<script async defer src="https://apis.google.com/js/api.js"
        onreadystatechange="if (this.readyState === 'complete') this.onload()">
</script>