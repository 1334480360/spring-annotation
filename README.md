## Spring注解开发练习

IOC
---

1. **@Configuration**：标记为配置类
    
2. **@ComponentScan**：组件扫描
    1. useDefaultFilters=false：禁用默认过滤器；
    2. 自定义过滤器：实现`org.springframework.core.type.filter.TypeFilter接口`。
    ```
    /**
     * @title：自定义包扫描过滤器
     * @author：xuan
     * @date：2018/10/30
     */
    public class MyTypeFilter implements TypeFilter {
    	/**
    	 * @title：匹配器
    	 * metadataReader：读取到的当前正在扫描的类的信息
    	 * metadataReaderFactory：可以获取到其他任何类信息的工厂
    	 * @author：xuan
    	 * @date：2018/10/30
    	 */
    	@Override
    	public boolean match(MetadataReader metadataReader, MetadataReaderFactory metadataReaderFactory) {
    		//获取当前类的注解信息
    		AnnotationMetadata annotationMetadata = metadataReader.getAnnotationMetadata();
    		//获取当前类的类信息
    		ClassMetadata classMetadata = metadataReader.getClassMetadata();
    		//获取当前类的资源信息（类路径）
    		Resource resource = metadataReader.getResource();
    
    		String className = classMetadata.getClassName();
    		System.out.println("类名----" + className);
    
    		if (className.contains("service")) {
    			return true;
    		}
    
    		return false;
    	}
    }
    ```
3. **@Bean**：注册Bean
    1. 默认使用方法名作为id，可以在后面定义id如@Bean("xx")；
    2. 默认为单例。
    3. 可以指定init方法和destroy方法：`@Bean(initMethod = "init", destroyMethod = "destroy")`
    > 1. 对象创建和赋值完成，调用初始化方法；
    > 2. 单实例bean在容器销毁的时候执行destroy方法；
    > 3. 多实例bean，容器关闭是不会调用destroy方法。

4. **@Scope**：Bean作用域
    1. 默认为singleton；
    2. 类型：
        1. singleton单实例（默认值）：ioc容器启动时会调用方法创建对象放到ioc容器中，以后每次获取就是直接从容器中拿实例；
        2. prototype多实例：ioc容器启动不会创建对象，每次获取时才会调用方法创建实例；
        3. request同一次请求创建一个实例；
        4. session同一个session创建一个实例。

5. **@Lazy**：懒加载
    1. 针对singleton，容器启动不创建对象，第一次获取时才创建实例

6. **@Conditional**：创建bean的条件
    1. 需要编写条件，实现`org.springframework.context.annotation.Condition`接口；
    2. 使用：`@Conditional({MacCondition.class})`
    ```
    /**
     * @title：判断是否为mac os
     * @author：xuan
     * @date：2018/10/30
     */
    public class MacCondition implements Condition {
    
    	/**
    	 * @title：条件匹配器
    	 * @param context 上下文
    	 * @param annotatedTypeMetadata 注释信息
    	 * @author：xuan
    	 * @date：2018/10/30
    	 */
    	@Override
    	public boolean matches(ConditionContext context, AnnotatedTypeMetadata annotatedTypeMetadata) {
    		//获取ioc使用的beanFactory
    		ConfigurableListableBeanFactory beanFactory = context.getBeanFactory();
    		//获取类加载器
    		ClassLoader classLoader = context.getClassLoader();
    		//获取环境变量
    		Environment environment = context.getEnvironment();
    		//获取bean定义的注册类
    		BeanDefinitionRegistry registry = context.getRegistry();
    
    		//可以判断容器中的bean注册情况，也可以给容器中注册bean
    //		boolean flag = registry.containsBeanDefinition("person");
    
    		String property = environment.getProperty("os.name");
    		if (property.contains("Mac")) {
    			return true;
    		}
    
    		return false;
    	}
    }
    ```

