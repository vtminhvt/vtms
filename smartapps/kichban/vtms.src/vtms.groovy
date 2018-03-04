definition(
    name: "VTMS",
    namespace: "KichBan",
    author: "Võ Thanh Minh",
    description: "Kịch bản điều khiển thiết bị dựa vào thói quen, sở thích và mệnh lệnh",
    category: "Safety & Security",
    iconUrl: "https://s3.amazonaws.com/vtmsmartthings/vtms60.png",
    iconX2Url: "https://s3.amazonaws.com/vtmsmartthings/vtms120.png",
    iconX3Url: "https://s3.amazonaws.com/vtmsmartthings/vtms120.png")


preferences {
	
    section("VTMS TIME EVENT")
    {
     input name: "timeCB", type: "time", title: "VTMS TEXT"
    } 
    section("TEXT")
    	{
        	input name:"txt",type:"text", title:"VTMS TEXT",defaultValue:"VTMS SEVICES."
    	}
}
def installed() 
{
    schedule(timeCB, cb)
    init()
}
def updated() 
{
	unschedule()
	schedule(timeCB, cb)
       	init()	
}

def init()
{ 
}
def initialize() {
    schedule(timeCB, cb)
   }
def cb()
{
	sendPush("${txt}")
//log.debug "${txt}"
}