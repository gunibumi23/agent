const fnLogin = () => {
	Utils.ajax({
		url  : "/login/check",
		data : $("form").serialize(),
		success : function(res){
			const code = res.code;
			if(code === "SUCCESS") {
				location.href = res.redirect;
			}
		}
	});
	/*let form = $("form");
	form.attr("action","/login/check");
	form.attr("method","POST");
	form.submit();*/
}

$(document).ready( () =>  {
	$("#loginBtn").click(function(){
		fnLogin();
	});
})