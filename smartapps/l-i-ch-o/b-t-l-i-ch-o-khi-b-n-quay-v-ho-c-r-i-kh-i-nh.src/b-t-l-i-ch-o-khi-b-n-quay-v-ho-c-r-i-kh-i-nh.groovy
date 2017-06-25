definition(
    name: "Bật lời chào khi bạn quay về hoặc rời khỏi nhà",
    namespace: "Lời chào",
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
        input("presenceiPhone","capability.presenceSensor",title:"iPhone của Tôi")
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
   subscribe(presenceiPhone,"presence",presence_iPhone)
}

def presence_iPhone(evt)
{
	if (sel=="on")
    {
		if(evt.value=="present")
		{
        	thongbao("[iPhone]Chào bạn đã về Nhà! ")
		}
		if(evt.value=="not present")
		{
    		thongbao("[iPhone]Bạn đã rời khỏi Nhà! An toàn nhé!")
		}
	}
}