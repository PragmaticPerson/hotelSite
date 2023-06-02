function editButton(id) {
	var editButton = document.getElementById('editButton-' + id);
	var saveButton = document.getElementById('saveButton-' + id);
	var nameInput = document.getElementById('nameInput-' + id);
	var phoneInput = document.getElementById('phoneInput-' + id);
	var emailInput = document.getElementById('emailInput-' + id);

	nameInput.removeAttribute('disabled');
	phoneInput.removeAttribute('disabled');
	emailInput.removeAttribute('disabled');

	editButton.classList.add('d-none');
	saveButton.classList.remove('d-none');
}

function saveButton(id) {
	var editButton = document.getElementById('editButton-' + id);
	var saveButton = document.getElementById('saveButton-' + id);
	var nameInput = document.getElementById('nameInput-' + id);
	var phoneInput = document.getElementById('phoneInput-' + id);
	var emailInput = document.getElementById('emailInput-' + id);

	nameInput.setAttribute('disabled', 'disabled');
	phoneInput.setAttribute('disabled', 'disabled');
	emailInput.setAttribute('disabled', 'disabled');

	const data = {
		'id': id,
		'name': nameInput.value,
		'phone': phoneInput.value,
		'email': emailInput.value
	};

	$.ajax({
		url: '/admin/client/' + id,
		method: 'post',
		data: data,
		success: function(response) {
			showPopup("Успешное изменение записи");
		},
		error: function(xhr, status, error) {
			showPopup(error);
		}
	});

	editButton.classList.remove('d-none');
	saveButton.classList.add('d-none');
}

function showPopup(message) {
	var popup = document.getElementById('popup');
	var popupMessage = document.getElementById('popup-message');

	popupMessage.textContent = message;

	popup.classList.add('popup-show');

	setTimeout(function() {
		popup.classList.remove('popup-show');
	}, 5000);
}