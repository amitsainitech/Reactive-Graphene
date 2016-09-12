package com.graphene.config

class ConfigurationContext(val tenant:String,val requestor:String){
  
	 override def toString:String={
		tenant+""+requestor;
	  }
}