

const Forms = function(params) {
  const target = params.target;
  const tForm = $(`#${target}`);
  this.defauiltData = params.defaultData || {};
  
  //유혀성 검증
  const vrules = params.vrules||{};        
  if(!Utils.isEmptyObject(vrules)) {
    for(let key in vrules) {
      let type = vrules[key]["type"];
      if(type) {
        if(type.indexOf("required") > -1){
          let obj = tForm.find(`#${key}`);
          if(obj.length > 0) {
            console.info(tForm.find(`label[for=${key}]`));
            tForm.find(`label[for=${key}]`).addClass("required");
          }
        }
      }else{
        delete vrules[key];
      }
    }
    this.validator = Validates.validate({
        target : target,
        rules : vrules
    });      
  }
    
  this.validate = () => {    
    return tForm.valid();    
  }

  //폼데이터 가져오기
  this.get = () => {
    if (tForm && tForm.length > 0) {
      return tForm.serializeObject();
    } else {
      return {};
    }
  };

  //폼 데이터 세팅
  this.set = (data, fn) => {
    if (!Utils.isEmptyObject(data) && tForm && tForm.length > 0) {
      for (let key in data) {
        const tObj = tForm.find(`[name=${key}],[id=${key}]`);
        this.setVal(tObj, Utils.nvl(data[key]));
      }

      if (fn && Utils.isFunction(fn)) {
        fn(data);
      }
    }
  };

  //폼 초기화
  this.clear = (fn) => {
    if (tForm && tForm.length > 0) {
      const errorCls = Validates.errorClass;
      
      this.validator.resetForm();
      
      tForm.find(`.${errorCls}`).each((idx,el)=>{
        $(el).removeClass(errorCls);
      });
      
      
      const tObjs = tForm.find(`input,textarea,select`);
      tObjs.each((idx, tObj) => {
        if (!Utils.isEmptyObject(this.defauiltData)) {
          this.setVal($(tObj), Utils.nvl(data[key]));
        } else {
          this.setVal($(tObj), "");
        }
      });
      if (fn && Utils.isFunction(fn)) {
        fn(data);
      }
    }
  };

  //value 세팅
  this.setVal = (tObj, val) => {
    if (tObj) {
      const tagNm = tObj.prop("tagName");
      if (tObj.length == 1) {
        const tagNm = tObj.prop("tagName");
        if (tagNm == "INPUT" || tagNm == "TEXTAREA" || tagNm == "SELECT") {
          tObj.val(val);
        }
      } else if (tObj.length > 1) {
        if (tagNm == "INPUT") {

        }
      }
    }
  }

  //폼 초기화
  this.init = () => {
    const tObjs = tForm.find(`input,textarea,select`);
    tObjs.each((idx, tObj) => {
      const id = $(tObj).prop("id");
      const name = $(tObj).prop("name");
      if (Utils.isEmpty(name)) {
        $(tObj).prop("name", id);
      }
    });
  };

  //폼 이벤트 바인딩
  this.event = (config) => {
    for (let key in config) {
      const tObjs = (tForm.find(`${key}`).length > 0) ? tForm.find(`${key}`) : tForm.find(`#${key}`);
      const tConf = config[key];
      const evt = Utils.nvl(tConf["evt"], "click");
      const deli = Utils.nvl(tConf["deli"]);
      const fn = tConf["fn"];

      if (tObjs.length > 0) {
        tObjs.each((idx, tObj) => {
          $(tObj).unbind(evt);
          $(tObj).on(evt, deli, (e) => {
            e.preventDefault();
            if (fn && Utils.isFunction(fn)) {
              fn(e.originalEvent);
            }
          });
        });
      }
    }
  };

  //찾기
  this.find = (sel) => {
    return tForm.find(sel);
  }

  //디폴트 데이터 세팅
  if (!Utils.isEmptyObject(this.defauiltData)) {
    this.set(this.defauiltData);
  }

  if ("event" in params) {
    const event = params.event || {};
    if (!Utils.isEmptyObject(event)) {
      this.event(event);
    }
  }

  this.target = tForm;
  this.init();
  return this;
}