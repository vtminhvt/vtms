definition(
    name: "Bật lời chào khi về hoặc rời",
    namespace: "KichBan",
    author: "Võ Thanh Minh",
    description: "Bạn muốn hệ thống thực hiện lời chào",
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

    section("Cảm biến hiện diện")
    {
        input("presenceNguyen","capability.presenceSensor",title:"Cảm biến hiện diện Nguyên")
        input("presenceNguyeniPhone","capability.presenceSensor",title:"iPhone của Nguyen")
    }//  
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
    subscribe(presenceNguyen,"presence",presence_Nguyen)
    subscribe(presenceNguyeniPhone,"presence",presence_NguyeniPhone)
}

def presence_Nguyen(evt)
{
	if (sel=="on")
	{
        if(evt.value=="present")
        {
            thongbao("[Present]Chào bạn đã về Nhà! ")
        }

        if(evt.value=="not present")
        {
            thongbao("[Present]Bạn đã rời khỏi Nhà!")
        }
   } 
}

def presence_NguyeniPhone(evt)
{
	if (sel=="on")
    {
		if(evt.value=="present")
		{
        	thongbao("[iPhone]Chào bạn đã về Nhà! ")
		}
		if(evt.value=="not present")
		{
    		thongbao("[iPhone]Bạn đã rời khỏi Nhà!")
		}
	}
}