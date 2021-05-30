# AirQualityProject
#This project is for assigment
#Project responsibility was to listen a Websocket open channnel, Collect the data and show it in Graph
#Project is Written on Kotlin
# Followed with MVVM patteren with the help of jetpack Lifecycle, ViewBindig component.
#For web socket connection using a open library 
##implementation "org.java-websocket:Java-WebSocket:1.5.1" for performing the web socket connection.
#For graph using open library MPAndroidChart
#It is simple project having websocket manager class to manage the socket functionalty

#Program flow is Start from AnalyticsActivity.kt -> AnalyticsViewModel.kt - > Socketmanger
#Graph AnalyticDetailsPage.kt -> AnalyticsActivity.kt -> AnalyticsViewModel.kt - > Socketmanger

# I have taken aroung 16 hr to complete this project, Since power cut issue was there

# Also Websoket was new to me since "ws://city-ws.herokuapp.com/" link not supported by Socket.IO open lib package. Because of that i have to explore from scarch
#and then impliment it in proper way in MVVM patteren.

#MPAndroidChart was alos new to me, I has so many features, But due to short time span, I have explored minimal features, Still more toexplore. if i will get more time,
#THen definitely i will love to explore the MPAndroidChart. To use it more efficiently.
