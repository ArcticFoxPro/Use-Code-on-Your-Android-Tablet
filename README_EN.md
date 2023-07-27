> Translated from Chinese Simplified by ChatGPT 3.5.

# Use VS Code on Your Android Tablet（[`code-server`](https://coder.com/docs/code-server/)）

![](/ReadmeImageEN.png)

## 简体中文

[README简体中文.md](/README.md)

## What is this repository?

This repository is actually the source code of an Android WebView application with a shell, which points to http://127.0.0.1:8080/.

I created this project because I felt that the existing solutions for running [`code-server`](https://coder.com/docs/code-server/) on Android tablets, which involve emulating a local Linux server and running `code-server` on top of it, had interfaces that were not very appealing. Therefore, I created an Android WebView shell application that adapts to unconventional screen sizes, hides the navigation bar but not the status bar, to address this issue. This can save time for those with the same requirement.

If you have any suggestions or feedback regarding this repository, please feel free to submit issue(s) or PR.

## How to use?

### Initial Configuration

Download the `termux` app from F-Droid: [https://f-droid.org/packages/com.termux/](https://f-droid.org/packages/com.termux/)

[![](https://gitlab.com/fdroid/artwork/-/raw/master/badge/get-it-on-en-us.png)](https://f-droid.org/en/packages/com.termux/)

In the Releases section of this repository, download the 8080 app, or you can compile it yourself.

After the download is complete, open the `termux` app and enter the following command to select the mirror for `termux`:

```bash
termux-change-repo
```

Then enter the following commands to install OpenSSL and the Ubuntu environment:

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

Then, enter the following command to start the Ubuntu environment:

```bash
./start-ubuntu.sh
```

Once inside the Ubuntu environment, enter the following command to download and extract `code-server`:

```bash
apt update && apt upgrade
```

> Please note to replace the angle brackets and their contents with the corresponding version number, for example: `wget https://github.com/coder/code-server/releases/download/v4.15.0/code-server-4.15.0-linux-arm64.tar.gz`.

```bash
wget https://github.com/cdr/code-server/releases/download/<VERSION>/code-server-<VERSION>-linux-arm64.tar.gz
```

> Please note to replace the angle brackets and their contents with the corresponding version number, for example: `tar -xvf ./code-server-4.15.0-linux-arm64.tar.gz`.

```bash
tar -xvf ./code-server-<VERSION>-linux-arm64.tar.gz
```

Move to the extracted directory:

> Please note to replace the angle brackets and their contents with the corresponding version number, for example: `cd code-server-4.15.0-linux-arm64/bin`.

```bash
cd code-server-<VERSION>-linux-arm64/bin
```

Enter the following command to set a temporary password for accessing `code-server`:

> Please note to replace the angle brackets and their contents with your desired password.

```bash
export PASSWORD="<PASSWORD>"
```

Start `code-server`:

```bash
./code-server
```

Open the 8080 app that you downloaded from this repository and enter the password you set to access `code-server`.

### Subsequent Usage

Open `termux` and execute the following commands:

```bash
./start-ubuntu.sh
```

> Please note to replace the angle brackets and their contents with the corresponding version number.

```bash
cd code-server-<VERSION>-linux-arm64/bin
```

> Please note to replace the angle brackets and their contents with your set password.

```bash
export PASSWORD="<PASSWORD>"
```

```bash
./code-server
```

Then open the 8080 app.

### Additional Notes

Since termux stores files in a private directory (data/data), non-root devices cannot access it. Therefore, you can install various server management tools in the Ubuntu environment, such as SSH or web-based tools, to manage files.