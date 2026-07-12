# NekoBoxForAndroid → SeeW 重构方案

## 目标

应对自动化扫描，消除 NekoBoxForAndroid 应用在包名、字符串、运行时特征层面的可识别性。

## 命名映射表

| 旧值 | 新值 |
|------|------|
| `io.nekohasekai.sagernet` | `com.orbit.shuttle` |
| `moe.matsuri.nb4a` | `com.orbit.shuttle.module` |
| `moe.nb4a` | `com.orbit.shuttle` |
| `NekoBox` / `NekoBox for Android` | `SeeW` |
| `sager_net.db` | `seew_data.db` |
| `SagerNet` (application class) | `SeeWApp` |
| `SagerConnection` | `SeeWConnection` |
| `SagerDatabase` | `SeeWDatabase` |
| `ISagerNetService` | `ISeeWService` |
| `ISagerNetServiceCallback` | `ISeeWServiceCallback` |
| `SagerNetVpnService` (log tag) | `SeeWVpn` |
| `SagerNetProxyService` (log tag) | `SeeWProxy` |
| `sagernet:vpn` (wakelock) | `seew:vpn` |
| `sagernet:proxy` (wakelock) | `seew:proxy` |
| `service-vpn` (channel) | `ch_vpn` |
| `service-proxy` (channel) | `ch_proxy` |
| `service-subscription` (channel) | `ch_sub` |
| `connection-test` (channel) | `ch_test` |
| `"NB4A Crash"` (crash extra) | `"SeeW Crash"` |
| `SubscriptionUpdater` (work name) | `SubUpdate` |
| `Theme.SagerNet` (style prefix) | `Theme.SeeW` |
| `nekobox_backup_${Date}.json` | `seew_backup_${Date}.json` |
| User-Agent | `okhttp/5.0.0-alpha.3` |

## 不可变清单

编译后不在 APK 中的内容不改：
- go.mod、README.md、LICENSE、AUTHORS
- buildScript/ 目录、.github/ 目录
- run 脚本、lint.xml
- 源码注释中的品牌引用（无运行时影响）

## 执行结果

### ✅ 已完成

| # | 项目 | 状态 |
|---|------|------|
| 1 | 包名迁移 `io.nekohasekai.sagernet` → `com.orbit.shuttle` | ✅ |
| 2 | 包名迁移 `moe.matsuri.nb4a` → `com.orbit.shuttle.module` | ✅ |
| 3 | AIDL 包名迁移 + 接口重命名 | ✅ |
| 4 | AndroidManifest.xml 所有组件引用更新 | ✅ |
| 5 | shortcuts.xml / proguard-rules.pro / nb4a.properties | ✅ |
| 6 | strings.xml app_name 所有语言 → `SeeW` | ✅ |
| 7 | User-Agent 改为 `okhttp/5.0.0-alpha.3` | ✅ |
| 8 | 数据库名 `sager_net.db` → `seew_data.db` | ✅ |
| 9 | log TAG / WakeLock TAG / 通知频道 ID | ✅ |
| 10 | SagerNet → SeeWApp / SagerConnection → SeeWConnection | ✅ |
| 11 | SagerDatabase → SeeWDatabase / AIDL 接口重命名 | ✅ |
| 12 | Intent Action 常量 | ✅ |
| 13 | Plugin authorities / 插件包名引用 | ✅ |
| 14 | QRCodeDialog 常量键 | ✅ |
| 15 | 备份文件名 `nekobox_backup_*` → `seew_backup_*` | ✅ |
| 16 | CrashHandler 报告文本 | ✅ |
| 17 | 构建产物命名 `NekoBox-*` → `SeeW-*` | ✅ |
| 18 | themes.xml 所有 Theme.SagerNet → Theme.SeeW | ✅ |
| 19 | 布局/偏好 xml 所有控件类名引用 | ✅ |
| 20 | strings 中 SagerNet/SeeW 用户可见文本 | ✅ |
| 21 | GitHub API URL 及 About 页面链接（指向新占位仓库） | ✅ |
| 22 | `matsuridayo.github.io` 链接移除 | ✅ |
| 23 | `PluginEntry.kt` 插件包名更新 | ✅ |
| 24 | `release.keystore` 废弃（重命名为 `.DEPRECATED_BRANDED`） | ✅ |

### ⏳ 需手动处理

| # | 项目 | 说明 |
|---|------|------|
| 1 | 生成新签名密钥 | 运行 `keytool -genkey -v -keystore release.keystore -alias orbit -keyalg RSA -keysize 2048 -validity 10000` |
| 2 | Git 历史清除旧 keystore | `git filter-repo --path release.keystore.DEPRECATED_BRANDED --invert-paths` |
| 3 | Go 层重命名（需 Go + gomobile 环境） | `libcore/nb4a.go` `libcore/box_include.go` `libcore/platform_java.go` — 函数名 `nekoboxAndroid*`、接口 `NB4AInterface`、常量 `RunMode_NekoBoxForAndroid` |
| 4 | 创建 GitHub org `OrbitCode` 和仓库 `SeeW` | 否则 About 页面 URL 会 404 |
| 5 | 重新编译 Go native library | 修改 Go 代码后需 `libcore/build.sh` 重新编译 |
| 6 | 重新签名插件 | 插件包名变更后需重新发布 |

## 验证方式

```bash
# 检查 APK 中的所有可识别的品牌串
strings release.apk | grep -iE 'nekobox|sagernet|nb4a|nekohasekai|matsuri|clashmeta'
# 预期输出：无匹配
```
