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
let boardTitle;

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

function getIndexInIDListArray(category){
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

// Get Arrays from localStorage if available, set default values if not
async function getSavedColumns() {
  // use local storage
  // if (localStorage.getItem('backlogItems')) {
  //   backlogListArray = JSON.parse(localStorage.backlogItems);
  //   progressListArray = JSON.parse(localStorage.progressItems);
  //   completeListArray = JSON.parse(localStorage.completeItems);
  //   onHoldListArray = JSON.parse(localStorage.onHoldItems);
  // } else {
  //   backlogListArray = ['Release the course', 'Sit back and relax'];
  //   progressListArray = ['Work on projects', 'Listen to music'];
  //   completeListArray = ['Being cool', 'Getting stuff done'];
  //   onHoldListArray = ['Being uncool'];
  // }

  // fetch data from database
  const apiURL = "http://localhost:8080/api/card/board/".concat(boardID.toString());
  const resp = await fetch(apiURL);
  const data = await resp.json();
  const numOfCards = Object.keys(data).length;
  // console.log(data);
  if(numOfCards > 0){
    var i;
    for(i = 0; i < numOfCards; i++){
      switch (data[i].category){
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
  listEl.setAttribute('onfocusout', `updateItem(${index}, ${column})`);
  listEl.setAttribute('ondragstart', 'drag(event)');
  listEl.contentEditable = true;

  // bug =)))
  // add edit-button
  // const editButton = document.createElement('div');
  // editButton.setAttribute('class', 'add-btn');
  // editButton.setAttribute('onclick', 'openEditForm()');
  // editButton.setAttribute('display', 'inline');

  // const spanTag = document.createElement('span');
  // spanTag.textContent = 'Chinh';
  // editButton.appendChild(spanTag);
  
  // listEl.appendChild(editButton);
  // Append
  columnEl.appendChild(listEl);
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

  // console.log(backlogIdArray);
  // console.log(progressIdArray);
  // console.log(completeIdArray);
  // console.log(ohHoldIdArray);
}

// Update Item - Delete if necessary, or update Array value
function updateItem(id, column) {
  const selectedArray = listArrays[column];
  const selectedColumn = listColumns[column].children;
  const idArray = idListArray[column];
  const idOfCard = idArray[id];
  if (!dragging) {
    // delete item
    if (!selectedColumn[id].textContent) {
      delete selectedArray[id]; // !!!
      // delete card in database
      var deleteApiUrl = "http://localhost:8080/api/card/".concat(idOfCard.toString());
      const otherParams = {
        headers : { "content-type" : "application/json; charset=UTF-8"},
        method : "DELETE",
      };
      fetch(deleteApiUrl, otherParams)
        .then(function(response){
          if(response.ok){
            console.log("deleted card !!!");
          }else{
            throw new Error(response.statusText);
          }
        })
      
    } else {
      // modify item title
      selectedArray[id] = selectedColumn[id].textContent;
      const newTitle = selectedColumn[id].textContent;
      var modifyApiUrl = "http://localhost:8080/api/card/title/".concat(idOfCard.toString()).concat("/").concat(newTitle);
      const otherParams = {
        headers : { "content-type" : "application/json; charset=UTF-8"},
        method : "PUT",
      };
      fetch(modifyApiUrl, otherParams)
        .then(function(response){
          if(response.ok){
            console.log("update title success");
          }else{
            throw new Error(response.statusText);
          }
        })
    }
    updateDOM();
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
  document.getElementById("board-title").innerHTML = boardTitle;

  // send new card data to database
  var cardApiPath = "http://localhost:8080/api/card/add";
  const otherDataOfCard = {
    "title" : itemText,
    "dueDate": "",
    "description": "",
    "color": "blue",
    "category": arrayNames[column],
    "board" : {
      "id": boardData.id,
      "title": boardData.title,
      "backGroundImage": boardData.backGroundImage,
      "visiable": boardData.visiable,
      "user" : [
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
    headers : { "content-type" : "application/json; charset=UTF-8"},
    method : "POST",
    body : JSON.stringify(otherDataOfCard),
  }
  // console.log(otherParams);
  let idOfNewCard;
  await fetch(cardApiPath, otherParams)
    .then(function(response){
      if(response.ok){
        document.getElementById("message").innerHTML = "success";
        // console.log("fetched api !");

        // get id of new card
        response.json().then(data => {
          idOfNewCard = data.id;
          // console.log(idOfNewCard);
          idListArray[column].push(idOfNewCard);
        })
        
      }else{
        document.getElementById("message").innerHTML = "something error!!!";
        throw new Error("Could not reach the API" + response.statusText);
      }
    })
  
  // web socket
  const cardMessage = {
    "cardID" : 0,
    "boardID" : boardID,
    "category" : arrayNames[column],
    "title" : itemText
  };
  stompClient.send("/app/board/update", {}, JSON.stringify(cardMessage));
  
  updateDOM(column);
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
  for (let i = 0; i < backlogListEl.children.length; i++) {
    backlogListArray.push(backlogListEl.children[i].textContent);
  }
  progressListArray = [];
  for (let i = 0; i < progressListEl.children.length; i++) {
    progressListArray.push(progressListEl.children[i].textContent);
  }
  completeListArray = [];
  for (let i = 0; i < completeListEl.children.length; i++) {
    completeListArray.push(completeListEl.children[i].textContent);
  }
  onHoldListArray = [];
  for (let i = 0; i < onHoldListEl.children.length; i++) {
    onHoldListArray.push(onHoldListEl.children[i].textContent);
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
  // update category of card in db
  var putApiUrl = "http://localhost:8080/api/card/category/".concat(idCardInDB.toString())
    .concat("/").concat(nameOfCurrentColumn);
  const otherParams = {
    headers : { "content-type" : "application/json; charset=UTF-8"},
    method : "PUT",
  }
  fetch(putApiUrl, otherParams)
    .then(function(response){
      if(response.ok){
        console.log("update category success");
      }else{
        throw new Error(response.statusText);
      }
    })
  rebuildArrays();
}

// On Load
updateDOM();

// // onclick button
// var notification_click = document.getElementById("notification-click")
// console.log(notification_click);
// notification_click.addEventListener('click',function(){
//     var modal_notification = document.getElementById("modal-notification")
//     // console.log(notification_click)
//     modal_notification.classList.toggle('open-modal-notification');
// });

// var avatar_click = document.getElementById("user-avatar")
// console.log(avatar_click);
// avatar_click.addEventListener('click',function(){
//     var modal_notification = document.getElementById("modal-user-info")
//     // console.log(notification_click)
//     modal_notification.classList.toggle('open-modal-user-info');
// });

// var list_item_team_click = document.getElementById("menu-extend");
// console.log(list_item_team_click);
// list_item_team_click.addEventListener('click',function(){
//     var list_item_extend = document.querySelector("#list-iteam-team > ul:nth-child(2)");
//     // console.log(list_item_team_click)
//     // list_item_extend.style.backgroundColor = "red";
//     list_item_extend.classList.toggle('ul-extend');
// });

// // open edit form
// function openEditForm() {
//   var edit_form = document.getElementById("form-edit-board")
//   edit_form.classList.toggle('display-none');

// }
// function closeEditForm(){
//   var edit_form = document.getElementById("form-edit-board")
//   edit_form.classList.toggle('display-none');
// }

// implement WebSocket
var stompClient = null;

function connect(){
  var socket = new SockJS('/gs-guide-websocket');
  console.log("Connecting....");
  stompClient = Stomp.over(socket);
  stompClient.connect({}, function(frame){
    console.log('Connected: ' + frame);
    stompClient.subscribe('/topic/update', function(newCardMessage){
      // add new card
      const resp = JSON.parse(newCardMessage.body);
      const index = getIndexInIDListArray(resp.category);
      let isSender = idListArray[index].includes(resp.cardID);
      if(!isSender){
        idListArray[index].push(resp.cardID);
        listArrays[index].push(resp.title);
        updateDOM();
      }
      console.log(resp);
    })  
  })
}

connect();













