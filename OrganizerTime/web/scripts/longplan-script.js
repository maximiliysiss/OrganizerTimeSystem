/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * Window for work with finished long plan
 * @type {Modal Window}
 */
var endLongPlanMenu = document.getElementById('modal-longplan-work-end');


/**
 * Confirmed Change LongPlan
 * @param {event} event
 * @return {void}
 */
function changePlanLP(event) {
    if (event.keyCode === 13)
        if (changeTypeShLP === 'long')
            ModifConfirm();
        else
            ChangeShConfirm();
}

/**
 * Modal Window for end finished long task
 * @type {Modal Window}
 */
var endLongTaskMenu = document.getElementById('modal-longtask-work-end');

/**
 * Show or Hide Create Box for Long Plans
 * @param {event} event
 * @return {void}
 */
function ShowHideCreateLP(event) {
    if (event.target.id !== 'f-column-content')
        return;
    var create = document.getElementById('create-new-plan-column');
    if (create.style.display === 'none') {
        create.style.display = 'block';
        document.getElementById("planval").focus();
    } else {
        document.getElementById('planval').blur();
        create.style.display = 'none';
    }
}

/**
 * Show Menu for LongPlan or Task
 * @param {event} event
 * @param {type} task
 * @return {void}
 */
function MenuEndsLP(event, task) {
    modalTarget = task === 'plan' ? endLongPlanMenu : endLongTaskMenu;
    //isOpenEndTodayMenu = true;
    modalTarget.style.display = 'block';
    modalTarget.style.left = event.pageX;
    modalTarget.style.top = event.pageY;
    LPTarget = event.target;
}

/**
 * is open long plan menu delete?
 * @type {Boolean}
 */
//var isOpenLongPlanDel = false;

/**
 * Open menu for first column
 * @param {event} event
 * @return {void}
 */
function longWorkAll(event) {
    if (event.target.id !== 'f-column' && event.target.id !== 'f-column-content')
        return;
    var elem = document.getElementById('modal-long-plan-del');
    elem.style.display = 'block';
    elem.style.left = event.pageX;
    elem.style.top = event.pageY;
    //isOpenLongPlanDel = true;
}

/**
 * is long plan?
 * @param {context element} element
 * @return {Boolean}
 */
function isLongPlan(element) {
    if (!element)
        return false;
    if (element.id.indexOf('idlp') !== -1)
        return true;
    return isLongPlan(element.parentElement);
}

/**
 * After load first column
 */
$('#f-column-content').ready(function () {
    GetLongPlans('null');
});

/**
 * Finish long plan
 * @param {int} ident
 * @return {void}
 */
function ExitLongPlane(ident) {
    ModifToDBLPlanL(document.getElementById(ident + JGetIDVal(LPTarget.id)).innerHTML,
            '4', JGetIDVal(LPTarget.id));
    CloseModalWindows(null);
}

/**
 * Set actialy plan
 * @param {int} ident
 * @return {void}
 */
function SetActualyPlan(ident) {
    ModifToDBLPlanL(document.getElementById(ident + JGetIDVal(LPTarget.id)).innerHTML,
            '1', JGetIDVal(LPTarget.id));
    CloseModalWindows(null);
}

/**
 * Postpone plan
 * @param {int} ident
 * @return {void}
 */
function PutOutPlan(ident) {
    ModifToDBLPlanL(document.getElementById(ident + JGetIDVal(LPTarget.id)).innerHTML,
            '5', JGetIDVal(LPTarget.id));
    CloseModalWindows(null);
}

/**
 * Catalog level
 * @type {String|id|backToEl}
 */
var currentLevel = 'null';

/**
 * To next catalog level
 * @param {event} event
 * @return {void}
 */
function NextLevel(event) {
    var id = JGetIDVal(event.target.id);
    infoState.innerHTML += '<div ondrop="drop(event)"' + (currentLevel === 'null' ? 'id="null1"' : '')
            + ' ondragover="allowDrop(event)" style="display:inline-block" onclick="BackToUpper(event)" name="' + currentLevel
            + '">' + (infoState.innerHTML.length !== 0 ? '> ' : '') +
            document.getElementById('lpvalid' + id).innerHTML + '</div>';
    currentLevel = id;
    GetLongPlans(id);
}

