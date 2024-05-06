let packingSlipTable;
// Array to track the ids of the details displayed rows
const detailRows = [];
let selectedColor;

function format(d) {
    return (
        'Item 1: ' +
        'Test item name' +
        ' ' +
        'Test item quantity' +
        '<br>' +
        'Item 2: ' +
        'Test item name' +
        ' ' +
        'Test item quantity' +
        '<br>' +
        'Driver name - cellphone'
    );
}
function checkAll(){
    //var rows = tables.rows({'search': 'applied'}).nodes();
    //$('input[type="checkbox"]', rows).prop('checked', this.checked);
    let checked = document.getElementById('example-select-all').checked;
    console.log(checked);
    $("input[name='checkbox-id']").each(function(){
        this.checked = checked;
    });
}

$(document).ready(function() {
    $('#startDatepicker').datepicker("setDate", new Date());
    $('#endDatepicker').datepicker("setDate", new Date());

    packingSlipTable = $('#packingSlipTable').DataTable({
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
            url : "/packing/getFilteredPackingSlips",
            dataSrc : "data",
            data : function (d) {
                let param = {};
                let statusList = $('#packingSlipStatus').val();
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
            {
                className: "dt-control",
                orderable: false,
                data: null,
                defaultContent: ''
            },
            {"data" : 'id', "bSortable" : true},
            {"data" : 'dealer', "bSortable" : true},
            {"data" : 'packingStatus', "bSortable" : true}
        ],
        'columnDefs': [{
            'targets': 3,
            "render": function(data, type, full, meta) {
                // Define the background colors for each option
                const optionClasses = {
                    '1': 'created',
                    '2': 'preparing',
                    '3': 'ready',
                    '4': 'loaded'
                };

                // Render the div with custom dropdown and options
                let selectHtml = '<div class="dropdown bootstrap-select packingStatusSelect">';
                selectHtml += '<select aria-label="Status" class="packingStatusSelector selectpicker" data-container="body" tabindex="null">';
                selectHtml += '<option value="1">Created</option>';
                selectHtml += '<option value="2">Preparing</option>';
                selectHtml += '<option value="3">Ready</option>';
                selectHtml += '<option value="4">Loaded</option>';
                selectHtml += '</select>';
                selectHtml += '</div>';
                return selectHtml;
            }
        }]
    });

    $('#packingSlipTable tbody').on('loaded.bs.select changed.bs.select', '.packingStatusSelector', function (e, clickedIndex, isSelected, previousValue) {
        const selectedValue = $(this).val();
        if (selectedValue === "")
            return;
        switch (selectedValue) {
            case '1':
                selectedColor = 'red';
                break;
            case '2':
                selectedColor = 'yellow';
                break;
            case '3':
                selectedColor = '#007bff';
                break;
            default:
                selectedColor = 'green';
        }
        let innerInner = $(this).closest('.dropdown.bootstrap-select').find('.filter-option-inner-inner');
        innerInner.each(function() {
            $(this).css('background', selectedColor);
        });
    });

    $('#packingSlipTable tbody').on('dblclick', 'tr', function(){
        let rowData = packingSlipTable.row(this).data();
        console.log("The double clicked sales order id = " + rowData.id);
        let baseurl = window.location;
        window.open(baseurl + "/" + rowData.id);
    });

    packingSlipTable.on('click', 'tbody td.dt-control', function () {
        let tr = event.target.closest('tr');
        let row = packingSlipTable.row(tr);
        let idx = detailRows.indexOf(tr.id);

        if (row.child.isShown()) {
            tr.classList.remove('details');
            row.child.hide();

            // Remove from the 'open' array
            detailRows.splice(idx, 1);
        }
        else {
            tr.classList.add('details');
            row.child(format(row.data())).show();

            // Add to the 'open' array
            if (idx === -1) {
                detailRows.push(tr.id);
            }
        }
    });

    // On each draw, loop over the `detailRows` array and show any child rows
    packingSlipTable.on('draw', () => {
        $('.packingStatusSelector').selectpicker();
        detailRows.forEach((id, i) => {
            let el = document.querySelector('#' + id + ' td.dt-control');

            if (el) {
                el.dispatchEvent(new Event('click', { bubbles: true }));
            }
        });
    });

    $('#searchPackingSlipBtn').on('click', function() {
        packingSlipTable.draw();
    });

    $('#createPacking').on('click', function(){
        let rows = $(packingSlipTable.$('input[type="checkbox"]').map(function(){
            return $(this).prop("checked") ? $(this).closest('tr') : null;
        }))

        // Get the selected rows
        //let rows = packingSlipTable.rows({ selected: true }).data();

        // Extract IDs from selected rows and store them in a list
        let ids = [];
        rows.each(function (index, row) {
            ids.push(packingSlipTable.row(row).data().id);
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