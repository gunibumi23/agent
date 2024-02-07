const Utils = {
	ajax : (params) => {
		const url   = params.url;
		if(url) {			
			const type     = Utils.nvl(params.type    ,"POST");
			const dataType = Utils.nvl(params.dataType,"json");
			const async    = Utils.nvl(params.async   ,true);
			const sendType = Utils.nvl(params.sendType,"json");
			let   data     = params.data||{};
			
			if(!Utils.isEmptyObject(data)){
				if(sendType === "json") {
					//data = JSON.stringify(data);
				}
			}
			
			let config  = {
				url,
				type,
				dataType,
				async,
				data,
				error : (xhr, status, error) => {
					let resData  = xhr.responseJSON;
					if(typeof resData == "string"){
						resData = JSON.parse(resData);
					}
					const message = Utils.nvl(resData.message,"오류가 발생하였습니다.\n관리자에게 문의해주세요.");
					if(message === "SESSION_EXPIRED"){
						Msgs.alert("세션이 만료되었습니다. 다시로그인 해주시기 바랍니다.",() => {
							Utils.loadMask(false);
							location.href = '/login'
						});
					}else{
						Msgs.alert(message,() => {
							Utils.loadMask(false);							
						});
					}
					console.info(resData);	
				},
				beforeSend: function(xhr) {
					Utils.loadMask(true);
				},
				complete: function () {
					Utils.loadMask(false);
				}
			}
			
			if(params.success && Utils.isFunction(params.success)) {
				config.success = params.success;
			}
			
			const res = $.ajax(config);
			
			let resData = {};
			if(!async) {
				res.responseJSON;
			}
			return resData;
		}
	},
	isEmptyObject : (data) =>{
		return $.isEmptyObject(data);
	},
	isEmpty : (val) => {
		if ( typeof val === "undefined" ||
	         val === null ||
	         val === "" ||
	         val === "null" ||
	         val.length === 0 ||
	         (typeof val === "object" && !Object.keys(val).length)
	    ){
	        return true;
	    }else{
			return false;
		}
	},
	isFunction : (fn) => {
		return $.isFunction(fn);
	},
	nvl : (val,repVal) => {
		if(Utils.isEmpty(val)) {
			if(Utils.isEmpty(repVal)) {
				return "";
			}else{
				return repVal;
			}
		}else{
			return val;
		}
	},
	loadMask : (flag) => {
		const obj = $(".agent-spinner");
		if(flag === true){
			if(!obj.hasClass("show")) obj.addClass("show");
		}else{
			if(obj.hasClass("show")) $(".agent-spinner").removeClass("show");	
		}
	}
} 


