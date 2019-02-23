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

public class AxyzGemsExtension extends ControllerExtension {
	protected AxyzGemsExtension(final AxyzGemsExtensionDefinition definition, final ControllerHost host) {
		super(definition, host);
	}

	private static final String[] BOOLEAN_OPTIONS = new String[] { "Off", "On", };

	private static final String[] LAYOUT_OPTIONS = new String[] { "Rows", "Columns", };
	private static final int[] LAYOUT_COLUMNS_MAP = { 0, 4, 1, 5, 2, 6, 3, 7 };
	private static final int[] REVERSE_LAYOUT_COLUMNS_MAP = { 0, 2, 4, 6, 1, 3, 5, 7 };

	private static final int[] AXYZ_CC_MSB = { 20, 21, 23, 24, 26, 27, 29, 30 };
	private static final int[] AXYZ_CC_LSB = { 20 + 32, 21 + 32, 23 + 32, 24 + 32, 26 + 32, 27 + 32, 29 + 32, 30 + 32 };

	private Boolean highRes = true;
	private Boolean layoutColumns = false;
	private CursorTrack cursorTrack;
	private PinnableCursorDevice cursorDevice;
	private CursorRemoteControlsPage remoteControlsBank;
	private int channel = 1;

	private int[] values = { 0, 0, 0, 0, 0, 0, 0, 0 };

	@Override
	public void init() {
		final ControllerHost host = getHost();
		host.getMidiInPort(0).setMidiCallback((ShortMidiMessageReceivedCallback) msg -> onMidi(msg));

		Preferences preferences = host.getPreferences();

		preferences.getEnumSetting("Enable", "High Resolution", BOOLEAN_OPTIONS, BOOLEAN_OPTIONS[1])
				.addValueObserver(value -> {
					this.highRes = (value == BOOLEAN_OPTIONS[1]);
				});

		preferences.getEnumSetting("Layout", "Button Order", LAYOUT_OPTIONS, LAYOUT_OPTIONS[0])
				.addValueObserver(value -> {
					this.layoutColumns = (value == LAYOUT_OPTIONS[1]);
				});

		preferences.getNumberSetting("Channel", "MIDI", 1, 16, 1, "", 1).addValueObserver(value -> {
			this.channel = (int) ((value * 15) + 1) ;
			host.showPopupNotification(String.format("Axyz Gems MIDI Channel: %d",this.channel));
		});

		for (int i=0; i < 8; i++) {
			final Integer innerI = Integer.valueOf(i);
			preferences.getNumberSetting(String.format("%s axis Control Change",(i % 2) == 0 ? "X" : "Y"), String.format("Axyz Gem %d",(((int)(i/2)) % 4)+1), 0, 127, 1, "", AXYZ_CC_MSB[i]).addValueObserver(value -> {
				value = value * 128;
				host.showPopupNotification(String.format("Axyz Gems %d %s axis Control Change: %d",((((int)(innerI.intValue()/2)) % 4)+1),(innerI.intValue() % 2) == 0 ? "X" : "Y",(int)value));
				AXYZ_CC_MSB[innerI.intValue()] = (int) value;
				AXYZ_CC_LSB[innerI.intValue()] = ((int) value) < 32 ? (((int) value) + 32) : 0xFF;
			});
		}
		this.cursorTrack = host.createCursorTrack("AXYZ_GEMS_CURSOR_TRACK", "Cursor Track", 0, 0, true);
		this.cursorDevice = this.cursorTrack.createCursorDevice("AXYZ_GEMS_CURSOR_DEVICE", "Cursor Device", 0,
				CursorDeviceFollowMode.FOLLOW_SELECTION);

		this.remoteControlsBank = this.cursorDevice.createCursorRemoteControlsPage(8);
		this.remoteControlsBank.selectedPageIndex().markInterested();

		for (int i = 0; i < this.remoteControlsBank.getParameterCount(); i++) {

			RemoteControl parameter = this.remoteControlsBank.getParameter(i);
			parameter.markInterested();
			parameter.setIndication(true);

			final Integer innerI = Integer.valueOf(i);
			parameter.value().markInterested();
			parameter.value().addValueObserver(value -> {
				int idx = (layoutColumns ? REVERSE_LAYOUT_COLUMNS_MAP[innerI.intValue()] : innerI.intValue());

				if (this.values[idx] != (value * 16383)) {
					host.showPopupNotification(String.format("Channel: %d",this.channel));
					host.getMidiOutPort(0).sendMidi(0xB0 | (this.channel - 1), AXYZ_CC_MSB[idx],
							(((int) (value * 16383)) >> 7) & 0x7F);
					if (this.highRes && AXYZ_CC_LSB[idx] != 0xFF)
						host.getMidiOutPort(0).sendMidi(0xB0 | (this.channel - 1), AXYZ_CC_LSB[idx],
								(((int) (value * 16383)) >> 0) & 0x7F);
				}
			});
		}

		host.showPopupNotification("Axyz Gems Initialized");
	}

	@Override
	public void exit() {
		getHost().showPopupNotification("Axyz Gems Exited");
	}

	@Override
	public void flush() {
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

	private void onMidi(ShortMidiMessage msg) {
		if (msg.isControlChange() && msg.getChannel() == (this.channel - 1)) {
			int idx = AxyzGemsExtension.indexOfIntArray(AXYZ_CC_MSB, msg.getData1());
			if (idx >= 0) {
				values[idx] = (values[idx] & (0x7F << 0)) | (msg.getData2() << 7);
				remoteControlsBank.getParameter(layoutColumns ? LAYOUT_COLUMNS_MAP[idx] : idx).set(values[idx], 16384);
			} else if (highRes) {
				idx = AxyzGemsExtension.indexOfIntArray(AXYZ_CC_LSB, msg.getData1());
				if (idx >= 0) {
					values[idx] = (values[idx] & (0x7F << 7)) | (msg.getData2() << 0);
					remoteControlsBank.getParameter(layoutColumns ? LAYOUT_COLUMNS_MAP[idx] : idx).set(values[idx],
							16384);
				}
			}
		}
	}
}
