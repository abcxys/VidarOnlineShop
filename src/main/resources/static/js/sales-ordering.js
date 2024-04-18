let salesOrdersTable;
$(function() {
    salesOrdersTable = $('#itemOrderingTable').DataTable({
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
                return data.batch_id;
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
});