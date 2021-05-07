

var notification_click = document.getElementById("notification-click")
console.log(notification_click);
notification_click.addEventListener('click',function(){
    var modal_notification = document.getElementById("modal-notification")
    // console.log(notification_click)
    modal_notification.classList.toggle('open-modal-notification');
});

var avatar_click = document.getElementById("user-avatar")
console.log(avatar_click);
avatar_click.addEventListener('click',function(){
    var modal_notification = document.getElementById("modal-user-info")
    // console.log(notification_click)
    modal_notification.classList.toggle('open-modal-user-info');
});

var list_item_team_click = document.getElementById("menu-extend");
console.log(list_item_team_click);
list_item_team_click.addEventListener('click',function(){
    var list_item_extend = document.querySelector("#list-iteam-team > ul:nth-child(2)");
    // console.log(list_item_team_click)
    // list_item_extend.style.backgroundColor = "red";
    list_item_extend.classList.toggle('ul-extend');
});

var gmailOfUser = document.getElementById("gmail").textContent;

async function getTitle(){

    var path = "http://localhost:8080/board/title/";
    var apiURL = path.concat(gmailOfUser);
    const resp = await fetch(apiURL);
    const data = await resp.json();
    var count = Object.keys(data).length;
    var i;
    
    if(count > 0){
        for(i = 0; i < count; i++){    
            const li1 = document.createElement("li");
            li1.setAttribute("class", "each-board");
            const a1 = document.createElement("a");
            a1.setAttribute("class", "title-each-board");
            a1.setAttribute("href", "#");
            a1.appendChild(document.createTextNode(data[i]));
            li1.appendChild(a1);

            document.getElementById("owned-board").appendChild(li1);
        }
    }

    const lastItem = document.createElement("li");
    lastItem.setAttribute("class", "add-board");
    const a1 = document.createElement("span");
    a1.appendChild(document.createTextNode("Tạo bảng mới"));
    lastItem.appendChild(a1);

    document.getElementById("owned-board").appendChild(lastItem);
    
    console.log(data)
}
getTitle();
console.log(gmailOfUser)