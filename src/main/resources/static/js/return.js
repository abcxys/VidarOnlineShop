let packingSlipsTable;
let subtable;
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

const createdCell = function(cell) {
    let original;
    let maxVal=10000;
    cell.setAttribute('contenteditable', true);
    cell.setAttribute('spellcheck', false);
    cell.addEventListener("focus", function(e) {
        original = e.target.textContent;
        maxVal = subtable.row(e.target).data().quantity;
    });
    cell.addEventListener("keyup", function(e) {
        if (original !== e.target.textContent) {
            const newValue = e.target.textContent.trim();
            if (!isNaN(newValue)){
                //const row = subtable.row(e.target.parentElement);
                if (newValue > maxVal){
                    console.log('Value greater than max value. Reverting to original value.');
                    e.target.textContent = original;
                } else {
                    const cellData = subtable.cell(e.target).data();
                    const rowIndex = subtable.cell(e.target).index().row;
                    const colIndex = subtable.cell(e.target).index().column;
                    //row.invalidate()
                    subtable.cell({ row: rowIndex, column: colIndex }).data(newValue);
                    console.log('Row changed: ', newValue);
                    // Set cursor position to end of cell content
                    const range = document.createRange();
                    const sel = window.getSelection();
                    range.selectNodeContents(e.target);
                    range.collapse(false);
                    sel.removeAllRanges();
                    sel.addRange(range);
                    e.target.focus();
                }
            } else {
                // If the input is not a valid number, revert to the original value
                e.target.textContent = original;
                console.log('Invalid input. Reverting to original value.');
            }
        }
    });
    cell.addEventListener("blur", function(e) {
        if (original !== e.target.textContent) {
            const newValue = e.target.textContent.trim();
            if (!isNaN(newValue)){
                //const row = subtable.row(e.target.parentElement);
                if (newValue > maxVal){
                    console.log('Value greater than max value. Reverting to original value.');
                    e.target.textContent = original;
                } else {
                    const cellData = subtable.cell(e.target).data();
                    const rowIndex = subtable.cell(e.target).index().row;
                    const colIndex = subtable.cell(e.target).index().column;
                    //row.invalidate()
                    subtable.cell({ row: rowIndex, column: colIndex }).data(newValue);
                    console.log('Row changed: ', newValue);
                    // Set cursor position to end of cell content
                    const range = document.createRange();
                    const sel = window.getSelection();
                    range.selectNodeContents(e.target);
                    range.collapse(false);
                    sel.removeAllRanges();
                    sel.addRange(range);
                    e.target.focus();
                }
            } else {
                // If the input is not a valid number, revert to the original value
                e.target.textContent = original;
                console.log('Invalid input. Reverting to original value.');
            }
        }
    });
}

function closeCreateReturnSlipModal() {
    $('#createReturnSlipModal').modal('hide');
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
            {"data" : 'floor', "bSortable" : true},
            {"data" : 'floor', "bSortable" : false}
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
            'render': function(data){
                return data.size.split('inch')[0].trim() + "\"" + " " +
                    data.species.split(' ')[data.species.split(' ').length-1] + " " +
                    data.color + " " +
                    data.grade + " " +
                    data.size.split(' ')[data.size.split(' ').length-1] + " " +
                    data.batchNumber;
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

    $('form#updatePackingForm').submit(function(event){
        // click on updatePackingSlipBtn
        event.preventDefault();
        console.log("Submitting packing slip form!");

        let tableData = [];
        $('#packingSlipProductsTable tbody tr').each(function(){
            if (packingSlipsTable.row(this).data()!=null){
                let rowData = {
                    id: packingSlipsTable.row(this).data().id,
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
            "id": $('#packingSlipId').val(),
            "driverId": $('#driver').val(),
            "statusId": $('#packingSlipStatus').val(),
            "shippingMethodId": $('#via').val(),
            "dealerCompanyName": $('#dealer_address').val(),
            "packingSlipItems": tableData
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

    $('#createReturn').on('click', function(){

        $('#createReturnSlipModal').modal('show');
    });

    $('#createReturnSlipModal').on('shown.bs.modal', function () {
        subtable = $('#createReturnSlipTable').DataTable({
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
                {"data" : 'floor', "bSortable" : false},
                {"data" : 'quantity', "bSortable" : true},
                {"data" : '', "bSortable" : false}
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
                targets: -1,
                data: null,
                defaultContent: '',
                createdCell: createdCell
            }],
            order: []
        });
    });

    $('form#createReturnForm').submit(function(event) {
        event.preventDefault();
        console.log('create return form submitted!');

        let tableData = [];
        $('#createReturnSlipTable tbody tr').each(function(){
            if (packingSlipsTable.row(this).data()!=null && Number($(this).find('td:eq(3)').html()) > 0){
                let rowData = {
                    productId: packingSlipsTable.row(this).data().floor.id,
                    quantity: Number($(this).find('td:eq(3)').html())
                };
                tableData.push(rowData);
            }
        });

        let jsonData = {
            "packingSlipId": $('#packingSlipId').val(),
            "returnItems": tableData
        };

        console.log('Transfer json data to server side.');

        $.ajax({
            url: $(this).attr('action'),
            method: $(this).attr('method'),
            data: JSON.stringify(jsonData),
            contentType: "application/json",
            success: function(response) {
                bootboxAlertPrompt(response);
                let baseUrl = window.location.origin;
                setTimeout(function() {
                    window.open(baseUrl + "/return", "_blank");
                }, 2000);
            },
            error: function(xhr, status, error) {
                bootboxAlertError(xhr.responseText);
            }
        });
    })
});