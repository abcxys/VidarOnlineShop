var tables;
$(function() {
	tables = $('#packingSlipTable').DataTable({
	"serverSide" : true,//分页，取数据等等的都放到服务端去
	"lengthChange": true,
	"order": [],
	"bProcessing" : true,
	"searching" : false,
	"autoWidth": false,
	"scrollY": "400px",
    "scrollX": true,
    "scrollCollapse": true,
	"sPaginationType" : "full_numbers",
	ajax : {
		type : "post",
		url : "/inventory/getFilteredStocks",
		dataSrc : "data",
		data : function (d) {
			var param = {};
			param.draw = d.draw;
			param.startPos = d.start;
			param.pageSize = d.length;
			param.colour = $('#colour').val();
			param.width = $('#width').val();
			param.species = $('#species').val();
			param.grade = $('#grade').val();
			return param;
		},
	    "error": function (data) {
	    	alert("error");
	    }
	},
	columns : [
	    {"data" : '', "bSortable" : false},
	    {"data" : 'speciesName', "bSortable" : true},
	    {"data" : 'width', "bSortable" : true},
		{"data" : 'colorName', "bSortable" : true},
		{"data" : 'gradeAlias', "bSortable" : true},
		{"data" : 'stock', "bSortable" : false},
		{"data" : '', "bSortable" : false}
	],
	'columnDefs': [{
		'targets': 0,
		'searchable': false,
		'orderable': false,
		'className': 'dt-body-center',
		'render': function (data, type, full, meta){
			return '<input type="checkbox" class="call-checkbox" name="checkbox-id" value="' + '">';
		}
	},{
		data: null,
		defaultContent: '<i class="fa fa-light fa-circle-plus add2order" style="cursor: pointer;"></i>',
		targets: -1
	}]
});
tables.on('click', '.add2order', function (e) {
    $("#addFloorToOrderModal").modal('show');
});
});

$("#searchInventoryBtn").on("click", function () {
	tables.draw();
});

function closeAddFloorToOrderModal() {
	$("#addFloorToOrderModal").modal('hide');
}