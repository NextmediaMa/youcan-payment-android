<p align="center"><a href="https://pay.youcan.shop" target="_blank"><img src="https://pay.youcan.shop/images/ycpay-logo.svg" width="400"></a></p>


The YCPayment Android SDK makes it quick and easy to build an excellent payment experience in your Android app. We provide a powerful and customizable YCPayWebView that can be used to handle 3DS step and build a fully custom experience.
## Requirements :

[minSdkVersion: 21](https://developer.android.com/studio/releases/platforms#5.0)

## Basic Usage

###  Server-side

This integration requires endpoints on your server that talk to the YouCanPay API. Use our official libraries for access to the YouCanPay API from your server:  the following steps in our [Documentation](https://pay.youcan.shop/docs) will guide you through.

### Add the endpoints :

___Tokenizer endpoint :___ allows you to generate a token as well as a public key on the backend side in order to use it to communicate with YouCanPay API

___Callback endpoint :___  allows you to retrieve the payment status


### Install the YouCan Pay SDK :
<b>Step 1:</b> Add the JitPack repository to your build file
Add it in your root build.gradle at the end of repositories:

```gradel
allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```
<b>Step 2:</b> Add the dependency
```gradel
dependencies {
	  implementation 'com.github.NextmediaMa:youcan_payment_android:Tag'
}

 ```
 ### Set up Payment :
<b>Step 1:</b> init YCPay Elements

```java
// init YCPay
YCPay ycPay = new YCPay("your-pub-key", "token-id");
// init Card Informtaion
YCPayCardInformation cardInformation = new YCPayCardInformation("card-holder-name", "4242424242424242", "10/24", "112");
// init onPayCallBack
PayCallBackImpl onPayListener = new PayCallBackImpl() {
            @Override
            public void onPaySuccess(YCPayResult response) {
               // ... pay success without 3Ds
            }

            @Override
            public void onPayFailure(String response) {
                // ... pay Failure with reason -response-
            }

            @Override
            public void on3DsResult(YCPayResult result) {
              // ... pay need 3Ds to succuss
              ycPayWebView.loadResultUrl(result);
            }
        };
```
<b>Step 2:</b> init YCPayWebView


```java
// init YCPayWebView Compnent
 YCPayWebView ycPayWebView = findViewById(R.id.webPaiment);

//...

// init YCPayWebViewCallBackImpl for Handle result of YCPayWebView
YCPayWebViewCallBackImpl webViewCallBackImpl = new YCPayWebViewCallBackImpl() {
            @Override
            public void onPaySuccess() {
                // ... pay with 3Ds success
            }

            @Override
            public void onPayFailure(String message) {
                // ... pay with 3Ds Failure
            }
        };
// affect callback to ycPayWebView
 ycPayWebView.setWebViewListener(webViewCallBackImpl);

```
Now You can effect your payment 💸 💸 with :

```java
// effect your payment with :
   try {
       ycPay.pay(cardInformation, onPayListener);
   } catch (Exception e) {
      e.printStackTrace();
   }
```

