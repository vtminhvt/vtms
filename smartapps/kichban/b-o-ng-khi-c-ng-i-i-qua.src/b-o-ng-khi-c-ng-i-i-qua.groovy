definition(
    name: "Báo động khi có người đi qua",
    namespace: "KichBan",
    author: "Võ Thanh Minh",
    description: "Kịch bản điều khiển thiết bị dựa vào thói quen, sở thích và mệnh lệnh",
    category: "Safety & Security",
    iconUrl: "https://s3.amazonaws.com/vtmsmartthings/vtms60.png",
    iconX2Url: "https://s3.amazonaws.com/vtmsmartthings/vtms120.png",
    iconX3Url: "https://s3.amazonaws.com/vtmsmartthings/vtms120.png")
//Test:OK 

preferences 
{	
    section("Kích hoạt kịch bản")
    	{
        	input name:"sel",type:"enum", title:"Hoạt động", options: ["on","off"], defaultValue:"off"
    	}
    section("Kiểu báo động")
    	{
       		 input name:"typ",type:"enum", title:"Chọn kiểu báo động", options: ["A","L","AL"], defaultValue:"L"
    	}
    section("Chọn khoảng thời gian")
        {
             input name: "timeB", type: "time", title: "Đặt thời gian bắt đầu"
             input name: "timeE", type: "time", title: "Đặt thời gian kết thúc"
        }
    section ("Thời gian báo động")
        {
        	 input name: "tp", type: "number", title: "Báo động trong bao lâu(giây)?", defaultValue:"15"
        }

    section("Thiết bị báo động")
        {
            input("alamH","capability.alarm",title:"Chọn thiết bị báo động")
        }
     section("Chuyển động ở đâu")
		{
			input("motionCD", "capability.motionSensor",title:"Chọn cảm biến chuyển động")
		}   
}

def installed() 
{
    init()
}
def updated() 
{
	unschedule()
   	init()	
}

def init()
{
	subscribe(motionCD,"motion",motion_CD)
  	subscribe(alamH,"alarm",alam_H) 
}
def initialize() 
{ 

}

def motion_CD(evt)
{

    def timeofB = timeToday(timeB)
    def timeofE= timeToday(timeE)
    def timeC=now()
    def p=tp*1000
     def dk1= (timeofB<timeofE) && (timeC >= timeofB.time && timeC<=timeofE.time)
    def dk2= (timeofB>timeofE) && (timeC >= timeofB.time || timeC<=timeofE.time)
    
    if (evt.value == "active" && sel == "on")
    {
    
        if (dk1 || dk2)
            {
                if(typ=="L") alamH.strobe()
                if(typ=="A") alamH.siren()
                if(typ=="AL") alamH.both()
                schedule(now()+p,alamF) // turn off in 10 second
                sendPush("[Nhà]Báo động do phát hiện chuyển động")
            }
    }
}
def alamF()
{
	alamH.off()  
}