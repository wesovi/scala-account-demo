package com.wesovi.demo.facade.persistence.model

import com.github.t3hnar.bcrypt._
import reactivemongo.bson._
import reactivemongo.core.commands.Count
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import com.wesovi.demo.facade.persistence.MongoDb


object StudentDocumentStructure{
  val _COLLECTION ="accounts"
  val ID:String = "_id"
  val EMAIL:String ="_email"
}

case class StudentDocument ( _id: Option[BSONObjectID] = None,email:String){

  def this(email:String) = this(null,email)

}

case class StudentDocumentCollection()



object StudentDocument{
  
  lazy val collection = MongoDb.getCollection(StudentDocumentStructure._COLLECTION)

  implicit object StudentDocumentReader extends BSONReader[BSONDocument,StudentDocument]{

    override def read(document: BSONDocument): StudentDocument = {
      StudentDocument(
        document.getAs[BSONObjectID](StudentDocumentStructure.ID),
        document.getAs[String](StudentDocumentStructure.EMAIL).get
      )
    }
  }

  implicit object StudentDocumentWriter extends BSONWriter[StudentDocument,BSONDocument]{
    override def write(student: StudentDocument): BSONDocument = {
      BSONDocument(
        StudentDocumentStructure.ID -> student._id.getOrElse(BSONObjectID.generate),
        StudentDocumentStructure.EMAIL -> BSONString(student.email)
      )
    }
  }


  def create(email:String):Future[StudentDocument] = {
    val student:StudentDocument = StudentDocument(email=email)
    collection.insert(StudentDocumentWriter.write(student)).map{ error =>
      if(error.ok)
        student
      else
        throw error
    }
  }

  def countByEmail(email:String):Future[Int] = {
   /** val query:BSONDocument = accountFormat.write(new StudentDocument(email))
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