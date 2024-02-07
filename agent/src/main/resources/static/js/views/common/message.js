 const Msgs = {	 
	alert : (msg,fn) => {
		alert(msg);
		if(fn && Utils.isFunction(fn)){
			fn();
		}
	},
	confirm : (msg,okFn,canFn) => {
		if(confirm(msg)){
			if(okFn && Utils.isFunction(okFn)) okFn();		
		}else{
			if(canFn && Utils.isFunction(canFn)) canFn();
		}
	}
 }