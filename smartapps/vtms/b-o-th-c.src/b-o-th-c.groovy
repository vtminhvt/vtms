definition(
    name: "Báo thức",
    namespace: "vtms",
    author: "vtminhvt",
    description: "Kịch bản điều khiển thiết bị dựa vào thói quen, sở thích và mệnh lệnh",
    category: "Safety & Security",
    iconUrl: "https://s3.amazonaws.com/vtmsmartthings/vtms60.png",
    iconX2Url: "https://s3.amazonaws.com/vtmsmartthings/vtms120.png",
    iconX3Url: "https://s3.amazonaws.com/vtmsmartthings/vtms120.png")


preferences {
	
section("Chọn Hẹn giờ hoặc tắt")
{
	input name:"chon",type:"enum", title:"Hẹn giờ", options: ["on","off"], defaulValue:"off"
}
section("Giờ hẹn ")
    {
     input name: "timeCB", type: "time", title: "Đặt thời gian"
    }

 section ("Thời gian báo thức")
	{
     input name: "timeofP", type: "number", title: "Báo thức trong bao lâu(giây)?", defaultValue:"1"
    } 
    
section("Báo động Nhà")
    {
    	input("alamH","capability.alarm",title:"Báo động ở Nhà")
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
	schedule(timeCB, alamR)
   	init()	
}

def init()
{
  	subscribe(alamH,"alarm",alam_H) 
}
def initialize() 
{  
}

def alamR()
{
def timeP=timeofP*10000
if (chon=="on")
{
	sendPush("Đang báo thức bằng chuông báo động")
	alamH.both()
    schedule(now()+timeP,alamF)
 }
}

def alamF()
{
	alamH.off()
}