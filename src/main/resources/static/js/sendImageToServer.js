document.querySelector('#uploadImages').addEventListener('click', async function(event) {
	handleImageUpload();
	window.location.reload();
});

function handleImageUpload() {
	const id = document.getElementById("id").value;
	const files = document.querySelector('#inputImages').files;

	const url = '/admin/rooms/' + id + '/images';

	for (let i = 0; i < files.length; i++) {
		const formData = new FormData();
		formData.append('file', files[i]);

		$.ajax({
			url: url,
			type: "POST",
			data: formData,
			processData: false,
			contentType: false,
			async: false,
			error: function(jqXHR, textStatus, errorMessage) {
				console.log(errorMessage);
			}
		});
	}
}