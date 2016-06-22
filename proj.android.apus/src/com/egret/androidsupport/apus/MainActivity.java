package com.egret.androidsupport.apus;

import java.util.HashMap;
import java.util.Map;

import org.egret.egretframeworknative.engine.EgretGameEngine;
import org.egret.runtime.nest.NestDelegate;

import android.util.Log;

import com.egret.nest.apussdk.NestAppImpl;
import com.egret.nest.apussdk.NestLoginImpl;
import com.egret.nest.apussdk.NestPayImpl;
import com.egret.nest.apussdk.NestShareImpl;
import com.egret.nest.apussdk.NestSocialImpl;
import com.apusplay.sdk.ApusPlaySdk;
import com.apusplay.sdk.ICallBack;
import com.apusplay.sdk.InitCallBack;
import com.apusplay.sdk.InitConfig;
import com.apusplay.sdk.InitResult;
import com.apusplay.sdk.SdkConstants;
import com.apusplay.sdk.UserProfile;
import com.apusplay.sdk.auth.AuthCallBack;
import com.apusplay.sdk.exception.ValidationException;

public class MainActivity extends com.egret.androidsupport.GameActivity {
    protected static final String TAG = MainActivity.class.toString();
	Map properties;
	EgretGameEngine mGameEngine;
	@Override
	protected void onCreateEgretGameEngine(EgretGameEngine gameEngine) {
		// TODO Auto-generated method stub
		super.onCreateEgretGameEngine(gameEngine);
		if(null == gameEngine){
			Log.e(TAG,"createApusSDK mGameEngine is lost");
			return;
		}
		NestDelegate.temp_instance.API_DOMAIN = "http://tw.api.egret.com/v2/";
		mGameEngine = gameEngine;
        createApusSDK(gameEngine); 
	}
	/**
	 * 开放平台后台配置参数
	 * spid
	 * appkey
	 */
	@Override
	protected HashMap<String, Object> getGameOptions() {
		HashMap<String,Object> options = super.getGameOptions();
        options.put("egret.runtime.nest", "4");
        options.put("egret.runtime.spid", "21110");
        options.put("egret.runtime.appkey","XiSoPPcKPB4RO8Ejr9rTZ");
		return options;
	}

	private InitConfig getInitConfig() {
		InitConfig initConfig = new InitConfig() {
			@Override
			public  String getAppId() {
				return Constants.appId;
			}

			@Override
			public  String getAppKey() {
				return Constants.appSecret;
			}

			/**
			 * The value to public key 
			 * Google In-App Billing approach, get this "Services & APIs " 
			 * @return the base64 encoded with public key
			 */
			@Override
			public String getPublicKey() {
				// The based64 encoded public key by Google Play publish
				return Constants.publicKey;
			}

			@Override
			public String getApusImId() {
				return Constants.imId;
			}

			@Override
			public String getApusAppSource() {
				return Constants.appSource;
			}

			@Override
			public boolean isSupportTopup() {
				return true;
			}

		};
		return initConfig;
	}
	/**
     * 创建并配置quickSDK
     * @param mGameEngine
     */
  	private void createApusSDK(EgretGameEngine gameEngine){
    		try {
			ApusPlaySdk.initialize(getApplicationContext(), getInitConfig(), new InitCallBack() {
				@Override
				public void onAbort() {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void onFailure(Throwable t, int errorCode, String msg) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void onSuccess(InitResult result) {
					// TODO Auto-generated method stub
					Log.d(TAG,"Apus SDK init result:"+result.isFailure());
					NestLoginImpl mLoginImpl = new NestLoginImpl(MainActivity.this,mGameEngine);
					NestPayImpl mPayImpl = new NestPayImpl(MainActivity.this);
					NestAppImpl mAppImpl = new NestAppImpl();
					NestShareImpl mShareImpl = new NestShareImpl(MainActivity.this);
			    	
				    	Util.registerPlugin(mGameEngine, "user", mLoginImpl);
				    	Util.registerPlugin(mGameEngine, "iap", mPayImpl);
				    	Util.registerPlugin(mGameEngine, "app", mAppImpl);
				    	Util.registerPlugin(mGameEngine, "share", mShareImpl);
				    	Util.registerPlugin(mGameEngine, "social", new NestSocialImpl());
				}
			});
		} catch (ValidationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		QuickSDK.getInstance()
//		.setIsLandScape(isLandscape)
//		.setInitNotifier(new InitNotifier(){
//			@Override
//			public void onFailed(String message, String trace) {
////				QuickSDKOperator.getInstance().onInitFailed(message,trace);
//			}
//
//			@Override
//			public void onSuccess() {
////				QuickSDKOperator.getInstance().onInitSucess();
//			}
//		})
//		// 3.设置登录通知
//		.setLoginNotifier(mLoginImpl.loginNotifier)
//		// 4.设置注销通知
//		.setLogoutNotifier(mLoginImpl.logoutNotifier)
//		// 5.设置支付通知
//		.setPayNotifier(mPayImpl)
//		// 6.设置退出通知
//		.setExitNotifier(mAppImpl.exitNotifier);
//	
//		com.quicksdk.Sdk.getInstance().init(this, productCode, productKey);
//		com.quicksdk.Sdk.getInstance().onCreate(this);
	}
}