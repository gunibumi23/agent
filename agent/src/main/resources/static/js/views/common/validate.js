$.extend($.validator.messages, {
  required: "필수 입력값 입니다.",
  remote: "유효하지 않은 입력값입니다.",
  email: "유효하지 않은 E-Mail주소입니다.",
  url: "유효하지 않은 URL입니다.",
  date: "유효하지 않은 날짜입력값 입니다.",
  dateISO: "올바른 날짜(ISO)를 입력하세요.",
  number: "유효한 숫자가 아닙니다.",
  digits: "숫자만 입력 가능합니다.",
  creditcard: "신용카드 번호가 바르지 않습니다.",
  equalTo: "같은 값을 다시 입력하세요.",
  extension: "올바른 확장자가 아닙니다.",
  maxlength: $.validator.format("{0}자를 넘을 수 없습니다. "),
  minlength: $.validator.format("{0}자 이상 입력하세요."),
  rangelength: $.validator.format("문자 길이가 {0} 에서 {1} 사이의 값을 입력하세요."),
  range: $.validator.format("{0} 에서 {1} 사이의 값을 입력하세요."),
  max: $.validator.format("{0} 이하의 값을 입력하세요."),
  min: $.validator.format("{0} 이상의 값을 입력하세요.")
});


/*$.validator.setDefaults({
    onkeyup:false,
    onclick:false,
    onfocusout:false,
    showErrors: function(map,list) {
        console.info(map);
        if(this.numberOfInvalids()) {
            alert(list[0].message);
        }
    }
});*/
/*
           depends: function(element) {
             return $("#contactform_email:checked")
           }
출처: https://blueamor.tistory.com/907 [51%의 가능성:티스토리]
 */

/*
$.validator.addMethod(
    "extraMethod",
    function (value, element) {
        if(value != "") {
            return true;
        } else {
            return false;
        }
    },
    "값이 없습니다."
);
*/


$.validator.addMethod("regex", (val, el, regexp) => {
  if(val){
    return regexp.test(val);
  }else{
    return true;
  }
}, (regexp, el) => {
  console.info("에러 오브젝트 => ",el);
  return "유효한 값이 아닙니다.";
});

/**
 * 유효성 검증
 */
const Validates = {
  errorClass : "validError",
  validate: (params) => {
    const tForm  = (typeof params.target == "object") ? params.target : $(`#${params.target}`);
    const mrules = params.rules||{};
    const rules  = {};
    for(let key in mrules){
      const rule = mrules[key];
      const types = rule.type;
      const typeArr = types.split(";");      
      typeArr.forEach((type,idx) => {
          Validates.getDefValidate(type,rule);
      });
      delete rule.type;
    }    
    
    console.info(mrules);
    
    if (tForm && tForm.length > 0) {
      return tForm.validate({
        errorClass: Validates.errorClass,
        rules: mrules,
        errorPlacement: function(err, el) {
          if (el.is(":radio") || el.is(":checkbox") ) {
            el.parent().after(err);
          } else if(el.parent().find(".input-group-append").length > 0) {
            el.parent().after(err);
          }else {
            el.after(err);
          }
        }
      });
    }
    return null;
  },
  getDefValidate: (type,rule) => {    
    switch (type) {      
      case "phone":
        rule.regex = /^[0-9]{3}-[0-9]{4}-[0-9]{4}$/;
      break;
      case "bizno":
        rule.regex = /^[0-9]{3}-[0-9]{2}-[0-9]{5}$/;        
      break;
      default :
        rule[type] = true; 
      break;
    }
  }
}