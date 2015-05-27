package com.wesovi.demo.facade.persistence.model


import reactivemongo.bson._
import reactivemongo.core.commands.Count
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import com.wesovi.demo.facade.persistence.MongoDb


object AccountDocumentStructure{
  val _COLLECTION ="accounts"
  val ID:String = "_id"
  val EMAIL:String ="_email"
}

case class AccountDocument ( _id: Option[BSONObjectID] = None,email:String){

  def this(email:String) = this(null,email)

}

case class AccountDocumentCollection()



object AccountDocument{
  
  lazy val collection = MongoDb.getCollection(AccountDocumentStructure._COLLECTION)

  implicit object AccountDocumentReader extends BSONReader[BSONDocument,AccountDocument]{

    override def read(document: BSONDocument): AccountDocument = {
      AccountDocument(
        document.getAs[BSONObjectID](AccountDocumentStructure.ID),
        document.getAs[String](AccountDocumentStructure.EMAIL).get
      )
    }
  }

  implicit object AccountDocumentWriter extends BSONWriter[AccountDocument,BSONDocument]{
    override def write(account: AccountDocument): BSONDocument = {
      BSONDocument(
        AccountDocumentStructure.ID -> account._id.getOrElse(BSONObjectID.generate),
        AccountDocumentStructure.EMAIL -> BSONString(account.email)
      )
    }
  }


  def create(email:String):Future[AccountDocument] = {
    val account:AccountDocument = AccountDocument(email=email)
    collection.insert(AccountDocumentWriter.write(account)).map{ error =>
      if(error.ok)
        account
      else
        throw error
    }
  }

  def countByEmail(email:String):Future[Int] = {
   /** val query:BSONDocument = accountFormat.write(new AccountDocument(email))
    count(query)
     **/
    null
  }

  def count(query:BSONDocument): Future[Int] ={
    collection.db.command(
      Count(
        collection.name,
        Some(query)
      )
    )
  }

}