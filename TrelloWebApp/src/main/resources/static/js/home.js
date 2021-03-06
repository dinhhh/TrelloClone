let boardIDDelete = -1;
var gmailOfUser = document.getElementById("gmail").textContent;
let boardJson = {};
let loaded = false;

var notification_click = document.getElementById("notification-click")
notification_click.addEventListener('click', async function () {
	if (!loaded){
		let userName = [];
		let userID = [];
		let boardID = [];
		let boardTitle = [];
		let counter = 0;
		await fetch("http://localhost:8080/api/activity/findAddNewMember/"
		.concat(gmailOfUser), {
			method : "GET"
		})
			.then(response => response.json())
			.then(data => {
				counter = Object.keys(data).length;
				if (counter > 0){
					for (var i = 0; i < counter; i++){
						userID.push(data[i].sourceUser);
						boardID.push(data[i].board);
					}
				}
			})
		for (var i = 0; i < counter; i++){
			await fetch("http://localhost:8080/api/board/title/"
			.concat(boardID[i].toString()), {
				method : "GET"
			})
				.then(response => response.json())
				.then(data => {
					boardTitle.push(data.title);
				})
		}
		for (var i = 0; i < counter; i++){
			await fetch("http://localhost:8080/api/user/infor/"
			.concat(userID[i].toString()), {
				method : "GET"
			})
				.then(response => response.json())
				.then(data => {
					var name = data.firstName.concat(" ").concat(data.lastName);
					userName.push(name);
				})
		}
		var parentTag = document.getElementById("notification");
		for (var i = 0; i < counter; i++){
			var newDivTag = document.createElement('div');
			newDivTag.setAttribute('class', 'flex-flex-start modal-user-button');
			var content = userName[i].concat(" đã thêm bạn vào bảng ").concat(boardTitle[i]);
			newDivTag.textContent = content;
			console.log(content);
			parentTag.appendChild(newDivTag);
		}
		loaded = true;
	}

	var modal_notification = document.getElementById("modal-notification")
	// console.log(notification_click)
	modal_notification.classList.toggle('open-modal-notification');
});

function handle_board(boardID) {
	var delete_form = document.getElementById("delete-form");
	delete_form.classList.toggle('display-none');
	document.getElementById("delete-board-button").onclick = async function (){
		console.log("board id = " + boardID);
		await fetch("http://localhost:8080/api/board/".concat(boardID.toString()), {
			method : "DELETE"
		});
		location.reload();
	}
}

var avatar_click = document.getElementById("user-avatar")
console.log(avatar_click);
avatar_click.addEventListener('click', async function () {
	var modal_notification = document.getElementById("modal-user-info")
	// console.log(notification_click)
	modal_notification.classList.toggle('open-modal-user-info');

});

var list_item_team_click = document.getElementById("menu-extend");
console.log(list_item_team_click);
list_item_team_click.addEventListener('click', function () {
	var list_item_extend = document.querySelector("#list-iteam-team > ul:nth-child(2)");
	// console.log(list_item_team_click)
	// list_item_extend.style.backgroundColor = "red";
	list_item_extend.classList.toggle('ul-extend');
});

function cancle() {
	var cancle_form_create_board = document.getElementById("form-add-board");
	console.log(cancle_form_create_board);
	cancle_form_create_board.classList.toggle('display-none');
	console.log("ok");
}
function create() {
	var form_create_board = document.getElementById("form-add-board");
	form_create_board.classList.toggle('display-none');
}


