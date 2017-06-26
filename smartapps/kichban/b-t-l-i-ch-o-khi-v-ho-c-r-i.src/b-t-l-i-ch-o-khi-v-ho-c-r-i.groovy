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
        input("presence","capability.presenceSensor",title:"Cảm biến hiện diện")
        input("iPhone","capability.presenceSensor",title:"iPhone của bạn")
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
    subscribe(presence,"presence",presence_)
    subscribe(iPhone,"presence",iPhone_)
}

def presence_(evt)
{
	if (sel=="on")
	{
        if(evt.value=="present")
        {
            thongbao("Chào bạn đã về Nhà! ")
        }

        if(evt.value=="not present")
        {
            thongbao("Bạn đã rời khỏi Nhà!")
        }
   } 
}

def iPhone_(evt)
{
	if (sel=="on")
    {
		if(evt.value=="present")
		{
        	thongbao("Chào bạn đã về Nhà! ")
		}
		if(evt.value=="not present")
		{
    		thongbao("Bạn đã rời khỏi Nhà!")
		}
	}
}