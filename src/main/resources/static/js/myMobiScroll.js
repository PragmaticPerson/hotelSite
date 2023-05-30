var mbscData = [];
var resources = [];

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
	resources: [
		{
			"id": 1,
			"name": "1 Одноместный люкс",
			"color": "#4981d6"
		},
		{
			"id": 2,
			"name": "2 Одноместный люкс",
			"color": "#4981d6"
		},
		{
			"id": 3,
			"name": "3 Одноместный",
			"color": "#4981d6"
		},
		{
			"id": 4,
			"name": "4 Одноместный",
			"color": "#4981d6"
		},
		{
			"id": 5,
			"name": "5 Одноместный",
			"color": "#4981d6"
		},
		{
			"id": 6,
			"name": "6 Одноместный",
			"color": "#4981d6"
		},
		{
			"id": 7,
			"name": "7 Двухместный люкс",
			"color": "#4981d6"
		},
		{
			"id": 8,
			"name": "8 Двухместный люкс",
			"color": "#4981d6"
		},
		{
			"id": 9,
			"name": "9 Двухместный с совместным размещением",
			"color": "#4981d6"
		},
		{
			"id": 10,
			"name": "10 Двухместный с совместным размещением",
			"color": "#4981d6"
		},
		{
			"id": 11,
			"name": "11 Двухместный с раздельным размещением",
			"color": "#4981d6"
		},
		{
			"id": 12,
			"name": "12 Двухместный с раздельным размещением",
			"color": "#4981d6"
		}
	],
	onPageLoading: function(event, inst) {
		var fromDay = event.firstDay.toISOString();
		var toDay = event.lastDay.toISOString();

		$.ajax({
			url: '/admin/api/calendar/' + fromDay + '/' + toDay,
			method: 'GET',
			success: function(response) {
				inst.setEvents(response.data);
			},
			error: function(xhr, status, error) {
				console.error(error);
			}
		});
	}
});