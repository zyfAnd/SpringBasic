@Service
**
**@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface Service {
@AliasFor(
annotation = Component.class
)
String value() default "";
}
**

@Component
**
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Indexed
public @interface Component {
String value() default "";
}
**