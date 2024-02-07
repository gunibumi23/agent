let grid = null;

$(document).ready(function(){
	
	//폼 초기화
	Forms.init("userForm");
	
	grid = new Grids({
		target : "example",
		lengthChange : true,
		searching: true,
		ordering:true,
		info:true,
		/*processing: true,
    	serverSide: true,*/
		//pageResize: true,
		//paging:true,
		paging: false,
	    scrollCollapse: true,
	    scrollY: '55vh',
		ajax: {
	        'url':'/users/list',
	        'type' : "GET", 
	        'dataSrc':'list',
	        'error' : (a,b,c,d,e)=>{
				console.info(a,b,c,d,e);
			}
	    },	    
	    columns: [
	        {"data": "userCd"},
	        {"data": "userNm"},
	        {"data": "userSts"}, 
	        {"data": "userPh"},
	        {"data": "updatedDt"},
	        {"data": "updatedNm"}
	    ],
	    "language": {
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
                    "_": "%개행 선택",
                    "0": "",
                    "1": ""
                } 
	        }
	    },
	    /*columnDefs:[
			{targets:0,visible:false}
		],*/
		/*lengthMenu : [10,20],
		diplayLength:20,*/
		select: {
	          style: 'single'
	    }
	});
	
	console.info(grid.table)
	
	
	//이벤튼 핸들러
	Forms.event("userCont",{
		"chkIdBtn"  : {evt : "click", 
						fn : (e) => {
							console.info(e);
						}},
		"reloadBtn" : {evt : "click", 
						fn : (e) => {
							grid.reload(() => {
								Forms.clear("userForm");
							});
						}},
		"example" 	: {evt : "click", 
					  deli : 'tbody tr',
						fn : (e,tObj) => {							
							const data = grid.getRow(e.currentTarget);									
							fnRowSelect(data["userCd"]);
						}},
	});
	
	/**
	            pageLength: 3,
                pagingType : "full_numbers",
                bPaginate: true,
                bLengthChange: true,
                lengthMenu : [ [ 1, 3, 5, 10, -1 ], [ 1, 3, 5, 10, "All" ] ],
                responsive: true,
                bAutoWidth: false,
                processing: true,
                ordering: true,
                bServerSide: true,
                searching: false,
           		ajax : {
                    "url":"/getUserList.do",
                    "type":"POST",
                    "data": function (d) {
                        d.userStatCd = "NR";
                    }
                },
                sAjaxSource : "/getUserList.do?userStatCd=NR&columns="+columns,
                sServerMethod: "POST",
                columns : [
                    {data: "email"},
                    {data: "fullNmKr"},
                    {data: "userStatCd"},
                    {data: "superUser"}
                ],
                
                columnDefs : [
                    {
                        "targets": [0,1,3],
                        "visible": true,
                    },
                    {
                        "targets": 2,
                        "visible": false,
                    },
                ]
 */
});


const fnRowSelect = (userCd) => {
	Utils.ajax({
		url  : `/users/${userCd}`,
		type : "GET",
		success : function(res){
			const data = res.data;
			if(!Utils.isEmptyObject(data)){
				Forms.set("userForm",data);	
			}
		}
	});
}

const fnRowSave = () => {
	let data = $("userForm").get();
	Forms.get("userForm");
		
	Utils.ajax({
		url  : `/users/${userCd}`,
		type : "PUT",
		data : data ,
		success : function(data){
			if(!Utils.isEmptyObject(data)){
				Forms.set("userForm",data);	
			}
		}
	});
}

