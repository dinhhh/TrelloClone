const userGmail = document.getElementById("user-gmail").textContent;
const submit = document.getElementById("submit-button");
const submitChangePassword = document.getElementById("submit-button-sercurity");

async function getUserInformation() {
	const getInforApiUrl = "http://localhost:8080/api/user/".concat(userGmail);
	const resp = await fetch(getInforApiUrl);
	const data = await resp.json();

	document.getElementById("user-name").textContent = data.firstName.concat(" ").concat(data.lastName).concat(" ");
	document.getElementById("userName").placeholder = data.firstName.concat(" ").concat(data.lastName);

	var date = data.dateOfBirth;
	date = date.split("-").reverse().join("-");
	document.getElementById("date-of-birth").placeholder = date;

	var gender;
	switch (data.gender) {
		case "male":
			gender = "Nam";
			break;
		case "female":
			gender = "Nữ";
			break;
		default:
			gender = "";
			break;
	}
	document.getElementById("gender").placeholder = gender;


	console.log(data);
}

getUserInformation();

submit.onclick = async function () {
	const newName = document.getElementById("userName").value;
	if (newName != null && newName != "") {
		const changeNameApiUrl = "http://localhost:8080/api/user/name/".concat(userGmail);
		await fetch(changeNameApiUrl, {
			method: "PUT",
			body: newName
		})
			.then(function (response) {
				if (response.ok) {
					console.log("change name ok");
				} else {
					throw new Error("Could not reach the API" + response.statusText);
				}
			});
	}

	const newDoB = document.getElementById("date-of-birth").value;
	if (newDoB != null && newDoB != "") {
		const changeDoBApiUrl = "http://localhost:8080/api/user/dob/".concat(userGmail);
		await fetch(changeDoBApiUrl, {
			method: "PUT",
			body: newDoB
		})
			.then(function (response) {
				if (response.ok) {
					console.log("change dob ok");
				} else {
					throw new Error("Could not reach the API" + response.statusText);
				}
			});
	}

	const newGender_ = document.getElementById("gender").value;

	if (newGender_ != null && newGender_ != "") {
		const newGender = newGender_ == "Nam" ? "male" : "female";
		const changeGenderApiUrl = "http://localhost:8080/api/user/gender/".concat(userGmail);
		await fetch(changeGenderApiUrl, {
			method: "PUT",
			body: newGender
		})
			.then(function (response) {
				if (response.ok) {
					console.log("change gender ok");
				} else {
					throw new Error("Could not reach the API" + response.statusText);
				}
			});
	}
	location.reload();
}

submitChangePassword.onclick = async function () {
	// validate input of user

	// update password
	const oldPass = document.getElementById("old-password").value;
	const newPass = document.getElementById("new-password-1").value;
	const changePassApiUrl = "http://localhost:8080/api/user/password/".concat(userGmail).concat("/").concat(oldPass);
	await fetch(changePassApiUrl, {
		method: "PUT",
		body: newPass
	})
		.then(function (response) {
			if (response.ok) {
				document.getElementById("message").textContent = "Password is updated !"
			} else {
				document.getElementById("message").textContent = "Please check your old password !"
			}
		});
	document.getElementById("old-password").value = "";
	document.getElementById("new-password-1").value = "";
	document.getElementById("new-password-2").value = "";
}

