/**
 * Menu for Long Plan
 * @type {Modal Window}
 */
var longPlanWorkModule = document.getElementById('modal-longplan-work');
/**
 * is open long plan menu?
 * @type {Boolean}
 */
//var isOpenLongPlanWorkModule = false;

/**
 * Work with long task
 * @type {Modal Window}
 */
var longTaskWorkModule = document.getElementById('modal-longtask-work');

/**
 * is open task plan menu
 * @type {Boolean}
 */
//var isOpenLongTaskWorkModule = false;

/**
 * Change long plan
 * @type {Modal Window}
 */
var modifyLPModule = document.getElementById('change-plan-lp');

/**
 * Catalog Way
 * @type {undefined}
 */
var infoState = document.getElementById('info-st');

/**
 * Long Plan Target
 * @type {element}
 */
var LPTarget;

/**
 * Show Long plan menu
 * @param {event} event
 * @return {void}
 */
function LongPlanWork(event) {
    //CloseOther(event);
    //isOpenLongPlanWorkModule = true;
    longPlanWorkModule.style.display = 'block';
    longPlanWorkModule.style.left = event.pageX;
    longPlanWorkModule.style.top = event.pageY;
    LPTarget = event.target;
}

/**
 * Show Long task menu
 * @param {event} event
 * @return {void}
 */
function LongTaskWork(event) {
    //CloseOther(event);
    //isOpenLongTaskWorkModule = true;
    longTaskWorkModule.style.display = 'block';
    longTaskWorkModule.style.left = event.pageX;
    longTaskWorkModule.style.top = event.pageY;
    LPTarget = event.target;
}

/**
 * Delete Long plan
 * @return {void}
 */
function DeleteLP() {
    var id = JGetIDVal(LPTarget.id);
    DeleteToDBLPlan(id, 'LongPlanWork');
    GetLongPlans(currentLevel);
    CloseModalWindows(null);
}

/**
 * Modify Long Plan/Task
 * @return {void}
 */
function ModifyLPPlan() {
    modifyLPModule.style.display = 'block';
    longPlanWorkModule.style.display = 'none';
    document.getElementById('changeplanval').value
            = document.getElementById('lpvalid' + JGetIDVal(LPTarget.id)).innerHTML;
    modifyLPModule.style.top = longPlanWorkModule.style.top;
    modifyLPModule.style.left = longPlanWorkModule.style.left;
    changeTypeShLP = 'long';
    document.getElementById('changeplanval').focus();
}

/**
 * Modify Long Task
 * @return {void}
 */
function ModifyLPTask() {
    modifyLPModule.style.display = 'block';
    longTaskWorkModule.style.display = 'none';
    document.getElementById('changeplanval').value
            = document.getElementById('idlt' + JGetIDVal(LPTarget.id)).innerHTML;
    modifyLPModule.style.top = longTaskWorkModule.style.top;
    modifyLPModule.style.left = longTaskWorkModule.style.left;
    changeTypeShLP = 'long';
    document.getElementById('changeplanval').focus();
}

/**
 * Confirm Modify
 * @return {void}
 */
function ModifConfirm() {
    document.getElementById('changeplanval').blur();
    modifyLPModule.style.display = 'none';
    var value = document.getElementById('changeplanval').value;
    if (value.trim().length === 0)
        return;
    ModifToDBLPlanL(value, '0', JGetIDVal(LPTarget.id));
    CloseModalWindows(null);
}

/**
 * Add new long plan/task
 * @param {type} createtype
 * @return {void}
 */
function CreateLongPlanConfirm(createtype) {
    var value = document.getElementById('planval').value;
    if (value.trim().length === 0)
        return;
    document.getElementById('planval').value = '';
    AddToDBPlanL(value, currentLevel, createtype);
}

/**
 * Open catalog level from info-st
 * @param {event} event
 * @return {void}
 */
function BackToUpper(event) {
    var backToEl = $(event.target).attr('name');
    if (currentLevel === backToEl)
        return;
    infoState.innerHTML = infoState.innerHTML.substring(0, infoState.innerHTML.indexOf(backToEl) + backToEl.length);
    currentLevel = backToEl;
    GetLongPlans(backToEl);
}



