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

function closeCreatePackingSlipModal() {
    $('#createPackingSlipModal').modal('hide');
}
$(document).ready(function() {
    $('#startDatepicker').datepicker("setDate", new Date());
    $('#endDatepicker').datepicker("setDate", new Date());

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

});