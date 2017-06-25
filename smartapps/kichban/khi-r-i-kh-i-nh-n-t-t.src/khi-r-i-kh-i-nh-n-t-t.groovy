definition(
    name: "Khi rời khỏi nhà đèn tắt",
    namespace: "KichBan",
    author: "Võ Thanh Minh",
    description: "Kịch bản điều khiển thiết bị dựa vào thói quen, sở thích và mệnh lệnh",
    category: "Safety & Security",
    iconUrl: "https://s3.amazonaws.com/vtmsmartthings/vtms60.png",
    iconX2Url: "https://s3.amazonaws.com/vtmsmartthings/vtms120.png",
    iconX3Url: "https://s3.amazonaws.com/vtmsmartthings/vtms120.png")
//Test: OK

preferences 
{
    section("Kích hoạt kịch bản")
    {
        input name:"sel",type:"enum", title:"Hoạt động", options: ["on","off"], defaultValue:"off"
    }
    section("Chọn công tắc")
    {
   		input("swCC","capability.switch",title:"Đèn Cửa chính")     
        input("swT2","capability.switch",title:"Đèn Tầng 2")
        input("swT3","capability.switch",title:"Đèn Tầng 3")
        input("swPK","capability.switch",title:"Đèn Phòng khách")
    }
    section("Cảm biến hiện diện")
    {
    	input("presenceNguyen","capability.presenceSensor",title:"Cảm biến hiện diện Nguyên")
       	input("presenceNguyeniPhone","capability.presenceSensor",title:"iPhone của Nguyen")
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
    subscribe(swCC,"switch",sw_CC)
    subscribe(swT2,"switch",sw_T2)
    subscribe(swT3,"switch",sw_T3)
    subscribe(swPK,"switch",sw_PK)
    
    subscribe(presenceNguyen,"presence",presence_Nguyen)
    subscribe(presenceNguyeniPhone,"presence",presence_NguyeniPhone)
}

def presence_Nguyen(evt)
{    
	if (evt.value == "present")
	{
            
	}
    else
      if (sel == "on")
    {
    		swCC.off()
			swT2.off()
			swT3.off()
			swPK.off()	
           
    }
}

def presence_NguyeniPhone(evt)
{
	if (evt.value == "present")
	{
            
	}
    else
      if (sel == "on")
    {
    		swCC.off()
			swT2.off()
			swT3.off()
			swPK.off()	
    }
}