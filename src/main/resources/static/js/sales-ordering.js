let salesOrdersTable;
$(function() {
    salesOrdersTable = $('#itemOrderingTable').DataTable({
        "serverSide" : false,//分页，取数据等等的都放到服务端去
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
            method : "get",
            url : "/salesOrder/getSalesOrderItems",
            dataSrc : "data",
            data : function (d) {
                var param = {};
                param.draw = d.draw;
                param.startPos = d.start;
                param.pageSize = d.length;
                return param;
            },
            "error": function (data) {
                alert("error");
            }
        },
        columns : [
            {"data" : '', "bSortable" : false},
            {"data" : 'quantity', "bSortable" : true},
            {"data" : 'floorColorSize', "bSortable" : false},
            {"data" : 'floorColorSize', "bSortable" : true},
            {"data" : 'floorColorSize', "bSortable" : false}
        ],
        'columnDefs': [{
            'targets': 0,
            'searchable': false,
            'className': 'dt-body-center editor-delete',
            'render': function (data, type, full, meta){
                return '<button type="button"><i class="fa fa-trash"/></button>';
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
                return data.width +'\" ' + data.woodSpeciesName.split(" ")[data.woodSpeciesName.split(" ").length - 1]
                    + " " + data.colorName + " " + data.gradeAlias + " " + data.sqftPerCarton + " " + data.batchName;
            }
        }, {
            'targets': 4,
            'searchable': false,
            'className': 'dt-body-center',
            'render': function(data) {
                return data.price;
            }
        }]
    });

    salesOrdersTable.on('click', 'td.editor-delete button', function(e){
        let row = $(this).parent('tr');
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

    $('.datepicker').datepicker({
        format: 'yyyy-mm-dd',
        autoclose: true,
        todayHighlight: true
    }).on('changeDate', function(e) {
        // Handle the change date event for the start date picker
        console.log('Start date changed:', e.date);
    });

    // set the value of the datepicker default to be current datetime.
    $('#soDatepicker').datepicker("setDate", new Date());

    $('form#salesOrderForm').submit(function(event){
        event.preventDefault();
        console.log("Submitting sales order form!");

        let tableData = [];
        $('#itemOrderingTable tbody tr').each(function(){
           let rowData = {
               productId: salesOrdersTable.row(this).data().floorColorSize.id,
               quantity: salesOrdersTable.row(this).data().quantity
           };
           tableData.push(rowData);
        });

        let jsonData = {
            "address": $('#dealer_address').val(),
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
});