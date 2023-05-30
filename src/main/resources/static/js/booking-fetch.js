const adultCountElement = document.getElementById('adultCount');
const childCountElement = document.getElementById('childCount');
const dateInput = document.getElementById('date');

var stageOneButton = document.getElementById('stage1');
var adultCount = 0;
var childCount = 0;
var peopleCount = document.getElementById('dropdownMenuButton');

var selectedRooms = [];

var isDateValid = false;
var isCountValid = true;

function changeButtonParam() {
	if (isDateValid && isCountValid) {
		stageOneButton.disabled = false;
	} else {
		stageOneButton.disabled = true;
	}
}

dateInput.addEventListener('click', function() {
	document.getElementsByClassName('datepicker')[0].addEventListener('click', function() {
		if (dateInput.checkValidity()) {
			isDateValid = true;
		} else {
			isDateValid = false;
		}

		changeButtonParam();
	});
});

function isGuestCountValid() {
	adultCount = adultCountElement.innerHTML;
	childCount = childCountElement.innerHTML;
	if (parseInt(adultCount) >= 1 && parseInt(adultCount) <= 8 &&
		parseInt(childCount) >= 0 && parseInt(childCount) <= 8) {
		isCountValid = true;
	} else {
		isCountValid = false;
	}

	changeButtonParam();
}

stageOneButton.addEventListener('click', function() {
	isGuestCountValid();
	if (!isCountValid) {
		return;
	}

	var dates = dateInput.value.split('-');
	var url = '/api/rooms/' + dates[0] + '/' + dates[1] + '?adult=' + adultCount + '&child=' + childCount;

	fetch(url, {
		method: 'GET'
	}).then(response => {
		if (response.ok) {
			return response.text();
		} else {
			throw new Error('Network response was not ok.');
		}
	}).then(data => {
		const parentElement = document.getElementById('formTwo');
		parentElement.innerHTML = '';
		parentElement.insertAdjacentHTML('beforeend', data);
		runScripts();
	}).catch(error => {
		console.error('There was a problem with the fetch operation:', error);
	});
});

function runScripts() {
	var scripts = document.getElementsByClassName("script-room");
	for (let i = 0; i < scripts.length; i++) {
		var scriptCode = scripts[i].innerHTML;
		eval(scriptCode);
	}
};