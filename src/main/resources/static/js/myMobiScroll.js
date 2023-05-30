var mbscData = [];
var resources = [];

$.ajax({
	url: '/admin/api/calendar/rooms',
	method: 'GET',
	async: false,
	success: function(response) {
		resources = response;
	},
	error: function(xhr, status, error) {
		console.error(error);
	}
});

mobiscroll.setOptions({
	theme: 'ios',
	themeVariant: 'light'
});

mobiscroll.eventcalendar('#month-view', {
	view: {
		timeline: {
			type: 'month'
		}
	},
	resources: resources,
	onPageLoading: function(event, inst) {
		var fromDay = event.firstDay.toISOString();
		var toDay = event.lastDay.toISOString();

		$.ajax({
			url: '/admin/api/calendar/' + fromDay + '/' + toDay,
			method: 'GET',
			success: function(response) {
				inst.setEvents(response);
			},
			error: function(xhr, status, error) {
				console.error(error);
			}
		});
	}
});