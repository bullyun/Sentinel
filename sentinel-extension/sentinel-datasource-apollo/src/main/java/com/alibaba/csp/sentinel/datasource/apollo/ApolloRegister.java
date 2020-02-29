package com.alibaba.csp.sentinel.datasource.apollo;

import com.alibaba.csp.sentinel.datasource.ReadableDataSource;
import com.alibaba.csp.sentinel.init.InitFunc;
import com.alibaba.csp.sentinel.init.InitOrder;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

import java.util.List;

public class ApolloRegister implements InitFunc {

    @Override
    public void init() throws Exception {
        String namespaceName = System.getProperty("sentinel.apollo.namespace", "application");
        String appId = System.getProperty("app.id", "sentinel");

        String flowRuleKey = appId + "-flow-rules";
        String defaultFlowRules = "[]";
        ReadableDataSource<String, List<FlowRule>> flowRuleDataSource = new ApolloDataSource<>(namespaceName,
                flowRuleKey, defaultFlowRules, source -> JSON.parseObject(source, new TypeReference<List<FlowRule>>() {
        }));
        FlowRuleManager.register2Property(flowRuleDataSource.getProperty());
    }
}
