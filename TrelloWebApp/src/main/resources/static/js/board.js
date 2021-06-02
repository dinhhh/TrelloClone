const addBtns = document.querySelectorAll('.add-btn:not(.solid)');
const saveItemBtns = document.querySelectorAll('.solid');
const addItemContainers = document.querySelectorAll('.add-container');
const addItems = document.querySelectorAll('.add-item');
// Item Lists
const listColumns = document.querySelectorAll('.drag-item-list');
const backlogListEl = document.getElementById('backlog-list');
const progressListEl = document.getElementById('progress-list');
const completeListEl = document.getElementById('complete-list');
const onHoldListEl = document.getElementById('on-hold-list');

// Items
let updatedOnLoad = false;

// board infor
const currentURL = window.location.href;
const boardID = currentURL.substring(currentURL.lastIndexOf("/") + 1);
let member = [];
let memberID = [];
let memberEmail = [];
let boardTitle;
let userID;
const userGmail = document.getElementById("gmail").textContent;
let currentCardIDFormDisplay;

// Initialize Arrays
let backlogListArray = [];
let progressListArray = [];
let completeListArray = [];
let onHoldListArray = [];
let listArrays = [];

// ID of card in database
let backlogIdArray = [];
let progressIdArray = [];
let completeIdArray = [];
let ohHoldIdArray = [];
let idListArray = [backlogIdArray, progressIdArray, completeIdArray, ohHoldIdArray];

// Drag Functionality
let draggedItem;
let dragging = false;
let currentColumn;
let indexItem;
let sourceColumn;

// ignore 
let currentTitle;

function getCardTitleByID(id){
  for(var j = 0; j < idListArray.length; j++){
    for(var i = 0; i < idListArray[j].length; i++){
      if(idListArray[j][i] == id){
        return listArrays[j][i];
      }
    }
  }
  return "!!!";
}

// get id of member of this board
async function getMemberID() {
  await fetch("http://localhost:8080/api/board/member/".concat(boardID.toString()), {
    method: "GET"
  })
    .then(response => response.json())
    .then(data => {
      const num = Object.keys(data).length;
      var flag = 0;
      for (var i = 0; i < num; i++) {
        member.push(data[i].firstName.concat(" ").concat(data[i].lastName));
        memberID.push(data[i].id);
        memberEmail.push(data[i].email);
        // check permission of current user to this board
        if (data[i].email == userGmail) {
          flag = 1;
        }
      }
      if (flag != 1) {
        window.alert("Bạn không có quyền truy cập vào trang này !");
        hideAllCard();
      }
    })
}

getMemberID();

// hide all card in this board
function hideAllCard() {
  document.getElementById("backlog-content").style.display = "none";
  document.getElementById("progress-content").style.display = "none";
  document.getElementById("complete-content").style.display = "none";
  document.getElementById("on-hold-content").style.display = "none";
}

function getIndexInIDListArray(category) {
  switch (category) {
    case "backlog":
      return 0;
      break;
    case "progress":
      return 1;
      break;
    case "complete":
      return 2;
      break;
    case "onHold":
      return 3;
      break;
  }
}

function sleep(ms) {
  return new Promise(resolve => setTimeout(resolve, ms));
}

function removeElement(id) {
  var elem = document.getElementById(id);
  return elem.parentNode.removeChild(elem);
}

// Get Arrays from localStorage if available, set default values if not
async function getSavedColumns() {

  // fetch data from database
  const apiURL = "http://localhost:8080/api/card/board/".concat(boardID.toString());
  const resp = await fetch(apiURL);
  const data = await resp.json();
  const numOfCards = Object.keys(data).length;
  // console.log(data);
  if (numOfCards > 0) {
    var i;
    for (i = 0; i < numOfCards; i++) {
      switch (data[i].category) {
        case "backlog":
          backlogListArray.push(data[i].title);
          backlogIdArray.push(data[i].id);
          break;
        case "progress":
          progressListArray.push(data[i].title);
          progressIdArray.push(data[i].id);
          break;
        case "complete":
          completeListArray.push(data[i].title);
          completeIdArray.push(data[i].id);
          break;
        case "onHold":
          onHoldListArray.push(data[i].title);
          ohHoldIdArray.push(data[i].id);
          break;
      }
    }
  }
  updateDOM();
}

