let salesOrdersTable;
$(document).ready(function() {
    salesOrdersTable = $('#salesOrderProductsTable').DataTable({
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
            url : "/salesOrder/getSalesOrderProducts",
            dataSrc : "data",
            data : function (d) {
                var param = {};
                param.draw = d.draw;
                param.startPos = d.start;
                param.pageSize = d.length;
                param.so_id = $('#salesOrderId').val();
                return param;
            },
            "error": function (data) {
                alert("error");
            }
        },
        columns : [
            {"data" : '', "bSortable" : false},
            {"data" : 'quantity', "bSortable" : true},
            {"data" : 'floor', "bSortable" : false},
            {"data" : 'floor', "bSortable" : true},
            {"data" : 'floor', "bSortable" : false},
            {"data" : 'quantity_picked_up', "bSortable" : false}
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
            'targets': 2,
            'visible': false,
            'searchable': false,
            'className': 'dt-body-center',
            'render': function(data) {
                return data.id;
            }
        }, {
            'targets': 3,
            'searchable': false,
            'className': 'dt-body-center',
            'render': function(data) {
                return data.size.split('inch')[0].trim() + "\"" + " " +
                    data.species.split(' ')[data.species.split(' ').length-1] + " " +
                    data.color + " " +
                    data.grade + " " +
                    data.size.split(' ')[data.size.split(' ').length-1] + " " +
                    data.batchNumber;
            }
        }, {
            'targets': 4,
            'searchable': false,
            'className': 'dt-body-center',
            'render': function(data) {
                return data.price;
            }
        }, {
            'targets': 5,
            'visible': false
        }],
        order: []
    });

    salesOrdersTable.on('click', 'td.editor-delete button', function (e) {
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

    $('form#salesOrderForm').submit(function(event){
        event.preventDefault();
        console.log("Submitting sales order form!");

        let tableData = [];
        $('#salesOrderProductsTable tbody tr').each(function(){
            if (salesOrdersTable.row(this).data()!=null){
                let rowData = {
                    productId: salesOrdersTable.row(this).data().floorColorSize.id,
                    quantity: salesOrdersTable.row(this).data().quantity
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
            "date": $('#soDatepicker').datepicker('getDate'),
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
        $('#salesOrderProductsTable tbody').append(`
            <tr>
                <td class="dt-body-center editor-delete">
                    <button type="button"><i class="fa fa-trash"/></button>
                </td>
                <td class="dt-body-center">
                    <input type="number" step="0.5" class="form-control" name="quantity" required>
                </td>
                <td class="dt-body-center">
                    <select class="selectpicker form-control productSelector" data-live-search="true" required>
                    </select>
                </td>
                <td class="dt-body-center">
                    <input type="number" step="0.01" class="form-control" name="price" required>
                </td>
            </tr>
        `);
        populateSelect($('.productSelector').last());
    });
});