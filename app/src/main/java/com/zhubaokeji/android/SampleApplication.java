package com.zhubaokeji.android;

import com.tencent.tinker.loader.app.TinkerApplication;
import com.tencent.tinker.loader.shareutil.ShareConstants;

/**
 * Created by fisho on 2017/7/25.
 */

public class SampleApplication extends TinkerApplication {
    public SampleApplication() {
        super(ShareConstants.TINKER_ENABLE_ALL, "com.zhubaokeji.android.SampleApplicationLike",
                "com.tencent.tinker.loader.TinkerLoader", false);
    }

}
