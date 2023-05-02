function deleteButtons(imageId) {
	const id = document.getElementById("id").value;
	const url = '/admin/rooms/' + id + '/images/' + imageId;

	var http = new XMLHttpRequest();
	http.open("POST", url, true);
	http.addEventListener("readystatechange", function() {
		if (http.readyState === 4 && http.status === 200) {
			window.location.reload(true);
		}
	});
	http.send();
}