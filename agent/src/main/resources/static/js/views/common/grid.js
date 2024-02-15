
/*
new DataTable('#example', {
    ajax: '../ajax/data/objects_salary.txt',
    columns: [
        {
            data: 'name'
        },
        {
            data: 'position',
            render: function (data, type) {
                if (type === 'display') {
                    let link = 'https://datatables.net';
 
                    if (data[0] < 'H') {
                        link = 'https://cloudtables.com';
                    }
                    else if (data[0] < 'S') {
                        link = 'https://editor.datatables.net';
                    }
 
                    return '<a href="' + link + '">' + data + '</a>';
                }
 
                return data;
            }
        },
        {
            className: 'f32', // used by world-flags-sprite library
            data: 'office',
            render: function (data, type) {
                if (type === 'display') {
                    let country = '';
 
                    switch (data) {
                        case 'Argentina':
                            country = 'ar';
                            break;
                        case 'Edinburgh':
                            country = '_Scotland';
                            break;
                        case 'London':
                            country = '_England';
                            break;
                        case 'New York':
                        case 'San Francisco':
                            country = 'us';
                            break;
                        case 'Sydney':
                            country = 'au';
                            break;
                        case 'Tokyo':
                            country = 'jp';
                            break;
                    }
 
                    return '<span class="flag ' + country + '"></span> ' + data;
                }
 
                return data;
            }
        },
        {
            data: 'extn',
            render: function (data, type, row, meta) {
                return type === 'display'
                    ? '<progress value="' + data + '" max="9999"></progress>'
                    : data;
            }
        },
        {
            data: 'start_date'
        },
        {
            data: 'salary',
            render: function (data, type) {
                var number = DataTable.render
                    .number(',', '.', 2, '$')
                    .display(data);
 
                if (type === 'display') {
                    let color = 'green';
                    if (data < 250000) {
                        color = 'red';
                    }
                    else if (data < 500000) {
                        color = 'orange';
                    }
 
                    return `<span style="color:${color}">${number}</span>`;
                }
 
                return number;
            }
        }
    ]
});

이벤트
function eventFired(type) {
    let n = document.querySelector('#demo_info');
    n.innerHTML +=
        '<div>' + type + ' event - ' + new Date().getTime() + '</div>';
    n.scrollTop = n.scrollHeight;
}
 
new DataTable('#example')
    .on('order.dt', () => eventFired('Order'))
    .on('search.dt', () => eventFired('Search'))
    .on('page.dt', () => eventFired('Page'));
    
    //
    createdRow: (row, data, index) => {
        if (data[5].replace(/[\$,]/g, '') * 1 > 150000) {
            row.querySelector(':nth-child(6)').classList.add('highlight');
        }
    }


*/
const gridLanguage = {
  "emptyTable": "검색된 데이터가 없습니다.",
  "lengthMenu": "페이지당 _MENU_ 개씩 보기",
  "info": "현재 _START_ - _END_ / _TOTAL_건",
  "infoEmpty": "검색된 데이터가 없습니다.",
  "infoFiltered": "( _MAX_건의 데이터에서 검색됨 )",
  "search": "검색: ",
  "zeroRecords": "검색된 데이터가 없습니다.",
  "loadingRecords": "로딩중...",
  "processing": "잠시만 기다려 주세요...",
  "paginate": {
    "next": "다음",
    "previous": "이전"
  },
  "select": {
    "rows": {
      "_": "%d개행 선택",
      "0": "",
      "1": ""
    }
  }
};

Object.assign(DataTable.defaults, {
    language: gridLanguage,
    info: true,
    scrollCollapse: true,
    scrollY: '55vh',
    ordering: true,
    searching: true,
    paging: false,
    search: {
        return: true
    }
});


