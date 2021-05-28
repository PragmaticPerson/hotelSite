var b = false;
document.getElementById('mobile-button').addEventListener('click', function (event) {
	if (b == false) {
		b = true;
		document.getElementById('mobileMenuDown').style.display = 'block';
		document.getElementById('mobileLogo').style.display = 'flex';
		document.getElementById('main_content').style.display = 'none';
	}
	else{
		b = false;
		document.getElementById('mobileMenuDown').style.display = 'none';
		document.getElementById('mobileLogo').style.display = 'none';
		document.getElementById('main_content').style.display = 'block';
	}
});