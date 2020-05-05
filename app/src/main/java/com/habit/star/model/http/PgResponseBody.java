package com.habit.star.model.http;


import com.habit.star.model.bean.FileProgressEvent;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import okio.ForwardingSource;
import okio.Okio;
import okio.Source;

/**
 * Created by sundongdong on 2017/3/22.
 */

public class PgResponseBody extends ResponseBody {
    public static boolean download = false;
    private final String TAG = "PgResponseBody";
    ResponseBody originalResponseBody;
    FileProgressEvent fileProgressEvent;
    BufferedSource bufferedSource;

    long contentLength = -1;

    public PgResponseBody(ResponseBody responseBody) {
        this.originalResponseBody = responseBody;
        this.fileProgressEvent = new FileProgressEvent();
    }

    public static void setDownload(boolean isDownload) {
        download = isDownload;
    }

    @Override
    public MediaType contentType() {
        return originalResponseBody.contentType();
    }

    @Override
    public long contentLength() {
        contentLength = originalResponseBody.contentLength();
        return contentLength;
    }

    @Override
    public BufferedSource source() {

        if (bufferedSource == null) {
            bufferedSource = Okio.buffer(source(originalResponseBody.source()));
        }

        return bufferedSource;

    }

    private Source source(Source source) {
        return new ForwardingSource(source) {
            long bytesReaded = 0;
            long bytesRead = 0;

            @Override
            public long read(Buffer sink, long byteCount) throws IOException {
                if (!download) {
                    return super.read(sink, byteCount);
                } else {
                    bytesRead = super.read(sink, byteCount);
                    bytesReaded += bytesRead == -1 ? 0 : bytesRead;
//                    Alog.i(TAG, "current:" + bytesReaded + " total:" + contentLength());
                    fileProgressEvent.current = bytesReaded;
                    fileProgressEvent.total = contentLength();
//                    OkBus.getInstance().onEvent(EventTags.ON_DOWNLOAD_FILE_PROGRESS, fileProgressEvent);
//                    RxBus.getDefault().post(fileProgressEvent);
                    return bytesRead;
                }

            }
        };

    }


}
