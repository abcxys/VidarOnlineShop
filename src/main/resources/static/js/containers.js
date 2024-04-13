let containerTable;
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
            data: {
                searchType: $('#searchType').val(),
                text: $('#searchValue').val()
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
            'targets': -1,
            data: null,
            defaultContent: '<i class="fas fa-light fa-edit editContainer" style="cursor: pointer;"></i>'
        }]
    });
});