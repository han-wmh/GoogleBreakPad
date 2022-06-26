源码来自[Gitee项目](https://gitee.com/wufanguitar/GoogleBreakpad?_from=gitee_search)
[linux_syscall_support.h](https://github.com/han-wmh/linuxSysCallSupportll)

用法：

- 1.编译模块breakpad.aar

- 2.项目中引入aar，初始化路径
```java
public class Test {
    private File mExternalReportPath;
    private void initBreakPad() {
        if (mExternalReportPath == null) {
           mExternalReportPath = new File(getFilesDir(), "crashDump");
           if (!mExternalReportPath.exists()) {
               mExternalReportPath.mkdirs();
           }
        }
        BreakpadDumper.initBreakpad(mExternalReportPath.getAbsolutePath());
    }
}
```

- 3.adb 修改/storage/sdcard0/crashDump/xxx.dmp文件为crash0.dmp
mv xxxxxxxxx.dmp crash0.dmp

- 4.adb pull /storage/sdcard0/crashDump/crash0.dmp xxx\xxx\Desktop

**adb pull 解决问题：**

xxx\AndroidStudio\bin\lldb\bin> minidump_stackwalk.exe xxx\xxx\crash0.dmp > xxx\xxx\11111.txt

2020-08-14 10:05:43: minidump.cc:4740: ERROR: Minidump could not open minidump xxx\xxx\crash0.dmp, error 2: No such file or directory

2020-08-14 10:05:44: minidump.cc:4834: ERROR: Minidump cannot open minidump

2020-08-14 10:05:44: minidump_stackwalk.cc:87: ERROR: Minidump xxx\xxx\crash0.dump could not be read

- 5.xxx\AndroidStudio\bin\lldb\bin> minidump_stackwalk.exe xxx\xxx\crash0.dmp > xxx\xxx\11111.txt
打开txt查看
Thread 0 (crashed)
 0  libcrash-lib.so + 0x612
// 省略其他日志

 0x612为 发生 crash 的位置和寄存器信息

 
- 6.使用addr2line.exe获取崩溃堆栈

命令：xxx\xx-addr2line.exe -f -C -e \xxx\xxx.so 寄存器信息
"your ndk path"\toolchains\arm-linux-androideabi-4.9\prebuilt\"cpu 架构（如windows-x86_64)"\bin\arm-linux-androideabi-addr2line.exe -f -C -e "your so name
(如libcrash-lib.so) 0x612
