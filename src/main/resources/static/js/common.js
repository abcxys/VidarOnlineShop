/**
 * 清空检索框的值
 */
function emptyValue(inputId) {
	$("#" + inputId).val('');
}

function emptyValueSelector(inputId){
	document.getElementById(inputId).options.selectedIndex = 0; //回到初始状态
	$("#" + inputId).selectpicker('refresh');
}

function bootboxAlertError(message) {
	var dialog = bootbox.dialog({
		title: '<i class="fa fa-fw fa-times-circle" style="color: #dd4b39;"></i>&nbsp;' + 'Error' +'',
		message: "<p>" + message + "</p>",
		buttons: {
			ok: {
				label: 'Close',
				className: 'btn-danger',
				callback: function() {}
			}
		}
	});
}