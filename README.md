# AdvancedAI
AdvancedAI Selection Option for Command and Conquer Generals Zero Hour

## Features
- Works on the Mac version of Command and Conquer Generals! 
- Works with the [ModDB Zero Hour Advanced AI Mod ](http://www.moddb.com/mods/advanced-ai-mod-for-zero-hour-improved-by-aei/downloads/advanced-ai-mod-098-improved-by-aei-v28)
- No need to download this ModDB Zero Hour AI mod (this script has the mod included)
- Toggle advanced AI with 1 click and Toggle back to normal with another click! 
- Auto-detects whether Advanced AI are enabled or not
- Well tested to ensure it will work on any and every Mac.
- Auto Setup with a fresh copy of Command and Conquer! (See more in the How to Use section)

## How to Use
This script makes it simple to toggle the Advanced AI modification for Command & Conquer Generals Zero Hour for Mac! So if your getting tired of advanced AI simply run this program and with a click of a button you can revert back to normal AI and vice versa!

- Clone this repo (click the big green button that says "Clone or Download"
- Open the containing folder (Should be Titled `AdvancedAI`)
- Double click the JAR file (`AdvancedAI1.jar`) to run the program!


This software automatically handles all the setup it requires! It will auto-detect if you have an AdvancedAI mod installed and create a backup folder within the C&C generals app. The software will then proceed to create either the AdvancedAI mod file you need or the Normal AI mod file you need depending on what it finds in your `/scripts` folder! 


At this point the software has everything it needs to toggle the AdvancedAI on or off with the click of a button have fun! Finally a software that "Just works" enjoy!


If all goes well you should see a nice little interface to Toggle the AI **Make sure you follow the requirements before playing with the program**

## Requirements
There are just a few requirements to use this software.

- The Command and Conquer Generals app downloaded from the app store **MUST** be located in the Applications folder on the Mac.
- This script assumes that **NO** other modifications have been done to the `/scripts` portion of command and conquer generals (modding the INIZH.big file and other files is perfectly fine) if so it could render this software useless
- If you have modified your `/scripts` folder (i.e. by adding a backup folder with other SkirmishScripts.scb files) ensure that you remove any modifications made to your `/scripts` folder so that it looks like this
![alt text](https://image.ibb.co/k2LcV5/scripts_folder.png "What your scripts folder should look like")
- You must know how to clone (download) this repo and double click and run a jar file. Literally you just double click it....


## Version Log

1.0.0
- Initial Release

1.1.0 
- Added Setup script to ensure your c&c generals app is ready to be used by this software
- Added Jar file for easy running

1.2.0 
- Fixed Setup to work anywhere that its run
- Error logging in the App
- Comes with both the Advanced & Normal Mod files
