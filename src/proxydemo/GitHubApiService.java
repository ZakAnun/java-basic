package proxydemo;

/**
 * 模拟 Retrofit，定义 API 接口
 */
public interface GitHubApiService {
    void listRepos(String user);
}
