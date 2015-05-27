package com.wesovi.demo.util

import scala.util.matching.Regex

/**
 * Created by ivan on 11/10/14.
 */
object EmailValidation {

  private val emailRegex:Regex = """^(?!\.)("([^"\r\\]|\\["\r\\])*"|([-a-zA-Z0-9!#$%&'*+/=?^_`{|}~]|(?<!\.)\.)*)(?<!\.)@[a-zA-Z0-9][\w\.-]*[a-zA-Z0-9]\.[a-zA-Z][a-zA-Z\.]*[a-zA-Z]$""".r

  def isValid(email:String):Boolean={
    email.matches(emailRegex.regex)
  }

}