# Axyz Gems v1.21
**ROLI Lightpad Block** code for running as a high-res multi XY surface.

##### Table of Contents
[Introduction](#introduction)  
[Control Changes](#control-changes)  
[Operation](#operation)  
[Parameters](#parameters)  
[Installation](#installation)  
[Heads up!](#heads-up)  
[Integration](#integration)  

## Introduction

This LittleFoot script will turn a **ROLI Lightpad Block** into a high-res multi XY surface. Multi as in five times... for five fingers... at the same time!

For every finger (touch) it shows a pulsing (on the beat if available) gem in a bright colour. The position of these  gems are translated into MIDI Control Changes which can be mapped to anything in modern DAW's (Digital Audio Workstations) such as Ableton Live. A setup page on the device itself facilitates the easy creation of this kind of mappings.

I created this script because I really enjoy the plugins by U-he (Hive & Zebra2 in particular). These plugins have very nice XY controls which I map through Ableton Live (with Absolute 14-bit precision). These plugins also have CC 2 (breath) and CC 11 (expression) mapped as extra controllers, so the first gem (orange) uses these.

To see the gems operate bi-directional, make sure the mapped control values are send back to the device. In case of Ableton Live, ensure the **Remote** option is selected with your **Output: Lightpad Block** MIDI setup. If you like to see the gems pulsing on the beat, make sure **Sync** is selected as-well.


## Control Changes

The arrangement of gems is as follows:

| Gem  | Colour | Indicator | CC X | CC Y | CC Z |
|:----:|:------:|:---------:|:----:|:----:| :---:|
|   1  | orange |    none   |   2  |  11  |  14  |
|   2  |  red   |     NE    |  20  |  21  |  22  |
|   3  |  green |     SE    |  23  |  24  |  25  |
|   4  |  blue  |     NW    |  26  |  27  |  28  |
|   5  |  pink  |     SW    |  29  |  30  |  31  |

## Operation

Use one to five fingers at once to place & move the gems on the pad. The first touch will correspond to the **Primary Gem** (default is 1: orange).Increasing the pressure will scale the gems outward (positive **Z Scale**) or inward (negative **Z Scale**).

Pressing the **Side Button** shortly will advance (and possibly wrap around) the **Primary Gem**, shown by the **Indicator** (see table above).

Pressing the **SideButton** a bit longer will open the **Setup Page** from which you can easily map your DAW's controls. Here selecting a dimension (X, Y or Z) and tapping one of the gems will only send out the corresponding the MIDI CC's (which prevents confusing the receiver). Press the **Side Button** again to leave this page, and return to normal operation.

## Parameters

The **Orientation** give the (generally missing) option to have your ROLI Lightpad Block oriented in any way. This is especially handy if you combine the Lightpad Block with the Seaboard Block, and want to have it connected Seaboard Block left and Lightpad Block right. Without this option the **Side Button** would be obstructed.

The **Active Gems**  determine which gems are accesible. So 'All gems' will work with the full 5 gems, and 'Only Gem 3' will work only with Gem 3. The last option 'Only Gems 2-5' is handy if you want only the last 4 gems to access 4 XY pads in your software.

The **Primary Gem** determines which gem is selected by the first touch (finger), the next gem is accessible by the second touch, etc.

If the option **Z Scale** allows pressure to influence (amplify) the actual position of the gem. The **Z Threshold** determines to amount of pressure needed before the scaling kicks in.

The **High resolution** option will (when selected) send out two CC's per dimension per gem, as such increasing the resolution. This is done according to the MIDI specification by sending out an extra CC (called LSB) for every value (called MSB). Most modern DAW's understand this mode (double CC's) as 14-bit CC's.

The **MIDI Channel** determines the MIDI channel used for sending the CC's.

The **Recorder** offers a *Per Beat* option, which will hold and playback all the values per beat. (This option might expand in the future)
A large beat counter will show up, and advance. For this option to work, a MIDI clock signal should be sent to the **Lightpad Block**, as it needs to know the beat.


## Installation

To install this script save (only) the **Axyz Gems.littlefoot** script (<a href="https://raw.githubusercontent.com/jorisroling/axyz-gems/master/Axyz%20Gems.littlefoot" target="_blank">here</a>)  in the default **ROLI Blocks Littlefoot** location of your platform (On Macos this is **~/Documents/ROLI/LittleFoot**). Now whenever you start the **ROLI Dashboard**, you will see the **Axyz Gems** app next to the default apps. Select it, and start tweaking... Enjoy! 😊

### Heads up!

It has come to my attention that some users download the **Axyz Gems.littlefoot** by right clicking the file (above) and selecting _download_ as an option. Saving that way will in fact dowload the container HTML file, and so it is impossible for **ROLI Dashboard** to upload the script to the **Lightpad BLOCK**. You will typicly be warned by an alert saying something like "Line 7, Column1: Found "<" when expecting a global variable or function". Please use the instructions above, or if you prefer, download the full release including these instructions by clicking [here](https://github.com/jorisroling/axyz-gems/releases/latest) and then click **Source code(zip)**.

## Integration

For enjoy the full joy of **Axyz Gems**, including high-resolution, there are dedicated Controller Surface scripts for Bitwig and Ableton.

### Bitwig

There are two 'drivers' for Bitwig, the **AxysGems.control.js** which is a Javascript controller, and a (bit more advanced) Bitwig Extension  called **AxyzGems.bwextension**. The latter is superior, as it allows to match your Axyz Gems setup (in ROLI Dashboard) to be specified. The other assumes the default settings.

To install the Bitwig Extension, copy the file **Bitwig/AxyzGems.bwextension** (get it <a href="https://raw.githubusercontent.com/jorisroling/axyz-gems/master/Bitwig/AxyzGems.bwextension" target="_blank">here</a>) to your **Documents/Bitwig Studio/Extensions** folder. Start Bitwig, go to **Bitwig Settings** -> **Controllers**, add the controller by clicking **Add** and selecting **Bonboa** -> **Axyz Gems Controller (by Joris Röling)**, set the MIDI in and out to **Lightpad** or **Seaboard**) and your good to go. The 8 macro knobs of the selected page of the selected device will be mapped to Gem 2 - 5.

For Bitwig Controll copy the **Bitwig/AxyzGems.control.js** (get it <a href="https://raw.githubusercontent.com/jorisroling/axyz-gems/master/Bitwig/AxyzGems.control.js" target="_blank">here</a>) to your **Documents/Bitwig Studio/Controller Scripts** folder. Start Bitwig, go to **Bitwig Settings** -> **Controllers**, add the controller by clicking **Add** and selecting **Bonboa** -> **Axyz Gems Controller (by Joris Röling)**, set the MIDI in and out to **Lightpad** or **Seaboard**) and your good to go. The 8 macro knobs of the selected page of the selected device will be mapped to Gem 2 - 5.

As an alternative I have included the **Bitwig/Axyz Gems.flexi** file that can be used as import for the brilliant **Generic Flexi** controller by [Jürgen Moßgraber](http://www.mossgrabers.de/Software/Bitwig/Bitwig.html). Any selected device page parameter will now be controlled by the Gems. Works great with XY device pages for U-He Hive & Zebra2.

### Ableton

For Ableton copy the entire **Ableton/Axyz_Gems** folder to your **MIDI Remote Scripts** folder. Start Ableton, go to **Preferences** -> **Link MIDI**, select **Axyz Gems** in an empty **Control Surface** slot, select **Lightpad** or **Seaboard** in **Input** and **Output** and your good to go. The 8 macro knobs of a selected grouped device will be mapped to Gems 2 - 5.