const Grids = function(params) {
  // stateSave  => reload 나 그런거 할때 상태 저장
  let config = {};
  //설정 엎어쓰기
  for (let key in config) {
    if (!Utils.isEmpty(params[key])) {
      config[key] = params[key];
    }
  }

  if (!Utils.isEmpty(params.paging)) {
    const paging = params.paging;
    config.paging = paging;
    if (config.paging) {
      config.lengthChange = true;
    }
  }

  const target = params.target;
  const tObj = $(`#${target}`);

  const columns = [];
  const columnDefs = [];

  const tHead = $(`<thead></thead>`);
  const tTr = $(`<tr></tr>`);
  
  let idx = 0;
  
  if(params.check){ 
    columnDefs.push({orderable: false,searchable : false,className: 'select-checkbox',targets: idx});
    config["select"] = {style: 'os',selector: 'td:first-child'};    
    idx++;
  } 
  
  params.columns.forEach((info, idx1) => {
    const tTh = $(`<th>${info.label}</th>`);
    const col = { "data": info.key };
    const colDef = {};
    //체크 박스일때
    if(params.check && idx1 == 0){
      tTr.append($(`<th></th>`));
      columns.push({"data" : null,"defaultContent": ""});
    }
    if (info.align === "center") {
      colDef.target = idx;
      colDef.className = "dt-body-center";
    } else if (info.align === "right") {
      colDef.target = idx;
      colDef.className = "dt-body-right";
    }

    if (!Utils.isEmpty(info.hidden)) {
      //혹시 텍스로 들어올수도있어서			
      const hidden = JSON.parse(info.hidden + "");
      if (hidden) {
        colDef.target = idx;
        colDef.visible = false;
      }
    }

    if (!Utils.isEmptyObject(colDef)) {
      columnDefs.push(colDef);
    }
    columns.push(col);
    tTr.append(tTh);
    
    idx++;
  });

  const tBody = $(`<tbody></tbody>`);

  tHead.append(tTr);
  tObj.append(tHead);
  tObj.append(tBody);

  // column 시작
  config.columns = columns;

  // column def 시작
  if (columnDefs.length > 0) {
    //searchable: false
    config.columnDefs = columnDefs;
  }

  //일단은 기본은 ajax;
  if (params.url) {
    config.ajax = {
      url: Utils.getUrl(params.url),
      type: Utils.nvl(params.type, "GET"),
      dataSrc: Utils.nvl(params.dataSrc, "list"),
      data : (!Utils.isEmptyObject(params.data)) ? params.data: {}, 
      beforeSend : () => {
        Utils.loadMask(true);
      },
      complete: () => {
        Utils.loadMask(false);
      },
      error : (xhr, status, error) => {
        let resData  = xhr.responseJSON;
        console.info(xhr)
        if(resData){
          if(typeof resData == "string"){
            resData = JSON.parse(resData);
          }
          const message = Utils.nvl(resData.message,"오류가 발생하였습니다.\n관리자에게 문의해주세요.");           
          if(message === "SESSION_EXPIRED"){
            Msgs.alert("세션이 만료되었습니다. 다시로그인 해주시기 바랍니다.",() => {
              Utils.loadMask(false);
              location.href = Utils.getUrl('/login');
            });
          }else{
            Msgs.alert(message,() => {
              Utils.loadMask(false);              
            });
          }
        }else{
          Utils.loadMask(false);
        }
      }
    };
  }

  //행 선택
  config.select = { "style": "single" };
  if (!Utils.isEmpty(params.multiple)) {
    const multiple = JSON.parse(params.multiple + "");
    if (multiple) {
      config.select.style = "multi";
    }
  }
  /*
  config["buttons"] = ['searchBuilder'];
  config["dom"] = 'Bfrtip'*/
  config['order']  =  [ [ 1, 'asc' ] ];

console.info(config)
  this.table = tObj.DataTable(config);

  /*new $.fn.dataTable.SearchBuilder(this.table, {});
  this.table.searchBuilder.container().prependTo(this.table.table().container());*/

  if (params.click && Utils.isFunction(params.click)) {
    tObj.on("click", "tbody tr", (e) => {
      const data = this.getRow(e.target);
      params.click(e, data, this);
    });
  }

  //공통 메소드 시작
  this.reload = (fn) => {    
    this.table.ajax.reload(() => {
      if (fn) {
        fn();
      }
    });
  }
  this.getRow = (tObj) => {
    const data = this.table.row($(tObj)).data();   
    return data;
  }
  this.getRowCnt = () => {
    this.table.data().count();
  }
  
  if(params.reload) {
    const reloadInfo = params.reload;
    if(!Utils.isEmptyObject(reloadInfo)) {
      if(reloadInfo.btn && reloadInfo.btn.length > 0) {
        reloadInfo.btn.on("click",() => {
          if(reloadInfo.fn && Utils.isFunction(reloadInfo.fn)) {
            this.reload()
          }else{
            this.reload(() => {
              fn();
            });
          } 
        });
      }
    }
  }
  
  return this;
}


/**
 * 
 * 
 
 function filterGlobal(table) {
    let filter = document.querySelector('#global_filter');
    let regex = document.querySelector('#global_regex');
    let smart = document.querySelector('#global_smart');
 
    table.search(filter.value, regex.checked, smart.checked).draw();
}
 
function filterColumn(table, i) {
    let filter = document.querySelector('#col' + i + '_filter');
    let regex = document.querySelector('#col' + i + '_regex');
    let smart = document.querySelector('#col' + i + '_smart');
 
    table.column(i).search(filter.value, regex.checked, smart.checked).draw();
}
 
let table = new DataTable('#example');
 
document.querySelectorAll('input.global_filter').forEach((el) => {
    el.addEventListener(el.type === 'text' ? 'keyup' : 'change', () =>
        filterGlobal(table)
    );
});
 
document.querySelectorAll('input.column_filter').forEach((el) => {
    let tr = el.closest('tr');
    let columnIndex = tr.getAttribute('data-column');
 
    el.addEventListener(el.type === 'text' ? 'keyup' : 'change', () =>
        filterColumn(table, columnIndex)
    );
});

 */
