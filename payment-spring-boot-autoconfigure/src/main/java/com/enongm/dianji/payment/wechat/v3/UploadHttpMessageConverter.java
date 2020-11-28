package com.enongm.dianji.payment.wechat.v3;

import org.springframework.http.*;
import org.springframework.http.converter.*;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * The type Upload http message converter.
 */
final class UploadHttpMessageConverter extends FormHttpMessageConverter {
    private static final String BOUNDARY = "boundary";
    private final List<HttpMessageConverter<?>> partConverters = new ArrayList<>();

    /**
     * Instantiates a new Upload http message converter.
     */
    public UploadHttpMessageConverter() {
        StringHttpMessageConverter stringHttpMessageConverter = new StringHttpMessageConverter();
        stringHttpMessageConverter.setWriteAcceptCharset(false);  // see SPR-7316

        this.partConverters.add(new ByteArrayHttpMessageConverter());
        this.partConverters.add(stringHttpMessageConverter);
        this.partConverters.add(new ResourceHttpMessageConverter());

        applyDefaultCharset();
    }

    /**
     * Apply the configured charset as a default to registered part converters.
     */
    private void applyDefaultCharset() {
        for (HttpMessageConverter<?> candidate : this.partConverters) {
            if (candidate instanceof AbstractHttpMessageConverter) {
                AbstractHttpMessageConverter<?> converter = (AbstractHttpMessageConverter<?>) candidate;
                // Only override default charset if the converter operates with a charset to begin with...
                if (converter.getDefaultCharset() != null) {
                    converter.setDefaultCharset(DEFAULT_CHARSET);
                }
            }
        }
    }
    @Override
    @SuppressWarnings("unchecked")
    public void write(MultiValueMap<String, ?> map, @Nullable MediaType contentType, HttpOutputMessage outputMessage)
            throws IOException, HttpMessageNotWritableException {

        if (!isMultipart(map, contentType)) {
            writeForm((MultiValueMap<String, Object>) map, contentType, outputMessage);
        } else {
            writeMultipart((MultiValueMap<String, Object>) map, outputMessage);
        }
    }

    private boolean isMultipart(MultiValueMap<String, ?> map, @Nullable MediaType contentType) {
        if (contentType != null) {
            return MediaType.MULTIPART_FORM_DATA.includes(contentType);
        }
        for (List<?> values : map.values()) {
            for (Object value : values) {
                if (value != null && !(value instanceof String)) {
                    return true;
                }
            }
        }
        return false;
    }

    private void writeForm(MultiValueMap<String, Object> formData, @Nullable MediaType contentType,
                           HttpOutputMessage outputMessage) throws IOException {

        contentType = getMediaType(contentType);
        outputMessage.getHeaders().setContentType(contentType);

        Charset charset = contentType.getCharset();
        Assert.notNull(charset, "No charset"); // should never occur

        final byte[] bytes = serializeForm(formData, charset).getBytes(charset);
        outputMessage.getHeaders().setContentLength(bytes.length);

        if (outputMessage instanceof StreamingHttpOutputMessage) {
            StreamingHttpOutputMessage streamingOutputMessage = (StreamingHttpOutputMessage) outputMessage;
            streamingOutputMessage.setBody(outputStream -> StreamUtils.copy(bytes, outputStream));
        } else {
            StreamUtils.copy(bytes, outputMessage.getBody());
        }
    }

    private MediaType getMediaType(@Nullable MediaType mediaType) {
        if (mediaType == null) {
            return new MediaType(MediaType.APPLICATION_FORM_URLENCODED, DEFAULT_CHARSET);
        } else if (mediaType.getCharset() == null) {
            return new MediaType(mediaType, DEFAULT_CHARSET);
        } else {
            return mediaType;
        }
    }

