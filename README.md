# TimeForFlight 限时飞行！

![CodeSize](https://img.shields.io/github/languages/code-size/CarmJos/TimeForFlight)
[![License](https://img.shields.io/github/license/CarmJos/TimeForFlight)](https://opensource.org/licenses/mit-license.php)
[![Build](https://github.com/CarmJos/TimeForFlight/actions/workflows/maven.yml/badge.svg?branch=master)](https://github.com/CarmJos/TimeForFlight/actions/workflows/maven.yml)
![Support](https://img.shields.io/badge/Minecraft-Java%201.12--Latest-yellow)
![](https://visitor-badge.glitch.me/badge?page_id=TimeForFlight.readme)

## 简介

本插件支持玩家通过在线游玩积攒飞行时间，并在需要的时候开关飞行！

玩家数据将存储为YAML。

本插件基于Spigot实现，功能简单，运行稳定，**理论上支持全版本**。

本插件由 [桦木原Harmoland](https://www.mcbbs.net/thread-1028923-1-1.html) 请求本人开发，经过授权后开源。

## 依赖

- **[必须]** 插件本体基于 [Spigot-API](https://hub.spigotmc.org/stash/projects/SPIGOT) 、[BukkitAPI](http://bukkit.org/) 实现。

详细依赖列表可见 [Dependencies](https://github.com/CarmJos/TimeForFlight/network/dependencies) 。

## 指令

### 玩家指令

```text
# /toggleFly
- 开关飞行模式
```

### 管理员指令

```text
# /TimeForFlight set <玩家> <秒数>
- 设置玩家的飞行时间
# /TimeForFlight add <玩家> <秒数> 
- 添加玩家的飞行时间
# /TimeForFlight remove <玩家> <秒数> 
- 移除玩家的飞行时间
# /TimeForFlight get <玩家> 
- 查看玩家的飞行时间
# /TimeForFlight clear <玩家> 
- 清空玩家的飞行时间
# /TimeForFlight help 
- 查看此帮助
```

## 权限

```yaml
permissions:
  timeforflight.admin:
    default: op
    description: "管理员权限"
  timeforflight.unlimited:
    default: op
    description: "无限制飞行，不计算飞行时间"
  timeforflight.allowflight:
    description: "拥有此权限的玩家允许飞行"
  timeforflight.getflighttime:
    description: "拥有次权限的玩家会获赠飞行时间"
```

## 支持与捐赠

若您觉得本插件做的不错，您可以捐赠支持我！

感谢您成为开源项目的支持者！

<img height=25% width=25% src="https://raw.githubusercontent.com/CarmJos/CarmJos/main/img/donate-code.jpg" />

## 开源协议

本项目源码采用 [The MIT License](https://opensource.org/licenses/mit-license.php) 开源协议。

> ### 关于 MIT 协议
> MIT 协议可能是几大开源协议中最宽松的一个，核心条款是：
>
> 该软件及其相关文档对所有人免费，可以任意处置，包括使用，复制，修改，合并，发表，分发，再授权，或者销售。唯一的限制是，软件中必须包含上述版 权和许可提示。
>
> 这意味着：
> - 你可以自由使用，复制，修改，可以用于自己的项目。
> - 可以免费分发或用来盈利。
> - 唯一的限制是必须包含许可声明。
> - MIT 协议是所有开源许可中最宽松的一个，除了必须包含许可声明外，再无任何限制。
>
> *以上文字来自 [五种开源协议GPL,LGPL,BSD,MIT,Apache](https://www.oschina.net/question/54100_9455) 。*
