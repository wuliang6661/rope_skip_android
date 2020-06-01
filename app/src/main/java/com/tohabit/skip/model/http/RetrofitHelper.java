package com.tohabit.skip.model.http;

import android.util.Log;

import com.tohabit.skip.BuildConfig;
import com.tohabit.skip.app.App;
import com.tohabit.skip.app.Constants;
import com.tohabit.skip.model.http.api.BusinessApis;
import com.tohabit.skip.model.http.interceptor.AddTokenInterceptor;
import com.tohabit.skip.model.http.interceptor.CacheInterceptor;
import com.tohabit.skip.model.http.interceptor.HttpLoggingInterceptor;
import com.tohabit.skip.model.http.interceptor.ProgressInterceptor;
import com.tohabit.skip.model.http.interceptor.ReceivedAuthorizationInterceptor;
import com.tohabit.skip.utils.LogUtil;
import com.tohabit.skip.utils.PrefUtils;
import com.orhanobut.logger.Logger;

import java.io.File;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by sundongdong on 2017/2/24.
 */
public class RetrofitHelper {
    public static final String HTTP_TAG = "okhttp";
    private static final String TAG = "RetrofitHelper";
    public static String HOST_URL;
    public static ArrayList<String> cookies = new ArrayList<>();
    public static final String HTTP_HEAD = "http://%s%s";
    public BusinessApis businessApis = null;
    private static OkHttpClient okHttpClient = null;

    public RetrofitHelper() {
        init();
    }

    public static RetrofitHelper getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public static ArrayList<String> getCookie() {
        return cookies;
    }

    private static void initOkHttp() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();

        File cacheFile = new File(Constants.PATH_CACHE);
        Cache cache = new Cache(cacheFile, 1024 * 1024 * 50);
        CacheInterceptor cacheInterceptor = new CacheInterceptor();
        //设置网络缓存
        builder.addNetworkInterceptor(cacheInterceptor);
        builder.addNetworkInterceptor(new ProgressInterceptor());
        builder.addInterceptor(new AddTokenInterceptor());
        builder.addInterceptor(new ReceivedAuthorizationInterceptor());
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                Log.i(HTTP_TAG, message);
            }

            @Override
            public void logBody(String bodyMessage) {
                if (BuildConfig.DEBUG) {
                    LogUtil.jsonD(bodyMessage);
                } else {
//                    Alog.json(HTTP_TAG, bodyMessage);
                }
            }
        });
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        builder.addInterceptor(loggingInterceptor);
        builder.cache(cache);
        //设置超时
        builder.connectTimeout(10, TimeUnit.SECONDS);
        builder.readTimeout(20, TimeUnit.SECONDS);
        builder.writeTimeout(20, TimeUnit.SECONDS);
        //错误重连
        builder.retryOnConnectionFailure(true);
        //设置https
        try {
            builder.sslSocketFactory(getSSLSocketFactory()).hostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        okHttpClient = builder.build();
    }

    public static SSLSocketFactory getSSLSocketFactory() throws Exception {
        //创建一个不验证证书链的证书信任管理器。
        final TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
            @Override
            public void checkClientTrusted(
                    java.security.cert.X509Certificate[] chain,
                    String authType) throws CertificateException {
            }

            @Override
            public void checkServerTrusted(
                    java.security.cert.X509Certificate[] chain,
                    String authType) throws CertificateException {
            }

            @Override
            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                return new java.security.cert.X509Certificate[0];
            }
        }};

        // Install the all-trusting trust manager
        final SSLContext sslContext = SSLContext.getInstance("SSL");
        sslContext.init(null, trustAllCerts,
                new java.security.SecureRandom());
        // Create an ssl socket factory with our all-trusting manager
        return sslContext
                .getSocketFactory();
    }

    public static String getHostUrl() {
        if (HOST_URL == null) {
            HOST_URL = PrefUtils.getPrefString(App.getInstance(),
                    Constants.PREFERENCE_KEY_SERVER_ADDRESS, Constants.HOST_DEFAULT);
        }
        return String.format(HTTP_HEAD, HOST_URL, "/");
    }

    public void setHostUrl(String url) {
        HOST_URL = url;
        if (okHttpClient == null) {
            initOkHttp();
        }
        businessApis = new Retrofit.Builder()
                .baseUrl(getHostUrl())
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build().create(BusinessApis.class);
    }

    public static OkHttpClient getOkHttpClient() {
        if (okHttpClient == null) {
            initOkHttp();
        }
        return okHttpClient;
    }

    private void init() {
        Logger.init(HTTP_TAG).hideThreadInfo();
        initOkHttp();
        businessApis = getApiService(getHostUrl(), BusinessApis.class);
    }

    private <T> T getApiService(String baseUrl, Class<T> clz) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        return retrofit.create(clz);
    }

    private static class SingletonHolder {
        private static final RetrofitHelper INSTANCE = new RetrofitHelper();
    }


