# Axyz
ROLI Lightpad Block code for running as a high-res multi XY surface

This LittleFoot script will turn a ROLI Lightpad Block into a high-res multi XY surface. Multi as in five times... for five fingers... at the same time!
For every finger (page actually) it shows a pulsing (on the beat if available) gem in a bright colour. The position of these  gems are translated into MIDI Control Changes which can be mapped to anything in modern DAW's (Digital Audio Workstations) such as Ableton Live. A configuration mode on the device itself facilitates the easy creation of this kind of mappings.


The arrangement of pages is as follows:

| Page | Colour | CC X | CC Y | CC Z |
|:----:|:------:|:----:|:----:| :---:|
|   0  | orange |   3  |  11  |  14  |
|   1  |  red   |  20  |  21  |  22  |
|   2  |  green |  23  |  24  |  25  |
|   3  |  blue  |  26  |  27  |  28  |
|   4  |  pink  |  29  |  30  |  31  |

The high-resolution option will send out two CC's per dimension per page, as such increasing the rsolution. This is done according to the MIDI specification by sending out an extra CC (called LSB) for every value (called MSB). Most modern DAW's understand this mode (double CC's) as 14-bit CC's.
