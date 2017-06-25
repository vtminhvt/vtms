definition(
    name: "Báo thức",
    namespace: "KichBan",
    author: "Võ Thanh Minh",
    description: "Kịch bản điều khiển thiết bị dựa vào thói quen, sở thích và mệnh lệnh",
    category: "Safety & Security",
    iconUrl: "https://s3.amazonaws.com/vtmsmartthings/vtms60.png",
    iconX2Url: "https://s3.amazonaws.com/vtmsmartthings/vtms120.png",
    iconX3Url: "https://s3.amazonaws.com/vtmsmartthings/vtms120.png")
//Test:OK
preferences {
	
    section("Kích hoạt kịch bản")
    {
        input name:"sel",type:"enum", title:"Hẹn giờ", options: ["on","off"], defaulValue:"off"
    }
    section("Giờ hẹn")
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
	schedule(timeCB, alamR)
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
def timeP=timeofP*1000
if (sel=="on")
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