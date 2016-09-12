package com.graphene.config.service

import java.io.PrintWriter
import java.io.File

object FileRepoUtil {

  def is(namespace: String) {

  }

  def writeConfig(filePath: String, filename: String, content: String) {
    val contentDir = new File(filePath)
    val isDir = contentDir.isDirectory()
    if(!contentDir.exists()){
      contentDir.mkdirs();
    }
    val writer = new PrintWriter(new File(filePath + "/" + filename))
    writer.write(content)
    writer.close()
  }
}