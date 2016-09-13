package com.graphene.notification.inchannel

trait DeliveryChanel {
  def canDeliver:Boolean
  def deliverMsg
  
}