package at.crowdware.shift;

import android.content.Context;
import dalvik.system.DexClassLoader;

public class ClassLoaderUtils {
    public Class<?> loadClass(String apkPath, Context context) throws ClassNotFoundException {
        final ClassLoader classLoader = new DexClassLoader(apkPath, context.getCacheDir().getAbsolutePath(), null, this.getClass().getClassLoader());
        return classLoader.loadClass("at.crowdware.shift.plugin.Plugin");
    }
}