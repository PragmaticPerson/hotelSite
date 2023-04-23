const adultCount = document.getElementById('adultCount');
const childCount = document.getElementById('childCount');
const dateInput = document.getElementById('date');

var stageOneButton = document.getElementById('stage1');
var peopleCount = document.getElementById('dropdownMenuButton');

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
	if (parseInt(adultCount.innerHTML) >= 1 && parseInt(adultCount.innerHTML) <= 8 &&
		parseInt(childCount.innerHTML) >= 0 && parseInt(childCount.innerHTML) <= 8) {
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
	var url = '/api/rooms/' + dates[0] + '/' + dates[1];
	console.log(url);

	$('#formTwo').load(url);
});
