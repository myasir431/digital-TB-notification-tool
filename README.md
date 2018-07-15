# digital-TB-notification-tool
DTNT is a research tool that allows doctors to collect TB patient data and send to the server using SMS. It's an open source project. If any one working in mHealth and looking for simmilar kind of work can use this project and modify it according to the requirement. 
UserInterface is the user application that will be used by doctors. They will login to the app and provide patient information and can also view data that have sent. 
SMSReceiver is local server that will receive sms decrypt and after adding sender information encrypt datagram and send to the web interface using HTTP Post.
Web interface folder contain php files. Whenever it receive data it will generate notification on web portal display data in graphical form.
