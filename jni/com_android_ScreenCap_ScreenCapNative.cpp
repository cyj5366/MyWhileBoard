/*
 * Copyright (C) 2010 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

#define LOG_TAG "ScreenCapNative-JNI"

//#define LOG_NDEBUG 0

#include "JNIHelp.h"
#include "jni.h"

#include <utils/Log.h>

#include <binder/IPCThreadState.h>
#include <binder/ProcessState.h>
#include <binder/IServiceManager.h>

#include <binder/IMemory.h>
#include <surfaceflinger/ISurfaceComposer.h>

#include <SkImageEncoder.h>
#include <SkBitmap.h>

namespace android {	

static void android_ScreenCap_ScreenCapNative_nativeCaptureScreen(JNIEnv* env,
        jobject obj, jstring file) 
{
	const char *file_path = env->GetStringUTFChars(file, NULL);

    const String16 name("SurfaceFlinger");
    sp<ISurfaceComposer> composer;
    getService(name, &composer);

    sp<IMemoryHeap> heap;
    uint32_t w, h;
    PixelFormat f;
    status_t err = composer->captureScreen(0, &heap, &w, &h, &f, 0, 0);
    if (err != NO_ERROR) {
        LOGE("screen capture failed: %s\n", strerror(-err));
        exit(0);
    }

    LOGD("screen capture success: w=%u, h=%u, pixels=%p\n",
            w, h, heap->getBase());

    LOGD("saving file as PNG in %s ...\n", file_path);

    SkBitmap b;
    b.setConfig(SkBitmap::kARGB_8888_Config, w, h);
    b.setPixels(heap->getBase());
    SkImageEncoder::EncodeFile(file_path, b,
            SkImageEncoder::kPNG_Type, SkImageEncoder::kDefaultQuality);        	
}

static JNINativeMethod gScreenCapNativeMethods[] = {
    /* name, signature, funcPtr */
    { "nativeCaptureScreen", "(Ljava/lang/String;)V",
            (void*) android_ScreenCap_ScreenCapNative_nativeCaptureScreen },            
};
	
int register_android_ScreenCap_ScreenCapNative(JNIEnv* env) {
    int res = jniRegisterNativeMethods(env, "com/android/ScreenCap/ScreenCapNative",
            gScreenCapNativeMethods, NELEM(gScreenCapNativeMethods));
    LOG_FATAL_IF(res < 0, "Unable to register native methods.");

    return 0;
}	
	
extern "C" jint JNI_OnLoad(JavaVM* vm, void* reserved)
{
    JNIEnv* env = NULL;
    jint result = -1;

    if (vm->GetEnv((void**) &env, JNI_VERSION_1_4) != JNI_OK) {
        LOGE("GetEnv failed!");
        return result;
    }
    LOG_ASSERT(env, "Could not retrieve the env!");

    register_android_ScreenCap_ScreenCapNative(env);

    return JNI_VERSION_1_4;
}	
	
} /* namespace android */

