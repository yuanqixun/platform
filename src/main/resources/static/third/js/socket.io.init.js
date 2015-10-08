var io;
$(function () {
	var push = {
		"enable": false,
		"url": "toget_push_url",//"http://127.0.0.1:7076",
		"appurl": "toget_app_url"//"http://localhost:8080/infoship"
	}
	if(push.enable && push.url){
		io = io.connect(push.url,push);
	}else{
		io.on = function(){
			//console.log("io's on function not be defined.");
		}
	}

	if ($.isNotBlank(io)) {
		//监听系统通知消息
		io.on('socket.infoship.event_notification', function (data) {
			if (data && data.message) {
				$.jGrowl(data.message, {
					header: '系统通知',
					position: 'bottom-right',
					sticky: true
				});
			}
		});
	}
});