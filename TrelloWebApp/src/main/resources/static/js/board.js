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

// Initialize Arrays
let backlogListArray = [];
let progressListArray = [];
let completeListArray = [];
let onHoldListArray = [];
let listArrays = [];

// Drag Functionality
let draggedItem;
let dragging = false;
let currentColumn;

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
  console.log(data);
  if(numOfCards > 0){
    var i;
    for(i = 0; i < numOfCards; i++){
      switch (data[i].category){
        case "backlog":
          backlogListArray.push(data[i].title);
          break;
        case "progress":
          progressListArray.push(data[i].title);
          break;
        case "complete":
          completeListArray.push(data[i].title);
          break;  
        case "onHold":
          onHoldListArray.push(data[i].title);
          break;
      }
    }
  }
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
  // Append
  columnEl.appendChild(listEl);
}

// Update Columns in DOM - Reset HTML, Filter Array, Update localStorage
function updateDOM() {
  // Check localStorage once
  if (!updatedOnLoad) {
    console.log("get saved column !");
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
function updateItem(id, column) {
  const selectedArray = listArrays[column];
  const selectedColumn = listColumns[column].children;
  if (!dragging) {
    if (!selectedColumn[id].textContent) {
      delete selectedArray[id];
    } else {
      selectedArray[id] = selectedColumn[id].textContent;
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
  console.log(boardData);
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
  console.log(otherParams);
  fetch(cardApiPath, otherParams)
    .then(function(response){
      if(response.ok){
        document.getElementById("message").innerHTML = "success";
        return response.json();
      }else{
        document.getElementById("message").innerHTML = "something error!!!";
        throw new Error("Could not reach the API" + response.statusText);
      }
    })

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
  rebuildArrays();
}

// On Load
updateDOM();