//    ///上传文件
//    public Observable<ResultSet<FileBean>> upLoadImage(File file) {
//        //构建requestbody
//        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
//        //将resquestbody封装为MultipartBody.Part对象
//        MultipartBody.Part body = MultipartBody.Part.createFormData("img", file.getName(), requestFile);
//        return businessApis.upLoadImage(App.getInstance().loginBean.token, Constants.APP_ACCESS, body);
//    }

//    public Observable<LoginBean> login(String userName, String password) {
//        return businessApis.login(userName, password, Constants.APP_ACCESS).compose(RxUtil.<LoginBean>handleResult()).compose(RxUtil.<LoginBean>rxSchedulerHelper());
//    }
//
//    public Observable<String> register(String userName, String password, String yzm) {
//        return businessApis.register(userName, password, yzm, Constants.APP_ACCESS).compose(RxUtil.<String>handleResult()).compose(RxUtil.<String>rxSchedulerHelper());
//    }
//
//    public Observable<String> getYZM(String phone) {
//        return businessApis.getYZM(phone, Constants.APP_ACCESS).compose(RxUtil.<String>handleResult()).compose(RxUtil.<String>rxSchedulerHelper());
//    }
//
//    public Observable<String> editPassword(String userName, String password, String yzm) {
//        return businessApis.editPassword(userName, password, yzm, Constants.APP_ACCESS).compose(RxUtil.<String>handleResult()).compose(RxUtil.<String>rxSchedulerHelper());
//    }
//
//    public Observable<UserInfoMode> getUserInfo(String token, int type, int cate) {
//        return businessApis.getUserInfo(token, type, cate, Constants.APP_ACCESS).compose(RxUtil.<UserInfoMode>handleResult()).compose(RxUtil.<UserInfoMode>rxSchedulerHelper());
//    }
//
//    public Observable<UserInfoMode> getNowUserInfoOne() {
//        return businessApis.getNowUserInfoOne(true, Constants.APP_ACCESS).compose(RxUtil.<UserInfoMode>handleResult()).compose(RxUtil.<UserInfoMode>rxSchedulerHelper());
//    }
//
//    public Observable<ArrayList<MyOrderModel>> getOrderList(String token) {
//        return businessApis.getOrderList(token).compose(RxUtil.<ArrayList<MyOrderModel>>handleResult()).compose(RxUtil.<ArrayList<MyOrderModel>>rxSchedulerHelper());
//    }
//
//    public Observable<List<LeftDataInfo>> getLeftData(String token) {
//        return businessApis.getOrderCategoryList(Constants.APP_ACCESS, token).compose(RxUtil.<List<LeftDataInfo>>handleResult()).compose(RxUtil.<List<LeftDataInfo>>rxSchedulerHelper());
//    }
//
//    public Observable<List<RightDataInfo>> getRightData(String token, int cateId) {
//        return businessApis.getRightDataList(Constants.APP_ACCESS, token, cateId).compose(RxUtil.<List<RightDataInfo>>handleResult()).compose(RxUtil.<List<RightDataInfo>>rxSchedulerHelper());
//    }
//
//    public Observable<ArrayList<MyOrderModel>> getjobCompanyList(String token, int type, int cate) {
//        return businessApis.getjobCompanyList(token, type, cate, Constants.APP_ACCESS).compose(RxUtil.<ArrayList<MyOrderModel>>handleResult()).compose(RxUtil.<ArrayList<MyOrderModel>>rxSchedulerHelper());
//    }
//
//    public Observable<ArrayList<MyOrderModel>> getNewsCompanyList(String token, int type, int cate) {
//        return businessApis.getNewsCompanyList(token, type, cate, Constants.APP_ACCESS).compose(RxUtil.<ArrayList<MyOrderModel>>handleResult()).compose(RxUtil.<ArrayList<MyOrderModel>>rxSchedulerHelper());
//    }
//
//    public Observable<ArrayList<MyPublishOrderModel>> getPublishOrderList(int page) {
//        return businessApis.getPublishOrderList(page, App.getInstance().loginBean.token, Constants.APP_ACCESS).compose(RxUtil.<ArrayList<MyPublishOrderModel>>handleResult()).compose(RxUtil.<ArrayList<MyPublishOrderModel>>rxSchedulerHelper());
//    }
//
//    public Observable<UserInfoMode> eidtInfoNick(String nickName) {
//        return businessApis.eidtInfoNick(nickName, App.getInstance().loginBean.type, App.getInstance().loginBean.cate, App.getInstance().loginBean.token, Constants.APP_ACCESS).compose(RxUtil.<UserInfoMode>handleResult()).compose(RxUtil.<UserInfoMode>rxSchedulerHelper());
//    }
//
//    public Observable<UserInfoMode> eidtInfoSex(int sex) {
//        return businessApis.eidtInfoSex(sex, App.getInstance().loginBean.type, App.getInstance().loginBean.cate, App.getInstance().loginBean.token, Constants.APP_ACCESS).compose(RxUtil.<UserInfoMode>handleResult()).compose(RxUtil.<UserInfoMode>rxSchedulerHelper());
//    }
//
//    public Observable<ArrayList<ConsultationModel>> getConsultationList() {
//        return businessApis.getConsultationList(Constants.APP_ACCESS).compose(RxUtil.<ArrayList<ConsultationModel>>handleResult()).compose(RxUtil.<ArrayList<ConsultationModel>>rxSchedulerHelper());
//    }
//
//    public Observable<ConsultationModelDetail> getConsultationDetail(String id) {
//        return businessApis.getConsultationDetail(id, Constants.APP_ACCESS).compose(RxUtil.<ConsultationModelDetail>handleResult()).compose(RxUtil.<ConsultationModelDetail>rxSchedulerHelper());
//    }
//
//    public Observable<PraiseModelDetail> praiseConsultation(String id) {
//        return businessApis.praiseConsultation(id, App.getInstance().loginBean.type, App.getInstance().loginBean.cate, App.getInstance().loginBean.token, Constants.APP_ACCESS).compose(RxUtil.<PraiseModelDetail>handleResult()).compose(RxUtil.<PraiseModelDetail>rxSchedulerHelper());
//    }
//
//    public Observable<MessageCountModel> getMessageCount() {
//        return businessApis.getMessageCount(App.getInstance().loginBean.token, Constants.APP_ACCESS).compose(RxUtil.<MessageCountModel>handleResult()).compose(RxUtil.<MessageCountModel>rxSchedulerHelper());
//    }
//
//    public Observable<OrderDetailInfo> getOrderDetailInfo(String token, int id) {
//        return businessApis.getOrderDetailInfo(Constants.APP_ACCESS, token, "json", id).compose(RxUtil.<OrderDetailInfo>handleResult()).compose(RxUtil.<OrderDetailInfo>rxSchedulerHelper());
//    }
//
//    public Observable<ArrayList<MyYpModel>> getYpDataList() {
//        return businessApis.getYpDataList(App.getInstance().loginBean.token, Constants.APP_ACCESS).compose(RxUtil.<ArrayList<MyYpModel>>handleResult()).compose(RxUtil.<ArrayList<MyYpModel>>rxSchedulerHelper());
//    }
//
//    public Observable<MyYpShowDetailModel> getShowGwList(String id) {
//        return businessApis.getShowGwList(id, App.getInstance().loginBean.token, Constants.APP_ACCESS).compose(RxUtil.<MyYpShowDetailModel>handleResult()).compose(RxUtil.<MyYpShowDetailModel>rxSchedulerHelper());
//    }
//
//    public Observable<MyGwDetailModel> getGwDetail(String job) {
//        return businessApis.getGwDetail(job, App.getInstance().loginBean.token, Constants.APP_ACCESS).compose(RxUtil.<MyGwDetailModel>handleResult()).compose(RxUtil.<MyGwDetailModel>rxSchedulerHelper());
//    }
//
//    public Observable<JianLiModel> getJianLiDetail() {
//        return businessApis.getJianLiDetail(App.getInstance().loginBean.token, Constants.APP_ACCESS).compose(RxUtil.<JianLiModel>handleResult()).compose(RxUtil.<JianLiModel>rxSchedulerHelper());
//    }
//
//    public Observable<String> resumeSearch(int resume) {
//        return businessApis.resumeSearch(resume, App.getInstance().loginBean.token, Constants.APP_ACCESS).compose(RxUtil.<String>handleResult()).compose(RxUtil.<String>rxSchedulerHelper());
//    }
//
//    public Observable<ArrayList<JobListModel>> getJoblist() {
//        return businessApis.getJoblist(App.getInstance().loginBean.type, App.getInstance().loginBean.cate, App.getInstance().loginBean.token, Constants.APP_ACCESS).compose(RxUtil.<ArrayList<JobListModel>>handleResult()).compose(RxUtil.<ArrayList<JobListModel>>rxSchedulerHelper());
//    }
//
//    public Observable<ArrayList<PublishGwModel>> getpublisGwList(int page) {
//        return businessApis.getpublisGwList(page, App.getInstance().loginBean.token, Constants.APP_ACCESS).compose(RxUtil.<ArrayList<PublishGwModel>>handleResult()).compose(RxUtil.<ArrayList<PublishGwModel>>rxSchedulerHelper());
//    }
//
//    public Observable<YgrzListModel> getYgrzImglist() {
//        return businessApis.getYgrzImglist(App.getInstance().loginBean.type, App.getInstance().loginBean.cate, App.getInstance().loginBean.token, Constants.APP_ACCESS).compose(RxUtil.<YgrzListModel>handleResult()).compose(RxUtil.<YgrzListModel>rxSchedulerHelper());
//    }
//
//    public Observable<CompanyOrderDetail> getCompanyOrderDetailInfo(String token, int id) {
//        return businessApis.getCompanyOrderDetailInfo(Constants.APP_ACCESS, token, id).compose(RxUtil.<CompanyOrderDetail>handleResult()).compose(RxUtil.<CompanyOrderDetail>rxSchedulerHelper());
//    }
//
//    public Observable<HomeInfoBean> getHomeInfo() {
//        return businessApis.getHomeInfo(Constants.APP_ACCESS).compose(RxUtil.<HomeInfoBean>handleResult()).compose(RxUtil.<HomeInfoBean>rxSchedulerHelper());
//    }
//
//    public Observable<BannerInfoBean> getHomeBanner() {
//        return businessApis.getBannerInfo(Constants.APP_ACCESS, "1").compose(RxUtil.<BannerInfoBean>handleResult()).compose(RxUtil.<BannerInfoBean>rxSchedulerHelper());
//    }
//
//    public Observable<JobDetailInfoBean> getJobDetailInfo(int job) {
//        return businessApis.getJobDetail(App.getInstance().loginBean.token, Constants.APP_ACCESS, job).compose(RxUtil.<JobDetailInfoBean>handleResult()).compose(RxUtil.<JobDetailInfoBean>rxSchedulerHelper());
//    }
//
//    public Observable<List<Job>> getPostList(int page) {
//        return businessApis.getPostList(App.getInstance().loginBean.token, Constants.APP_ACCESS, page).compose(RxUtil.<List<Job>>handleResult()).compose(RxUtil.<List<Job>>rxSchedulerHelper());
//    }
//
//
//    public Observable<String> getMoneyCount() {
//        return businessApis.getMoneyCount(App.getInstance().loginBean.token, Constants.APP_ACCESS).compose(RxUtil.<String>handleResult()).compose(RxUtil.<String>rxSchedulerHelper());
//    }
//
//    public Observable<String> applayGwforYg(int genre, int job, int cid) {
//        return businessApis.applayGwforYg(genre, job, cid, App.getInstance().loginBean.token, Constants.APP_ACCESS).compose(RxUtil.<String>handleResult()).compose(RxUtil.<String>rxSchedulerHelper());
//    }
//
//    public Observable<FunctionSwitchModel> getNowUserInfo() {
//        return businessApis.getNowUserInfo(App.getInstance().loginBean.token, true).compose(RxUtil.<FunctionSwitchModel>handleResult()).compose(RxUtil.<FunctionSwitchModel>rxSchedulerHelper());
//    }
//
//    public Observable<String> upSwitchFun(String name, boolean value) {
//        return businessApis.upSwitchFun(name, value, Constants.APP_ACCESS).compose(RxUtil.<String>handleResult()).compose(RxUtil.<String>rxSchedulerHelper());
//    }
//
//    public Observable<ExpandObjModel> getExpandDetail() {
//        return businessApis.getExpandDetail(2, 2, Constants.APP_ACCESS).compose(RxUtil.<ExpandObjModel>handleResult()).compose(RxUtil.<ExpandObjModel>rxSchedulerHelper());
//    }
//
//    public Observable<List<String>> submitExpandMsg(ExpandUploadObjModel model) {
//        ExpandUploadObjMapModel uploadObjMapModel = new ExpandUploadObjMapModel();
//        uploadObjMapModel.staffOptions = model;
////        Log.d(TAG, ""+ObjectUtil.objToHashMapValueString(model));
//
////        Gson gson = new Gson();
////        String json = gson.toJson(uploadObjMapModel);
////        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json);
//        return businessApis.submitExpandMsg(RequestBody.create(okhttp3.MediaType.parse("application/json;charset=UTF-8"), new Gson().toJson(uploadObjMapModel))).compose(RxUtil.<List<String>>handleResult()).compose(RxUtil.<List<String>>rxSchedulerHelper());
//    }
//
//    public Observable<List<String>> submitYpygMsg(YpBaseUploadObjModel model) {
//        YgYpUpdateObjMapModel uploadObjMapModel = new YgYpUpdateObjMapModel();
//        uploadObjMapModel.staffInfos = model;
//        uploadObjMapModel.token = App.getInstance().loginBean.token;
//        return businessApis.submitYpygMsg(RequestBody.create(okhttp3.MediaType.parse("application/json;charset=UTF-8"), new Gson().toJson(uploadObjMapModel))).compose(RxUtil.<List<String>>handleResult()).compose(RxUtil.<List<String>>rxSchedulerHelper());
//    }
//
//    public Observable<YpBaseInfoBaseModel> getYpInfoMsg() {
//        return businessApis.getYpInfoMsg(App.getInstance().loginBean.token, Constants.APP_ACCESS).compose(RxUtil.<YpBaseInfoBaseModel>handleResult()).compose(RxUtil.<YpBaseInfoBaseModel>rxSchedulerHelper());
//    }
//
//    ///上传文件
//    public Observable<ResultSet<FileBean>> upBasePic(File file) {
//        Map<String, String> map = new HashMap<>();
//        String imgBase64 = FileUtil.imageToBase64(file);
//        map.put("base64", "data:image/jpeg;base64," + imgBase64);
//        map.put("app_access", Constants.APP_ACCESS);
//        return businessApis.upBasePic(map);
//    }
//
//    //获取通用的左边分类列表数据
//    public Observable<List<LeftInfo>> getReuseLeftListData(int type) {
//        return businessApis.getReuseLeftListData(Constants.APP_ACCESS, type).compose(RxUtil.<List<LeftInfo>>handleResult()).compose(RxUtil.<List<LeftInfo>>rxSchedulerHelper());
//    }
//
//    //搜索公司，工厂，应聘
//    public Observable<List<SearchCompany>> getSearchFCPing(String token, String word, String type) {
//        return businessApis.getSearchFCPing(token, Constants.APP_ACCESS, word, type).compose(RxUtil.<List<SearchCompany>>handleResult()).compose(RxUtil.<List<SearchCompany>>rxSchedulerHelper());
//    }
//
//    //搜索货单
//    public Observable<List<SearchOrder>> getSearchOrder(String token, String word, String type) {
//        return businessApis.getSearchOrder(token, Constants.APP_ACCESS, word, type).compose(RxUtil.<List<SearchOrder>>handleResult()).compose(RxUtil.<List<SearchOrder>>rxSchedulerHelper());
//    }
//
//    //搜索岗位
//    public Observable<List<SearchJob>> getSearchJob(String token, String word, String type) {
//        return businessApis.getSearchJob(token, Constants.APP_ACCESS, word, type).compose(RxUtil.<List<SearchJob>>handleResult()).compose(RxUtil.<List<SearchJob>>rxSchedulerHelper());
//    }
//
//    //获取职位列表
//    public Observable<List<JobZwListModel>> getjobZwList() {
//        return businessApis.getjobList(Constants.APP_ACCESS).compose(RxUtil.<List<JobZwListModel>>handleResult()).compose(RxUtil.<List<JobZwListModel>>rxSchedulerHelper());
//    }
//
//
//    //获取工厂基本信息
//    public Observable<FactoryBaseInfoListModel> getGcBaseInfoMsg() {
//        return businessApis.getGcBaseInfoMsg(App.getInstance().loginBean.token, Constants.APP_ACCESS).compose(RxUtil.<FactoryBaseInfoListModel>handleResult()).compose(RxUtil.<FactoryBaseInfoListModel>rxSchedulerHelper());
//    }
//
//
//    //获取职位列表
//    public Observable<List<FactoryModel>> getFactoryTypeList() {
//        return businessApis.getFactoryTypeList(App.getInstance().loginBean.token, Constants.APP_ACCESS).compose(RxUtil.<List<FactoryModel>>handleResult()).compose(RxUtil.<List<FactoryModel>>rxSchedulerHelper());
//    }
//
//
//    //上传工厂基本信息
//    public Observable<List<String>> saveFactoryBaseInfo(ComInfoBaseUploadObjModel model) {
//        ComInfoUpdateObjMapModel uploadObjMapModel = new ComInfoUpdateObjMapModel();
//        uploadObjMapModel.comInfo = model;
//        uploadObjMapModel.token = App.getInstance().loginBean.token;
//        return businessApis.saveFactoryBaseInfo(RequestBody.create(okhttp3.MediaType.parse("application/json;charset=UTF-8"), new Gson().toJson(uploadObjMapModel))).compose(RxUtil.<List<String>>handleResult()).compose(RxUtil.<List<String>>rxSchedulerHelper());
//    }
//
//    //上传公司基本信息
//    public Observable<List<String>> saveGsBaseInfo(GsBaseInfoUpdateListModel model) {
//        GsInfoUpdateObjMapModel uploadObjMapModel = new GsInfoUpdateObjMapModel();
//        uploadObjMapModel.comInfo = model;
//        uploadObjMapModel.token = App.getInstance().loginBean.token;
//        return businessApis.saveGsBaseInfo(RequestBody.create(okhttp3.MediaType.parse("application/json;charset=UTF-8"), new Gson().toJson(uploadObjMapModel))).compose(RxUtil.<List<String>>handleResult()).compose(RxUtil.<List<String>>rxSchedulerHelper());
//    }
//
//    ///上传认证信息
//    public Observable<String> saveGcRzInfo(GcRzUpdateModel model, String type) {
//        return businessApis.saveGcRzInfo(new Gson().toJson(model), type, App.getInstance().loginBean.token, Constants.APP_ACCESS).compose(RxUtil.<String>handleResult()).compose(RxUtil.<String>rxSchedulerHelper());
//    }
//
//    ///获取类型文件信息
//    public Observable<FileListModel> getFilelist(String type) {
//        return businessApis.getFilelist(type, 2, App.getInstance().loginBean.token, Constants.APP_ACCESS).compose(RxUtil.<FileListModel>handleResult()).compose(RxUtil.<FileListModel>rxSchedulerHelper());
//    }
//
//    ///工厂产品信息
//    public Observable<MyGcProductListModel> getGcProductList() {
//        return businessApis.getGcProductList(2, 2, App.getInstance().loginBean.token, Constants.APP_ACCESS).compose(RxUtil.<MyGcProductListModel>handleResult()).compose(RxUtil.<MyGcProductListModel>rxSchedulerHelper());
//    }
//
//    ///工厂设备信息
//    public Observable<MyGcDeviceListModel> getGcDeviceList() {
//        return businessApis.getGcDeviceList(2, 2, App.getInstance().loginBean.token, Constants.APP_ACCESS).compose(RxUtil.<MyGcDeviceListModel>handleResult()).compose(RxUtil.<MyGcDeviceListModel>rxSchedulerHelper());
//    }
//
//    //上传工厂产品信息
//    public Observable<List<String>> savaGcProductMsg(MyGcProductUpdateModel model) {
//        model.token = App.getInstance().loginBean.token;
//        return businessApis.savaGcProductMsg(RequestBody.create(okhttp3.MediaType.parse("application/json;charset=UTF-8"), new Gson().toJson(model))).compose(RxUtil.<List<String>>handleResult()).compose(RxUtil.<List<String>>rxSchedulerHelper());
//    }
//
//    //上传工厂设备信息
//    public Observable<List<String>> savaGcDeviceMsg(MyGcDevicetUpdateModel model) {
//        model.token = App.getInstance().loginBean.token;
//        return businessApis.savaGcDeviceMsg(RequestBody.create(okhttp3.MediaType.parse("application/json;charset=UTF-8"), new Gson().toJson(model))).compose(RxUtil.<List<String>>handleResult()).compose(RxUtil.<List<String>>rxSchedulerHelper());
//    }
//
//    ///工厂产量产能
//    public Observable<MyGcYeildListModel> getGcYieldList() {
//        return businessApis.getGcYieldList(2, 2, App.getInstance().loginBean.token, Constants.APP_ACCESS).compose(RxUtil.<MyGcYeildListModel>handleResult()).compose(RxUtil.<MyGcYeildListModel>rxSchedulerHelper());
//    }
//
//
//    //上传工厂产量产能
//    public Observable<List<String>> savaGcYieldMsg(MyGcYeildUpdateModel model) {
//        model.token = App.getInstance().loginBean.token;
//        return businessApis.savaGcYieldMsg(RequestBody.create(okhttp3.MediaType.parse("application/json;charset=UTF-8"), new Gson().toJson(model))).compose(RxUtil.<List<String>>handleResult()).compose(RxUtil.<List<String>>rxSchedulerHelper());
//    }
//
//
//    ///工厂环境
//    public Observable<MyGcEnvModel> getGcEnvMsg(String type) {
//        return businessApis.getGcEnvMsg(type, App.getInstance().loginBean.token, Constants.APP_ACCESS).compose(RxUtil.<MyGcEnvModel>handleResult()).compose(RxUtil.<MyGcEnvModel>rxSchedulerHelper());
//    }
//
//    ///您工厂成本明细表
//    public Observable<MyGcCostDataListModel> getGcCostList(String type) {
//        return businessApis.getGcCostList(type, App.getInstance().loginBean.token, Constants.APP_ACCESS).compose(RxUtil.<MyGcCostDataListModel>handleResult()).compose(RxUtil.<MyGcCostDataListModel>rxSchedulerHelper());
//    }
//
//    ///上传环境信息
//    public Observable<String> updateGcEnvMsg(MyGcEnvModel model, String type) {
//        return businessApis.updateGcEnvMsg(new Gson().toJson(model), type, App.getInstance().loginBean.token, Constants.APP_ACCESS).compose(RxUtil.<String>handleResult()).compose(RxUtil.<String>rxSchedulerHelper());
//    }
//
//    //获取公司基本信息
//    public Observable<GsBaseInfoListModel> getGsBaseInfoMsg() {
//        return businessApis.getGsBaseInfoMsg(App.getInstance().loginBean.token, Constants.APP_ACCESS).compose(RxUtil.<GsBaseInfoListModel>handleResult()).compose(RxUtil.<GsBaseInfoListModel>rxSchedulerHelper());
//    }
//
//
//    ///工厂产量产能
//    public Observable<MyGcYeildListModel> getGsYieldList() {
//        return businessApis.getGcYieldList(2, 2, App.getInstance().loginBean.token, Constants.APP_ACCESS).compose(RxUtil.<MyGcYeildListModel>handleResult()).compose(RxUtil.<MyGcYeildListModel>rxSchedulerHelper());
//    }
//
//
//    //上传工厂产量产能
//    public Observable<List<String>> savaGsYieldMsg(MyGcYeildUpdateModel model) {
//        model.token = App.getInstance().loginBean.token;
//        return businessApis.savaGcYieldMsg(RequestBody.create(okhttp3.MediaType.parse("application/json;charset=UTF-8"), new Gson().toJson(model))).compose(RxUtil.<List<String>>handleResult()).compose(RxUtil.<List<String>>rxSchedulerHelper());
//    }
//
//    ///获取公司设计样式
//    public Observable<List<MyGsDesignModel>> getGsDesignList() {
//        return businessApis.getGsDesignList(App.getInstance().loginBean.token, Constants.APP_ACCESS).compose(RxUtil.<List<MyGsDesignModel>>handleResult()).compose(RxUtil.<List<MyGsDesignModel>>rxSchedulerHelper());
//    }
//
//    ///获取公司产品列表
//    public Observable<MyGcProductListModel> getGsProductList() {
//        return businessApis.getGsProductList(2, 2, App.getInstance().loginBean.token, Constants.APP_ACCESS).compose(RxUtil.<MyGcProductListModel>handleResult()).compose(RxUtil.<MyGcProductListModel>rxSchedulerHelper());
//    }
//
//
//    //上传公司产品信息
//    public Observable<List<String>> savaGsProductMsg(MyGcProductUpdateModel model) {
//        model.token = App.getInstance().loginBean.token;
//        return businessApis.savaGsProductMsg(RequestBody.create(okhttp3.MediaType.parse("application/json;charset=UTF-8"), new Gson().toJson(model))).compose(RxUtil.<List<String>>handleResult()).compose(RxUtil.<List<String>>rxSchedulerHelper());
//    }
//
//    ///保存公司设计样式
//    public Observable<List<String>> saveGsDesignList(String design) {
//        return businessApis.saveGsDesignList(design, App.getInstance().loginBean.token, Constants.APP_ACCESS).compose(RxUtil.<List<String>>handleResult()).compose(RxUtil.<List<String>>rxSchedulerHelper());
//    }
//
//    ///获取公司产品样式
//    public Observable<MyGsProductStyleListModel> getGsProductStyleList() {
//        return businessApis.getGsProductStyleList(2, 2, App.getInstance().loginBean.token, Constants.APP_ACCESS).compose(RxUtil.<MyGsProductStyleListModel>handleResult()).compose(RxUtil.<MyGsProductStyleListModel>rxSchedulerHelper());
//    }
//
//    ///保存公司产品样式
//    public Observable<List<String>> saveGsProductStyleList(String styles) {
//        return businessApis.saveGsProductStyleList(styles, 2, 2, App.getInstance().loginBean.token, Constants.APP_ACCESS).compose(RxUtil.<List<String>>handleResult()).compose(RxUtil.<List<String>>rxSchedulerHelper());
//    }
//
//    ///获取公司产品风格
//    public Observable<List<MyGcProjuctTypeModel>> getGsProductTypeList() {
//        return businessApis.getGsProductTypeList(App.getInstance().loginBean.token, Constants.APP_ACCESS).compose(RxUtil.<List<MyGcProjuctTypeModel>>handleResult()).compose(RxUtil.<List<MyGcProjuctTypeModel>>rxSchedulerHelper());
//    }
//
//    ///保存公司产品风格
//    public Observable<List<String>> saveGsProductTypeList(String pro_types) {
//        return businessApis.saveGsProductTypeList(pro_types, App.getInstance().loginBean.token, Constants.APP_ACCESS).compose(RxUtil.<List<String>>handleResult()).compose(RxUtil.<List<String>>rxSchedulerHelper());
//    }
//
//    ///发布咨询
//    public Observable<BaseMode> publishNews(String title, String thumb, String content) {
//        return businessApis.publishNews(title, thumb, content, App.getInstance().loginBean.type, App.getInstance().loginBean.token, Constants.APP_ACCESS).compose(RxUtil.<BaseMode>handleResult()).compose(RxUtil.<BaseMode>rxSchedulerHelper());
//    }
}
