definition(
    name: "Đặt giờ kiểm tra trạng thái an ninh",
    namespace: "KichBan",
    author: "Võ Thanh Minh",
    description: "Kịch bản điều khiển thiết bị dựa vào thói quen, sở thích và mệnh lệnh",
    category: "Safety & Security",
    iconUrl: "https://s3.amazonaws.com/vtmsmartthings/vtms60.png",
    iconX2Url: "https://s3.amazonaws.com/vtmsmartthings/vtms120.png",
    iconX3Url: "https://s3.amazonaws.com/vtmsmartthings/vtms120.png")
//test: OK

preferences 
{
	section("Kích hoạt kịch bản")
	{
		input name:"sel",type:"enum", title:"Hoạt động", options: ["on","off"], defaultValue:"off"
	}
    
    section("Đặt thời gian cảnh báo An toàn")
    {
     input name: "timeCB", type: "time", title: "Thời gian cảnh báo an toàn"
    }
   
	section ("Chọn thời gian")
	{
     input name: "tl", type: "number", title: "Bật cảnh báo sau lời nhắc nhở đầu tiên(giây)?", defaultValue:"30"
     input name: "tp", type: "number", title: "Cảnh báo bao lâu(giây)?", defaultValue:"30"
    }
    section("Kiểu báo động")
    {
        input name:"typ",type:"enum", title:"Kiểu báo động", options: ["A","L","AL"], defaultValue:"L"
    }
    section("Chọn các cảm biến đóng mở cần cảnh báo")
    {
    	input("csCC","capability.contactSensor",title:"Cảm biến Cửa chính")
    	input("csT2","capability.contactSensor",title:"Cảm biến Tầng 2")
    	input("csT3","capability.contactSensor",title:"Cảm biến Tầng 3")
    	input("csT4","capability.contactSensor",title:"Cảm biến Tầng 4")
        input("csT4s","capability.contactSensor",title:"Cảm biến Tầng 4 sau")
    }
    
    section("Báo động Nhà")
    {
    	input("alamH","capability.alarm",title:"Báo động ở Nhà")
    }
}
def init()
{
    subscribe(csCC,"contact",cs_CC)
    subscribe(csT2,"contact",cs_T2)
    subscribe(csT3,"contact",cs_T3)
    subscribe(csT4,"contact",cs_T4)
    subscribe(csT4s,"contact",cs_T4s)
  	subscribe(alamH,"alarm",alam_H)
}

def installed() 
{
    init()
	schedule(timeCB, cb)
}

def updated() 
{
	init()	
	unschedule()
	schedule(timeCB, cb)
}

def initialize() 
{
    schedule(timeCB, cb)   
}
   
def cb()
{
    if (sel=="on")
    {
        def cs1=(csCC.currentValue("contact")=="closed")
        def cs2=(csT2.currentValue("contact")=="closed")
        def cs3=(csT3.currentValue("contact")=="closed")
        def cs4=(csT4.currentValue("contact")=="closed")
        def cs4s=(csT4s.currentValue("contact")=="closed") 
        
        def t1=csCC.currentValue("contact")
        def t2=csT2.currentValue("contact")
        def t3=csT3.currentValue("contact")
        def t4=csT4.currentValue("contact")
        def t4s=csT4s.currentValue("contact")
        
        
        if (! (cs1 && cs2 && cs3 && cs4 && cs4s))
        {
           sendPush("Trạng thái: Vẫn còn cửa chưa đóng, hãy kiểm tra lại. \n Cửa chính ${t1} \n Cửa T2 ${t2} \n Cửa T3 ${t3} \n Cửa T4 ${t4} \n Cửa T4s ${t4s}")
            def t_l=tl*1000
            schedule(now()+t_l,cb2)

        }
        else
        {
            sendPush("Trạng thái: Nhà của bạn đã an toàn!")
        }
    }
}
def cb2()
{
	def t_p=tp*1000
    if (sel=="on")
    {

        def cs1=(csCC.currentValue("contact")=="closed")
        def cs2=(csT2.currentValue("contact")=="closed")
        def cs3=(csT3.currentValue("contact")=="closed")
        def cs4=(csT4.currentValue("contact")=="closed")
        def cs4s=(csT4s.currentValue("contact")=="closed")
        
         def t1=csCC.currentValue("contact")
        def t2=csT2.currentValue("contact")
        def t3=csT3.currentValue("contact")
        def t4=csT4.currentValue("contact")
        def t4s=csT4s.currentValue("contact")    

        if (! (cs1 && cs2 && cs3 && cs4 && cs4s))
        {
          sendPush("Trạng thái: Vẫn còn cửa chưa đóng, hãy kiểm tra lại. \n Cửa chính ${t1} \n Cửa T2 ${t2} \n Cửa T3 ${t3} \n Cửa T4 ${t4} \n Cửa T4s ${t4s}")
           
            if(typ=="L") 
            {
        		sendPush("Báo động do phát hiện cửa vẫn còn mở")
           		alamH.strobe()
        	}
            if(typ=="A")
            {
        		sendPush("Báo động do phát hiện cửa vẫn còn mở")
        		alamH.siren()
            	
        	}
        	if(typ=="AL")
        	{
        		sendPush("Báo động do phát hiện cửa vẫn còn mở")
        		alamH.both()
        	}
             schedule(now()+t_p,tatBD)
        }
        else
        {
            sendPush("Trạng thái: Nhà của bạn đã an toàn!")
        }
    }
}

def tatBD()
{
	alamH.off()
}



