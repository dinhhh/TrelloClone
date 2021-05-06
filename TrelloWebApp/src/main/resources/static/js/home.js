var notification_click = document.getElementById("notification-click")
console.log(notification_click)
notification_click.addEventListener('click',function(){
    var modal_notification = document.getElementById("modal-notification")
    // console.log(notification_click)
    modal_notification.classList.toggle('open-modal-notification');
})

var avatar_click = document.getElementById("user-avatar")
console.log(avatar_click)
avatar_click.addEventListener('click',function(){
    var modal_notification = document.getElementById("modal-user-info")
    // console.log(notification_click)
    modal_notification.classList.toggle('open-modal-user-info');
})

var list_item_team_click = document.getElementById("menu-extend");
console.log(list_item_team_click)
list_item_team_click.addEventListener('click',function(){
    var list_item_extend = document.querySelector("#list-iteam-team > ul:nth-child(2)");
    // console.log(list_item_team_click)
    // list_item_extend.style.backgroundColor = "red";
    list_item_extend.classList.toggle('ul-extend');
})