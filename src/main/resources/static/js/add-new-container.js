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
    $('#productQuantities tbody').append(`
        <tr>
            <td>
                <select class="selectpicker form-control productSelector" data-live-search="true">
                </select>
            </td>
            <td>
                <input type="number" class="form-control" name="quantity" required>
            </td>
            <td>
                <input type="number" class="form-control" name="box" required>
            </td>
        </tr>
    `);
    populateSelect($('.productSelector').last());
    //$(".productSelector").last().append($('#mySelect').clone().children());
    //$('.productSelector').last().val("")
    //$(".productSelector").last().selectpicker('refresh');
});
$(document).ready(function(){
    //populateSelect($('#mySelect'));
});