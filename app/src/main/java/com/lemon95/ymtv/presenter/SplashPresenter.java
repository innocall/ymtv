package com.lemon95.ymtv.presenter;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.lemon95.ymtv.R;
import com.lemon95.ymtv.bean.DeviceLogin;
import com.lemon95.ymtv.bean.Recommend;
import com.lemon95.ymtv.bean.Result;
import com.lemon95.ymtv.bean.Version;
import com.lemon95.ymtv.bean.Video;
import com.lemon95.ymtv.bean.VideoType;
import com.lemon95.ymtv.bean.impl.ISplashBean;
import com.lemon95.ymtv.common.AppConstant;
import com.lemon95.ymtv.dao.SplashDao;
import com.lemon95.ymtv.db.DataBaseDao;
import com.lemon95.ymtv.myview.ConfirmDialog;
import com.lemon95.ymtv.utils.AppSystemUtils;
import com.lemon95.ymtv.utils.LogUtils;
import com.lemon95.ymtv.utils.PreferenceUtils;
import com.lemon95.ymtv.utils.QRUtils;
import com.lemon95.ymtv.utils.StringUtils;
import com.lemon95.ymtv.utils.XmlUtils;
import com.lemon95.ymtv.view.activity.SplashActivity;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import okhttp3.ResponseBody;

/**
 * Created by WXT on 2016/7/14.
 * 接口顺序
 * 1、检测app版本
 * 2、获取每日推荐数据
 *
 */
public class SplashPresenter {

