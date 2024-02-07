const Grids = function(config){
	
	const target  = config.target;
	const tObj    = $(`#${target}`);		
	const language = {
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
	}; 
	
	config.language = language;
	
	this.table   = tObj.DataTable(config);
	this.reload  = () =>{
		this.table.ajax.reload();
	}
	this.getRow  = (tObj) => {
		const data = this.table.row($(tObj)).data();
		return data;
	} 
	return this; 
}