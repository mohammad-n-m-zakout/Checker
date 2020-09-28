LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)
LOCAL_LDLIBS:=-L$(SYSROOT)/usr/lib -llog
LOCAL_MODULE    := emulator_check
LOCAL_SRC_FILES := emulator/EmulatorDetectUtil.c
include $(BUILD_SHARED_LIBRARY)

include $(CLEAR_VARS)
LOCAL_LDLIBS:=-L$(SYSROOT)/usr/lib -llog
LOCAL_MODULE    := property_get
LOCAL_SRC_FILES := property/jni_PropertiesGet.c
include $(BUILD_SHARED_LIBRARY)

LOCAL_PATH := $(call my-dir)
