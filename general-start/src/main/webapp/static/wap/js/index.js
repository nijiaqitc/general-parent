function searchDoc(){
	if($(".searchText").val()==""){
		alert("请输入搜索关键字");
		return;
	}
	$("#isearch").submit();
//	var searchValue=$(".searchText").val().replace(/(^\s*)|(\s*$)/g,'').replace(/\s+/g,",");
//	loadDocs(searchValue,1,10);
}

function loadDocs(value,p,s){
	$.ajax({
		url:"wap/searchDoc",
		data:{
			searchValue:value,
			page:p,
			size:s
		},
		type:"get",
		success:function(data){
			console.info(data.doc)
		}
	})
}