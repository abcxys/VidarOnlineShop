$(document).ready(function () {
    adjustBaseQuantity();
    retrieveInventory();
    retrieveOrderedQuantity();
    retrieveEstimatedArrivalDates();
    retrieveFactoryInventory();
});

$('#sqft_quantity').on("propertychange change keyup paste input",function(){
    adjustBaseQuantity();
});
$('#carton_quantity').on("propertychange change keyup paste input", function(){
    adjustBaseQuantity();
})

function adjustBaseQuantity() {
    let sqftPerCarton = $('#sqftPerCartonVal').val();
    let qtyLevel1 = eval($("#sqft_quantity").val() * 1.0000);

    qtyLevel1 = Math.ceil(qtyLevel1 / sqftPerCarton) * sqftPerCarton;

    let qtyLevel2 = eval($("#carton_quantity").val() * sqftPerCarton);
    let qtyLevel3 = eval($("#alt3itemquantity382").val() * 1.0000);
    if (qtyLevel1 === undefined || isNaN(qtyLevel1)) {
        qtyLevel1 = 0;
    }
    if (qtyLevel2 === undefined || isNaN(qtyLevel2)) {
        qtyLevel2 = 0;
    }
    if (qtyLevel3 === undefined || isNaN(qtyLevel3)) {
        qtyLevel3 = 0;
    }

    let tot = eval(qtyLevel1) + eval(qtyLevel2) + eval(qtyLevel3);
    $('#estimatedBase').html('Estimated actual ' + getNum(tot).toFixed(3) + ' SQFT');
    $('#txtEstimatedCarton').val(Math.ceil(getNum(tot)/sqftPerCarton));
}
function getNum(val) {
    if (isNaN(val)) {
        return 0;
    }
    return val;
}
function retrieveInventory() {
    let physicalInventoryBoxes = 100;
    let physicalInventorySqft = physicalInventoryBoxes * $('#sqftPerCartonVal').val();
    $('#inventoryQuantity').html('Physical inventory: ' + physicalInventoryBoxes +
        ' boxes, ' + physicalInventorySqft + ' sqft');
}
function retrieveOrderedQuantity() {
    let orderQuantity = 50;
    $('#orderedQuantity').html('Ordered quantity: ' + orderQuantity + ' boxes');
}
function retrieveEstimatedArrivalDates() {
    let estimatedArrivalDate = '2024-10-1';
    $('#estimatedArrivalDates').html('Estimated arrival dates: ' + estimatedArrivalDate);
}
function retrieveFactoryInventory() {
    let factoryInventoryBoxes = 200;
    let factoryInventorySqft = factoryInventoryBoxes * $('#sqftPerCartonVal').val();
    $('#factoryInventoryQuantity').html('Factory inventory: ' + factoryInventoryBoxes +
        ' boxes, ' + factoryInventorySqft + ' sqft');
}

$("#check_product_stock").on("click",function(){
    var currentClassName = $("#check-result").attr('class');

    $.ajax({
        url:"/product/checkProductStock",
        type:"POST",
        data: {
            productId: $('#productIdVal').val(),
            quantity: $('#txtEstimatedCarton').val(),
        },
        success:function(response){
            console.log(response.Status);
            var className='success';
            if(response.Status==0){//unkown
                className = 'unknown';
            }
            else if(response.Status==2){
                className = 'failure';
            }
            else if(response.Status==1){
                className='success';
            }
            $("#check_product_stock").removeAttr('disabled');
            $("#check-result").text(response);

            $("#check-result").removeClass(currentClassName);
            $("#check-result").addClass('send-request ' + className);
            console.log('success');
        },
        error: function (xhr) {
            var errorMessage = xhr.responseText ? xhr.responseText : 'Unknown error';
            $("#check_product_stock").removeAttr('disabled');
            $("#check-result").text(errorMessage);
            $("#check-result").removeClass(currentClassName);
            $("#check-result").addClass("send-request failure");
        }
    });
})