// Set localStorage Arrays
function updateSavedColumns() {
  listArrays = [backlogListArray, progressListArray, completeListArray, onHoldListArray];
  const arrayNames = ['backlog', 'progress', 'complete', 'onHold'];
  arrayNames.forEach((arrayName, index) => {
    localStorage.setItem(`${arrayName}Items`, JSON.stringify(listArrays[index]));
  });
}

// Filter Array to remove empty values
function filterArray(array) {
  const filteredArray = array.filter(item => item !== null);
  return filteredArray;
}

// Create DOM Elements for each list item
function createItemEl(columnEl, column, item, index) {
  // List Item
  const listEl = document.createElement('li');
  listEl.textContent = item;

  listEl.id = index;
  listEl.classList.add('drag-item');
  listEl.draggable = true;
  listEl.setAttribute('ondragstart', 'drag(event)');
  listEl.setAttribute('onfocusout', `updateItem(${index}, ${column})`);
  listEl.setAttribute('ondblclick', `openEditForm(${index}, ${column})`);
  listEl.setAttribute('onclick', `setContentEditable(${index}, ${column})`)
  listEl.contentEditable = false;

  // Append
  columnEl.appendChild(listEl);
}

function setContentEditable(index, column){
  const selectedArray = listArrays[column];
  const selectedColumn = listColumns[column].children;
  selectedColumn[index].contentEditable = true;
  currentTitle = selectedColumn[index].textContent;
}

// Update Columns in DOM - Reset HTML, Filter Array, Update localStorage
async function updateDOM() {
  console.log("update DOM");
  // Check localStorage once
  if (!updatedOnLoad) {
    var boardApiPath = "http://localhost:8080/api/board/".concat(boardID);
    const resp = await fetch(boardApiPath);
    const boardData = await resp.json();
    boardTitle = boardData.title;
    document.getElementById("board-title").innerHTML = boardTitle;

    // get user id
    var userIDApi = "http://localhost:8080/user/".concat(userGmail);
    const resp2 = await fetch(userIDApi);
    const userData = await resp2.json();
    userID = userData[0].id;
    getSavedColumns();
  }

  // Backlog Column
  backlogListEl.textContent = '';
  backlogListArray.forEach((backlogItem, index) => {
    createItemEl(backlogListEl, 0, backlogItem, index);
  });
  backlogListArray = filterArray(backlogListArray);
  // Progress Column
  progressListEl.textContent = '';
  progressListArray.forEach((progressItem, index) => {
    createItemEl(progressListEl, 1, progressItem, index);
  });
  progressListArray = filterArray(progressListArray);
  // Complete Column
  completeListEl.textContent = '';
  completeListArray.forEach((completeItem, index) => {
    createItemEl(completeListEl, 2, completeItem, index);
  });
  completeListArray = filterArray(completeListArray);
  // On Hold Column
  onHoldListEl.textContent = '';
  onHoldListArray.forEach((onHoldItem, index) => {
    createItemEl(onHoldListEl, 3, onHoldItem, index);
  });
  onHoldListArray = filterArray(onHoldListArray);
  // Don't run more than once, Update Local Storage
  updatedOnLoad = true;
  updateSavedColumns();
}

