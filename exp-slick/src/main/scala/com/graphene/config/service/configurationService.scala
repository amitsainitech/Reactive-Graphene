package com.graphene.config.service

import com.graphene.config._;
//Requesting a configuration
case class FetchConfigurationCmd(override val tenant: String, override val requestor: String, val configNameSpace: String, val configName: String) extends ConfigurationContext(tenant, requestor);
case class FetchConfigResponse(val request:FetchConfigurationCmd,val filePath:String)
case class ConfigCmdFailure(val request:FetchConfigurationCmd,val errorCode:String ,val errorMsg:String)
//Adding a configuration
case class AddConfigurationCmd(override val tenant: String, override val requestor: String,val configNameSpace: String, val configName: String,val content: String) extends ConfigurationContext(tenant, requestor);

//Deleting a configuration
//case class DeleteConfigurationCmd(override val tenant: String, override val requestor: String, val configName: String, val configType: String, val content: String) extends ConfigurationContext(tenant, requestor);
//Update a Configuration
//case class UpdateConfigurationCmd(override val tenant: String, override val requestor: String, val configName: String, val configType: String, val content: String) extends ConfigurationContext(tenant, requestor);
//DeleteAll for Tenant
//case class DeleteAllConfigurationForTenant(override val tenant:String,override val requestor:String) extends ConfigurationContext;