package annotationdemo;

import java.lang.reflect.Field;

public class AnnotationDemo {

    @Report(type = 12, level = "public", value = "哈哈哈哈哈哈哈哈")
    public String reportStr = "publicStr";

    @Report(type = 10, level = "private", value = "嘻嘻嘻嘻嘻嘻嘻嘻")
    private String privateReportStr = "privateStr";

    public static void main(String[] args) {
        AnnotationDemo annotationDemo = new AnnotationDemo();

        annotationDemo.checkAnnotation(annotationDemo);
    }

    private void checkAnnotation(AnnotationDemo annotationDemo) {
        Class cls = annotationDemo.getClass();
        Field[] fields = cls.getDeclaredFields();
        for (Field field : fields) {
            Report report = field.getAnnotation(Report.class);
            if (report != null) {
                // report 注解存在
                System.out.println("report.type = " + report.type() + ", report.level = " + report.level() + ", report.value = " + report.value());
            }
        }
    }
}
