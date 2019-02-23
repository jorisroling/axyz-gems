package com.bonboa;

import com.bitwig.extension.api.util.midi.ShortMidiMessage;
import com.bitwig.extension.callback.ShortMidiMessageReceivedCallback;
import com.bitwig.extension.controller.api.ControllerHost;
import com.bitwig.extension.controller.api.CursorDeviceFollowMode;
import com.bitwig.extension.controller.api.CursorRemoteControlsPage;
import com.bitwig.extension.controller.api.CursorTrack;
import com.bitwig.extension.controller.api.PinnableCursorDevice;
import com.bitwig.extension.controller.api.Preferences;
import com.bitwig.extension.controller.api.RemoteControl;
//import com.bitwig.extension.controller.api.Transport;
import com.bitwig.extension.controller.ControllerExtension;

//var BOOLEAN_OPTIONS = [ "Off", "On" ];
//var LAYOUT_COLUMNS_MAP = [0, 4, 1, 5, 2, 6, 3, 7];
//var REVERSE_LAYOUT_COLUMNS_MAP = [0, 2, 4, 6, 1,3, 5, 7];
//
//var LAYOUT_OPTIONS = [ "Rows", "Columns" ];


public class AxyzGemsExtension extends ControllerExtension
{
   protected AxyzGemsExtension(final AxyzGemsExtensionDefinition definition, final ControllerHost host)
   {
      super(definition, host);
   }

   private static final String []  BOOLEAN_OPTIONS = new String []
		   {
				   "Off",
				   "On",
		   	};

   private static final String []  LAYOUT_OPTIONS = new String []
		   {
				   "Rows",
				   "Columns",
		   };
   private static final int []  LAYOUT_COLUMNS_MAP = {0, 4, 1, 5, 2, 6, 3, 7};
   private static final int []  REVERSE_LAYOUT_COLUMNS_MAP = {0, 2, 4, 6, 1,3, 5, 7};

   private static final int []  AXYZ_CC_MSB = {20, 21, 23, 24, 26, 27, 29, 30};
   private static final int []  AXYZ_CC_LSB = {20 + 32, 21 + 32, 23 + 32, 24 + 32, 26 + 32, 27 + 32, 29 + 32, 30 + 32};

   private Boolean highRes = true;
   private Boolean layoutColumns = false;
   private CursorTrack    cursorTrack;
   private PinnableCursorDevice cursorDevice;
   private CursorRemoteControlsPage remoteControlsBank;

   private int [] values = {0,0,0,0,0,0,0,0};

   @Override
   public void init()
   {
      final ControllerHost host = getHost();
//      mTransport = host.createTransport();
      host.getMidiInPort(0).setMidiCallback((ShortMidiMessageReceivedCallback)msg -> onMidi(msg));
//      host.getMidiInPort(0).setSysexCallback((String data) -> onSysex(data));



      Preferences preferences = host.getPreferences();

      preferences.getEnumSetting("Enable", "High Resolution", BOOLEAN_OPTIONS, BOOLEAN_OPTIONS[1]).addValueObserver(value -> {
          this.highRes = (value == BOOLEAN_OPTIONS[1]);
      });

      preferences.getEnumSetting("Layout", "Button Order", LAYOUT_OPTIONS, LAYOUT_OPTIONS[0]).addValueObserver(value -> {
        this.layoutColumns = (value == LAYOUT_OPTIONS[1]);
      });

      this.cursorTrack = host.createCursorTrack("AXYZ_GEMS_CURSOR_TRACK", "Cursor Track", 0, 0, true);
      this.cursorDevice = this.cursorTrack.createCursorDevice("AXYZ_GEMS_CURSOR_DEVICE", "Cursor Device", 0, CursorDeviceFollowMode.FOLLOW_SELECTION);

      this.remoteControlsBank = this.cursorDevice.createCursorRemoteControlsPage(8);
      this.remoteControlsBank.selectedPageIndex().markInterested();

      for (int i = 0; i < this.remoteControlsBank.getParameterCount(); i++) {

  	    RemoteControl parameter = this.remoteControlsBank.getParameter(i);
  	    parameter.markInterested();
  	    parameter.setIndication(true);

    	final Integer innerI = Integer.valueOf(i);
  	    parameter.value().markInterested();
  	    parameter.value().addValueObserver ( value -> {
  	      int idx = (layoutColumns ? REVERSE_LAYOUT_COLUMNS_MAP[innerI.intValue()] : innerI.intValue());

  	      if (this.values[idx] != (value * 16383)) {
  	    	host.getMidiOutPort(0).sendMidi(0xB0,AXYZ_CC_MSB[idx],( ((int)(value * 16383)) >> 7) & 0x7F);
  	        if (this.highRes) host.getMidiOutPort(0).sendMidi(0xB0,AXYZ_CC_LSB[idx],(((int)(value * 16383)) >> 0) & 0x7F);
  	      }
  	    });
      }

//      this.cursorDevice.isEnabled().markInterested();
//      this.cursorDevice.isWindowOpen().markInterested();


      host.showPopupNotification("Axyz Gems Initialized");
   }

   @Override
   public void exit()
   {
      getHost().showPopupNotification("Axyz Gems Exited");
   }

   @Override
   public void flush()
   {
   }

   public static int indexOfIntArray(int[] array, int key) {
       int returnvalue = -1;
       for (int i = 0; i < array.length; ++i) {
           if (key == array[i]) {
               returnvalue = i;
               break;
           }
       }
       return returnvalue;
   }


   /** Called when we receive short MIDI message on port 0. */
   private void onMidi(ShortMidiMessage msg)
   {
	   if (msg.isControlChange()) {
		    int idx = AxyzGemsExtension.indexOfIntArray(AXYZ_CC_MSB,msg.getData1());
		    if (idx >= 0) {
		      values[idx] = (values[idx] & (0x7F << 0)) | (msg.getData2() << 7);
		      remoteControlsBank.getParameter(layoutColumns ? LAYOUT_COLUMNS_MAP[idx] : idx).set(values[idx], 16384);
		    } else if (highRes) {
		      idx = AxyzGemsExtension.indexOfIntArray(AXYZ_CC_LSB,msg.getData1());
		      if (idx >= 0) {
		        values[idx] = (values[idx] & (0x7F << 7)) | (msg.getData2() << 0);
		        remoteControlsBank.getParameter(layoutColumns ? LAYOUT_COLUMNS_MAP[idx] : idx).set(values[idx], 16384);
		      }
		    }
		  }
   }

//   /** Called when we receive sysex MIDI message on port 0. */
//   private void onSysex(final String data)
//   {
//      // MMC Transport Controls:
//      if (data.equals("f07f7f0605f7"))
//            mTransport.rewind();
//      else if (data.equals("f07f7f0604f7"))
//            mTransport.fastForward();
//      else if (data.equals("f07f7f0601f7"))
//            mTransport.stop();
//      else if (data.equals("f07f7f0602f7"))
//            mTransport.play();
//      else if (data.equals("f07f7f0606f7"))
//            mTransport.record();
//   }

//   private Transport mTransport;
}
