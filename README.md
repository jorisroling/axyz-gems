# Axyz Gems
**ROLI Lightpad Block** code for running as a high-res multi XY surface.

This LittleFoot script will turn a **ROLI Lightpad Block** into a high-res multi XY surface. Multi as in five times... for five fingers... at the same time!

For every finger (touch) it shows a pulsing (on the beat if available) gem in a bright colour. The position of these  gems are translated into MIDI Control Changes which can be mapped to anything in modern DAW's (Digital Audio Workstations) such as Ableton Live. A configuration mode on the device itself facilitates the easy creation of this kind of mappings.

I created this script because I really enjoy the plugins by U-he (Hive & Zebra2 in particular). These plugins have very nice XY pages which I map through Ableton Live (with Absolute 14-bit precision). These plugins also have CC 3 (breath) and CC 11 (expression) mapped as extra controllers, so the first gem (orange) uses these.

To see the gems operate bi-directional, make sure the mapped control values are send back to the device. In case of Ableton Live, ensure the **Remote** option is selected with your **Output: Lightpad Block** MIDI setup. If you like to see the gems pulsing on the beat, make sure **Sync** is selected aswell.

## Control Changes
The arrangement of pages is as follows:

| Gem  | Colour | CC X | CC Y | CC Z |
|:----:|:------:|:----:|:----:| :---:|
|   0  | orange |   3  |  11  |  14  |
|   1  |  red   |  20  |  21  |  22  |
|   2  |  green |  23  |  24  |  25  |
|   3  |  blue  |  26  |  27  |  28  |
|   4  |  pink  |  29  |  30  |  31  |

## Parameters

The **Primary Gem** determines which gem is selected by the first touch (finger), the next gem is accessible by the second touch, etc.

If the option **Z Scale** allows pressure to influence (amplify) the actual position of the gem. The **Z Threshold** determines to amount of pressure needed before the scaling kicks in.

The **High resolution** option will (when selected) send out two CC's per dimension per gem, as such increasing the rsolution. This is done according to the MIDI specification by sending out an extra CC (called LSB) for every value (called MSB). Most modern DAW's understand this mode (double CC's) as 14-bit CC's.

The **MIDI Channel** determines the MIDI channel used for sending the CC's.


## Setup Page
To access the setup page, hold down the side button for a while. You' ll be presented by a page showing the dimesnions X, Y & Z, and the five gems. One of the dimensions will be highlighted. By pressing any one of the dimensions it will be selected.By touching one of the gems, the corresponding CC for that gem and the selected dimnension will be send so that a easy mapping in your DAW can be made. To leave the setup page, a short press of the side button will suffice.

## Installation

To install this script save the **Axyz Gems.littlefoot** script in the default **ROLI BLocks Littlefoot** location of your platform (On Macos this is **~/Documents/ROLI/LittleFoot**). Now whenever you start the **ROLI Dashboard**, you will see the **Axyz Gems** app next to the defauilt apps. Select it, and start tweeking... Enjoy! 😊