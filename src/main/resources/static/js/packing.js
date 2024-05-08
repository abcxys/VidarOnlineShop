let packingSlipsTable;
const quantityCell = function(cell) {
    let original;
    cell.setAttribute('contenteditable', true)
    cell.setAttribute('spellcheck', false)
    cell.addEventListener("focus", function(e) {
        original = e.target.textContent
    });
    cell.addEventListener("blur", function(e) {
        const parentTable = $(e.target).closest('table').DataTable();
        if (original !== e.target.textContent) {
            //const row = parentTable.row(e.target.parentElement);
            const cellData = parentTable.cell(e.target).data();
            const rowIndex = parentTable.cell(e.target).index().row;
            const colIndex = parentTable.cell(e.target).index().column;
            //row.invalidate()
            parentTable.cell({ row: rowIndex, column: colIndex }).data(e.target.textContent);
            console.log('Row changed: ', e.target.textContent);
        }
    });
    cell.addEventListener("keypress", function(e) {
        let charCode = (e.which) ? e.which : e.keyCode;
        if (charCode < 48 || charCode > 57){
            e.preventDefault();
        }
    });
}

$(document).ready(function() {
    packingSlipsTable = $('#packingSlipProductsTable').DataTable({
        "serverSide" : false,
        "lengthChange": false,
        "info": false,
        "bProcessing" : true,
        "bPaginate" : false,
        "searching" : false,
        "autoWidth": false,
        "scrollY": "400px",
        "scrollX": true,
        "scrollCollapse": true,
        ajax : {
            method : "GET",
            url : "/packing/getSalesOrderItemsTableById",
            dataSrc : "data",
            data : function (d) {
                let param = {};
                // param.draw = d.draw;
                // param.startPos = d.start;
                // param.pageSize = d.length;
                param.packingSlipId = $('#packingSlipId').val();
                return param;
            },
            "error": function (data) {
                alert("error");
            }
        },
        columns : [
            {"data" : '', "bSortable" : false},
            {"data" : 'quantity', "bSortable" : true},
            {"data" : 'id', "bSortable" : false},
            {"data" : 'floorColorSize', "bSortable" : true},
            {"data" : 'floorColorSize', "bSortable" : false}
        ],
        'columnDefs': [{
            'targets': 0,
            'searchable': false,
            'className': 'dt-body-center editor-delete',
            'render': function (data, type, full, meta){
                //return '<input type="checkbox" class="call-checkbox" name="checkbox-id" value="' + '">';
                return '<button type="button"><i class="fa fa-trash"/></button>'
            }
        }, {
            'targets': 1,
            'createdCell': quantityCell
        },{
            'targets': 2,
            'visible': false,
            'searchable': false,
            'className': 'dt-body-center',
            'render': function(data) {
                return data;
            }
        }, {
            'targets': 3,
            'searchable': false,
            'visible': false,
            'className': 'dt-body-center',
            'render': function(data) {
                return data.id;
            }
        }, {
            'targets': 4,
            'searchable': false,
            'className': 'dt-body-center',
            'render': function(data) {
                return data.width +'\" ' + data.woodSpeciesName.split(" ")[data.woodSpeciesName.split(" ").length - 1]
                    + " " + data.colorName + " " + data.gradeAlias + " " + data.sqftPerCarton + " " + data.batchName;
            }
        }],
        order: []
    });

    packingSlipsTable.on('click', 'td.editor-delete button', function (e) {
        $(this).closest('tr').remove();
    });

    $('#dealer').on('change', function(){
        $.ajax({
            url: '/salesOrder/getDealerInfoById',
            method: 'GET',
            data: {
                id: $('#dealer').val()
            },
            success : function(response){
                $('#dealer_name').val(response.address).change();
                $('#dealer_address').val(response.address).change();
            },
            error: function(xhr){
                bootboxAlertError(xhr.responseText);
            }
        });
    });

    $('.dealer-address').on('input change', function(){
        let fontSize = parseInt(window.getComputedStyle(this, null).getPropertyValue('font-size'));
        let lineHeight = parseInt(window.getComputedStyle(this, null).getPropertyValue('line-height'));

        while(this.scrollHeight > this.offsetHeight){
            fontSize--;
            lineHeight -=2;
            this.style.fontSize = fontSize + 'px';
            this.style.lineHeight = lineHeight + 'px';
        }
    });

    // set the value of the datepicker default to be current datetime.
    //$('#soDatepicker').datepicker("setDate", new Date());

    $('form#packingSlipForm').submit(function(event){
        event.preventDefault();
        console.log("Submitting sales order form!");

        let tableData = [];
        $('#packingSlipProductsTable tbody tr').each(function(){
            if (packingSlipsTable.row(this).data()!=null){
                let rowData = {
                    productId: packingSlipsTable.row(this).data().floorColorSize.id,
                    quantity: packingSlipsTable.row(this).data().quantity
                };
                tableData.push(rowData);
            } else{
                let rowData = {
                    productId: Number($(this).find('select').val()),
                    quantity: Number($(this).find('td:eq(1) input').val())
                };
                tableData.push(rowData);
            }

        });

        let jsonData = {
            "id": $('#salesOrderId').val(),
            "address": $('#dealer_address').val(),
            "statusId": $('#salesOrderStatus').val(),
            "releaseOk": parseInt($('#releaseOk').val()) === 1,
            "date": $('#packingDatepicker').datepicker('getDate'),
            "dateWanted": $('#dateWanted').datepicker('getDate'),
            "soNumber": $('#soNumber').val(),
            "poNumber": $('#poNumber').val(),
            "dealerId": $('#dealer').val(),
            "salesRepId": $('#salesRep').val(),
            "warehouseId": $('#warehouse').val(),
            "salesOrderItems": tableData
        };

        console.log('The json data to be transferred is ');
        console.log(jsonData);

        $.ajax({
            url: $(this).attr('action'),
            method: $(this).attr('method'),
            data: JSON.stringify(jsonData),
            contentType: "application/json",
            success: function(response){
                bootboxAlertPrompt(response);
                setTimeout(function(){location.reload();}, 2000);
            },
            error: function(xhr, status, error){
                bootboxAlertError(xhr.responseText);
                setTimeout(function(){location.reload();}, 2000);
            }
        });
    });

    $('#addProductBtn').on('click', function(){
        $('#packingSlipProductsTable tbody').append(`
            <tr>
                <td class="dt-body-center editor-delete">
                    <button type="button"><i class="fa fa-trash"/></button>
                </td>
                <td class="dt-body-center">
                    <input type="number" class="form-control" name="quantity" required>
                </td>
                <td class="dt-body-center">
                    <select class="selectpicker form-control productSelector" data-live-search="true" required>
                    </select>
                </td>
            </tr>
        `);
        populateSelect($('.productSelector').last());
    });
});