// Update Item - Delete if necessary, or update Array value
async function updateItem(id, column) {
  const selectedArray = listArrays[column];
  const selectedColumn = listColumns[column].children;
  const idArray = idListArray[column];
  const idOfCard = idArray[id];
  if(selectedColumn[id].textContent != currentTitle){
    if (!dragging) {
      // delete item
      if (!selectedColumn[id].textContent) {
        delete selectedArray[id]; // !!!
        updateDOM();

        // delete card in database
        fetch("http://localhost:8080/api/card/".concat(idOfCard.toString()), {
          headers: { "content-type": "application/json; charset=UTF-8" },
          method: "DELETE"
        })
          .then(response => response.json())
          .then(data => {
            // web socket
            const cardMessage = {
              "sourceUserGmail": userGmail,
              "targetUserGmail": userGmail,
              "method": "deleteCard",
              "cardID": idOfCard,
              "boardID": Number.parseInt(boardID),
              "cardCategory": data.cardCategory,
              "cardTitle": data.cardTitle,
            };
            stompClient.send("/app/board/update", {}, JSON.stringify(cardMessage));
          })
          .catch(error => console.log("error " + error));
      } else {
        // modify item title
        selectedArray[id] = selectedColumn[id].textContent;
        const newTitle = selectedColumn[id].textContent;
        var modifyApiUrl = "http://localhost:8080/api/card/title/".concat(idOfCard.toString()).concat("/").concat(newTitle);
        const otherParams = {
          headers: { "content-type": "application/json; charset=UTF-8" },
          method: "PUT",
        };
        fetch(modifyApiUrl, otherParams)
          .then(response => response.json())
          .then(data => {
            // web socket
            const cardMessage = {
              "sourceUserGmail": userGmail,
              "targetUserGmail": userGmail,
              "method": "changeCardTitle",
              "cardID": idOfCard,
              "boardID": Number.parseInt(boardID),
              "cardCategory": data.category,
              "cardTitle": data.title,
            };
            stompClient.send("/app/board/update", {}, JSON.stringify(cardMessage));
          })

        // add to activity
        await sleep(2000);
        const activityApiUrl = "http://localhost:8080/api/activity/title/".concat(boardID.toString()).concat("/")
          .concat(idOfCard.toString()).concat("/").concat(userID.toString());
        await fetch(activityApiUrl, {
          headers: { "content-type": "application/json; charset=UTF-8" },
          method: "PUT",
        })
          .then(function (response) {
            if (response.ok) {
              console.log("add new update title activity ");
            } else {
              throw new Error("Could not reach the API" + response.statusText);
            }
          });
      }
    }
  }
}

// Add to Column List, Reset Textbox
async function addToColumn(column) {
  const arrayNames = ['backlog', 'progress', 'complete', 'onHold'];
  const itemText = addItems[column].textContent;
  const selectedArray = listArrays[column];
  selectedArray.push(itemText);
  addItems[column].textContent = '';

  // get board information
  var boardApiPath = "http://localhost:8080/api/board/".concat(boardID);
  const resp = await fetch(boardApiPath);
  const boardData = await resp.json();
  boardTitle = boardData.title;
  const numMember = Object.keys(boardData)
  document.getElementById("board-title").innerHTML = boardTitle;
  updateDOM(column);
  // send new card data to database
  var cardApiPath = "http://localhost:8080/api/card/add";
  const otherDataOfCard = {
    "title": itemText,
    "dueDate": "",
    "description": "",
    "color": "blue",
    "category": arrayNames[column],
    "board": {
      "id": boardData.id,
      "title": boardData.title,
      "backGroundImage": boardData.backGroundImage,
      "visiable": boardData.visiable,
      "user": [
        {
          "id": boardData.users[0].id,
          "email": boardData.users[0].email,
          "firstName": boardData.users[0].firstName,
          "lastName": boardData.users[0].lastName,
          "gender": boardData.users[0].gender,
          "authProvider": boardData.users[0].authProvider,
          "userRole": boardData.users[0].userRole,
          "dateOfBirth": boardData.users[0].dateOfBirth,
          "locked": boardData.users[0].locked,
          "enabled": boardData.users[0].enabled,
          "boards": []
        }
      ]
    }
  };
  const otherParams = {
    headers: { "content-type": "application/json; charset=UTF-8" },
    method: "POST",
    body: JSON.stringify(otherDataOfCard),
  }
  // console.log(otherParams);
  let idOfNewCard;
  await fetch(cardApiPath, otherParams)
    .then(function (response) {
      if (response.ok) {
        document.getElementById("message").innerHTML = "success";

        // get id of new card
        response.json().then(data => {
          idOfNewCard = data.id;
          idListArray[column].push(idOfNewCard);
        })

      } else {
        document.getElementById("message").innerHTML = "something error!!!";
        throw new Error("Could not reach the API" + response.statusText);
      }
    })

  // web socket
  const cardMessage = {
    "sourceUserGmail": userGmail,
    "targetUserGmail": userGmail,
    "method": "create",
    "cardID": 0,
    "boardID": boardID,
    "cardCategory": arrayNames[column],
    "cardTitle": itemText
  };
  stompClient.send("/app/board/update", {}, JSON.stringify(cardMessage));

  // add to activity
  await sleep(2000);
  const activityApiUrl = "http://localhost:8080/api/activity/add/".concat(boardID.toString()).concat("/").concat(idOfNewCard.toString()).concat("/")
    .concat(userID).concat("/create");
  await fetch(activityApiUrl, {
    headers: { "content-type": "application/json; charset=UTF-8" },
  })
    .then(function (response) {
      if (response.ok) {
        console.log("add new create activity ");
      } else {
        throw new Error("Could not reach the API" + response.statusText);
      }
    })
}