    private static final String TAG = "SplashPresenter";
    private static final int TOMAIN = 0;
    private SplashActivity splashActivity;
    private ISplashBean iSplashBean;
    private AlertDialog alertDialog; //表示提示对话框、进度条对话框
    private static final int DOWNLOADING = 1; //表示正在下载
    private static final int DOWNLOADED = 2; //下载完毕
    private static final int DOWNLOAD_FAILED = 3; //下载失败
    private int progress; //下载进度
    private boolean cancelFlag = false; //取消下载标志位
    private String urls;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case TOMAIN:
                    splashActivity.toMainActivity();
                    break;
                case DOWNLOADING:
                    splashActivity.mProgress.setProgress(progress);
                    break;
                case DOWNLOADED:
                    if (alertDialog != null)
                        alertDialog.dismiss();
                    splashActivity.lemon_updata_pro.setVisibility(View.GONE);
                    installAPK();
                    break;
                case DOWNLOAD_FAILED:
                    Toast.makeText(splashActivity, "网络断开，请稍候再试", Toast.LENGTH_LONG).show();
                    break;
            }
        }
    };

    public SplashPresenter(SplashActivity splashActivity) {
        this.splashActivity = splashActivity;
        iSplashBean = new SplashDao();
    }

    /**
     * 检测版本
     * @param newVersion
     */
    public void checkVersion(final String newVersion) {
        String url = AppConstant.VERSIONURL;
        iSplashBean.checkVersion(url, new SplashDao.OnVersionLisener() {
            @Override
            public void updateVersion(ResponseBody responseBody) {
                try {
                    String ver = responseBody.string();
                    LogUtils.i(TAG,ver);
                    Version version = XmlUtils.formentVersion(ver);
                    if (newVersion.equals(version.getVersionId())) {
                        //版本相同，不需要更新
                        getRecommends();
                    } else {
                        upDateVersion(version);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    getRecommends();
                }
            }

            @Override
            public void noUpdateVersion() {
               // splashActivity.showToastShort("最新版本");
                getRecommends();
            }
        });
    }

    /**
     * 更新版本
     * @param version
     */
    private void upDateVersion(final Version version) {
        if ("true".equals(version.getIsUpdate())) {
            //强制更新
            ConfirmDialog.Builder dialog = new ConfirmDialog.Builder(splashActivity);
            dialog.setMessage("有新版本需要更新");
            dialog.setPositiveButton("更新", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    urls = version.getUrl();
                    showLoad();
                }
            });
            dialog.create().show();
        } else {
            //选择更新
            ConfirmDialog.Builder dialog = new ConfirmDialog.Builder(splashActivity);
            dialog.setMessage("有新版本需要更新");
            dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    getRecommends();
                }
            }).setPositiveButton("更新", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    urls = version.getUrl();
                    showLoad();
                }
            });
            dialog.create().show();
        }
    }

    private void showLoad() {
        splashActivity.lemon_updata_pro.setVisibility(View.VISIBLE);
        //下载apk
        downloadAPK();
    }

    private void downloadAPK() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL(urls);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.connect();
                    int length = conn.getContentLength();
                    InputStream is = conn.getInputStream();
                    String sdPath = Environment.getExternalStorageDirectory().getPath();
                    String sdStatus = Environment.getExternalStorageState();
                    if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) { // 检测sd是否可用
                        LogUtils.v("TestFile", "SD card is not avaiable/writeable right now.");
                        return;
                    }
                    File file = new File(sdPath + AppConstant.DIRS);
                    if(!file.exists()){
                        file.mkdir();
                    }
                    String apkFile = "ymtv.apk";
                    File ApkFile = new File(sdPath + AppConstant.DIRS + "/" + apkFile);
                    FileOutputStream fos = new FileOutputStream(ApkFile);
                    int count = 0;
                    byte buf[] = new byte[1024];
                    do{
                        int numread = is.read(buf);
                        count += numread;
                        progress = (int)(((float)count / length) * 100);
                        //更新进度
                        handler.sendEmptyMessage(DOWNLOADING);
                        if(numread <= 0){
                            //下载完成通知安装
                            handler.sendEmptyMessage(DOWNLOADED);
                            break;
                        }
                        fos.write(buf, 0, numread);
                    }while(!cancelFlag); //点击取消就停止下载.

                    fos.close();
                    is.close();
                } catch(Exception e) {
                    handler.sendEmptyMessage(DOWNLOAD_FAILED);
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /** 下载完成后自动安装apk */
    public void installAPK() {
        String sdPath = Environment.getExternalStorageDirectory().getPath();
        String saveFileName = sdPath + AppConstant.DIRS + "/ymtv.apk";
        File apkFile = new File(saveFileName);
        if (!apkFile.exists()) {
            return;
        }
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setDataAndType(Uri.parse("file://" + apkFile.toString()), "application/vnd.android.package-archive");
        splashActivity.startActivity(intent);
        splashActivity.finish();
    }

    /**
     * 获取每日推荐数据
     */
    public void getRecommends() {
        iSplashBean.getRecommends(new SplashDao.OnVideoListener() {
            @Override
            public void onSuccess(Recommend recommend) {
                //数据获取成功
                final List<Recommend.Data> data = recommend.getData();
                if (data == null) {
                    LogUtils.e(TAG, "每日推荐获取为空");
                } else {
                    initVideoData(data);  //保存数据到数据库
                }
                initVideoType();
            }

            @Override
            public void onFailure(Throwable e) {
                initVideoType();
            }
        });
    }

    /**
     * 获取影视分类
     */
    public void initVideoType() {
        iSplashBean.getVideoType(new SplashDao.OnVideoTypeListener() {
            @Override
            public void onSuccess(VideoType videoType) {
                //数据获取成功
                final List<VideoType.Data> data = videoType.getData();
                if (data == null) {
                    LogUtils.e(TAG, "每日推荐获取为空");
                } else {
                    saveDateToVideoType(data);
                }
                splashActivity.toMainActivity();
            }

            @Override
            public void onFailure(Throwable e) {
                splashActivity.toMainActivity();
            }
        });
    }

    /**
     * 保存影视分类数据到数据库
     * @param data
     */
    private void saveDateToVideoType(List<VideoType.Data> data) {
        for (int i=0;i<data.size();i++) {
            com.lemon95.ymtv.bean.VideoType.Data d = data.get(i);
            //保存数据到数据库
            DataBaseDao baseDao = new DataBaseDao(splashActivity);
            baseDao.addOrUpdateVideoType(d);
        }
    }

    /**
     * 保存每日推荐数据到数据库
     * @param data
     */
    private void initVideoData(List<Recommend.Data> data) {
        for (int i=0;i<data.size();i++) {
            com.lemon95.ymtv.bean.Recommend.Data d = data.get(i);
            Video video = new Video();
            video.setVideoTypeId(d.getVideoTypeId());
            video.setVideoId(d.getVideoId());
            video.setTitle(d.getTitle());
            video.setOrderNum(d.getOrderNum());
            video.setPicturePath(d.getPicturePath());
            //保存数据到数据库
            DataBaseDao baseDao = new DataBaseDao(splashActivity);
            baseDao.addOrUpdateVideo(video);
        }
    }

    public void createToken() {
        iSplashBean.GenerateToken(AppSystemUtils.getDeviceId(), new SplashDao.OnResultListener() {
            @Override
            public void onSuccess(Result deviceLogin) {
                String token = deviceLogin.getData();
                if (deviceLogin != null && StringUtils.isNotBlank(token)) {
                    PreferenceUtils.putString(splashActivity,AppConstant.MACTOKEN,token);
                    createOr(token);
                }
            }

            @Override
            public void onFailure(Throwable e) {
                e.printStackTrace();
            }
        });
    }

    public void start() {
        //检测版本更新
        checkVersion(splashActivity.getVersion());
        //生成登录二维码
        String token = PreferenceUtils.getString(splashActivity,AppConstant.MACTOKEN,"");
        if (StringUtils.isBlank(token)) {
            createToken();
        } else {
            createOr(token);
            loginUser(token);
        }
    }

    /**
     * 登录用户
     * @param token
     */
    private void loginUser(String token) {
        iSplashBean.deviceLogin(token, new SplashDao.OnDeviceLoginListener() {
            @Override
            public void onSuccess(DeviceLogin deviceLogin) {
                if (deviceLogin != null && deviceLogin.getData() != null) {
                    com.lemon95.ymtv.bean.DeviceLogin.Data user = deviceLogin.getData();
                    PreferenceUtils.putString(splashActivity, AppConstant.USERID,user.getId());
                    PreferenceUtils.putString(splashActivity, AppConstant.USERNAME,user.getNickName());
                    PreferenceUtils.putString(splashActivity, AppConstant.USERIMG,user.getHeadImgUrl());
                    PreferenceUtils.putString(splashActivity, AppConstant.USERMOBILE,user.getMobile());
                }
            }

            @Override
            public void onFailure(Throwable e) {

            }
        });
    }

    /**
     * 生成二维码
     */
    private void createOr(String token) {
        String sdPath = Environment.getExternalStorageDirectory().getPath();
        String sdStatus = Environment.getExternalStorageState();
        if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) { // 检测sd是否可用
            LogUtils.v("TestFile", "SD card is not avaiable/writeable right now.");
            return;
        }
        String PATH = "";
        File file2 = new File(sdPath + AppConstant.DIRS);
        if (!file2.exists()) {
            file2.mkdirs();
        }
        File dir = new File(sdPath + AppConstant.DIRS + AppConstant.QRNAME);
        if (!dir.exists()) {
            try {
                //在指定的文件夹中创建文件
                dir.createNewFile();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        LogUtils.e(TAG,"生成二维码");
        Bitmap bitmap = BitmapFactory.decodeResource(splashActivity.getResources(), R.drawable.ic_launcher);
        boolean is = QRUtils.createQRImage(token,220,220,bitmap,sdPath + AppConstant.DIRS + AppConstant.QRNAME);
    }
}
