package com.mybatismbg.testmybatismbg;

import com.alibaba.druid.pool.DruidDataSource;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mybatismbg.testmybatismbg.bean.Employees;
import com.mybatismbg.testmybatismbg.service.EmpService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.convert.ElasticsearchConverter;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.*;

import javax.sql.DataSource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Slf4j
@SpringBootTest
class TestmybatismbgApplicationTests {

    @Autowired
    EmpService empService;

    @Test
    void contextLoads() {
        // 获取一个emp对象测试他的日期
        Employees empById = empService.getEmpById(106);
        System.out.println(empById);
        Date empBirth = empById.getEmpBirth();
        String format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(empBirth);
        log.info("查出的员工:" + empById);
        log.info("该员工的生日:" + format);
        // 使用jackson将对象转为json数据
        ObjectMapper objectMapper = new ObjectMapper();
        // 关闭时间戳的功能,否则时间显示为时间戳,设置时间的格式
        objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
        String json = "{}";
        try {
            json = objectMapper.writeValueAsString(empById);
            log.warn("解析的json:" + json);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        // 将json转换为对象,传入的参数:json字符串,目标类型(Class)
        try {
            Employees employees = objectMapper.readValue(json, Employees.class);
            log.info("得到的对象:" + employees.getClass());
            log.info("输出:" + employees);
            // 比较两个对象的值
            log.info("是否一样:" + employees.equals(empById));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

    }

    @Autowired
    DruidDataSource dataSource;

    @Test
    public void testDatasource() {
        System.out.println(dataSource);
        System.out.println("初始连接数:" + dataSource.getInitialSize());
    }
    /*
    @Test
    public void testPassUsername() {
//        char[] chars = {'4','5','2','4'};
//        String s = String.copyValueOf(chars);
//        System.out.println(s);
        ByteSource salt = ByteSource.Util.bytes("user");
        SimpleHash md5 = new SimpleHash("md5", "654321", salt, 5);
        System.out.println(md5);
    }

    // 测试使用ElasticSearchTemplate模版
    @Autowired
    ElasticsearchRestTemplate restTemplate;

    // 删除索引的测试
    @Test
    public void testDELETEIndex(){
        boolean megacorp = restTemplate.deleteIndex("megacorp");
        System.out.println("删除:" + megacorp);
    }

    // 向es2中保存文档
    @Test
    public void testElasticsearchTemplate() {
        // 设置索引
        IndexCoordinates indexCoordinates = IndexCoordinates.of("megacorp");
        ObjectMapper objectMapper = new ObjectMapper();
        // 向es2中的megacorp索引添加20条记录
        for (int i = 101;i <= 121;i++){
            // 查询数据
            Employees empById = empService.getEmpById(i);
            // 转化为json数据
            String json = "";
            try {
                json = objectMapper.writeValueAsString(empById);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            // 创建indexQuery,填充数据
            IndexQuery indexQuery = new IndexQueryBuilder()
                    .withId(String.valueOf(empById.getEmployeeId()))
                    .withSource(json)
                    .build();
            // 执行索引
            String index = restTemplate.index(indexQuery, indexCoordinates);
            System.out.println("索引完成:" + index);

        }
    }


    // 使用查询字符串查询出文档信息
    @Test
    public void testSelectByEs2() {
        IndexCoordinates indexCoordinates = IndexCoordinates.of("megacorp");
        Employees employees = restTemplate.get("110", Employees.class, indexCoordinates);
        log.info("查询的结果: " + employees.toString());
        System.out.println("----------下面将按照条件查询----------");
        // 使用查询字符串的方式
        String match = "{\n" +
                "    \"query\":\n" +
                "    {\n" +
                "        \"match_phrase\":\n" +
                "        {\n" +
                "            \"departmentId\": \"100\"\n" +
                "        }\n" +
                "    }\n" +
                "}";
//        Query query = new StringQuery(match);
//        List<Employees> search = restTemplate.multiGet(query, Employees.class, indexCoordinates);
        Criteria criteria = new Criteria("departmentId");

        Query criteriaQuery = new CriteriaQuery(criteria);
        SearchHits<Employees> search = restTemplate.search(criteriaQuery, Employees.class, indexCoordinates);
        System.out.println("结果:" + search);
        System.out.println("查询到的记录数:" + search.getTotalHits());
        List<SearchHit<Employees>> searchHits = search.getSearchHits();
        int i = 1;
        for (SearchHit<Employees> hit : searchHits){
            System.out.println("####第" + i + "个####");
            System.out.println(hit.toString());
            i++;
        }

    }
     */

    @Test
    public void testClassLoader(){
        // 测试类加载器
        Employees obj = new Employees();
        String str = new String("4444");
        // 测试输出
        System.out.println(obj.getClass().getClassLoader().getParent().getParent());
        System.out.println(obj.getClass().getClassLoader().getParent());
        System.out.println(obj.getClass().getClassLoader());
        // 测试,JDK自带的类是通过启动类加载器去加载
        System.out.println("String的类加载器是:" + str.getClass().getClassLoader());
        // 获取运行时的信息
        System.out.println("------------------------------");
        // 虚拟机试图获取的最大的内存
        long MAX_MEMORY = Runtime.getRuntime().maxMemory();
        long MIN_MEMORY = Runtime.getRuntime().totalMemory();
        // 输出
        System.out.println("虚拟机试图获取的最大内存:" + Math.round(MAX_MEMORY/1000.0/1000) + "MB");
        System.out.println("虚拟机初始的内存大小:" + Math.round(MIN_MEMORY/1000.0/1000) + "MB");
    }


}