// Show Add Item Input Box
function showInputBox(column) {
  addBtns[column].style.visibility = 'hidden';
  saveItemBtns[column].style.display = 'flex';
  addItemContainers[column].style.display = 'flex';
}

// Hide Item Input Box
function hideInputBox(column) {
  addBtns[column].style.visibility = 'visible';
  saveItemBtns[column].style.display = 'none';
  addItemContainers[column].style.display = 'none';
  addToColumn(column);
}

// Allows arrays to reflect Drag and Drop items
function rebuildArrays() {
  backlogListArray = [];
  let str;
  for (let i = 0; i < backlogListEl.children.length; i++) {
    str = backlogListEl.children[i].textContent;
    backlogListArray.push(str);
  }
  progressListArray = [];
  for (let i = 0; i < progressListEl.children.length; i++) {
    str = progressListEl.children[i].textContent;
    progressListArray.push(str);
  }
  completeListArray = [];
  for (let i = 0; i < completeListEl.children.length; i++) {
    str = completeListEl.children[i].textContent;
    completeListArray.push(str);
  }
  onHoldListArray = [];
  for (let i = 0; i < onHoldListEl.children.length; i++) {
    str = onHoldListEl.children[i].textContent;
    onHoldListArray.push(str);
  }
  updateDOM();
}

// When Item Enters Column Area
function dragEnter(column) {
  listColumns[column].classList.add('over');
  currentColumn = column;
}

// When Item Starts Dragging
function drag(e) {
  draggedItem = e.target;
  indexItem = e.target.id;
  // console.log(draggedItem.parentNode.parentNode.id);
  switch (draggedItem.parentNode.parentNode.id) {
    case "backlog-content":
      sourceColumn = 0;
      break;
    case "progress-content":
      sourceColumn = 1;
      break;
    case "complete-content":
      sourceColumn = 2;
      break;
    case "on-hold-content":
      sourceColumn = 3;
      break;
    default:
      sourceColumn = -1;
      break;
  }
  dragging = true;
}

// Column Allows for Item to Drop
function allowDrop(e) {
  e.preventDefault();
}

