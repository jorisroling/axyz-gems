# Axyz Gems v1.5
**ROLI Lightpad Block** code for running as a high-res multi XY surface.

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

The **Primary Gem** determines which gem is selected by the first touch (finger), the next gem is accessible by the second touch, etc.

If the option **Z Scale** allows pressure to influence (amplify) the actual position of the gem. The **Z Threshold** determines to amount of pressure needed before the scaling kicks in.

The **High resolution** option will (when selected) send out two CC's per dimension per gem, as such increasing the resolution. This is done according to the MIDI specification by sending out an extra CC (called LSB) for every value (called MSB). Most modern DAW's understand this mode (double CC's) as 14-bit CC's.

The **MIDI Channel** determines the MIDI channel used for sending the CC's.

The **Recorder** offers a *Per Beat* option, which will hold and playback all the values per beat. (This option might expand in the future)
A large beat counter will show up, and advance. For this option to work, a MIDI clock signal should be sent to the **Lightpad Block**, as it needs to know the beat.

<!---## Setup Page

To access the setup page, hold down the side button for a while. You' ll be presented by a page showing the dimensions X, Y & Z, and the five gems. One of the dimensions will be highlighted. By pressing any one of the dimensions it will be selected.By touching one of the gems, the corresponding CC for that gem and the selected dimension will be send so that an easy mapping in your DAW can be made. To leave the setup page, a short press of the side button will suffice. -->

## Installation

To install this script save (only) the **Axyz Gems.littlefoot** script (<a href="https://raw.githubusercontent.com/jorisroling/axyz-gems/master/Axyz%20Gems.littlefoot" target="_blank">here</a>)  in the default **ROLI Blocks Littlefoot** location of your platform (On Macos this is **~/Documents/ROLI/LittleFoot**). Now whenever you start the **ROLI Dashboard**, you will see the **Axyz Gems** app next to the default apps. Select it, and start tweaking... Enjoy! 😊