> 给容器注册组件：
> 1. 包扫描+组件标注注解，如@Controller/@Service/@Repository/@Component[局限于自己写的类]。
> 2. @Bean[导入第三方包里面的组件]
> 3. @Import/@ImportSelector[快速给容器导入一些组件]
> 4. 实现`org.springframework.context.annotation.ImportBeanDefinitionRegistrar`接口，手动注入类。
> 5. 使用FactoryBean(工厂Bean)

7. **@Import**：快速导入一些组件
    1. 代码：`@Import({Color.class, Red.class})`;
    2. 组件名称默认为类全路径名。

8. **@ImportSelector**：快速导入一些组件
    1. 需要实现`org.springframework.context.annotation.ImportSelector`接口，编写选择器；
    2. 返回导入到容器中的组件的全类名数组。
    ```
    /**
     * @title：自定义导入组件选择器
     * @author：xuan
     * @date：2018/10/30
     */
    public class MyImportSelector implements ImportSelector {
    	/**
    	 * @title：返回值就是导入到容器中的组件的全类名数组
    	 * @param annotationMetadata 当前标注@Import注解的类的所有注解信息
    	 * @author：xuan
    	 * @date：2018/10/30
    	 */
    	@Override
    	public String[] selectImports(AnnotationMetadata annotationMetadata) {
    
    		return new String[]{"com.test.bean.Blue", "com.test.bean.Yellow"};
    	}
    }
    ```

9. **使用FactoryBean创建Bean**：
    1. 需要实现FactoryBean接口：
    2. 默认获取到的是FactoryBean调用getObject()创建的对象；`
		Object colorFactoryBean = applicationContext2.getBean("colorFactoryBean");`
    3. 代码：
    ```
    import org.springframework.beans.factory.FactoryBean;
    /**
     * @title：工厂Bean
     * @author：xuan
     * @date：2018/10/30
     */
    public class ColorFactoryBean implements FactoryBean<Color> {
    	/**
    	 * @title：返回对象，添加到容器中
    	 * @author：xuan
    	 * @date：2018/10/30
    	 */
    	@Override
    	public Color getObject() throws Exception {
    		System.out.println("注册对象");
    		return new Color();
    	}
    
    	/**
    	 * @title：返回对象类型
    	 * @author：xuan
    	 * @date：2018/10/30
    	 */
    	@Override
    	public Class<?> getObjectType() {
    		return Color.class;
    	}
    
    	/**
    	 * @title：控制对象是否为单例
    	 * @author：xuan
    	 * @date：2018/10/30
    	 */
    	@Override
    	public boolean isSingleton() {
    		return false;
    	}
    }
    ```
