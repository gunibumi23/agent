package com.agent.config.db;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.aspectj.lang.annotation.Aspect;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.interceptor.DefaultTransactionAttribute;
import org.springframework.transaction.interceptor.RollbackRuleAttribute;
import org.springframework.transaction.interceptor.RuleBasedTransactionAttribute;
import org.springframework.transaction.interceptor.TransactionInterceptor;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
@Aspect
@RequiredArgsConstructor
public class AgentDbAdvice {
	
	private static final String AOP_POINTCUT_EXPRESSION = "execution(* com.agent.domains..*Service.*(..))";
	private static final int TX_METHOD_TIMEOUT = 10;
	
	private final PlatformTransactionManager txManager;

    @Bean
    TransactionInterceptor txAdvice() {

        TransactionInterceptor txAdvice = new TransactionInterceptor();
        
        DefaultTransactionAttribute readAttr = new DefaultTransactionAttribute(TransactionDefinition.PROPAGATION_REQUIRED);
        readAttr.setReadOnly(true);
        readAttr.setTimeout(TX_METHOD_TIMEOUT);
        
        
        List<RollbackRuleAttribute> rollbackRules = new ArrayList<>();
        rollbackRules.add(new RollbackRuleAttribute(Exception.class));

        DefaultTransactionAttribute transAttr = new RuleBasedTransactionAttribute(TransactionDefinition.PROPAGATION_REQUIRED, rollbackRules);
        transAttr.setTimeout(TX_METHOD_TIMEOUT);
        
        String strReadAttr  = readAttr.toString();
        String strTransAttr = transAttr.toString();
        
        Properties txAttr = new Properties();
        
        txAttr.setProperty("select", strReadAttr);        
        txAttr.setProperty("update", strTransAttr);
        txAttr.setProperty("insert", strTransAttr);
        txAttr.setProperty("delete", strTransAttr);
        txAttr.setProperty("merge" , strTransAttr);

        txAdvice.setTransactionAttributes(txAttr);
        txAdvice.setTransactionManager(txManager);

        return txAdvice;
    }

    @Bean
    DefaultPointcutAdvisor txAdviceAdvisor() {
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression(AOP_POINTCUT_EXPRESSION);
        return new DefaultPointcutAdvisor(pointcut, txAdvice());
    }
}