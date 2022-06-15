package org.ryuji.ddd.demo.infrastructure.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.config.GlobalConfig;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.OptimisticLockerInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.ryuji.ddd.demo.project.config.MyMetaObjectHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.annotation.Resource;
import javax.sql.DataSource;


@EnableTransactionManagement
@MapperScan(basePackages = "com.showyou.core.infrastructure.mapper", sqlSessionFactoryRef = "mybatisSqlSessionFactory")
@Configuration
public class MybatisDataSource {

    private final static Logger logger = LoggerFactory.getLogger(MybatisDataSource.class);

    @Resource
    @Qualifier("master")
    private DataSource dataSource;

    @Value("${mybatis.config-location}")
    private String configLocation;

    @Value("${mybatis.mapper-locations}")
    private String mapperLocation;

    @Value("${mybatis.type-aliases-package}")
    private String typeAliasesPackage;

    @Bean("mybatisSqlSessionFactory")
    public SqlSessionFactory ds1SqlSessionFactory(DataSource dataSource) throws Exception {
        MybatisSqlSessionFactoryBean sqlSessionFactoryBean = new MybatisSqlSessionFactoryBean();
        GlobalConfig globalConfig = new GlobalConfig();
        globalConfig.setMetaObjectHandler(new MyMetaObjectHandler());
        sqlSessionFactoryBean.setPlugins(mybatisPlusInterceptor());
        sqlSessionFactoryBean.setGlobalConfig(globalConfig);
        sqlSessionFactoryBean.setDataSource(dataSource);
        sqlSessionFactoryBean.setMapperLocations(
                new PathMatchingResourcePatternResolver().getResources(mapperLocation));
        return sqlSessionFactoryBean.getObject();
    }

    @Bean(name = "mybatisTransactionManager")
    public DataSourceTransactionManager mybatisTransactionManager() {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = "mybatisSqlSessionTemplate")
    public SqlSessionTemplate primarySqlSessionTemplate(@Qualifier("mybatisSqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

    /** MybatisPlus 3.x 插件
     * @return
     */
    public MybatisPlusInterceptor mybatisPlusInterceptor(){
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        // 字段值自动填充
        interceptor.addInnerInterceptor(new OptimisticLockerInnerInterceptor());
        // 分页
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        //乐观锁默认已开启
        return interceptor;
    }

}