// Dropping Item in Column
function drop(e) {
  e.preventDefault();
  const parent = listColumns[currentColumn];
  // Remove Background Color/Padding
  listColumns.forEach((column) => {
    column.classList.remove('over');
  });
  // Add item to Column
  parent.appendChild(draggedItem);
  // Dragging complete
  dragging = false;

  // update list of ID
  const idCardInDB = idListArray[sourceColumn][indexItem];
  idListArray[sourceColumn].splice(indexItem, 1);
  idListArray[currentColumn].push(idCardInDB);
  rebuildArrays();
  // update category of card in db
  let nameOfCurrentColumn;
  switch (currentColumn) {
    case 0:
      nameOfCurrentColumn = "backlog";
      break;
    case 1:
      nameOfCurrentColumn = "progress";
      break;
    case 2:
      nameOfCurrentColumn = "complete";
      break;
    case 3:
      nameOfCurrentColumn = "onHold";
      break;
    default:
      console.log("name of current column is invalid");
      break;
  }

  var putApiUrl = "http://localhost:8080/api/card/category/".concat(idCardInDB.toString())
    .concat("/").concat(nameOfCurrentColumn);
  const otherParams = {
    headers: { "content-type": "application/json; charset=UTF-8" },
    method: "PUT",
  };
  fetch(putApiUrl, otherParams)
    .then(function (response) {
      if (response.ok) {
        console.log("update category success");
      } else {
        throw new Error(response.statusText);
      }
    })

  // web socket
  var cardTitle;
  fetch(putApiUrl, otherParams)
    .then(response => response.json())
    .then(response => {
      cardTitle = response.title;
      // console.log(cardTitle);
      const cardMessage = {
        "sourceUserGmail": userGmail,
        "targetUserGmail": userGmail,
        "method": "changeCardCategory",
        "cardID": idCardInDB,
        "boardID": boardID,
        "cardCategory": nameOfCurrentColumn,
        "cardTitle": cardTitle,
      };
      stompClient.send("/app/board/update", {}, JSON.stringify(cardMessage));
    })
    .catch(error => console.error("error", error));

  // add activity
  const activityApiUrl = "http://localhost:8080/api/activity/category/".concat(boardID.toString()).concat("/")
    .concat(idCardInDB).concat("/").concat(userID.toString());
  fetch(activityApiUrl, {
    headers: { "content-type": "application/json; charset=UTF-8" },
    method: "PUT"
  })
    .then(function (response) {
      if (response.ok) {
        console.log("add new category activity ");
      } else {
        throw new Error("Could not reach the API" + response.statusText);
      }
    });
}

// On Load
updateDOM();

// // onclick button
var notification_click = document.getElementById("notification-click")
notification_click.addEventListener('click', async function () {
  var modal_notification = document.getElementById("modal-notification")
  modal_notification.classList.toggle('open-modal-notification');

  var divParent = document.getElementById("notification-info");
  await fetch("http://localhost:8080/api/activity/notification/"
    .concat(userID.toString())
    .concat("/")
    .concat(boardID.toString()), {
      method : "GET" ,
    })
      .then(response => response.json())
      .then(data => {
        for(var j = 0; j < data.length; j++){
          
          if(data[j].sourceUser != -1){  
            var newDivTag = document.createElement('div');
            newDivTag.setAttribute('class', 'flex-flex-start modal-user-button');
            let content = "";
            for(var i = 0; i < memberID.length; i++){
              if(memberID[i] == data[j].sourceUser){
                content = content.concat(member[i]);
              }
            }
            data[j].method = data[j].method.slice(0, -1);
            var array = data[j].method.split(" ");
            array.pop();
            var method = array.join(" ");
            content = content.concat(" ").concat(method).concat(" ").concat(getCardTitleByID(data[j].card)).concat(" cho bạn !");
            newDivTag.textContent = content;
            console.log("content: " + content);
            divParent.appendChild(newDivTag);
          }
        }
      });
  console.log("notification click!!");
});

notification_click.addEventListener('onfocusout', function(){
  console.log("ON FOCUS OUT");
  var divParent = document.getElementById("notification-info");
  divParent.removeChild();
})


var avatar_click = document.getElementById("user-avatar")
avatar_click.addEventListener('click',function(){
    var modal_notification = document.getElementById("modal-user-info")
    // console.log(notification_click)
    modal_notification.classList.toggle('open-modal-user-info');
});

// var list_item_team_click = document.getElementById("menu-extend");
// console.log(list_item_team_click);
// list_item_team_click.addEventListener('click',function(){
//     var list_item_extend = document.querySelector("#list-iteam-team > ul:nth-child(2)");
//     // console.log(list_item_team_click)
//     // list_item_extend.style.backgroundColor = "red";
//     list_item_extend.classList.toggle('ul-extend');
// });

