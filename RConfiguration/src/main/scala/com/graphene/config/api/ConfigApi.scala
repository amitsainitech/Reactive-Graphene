package com.graphene.config.api

import com.graphene.core._


//Request Messages
case class ConfigurationRequestMsg(tenantId:String,configNS:MSNameSpace,configName:String,contentType:String=".conf") extends TenantCtx
case class AddConfigurationMsg(tenantId:String,configNS:MSNameSpace,configName:String,content:String,contentType:String) extends TenantCtx
case class DeleteConfigurationMsg(tenantId:String,configNS:MSNameSpace,configName:String) extends TenantCtx
case class UpdateConfigurationMsg(tenantId:String,configNS:MSNameSpace,configName:String,content:String,contentType:String) extends TenantCtx
//Response Messages
case class ConfigurationResponseMsg(tenantId:String,configReqMsg:ConfigurationRequestMsg,config:String) extends TenantCtx
case class RequestedConfigurationNotFoundMsg(tenantId:String,configReqMsg:ConfigurationRequestMsg,msg:String) extends TenantCtx
case class ConfigurationAddedResMsg(code:String,msg:String,tenantId:String) 