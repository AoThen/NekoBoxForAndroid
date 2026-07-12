package com.orbit.shuttle.aidl;

import com.orbit.shuttle.aidl.SpeedDisplayData;
import com.orbit.shuttle.aidl.TrafficData;

oneway interface ISeeWServiceCallback {
  void stateChanged(int state, String profileName, String msg);
  void missingPlugin(String profileName, String pluginName);
  void cbSpeedUpdate(in SpeedDisplayData stats);
  void cbTrafficUpdate(in TrafficData stats);
  void cbSelectorUpdate(long id);
}