// open edit form
async function openEditForm(id, column) {

  document.getElementsByTagName('li').contentEditable = false;
  var edit_form = document.getElementById("form-edit-board")
  edit_form.classList.toggle('display-none');

  const selectedArray = listArrays[column];
  const selectedColumn = listColumns[column].children;
  const idArray = idListArray[column];
  const idOfCard = idArray[id];
  currentCardIDFormDisplay = idOfCard;
  const cardTitle = listArrays[column][id];
  let cardCategory = "";
  switch (column) {
    case 1:
      cardCategory = "backlog";
      break;
    case 2:
      cardCategory = "progress";
      break;
    case 3:
      cardCategory = "complete";
      break;
    case 4:
      cardCategory = "onHold";
      break;
    default:
      break;
  }
  // fetch infor of card
  await fetch("http://localhost:8080/api/card/".concat(idOfCard.toString()), {
    headers: { "content-type": "application/json; charset=UTF-8" },
    method: "GET"
  })
    .then(response => response.json())
    .then(data => {
      // print deadline of card
      if (data.dueDate != null && data.dueDate != "") {
        var dMY = data.dueDate.split("-");
        var date = new Date();
        date.setFullYear(parseInt(dMY[2]), parseInt(dMY[1]) - 1, parseInt(dMY[0]));
        document.getElementById("deadline").valueAsDate = date;
      }

      // print description of card 
      if (data.description != null && data.description != "") {
        document.getElementById("describe-card").value = data.description;
      }

      // print member who is assigned to this card
      if (data.userID != null && data.userID != "") {
        for (var i = 0; i < memberID.length; i++) {
          if (memberID[i] == data.userID) {
            document.getElementById("add-new-member-value").value = member[i];
            break;
          }
        }
      }
    })

  // fetch infor about activity of this card
  const inforApiUrl = "http://localhost:8080/api/activity/card/".concat(idOfCard.toString());
  let sourceUser = [];
  let destUser = [];
  let method = [];
  await fetch(inforApiUrl, {
    headers: { "content-type": "application/json; charset=UTF-8" },
    method: "GET"
  })
    .then(response => response.json())
    .then(data => {
      const count = Object.keys(data).length;
      console.log("count = ");
      console.log(count);
      var i;
      for (i = 0; i < count; i++) {
        sourceUser.push(data[i].sourceUser);
        destUser.push(data[i].destUser);
        method.push(data[i].method);
      }
    })
    .catch(error => console.log("error " + error));

  // display list of activity
  let ulTag = document.createElement('ol');
  ulTag.setAttribute("id", "temp");
  for (let i = 0; i < sourceUser.length; i++) {
    if (sourceUser[i] == destUser[i]) {
      // get name of source user 
      await fetch("http://localhost:8080/api/user/id/".concat(sourceUser[i]))
        .then(response => response.json())
        .then(data => {
          var infor = data.firstName.concat(" ").concat(data.lastName).concat(" ").concat(method[i]);
          var li = document.createElement('li');
          li.textContent = infor;
          ulTag.appendChild(li);
        })
        .catch(error => console.log("error " + error));
    } else {
      let sourceUserName;
      let destUserName;
      await fetch("http://localhost:8080/api/user/id/".concat(sourceUser[i]))
        .then(response => response.json())
        .then(data => {
          sourceUserName = data.firstName.concat(" ").concat(data.lastName)
        })
        .catch(error => console.log("error " + error));

      await fetch("http://localhost:8080/api/user/id/".concat(destUser[i]))
        .then(response => response.json())
        .then(data => {
          destUserName = data.firstName.concat(" ").concat(data.lastName)
        })
        .catch(error => console.log("error " + error));

      var infor = sourceUserName.concat(" ").concat(method[i]).concat(" ").concat(destUserName);
      var li = document.createElement('li');
      li.textContent = infor;
      ulTag.appendChild(li);
    }
  }
  document.getElementById("activity-history").appendChild(ulTag);


  // deadline add
  document.getElementById('deadline-button').onclick = async function () {
    const deadlineValue = document.getElementById("deadline").value;
    const deadlineApiUrl = "http://localhost:8080/api/card/deadline/".concat(idOfCard.toString());
    await fetch(deadlineApiUrl, {
      method: "PUT",
      body: deadlineValue
    })
      .then(function (response) {
        if (response.ok) {
          document.getElementById("update-infor-message").textContent = "Cập nhật deadline thành công";
        } else {
          throw new Error("Could not reach the API" + response.statusText);
        }
      })

    await fetch("http://localhost:8080/api/activity/deadline/"
      .concat(boardID.toString())
      .concat("/")
      .concat(idOfCard.toString())
      .concat("/")
      .concat(userID.toString()), {
        method : "PUT",
      })
        .then(function (response) {
          if (response.ok) {
            console.log("OK!");
          } else {
            throw new Error("Could not reach the API" + response.statusText);
          }
        });
  }

  // description update
  document.getElementById("description-button").onclick = async function () {
    const description = document.getElementById("describe-card").value;
    await fetch("http://localhost:8080/api/card/description/".concat(idOfCard.toString()), {
      method: "PUT",
      body: description
    })
      .then(function (response) {
        if (response.ok) {
          document.getElementById("description-message").textContent = "Cập nhật miêu tả cho thẻ thành công !";
        } else {
          document.getElementById("description-message").textContent = "Vui lòng thử lại !";
        }
      });
    
    await fetch("http://localhost:8080/api/activity/description/".concat(boardID.toString())
      .concat("/")
      .concat(idOfCard.toString())
      .concat("/")
      .concat(userID.toString()), {
        method : "PUT",
      })
        .then(function (response) {
          if (response.ok) {
            console.log("OK!");
          } else {
            throw new Error("Could not reach the API" + response.statusText);
          }
        });

  }

  // add new member
  document.getElementById("add-user-button").onclick = async function () {
    for (var i = 0; i < member.length; i++) {
      if (member[i] == document.getElementById("add-new-member-value").value) {
        await fetch("http://localhost:8080/api/card/member/".concat(memberID[i].toString()).concat("/").concat(idOfCard.toString()), {
          method: "PUT"
        })
          .then(function (response) {
            if (response.ok) {
              document.getElementById("add-member-suggest").style.display = "none";
              document.getElementById("update-infor-message").textContent = "Gán thành viên thành công !";
              // web socket
              const cardMessage = {
                "sourceUserGmail": userGmail,
                "targetUserGmail": memberEmail[i],
                "method": "assignToCard",
                "cardID": idOfCard,
                "boardID": Number.parseInt(boardID),
                // store gmail of user who assigns this card to another member
                "cardCategory": cardCategory,
                "cardTitle": cardTitle,
              };
              stompClient.send("/app/board/update", {}, JSON.stringify(cardMessage));
            } else {
              document.getElementById("add-member-suggest").style.display = "none";
              document.getElementById("update-infor-message").textContent = "Gán thành viên thất bại. Vui lòng thử lại !";
            }
          });

        await fetch("http://localhost:8080/api/activity/assign/".concat(boardID.toString()).concat("/")
          .concat(idOfCard.toString()).concat("/")
          .concat(userGmail).concat("/")
          .concat(memberEmail[i]), {
          method: "PUT"
        })
          .then(function (response) {
            if (response.ok) {
              console.log("them hoat dong gan the thanh cong");
            } else {
              throw new Error("Could not reach the API" + response.statusText);
            }
          });
      }
    }
  }
}

