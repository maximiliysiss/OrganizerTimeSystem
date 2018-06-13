/**
 * Finish Short plan
 * @return {void}
 */
function ExitShortPlane() {
    ModifToDBLPlanS(document.getElementById('idvalsp' + JGetIDVal(shTarget.id)).innerHTML,
            '4', JGetIDVal(shTarget.id), document.getElementById('typeshid' + JGetIDVal(shTarget.id)).value);
    CloseModalWindows(null);
}

/**
 * Postpone short plan
 * @return {void}
 */
function PutOutShortPlane() {
    ModifToDBLPlanS(document.getElementById('idvalsp' + JGetIDVal(shTarget.id)).innerHTML,
            '5', JGetIDVal(shTarget.id), document.getElementById('typeshid' + JGetIDVal(shTarget.id)).value);
    CloseModalWindows(null);
}

/**
 * Set actualy short plan
 * @return {void}
 */
function SetActualyShortPlane() {
    ModifToDBLPlanS(document.getElementById('idvalsp' + JGetIDVal(shTarget.id)).innerHTML,
            '2', JGetIDVal(shTarget.id), document.getElementById('typeshid' + JGetIDVal(shTarget.id)).value);
    CloseModalWindows(null);
}

/**
 * Show Hide Create Box for Short plans
 * @param {event} event
 * @return {void}
 */
function ShowHideCreate(event) {
    if (event.target.id !== 's-column-content')
        return;
    var create = document.getElementById('create-new-sh');
    if (create.style.display === 'none') {
        create.style.display = 'block';
        document.getElementById('shplanval').focus();
    } else {
        document.getElementById('shplanval').blur();
        create.style.display = 'none';
    }
}

/**
 * Set duration for short plan
 * @return {void}
 */
function timeRun() {
    document.getElementById('timerun').style.display = 'none';
    var time = document.getElementById('timerun-val').value;
    document.getElementById('timerun-val').value = '';
    document.getElementById('clockimg' + targetTimeRunId).src = 'img/AddingStyleElement/Clock_Icon - Black.png';
    SetTimeRun(targetTimeRunId, time);
}

/**
 * Confirmed Adding Short plan
 * @param {event} event
 * @return {void}
 */
function AddShPlan(event) {
    if (event.keyCode === 13) {
        createShConfirm();
    }
}

/**
 * Menu for finished short plan
 * @type {Modal Window}
 */
var endShortPlanMenu = document.getElementById('modal-short-task-work-end');

/**
 * Show menu for finished short plan
 * @param {event} event
 * @return {void}
 */
function MenuEndsShP(event) {
    shTarget = event.target;
    endShortPlanMenu.style.display = 'block';
    endShortPlanMenu.style.left = event.pageX;
    endShortPlanMenu.style.top = event.pageY;
}

/**
 * Short Plan target for Change duration or date
 * @type {id}
 */
var targetTimeRunId;

/**
 * Change date short plan
 * @return {void}
 */
function eventSysClick() {
    document.getElementById('eventsys').style.display = 'none';
    var date = document.getElementById('eventsys-val').value;
    document.getElementById('eventsys-val').value = '';
    document.getElementById('ringimg' + targetTimeRunId).src = 'img/AddingStyleElement/Ring_Icon - Black.png';
    SetDateRun(targetTimeRunId, date);
}

/**
 * Set/Unset duration for short plan
 * @param {event} event
 * @return {void}
 */
function TimeRunSet(event) {
    var id = JGetIDVal(event.target.id);
    var img = document.getElementById('clockimg' + id);
    if (img.src.indexOf('Black') !== -1) {
        SetTimeRun(id, '0');
        return;
    }
    targetTimeRunId = id;
    document.getElementById('timerun').style.display = 'block';
    document.getElementById('timerun').style.left = event.pageX;
    document.getElementById('timerun').style.top = event.pageY;
}

/**
 * Set/Unset date for Short Plan
 * @param {event} event
 * @return {void}
 */
function EventSys(event) {
    var id = JGetIDVal(event.target.id);
    var img = document.getElementById('ringimg' + id);
    if (img.src.indexOf('Black') !== -1) {
        SetDateRun(id, '0');
        return;
    }
    targetTimeRunId = id;
    document.getElementById('eventsys').style.display = 'block';
    document.getElementById('eventsys').style.left = event.pageX;
    document.getElementById('eventsys').style.top = event.pageY;
}

/**
 * Menu for Short plan
 * @type {Modal Window}
 */
var shortPlanWorkModule = document.getElementById('modal-short-task-work');
/**
 * is open menu for short plan
 * @type {Boolean}
 */
//var isOpenShortPlanWorkModule = false;

/**
 * Short plan target
 * @type {context element}
 */
var shTarget = null;

/**
 * Show menu for short plan
 * @param {event} event
 * @return {void}
 */
function ShPlanWorks(event) {
    //CloseOther(event);
    shortPlanWorkModule.style.display = 'block';
    //isOpenShortPlanWorkModule = true;
    shortPlanWorkModule.style.left = event.pageX;
    shortPlanWorkModule.style.top = event.pageY;
    shTarget = event.target;
}

/**
 * Change priority of short plan
 * @param {event} event
 * @return {void}
 */
function ChangeTypeSh(event) {
    var id = JGetIDVal(event.target.id);
    ModifToDBLPlanS(document.getElementById('idvalsp' + id).innerHTML, '2', id,
            document.getElementById('typeshid' + id).value);
}

/**
 * Delete short plan
 * @return {void}
 */
function DeleteSColumn() {
    var id = JGetIDVal(shTarget.id);
    DeleteToDBLPlan(id, 'ShortPlanWork');
    CloseModalWindows(null);
}

/**
 * Type of Changing
 * @type {String}
 */
var changeTypeShLP = null;

/**
 * Change Short plan
 * @return {void}
 */
function ChangeShPlan() {
    //CloseOther(event);
    modifyLPModule.style.display = 'block';
    document.getElementById('changeplanval').focus();
    shortPlanWorkModule.style.display = 'none';
    //isOpenShortPlanWorkModule = false;
    var id = JGetIDVal(shTarget.id);
    modifyLPModule.style.top = shortPlanWorkModule.style.top;
    modifyLPModule.style.left = shortPlanWorkModule.style.left;
    changeTypeShLP = 'short';
    document.getElementById('changeplanval').value = document.getElementById('idvalsp' + id).innerHTML;
}

/**
 * Change Plan Confirm
 * @return {void}
 */
function ChangeShConfirm() {
    document.getElementById('changeplanval').blur();
    modifyLPModule.style.display = 'none';
    var value = document.getElementById('changeplanval').value;
    document.getElementById('changeplanval').value = '';
    if (value.trim().length === 0)
        return;
    var id = JGetIDVal(shTarget.id);
    ModifToDBLPlanS(value, '2', id, document.getElementById('typeshid' + id).value);
    CloseModalWindows(null);
}

/**
 * Confirm adding short plan
 * @return {void}
 */
function createShConfirm() {
    var value = document.getElementById('shplanval').value;
    document.getElementById('shplanval').value = '';
    if (value.trim().length === 0)
        return;
    AddToDBPlanS(value, 'E');
}


var shortPlanModule = document.getElementById('modal-short-task');
//var isOpenShortPlanModule = false;
function OpenContextMenuShort(event) {
    if (endShortPlanMenu.style.display === 'block')
        return;
    //CloseOther(event);
    shortPlanModule.style.display = 'block';
    //isOpenShortPlanModule = true;
    shortPlanModule.style.left = event.pageX;
    shortPlanModule.style.top = event.pageY;
}