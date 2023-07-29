package com.dev.a8080

import android.Manifest
import android.annotation.SuppressLint
import android.app.Application
import android.app.DownloadManager
import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.graphics.Bitmap
import android.net.Uri
import android.net.http.SslError
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.Settings
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.webkit.SslErrorHandler
import android.webkit.WebChromeClient
import android.webkit.WebResourceRequest
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsAnimationCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.color.DynamicColors
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class CustomWebView(context: Context) : WebView(context) {
    override fun onSizeChanged(w: Int, h: Int, ow: Int, oh: Int) {
        super.onSizeChanged(w, h, ow, oh)
        requestLayout()
    }
}

class TipTimeApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        DynamicColors.applyToActivitiesIfAvailable(this)
    }
}

class MainActivity : AppCompatActivity() {
    private var originalPaddingBottom = 0
    private lateinit var myWebView: WebView
    private lateinit var progressBar: View

    @SuppressLint("UseCompatLoadingForDrawables", "ResourceAsColor")
    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            val cutoutMode = window.attributes.layoutInDisplayCutoutMode
            if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
                window.attributes.layoutInDisplayCutoutMode =
                    WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES
            } else {
                window.attributes.layoutInDisplayCutoutMode = cutoutMode
            }
        }
        window.decorView.apply {
            systemUiVisibility =
                if ((context.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) == Configuration.UI_MODE_NIGHT_NO) {
                    window.setBackgroundDrawableResource(R.drawable.white)
                    window.navigationBarColor = getColor(R.color.white)
                    window.statusBarColor = getColor(R.color.white)
                    setBackgroundColor(getColor(R.color.white))
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR or WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS
                } else {
                    window.setBackgroundDrawableResource(R.drawable.black)
                    window.navigationBarColor = getColor(R.color.black)
                    window.statusBarColor = getColor(R.color.black)
                    setBackgroundColor(getColor(R.color.black))
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY or WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS or View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                }
        }
    }


    @RequiresApi(Build.VERSION_CODES.Q)
    @SuppressLint("SetJavaScriptEnabled", "MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        window.decorView.apply {
            systemUiVisibility =
                if ((context.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) == Configuration.UI_MODE_NIGHT_NO) {
                    window.setBackgroundDrawableResource(R.drawable.white)
                    window.navigationBarColor = getColor(R.color.white)
                    window.statusBarColor = getColor(R.color.white)
                    setBackgroundColor(getColor(R.color.white))
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR or WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS
                } else {
                    window.setBackgroundDrawableResource(R.drawable.black)
                    window.navigationBarColor = getColor(R.color.black)
                    window.statusBarColor = getColor(R.color.black)
                    setBackgroundColor(getColor(R.color.black))
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY or WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS or View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                }
        }

        @RequiresApi(Build.VERSION_CODES.R) if (!Environment.isExternalStorageManager()) {
            val builder = MaterialAlertDialogBuilder(
                this
            )
            builder.setTitle("请求所有文件访问权限").setIcon(R.drawable.database_2_line)
                .setMessage("Android WebView 可能调用文件，是否给予所有文件访问权限？")
                .setPositiveButton("前往权限设置") { _, _ ->
                    var intent = Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION)
                    intent.data = Uri.parse("package:" + this.packageName)
                    val rmes = 100
                    startActivityForResult(intent, rmes)
                }.setNeutralButton("拒绝") { _, _ -> }
            val dialog = builder.create()
            dialog.show()

        }
        //隐藏导航栏
        window.decorView.apply {
            systemUiVisibility =
                if ((context.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) == Configuration.UI_MODE_NIGHT_NO) {
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR or WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS
                } else {
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY or WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS
                }
        }

        //强制横屏
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE


        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        myWebView = findViewById(R.id.webview)
        var rootLayout: ViewGroup? = findViewById(R.id.rootLayout)
        progressBar = findViewById(R.id.progressBar)


        //权限申请
        if (ContextCompat.checkSelfPermission(
                this, Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_DENIED
        ) {
            val requestCode = 100
            ActivityCompat.requestPermissions(
                this, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), requestCode
            )
        }


        if (ContextCompat.checkSelfPermission(
                this, Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_DENIED
        ) {
            val requestCode = 100
            ActivityCompat.requestPermissions(
                this, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), requestCode
            )
        }
        if (ContextCompat.checkSelfPermission(
                this, Manifest.permission.READ_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_DENIED
        ) {
            val requestCode = 100
            ActivityCompat.requestPermissions(
                this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), requestCode
            )
        }
        if (ContextCompat.checkSelfPermission(
                this, Manifest.permission.READ_MEDIA_AUDIO
            ) == PackageManager.PERMISSION_DENIED
        ) {
            val requestCode = 100
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                ActivityCompat.requestPermissions(
                    this, arrayOf(Manifest.permission.READ_MEDIA_AUDIO), requestCode
                )
            }
        }
        if (ContextCompat.checkSelfPermission(
                this, Manifest.permission.READ_MEDIA_VIDEO
            ) == PackageManager.PERMISSION_DENIED
        ) {
            val requestCode = 100
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                ActivityCompat.requestPermissions(
                    this, arrayOf(Manifest.permission.READ_MEDIA_VIDEO), requestCode
                )
            }
        }
        if (ContextCompat.checkSelfPermission(
                this, Manifest.permission.READ_MEDIA_IMAGES
            ) == PackageManager.PERMISSION_DENIED
        ) {
            val requestCode = 100
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                ActivityCompat.requestPermissions(
                    this, arrayOf(Manifest.permission.READ_MEDIA_IMAGES), requestCode
                )
            }
        }

        //WebView的设置
        //myWebView.clearCache(true)
        myWebView.webViewClient = WebViewClient()
        myWebView.settings.javaScriptEnabled = true
        myWebView.settings.domStorageEnabled = true
        myWebView.settings.safeBrowsingEnabled = false
        myWebView.settings.cacheMode = WebSettings.LOAD_NO_CACHE
        myWebView.settings.databaseEnabled = true
        myWebView.settings.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
        myWebView.settings.pluginState = WebSettings.PluginState.ON
        myWebView.settings.allowFileAccess = true
        myWebView.setBackgroundColor(0)
        myWebView.background.alpha = 0
        myWebView.settings.loadWithOverviewMode = true
        myWebView.settings.javaScriptCanOpenWindowsAutomatically = true
        myWebView.settings.allowUniversalAccessFromFileURLs = true
        myWebView.settings.loadsImagesAutomatically = true
        myWebView.settings.defaultTextEncodingName = "utf-8"
        myWebView.settings.allowContentAccess = true
        myWebView.settings.forceDark = WebSettings.FORCE_DARK_AUTO
        originalPaddingBottom = myWebView.paddingBottom

        myWebView.setDownloadListener { url, _, _, _, _ ->
            val builder = MaterialAlertDialogBuilder(
                this
            )
            builder.setTitle("下载文件？").setIcon(R.drawable.download_line).setMessage(url)
                .setPositiveButton("下载") { _, _ ->
                    //系统下载
                    val request = DownloadManager.Request(Uri.parse(url))
                    val downloadManager =
                        getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
                    downloadManager.enqueue(request)
                }.setNeutralButton("拒绝") { _, _ -> }
            val dialog = builder.create()
            dialog.show()
        }



        myWebView.webViewClient = object : WebViewClient() {
            @Deprecated("Deprecated in Java", ReplaceWith("false"))
            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                return false
            }

            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
            }

            @SuppressLint("QueryPermissionsNeeded")
            @Deprecated("Deprecated in Java")
            override fun shouldOverrideUrlLoading(
                view: WebView, request: WebResourceRequest
            ): Boolean {
                val url = request.url.toString()

                val uri = Uri.parse(url)

                //检测是否127.0.0.1
                val host = uri.host
                if (host != null && (host == "127.0.0.1" || host.endsWith(".127.0.0.1") || host == "localhost")) {
                    view.loadUrl(url)
                } else {
                    val builder = MaterialAlertDialogBuilder(
                        this@MainActivity,
                        com.google.android.material.R.style.ThemeOverlay_Material3_MaterialAlertDialog
                    )
                    builder.setTitle("打开外链？").setMessage(url)
                        .setIcon(R.drawable.share_circle_line).setPositiveButton("打开") { _, _ ->
                            val customTabsIntent: CustomTabsIntent =
                                CustomTabsIntent.Builder().build()
                            customTabsIntent.launchUrl(this@MainActivity, Uri.parse(url))
                        }.setNeutralButton("拒绝") { _, _ -> }
                    val dialog = builder.create()
                    dialog.show()
                }
                return true
            }

            @SuppressLint("WebViewClientOnReceivedSslError")
            override fun onReceivedSslError(
                view: WebView?, handler: SslErrorHandler?, error: SslError?
            ) {
                handler?.proceed()//信任所有SSL证书
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                myWebView.requestLayout()
            }
        }
        myWebView.settings.userAgentString =
            "Mozilla/5.0 (Windows NT 10.0; Win64; arm64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/116.0.0.0 Safari/537.36"


        //适配状态栏、异形屏和软键盘高度
        ViewCompat.setOnApplyWindowInsetsListener(rootLayout!!) { _, insets ->
            val statusBarHeight = insets.getInsets(WindowInsetsCompat.Type.systemBars()).top
            rootLayout.setPadding(0, statusBarHeight, 0, 0)
            val imeVisible = insets.isVisible(WindowInsetsCompat.Type.ime())
            val cutoutLeft = rootLayout.rootWindowInsets.displayCutout?.safeInsetLeft ?: 0
            val cutoutRight = rootLayout.rootWindowInsets.displayCutout?.safeInsetRight ?: 0

            if (imeVisible) {
                val imeHeight = insets.getInsets(WindowInsetsCompat.Type.ime()).bottom
                rootLayout.setPadding(cutoutLeft, statusBarHeight, cutoutRight, imeHeight)
            } else {
                rootLayout.setPadding(cutoutLeft, statusBarHeight, cutoutRight, 0)
            }
            WindowInsetsCompat.Builder(insets).build()
        }
        myWebView.webChromeClient = object : WebChromeClient() {
            override fun onProgressChanged(view: WebView?, newProgress: Int) {
                super.onProgressChanged(view, newProgress)
                if (newProgress == 100) {
                    progressBar.visibility = View.GONE
                    progressBar.bringToFront()
                } else {
                    progressBar.visibility = View.VISIBLE
                }
            }
        }

        myWebView.loadUrl("http://127.0.0.1:8080")

        //软键盘动画
        ViewCompat.setWindowInsetsAnimationCallback(
            rootLayout,
            object : WindowInsetsAnimationCompat.Callback(DISPATCH_MODE_STOP) {
                var startBottom = 0f

                override fun onPrepare(animation: WindowInsetsAnimationCompat) {
                    startBottom = rootLayout.bottom.toFloat()
                }

                var endBottom = 0f

                override fun onStart(
                    animation: WindowInsetsAnimationCompat,
                    bounds: WindowInsetsAnimationCompat.BoundsCompat
                ): WindowInsetsAnimationCompat.BoundsCompat {
                    endBottom = rootLayout.bottom.toFloat()

                    return bounds
                }

                override fun onProgress(
                    insets: WindowInsetsCompat,
                    runningAnimations: MutableList<WindowInsetsAnimationCompat>
                ): WindowInsetsCompat {
                    val imeAnimation = runningAnimations.find {
                        it.typeMask and WindowInsetsCompat.Type.ime() != 0
                    } ?: return insets

                    myWebView.translationY =
                        (startBottom - endBottom) * (1 - imeAnimation.interpolatedFraction)

                    return insets
                }
            })
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        if (myWebView.canGoBack()) {
            myWebView.goBack()
        } else {
            super.onBackPressed()
        }
    }


}