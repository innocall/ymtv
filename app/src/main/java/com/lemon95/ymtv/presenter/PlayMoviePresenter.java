package com.lemon95.ymtv.presenter;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import com.google.gson.Gson;
import com.lemon95.ymtv.bean.ForWechat;
import com.lemon95.ymtv.bean.GetOrder;
import com.lemon95.ymtv.bean.Movie;
import com.lemon95.ymtv.bean.MovieSources;
import com.lemon95.ymtv.bean.RealSource;
import com.lemon95.ymtv.bean.Unifiedorder;
import com.lemon95.ymtv.bean.UploadResult;
import com.lemon95.ymtv.bean.VideoWatchHistory;
import com.lemon95.ymtv.bean.WxPayDto;
import com.lemon95.ymtv.bean.impl.IMovieBean;
import com.lemon95.ymtv.common.AppConstant;
import com.lemon95.ymtv.dao.MovieDao;
import com.lemon95.ymtv.utils.AppSystemUtils;
import com.lemon95.ymtv.utils.LogUtils;
import com.lemon95.ymtv.utils.PreferenceUtils;
import com.lemon95.ymtv.utils.RandomSecquenceCreator;
import com.lemon95.ymtv.utils.StringUtils;
import com.lemon95.ymtv.utils.ToastUtils;
import com.lemon95.ymtv.utils.WeixinUtil;
import com.lemon95.ymtv.utils.XmlUtils;
import com.lemon95.ymtv.view.activity.PlayActivity;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import okhttp3.ResponseBody;


/**
 * Created by wuxiaotie on 16/7/23.
 * 电影播放页
 */
public class PlayMoviePresenter {

    private static final String TAG = "PlayMoviePresenter";
    private PlayActivity playActivity;
    private IMovieBean iMovieBean;
    private boolean isParam = true;
    public boolean isOrder = false;

    public PlayMoviePresenter(PlayActivity playActivity) {
        this.playActivity = playActivity;
        iMovieBean = new MovieDao();
    }

    /**
     * 获取电影详情
     * @param id
     * @param userId
     * @param isPre
     */
    public void initPageData(final String id, final String userId,boolean isPre) {
        iMovieBean.getMovieDetails(id,userId,isPre, new MovieDao.OnMovieDetailsListener(){

            @Override
            public void onSuccess(Movie movie) {
                com.lemon95.ymtv.bean.Movie.Data data = movie.getData();
                if (data != null) {
                    List<Movie.Data.MovieSources> movieSources = data.getMovieSources();
                    if (movieSources != null && movieSources.size() > 0) {
                        String sources = movieSources.get(0).getRealSource();
                        if (StringUtils.isBlank(sources)) {
                            playActivity.showError("播放失败，视频地址不存在");
                        } else {
                            sources = sources.replace("\\", "");
                            Gson gson = new Gson();
                            RealSource realSource = gson.fromJson(sources, RealSource.class);
                            List<RealSource.seg> seg = realSource.getSeg();
                            if (seg != null && seg.size() > 0) {
                                RealSource.seg s = seg.get(RandomSecquenceCreator.getRandom(seg.size()));
                                LogUtils.i("播放地址：", s.getFurl());
                                playActivity.startPlay(s.getFurl());
                                if ("false".equals(data.getEnable())) {
                                    playActivity.isPro = true;
                                    setIsParam(true);
                                    createOrder(userId, "13", id);  //生产订单
                                }
                            }
                        }
                    } else {
                        playActivity.showError("播放失败，视频地址不存在");
                    }
                } else {
                    playActivity.showError("播放失败，视频地址不存在");
                }
            }

            @Override
            public void onFailure(Throwable e) {
                e.printStackTrace();
                playActivity.showError("播放失败，视频地址不存在");
            }
        });
    }

    public void getPlayUrl(String videoId) {
        iMovieBean.getMovieAnalysis(videoId, new MovieDao.OnMovieAnalysisListener() {

            @Override
            public void onSuccess(List<MovieSources> movieSources) {
                if (movieSources != null && movieSources.size() > 0) {
                    String sources = movieSources.get(0).getRealSource();
                    if (StringUtils.isBlank(sources)) {
                        playActivity.showError("播放失败，视频地址不存在");
                    } else {
                        sources = sources.replace("\\", "");
                        Gson gson = new Gson();
                        RealSource realSource = gson.fromJson(sources, RealSource.class);
                        List<RealSource.seg> seg = realSource.getSeg();
                        if (seg != null && seg.size() > 0) {
                            RealSource.seg s = seg.get(RandomSecquenceCreator.getRandom(seg.size()));
                            LogUtils.i("播放地址：", s.getFurl());
                            playActivity.startPlay(s.getFurl());
                        }
                    }
                } else {
                    playActivity.showError("播放失败，视频地址不存在");
                }
            }

            @Override
            public void onFailure(Throwable e) {
                e.printStackTrace();
                playActivity.showError("播放失败，视频地址不存在");
            }
        });
    }

