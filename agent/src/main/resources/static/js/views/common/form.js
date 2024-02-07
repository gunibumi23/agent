const Forms =  {
	get : (tForm) => {
		tForm = $(`#${tForm}`);
		if(tForm && tForm.length > 0){
			return tForm.serializeObject();
		}else{
			return {};
		}
	},
	set : (tForm,data) => {
		tForm = $(`#${tForm}`);
		if(!Utils.isEmptyObject(data) && tForm && tForm.length > 0) {
			for(let key in data) {
				const tObj = tForm.find(`[name=${key}],[id=${key}]`);					
				Forms.setVal(tObj,Utils.nvl(data[key]));
			}	
		}
	},
	clear : (tForm,data) => {
		tForm = $(`#${tForm}`);
		if(tForm && tForm.length > 0) {
			const tObjs = tForm.find(`input,textarea,select`);
			tObjs.each((idx,tObj) => {
				if(!Utils.isEmptyObject(data)){
					Forms.setVal($(tObj),Utils.nvl(data[key]));
				}else{
					Forms.setVal($(tObj),"");
				}	
			});
		}
	},
	setVal : (tObj,val) => {
		if(tObj) {
			const tagNm = tObj.prop("tagName");
			if(tObj.length == 1 ) {
				const tagNm = tObj.prop("tagName");
				if(tagNm == "INPUT" || tagNm == "TEXTAREA" || tagNm == "SELECT"){
					tObj.val(val);
				}
			}else if(tObj.length > 1){
				if(tagNm == "INPUT"){
					
				}
			}
		}
	},
	init : (tForm) => {
		tForm = $(`#${tForm}`);
		const tObjs = tForm.find(`input,textarea,select`);
		tObjs.each((idx,tObj) => {
			const id = $(tObj).prop("id");
			$(tObj).prop("name",id);
		});
	},
	event : (tId,config) => {
		const tObj = $(`#${tId}`);
		if(tObj.length > 0) {
			for(let key in config) {
				const btnObj  = tObj.find(`#${key}`);
				const btnConf = config[key];
				const evt     = btnConf["evt"];
				const deli    = Utils.nvl(btnConf["deli"]);
				const fn      = btnConf["fn"];
				
				if(btnObj.length > 0) {
					btnObj.on(evt,deli,(e) => {
						e.preventDefault();
						if(fn && Utils.isFunction(fn)){
							fn(e,btnObj);
						}
					})
				}
			}
		}
	}
}