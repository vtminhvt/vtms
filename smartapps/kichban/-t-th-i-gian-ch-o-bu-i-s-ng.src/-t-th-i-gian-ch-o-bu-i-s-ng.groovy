definition(
    name: "Đặt thời gian chào buổi sáng",
    namespace: "KichBan",
    author: "Võ Thanh Minh",
    description: "Kịch bản điều khiển thiết bị dựa vào thói quen, sở thích và mệnh lệnh",
    category: "Safety & Security",
    iconUrl: "https://s3.amazonaws.com/vtmsmartthings/vtms60.png",
    iconX2Url: "https://s3.amazonaws.com/vtmsmartthings/vtms120.png",
    iconX3Url: "https://s3.amazonaws.com/vtmsmartthings/vtms120.png")


preferences 
{
    section("Thời gian chào")
    {
    	input name: "timeCB", type: "time", title: "Chọn thời gian chào"
    } 
}

def installed() 
{
    schedule(timeCB, cb)
}

def updated() 
{
	unschedule()
	schedule(timeCB, cb)
}



def cb()
	{
	sendPush("Good morning, Have a good day.")
    sendPush("Chào buổi sáng, Ngày mới tốt lành.")
	}