10. **Bean生命周期**：
    1. 通过@Bean指定init和destroy方法：`@Bean(initMethod = "init", destroyMethod = "destroy")`
        1. 对象创建和赋值完成，调用初始化方法；
        2. 单实例bean在容器销毁的时候执行destroy方法；
        3. 多实例bean，容器关闭是不会调用destroy方法。
    2. 通过让Bean实现InitializingBean接口和DisposableBean接口来管理：
    ```
    @Component
    public class Cat implements InitializingBean, DisposableBean {
    	public Cat() {
    		System.out.println("cat constructor...");
    	}
    
    	@Override
    	public void destroy() throws Exception {
    		System.out.println("cat destroy...");
    	}
    
    	@Override
    	public void afterPropertiesSet() throws Exception {
    		System.out.println("cat afterPropertiesSet...");
    	}
    }
    ```
    3. 可以使用JSR250的`@PostConstruct`和`@PreDestroy`注解实现：
    ```
    @Component
    public class Dog {
    
    	public Dog() {
    		System.out.println("dog constructor...");
    	}
    
    	@PostConstruct
    	public void init() {
    		System.out.println("dog @PostConstruct...");
    	}
    
    	@PreDestroy
    	public void destroy() {
    		System.out.println("dog @PreDestroy...");
    	}
    }
    ```
    4. 使用`BeanPostProcessor`（后置处理器）接口管理++所有++bean初始化前后进行一些处理。
        1. 原理：遍历得到容器中所有的BeanPostProcessor，挨个执行下面的方法;
            1. 给bean进行属性赋值`populateBean(beanName, mbd, instanceWrapper)`
            2. 执行初始化前方法`applyBeanPostProcessorsBeforeInitialization(bean, beanName)`
            3. 执行自定义初始化方法`invokeInitMethods(beanName, wrappedBean, mbd)`
            4. 执行初始化后方法`postProcessAfterInitialization(result, beanName)`
    
        2. 代码：
        ```
        @Component
        public class MyBeanPostProcessor implements BeanPostProcessor {
        	@Override
        	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        		System.out.println("postProcessBeforeInitialization..." + beanName);
        		return bean;
        	}
        
        	@Override
        	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        		System.out.println("postProcessAfterInitialization..." + beanName);
        		return bean;
        	}
        }
        ```
        3. spring底层对BeanPostProcessor的使用：
            1. ApplicationContextAwareProcessor管理ioc容器applicationContext
            ```
            class ApplicationContextAwareProcessor implements BeanPostProcessor {
                private final ConfigurableApplicationContext applicationContext;
                private final StringValueResolver embeddedValueResolver;
            
                public ApplicationContextAwareProcessor(ConfigurableApplicationContext applicationContext) {
                    this.applicationContext = applicationContext;
                    this.embeddedValueResolver = new EmbeddedValueResolver(applicationContext.getBeanFactory());
                }
            
                public Object postProcessBeforeInitialization(final Object bean, String beanName) throws BeansException {
                    //省略。。。
                    this.invokeAwareInterfaces(bean);
                    return bean;
                }
            
                private void invokeAwareInterfaces(Object bean) {
                    if (bean instanceof Aware) {
                        //省略。。。
                        if (bean instanceof ApplicationContextAware) {
                            ((ApplicationContextAware)bean).setApplicationContext(this.applicationContext);
                        }
                    }
            
                }
            
                public Object postProcessAfterInitialization(Object bean, String beanName) {
                    return bean;
                }
            }
            ```
            2. BeanValidationPostProcessor做数据校验：
            ```
            public class BeanValidationPostProcessor implements BeanPostProcessor, InitializingBean {
                private Validator validator;
                private boolean afterInitialization = false;
            
                public BeanValidationPostProcessor() {
                }
            
                public void setValidator(Validator validator) {
                    this.validator = validator;
                }
            
                public void setValidatorFactory(ValidatorFactory validatorFactory) {
                    this.validator = validatorFactory.getValidator();
                }
            
                public void setAfterInitialization(boolean afterInitialization) {
                    this.afterInitialization = afterInitialization;
                }
            
                public void afterPropertiesSet() {
                    if (this.validator == null) {
                        this.validator = Validation.buildDefaultValidatorFactory().getValidator();
                    }
            
                }
            
                public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
                    if (!this.afterInitialization) {
                        this.doValidate(bean);
                    }
            
                    return bean;
                }
            
                public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
                    if (this.afterInitialization) {
                        this.doValidate(bean);
                    }
            
                    return bean;
                }
            
                protected void doValidate(Object bean) {
                    Set<ConstraintViolation<Object>> result = this.validator.validate(bean, new Class[0]);
                    if (!result.isEmpty()) {
                        StringBuilder sb = new StringBuilder("Bean state is invalid: ");
                        Iterator it = result.iterator();
            
                        while(it.hasNext()) {
                            ConstraintViolation<Object> violation = (ConstraintViolation)it.next();
                            sb.append(violation.getPropertyPath()).append(" - ").append(violation.getMessage());
                            if (it.hasNext()) {
                                sb.append("; ");
                            }
                        }
            
                        throw new BeanInitializationException(sb.toString());
                    }
                }
            }
            ```
            3. InitDestroyAnnotationBeanPostProcessor控制生命周期注解的执行：
            ```
            public class InitDestroyAnnotationBeanPostProcessor implements DestructionAwareBeanPostProcessor, MergedBeanDefinitionPostProcessor, PriorityOrdered, Serializable
            ```
            4. AutowiredAnnotationBeanPostProcessor控制@Autowired注解的作用
            > spring注解都是通过BeanPostProcessor功能实现的。
            
            ```
            public class AutowiredAnnotationBeanPostProcessor extends InstantiationAwareBeanPostProcessorAdapter implements MergedBeanDefinitionPostProcessor, PriorityOrdered, BeanFactoryAware 
            ```
