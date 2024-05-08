function populateSelect(node) {
    $.ajax({
        url: '/productApi/getActiveProductDict',
        method: 'GET',
        success: function(response) {
            // Clear existing options
            node.empty();
            node.append($('<option>', {
                value: "",
                text: "Select product"
            }))
            // Populate select with received options
            response.forEach(function(option) {
                node.append($('<option>', {
                    value: option.id,
                    text: option.width +'\" ' + option.woodSpeciesName.split(" ")[option.woodSpeciesName.split(" ").length - 1]
                        + " " + option.colorName + " " + option.gradeAlias + " " + option.sqftPerCarton + " " + option.batchName
                }));
            });

            // Refresh the selectpicker to reflect changes
            node.selectpicker('refresh');
            node.hide();
        },
        error: function(xhr, status, error) {
            console.error(error);
        }
    });
}

$('#addProductBtn').on('click', function() {
    // Append a new row to the product quantities table
    $('#productQuantitiesForm tbody').append(`
        <tr>
            <td>
                <select class="selectpicker form-control productSelector" data-live-search="true">
                </select>
            </td>
            <td>
                <input type="number" class="form-control" name="quantity">
            </td>
            <td>
                <input type="number" class="form-control" name="box">
            </td>
        </tr>
    `);
    populateSelect($('.productSelector').last());
});

function serializeTableData() {
    let tableData = [];
    $('#productQuantitiesForm tbody tr').each(function(){
        let rowData = {
            productId: $(this).find('td:eq(0) select').val(),
            skid: $(this).find('td:eq(1) input').val(),
            box: $(this).find('td:eq(2) input').val()
        };
        tableData.push(rowData);
    });
    console.log(tableData);
    $('#containerItems').val(JSON.stringify(tableData));
}

$('form#containerForm').submit(function(event){
    event.preventDefault();

    let tableData = [];
    $('#productQuantitiesForm tbody tr').each(function(){
        let rowData = {
            productId: $(this).find('td:eq(0) select').val(),
            skid: $(this).find('td:eq(1) input').val(),
            box: $(this).find('td:eq(2) input').val()
        };
        tableData.push(rowData);
    });

    let jsonData = {
        "containerNumber": $('#containerNumber').val(),
        "billOfLandingNumber": $('#billOfLandingNumber').val(),
        "shippingCompany": $('#shippingCompany').val(),
        "freightForwarder": $('#freightForwarder').val(),
        "portName": $('#portName').val(),
        "containerItems": tableData,
        "estimatedArrivalDate": $('#estimatedArrivalDate').datepicker('getDate')
    };

    $.ajax({
        url: $(this).attr('action'),
        method: $(this).attr('method'),
        data: JSON.stringify(jsonData),
        contentType: "application/json",
        success: function(response){
            console.log('success');
            bootboxAlertPrompt('Container successfully added');
        },
        error: function(xhr, status, error){

        }
    });
});

$(document).ready(function(){
    //populateSelect($('#mySelect'));
    $('.datepicker').datepicker({
        format: 'yyyy-mm-dd',
        autoclose: true,
        todayHighlight: true
    }).on('changeDate', function(e) {
        // Handle the change date event for the start date picker
        console.log('Start date changed:', e.date);
    });
});