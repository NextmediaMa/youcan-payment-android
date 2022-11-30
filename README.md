<p align="center"><a href="https://pay.youcan.shop" target="_blank"><img src="https://pay.youcan.shop/images/ycpay-logo.svg" width="400"></a></p>
  
<p align="center">
<a href="https://pay.youcan.shop"><img src="https://jitpack.io/v/NextmediaMa/youcan_payment_android.svg" alt="Latest Version""></a>
</p>
  
YouCanPay Android SDK makes it quick and easy to build an excellent payment experience in your Android app with [YouCan Pay API](https://pay.youcan.shop/docs).

  
## Requirements :

[minSdkVersion: 21](https://developer.android.com/studio/releases/platforms#5.0)

## Basic Usage

###  Server-side

This integration requires endpoints on your server in order to communicate with YouCanPay API. Use our official libraries for access to the YouCanPay API from your server:  the following steps in our [Documentation](https://pay.youcan.shop/docs) will guide you through.



### Install the YouCan Pay SDK :
<b>Step 1:</b> Add the JitPack repository to your root build.gradle :

```gradle
allprojects {
	repositories {
		...
		maven { url 'https://jitpack.io' }
	}
}
```
<b>Step 2:</b> Then add the dependency.
```gradel
dependencies {
	implementation 'com.github.youcan-shop:youcan-pay-android-sdk:0.5.0'
}
 ```
 ### Set up Payment :
 #### Initials YCPay
 The first step is to initialize YouCanPay SDK by creating an instance using the following parameters : ```pub_key``` and ```this``` as a context 
```java
YCPay ycPay = new YCPay(this, "pub_key");    
 ```
 
##### pub_key 
The seller's YouCanPay account public key. This lets us know who is receiving the payment.

 
 #### Load supported payment methods
 After we initials ```ycPay``` we can load the supprted payment methods using: 
 ```java
 this.ycPay.getAccountConfigLiveData().observe(this, accountConfig -> {
            if (!accountConfig.isSuccess()) {
             	// load config failed.
                return;
            }

            if (accountConfig.isAcceptsCashPlus()) {
                // CashPlus is available
            }
	
            if (accountConfig.isAcceptsCreditCards()) {
                // Credit cards are available
            }
        });

```
### Start Payment Using Credit Card:
When you get ```accountConfig.isAcceptsCreditCards() == true``` it means that the Credit Card payment method is allowed.

#### Initials Card Informtaion
```java
YCPayCardInformation cardInformation = new YCPayCardInformation("card-holder-name", "1234123412341234", "MM", "YY", "CVV");
 ```
#### Initials PayCallbackImpl
PayCallbackImpl is the callback that you can get status of your payment if is success or failure.
```java
PayCallbackImpl payCallback = new PayCallbackImpl() {
            @Override
            public void onSuccess(String transactionId) {
               // ... pay success 
            }

            @Override
            public void onFailure(String message) {
                // ... pay Failure with reason 
            }
};
```
Once the onSuccess callback invoked it means that the transaction is processeded successfully, a transaction ID will be recieved as a parameter that you can submit with your order details. Similarly, onFailure is called when an error is occurred during the payment, and you get the error message as a parameter to show to customer.
 
#### Proceed payment using Credit Card:
You can use ```ycPay.payWithCard```  to proceed your payment use as parametrs the ```token_id``` it can be generated from your server side and received through an endpoint to the mobile application, to generate a token please refer to the [Tokenization section](https://youcanpay.com/docs#tokenization).
```java
try {
	this.ycPay.payWithCard("token_id", ycPayCardInformation, payCallback);
} catch (Exception e) {
	e.printStackTrace();
}
```
### Start Payment Using CashPlus:
If you you get ```accountConfig.isAcceptsCashPlus() == true``` that's mean that you have CashPlus as a payment method

#### Initials CashPlusCallbackImpl
CashPlusCallbackImpl is the callback that provides payment status.
```java
CashPlusCallbackImpl cashPlusCallbackImpl = new CashPlusCallbackImpl() {
            @Override
            public void onSuccess(String transactionId, String cashPlusToken) {
               // ... getting token is success
            }

            @Override
            public void onFailure(String message) {
                // ... getting token Failure with reason 
            }
};
```

#### Proceed payment using CashPlus:
You can use ```ycPay.payWithCashPlus``` to proceed your payment use as parametrs the ```token_id``` it can be generated from your server side and received through an endpoint to the mobile application, to generate a token please refer to the [Tokenization section](https://youcanpay.com/docs#tokenization).
```java
try {
	this.ycPay.payWithCashPlus("token_id", cashPlusCallbackImpl);
} catch (Exception e) {
	e.printStackTrace();
}
```

### Sandbox
YouCan Pay [Sandbox](https://pay.youcan.shop/docs#sandbox) offers an easy way for developers to test YouCan Pay in their test environment.

```java
// setting the sandbox mode
this.ycPay.setSandboxMode(true)
```