11. **@Value**：给变量赋值
    1. 代码：
    ```
    import org.springframework.beans.factory.annotation.Value;

    public class Person extends BaseEntity{
    	@Value("xuan")
    	private String name;
    	@Value("27")
    	private int age;
    	@Value("#{20-7}")
    	private int count;
    	@Value("${person.nickName}")
    	private String nickName;
    }
    ```
    2. 方式：
        1. 基本数字
        2. 可以写SpEL（Spring EL表达式）：#{}
        3. 可以写${}，取出配置文件中的值（在运行环境变量里面的值）
13. **@PropertySource**：加载配置文件
    ```
    @PropertySource(value = {"person.properties"})
    ```
14. **@Autowired**：自动装配
    1. 默认优先按照类型去容器中找对应的组件：`BookService bookService = applicationContext.getBean(BookService.class);`
    2. 默认一定要找到，如果没有找到则报错。可以使用`@Autowired(required = false)`标记bean为非必须的。
    3. 如果找到多个相同类型的组件，再根据属性名称去容器中查找。
    4. `@Qualifier("bookDao2")`明确的指定要装配的bean。
    5. `@Primary`：让spring默认装配首选的bean，也可以使用`@Qualifier()`指定要装配的bean。
    ```
    @Service
    public class BookService {
    	@Qualifier("bookDao2")
    	@Autowired
    	private BookDao bookDao;
    
    	public void print() {
    		System.out.println(bookDao);
    	}
    }
    
    @Configuration
    @ComponentScan(value = {"com.test.controller", "com.test.service", "com.test.dao"})
    public class MainConfigOfAutowired {
    
    	@Primary
    	@Bean("bookDao2")
    	public BookDao bookDao() {
    		return new BookDao(2);
    	}
    
    }
    ```
    6. 自动装配-方法、构造器位置的自动装配：
        1. 方法位置：（默认不写）
            1. @Autowired放在set方法上，参数自动从容器中获取。
            2. 在@Bean标注的方法上，参数自动从容器中获取。
        ```
        @Component
        public class Boss extends BaseEntity{
        
        	private Car car;
        
        	public Car getCar() {
        		return car;
        	}
        
        	//@Autowired
        	public void setCar(Car car) {
        		this.car = car;
        	}
        }
        
        @Configuration
        @ComponentScan(value = {"com.test.controller", "com.test.service", "com.test.dao", "com.test.bean"})
        public class MainConfigOfAutowired {
        	@Bean
        	public Color color(Car car) {
        		return new Color();
        	}
        
        }
        ```
        2. 构造器位置：
            1. 如果组件只有一个有参构造器，这个有参构造器的@Autowired可以省略.
        ```
        //@Autowired
    	public Boss(Car car) {
    		System.out.println("boss car...");
    		this.car = car;
    	}
        ```
        3. 参数位置：(默认不写)
        ```
        public void setCar(@Autowired Car car) {
    		this.car = car;
    	}
        ```
15. **@Resource和@Inject**：java规范注解
    1. `@Resource`可以和`@Autowired`一样自动装配，默认是按照组件名称进行装配的；可以使用`@Resource(name = "bookDao2")`指定要注入的bean，但是不支持@Primary，功能没有支持像@Arowired中的requred=false功能。
    2. `@Inject`：需要导入javax.inject包，也能实现Autowired自动装配功能，支持`@Primary`，但是没有requred=false功能。
    > 推荐使用`@Autowired`.
