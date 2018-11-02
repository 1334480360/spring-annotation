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
    
    		return new String[]{"com.test.bean.ioc.Blue", "com.test.bean.ioc.Yellow"};
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
1. **@Aspect**：标记为切面类
    1. 导入aop依赖包：
    ```
    <dependency>
        <groupId>org.springframework</groupId>
        <artifactId>spring-aspects</artifactId>
        <version>4.3.12.RELEASE</version>
    </dependency>
    ```
    2. 创建切面类：
    ```
    /**
     * 日志切面类
     *
     * @author xuan
     * @date 2018/11/1
     */
    @Aspect
    public class LogAspects {
    	/**
    	 * 公共的切入点表达式
    	 * 1、本类引用
    	 * 2、其他的切面引用
    	 *
    	 * @author xuan
    	 * @date 2018/11/1
    	 */
    	@Pointcut(value = "execution(public int com.test.aop.MathCalculator.*(..))")
    	public void pointCut(){}
    
    	/**
    	 * 前置通知
    	 *
    	 * 在目标方法之前切入，切入点表达式（指在哪个方法切入）
    	 * joinPoint参数一定要出现在参数列表第一位，放在后面会报错
    	 *
    	 * @author xuan
    	 * @date 2018/11/1
    	 */
    	@Before(value = "pointCut()")
    	public void logStart(JoinPoint joinPoint) {
    		String methodName = joinPoint.getSignature().getName();
    		Object[] args = joinPoint.getArgs();
    		System.out.println(methodName + "运行。。。参数列表是：" + Arrays.toString(args));
    	}
    
    	@After(value = "pointCut()")
    	public void logEnd() {
    		System.out.println("除法结束。。。");
    	}
    
    	@AfterReturning(value = "pointCut()", returning = "result")
    	public void logReturn(JoinPoint joinPoint, Object result) {
    		String methodName = joinPoint.getSignature().getName();
    		System.out.println(methodName + "正常返回。。。计算结果：" + result);
    	}
    
    	@AfterThrowing(value = "pointCut()", throwing = "exception")
    	public void logException(JoinPoint joinPoint, Exception exception){
    		String methodName = joinPoint.getSignature().getName();
    		System.out.println(methodName + "异常，异常信息：" + exception);
    	}
    
    }
    ```
    >通知方法类型：
    >1. 前置通知（logStart）：在目标方法运行之前运行；
    >2. 后置通知（logEnd）：在目标方法运行结束之后运行；
    >3. 返回通知（logReturn）：在目标方法正常返回之后运行；
    >4. 异常通知（logException）：在目标方法出现异常是运行；
    >5. 环绕通知（动态代理）：手动推荐目标方法运行（joinPoint.procced()）。
    
    3. 将切面类和目标类都加入到容器中，并开启基于注解的aop动态代理：
    ```
    public class MathCalculator {
    	public int div(int i, int j) {
    		System.out.println("MathCalculator.div.....");
    		return i/j;
    	}
    
    }
    
    
    /**
     * aop
     *  在程序运行期间动态的将某段代码切入到指定方法指定位置进行运行的编程方式
     * '@EnableAspectJAutoProxy'：开启基于注解的aop动态代理
     *
     * @author xuan
     * @date 2018/11/1
     */
    @Configuration
    @EnableAspectJAutoProxy
    public class MainConfigOfAOP {
    
    	/**
    	 * 业务逻辑类加入容器中
    	 *
    	 * @author xuan
    	 * @date 2018/11/1
    	 */
    	@Bean
    	public MathCalculator calculator() {
    		return new MathCalculator();
    	}
    
    	/**
    	 * 切面类加入容器中
    	 *
    	 * @author xuan
    	 * @date 2018/11/1
    	 */
    	@Bean
    	public LogAspects logAspects(){
    		return new LogAspects();
    	}
    
    }
    
    ```
    4. 测试： 
    ```
    @Test
	public void test01() { 
		ApplicationContext applicationContext = new AnnotationConfigApplicationContext(MainConfigOfAOP.class);
		MathCalculator calculator = applicationContext.getBean(MathCalculator.class);
		calculator.div(1, 0);
	}
    ```
