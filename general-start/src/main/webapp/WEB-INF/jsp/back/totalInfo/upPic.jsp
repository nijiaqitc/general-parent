<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link href="${resPath }/chajian/cropper-1.0.0/assets/css/font-awesome.min.css" rel="stylesheet" type="text/css" />
<link href="${resPath }/chajian/cropper-1.0.0/assets/css/bootstrap.min.css" rel="stylesheet" type="text/css" />
<link href="${resPath }/chajian/cropper-1.0.0/assets/css/tooltip.min.css" rel="stylesheet" type="text/css" />
<link href="${resPath }/chajian/cropper-1.0.0/dist/cropper.css" rel="stylesheet" type="text/css" />
<link href="${resPath }/chajian/cropper-1.0.0/demo/css/main.css" rel="stylesheet" type="text/css" />
</head>
<body style="background-color: #f5f5f5;">
  <!-- Content -->
  <div class="container" style="width: 580px;height: 400px;">
    <div class="row" style="height: 400px;">
      <div class="col-md-9" style="width: 400px;height: 400px;">
        <!-- <h3 class="page-header">Demo:</h3> -->
        <div class="img-container">
          <img src="${resPath }/chajian/cropper-1.0.0/assets/img/picture.jpg" alt="Picture">
        </div>
      </div>
      <div class="col-md-3">
        <!-- <h3 class="page-header">Preview:</h3> -->
        <div class="docs-preview clearfix">
          <div class="img-preview preview-lg"></div>
          <div class="img-preview preview-md"></div>
          <div class="img-preview preview-sm"></div>
          <div class="img-preview preview-xs"></div>
        </div>

        <!-- <h3 class="page-header">Data:</h3> -->
        <div class="docs-data">
          <div class="input-group input-group-sm" style="width: 262px;">
            <label class="input-group-addon" for="dataX">X</label>
            <input type="text" class="form-control" id="dataX" placeholder="x">
            <span class="input-group-addon">px</span>
          </div>
          <div class="input-group input-group-sm" style="width: 262px;">
            <label class="input-group-addon" for="dataY">Y</label>
            <input type="text" class="form-control" id="dataY" placeholder="y">
            <span class="input-group-addon">px</span>
          </div>
        </div>
      </div>
    </div>
    <div class="row docs-actions">
      <div class="col-md-9 docs-buttons" style="width: 100%">

        <div class="btn-group">
          <button type="button" class="btn btn-primary" data-method="move" data-option="-10" data-second-option="0" title="Move Left">
            <span class="docs-tooltip" data-toggle="tooltip" title="左移10像素">
              <span class="fa fa-arrow-left"></span>
            </span>
          </button>
          <button type="button" class="btn btn-primary" data-method="move" data-option="10" data-second-option="0" title="Move Right">
            <span class="docs-tooltip" data-toggle="tooltip" title="右移10像素">
              <span class="fa fa-arrow-right"></span>
            </span>
          </button>
          <button type="button" class="btn btn-primary" data-method="move" data-option="0" data-second-option="-10" title="Move Up">
            <span class="docs-tooltip" data-toggle="tooltip" title="上移10像素">
              <span class="fa fa-arrow-up"></span>
            </span>
          </button>
          <button type="button" class="btn btn-primary" data-method="move" data-option="0" data-second-option="10" title="Move Down">
            <span class="docs-tooltip" data-toggle="tooltip" title="下移10像素">
              <span class="fa fa-arrow-down"></span>
            </span>
          </button>
        </div>

        <div class="btn-group">
          <button type="button" class="btn btn-primary" data-method="rotate" data-option="-45" title="Rotate Left">
            <span class="docs-tooltip" data-toggle="tooltip" title="右转45度">
              <span class="fa fa-rotate-left"></span>
            </span>
          </button>
          <button type="button" class="btn btn-primary" data-method="rotate" data-option="45" title="Rotate Right">
            <span class="docs-tooltip" data-toggle="tooltip" title="左转45度">
              <span class="fa fa-rotate-right"></span>
            </span>
          </button>
        </div>

        <div class="btn-group">
          <button type="button" class="btn btn-primary" data-flip="horizontal" data-method="scale" data-option="-1" data-second-option="1" title="Flip Horizontal">
            <span class="docs-tooltip" data-toggle="tooltip" title="左右翻转">
              <span class="fa fa-arrows-h"></span>
            </span>
          </button>
          <button type="button" class="btn btn-primary" data-flip="vertical" data-method="scale" data-option="1" data-second-option="-1" title="Flip Vertical">
            <span class="docs-tooltip" data-toggle="tooltip" title="上下翻转">
              <span class="fa fa-arrows-v"></span>
            </span>
          </button>
        </div>

        <div class="btn-group">
          <button type="button" class="btn btn-primary" data-method="disable" title="Disable">
            <span class="docs-tooltip" data-toggle="tooltip" title="锁定">
              <span class="fa fa-lock"></span>
            </span>
          </button>
          <button type="button" class="btn btn-primary" data-method="enable" title="Enable">
            <span class="docs-tooltip" data-toggle="tooltip" title="解锁">
              <span class="fa fa-unlock"></span>
            </span>
          </button>
        </div>

        <div class="btn-group">
          <button type="button" class="btn btn-primary" data-method="reset" title="Reset">
            <span class="docs-tooltip" data-toggle="tooltip" title="重置">
              <span class="fa fa-refresh"></span>
            </span>
          </button>
          <label class="btn btn-primary btn-upload" for="inputImage" title="Upload image file">
            <input type="file" class="sr-only" id="inputImage" name="file" accept="image/*">
            <span class="docs-tooltip" data-toggle="tooltip" title="Import image with Blob URLs">
              <span class="fa fa-upload"></span>
            </span>
          </label>
        </div>

        <div class="btn-group btn-group-crop">
          <button type="button" class="btn btn-primary" data-method="getCroppedCanvas">
            <span class="docs-tooltip" style="height:25px;" data-toggle="tooltip" title="$().cropper(&quot;getCroppedCanvas&quot;)">
              	 <label style="margin-top: -4px;">上传</label> 
            </span>
          </button>
        </div>

        <!-- Show the cropped image in modal -->
        <div class="modal fade docs-cropped" id="getCroppedCanvasModal" aria-hidden="true" aria-labelledby="getCroppedCanvasTitle" role="dialog" tabindex="-1">
          <div class="modal-dialog" style="width: 380px;">
            <div class="modal-content">
              <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title" id="getCroppedCanvasTitle">预览<label id="picLength"></label> </h4>
              </div>
              <div class="modal-body"></div>
              <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <a class="btn btn-primary" id="download" download="cropped.png" href="javascript:void(0);">下载</a>
                <a class="btn btn-primary" id="myUpImg" onclick="uppic()"  href="javascript:void(0);">上传</a>
              </div>
            </div>
          </div>
        </div><!-- /.modal -->
      </div><!-- /.docs-buttons -->

      <div class="col-md-3 docs-toggles" style="width: 262px;">
        <!-- <h3 class="page-header">Toggles:</h3> -->
        <div class="btn-group btn-group-justified" data-toggle="buttons">
          <label class="btn btn-primary active" data-method="setAspectRatio" data-option="1.7777777777777777" title="Set Aspect Ratio">
            <input type="radio" class="sr-only" id="aspestRatio1" name="aspestRatio" value="1.7777777777777777">
            <span class="docs-tooltip" data-toggle="tooltip" title="$().cropper(&quot;setAspectRatio&quot;, 16 / 9)">
              16:9
            </span>
          </label>
          <label class="btn btn-primary" data-method="setAspectRatio" data-option="1.3333333333333333" title="Set Aspect Ratio">
            <input type="radio" class="sr-only" id="aspestRatio2" name="aspestRatio" value="1.3333333333333333">
            <span class="docs-tooltip" data-toggle="tooltip" title="$().cropper(&quot;setAspectRatio&quot;, 4 / 3)">
              4:3
            </span>
          </label>
          <label class="btn btn-primary" data-method="setAspectRatio" data-option="1" title="Set Aspect Ratio">
            <input type="radio" class="sr-only" id="aspestRatio3" name="aspestRatio" value="1">
            <span class="docs-tooltip" data-toggle="tooltip" title="$().cropper(&quot;setAspectRatio&quot;, 1 / 1)">
              1:1
            </span>
          </label>
          <label class="btn btn-primary" data-method="setAspectRatio" data-option="0.6666666666666666" title="Set Aspect Ratio">
            <input type="radio" class="sr-only" id="aspestRatio4" name="aspestRatio" value="0.6666666666666666">
            <span class="docs-tooltip" data-toggle="tooltip" title="$().cropper(&quot;setAspectRatio&quot;, 2 / 3)">
              2:3
            </span>
          </label>
          <label class="btn btn-primary" data-method="setAspectRatio" data-option="NaN" title="Set Aspect Ratio">
            <input type="radio" class="sr-only" id="aspestRatio5" name="aspestRatio" value="NaN">
            <span class="docs-tooltip" data-toggle="tooltip" title="$().cropper(&quot;setAspectRatio&quot;, NaN)">
              Free
            </span>
          </label>
        </div>

        <div class="dropdown dropup docs-options">
          <!-- <button type="button" class="btn btn-primary btn-block dropdown-toggle" id="toggleOptions" data-toggle="dropdown" aria-expanded="true">
            Toggle Options
            <span class="caret"></span>
          </button> -->
          <ul class="dropdown-menu" aria-labelledby="toggleOptions" role="menu">
            <li role="presentation">
              <label class="checkbox-inline">
                <input type="checkbox" name="option" value="strict" checked>
                strict
              </label>
            </li>
            <li role="presentation">
              <label class="checkbox-inline">
                <input type="checkbox" name="option" value="responsive" checked>
                responsive
              </label>
            </li>
            <li role="presentation">
              <label class="checkbox-inline">
                <input type="checkbox" name="option" value="checkImageOrigin" checked>
                checkImageOrigin
              </label>
            </li>

            <li role="presentation">
              <label class="checkbox-inline">
                <input type="checkbox" name="option" value="modal" checked>
                modal
              </label>
            </li>
            <li role="presentation">
              <label class="checkbox-inline">
                <input type="checkbox" name="option" value="guides" checked>
                guides
              </label>
            </li>
            <li role="presentation">
              <label class="checkbox-inline">
                <input type="checkbox" name="option" value="center" checked>
                center
              </label>
            </li>
            <li role="presentation">
              <label class="checkbox-inline">
                <input type="checkbox" name="option" value="highlight" checked>
                highlight
              </label>
            </li>
            <li role="presentation">
              <label class="checkbox-inline">
                <input type="checkbox" name="option" value="background" checked>
                background
              </label>
            </li>

            <li role="presentation">
              <label class="checkbox-inline">
                <input type="checkbox" name="option" value="autoCrop" checked>
                autoCrop
              </label>
            </li>
            <li role="presentation">
              <label class="checkbox-inline">
                <input type="checkbox" name="option" value="dragCrop" checked>
                dragCrop
              </label>
            </li>
            <li role="presentation">
              <label class="checkbox-inline">
                <input type="checkbox" name="option" value="movable" checked>
                movable
              </label>
            </li>
            <li role="presentation">
              <label class="checkbox-inline">
                <input type="checkbox" name="option" value="rotatable" checked>
                rotatable
              </label>
            </li>
            <li role="presentation">
              <label class="checkbox-inline">
                <input type="checkbox" name="option" value="scalable" checked>
                scalable
              </label>
            </li>
            <li role="presentation">
              <label class="checkbox-inline">
                <input type="checkbox" name="option" value="zoomable" checked>
                zoomable
              </label>
            </li>
            <li role="presentation">
              <label class="checkbox-inline">
                <input type="checkbox" name="option" value="mouseWheelZoom" checked>
                mouseWheelZoom
              </label>
            </li>
            <li role="presentation">
              <label class="checkbox-inline">
                <input type="checkbox" name="option" value="touchDragZoom" checked>
                touchDragZoom
              </label>
            </li>
            <li role="presentation">
              <label class="checkbox-inline">
                <input type="checkbox" name="option" value="cropBoxMovable" checked>
                cropBoxMovable
              </label>
            </li>
            <li role="presentation">
              <label class="checkbox-inline">
                <input type="checkbox" name="option" value="cropBoxResizable" checked>
                cropBoxResizable
              </label>
            </li>
            <li role="presentation">
              <label class="checkbox-inline">
                <input type="checkbox" name="option" value="doubleClickToggle" checked>
                doubleClickToggle
              </label>
            </li>
          </ul>
        </div><!-- /.dropdown -->
      </div><!-- /.docs-toggles -->
    </div>
  </div>
  <input type="hidden" id="hidePlace" >
  <input type="hidden" id="hideId" >
  <!-- Scripts -->
  <script src="${resPath }/chajian/cropper-1.0.0/assets/js/jquery.min.js"></script>
  <script src="${resPath }/chajian/cropper-1.0.0/assets/js/bootstrap.min.js"></script>
  <script src="${resPath }/chajian/cropper-1.0.0/assets/js/tooltip.min.js"></script>
  <script src="${resPath }/chajian/cropper-1.0.0/dist/cropper.js"></script>
  <script src="${resPath }/chajian/cropper-1.0.0/demo/js/main.js"></script>
  <script type="text/javascript">
  
  	function uppic(){
  		if($(".modal-body").children()[0]. toDataURL("image/png").length>1800000){
  			alert("图片太大无法上传")
  			return
  		};
  		$.ajax({
  			url:"${path}/issueDoc/upPic",
  			type:"post",
  			data:{
  				base64Data:$("#myUpImg").prev().attr("href")
  			},
  			success:function(data){
  				if(data.state==1){
  					$("#hidePlace").val("${imgPath}"+data.place);
  					$("#hideId").val(data.id);
  					$(".close").trigger("click");
  					parent.closeDialog();
  				}
  			}
  		})
  	}
  	
  	
  	
  	
  </script>
</body>
</html>