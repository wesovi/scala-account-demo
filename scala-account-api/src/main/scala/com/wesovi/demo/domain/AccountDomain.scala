package com.wesovi.demo.domain

import com.wesovi.demo.util.EmailValidation
import spray.json.DefaultJsonProtocol

sealed trait AccountModelResponse

sealed trait AccountModelRequest

case class AccountCreationRequest(email:String,password:String) extends AccountModelRequest{
  
  def validate(){
    assume(email!=null,"password must not be null")
    assume(password!=null,"password must not be null")
    require(!email.isEmpty,"email must not be empty.")
    require(EmailValidation.isValid(email),"Invalid email format.")
    require(!password.isEmpty,"password must not be empty")
    require(password.length>=6 && password.length<=10,"the password length must be between 6 and 10 characters.")
  }
  
}

object AccountCreationRequest extends DefaultJsonProtocol{
  implicit val accountCreationRequestFormat = jsonFormat2(AccountCreationRequest.apply)
}

case class AccountResponse(id:String, email:String) extends AccountModelResponse

object AccountResponse extends DefaultJsonProtocol{
  implicit val accountResponseFormat = jsonFormat2(AccountResponse.apply)
}