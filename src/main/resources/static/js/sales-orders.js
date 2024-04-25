let salesOrdersTable;
$(function() {
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
            type : "post",
            url : "/packing/showOrders",
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
});