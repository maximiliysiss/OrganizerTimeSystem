<%-- 
    Document   : journal
    Created on : Apr 20, 2018, 8:29:55 AM
    Author     : zimma
--%>

<%@page import="DbConnection.DBUser"%>
<%@page import="DbConnection.DBJournal"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<link rel="stylesheet" href="styles/modal-user-style.css" type="text/css">
<link type="text/css" rel="stylesheet" href="styles/journal-style.css">

<div id="header-journal" class="journal-name"></div>

<div class="journal" id="journal" ondblclick="dblClickJournal(event)"  onclick="ShowHideJRecord(event)">
    <div class="modal-window-user" id="create-jstep">
        <div id="modal-cnr" class="modal-content-user">
            <div class="modal-string-minpadding">
                <input id="jcreateval-step" onkeydown="AddJStepKey(event)" type="text">
            </div>
        </div>
    </div>


    <div class="modal-window-user" id="change-jtask">
        <div id="modal-cnr" class="modal-content-user">
            <div class="modal-string-minpadding">
                <input id="jchangeval" type="text">
            </div>
        </div>
    </div>

    <div class="modal-window-user" id="change-jstep">
        <div id="modal-cnr" class="modal-content-user">
            <div class="modal-string-minpadding">
                <input onkeydown="reactionChangeStep(event)" style="width: 55%; display: inline-block" id="jchangeval-step" type="text">
            </div>
        </div>
    </div>

    <script>
        /**
         * Confirm Change Step
         * @param {event} event
         * @return {void}
         */
        function reactionChangeStep(event) {
            if (event.keyCode === 13) {
                ChangeJournalStepConfirm();
            }
        }
    </script>

    <div class="modal-window-user" id="modal-jtask-work">
        <div class="modal-content-user">
            <div id="jtask-menu-add" class="modal-string" onclick="AddJStep()">
            </div>
            <div id="jtask-menu-modify" class="modal-string" onclick="ChangeJournalRecord()">
            </div>
            <div id="jtask-menu-delete" class="modal-string" onclick="DeleteJGoal()">
            </div>
        </div>
    </div>
    <div class="modal-window-user" id="modal-step-work">
        <div class="modal-content-user">
            <div id="jstep-menu-modify" class="modal-string" onclick="ChangeJournalStep()">
            </div>
            <div id="jstep-menu-delete" class="modal-string" onclick="DeleteJStep()">
            </div>
        </div>
    </div>
    <div id="content-journal" class="journal"></div>
