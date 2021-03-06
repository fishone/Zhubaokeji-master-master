package com.zhubaokeji.android.view;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.blankj.utilcode.util.ConvertUtils;
import com.zhubaokeji.android.R;
import com.zhubaokeji.android.bean.DataHolder;
import com.zhubaokeji.android.biz.presenter.GlideFactory;
import com.zhubaokeji.android.biz.presenter.ImageLoaderWrapper;
import com.zhubaokeji.android.base.BaseActivity;
import com.zhubaokeji.android.biz.presenter.ImageWrapper;
import com.zhubaokeji.android.utils.NetUtil;
import com.zhubaokeji.android.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;
import static com.zhubaokeji.android.fragment.JpHomeFragment.jp_Login_Boolean;
import static com.zhubaokeji.android.fragment.ZhubaoHomeFragment.zhubao_Login_boolean;

/**
 * 图片预览界面
 *
 * @author Clock
 * @since 2016-01-25
 */
public class ImagePreviewActivity extends BaseActivity implements View.OnClickListener{

    private final static String TAG = ImagePreviewActivity.class.getSimpleName();

    public final static String EXTRA_IMAGE_MAPID = "IMAGEID";
    public final static String EXTRA_IMAGE_FLAG = "ImageFlag";

    public final static String EXTRA_NEW_IMAGE_LIST = "NewImageList";
    private int imageIndex;
    private ViewPager mPreviewViewPager;
    private PagerAdapter mPreviewPagerAdapter;
    private ViewPager.OnPageChangeListener mPreviewChangeListener;
    private TextView mTitleView;
    //    private CheckBox mImageSelectedBox;
    private View mHeaderView, mFooterView;

    /**
     * 所有图片的列表
     */
    private List<Bitmap> mPreviewImageInfoList =new ArrayList<>();
    /**
     * 刚进入页面显示的图片
     */
    private Bitmap mPreviewImageInfo;

    private ImageLoaderWrapper mImageLoaderWrapper;

    private ImageWrapper imageWrapper;

    private Activity mContext;

