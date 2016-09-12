package com.bizruntime.config.repo.filerepo

import java.nio.file.Files
import java.nio.file.Paths

import com.bizruntime.core.MSNameSpace

object FileRepoUtil extends App {
  
  def setupNSFolder(baseDir: String, tenantId: String, ns: MSNameSpace) {
    def filePathList(baseDir: String, nsList: List[String]): List[String] = {
      var lastString = baseDir
      for (fol <- nsList) yield {
        lastString = lastString + "/" + fol
        lastString
      }
    }
    val filePaths = filePathList(baseDir, tenantId :: ns.toList)
    val nonExistantPath = filePaths.filter(x => (!isFileExist(x))) foreach { x =>
      Files.createDirectory(Paths.get(x))
    }
  }

  def doesFinalNSDirExist(baseDir: String, tenantId: String, ns: MSNameSpace):Boolean={
      isFileExist(finalNSDir(baseDir,tenantId,ns))
  }
  def finalNSDir(baseDir: String, tenantId: String, ns: MSNameSpace):String={
    val msId=ns.msId
    val msDiff=ns.msDiff
    val version=ns.version
    s"$baseDir/$tenantId/$msId/$msDiff/$version"
  }
  def isFileExist(filePath: String): Boolean = {
// io.File("D:\\Architectural Notes.txt").slurp   
Files.exists(Paths.get(filePath))
  }
 
   
}