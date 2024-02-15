let _grid = null;
let _form = null;

$(document).ready(function() {
  
  _form = new Forms({
    "target": "userForm"
    , "vrules": {
      userCd : { type : "required"},
      userNm : { type : "required"},
      userSts: { type : "required"},
      userPh : { type : "phone"}
    }
    , "event": {
      "chkIdBtn": {
        fn: (e) => {
          console.info(e);
        }
      },
      "newBtn": {
        fn: (e) => {
          fnAddUser();
        }
      },
      "canBtn": {
        fn: (e) => {
          //폼 초기화
          fnFormHandler(true);
        }
      },
      "saveBtn": {
        fn: (e) => {
          fnRowSave();
        }
      },
      "delBtn": {
        fn: (e) => {
          fnAddUser();
        }
      }
    }
  });

  let columns = [{ key: "userCd", label: "ID", align: "center" }
    , { key: "userNm", label: "이름", align: "left" }
    , { key: "userSts", label: "상태", align: "center" }
    , { key: "userPh", label: "휴대폰", align: "center" }
    , { key: "updatedDt", label: "최종수정일시", align: "center" }
    , { key: "updatedNm", label: "최종수정자", align: "left" }
  ];

  _grid = new Grids({
    target: "example",
    url: '/user/list',
    data : {delYn : "N"},
    columns: columns,
    paging: false,
    reload: {
      btn: $("#reloadBtn"),
      fn: () => {
        fnFormHandler(true);
      }
    },
    click: (e, data, _grid) => {
      fnRowSelect(data["userCd"]);
    }
  });

  //폼 초기화
  fnFormHandler(true);
  /*const flatList = [
    { id: 1, name: 'Node 1', parentId: null },
    { id: 2, name: 'Node 2', parentId: 1 },
    { id: 3, name: 'Node 3', parentId: 1 },
    { id: 4, name: 'Node 4', parentId: 2 },
    { id: 5, name: 'Node 5', parentId: 2 },
    { id: 6, name: 'Node 6', parentId: 4 },
    { id: 7, name: 'Node 7', parentId: null },
    { id: 8, name: 'Node 8', parentId: 7 }
];
  const _trees  = new Trees({list : flatList});
  console.info(_trees);*/
});

/**
 * 폼 초기화
 */
const fnFormHandler = (disabled, data) => {
  _form.find("input,select").attr("disabled", disabled);
  _form.find("#chkIdBtn").hide();
  _form.find("button:visible").attr("disabled",true);  
  //비활성화
  if (disabled) {
    //폼 초기화
    _form.find("#newBtn").attr("disabled",false);
    _form.clear();
  } else {
    //데이터 세팅
    _form.set(data);
    _form.find("#saveBtn,#canBtn").attr("disabled",false);
    //저장된데이터
    if (data.saveFlag === "N") {
      _form.find("#chkIdBtn").show();
      _form.find("#delBtn,#newBtn").attr("disabled",true);
      _form.find("#userCd").attr("disabled", false);
    } else {
      _form.find("#delBtn").attr("disabled",false);
      _form.find("#userCd").attr("disabled", true);
    }
  }
}


/**
 * 사용자 상세
 */
const fnAddTest = () => {
  Utils.ajax({
    url: `/user`,
    type: "PUT",
    success: function(res) {
      _grid.reload();
    }
  });
}

/**
 * 사용자 상세
 */
const fnRowSelect = (userCd) => {
  Utils.ajax({
    url: `/user/${userCd}`,
    type: "GET",
    success: function(res) {
      const data = res.data;
      if (!Utils.isEmptyObject(data)) {
        fnFormHandler(false, data);
      }
    }
  });
}

/**
 * 사용자 정보 저장
 */
const fnRowSave = (delYn) => {
  if(!_form.validate()){
    return;
  }
  confirm("저장하시겠습니까?", () =>{
    console.info(arguments);
    Utils.ajax({
      url: `/user/${userCd}`,
      type: "PUT",
      data: _form.get(),
      success: function(data) {
        if (!Utils.isEmptyObject(data)) {
          _form.set(data);
        }
      }
    });
  })
};

/**
 * 사용자 신규
 */
const fnAddUser = () => {
  //폼 초기화
  fnFormHandler(false, { "saveFlag": "N" });
}

