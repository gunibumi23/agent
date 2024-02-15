let _form = null;

/**
 * 로그인
 */
const fnLogin = () => {
  
  if(_form.validate()){
    Utils.ajax({
        url: "/login/check",
        data: _form.get(),
        success: function(res) {
          const code = res.code;
          if (code === "SUCCESS") {
            location.href = Utils.getUrl(res.redirect);
          }else{
            alert(res.message)
          }
        }
      });
    }
}

$(document).ready(() => {
  //폼정의
  _form = new Forms({
    "target"   : "login",
    "vrules" : {
        userCd : {type : "required"},
        userPw : {type : "required"}      
    },
    "event": {
      "loginBtn": {
        fn: (e) => {
          fnLogin();
        }
      },
      "input": {
        evt: "keyup",
        fn: (e) => {
          if (e.key == "Enter") {
            const target = $(e.target);
            const name = target.prop("name");
            if (name === "userCd") {
              $("[name=userPw]").focus();
            } else if (name === "userPw") {
              fnLogin();
            }
          }
        }
      }
    }
  });
  $("#userCd").focus();
})