definition(
        name: "Hẹn giờ bật đèn",
        namespace: "KichBan",
        author: "Võ Thanh Minh",
        description: "Kịch bản điều khiển thiết bị dựa vào thói quen, sở thích và mệnh lệnh",
        category: "Safety & Security",
        iconUrl: "https://s3.amazonaws.com/vtmsmartthings/vtms60.png",
        iconX2Url: "https://s3.amazonaws.com/vtmsmartthings/vtms120.png",
        iconX3Url: "https://s3.amazonaws.com/vtmsmartthings/vtms120.png")


preferences 
{
    section("Kích hoạt kịch bản")
    {
        input name:"sel",type:"enum", title:"Hẹn giờ", options: ["on","off"], defaulValue:"off"
    }
    section("Giờ hẹn")
    {
        input name: "timeB", type: "time", title: "Đặt thời gian bật đèn"
        input name: "timeE", type: "time", title: "Đặt thời gian tắt đèn"
    }
    section("Chọn công tắc")
    {
        input("sw1","capability.switch",title:"Công tắc")
    }
}
def installed() 
{
	init()
	schedule(timeB, lightON)
	schedule(timeE, lightOFF)
}

def updated() 
{
	init()
    unschedule()
	schedule(timeB, lightON)
	schedule(timeE, lightOFF)
   	
}
def init()
{
  	subscribe(sw1,"switch",sw_1) 
}
def lightON()
{
	if (sel=="on") 
    {
    	sw1.on()
        sendPush("Đèn tầng 3 đang sáng")
    }
}

def lightOFF()
{
	if (sel=="on")	
    {
    sw1.off()
    sendPush("Đèn tầng 3 đã tắt")
    }
}