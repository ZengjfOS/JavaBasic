# SystemUI Dagger

SystemUI Dagger使用示例

## 参考文档

* [Android SystemUI](https://cs.android.com/android/platform/superproject/+/master:frameworks/base/packages/SystemUI/)
* [Dagger 2 in SystemUI](https://cs.android.com/android/platform/superproject/+/master:frameworks/base/packages/SystemUI/docs/dagger.md)

## 简述

Dagger 2 is a dependency injection framework that compiles annotations to code to create dependencies without reflection


## SystemUI Dagger

Component声明

```java
// frameworks/base/packages/SystemUI/src/com/android/systemui/SystemUIFactory.java

@Singleton
@Component(modules = {SystemUIFactory.class, DependencyProvider.class, DependencyBinder.class,
        ContextHolder.class})
public interface SystemUIRootComponent {
    @Singleton
    Dependency.DependencyInjector createDependency();

    @Singleton
    StatusBar.StatusBarInjector getStatusBarInjector();

    /**
     * FragmentCreator generates all Fragments that need injection.
     */
    @Singleton
    FragmentService.FragmentCreator createFragmentCreator();

    /**
     * ViewCreator generates all Views that need injection.
     */
    InjectionInflationController.ViewCreator createViewCreator();

    @Singleton
    GarbageMonitor createGarbageMonitor();
}
```

* Component使用
* 注意这里用的builder，是需要自己取设置实例对象的，create就不用

```java
// frameworks/base/packages/SystemUI/src/com/android/systemui/SystemUIFactory.java

public SystemUIFactory() {}

protected void init(Context context) {
    mRootComponent = DaggerSystemUIFactory_SystemUIRootComponent.builder()
            .systemUIFactory(this)
            .dependencyProvider(new com.android.systemui.DependencyProvider())
            .contextHolder(new ContextHolder(context))
            .build();
}

public SystemUIRootComponent getRootComponent() {
    return mRootComponent;
}
```

Component初始化

```
* frameworks/base/packages/SystemUI/AndroidManifest.xml
  * <application android:name=".SystemUIApplication" ...省略 >
    * frameworks/base/packages/SystemUI/src/com/android/systemui/SystemUIApplication.java
      * public class SystemUIApplication extends Application implements SysUiServiceProvider
        * public void onCreate()
          * SystemUIFactory.createFromConfig(this);
            * frameworks/base/packages/SystemUI/src/com/android/systemui/SystemUIFactory.java
              * public class SystemUIFactory
                * public static void createFromConfig(Context context)
                  * final String clsName = context.getString(R.string.config_systemUIFactoryComponent);
                    * frameworks/base/packages/SystemUI/res/values/config.xml
                      * <string name="config_systemUIFactoryComponent" translatable="false">com.android.systemui.SystemUIFactory</string>
                  * cls = context.getClassLoader().loadClass(clsName);
                  * mFactory = (SystemUIFactory) cls.newInstance();
                  * mFactory.init(context);
                    * protected void init(Context context)
                      * mRootComponent = DaggerSystemUIFactory_SystemUIRootComponent.builder().systemUIFactory(this).dependencyProvider(new com.android.systemui.DependencyProvider()).contextHolder(new ContextHolder(context)).build();
```

## SystemUIService启动服务

SystemUIService启动服务流程

```
* frameworks/base/packages/SystemUI/AndroidManifest.xml
  * <service android:name="SystemUIService" android:exported="true" />
    * frameworks/base/packages/SystemUI/src/com/android/systemui/SystemUIService.java
      * public class SystemUIService extends Service
        * public void onCreate()
          * ((SystemUIApplication) getApplication()).startServicesIfNeeded();
            * frameworks/base/packages/SystemUI/src/com/android/systemui/SystemUIApplication.java
              * public void startServicesIfNeeded()
                * String[] names = getResources().getStringArray(R.array.config_systemUIServiceComponents);
                * startServicesIfNeeded(names);
                  * for (int i = 0; i < N; i++)
                    * String clsName = services[i];
                    * cls = Class.forName(clsName);
                    * Object o = cls.newInstance();
                    * if (o instanceof SystemUI.Injector)
                      * o = ((SystemUI.Injector) o).apply(this);
                    * mServices[i] = (SystemUI) o;
                    * mServices[i].start();
```

* services列表
* `<item>com.android.systemui.Dependency$DependencyCreator</item>`
  * 继续解析

```xml
<!-- frameworks/base/packages/SystemUI/res/values/config.xml -->

<!-- SystemUI Services: The classes of the stuff to start. -->
<string-array name="config_systemUIServiceComponents" translatable="false">
    <item>com.android.systemui.Dependency$DependencyCreator</item>
    <item>com.android.systemui.util.NotificationChannels</item>
    <item>com.android.systemui.statusbar.CommandQueue$CommandQueueStart</item>
    <item>com.android.systemui.keyguard.KeyguardViewMediator</item>
    <item>com.android.systemui.recents.Recents</item>
    <item>com.android.systemui.volume.VolumeUI</item>
    <item>com.android.systemui.stackdivider.Divider</item>
    <item>com.android.systemui.SystemBars</item>
    <item>com.android.systemui.usb.StorageNotification</item>
    <item>com.android.systemui.power.PowerUI</item>
    <item>com.android.systemui.media.RingtonePlayer</item>
    <item>com.android.systemui.keyboard.KeyboardUI</item>
    <item>com.android.systemui.pip.PipUI</item>
    <item>com.android.systemui.shortcut.ShortcutKeyDispatcher</item>
    <item>@string/config_systemUIVendorServiceComponent</item>
    <item>com.android.systemui.util.leak.GarbageMonitor$Service</item>
    <item>com.android.systemui.LatencyTester</item>
    <item>com.android.systemui.globalactions.GlobalActionsComponent</item>
    <item>com.android.systemui.ScreenDecorations</item>
    <item>com.android.systemui.biometrics.BiometricDialogImpl</item>
    <item>com.android.systemui.SliceBroadcastRelayHandler</item>
    <item>com.android.systemui.SizeCompatModeActivityController</item>
    <item>com.android.systemui.statusbar.notification.InstantAppNotifier</item>
    <item>com.android.systemui.theme.ThemeOverlayController</item>
</string-array>
```

实际处理注入的地方，按照上面的代码处理逻辑，Dependency是一个服务

```java
// frameworks/base/packages/SystemUI/src/com/android/systemui/Dependency.java

public static class DependencyCreator implements Injector {
    @Override
    public SystemUI apply(Context context) {
        Dependency dependency = new Dependency();
        SystemUIFactory.getInstance().getRootComponent()
                .createDependency()
                .createSystemUI(dependency);
        return dependency;
    }
}
```

Dependency继承关系如下

```
* frameworks/base/packages/SystemUI/
  * src/com/android/systemui/Dependency.java
    * public class Dependency extends SystemUI
      * src/com/android/systemui/SystemUI.java
        * public abstract class SystemUI implements SysUiServiceProvider
          * src/com/android/systemui/SysUiServiceProvider.java
            * public interface SysUiServiceProvider
```
