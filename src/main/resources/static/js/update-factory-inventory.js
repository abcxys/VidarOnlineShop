let factoryInventoryTable;
let subtable;
$(function() {
    factoryInventoryTable = $('#updateInventoryTable').DataTable({
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
            method : "GET",
            url : "/inventory/getFilteredProductFactoryStocks",
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
            method : "GET",
            url : "/inventory/getFactoryInventoryByProductId",
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
            defaultContent: '<i class="fas fa-light fa-edit editInventoryItem" style="cursor: pointer;"></i>'
        },{
            targets: 3,
            createdCell: createdCell
        }]
    });
    factoryInventoryTable.on('click', '.editInventory', function (e) {
        let rowData = factoryInventoryTable.row($(this).closest('tr')).data();
        $('#editInventoryProductId').val(rowData.id);
        $("#editInventoryModal").modal('show');
        subtable.columns.adjust().draw();
    });
    subtable.on('click', '.editInventoryItem', function() {
        let rowData = subtable.row($(this).closest('tr')).data();
        // this api can only change either location or quantity
        console.log('Quantity changed: ', rowData.quantity);
        console.log('Location changed: ', rowData.location);
        if (!rowData.location.trim()){
            bootboxAlertError("Location can not be empty!");
            return;
        }
        if (!rowData.quantity || typeof(parseFloat(rowData.quantity)) !== 'number'){
            bootboxAlertError("Quantity value is invalid!");
            return;
        }
        $.ajax({
            url: '/factory-inventory/update',
            method: 'PUT',
            data: {
                id: rowData.id,
                location: rowData.location,
                quantity: rowData.quantity
            },
            success : function(response) {
                bootboxAlertPrompt(response);
                //setTimeout(function(){location.reload();}, 2000);
            },
            error: function(xhr, status, error){
                bootboxAlertError(xhr.responseText);
                //setTimeout(function(){location.reload();}, 2000)
            }
        });
    });

    $("#searchProductInventoryBtn").on("click", function () {
        factoryInventoryTable.draw();
    });

    $("#addNewInventoryBtn").on("click", function() {
        console.log("Add!");
        const mockInventory = {
            id: "",
            productId: $('#editInventoryProductId').val(),
            location: "factory",
            quantity: ""
        }
        console.log("The product id for adding new inventory is " + mockInventory.productId);
        let newInventoryItemDiv = '<form class="form-horizontal" method="post" action="/update/add-new-factory-inventory">' +
            '<div class="modal-body">'+
            '<div class="form-group row">' +
            '<div style="display: none;"><input type="number" name="id" value="' + mockInventory.id + '"></div>' +
            '<div style="display: none;"><input type="number" name="productId" value="' + mockInventory.productId + '"></div>' +
            '<div class="col-md-2">' +
            '<label class="control-label xrequired">Location: </label>' +
            '<input type="text" name="location" value="' + mockInventory.location + '" style="height: 50%;" readonly>' +
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
            //const row = subtable.row(e.target.parentElement);
            const cellData = subtable.cell(e.target).data();
            const rowIndex = subtable.cell(e.target).index().row;
            const colIndex = subtable.cell(e.target).index().column;
            //row.invalidate()
            subtable.cell({ row: rowIndex, column: colIndex }).data(e.target.textContent);
            console.log('Row changed: ', e.target.textContent);
        }
    })
}