    public void getPlaySerialUrl(String videoId) {
        LogUtils.i(TAG,"电视剧ID:" + videoId);
        iMovieBean.getSerialAnalysis(videoId, new MovieDao.OnSerialAnalysisListener() {
            @Override
            public void onSuccess(String movieSources) {
                if (StringUtils.isBlank(movieSources)) {
                    playActivity.showError("播放失败，视频地址不存在");
                } else {
                    movieSources = movieSources.replace("\\", "");
                    Gson gson = new Gson();
                    RealSource realSource = gson.fromJson(movieSources, RealSource.class);
                    List<RealSource.seg> seg = realSource.getSeg();
                    if (seg != null && seg.size() > 0) {
                        RealSource.seg s = seg.get(RandomSecquenceCreator.getRandom(seg.size()));
                        LogUtils.i("播放地址：", s.getFurl());
                        playActivity.startPlay(s.getFurl());
                    }
                }
            }

            @Override
            public void onFailure(Throwable e) {
                e.printStackTrace();
                playActivity.showError("播放失败，视频地址不存在");
            }
        });
    }

    public void createUrl(final String xml) {
       iMovieBean.unifiedorder(xml, new MovieDao.OnUnifiedorderListener() {

           @Override
           public void onSuccess(ResponseBody responseBody) {
               try {
                   String str = responseBody.string();
                   LogUtils.e(TAG, str);
                   Unifiedorder unifiedorder = XmlUtils.fiedorder(str);
                   if (unifiedorder != null && "SUCCESS".equals(unifiedorder.getReturn_code())) {
                       playActivity.initPay(AppConstant.QR_TOP + unifiedorder.getCode_url());
                   }
               } catch (Exception e) {
                   e.printStackTrace();
                   if (isParam) {
                       createUrl(xml);
                   }
               }
           }

           @Override
           public void onFailure(Throwable e) {
               if (isParam) {
                   createUrl(xml);
               }
               e.printStackTrace();
           }
       });
    }

    public void addVideoHistory(VideoWatchHistory videoWatchHistory) {
        iMovieBean.addVideoWatchHistory(videoWatchHistory, new MovieDao.OnUpdateListener() {

            @Override
            public void onSuccess(UploadResult uploadResult) {

            }

            @Override
            public void onFailure(Throwable e) {

            }
        });
    }

    public void createOrder(final String userId, final String s, final String videoId) {
        iMovieBean.getForWechat(userId, s, videoId, new MovieDao.OnForWechatListener() {
            @Override
            public void onSuccess(ForWechat forWechat) {
                ForWechat.Data data = forWechat.getData();
                if (data != null) {
                    playActivity.orderId = data.getOutTradeNo();
                    // 统一下单
                    WxPayDto dto = new WxPayDto();
                    dto.setAppid(AppConstant.appid);
                    dto.setMch_id(AppConstant.mch_id);
                    dto.setDevice_info(AppSystemUtils.getDeviceId());
                    dto.setNonce_str(WeixinUtil.getNonceStr());
                    dto.setSpbillCreateIp(AppConstant.spbillCreateIp);
                    dto.setPartnerkey(AppConstant.partnerKey);
                    dto.setNotifyUrl(data.getNotifyUrl()); //支付完回调
                    dto.setOrderId(data.getOutTradeNo());
                    dto.setBody(data.getSubject());
                    dto.setTotalFee(WeixinUtil.getMoney(data.getTotalFee()));
                    // 生成订单签名
                    String sign = WeixinUtil.getmSign(dto);
                    dto.setSign(sign);
                    // 获取微信扫码支付二维码连接参数
                    String xml = WeixinUtil.getCodeUrl(dto);
                    createUrl(xml);
                }
            }

            @Override
            public void onFailure(Throwable e) {
                if (isParam) {
                    createOrder(userId, s, videoId);
                }
                e.printStackTrace();
            }
        });
    }

    public boolean isParam() {
        return isParam;
    }

    public void setIsParam(boolean isParam) {
        this.isParam = isParam;
    }

    public void findOrder(final String orderId) {
        if (StringUtils.isBlank(orderId) || isOrder) {
            return;
        }
        iMovieBean.getOrder(orderId, new MovieDao.OnOrderListener() {
            @Override
            public void onSuccess(GetOrder getOrder) {
                GetOrder.Data data = getOrder.getData();
                if (data != null) {
                    if ("1".equals(data.getStatus())) {
                        //支付成功
                        playActivity.hidePay();
                    } else {
                        findOrder(orderId);
                    }
                } else {
                    findOrder(orderId);
                }
            }

            @Override
            public void onFailure(Throwable e) {
                findOrder(orderId);
            }
        });
    }
}
