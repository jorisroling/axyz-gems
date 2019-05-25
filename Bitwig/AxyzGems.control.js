loadAPI(7);

host.setShouldFailOnDeprecatedUse(true);
host.defineController("Bonboa", "Axyz Gems Control", "1.21", "7f4b4851-911b-4dbf-a6a7-ee7801296c7e", "Joris Röling");

host.defineMidiPorts(1, 1);

if (host.platformIsWindows()) {
  host.addDeviceNameBasedDiscoveryPair(["Lightpad BLOCK"], ["Lightpad BLOCK"]);
} else if (host.platformIsMac()) {
  host.addDeviceNameBasedDiscoveryPair(["Lightpad BLOCK"], ["Lightpad BLOCK"]);
} else if (host.platformIsLinux()) {
  host.addDeviceNameBasedDiscoveryPair(["Lightpad BLOCK"], ["Lightpad BLOCK"]);
}

var remoteControlsBank = null;

var AXYZ_CC_MSB = [20, 21, 23, 24, 26, 27, 29, 30];

var AXYZ_CC_LSB = [];

for (var a = 0; a < AXYZ_CC_MSB.length; a++)
if (AXYZ_CC_MSB[a] < 32) AXYZ_CC_LSB.push(AXYZ_CC_MSB[a] + 32)

var BOOLEAN_OPTIONS = [ "Off", "On" ];
var LAYOUT_COLUMNS_MAP = [0, 4, 1, 5, 2, 6, 3, 7];
var REVERSE_LAYOUT_COLUMNS_MAP = [0, 2, 4, 6, 1,3, 5, 7];

var LAYOUT_OPTIONS = [ "Rows", "Columns" ];

function doObject (object, f)
{
    return function ()
    {
        f.apply (object, arguments);
    };
}

var highRes = true;
var layoutColumns = true;

var translateWithMap = true;

function init() {
  var controls=[];
  for (var c=0;c<128;c++) controls.push(i+'')
  var preferences = host.getPreferences ();

  preferences.getEnumSetting ("Enable", "High Resolution", BOOLEAN_OPTIONS, BOOLEAN_OPTIONS[1]).addValueObserver (function (value) {
    highRes = value == BOOLEAN_OPTIONS[1];
  });
  preferences.getEnumSetting ("Layout", "Button Order", LAYOUT_OPTIONS, LAYOUT_OPTIONS[0]).addValueObserver (function (value) {
    layoutColumns = (value == LAYOUT_OPTIONS[1]);
  });



  host.getMidiInPort(0).setMidiCallback(handleMidi);

  var cursorTrack = host.createCursorTrack("AXYZ_GEMS_CURSOR_TRACK", "Cursor Track", 0, 0, true);

  var cursorDevice = cursorTrack.createCursorDevice("AXYZ_GEMS_CURSOR_DEVICE", "Cursor Device", 0, CursorDeviceFollowMode.FOLLOW_SELECTION);
  // var primaryDevice = cursorTrack.getPrimaryDevice();

  remoteControlsBank = cursorDevice.createCursorRemoteControlsPage(8);
  remoteControlsBank.selectedPageIndex().markInterested();

		for (var i=0; i< 2; i++) {
			preferences.getEnumSetting((i % 2) == 0 ? "Previous" : "Next", "Device Preset", controls, ''+(32+i)).addValueObserver( function(value) {
				var control = parseInt(value);
				if (control == 32) {
          println("Previous Preset");
//					primaryDevice.switchToPreviousPreset();
				} else if (control == 32 + 1) {
          println("Next Preset");
//					primaryDevice.switchToNextPreset();
				}
			});
		}

  function setupParameter(i) {
    const parameter = remoteControlsBank.getParameter(i);
    parameter.markInterested();
    parameter.setIndication(true);

    parameter.value().addValueObserver (function (value) {
      const idx = (layoutColumns ? REVERSE_LAYOUT_COLUMNS_MAP[i] : i);

      if (values[idx] != (value * 16383)) {
        sendMidi(0xB0,AXYZ_CC_MSB[idx],((value * 16383) >> 7) & 0x7F);
        if (highRes) sendMidi(0xB0,AXYZ_CC_LSB[idx],((value * 16383) >> 0) & 0x7F);
      }
    });
  }

  for (var i = 0; i < remoteControlsBank.getParameterCount(); i++) {
    setupParameter(i);
  }

  cursorDevice.isEnabled().markInterested();
  cursorDevice.isWindowOpen().markInterested();

  println("Axyz Gems initialized!");
}

const values = [];

function handleMidi(status, data1, data2) {
  if (isChannelController(status)) {
    var idx = AXYZ_CC_MSB.indexOf(data1);
    if (idx >= 0) {
      values[idx] = (values[idx] & (0x7F << 0)) | (data2 << 7);
      remoteControlsBank.getParameter(layoutColumns ? LAYOUT_COLUMNS_MAP[idx] : idx).set(values[idx], 16384);
    } else if (highRes) {
      idx = AXYZ_CC_LSB.indexOf(data1);
      if (idx >= 0) {
        values[idx] = (values[idx] & (0x7F << 7)) | (data2 << 0);
        remoteControlsBank.getParameter(layoutColumns ? LAYOUT_COLUMNS_MAP[idx] : idx).set(values[idx], 16384);
      }
    }
  }
  return false;
}

function flush() {
}

function exit() {
  println("Axyz Gems exited!");
}
