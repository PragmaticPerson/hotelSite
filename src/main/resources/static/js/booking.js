$(function() {
	var myDatepicker = $('#date').datepicker({
		minDate: new Date(),
		range: true,
		multipleDatesSeparator: '-',
		onSelect: function onSelect(selectedDates) {
			var today = new Date();
			if (selectedDates !== undefined && selectedDates.indexOf('-') <= -1 && selectedDates != '') {
				var dayst = selectedDates.split('.');
				var year = +dayst[2];
				var month = +dayst[1] - 1;
				var daymax = +dayst[0] + 21;
				var daymin = +dayst[0] - 21;
				var daycheck = new Date(year, month, daymin);
				if (today.valueOf() <= daycheck.valueOf()) {
					$('#date').datepicker({
						maxDate: new Date(year, month, daymax),
						minDate: new Date(year, month, daymin)
					});
				} else {
					$('#date').datepicker({
						maxDate: new Date(year, month, daymax),
						minDate: new Date()
					});
				}

			} else if (selectedDates !== undefined && selectedDates != '' && selectedDates.indexOf('-') > -1) {
				var mdy = selectedDates.split('-');
				var dist = mdy[0].split('.');
				var year = +dist[2];
				var month = +dist[1] - 1;
				var daymax = +dist[0] + 21;
				var daymin = +dist[0] - 21;
				var daycheck = new Date(year, month, daymin);
				if (today.valueOf() <= daycheck.valueOf()) {
					$('#date').datepicker({
						maxDate: new Date(year, month, daymax),
						minDate: new Date(year, month, daymin)
					});
				} else {
					$('#date').datepicker({
						maxDate: new Date(year, month, daymax),
						minDate: new Date()
					});
				}
			} else if (selectedDates !== undefined || selectedDates != '') {
				$('#date').datepicker({
					maxDate: '',
					minDate: new Date
				});
			}
		}
	})
});

var stageOneButton = document.getElementById('stage1');
var dateInput = document.getElementById('date');
var peopleCount = document.getElementById('peopleCount');

var isDateValid = false;
var isCountValid = false;

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

peopleCount.addEventListener('input', function() {
	if (this.value > 0 && this.value < 10) {
		isCountValid = true;
	} else {
		isCountValid = false;
	}

	changeButtonParam();
});

stageOneButton.addEventListener('click', function() {
	var dates = dateInput.value.split('-');
	var url = '/api/rooms/' + dates[0] + '/' + dates[1];
	console.log(url);

	$('#formTwo').load(url);
});
