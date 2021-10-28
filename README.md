<p align="center"><a href="https://pay.youcan.shop" target="_blank"><img src="https://pay.youcan.shop/images/ycpay-logo.svg" width="400"></a></p>
  
<p align="center">
<a href="https://pay.youcan.shop"><img src="https://jitpack.io/v/NextmediaMa/youcan_payment_android.svg" alt="Latest Version""></a>
</p>
  
YouCanPay Android SDK makes it quick and easy to build an excellent payment experience in your Android app with [YouCan Pay API](https://pay.youcan.shop/docs).

  
## Requirements :

[minSdkVersion: 21](https://developer.android.com/studio/releases/platforms#5.0)

## Basic Usage

###  Server-side

This integration requires endpoints on your server that talk to the YouCanPay API. Use our official libraries for access to the YouCanPay API from your server:  the following steps in our [Documentation](https://pay.youcan.shop/docs) will guide you through.



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
 
```java
// init YCPay
YCPay ycPay = new YCPay(this, "pub_key");
// init Card Informtaion
YCPayCardInformation cardInformation = new YCPayCardInformation("card-holder-name", "1234123412341234", "12/34", "123");
// init onPayCallback
PayCallbackImpl onPayListener = new PayCallbackImpl() {
            @Override
            public void onSuccess(String transactionId) {
               // ... pay success 
            }

            @Override
            public void onFailure(String message) {
                // ... pay Failure with reason 
            }
        };
        
        
// setting the sandbox mode
ycPay.setSandboxMode(true)
```
Now You can effect your payment 💸 💸 with :

```java
// effect your payment with :
   try {
       ycPay.pay("token_id", cardInformation, onPayListener);;
   } catch (Exception e) {
      e.printStackTrace();
   }
```
