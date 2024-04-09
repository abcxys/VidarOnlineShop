var tables;
var subtable;
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
		{"data" : 'id', "bSortable" : false},
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
		'visible': false
	},{
		'targets': 1,
		'render': function (data, type, full, meta){
			return data.split(' ')[data.split(' ').length - 1];
		}
	},{
		data: null,
		defaultContent: '<i class="fas fa-light fa-edit editInventory" style="cursor: pointer;"></i>',
		targets: -1
	}]
});
subtable = $('#updateInventoryItemTable').DataTable({
	"serverSide" : true,//分页，取数据等等的都放到服务端去
	"lengthChange": true,
	"order": [],
	"bProcessing" : true,
	"autoWidth": false,
	"searching" : false,
	"bInfo": false,
	"paging": false,
	ajax : {
		type : "post",
		url : "/inventory/getInventoryByProductId",
		dataSrc : "data",
		data : function () {
			return {
				productId: $('#editInventoryProductId').val()
			};
		},
		"error": function (data) {
			alert("error");
		}
	},
	columns : [
		{"data" : 'id', "bSortable" : false},
		{"data" : 'productId', "bSortable" : true},
		{"data" : 'location', "bSortable" : true},
		{"data" : 'quantity', "bSortable" : true},
		{"data" : '', "bSortable" : false}
	],
	'columnDefs': [{
		'targets': 0,
		'visible': false
	},{
		'targets': 1,
		'visible': false
	},{
		targets: -1,
		data: null,
		defaultContent: '<i class="fas fa-light fa-edit editInventory" style="cursor: pointer;"></i>'
	},{
		targets: [2, 3],
		createdCell: createdCell
	}]
});
tables.on('click', '.editInventory', function (e) {
	var rowData = tables.row($(this).closest('tr')).data();
	$('#editInventoryProductId').val(rowData.id);
	$("#editInventoryModal").modal('show');
	subtable.columns.adjust().draw();
});

$("#searchProductInventoryBtn").on("click", function () {
	tables.draw();
});

$("#addNewInventoryBtn").on("click", function() {
	console.log("Add!");
	const mockInventory = {
		id: "",
		productId: $('#editInventoryProductId').val(),
		location: "",
		quantity: ""
	}
	console.log("The product id for adding new inventory is " + mockInventory.productId);
	let newInventoryItemDiv = '<form class="form-horizontal" method="post" action="/update/add-new-inventory">' +
		'<div class="modal-body">'+
		'<div class="form-group row">' +
		'<div style="display: none;"><input type="number" name="id" value="' + mockInventory.id + '"></div>' +
		'<div style="display: none;"><input type="number" name="productId" value="' + mockInventory.productId + '"></div>' +
		'<div class="col-md-2">' +
		'<label class="control-label xrequired">Location: </label>' +
		'<input type="text" name="location" value="' + mockInventory.location + '" style="height: 50%;">' +
		'</div>' +
		'<div class="col-md-2 ml-auto">' +
		'<label class="control-label xrequired">Quantity: </label>' +
		'<input type="number" name="quantity" value="' + mockInventory.quantity + '" style="height: 50%;">' +
		'</div>' +
		'<div class="col-md-2 ml-auto">' +
		'<button type="submit" class="btn btn-primary" style="position: absolute;bottom: 0;">' +
		'<i class="fas fa-pencil"></i>' +
		'</button>' +
		'</div>' +
		'</div>' +
		'</div>' +
		'</form>';
	$('<div class="addInventoryItemDiv">').html(newInventoryItemDiv).insertBefore('.modal-footer');
});
});

function closeEditInventoryModal() {
	$(".addInventoryItemDiv").remove();
	$("#editInventoryModal").modal('hide');
}

const createdCell = function(cell) {
	let original;
	cell.setAttribute('contenteditable', true)
	cell.setAttribute('spellcheck', false)
	cell.addEventListener("focus", function(e) {
		original = e.target.textContent
	})
	cell.addEventListener("blur", function(e) {
		if (original !== e.target.textContent) {
			const row = subtable.row(e.target.parentElement)
			row.invalidate()
			console.log('Row changed: ', row.data())
		}
	})
}