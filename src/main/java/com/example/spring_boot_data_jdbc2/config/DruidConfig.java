package com.example.spring_boot_data_jdbc2.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 贺威
 * @create 2018-12-04 16:07
 */
@Configuration
public class DruidConfig {


    /**
     * 引入其他配置
     * @return
     */
    @ConfigurationProperties(prefix = "spring.datasource")
    @Bean
    public DataSource druid() {

      return   new DruidDataSource();
    }


    //配置druid的监控

    /**
     * // 1,配置一个管理后台的Servlet
     * @return
     */
    @Bean
    public ServletRegistrationBean servletRegistrationBean(){
        ServletRegistrationBean bean = new ServletRegistrationBean(new StatViewServlet(), "/druid/*");

        Map<String,String> initParams= new HashMap<>();
        initParams.put("loginUsername", "admin");
        initParams.put("loginPassword", "123");
        //如果为空就是默认允许所有人访问
        initParams.put("allow", "");
        initParams.put("deny", "192.168.245.132");
        bean.setInitParameters(initParams);
        return bean;
    }

    /**
     *  // 2,配置一监控Web的filter
     */
    @Bean
    public FilterRegistrationBean webStaFilter(){
        FilterRegistrationBean bean = new FilterRegistrationBean();

        bean.setFilter(new WebStatFilter());
        Map<String,String> map=new HashMap<>();
        // 不拦截
        map.put("exclusions", "*.js,*.css,/druid/*");
        bean.setInitParameters(map);
        // 拦截所有请求
        bean.setUrlPatterns(Arrays.asList("/*"));
        return bean;
    }
}
