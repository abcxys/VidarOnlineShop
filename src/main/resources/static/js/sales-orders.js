let salesOrdersTable;
let subtable;
function checkAll(){
    //var rows = tables.rows({'search': 'applied'}).nodes();
    //$('input[type="checkbox"]', rows).prop('checked', this.checked);
    var checked = document.getElementById('example-select-all').checked;
    console.log(checked);
    $("input[name='checkbox-id']").each(function(){
        this.checked = checked;
    });
}

const createdCell = function(cell) {
    let original;
    let maxVal=1000;
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

function closeCreatePackingSlipModal() {
    $('#createPackingSlipModal').modal('hide');
}
$(document).ready(function() {
    $('#startDatepicker').datepicker("setDate", new Date());
    $('#endDatepicker').datepicker("setDate", new Date());

    $('.datepicker').datepicker({
        autoclose: true // Enable autoclose to close the datepicker when a date is selected
    }).on('changeDate', function(e) {
        // Handle the change date event for the start date picker
        console.log('Start date changed:', e.date);
    });

    salesOrdersTable = $('#salesOrdersTable').DataTable({
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
            url : "/salesOrder/getFilteredSalesOrders",
            dataSrc : "data",
            data : function (d) {
                let param = {};
                let statusList = $('#salesOrderStatus').val();
                statusString = statusList.join(',');
                param.draw = d.draw;
                param.startPos = d.start;
                param.pageSize = d.length;
                param.dealerId = $('#dealer').val() || null;
                param.statusIdsString = statusString || null;
                param.startDate = ($('#startDatepicker').datepicker('getDate') == null) ? new Date(0) : $('#startDatepicker').datepicker('getDate');
                param.endDate = ($('#endDatepicker').datepicker('getDate') == null) ? new Date(0) : $('#endDatepicker').datepicker('getDate');
                return param;
            },
            "error": function (data) {
                alert("error");
            }
        },
        columns : [
            {"data" : '', "bSortable" : false},
            {"data" : 'date', "bSortable" : true},
            {"data" : 'totalPrice', "bSortable" : true},
            {"data" : 'firstName', "bSortable" : true},
            {"data" : 'soNumber', "bSortable" : false}
        ],
        'columnDefs': [{
            'targets': 0,
            'searchable': false,
            'orderable': false,
            'className': 'dt-body-center',
            'render': function (data, type, full, meta){
                return '<input type="checkbox" class="call-checkbox" name="checkbox-id" value="' + '">';
            }
        }, {
            'targets': 1,
            'render': function(data){
                let date = new Date(data);
                tempDate = data;

                // Extract day, month, and year from the Date object
                let day = date.getDate();
                let month = date.getMonth() + 1; // Months are zero-indexed, so add 1
                let year = date.getFullYear();
                return year + '-' + (month < 10 ? '0' : '') + month + '-' + (day < 10 ? '0' : '') + day;
            }
        }]
    });

    $('#salesOrdersTable tbody').on('dblclick', 'tr', function(){
        let rowData = salesOrdersTable.row(this).data();
        console.log("The double clicked sales order id = " + rowData.id);
        let baseurl = window.location;
        window.open(baseurl + "/" + rowData.id);
    });

    $('#searchSalesOrderBtn').on('click', function() {
        salesOrdersTable.draw();
    });

    $('#createPacking').on('click', function(){
        let rows = $(salesOrdersTable.$('input[type="checkbox"]').map(function(){
            return $(this).prop("checked") ? $(this).closest('tr') : null;
        }))

        if (rows.length > 0){
            $('#dealerCompanyName').val(salesOrdersTable.row(rows[0]).data().dealer);
        }

        // Get the selected rows
        //let rows = salesOrdersTable.rows({ selected: true }).data();

        // Extract IDs from selected rows and store them in a list
        let ids = [];
        rows.each(function (index, row) {
            ids.push(salesOrdersTable.row(row).data().id);
        });

        // Convert array to comma-separated string
        let idsString = ids.join(',');
        subtable = $('#createPackingSlipTable').DataTable({
            "serverSide": true,
            "lengthChange": true,
            "order": [],
            "bProcessing": true,
            "autoWidth": true,
            "searching": false,
            "bInfo": false,
            "paging": false,
            "bDestroy": true,
            ajax : {
                method : "GET",
                url : "/salesOrder/getSalesOrderProductsList",
                dataSrc : "data",
                data: { ids: idsString }, // Pass ids as URL parameter
                "error": function (data) {
                    alert("error");
                }
            },
            columns : [
                {"data" : 'id', "bSortable" : false},
                {"data" : 'floorColorSize', "bSortable" : false},
                {"data" : 'soDate', "bSortable" : false},
                {"data" : 'soNumber', "bSortable" : false},
                {"data" : 'quantity_on_hand', "bSortable" : false},
                {"data" : 'quantity', "bSortable" : false},
                {"data" : 'quantity_picked_up', "bSortable" : false},
                {"data" : '', "bSortable" : false}
            ],
            'columnDefs': [{
                'targets': 0,
                'visible': false
            },{
                'targets': 1,
                'searchable': false,
                'className': 'dt-body-center',
                'render': function(data) {
                    return data.width +'\" ' + data.woodSpeciesName.split(" ")[data.woodSpeciesName.split(" ").length - 1]
                        + " " + data.colorName + " " + data.gradeAlias + " " + data.sqftPerCarton + " " + data.batchName;
                }
            }, {
                'targets': 2,
                'render' : function(data, type, full, meta){
                    // Convert the milliseconds to a Date object
                    let date = new Date(data.time);
                    tempDate = data;

                    // Extract day, month, and year from the Date object
                    let day = date.getDate();
                    let month = date.getMonth() + 1; // Months are zero-indexed, so add 1
                    let year = date.getFullYear();

                    // Construct the formatted date string
                    let formattedDate = year + '-' + (month < 10 ? '0' : '') + month + '-' + (day < 10 ? '0' : '') + day;
                    return formattedDate;
                }
            }, {
                targets: -1,
                data: null,
                defaultContent: '',
                createdCell: createdCell
            }]
        });
        //subtable.columns.adjust().draw();
        $('#createPackingSlipModal').modal('show');
    });


    $('form#createPackingForm').on('submit',function(event){
        event.preventDefault();
        // create packing slip
        console.log('create packing slip!');

        // collect a list of SalesOrderPacking instances
        // so_product_id
        // quantity
        let tableData = [];
        $('#createPackingSlipTable tbody tr').each(function() {
            // Aggregate data for table 'sales_order_packing'
            // Note that if only push to tableData when quantity > 0
            let rowData = {
                soProductId: subtable.row($(this)).data().id,
                quantity: subtable.cell(subtable.row($(this)).index(), 7).data()
            };
            if (rowData.quantity > 0)
                tableData.push(rowData);
        });

        let jsonData = {
            "driverId": $('#driver').val(),
            "dealerCompanyName": $('#dealerCompanyName').val(),
            "packingSlipItems": tableData,
            "description": $('description').val()
        }

        $.ajax({
            url: $(this).attr('action'),
            method: $(this).attr('method'),
            data: JSON.stringify(jsonData),
            contentType: "application/json",
            success: function(response) {
                console.log(response);
            },
            error: function(xhr, status, error){

            }
        });
    });
});