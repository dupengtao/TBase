package com.dpt.tbase.app.base.exception;

/**
 * 网络没有连接异常
 * 
 * @author dupengtao@cyou-inc.com
 *
 * 2014-3-18
 */
public class NetNotConnException extends RuntimeException {

    /**
     * 
     */
    private static final long serialVersionUID = 8009503764574710796L;

    public NetNotConnException() {
        super();
    }

    public NetNotConnException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }

    public NetNotConnException(String detailMessage) {
        super(detailMessage);
    }

    public NetNotConnException(Throwable throwable) {
        super(throwable);
    }

    
    
}
