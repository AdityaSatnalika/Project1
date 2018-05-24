_______________________________________________________
ABOUT THE APPLICATION
_______________________________________________________

1. The application utilises the Property of Android Broadcast 
   and services.
2. This app works to capture the Number of time any app in mobile
   try to use either camera wifi location and phone services .
3. The wifi broadcast checks how many times we switched on
   the wifi and how many time we were connected to netwrok.
4. The location broadcast captures how many time location is turned
   on and how many times hotspot is switched on.
5. The Phone broadcast helps us to capture the state of phone call
   i.e. either received state , idle or hang up . and also tell us in
   received state the number whom we are talking to.
6. The camera is not a broadcast but a service , but that service in 
   coupled with broadcast i.e. the service is only launched whenever 
   an app that uses camera permission is launched . The service then
   check continuosly till app is in memory for camera hardware avai-
   lability . This helps to save Lot of battery as service is not 
   running always.


This app is helful for tacking feature or for anitvirus and security apps tocheck if there is
no misuse of any features of system.