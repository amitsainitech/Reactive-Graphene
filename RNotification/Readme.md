# Notification System

Notification has three main component
1.) **Delivery Channel**
	Responsible for delivering the msg to specific endpoint. EmailChanel,TwitterChanel etc.
2.) **Data Transformers**
    Responsible for data transformation from json to xml or xml to text.
3.) **Data Enricher**
    Enriches existing data with more information from a given source.
4.) **DataPipeline **
    Wires and binds all the components and manages their execution.
	
Notification{
		InChanel{ Rest{type=ip}
		}.DataEnricher{
		}.DataTransformer{
		}.OutChanel{
		}
}