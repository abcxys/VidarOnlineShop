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

/**
 * Alert info boot box
 */
function bootboxAlertPrompt(message) {
	var dialog = bootbox.dialog({
	    title: '<i class="fa fa-fw fa-info-circle" style="color: #00c0ef;"></i>&nbsp;'+ 'Alert' +'',
	    message: "<p>" + message + "</p>",
	    buttons: {
	        ok: {
	            label: 'Close',
	            className: 'btn-info',
	            callback: function() {
					location.reload();
				}
	        }
	    }
	});
}

/**
 * Error info boot box
 */
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

$.fn.datepicker.defaults.format = "yyyy-mm-dd";