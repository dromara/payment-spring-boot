package cn.felord.payment.wechat;

import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.DefaultResponseErrorHandler;

import java.io.IOException;

/**
 *
 * @author felord.cn
 * @since 1.0.0.RELEASE
 */
public class WechatPayResponseErrorHandler extends DefaultResponseErrorHandler {

    @Override
    public boolean hasError(ClientHttpResponse response) throws IOException {
       return false;
    }
}
