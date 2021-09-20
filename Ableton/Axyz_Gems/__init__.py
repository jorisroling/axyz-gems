# Embedded file name: /Users/versonator/Jenkins/live/output/mac_64_static/Release/python-bundle/MIDI Remote Scripts/BCR2000/__init__.py
# Compiled at: 2018-04-23 20:27:04
from __future__ import absolute_import, print_function, unicode_literals
from _Generic.GenericScript import GenericScript
import Live
from .config import *

def create_instance(c_instance):
    u""" The generic script can be customised by using parameters (see config.py). """
    return GenericScript(c_instance, Live.MidiMap.MapMode.absolute, Live.MidiMap.MapMode.absolute, DEVICE_CONTROLS, TRANSPORT_CONTROLS, VOLUME_CONTROLS, TRACKARM_CONTROLS, BANK_CONTROLS, CONTROLLER_DESCRIPTION)


from _Framework.Capabilities import *

def get_capabilities():
    return {CONTROLLER_ID_KEY: controller_id(vendor_id=95015, product_ids=[
                         9188], model_name='Axyz Gems'),
       PORTS_KEY: [
                 inport(props=[NOTES_CC, SCRIPT]),
                 outport(props=[NOTES_CC, SCRIPT])]}
