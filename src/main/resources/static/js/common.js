/**
 * 清空检索框的值
 */
function emptyValue(inputId) {
	$("#" + inputId).val('');
}

function emptyValueTumor(inputId){
	document.getElementById(inputId).options.selectedIndex = 0; //回到初始状态
	$("#" + inputId).selectpicker('refresh');
	window.location.reload(true);
}