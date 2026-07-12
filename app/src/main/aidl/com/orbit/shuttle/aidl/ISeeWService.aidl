package com.orbit.shuttle.aidl;

import com.orbit.shuttle.aidl.ISeeWServiceCallback;

interface ISeeWService {
  int getState();
  String getProfileName();

  void registerCallback(in ISeeWServiceCallback cb, int id);
  oneway void unregisterCallback(in ISeeWServiceCallback cb);

  int urlTest();
}