function closeEditForm() {
  document.getElementById("add-new-member-value").value = "";
  document.getElementById("add-member-suggest").style.display = "none";
  document.getElementById("update-infor-message").textContent = "";
  document.getElementById("description-message").textContent = "";
  document.getElementById("describe-card").value = "";
  var edit_form = document.getElementById("form-edit-board")
  edit_form.classList.toggle('display-none');
  removeElement("temp");
  currentCardIDFormDisplay = -1;
}

// implement WebSocket
var stompClient = null;

function connect() {
  var socket = new SockJS('/gs-guide-websocket');
  console.log("Connecting....");
  stompClient = Stomp.over(socket);
  stompClient.connect({}, function (frame) {
    console.log('Connected: ' + frame);
    stompClient.subscribe('/topic/update/'.concat(boardID.toString()), function (newCardMessage) {
      // add new card
      const resp = JSON.parse(newCardMessage.body);
      const index = getIndexInIDListArray(resp.cardCategory);
      let isSender = idListArray[index].includes(resp.cardID);
      if (resp.method == 'create') {
        if (!isSender) {
          idListArray[index].push(resp.cardID);
          listArrays[index].push(resp.cardTitle);
          updateDOM();
        }
      }

      if (resp.method == 'changeCardCategory') {
        if (!isSender) {
          // delete old card
          var i;
          for (i = 0; i < idListArray.length; i++) {
            const indexOldCard = idListArray[i].indexOf(resp.cardID);
            if (indexOldCard > -1) {
              idListArray[i].splice(indexOldCard, 1);
              listArrays[i].splice(indexOldCard, 1);
              break;
            }
          }
          // update new card
          idListArray[index].push(resp.cardID);
          listArrays[index].push(resp.cardTitle);
          updateDOM();
        }
      }

      if (resp.method == 'deleteCard') {
        var i;
        for (i = 0; i < idListArray.length; i++) {
          const indexOldCard = idListArray[i].indexOf(resp.cardID);
          if (indexOldCard > -1) {
            idListArray[i].splice(indexOldCard, 1);
            listArrays[i].splice(indexOldCard, 1);
            break;
          }
        }
        if (!isSender) {
          // delete old card
          console.log("is not sender");
        }
        updateDOM();
      }

      if (resp.method == 'changeCardTitle') {
        var i;
        for (i = 0; i < idListArray.length; i++) {
          const indexOldCard = idListArray[i].indexOf(resp.cardID);
          if (indexOldCard > -1) {
            listArrays[i][indexOldCard] = resp.cardTitle;
            break;
          }
        }
        updateDOM();
      }

      if ((resp.method == 'assignToCard') && (resp.targetUserGmail == userGmail) && (resp.sourceUserGmail != userGmail)) {

        for (var i = 0; i < idListArray.length; i++) {
          for (var j = 0; j < idListArray[i].length; j++) {
            if (idListArray[i][j] == resp.cardID) {
              for (var k = 0; k < memberEmail.length; k++) {
                document.getElementById("notification-div-tag").style.display = "flex";
                document.getElementById("assign-to-card-notification").innerHTML = member[k] + " đã gán thẻ " + listArrays[i][j] + " cho bạn !";
                setTimeout(function () {
                  document.getElementById("assign-to-card-notification").innerHTML = "";
                  document.getElementById("notification-div-tag").style.display = "none";
                }, 3000);
                break;
              }
            }
          }
        }
      }
      console.log(resp);
    })
  })
}