2. **aop原理**：
    1. `@EnableAspectJAutoProxy`注解作用：
        1. `@Import(AspectJAutoProxyRegistrar.class)`：给容器中导入`AspectJAutoProxyRegistrar`。利用`AspectJAutoProxyRegistrar`给容器中注册bean（注册了`AnnotationAwareAspectJAutoProxyCreator`创建器）。
        2. `AnnotationAwareAspectJAutoProxyCreator`继承结构：
            1. 继承结构：
            ```
            AnnotationAwareAspectJAutoProxyCreator
                ->AspectJAwareAdvisorAutoProxyCreator
                    ->AbstractAdvisorAutoProxyCreator
                        ->AbstractAutoProxyCreator
                            ->ProxyProcessorSupport
                            implements SmartInstantiationAwareBeanPostProcessor, BeanFactoryAware
            ```
            2. `AbstractAutoProxyCreator`重要方法：
                1. `setBeanFactory(BeanFactory beanFactory)`
                2. `postProcessBeforeInstantiation()` && `postProcessAfterInstantiation()`后置处理器方法
            3. `AbstractAdvisorAutoProxyCreator`重要方法：
                1. `setBeanFactory(BeanFactory beanFactory)`
                2. `initBeanFactory(ConfigurableListableBeanFactory beanFactory)`
            4. `AnnotationAwareAspectJAutoProxyCreator`重要方法：
                1. `initBeanFactory(ConfigurableListableBeanFactory beanFactory)`
        3. 方法调用流程：
            1. 传入配置类，创建ioc容器；
            2. 注册配置类，调用AbstractApplicationContext.refresh()，创建spring容器配置：
            ```
            public void refresh() throws BeansException, IllegalStateException {
        		synchronized (this.startupShutdownMonitor) {
        			// Prepare this context for refreshing.
        			prepareRefresh();
        
        			// Tell the subclass to refresh the internal bean factory.
        			//主要是创建beanFactory，同时加载配置文件.xml中的beanDefinition
			        //通过String[] configLocations = getConfigLocations()获取资源路径，然后加载beanDefinition
        			ConfigurableListableBeanFactory beanFactory = obtainFreshBeanFactory();
        
        			// Prepare the bean factory for use in this context.
        			// 给beanFactory注册一些标准组件，如ClassLoader，BeanPostProcess
        			prepareBeanFactory(beanFactory);
        
        			try {
        				// Allows post-processing of the bean factory in context subclasses.
                        //提供给子类实现一些postProcess的注册，如AbstractRefreshableWebApplicationContext注册一些Servlet相关的
				        //postProcess，真对web进行生命周期管理的Scope，通过registerResolvableDependency()方法注册指定ServletRequest，HttpSession，WebRequest对象的工厂方法
        				postProcessBeanFactory(beanFactory);
        
        				// Invoke factory processors registered as beans in the context.
        				//调用所有BeanFactoryProcessor的postProcessBeanFactory()方法
        				invokeBeanFactoryPostProcessors(beanFactory);
        
        				// Register bean processors that intercept bean creation.
        				//注册BeanPostProcessor，BeanPostProcessor作用是用于拦截Bean的创建
        				registerBeanPostProcessors(beanFactory);
        
        				// Initialize message source for this context.
        				//初始化消息Bean
        				initMessageSource();
        
        				// Initialize event multicaster for this context.
        				//初始化上下文的事件多播组件，ApplicationEvent触发时由multicaster通知给ApplicationListener
        				initApplicationEventMulticaster();
        
        				// Initialize other special beans in specific context subclasses.
        				//ApplicationContext初始化一些特殊的bean
        				onRefresh();
        
        				// Check for listener beans and register them.
        				//注册事件监听器，事件监听Bean统一注册到multicaster里头，ApplicationEvent事件触发后会由multicaster广播
        				registerListeners();
        
        				// Instantiate all remaining (non-lazy-init) singletons.
        				//非延迟加载的单例Bean实例化
        				finishBeanFactoryInitialization(beanFactory);
        
        				// Last step: publish corresponding event.
        				finishRefresh();
        			}
        
        			catch (BeansException ex) {
        				if (logger.isWarnEnabled()) {
        					logger.warn("Exception encountered during context initialization - " +
        							"cancelling refresh attempt: " + ex);
        				}
        
        				// Destroy already created singletons to avoid dangling resources.
        				destroyBeans();
        
        				// Reset 'active' flag.
        				cancelRefresh(ex);
        
        				// Propagate exception to caller.
        				throw ex;
        			}
        
        			finally {
        				// Reset common introspection caches in Spring's core, since we
        				// might not ever need metadata for singleton beans anymore...
        				resetCommonCaches();
        			}
        		}
        	}
            ```
        4. 注册BeanPostProcessor：
            >1. 【BeanPostProcessor是在bean对象创建完成初始化前后调用的】
            >2. 【InstantiationAwareBeanPostProcessor是在创建Bean实例之前先尝试用后置处理器返回对象】
            >3. 【AnnotationAwareAspectJAutoProxyCreator实现了InstantiationAwareBeanPostProcessor】
        
        5. 每一个bean创建之前，调用postProcessBeforeInstantiation()：
        ```
        public Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeansException {
        	Object cacheKey = getCacheKey(beanClass, beanName);
        
        	if (beanName == null || !this.targetSourcedBeans.contains(beanName)) {
        		if (this.advisedBeans.containsKey(cacheKey)) {
        			return null;
        		}
        		if (isInfrastructureClass(beanClass) || shouldSkip(beanClass, beanName)) {
        			this.advisedBeans.put(cacheKey, Boolean.FALSE);
        			return null;
        		}
        	}
        
        	// Create proxy here if we have a custom TargetSource.
        	// Suppresses unnecessary default instantiation of the target bean:
        	// The TargetSource will handle target instances in a custom fashion.
        	if (beanName != null) {
        		TargetSource targetSource = getCustomTargetSource(beanClass, beanName);
        		if (targetSource != null) {
        			this.targetSourcedBeans.add(beanName);
        			Object[] specificInterceptors = getAdvicesAndAdvisorsForBean(beanClass, beanName, targetSource);
        			Object proxy = createProxy(beanClass, beanName, specificInterceptors, targetSource);
        			this.proxyTypes.put(cacheKey, proxy.getClass());
        			return proxy;
        		}
        	}
        
        	return null;
        }
        ```
        6. 创建对象后，调用postProcessAfterInitialization();return wrapIfNecessary(bean, beanName, cacheKey)来判断对象是否要包装：
        ```
        /**
         * Wrap the given bean if necessary, i.e. if it is eligible for being proxied.
         * @param bean the raw bean instance
         * @param beanName the name of the bean
         * @param cacheKey the cache key for metadata access
         * @return a proxy wrapping the bean, or the raw bean instance as-is
         */
        protected Object wrapIfNecessary(Object bean, String beanName, Object cacheKey) {
        	if (beanName != null && this.targetSourcedBeans.contains(beanName)) {
        		return bean;
        	}
        	if (Boolean.FALSE.equals(this.advisedBeans.get(cacheKey))) {
        		return bean;
        	}
        	if (isInfrastructureClass(bean.getClass()) || shouldSkip(bean.getClass(), beanName)) {
        		this.advisedBeans.put(cacheKey, Boolean.FALSE);
        		return bean;
        	}
        
        	// Create proxy if we have advice.
        	Object[] specificInterceptors = getAdvicesAndAdvisorsForBean(bean.getClass(), beanName, null);
        	if (specificInterceptors != DO_NOT_PROXY) {
        		this.advisedBeans.put(cacheKey, Boolean.TRUE);
        		Object proxy = createProxy(
        				bean.getClass(), beanName, specificInterceptors, new SingletonTargetSource(bean));
        		this.proxyTypes.put(cacheKey, proxy.getClass());
        		return proxy;
        	}
        
        	this.advisedBeans.put(cacheKey, Boolean.FALSE);
        	return bean;
        }
        ```
        如果对象需要包装，则给容器返回当前组件使用cglib增强了的代理对象，以后容器中获取到的就是这个组件的代理对象，执行目标方法的时候，代理对象就会执行通知方法的流程。