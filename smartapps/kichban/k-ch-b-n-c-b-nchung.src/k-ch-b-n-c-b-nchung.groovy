definition(
    name: "Kịch bản cơ bản,chung",
    namespace: "KichBan",
    author: "Võ Thanh Minh",
    description: "Trình điều khiển, giao tiếp, ứng dụng cơ bản",
    category: "Safety & Security",
    iconUrl: "https://s3.amazonaws.com/vtmsmartthings/vtms60.png",
    iconX2Url: "https://s3.amazonaws.com/vtmsmartthings/vtms120.png",
    iconX3Url: "https://s3.amazonaws.com/vtmsmartthings/vtms120.png")


preferences {
	section("CB Chuyển động")
    {
    	input("motionCC","capability.motionSensor",title:"CB chuyển động T1")
        input("motionT2","capability.motionSensor",title:"CB chuyển động Phòng Khách")  	
    }
    section("Cảm biến hiện diện")
    {
    	input("presenceNguyen","capability.presenceSensor",title:"Cảm biến hiện diện Nguyên")
       	input("presenceNguyeniPhone","capability.presenceSensor",title:"iPhone của Nguyen")
    }
    
    section("Công tắc điện")
    {
    	input("swCC","capability.switch",title:"Đèn Cửa chính")     
        input("swT2","capability.switch",title:"Đèn Tầng 2")
        input("swT3","capability.switch",title:"Đèn Tầng 3")
        input("swPK","capability.switch",title:"Đèn Phòng khách")
        
    }
    
    section("Cảm biến đóng mở")
    {
    	input("csCC","capability.contactSensor",title:"Cảm biến Cửa chính")
    	input("csT2","capability.contactSensor",title:"Cảm biến Tầng 2")
    	input("csT3","capability.contactSensor",title:"Cảm biến Tầng 3")
    	input("csT4","capability.contactSensor",title:"Cảm biến Tầng 4")
        input("csT4s","capability.contactSensor",title:"Cảm biến Tầng 4 sau")
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
	subscribe(motionCC,"motion",motion_CC)
    subscribe(motionT2,"motion",motion_T2)
    
    subscribe(presenceNguyen,"presence",presence_Nguyen)
    subscribe(presenceNguyeniPhone,"presence",presence_NguyeniPhone)
    
    subscribe(swCC,"switch",sw_CC)
    subscribe(swT2,"switch",sw_T2)
    subscribe(swT3,"switch",sw_T3)
    subscribe(swPK,"switch",sw_PK)
    
    subscribe(csCC,"contact",cs_CC)
    subscribe(csT2,"contact",cs_T2)
    subscribe(csT3,"contact",cs_T3)
    subscribe(csT4,"contact",cs_T4)
    subscribe(csT4s,"contact",cs_T4s)
    
    subscribe(alaH,"alarm",ala_H)
  
}
def time_CB(evt)
{
	log.debug "${evt.value}"
}

def ala_H(evt)
{
	if (evt.value == "strobe")
  {
    tb("[Nhà]Báo động đang nhấp nháy đèn")
    schedule(now()+15*1000,alamF)
  } 
  if (evt.value == "siren") 
  {
    tb( "[Nhà]Báo động đang phát âm thanh")
  }
  if (evt.value == "both") {
    tb ("[Nhà]Báo động đang phát đèn và âm thanh")
  } 
  if (evt.value == "off") {
    tb("[Nhà]Đã tắt báo động")
  } 
}
def alamF()
{
	alaH.off();
}
def sw_CC(evt)
{
//if(evt.value=="on") 	{thongbao("Đèn cổng sáng")}
//else if(evt.value=="off") 	{thongbao("Đèn cổng đã tắt")}
}

def sw_PK(evt)
{
if (!orPresence)
{
	//if(evt.value=="on"){ 	thongbao("Đèn Phòng khách sáng") 	}
	//else if(evt.value=="off")  	{ 	thongbao("Đèn Phòng khách đã tắt")	}
}
}

def sw_T2(evt)
{
if (!orPresence)
{
	if(evt.value=="on")	{thongbao("Đèn ở Hiên tầng 2 sáng")}
    else if(evt.value=="off")    {thongbao("Đèn ở Hiên tầng 2 đã tắt")}
}
}

def sw_T3(evt)
{
if (!orPresence)
{
if(evt.value=="on") {thongbao("Đèn ở Hiên tầng 3 sáng")}
else if(evt.value=="off") {thongbao("Đèn ở Hiên tầng 3 đã tắt")}
}}

def cs_CC(evt)
{if (!orPresence)
{	if (evt.value=="open")
    {
    //thongbao("Cửa chính đang mở!")
    }
    else
    {
    //thongbao("Cửa chính đã đóng!")
    }
    }
}
def cs_T2(evt)
{
if (!orPresence)
{
	if (evt.value=="open")
    {
    thongbao("Cửa tầng 2 đang mở!")
    }
    else
    {
    thongbao("Cửa tầng 2 đã đóng!")
    }
    }
}
def cs_T3(evt)
{
if (!orPresence)
{
	if (evt.value=="open")
    {
    thongbao("Cửa tầng 3 đang mở!")
    }
    else
    {
    thongbao("Cửa tầng 3 đã đóng!")
    }
 }
 }
  def cs_T4(evt)
{
if (!orPresence)
{	if (evt.value=="open")
    {
    thongbao("Cửa tầng 4 đang mở!")
    }
    else
    {
    thongbao("Cửa tầng 4 đã đóng!")
    }
}}

 def cs_T4s(evt)
{
if (!orPresence)
{
	if (evt.value=="open")
    {
    thongbao("Cửa phía sau tầng 4 đang mở!")
    }
    else
    {
    thongbao("Cửa phía sau tầng 4 đã đóng!")
    }
}
}
def thongbao(msg)
{
	sendPush(msg)
}
def tb(msg)
{
	sendPush(msg)
}
def motion_T2(evt)
{
if (evt.value=="active")
{
	//thongbao("[Testing]Có chuyển động ở cổng!")
    
}


if(evt.value=="active")
{
	    if(! orPresence() )
    {
    	if(getSunriseAndSunset().sunrise.time<now() && getSunriseAndSunset().sunset.time >now())
        {
        //Buổi ngày
        //thongbao("Có chuyển động trong Hiệu vàng PTN!, Trong khi người quản lý vắng mặt")
        }
        else //Ban đêm
        {
        	//thongbao("Có chuyển động bất thường, Bạn hãy xem camera quan sát, hãy nhấn bút báo động nếu cần.")
            //bien=bien+1
            //if (bien==solancd)
            //{
            
            //Đoạn mã cho chuông báo động.
            //bien=0
            //}
        }
    }
}

else
{
	//t=evt.value("temperature")
    //thongbao("${t}")
}
}

public boolean andPresence()
{
	def tt=presenceNguyen.currentValue("presence");
	def ttp=presenceNguyeniPhone.currentValue("presence");  
	return((tt!="present")&&(ttp!="present"))
}

public boolean orPresence()
{
	def tt=presenceNguyen.currentValue("presence");
	def ttp=presenceNguyeniPhone.currentValue("presence");  
	return((tt=="present")||(ttp=="present"))
}