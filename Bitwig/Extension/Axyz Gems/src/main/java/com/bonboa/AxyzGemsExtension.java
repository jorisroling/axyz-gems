package com.bonboa;

import com.bitwig.extension.api.util.midi.ShortMidiMessage;
import com.bitwig.extension.callback.ShortMidiMessageReceivedCallback;
import com.bitwig.extension.controller.api.ControllerHost;
import com.bitwig.extension.controller.api.CursorTrack;
import com.bitwig.extension.controller.api.Preferences;
import com.bitwig.extension.controller.api.Transport;
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

   private Boolean highRes = true;
   private Boolean layoutColumns = false;
   private CursorTrack    cursorTrack;
   
   @Override
   public void init()
   {
      final ControllerHost host = getHost();      

      
      Preferences preferences = host.getPreferences();

      preferences.getEnumSetting("Enable", "High Resolution", BOOLEAN_OPTIONS, BOOLEAN_OPTIONS[1]).addValueObserver(value -> {
          this.highRes = (value == BOOLEAN_OPTIONS[1]);
      });
      
      preferences.getEnumSetting("Layout", "Button Order", LAYOUT_OPTIONS, LAYOUT_OPTIONS[0]).addValueObserver(value -> {
        this.layoutColumns = (value == LAYOUT_OPTIONS[1]);
      });

//        this.cursorTrack = controllerHost.createCursorTrack ("MyCursorTrackID", "The Cursor Track", 0, 0, true);
      this.cursorTrack = host.createCursorTrack("AXYZ_GEMS_CURSOR_TRACK", "Cursor Track", 0, 0, true);


      
      mTransport = host.createTransport();
      host.getMidiInPort(0).setMidiCallback((ShortMidiMessageReceivedCallback)msg -> onMidi0(msg));
      host.getMidiInPort(0).setSysexCallback((String data) -> onSysex0(data));

      // TODO: Perform your driver initialization here.
      // For now just show a popup notification for verification that it is running.
      host.showPopupNotification("Axyz Gems Initialized");
   }

   @Override
   public void exit()
   {
      // TODO: Perform any cleanup once the driver exits
      // For now just show a popup notification for verification that it is no longer running.
      getHost().showPopupNotification("Axyz Gems Exited");
   }

   @Override
   public void flush()
   {
      // TODO Send any updates you need here.
   }

   /** Called when we receive short MIDI message on port 0. */
   private void onMidi0(ShortMidiMessage msg) 
   {
      // TODO: Implement your MIDI input handling code here.
   }

   /** Called when we receive sysex MIDI message on port 0. */
   private void onSysex0(final String data) 
   {
      // MMC Transport Controls:
      if (data.equals("f07f7f0605f7"))
            mTransport.rewind();
      else if (data.equals("f07f7f0604f7"))
            mTransport.fastForward();
      else if (data.equals("f07f7f0601f7"))
            mTransport.stop();
      else if (data.equals("f07f7f0602f7"))
            mTransport.play();
      else if (data.equals("f07f7f0606f7"))
            mTransport.record();
   }

   private Transport mTransport;
}
