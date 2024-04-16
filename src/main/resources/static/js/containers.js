let containerTable;
let containerItemTable;
let tempDate;
function submitContainerSearchForm(e){
    e.preventDefault();
    //console.log("The search type is " + $('#searchType').val());
    //console.log("The search value is " + $('#searchValue').val());
    containerTable.draw();
}
$(function() {
    containerTable = $('#updateContainerTable').DataTable({
        "serverSide": true,
        "lengthChange": true,
        "order": [],
        "bProcessing": true,
        "autoWidth": false,
        "searching": false,
        "bInfo": false,
        "paging": false,
        ajax:{
            type: "post",
            url: "/container/getFilteredContainers",
            dataSrc: "data",
            data: function(){
                return {
                    searchType: $('#searchType').val(),
                    searchValue: $('#searchValue').val()
                }
            },
            "error": function (data) {
                alert("error");
            }
        },
        columns: [
            {"data" : 'id', "bSortable" : false},
            {"data" : 'containerNumber', "bSortable" : true},
            {"data" : 'billOfLandingNumber', "bSortable" : false},
            {"data" : 'estimatedArrivalDate', "bSortable" : false},
            {"data" : 'containerStatusId', "bSortable" : false},
            {"data" : '', "bSortable" : false}
        ],
        'columnDefs': [{
            'targets': 0,
            'visible': false
        },{
            'targets': 3,
            'className': "datepicker_cell",
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
            },
            'createdCell': function (td, cellData, rowData, row, col) {
                // Wrap the content of the cell with a <div> element and add a class for styling
                $(td).wrapInner('<div class="editable-date"></div>');

                // Add click event listener to the cell
                $(td).on('click', function(e) {
                    // Check if the cell is already being edited (datepicker is active)
                    if (!$(this).hasClass('editing')) {
                        // Add 'editing' class to indicate that the cell is being edited
                        $(this).addClass('editing');

                        // Get the current value of the cell
                        let currentValue = $(this).text().trim();

                        // Replace the text content of the cell with an input element
                        $(this).html('<input type="text" class="form-control datepicker" value="' + currentValue + '">');

                        // Initialize the datepicker plugin for the input element
                        $(this).find('.datepicker').datepicker({
                            format: 'yyyy-mm-dd',
                            autoclose: true,
                            todayHighlight: true
                        }).on('changeDate', function(e) {
                            // Update the DataTable cell data when the datepicker value changes
                            tempDate.time = e.date.getTime();
                            containerTable.cell(row, col).data(tempDate);
                            return e.format();
                        }).on('hide', function(e) {
                            // Remove 'editing' class when the datepicker is hidden
                            $(td).removeClass('editing');
                        });

                        // Show the datepicker
                        $(this).find('.datepicker').datepicker('show');
                    }
                });
            }
        }, {
            'targets': [1,2],
            'createdCell': createdCell
        },{
            'targets': 4,
            "render": function(data, type, full, meta) {
                // Render the select element with options
                let selectHtml = '<select aria-label="Status" class="containerStatusSelect selectpicker" data-live-search="true">';
                // Add options dynamically, you can fetch these from the server or define them statically
                selectHtml += '<option value="1" ' + (data === 1 ? 'selected' : '') + '>Created</option>';
                selectHtml += '<option value="2" ' + (data === 2 ? 'selected' : '') + '>On water</option>';
                selectHtml += '<option value="3" ' + (data === 3 ? 'selected' : '') + '>At port</option>';
                selectHtml += '<option value="4" ' + (data === 4 ? 'selected' : '') + '>On rail</option>';
                selectHtml += '<option value="5" ' + (data === 5 ? 'selected' : '') + '>Arrive</option>';
                // Add more options as needed...
                selectHtml += '</select>';
                return selectHtml;
            }
        },{
            'targets': -1,
            data: null,
            defaultContent: '<i class="fas fa-light fa-edit editContainer" style="cursor: pointer;"></i>'
        }]
    });
    containerTable.on('draw', function() {
        $('.containerStatusSelect').selectpicker();
    });
    containerTable.on('click', '.editContainer', function(){
        let rowData = containerTable.row($(this).closest('tr')).data();
        console.log('container id: ', rowData.id);
        console.log('Container name changed: ', rowData.containerNumber);
        console.log('BLN changed: ', rowData.billOfLandingNumber);
        console.log('ETA changed: ', rowData.estimatedArrivalDate);
        console.log('Status changes: ', rowData.containerStatusId);
        $.ajax({
            url: '/container/update',
            method: 'PUT',
            data: {
                id: rowData.id,
                containerNumber: rowData.containerNumber,
                billOfLandingNumber: rowData.billOfLandingNumber,
                estimatedArrivalDate: formatDate(new Date(rowData.estimatedArrivalDate.time)),
                containerStatusId: rowData.containerStatusId
            },
            success: function(response){
                bootboxAlertPrompt(response);
                setTimeout(function(){
                    location.reload();
                })
            },
            error: function(xhr, status, error){
                bootboxAlertError(xhr.responseText);
            }
        });
    });

    containerItemTable = $('#updateContainerItemTable').DataTable({
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
            url : "/container/getItemsByContainerId",
            dataSrc : "data",
            data : function () {
                return {
                    containerId: $('#editContainerId').val()
                };
            },
            "error": function (data) {
                alert("error");
            }
        },
        columns : [
            {"data" : 'id', "bSortable" : false},
            {"data" : 'containerId', "bSortable" : true},
            {"data" : 'productId', "bSortable" : false},
            {"data" : 'skid', "bSortable" : true},
            {"data" : 'box', "bSortable" : true},
            {"data" : '', "bSortable" : false}
        ],
        'columnDefs': [{
            'targets': 0,
            'visible': false
        },{
            'targets': 1,
            'visible': false
        },{
            'targets': 2,
            'render' : function(data){
                let baseUrl = window.location.origin;
                return '<a href="' + baseUrl + '/product/' + data + '" target="_blank">' + data + '</a>';
            }
        },{
            targets: -1,
            data: null,
            defaultContent: '<i class="fas fa-light fa-edit editContainerItem" style="cursor: pointer;"></i>'
        },{
            'targets': [3, 4],
            "createdCell": createdNumericCell,
            "render": function(data, type, full, meta) {
                // Render the select element with options
                return data;
            }
        }]
    });

    $('#updateContainerTable tbody').on('dblclick', 'tr', function(){
        let rowData = containerTable.row(this).data();
        console.log("Double click with data id = " + rowData.id);
        $('#editContainerModal').modal('show');
        $('#editContainerId').val(rowData.id)
        containerItemTable.columns.adjust().draw();
    })
});

// Function to pad single digit numbers with leading zero
function padZero(num) {
    return num < 10 ? '0' + num : num;
}

function formatDate(date) {
    console.log('The formatted date is ' + date);
    return (date.getFullYear()) + '-' + padZero(date.getMonth() + 1) + '-' + padZero(date.getDate());
}
const createdCell = function(cell) {
    let original;
    cell.setAttribute('contenteditable', true)
    cell.setAttribute('spellcheck', false)
    cell.addEventListener("focus", function(e) {
        original = e.target.textContent
    });
    cell.addEventListener("keypress", function(e) {
        let charCode = (e.which) ? e.which : e.keyCode;
        if (charCode < 48 || charCode > 57){
            e.preventDefault();
        }
    });
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

const createdNumericCell = function(cell) {
    createdCell(cell);
    cell.addEventListener("keypress", function(e) {
        let charCode = (e.which) ? e.which : e.keyCode;
        if (charCode < 48 || charCode > 57){
            e.preventDefault();
        }
    });

}

function closeEditContainerModal(){
    $("#editContainerModal").modal('hide');
}