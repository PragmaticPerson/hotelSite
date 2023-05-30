var items = document.getElementsByClassName('dropdown-item-my');
document.getElementById('adultInput').value = '1';

for (var i = 0; i < items.length; ++i) {
	var item = items[i];
	item.addEventListener('click', function(event) {
		event.stopPropagation();
		isGuestCountValid();
	});
}

function sub(type) {
	var countElement = document.getElementById(type + 'Count');
	var countInput = document.getElementById(type + 'Input');
	var count = parseInt(countElement.innerHTML) - 1;
	
	if (type === 'adult') {
		if (count >= 1) {
			countElement.innerHTML = count;
			countInput.value = String(count);
		}
		if (count == 1) {
			document.getElementById(type + 'Sub').disabled = true;
		}
	} else if (type === 'child') {
		if (count >= 0) {
			countElement.innerHTML = count;
			countInput.value = String(count);
		}
		if (count == 0) {
			document.getElementById(type + 'Sub').disabled = true;
		}
	}

	document.getElementById(type + 'Add').disabled = false;
	updateGuestDisplay();
}

function add(type) {
	var countElement = document.getElementById(type + 'Count');
	var countInput = document.getElementById(type + 'Input');
	var count = parseInt(countElement.innerHTML) + 1;
	
	if (count <= 8) {
		countElement.innerHTML = count;
		countInput.value = String(count);
	}
	if (count == 8) {
		document.getElementById(type + 'Add').disabled = true;
	}

	document.getElementById(type + 'Sub').disabled = false;
	updateGuestDisplay();
}

//Обновляет количество гостей и обновляет отображение количества гостей на странице
function updateGuestCount(change, type) {
	var countElement = document.getElementById(type + 'Count');
	var count = parseInt(countElement.innerHTML) + change;
	if (count >= 0) {
		countElement.innerHTML = count;
		updateGuestDisplay();
	}
}

// Обновляет текст на кнопке выпадающего списка, чтобы показать выбранное количество гостей
function updateGuestDisplay() {
	var guestCount = document.getElementById('guestCount');
	var adultCount = Number(document.getElementById('adultCount').innerHTML);
	var childCount = Number(document.getElementById('childCount').innerHTML);
	guestCount.innerHTML = adultCount + ' взрослых, ' + childCount
		+ ' детей';
}