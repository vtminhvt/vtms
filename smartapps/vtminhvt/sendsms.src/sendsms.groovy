definition(
    name: "sendSMS",
    namespace: "vtminhvt",
    author: "Vo Thanh Minh",
    description: "Vo Thanh Minh",
    category: "",
    iconUrl: "https://s3.amazonaws.com/vtmsmartthings/vtms60.png",
    iconX2Url: "https://s3.amazonaws.com/vtmsmartthings/vtms120.png",
    iconX3Url: "https://s3.amazonaws.com/vtmsmartthings/vtms120.png")


preferences {
	section("Title") {
		input("a1","capability.switch",title:"a1")
	}
}

def installed() {
	log.debug "Installed with settings: ${settings}"
	
	
    subscribe(a1,"switch",a_1)
}

def updated() {
	log.debug "Updated with settings: ${settings}"
	
	unsubscribe()
    subscribe(a1,"switch",a_1)	
}

def a_1(evt)
{  
sendPush("[Thông báo]Phát hiện gián đoạn, Hãy tháo và lắp lại Pin của Cảm biến chuyển động tại PTN!")
//sendPush("[Thông báo PTN]Để đảm bảo hoạt động chính xác. \n Bạn hãy thay Pin có mã số CR2032 cho Cảm biến hiện diện ở PTN với tên thiết bị[Giàu].")
//sendPush("SMARTTHINGS report : 63% thiết bị của bạn đang hoạt động tốt!. Một số thiết bị không hoạt đông do Pin đã cạn!")
//sendSms("+840948557126", "[SmartThings]Phát hiện gián đoạn, Hãy tháo và lắp lại Pin của Cảm biến chuyển động tại PTN!")
//sendSms("+840919501905", "[SmartThings]Phát hiện gián đoạn, Hãy tháo và lắp lại Pin của Cảm biến chuyển động tại PTN!")
}