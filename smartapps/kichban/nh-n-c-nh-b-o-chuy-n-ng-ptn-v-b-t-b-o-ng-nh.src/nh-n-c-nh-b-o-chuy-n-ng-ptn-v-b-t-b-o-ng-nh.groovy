definition(
    name: "Nhận cảnh báo chuyển động ở PTN và bật báo động ở Nhà",
    namespace: "KichBan",
    author: "Võ Thanh Minh",
    description: "Trình điều khiển",
    category: "Safety & Security",
    iconUrl: "https://s3.amazonaws.com/vtmsmartthings/vtms60.png",
    iconX2Url: "https://s3.amazonaws.com/vtmsmartthings/vtms120.png",
    iconX3Url: "https://s3.amazonaws.com/vtmsmartthings/vtms120.png")


preferences 
{
    section("Kích hoạt kịch bản")
    {
        input name:"sel",type:"enum", title:"Hoạt động", options: ["on","off"], defaultValue:"off"
    }
    section("Chọn khoảng thời gian")
    {
         input name: "timeB", type: "time", title: "Đặt thời gian bắt đầu"
         input name: "timeE", type: "time", title: "Đặt thời gian kết thúc"
    }
    section("Kiểu báo động")
    {
        input name:"typ",type:"enum", title:"Chọn kiểu báo động", options: ["A","L","AL"], defaultValue:"L"
    }
    section ("Thời gian báo động")
    {
       input name: "tp", type: "number", title: "Báo động trong bao lâu(giây)?", defaultValue:"10"
    }
    section("Công tắc điện đồng bộ")
    {
       input("swLiMo","capability.switch",title:"Công tắc")
    }
    section("Báo động Nhà")
    {
	   input("alaH","capability.alarm",title:"Báo động ở Nhà")
    }
}
def installed() 
{
	init()
}
def updated() 
{
	init()	
}

def init()
{
    subscribe(swLiMo,"switch",sw_LiMo) 
    subscribe(alaH,"alarm",ala_H)
}

def sw_LiMo(evt)
{
    def timeofB = timeToday(timeB)
    def timeofE = timeToday(timeE)
    def timeC = now()
    def p = tp*1000

	def dk1= (timeofB<timeofE) && (timeC >= timeofB.time && timeC<=timeofE.time)
    def dk2= (timeofB>timeofE) && (timeC >= timeofB.time || timeC<=timeofE.time)
    
    if (evt.value == "open" && sel == "on")
    {
    
        if (dk1 || dk2) 
      		{
           		sendPush("[Nhà] Báo động vì phát hiện chuyển động PTN")
           		if (typ=="L") alaH.strobe();
           		if (typ=="A") alaH.siren();
           		if (typ=="AL") alaH.both();
           		schedule(now() + p, alaH_off)
      		}
       schedule(now() + p, swLiMo_off)
    }    
}
def alaH_off()
{
	alaH.off()
}

def swLiMo_off()
{
	swLiMo.off()
}
