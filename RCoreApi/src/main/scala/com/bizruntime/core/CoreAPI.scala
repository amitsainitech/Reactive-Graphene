package com.bizruntime.core

abstract class TenantCtx{
    def tenantId:String
 }

case class MSNameSpace(msId:String,version:String,msDiff:String,tag:List[(String,String)]){
  override def toString:String={
    s"$msId::$msDiff::$version"
  }
  def toList:List[String]={
    List(msId,msDiff,version)
  }
}

class SuccessReplyMsg(code:String="200",msg:String){
  
}