/*
 *
 *  Copyright 2019-2022 felord.cn
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       https://www.apache.org/licenses/LICENSE-2.0
 *  Website:
 *       https://felord.cn
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */
package cn.felord.payment.wechat.v3;

import org.springframework.http.*;
import org.springframework.http.converter.*;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.http.converter.json.JsonbHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.smile.MappingJackson2SmileHttpMessageConverter;
import org.springframework.http.converter.support.AllEncompassingFormHttpMessageConverter;
import org.springframework.http.converter.xml.Jaxb2RootElementHttpMessageConverter;
import org.springframework.http.converter.xml.SourceHttpMessageConverter;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;
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
 * 用于微信支付处理上传的自定义消息转换器.
 *
 * @author felord.cn
 * @see AllEncompassingFormHttpMessageConverter
 * @since 1.0.0.RELEASE
 */
final class ExtensionFormHttpMessageConverter extends FormHttpMessageConverter {
    /**
     * The constant BOUNDARY.
     */
    private static final String BOUNDARY = "boundary";

    /**
     * The constant jaxb2Present.
     */
    private static final boolean JAXB_2_PRESENT;

    /**
     * The constant jackson2Present.
     */
    private static final boolean JACKSON_2_PRESENT;

    /**
     * The constant jackson2XmlPresent.
     */
    private static final boolean JACKSON_2_XML_PRESENT;

    /**
     * The constant jackson2SmilePresent.
     */
    private static final boolean JACKSON_2_SMILE_PRESENT;

    /**
     * The constant gsonPresent.
     */
    private static final boolean GSON_PRESENT;

    /**
     * The constant jsonbPresent.
     */
    private static final boolean JSONB_PRESENT;

    /**
     * The Part converters.
     */
    private final List<HttpMessageConverter<?>> partConverters = new ArrayList<>();

    static {
        ClassLoader classLoader = AllEncompassingFormHttpMessageConverter.class.getClassLoader();
        JAXB_2_PRESENT = ClassUtils.isPresent("javax.xml.bind.Binder", classLoader);
        JACKSON_2_PRESENT = ClassUtils.isPresent("com.fasterxml.jackson.databind.ObjectMapper", classLoader) &&
                ClassUtils.isPresent("com.fasterxml.jackson.core.JsonGenerator", classLoader);
        JACKSON_2_XML_PRESENT = ClassUtils.isPresent("com.fasterxml.jackson.dataformat.xml.XmlMapper", classLoader);
        JACKSON_2_SMILE_PRESENT = ClassUtils.isPresent("com.fasterxml.jackson.dataformat.smile.SmileFactory", classLoader);
        GSON_PRESENT = ClassUtils.isPresent("com.google.gson.Gson", classLoader);
        JSONB_PRESENT = ClassUtils.isPresent("javax.json.bind.Jsonb", classLoader);
    }