    private void writeMultipart(final MultiValueMap<String, Object> parts, HttpOutputMessage outputMessage)
            throws IOException {


        Map<String, String> parameters = new LinkedHashMap<>(1);

        parameters.put(BOUNDARY, BOUNDARY);

        MediaType contentType = new MediaType(MediaType.MULTIPART_FORM_DATA, parameters);
        HttpHeaders headers = outputMessage.getHeaders();
        headers.setContentType(contentType);

        byte[] boundaryBytes = BOUNDARY.getBytes();
        if (outputMessage instanceof StreamingHttpOutputMessage) {
            StreamingHttpOutputMessage streamingOutputMessage = (StreamingHttpOutputMessage) outputMessage;
            streamingOutputMessage.setBody(outputStream -> {
                writeParts(outputStream, parts, boundaryBytes);
                writeEnd(outputStream, boundaryBytes);
            });
        } else {
            writeParts(outputMessage.getBody(), parts, boundaryBytes);
            writeEnd(outputMessage.getBody(), boundaryBytes);
        }
    }

    private void writeParts(OutputStream os, MultiValueMap<String, Object> parts, byte[] boundary) throws IOException {
        for (Map.Entry<String, List<Object>> entry : parts.entrySet()) {
            String name = entry.getKey();
            for (Object part : entry.getValue()) {
                if (part != null) {
                    writeBoundary(os, boundary);
                    writePart(name, getHttpEntity(part), os);
                    writeNewLine(os);
                }
            }
        }
    }

    @SuppressWarnings("unchecked")
    private void writePart(String name, HttpEntity<?> partEntity, OutputStream os) throws IOException {
        Object partBody = partEntity.getBody();
        if (partBody == null) {
            throw new IllegalStateException("Empty body for part '" + name + "': " + partEntity);
        }
        Class<?> partType = partBody.getClass();
        HttpHeaders partHeaders = partEntity.getHeaders();
        MediaType partContentType = partHeaders.getContentType();
        for (HttpMessageConverter<?> messageConverter : this.partConverters) {
            if (messageConverter.canWrite(partType, partContentType)) {

                HttpOutputMessage multipartMessage = new MultipartHttpOutputMessage(os, DEFAULT_CHARSET);
                multipartMessage.getHeaders().setContentDispositionFormData(name, getFilename(partBody));
                if (!partHeaders.isEmpty()) {
                    multipartMessage.getHeaders().putAll(partHeaders);
                }
                ((HttpMessageConverter<Object>) messageConverter).write(partBody, partContentType, multipartMessage);
                return;
            }
        }
        throw new HttpMessageNotWritableException("Could not write request: no suitable HttpMessageConverter " +
                "found for request type [" + partType.getName() + "]");
    }


    private void writeBoundary(OutputStream os, byte[] boundary) throws IOException {
        os.write('-');
        os.write('-');
        os.write(boundary);
        writeNewLine(os);
    }

    private static void writeEnd(OutputStream os, byte[] boundary) throws IOException {
        os.write('-');
        os.write('-');
        os.write(boundary);
        os.write('-');
        os.write('-');
        writeNewLine(os);
    }

    private static void writeNewLine(OutputStream os) throws IOException {
        os.write('\r');
        os.write('\n');
    }
    private static class MultipartHttpOutputMessage implements HttpOutputMessage {

        private final OutputStream outputStream;

        private final Charset charset;

        private final HttpHeaders headers = new HttpHeaders();

        private boolean headersWritten = false;

        /**
         * Instantiates a new Multipart http output message.
         *
         * @param outputStream the output stream
         * @param charset      the charset
         */
        public MultipartHttpOutputMessage(OutputStream outputStream, Charset charset) {
            this.outputStream = outputStream;
            this.charset = charset;
        }

        @Override
        public HttpHeaders getHeaders() {
            return (this.headersWritten ? HttpHeaders.readOnlyHttpHeaders(this.headers) : this.headers);
        }

        @Override
        public OutputStream getBody() throws IOException {
            writeHeaders();
            return this.outputStream;
        }

        private void writeHeaders() throws IOException {
            if (!this.headersWritten) {
                for (Map.Entry<String, List<String>> entry : this.headers.entrySet()) {
                    byte[] headerName = getBytes(entry.getKey());
                    for (String headerValueString : entry.getValue()) {
                        byte[] headerValue = getBytes(headerValueString);
                        this.outputStream.write(headerName);
                        this.outputStream.write(':');
                        this.outputStream.write(' ');
                        this.outputStream.write(headerValue);
                        writeNewLine(this.outputStream);
                    }
                }
                writeNewLine(this.outputStream);
                this.headersWritten = true;
            }
        }

        private byte[] getBytes(String name) {
            return name.getBytes(this.charset);
        }
    }

}
