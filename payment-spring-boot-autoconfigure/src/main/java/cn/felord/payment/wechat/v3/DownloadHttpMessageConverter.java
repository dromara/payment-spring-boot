package cn.felord.payment.wechat.v3;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.*;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;
import org.springframework.util.MimeType;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

/**
 * @author Dax
 * @since 12:46
 */
public class DownloadHttpMessageConverter extends AbstractHttpMessageConverter<ObjectNode> {

    public DownloadHttpMessageConverter() {
    }

    public DownloadHttpMessageConverter(MediaType supportedMediaType) {
        super(supportedMediaType);
    }

    @Override
    protected boolean supports(Class<?> clazz) {
        return clazz.isAssignableFrom(ObjectNode.class);
    }

    @Override
    protected ObjectNode readInternal(Class<? extends ObjectNode> clazz, HttpInputMessage inputMessage) throws IOException, HttpMessageNotReadableException {
        Charset charset = getContentTypeCharset(inputMessage.getHeaders().getContentType());
        String s = StreamUtils.copyToString(inputMessage.getBody(), charset);
        return new ObjectNode(JsonNodeFactory.withExactBigDecimals(true)).put("result", s);
    }

    @Override
    protected void writeInternal(ObjectNode jsonNodes, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {

    }

    private Charset getContentTypeCharset(@Nullable MediaType contentType) {
        if (contentType != null && contentType.getCharset() != null) {
            return contentType.getCharset();
        }
        else if (contentType != null && contentType.isCompatibleWith(MediaType.APPLICATION_JSON)) {
            // Matching to AbstractJackson2HttpMessageConverter#DEFAULT_CHARSET
            return StandardCharsets.UTF_8;
        }
        else {
            Charset charset = getDefaultCharset();
            Assert.state(charset != null, "No default charset");
            return charset;
        }
    }
}
