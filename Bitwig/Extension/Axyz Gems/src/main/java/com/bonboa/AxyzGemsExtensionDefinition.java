package com.bonboa;
import java.util.UUID;

import com.bitwig.extension.api.PlatformType;
import com.bitwig.extension.controller.AutoDetectionMidiPortNamesList;
import com.bitwig.extension.controller.ControllerExtensionDefinition;
import com.bitwig.extension.controller.api.ControllerHost;

public class AxyzGemsExtensionDefinition extends ControllerExtensionDefinition
{
   private static final UUID DRIVER_ID = UUID.fromString("f83d80c8-8310-47d7-aac7-8c6e73a64432");
   
   public AxyzGemsExtensionDefinition()
   {
   }

   @Override
   public String getName()
   {
      return "Axyz Gems2";
   }
   
   @Override
   public String getAuthor()
   {
      return "Joris Röling";
   }

   @Override
   public String getVersion()
   {
      return "1.0";
   }

   @Override
   public UUID getId()
   {
      return DRIVER_ID;
   }
   
   @Override
   public String getHardwareVendor()
   {
      return "Bonboa";
   }
   
   @Override
   public String getHardwareModel()
   {
      return "Axyz Gems";
   }

   @Override
   public int getRequiredAPIVersion()
   {
      return 7;
   }

   @Override
   public int getNumMidiInPorts()
   {
      return 1;
   }

   @Override
   public int getNumMidiOutPorts()
   {
      return 1;
   }

   @Override
   public void listAutoDetectionMidiPortNames(final AutoDetectionMidiPortNamesList list, final PlatformType platformType)
   {
      if (platformType == PlatformType.WINDOWS)
      {
         // TODO: Set the correct names of the ports for auto detection on Windows platform here
         // and uncomment this when port names are correct.
         list.add(new String[]{"Lightpad BLOCK"}, new String[]{"Lightpad BLOCK"});
      }
      else if (platformType == PlatformType.MAC)
      {
         // TODO: Set the correct names of the ports for auto detection on Windows platform here
         // and uncomment this when port names are correct.
          list.add(new String[]{"Lightpad BLOCK"}, new String[]{"Lightpad BLOCK"});
      }
      else if (platformType == PlatformType.LINUX)
      {
         // TODO: Set the correct names of the ports for auto detection on Windows platform here
         // and uncomment this when port names are correct.
          list.add(new String[]{"Lightpad BLOCK"}, new String[]{"Lightpad BLOCK"});
      }
   }

   @Override
   public AxyzGemsExtension createInstance(final ControllerHost host)
   {
      return new AxyzGemsExtension(this, host);
   }
}
