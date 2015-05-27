package com.wesovi.demo.facade.persistence

/**
 * @author icorrales
 */

import akka.event.slf4j.Logger
import com.typesafe.config.ConfigFactory
import reactivemongo.api.collections.default.BSONCollection
import reactivemongo.api.{DB, MongoDriver} 

object MongoDb  extends {
  private val logger = Logger("MongoDB")

  private val config = ConfigFactory.load
  private val servers= config.getString("mongodb.server")
  private val dbName = config.getString("mongodb.db")

  private val driver = new MongoDriver
  private val db = DB(dbName, driver.connection(List(servers)))

  def getCollection(name: String): BSONCollection ={
    db(name)
  }
 
  def close(){
    db.connection.close()
  }

}