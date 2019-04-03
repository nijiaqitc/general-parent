package com.njq.start.filter;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.njq.common.exception.BaseKnownException;

@ControllerAdvice  
public class GlobalExceptionHandler {  
  
    
//    /**
//     * 捕捉其他异常
//     * @param exception
//     */
//    @ExceptionHandler
//    //返回的code值优先级大于方法体内设置的code值
//    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
//    public void handleIOException(Exception exception){  
//    	
//    	System.out.println("获取到了异常");
//    }  
      
    //全局捕捉的异常类型
    @ExceptionHandler(BaseKnownException.class)  
    @ResponseBody  
    public ExceptionResponse signException(BaseKnownException ex) {  
    	return ExceptionResponse.create(Integer.parseInt(ex.getErrorCode()), ex.getErrorMsg());  
    }  
  
}  