# 在 Android 平板上使用 VS Code（[`code-server`](https://coder.com/docs/code-server/)）

![](/ReadmeImageZhCN.png)

## English

[README_EN.md](/README_EN.md)（Translated by ChatGPT 3.5）

## 这个仓库是什么？

这个仓库实际上是一个套壳 Android WebView 的应用程序源码，网站指向了 http://127.0.0.1:8080/ 。

其实做这个项目是因为是我觉得截止目前的主流通过在 Android 本地模拟 Linux 服务器并在此基础上运行 [`code-server`](https://coder.com/docs/code-server/) 的方案的界面并不是很好看，于是我做了一个适配异形屏，隐藏导航栏但不隐藏状态栏的 Android WebView 套壳软件来弥补这个问题。可以为有相同需求者节省时间。

如果你对此仓库有相关建议，欢迎提交 issue(s) 或 PR。

## 如何使用？

以下内容均需要非中国大陆网络环境。

### 初次配置

在 F-Droid 中下载 Termux 软件：[https://f-droid.org/zh_Hans/packages/com.termux/](https://f-droid.org/zh_Hans/packages/com.termux/)

[![](https://gitlab.com/fdroid/artwork/-/raw/master/badge/get-it-on-zh-hans.png)](https://f-droid.org/zh_Hans/packages/com.termux/)

在本仓库的发行版中下载 8080 软件，也可自行编译。

下载完成后，打开 Termux 软件，输入此命令以选择 Termux 所在镜像：

```bash
termux-change-repo
```

然后输入下列命令安装 OpenSSL 和 Ubuntu 环境

```bash
pkg install wget openssl-tool proot -y
```

```bash
hash -r
```

```bash
wget https://raw.githubusercontent.com/EXALAB/AnLinux-Resources/master/Scripts/Installer/Ubuntu/ubuntu.sh
```

```bash
bash ubuntu.sh
```

然后，输入此命令以启动 Ubuntu 环境：

```bash
./start-ubuntu.sh
```

进入 Ubuntu 环境后，输入下列命令下载并解压 `code-server`：

```bash
apt update && apt upgrade
```

> 请注意替换尖括号及其内容为相应版本号，例如：`wget https://github.com/coder/code-server/releases/download/v4.15.0/code-server-4.15.0-linux-arm64.tar.gz`。
```bash
wget https://github.com/coder/code-server/releases/download/<VERSION>/code-server-<VERSION>-linux-arm64.tar.gz
```

> 请注意替换尖括号及其内容为相应版本号，例如：`tar -xvf ./code-server-4.15.0-linux-arm64.tar.gz`。
```bash
tar -xvf ./code-server-<VERSION>-linux-arm64.tar.gz
```

移动到解压后目录：

> 请注意替换尖括号及其内容为相应版本号，例如：`cd code-server-4.15.0-linux-arm64/bin`。
```bash
cd code-server-<VERSION>-linux-arm64/bin
```

输入以下命令设置进入 `code-server` 的临时密码：

> 请注意替换尖括号及其内容为你设置的密码。
```bash
export PASSWORD="<PASSWORD>"
```

启动 `code-server`:

```bash
./code-server
```

打开从本仓库下载的 8080 软件，输入你设置的密码，即可进入 `code-server`。

### 后续使用

打开 Termux，依次运行以下命令：

```bash
./start-ubuntu.sh
```

> 请注意替换尖括号及其内容为相应版本号。
```bash
cd code-server-<VERSION>-linux-arm64/bin
```

> 请注意替换尖括号及其内容为你设置的密码。
```bash
export PASSWORD="<PASSWORD>"
```

```bash
./code-server
```

然后打开 8080 软件即可。

### 附加说明

由于 Termux 的文件保存在私有目录（data/data）中，非 root 设备无法进入，因此可以通过在 Ubuntu 环境下安装各种使用 SSH、Web 等方式的服务器管理工具进行文件管理。
