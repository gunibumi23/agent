const Utils = {
	ajax : (params) => {
		const url   = params.url;
		if(url) {
			const config = $.extend({},{
				type : "POST",
				dataType : "json",
				async : true,
				error : (xhr, status, error) => {
					console.info(arguments);	
				}
			},params);						
			return $.ajax(config);
		}
	}
} 