async function getTitle() {
	// var path = "http://localhost:8080/board/title/";
	// var apiURL = path.concat(gmailOfUser);
	// const resp = await fetch(apiURL);
	// const data = await resp.json();
	// var count = Object.keys(data).length;
	// const userID = data[0].users[0].id;
	// var i;

	// if (count > 0) {
	// 	for (i = 0; i < count; i++) {
	// 		const li1 = document.createElement("li");
	// 		li1.setAttribute("class", "each-board");
	// 		const a1 = document.createElement("a");
	// 		a1.setAttribute("class", "title-each-board");
	// 		a1.setAttribute("href", "http://localhost:8080/board/".concat(data[i].id.toString()));
	// 		a1.appendChild(document.createTextNode(data[i].title));
	// 		li1.appendChild(a1);
	// 		boardJson[data[i].id] = data[i].title;
	// 		document.getElementById("owned-board").appendChild(li1);
	// 	}
	// }

	// fetch title of board
	await fetch("http://localhost:8080/api/board/title/user/".concat(gmailOfUser))
		.then(response => response.json())
		.then(data => {
			const counter = Object.keys(data).length;
			if(counter > 0){
				for(var key in data){
					const li1 = document.createElement("li");
					li1.setAttribute("class", "each-board");
					const a1 = document.createElement("a");
					a1.setAttribute("class", "title-each-board");
					a1.setAttribute("href", "http://localhost:8080/board/".concat(key.toString()));
					a1.appendChild(document.createTextNode(data[key]));

					const fDivTag = document.createElement('div');

					const divTag = document.createElement('div');
					divTag.setAttribute("class", "title-each-board button-delete");
					divTag.setAttribute("onclick", `handle_board(${key})`);
					const iTag = document.createElement("i");
					iTag.setAttribute("class", "fas fa-trash-alt")
					divTag.appendChild(iTag);

					fDivTag.appendChild(divTag);

					li1.appendChild(a1);
					li1.appendChild(fDivTag)
					boardJson[key] = data[key];
					document.getElementById("owned-board").appendChild(li1);
				}
			}
		})

	var userID;
	await fetch("http://localhost:8080/user/".concat(gmailOfUser))
		.then(response => response.json())
		.then(data => {
			userID = data[0].id;
		})

	// get recently viewed board of user
	await fetch("http://localhost:8080/api/rvb/".concat(userID.toString()))
		.then(response => response.json())
		.then(data => {
			const counter = Object.keys(data).length;
			if (counter > 0) {
				var limit = 0;
				if(counter > 4){
					limit = 4;
				}else{
					limit = counter;
				}
				for (var i = 0; i < limit; i++) {
					const li1 = document.createElement("li");
					li1.setAttribute("class", "each-board");
					const a1 = document.createElement("a");
					a1.setAttribute("class", "title-each-board");
					a1.setAttribute("href", "http://localhost:8080/board/".concat(data[i].id.toString()));
					a1.appendChild(document.createTextNode(data[i].title));
					li1.appendChild(a1);
					boardJson[data[i].id] = data[i].title;
					document.getElementById("recently-viewed-board").appendChild(li1);
				}
			}
		})
}
getTitle();

function check(event) {
	event.preventDefault();

	const url = "http://localhost:8080/board/title/".concat(document.getElementById("new-board-title").value).concat("/").concat(gmailOfUser);
	const other_params = {
		headers: { "content-type": "application/json; charset=UTF-8" },
		method: "POST",
		mode: "cors"
	};

	fetch(url, other_params)
		.then(function (response) {
			if (response.ok) {
				document.getElementById('message').innerHTML = "success";
				location.reload()
				return response.json();
			} else {
				document.getElementById('message').innerHTML = "Tên bảng không được để trống";
				throw new Error("Could not reach the API: " + response.statusText);
			}
		})
	return true;
}

function getStates(value) {
	const listElement = document.getElementById("search-result");
	document.getElementById("div-search-result").style.display = "block";
	listElement.innerHTML = '';
	for (var key in boardJson) {
		// console.log(boardJson[key]);
		if (boardJson[key].toLowerCase().includes(value.toLowerCase())) {
			let liEl = document.createElement('li');
			// let anchorEl = document.createElement('a');
			// anchorEl.setAttribute('href', 'board/'.concat(key));
			// liEl.appendChild(anchorEl);
			liEl.setAttribute('class', 'modal-user-button');
			liEl.setAttribute('onclick', "location.href=".concat("'").concat("http://localhost:8080/board/").concat(key).concat("'"));
			liEl.textContent = boardJson[key];
			listElement.appendChild(liEl);
		}
	}
}

function sleep(ms) {
	return new Promise(resolve => setTimeout(resolve, ms));
}

const searchField = document.getElementById("search-field");
searchField.addEventListener('focusout', () => {
	var searchValue = document.getElementById("search-field").value;
	console.log(searchValue);
	if(searchValue == null || searchValue == ""){
		document.getElementById("div-search-result").style.display = "none";
	}
});