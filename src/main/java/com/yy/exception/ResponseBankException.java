package com.yy.exception;

/**
 * Created by 稻草人 on 2018/10/28.
 * ajax请求接口时，即是接口处理发生错误，虽然开发者可以自定义返回json的code，值
 * 但是http的status仍然会返回状态码200。在银行业务中，这种做法通常是不被允许的。
 * 因此，当接口处理业务出现错误时，应修改http返回的status值。
 *
 * 此异常类即可以解决以上问题。
 * 此时，遇到以上问题时，抛出异常时，应抛出ResponseBankException而不是SellException。
 * 并需要在ExceptionHandler异常处理类中，接收抛出的异常。
 */
public class ResponseBankException extends RuntimeException{

}
