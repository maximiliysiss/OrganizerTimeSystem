/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * Menu for today plan
 * @type {Modal Window}
 */
var todayPlanModule = document.getElementById('modal-today-work');

/**
 * is Open menu for today plan
 * @type {Boolean}
 */
//var isOpenTodayPlanModule = false;

/**
 * Today plan target
 * @type {context element}
 */
var todayTarget;

/**
 * Change today plan
 * @type {Modal Window}
 */
var todayPlanChangerModule = document.getElementById('change-today');

/**
 * Show menu for today plan
 * @param {event} event
 * @return {void}
 */
function workWithToday(event) {
    //CloseOther(event);
    todayPlanModule.style.display = 'block';
    todayPlanModule.style.left = event.pageX;
    todayPlanModule.style.top = event.pageY;
    //isOpenTodayPlanModule = true;
    todayTarget = event.target;
}

/**
 * Show modal window for changing today plan
 * @param {event} event
 * @return {void}
 */
function changeTodayPlan(event) {
    var id = JGetIDVal(event.target.id);
    if (document.getElementById('changetplan' + id))
        return;
    var value = document.getElementById('today' + id).innerHTML;
    document.getElementById('today' + id).innerHTML = '<input size="' + (value.length) + '" onkeyup="onkeydown()" onkeypress="onkeydown()" onchange="onkeydown()" style="display:inline; border: 1px solid #696969; border-radius:5px" onkeydown="ModifTodayConfirm(event); size=value.length||1" type="text" id="changetplan' + id +
            '" value="' + value + '">';
    document.getElementById('changetplan' + id).focus();
}

/**
 * Menu for finished today plan
 * @type {Modal window}
 */
var endTodayMenu = document.getElementById('modal-today-end-work');

/**
 * is Open menu for finished today plan
 * @type {Boolean}
 */
//var isOpenEndTodayMenu = false;

/**
 * Show menu for finished today plan
 * @param {event} event
 * @return {void}
 */
function MenuEndsToday(event) {
    //isOpenEndTodayMenu = true;
    endTodayMenu.style.display = 'block';
    endTodayMenu.style.left = event.pageX;
    endTodayMenu.style.top = event.pageY;
    todayTarget = event.target;
}