connect();

// add recent view
// RVB = recently viewed board
async function addRVB() {
  await sleep(2000);
  await fetch("http://localhost:8080/api/rvb/".concat(userID.toString()).concat("/").concat(boardID.toString()), {
    method: "POST"
  })
    .then(function (response) {
      if (response.ok) {
        console.log("added RVB");
      } else {
        throw new Error("Could not reach the API" + response.statusText);
      }
    })
}

addRVB();

document.getElementById("new-member-form").addEventListener("focusout", () => {
  if (document.getElementById("new-member-gmail").value == null || document.getElementById("new-member-gmail").value == "") {
    document.getElementById("new-member-form").style.display = "none";
  }
})

function showInputGmail() {
  document.getElementById("new-member-form").style.display = "block";
  document.getElementById("add-new-member").onclick = async function () {
    const newGmail = document.getElementById("new-member-gmail").value;
    await fetch("http://localhost:8080/api/board/member/".concat(boardID.toString()).concat("/").concat(newGmail), {
      method: "PUT"
    })
      .then(function (response) {
        if (response.ok) {
          console.log("them thanh cong");
          document.getElementById("new-member-message").textContent = "Thêm thành viên mới vào bảng thành công";
        } else {
          console.log("them that bai");
          document.getElementById("new-member-message").textContent = "Tài khoản email chưa được đăng ký trên hệ thống !";
        }
      })
  }
}




function getStates(value) {
  const listElement = document.getElementById("add-member-suggest");
  listElement.style.display = "block";
  listElement.innerHTML = '';
  for (var key in member) {
    if (member[key].toLowerCase().includes(value.toLowerCase())) {
      let liEl = document.createElement('li');
      liEl.textContent = member[key];
      liEl.onclick = function () {
        document.getElementById("add-new-member-value").value = liEl.textContent;
      }
      listElement.appendChild(liEl);
    }
  }
}
