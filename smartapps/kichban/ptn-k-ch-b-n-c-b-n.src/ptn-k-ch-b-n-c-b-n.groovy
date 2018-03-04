definition(
    name: "PTN: Kịch bản cơ bản",
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
            input name:"sel",type:"enum", title:"Hoạt động", options: ["on","off"], defaultValue:"off"
        }
        
    section("Chọn khoảng thời gian")
        {
         input name: "timeB", type: "time", title: "Đặt thời gian bắt đầu"
         input name: "timeE", type: "time", title: "Đặt thời gian kết thúc"
        }
        
        section("Cảm biến chuyển động PTN")
        {
            input("motionPTN","capability.motionSensor",title:"CB Chuyển động PTN")  	
        }
        
        section("Cảm biến hiện diện của Giàu")
        {
            input("presenceGiau","capability.presenceSensor",title:"Cảm biến hiện diện Giàu")
        }
        
        section("iPhone của Giàu")
        {
            input("presenceGiauiPhone","capability.presenceSensor",title:"iPhone của Giàu")
        }

        section("Công tắc ở PTN")
        {
            input("switchPTN","capability.switch",title:"Công tắc ở PTN")
        }
        
        section("Báo động PTN")
        {
            input("alaPTN","capability.alarm",title:"Báo động ở PTN")
        }      
}
def init()
{
        subscribe(motionPTN,"motion",motion_PTN)
        subscribe(presenceGiau,"presence",presence_Giau)
        subscribe(presenceGiauiPhone,"presence",presence_GiauiPhone)
        subscribe(switchPTN,"switch",switch_PTN)
        subscribe(alaPTN,"alarm",ala_PTN)
}

def installed() 
{
  init()   
}

def updated() 
{
	init()
}

def tb(msg)
{
	sendPush(msg)
}

def switch_PTN(evt)
{
	if (evt.value=="on")
	{
		tb("[PhúcThịnhNguyên] Đèn đang sáng")
	}
	
    if (evt.value=="off")
	{
		tb("[PhúcThịnhNguyên] Đèn đã tắt")
	}
}

def ala_PTN(evt)
{
  if (evt.value == "strobe") 
  {
    tb("[PhúcThịnhNguyên]Báo động đang nhấp nháy đèn")
  }
  if (evt.value == "siren") 
  {
    tb( "[PhúcThịnhNguyên]Báo động đang phát âm thanh")
  } 
  
  if (evt.value == "off") 
  {
    tb("[PhúcThịnhNguyên]Đã tắt báo động")
  } 
}

def motion_PTN(evt)
{
	def timeofB = timeToday(timeB)
	def timeofE= timeToday(timeE)
	def timeC=now()
	def tt=presenceGiau.currentValue("presence")

		if(evt.value=="active")
		{    
    		if ((timeofB<=timeofE && timeC >= timeofB.time && timeC<=timeofE.time && sel == "on" && tt!="present")
    					||(timeofB>=timeofE && timeC >= timeofB.time || timeC<=timeofE.time && sel == "on" && tt!="present" ))
        		{
        			tb("[PhúcThịnhNguyên]Có chuyển động ở Hiệu vàng!")
        		}
		}
}

def presence_Giau(evt)
{
	if(evt.value=="present")
	{
        tb("Chào bạn đã đến HV Phúc Thịnh Nguyên! ")
	}
	if(evt.value=="not present")
	{
    	tb("Bạn đã rời khỏi HV Phúc Thịnh Nguyên! ") 
	}   
}


public boolean andPresence()
{
	def tt=presenceGiau.currentValue("presence");
	def ttp=presenceGiauiPhone.currentValue("presence");  
	return((tt!="present")&&(ttp!="present"))
}

public boolean orPresence()
{
	def tt=presenceGiau.currentValue("presence");
	def ttp=presenceGiauiPhone.currentValue("presence");  
	return((tt=="present")||(ttp=="present"))
}