16. **Aware接口**：将Spring底层组件注入之定义组件中使用
    1. 自定义组件想要使用Spring容器底层的一些组件（ApplicationContext，BeanFactory等），则自定义组件需要实现对应的xxxAware接口，在创建对象的时候，会调用接口规定的方法注入相关组件。
    ```
    /**
     * @title：调用Spirng底层组件
     * @author：xuan
     * @date：2018/10/31
     */
    @Component
    public class Red extends BaseEntity implements ApplicationContextAware, BeanNameAware, EmbeddedValueResolverAware {
    	private ApplicationContext applicationContext;
    
    	@Override
    	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
    		System.out.println("传入的ioc：" + applicationContext);
    		this.applicationContext = applicationContext;
    	}
    
    	@Override
    	public void setBeanName(String name) {
    		System.out.println("当前bean的名字：" + name);
    	}
    
    	/**
    	 * @title：String值解析器
    	 */
    	@Override
    	public void setEmbeddedValueResolver(StringValueResolver resolver) {
    		String s = resolver.resolveStringValue("你好${os.name}");
    		System.out.println("解析的字符串：" + s);
    	}
    }
    ```
    2. xxxAware：功能使用xxxProcessor。如`ApplicationContextAware-- ApplicationContextAwareProcessor`
    ```
    class ApplicationContextAwareProcessor implements BeanPostProcessor {
        private final ConfigurableApplicationContext applicationContext;
        private final StringValueResolver embeddedValueResolver;
    
        public ApplicationContextAwareProcessor(ConfigurableApplicationContext applicationContext) {
            this.applicationContext = applicationContext;
            this.embeddedValueResolver = new EmbeddedValueResolver(applicationContext.getBeanFactory());
        }
    
        public Object postProcessBeforeInitialization(final Object bean, String beanName) throws BeansException {
            //省略。。。
            this.invokeAwareInterfaces(bean);
            return bean;
        }
    
        private void invokeAwareInterfaces(Object bean) {
            if (bean instanceof Aware) {
                //省略。。。
                if (bean instanceof ApplicationContextAware) {
                    ((ApplicationContextAware)bean).setApplicationContext(this.applicationContext);
                }
            }
    
        }
    
        public Object postProcessAfterInitialization(Object bean, String beanName) {
            return bean;
        }
    }
    ```
    3. 
17. **@Profile**：环境标识
    1. Spring为我们提供的可以根据当前环境，动态的激活和切换一系列组件的功能；
    2. @Profile指定组件在哪个环境才能被注册到容器中，默认为"default"`@Profile("default")`。
    3. 激活方式：
        1. 运行时添加虚拟机参数：`-Dspring.profiles.active=test`
        2. 代码方式：
        ```
        @PropertySource("classpath:/dbconfig.properties")
        @Configuration
        public class MainConfigOfProfile {
        
        	@Value("${db.url}")
        	private String url;
        	@Value("${db.userName}")
        	private String userName;
        	@Value("${db.password}")
        	private String password;
        
        	@Profile("dev")
        	@Bean
        	public DataSource dataSource() {
        		MysqlDataSource dataSource = new MysqlDataSource();
        		dataSource.setURL(url);
        		dataSource.setUser(userName);
        		dataSource.setPassword(password);
        		return dataSource;
        	}
        }
        
        
        
    	@Test
    	public void test01() {
    		//1、创建一个无参applicationContext
    		AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
    		//2、设置需要激活的环境
    		applicationContext.getEnvironment().setActiveProfiles("test");
    		//3、注册主配置类
    		applicationContext.register(MainConfigOfProfile.class);
    		//4、启动刷新容器
    		applicationContext.refresh();
    
    		Map<String, DataSource> beansOfType = applicationContext.getBeansOfType(DataSource.class);
    		for (Map.Entry<String, DataSource> entry : beansOfType.entrySet()) {
    			System.out.println("key:" + entry.getKey() + ", value:" + entry.getValue());
    		}
    	}
        ```
    4. `@Profile`也可以配置在类上。

### AOP
---
1. 