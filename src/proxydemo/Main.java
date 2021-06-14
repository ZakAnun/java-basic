package proxydemo;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;

/**
 * 演示动态代理
 *
 * 在调用接口方法的时候，通过 InvocationHandler 中的 invoke() 可以拿到 GitHubApiService 的方法信息
 * 并且在调用的时候，可以执行相关的逻辑（这也是 Retrofit 的原理）
 *
 * 在调用 GitHubApiService#listRepo(str) 时，实际上调用是通过 Proxy.newProxyInstance() 中的
 * InvocationHandler#invoke()
 *
 * 动态（运行期）代理（实现了接口的具体类），
 * 在运行的过程中，会动态创建接口的实现类作为代理对象并执行 InvocationHandler#invoke()
 *
 */
public class Main {

    public static void main(String[] args) {
        //通过动态代理获取 ApiService 的对象
        GitHubApiService apiService = (GitHubApiService) Proxy.newProxyInstance(
                GitHubApiService.class.getClassLoader(),
                new Class[]{GitHubApiService.class},
                new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

                        System.out.println("method = " + method.getName() + "   args = " + Arrays.toString(args));

                        return null;
                    }
                });

        System.out.println(apiService.getClass());
        //调用 listRepos 方法
        apiService.listRepos("octcat");
    }
}
