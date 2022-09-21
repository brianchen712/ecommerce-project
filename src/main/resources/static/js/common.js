function changeDateFormat(val) {
	if(val.length == 2){
		if (window.event.keyCode != 8) {
			// 不是按下刪除鑑
			var value = document.getElementById('displayExpiredDate').value
			document.getElementById('displayExpiredDate').value = value + "/";
		} else {
			// 按下刪除鍵
			var value = document.getElementById('displayExpiredDate').value
			document.getElementById('displayExpiredDate').value = value.substring(0,1);
		}
	}
	document.getElementById('expiredDate').value = document.getElementById('displayExpiredDate').value.replace('/','')
}

function changeDateFormat1(val) {
	if(val.length == 2){
		if (window.event.keyCode != 8) {
			// 不是按下刪除鑑
			var value = document.getElementById('expiredDate').value
			document.getElementById('expiredDate').value = value + "/";
		} else {
			// 按下刪除鍵
			var value = document.getElementById('expiredDate').value
			document.getElementById('expiredDate').value = value.substring(0,1);
		}
	}
}

function changeExpiredDate() {
	var value = document.getElementById('expiredDate').value;
	document.getElementById('expiredDate').value = value.replace('/','');
}

function openPopup(){
	var popup = document.getElementById("popup");
	// 新增class
    popup.classList.add("open-popup");
}

function closePopup(){
	let popup = document.getElementById("popup");
	// 移除class
    popup.classList.remove("open-popup");

}