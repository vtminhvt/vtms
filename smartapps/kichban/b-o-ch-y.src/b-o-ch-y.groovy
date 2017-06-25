definition(
    name: "Báo cháy ",
    namespace: "KichBan",
    author: "Võ Thanh Minh",
    description: "Kịch bản điều khiển",
    category: "Safety & Security",
    iconUrl: "https://s3.amazonaws.com/vtmsmartthings/vtms60.png",
    iconX2Url: "https://s3.amazonaws.com/vtmsmartthings/vtms120.png",
    iconX3Url: "https://s3.amazonaws.com/vtmsmartthings/vtms120.png")
//Test:OK
preferences {
  section("Báo cháy"){
   input "smokeH", "capability.smokeDetector", title: "Chọn thiết bị"
  }
  
section("Báo động Nhà"){
   input("alamH","capability.alarm",title:"Báo động ở Nhà")
   }
}

def init()
{
	subscribe(smokeH, "smoke", smoke_H)
  	subscribe(alamH, "alarm", alam_H)
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

def smoke_H(evt) {
  if("detected" == evt.value) 
  {
  	alamH.siren()
  	sendPush("Phát hiện cháy tại Khu gia công. Hãy kiểm tra lại")
    sendSms("+840948557126","Phát hiện cháy tại Khu gia công. Hãy kiểm tra lại")
  }
   if("tested" == evt.value) 
  {
  	alamH.siren()
  	sendPush("[Đang sử dụng nút Test]: Phát hiện cháy tại Khu gia công. Hãy kiểm tra lại")
    sendSms("+840948557126","Phát hiện cháy tại Khu gia công. Hãy kiểm tra lại")
  }
}