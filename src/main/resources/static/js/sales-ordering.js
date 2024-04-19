let salesOrdersTable;
$(function() {
    salesOrdersTable = $('#itemOrderingTable').DataTable({
        "serverSide" : true,//分页，取数据等等的都放到服务端去
        "lengthChange": false,
        "info": false,
        "bProcessing" : true,
        "searching" : false,
        "autoWidth": false,
        "scrollY": "400px",
        "scrollX": true,
        "scrollCollapse": true,
        ajax : {
            method : "get",
            url : "/salesOrder/getItemsInCart",
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
            {"data" : 'floor', "bSortable" : true},
            {"data" : 'floor', "bSortable" : false}
        ],
        'columnDefs': [{
            'targets': 0,
            'searchable': false,
            'className': 'dt-body-center',
            'render': function (data, type, full, meta){
                return '<input type="checkbox" class="call-checkbox" name="checkbox-id" value="' + '">';
            }
        }, {
            'targets': 2,
            'searchable': false,
            'className': 'dt-body-center',
            'render': function(data) {
                return data.batchNumber;
            }
        }, {
            'targets': 3,
            'searchable': false,
            'className': 'dt-body-center',
            'render': function(data) {
                return data.price;
            }
        }]
    });

    $('#dealer').on('change', function(){
       console.log('Selected dealer changed!');
       $.ajax({
          url: '/salesOrder/getDealerInfoById',
          method: 'GET',
          data: {
              id: $('#dealer').val()
          },
           success : function(response){
                console.log(response);
                $('#dealer_name').val(response.address).change();
                $('#dealer_address').val(response.address).change();
           },
           error: function(xhr){
              bootboxAlertError(xhr.responseText);
           }
       });
    });

    $('#dealer_name').on('input change', function(){
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
    $('#soDatepicker').datepicker("setDate", new Date());
});