</div>
<div style="display: none" id="resultWork"></div>
<script>

    /**
     * Show/Hide list of steps for Jrecord
     * @param {event} event
     * @return {void}
     */
    function ShowHideJRecord(event) {
        if (event.target.id === 'journal' || event.target.id.indexOf('column') > -1) {
            if (document.getElementById('jreccreate').style.display === 'none') {
                document.getElementById('jreccreate').style.display = 'block';
                document.getElementById('jcreaterec').focus();
            } else {
                document.getElementById('jcreaterec').blur();
                document.getElementById('jreccreate').style.display = 'none';
            }
        }
        document.getElementById('modal-jtask-work').style.display = 'none';
    }

    /**
     * Double Click on Journal
     * @param {event} event
     * @return {void}
     */
    function dblClickJournal(event) {
        document.getElementById('change-jstep').style.display = 'none';
        document.getElementById('change-jtask').style.display = 'none';
        document.getElementById('modal-cnr').style.display = 'none';
    }

    /**
     * On load journal
     * @param {type} isFind
     * @return {getConditions.result|String}
     */
    $('#content-journal').ready(function () {
        GetJournal(true);
    });

    /**
     * Confirmed adding step
     * @param {event} event
     * @return {void}
     */
    function AddJStepKey(event) {
        if (event.keyCode === 13) {
            AddJStepConfirm();
        }
    }

    /**
     * Menu for Journal
     * @type {Modal Window}
     */
    var JournalModule = document.getElementById('modal-journal');

    /**
     * Menu for JRecord
     * @type {Modal Window}
     */
    var JTaskModule = document.getElementById('modal-jtask-work');

    /**
     * Menu for Step
     * @type {Modal Window}
     */
    var JStepModule = document.getElementById('modal-step-work');

    /**
     * Menu for Create JRecord
     * @type {Modal Window}
     */
    //var JCreateModule = document.getElementById('create-jtask');
    /**
     * Change Jrecord
     * @type {Modal Window}
     */
    var JChangeModule = document.getElementById('change-jtask');

    /**
     * Change Step Journal
     * @type {Modal Window}
     */
    var JChangeStep = document.getElementById('change-jstep');

    /**
     * is open journal
     * @type {Boolean}
     */
    //var isOpenJournalModule = false;
    /**
     * is open menu for Jrecord
     * @type {Boolean}
     */
    //var isOpenJTaskModule = false;

    /**
     * is open menu for step journal
     * @type {Boolean}
     */
    //var isOpenJStepModule = false;

    /**
     * is open menu for create box journal
     * @type {Boolean}
     */
    //var isOpenJCreateModule = false;


    /**
     * Open/Hide JListStep
     * @param {event} event
     * @return {void}
     */
    function OpenHideJRec(event) {
        JTargetElement = event.target;
        var id = JGetID();
        if (document.getElementById('jrecidl' + id).style.display === 'none' ||
                document.getElementById('jrecidl' + id).style.display === '') {
            document.getElementById('jrecidl' + id).style.display = 'block';
        } else {
            document.getElementById('jrecidl' + id).style.display = 'none';
        }
    }

    /**
     * Get Conditions of opened/closed elements
     * @param {boolean} isFind
     * @return {getConditions.result|String}
     */
    function getConditions(isFind) {
        if (isFind)
            return 'none';
        var conditions = [];
        var tmp = [];
        var fColumntNodes = document.getElementById('column0').childNodes;
        for (var i = 1; i < fColumntNodes.length - 1; i += 2)
            if (fColumntNodes[i].id !== 'jcreateempty')
                tmp.push(fColumntNodes[i]);
        fColumntNodes = tmp;
        tmp = [];
        var sColumntNodes = document.getElementById('column1').childNodes;
        for (var i = 1; i < sColumntNodes.length - 1; i += 2)
            if (sColumntNodes[i].id !== 'jcreateempty')
                tmp.push(sColumntNodes[i]);
        sColumntNodes = tmp;
        tmp = [];
        var tColumntNodes = document.getElementById('column2').childNodes;
        for (var i = 1; i < tColumntNodes.length - 1; i += 2)
            if (tColumntNodes[i].id !== 'jcreateempty')
                tmp.push(tColumntNodes[i]);
        tColumntNodes = tmp;
        tmp = [];
        var fourColumntNodes = document.getElementById('column3').childNodes;
        for (var i = 1; i < fourColumntNodes.length - 1; i += 2)
            if (fourColumntNodes[i].id !== 'jcreateempty')
                tmp.push(fourColumntNodes[i]);
        fourColumntNodes = tmp;
        for (var i = 0; i < ((fColumntNodes.length === null?0:fColumntNodes.length)
                + (sColumntNodes.length === null?0:sColumntNodes.length)
                + (tColumntNodes.length === null?0:tColumntNodes.length)
                + (fourColumntNodes.length === null?0:fourColumntNodes.length)); i++)
            conditions.push(0);
        for (var i = 0; i < fColumntNodes.length; i++) {
            if (fColumntNodes[i].style.display === 'block')
                conditions[i * 4] = 1;
        }
        for (var i = 0; i < sColumntNodes.length; i++) {
            if (sColumntNodes[i].style.display === 'block')
                conditions[i * 4 + 1] = 1;
        }
        for (var i = 0; i < tColumntNodes.length; i++) {
            if (tColumntNodes[i].style.display === 'block')
                conditions[i * 4 + 2] = 1;
        }
        for (var i = 0; i < fourColumntNodes.length; i++) {
            if (fourColumntNodes[i].style.display === 'block')
                conditions[i * 4 + 3] = 1;
        }
        var result = '';
        for (var i = 0; i < conditions.length; i++)
            result += conditions[i];
        return result.length === 0 ? 'none' : result;
    }

    /**
     * Change JournalRecord
     * @return {void}
     */
    function ChangeJournalRecord() {
        JTaskModule.style.display = 'none';
        //isOpenJTaskModule = false;
        JChangeModule.style.display = 'block';
        document.getElementById('jchangeval').focus();
        JChangeModule.style.top = JTaskModule.style.top;
        JChangeModule.style.left = JTaskModule.style.left;
        document.getElementById('jchangeval').value = document.getElementById('jrecvalid' + JGetID()).innerHTML.substring(2);
        var target = JTargetElement;
        document.getElementById('jchangeval').onkeydown = function (event) {
            if (event.keyCode === 13)
                ChangeJournalRecordConfirm(target);
        };
    }

    /**
     * Confirmed Change JRecord
     * @param {element} target
     * @return {void}
     */
    function ChangeJournalRecordConfirm(target) {
        if (target !== JTargetElement)
            return;
        document.getElementById('jchangeval').blur();
        JChangeModule.style.display = 'none';
        var value = document.getElementById('jchangeval').value;
        document.getElementById('jchangeval').value = '';
        if (value.trim().length === 0)
            return;
        ModifyToDB(value, document.getElementById('jrectimeid' + JGetID()).innerHTML, JGetID());
    }

    /**
     * Change journal step
     * @return {void}
     */
    function ChangeJournalStep() {
        JStepModule.style.display = 'none';
        //isOpenJTaskModule = false;
        JChangeStep.style.display = 'block';
        document.getElementById('jchangeval-step').focus();
        JChangeStep.style.top = JStepModule.style.top;
        JChangeStep.style.left = JStepModule.style.left;
        document.getElementById('jchangeval-step').value =
                document.getElementById('jstepvalid' + JGetID()).innerHTML;
    }

    /**
     * Confirmed Change journal record
     * @return {void}
     */
    function ChangeJournalStepConfirm() {
        document.getElementById('jchangeval-step').blur();
        JChangeStep.style.display = 'none';
        var value = document.getElementById('jchangeval-step').value;
        if (value.trim().length === 0)
            return;
        ModifyToDB(value, document.getElementById('jsteptimeid' + JGetID()).innerHTML, JGetID());
        ModifyToDB(document.getElementById('jrecvalid' + recId).innerHTML,
                document.getElementById('jrectimeid' + recId).innerHTML,
                JGetIDVal(document.getElementById('jsteptimeid' + JGetID()).parentElement.id));
    }

    /**
     * Journal Target
     * @type {context element}
     */
    var JTargetElement = null;
    /*function OpenJournalModule(event) {
     isOpenJournalModule = true;
     JournalModule.style.display = 'block';
     JournalModule.style.left = event.pageX;
     JournalModule.style.top = event.pageY;
     }*/


    /**
     * Show menu for journal step
     * @param {event} event
     * @return {void}
     */
    function JStepWork(event) {
        // isOpenJStepModule = true;
        JStepModule.style.display = 'block';
        JStepModule.style.left = event.pageX;
        JStepModule.style.top = event.pageY;
        JTargetElement = event.target;
    }


    /**
     * Delete JRecord
     * @return {void}
     */
    function DeleteJGoal() {
        //isOpenJTaskModule = false;
        JTaskModule.style.display = 'none';
        DeleteToDB(JGetID());
    }

    /**
     * Delete journal step
     * @return {void}
     */
    function DeleteJStep() {
        var id = JGetID();
        for (var i = 0; i < timerID.length; i++)
            if (timerID[i] === id) {
                clearInterval(timers[i]);
                timerID[i] = -1;
                document.getElementById('jsteptimeid' + id).innerHTML = timerSt[i];
                break;
            }
        var par_id = JGetIDVal(JTargetElement.parentElement.parentElement.id);
        var new_time = MinusTime(document.getElementById('jrectimeid' + par_id).innerHTML,
                document.getElementById('jsteptimeid' + id).innerHTML);
        ModifyToDB(document.getElementById('jrecvalid' + par_id).innerHTML.substring(2),
                new_time, par_id);
        JStepModule.style.display = 'none';
        DeleteToDB(id);
    }

    /**
     * Show menu for journal record
     * @param {event} event
     * @return {void}
     */
    function JTaskWork(event) {
        //isOpenJTaskModule = true;
        JTaskModule.style.display = 'block';
        JTaskModule.style.left = event.pageX;
        JTaskModule.style.top = event.pageY;
        JTargetElement = event.target;
    }

    /**
     * is Number
     * @param {int} elem
     * @return {Boolean}
     */
    function isNumber(elem) {
        var Numbers = ['0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '-'];
        for (var i = 0; i < Numbers.length; i++)
            if (Numbers[i] === elem)
                return true;
        return false;
    }

    /**
     * Get ID of journal target
     * @return {string | int}
     */
    function JGetID() {
        var id = JTargetElement.id;
        var pos = id.length;
        for (var i = 0; i < pos; i++) {
            if (isNumber(id[i])) {
                pos = i;
                break;
            }
        }
        return id.substring(i);
    }

    /**
     * Get ID of element
     * @param {string} str
     * @return {string|int}
     */
    function JGetIDVal(str) {
        var id = str;
        var pos = id.length;
        for (var i = 0; i < pos; i++) {
            if (isNumber(id[i])) {
                pos = i;
                break;
            }
        }
        return id.substring(i);
    }


    /*function ShowJList() {
     var id = JGetID();
     if (id === -1)
     return;
     document.getElementById('jrecidl' + id).style.display = 'block';
     JTargetElement = null;
     }
     
     function HideJList() {
     var id = JGetID();
     if (id === -1)
     return;
     document.getElementById('jrecidl' + id).style.display = 'none';
     JTargetElement = null;
     }*/



    /**
     * Create new journal's step
     * @type {Modal Window}
     */
    var JCreateStep = document.getElementById('create-jstep');

    /**
     * Show create box for step
     * @return {void}
     */
    function AddJStep() {
        //isOpenJTaskModule = false;
        JTaskModule.style.display = 'none';
        JCreateStep.style.display = 'block';
        document.getElementById('jcreateval-step').focus();
        JCreateStep.style.top = JTaskModule.style.top;
        JCreateStep.style.left = JTaskModule.style.left;
    }

    /**
     * Format time to "00:00"
     * @param {string} time1
     * @return {String}
     */
    function FormatTime(time1) {
        var h1 = time1.substring(0, time1.indexOf(':'));
        var m1 = time1.substring(time1.indexOf(':') + 1);
        return (h1.length === 1 ? "0" : "") + h1 + ":" + (m1.length === 1 ? "0" : "") + m1;
    }

    /**
     * Sum of times
     * @param {string} time1
     * @param {string} time2
     * @return {String}
     */
    function PlusTime(time1, time2) {
        var h1 = time1.substring(0, time1.indexOf(':')),
                h2 = time2.substring(0, time2.indexOf(':'));
        var m1 = time1.substring(time1.indexOf(':') + 1),
                m2 = time2.substring(time2.indexOf(':') + 1);
        m1 = Number(h1) * 60 + Number(m1) + Number(h2) * 60 + Number(m2);
        return FormatTime(parseInt(m1 / 60) + ":" + (m1 % 60));
    }

    /**
     * Substruct times
     * @param {string} time1
     * @param {string} time2
     * @return {String}
     */
    function MinusTime(time1, time2) {
        var h1 = time1.substring(0, time1.indexOf(':')),
                h2 = time2.substring(0, time2.indexOf(':'));
        var m1 = time1.substring(time1.indexOf(':') + 1),
                m2 = time2.substring(time2.indexOf(':') + 1);
        m1 = Number(h1) * 60 + Number(m1) - (Number(h2) * 60 + Number(m2));
        return FormatTime(parseInt(m1 / 60) + ":" + (m1 % 60));
    }

    /**
     * Confirmed adding journal's step
     * @return {void}
     */
    function AddJStepConfirm() {
        document.getElementById('jcreateval-step').blur();
        JCreateStep.style.display = 'none';
        var value = document.getElementById('jcreateval-step').value;
        document.getElementById('jcreateval-step').value = '';
        if (value.trim().length === 0 || JGetID() === -1)
            return;
        var id = JGetID();
        AddToDB(value, id, '00:00');
        JTargetElement = null;
    }

    /**
     * Timers
     * @type {Array}
     */
    var timers = [];

    /**
     * IDs of elements in timers;
     * @type {Array}
     */
    var timerID = [];

    /**
     * Former time
     * @type {Array}
     */
    var timerSt = [];


    /**
     * Get ID of journal list include this element
     * @param {string} element
     * @return {string}
     */
    function getUnderID(element) {
        if (element.className !== 'journal-list')
            return getUnderID(element.parentElement);
        return element.id;
    }

    /**
     * Get information about actual timers
     * @param {boolean} isElem
     * @return {getTimerInfo.info|String}
     */
    function getTimerInfo(isElem) {
        if (isElem)
            return 'none';
        var info = '';
        for (var i = 0; i < timerID.length; i++)
            if (timerID[i] !== -1)
                info += timerID[i] + ';' + document.getElementById('jsteptimeid' + timerID[i]).innerHTML + '/';
        return info.length === 0 ? 'none' : info;
    }

    /**
     * Start/Stop of timers
     * @param {event} event
     * @return {void}
     */
    function StartTimer(event) {
        var id = JGetIDVal(event.target.id);
        var i = 0;
        for (; i < timerID.length; i++) {
            if (Number(timerID[i]) - Number(id) === 0) {
                clearInterval(timers[i]);
                ModifyToDB(document.getElementById('jstepvalid' + id).innerHTML,
                        document.getElementById('jsteptimeid' + id).innerHTML, id);
                var parID = JGetIDVal(getUnderID(event.target));
                var newTime = PlusTime(document.getElementById('jrectimeid' + parID).innerHTML,
                        document.getElementById('jsteptimeid' + id).innerHTML);
                newTime = MinusTime(newTime, timerSt[i]);
                ModifyToDB(document.getElementById('jrecvalid' + parID).innerHTML.substring(2), newTime, parID);
                timerID[i] = -1;
                return;
            }
        }
        var value = document.getElementById('jsteptimeid' + id).innerHTML;
        timerID.push(id);
        timerSt.push(value);
        var timeStart = '00:00';
        document.getElementById('jsteptimeid' + id).innerHTML = timeStart;
        var timer = (setInterval(function () {
            timeStart = PlusTime(timeStart, '00:01');
            document.getElementById('jsteptimeid' + id).innerHTML = timeStart;
        }, 1000));
        timers.push(timer);
    }

    /**
     * Close Unused
     * @return {void}
     */
    /*function JCloseUnused() {
        if (isOpenJStepModule) {
            isOpenJStepModule = false;
            JStepModule.style.display = 'none';
        }
        if (isOpenJTaskModule) {
            isOpenJTaskModule = false;
            JTaskModule.style.display = 'none';
        }
        if (isOpenJournalModule) {
            isOpenJournalModule = false;
            JournalModule.style.display = 'none';
        }
    }*/

    /**
     * Add new JRecord
     * @param {event} event
     * @return {void}
     */
    function AddJRecord(event) {
        if (event.keyCode === 13) {
            AddJTaskConfirm();
        }
    }

    /**
     * Confirmed adding JRecord
     * @return {void}
     */
    function AddJTaskConfirm() {
        document.getElementById('jcreaterec').blur();
        var value = document.getElementById('jcreaterec').value;
        if (value.trim().length === 0)
            return;
        AddToDB(value, null, "00:00");
    }

    /**
     * Adding to DB
     * @param {string} val
     * @param {int} par
     * @param {string} time_val
     * @return {void}
     */
    function AddToDB(val, par, time_val) {
        $.ajax({
            url: 'JournalWork',
            type: 'POST',
            data: {time: time_val, type_req: 'add', value: val, parent: (par === null ? "null" : par), user: "<%= request.getParameter("useremail")%>"},
            success: function (data) {
                GetJournal();
            },
            error: function () {
                alert("Error adding JournalBlock");
            }
        });
    }


    /**
     * Delete from DB
     * @param {int} identity
     * @return {void}     */
    function DeleteToDB(identity) {
        $.ajax({
            url: 'JournalWork',
            type: 'POST',
            data: {type_req: 'delete', id: identity, user: "<%= request.getParameter("useremail")%>"},
            success: function (data) {
                GetJournal();
            },
            error: function () {
                alert("Error deleting from JournalBlock");
            }
        });
    }

    /**
     * Modify Records     
     * @param {string} val_new
     * @param {string} time_new
     * @param {int} identity
     * @return {void}     */
    function ModifyToDB(val_new, time_new, identity) {
        $.ajax({
            url: 'JournalWork',
            type: 'POST',
            data: {type_req: 'modify', id: identity,
                time: time_new, value: val_new, user: "<%= request.getParameter("useremail")%>"},
            success: function (data) {
                GetJournal();
            },
            error: function () {
                alert("Error modifying in JournalBlock");
            }
        });
    }

    /**
     * Get Journal
     * @param {boolean} isGetNull
     * @return {void}     */
    function GetJournal(isGetNull = false) {
        $.ajax({
            url: 'JournalGets',
            type: 'POST',
            data: {user: "<%=new DBUser().getUserID(request.getParameter("useremail"))%>", conditions: getConditions(isGetNull), timerCond: getTimerInfo(isGetNull)},
            success: function (data) {
                document.getElementById('content-journal').innerHTML = data;
            },
            error: function (textStatus, errorThrown) {
                alert("Error getJournal");
            }
        });
    }
</script>
