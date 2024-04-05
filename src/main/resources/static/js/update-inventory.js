var tables;
$(function() {
	tables = $('#updateInventoryTable').DataTable({
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
		url : "/inventory/getFilteredProductStocks",
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
			param.batch = $('#batch').val();
			return param;
		},
	    "error": function (data) {
	    	alert("error");
	    }
	},
	columns : [
	    {"data" : 'speciesName', "bSortable" : true},
	    {"data" : 'width', "bSortable" : true},
		{"data" : 'colorName', "bSortable" : true},
		{"data" : 'gradeAlias', "bSortable" : true},
		{"data" : 'batch', "bSortable" : true},
		{"data" : 'bay', "bSortable" : false},
		{"data" : 'stock', "bSortable" : false},
		{"data" : '', "bSortable" : false}
	],
	'columnDefs': [{
		'targets': 0,
		'render': function (data, type, full, meta){
			return data.split(' ')[data.split(' ').length - 1];
		}
	},{
		data: null,
		defaultContent: '<i class="fas fa-light fa-edit editInventory" style="cursor: pointer;"></i>',
		targets: -1
	}]
});
tables.on('click', '.editInventory', function (e) {
    $("#editInventoryModal").modal('show');
});

$("#searchProductInventoryBtn").on("click", function () {
	tables.draw();
});
});

function closeEditInventoryModal() {
	$("#editInventoryModal").modal('hide');
}