    /**
     * Instantiates a new Upload http message converter.
     */
    public ExtensionFormHttpMessageConverter() {
        StringHttpMessageConverter stringHttpMessageConverter = new StringHttpMessageConverter();
        // see SPR-7316
        stringHttpMessageConverter.setWriteAcceptCharset(false);

        this.partConverters.add(new ByteArrayHttpMessageConverter());
        this.partConverters.add(stringHttpMessageConverter);
        this.partConverters.add(new ResourceHttpMessageConverter());
        try {
            this.partConverters.add(new SourceHttpMessageConverter<>());
        } catch (Error err) {
            // Ignore when no TransformerFactory implementation is available
        }

        if (JAXB_2_PRESENT && !JACKSON_2_XML_PRESENT) {
            this.partConverters.add(new Jaxb2RootElementHttpMessageConverter());
        }

        if (JACKSON_2_PRESENT) {
            this.partConverters.add(new MappingJackson2HttpMessageConverter());
        } else if (GSON_PRESENT) {
            this.partConverters.add(new GsonHttpMessageConverter());
        } else if (JSONB_PRESENT) {
            this.partConverters.add(new JsonbHttpMessageConverter());
        }

        if (JACKSON_2_SMILE_PRESENT) {
            this.partConverters.add(new MappingJackson2SmileHttpMessageConverter());
        }
        this.setPartConverters(this.partConverters);
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

    /**
     * Is multipart boolean.
     *
     * @param map         the map
     * @param contentType the content type
     * @return the boolean
     */
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

    /**
     * Write form.
     *
     * @param formData      the form data
     * @param contentType   the content type
     * @param outputMessage the output message
     * @throws IOException the io exception
     */
    private void writeForm(MultiValueMap<String, Object> formData, @Nullable MediaType contentType,
                           HttpOutputMessage outputMessage) throws IOException {

        contentType = getMediaType(contentType);
        outputMessage.getHeaders().setContentType(contentType);

        Charset charset = contentType.getCharset();
        // should never occur
        Assert.notNull(charset, "No charset");

        final byte[] bytes = serializeForm(formData, charset).getBytes(charset);
        outputMessage.getHeaders().setContentLength(bytes.length);

        if (outputMessage instanceof StreamingHttpOutputMessage) {
            StreamingHttpOutputMessage streamingOutputMessage = (StreamingHttpOutputMessage) outputMessage;
            streamingOutputMessage.setBody(outputStream -> StreamUtils.copy(bytes, outputStream));
        } else {
            StreamUtils.copy(bytes, outputMessage.getBody());
        }
    }

    /**
     * Gets media type.
     *
     * @param mediaType the media type
     * @return the media type
     */
    private MediaType getMediaType(@Nullable MediaType mediaType) {
        if (mediaType == null) {
            return new MediaType(MediaType.APPLICATION_FORM_URLENCODED, DEFAULT_CHARSET);
        } else if (mediaType.getCharset() == null) {
            return new MediaType(mediaType, DEFAULT_CHARSET);
        } else {
            return mediaType;
        }
    }

    /**
     * Write multipart.
     *
     * @param parts         the parts
     * @param outputMessage the output message
     * @throws IOException the io exception
     */
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

    /**
     * Write parts.
     *
     * @param os       the os
     * @param parts    the parts
     * @param boundary the boundary
     * @throws IOException the io exception
     */
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

    /**
     * Write part.
     *
     * @param name       the name
     * @param partEntity the part entity
     * @param os         the os
     * @throws IOException the io exception
     */
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


    /**
     * Write boundary.
     *
     * @param os       the os
     * @param boundary the boundary
     * @throws IOException the io exception
     */
    private void writeBoundary(OutputStream os, byte[] boundary) throws IOException {
        os.write('-');
        os.write('-');
        os.write(boundary);
        writeNewLine(os);
    }

    /**
     * Write end.
     *
     * @param os       the os
     * @param boundary the boundary
     * @throws IOException the io exception
     */
    private static void writeEnd(OutputStream os, byte[] boundary) throws IOException {
        os.write('-');
        os.write('-');
        os.write(boundary);
        os.write('-');
        os.write('-');
        writeNewLine(os);
    }

    /**
     * Write new line.
     *
     * @param os the os
     * @throws IOException the io exception
     */
    private static void writeNewLine(OutputStream os) throws IOException {
        os.write('\r');
        os.write('\n');
    }

    /**
     * The type Multipart http output message.
     */
    private static class MultipartHttpOutputMessage implements HttpOutputMessage {

        /**
         * The Output stream.
         */
        private final OutputStream outputStream;

        /**
         * The Charset.
         */
        private final Charset charset;

        /**
         * The Headers.
         */
        private final HttpHeaders headers = new HttpHeaders();

        /**
         * The Headers written.
         */
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

        /**
         * Write headers.
         *
         * @throws IOException the io exception
         */
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

        /**
         * Get bytes byte [ ].
         *
         * @param name the name
         * @return the byte [ ]
         */
        private byte[] getBytes(String name) {
            return name.getBytes(this.charset);
        }
    }

}
