loadAPI(7);

host.setShouldFailOnDeprecatedUse(true);
host.defineController("Bonboa", "Axyz Gems", "0.2", "7f4b4851-911b-4dbf-a6a7-ee7801296c7e", "Joris Röling");

host.defineMidiPorts(1, 1);

if (host.platformIsWindows()) {
  host.addDeviceNameBasedDiscoveryPair(["Lightpad BLOCK"], ["Lightpad BLOCK"]);
} else if (host.platformIsMac()) {
  host.addDeviceNameBasedDiscoveryPair(["Lightpad BLOCK"], ["Lightpad BLOCK"]);
} else if (host.platformIsLinux()) {
  host.addDeviceNameBasedDiscoveryPair(["Lightpad BLOCK"], ["Lightpad BLOCK"]);
}

var hardware = null;
var remoteControlsBank = null;

var AXYZ_CC_MSB = [20, 21, 23, 24, 26, 27, 29, 30];

var AXYZ_CC_LSB = [];

for (var a = 0; a < AXYZ_CC_MSB.length; a++)
if (AXYZ_CC_MSB[a] < 32) AXYZ_CC_LSB.push(AXYZ_CC_MSB[a] + 32)

var XY_MAP = [0, 4, 1, 5, 2, 6, 3, 7];
var rev_XY_MAP = [0, 2, 4, 6, 1,3, 5, 7];

var BOOLEAN_OPTIONS = [ "Off", "On" ];

function doObject (object, f)
{
    return function ()
    {
        f.apply (object, arguments);
    };
}

var highRes = true;
var diverge = true;

var translateWithMap = true;

function init() {

  var preferences = host.getPreferences ();

  preferences.getEnumSetting ("Enable", "High Resolution", BOOLEAN_OPTIONS, BOOLEAN_OPTIONS[1]).addValueObserver (function (value) {
    highRes = value == BOOLEAN_OPTIONS[1];
    println('highRes = '+highRes);
  });
  preferences.getEnumSetting ("Enable", "Diverge Mapping", BOOLEAN_OPTIONS, BOOLEAN_OPTIONS[1]).addValueObserver (function (value) {
    diverge = value == BOOLEAN_OPTIONS[1];
    println('diverge = '+diverge);
  });



  host.getMidiInPort(0).setMidiCallback(handleMidi);

  var cursorTrack = host.createCursorTrack("MOXF_CURSOR_TRACK", "Cursor Track", 0, 0, true);

  var cursorDevice = cursorTrack.createCursorDevice("MOXF_CURSOR_DEVICE", "Cursor Device", 0, CursorDeviceFollowMode.FOLLOW_SELECTION);

  remoteControlsBank = cursorDevice.createCursorRemoteControlsPage(8);

  function para(i) {
    const parameter = remoteControlsBank.getParameter(i);
    parameter.markInterested();
    parameter.setIndication(true);

    parameter.addValueObserver (function (value) {
      const idx = (diverge ? rev_XY_MAP[i] : i);
      println('parameter '+ (diverge ? rev_XY_MAP[i] : i)  + ' = '+value);

      sendMidi(0xB0,AXYZ_CC_MSB[idx],((value * 16383) >> 7) & 0x7F);
      if (highRes) sendMidi(0xB0,AXYZ_CC_LSB[idx],((value * 16383) >> 0) & 0x7F);
    });
  }

  for (var i = 0; i < remoteControlsBank.getParameterCount(); i++) {
    para(i);
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
      remoteControlsBank.getParameter(diverge ? XY_MAP[idx] : idx).set(values[idx], 16384);
    } else if (highRes) {
      idx = AXYZ_CC_LSB.indexOf(data1);
      if (idx >= 0) {
        values[idx] = (values[idx] & (0x7F << 7)) | (data2 << 0);
        remoteControlsBank.getParameter(diverge ? XY_MAP[idx] : idx).set(values[idx], 16384);
      }
    }
  }
  return false;
}

function flush() {
  // transportHandler.updateLEDs ();
}

function exit() {
  println("Axyz Gems exited!");
}
