/*
 *
 *  Copyright 2019-2021 felord.cn
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
package cn.felord.payment.wechat;

import org.springframework.boot.autoconfigure.condition.ConditionMessage;
import org.springframework.boot.autoconfigure.condition.ConditionOutcome;
import org.springframework.boot.autoconfigure.condition.SpringBootCondition;
import org.springframework.boot.context.properties.bind.Bindable;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotatedTypeMetadata;

import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * The type Wechat pay configured condition.
 *
 * @author felord.cn
 * @since 1.0.3.RELEASE
 */
public class WechatPayConfiguredCondition extends SpringBootCondition {

    /**
     * The constant STRING_WECHAT_V3_MAP.
     */
    private static final Bindable<Map<String, WechatPayProperties.V3>> STRING_WECHAT_V3_MAP = Bindable
            .mapOf(String.class, WechatPayProperties.V3.class);

    @Override
    public ConditionOutcome getMatchOutcome(ConditionContext context, AnnotatedTypeMetadata metadata) {
        ConditionMessage.Builder message = ConditionMessage.forCondition("Wechat Pay V3 Configured Condition");
        Map<String, WechatPayProperties.V3> v3 = getV3(context.getEnvironment());
        if (!v3.isEmpty()) {
            return ConditionOutcome.match(message.foundExactly("registered wechat mchIds " + v3.values().stream()
                    .map(WechatPayProperties.V3::getMchId).collect(Collectors.joining(", "))));
        }
        return ConditionOutcome.noMatch(message.notAvailable("registered wechat pay configs"));
    }

    private Map<String, WechatPayProperties.V3> getV3(Environment environment) {
        return Binder.get(environment).bind("wechat.pay.v3", STRING_WECHAT_V3_MAP)
                .orElse(Collections.emptyMap());
    }

}