    ArrayList<DataHolder> byteList=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_preview);
        mContext=this;

        if (Build.VERSION.SDK_INT >= 11) {
            getWindow().getDecorView().setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener() {
                @Override
                public void onSystemUiVisibilityChange(int visibility) {
                    if (View.SYSTEM_UI_FLAG_VISIBLE == visibility) {//此处需要添加顶部和底部消失和出现的动画效果
                        Log.i(TAG, "SYSTEM_UI_FLAG_VISIBLE");
                        mHeaderView.startAnimation(AnimationUtils.loadAnimation(ImagePreviewActivity.this, R.anim.top_enter_anim));
                        mFooterView.startAnimation(AnimationUtils.loadAnimation(ImagePreviewActivity.this, R.anim.bottom_enter_anim));

                    } else {
                        Log.i(TAG, "SYSTEM_UI_FLAG_INVISIBLE");
                        mHeaderView.startAnimation(AnimationUtils.loadAnimation(ImagePreviewActivity.this, R.anim.top_exit_anim));
                        mFooterView.startAnimation(AnimationUtils.loadAnimation(ImagePreviewActivity.this, R.anim.bottom_exit_anim));

                    }
                }
            });
        }

        imageWrapper= GlideFactory.getLoader();
        mPreviewImageInfoList=new ArrayList<>();
        imageIndex = getIntent().getIntExtra(EXTRA_IMAGE_FLAG,0);
        String ImageId= getIntent().getExtras().getString(EXTRA_IMAGE_MAPID);
        mPreviewImageInfoList= (List<Bitmap>) DataHolder.retrieve(ImageId);
        mPreviewImageInfo=mPreviewImageInfoList.get(imageIndex);
        initImageLoader(this);
        initView();

    }

    @Override
    protected void onNetworkConnected(NetUtil.NetType type) {
        if(type== NetUtil.NetType.NONE){
            jp_Login_Boolean = false;
            zhubao_Login_boolean=false;
            ToastUtil.show(mContext,"网络未连接,请连接网络");
        }
    }

    private void initView() {
        mTitleView = (TextView) findViewById(R.id.tv_title);
        if (mPreviewImageInfo != null && mPreviewImageInfoList != null) {
            if (mPreviewImageInfoList.contains(mPreviewImageInfo)) {
                int imageIndex = mPreviewImageInfoList.indexOf(mPreviewImageInfo);
                setPositionToTitle(imageIndex);

            }
        }
        mPreviewViewPager = (ViewPager) findViewById(R.id.gallery_viewpager);
        mPreviewPagerAdapter = new PreviewPagerAdapter();
        mPreviewViewPager.setAdapter(mPreviewPagerAdapter);
        if (mPreviewImageInfo != null && mPreviewImageInfoList != null && mPreviewImageInfoList.contains(mPreviewImageInfo)) {
            int initShowPosition = mPreviewImageInfoList.indexOf(mPreviewImageInfo);
            mPreviewViewPager.setCurrentItem(initShowPosition);
        }
        mPreviewChangeListener = new PreviewChangeListener();
        mPreviewViewPager.addOnPageChangeListener(mPreviewChangeListener);

        findViewById(R.id.iv_back).setOnClickListener(this);

        mHeaderView = findViewById(R.id.header_view);
        mFooterView = findViewById(R.id.footer_view);
    }

    @Override
    public void onClick(View v) {
        int viewId = v.getId();
        if (viewId == R.id.iv_back) {
           finish();
        }
    }

    /**
     * 监听PhotoView的点击事件
     */
    private PhotoViewAttacher.OnViewTapListener mOnPreviewTapListener = new PhotoViewAttacher.OnViewTapListener() {
        @Override
        public void onViewTap(View view, float v, float v1) {
            toggleImmersiveMode();
        }
    };



    /**
     * 相册适配器
     */
    private class PreviewPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            if (mPreviewImageInfoList == null) {
                return 0;
            }
            return mPreviewImageInfoList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            PhotoView galleryPhotoView = (PhotoView) view.findViewById(R.id.iv_show_image);
            galleryPhotoView.setScale(1.0f);//让图片在滑动过程中恢复回缩放操作前原图大小
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View galleryItemView = View.inflate(ImagePreviewActivity.this, R.layout.preview_image_item, null);

            Bitmap imageInfo = mPreviewImageInfoList.get(position);
            PhotoView galleryPhotoView = (PhotoView) galleryItemView.findViewById(R.id.iv_show_image);
            galleryPhotoView.setOnViewTapListener(mOnPreviewTapListener);
            imageWrapper.displayImage(galleryPhotoView,imageInfo);
            container.addView(galleryItemView);
            return galleryItemView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }

    /**
     * 相册详情页面滑动监听
     */
    private class PreviewChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
//            mImageSelectedBox.setOnCheckedChangeListener(null);//先反注册监听，避免重复更新选中的状态
//
            setPositionToTitle(position);
//            ImageInfo imageInfo = mPreviewImageInfoList.get(position);
//            mImageSelectedBox.setChecked(imageInfo.isSelected());
//
//            mImageSelectedBox.setOnCheckedChangeListener(ImagePreviewActivity.this);
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }

    /**
     * 设置标题现实当前所处的位置
     *
     * @param position
     */
    private void setPositionToTitle(int position) {
        if (mPreviewImageInfoList != null) {
            String title = String.format(getString(R.string.image_index), position + 1, mPreviewImageInfoList.size());
            mTitleView.setText(title);
        }
    }

    /**
     * 切换沉浸栏模式（Immersive - Mode）
     */
    private void toggleImmersiveMode() {
        if (Build.VERSION.SDK_INT >= 11) {
            int uiOptions = getWindow().getDecorView().getSystemUiVisibility();
            // Navigation bar hiding:  Backwards compatible to ICS.
            if (Build.VERSION.SDK_INT >= 14) {
                uiOptions ^= View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
            }
            // Status bar hiding: Backwards compatible to Jellybean
            if (Build.VERSION.SDK_INT >= 16) {
                uiOptions ^= View.SYSTEM_UI_FLAG_FULLSCREEN;
            }
            // Immersive mode: Backward compatible to KitKat.
            if (Build.VERSION.SDK_INT >= 18) {
                uiOptions ^= View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
            }
            getWindow().getDecorView().setSystemUiVisibility(uiOptions);
        }
    }
}
