package com.bizruntime.config.repo.filerepo
import com.bizruntime.core.MSNameSpace
import com.typesafe.config.ConfigFactory
 import java.nio.file.{Paths, Files}
 
object FileRepo {
  println("Welcome to the Scala worksheet")
  val lst=List("inventory","Biz","v1")
  lst.foldLeft("d:/basedir"+"/"+"BizT1")((x,y)=>x+"/"+y)
 
 def handleFileFolder()={
 		val filePaths=filePathList("D:/Bizruntime-Reactive/ConfigRepo","Gap"::List("ConfigRepo","v1","FileRepo"))
 		val nonExistantPath=filePaths.filter(x=>(!isFileExist(x))) foreach { x =>
 			Files.createDirectory(Paths.get(x))
 		}
 		
 }
 
 def filePathList(baseDir:String,nsList:List[String]):List[String]={
 		 var lastString=baseDir
     for(fol<-nsList)yield{
     		lastString=lastString+"/"+fol
     		lastString
     }
   }
   
   def isFileExist(filePath:String):Boolean={
   	  Files.exists(Paths.get(filePath))
   }
   
  handleFileFolder()
  io.File("D:\\Architectural Notes.txt").slurp
  val lines = scala.io.Source.fromFile("D:\\Architectural Notes.txt").mkString
   isFileExist("D:/Bizruntime-Reactive/ConfigRepo")
}